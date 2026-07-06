package com.TAF.Utils.Actions;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.WaitsAndTime.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ElementActions {

    private final WebDriver driver;
    private WaitManager waitManager;


    public ElementActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    // Add common action methods here, e.g., click, type, wait, etc.
    //clicking (need to scroll to Element before clicking then check if it is displayed then click)
    public ElementActions click(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                LogsManager.info("Clicked " + locator);
                return element.isDisplayed();
            } catch (Exception e) {
                System.out.println("Exception occurred while clicking element: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
        driver.findElement(locator).click();
        return this;
    }

    //Hover (need to scroll to Element before clicking then check if it is displayed then click)
    public ElementActions hover(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                new Actions(d).moveToElement(element).perform();
                LogsManager.info("Clicked " + locator);
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while hovering element: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
        return this;
    }

    //typing
    //(need to scroll to Element before clicking then check if it is displayed then click)
    public ElementActions type(By locator, String text) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                LogsManager.info("Typed " + locator);
                return element.isDisplayed() && element.isEnabled();
            } catch (Exception e) {
                System.out.println("Exception occurred while typing in element: " + e.getMessage());
                return false; // wait will retry
            }
        });

        WebElement element = driver.findElement(locator);
        element.clear();
        element.sendKeys(text);
        return this;
    }

    //getting text
    //(need to scroll to Element before clicking then check if it is displayed then click)
    public String getText(By locator) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                LogsManager.info("Getted text " + locator);
                return element.isDisplayed();
            } catch (Exception e) {
                System.out.println("Exception occurred while getting text from element: " + e.getMessage());
                return false; // wait will retry
            }
        });
        return driver.findElement(locator).getText();
    }


    //scroll to element
    public void scrollToElement(By locator) {
        WebElement element = driver.findElement(locator);
        // scroll to the middle of the element by javascript
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'auto', block: 'center', inline: 'center'});",
                findElement(locator)
        );
    }

    // find Element
    public WebElement findElement(By locator) {
        return driver.findElement(locator);
    }

    //check if element is displayed
    public boolean isElementDisplayed(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            scrollToElement(locator);
            LogsManager.info("Element is displayed " + locator);
            return element.isDisplayed();
        } catch (Exception e) {
            System.out.println("Exception occurred while checking if element is displayed: " + e.getMessage());
            return false;
        }
    }

    //check if element is enabled
    public boolean isElementEnabled(By locator) {
        try {
            WebElement element = driver.findElement(locator);
            scrollToElement(locator);
            LogsManager.info("Element is disabled " + locator);
            return element.isEnabled();
        } catch (Exception e) {
            System.out.println("Exception occurred while checking if element is enabled: " + e.getMessage());
            return false;
        }
    }

    //upload file
    public ElementActions uploadFile(By locator, String filePath) {
        String FileAbsolutePath = System.getProperty("user.dir") + filePath;
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                LogsManager.info("Uploading file " + locator);
                return element.isDisplayed();
            } catch (Exception e) {
                System.out.println("Exception occurred while uploading file: " + e.getMessage());
                return false; // wait will retry
            }
        });
        driver.findElement(locator).sendKeys(FileAbsolutePath);
        return this;
    }

    //Select From Dropdown
    public ElementActions selectFromDropDown(By locator, String Value) {
        waitManager.fluentWait().until(d -> {
            try {
                WebElement element = driver.findElement(locator);
                scrollToElement(locator);
                Select select = new Select(element);
                select.selectByValue(Value);
                LogsManager.info("selected " + locator + Value);
                return element.isDisplayed();
            } catch (Exception e) {
                System.out.println("Exception occurred while choosing from dropdown in element: " + e.getMessage());
                return false; // wait will retry
            }
        });
        return this;
    }
}