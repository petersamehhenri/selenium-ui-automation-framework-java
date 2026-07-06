package com.TAF.Tests.UI;

import com.TAF.Apis.UserManagementApi;
import com.TAF.Drivers.GuiDriver;
import com.TAF.Pages.NavigationBarComponent;
import com.TAF.Pages.SignupLoginPage;
import com.TAF.Pages.SignupPage;
import com.TAF.Tests.BaseTest;
import com.TAF.Utils.DataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.TAF.Utils.WaitsAndTime.TimeManager.getSimpleTimeStamp;

@Epic("Automation Exercise Project")
@Feature("UI User Management - Registration")
@Story("User SignUp")
@Owner("Peter")
public class SignUpTest extends BaseTest {
    String timeStamp = getSimpleTimeStamp();

    //Tests
    @Test
    @Story("User Registration - Successful Signup")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify that a user can successfully create a new account via UI")
    public void shouldRegisterNewUserSuccessfully() {
        new SignupLoginPage(driver).navigate()
                .enterSignupName(testData.getJsonData("SignUpName"))
                .enterSignupEmail(testData.getJsonData("SignUpEmail") + getSimpleTimeStamp() + "@gmail.com")
                .signUp();
        new SignupPage(driver)
                .fillSignupForm(
                        testData.getJsonData("TitleMale"),
                        testData.getJsonData("SignUpPassword"),
                        testData.getJsonData("Day"),
                        testData.getJsonData("Month"),
                        testData.getJsonData("Year"),
                        testData.getJsonData("FirstName"),
                        testData.getJsonData("LastName"),
                        testData.getJsonData("CompanyName"),
                        testData.getJsonData("Address1"),
                        testData.getJsonData("Address2"),
                        testData.getJsonData("Country"),
                        testData.getJsonData("State"),
                        testData.getJsonData("City"),
                        testData.getJsonData("ZipCode"),
                        testData.getJsonData("MobileNo")
                )
                .clickOnCreateAccountButton()
                .isAccountCreatedMessageDisplayed();
    }

    @Test
    @Story("User Registration - Duplicate Account Validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verify that the system prevents duplicate account registration and displays an error message when an existing email address is used.")
    public void shouldPreventDuplicateAccountRegistration() {
        //Precondition > Create a New Account
        new UserManagementApi().CreateRegisterUserAccount(
                        testData.getJsonData("SignUpName"),
                        testData.getJsonData("SignUpEmail") + timeStamp + "@gmail.com",
                        testData.getJsonData("SignUpPassword"),
                        testData.getJsonData("TitleMale"),
                        testData.getJsonData("Day"),
                        testData.getJsonData("Month"),
                        testData.getJsonData("Year"),
                        testData.getJsonData("FirstName"),
                        testData.getJsonData("LastName"),
                        testData.getJsonData("CompanyName"),
                        testData.getJsonData("Address1"),
                        testData.getJsonData("Address2"),
                        testData.getJsonData("Country"),
                        testData.getJsonData("State"),
                        testData.getJsonData("City"),
                        testData.getJsonData("ZipCode"),
                        testData.getJsonData("MobileNo")
                )
                .VerifyUserIsCreated();

        new SignupLoginPage(driver)
                .navigate()
                .enterSignupName(testData.getJsonData("SignUpName"))
                .enterSignupEmail(testData.getJsonData("SignUpEmail") + timeStamp + "@gmail.com")
                .signUp()
                .verifySignupErrorMessage(testData.getJsonData("Messages.Error"));
    }

    //Configurations
    @BeforeClass(alwaysRun = true)
    public void PreConditions() {
        testData = new JsonReader("SignUp_Data");
    }

    @BeforeMethod
    public void setup() {
        driver = new GuiDriver();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }

}
