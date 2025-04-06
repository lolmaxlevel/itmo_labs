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

        private val fiveStarFilter = "//div[@data-qa-type='filter-stars']/label[1]/span/span/input"
        private val barFilter = "//div[@data-qa-type='filter-hotel_entertainments']/label[1]/span/span/input"

        private val hotelRating = "//div[@data-qa-type='Rating']"

        private val hotelCardLink = "//span[@data-qa-type='SelectButton.content'][1]"

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

        fun getActualResultsCount(): Int {
            try {
                // Wait for the counter to be visible
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@data-qa-file='ResultCounter']")))

                // Get the text from the counter element
                val resultCounterElement = driver.findElement(By.xpath("//span[@data-qa-file='ResultCounter']"))
                val text = resultCounterElement.text

                // Extract the number using regex (matches first group of digits)
                val numberRegex = Regex("(\\d+[\\s\\d]*)")
                val matchResult = numberRegex.find(text)

                return matchResult?.value?.replace("\\s".toRegex(), "")?.toInt() ?: 0
            } catch (e: Exception) {
                println("Failed to get actual result count: ${e.message}")
                return 0
            }
        }

        fun isEveryFiveStarHotel(): Boolean {
            val items = driver.findElements(By.xpath(hotelRating))

            for (item in items) {
                val stars = item.findElements(By.xpath(".//span[@data-qa-type='uikit/icon']"))
                if (stars.size != 5) {
                    return false
                }
            }
            return true
        }

        fun isRedirected(): Boolean {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(hotelRating)))
            val hotelCardElement = driver.findElement(By.xpath(hotelCardLink))
            val originalUrl = driver.currentUrl
            hotelCardElement.click()
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Удобства отеля')]")))
            val newUrl = driver.currentUrl
            return originalUrl != newUrl
        }

        fun clickHotelCard(): HotelPage {
            // Store the original window handle
            val originalWindow = driver.windowHandle

            // Scroll to top of page
            val js = driver as org.openqa.selenium.JavascriptExecutor
            js.executeScript("window.scrollTo(0, 0);")

            // Wait for the hotel card to be clickable
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(hotelCardLink)))
            clickElement(hotelCardLink)

            // Wait for the new window/tab to appear
            wait.until { driver.windowHandles.size > 1 }

            // Switch to the new window/tab
            for (windowHandle in driver.windowHandles) {
                if (windowHandle != originalWindow) {
                    driver.switchTo().window(windowHandle)
                    break
                }
            }

            // Return the HotelPage for the new window
            return HotelPage(driver)
        }

        fun clickFiveStarFilter(): HotelsSearchResultsPage {
            clickElement(fiveStarFilter)
            return this
        }

        fun clickBarFilter(): HotelsSearchResultsPage {
            clickElement(barFilter)
            return this
        }
    }

    class HotelPage(driver: WebDriver) : BasePage(driver) {
        private val hotelTitle = "//h1[@data-qa-type='Title']"
        private val hotelFacilities = "//div[@data-qa-type='TopFacilitiesPanel']"
        private val barFacility = "//li[@data-qa-type='topFacilitiesItem']//span[contains(text(), 'Бар')]"

        fun isPageLoaded(): Boolean {
            return isElementDisplayed(hotelTitle) && isElementDisplayed(hotelFacilities)
        }

        fun isBarFacilityPresent(): Boolean {
            return try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(barFacility)))
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}