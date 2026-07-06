package com.TAF.Drivers;

import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class FireFoxFactory extends AbstractDriver {

    private FirefoxOptions getOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--start-maximized");
        // if i want to run local headless or remotly
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") || PropertyReader.getProperty("executionType").equalsIgnoreCase("Remotly")) {
            options.addArguments("--headless");
        }
        return options;
    }

    @Override
    public WebDriver CreateDriver() {
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") || PropertyReader.getProperty("excutionType").equalsIgnoreCase("LocalHeadless")) {
            return new FirefoxDriver(getOptions());
        } else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + PropertyReader.getProperty("remoteHost")
                                + ":" + PropertyReader.getProperty("remotePort")
                                + "/wd/hub").toURL(),
                        getOptions()
                );            }
            catch (Exception e) {
                LogsManager.error("failed to create remote driver", e.getMessage());
                throw new RuntimeException(e);
            }
        }
        else {
            LogsManager.error("invalid executionType" + PropertyReader.getProperty("excutionType"));
            throw new RuntimeException("invalid executionType" + PropertyReader.getProperty("excutionType"));

        }

    }
}