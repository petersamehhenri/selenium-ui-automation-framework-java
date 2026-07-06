package com.TAF.Utils.Actions;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.WaitsAndTime.WaitManager;
import org.openqa.selenium.WebDriver;

public class AlertActions {
    private WebDriver driver;
    private WaitManager waitManager;

    public AlertActions(WebDriver driver) {
        this.driver = driver;
        this.waitManager = new WaitManager(driver);
    }

   //Accept alerts
    public void acceptAlert() {
        waitManager.fluentWait() .until(d -> {
            try {
                d.switchTo().alert().accept();
                return true;
            } catch (Exception e) {
                LogsManager.error("No alert present to accept:" , e.getMessage());
                return false;
            }
        });
    }

    //Dismiss alerts
    public void dismissAlert() {
        waitManager.fluentWait() .until(d -> {
            try {
                d.switchTo().alert().dismiss();
                return true;
            } catch (Exception e) {
                LogsManager.error("No alert present to dismiss:" , e.getMessage());
                return false;
            }
        });
    }
    //Get alert text
    public String getAlertText() {
        return waitManager.fluentWait() .until(d -> {
            try {
                String alertText = driver.switchTo().alert().getText();
                return !alertText.isEmpty() ? alertText : null;
            } catch (Exception e) {
                LogsManager.error("No alert present to get text from:" , e.getMessage());
                return null;
            }
        });
    }

    //Send text to alert
    public void sendTextToAlert(String text) {
        waitManager.fluentWait().until(d -> {
            try {
                d.switchTo().alert().sendKeys(text);
                return true;
            } catch (Exception e) {
                LogsManager.error("No alert present to send text to:" , e.getMessage());
                return false;
            }
        });
    }
}