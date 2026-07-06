package com.TAF.Utils.report;

import com.TAF.Utils.Logs.LogsManager;
import com.google.common.collect.ImmutableMap;

import java.io.File;

import static com.TAF.Utils.DataReader.PropertyReader.getProperty;
import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;

public class AllureEnvironmentManager {

    public static void setEnvironmentVariables() {
        try {
            ImmutableMap<String, String> envVars = ImmutableMap.<String, String>builder()
                    .put("OS", safeGet("os.name"))
                    .put("Java version", safeGet("java.runtime.version"))
                    .put("Browser", safeGet("browserType"))
                    .put("Execution Type", safeGet("executionType"))  // typo-proof now
                    .put("URL", safeGet("baseUrlWeb"))
                    .build();

            allureEnvironmentWriter(
                    envVars,
                    AllureConstant.RESULTS_FOLDER + File.separator
            );

            LogsManager.info("Allure environment variables set: " + envVars.toString());
            AllureBinaryManager.downloadAndExtract();

        } catch (Exception e) {
            LogsManager.error("Failed to set Allure environment variables", e.getMessage());
        }
    }

    private static String safeGet(String key) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            LogsManager.warn("Property not found or empty: " + key + " → using 'NOT_DEFINED'");
            return "NOT_DEFINED";
        }
        return value;
    }
}