package org.lolmaxlevel.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class AirlinesPage(driver: WebDriver) : BasePage(driver) {
    private val logo = "//img[@alt='Т-Банк']"
    private val popularDestinations = "//h2[contains(text(), 'Популярные направления')]"
    private val firstDirection = "//div[@data-qa-type='routesWidgetName'][1]/a"

    private val fromField = "//input[@aria-labelledby='Input_Откуда']"
    private val toField = "//input[@aria-labelledby='Input_Куда']"

    private val airlinesSection = "//section[@data-qa-file='AirlinesWidget']"

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(logo) && isElementDisplayed(popularDestinations)
    }

    fun clickFirstPopularDirection(): AirlinesPage {
        clickElement(firstDirection)
        return this
    }

    fun getFirstPopularDirectionText(): List<String> {
        return driver.findElement(By.xpath(firstDirection)).text.split(" ")
    }

    fun checkDirections(from: String, to: String): Boolean {
        // Wait for the elements to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fromField)))
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(toField)))
        return driver.findElement(By.xpath(fromField)).getDomAttribute("value")?.contains(from) ?: false
                && driver.findElement(By.xpath(toField)).getDomAttribute("value")?.contains(to) ?: false
    }

    fun getSecondAirline(): String {
        // Wait for the airlines section to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(airlinesSection)))
        return driver.findElement(By.xpath("//section[@data-qa-file='AirlinesWidget']/a[2]")).text
    }

    fun clickSecondAirline(): AirlinesPage {
        // Wait for the airlines section to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(airlinesSection)))
        clickElement("//section[@data-qa-file='AirlinesWidget']/a[2]")
        return this
    }

    fun isAirlineNameDisplayed(airlineName: String): Boolean {
        // Wait for the airlines section to be visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[contains(text(), 'Поиск билетов')]")))
        return driver.findElement(By.xpath("//p[contains(text(), ${airlineName})]")).isDisplayed
    }
}