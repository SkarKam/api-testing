package org.griddynamics.retrofit;

import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPOST;
import org.griddynamics.models.dtos.BoardPUT;
import retrofit2.Call;
import retrofit2.http.*;

public interface TrelloBoards {
    @GET("1/boards/{id}")
    Call<Board> get(@Path("id") String id, @Query("key") String key, @Query("token") String token);

    @POST("1/boards/")
    Call<Board> post(@Query("key") String key, @Query("token") String token, @Body BoardPOST boardPOST);

    @PUT("1/boards/{id}")
    Call<Board> put(@Path("id") String id, @Query("key") String key, @Query("token") String token , @Body BoardPUT boardPUT);

    @DELETE("1/boards/{id}")
    Call<Void> delete(@Path("id") String id, @Query("key") String key, @Query("token") String token);
}
