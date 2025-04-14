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

public class CreateBoard extends BaseTest {

    @Test
    public void createBoard() throws IOException {
        Call<Board> call = trelloBoard.post(Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),"NewBoard", Dotenv.load().get("ORG_ID"));
        Response<Board> response = call.execute();

        Assert.assertTrue(response.isSuccessful(),"Fail to create a board");
        Board actualBoard = response.body();

        Assert.assertEquals(actualBoard.getName(),"NewBoard","Names are diffrent!");
        TestStepsRetrofit.removeBoard(actualBoard);
    }
}
