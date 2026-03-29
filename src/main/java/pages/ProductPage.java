package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductPage {

    WebDriver driver;
    WebDriverWait wait;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // 🔥 STEP 1: Click Sub Category
    public void clickSubCategory() {

        Actions actions = new Actions(driver);

        WebElement menu = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[contains(text(),'Sofas & Seating')]")
        ));

        actions.moveToElement(menu).perform();

        WebElement subCategory = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'3 Seater Sofas')]")
        ));

        subCategory.click();

        System.out.println("Sub-category clicked");
    }

    // 🔥 STEP 2: Apply Filter
    public void applyFilter() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0, 800)");

        WebElement filter = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//input[@type='checkbox'])[1]")
        ));

        js.executeScript("arguments[0].scrollIntoView(true);", filter);
        js.executeScript("arguments[0].click();", filter);

        System.out.println("Filter applied ✅");
    }

    // 🔥 STEP 3: Click First Product
    public void clickFirstProduct() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollTo(0, 1500)");

        WebElement product = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("(//a[contains(@href,'/product/')])[1]")
        ));

        js.executeScript("arguments[0].scrollIntoView(true);", product);
        js.executeScript("arguments[0].click();", product);

        System.out.println("Product clicked 🔥");
    }

    // 🔥 STEP 4: Switch to New Tab
    public void switchToNewTab() {

        for (String window : driver.getWindowHandles()) {
            driver.switchTo().window(window);
        }

        System.out.println("Switched to product tab");
    }

    // 🔥 STEP 5: Get Product Name
    public String getProductName() {

        WebElement name = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1")
        ));

        String productName = name.getText();

        System.out.println("Product Name: " + productName);

        return productName;
    }

    public void selectVariantIfPresent() {

        try {
            WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("(//button | //div)[contains(@class,'variant') or contains(@class,'option')][1]")
            ));

            option.click();

            System.out.println("Variant selected ✅");

        } catch (Exception e) {
            System.out.println("No variant required");
        }
    }

    // 🔥 STEP 6: Add to Cart
    public void addToCart() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 🔥 Step 1: Scroll slowly (important for lazy loading)
        for (int i = 0; i < 3; i++) {
            js.executeScript("window.scrollBy(0, 500)");
        }

        // 🔥 Step 2: Try MULTIPLE locators (VERY IMPORTANT)
        WebElement cartBtn = null;

        try {
            cartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//button[contains(.,'Cart') or contains(.,'Buy')]")
            ));
        } catch (Exception e1) {
            try {
                cartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[contains(@class,'cart') or contains(@class,'buy')]")
                ));
            } catch (Exception e2) {
                cartBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//button | //a")
                ));
            }
        }

        // 🔥 Step 3: Bring into view
        js.executeScript("arguments[0].scrollIntoView(true);", cartBtn);

        // 🔥 Step 4: REAL click (IMPORTANT)
        try {
            cartBtn.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", cartBtn);
        }

        System.out.println("Clicked Add to Cart 🛒");

        // 🔥 Step 5: Wait for UI change
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    public void openCart() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 🔥 Step 1: Scroll to TOP (VERY IMPORTANT)
        js.executeScript("window.scrollTo(0, 0)");

        // 🔥 Step 2: Wait for cart icon at top
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(@href,'cart') or contains(@class,'cart')]")
        ));

        // 🔥 Step 3: Click cart
        cartIcon.click();

        System.out.println("Cart opened 🛒");
    }

    public boolean isProductPresentInCart(String productName) {

        try {
            WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//*[contains(text(),'" + productName + "')]")
            ));

            System.out.println("Product ACTUALLY found in cart ✅");

            return true;

        } catch (Exception e) {
            System.out.println("Product NOT found in cart ❌");
            return false;
        }
    }

    public void proceedToCheckout() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 🔥 Scroll a bit (checkout usually lower)
        js.executeScript("window.scrollBy(0, 500)");

        // 🔥 Flexible locator (VERY IMPORTANT)
        WebElement checkoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(text(),'Checkout') or contains(text(),'Proceed') or contains(text(),'Place') or contains(text(),'Buy')]")
        ));

        // 🔥 Bring into view
        js.executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);

        // 🔥 Click safely
        js.executeScript("arguments[0].click();", checkoutBtn);

        System.out.println("Proceeded to checkout 💳");
    }
}