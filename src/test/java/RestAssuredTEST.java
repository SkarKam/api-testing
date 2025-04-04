import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.griddynamics.models.Board;
import org.griddynamics.models.dtos.BoardPOST;
import org.griddynamics.models.dtos.BoardPUT;
import org.griddynamics.utilities.BaseTest;
import org.griddynamics.utilities.TestSteps;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


import java.net.URISyntaxException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTEST extends BaseTest {
    private static String uri = "https://api.trello.com/";
    private static String key;
    private static String token;
    private BoardPOST boardPOST;
    private BoardPUT boardPUT;
    private static String boardID;

        public RestAssuredTEST() throws URISyntaxException {
        }

        @BeforeSuite
        public void setUpTest(){
            boardPOST = new BoardPOST("Counter-Strike", "1.6");
            boardPUT = new BoardPUT("Praetorians", "1.3");
            Dotenv dotenv = Dotenv.load();
            key = dotenv.get("TRELLO_WORKPLACE_KEY");
            token = dotenv.get("TRELLO_WORKPLACE_TOKEN");
        }

        //TODO: Repair sendRequest methodś
        @Test(priority = 1)
        public void POSTBoard() {
            System.out.println(requestSpec);
            Response response = TestSteps.sendRequest(requestSpec,"POST",boardPOST);

            System.out.println(TestSteps.url(requestSpec,boardPOST));
            Assert.assertTrue(TestSteps.checkResponseIsValid(response),"Board is not created!");

            Board board = TestSteps.prepareActualResponse(response, Board.class);

            TestSteps.checkActualVsExpectedResponses(TestSteps.prepareActualResponse(response, Board.class),TestSteps.prepareExpectedResponse(boardPOST));

            boardID = board.getId();
//            boardID = given()
//                        .baseUri(uri)
//                        .queryParam("key",key)
//                        .queryParam("token",token)
//                        .contentType(ContentType.JSON)
//                        .body(boardPOST)
//                    .when()
//                        .post("1/boards/")
//                    .then()
//                        .statusCode(200)
//                        .body("name", equalTo(boardPOST.getName()))
//                        .body("desc", equalTo(boardPOST.getDesc()))
//                        .extract().path("id");

        }
    @Test(priority = 2)
    public void GETBoard(){
          Response response = TestSteps.sendRequest(TestSteps.buildRequest(requestSpec),"GET");

          Assert.assertTrue(TestSteps.checkResponseIsValid(response));

          Board board = TestSteps.prepareActualResponse(response, Board.class);
          TestSteps.checkActualVsExpectedResponses(board,boardPOST);

//        given()
//                .baseUri(uri)
//                .queryParam("key",key)
//                .queryParam("token",token)
//                .pathParam("id",boardID)
//                .contentType(ContentType.JSON)
//            .when()
//                .get("1/boards/{id}")
//            .then()
//                .assertThat().statusCode(200)
//                .body("id",equalTo(boardID))
//                .body("name",equalTo(boardPOST.getName()))
//                .body("desc",equalTo(boardPOST.getDesc()));

    }
    @Test(priority = 3)
    public void PUTBoard(){
        given()
                .baseUri(uri)
                .queryParam("key",key)
                .queryParam("token",token)
                .pathParam("id",boardID)
                .contentType(ContentType.JSON)
                .body(boardPUT)
            .when()
                .put("1/boards/{id}")
            .then()
                .statusCode(200)
                .body("id",equalTo(boardID))
                .body("name",equalTo(boardPUT.getName()))
                .body("desc", equalTo(boardPUT.getDesc()));
    }


    @Test(priority = 4)
    public void DELETEBoard(){
        given()
                .baseUri(uri)
                .queryParam("key",key)
                .queryParam("token",token)
                .pathParam("id",boardID)
                .contentType(ContentType.JSON)
            .when()
                .delete("1/boards/{id}")
            .then()
                .statusCode(200);
    }
}
