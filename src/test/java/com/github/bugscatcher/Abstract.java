package com.github.bugscatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static io.restassured.RestAssured.given;

public abstract class Abstract {
    private static RequestSpecification spec;
    private Path tempRequestFile;
    private Path tempResponseFile;

    @BeforeMethod(description = "Configure temp files, base uri, base path and etc.")
    public void setUp() throws IOException {
        tempRequestFile = Files.createTempFile("request-", ".xml");
        tempResponseFile = Files.createTempFile("response-", ".xml");
        PrintStream printStreamRequest = new PrintStream(new File(tempRequestFile.toUri()));
        PrintStream printStreamResponse = new PrintStream(new File(tempResponseFile.toUri()));
        spec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://reqres.in")
                .setBasePath("/api")
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL, true, printStreamResponse))
                .addFilter(new RequestLoggingFilter(LogDetail.ALL, true, printStreamRequest))
                .build();
    }

    @Step(value = "Create resource")
    Response createResource(Object body, String path) throws IOException {
        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .response();
        attachFile(tempRequestFile);
        attachFile(tempResponseFile);
        return response;
    }

    @Step(value = "Update resource")
    Response updateResource(Object body, Method method, String path) throws IOException {
        Response response = given()
                .spec(spec)
                .body(body)
                .when()
                .request(method, path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .response();
        attachFile(tempRequestFile);
        attachFile(tempResponseFile);
        return response;
    }

    @Step(value = "Get resource")
    <T> T getResource(String path, Class<T> responseClass) throws IOException {
        Response response = given()
                .spec(spec)
                .when()
                .get(path)
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract()
                .response();
        attachFile(tempRequestFile);
        attachFile(tempResponseFile);
        return response.as(responseClass);
    }

    @Step(value = "Get resource")
    Response getResource(String path) throws IOException {
        Response response = given()
                .spec(spec)
                .when()
                .get(path)
                .then()
                .extract()
                .response();
        attachFile(tempRequestFile);
        attachFile(tempResponseFile);
        return response;
    }


    @Step(value = "Delete resource")
    Response deleteResource(String path) throws IOException {
        Response response = given()
                .spec(spec)
                .when()
                .delete(path)
                .then()
                .extract()
                .response();
        attachFile(tempRequestFile);
        attachFile(tempResponseFile);
        return response;
    }

    @Step("Get expected result from resources")
    <T> T getExpectedResult(String path, Class<T> valueClass) throws IOException {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream is = classloader.getResourceAsStream(path);
        return new ObjectMapper().readValue(is, valueClass);
    }

    @AfterMethod(description = "Delete temp files")
    public void tearDown() throws IOException {
//        Files.deleteIfExists(tempRequestFile);
//        Files.deleteIfExists(tempResponseFile);
    }

    @Attachment(value = "{path}")
    private byte[] attachFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }
}
