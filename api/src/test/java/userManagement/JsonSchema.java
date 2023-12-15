package userManagement;

import org.testng.annotations.Test;

import core.BaseTest;
import core.StatusCode;
import io.restassured.response.Response;
import utils.ExtentReport;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class JsonSchema extends BaseTest {

    @Test(groups = { "Regression" })
    public void jsonschema() {

        if (ExtentReport.extentreport != null) {
            ExtentReport.extentlog = ExtentReport.extentreport.startTest("jsonschema", "jsonschema test case");
        } else {
            System.out.println("ExtentReport.extentreport is null");
            return;
        }
        Response res = given().when().get("https://reqres.in/api/user/2");
        assertThat(res.statusCode(), equalTo(StatusCode.SUCCESS.code));
        System.out.println(res.getBody().asString());
    }
}
