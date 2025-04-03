// ToursPage.kt
package org.lolmaxlevel.pages

import org.openqa.selenium.WebDriver

class ToursPage(driver: WebDriver) : BasePage(driver) {
    private val headerLogo = "//img[@alt='Т-Банк']"
    private val toursText = "//div[contains(text(), 'Туры с кэшбэком')]"

    private val fromField = "//input[@placeholder='Откуда']"
    private val toField = "//input[@placeholder='Куда']"
    private val datesField = "//div[@class='travelsearchform__xOjrdh'][.//span[@data-qa-type='uikit/inputBox.label']]"
    private val searchButton = "//span[@class='travelsearchform__fCnYdD']"

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(headerLogo) && isElementDisplayed(toursText)
    }

    fun enterDestination(from: String, to: String): ToursPage {
        enterText(fromField, from)
        enterText(toField, to)
        return this
    }

    fun enterDates(startDate: String, endDate: String): ToursPage {
        clickElement(datesField)
        val js = driver as org.openqa.selenium.JavascriptExecutor
        js.executeScript("""
            document.querySelector('input[placeholder*="Туда"]').value = "$startDate";
            document.querySelector('input[placeholder*="Обратно"]').value = "$endDate";
        """)
        clickElement("//body")
        return this
    }

    fun clickSearchButton(): SearchResultsPage {
        clickElement(searchButton)
        return SearchResultsPage(driver)
    }
}