package org.lolmaxlevel.driver

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

enum class BrowserType {
    CHROME,
//    FIREFOX
}

class WebDriverFactory {
    companion object {
        fun getDriver(browserType: BrowserType): WebDriver {
            return when (browserType) {
                BrowserType.CHROME -> {
                    WebDriverManager.chromedriver().browserInDocker()
                    val options = ChromeOptions()
                    options.addArguments("disable-notifications")
                    ChromeDriver(options)
                }
//                BrowserType.FIREFOX -> {
//                    WebDriverManager.firefoxdriver().browserInDocker()
//                    val options = FirefoxOptions()
//                    FirefoxDriver(options)
//                }
            }
        }
    }
}