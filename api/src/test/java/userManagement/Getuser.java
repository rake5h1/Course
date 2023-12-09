
package userManagement;

import org.testng.annotations.Test;

import core.StatusCode;
import io.restassured.response.Response;
import utils.JsonReader;
import utils.PropertyReader;
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

public class Getuser {
    PropertyReader configfile;

    public Getuser() {
        this.configfile = new PropertyReader();
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

    @Test
    public void hassize() {
        baseURI = "https://jsonplaceholder.typicode.com/";
        Response response = given().when().get("/posts").then().extract().response();

        assertThat(response.jsonPath().getList("title"), hasSize(100));
    }

    @Test
    public void validatecontainsite() {

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

    @Test
    public void multiplequeryparam() {
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
    public void basicauth() {
        baseURI = "https://postman-echo.com/basic-auth";

        Response res = given().auth().basic("postman", "password").when().get();
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        res.then().body("authenticated", equalTo(true));
    }

    @Test
    public void deletemethod() {
        baseURI = "https://reqres.in/api/users?=2";

        Response res = given().delete();
        assertThat(res.getStatusCode(), equalTo(StatusCode.NO_CONTENT.code));
        // System.out.println(StatusCode.NO_CONTENT.msg);
    }

    @Test
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
    public void getdatafrompropertyfile() {

        String server = configfile.propertydata("config.properties", "server");
        // System.out.println(server);
        Response res = given().queryParam("page", 2).when().get(server);
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        System.out.println("getdatafrompropertyfile passed");
    }
}
