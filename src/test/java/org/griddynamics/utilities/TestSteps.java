package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import org.griddynamics.exceptions.NoHTTPMethodException;
import org.griddynamics.models.dtos.BoardPUT;
import org.testng.Assert;


public class TestSteps {

        public static RequestSpecification buildRequest(RequestSpecification request, String... boardID) {
            if (boardID != null && boardID.length > 0) {
                request.given().pathParam("id", boardID[0]);
            }
                return request;

        }

        @SafeVarargs
        public static <T> Response sendRequest(RequestSpecification request, Method method, T... nameOrPUTBody) {
            if(nameOrPUTBody.length<=1){
            switch (method) {
                case GET:
                     return RestAssured.given()
                             .spec(request)
                             .when()
                             .get(Dotenv.load().get("ENDPOINT")+"{id}");

                case POST:
                     return RestAssured.given()
                        .spec(request)
                        .queryParam("name", nameOrPUTBody)
                        .contentType(ContentType.JSON)
                        .when()
                        .post(Dotenv.load().get("ENDPOINT"));

                case PUT:
                     BoardPUT boardPUT = (BoardPUT) nameOrPUTBody[0];
                     return RestAssured.given()
                                .spec(request)
                                .contentType(ContentType.JSON)
                                .body(boardPUT)
                                .when()
                                .put(Dotenv.load().get("ENDPOINT")+"{id}");

                case DELETE:
                     return RestAssured
                             .given()
                             .spec(request)
                             .when()
                             .delete(Dotenv.load().get("ENDPOINT")+"{id}");
                 default:
                     throw new NoHTTPMethodException("HTTP Method not recognize");
                }
            }
            throw new IllegalArgumentException("Vargant is too long");
        }

        public static boolean checkResponseIsValid(Response response){
                return response.statusCode()==HttpStatus.SC_OK;
        }
        public static <T> T prepareActualResponse(Response response, Class<T> tClass){
            return response.getBody().as(tClass);
        }

        public static void checkActualVsExpectedResponses(Object actual, Object expected){
            Assert.assertEquals(actual,expected,"Object have different "+actual.getClass().getName());
        }
}

