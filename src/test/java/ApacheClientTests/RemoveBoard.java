package ApacheClientTests;

import io.restassured.http.Method;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsApache;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.Closeable;
import java.io.IOException;
import java.net.URISyntaxException;

public class RemoveBoard extends BaseTest {

    @Test
    public void removeBoard() throws URISyntaxException, IOException {
        Board board = TestStepsApache.createBoard();

        HttpDelete request = (HttpDelete) TestStepsApache.buildRequest(uri, Method.DELETE,board.getId());
        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to delete board");
    }
}
