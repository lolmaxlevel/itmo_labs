// HotelsPage.kt
package org.lolmaxlevel.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions

class HotelsPage(driver: WebDriver) : BasePage(driver) {
    private val headerLogo = "//img[@alt='Т-Банк']"
    private val hotelsText = "//span[contains(text(), 'Отели с кэшбэком')]"
    private val differentText = "//p[contains(text(), 'Отели с кэшбэком')]"

    private val destinationField = "//input[contains(@type, 'text')]"
    private val destinationHotel = "//div[@data-dropdown-item-index='0_0']"
    private val datesField = "//div[contains(text(), 'Даты поездки')]"

    private val calendar = "//div[@data-qa-file='DaySelector']"

    private val searchButton = "//div[@data-qa-type='searchButton']"

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(headerLogo) && (isElementDisplayed(hotelsText) || isElementDisplayed(differentText))
    }

    fun enterDestination(city: String): HotelsPage {
        enterText(destinationField, city)

        // Use a retry mechanism with refreshed element
        val maxRetries = 3
        for (attempt in 1..maxRetries) {
            try {
                // Wait for dropdown to appear with a fresh reference each time
                wait.until(
                    ExpectedConditions.refreshed(
                        ExpectedConditions.visibilityOfElementLocated(By.xpath(destinationHotel))
                    )
                )

                // Get fresh reference to element right before clicking
                val dropdownItem = driver.findElement(By.xpath(destinationHotel))
                dropdownItem.click()
                break
            } catch (e: Exception) {
                if (attempt == maxRetries) {
                    // Try JavaScript click as last resort
                    val js = driver as org.openqa.selenium.JavascriptExecutor
                    val element = driver.findElement(By.xpath(destinationHotel))
                    js.executeScript("arguments[0].click();", element)
                }
            }
        }

        // Wait for dropdown to disappear
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(destinationHotel)))
        return this
    }

    fun enterDates(startDate: String, endDate: String): HotelsPage {
        // Click to open the calendar
        clickElement(datesField)
        clickElement(datesField)
        // Wait for the calendar to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(calendar)))

        // Use JavaScript to select dates (avoids stale element issues)
        val js = driver as org.openqa.selenium.JavascriptExecutor

        // Select start date
        js.executeScript(
            """
        var dateElements = document.querySelectorAll('span[data-date="$startDate"]');
        if (dateElements.length > 0) {
            dateElements[0].click();
        }
    """
        )


        // Select end date if provided
        if (endDate.isNotEmpty()) {
            js.executeScript(
                """
            var dateElements = document.querySelectorAll('span[data-date="$endDate"]');
            if (dateElements.length > 0) {
                dateElements[0].click();
            }
        """
            )
        }

        return this
    }

    fun clickSearchButton(): HotelsSearchResultsPage {
        clickElement(searchButton)
        return HotelsSearchResultsPage(driver)
    }

    class HotelsSearchResultsPage(driver: WebDriver) : BasePage(driver) {
        private val resultsList = "//span[contains(text(), 'Выбрать')]"
        private val loading = "//div[contains(text(), 'Ищем лучшие предложения')]"

        private val whereNowBlock = "//h6[contains(text(), 'Куда теперь?')]"

        fun isPageLoaded(): Boolean {
            return isElementDisplayed(whereNowBlock)
        }

        fun getResultsCount(): Int {
            Thread.sleep(2000)
            longWait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.xpath(loading))
            )
            val items = driver.findElements(By.xpath(resultsList))
            return items.size
        }
    }
}