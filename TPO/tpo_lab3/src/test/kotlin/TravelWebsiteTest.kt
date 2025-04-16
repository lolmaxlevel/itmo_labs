package org.lolmaxlevel.tests

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.lolmaxlevel.driver.BrowserType
import org.lolmaxlevel.driver.WebDriverFactory
import org.lolmaxlevel.pages.AirlinesPage
import org.lolmaxlevel.pages.HomePage
import org.lolmaxlevel.pages.HotelsPage import org.lolmaxlevel.pages.TrainPage
import org.openqa.selenium.WebDriver
import java.time.LocalDate
import java.time.format.DateTimeFormatter


class TravelWebsiteTest {
    private var driver: WebDriver? = null

    @AfterEach
    fun tearDown() {
        WebDriverFactory.quitDriver()
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
    @EnumSource
    fun testSearchWithoutFromAndTo(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val resultsPage = homePage
            .clickHotelsSwitch()
            .clickSearchButtonNoRedirect()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")
        assertTrue(resultsPage.isMissingInputField(), "red funny elements should be displayed")
    }

    @ParameterizedTest
    @EnumSource
    fun testSearchWithRedirect(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")
        homePage
//            .clickHotelsSwitch()
            .enterDestination("Санкт-Петербург", "Москва")
            .enterDates(
                startDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("ddMMyyyy")),
                endDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("ddMMyyyy"))
            )
            .clickSearchButton()

        assertTrue(driver!!.windowHandles.size > 1, "New window should be opened")
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
    fun testHotelFilterFunctionality(browserType: BrowserType) {
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

        val notFiltered = resultsPage.getActualResultsCount()

        val filtered = resultsPage
            .clickFiveStarFilter().getActualResultsCount()

        assertTrue(filtered > 10, "Five star filter should return more than 10 results")
        assertTrue(notFiltered > filtered, "Five star filter should return less results")
        assertTrue(resultsPage.isEveryFiveStarHotel(), "All hotels should be five star")
    }

    @ParameterizedTest
    @EnumSource
    fun testRedirectOnHotelClick(browserType: BrowserType) {
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
        assertTrue(resultsPage.isRedirected())
    }

    @ParameterizedTest
    @EnumSource
    fun testBarFilter(browserType: BrowserType) {
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

        resultsPage.clickBarFilter()
        val hotelPage = resultsPage.clickHotelCard()

        assertTrue(hotelPage.isPageLoaded(), "Hotel page should be loaded")
        assertTrue(hotelPage.isBarFacilityPresent(), "Hotel should have bar facility")
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

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testPopularDirections(browserType: BrowserType){
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val airlinesPage = homePage.selectTravelType(1) as AirlinesPage
        assertTrue(airlinesPage.isPageLoaded(), "Airlines page should be loaded")

        val firstDirection = airlinesPage.getFirstPopularDirectionText()
        val resultsPage = airlinesPage
            .clickFirstPopularDirection()
        assertTrue(resultsPage.checkDirections(firstDirection[0], firstDirection[2]), "Directions should be correct")
    }

    @ParameterizedTest
    @EnumSource(BrowserType::class)
    fun testAirlinesList(browserType: BrowserType) {
        driver = WebDriverFactory.getDriver(browserType)
        val homePage = HomePage(driver!!)

        homePage.open()
        assertTrue(homePage.isPageLoaded(), "Home page should be loaded")

        val airlinesPage = homePage.selectTravelType(1) as AirlinesPage
        assertTrue(airlinesPage.isPageLoaded(), "Airlines page should be loaded")

        val secondAirline = airlinesPage.getSecondAirline()

        val resultsPage = airlinesPage
            .clickSecondAirline()

        assertTrue(resultsPage.isAirlineNameDisplayed(secondAirline), "Airline name should be displayed")
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