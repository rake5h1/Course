
package userManagement;

import org.json.simple.JSONArray;
import org.testng.annotations.Test;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import utils.ExtentReport;
import utils.JsonReader;
import utils.PropertyReader;
import utils.SoftAssertionUtil;
import io.restassured.http.Header;
import io.restassured.http.Headers;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import java.util.HashMap;
import java.util.Iterator;

public class Getuser extends BaseTest {
    PropertyReader configfile;
    SoftAssertionUtil softassert;

    public Getuser() {
        this.configfile = new PropertyReader();
        this.softassert = new SoftAssertionUtil();
    }

    @Test
    public void validateTestBody() {
        baseURI = "https://jsonplaceholder.typicode.com/";
        given().when().get("/todos/1")
                .then().assertThat().statusCode(200)
                .body(not(emptyString()))
                .body("title", equalTo("delectus aut autem"));
    }

    @Test
    public void hasItem() {
        baseURI = "https://jsonplaceholder.typicode.com/";
        Response response = given().when().get("/posts").then().assertThat().statusCode(200).extract().response();

        assertThat(response.jsonPath().getList("title"), hasItems("qui est esse", "nesciunt quas odio"));

    }

    @Test(groups = { "Regression" })
    public void hassize() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("hassize", "hassize test case");
        baseURI = "https://reqres.in/api/users";
        Response response = given().queryParam("page", 2).when().get();
        assertThat(response.statusCode(), equalTo(StatusCode.SUCCESS.code));
        // System.out.println(response.jsonPath().getList("data"));
        assertThat(response.jsonPath().getList("data"), hasSize(6));
    }

    @Test(groups = { "Regression" })
    public void validatecontainsite() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("validatecontainsite",
                "validatecontainsite test case");

        baseURI = "https://jsonplaceholder.typicode.com/";

        Response response = given().when().get("/comments?postId=1").then().extract().response();

        List<String> expectedEmails = Arrays.asList("Eliseo@gardner.biz", "Jayne_Kuhic@sydney.com",
                "Nikita@garfield.biz", "Lew@alysha.tv", "Hayden@althea.biz");

        assertThat(response.jsonPath().getList("email"), contains(expectedEmails.toArray(new String[0])));

    }

    @Test
    public void queryparam() {

        baseURI = "https://reqres.in/api";

        Response res = given().queryParam("page", 2).when().get("/users").then().assertThat().statusCode(200).extract()
                .response();

        res.then().body("data[0].id", equalTo(7));
        res.then().body("data[0].email", equalTo("michael.lawson@reqres.in"));
        res.then().body("data[0].first_name", equalTo("Michael"));

    }

    @Test(groups = { "Regression" })
    public void multiplequeryparam() {
        ExtentReport.extentlog = ExtentReport.extentreport.startTest("multiplequeryparam",
                "multiplequeryparam test case");

        baseURI = "https://reqres.in/api";

        Response res = given().queryParam("page", 2).queryParam("per_page", 1).when().get("/users").then().assertThat()
                .statusCode(200).extract().response();
        res.then().body("data[0].id", equalTo(2));
    }

    @Test
    public void pathparam() {

        baseURI = "https://ergast.com/api/f1";

        Response res = given().pathParam("season", 2017).when().get("/{season}/circuits.json");
        int code = res.statusCode();
        assertThat(code, equalTo(200));

    }

    @Test
    public void formparam() {
        baseURI = "https://reqres.in/api";

        Response res = given().header("Content-Type", "application/x-www-form-urlencoded")
                .formParam("name", "morpheus")
                .formParam("job", "leader").when().post("/users");

        // System.out.println(res.statusCode());
    }

    @Test
    public void multipleheaders() {
        baseURI = "https://reqres.in/api";

        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "bearer asdndshsdfhdsuhdsuhuh4545");
        Response res = given().headers(headers).when().get("/users?page=2");
        assertThat(res.statusCode(), equalTo(200));
        // System.out.println("multipleheaders passed");
    }

    @Test
    public void testfetchheaders() {
        baseURI = "https://reqres.in/api";

        Response res = given().header("Content-Type", "application/json").when().get("/users?page=2").then()
                .statusCode(200).extract().response();
        Headers headers = res.getHeaders();
        for (Header header : headers) {
            if (header.getName().contains("Server")) {
                // System.out.println(header.getName() + " : " + header.getValue());
                assertThat(header.getValue(), equalTo("cloudflare"));
                // System.out.println("testfetchheaders passed");
            }
        }
    }

    @Test
    public void getcookies() {
        baseURI = "https://reqres.in/api";

        Response res = given().cookie("test1", "cookie1")
                .when().get("/users?page=2").then().assertThat().statusCode(StatusCode.SUCCESS.code).extract()
                .response();
        Map<String, String> cookies = res.getCookies();

        // System.out.println(cookies);

    }

    @Test
    public void deletemethod() {
        baseURI = "https://reqres.in/api/users?=2";

        Response res = given().delete();
        assertThat(res.getStatusCode(), equalTo(StatusCode.NO_CONTENT.code));
        // System.out.println(StatusCode.NO_CONTENT.msg);
    }

    @Test
    public void getdatafrompropertyfile() {

        String url = configfile.propertydata("config.properties", "url");
        String server = configfile.propertydata("config.properties", "server");
        // System.out.println(server);
        Response res = given().queryParam("page", 2).when().get(url + "users");
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        // assertThat(res.jsonPath().getList("total"), equalTo(12));
        Headers header = res.getHeaders();
        for (Header h : header) {
            if (h.getName().contains("Server")) {
                assertThat(h.getValue(), equalTo(server));

            }
        }

        // System.out.println("getdatafrompropertyfile passed");
    }

    @Test
    public void getdatafromjsonandpropertiesboth() throws IOException {
        String server = configfile.propertydata("config.properties", "url");
        String endpoint = new JsonReader().gettestdata("endpoint");
        String url = server + endpoint;
        Response res = given().when().get(url);
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        // System.out.println("getdatafromjsonandpropertiesboth passed");

    }

    @Test
    public void validatesoftassert() {

        baseURI = "https://reqres.in/api";

        Response res = given().queryParam("page", 2).when().get("/users");

        softassert.assertEquals(res.statusCode(), StatusCode.SUCCESS.code, "Status code is not 200");
        softassert.assertAll();
        List<String> emails = res.jsonPath().getList("data.email");
        softassert.assertEquals(emails.get(0), "michael.lawson@reqres.in", "Email is not correct");
        softassert.assertAll();
        // System.out.println("validatesoftassert passed");
    }

    @Test
    public void getdatafromjsonarray() throws IOException {
        String data = new JsonReader().getjsonarraydata("languages", 2).toString();
        // System.out.println(data);
        System.out.println("getdatafromjsonarray passed");
    }

    @Test
    public void getdatafromjsonarrayiteration() throws IOException {
        JSONArray array = new JsonReader().getjsonarray("contact");
        Iterator<String> itr = array.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
        System.out.println("getdatafromjsonarrayiteration passed");

    }

}
