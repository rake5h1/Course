package userManagement;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import pojo.DataPojo;
import pojo.PostrequestBody;
import utils.ExtentReport;
import utils.JsonReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.SoftAssertionUtil;

public class GetPost extends BaseTest {
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

    @Test(groups = { "size" })
    public void getdatafromjson() throws IOException {
        String username = new JsonReader().gettestdata("username");
        String password = new JsonReader().gettestdata("password");
        baseURI = "https://postman-echo.com/basic-auth";

        Response res = given().auth().basic(username, password).when().get();
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        res.then().body("authenticated", equalTo(true));
        // System.out.println(StatusCode.SUCCESS.msg);
    }

    @Test
    public void postdatarestassured() throws IOException {
        String json = FileUtils.readFileToString(new File("resources/TestData/postdata.json"), "UTF-8");
        Response res = given().header("Content-Type", "application/json").body(json).when()
                .post("https://reqres.in/api/users");
        softassert.assertEquals(res.statusCode(), StatusCode.CREATED.code, "Object not created");
        softassert.assertAll();
        // System.out.println("postdatarestassured is working fine ");
    }

    @Test()
    public void postwithpojo() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("postwithpojo", "postwithpojo test case");
        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("Python");
        PostrequestBody postbody = new PostrequestBody();
        postbody.setName("morpheus");
        postbody.setJob("leader");
        postbody.setLanguages(languages);
        Response res = given().header("Content-Type", "application/json").body(postbody).when()
                .post("https://reqres.in/api/users/2");
        softassert.assertEquals(res.statusCode(), StatusCode.CREATED.code, "Object not created");
        softassert.assertAll();
        // System.out.println(res.getBody().asString());
        // System.out.println("postwithpojo is working fine ");
    }

    @Test(groups = { "Regression" })
    public void putwithpojo() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("putwithpojo", "putwithpojo test case");
        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("Python");
        PostrequestBody postbody = new PostrequestBody();
        postbody.setName("Rahul");
        postbody.setJob("Engineer");
        postbody.setLanguages(languages);
        Response res = given().header("Content-Type", "application/json").body(postbody).when()
                .put("https://reqres.in/api/users/2");
        softassert.assertEquals(res.statusCode(), StatusCode.SUCCESS.code, "Object not created");
        softassert.assertAll();
        // System.out.println(res.getBody().asString());
        // System.out.println("putwithpojo is working fine ");
    }

    @Test(groups = { "Regression" })
    public void putwithpojoList() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("putwithpojoList", "putwithpojoList test case");
        List<String> languages = new ArrayList<>();
        languages.add("Java");
        languages.add("Python");
        PostrequestBody postbody = new PostrequestBody();
        postbody.setName("Rahul");
        postbody.setJob("Engineer");
        postbody.setLanguages(languages);
        DataPojo data1 = new DataPojo();
        data1.setCity("Mumbai");
        data1.setTemp(40);
        DataPojo data2 = new DataPojo();
        data2.setCity("delhi");
        data2.setTemp(45);
        List<DataPojo> data = new ArrayList<>();
        data.add(data1);
        data.add(data2);
        postbody.setData(data);
        Response res = given().header("Content-Type", "application/json").body(postbody).when()
                .post("https://reqres.in/api/users/2");
        softassert.assertEquals(res.statusCode(), StatusCode.CREATED.code, "Object not created");
        softassert.assertAll();
        //System.out.println(res.getBody().asString());
        //System.out.println("putwithpojoList is working fine ");
    }

}
