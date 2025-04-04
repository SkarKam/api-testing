import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.griddynamics.models.dtos.BoardPOST;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class ApacheClientTEST extends BaseTest {
    private BoardPOST boardPOST;
    private BoardPUT boardPUT;
    private URI uri1, uri2;
    private String boardID;
    private String key;
    private  String token;


    public ApacheClientTEST() throws URISyntaxException {
    }

    @BeforeSuite
    public void setUp() throws URISyntaxException {
        Dotenv dotenv = Dotenv.load();
        key= dotenv.get("TRELLO_WORKPLACE_KEY");
        token=dotenv.get("TRELLO_WORKPLACE_TOKEN");
        boardPOST = new BoardPOST("Terraria","Release 2.13.7");
        boardPUT = new BoardPUT("Roblox","New mode");
        uri1 = new URI("https://api.trello.com/1/boards/");
        URIBuilder uriBuilder = new URIBuilder(uri1);
        uriBuilder.addParameter("key", key)
                .addParameter("token",token);

        uri1 = uriBuilder.build();
    }

    @Test(priority = 1)
    public void POSTBoard() throws IOException {
        HttpPost request = new HttpPost(uri1);
        request.setHeader("Content-Type", "application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonPayload = objectMapper.writeValueAsString(boardPOST);
        StringEntity entity = new StringEntity(jsonPayload);
        request.setEntity(entity);
        try(CloseableHttpClient client = HttpClients.createDefault()) {
            CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request);

            String jsonResponse = EntityUtils.toString(response.getEntity());
            Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

            JsonNode node = objectMapper.readTree(jsonResponse);

            String name = node.get("name").asText();
            String desc = node.get("desc").asText();

            Assert.assertEquals(name,"Terraria");
            Assert.assertEquals(desc,"Release 2.13.7");
            boardID = node.get("id").asText();
        }
//        HttpRequest request = new HttpPost(String.valueOf(TestSteps.buildRequest(requestSpec)));

    }

    @Test(priority = 2)
    public void GETBoardByID() throws URISyntaxException {
        uri2 = new URI("https://api.trello.com/1/boards/"+boardID);
        URIBuilder uriBuilder = new URIBuilder(uri2);
        uriBuilder.addParameter("key", key)
                .addParameter("token",token);

        uri2 = uriBuilder.build();
        HttpGet request = new HttpGet(uri2);
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);

            Assert.assertEquals(response.getStatusLine().getStatusCode(), HttpStatus.SC_OK);

            String jsonResponse = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonResponse);

            String boardName = jsonNode.get("name").asText();
            String boardDesc = jsonNode.get("desc").asText();

            Assert.assertEquals(boardName,"Terraria");
            Assert.assertEquals(boardDesc,"Release 2.13.7");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test(priority = 3)
    public void PUTBoard() throws IOException, URISyntaxException {
        uri2 = new URI("https://api.trello.com/1/boards/"+boardID);
        URIBuilder uriBuilder = new URIBuilder(uri2);
        uriBuilder.addParameter("key", key)
                .addParameter("token",token);

        uri2 = uriBuilder.build();

        HttpPut request = new HttpPut(uri2);
        request.setHeader("Content-Type","application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String toJson = objectMapper.writeValueAsString(boardPUT);

        request.setEntity(new StringEntity(toJson));

        try(CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);

            String resp = EntityUtils.toString(response.getEntity());
            Assert.assertEquals(response.getStatusLine().getStatusCode(),HttpStatus.SC_OK);

            JsonNode jsonNode = objectMapper.readTree(resp);
            String name = jsonNode.get("name").asText();
            String description = jsonNode.get("desc").asText();

            Assert.assertEquals(name,"Roblox");
            Assert.assertEquals(description,"New mode");
            boardID = jsonNode.get("id").asText();
        }

    }
    @Test(priority = 4)
    public void DELETEBoard() throws IOException, URISyntaxException {
        uri2 = new URI("https://api.trello.com/1/boards/"+boardID);
        URIBuilder uriBuilder = new URIBuilder(uri2);
        uriBuilder.addParameter("key", key)
                .addParameter("token",token);

        uri2 = uriBuilder.build();
        HttpDelete request = new HttpDelete(uri2);
        try(CloseableHttpClient client = HttpClients.createDefault()){
            CloseableHttpResponse response = client.execute(request);
            Assert.assertEquals(response.getStatusLine().getStatusCode(),HttpStatus.SC_OK);
        }
    }
}
