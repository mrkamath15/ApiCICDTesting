package getUsers;

import baseTest.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetAllUsersTest extends BaseTest {

    @Test
    public void getAllUsersTest() {
        given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .body("page", equalTo(1))
                .body("per_page", equalTo(6))
                .body("total", equalTo(12))
                .body("total_pages", equalTo(2))
                .body("data", notNullValue())
                .body("support", notNullValue());
    }
}
