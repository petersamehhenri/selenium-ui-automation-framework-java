package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class ProductDetailsPage {
    /* ********************************* VARIABLES ********************************* */

    private final GuiDriver driver;
    private final String PRODUCT_DETAILS_PAGE_ENDPOINT = "/product-details/2";

    /* ********************************* LOCATORS ********************************** */

    private final By productNameField = By.cssSelector(".product-information > h2");
    private final By productPriceField = By.cssSelector(".product-information > span > span");
    private final By addToCartButton = By.cssSelector(".cart");
    private final By availabilityStatus = By.cssSelector(".product-information > p:nth-of-type(2)");
    private final By quantityInput = By.cssSelector(".product-information > input");
    private final By viewCartButton = By.xpath("//u[.='View Cart']");
    private final By continueShoppingButton = By.xpath(".btn-success");
    private final By nameInput = By.cssSelector("#name");
    private final By emailInput = By.cssSelector("#email");
    private final By reviewInput = By.cssSelector("#review");
    private final By submitButton = By.cssSelector("#button-review");
    private final By reviewLabel = By.xpath("//span[.='Thank you for your review.']");

    /* ******************************** CONSTRUCTORS ******************************* */

    public ProductDetailsPage(GuiDriver driver) {
        this.driver = driver;
    }


    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Product Details Page")
    public ProductDetailsPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + PRODUCT_DETAILS_PAGE_ENDPOINT);
        return this;
    }

    @Step("Add product review, {name}, {email}, {review}")
    public ProductDetailsPage addReview(String name, String email, String review) {
        driver.element().type(nameInput, name);
        driver.element().type(emailInput, email);
        driver.element().type(reviewInput, review);
        driver.element().click(submitButton);
        return this;
    }


    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify product details for {productName} with price {productPrice}")
    public ProductDetailsPage verifyProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().getText(productNameField);
        String actualProductPrice = driver.element().getText(productPriceField);
        LogsManager.info("Actual product name: " + actualProductName + " , actual price: " + actualProductPrice);
        driver.validation().Equals(productName, actualProductName, "Product name does not match, ");
        driver.validation().Equals(productPrice, actualProductPrice, "Product price does not match, ");
        return this;
    }

    @Step("Verify review message: {expectedText}")
    public ProductDetailsPage verifyReviewMessage(String expectedText) {
        String actualText = driver.element().getText(reviewLabel);
        LogsManager.info("Actual review message: " + actualText);
        driver.validation().Equals(expectedText, actualText, "Review label does not match expected text, ");
        return this;
    }
}
