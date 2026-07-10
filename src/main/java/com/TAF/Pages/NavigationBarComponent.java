package com.TAF.Pages;

import com.TAF.Drivers.GuiDriver;
import com.TAF.Utils.DataReader.PropertyReader;
import com.TAF.Utils.Logs.LogsManager;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

public class NavigationBarComponent {
    /* ********************************* VARIABLES ********************************* */

    private final GuiDriver driver;

    /* ********************************* LOCATORS ********************************** */
    private final By homeButton = By.xpath("//a[contains(.,'Home')]");
    private final By productsButton = By.xpath("//a[.='\uE8F8 Products']");
    private final By cartButton = By.xpath("//ul[@class='nav navbar-nav']//i[@class='fa fa-shopping-cart']");
    private final By signupLoginButton = By.xpath("//a[contains(.,'Signup / Login')]");
    private final By testCasesButton = By.xpath("//ul[@class='nav navbar-nav']//a[contains(.,'Test Cases')]");
    private final By apiButton = By.xpath("//a[contains(.,'API Testing')]");
    private final By videoTutorialsButton = By.xpath("//a[contains(.,'Video Tutorials')]");
    private final By contactUsButton = By.xpath("//a[contains(.,'Contact us')]");
    private final By logoutButton = By.cssSelector("[href='/logout']");
    private final By deleteAccountButton = By.cssSelector("[href='/delete_account']");
    private final By homePageLabel = By.xpath("//div[@id='slider-carousel']//div[@class='item active']//span[.='Automation']");
    private final By userLabel = By.xpath("//a/b");

    /* ******************************** CONSTRUCTORS ******************************* */

    public NavigationBarComponent(GuiDriver driver) {
        this.driver = driver;
    }

    /* ********************************** ACTIONS ********************************** */

    @Step("Navigate to Home Page")
    public NavigationBarComponent navigate() {
        driver.browser().navigateTo(PropertyReader.getProperty("BaseUrl_Web"));
        return this;
    }

    @Step("Navigate to Home Page")
    public NavigationBarComponent goToHomePage() {
        driver.element().click(homeButton);
        return this;
    }

    @Step("Navigate to Products page")
    public ProductsPage goToProductsPage() {
        driver.element().click(productsButton);
        return new ProductsPage(driver);
    }

    @Step("Navigate to Cart page")
    public CartPage goToCartPage() {
        driver.element().click(cartButton);
        return new CartPage(driver);
    }

    @Step("Navigate to Signup / Login page")
    public SignupLoginPage goToSignupLoginPage() {
        driver.element().click(signupLoginButton);
        return new SignupLoginPage(driver);
    }

    @Step("Navigate to Test Cases page")
    public TestCasesPage goToTestCasesPage() {
        driver.element().click(testCasesButton);
        return new TestCasesPage(driver);
    }

    @Step("Navigate to Video Tutorials page")
    public VideoTutorialsPage goToVideoTutorialsPage() {
        driver.element().click(videoTutorialsButton);
        return new VideoTutorialsPage(driver);
    }

    @Step("Navigate to Contact Us Page")
    public ContactUsPage goToContactUsPage() {
        driver.element().click(contactUsButton);
        return new ContactUsPage(driver);
    }

    @Step("Click on Logout Button")
    public LogoutPage clickOnLogoutButton() {
        driver.element().click(logoutButton);
        return new LogoutPage(driver);
    }

    @Step("Click on Delete Account Button")
    public DeleteAccountPage clickOnDeleteAccountButton() {
        driver.element().click(deleteAccountButton);
        return new DeleteAccountPage(driver);
    }

    /* ******************************** VALIDATIONS ******************************** */

    @Step("Verify Home Page")
    public NavigationBarComponent verifyHomePage() {
        driver.verification().isElementVisible(homePageLabel);
        return this;
    }

    @Step("Verify User Label")
    public NavigationBarComponent verifyUserLabel(String expectedName) {
        String actualName = driver.element().getText(userLabel);
        LogsManager.info("Verifying user label: " + actualName);
        driver.verification().Equals(expectedName, actualName, "User label does not match. Expected: " + expectedName + ", Actual: " + actualName);
        return this;
    }
}
