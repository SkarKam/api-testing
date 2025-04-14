package RESTAssuredTests;

import io.restassured.http.Method;
import io.restassured.response.Response;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RemoveBoard extends BaseTest {

    @Test
    public void removeBoard(){
            Board board = TestSteps.createBoard(requestSpec,"BoardToRemove");

        Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,board.getId()), Method.DELETE);
        Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Fail to remove board");

        Response responseNotFound = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec,board.getId()),Method.GET);
        Assert.assertTrue(TestSteps.isNotFound(responseNotFound),"Deleted response was found!");
    }
}
