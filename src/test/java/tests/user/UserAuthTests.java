package tests.user;

import base.BaseTest;
import utils.RandomData;

import io.restassured.response.Response;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import io.qameta.allure.testng.AllureTestNg;

import static io.restassured.RestAssured.*;

@Listeners({AllureTestNg.class})
public class UserAuthTests extends BaseTest {

    @Test(priority = 1)
    public void registerUser() {

        email = RandomData.randomEmail();

        String body = "{"
                + "\"name\":\"Ruturaj\","
                + "\"email\":\"" + email + "\","
                + "\"password\":\"123456\""
                + "}";

        Response res =
                given()
                        .header("Content-Type","application/json")
                        .body(body)
                        .when()
                        .post("/register")
                        .then()
                        .statusCode(201)
                        .extract().response();

        userId = res.jsonPath().getString("id");
    }

    @Test(priority = 2)
    public void loginUser() {

        String body = "{"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"123456\""
                + "}";

        Response res =
                given()
                        .header("Content-Type","application/json")
                        .body(body)
                        .when()
                        .post("/login")
                        .then()
                        .statusCode(200)
                        .extract().response();

        token = res.jsonPath().getString("token");
    }

    @Test(priority = 3)
    public void getUser() {

        given()
                .pathParam("id", userId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200);
    }

    @Test(priority = 4)
    public void updateUser() {

        String body = "{ \"name\":\"Updated Ruturaj\" }";

        given()
                .pathParam("id", userId)
                .header("Content-Type","application/json")
                .body(body)
                .when()
                .put("/{id}")
                .then()
                .statusCode(200);
    }

    @Test(priority = 5)
    public void searchUser() {

        given()
                .queryParam("name","Updated")
                .when()
                .get("/search")
                .then()
                .statusCode(200);
    }
}
