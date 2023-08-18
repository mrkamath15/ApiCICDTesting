package getUsers;

import baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import users.Data;
import users.Support;
import users.Users;

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
                .body("data.size()", equalTo(6))
                .body("support", notNullValue());
    }

    @Test
    public void verifyUserData() {
        given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .body("data.id", everyItem(greaterThanOrEqualTo(1)))
                .body("data.email", everyItem(notNullValue()))
                .body("data.first_name", everyItem(notNullValue()))
                .body("data.last_name", everyItem(notNullValue()))
                .body("data.avatar", everyItem(notNullValue()));
    }

    @Test
    public void verifyAllUsersWithPojo() {
        Users users = given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .extract()
                .response()
                .as(Users.class);
        Assert.assertEquals(users.getPage(), 1);
        Assert.assertEquals(users.getPer_page(), 6);
        Assert.assertEquals(users.getTotal(), 12);
        Assert.assertEquals(users.getTotal_pages(), 2);

        Data[] data = users.getData();
        for (Data eachData : data) {
            Assert.assertTrue(eachData.getId() >= 1);
            Assert.assertNotNull(eachData.getEmail());
            Assert.assertNotNull(eachData.getFirst_name());
            Assert.assertNotNull(eachData.getLast_name());
            Assert.assertNotNull(eachData.getAvatar());
        }

        Support support = users.getSupport();
        Assert.assertNotNull(support.getUrl());
        Assert.assertNotNull(support.getText());

    }
}
