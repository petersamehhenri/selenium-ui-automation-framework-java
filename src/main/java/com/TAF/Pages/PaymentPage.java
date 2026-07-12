package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class PaymentPage {

    /* ********************************* VARIABLES ********************************* */

    private final String PAYMENT_PAGE_ENDPOINT = "/payment";
    private final GuiDriver driver;

    /* ********************************* LOCATORS ********************************** */

    private final By nameOnCardField = By.cssSelector("[name='name_on_card']");
    private final By cardNumberField = By.cssSelector("[name='card_number']");
    private final By cvcField = By.cssSelector("[name='cvc']");
    private final By expirationMonthField = By.cssSelector("[name='expiry_month']");
    private final By expirationYearField = By.cssSelector("[name='expiry_year']");
    private final By payButton = By.cssSelector("#submit");
    private final By paymentSuccessMessage = By.cssSelector(".title > b");
    private final By downloadInvoiceButton = By.cssSelector(".check_out");

    /* ******************************** CONSTRUCTORS ******************************* */

    public PaymentPage(GuiDriver driver) {
        this.driver = driver;
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Payment Page")
    public PaymentPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + PAYMENT_PAGE_ENDPOINT);
        return this;
    }

    @Step("Fill payment card information")
    public PaymentPage enterCardDetails(String nameOnCard, String cardNumber, String cvc, String expirationMonth, String expirationYear) {
        driver.element()
                .type(nameOnCardField, nameOnCard)
                .type(cardNumberField, cardNumber)
                .type(cvcField, cvc)
                .type(expirationMonthField, expirationMonth)
                .type(expirationYearField, expirationYear)
                .click(payButton);
        return this;
    }

    @Step("Download invoice")
    public PaymentPage downloadInvoice() {
        driver.element().click(downloadInvoiceButton);
        return this;
    }

    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify payment success message")
    public PaymentPage verifyPaymentSuccessMessage(String expectedMessage) {
        String actualMessage = driver.element().getText(paymentSuccessMessage);
        driver.validation().Equals(expectedMessage, actualMessage, "Payment success message does not match, ");
        return this;
    }

    @Step("Verify invoice downloaded")
    public PaymentPage verifyInvoiceDownloaded(String invoiceName) {
        driver.verification().assertFileExists(invoiceName, "File does not exist, ");
        return this;
    }
}
