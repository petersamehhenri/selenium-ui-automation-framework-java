package com.TAF.Validations;

import com.TAF.Utils.Logs.LogsManager;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

// validation class will contain soft Assertions
public class Validation extends BaseAssertions {
    private static SoftAssert softAssert = new SoftAssert();
    private static boolean Used = false;
    public Validation (){
        super();
    }
    public Validation(WebDriver driver) {
        super(driver);
    }
@Override
    protected void assertTrue(boolean condition, String message) {
    Used = true;
    softAssert.assertTrue(condition, message);
    }
@Override
    protected void assertFalse(boolean condition, String message) {
    Used = true;
    softAssert.assertFalse(condition, message);
    }
@Override
    protected void assertEquals(Object actual, Object expected, String message) {
    Used = true;
    softAssert.assertEquals(actual, expected, message);
    }
    //assert all method to be called at the end of the test
    public static void assertAll() {
        if (!Used) {
            LogsManager.warn("No assertions were made during the test.");
            return;
        }
        try {
            softAssert.assertAll();
        } catch (AssertionError e) {
            LogsManager.error("Assertion failed" ,e.getMessage());
            throw e;
        }
        finally {
            softAssert = new SoftAssert(); // Reset for next use
        }
    }
}