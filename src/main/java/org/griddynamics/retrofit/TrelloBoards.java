package org.griddynamics.retrofit;

import io.github.cdimascio.dotenv.Dotenv;
import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPUT;
import retrofit2.Call;
import retrofit2.http.*;

public interface TrelloBoards {

    @GET("1/boards/{id}")
    Call<Board> get(@Path("id") String id, @Query("key") String key, @Query("token") String token);

    @POST("1/boards/")
    Call<Board> post(@Query("key") String key, @Query("token") String token, @Query("name") String name, @Query("idOrganization") String idOrganization);

    @PUT("1/boards/{id}")
    Call<Board> put(@Path("id") String id, @Query("key") String key, @Query("token") String token , @Body BoardPUT boardPUT);

    @DELETE("1/boards/{id}")
    Call<Void> delete(@Path("id") String id, @Query("key") String key, @Query("token") String token);
}
