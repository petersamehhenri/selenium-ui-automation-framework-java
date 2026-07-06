package com.TAF.Drivers;

import com.TAF.Utils.Actions.AlertActions;
import com.TAF.Utils.Actions.BrowserActions;
import com.TAF.Utils.Actions.ElementActions;
import com.TAF.Utils.Actions.FrameActions;
import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Validations.Validation;
import com.TAF.Validations.Verification;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;

public class GuiDriver {
    private final String browser = PropertyReader.getProperty("browserType");
    private ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public GuiDriver() {
        // Use your custom Browser enum, not Selenium's
        LogsManager.info("GuiDriver started" + browser);
        Browser browserType = Browser.valueOf(browser.toUpperCase());
        LogsManager.info("Browser: " + browserType.name());
        AbstractDriver abstractDriver = browserType.getDriverFactory();
        WebDriver driver = ThreadGuard.protect(abstractDriver.CreateDriver());
        driverThreadLocal.set(driver);
    }

    //return new obj for all classes I will use
    public Validation validation() {
        return new Validation(getDriver());
    }

    public Verification verification() {
        return new Verification (getDriver());
    }

    public AlertActions alert(){
        return new AlertActions(getDriver());
    }

    public FrameActions frame(){
        return new FrameActions(getDriver());
    }

    public BrowserActions browser(){
        return new BrowserActions(getDriver());
    }


    public ElementActions element() {
        return new ElementActions(getDriver());
    }



    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public void quitDriver() {
        driverThreadLocal.get().quit();
    }
}