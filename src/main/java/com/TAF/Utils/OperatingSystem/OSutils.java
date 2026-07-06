package com.TAF.Utils.OperatingSystem;

import com.TAF.Utils.DataReader.PropertyReader;

public class OSutils {
    public enum OS { WINDOWS , MAC , LINUX , OTHER }

    public static OS getOS() {
        String osName = PropertyReader.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OS.WINDOWS;
        }
        if (osName.contains("mac")) {
            return OS.MAC;
        }
        if (osName.contains("nux") || osName.contains("nix") || osName.contains("aix")) {
            return OS.LINUX;
        }
        return OS.OTHER;
    }
}