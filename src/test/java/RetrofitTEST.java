import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPOST;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.retrofit.TrelloBoards;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;

public class RetrofitTEST {

    Retrofit retrofit;
    TrelloBoards trelloBoard;
    private String uri = "https://api.trello.com";
    private String key, token;
    private BoardPOST boardPOST;
    private BoardPUT boardPUT;
    private String boardId;

    @BeforeSuite
    public void setUp() {
        boardPOST = new BoardPOST("Counter Strike", "1.6");
        boardPUT = new BoardPUT("Praetorians", "1.3");
        Dotenv dotenv = Dotenv.load();
        key = dotenv.get("TRELLO_WORKPLACE_KEY");
        token = dotenv.get("TRELLO_WORKPLACE_TOKEN");

        retrofit = new Retrofit.Builder()
                .baseUrl(uri)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        trelloBoard = retrofit.create(TrelloBoards.class);
    }


    @Test(priority = 1)
    public void POSTBoard() throws IOException {
        Call<Board> call = trelloBoard.post(key,token,boardPOST);
        Response<Board> response = call.execute();

        Assert.assertTrue(response.isSuccessful(), "Fail to post a board");
        Board posted = response.body();

        Assert.assertNotNull(posted);
        Assert.assertEquals(posted.getName(),boardPOST.getName(),"Board name didn't change");
        Assert.assertEquals(posted.getDesc(),boardPOST.getDesc(),"Board description didn't change");

        boardId = posted.getId();

    }

    @Test(priority = 2)
    public void GETBoardById() throws IOException {
        Call<Board> call = trelloBoard.get(boardId,key,token);
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
        Call<Board> call = trelloBoard.put(boardId,key,token,boardPUT);
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
        Call<Void> call = trelloBoard.delete(boardId,key,token);
        Response<Void> response = call.execute();

        Assert.assertTrue(response.isSuccessful(),"Fail to delete a board");
    }
}
