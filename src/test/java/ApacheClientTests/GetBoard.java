package ApacheClientTests;

import io.restassured.http.Method;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsApache;
import org.testng.Assert;
import org.testng.annotations.Test;


import java.io.IOException;
import java.net.URISyntaxException;

public class GetBoard extends BaseTest {

    @Test
    public void getBoard() throws URISyntaxException, IOException {
        Board board = TestStepsApache.createBoard();

        HttpGet request = (HttpGet) TestStepsApache.buildRequest(uri, Method.GET,board.getId());

        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to get a board");
        Board actualBoard = TestStepsApache.prepareActualResponse(response, Board.class);

        TestStepsApache.checkActualVsExpectedResponses(actualBoard,board);
        TestStepsApache.deleteBoard(actualBoard);
    }
}
