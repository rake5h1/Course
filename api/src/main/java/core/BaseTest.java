package core;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.LogStatus;

import static helper.Helper.*;
import utils.ExtentReport;

public class BaseTest {

    @BeforeSuite(alwaysRun = true)
    public void config() {
        String folderpath = System.getProperty("user.dir") + "/reports/" + timestamp();
        createFolder(folderpath);
        ExtentReport.initialize(folderpath + "/" + "API_Automation.html");
    }

    @AfterMethod(alwaysRun = true)
    public void getResult(ITestResult result) {

        if (result.getStatus() == ITestResult.SUCCESS) {

            ExtentReport.extentlog.log(LogStatus.PASS, "Test Case : " + result.getName() + " is passed ");

        } else if (result.getStatus() == ITestResult.FAILURE) {

            ExtentReport.extentlog.log(LogStatus.FAIL, "Test case : " + result.getName() + " is failed ");

            ExtentReport.extentlog.log(LogStatus.FAIL, "Test case is failed due to:  " + result.getThrowable());

        } else if (result.getStatus() == ITestResult.SKIP) {

            ExtentReport.extentlog.log(LogStatus.SKIP, "Test case is Skiped " + result.getName());

        }

        ExtentReport.extentreport.endTest(ExtentReport.extentlog);

    }

    @AfterSuite(alwaysRun = true)

    public void endReport() {

        // ExtentReport.extentreport.flush();

        ExtentReport.extentreport.close();

        // Logging.setinstanceNull();

    }
}
