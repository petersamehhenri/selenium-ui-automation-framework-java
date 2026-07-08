package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class AppleFeedback {
    private final GuiDriver driver;
    private final String feedbackUrl = "https://www.apple.com/feedback/iphone/";

    //Locators
    private final By nameField = By.cssSelector("#customer_name");
    private final By emailField = By.cssSelector("#customer_email");
    private final By subjectField = By.cssSelector("#subject");
    private final By countryDropdown = By.cssSelector("#feedback_country");
    private final By feedbackTypeDropdown = By.cssSelector("#feedback_type");
    private final By commentsField = By.cssSelector("#feedback_comment");
    private final By iosVersionDropdown = By.cssSelector("#ios_version");
    private final By policyCheckBox = By.cssSelector(".form-checkbox-indicator");
    private final By submitButton = By.cssSelector(".button");
    private final By successMessage = By.cssSelector(".typography-eyebrow-elevated");

    //Methods
    public AppleFeedback(GuiDriver driver) {
        this.driver = driver;
    }

    @Step("Navigate to Feedback page")
    public AppleFeedback navigate() {
        driver.browser().navigateTo(feedbackUrl);
        return this;
    }

    @Step("Fill form with {name}, {email}, {subject}, {country}, {feedbackType}, {comments}, {iosVersion}")
    public AppleFeedback fillForm(String name, String email, String subject, String country, String feedbackType, String comments, String iosVersion) {
        driver.element().type(nameField, name);
        driver.element().type(emailField, email);
        driver.element().type(subjectField, subject);
        driver.element().selectFromDropDown(countryDropdown, country);
        driver.element().selectFromDropDown(feedbackTypeDropdown, feedbackType);
        driver.element().type(commentsField, comments);
        driver.element().selectFromDropDown(iosVersionDropdown, iosVersion);
        return this;
    }

    @Step("Click on Submit button")
    public AppleFeedback clickOnSubmitButton() {
        driver.element().click(submitButton);
        return this;
    }

    @Step("Click on Policy checkbox")
    public AppleFeedback clickOnPolicyCheckBox() {
        driver.element().click(policyCheckBox);
        return this;
    }


    //Validations
    @Step("Verify success message: {expectedMessage}")
    public AppleFeedback verifySuccessMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(successMessage);
        driver.verification().Equals(expectedMessage, actualMessage, "Success message does not match, ");
        return this;
    }
}
