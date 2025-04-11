
import io.restassured.http.Method;
import io.restassured.response.Response;

import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class RestAssuredTEST extends BaseTest {

    private BoardPUT boardPUT;
    private String boardID;
    private String name;

    public RestAssuredTEST() {
    }

    @BeforeSuite
    public void setUpTest(){
        boardPUT = new BoardPUT("Upgrades","List of upgrades");
        name = "IGN";
    }

    @Test(priority = 1)
    public void POSTBoard() {
            Response response = TestSteps.sendRequest(requestSpec,Method.POST,name);

            Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Board is not created!");

            Board board = TestSteps.prepareActualResponse(response, Board.class);
            TestSteps.checkActualVsExpectedResponses(board.getName(),name);

            boardID = board.getId();
        }

    @Test(priority = 2)
    public void GETBoard() {
          Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,boardID), Method.GET);

          Assert.assertTrue(TestSteps.checkResponseIsValid(response));

          Board actualBoard = TestSteps.prepareActualResponse(response, Board.class);
          TestSteps.checkActualVsExpectedResponses(actualBoard.getName(),name);
          TestSteps.checkActualVsExpectedResponses(actualBoard.getId(),boardID);
        }

    @Test(priority = 3)
    public void PUTBoard() {
            Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,boardID),Method.PUT, boardPUT);

            Assert.assertTrue(TestSteps.checkResponseIsValid(response));

            Board board = TestSteps.prepareActualResponse(response, Board.class);

            TestSteps.checkActualVsExpectedResponses(board.getId(),boardID);
            TestSteps.checkActualVsExpectedResponses(board.getName(), boardPUT.getName());
            TestSteps.checkActualVsExpectedResponses(board.getDesc(), boardPUT.getDesc());
        }

    @Test(priority = 4)
    public void DELETEBoard() {
        Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,boardID),Method.DELETE);

        Assert.assertTrue(TestSteps.checkResponseIsValid(response));
    }
}
