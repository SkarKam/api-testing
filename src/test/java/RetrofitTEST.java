import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.retrofit.TrelloBoards;
import org.griddynamics.utilities.TestStepsRetrofit;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.net.MalformedURLException;

public class RetrofitTEST {

    private Retrofit retrofit;
    private TrelloBoards trelloBoard;
    private BoardPUT boardPUT;
    private String name;
    private String boardId;

    @BeforeSuite
    public void setUp() throws MalformedURLException {
        boardPUT = new BoardPUT("Upgrades","List of upgrades");
        name = "IGN";
        retrofit = TestStepsRetrofit.buildRequest();

        trelloBoard = retrofit.create(TrelloBoards.class);
    }


    @Test(priority = 1)
    public void POSTBoard() throws IOException {
        Call<Board> call = trelloBoard.post(Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),name);
        Response<Board> response = call.execute();

        Assert.assertTrue(response.isSuccessful(), "Fail to post a board");
        Board posted = response.body();

        Assert.assertNotNull(posted);
        Assert.assertEquals(posted.getName(),name,"Board name didn't change");
        boardId = posted.getId();

    }

    @Test(priority = 2)
    public void GETBoardById() throws IOException {
        Call<Board> call = trelloBoard.get(boardId,Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"));
        Response<Board> response = call.execute();

        Assert.assertTrue(response.isSuccessful(),"Fail to get a board from Trello");
        Board getBoard = response.body();

        Assert.assertNotNull(getBoard);
        Assert.assertEquals(getBoard.getId(),boardId);
        Assert.assertNotNull(getBoard.getName(),"Board name cannot be null");
        Assert.assertNotNull(getBoard.getDesc(), "Description cannot be null");

    }
    @Test(priority = 3)
    public void PUTBoard() throws IOException{
        Call<Board> call = trelloBoard.put(boardId,Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"),boardPUT);
        Response<Board> response = call.execute();

        Assert.assertTrue(response.isSuccessful(),"Fail to modify the board");

        Board putBoard = response.body();

        Assert.assertNotNull(putBoard);
        Assert.assertEquals(putBoard.getId(),boardId,"Board ID is diffrent.");
        Assert.assertEquals(putBoard.getName(),boardPUT.getName(),"Board name didn't change");
        Assert.assertEquals(putBoard.getDesc(),boardPUT.getDesc(),"Board description didn't change");
    }


    @Test(priority = 4)
    public void DELETEBoard() throws IOException {
        Call<Void> call = trelloBoard.delete(boardId,Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN"));
        Response<Void> response = call.execute();

        Assert.assertTrue(response.isSuccessful(),"Fail to delete a board");
    }
}
