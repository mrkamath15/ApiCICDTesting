package getUsers;

import baseTest.BaseTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import users.Data;
import users.Support;
import users.Users;
import utility.Constants;

import java.io.File;
import java.util.List;

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

    @Test
    public void getDataAsArray() {
        Response response = given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .extract().response();

        Data[] data  = response.jsonPath().getObject("data", Data[].class);

        for (Data eachData : data) {
            Assert.assertTrue(eachData.getId() >= 1);
            Assert.assertNotNull(eachData.getEmail());
            Assert.assertNotNull(eachData.getFirst_name());
            Assert.assertNotNull(eachData.getLast_name());
            Assert.assertNotNull(eachData.getAvatar());
        }
    }

    @Test
    public void getDataAsList() {
        Response response = given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .extract().response();

        List<Data> data = response.jsonPath().getList("data", Data.class);

        for (Data eachData : data) {
            Assert.assertTrue(eachData.getId() >= 1);
            Assert.assertNotNull(eachData.getEmail());
            Assert.assertNotNull(eachData.getFirst_name());
            Assert.assertNotNull(eachData.getLast_name());
            Assert.assertNotNull(eachData.getAvatar());
        }
    }

    @Test
    public void getUsersResponseTest() {
        Response response = given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .extract()
                .response();
        System.out.println("Total : " + response.path("total").toString());
        System.out.println("FirstName : " + response.path("data[0].first_name"));
        System.out.println("Response Time : " + response.getTime());
        System.out.println("Status Code : " + response.statusCode());
        System.out.println("Headers : " + response.getHeaders());
        System.out.println(response.jsonPath().getString("page"));
    }

    @Test
    public void getUsersResponseTimeTest() {
        given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .time(lessThan(700L));
    }

    @Test
    public void getAllUsersSchemaValidationTest() {
        given()
                .spec(spec)
                .when()
                .get("users")
                .then()
                .statusCode(200)
                .body(JsonSchemaValidator.matchesJsonSchema(new File(Constants.GET_ALL_USERS_SCHEMA)));
    }

}
