package com.TAF.Utils.report;

import com.TAF.Utils.Logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.awt.Desktop;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.TAF.Utils.DataReader.PropertyReader.getProperty;
import static com.TAF.Utils.report.AllureConstant.*;

public class AllureReportGenerator {

    // Generate allure report
    public static void generateReports(boolean isSingleFile) {
        Path outputFolder = isSingleFile ? REPORT_PATH : FULL_REPORT_PATH;

        List<String> command = new ArrayList<>(List.of(
                AllureBinaryManager.getExecutable().toString(),
                "generate",
                RESULTS_FOLDER.toString(),
                "-o", outputFolder.toString(),
                "--clean"
        ));

        if (isSingleFile) {
            command.add("--single-file");
        }

        LogsManager.info("Generating Allure report: " + command);
        com.TAF.Utils.TerminalUtils.executeTerminalCommand(command.toArray(new String[0]));
    }

    // Rename and open allure report automatically
    public static void renameAndOpenReport(boolean isSingleFile) {
        try {
            Path indexPath = REPORT_PATH.resolve(INDEX_HTML);

            if (!Files.exists(indexPath)) {
                LogsManager.error("Cannot rename/open report. File not found: " + indexPath);
                return;
            }

            String newFileName;
            if (isSingleFile) {
                // Safe filename for Windows (no : or ,)
                String safeTimestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
                newFileName = REPORT_PREFIX + safeTimestamp + REPORT_EXTENSION;

                File renamedFile = new File(REPORT_PATH.toFile(), newFileName);
                Files.move(indexPath, renamedFile.toPath());

                LogsManager.info("Renamed report: " + renamedFile.getAbsolutePath());

                // Open the new file directly
                openReport(renamedFile.toPath());
            } else {
                LogsManager.info("Opening report from index.html (multi-file mode).");
                openReport(indexPath);
            }

        } catch (Exception e) {
            LogsManager.error("Failed to rename/open report: " + e.getMessage());
        }
    }

    // Open allure report in browser
    private static void openReport(Path reportPath) {
        if (!getProperty("executionType").equalsIgnoreCase("Local")) return;

        if (!Files.exists(reportPath)) {
            LogsManager.warn("Report file not found: " + reportPath);
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(reportPath.toUri());
                LogsManager.info("Allure report opened in browser: " + reportPath);
            } else {
                LogsManager.warn("Desktop not supported. Report available at: " + reportPath);
            }
        } catch (Exception e) {
            LogsManager.error("Failed to open Allure report", e.getMessage());
        }
    }

    // Copy history folder to results
    public static void copyHistory() {
        try {
            Path sourceHistory = REPORT_PATH.resolve("history");     // reports/history
            Path targetHistory = RESULTS_FOLDER.resolve("history");  // allure-results/history

            if (Files.exists(sourceHistory)) {
                FileUtils.copyDirectory(sourceHistory.toFile(), targetHistory.toFile());
                LogsManager.info("History copied from " + sourceHistory + " to " + targetHistory);
            } else {
                LogsManager.warn("No history found in previous reports folder: " + sourceHistory);
            }
        } catch (Exception e) {
            LogsManager.error("Error copying history files", e.getMessage());
        }
    }


}