package org.lolmaxlevel.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class HomePage(driver: WebDriver) : BasePage(driver) {
    private val url = "https://www.tbank.ru/travel/?newmain=false"

    // XPath selectors
    private val headerLogo = "//img[@alt='Т-Банк']"
    private val searchBox = "//div[@class='travelsearchform__a--9kDE']"
    private val newSearchBox = "//div[@data-qa-file='Panel']"

    private val searchFromField = "//input[contains(@aria-labelledby, 'Откуда')]"
    private val searchDestinationField = "//input[@aria-labelledby='Input_Куда']"

    private val searchDatesField =
        "/html/body/div[1]/div/div/div[6]/div/div/form/div[1]/div[1]/div/div/div[3]/div/div[1]/div/div/div[2]/div/div/div/div/div/div/div[2]/div/input"

    private val hotelsSwitch = "//span[@data-qa-type='tui/switch']"

    private val searchButton = "//span[@class='travelsearchform__fCnYdD']"
    private val travelTypeTabs = "//ul[@class='dbYvsSvwF']"

    fun open(): HomePage {
        driver.get(url)
        return this
    }

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(headerLogo)
                && (isElementDisplayed(searchBox) || isElementDisplayed(newSearchBox))
    }

    fun isNewSearchBox(): Boolean {
        return isElementDisplayed(newSearchBox)
    }

    fun enterDestination(from: String, to: String): HomePage {
        // Enter departure city
        enterText(searchFromField, from)
        // Wait for dropdown to appear with explicit wait
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(text(), 'LED')]")
            )
        )
        sendEnter(searchFromField)

        // Enter destination city
        enterText(searchDestinationField, to)
        // Wait for dropdown to appear with explicit wait
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//label[contains(text(), 'MOW')]")
            )
        )
        sendEnter(searchDestinationField)
        return this
    }

    fun enterDates(startDate: String, endDate: String): HomePage {
        enterText(searchDatesField, startDate + endDate)
        return this
    }

    fun clickHotelsSwitch(): HomePage {
        clickElement(hotelsSwitch)
        return this
    }

    fun clickSearchButton(): SearchResultsPage {
        clickElement(searchButton)
        return SearchResultsPage(driver)
    }

    fun selectTravelType(index: Int): BasePage {
        val indexMap = mapOf(2 to 2, 4 to 3)
        if (isElementDisplayed(travelTypeTabs)) {
            clickElement("$travelTypeTabs/li[${index}]")
        } else {
            clickElement("//div[count(div)=6]/div[${indexMap[index]}]/div[1]/div/div[1]/a[1]")
        }
        return when (index) {
            2 -> HotelsPage(driver)    // Hotels
            4 -> TrainPage(driver)     // Railway
            5 -> ToursPage(driver)     // Tours
            else -> HomePage(driver)   // Default to HomePage
        }
    }
}

class SearchResultsPage(driver: WebDriver) : BasePage(driver) {
    // XPath selectors
    private val resultsList =
        "/html/body/div[1]/div/div/div[3]/div[3]/div[2]/div[4]/div/div[2]/div/div/div/div/div/div/div/div"
    private val filterPanel = "//div[contains(@class, 'filter-panel')]"
    private val loading = "//div[contains(text(), 'Ищем лучшие предложения')]"

    fun isPageLoaded(): Boolean {
        longWait.until(
            ExpectedConditions.visibilityOfElementLocated(By.xpath(resultsList))
        )
        return isElementDisplayed(resultsList)
    }

    fun getResultsCount(): Int {
        longWait.until(
            ExpectedConditions.invisibilityOfElementLocated(By.xpath(loading))
        ) // Wait for loading to disappear
        val items = driver.findElements(By.xpath(resultsList))
        return items.size
    }

    fun isFilterPanelDisplayed(): Boolean {
        return isElementDisplayed(filterPanel)
    }
}