package RESTAssuredTests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateBoard extends BaseTest {

    @Test
    public void createBoard(){

        Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec), Method.POST, "BoardTrel");
        Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Fail to create board");

        Board board = TestSteps.prepareActualResponse(response,Board.class);
        TestSteps.checkActualVsExpectedResponses(board.getName(),"BoardTrel");

        TestSteps.deleteBoard(requestSpec,board);
    }
}
