package com.appTest.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.apache.commons.io.FileUtils;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Base {
    public static AppiumDriver<MobileElement> driver;
    public static JSONObject objTestData;
    public static ExtentReports report;
    public static ExtentTest logger;

    public Base() {
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    @BeforeSuite
    public void beforeSuiteStartReports() {
        report = new ExtentReports("Reports/index.html", true);
        File configFile = new File("/Users/elifalp/IdeaProjects/MobileAppTest/extentReports.xml");
        report.addSystemInfo("Tester", "Elf Elf Elf");
        report.addSystemInfo("Host Name", "Hosta Host");
        report.loadConfig(configFile);
    }

    @AfterSuite
    public void afterSuiteReportsFlush() {
        report.flush();
    }

    @BeforeClass
    public void beforeClassGetTestData() throws FileNotFoundException {
        File fileTestUser = new File("src/main/resources/testUsers.json");
        FileInputStream stream = new FileInputStream(fileTestUser);
        JSONTokener tokener = new JSONTokener(stream);
        objTestData = new JSONObject(tokener);
    }

    @Parameters({"PlatformName", "DeviceName"})
    @BeforeMethod
    public void beforeTestLaunchApp(String platformName, String deviceName, Method method) throws IOException {
        logger = report.startTest(method.getName());
        logger.log(LogStatus.INFO, "INFO INFO");
        File ScreenShot = new File("ScreenShots");


        File fileProps = new File("src/main/resources/config.properties");
        FileInputStream propStream = new FileInputStream(fileProps);
        Properties props = new Properties();
        props.load(propStream);

        URL url = new URL(props.getProperty("url"));

        if (platformName.equalsIgnoreCase("Android")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            caps.setCapability(MobileCapabilityType.APP, props.getProperty("app"));
            driver = new AndroidDriver<MobileElement>(url, caps);
        } else if (platformName.equalsIgnoreCase("iOS")) {
            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
            caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
            caps.setCapability(MobileCapabilityType.APP, props.getProperty("appIOS"));
            caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            driver = new IOSDriver<MobileElement>(url, caps);
        }

        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);
    }

    @AfterMethod
    public void afterMethodReportAndScreenShotActions(ITestResult result) throws IOException {
        File shot = driver.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(shot, new File("Screenshots/screenshot.png"));


        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, result.getName() + "   FAILED --> " + result.getThrowable());
            logger.log(LogStatus.INFO, logger.addScreenCapture("../Screenshots/screenshot.png"));

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(LogStatus.PASS, result.getName() + "  Successfull");
        }
        report.endTest(logger);


    }


}
