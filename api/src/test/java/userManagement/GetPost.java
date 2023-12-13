package userManagement;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;
import utils.JsonReader;

import java.io.File;
import java.io.IOException;
import utils.SoftAssertionUtil;

public class GetPost {
    SoftAssertionUtil softassert;
    public GetPost() {
        this.softassert = new SoftAssertionUtil();
    }
    @Test
    public void basicauth() {
        baseURI = "https://postman-echo.com/basic-auth";

        Response res = given().auth().basic("postman", "password").when().get();
        // assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        // res.then().body("authenticated", equalTo(true));
        SoftAssertionUtil softassert = new SoftAssertionUtil();
        softassert.assertEquals(res.statusCode(), StatusCode.SUCCESS.code, "status code is not 200");
        softassert.assertAll();
    }

    @Test(groups = {"size"})
    public void getdatafromjson() throws IOException {
        String username = new JsonReader().gettestdata("username");
        String password = new JsonReader().gettestdata("password");
        baseURI = "https://postman-echo.com/basic-auth";

        Response res = given().auth().basic(username, password).when().get();
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        res.then().body("authenticated", equalTo(true));
        //System.out.println(StatusCode.SUCCESS.msg);
    }
    @Test
    public void postdatarestassured() throws IOException{
        String json  = FileUtils.readFileToString(new File("resources/TestData/postdata.json"),"UTF-8");
        Response res = given().header("Content-Type","application/json").body(json).when().post("https://reqres.in/api/users");
        softassert.assertEquals(res.statusCode(), StatusCode.CREATED.code,"Object not created");
        softassert.assertAll();
        System.out.println("postdatarestassured is working fine ");
    }
}
