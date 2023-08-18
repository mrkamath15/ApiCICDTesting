package getUsers;

import baseTest.BaseTest;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetSingleUser extends BaseTest {

    @Test
    public void getSingleUserTest() {
        given()
                .spec(spec)
                .pathParam("id", 2)
                .when()
                .get("users/{id}")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSingleNonExistingUserTest() {
        given()
                .spec(spec)
                .pathParam("id", 200)
                .when()
                .get("users/{id}")
                .then()
                .statusCode(404)
                .body("isEmpty()", equalTo(true));
    }
}
