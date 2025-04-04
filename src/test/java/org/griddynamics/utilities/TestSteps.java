package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.griddynamics.exceptions.NoHTTPMethodException;
import org.testng.Assert;

public class TestSteps {
    private static Dotenv dotenv = Dotenv.load();

    public static RequestSpecification buildRequest(RequestSpecification request, String ...boardID){
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder()
                .addRequestSpecification(request)
                .addParam("key",dotenv.get("TRELLO_WORKPLACE_KEY"))
                .addParam("token",dotenv.get("TRELLO_WORKPLACE_TOKEN"))
                .addHeader("Content-Type","application/json");


        if(boardID!=null && boardID.length > 0){
            requestSpecBuilder.addPathParam("id",boardID);
        }
        return requestSpecBuilder.build();
    }
    public static Response sendRequest(RequestSpecification request, String method, String ...body){
        return switch (method.toUpperCase()) {
            case "GET" -> request.get(dotenv.get("ENDPOINT"));
            case "POST" -> request.body(body).post(dotenv.get("ENDPOINT"));
            case "PUT" -> request.body(body).put(dotenv.get("ENDPOINT"));
            case "DELETE" -> request.delete(dotenv.get("ENDPOINT"));
            default -> throw new NoHTTPMethodException("HTTP Method not recognize");
        };
    }
    public static boolean checkResponseIsValid(Response response){
        return response.statusCode()==200;
    }
    public static <T> T prepareActualResponse(Response response, Class<T> tClass){
        return response.getBody().as(tClass);
    }

    public static Object prepareExpectedResponse(Object object){
        return object;
    }
    public static void checkActualVsExpectedResponses(Object actual, Object expected){
        Assert.assertEquals(actual,expected);
    }
}

