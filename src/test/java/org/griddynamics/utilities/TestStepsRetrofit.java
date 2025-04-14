package org.griddynamics.utilities;
import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import static org.griddynamics.utilities.BaseTest.trelloBoard;

public class TestStepsRetrofit {

    public static Retrofit buildRequest(String... boardIDorName) {
        return new Retrofit.Builder()
                .baseUrl(Dotenv.load().get("TRELLO_URL"))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public static void removeBoard(Board board) throws IOException {
        Call<Void> voidCall = trelloBoard.delete(board.getId(),Dotenv.load().get("TRELLO_WORKPLACE_KEY"),Dotenv.load().get("TRELLO_WORKPLACE_TOKEN") );
        voidCall.execute();
    }
}
