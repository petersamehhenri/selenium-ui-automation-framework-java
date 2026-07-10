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
@Feature("UI Products Management - Products")
@Story("Add to Cart")
@Owner("Peter")
public class ProductsTest extends BaseTest {

    //Tests
    @Test
    @Description("Search for a product without login and validate its details")
    public void searchForProductWithoutLogin() {
        new ProductsPage(driver).navigate()
                .searchProduct(testData.getJsonData("SearchedProduct.Name"))
                .verifyProductDetails(testData.getJsonData("SearchedProduct.Name"),
                        testData.getJsonData("SearchedProduct.Price"));
    }

    @Test
    @Description("Add product to cart without login")
    public void addProductToCartWithoutLogin() {
        new ProductsPage(driver).navigate()
                .clickOnAddToCartButton(testData.getJsonData("Product1.Name"))
                .verifyItemAddedLabel(testData.getJsonData("Messages.ItemAdded"));

    }

    //Configurations

    @BeforeClass(alwaysRun = true)
    public void PreConditions() {
        testData = new JsonReader("Products_Data");
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

