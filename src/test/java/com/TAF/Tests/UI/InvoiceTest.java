package com.TAF.Tests.UI;

import com.TAF.Apis.UserManagementApi;
import com.TAF.Drivers.GuiDriver;
import com.TAF.Pages.*;
import com.TAF.Tests.BaseTest;
import com.TAF.Utils.DataReader.JsonReader;
import com.TAF.Utils.WaitsAndTime.TimeManager;
import io.qameta.allure.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Epic("Automation Exercise Project")
@Feature("UI Invoice Management - Invoice")
@Story("User Invoice")
@Severity(SeverityLevel.CRITICAL)
@Owner("Peter")
public class InvoiceTest extends BaseTest {

    private String timeStamp = TimeManager.getSimpleTimeStamp();

    @Test
    @Description("Create new user account")
    public void shouldRegisterNewUser() {
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
    }


    @Test(dependsOnMethods = "shouldRegisterNewUser")
    @Description("Login with existing user account")
    public void shouldLogin() {
        new SignupLoginPage(driver).navigate()
                .enterLoginEmail(testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com")
                .enterLoginPassword(testData.getJsonData("LoginPassword"))
                .login()
                .navigationBar
                .verifyUserLabel(testData.getJsonData("LoginName"));
    }

    @Test(dependsOnMethods = {"shouldLogin", "shouldRegisterNewUser"})
    @Description("Add product to cart")
    public void shouldAddProductToCart() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCartButton(testData.getJsonData("Product.Name"))
                .verifyItemAddedLabel(testData.getJsonData("Messages.ItemAdded"))
                .clickOnViewCartButton()
                .verifyProductDetails(
                        testData.getJsonData("Product.Name"),
                        testData.getJsonData("Product.Price"),
                        testData.getJsonData("Product.Quantity"),
                        testData.getJsonData("Product.Total"));
    }

    @Test(dependsOnMethods = {"shouldRegisterNewUser", "shouldLogin", "shouldAddProductToCart"})
    @Description("Checkout")
    public void shouldCheckout() {
        new CartPage(driver).navigate()
                .verifyProductDetails(testData.getJsonData("Product.Name"),
                        testData.getJsonData("Product.Price"),
                        testData.getJsonData("Product.Quantity"),
                        testData.getJsonData("Product.Total"))
                .proceedToCheckout()
                .shouldVerifyDeliveryAddress(
                        testData.getJsonData("TitleMale"),
                        testData.getJsonData("FirstName"),
                        testData.getJsonData("LastName"),
                        testData.getJsonData("CompanyName"),
                        testData.getJsonData("Address1"),
                        testData.getJsonData("Address2"),
                        testData.getJsonData("City"),
                        testData.getJsonData("State"),
                        testData.getJsonData("ZipCode"),
                        testData.getJsonData("Country"),
                        testData.getJsonData("MobileNo"))
                .shouldVerifyBillingAddress(
                        testData.getJsonData("TitleMale"),
                        testData.getJsonData("FirstName"),
                        testData.getJsonData("LastName"),
                        testData.getJsonData("CompanyName"),
                        testData.getJsonData("Address1"),
                        testData.getJsonData("Address2"),
                        testData.getJsonData("City"),
                        testData.getJsonData("State"),
                        testData.getJsonData("ZipCode"),
                        testData.getJsonData("Country"),
                        testData.getJsonData("MobileNo"));
    }

    @Test(dependsOnMethods = {"shouldRegisterNewUser", "shouldLogin", "shouldAddProductToCart", "shouldCheckout"})
    @Description("Payment")
    public void shouldPay() {
        new CheckoutPage(driver)
                .placeOrder()
                .enterCardDetails(testData.getJsonData("Card.NameOnCard"),
                        testData.getJsonData("Card.CardNumber"),
                        testData.getJsonData("Card.CVC"),
                        testData.getJsonData("Card.ExpirationMonth"),
                        testData.getJsonData("Card.ExpirationYear"))
                .verifyPaymentSuccessMessage(testData.getJsonData("Messages.PaymentSuccess"));
    }

    @Test(dependsOnMethods = {"shouldRegisterNewUser", "shouldLogin", "shouldAddProductToCart", "shouldCheckout", "shouldPay"})
    @Description("Download Invoice")
    public void shouldDownloadInvoice() {
        new PaymentPage(driver)
                .downloadInvoice()
                .verifyInvoiceDownloaded(testData.getJsonData("invoiceName"));
    }

    @Test(dependsOnMethods = {"shouldRegisterNewUser", "shouldLogin", "shouldAddProductToCart", "shouldCheckout", "shouldPay", "shouldDownloadInvoice"})
    @Description("Delete account as post condition")
    public void shouldDeleteAccount() {
        new UserManagementApi()
                .DeleteRegisterUserAccount(
                        (testData.getJsonData("LoginEmail") + timeStamp + "@gmail.com")
                        , testData.getJsonData("LoginPassword"))
                .VerifyUserIsDeleted();
    }


    @BeforeClass
    public void setUp() {
        testData = new JsonReader("Card_Data");
        driver = new GuiDriver();
        new NavigationBarComponent(driver).navigate();
    }


    @AfterClass
    public void tearDown() {
        driver.quitDriver();
    }
}
