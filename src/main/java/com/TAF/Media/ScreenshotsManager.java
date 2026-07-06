package com.TAF.Media;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.WaitsAndTime.TimeManager;
import com.TAF.Utils.report.AllureAttachmentManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;

public class ScreenshotsManager  {
    public static final String SCREENSHOTS_Path = "TestOutput/Screenshots/";
    // Capture full screenshot and return the path
    public static String captureFullScreenshot(WebDriver driver, String screenshotName) {
    try{
        // Take screenshot
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Always save under TestOutput/Screenshots
        File screenshotFile = new File(SCREENSHOTS_Path + screenshotName+ TimeManager.getTimeStamp() + ".png");
        FileUtils.copyFile(src, screenshotFile);

        // Attach to Allure
        AllureAttachmentManager.attachScreenshot(screenshotName , screenshotFile.getAbsolutePath());

          LogsManager.info("Screenshot captured");
          return screenshotFile.getAbsolutePath();
    } catch (Exception e) {
        LogsManager.error("Screenshot capture failed" + e.getMessage());
        return null;
    }
    }
    // Capture element screenshot and return the path
    public static String captureElementScreenshot(WebDriver driver, String elementName, By elementSelector) {
        try {
            String ariaName= driver.findElement(elementSelector).getAccessibleName();
            // Take screenshot
            File src = driver.findElement(elementSelector).getScreenshotAs(OutputType.FILE);

            File screenshotFile = new File(SCREENSHOTS_Path + ariaName + TimeManager.getTimeStamp() + ".png");
            FileUtils.copyFile(src, screenshotFile);

            // ToDO Attach to Allure
            LogsManager.info("Element Screenshot captured for element: " + elementName);
            return screenshotFile.getAbsolutePath();
        } catch (Exception e) {
            LogsManager.error("Element Screenshot capture failed for element: " + elementName + " Error: " + e.getMessage());
            return null;
        }
    }
}