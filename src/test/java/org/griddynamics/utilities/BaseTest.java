package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import org.apache.http.client.utils.URIBuilder;
import org.griddynamics.models.dtos.BoardPUT;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.net.URI;
import java.net.URISyntaxException;

public class BaseTest {
        protected static RequestSpecification requestSpec;
        protected static ResponseSpecification responseSpec;
        protected static URI uri;

            @BeforeSuite
            public void setBaseURI() throws URISyntaxException {
                Dotenv dotenv = Dotenv.load();
                requestSpec = new RequestSpecBuilder().
                        setBaseUri(dotenv.get("TRELLO_URL"))
                        .addQueryParam("key",dotenv.get("TRELLO_WORKPLACE_KEY"))
                        .addQueryParam("token",dotenv.get("TRELLO_WORKPLACE_TOKEN"))
                        .build();

                uri = new URIBuilder(dotenv.get("TRELLO_URL")+dotenv.get("ENDPOINT"))
                        .addParameter("key",dotenv.get("TRELLO_WORKPLACE_KEY"))
                        .addParameter("token",dotenv.get("TRELLO_WORKPLACE_TOKEN"))

                        .build();


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
    public void beforeMethod() {
        responseSpec = new ResponseSpecBuilder().expectStatusCode(200).build();
    }

    //	@AfterMethod
    public void afterMethod() {

    }

}

