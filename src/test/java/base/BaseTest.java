package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    public static String baseURL = "http://localhost:3000/api/users";

    public static String userId;
    public static String token;
    public static String email;

    @BeforeClass
    public void setup(){

        RestAssured.baseURI = baseURL;

    }
}