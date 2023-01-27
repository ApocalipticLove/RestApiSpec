import lombok.LombokUserData;
import models.UserData;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReqresWithSpec {

    @Test
    void singleUserTestWithLombok() {
        LombokUserData data = given()
                .spec(Specs.request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.response)
                .log().status()
                .log().body()
                .extract().as(LombokUserData.class);
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
    }

    @Test
    void unsuccessfulRegistrationTest() {

        given()
                .spec(Specs.request)
                .when()
                .post("/register")
                .then()
                .log().status()
                .spec(Specs.response400);
    }

    @Test
    void listWithGroovyTest() {
        given()
                .spec(Specs.request)
                .when()
                .get("/unknown")
                .then()
                .spec(Specs.response)
                .log().body()
                .body("data.findAll{it.id == 3}.name", hasItem("true red"))
                .body("data.findAll{it.id == 3}.color", hasItem("#BF1932"));
    }

    @Test
    void singleUserTestWithModels() {
        UserData userData = given()
                .spec(Specs.request)
                .when()
                .get("/users/2")
                .then()
                .spec(Specs.response)
                .extract().as(UserData.class);

        assertEquals("Weaver", userData.getData().getLastName());
    }
}
