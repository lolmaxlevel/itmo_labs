package org.lolmaxlevel.tests

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.lolmaxlevel.driver.BrowserType
import org.lolmaxlevel.driver.WebDriverFactory
import org.lolmaxlevel.pages.HomePage
import org.lolmaxlevel.pages.HotelsPage
import org.lolmaxlevel.pages.ToursPage
import org.lolmaxlevel.pages.TrainPage
import org.openqa.selenium.WebDriver
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TravelWebsiteTest {
    private var driver: WebDriver? = null

    @AfterEach
    fun tearDown() {
        driver?.quit()
    }

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testHomePageLoads(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")
    }

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testSearchFunctionality(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()

        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val resultsPage = homePage
            .clickHotelsSwitch()
            .enterDestination("Санкт-Петербург", "Москва")
            .enterDates(
                startDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                endDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("ddMMyyyy"))
            )
            .clickSearchButton()

        assertTrue(resultsPage.isPageLoaded(), "Search results page should be loaded")
        assertTrue(resultsPage.getResultsCount() > 4, "Search results should be more than 4")
    }

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testSearchFunctionalityWithWrongDestination(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val resultsPage = homePage
            .clickHotelsSwitch()
            .enterDestination("LED", "MFA")
            .enterDates(
                startDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                endDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("ddMMyyyy"))
            )
            .clickSearchButton()

        assertTrue(resultsPage.isPageLoaded(), "Search results page should be loaded")
        assertTrue(resultsPage.getResultsCount() == 0, "Search results should be more than 4")
    }


    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testHotelsSearchFunctionality(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val hotelsPage = homePage.selectTravelType(2) as HotelsPage

        assertTrue(hotelsPage.isPageLoaded(), "Hotels page should be loaded")

        val resultsPage = hotelsPage
            .enterDestination("Москва")
            .enterDates(
                startDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                endDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            )
            .clickSearchButton()

        assertTrue(resultsPage.isPageLoaded(), "Hotels search results page should be loaded")
        assertTrue(resultsPage.getResultsCount() > 0, "Hotel search should return results")
    }

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testRailwayTicketsSearchFunctionality(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val trainPage = homePage.selectTravelType(4) as TrainPage
        assertTrue(trainPage.isPageLoaded(), "Train page should be loaded")

        val resultsPage = trainPage
            .enterDestination()
            .searchTrain()
        assertTrue(resultsPage.isPageLoaded(), "Railway tickets search results page should be loaded")
        assertTrue(resultsPage.getResultsCount() > 0, "Railway ticket search should return results")
    }

//    @ParameterizedTest
//    @EnumSource(BrowserType::class)
//    fun testToursSearchFunctionality(browserType: BrowserType) {
//        driver = WebDriverFactory.getDriver(browserType)
//        val homePage = HomePage(driver!!)
//
//        homePage.open()
//        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")
//
//        val toursPage = homePage.selectTravelType(4) as ToursPage
//        assertTrue(toursPage.isPageLoaded(), "Tours page should be loaded")
//
//        val resultsPage = toursPage
//            .enterDestination("Москва", "Турция")
//            .enterDates(
//                startDate = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("ddMMyyyy")),
//                endDate = LocalDate.now().plusDays(37).format(DateTimeFormatter.ofPattern("ddMMyyyy"))
//            )
//            .clickSearchButton()
//
//        assertTrue(resultsPage.isPageLoaded(), "Tours search results page should be loaded")
//        assertTrue(resultsPage.getResultsCount() > 0, "Tour search should return results")
//    }
}