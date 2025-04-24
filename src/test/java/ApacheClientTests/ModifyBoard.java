package ApacheClientTests;

import io.restassured.http.Method;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsApache;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class ModifyBoard extends BaseTest {

    @Test
    public void modifyBoard() throws URISyntaxException, IOException {
        Board board = TestStepsApache.createBoard();
        BoardPUT boardModification = BoardPUT.builder()
                .name("ModifyBoard")
                .desc("New add-ons")
                .build();

        HttpPut request = (HttpPut) TestStepsApache.buildRequest(uri,Method.PUT,board.getId());
        request = TestStepsApache.setHeaderToRequest(request);
        request = TestStepsApache.setEntityToRequest(request,boardModification);
        CloseableHttpResponse response = TestStepsApache.sendRequest(request);

        Assert.assertTrue(TestStepsApache.checkResponseIsValid(response),"Fail to modify board");

        Board actualBoard = TestStepsApache.prepareActualResponse(response, Board.class);

        TestStepsApache.checkActualVsExpectedResponses(actualBoard.getName(),boardModification.getName());
        TestStepsApache.checkActualVsExpectedResponses(actualBoard.getDesc(),boardModification.getDesc());

        TestStepsApache.deleteBoard(actualBoard);
    }
}
