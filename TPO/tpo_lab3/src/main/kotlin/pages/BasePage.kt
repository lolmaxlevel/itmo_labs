package org.lolmaxlevel.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class BasePage(protected val driver: WebDriver) {
    val wait = WebDriverWait(driver, Duration.ofSeconds(10))
    val longWait = WebDriverWait(driver, Duration.ofSeconds(60))

    private fun findElementByXPath(xpath: String): WebElement {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
    }

    protected fun clickElement(xpath: String) {
        val element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
        element.click()
    }

    protected fun enterText(xpath: String, text: String) {
        val element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))

        try {
            // Try the regular clear first
            element.clear()
        } catch (e: Exception) {
            // If clear fails, use JavaScript or alternative approach
            val js = driver as org.openqa.selenium.JavascriptExecutor
            js.executeScript("arguments[0].value = '';", element)

            // Alternative: select all text and delete
             element.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE)
        }

        element.sendKeys(text)
    }

    protected fun sendEnter(xpath: String) {
        val element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
        element.sendKeys(Keys.ENTER)
    }

    protected fun sendBackspace(xpath: String) {
        val element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)))
        element.sendKeys(Keys.DELETE)
    }

    protected fun isElementDisplayed(xpath: String): Boolean {
        return try {
            findElementByXPath(xpath).isDisplayed
        } catch (e: Exception) {
            false
        }
    }
}