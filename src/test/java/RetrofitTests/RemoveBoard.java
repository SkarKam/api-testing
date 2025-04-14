package RetrofitTests;

import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.utilities.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class RemoveBoard extends BaseTest {

    @Test
    public void removeBoard() throws IOException {
        Call<Board> call = trelloBoard.post(Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),"NewBoard", Dotenv.load().get("ORG_ID"));
        Response<Board> response = call.execute();
        Board board = response.body();

        Call<Void> actualCall = trelloBoard.delete(board.getId(),Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"));
        Response<Void> actualResponse = actualCall.execute();
        Assert.assertTrue(actualResponse.isSuccessful(),"Fail to remove board");
    }
}
