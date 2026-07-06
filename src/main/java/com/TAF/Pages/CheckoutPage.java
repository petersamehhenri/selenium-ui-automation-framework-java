package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CheckoutPage {
    /* ********************************* VARIABLES ********************************* */

    private final String CHECKOUT_PAGE_ENDPOINT = "/checkout";
    private final GuiDriver driver;

    /* ********************************* LOCATORS ********************************** */

    private final By placeOrderButton = By.cssSelector(".check_out");

    /* ************************* Delivery Address Locators ************************* */

    private final By deliveryName = By.cssSelector("#address_delivery > .address_firstname");
    private final By deliveryCompany = By.cssSelector("#address_delivery > li:nth-of-type(3)");
    private final By deliveryAddress1 = By.cssSelector("#address_delivery > li:nth-of-type(4)");
    private final By deliveryAddress2 = By.cssSelector("#address_delivery > li:nth-of-type(5)");
    private final By deliveryCityStateZipcode = By.cssSelector("#address_delivery > .address_city");
    private final By deliveryCountry = By.cssSelector("#address_delivery > .address_country_name");
    private final By deliveryPhone = By.cssSelector("#address_delivery > .address_phone");

    /* ************************* Billing Address Locators ************************** */

    private final By billingName = By.cssSelector("#address_invoice > .address_firstname");
    private final By billingCompany = By.cssSelector("#address_invoice > li:nth-of-type(3)");
    private final By billingAddress1 = By.cssSelector("#address_invoice > li:nth-of-type(4)");
    private final By billingAddress2 = By.cssSelector("#address_invoice > li:nth-of-type(5)");
    private final By billingCityStateZipcode = By.cssSelector("#address_invoice > .address_city");
    private final By billingCountry = By.cssSelector("#address_invoice > .address_country_name");
    private final By billingPhone = By.cssSelector("#address_invoice > .address_phone");

    /* ******************************** CONSTRUCTORS ******************************* */

    public CheckoutPage(GuiDriver driver) {
        this.driver = driver;
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Checkout Page")
    public CheckoutPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + CHECKOUT_PAGE_ENDPOINT);
        return this;
    }

    @Step("Place Order")
    public PaymentPage placeOrder() {
        driver.element().click(placeOrderButton);
        return new PaymentPage(driver);
    }

    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify Delivery Address")
    public CheckoutPage shouldVerifyDeliveryAddress(String expectedTitle, String expectedFirstName,
                                                    String expectedLastName, String expectedCompany,
                                                    String expectedAddress1, String expectedAddress2,
                                                    String expectedCity, String expectedState,
                                                    String expectedZipcode, String expectedCountry,
                                                    String expectedPhone) {
        String expectedName = expectedTitle + ". " + expectedFirstName + " " + expectedLastName;
        String expectedCityStateZipcode = expectedZipcode + " " + expectedCity + " " + expectedState;

        String actualName = driver.element().getText(deliveryName);
        String actualCompany = driver.element().getText(deliveryCompany);
        String actualAddress1 = driver.element().getText(deliveryAddress1);
        String actualAddress2 = driver.element().getText(deliveryAddress2);
        String actualCityStateZipcode = driver.element().getText(deliveryCityStateZipcode);
        String actualCountry = driver.element().getText(deliveryCountry);
        String actualPhone = driver.element().getText(deliveryPhone);
        driver.validation()
                .Equals(expectedName, actualName, "Delivery name does not match, ")
                .Equals(expectedCompany, actualCompany, "Delivery Company does not match, ")
                .Equals(expectedAddress1, actualAddress1, "Delivery Address1 does not match, ")
                .Equals(expectedAddress2, actualAddress2, "Delivery Address2 does not match, ")
                .Equals(expectedCityStateZipcode, actualCityStateZipcode, "Delivery CityStateZipcode does not match, ")
                .Equals(expectedCountry, actualCountry, "Delivery Country does not match, ")
                .Equals(expectedPhone, actualPhone, "Delivery Phone does not match, ");
        return this;
    }

    @Step("Verify Billing Address")
    public CheckoutPage shouldVerifyBillingAddress(String expectedTitle, String expectedFirstName,
                                                   String expectedLastName, String expectedCompany,
                                                   String expectedAddress1, String expectedAddress2,
                                                   String expectedCity, String expectedState,
                                                   String expectedZipcode, String expectedCountry,
                                                   String expectedPhone) {
        String expectedName = expectedTitle + ". " + expectedFirstName + " " + expectedLastName;
        String expectedCityStateZipcode = expectedZipcode + " " + expectedCity + " " + expectedState;


        String actualName = driver.element().getText(billingName);
        String actualCompany = driver.element().getText(billingCompany);
        String actualAddress1 = driver.element().getText(billingAddress1);
        String actualAddress2 = driver.element().getText(billingAddress2);
        String actualCityStateZipcode = driver.element().getText(billingCityStateZipcode);
        String actualCountry = driver.element().getText(billingCountry);
        String actualPhone = driver.element().getText(billingPhone);
        driver.validation()
                .Equals(expectedName, actualName, "Billing Name does not match, ")
                .Equals(expectedCompany, actualCompany, "Billing Company does not match, ")
                .Equals(expectedAddress1, actualAddress1, "Billing Address1 does not match, ")
                .Equals(expectedAddress2, actualAddress2, "Billing Address2 does not match, ")
                .Equals(expectedCityStateZipcode, actualCityStateZipcode, "Billing CityStateZipcode does not match, ")
                .Equals(expectedCountry, actualCountry, "Billing Country does not match, ")
                .Equals(expectedPhone, actualPhone, "Billing Phone does not match, ");
        return this;
    }
}
