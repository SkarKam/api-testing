package org.griddynamics.utilities;

import io.github.cdimascio.dotenv.Dotenv;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class BaseTest {
    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeSuite
    public void setBaseURI() {

        Dotenv dotenv = Dotenv.load();
        requestSpec = new RequestSpecBuilder().
                setBaseUri(dotenv.get("TRELLO_URL")).
                build();
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

