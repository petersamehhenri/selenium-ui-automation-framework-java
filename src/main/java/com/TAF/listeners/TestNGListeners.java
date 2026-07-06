package com.TAF.listeners;

import com.TAF.Drivers.WebDriverProvider;
import com.TAF.FileUtils;
import com.TAF.Media.ScreenRecordManager;
import com.TAF.Media.ScreenshotsManager;
import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.report.AllureAttachmentManager;
import com.TAF.Utils.report.AllureConstant;
import com.TAF.Utils.report.AllureEnvironmentManager;
import com.TAF.Utils.report.AllureReportGenerator;
import com.TAF.Validations.Validation;
import io.qameta.allure.testng.AllureTestNg;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;

public class TestNGListeners extends AllureTestNg implements ISuiteListener, ITestListener, IInvokedMethodListener, IExecutionListener {


    public void onStart(ISuite suite) {
        suite.getXmlSuite().setName("Automation Project");
    }

    @Override
    public void onExecutionStart() {
        LogsManager.info("Execution Started");

        CleanTestOutputDirectories();
        LogsManager.info("Directories are clean");

        AllureReportGenerator.copyHistory();
        LogsManager.info("History folder copied");

        CreateTestOutputDirectories();
        LogsManager.info("Directories are created");

        PropertyReader.loadProperties();
        LogsManager.info("Properties are loaded");


        AllureEnvironmentManager.setEnvironmentVariables();
        LogsManager.info("Environment variables are loaded");
    }

    @Override
    public void onExecutionFinish() {
        AllureReportGenerator.copyHistory();
        AllureReportGenerator.generateReports(true);

        // auto open report if flag is enabled
        if (Boolean.parseBoolean(System.getProperty("openAllureReportAfterExecution", "true"))) {
            AllureReportGenerator.renameAndOpenReport(true); // true = single-file
        }

        LogsManager.info("Report generated and opened (if configured).");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenRecordManager.startRecording();
            LogsManager.info("Test Case " + testResult.getMethod().getMethodName() + " Started");
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod()) {
            ScreenRecordManager.stopRecording(testResult.getName());
            Validation.assertAll();

            WebDriver driver = null;
            if (testResult.getInstance() instanceof WebDriverProvider provider) {
                driver = provider.getWebDriver();
            }

            // Always capture a screenshot based on result status
            switch (testResult.getStatus()) {
                case ITestResult.SUCCESS ->
                        ScreenshotsManager.captureFullScreenshot(driver, "Passed_" + testResult.getName());
                case ITestResult.FAILURE ->
                        ScreenshotsManager.captureFullScreenshot(driver, "Failed_" + testResult.getName());
                case ITestResult.SKIP ->
                        ScreenshotsManager.captureFullScreenshot(driver, "Skipped_" + testResult.getName());
            }

            // Attach artifacts to Allure
            AllureAttachmentManager.attachLogs();
            AllureAttachmentManager.attachRecords(testResult.getName());
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult) {
        LogsManager.info("Test Case " + testResult.getName() + " Passed");
        super.onTestSuccess(testResult); // Allure listener
    }

    @Override
    public void onTestFailure(ITestResult testResult) {
        LogsManager.info("Test Case " + testResult.getName() + " Failed");
        super.onTestFailure(testResult); // Allure listener
    }

    @Override
    public void onTestSkipped(ITestResult testResult) {
        LogsManager.info("Test Case " + testResult.getName() + " Skipped");
        super.onTestSkipped(testResult); // Allure listener
    }


    private void CleanTestOutputDirectories() {
        try {
            // 1. Shutdown log4j to release file lock
            org.apache.logging.log4j.LogManager.shutdown();

            // 2. Clean allure results
            FileUtils.cleanDirectory(AllureConstant.RESULTS_FOLDER.toFile());

            // 3. Clean screenshots and recordings
            FileUtils.cleanDirectory(new File(ScreenshotsManager.SCREENSHOTS_Path));
            FileUtils.cleanDirectory(new File(ScreenRecordManager.RECORDINGS_PATH));
            FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));

            // 4. Delete old log file
            File logFile = new File(LogsManager.LOGS_PATH + File.separator + "logs.log");
            if (logFile.exists()) {
                FileUtils.ForceDelete(logFile);
                LogsManager.info("Old log file deleted: " + logFile.getAbsolutePath());
            }

            // 5. Reinitialize log4j (so new logs go to a fresh logs.log file)
            ((org.apache.logging.log4j.core.LoggerContext) org.apache.logging.log4j.LogManager.getContext(false))
                    .reconfigure();

        } catch (Exception e) {
            LogsManager.error("Failed cleaning output directories", e.getMessage());
        }
    }


    private void CreateTestOutputDirectories() {
        FileUtils.createDirectory(ScreenshotsManager.SCREENSHOTS_Path);
        FileUtils.createDirectory(ScreenRecordManager.RECORDINGS_PATH);
        FileUtils.cleanDirectory(new File("src/test/resources/downloads/"));

    }
}