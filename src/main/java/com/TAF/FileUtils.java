package com.TAF;

import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;

import java.io.File;

import static org.testcontainers.shaded.com.google.common.base.StandardSystemProperty.USER_DIR;

public class FileUtils {
    private static final String User_dir = PropertyReader.getProperty("user.dir") + File.separator;

    private FileUtils() {
//prevent instantiation
    }

    //renaming
// Renaming
    public static void renameFile(String oldName, String newName) {
        try {
            File oldFile = new File(USER_DIR + oldName);
            File newFile = new File(USER_DIR + newName);

            if (oldFile.renameTo(newFile)) {
                LogsManager.info("File renamed from " + oldName + " to " + newName);
            } else {
                LogsManager.error("Failed to rename file from " + oldName + " to " + newName);
            }
        } catch (Exception e) {
            LogsManager.error("Error renaming file: " + e.getMessage());
        }
    }

    //creating directory
    public static void createDirectory(String Path) {
        try {
            File file = new File(User_dir + Path);
            if (!file.exists()) {
                file.mkdir();
                LogsManager.info("Directory created" + Path);
            }
        } catch (Exception e) {
            LogsManager.error("could not create directory" + Path, e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // Force Delete
    public static void ForceDelete(File file) {
        try {
            org.apache.commons.io.FileUtils.forceDelete(file);
        } catch (Exception e) {
            LogsManager.error("Failed to delete file" + file.getAbsolutePath(), e.getMessage());

        }
    }


    //cleaning directory
    public static void cleanDirectory(File file) {
        try {
            org.apache.commons.io.FileUtils.deleteQuietly(file); //will not Delete while file in use
        } catch (Exception e) {
            LogsManager.error("Failed to clean directory" + file.getAbsolutePath(), e.getMessage());

        }
    }

    //Check if file exists
    public static boolean isFileExists(String fileName) {
        String downloadsPath = System.getProperty("user.dir") + File.separator + "/src/test/resources/downloads/";
        File file = new File(downloadsPath + fileName);
        return file.exists();
    }
}