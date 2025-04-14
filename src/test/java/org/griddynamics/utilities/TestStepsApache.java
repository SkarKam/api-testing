package org.griddynamics.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.http.Method;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.griddynamics.exceptions.NoHTTPMethodException;

import org.griddynamics.models.Board;
import org.testng.Assert;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.griddynamics.utilities.BaseTest.uri;

public class TestStepsApache {

    public static Board createBoard() throws URISyntaxException, IOException {
        return prepareActualResponse(sendRequest(buildRequest(uri,Method.POST,"New board")), Board.class);
    }
    public static HttpUriRequest buildRequest(URI uri, Method method, String... boardIDorName) throws URISyntaxException {
        if (boardIDorName != null && boardIDorName.length == 1) {
            URIBuilder builder = new URIBuilder(uri);
            if(method.equals(Method.POST)){
                builder.addParameter("idOrganization", Dotenv.load().get("ORG_ID"));
                builder.addParameter("name", boardIDorName[0]);
            } else {
                builder.setPath(uri.getPath() + "/" + boardIDorName[0]);
            }
            uri = builder.build();
        }

        switch (method){
            case POST:
                return new HttpPost(uri);
            case GET:
                return new HttpGet(uri);
            case PUT:
                return new HttpPut(uri);
            case DELETE:
                return new HttpDelete(uri);
            default:
                throw new NoHTTPMethodException("HTTP method not found");

        }

    }

    public static <T> T setHeaderToRequest(HttpEntityEnclosingRequest request) {
        request.setHeader("Content-Type", "application/json");
        return (T) request;
    }

    public static <T,R> T setEntityToRequest(HttpEntityEnclosingRequest request, R entity) throws JsonProcessingException, UnsupportedEncodingException {
        ObjectMapper mapper = new ObjectMapper();
        request.setEntity(new StringEntity(mapper.writeValueAsString(entity)));
        return (T) request;
    }

    public static CloseableHttpResponse sendRequest(HttpUriRequest request) throws IOException {
        try(CloseableHttpClient client = HttpClients.createDefault()){

            CloseableHttpResponse response = client.execute(request);
            return response;
        }
    }

    public static boolean checkResponseIsValid(CloseableHttpResponse response){
        return response.getStatusLine().getStatusCode()==HttpStatus.SC_OK;
    }

    public static <T> T prepareActualResponse(CloseableHttpResponse response, Class<T> tClass) throws IOException {
        HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(entity.getContent(),tClass);
    }

    public static void checkActualVsExpectedResponses(Object actual, Object expected){
        Assert.assertEquals(actual,expected,"Object have different "+actual.getClass().getName());
    }

    public static void deleteBoard(Board board) throws URISyntaxException, IOException {
        sendRequest(buildRequest(uri,Method.DELETE,board.getId()));
    }
}
