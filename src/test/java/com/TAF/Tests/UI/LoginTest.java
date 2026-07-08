package com.TAF.Tests.UI;

import com.TAF.Apis.UserManagementApi;
import com.TAF.Drivers.GuiDriver;
import com.TAF.Pages.NavigationBarComponent;
import com.TAF.Pages.SignupLoginPage;
import com.TAF.Tests.BaseTest;
import com.TAF.Utils.DataReader.JsonReader;
import com.TAF.Utils.WaitsAndTime.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


@Epic("Automation Exercise Project")
@Feature("UI User Management - Login")
@Story("User Login")
@Severity(SeverityLevel.CRITICAL)
@Owner("Peter")
public class LoginTest extends BaseTest {

    /* ********************************* VARIABLES ********************************* */

    String timeStamp = TimeManager.getSimpleTimeStamp();

    /* ********************************* TESTS ********************************* */

    @Test
    @Description("Verify that the user can log in successfully with valid credentials.")
    public void shouldLoginSuccessfullyWithValidCredentials() {
        new UserManagementApi().CreateRegisterUserAccountMinimalDetails(
                testData.getJsonData("LoginName"),
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword"),
                testData.getJsonData("FirstName"),
                testData.getJsonData("LastName")
        ).VerifyUserIsCreated();

        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("LoginPassword"))
                .login()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("LoginName"));


        new UserManagementApi().DeleteRegisterUserAccount(
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword")
        ).VerifyUserIsDeleted();
    }


    @Test
    @Description("Verify that the user can't login successfully with invalid Email.")
    public void shouldNotLoginSuccessfullyWithInvalidEmail() {
        new UserManagementApi().CreateRegisterUserAccountMinimalDetails(
                testData.getJsonData("LoginName"),
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword"),
                testData.getJsonData("FirstName"),
                testData.getJsonData("LastName")
        ).VerifyUserIsCreated();

        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("LoginEmail") + "123456789" + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("LoginPassword"))
                .login()
                .verifyLoginErrorMessage(testData.getJsonData("Messages.Error"));


        new UserManagementApi().DeleteRegisterUserAccount(
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword")
        ).VerifyUserIsDeleted();
    }

    @Test
    @Description("Verify that the user can't log in successfully with invalid Password.")
    public void shouldNotLoginSuccessfullyWithInvalidPassword() {
        new UserManagementApi().CreateRegisterUserAccountMinimalDetails(
                testData.getJsonData("LoginName"),
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword"),
                testData.getJsonData("FirstName"),
                testData.getJsonData("LastName")
        ).VerifyUserIsCreated();

        new SignupLoginPage(driver)
                .navigate()
                .enterLoginEmail(testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("LoginPassword") + timeStamp)
                .login()
                .verifyLoginErrorMessage(testData.getJsonData("Messages.Error"));

        new UserManagementApi().DeleteRegisterUserAccount(
                testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com",
                testData.getJsonData("LoginPassword")
        ).VerifyUserIsDeleted();
    }


    /* ***************************** CONFIGURATIONS ***************************** */

    @BeforeClass(alwaysRun = true)
    public void PreConditions() {
        testData = new JsonReader("Login_Data");
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