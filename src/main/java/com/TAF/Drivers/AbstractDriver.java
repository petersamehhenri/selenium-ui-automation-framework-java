package com.TAF.Drivers;

import com.TAF.Utils.DataReader.PropertyReader;
import org.openqa.selenium.WebDriver;

import java.io.File;

public abstract class AbstractDriver {
    protected final String remoteHost = PropertyReader.getProperty("remoteHost");
    protected final String remotePort = PropertyReader.getProperty("remotePort");

    protected File haramBlurExtensions = new File("src/main/resources/Extensions/0.6.9_0.crx");
    protected File downloadsPath = new File("\\src\\test\\resources\\downloads");

    public abstract WebDriver CreateDriver();
}