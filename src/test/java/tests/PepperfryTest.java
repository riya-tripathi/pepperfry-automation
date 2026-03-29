package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.annotations.Test;
import org.testng.Assert;

import pages.HomePage;
import pages.ProductPage;

public class PepperfryTest {

    WebDriver driver;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testRun() {
        System.out.println("Running...");
    }

    @Test
    public void openSiteTest() throws InterruptedException {

        HomePage home = new HomePage(driver);
        ProductPage product = new ProductPage(driver);

        home.openWebsite();
        home.handlePopup();

        home.clickFirstCategory();
        product.clickSubCategory();
        product.applyFilter();

        product.clickFirstProduct();
        product.switchToNewTab();

        String name = product.getProductName();

        product.addToCart();

        // 🔥 NEW STEPS
        product.openCart();

        Assert.assertTrue(product.isProductPresentInCart(name), "Product not in cart");

        product.proceedToCheckout();

        System.out.println("TEST PASSED 🎉");
    }

    @AfterTest
    public void teardown() {
        driver.quit();
    }
}