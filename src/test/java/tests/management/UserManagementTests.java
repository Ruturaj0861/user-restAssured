package tests.management;

import base.BaseTest;
import utils.RandomData;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;

import io.qameta.allure.testng.AllureTestNg;

import static io.restassured.RestAssured.*;

@Listeners({AllureTestNg.class})
public class UserManagementTests extends BaseTest {

    @BeforeClass
    public void setupUser() {

        email = RandomData.randomEmail();

        String body = "{"
                + "\"name\":\"Ruturaj\","
                + "\"email\":\"" + email + "\","
                + "\"password\":\"123456\""
                + "}";

        Response registerRes =
                given()
                        .header("Content-Type","application/json")
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .statusCode(201)
                        .extract().response();

        userId = registerRes.jsonPath().getString("id");

        String loginBody = "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"123456\""
                + "}";

        Response loginRes =
                given()
                        .header("Content-Type","application/json")
                        .body(loginBody)
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(200)
                        .extract().response();

        token = loginRes.jsonPath().getString("token");
    }

    @Test
    public void countUsers(){

        given()
                .when()
                .get("/count")
                .then()
                .statusCode(200);
    }

    @Test
    public void blockUser(){

        given()
                .pathParam("id", userId)
                .when()
                .post("/block/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void activateUser(){

        given()
                .pathParam("id", userId)
                .when()
                .post("/activate/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void deleteUser(){

        given()
                .pathParam("id", userId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void logout(){

        given()
                .header("Authorization", token)
                .when()
                .post("/logout")
                .then()
                .statusCode(200);
    }

}