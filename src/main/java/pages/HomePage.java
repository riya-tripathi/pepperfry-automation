package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void openWebsite() {
        driver.get("https://www.pepperfry.com");
    }

    public void handlePopup() {

        try {
            // Click close button
            wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[contains(@class,'modal')]//button")
            )).click();

            System.out.println("Popup closed");

        } catch (Exception e) {
            System.out.println("No popup button");
        }

        // 🔥 REMOVE OVERLAY (THIS FIXES YOUR ERROR)
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('.modal-container, .modal-background').forEach(e => e.remove());"
        );
    }

    public void clickFirstCategory() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//a[contains(@href,'/category/')])[1]")
        )).click();

        System.out.println("Category clicked");
    }



}