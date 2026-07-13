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
@Feature("UI Products Details Management - Products")
@Story("Verify product details")
@Owner("Peter")
public class ProductDetailsTest extends BaseTest {

    @Test
    @Description("Verify product details")
    public void shouldVerifyProductDetails() {
        new ProductsPage(driver).navigate()
                .clickOnViewProductButton(testData.getJsonData("Product.Name"))
                .verifyProductDetails(testData.getJsonData("Product.Name"), testData.getJsonData("Product.Price"));
    }

    @Test
    @Description("Verify review message")
    public void shouldVerifyReviewMessage() {
        new ProductsPage(driver).navigate()
                .clickOnViewProductButton(testData.getJsonData("Product.Name"))
                .addReview(testData.getJsonData("Review.Name")
                        , testData.getJsonData("Review.Email")
                        , testData.getJsonData("Review.Review"))
                .verifyReviewMessage(testData.getJsonData("Messages.Review"));
    }

    @BeforeClass(alwaysRun = true)
    public void PreConditions() {
        testData = new JsonReader("ProductDetails_Data");
    }

    @BeforeMethod
    public void setup() {
        driver = new GuiDriver();
        new NavigationBarComponent(driver).navigate();
        driver.browser().closeExtensionTab();
    }

    @AfterMethod
    public void tearDown() {
        driver.quitDriver();
    }
}
