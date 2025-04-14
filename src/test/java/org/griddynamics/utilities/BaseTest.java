package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.griddynamics.models.Board;
import org.griddynamics.retrofit.TrelloBoards;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import retrofit2.Retrofit;

import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {
        protected static RequestSpecification requestSpec;
        protected static ResponseSpecification responseSpec;
        protected static URI uri;
        protected static Retrofit retrofit;
        protected static TrelloBoards trelloBoard;

        @BeforeSuite
        public void setBaseURI() throws URISyntaxException {
            }

    /*****************************************************************************************************************/
//	@AfterSuite
    public void afterSuite() {

    }

    /****************************************************************************************************************/
//	@BeforeClass
    public void beforeClass() {
    }

    /****************************************************************************************************************/
//	@AfterClass
    public void afterClass(){

    }

    /************************************************************************************************************************/
    @BeforeMethod
    public void beforeMethod() throws URISyntaxException {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
        RestAssured.reset();
        Dotenv dotenv = Dotenv.load();
        //For RESTAssured
        requestSpec = new RequestSpecBuilder().
                setBaseUri(dotenv.get("TRELLO_URL"))
                .addQueryParam("key",dotenv.get("TRELLO_WORKPLACE_KEY"))
                .addQueryParam("token",dotenv.get("TRELLO_WORKPLACE_TOKEN"))
                .build();
        //For ApacheHTTPClient
        uri = new URIBuilder(dotenv.get("TRELLO_URL")+dotenv.get("ENDPOINT"))
                .addParameter("key",dotenv.get("TRELLO_WORKPLACE_KEY"))
                .addParameter("token",dotenv.get("TRELLO_WORKPLACE_TOKEN"))
                .build();

        retrofit = TestStepsRetrofit.buildRequest();
        trelloBoard = retrofit.create(TrelloBoards.class);
    }

    //@AfterMethod
    public void afterMethod() {
    }

}

