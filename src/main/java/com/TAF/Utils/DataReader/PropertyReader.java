package com.TAF.Utils.DataReader;

import com.TAF.Utils.Logs.LogsManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.Collection;
import java.util.Properties;

public class PropertyReader {
    private static final Properties properties = new Properties();

    public static Properties loadProperties() {
        try {
            Collection<File> propertiesFiles =
                    FileUtils.listFiles(new File("src/main/resources"),
                            new String[]{"properties"}, true);

            propertiesFiles.forEach(file -> {
                try {
                    properties.load(FileUtils.openInputStream(file));
                } catch (Exception e) {
                    LogsManager.error("Error loading properties file: " + file.getName(), e.getMessage());
                }
            });

            // Merge with system properties (so JVM args override file values)
            properties.putAll(System.getProperties());
            System.getProperties().putAll(properties);

            return properties;
        } catch (Exception e) {
            LogsManager.error("Error loading properties files", e.getMessage());
            return null;
        }
    }

    public static String getProperty(String key) {
        try {
            // First try JVM args, then fallback to file
            String value = System.getProperty(key);
            if (value == null) {
                value = properties.getProperty(key);
            }
            return value;
        } catch (Exception e) {
            LogsManager.error("Error getting property: ", key, e.getMessage());
            return null;
        }
    }

    /**
     * Safer accessor: returns defaultValue if property missing/empty.
     */
    public static String safeGet(String key, String defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            LogsManager.warn("Property not found or empty: " + key + " → using '" + defaultValue + "'");
            return defaultValue;
        }
        return value;
    }
}