package com.TAF.Validations;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

// validation class will contain Hard Assertions
public class Verification extends BaseAssertions {
    public Verification (){
        super();
    }
    public Verification(WebDriver driver) {
        super(driver);
    }

    @Override
    protected void assertTrue(boolean condition, String message) {
    Assert.assertTrue(condition, message);
    }

    @Override
    protected void assertFalse(boolean condition, String message) {
    Assert.assertFalse(condition, message);
    }

    @Override
    protected void assertEquals(Object expected, Object actual, String message) {
    Assert.assertEquals(actual, expected, message);
    }


}