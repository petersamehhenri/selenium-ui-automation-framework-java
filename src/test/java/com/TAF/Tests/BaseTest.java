package com.TAF.Tests;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Drivers.WebDriverProvider;
import com.TAF.Utils.DataReader.JsonReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Listeners;

@Listeners({com.TAF.listeners.TestNGListeners.class})
public class BaseTest implements WebDriverProvider {
    protected GuiDriver driver;
    protected JsonReader testData;

    @Override
    public WebDriver getWebDriver() {
        return driver.getDriver();
    }
}
