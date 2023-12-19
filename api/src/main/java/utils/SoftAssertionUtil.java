package utils;

import org.testng.asserts.SoftAssert;

public class SoftAssertionUtil {
    private static SoftAssert softAssert;

    private static void SoftAssertionUtil() {
        // softAssert = new SoftAssert();
    }

    public static SoftAssert getInstance() {
        if (softAssert == null) {
            softAssert = new SoftAssert();
        }
        return softAssert;
    }

    public void assertTrue(boolean condition, String message) {
        try {
            softAssert.assertTrue(condition, message);
        } catch (AssertionError e) {
            softAssert.fail(message);
        }
    }

    public void assertEquals(Object actual, Object expected, String message) {
        try {
            softAssert.assertEquals(actual, expected, message);
        } catch (AssertionError e) {
            softAssert.fail(message);
        }
    }

    public void assertNotEquals(Object actual, Object expected, String message) {
        try {
            softAssert.assertNotEquals(actual, expected, message);
        } catch (AssertionError e) {
            softAssert.fail(message);
        }
    }

    public void assertAll() {
        softAssert.assertAll();

    }
}
