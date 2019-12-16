package com.stocker.spring;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
public class ChartDataClient extends AbstractClient {

    private static final String SERVICE = "STOCKER-CHART";

    public File getCompany(String symbol) {
        log.info(String.format("getting chart for company with symbol %s", symbol));

        WebDriver driver = null;

        try {
            String chromeDriverPath = "/usr/local/bin/chromedriver" ;
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--no-sandbox", "--disable-gpu", "--window-size=1920,1024","--ignore-certificate-errors");
            driver = new ChromeDriver(options);
            driver.get(String.format("%s/company/%s/", getDiscoveryClient().getApplication(SERVICE).getInstances().get(0).getHomePageUrl(), symbol));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshot.png"));
        } catch (IOException e) {
            log.error("Chrome driver could't create screenshot", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return new File("screenshot.png");
    }

    public File getView(String symbol) {
        log.info(String.format("getting view for company with symbol %s", symbol));

        WebDriver driver = null;

        try {
            String chromeDriverPath = "/usr/local/bin/chromedriver" ;
            System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless", "--no-sandbox", "--disable-gpu", "--window-size=400,400","--ignore-certificate-errors");
            driver = new ChromeDriver(options);
            driver.get(String.format("%s/overview/%s/", getDiscoveryClient().getApplication(SERVICE).getInstances().get(0).getHomePageUrl(), symbol));
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File("screenshot.png"));
        } catch (IOException e) {
            log.error("Chrome driver could't create screenshot", e);
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
        return new File("screenshot.png");
    }
}