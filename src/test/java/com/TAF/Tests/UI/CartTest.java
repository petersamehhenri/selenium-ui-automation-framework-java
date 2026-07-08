package com.TAF.Tests.UI;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Pages.NavigationBarComponent;
import com.TAF.Pages.ProductsPage;
import com.TAF.Tests.BaseTest;
import com.TAF.Utils.DataReader.JsonReader;
import io.qameta.allure.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Epic("Automation Exercise Project")
@Feature("UI Cart Management - Cart")
@Story("Verify product details on cart")
@Severity(SeverityLevel.CRITICAL)
@Owner("Peter")
public class CartTest extends BaseTest {

    /* ********************************* TESTS ********************************* */

    @Test
    @Description("Verify product details on cart without login")
    public void shouldVerifyProductDetailsOnCartWithoutLogin() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCartButton(testData.getJsonData("Product.Name"))
                .verifyItemAddedLabel(testData.getJsonData("Messages.CartAdded"))
                .clickOnViewCartButton()
                .verifyProductDetails(testData.getJsonData("Product.Name"),
                        testData.getJsonData("Product.Price"),
                        testData.getJsonData("Product.Quantity"),
                        testData.getJsonData("Product.Total"));
    }

    /* ***************************** CONFIGURATIONS ***************************** */

    @BeforeClass(alwaysRun = true)
    public void PreConditions() {
        testData = new JsonReader("Cart_Data");
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
