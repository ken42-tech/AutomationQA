package com.ken42;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.logging.*;
import java.util.regex.*;

import javax.mail.Quota.Resource;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

    public static void clickXpath(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver; 
        int count = 0;
		int maxTries = 7;
		final String XPATH = xpath;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(30))
				.pollingEvery(Duration.ofSeconds(6))
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
		int maxTries = 7;
		final String XPATH = Xpath;
		while (true){
			try {
				log.info("***********************Entering value   "+Value);
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
				Thread.sleep(1000);
				log.warning("Failed to send value  "+Value);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}

	public static void clickXpathWithJavascript(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver; 
        int count = 0;
		int maxTries = 7;
		final String XPATH = xpath;
		while (true){
			try {
				// Thread.sleep(1000);
				log.info("Click on the:"+msg);
				WebElement webElement = driver.findElement(By.xpath(xpath));
				JavascriptExecutor javaScriptExecutor = (JavascriptExecutor)driver;
				javaScriptExecutor.executeScript("arguments[0].click()", webElement);
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
	public static void cleartext(WebDriver driver, String Xpath) throws Exception {
		int count = 0;
		int maxTries = 7;
		while (true){
			try{
				driver.findElement(By.xpath(Xpath)).clear();
				Thread.sleep(0);
				break;
			}catch(Exception e){
				Utils.smallSleepBetweenClicks(1);
				log.warning("Failed to Clear the input text on the");
				if(++count > maxTries){
					log.info("Exception" +e);
					throw (e);
				}
			}
		}
		
	}

	public static boolean checkWindowsOs() {
		String OS = "";
		OS = System.getProperty("os.name"); 
		System.out.println(OS);

		if (OS.contains("Windows")){
			return true;
		}
		return false;

	}

	public static void clickXpathWithScroll(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js = (JavascriptExecutor) driver; 
		int count = 0;
		int maxTries = 7;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				WebElement el = driver.findElement(By.xpath(xpath));
				js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", el);
				el.click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
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
	public static void login(WebDriver driver, String Email, String url) throws Exception {
		try {

			if (checkoldlogin(url)) {
				int time = 2000;
				String regex = "Null";
				Utils.callSendkeys(driver, ActionXpath.email2, Email, time);
				Utils.clickXpath(driver, ActionXpath.SignIn, time, "Sign in");
				Utils.clickXpath(driver, ActionXpath.mobile, time, "Enter mobile Number");
				Utils.clickXpath(driver, ActionXpath.mobile2, time, "Click Mobile ");
				Utils.clickXpath(driver, ActionXpath.SignIn, time, "Sign in for otp");
				Alert alert = driver.switchTo().alert(); // switch to alert
				String alertMessage = driver.switchTo().alert().getText(); // capture alert message
				System.out.println(alertMessage); // Print Alert Message
				Pattern pt = Pattern.compile("-?\\d+");
				Matcher m = pt.matcher(alertMessage);
				while (m.find()) {
					regex = m.group();
				}
				// smallSleepBetweenClicks();
				alert.accept();
				Utils.callSendkeys(driver, ActionXpath.OtpInput, regex, time);
				Utils.clickXpath(driver, ActionXpath.submit, time, "Submit");
				System.out.println(
						"Sleeping after login for 7 seconds so that goBacktoHome function does not automatically logout user");
				bigSleepBetweenClicks(1);
			} else {
			int time = 2000;
			smallSleepBetweenClicks(1);
			String regex = "Null";
			Utils.callSendkeys(driver, ActionXpath.email, Email, time);
			Utils.clickXpath(driver, ActionXpath.requestotp, time, "Request OTP");
			int count = 0;
			int maxTries = 7;
			String alertMessage = "";
			while (true){
				try {
					Alert alert = driver.switchTo().alert(); // switch to alert
					alertMessage = driver.switchTo().alert().getText(); // capture alert message
					alert.accept();
					break;
				} catch (Exception e){
					Utils.smallSleepBetweenClicks(1);
					if (++count > maxTries){
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
			Utils.callSendkeys(driver, ActionXpath.otprequest2, regex, time);
			Utils.clickXpath(driver, ActionXpath.verifyotp, time, "Verify otp");
			System.out.println(
				"Sleeping after login for 7 seconds so that goBacktoHome function does not automatically logout user");
				bigSleepBetweenClicks(1);
			}
		} catch (Exception e) {
			log.warning("Login to portal failed \n\n\n");
			printException(e);
			driver.quit();
			// System.exit(01);
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

	public static void 
	
	
	
	checkAcadAndClick(WebDriver driver, String url) throws Exception{
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
	public static Boolean checkoldlogin(String url) {
		String urlToMatch = "ltsta|ltpct";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}
	@Test
	public static Boolean checkattempt(String url) {
		String urlToMatch = "nsom|esscisamsung";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}
	public static Boolean skipsubject(String url) {
		String urlToMatch = "esscisamsung|nsom|demo";
		Pattern pt = Pattern.compile(urlToMatch);
		Matcher m = pt.matcher(url);
		while (m.find()) {
			return true;
		}
		return false;
	}
	
	@Test
	public static void scrollUpOrDown(WebDriver driver, int pixel){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,pixel)");
	}

	@Test
	public static Boolean checkUrlToSkipTest(String url){
		String urlToMatch = "jdinstitutedelhi|nsom|ltsta|ltpct|esscisamsung";
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
	public static Boolean checksubject(String url){
		String urlToMatch = "nsom";
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
	public static Boolean checkifcourseissubject(String url){
		String urlToMatch = "portal-demo|nsom|esscisamsung";
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
	public static String[] convertContent(String input) throws CsvValidationException, IOException {
		String returnarray[]=new String[2];
		String Output1;
		String Output2;
		String CSV_PATH = "C:\\Users\\Public\\Documents\\programSubject.csv";
        CSVReader csvReader;
        int count =0;
        csvReader = new CSVReader(new FileReader(CSV_PATH));

        String[] csvCell;
        System.out.println(count);
        while ((csvCell = csvReader.readNext()) != null) {
            if (count == 0){
                count = count + 1;
                continue;
            }
            if(input.equals(csvCell[0])){
				returnarray[0]=csvCell[1];
				returnarray[1]=csvCell[2];
				Output1=csvCell[1];
				Output2=csvCell[2];

                System.out.println("Output is1  ******"+Output1);
				System.out.println("Output is2 ******"+Output2);
				return(returnarray);
            }
			
        }
		return(null);
		

// 		StringBuffer sb = new StringBuffer(input);
// 		// sb.deleteCharAt(sb.length() - 1);

// 		String tri = sb.toString();
// 		String tr = tri.trim();
// 		String msg = tr.toLowerCase();
// //		     System.out.println(msg);

// 		char[] charArray = msg.toCharArray();
// 		boolean foundSpace = true;
// 		for (int i = 0; i < charArray.length; i++) {
// 			if (Character.isLetter(charArray[i])) {
// 				if (foundSpace) {
// 					charArray[i] = Character.toUpperCase(charArray[i]);
// 					foundSpace = false;
// 				}
// 			} else {
// 				foundSpace = true;
// 			}
// 		}
// 		 return tr = String.valueOf(charArray);

	}

	@Test
	public static String  getTEXT(WebDriver driver, String xpath) throws Exception{
		int count = 0;
		int maxTries =7;
		String HtmlText = "";
		while (true){
			try {
				WebElement p = driver.findElement(By.xpath(xpath));
				HtmlText = p.getText();
				return HtmlText;
			} catch (Exception e){
				Utils.smallSleepBetweenClicks(1);
				if(++count > maxTries){
					throw (e);
				}
			}
		}
	}

	@Test
	public static String[]  getClassSubjectAndSection(WebDriver driver,String url,String type) throws Exception{
		
		try {
			String program,subject;
			String subject1,subject2;
			String program1,program2;
			String[] programconverted;
				String[]  ProgSubj = new String [4];
				Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
				Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select");
				 program = Utils.getTEXT(driver,"(//*[. and @aria-haspopup='listbox'])[1]");
				Utils.clickXpath(driver, ActionXpath.course, time, "click on subject");
				Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject"); 
				subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");
				
				System.out.println("program string is: "+program);
				System.out.println("subject string is: "+subject);
			if(type.equals("activity"))
			{
				programconverted = convertContent(program);
				program1 = programconverted[0];
				program2 = programconverted[1];

				
				System.out.println("Text program1 is : " + program1);
				System.out.println("Text program2 is : " + program2);

				
				if(Utils.checkifcourseissubject(url))
				{
					Utils.clickXpath(driver, ActionXpath.course, time, "click on subject");
					subject = Utils.getTEXT(driver, "(//li[@data-value])[1]");
					Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject"); 
				}
				else{
					
				
					Utils.clickXpath(driver, ActionXpath.subjectclick, time, "click on subject");
				 	subject = Utils.getTEXT(driver, "(//*[@class='MuiTab-wrapper']//p)[1]");
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
			}
			else if(type.equals("resource"))
			{
				// ProgSubj[0] = program1;
				// ProgSubj[1] = subject1;
				// ProgSubj[2] = program2;
				// ProgSubj[3] = subject2;

				return(ProgSubj);
			}	


			return(null);

		} catch (Exception e){
			Utils.printException(e);
			System.out.println("Failure in getClassSubjectAndSection function");
			log.info("Failure in Logout function");
			throw (e);
		}
		
		
		
	}
}
