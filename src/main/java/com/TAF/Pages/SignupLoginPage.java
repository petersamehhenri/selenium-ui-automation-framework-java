package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupLoginPage {
    public final NavigationBarComponent navigationBar;

    /* ********************************* VARIABLES ********************************* */
    private final GuiDriver driver;
    private final String SIGNUP_LOGIN_ENDPOINT = "/login";

    /* ********************************* LOCATORS ********************************** */
    private final By loginButton = By.cssSelector("[data-qa='login-button']");
    private final By signupName = By.cssSelector("[name='name']");
    private final By signupEmail = By.cssSelector("[data-qa='signup-email']");
    private final By signupButton = By.cssSelector("[data-qa='signup-button']");
    private final By signupLabel = By.cssSelector(".signup-form > h2");
    private final By loginErrorMessage = By.cssSelector("[action='/login'] > p");
    private final By signupErrorMessage = By.xpath("//p[.='Email Address already exist!']");
    private final By loginEmail = By.cssSelector("[data-qa='login-email']");
    private final By loginPassword = By.cssSelector("[name='password']");

    /* ******************************** CONSTRUCTORS ******************************* */

    public SignupLoginPage(GuiDriver driver) {
        this.driver = driver;
        this.navigationBar = new NavigationBarComponent(driver);
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Signup/Login Page")
    public SignupLoginPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + SIGNUP_LOGIN_ENDPOINT);
        return this;
    }

    @Step("Enter login email: {email}")
    public SignupLoginPage enterLoginEmail(String email) {
        driver.element().type(loginEmail, email);
        return this;
    }

    @Step("Enter login password: {password}")
    public SignupLoginPage enterLoginPassword(String password) {
        driver.element().type(loginPassword, password);
        return this;
    }

    @Step("Click Login Button")
    public SignupLoginPage login() {
        driver.element().click(loginButton);
        return this;
    }

    @Step("Enter signup name: {name}")
    public SignupLoginPage enterSignupName(String name) {
        driver.element().type(signupName, name);
        return this;
    }

    @Step("Enter signup email: {email}")
    public SignupLoginPage enterSignupEmail(String email) {
        driver.element().type(signupEmail, email);
        return this;
    }

    @Step("Click the Sign Up button")
    public SignupLoginPage signUp() {
        driver.element().click(signupButton);
        return new SignupLoginPage(driver);
    }


    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify that the signup page is displayed")
    public SignupLoginPage isSignupPageDisplayed() {
        driver.verification().isElementVisible(signupLabel);
        return this;
    }

    @Step("Verify Login Error Message: {expectedMessage}")
    public SignupLoginPage verifyLoginErrorMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(loginErrorMessage);
        driver.verification().Equals(expectedMessage, actualMessage, "Login Error Message is not as expected, ");
        return this;
    }

    @Step("Verify Signup Error Message: {expectedMessage}")
    public SignupLoginPage verifySignupErrorMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(signupErrorMessage);
        driver.verification().Equals(expectedMessage, actualMessage, "Signup Error Message is not as expected, ");
        return this;
    }
}
