package com.TAF.Utils.report;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AllureConstant {

    // Base directories
    public static final Path USER_DIR = Paths.get(System.getProperty("user.dir") + File.separator);
    public static final Path USER_HOME = Paths.get(System.getProperty("user.home") + File.separator);

    // Allure results and report paths
    public static final Path RESULTS_FOLDER =
            Paths.get(USER_DIR.toString(), "TestOutput", "allure-results" + File.separator);

    public static final Path REPORT_PATH =
            Paths.get(USER_DIR.toString(), "TestOutput", "reports" + File.separator);

    public static final Path FULL_REPORT_PATH =
            Paths.get(USER_DIR.toString(), "TestOutput", "full-report" + File.separator);

    // History for trends
    public static final Path HISTORY_FOLDER =
            Paths.get(FULL_REPORT_PATH.toString(), "history" + File.separator);

    public static final Path RESULTS_HISTORY_FOLDER =
            Paths.get(RESULTS_FOLDER.toString(), "history" + File.separator);

    // Report naming
    public static final String INDEX_HTML = "index.html";
    public static final String REPORT_PREFIX = "AllureReport_";
    public static final String REPORT_EXTENSION = ".html";

    // Allure binary download
    public static final String ALLURE_ZIP_BASE_URL =
            "https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/";

    // Where to extract binaries
    public static final Path EXTRACTION_DIR =
            Paths.get(USER_HOME.toString(), ".m2", "repository", "allure" + File.separator);
}