package com.TAF.Utils.WaitsAndTime;

import com.TAF.Utils.DataReader.PropertyReader;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class WaitManager {

    private WebDriver driver;

    public WaitManager(WebDriver driver) {
        this.driver = driver;
    }

    //Fluent Wait
    public FluentWait<WebDriver> fluentWait() {
        return new FluentWait<>(driver)
                .withTimeout(java.time.Duration.ofSeconds(Long.parseLong(PropertyReader.getProperty("Default_Wait"))))
                .pollingEvery(java.time.Duration.ofMillis(1))
                .ignoreAll(GetAllExceptions());
    }

    private ArrayList<Class<? extends Exception>> GetAllExceptions() {
        ArrayList<Class<? extends Exception>> exceptions = new ArrayList<>();
        exceptions.add(NoSuchElementException.class);
        exceptions.add(TimeoutException.class);
        exceptions.add(ElementClickInterceptedException.class);
        exceptions.add(StaleElementReferenceException.class);
        return (exceptions);
    }
}