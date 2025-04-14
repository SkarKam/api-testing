package RESTAssuredTests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetBoard extends BaseTest {

    @Test
    public void getBoard(){

        Board board = TestSteps.createBoard(requestSpec,"Portable Board");

        Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,board.getId()), Method.GET);
        Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Fail to get board");

        Board actualBoard = TestSteps.prepareActualResponse(response, Board.class);
        TestSteps.checkActualVsExpectedResponses(actualBoard,board);

        TestSteps.deleteBoard(requestSpec,actualBoard);

    }

}
