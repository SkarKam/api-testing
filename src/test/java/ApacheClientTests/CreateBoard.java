package ApacheClientTests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.griddynamics.utilities.TestStepsApache;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class CreateBoard extends BaseTest {

    @Test
    public void createBoard() throws URISyntaxException, IOException {
        HttpPost httpPost = (HttpPost) TestStepsApache.buildRequest(uri, Method.POST,"Apache");
        CloseableHttpResponse response = TestStepsApache.sendRequest(httpPost);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response), "Fail to create board");
        Board actualBoard = TestStepsApache.prepareActualResponse(response,Board.class);

        TestStepsApache.checkActualVsExpectedResponses(actualBoard.getName(),"Apache");
        TestStepsApache.deleteBoard(actualBoard);

    }
}
