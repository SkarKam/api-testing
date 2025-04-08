package org.griddynamics.utilities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.griddynamics.exceptions.NoHTTPMethodException;
import org.testng.Assert;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.griddynamics.utilities.BaseTest.uri;

public class TestStepsRetrofit {

    public static Retrofit buildRequest(String... boardIDorName) {
        return new Retrofit.Builder()
                .baseUrl(Dotenv.load().get("TRELLO_URL"))
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }
}
