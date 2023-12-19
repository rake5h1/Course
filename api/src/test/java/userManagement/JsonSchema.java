package userManagement;

import org.testng.annotations.Test;

import core.BaseTest;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utils.ExtentReport;

import static io.restassured.RestAssured.*;
import java.io.File;

public class JsonSchema extends BaseTest {

    @Test(groups = { "Regression" })
    public void jsonschema() {
        File schema = new File("resources//ExpectedSchema.json");

        if (ExtentReport.extentreport != null) {
            ExtentReport.extentlog = ExtentReport.extentreport.startTest("jsonschema", "jsonschema test case");
        } else {
            System.out.println("ExtentReport.extentreport is null");
            return;
        }
        Response res = given().when().get("https://reqres.in/api/user?page=2");
        res.then().body(JsonSchemaValidator.matchesJsonSchema(schema));

    }
}
