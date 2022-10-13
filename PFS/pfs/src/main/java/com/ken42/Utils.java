package com.ken42;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.logging.*;
import java.util.regex.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openxmlformats.schemas.drawingml.x2006.main.ThemeDocument;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.Test;

import com.google.common.base.Function;

import org.openqa.selenium.Alert;



public class Utils {
	// static Logger log = Logger.getLogger(Utils.class.getName());
	static int time = 1000;
	public static Logger log = Logger.getLogger("Pfs_portal");

    public static void clickXpath(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver; 
        int count = 0;
		int maxTries = 5;
		final String XPATH = xpath;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(20))
				.pollingEvery(Duration.ofSeconds(5))
				.ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
					  return driver.findElement(By.xpath(XPATH));
					}
				  });
				WE.click();
				// new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
				log.warning("Failed to Click on the :"+msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void callSendkeys(WebDriver driver,String Xpath, String Value, int time1) throws Exception {
		int count = 0;
		int maxTries = 4;
		final String XPATH = Xpath;
		while (true){
			try {
				log.info("Entering value   "+Value);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(20))
				.pollingEvery(Duration.ofSeconds(4))
				.ignoring(NoSuchElementException.class);
				WebElement WE = wait.until(new Function<WebDriver, WebElement>() {
					public WebElement apply(WebDriver driver) {
					  return driver.findElement(By.xpath(XPATH));
					}
				  });
				WE.sendKeys(Value);
				Thread.sleep(1000);
				break;
			} catch (Exception e) {
				Thread.sleep(250);
				log.warning("Failed to send value  "+Value);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}


	public static void cleartext(WebDriver driver, String faccmarks) {
	}

	public static void clickXpathWithScroll(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		int count = 0;
		int maxTries = 2;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				WebElement el = driver.findElement(By.xpath(xpath));
				js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", el);
				el.click();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				Utils.printException(e);
				log.warning("Failed to Click on the :"+msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	@Test
	public static void login(WebDriver driver, String Email) throws Exception {
		try {
			int time = 2000;
			smallSleepBetweenClicks(1);
			String regex = "Null";
			Utils.callSendkeys(driver, ActionXpath.email, Email, time);
			Utils.clickXpath(driver, ActionXpath.requestotp, time, "Request OTP");
			Utils.smallSleepBetweenClicks(3);
			Alert alert = driver.switchTo().alert(); // switch to alert
			String alertMessage = driver.switchTo().alert().getText(); // capture alert message
			System.out.println(alertMessage); // Print Alert Message
			Pattern pt = Pattern.compile("-?\\d+");
			Matcher m = pt.matcher(alertMessage);
			while (m.find()) {
				regex = m.group();
			}
			//smallSleepBetweenClicks();
			alert.accept();
			Utils.callSendkeys(driver, ActionXpath.otprequest2, regex, time);
			Utils.clickXpath(driver, ActionXpath.verifyotp, time, "Verify otp");
			System.out.println("Sleeping after login for 7 seconds so that goBacktoHome function does not automatically logout user");
			bigSleepBetweenClicks(1);
		} catch (Exception e) {
			log.warning("Login to portal failed \n\n\n");
			printException(e);
			driver.quit();
			throw(e);
		}
	}


	@Test
	public static void logout(WebDriver driver, String url, String Role) throws Exception {
		try {
			smallSleepBetweenClicks(1);
			JavascriptExecutor js = (JavascriptExecutor) driver; 
			js.executeScript("window.scrollBy(0,0)");
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "Click on  initial");
			Utils.clickXpath(driver, ActionXpath.facsignOut, time, "click on signout");
			
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in logout function");
			log.info("Failure in Logout function");
			throw(e);
		}
	}

	public static void checkAcadAndClick(WebDriver driver, String url) throws Exception{
		try{
			if (checkAcad(url)) {
				Utils.clickXpath(driver, ActionXpath.ltstaaccademics, time, "Click on LTSTA ACad");
			} else {
				Utils.clickXpath(driver, ActionXpath.accademics, time, "Click on non-ltsta Acad");
			}
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in checkAcadAndClick function");
			log.info("Failure in Logout function");
			throw(e);
		}
	}

	public static void clickOnFacultyService(WebDriver driver,String url) throws Exception{
		try {
			if(checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.facServicesltsta, time, "click on faculty services");
			}else {
				if (checkServiceTab(url)) {
					Utils.clickXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services");
					Utils.clickXpath(driver, ActionXpath.facRaiseCaseleftNavbar, time, "Left nav bar FacRaisebutton");
				} else {
					Utils.clickXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services");
				}
			}
		} catch (Exception e){
			Utils.printException(e);
			System.out.println("Failure in clickOnFacultyService function");
			log.info("Failure in Logout function");
			throw(e);
		}
	}

	@Test
	public static void scrollUpOrDown(WebDriver driver, int pixel){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,pixel)");
	}

	@Test
	public static Boolean checkUrlToSkipTest(String url){
		String urlToMatch = "jdinstitutedelhi|nsom|ltsta|ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            // regex = m.group();
            return true;
        }
        return false;
	}

	@Test
	public static Boolean checknewlogin(String url){
		String urlToMatch = "dev|jdinstitutedelhi|nsom|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}

	@Test
	public static Boolean checkServiceTab(String url){
		String urlToMatch = "bimtech|jdinstitutedelhi|nsom|portal-dev";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            // regex = m.group();
            return true;
        }
        return false;
	}

	@Test
	public static Boolean checkLtsta(String url){
		String urlToMatch = "ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            return true;
        }
        return false;
	}

	@Test
	public static Boolean checkBimtech(String url){
		String urlToMatch = "bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            return true;
        }
        return false;
	}

	@Test
	public static Boolean checkltpct(String url){
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            return true;
        }
        return false;
	}
	@Test
	public static Boolean checkAcad(String url){
		String urlToMatch = "ltsta";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            return true;
        }
        return false;
	}

	@Test
	public static Boolean checkDevBimtech(String url){
		String urlToMatch = "dev|bimtech";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
            return true;
        }
        return false;
	}

	
	@Test
	public static void goBackToHome(WebDriver driver,String url) throws InterruptedException{
		driver.navigate().to(url);
		smallSleepBetweenClicks(1);
	}

	@Test
	public static void smallSleepBetweenClicks(int loop) throws InterruptedException{
		int total_time = 2000 * loop;
		System.out.println("Sleeping for "+total_time);
		Thread.sleep(2000 * loop);
	}
	@Test
	public static void bigSleepBetweenClicks(int loop) throws InterruptedException{
		int total_time = 7000 * loop;
		System.out.println("Sleeping for "+total_time);
		Thread.sleep(7000 * loop);
	}
	@Test
	public static void printException(Exception e){
		log.warning("Exception is  "+e);
	}

	@Test
	public static int generateRandom(){
		double num = (int)Math.round(Math.random() *10000);
        int num1 = (int)num;
		return num1;
	}

	public static void executeLongWait(String url) throws InterruptedException{
		String urlToMatch = "ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
			bigSleepBetweenClicks(2);
        }
	}
	

	@Test
	public static String convertContent(String subject) {

		// delete a last char
		if ("DESIGN TECHNOLOGY-D-FD".equals(subject)){
			return ("Design Technology-D-FD");
		}
		if ("SALES & DISTRIBUTION MANAGEMENT-20-22-RETAIL MANAGEMENT".equals(subject)){
			return ("Sales & Distribution Management-20-22-Retail Management");
		}
		if ("BCA-OBJECT ORIENTED PROGRAMMING".equals(subject)){
			return ("BCA-Object Oriented Programming");
		}
		if ("ENGLISH - CLASS 8".equals(subject)){
			return ("English - Class 8");
		}
		StringBuffer sb = new StringBuffer(subject);
		// sb.deleteCharAt(sb.length() - 1);

		String tri = sb.toString();
		String tr = tri.trim();
		String msg = tr.toLowerCase();
//		     System.out.println(msg);

		char[] charArray = msg.toCharArray();
		boolean foundSpace = true;
		for (int i = 0; i < charArray.length; i++) {
			if (Character.isLetter(charArray[i])) {
				if (foundSpace) {
					charArray[i] = Character.toUpperCase(charArray[i]);
					foundSpace = false;
				}
			} else {
				foundSpace = true;
			}
		}
		 return tr = String.valueOf(charArray);

	}

	@Test
	public static String[]  getClassSubjectAndSection(WebDriver driver) throws Exception{
		try {
			String[]  ProgSubj = new String [2];
			// Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			// Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select");
			Utils.smallSleepBetweenClicks(6);
			WebElement p = driver.findElement(By.xpath("(//*[. and @aria-haspopup='listbox'])[1]"));
			String program = p.getText();
			ProgSubj[0] = program;
			System.out.println("Text program is : " + program);

			Utils.clickXpath(driver, ActionXpath.subjectclick, time, "click on subject");
			WebElement p2 = driver.findElement(By.xpath("(//*[@class='MuiTab-wrapper']//p)[1]"));
			String subject = p2.getText();
			System.out.println("Subject is  " +subject);
			String converted = convertContent(subject);
	
			ProgSubj[1] = converted;
			System.out.println("Conveted string is"+converted);
			return (ProgSubj);
		} catch (Exception e){
			Utils.printException(e);
			System.out.println("Failure in getClassSubjectAndSection function");
			log.info("Failure in Logout function");
			return null;
		}
	}
}