package com.TAF.Drivers;

import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;

public class EdgeFactory extends AbstractDriver {

    private EdgeOptions getOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-popups");
        // if i want to run local headless or remotly
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless") || PropertyReader.getProperty("executionType").equalsIgnoreCase("Remotly")) {
            options.addArguments("--headless");
        }
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver CreateDriver() {
        if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Local") || PropertyReader.getProperty("executionType").equalsIgnoreCase("LocalHeadless")) {
            return new EdgeDriver(getOptions());
        } else if (PropertyReader.getProperty("executionType").equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://" + PropertyReader.getProperty("remoteHost")
                                + ":" + PropertyReader.getProperty("remotePort")
                                + "/wd/hub").toURL(),
                        getOptions()
                );
            } catch (Exception e) {
                LogsManager.error("failed to create remote driver", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            LogsManager.error("invalid excution type" + PropertyReader.getProperty("executionType"));
            throw new RuntimeException("invalid excution type" + PropertyReader.getProperty("executionType"));

        }

    }
}