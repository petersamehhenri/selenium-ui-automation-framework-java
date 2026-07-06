package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;


public class ProductsPage {
    /* ********************************* VARIABLES ********************************* */

    private final GuiDriver driver;
    private final String PRODUCTS_PAGE_ENDPOINT = "/products";
    private final By searchField = By.cssSelector("#search_product");

    /* ********************************* LOCATORS ********************************** */
    private final By searchButton = By.cssSelector(".fa-search");
    private final By itemAddedLabel = By.cssSelector(".modal-body > p:nth-of-type(1)");
    private final By viewCartButton = By.xpath("//u[.='View Cart']");
    private final By continueShoppingButton = By.xpath(".btn-success");
    public NavigationBarComponent navigationBarComponent;

    /* ******************************** CONSTRUCTORS ******************************* */

    public ProductsPage(GuiDriver driver) {
        this.driver = driver;
        this.navigationBarComponent = new NavigationBarComponent(driver);
    }

    /* ****************************** DYNAMIC LOCATORS ***************************** */
    private By productName(String productName) {
        return By.xpath("//div[@class='features_items']//div[@class='product-overlay']//p[.='" + productName + "']");
    }

    private By productPrice(String productName) {
        return By.xpath(
                "//div[contains(@class,'productinfo')]" +
                        "[.//p[normalize-space()='" + productName.trim() + "']]//h2"
        );
    }

    private By hoverOnProduct(String productName) {
        return By.xpath("//div[@class='productinfo text-center'] /p [text()='" + productName + "']");
    }

    private By addToCartButton(String productName) {
        return By.xpath("//div[@class='productinfo text-center'] /p[.='" + productName + "'] //following-sibling::a");
    }

    private By viewProductButton(String productName) {
        return By.xpath(
                "//div[contains(@class,'product-image-wrapper')][.//p[normalize-space()='" + productName.trim() + "']]//a[contains(.,'View Product')]"
        );
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Products Page")
    public ProductsPage navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web") + PRODUCTS_PAGE_ENDPOINT);
        return this;
    }

    @Step("Search for product: {productName}")
    public ProductsPage searchProduct(String productName) {
        driver.element().type(searchField, productName).click(searchButton);
        return this;
    }

    @Step("Click on Add To Cart button for product: {productName}")
    public ProductsPage clickOnAddToCartButton(String productName) {
        driver.element().click(addToCartButton(productName));
        return this;
    }

    @Step("Click on View Product button for product: {productName}")
    public ProductDetailsPage clickOnViewProductButton(String productName) {
        driver.element().click(viewProductButton(productName));
        return new ProductDetailsPage(driver);
    }

    @Step("Click on View Cart button")
    public CartPage clickOnViewCartButton() {
        driver.element().click(viewCartButton);
        return new CartPage(driver);
    }

    @Step("Click on Continue Shopping button")
    public ProductsPage clickOnContinueShoppingButton() {
        driver.element().click(continueShoppingButton);
        return this;
    }

    /* ******************************** VALIDATIONS ******************************** */

    @Step("Validate product details for {productName} with price {productPrice}")
    public ProductsPage verifyProductDetails(String productName, String productPrice) {
        String actualProductName = driver.element().hover(productName(productName)).getText(productName(productName));
        String actualProductPrice = driver.element().hover(productName(productName)).getText(this.productPrice(productName));
        LogsManager.info("Validating product details for: " + actualProductName + "with price" + actualProductPrice);
        driver.validation().Equals(actualProductName, productName, "Product name does not match");
        driver.validation().Equals(actualProductPrice, productPrice, "Product price does not match");
        return this;
    }

    @Step("Validate item added label contains: {expectedText}")
    public ProductsPage verifyItemAddedLabel(String expectedText) {
        String actualText = driver.element().getText(itemAddedLabel);
        LogsManager.info("Validating item added label: " + actualText);
        driver.verification().Equals(actualText, expectedText, "Item added label does not match expected text, ");
        return this;
    }

}