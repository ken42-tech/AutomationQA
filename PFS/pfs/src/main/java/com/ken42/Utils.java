package com.ken42;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.*;
import java.util.regex.*;

import javax.mail.Quota.Resource;
import javax.management.relation.Role;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.google.common.base.Function;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.openqa.selenium.Alert;

public class Utils {

	static Logger log = Logger.getLogger(Utils.class.getName());
	static int time = 1000;
	// public static Logger log = Logger.getLogger("Pfs_portal");
	static boolean debug = true;

	public static void clickXpath(WebDriver driver, String xpath, int time, String msg, Logger log)
			throws NoSuchElementException, InterruptedException, ElementClickInterceptedException, Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		int count = 0;
		int maxTries = 12;
		final String XPATH = xpath;
		while (true) {
			try {
				Thread.sleep(1000);
				if (debug)
					log.info("Click on the:" + msg);
				System.out.print("Click on the:" + msg);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(60))
						.pollingEvery(Duration.ofSeconds(6))
						.ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(XPATH));
					}
				});
				WE.click();
				// WebDriverWait wait = new WebDriverWait(driver, 5, 500);
				// WebElement element =
				// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
				// element.click();
				break;
			} catch (NoSuchElementException e) {
				Thread.sleep(2000);
				if (debug)
					log.warning("Failed to Click on the :" + msg);
				System.out.println("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					log.warning("NoSuchElementException is " + e);
					Utils.printException(e);
					throw e;
				}
			} catch (ElementClickInterceptedException e) {
				Thread.sleep(2000);
				if (debug)
					log.warning("Failed to Click on the :" + msg);
				System.out.println("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					log.warning("ElementClickInterceptedException is " + e);
					Utils.printException(e);
					throw e;
				}
			} catch (Exception e) {
				Thread.sleep(2000);
				if (debug)
					log.warning("Failed to Click on the :" + msg);
				System.out.println("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					log.warning("Exception  is " + e);
					Utils.printException(e);
					throw e;
				}
			}
		}

	}

	public static void callSendkeys(WebDriver driver, String Xpath, String Value, int time1, Logger log)
			throws Exception, NoSuchElementException {
		int count = 0;
		int maxTries = 10;
		final String XPATH = Xpath;
		while (true) {
			try {
				if (debug)
					log.info("***********************Entering value   " + Value);
				System.out.print("***********************Entering value   " + Value);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
						.withTimeout(Duration.ofSeconds(40))
						.pollingEvery(Duration.ofSeconds(4))
						.ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
						return driver.findElement(By.xpath(XPATH));
					}
				});
				Thread.sleep(500);
				WE.sendKeys(Value);
				Thread.sleep(500);
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				log.warning("Failed to send value  " + Value);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void clickXpathWithJavascript(WebDriver driver, String xpath, int time, String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		int count = 0;
		int maxTries = 7;
		final String XPATH = xpath;
		while (true) {
			try {
				// Thread.sleep(2000);
				log.info("Click on the:" + msg);
				WebElement webElement = driver.findElement(By.xpath(xpath));
				JavascriptExecutor javaScriptExecutor = (JavascriptExecutor) driver;
				javaScriptExecutor.executeScript("arguments[0].click()", webElement);
				// new WebDriverWait(driver,
				// 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
				log.warning("Failed to Click on the :" + msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void cleartext(WebDriver driver, String Xpath) throws Exception {
		int count = 0;
		int maxTries = 7;
		while (true) {
			try {
				driver.findElement(By.xpath(Xpath)).clear();
				Thread.sleep(0);
				break;
			} catch (Exception e) {
				Utils.smallSleepBetweenClicks(1);
				log.warning("Failed to Clear the input text on the");
				if (++count > maxTries) {
					throw (e);
				}
			}
		}

	}

	public static boolean checkWindowsOs() {
		String OS = "";
		OS = System.getProperty("os.name");
		System.out.println(OS);

		if (OS.contains("Windows")) {
			return true;
		}
		return false;

	}

	@Test
	public static void login(WebDriver driver, String Email, String url, Logger log) throws Exception {
		try {
			System.out.println("**^#*:" + url);
			if (checkoldlogin(url)) {
				Utils.smallSleepBetweenClicks(1);
				if (usernameloginltpct(url)) {
					String studentuname = "Automation.Student8459";
					String facultyuname = "Automation.Faculty2568";
					String studentpassword = "Boom";
					String facultypassword = "Automate";

					if (Email.contains("student")) {
						Utils.callSendkeys(driver, ActionXpath.username, studentuname, time, log);
						Utils.callSendkeys(driver, ActionXpath.password, studentpassword, time, log);
						Utils.clickXpath(driver, ActionXpath.singnin, time, "Verify", log);
						Utils.bigSleepBetweenClicks(2);

					} else if (Email.contains("faculty")) {
						Utils.callSendkeys(driver, ActionXpath.username, facultyuname, time, log);
						Utils.callSendkeys(driver, ActionXpath.password, facultypassword, time, log);
						Utils.clickXpath(driver, ActionXpath.singnin, time, "Verify", log);
						Utils.bigSleepBetweenClicks(2);

					}

				}
				if (usernameloginsbmppsjal(url)) {
					String studentuname = "TestStudent0610";
					String facultyuname = "test.faculty123";
					String studentpassword = "TestStudent0610";
					String facultypassword = "test.faculty123";

					if (Email.contains("student")) {
						Utils.callSendkeys(driver, ActionXpath.username, studentuname, time, log);
						Utils.callSendkeys(driver, ActionXpath.password, studentpassword, time, log);
						Utils.clickXpath(driver, ActionXpath.singnin, time, "Verify", log);
						Utils.smallSleepBetweenClicks(2);

						List<WebElement> popUpElement = driver
								.findElements(By.xpath("//*[text()='Either Username or password is incorrect.']"));
						if (popUpElement.size() != 0) {
							System.out.println("Pop up is Present: " + popUpElement.get(0).getText());
							log.warning("Pop up is Present: " + popUpElement.get(0).getText());
							Pfs_portal.quitDriver(driver, url);
							log.warning("Driver is Quited");

						} else {
							System.out.println("Pop up is Absent");
						}

					} else if (Email.contains("faculty")) {
						Utils.callSendkeys(driver, ActionXpath.username, facultyuname, time, log);
						Utils.callSendkeys(driver, ActionXpath.password, facultypassword, time, log);
						Utils.clickXpath(driver, ActionXpath.singnin, time, "Verify", log);
						Utils.smallSleepBetweenClicks(2);

						List<WebElement> popUpElement = driver
								.findElements(By.xpath("//*[text()='Either Username or password is incorrect.']"));
						if (popUpElement.size() != 0) {
							System.out.println("Pop up is Present: " + popUpElement.get(0).getText());
							log.warning("Pop up is Present: " + popUpElement.get(0).getText());
							Pfs_portal.quitDriver(driver, url);
							log.warning("Driver is Quited");

						} else {
							System.out.println("Pop up is Absent");
						}

					}
				}
			} else {
				int time = 2000;
				smallSleepBetweenClicks(1);
				String regex = "Null";
				Utils.callSendkeys(driver, ActionXpath.email, Email, time, log);
				Utils.clickXpath(driver, ActionXpath.requestotp, time, "Request OTP", log);

				int count = 0;
				int maxTries = 7;
				String alertMessage = "";
				while (true) {
					try {
						Alert alert = driver.switchTo().alert(); // switch to alert
						alertMessage = driver.switchTo().alert().getText(); // capture alert message
						alert.accept();
						break;
					} catch (Exception e) {
						Utils.smallSleepBetweenClicks(1);
						if (++count > maxTries) {
							log.warning("Max retry of OTP reached");
							throw (e);
						}
					}

				}
				System.out.println(alertMessage); // Print Alert Message
				Pattern pt = Pattern.compile("-?\\d+");
				Matcher m = pt.matcher(alertMessage);
				while (m.find()) {
					regex = m.group();
				}
				// smallSleepBetweenClicks();
				Utils.callSendkeys(driver, ActionXpath.otprequest1, regex, time, log);
				Utils.clickXpath(driver, ActionXpath.verifyotp, time, "Verify otp", log);
				// System.out.println(
				// "Sleeping after login for some seconds so that goBacktoHome function does not
				// automatically logout user");
				// bigSleepBetweenClicks(1);
			}
			System.out.println(
					"Sleeping after login for some seconds so that goBacktoHome function does not automatically logout user");
			bigSleepBetweenClicks(1);
			Thread.sleep(6000);
		} catch (

		Exception e) {
			log.warning("Login to portal failed Quitting the driver" + url);
			printException(e);
			// driver.quit();
			// System.exit(01);
		}

	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException ex) {
			return false;
		}
	}

	public int getDecimalRandomNumber() {

		Random r = new Random();
		int low = 50;
		int high = 99;
		int result = r.nextInt(high - low) + low;
		System.out.println(result);
		return result;
	}

	@Test
	public static void logout(WebDriver driver, String url, String Role, Logger Log) throws Exception {
		try {
			driver.navigate().to(url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "Logout function Click on  initial", log);
			Utils.clickXpath(driver, ActionXpath.facsignOut, time, "Logout function click on signout", log);
			Utils.smallSleepBetweenClicks(2);
			if (Utils.checkIsLogoutWork(url)) {
				boolean signInPresent = false;
				signInPresent = driver.findElements(By.xpath("//*[text()='Sign in']")).size() > 0;
				if (signInPresent) {
					System.out.println("Sign In is present");
				} else {
					driver.navigate().refresh();
				}
			}

		} catch (Exception e) {
			driver.navigate().to(url);
			Utils.printException(e);
			System.out.println("Failure in logout function");
			log.info("Failure in Logout function");
			// Pfs_portal.quitDriver(driver, url);
			// throw (e);
		}
	}

	public static void checkAcadAndClick(WebDriver driver, String Email, String url, Logger log) throws Exception {
		try {
			if (checkAcad(url)) {
				Utils.clickXpath(driver, ActionXpath.ltstaaccademics, time, "Click on LTSTA ACad", log);
			} else {
				Utils.clickXpath(driver, ActionXpath.accademics, time, "Click on non-ltsta Acad", log);
			}
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in checkAcadAndClick function");
			log.info("Failure in checkAcadAndClick function for login id " + Email);
			checkIfStillInLoginScreenAndLogin(driver, url, Email, log);
			// throw (e);
		}
	}

	public static void checkIfStillInLoginScreenAndLogin(WebDriver driver, String url, String Email, Logger log)
			throws Exception {
		try {
			log.info("#########################CheckIfStillInLOginScreen function called");
			boolean signInPresent = false;
			signInPresent = driver.findElements(By.xpath("//*[text()='Sign in']")).size() > 0;
			if (signInPresent) {
				log.info("Hey Sign In is present, loggin in with id +++++" + Email);
				driver.navigate().to(url);
				Utils.login(driver, Email, url, log);
				Utils.smallSleepBetweenClicks(2);
				Utils.checkAcadAndClick(driver, Email, url, log);

			} else {
				log.warning("For this url failed to click on Acad and it's not on Login Page");
			}

		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failied to login again");
			log.info("Failed to login again");
			throw (e);
		}
	}

	public static void clickOnFacultyService(WebDriver driver, String url, Logger Log) throws Exception {
		try {
			if (checkLtsta(url)) {
				Utils.clickXpath(driver, ActionXpath.facServicesltsta, time, "click on faculty services", log);
			} else {
				if (checkServiceTab(url)) {
					Utils.clickXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services", log);
					Utils.clickXpath(driver, ActionXpath.facRaiseCaseleftNavbar, time, "Left nav bar FacRaisebutton",
							log);
				} else {
					Utils.clickXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services", log);
				}
			}
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in clickOnFacultyService function");
			log.info("Failure in Logout function");
			throw (e);
		}
	}

	@Test
	public static Boolean checkoldlogin(String url) {
		String urlToMatch = "ltpct|sbmppsjal";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkIsLogoutWork(String url) {
		String urlToMatch = "portal-demo|sbmppsjal|bimtech|jdinstitutedelhi|nsom";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean questionbank(String url) {
		String urlToMatch = "portal-demo|sbmppsjal|bimtech|jdinstitutedelhi|nsom";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean uploadimage(String url) {
		String urlToMatch = "ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean questionname(String url) {
		String urlToMatch = "portal-demo|bimtech|jdinstitutedelhi";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean raisecase(String url) {
		String urlToMatch = "portal-demo|sbmppsjal|ltpct|jdinstitutedelhi|bimtech|dev";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean viewresult(String url) {
		String urlToMatch = "portal-demo";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkattempt(String url) {
		String urlToMatch = "ecampus|nsom|esscisamsung|sbmppsjal|ltpct|ltsta|jdinstitutedelhi|bimtech|demo|portal-dev";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	public static Boolean skipsubject(String url) {
		String urlToMatch = "ecampus|bimtech|esscisamsung|nsom|portal-demo|portal-dev|sbmppsjal|jdinstitutedelhi|ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static void scrollUpOrDown(WebDriver driver, int pixel) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-100)");
	}

	@Test
	public static Boolean checkUrlToSkipTest(String url) {
		String urlToMatch = "dev|portal-demo|jdinstitutedelhi|nsom|ltsta|ltpct|esscisamsung|jdinstitutedelhi|sbmppsjal|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	@Test
	public static Boolean skipforedudeatils(String url) {
		String urlToMatch = "dev|demo|nsom|esscisamsung|sbmppsjal|bimtech|jdinstitutedelhi|ltpct|ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	@Test
	public static Boolean skipforedudeatils1(String url) {
		String urlToMatch = "nsom|esscisamsung|jdinstitutedelhi";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	@Test
	public static Boolean skipthefacultyprofile(String url) {
		String urlToMatch = "ltsta|nsom|ltpct|ecampus|sbmppsjal|jdinstitutedelhi|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	public static Boolean stueditprofil(String url) {
		String urlToMatch = "dev|demo|nsom|esscisamsung|sbmppsjal|bimtech|jdinstitutedelhi";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checknewlogin(String url) {
		String urlToMatch = "dev|jdinstitutedelhi|nsom|bimtech|ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checksubject(String url) {
		String urlToMatch = "portal-dev|ecampus|nsom|ltpct|sbmppsjal|jdinstitutedelhi|ltpct|esscisamsung|ltsta|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkServiceTab(String url) {
		String urlToMatch = "bimtech|jdinstitutedelhi|nsom|portal-dev|portal-demo|ltpct|sbmppsjal";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			// regex = m.group();
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkLtsta(String url) {
		String urlToMatch = "ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checknsom(String url) {
		String urlToMatch = "nsom";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	public static Boolean usernameloginltpct(String url) {
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	public static Boolean usernameloginsbmppsjal(String url) {
		String urlToMatch = "sbmppsjal";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean publishlink(String url) {
		String urlToMatch = "ltsta|nsom|ltpct|dev|demo|ecampus|sbmppsjal|jdinstitutedelhi|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkBimtech(String url) {
		String urlToMatch = "bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkltpct(String url) {
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkAcad(String url) {
		String urlToMatch = "ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkifcourseissubject(String url) {
		String urlToMatch = "ecampus|portal-demo|nsom|esscisamsung|ltpct|portal-dev|sbmppsjal|jdinstitutedelhi|ltsta|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkDevBimtech(String url) {
		String urlToMatch = "dev|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static void goBackToHome(WebDriver driver, String url, Logger log) throws Exception {
		try {
			boolean alertPresent = false;
			bigSleepBetweenClicks(1);
			driver.navigate().to(url);
			alertPresent = isAlertPresent(driver);
			if (alertPresent) {
				driver.switchTo().alert().accept();
			}
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in go back to");
			log.warning("Failure in go back to home page");
			logout(driver, url, "Role", log);
			// driver.quit();
		}

	}

	@Test
	public static void smallSleepBetweenClicks(int loop) throws InterruptedException {
		int total_time = 2000 * loop;
		System.out.println("Sleeping for " + total_time);
		Thread.sleep(2000 * loop);
	}

	@Test
	public static void vsmallSleepBetweenClicks(int loop) throws InterruptedException {
		int total_time = 1000 * loop;
		System.out.println("Sleeping for " + total_time);
		Thread.sleep(1000 * loop);
	}

	@Test
	public static void bigSleepBetweenClicks(int loop) throws InterruptedException {
		int total_time = 1000 * loop;
		System.out.println("Sleeping for " + total_time);
		Thread.sleep(1000 * loop);
	}

	@Test
	public static void printException(Exception e) {
		log.warning("Exception is  " + e);
	}

	@Test
	public static int generateRandom() {
		double num = (int) Math.round(Math.random() * 10000);
		int num1 = (int) num;
		return num1;
	}

	public static void executeLongWait(String url) throws InterruptedException {
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			bigSleepBetweenClicks(2);
		}
	}

	@Test
	public static String[] convertContent(String input) throws CsvValidationException, IOException {
		String returnarray[] = new String[2];
		String Output1;
		String Output2;
		String CSV_PATH = "C:\\Users\\Public\\Documents\\programSubject.csv";
		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));

		String[] csvCell;
		System.out.println(count);
		while ((csvCell = csvReader.readNext()) != null) {
			if (count == 0) {
				count = count + 1;
				continue;
			}
			if (input.equals(csvCell[0])) {
				returnarray[0] = csvCell[1];
				returnarray[1] = csvCell[2];
				Output1 = csvCell[1];
				Output2 = csvCell[2];

				System.out.println("Output is1  ******" + Output1);
				System.out.println("Output is2 ******" + Output2);
				return (returnarray);
			}

		}
		return (null);
	}

	@Test
	public static String getTEXT(WebDriver driver, String xpath, Logger log, String msg) throws Exception {
		int count = 0;
		int maxTries = 7;
		String HtmlText = "";
		while (true) {
			try {
				if (debug)
					log.info("Get text for xpath element " + msg);
				WebElement p = driver.findElement(By.xpath(xpath));
				HtmlText = p.getText();
				return HtmlText;
			} catch (Exception e) {
				Utils.smallSleepBetweenClicks(1);
				if (++count > maxTries) {
					log.warning("Unable to get text for xPath " + msg);
					throw (e);
				}
			}
		}
	}

	@Test
	public static String[] getClassSubjectAndSection(WebDriver driver, String url, String type) throws Exception {

		try {
			String program, subject;
			String subject1, subject2;
			String program1, program2;
			String[] programconverted;
			String[] ProgSubj = new String[4];
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]", log, "program");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]", log, "subject");

			System.out.println("program string is: " + program);
			System.out.println("subject string is: " + subject);
			if (type.equals("activity")) {
				programconverted = convertContent(program);
				program1 = programconverted[0];
				program2 = programconverted[1];

				System.out.println("Text program1 is : " + program1);
				System.out.println("Text program2 is : " + program2);

				if (Utils.checkifcourseissubject(url)) {
					Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
					subject = Utils.getTEXT(driver, "(//li[@data-value])[1]", log, "subject");
					Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
				} else {

					Utils.clickXpath(driver, ActionXpath.subjectclick, time, "click on subject", log);
					subject = Utils.getTEXT(driver, "(//*[@class='MuiTab-wrapper']//p)[1]", log, "subject");
				}
				String[] Subjectconvented = convertContent(subject);
				subject1 = Subjectconvented[0];
				subject2 = Subjectconvented[1];

				System.out.println("Text subject1 is : " + subject1);
				System.out.println("Text subject2 is : " + subject2);

				ProgSubj[0] = program1;
				ProgSubj[2] = program2;
				ProgSubj[1] = subject1;
				ProgSubj[3] = subject2;

				return (ProgSubj);
			} else if (type.equals("resource")) {
				// ProgSubj[0] = program1;
				// ProgSubj[1] = subject1;
				// ProgSubj[2] = program2;
				// ProgSubj[3] = subject2;

				return (ProgSubj);
			}

			return (null);

		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in getClassSubjectAndSection function");
			log.info("Failure in Logout function");
			throw (e);
		}

	}

}