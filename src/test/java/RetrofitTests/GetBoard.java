package RetrofitTests;

import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsRetrofit;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class GetBoard extends BaseTest {

    @Test
    public void getBoardById() throws IOException {
        Call<Board> board = trelloBoard.post(Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),"NewBoard", Dotenv.load().get("ORG_ID"));
        Response<Board> response = board.execute();
        Board expectBoard = response.body();

        Call<Board> boardCall = trelloBoard.get(response.body().getId(),Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"));
        Response<Board> actualResponse = boardCall.execute();
        Assert.assertTrue(actualResponse.isSuccessful(),"Fail to get a board");

        Board actualBoard = actualResponse.body();
        Assert.assertEquals(actualBoard.getId(),expectBoard.getId(),"Id are diffrent");
        Assert.assertEquals(actualBoard,expectBoard,"Objects are diffrent");
        TestStepsRetrofit.removeBoard(actualBoard);
    }
}
