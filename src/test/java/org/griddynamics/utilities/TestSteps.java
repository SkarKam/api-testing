package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.griddynamics.exceptions.NoHTTPMethodException;
import org.testng.Assert;


public class TestSteps {
        private static Dotenv dotenv = Dotenv.load();

        public static RequestSpecification buildRequest(RequestSpecification request, String... boardID) {
            if (boardID != null && boardID.length > 0) {
                request.given().pathParam("id", boardID);
            }
                return request;

        }
        public static String url(RequestSpecification request,  Object... body){
            String url = RestAssured.given().spec(request).body(body).log().uri().post("1/boards/").then().log().all().toString();
            return url;
    }
        public static Response sendRequest(RequestSpecification request, String method, Object... body){
            return switch (method.toUpperCase()) {
                case "GET" -> RestAssured.given()
                        .spec(request)
                        .get(dotenv.get("ENDPOINT"));
                case "POST" -> RestAssured.given()
                        .spec(request)
                        .body(body)
                        .when()
                        .post("1/boards/");

                case "PUT" -> RestAssured.given()
                        .spec(request)
                        .body(body)
                        .when()
                        .put(dotenv.get("ENDPOINT"));
                case "DELETE" -> RestAssured.given().spec(request).when().delete(dotenv.get("ENDPOINT"));
                default -> throw new NoHTTPMethodException("HTTP Method not recognize");
            };
        }

        public static boolean checkResponseIsValid(Response response){
            return response.statusCode()==200;
        }
        public static <T> T prepareActualResponse(Response response, Class<T> tClass){
            return response.getBody().as(tClass);
        }

        public static Object prepareExpectedResponse(Object object){
            return object;
        }
        public static void checkActualVsExpectedResponses(Object actual, Object expected){
            Assert.assertEquals(actual,expected);
        }
}

