package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class CartPage {
    /* ********************************* VARIABLES ********************************* */
    private final String CART_PAGE_ENDPOINT = "/view_cart";
    private final GuiDriver driver;

    /* ********************************* LOCATORS ********************************** */

    private final By proceedToCheckoutButton = By.cssSelector(".check_out");

    /* ******************************** CONSTRUCTORS ******************************* */

    public CartPage(GuiDriver driver) {
        this.driver = driver;
    }

    /* ****************************** DYNAMIC LOCATORS ***************************** */

    private By productName(String productName) {
        return By.xpath("//a[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath("//a[.='" + productName + "']/ancestor::tr//td[@class='cart_price']/p");
    }

    private By productQuantity(String productName) {
        return By.xpath("//a[.='" + productName + "']/ancestor::tr//td[@class='cart_quantity']/button");
    }

    private By removeButton(String productName) {
        return By.xpath("//a[.='" + productName + "']/ancestor::tr//td[contains(@class,'cart_delete')]/a");
    }

    private By totalPrice(String productName) {
        return By.xpath("//a[.='" + productName + "']/ancestor::tr//td[contains(@class,'cart_total')]/p");
    }

    /* ********************************* ACTIONS ******************************** */

    @Step("Navigate to Cart Page")
    public CartPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + CART_PAGE_ENDPOINT);
        return this;
    }

    @Step("Proceed to Checkout")
    public CheckoutPage proceedToCheckout() {
        driver.element().click(proceedToCheckoutButton);
        return new CheckoutPage(driver);
    }

    @Step("Remove product: {productName} from cart")
    public CartPage removeProduct(String productName) {
        driver.element().click(removeButton(productName));
        return this;
    }

    /* ********************************* VALIDATIONS ******************************** */

    @Step("Verify product details on cart. Product: {productName}, Price: {productPrice}, Quantity: {productQuantity}, Total: {productTotal}")
    public CartPage verifyProductDetails(String productName, String productPrice, String productQuantity, String productTotal) {
        String actualProductName = driver.element().getText(productName(productName));
        String actualProductPrice = driver.element().getText(productPrice(productName));
        String actualProductQuantity = driver.element().getText(productQuantity(productName));
        String actualProductTotal = driver.element().getText(totalPrice(productName));

        driver.validation().Equals(productName, actualProductName, "Product name does not match, ")
                .Equals(productPrice, actualProductPrice, "Product price does not match, ")
                .Equals(productQuantity, actualProductQuantity, "Product quantity does not match, ")
                .Equals(productTotal, actualProductTotal, "Product total does not match, ");
        return this;
    }
}
