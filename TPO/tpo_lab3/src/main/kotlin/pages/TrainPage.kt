package org.lolmaxlevel.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions

class TrainPage(driver: WebDriver) : BasePage(driver) {

    private val headerLogo = "//img[@data-item-name='logo']"
    private val railwayText = "//span[contains(text(), 'ЖД')]"
    private val mainFrame = "//div[@data-qa-file='RailwaysAppScriptPage']/iframe"
    //    private val fromButton = "//div[contains(text(), 'Москва')][1]"
    private val fromButton = "//div[@class='ott-quick-tags-QuickTags__searchTags']/div[1]/div[1]"

    //    private val destinationButton = "//div[contains(text(), 'Санкт-Петербург')][2]"
    private val destinationButton = "/html/body/div/div[2]/div/div[2]/div/div/div/div[1]/div/div/div/div[2]/div[1]"
    private val searchButton = "//div[contains(text(), 'Найти')]"

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(headerLogo) && isElementDisplayed(railwayText)
    }

    fun enterDestination(): TrainPage {
        Thread.sleep(2000)
        val secondIframe = driver.findElement(By.xpath(mainFrame))
        driver.switchTo().frame(secondIframe)
        clickElement(fromButton)
        clickElement(destinationButton)

        return this
    }

    fun searchTrain(): TrainResultsPage {
        clickElement(searchButton)
        return TrainResultsPage(driver)
    }

    class TrainResultsPage(driver: WebDriver) : BasePage(driver) {
        private val resultsCount = "//span[contains(text(), 'Выбрать')]"
        private val filterButton = "//button[@data-error-type='new']"
        private val skeletonBlock = "//div[starts-with(@class, 'SkeletonResultCard')]"


        fun isPageLoaded(): Boolean {
            return isElementDisplayed(filterButton)
        }

        fun getResultsCount(): Int {
            // Wait for the skeleton block to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(skeletonBlock)))
            // Wait for the results count to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(resultsCount)))
            val countText = driver.findElements(By.xpath(resultsCount))
            return countText.size
        }
    }
}