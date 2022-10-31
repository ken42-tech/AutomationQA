package com.w2l;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.reporters.jq.Main;

import io.github.bonigarcia.wdm.WebDriverManager;

import com.opencsv.CSVReader;

import java.util.logging.*;
import java.util.logging.FileHandler;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class w2l_portal {

	private static final Exception Exception = null;
	private static WebDriver driver;
	static int time = 1000;
	public static Logger log = Logger.getLogger("w2l_portal");

	public static void main(String[] args) throws Throwable {
		String CSV_PATH = "";
		String logFileName = "";
		boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());

		if (Utils.checkWindowsOs()) {
			CSV_PATH = "C:\\Users\\Public\\Documents\\datareadertext.csv";
			logFileName = String.format("C:\\Users\\Public\\Documents\\Testresult_%s.HTML", timeStamp);
		} else {
			CSV_PATH = "/Users/Shared/pfs.csv";
			logFileName = String.format("/users/Shared/Testresult_%s.HTML", timeStamp);
		}

		log.info("Found CSV file");
		FileHandler logFile = new FileHandler(logFileName, append);
		logFile.setFormatter(new MyHtmlFormatter());
		log.addHandler(logFile);

		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));

		String[] csvCell;
		while ((csvCell = csvReader.readNext()) != null) {

			if (count == 0) {
				count = count + 1;
				continue;
			}
			String w2l_url = csvCell[0];
			String app_url = csvCell[1];
			String Browser = csvCell[2];
			String From = csvCell[3];
			String To = csvCell[4];
			String sal_url = csvCell[40];

			log.info("URL is " + w2l_url);

			int from = Integer.parseInt(From);
			int to = Integer.parseInt(To);

			initDriver1(Browser, w2l_url);

			log.info("**********************Testing for  Portal  " + w2l_url);
			// Below If will execute all Student related test cases
			for (int i = from; i <= to; i++) {
				switch (i) {
				case 1:
					fillform_W2l(w2l_url, driver, csvCell); // TC-1
					break;
				case 2:
					Apploginuploadandsubmit(app_url, driver, csvCell);
					break;
				case 3:
					salesforce_applicationviewanddelete(sal_url, driver, csvCell);
					break;
				case 4:
					Apploginwithinvalidnumber(app_url, driver, csvCell);
					break;
				case 5:
					Application_fillandfilesizecheck(app_url, driver, csvCell);
					break;
				case 6:
					salesforce_loginviewdelete(sal_url, driver, csvCell);
					break;
				case 7:
					salesforce_applicationview(sal_url, driver, csvCell);
					break;

				default:

					throw Exception;
				}
			}
			// quitDriver(PFSurl);
			//
			log.info("***************** COMPLETED TESTTING OF PORTAL" + w2l_url);
		}
		SendMail.sendEmail(logFileName);
	}

	@BeforeMethod
	public static void initDriver1(String Browser, String url) throws Exception {
		String ChromeDriver = "";
		String EdgeDriver = "";
		String FirefoxDriver = "";
		if (Utils.checkWindowsOs()) {
			ChromeDriver = "C:\\Users\\Public\\Documents\\chromedriver.exe";
			EdgeDriver = "C:\\Users\\Public\\Documents\\msedgedriver.exe";
			FirefoxDriver = "C:\\Users\\Public\\Documents\\geckodriver.exe";
		} else {
			ChromeDriver = "Users/shared/chromedriver.exe";
			EdgeDriver = "Users/shared/msedgedriver.exe";
			FirefoxDriver = "Users/shared/geckodriver.exe";
			// url="https://ltpct-reg-stg-w2l.ken42.com/form";
		}

		System.out.println("Browser is " + Browser);
		System.out.println("URL is " + url);
		try {
			System.out.println("Browser is ****" + Browser);
			System.out.println("URL is " + url);
			if ("chrome".equals(Browser)) {
				System.setProperty("webdriver.chrome.driver", ChromeDriver);
				ChromeOptions op = new ChromeOptions();
				op.addArguments("--disable-notifications");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(op);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.manage().window().maximize();

			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", EdgeDriver);
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", FirefoxDriver);
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("safari".equals(Browser)) {
				driver = new SafariDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				driver.get(url);
				driver.manage().window().maximize();

			}
			driver.get(url);
		} catch (Exception e) {
			log.warning("UNABLE TO LAUNCH BROWSER \n\n\n");
			System.exit(01);
		}
	}

	@Test(priority = 1)
	public static void fillform_W2l(String url, WebDriver Driver, String[] csvCell) throws Throwable, Throwable {

		String web_url = csvCell[0];
		Driver.get(web_url);
		Utils.basic_info(Driver, csvCell, url);

		Utils.address_info(Driver, csvCell, url);

		Utils.other_info(driver, csvCell, web_url);

		Utils.submit_info(driver, csvCell, web_url);

		log.info("***************** COMPLETED TESTTING OF PORTAL" + url);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(1));

	}
	// SendMail.sendEmail(logFileName);

	@Test(priority = 2)
	public static void Apploginuploadandsubmit(String app_url, WebDriver driver, String[] csvCell) throws Throwable {

		String url = csvCell[1];
		driver.get(url);

		Utils.login(app_url, driver, csvCell);
		Utils.Appbasic_info(app_url, driver, csvCell);
		Utils.family_info(app_url, driver, csvCell);
		Utils.address_info(app_url, driver, csvCell);
		Utils.qualification_info(app_url, driver, csvCell);
		Utils.Multipleuploadfile_info(app_url, driver, csvCell);
		Utils.checkbox(app_url, driver, csvCell);
		Utils.submit_app(app_url, driver, csvCell);

		Utils.signout_app(app_url, driver, csvCell);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(2));

	}

	@Test(priority = 3)
	public static void salesforce_applicationviewanddelete(String sal_url, WebDriver driver, String[] csvCell)
			throws java.lang.Exception

	{
		String url = csvCell[40];
		driver.get(url);
		Utils.salesforce_login(sal_url, driver, csvCell);
		Utils.clickonApplicationandselect(sal_url, driver, csvCell);

		Utils.profile_logout(sal_url, driver, csvCell);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(3));

	}

	@Test(priority = 4)
	public static void Apploginwithinvalidnumber(String app_url, WebDriver driver2, String[] csvCell) throws Throwable

	{

		String url = csvCell[1];
		driver2.get(url);
		Utils.callSendkeys(driver, ActionXpath.addphone, "9080504030");
		Utils.clickXpath(driver, ActionXpath.getotp, "getotp");
		Thread.sleep(3000);
		WebElement getalert = driver.findElement(By.xpath("//div[@role='alert']"));
		String message = getalert.getText();

		System.out.println(message);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(4));

	}

	@Test(priority = 5)
	public static void Application_fillandfilesizecheck(String app_url, WebDriver driver, String[] csvCell)
			throws Throwable

	{
		String url = csvCell[1];
		driver.get(url);

		Utils.login(app_url, driver, csvCell);
		Utils.Appbasic_info(app_url, driver, csvCell);
		Utils.family_info(app_url, driver, csvCell);
		Utils.address_info(app_url, driver, csvCell);
		Utils.qualification_info(app_url, driver, csvCell);
		Utils.uploadfile_info(app_url, driver, csvCell);
		Utils.signout_app(app_url, driver, csvCell);

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab.get(5));

		Utils.salesforce_applicationviewanddelete(url, driver, csvCell);

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tab1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tab1.get(6));

	}

	@Test(priority = 6)
	public static void salesforce_loginviewdelete(String sal_url, WebDriver driver2, String[] csvCell)
			throws java.lang.Exception

	{
		String url = csvCell[40];
		driver.get(url);
		Utils.salesforce_login(sal_url, driver2, csvCell);
		Utils.clickonleadandselect(sal_url, driver2, csvCell);
		Utils.markasStatus(sal_url, driver2, csvCell);
		Utils.selectsaveanddelete(sal_url, driver2, csvCell);
		Utils.profile_logout(sal_url, driver2, csvCell);
		driver.quit();
	}

	@Test(priority = 7)
	public static void salesforce_applicationview(String sal_url, WebDriver driver, String[] csvCell)
			throws java.lang.Exception

	{
		String url = csvCell[40];
		driver.get(url);
		Utils.salesforce_login(sal_url, driver, csvCell);
		Utils.clickonApplicationview(sal_url, driver, csvCell);

		Utils.profile_logout(sal_url, driver, csvCell);
//		((JavascriptExecutor) driver).executeScript("window.open()");
//		ArrayList<String> tab = new ArrayList<String>(driver.getWindowHandles());
//		driver.switchTo().window(tab.get(3));

	}

	@AfterMethod
	public static void quitDriver(String Url) throws Exception {
		log.info("Close window  of portal" + Url);
		log.info("\n");
		driver.quit();
	}
}