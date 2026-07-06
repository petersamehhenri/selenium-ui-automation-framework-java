package com.TAF.Drivers;

import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class ChromeFactory extends AbstractDriver {

    private ChromeOptions getOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-infobars");
        options.addArguments("--start-maximized");
        Map<String, Object> prefs = new HashMap<>();
        String userDir = System.getProperty("user.dir");
        String downloadPath = userDir + "\\src\\test\\resources\\downloads";
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.prompt_for_download", false);
        prefs.put("download.default_directory", downloadPath);
        options.setExperimentalOption("prefs", prefs);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);
        options.setAcceptInsecureCerts(true);
        options.setCapability(CapabilityType.ENABLE_DOWNLOADS, true);

        // Normalize execution type to avoid NPE
        String executionType = PropertyReader.safeGet("executionType", "Local");

        // Headless mode for LocalHeadless or Remote
        if (executionType.equalsIgnoreCase("LocalHeadless") ||
                executionType.equalsIgnoreCase("Remote")) {
            options.addArguments("--headless=new"); // new headless mode for modern Chrome
        }

        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        return options;
    }

    @Override
    public WebDriver CreateDriver() {
        String executionType = PropertyReader.safeGet("executionType", "Local");

        if (executionType.equalsIgnoreCase("Local") || executionType.equalsIgnoreCase("LocalHeadless")) {
            return new ChromeDriver(getOptions());
        } else if (executionType.equalsIgnoreCase("Remote")) {
            try {
                return new RemoteWebDriver(
                        new URI("http://"
                                + PropertyReader.safeGet("remoteHost", "localhost")
                                + ":" + PropertyReader.safeGet("remotePort", "4444")
                                + "/wd/hub").toURL(),
                        getOptions()
                );
            } catch (Exception e) {
                LogsManager.error("Failed to create remote driver", e.getMessage());
                throw new RuntimeException(e);
            }
        } else {
            LogsManager.error("Invalid executionType: " + executionType);
            throw new RuntimeException("Invalid executionType: " + executionType);
        }
    }
}