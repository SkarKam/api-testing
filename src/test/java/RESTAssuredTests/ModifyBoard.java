package RESTAssuredTests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ModifyBoard extends BaseTest {

    @Test
    public void ModifyBoard(){
        Board boardToModify = TestSteps.createBoard(requestSpec,"BoardToModify");
        BoardPUT boardModification = BoardPUT.builder()
                .name("ModifyBoard")
                .desc("New add-ons")
                .build();

        Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,boardToModify.getId()), Method.PUT,boardModification);

        Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Fail to modify board");

        Board actualBoard = TestSteps.prepareActualResponse(response, Board.class);
        TestSteps.checkActualVsExpectedResponses(actualBoard.getId(),boardToModify.getId());
        TestSteps.checkActualVsExpectedResponses(actualBoard.getName(),boardModification.getName());
        TestSteps.checkActualVsExpectedResponses(actualBoard.getDesc(),boardModification.getDesc());

        TestSteps.deleteBoard(requestSpec,actualBoard);
    }
}
