package com.TAF.Validations;

import com.TAF.FileUtils;
import com.TAF.Utils.Actions.ElementActions;
import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.WaitsAndTime.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class BaseAssertions {
    protected WebDriver driver;
    protected WaitManager waitManager;
    protected ElementActions elementActions;

    public BaseAssertions() {

    }

    public BaseAssertions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
        this.elementActions = new ElementActions(driver);
    }

    protected abstract void assertTrue(boolean condition, String message);

    protected abstract void assertFalse(boolean condition, String message);

    protected abstract void assertEquals(Object expected, Object actual, String message);

    // Implementations of the assertion methods
    public BaseAssertions Equals(Object expected, Object actual, String message) {
        assertEquals(expected, actual, message);
        return this;
    }

    public void True(boolean condition, String message) {
        assertTrue(condition, message);
    }

    public void False(boolean condition, String message) {
        assertFalse(condition, message);
    }

    // verify element is visible
    public void isElementVisible(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                driver.findElement(locator).isDisplayed();
                return true;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while checking visibility of element: " + e.getMessage());
                return false; // wait will retry
            }
        });
        True(flag, "Element is not visible: " + locator.toString());
    }

    //verify page url
    public void isPageUrl(String expectedUrl) {
        String actualUrl = driver.getCurrentUrl();
        Equals(expectedUrl, actualUrl, "Page URL does not match. Expected: " + expectedUrl + ", Actual: " + actualUrl);
    }

    //verify element title
    public void isPageTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        Equals(expectedTitle, actualTitle, "Page title does not match. Expected: " + expectedTitle + ", Actual: " + actualTitle);
    }

    // verify element text
    public void isElementText(By locator, String expectedText) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                String actualText = elementActions.getText(locator);
                return actualText.equals(expectedText);
            } catch (Exception e) {
                LogsManager.error("Exception occurred while getting text from element: " + e.getMessage());
                return false; // wait will retry
            }
        });
        True(flag, "Element text does not match. Expected: " + expectedText + ", Actual: " + elementActions.getText(locator));
    }

    // verify element is enabled
    public void isElementEnabled(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                driver.findElement(locator).isEnabled();
                return true;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while checking if element is enabled: " + e.getMessage());
                return false; // wait will retry
            }
        });
        True(flag, "Element is not enabled: " + locator.toString());
    }

    // verify element is disabled
    public void isElementDisabled(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                driver.findElement(locator).isEnabled();
                return false;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while checking if element is disabled: " + e.getMessage());
                return true; // wait will retry
            }
        });
        True(flag, "Element is not disabled: " + locator.toString());
    }

    // verify element is selected
    public void isElementSelected(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                driver.findElement(locator).isSelected();
                return true;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while checking if element is selected: " + e.getMessage());
                return false; // wait will retry
            }
        });
        True(flag, "Element is not selected: " + locator.toString());
    }

    // verify element is not selected
    public void isElementNotSelected(By locator) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                driver.findElement(locator).isSelected();
                return false;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while checking if element is not selected: " + e.getMessage());
                return true; // wait will retry
            }
        });
        True(flag, "Element is selected: " + locator.toString());
    }

    // verify element count
    public void isElementCount(By locator, int expectedCount) {
        boolean flag = waitManager.fluentWait().until(driver1 -> {
            try {
                int actualCount = driver.findElements(locator).size();
                return actualCount == expectedCount;
            } catch (Exception e) {
                LogsManager.error("Exception occurred while getting element count: " + e.getMessage());
                return false; // wait will retry
            }
        });
        True(flag, "Element count does not match. Expected: " + expectedCount + ", Actual: " + driver.findElements(locator).size());
    }

    // Verify that file exists
    public void assertFileExists(String fileName, String message) {
        boolean fileExists = FileUtils.isFileExist(fileName, 3);
        assertTrue(FileUtils.isFileExists(fileName), message);
    }
}
