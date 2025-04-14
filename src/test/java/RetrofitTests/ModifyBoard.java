package RetrofitTests;

import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestStepsRetrofit;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

public class ModifyBoard extends BaseTest {

    @Test
    public void modifyBoard() throws IOException {
        Call<Board> board = trelloBoard.post(Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),"NewBoard", Dotenv.load().get("ORG_ID"));
        Response<Board> response = board.execute();
        Board expectedBoard = response.body();

        BoardPUT boardModification = BoardPUT.builder()
                .name("ModifyBoard")
                .desc("New add-ons")
                .build();

        Call<Board> actualCall = trelloBoard.put(expectedBoard.getId(),Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),boardModification );
        Response<Board> actualResponse = actualCall.execute();

        Assert.assertTrue(actualResponse.isSuccessful(),"Fail to modify board");
        Board actualBoard = actualResponse.body();

        Assert.assertEquals(actualBoard.getId(),expectedBoard.getId(),"Id are different");
        Assert.assertEquals(actualBoard.getName(),boardModification.getName(),"Name are different");
        Assert.assertEquals(actualBoard.getDesc(),boardModification.getDesc(),"Description are different");
        TestStepsRetrofit.removeBoard(actualBoard);
    }
}
