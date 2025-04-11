
import io.restassured.http.Method;
import org.apache.http.client.methods.*;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsApache;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;

import java.net.URISyntaxException;


public class ApacheClientTEST extends BaseTest {

    private String boardID;
    private BoardPUT boardPUT;
    private String boardPOSTName;


    public ApacheClientTEST() {
    }

    @BeforeSuite
    public void setUp() {
        boardPUT = new BoardPUT("Upgrades","List of upgrades");
        boardPOSTName = "Chaos";
    }

    @Test(priority = 1)
    public void POSTBoard() throws IOException, URISyntaxException {
        HttpPost request = (HttpPost) TestStepsApache.buildRequest(uri,Method.POST, boardPOSTName);
        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to create board");

        Board board = TestStepsApache.prepareActualResponse(response, Board.class);
        Assert.assertEquals(board.getName(),boardPOSTName);
        boardID = board.getId();
    }

    @Test(priority = 2)
    public void GETBoardByID() throws URISyntaxException, IOException {
        HttpGet get = (HttpGet) TestStepsApache.buildRequest(uri,Method.GET,boardID);
        CloseableHttpResponse response = TestStepsApache.sendRequest(get);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to get a board with id: "+boardID);

        Board board = TestStepsApache.prepareActualResponse(response, Board.class);
        TestStepsApache.checkActualVsExpectedResponses(board.getId(),boardID);
    }

    @Test(priority = 3)
    public void PUTBoard() throws IOException, URISyntaxException {
        HttpPut request = (HttpPut) TestStepsApache.buildRequest(uri,Method.PUT,boardID);
        request = TestStepsApache.setHeaderToRequest(request);
        request = TestStepsApache.setEntityToRequest(request,boardPUT);
        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to reach board with PUT!");

        Board actualResponse = TestStepsApache.prepareActualResponse(response, Board.class);
        TestStepsApache.checkActualVsExpectedResponses(actualResponse.getId(),boardID);
        TestStepsApache.checkActualVsExpectedResponses(actualResponse.getName(),boardPUT.getName());
        TestStepsApache.checkActualVsExpectedResponses(actualResponse.getDesc(),boardPUT.getDesc());
    }

    @Test(priority = 4)
    public void DELETEBoard() throws IOException, URISyntaxException {
        HttpDelete request = (HttpDelete) TestStepsApache.buildRequest(uri,Method.DELETE,boardID);
        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to delete board");
    }
}
