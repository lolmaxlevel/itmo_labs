package org.lolmaxlevel.driver

import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import java.util.concurrent.Semaphore

enum class BrowserType {
    CHROME,
    FIREFOX
}

object WebDriverFactory {
    private val driverThreadLocal = ThreadLocal<WebDriver>()
    private val browserThreadLocal = ThreadLocal<BrowserType>()

    // Create a semaphore for each browser type
    private val browserSemaphores = mapOf(
        BrowserType.CHROME to Semaphore(1),
        BrowserType.FIREFOX to Semaphore(1)
    )

    fun getDriver(browserType: BrowserType): WebDriver {
        if (driverThreadLocal.get() == null) {
            // Acquire semaphore for this browser type
            browserSemaphores[browserType]?.acquire()

            try {
                val driver = createDriver(browserType)
                driverThreadLocal.set(driver)
                browserThreadLocal.set(browserType)
            } catch (e: Exception) {
                // Release semaphore if driver creation fails
                browserSemaphores[browserType]?.release()
                throw e
            }
        }
        return driverThreadLocal.get()
    }

    private fun createDriver(browserType: BrowserType): WebDriver {
        return when (browserType) {
            BrowserType.CHROME -> {
                WebDriverManager.chromedriver().setup()
                val options = ChromeOptions()
                options.addArguments("--start-maximized")
                options.addArguments("--disable-notifications")
                options.addArguments("--headless=new")
                ChromeDriver(options)
            }
            BrowserType.FIREFOX -> {
                WebDriverManager.firefoxdriver().browserInDocker()
                val options = FirefoxOptions()
                options.addArguments("-headless")
                FirefoxDriver(options)
            }
        }
    }

    fun quitDriver() {
        driverThreadLocal.get()?.quit()

        // Release the semaphore for this browser type
        browserThreadLocal.get()?.let { browserType ->
            browserSemaphores[browserType]?.release()
        }

        driverThreadLocal.remove()
        browserThreadLocal.remove()
    }
}