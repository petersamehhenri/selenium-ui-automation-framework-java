package com.TAF.Utils.report;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Utils.OperatingSystem.OSutils;
import com.TAF.Utils.TerminalUtils;
import org.jsoup.Jsoup;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class AllureBinaryManager {

    public static void downloadAndExtract() {
        try {
            String version = LazyHolder.VERSION;
            Path extractionDir = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "allure-" + version);

            if (Files.exists(extractionDir)) {
                LogsManager.info("Allure binaries already exist.");
                return;
            }

            // Give execute permissions to the binary if not on Windows
            if (!OSutils.getOS().equals(OSutils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", AllureConstant.USER_DIR.toString());
            }

            Path zipPath = downloadZip(version);
            extractZip(zipPath);

            LogsManager.info("Allure binaries downloaded and extracted.");

            // Give execute permissions to the binary if not on Windows
            if (!OSutils.getOS().equals(OSutils.OS.WINDOWS)) {
                TerminalUtils.executeTerminalCommand("chmod", "u+x", getExecutable().toString());
            }

            // Clean up the zip file after extraction
            Files.deleteIfExists(
                    Files.list(AllureConstant.EXTRACTION_DIR)
                            .filter(p -> p.toString().endsWith(".zip"))
                            .findFirst()
                            .orElse(null)
            );
        } catch (Exception e) {
            LogsManager.error("Error downloading or extracting binaries", e.getMessage());
        }
    }
    public static Path getExecutable() {
        String version = LazyHolder.VERSION;
        Path binaryPath = Paths.get(
                AllureConstant.EXTRACTION_DIR.toString(),
                "allure-" + version,
                "bin",
                "allure"
        );
        return OSutils.getOS() == OSutils.OS.WINDOWS
                ? binaryPath.resolveSibling(binaryPath.getFileName() + ".bat")
                : binaryPath;
    }

    private static class LazyHolder{
        static final String VERSION = resolveVersion();

        private static String resolveVersion() {
            try {
                String url = Jsoup.connect("https://github.com/allure-framework/allure2/releases/latest")
                        .followRedirects(true)
                        .execute()
                        .url()
                        .toString(); // ✅ actual redirected URL e.g. ".../tag/2.29.1"

                if (url.contains("/tag/")) {
                    return url.substring(url.lastIndexOf("/tag/") + 5);
                } else {
                    LogsManager.warn("Could not extract Allure version from URL: " + url);
                    return "2.29.1"; // fallback default
                }
            } catch (IOException e) {
                LogsManager.error("Cannot reach Allure releases URL", e.getMessage());
                return "2.29.1"; // safe fallback
            }
        }


    }
    // download ZIP file for Allure
    private static Path downloadZip(String version) {
        try {
            // https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.34.1/allure-commandline-2.34.1.zip
            String url = AllureConstant.ALLURE_ZIP_BASE_URL + version + "/allure-commandline-" + version + ".zip";
            // C:/Users/QA/.m2/repository/allure
            Path zipFile = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), "allure-" + version + ".zip");

            if (!Files.exists(zipFile)) {
                Files.createDirectories(AllureConstant.EXTRACTION_DIR);
                try (BufferedInputStream in = new BufferedInputStream(new URI(url).toURL().openStream());
                     OutputStream out = Files.newOutputStream(zipFile)) {
                    in.transferTo(out);
                } catch (Exception e) {
                    LogsManager.error("Invalid URL for Allure download: ", e.getMessage());
                }
            }

            return zipFile;
        } catch (Exception e) {
            LogsManager.error("Error downloading Allure zip file", e.getMessage());
            return Paths.get("");
        }
    }
    // Extract ZIP file for Allure
    private static void extractZip(Path zipPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                Path filePath = Paths.get(AllureConstant.EXTRACTION_DIR.toString(), File.separator, entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(filePath);
                } else {
                    Files.createDirectories(filePath.getParent());
                    Files.copy(zipInputStream, filePath);
                }
            }
        } catch (Exception e) {
            LogsManager.error("Error extracting Allure zip file", e.getMessage());
        }
    }


}