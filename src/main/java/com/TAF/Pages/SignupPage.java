package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class SignupPage {
    /* ********************************* VARIABLES ********************************* */

    private final GuiDriver driver;

    /* ********************************* LOCATORS ********************************** */
    private final By nameField = By.cssSelector("[name='name']");
    private final By emailField = By.cssSelector("[name='email']");
    private final By passwordField = By.cssSelector("[name='password']");
    private final By dayDropdown = By.cssSelector("#days");
    private final By monthDropdown = By.cssSelector("#months");
    private final By yearDropdown = By.cssSelector("#years");
    private final By newsletter = By.cssSelector("[name='newsletter']");
    private final By specialOffers = By.cssSelector("#optin");
    private final By firstNameField = By.cssSelector("[name='first_name']");
    private final By lastNameField = By.cssSelector("[name='last_name']");
    private final By companyField = By.cssSelector("[name='company']");
    private final By address1Field = By.cssSelector("[name='address1']");
    private final By address2Field = By.cssSelector("[name='address2']");
    private final By countryDropdown = By.cssSelector("[name='country']");
    private final By stateField = By.cssSelector("[name='state']");
    private final By cityField = By.cssSelector("[name='city']");
    private final By zipCodeField = By.cssSelector("#zipcode");
    private final By mobileNumberField = By.cssSelector("#mobile_number");
    private final By createAccountButton = By.cssSelector("[data-qa='create-account']");
    private final By accountCreatedMessage = By.xpath("//b[.='Account Created!']");
    private final By continueButton = By.xpath("//a[.='Continue']");

    /* ******************************** CONSTRUCTORS ******************************* */

    public SignupPage(GuiDriver driver) {
        this.driver = driver;
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Select Title {title}") /* Mr - Mrs */
    private SignupPage selectTitle(String title) {
        By titleLocator = By.xpath("//input[@value='" + title + "']");
        driver.element().click(titleLocator);
        return this;
    }

    @Step("Fill signup form with user details")
    public SignupPage fillSignupForm(String title,
                                     String password,
                                     String day,
                                     String month,
                                     String year,
                                     String firstName,
                                     String lastName,
                                     String company,
                                     String address1,
                                     String address2,
                                     String country,
                                     String state,
                                     String city,
                                     String zipCode,
                                     String mobileNumber) {

        selectTitle(title);
        driver.element().type(this.passwordField, password);
        driver.element().selectFromDropDown(this.dayDropdown, day);
        driver.element().selectFromDropDown(this.monthDropdown, month);
        driver.element().selectFromDropDown(this.yearDropdown, year);
        driver.element().click(newsletter);
        driver.element().click(specialOffers);
        driver.element().type(this.firstNameField, firstName);
        driver.element().type(this.lastNameField, lastName);
        driver.element().type(this.companyField, company);
        driver.element().type(this.address1Field, address1);
        driver.element().type(this.address2Field, address2);
        driver.element().selectFromDropDown(this.countryDropdown, country);
        driver.element().type(this.stateField, state);
        driver.element().type(this.cityField, city);
        driver.element().type(this.zipCodeField, zipCode);
        driver.element().type(this.mobileNumberField, mobileNumber);
        return this;
    }

    @Step("Click on Create Account Button")
    public SignupPage clickOnCreateAccountButton() {
        driver.element().click(createAccountButton);
        return this;
    }

    @Step("Click on Continue Button")
    public NavigationBarComponent clickOnContinueButton() {
        driver.element().click(continueButton);
        return new NavigationBarComponent(driver);
    }

    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify Account Is Created")
    public SignupPage isAccountCreatedMessageDisplayed() {
        driver.verification().isElementVisible(accountCreatedMessage);
        return this;
    }
}
