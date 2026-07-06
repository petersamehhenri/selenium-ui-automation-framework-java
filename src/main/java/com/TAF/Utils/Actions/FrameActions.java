package com.TAF.Utils.Actions;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.WaitsAndTime.WaitManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FrameActions {
    WebDriver driver;
    WaitManager waitManager;
    public FrameActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

    //Switch to frame by index
    public void switchToFrameByIndex(int index) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(index);
                LogsManager.info("Switched to frame: " + index);
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while switching to frame by index: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
    }
    //Switch to frame by name or ID
    public void switchToFrameByNameOrId(String nameOrId) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(nameOrId);
                LogsManager.info("Switched to frame: " + nameOrId);
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while switching to frame by name or ID: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
    }
    //Switch to frame by WebElement
    public void switchToFrameByWebElement( By frameLocator) {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().frame(d.findElement(frameLocator));
                LogsManager.info(driver.getPageSource());
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while switching to frame by WebElement: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
    }
    //Switch back to the main document
    public void switchToDefaultContent() {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().defaultContent();
                LogsManager.info(driver.getTitle());
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while switching to default content: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
    }
    //Switch to parent frame
    public void switchToParentFrame() {
        waitManager.fluentWait().until(d -> {
            try {
                driver.switchTo().parentFrame();
                LogsManager.info("Switched to parent frame");
                return true;
            } catch (Exception e) {
                System.out.println("Exception occurred while switching to parent frame: " + e.getMessage());
                return false; // must return boolean because until() expects it
            }
        });
    }
}