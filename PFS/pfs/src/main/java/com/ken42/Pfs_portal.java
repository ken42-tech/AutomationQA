package com.ken42;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import com.opencsv.CSVReader;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.lang.Math;   


public class Pfs_portal {
	private static WebDriver driver;
	static int time = 1000;
	public static Logger log = Logger.getLogger("Pfs_portal");

	public static void main(String[] args) throws Exception {
		boolean append = false;
		FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\Testresult.HTML", append);
        logFile.setFormatter(new MyHtmlFormatter());
        log.addHandler(logFile);
		// FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\pfs_results.log", append);
		// logFile.setFormatter(new SimpleFormatter());
		// log.addHandler(logFile);
		String CSV_PATH = "C:\\Users\\Public\\Documents\\pfs.csv";
		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));

		String[] csvCell;
		while ((csvCell = csvReader.readNext()) != null) {

			if (count == 0) {
				count = count + 1;
				continue;
			}
			String PFSurl = csvCell[0];
			String facultyEmail = csvCell[1];
			String studentEmail = csvCell[2];
			String Role = csvCell[3];
			String Browser = csvCell[4];
			initDriver(Browser, PFSurl, Role);
			////bigSleepBetweenClicks();
			log.info("**********************Testing for  Portal  "+PFSurl);
			log.info("\n\n\n");
			// Below If will execute all Student related test cases
			if ("student".equals(Role)) {
				login(studentEmail);
				System.out.println("Executing Student portal");
				testStudent(PFSurl);
				testStudentEnrollment(PFSurl);			
				testStudentAcademic(PFSurl);
				testStudentExamination(PFSurl);
				testStudentAttendance(PFSurl);
				testStudentTimeTable(PFSurl);
				testStudentFees(PFSurl);
				testStudentFeedback(PFSurl);
				testStudentStudentStatus(PFSurl);
				testStudentRaiseCase(studentEmail, facultyEmail, PFSurl);
				testStudentMakeRequest(studentEmail, facultyEmail, PFSurl);
				testStudentEvent(studentEmail, facultyEmail, PFSurl);
				testStudentEditProfile(PFSurl);
				testStudentEditEducationDetails( PFSurl);
				testStudentEditAddress(PFSurl);
				testStudentSignout(PFSurl);
				log.info("STUDENT PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			}
			// This block will execute all facutly related test cases
			else if ("faculty".equals(Role)) {
				System.out.println("Executing Faculty portal");
				login(facultyEmail);
				testFaculty(PFSurl);
				testFacultyQuestionBank(PFSurl);
				testFacultyCourseContent(PFSurl);
				testFacultyExamination(PFSurl);
				testFacultyMYStudent(PFSurl);
				testFacultyAttendance(PFSurl);
				testFaculityTimetable(PFSurl);
				testFacultyService(PFSurl);
				testFacultyRaiseCase(studentEmail, facultyEmail, PFSurl);
				testFacultyMakeRequest(studentEmail, facultyEmail, PFSurl);
				testFacultyEvent(PFSurl);
				testfacultyEditProfile(studentEmail, facultyEmail, PFSurl);
				testfacultyEditAddress(studentEmail, facultyEmail, PFSurl);
				testfacultyEditAcademicDetails(studentEmail, facultyEmail, PFSurl);
				testfacultyEditReaserchSupervision(studentEmail, facultyEmail, PFSurl);
				testfacultyEditResearchPublication(studentEmail, facultyEmail, PFSurl);
				testfacultyEditConference(studentEmail, facultyEmail, PFSurl);
				testfacultyEditBook(studentEmail, facultyEmail, PFSurl);
				testfacultyEditProfessionalAssociation(studentEmail, facultyEmail, PFSurl);
				testfacultyOthers(studentEmail, facultyEmail, PFSurl);
				testFacultyDashboard(studentEmail, facultyEmail, PFSurl);
				testFacultyQuestionPaper(studentEmail, facultyEmail, PFSurl);
				testFacultySignout(PFSurl);
				log.info("FACULTY PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			} else if ("both".equals(Role)) {
				testSpreadsheetCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testPPTCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testPDFCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testVideoCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testLinkCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);
				testForumCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role);

			}
			// After all test are over close the browser
			quitDriver(PFSurl);
		}
		SendMail.sendEmail();
	}

	@BeforeSuite
	public static void initDriver(String Browser, String url, String Role) throws Exception {
		System.out.println("Browser is "+Browser);
			System.out.println("URL is "+url);
		try {
			System.out.println("Browser is ****"+Browser);
			System.out.println("URL is "+url);
			if ("chrome".equals(Browser)) {
				// System.setProperty("webdriver.chrome.driver",
				// 		"C:\\Users\\Public\\Documents\\edgedriver_win64\\chromedriver.exe");
				ChromeOptions op = new ChromeOptions();
				op.addArguments("--disable-notifications");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(op);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\edgedriver_win64\\msedgedriver.exe");
				EdgeOptions op = new EdgeOptions();
				// WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\geckodriver-v0.31.0-win64\\geckodriver.exe");
				// EdgeOptions op = new EdgeOptions();
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}
			driver.get(url);
			driver.manage().window().maximize();
		} catch (Exception e) {
			log.warning("UNABLE TO LAUNCH BROWSER \n\n\n");
			SendMail.sendEmail();
			System.exit(01);

		}
	}

	@Test
	public static void login(String Email) throws Exception {
		try {
			int time = 2000;
			smallSleepBetweenClicks();
			String regex = "Null";
			Automate.callSendkeys(driver, ActionXpath.email, Email, time);
			Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in");
			Automate.CallXpath(driver, ActionXpath.mobile, time, "Enter mobile Number");
			Automate.CallXpath(driver, ActionXpath.mobile2, time, "Click Mobile ");
			Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in for otp");
			// Thread.sleep(time);
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
			Automate.callSendkeys(driver, ActionXpath.OtpInput, regex, time);
			Automate.CallXpath(driver, ActionXpath.submit, time, "Submit");
			System.out.println("Sleeping after login for 7 seconds so that goBacktoHome function does not automatically logout user");
			bigSleepBetweenClicks();
		} catch (Exception e) {
			log.warning("Login to portal failed \n\n\n");
			printException(e);
			driver.quit();
			SendMail.sendEmail();
			// System.exit(01);
		}
	}

	@Test
	public static void logout(String url, String Role) throws Exception {
		try {
			smallSleepBetweenClicks();
			JavascriptExecutor js = (JavascriptExecutor) driver; 
			js.executeScript("window.scrollBy(0,0)");
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "Click on  initial");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "click on signout");
			
		} catch (Exception e) {
			System.out.println("Failure in logout function");
			System.out.println(e);
		}

	}

	@Test
	public static String convertContent(String subject) {

		// delete a last char
		if ("DESIGN TECHNOLOGY-D-FD".equals(subject)){
			return ("Design Technology-D-FD");
		}
		
		StringBuffer sb = new StringBuffer(subject);
		sb.deleteCharAt(sb.length() - 1);

		String tri = sb.toString();
		String tr = tri.trim();
		String msg = tr.toLowerCase();
//		     System.out.println(msg);

		char[] charArray = msg.toCharArray();
		boolean foundSpace = true;
		int count = 0;
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
	public static Boolean checkServiceTab(String url){
		String urlToMatch = "bimtech";
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

	public static void checkAcadAndClick(String url) throws Exception{
		if (checkAcad(url)) {
			Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "Click on LTSTA ACad");
		} else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "Click on non-ltsta Acad");
		}
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
	public static void goBackToHome(String url) throws InterruptedException{
		////bigSleepBetweenClicks();
		driver.navigate().to(url);
		// bigSleepBetweenClicks();
	}

	@Test
	public static void smallSleepBetweenClicks() throws InterruptedException{
		System.out.println("Sleeping for 2 seconds");
		Thread.sleep(2000);
	}
	@Test
	public static void bigSleepBetweenClicks() throws InterruptedException{
		System.out.println("Sleeping for 7 seconds");
		Thread.sleep(7000);
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
		String urlToMatch = "ltsta|jdinstitutedelhi";
		Pattern pt = Pattern.compile(urlToMatch);
        Matcher m = pt.matcher(url);
        while (m.find()) {
			bigSleepBetweenClicks();
			bigSleepBetweenClicks();
        }
	}
	
	@Test
	public static String[]  getClassSubjectAndSection() throws Exception{
		String[]  ProgSubj = new String [2];
		Automate.CallXpath(driver, ActionXpath.program, time, "click on program");
		Automate.CallXpath(driver, ActionXpath.programselect, time, "click on program select");
		WebElement p = driver.findElement(By.xpath("(//*[. and @aria-haspopup='listbox'])[1]"));
		String program = p.getText();
			System.out.println("Text program is : " + program);
		ProgSubj[0] = program;
		Automate.CallXpath(driver, ActionXpath.course, time, "click on Course");
		Automate.CallXpath(driver, ActionXpath.courseselect, time, "click on Course select");

		WebElement p1 = driver.findElement(By.xpath("(//*[. and @aria-haspopup='listbox'])[2]"));
		String course = p1.getText();
				System.out.println("Text course is : " + course);

		// WebElement p2 = driver.findElement(By.xpath("//*[@class='MuiTab-wrapper']"));
		WebElement p2 = driver.findElement(By.xpath("//*[@class='MuiTab-wrapper']//p"));
		String subject = p2.getText();
		System.out.println("Subject is  " +subject);
		String converted = convertContent(subject);

		ProgSubj[1] = converted;
		System.out.println("Conveted string is"+converted);
		return (ProgSubj);
	}

	public static void clickOnFacultyService(String url) throws Exception{
		if(checkLtsta(url)){
			Automate.CallXpath(driver, ActionXpath.facServicesltsta, time, "click on faculty services");
		}else {
			if (checkServiceTab(url)) {
				Automate.CallXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services");
				Automate.CallXpath(driver, ActionXpath.facRaiseCaseleftNavbar, time, "Left nav bar FacRaisebutton");
			} else {
				Automate.CallXpath(driver, ActionXpath.facServicespfsbmtnsom, time, "click on faculty services");
			}
		}
	}

	@Test(priority = 1)
	public static void testStudent(String url) throws Exception {
		try {
			System.out.println(" TC-1:  Student Starting Home tab  case execution");
			////bigSleepBetweenClicks();
			// Automate.CallXpath(driver, ActionXpath.Home, time, "Home button");
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "Click on Login initial");
			//Check if Attendance, Assessments, Schedule are present in home tab.
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Attendance") && p.contains("Assessments") 
			&& p.contains("Timetable") && p.contains("Schedule") && p.contains("MY SUBJECTS")){
				log.info(" TC-1: Student Home tab test case PASSED \n\n");
			}else {
				log.warning(" TC-1: Student Home tab test case FAILED it does not contain all the tabs\n\n");
			}

		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning(" TC-1: Student Home tab test case FAILED \n");
		}
	}

	@Test(priority = 2)
	public static void testStudentEnrollment(String url) throws Exception {
		try {
			System.out.println(" TC-2:  Starting Student Enrollment  case execution");
			if (checkUrlToSkipTest(url)){
				log.info("TC-2 Student Enrollement Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			goBackToHome(url); 
			Automate.CallXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
			//smallSleepBetweenClicks();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.ClickOpenEnroll, time, "Go to the open Enrollement");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			log.info("TC-2: Enrollment of the Student Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-2: Enrollment of the Student Test Case FAILED \n");
		}
	}

	@Test(priority = 3)
	public static void testStudentAcademic(String url) throws Exception {
		try {
			System.out.println("TC-3: Starting Student Academic  test case execution\n");
			goBackToHome(url); 
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaAcademic, time, "Exapand Academic ");
				Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,2000)");
				Automate.CallXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Automate.CallXpath(driver, ActionXpath.ltstaAcademic, time, "Close Academic Expand");
			}else {
				Automate.CallXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
				//smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,2000)");
				Automate.CallXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Automate.CallXpath(driver, ActionXpath.CloseAcademicExapand, time, "Close Academic Expand");
				//smallSleepBetweenClicks();
			}
			log.info("TC-3: Student Academic Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			driver.navigate().to(url);
			////bigSleepBetweenClicks();
			log.warning("TC-3: Student Academic Test Case FAILED \n");
		}
	}

	@Test(priority = 4)
	public static void testStudentExamination(String url) throws Exception {
		try {
			System.out.println("TC-4:   Starting Student Examination test case execution");
			goBackToHome(url);
			if (checkUrlToSkipTest(url)){
				log.info("TC-4: Exam tab Skipping as this is not applicable for this portal\n");
				return;
			}
			if(checkLtsta(url)){
				//Do nothing
			}else {
				// ////bigSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click Exams");
			}
			Automate.CallXpath(driver, ActionXpath.examAnnouncements, time, "Click on Announcement");
			//smallSleepBetweenClicks();
			log.info("Student TC-4: Student Examination Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-4: Student Examination Test Case FAILED \n");
		}
	}

	@Test(priority = 5)
	public static void testStudentAttendance(String url) throws Exception {
		try {
			System.out.println("TC-5: Starting Student Attendance test case execution");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaAttendance, time, "Select the Attendance");
			}else {
				Automate.CallXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
			}
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
			log.info("TC-5: Student Attendance Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-5: 	Student Attendance  Test Case FAILED \n");
		}
	}

	@Test(priority = 6)
	public static void testStudentTimeTable(String url) throws Exception {
		try {
			System.out.println("TC-6: Starting Student Timetable test case execution ");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaTimetable, time, "Select time table");
			}else {
				Automate.CallXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
			}
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.TimeTableMonth, time, "Select Month view");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.TimeTableWeek, time, "Selecte Week view");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.TimeTableDay, time, "Select Day view");
			//smallSleepBetweenClicks();
			log.info("TC-6:   Student Timetable Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-6:  Student Timetable Test Case Case FAILED \n");
		}
	}

	@Test(priority = 7)
	public static void testStudentFees(String url) throws Exception {
		try {
			System.out.println("TC-7:  Starting Student FEES test case execution");
			goBackToHome(url);
			if (checkUrlToSkipTest(url)){
				log.info("TC-7: Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			////bigSleepBetweenClicks();
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
				// //smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				// //smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
			}else {
				Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
				// //smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				// //smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
			}
			//smallSleepBetweenClicks();
			log.info("TC-7: Student Fees Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-7: Student Fees Test Case FAILED \n");
		}
	}

	@Test(priority = 8)
	public static void testStudentFeedback(String url) throws Exception {
		try {
			System.out.println("TC-8:   Starting Student FEEDBACK test case execution");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstafeedBack, time, "FeedBack");
			}else {
				Automate.CallXpath(driver, ActionXpath.feedBack, time, "FeedBack");
			}
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
			//smallSleepBetweenClicks();
			log.info("TC-8: Student FEEDBACK tab Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-8: Student FEEDBACK tab Test Case FAILED \n");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus(String url) throws Exception {
		try {
			System.out.println("TC-9:  Starting  Student Services check cancel button test case execution ");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
			log.info("TC-9: Student Services check cancel button Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning(" TC-9: Student Services check cancel button Test Case FAILED \n ");
		}
	}

	@Test(priority = 10)
	public static void testStudentRaiseCase(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-10: Starting Student Services Raise test case execution \n");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			// //smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.inputraise, "WIfi not working ", time);
			// //smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.description,
					"While i Select the network to check the Exam date and all that time i did not found the exam date & also tab ewas not working",
					time);
			Automate.CallXpath(driver, ActionXpath.submitofcase, time, "Submit the case");
			log.info(" TC-10: Student Service Raise case Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning(" TC-10: Student Service Raise case Test Case FAILED \n");
		}
	}

	@Test(priority = 11)
	public static void testStudentMakeRequest(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-11 Starting Student Services make request test case execution ");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			// //smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Click the button Make raise");
			Automate.CallXpath(driver, ActionXpath.makeRequest, time, "selet the Button Make request");
			////smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.enterSubject, "The Attendance Request for 2 days", time);
			Automate.callSendkeys(driver, ActionXpath.desc,
					"I need to apply the leave for the 2 days because of some personal issue so plz approve the request ",
					time);
			Automate.CallXpath(driver, ActionXpath.makeSubmit, time, "Submit the make requst option");
			//smallSleepBetweenClicks();
			log.info("TC-11: Student services make request test case PASSED \n");

		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-11: Student services make request test Case FAILED \n");
		}
	}

	@Test(priority = 12)
	public static void testStudentEvent(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-12:  Starting Student Event case Execution ");
			goBackToHome(url);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaEvent, time, "Event");
			}else {
				Automate.CallXpath(driver, ActionXpath.Event, time, "Event");
			}
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.back, time, "Go back");
			//smallSleepBetweenClicks();
			log.info("TC-12: Student EVENT Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-12: Student EVENT Test Case  FAILED \n");
		}
	}

	@Test(priority = 13)
	public static void testStudentEditProfile(String url) throws Exception{
		try {
			System.out.println("TC-13: Starting execution of edit basic details of student profile");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "Click on Initial icon");
			Automate.CallXpath(driver, ActionXpath.stuprofile, time, "Click on profile button");
			if(checkDevBimtech(url)){
				//There is no edit button here so do nothing
				System.out.println("In Dev/Bimtech no need for tab button");
			}else {
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.stubasicedit, time, "Click on edit SVG");
			Automate.CallXpath(driver, ActionXpath.Stubasicgender, time, "Stubasicgender");
			// Automate.CallXpath(driver, ActionXpath.stubasicgenderselect, time, "stubasicgenderselect");
			String gender = "Female";
			driver.findElement(By.xpath("//li[@data-value='" + gender + "']")).click();
			Automate.callSendkeys(driver, ActionXpath.stubasicdob, "02-02-2022", time);
			Automate.callSendkeys(driver, ActionXpath.stubasicnation, "India", time);
			Automate.CallXpath(driver, ActionXpath.stubasicsave, time, "Click on Save");
			//smallSleepBetweenClicks();
			log.info("  TC-13: Student edit profile test case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-13: Student edit profile test case FAILED \n");
		}
	}

	@Test(priority = 14)
	public static void testStudentEditEducationDetails(String url) throws Exception {
		try {
			System.out.println(" TC-14 :   Starting student edit profile education Details case execution");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "Click on profile Icon");
			Automate.CallXpath(driver, ActionXpath.stuprofile, time, "Click on profile button");
			//smallSleepBetweenClicks();
			if(checkDevBimtech(url)){
				//There is no edit button here so do nothing
				System.out.println("In Dev/Bimtech no need for tab button");
			}else {
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,2000)");
			Automate.CallXpath(driver, ActionXpath.stueddrop, time, "Click on education");
			Automate.CallXpath(driver, ActionXpath.stued, time, "CLick on edit SVG");
			Automate.callSendkeys(driver, ActionXpath.stued12school, "stpaul", time);
			Automate.callSendkeys(driver, ActionXpath.stued12country, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stued12year, "2017", time);
			Automate.callSendkeys(driver, ActionXpath.stuedclg, "SRKV", time);
			Automate.callSendkeys(driver, ActionXpath.stuedclgcountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stuedclgyear, "2020", time);
			Automate.callSendkeys(driver, ActionXpath.stuedpgclg, "SRKV", time);
			Automate.callSendkeys(driver, ActionXpath.stuedpgcountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stuedpgyear, "2022", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.stuedsave, time, "Click on save");
			////bigSleepBetweenClicks();
			log.info("  TC-14: Student profile edit Education Details test case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-14: Student profile edit Education Details test case FAILED \n");
		}
	}

	@Test(priority = 15)
	public static void testStudentEditAddress(String url) throws Exception {
		try {
			System.out.println(" TC-15:   Starting student prfofile edit Address test case execution");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "profile");
			Automate.CallXpath(driver, ActionXpath.stuprofile, time, "stuprofile");
			//smallSleepBetweenClicks();
			if(checkDevBimtech(url)){
				//There is no edit button here so do nothing
				System.out.println("In Dev/Bimtech no need for tab button");
			}else {
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,3500)");
			Automate.CallXpath(driver, ActionXpath.stuadddrop, time, "stuadddrop");
			////bigSleepBetweenClicks();

			Automate.CallXpath(driver, ActionXpath.stuedit, time, "stuedit");
			Automate.CallXpath(driver, ActionXpath.stuaddadd, time, "stuaddadd");
			Automate.callSendkeys(driver, ActionXpath.stuhouse, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.sturoad, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stusuburb, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stucountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stupincode, "600001", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.stusave, time, "stusave");
			log.info("  TC-15: Student profile edit address detail test case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-15: Student profile edit address detail test case FAILED \n");
		}
	}

	@Test(priority = 16)
	public static void testStudentSignout(String url) throws Exception {
		try {
			System.out.println("TC-16 :     Starting Student SIGNOUT  case execution ");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
			Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
			log.info(" TC-16: Student SIGNOUT Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-16: Student SIGNOUT Test Case FAILED \n");
		}
	}

	//////////////////////// FACULTY PORTAL
	//////////////////////// STARTED/////////////////////////////////////

	@Test(priority = 17)
	public static void testFaculty(String url) throws Exception {
		try {
			System.out.println("TC-17:  Starting FACULTY PORTAL Academic tab test case executation\n");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
			} else {
            	Automate.CallXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
            }
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "click on the dashboard");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facFilter, time, "click the dashboard activity filiter span bar");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdbfilterselect, time, "select the factivity filter option");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdbresfilter, time, "click on the resources filter");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdbrestypes, time, "click Type to open span");
			
			log.info("TC-17: Faculty ACADEMIC Test case PASSED \n");
		} catch (Exception e) {
			printException(e);
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-17: Faculty ACADEMIC Test case FAILED \n");
		}
	}

	@Test(priority = 18)
	public static void testFacultyQuestionBank(String url) throws Exception {
		try {
			System.out.println(" TC-18:  Faculty Starting QuestionBank Tab test case Executation");
			goBackToHome(url);
			if(checkLtsta(url))
           	{
				//smallSleepBetweenClicks();
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
				 }
           	else {
           		Automate.CallXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
           	}
			Automate.CallXpath(driver, ActionXpath.facqb, time, "click  the Question bank");
			Automate.CallXpath(driver, ActionXpath.facaddque, time, "clcik on the add Question manualy");
			Automate.CallXpath(driver, ActionXpath.facqueback, time, "go back");
			log.info("TC-18 : Faculty QuestionBank click BACK button Test Case PASSED \n ");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-18: Faculty QuestionBank click BACK button Test Case FAILED \n");
		}
	}


	@Test(priority = 19)
	public static void testFacultyCourseContent(String url) throws Exception {
		try {
			goBackToHome(url);
			System.out.println("TC-19: Faculty Course Content Test Execution  Started ");
			if(checkLtsta(url))
            {
                Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Automate.CallXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccc, time, "click on the Course content");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccactivity, time, "clck on activity button ");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "select the activity option named was fourm");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "click to add fourm");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccAsscancel, time, "cancel it ");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccAssYes, time, "confirm to cancel");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccc, time, "click on the course content");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.FaccClickResource, time, "Click Resource");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facrescancel, time, "cancel the resources");
			log.info("TC-19:  Faculty Course Content Test PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-19:  Faculty Course Content Test FAILED \n");
		}
	}

	@Test(priority = 20)
	public static void testFacultyExamination(String url) throws Exception {
		try {
			System.out.println("TC-20:     Faculty Examination Test Executation Statred");
			goBackToHome(url);
			if (checkUrlToSkipTest(url)){
				log.info("TC_20 Faculty Examination Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
				Automate.CallXpath(driver, ActionXpath.facexam, time, "Click on the Examination span");
				Automate.CallXpath(driver, ActionXpath.facexamarrow, time, "facexamarrow");
				Automate.CallXpath(driver, ActionXpath.facexamdropdown, time, "Examination naroow dropdown");
				Automate.CallXpath(driver, ActionXpath.facexamexam, time, "facexamexam");
				Automate.CallXpath(driver, ActionXpath.facexamdate, time, "facexamdate");
				Automate.CallXpath(driver, ActionXpath.faceexamtime, time, "faceexamtime");
				log.info("TC-20: Faculty Examanation test cases PASSED... \n ");
			} catch (Exception e) {
				printException(e);
				goBackToHome(url);
				log.warning("TC-20: Faculty Examanation test cases FAILED \n");
			}
	}

	@Test(priority = 21)
	public static void testFacultyMYStudent(String url) throws Exception {
		try {
			System.out.println("TC-21:   Faculty My Students Test Executation Started");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Automate.CallXpath(driver, ActionXpath.faccMyStudentltsta, time, "open the my student on ltsta");
				  }
            else {
            	Automate.CallXpath(driver, ActionXpath.faccMyStudent, time, "open the commom for all portal expect ltsta");    
            }
			// WebElement l= driver.findElement(By.tagName("body"));
        	// String p = l.getText();
			// log.info(p);
			// if (p.contains("My Students") && p.contains("Courses")){
			// 	log.info(" TC-21: Faculty My Student  tab test case PASSED \n\n");
			// }else {
			// 	log.warning(" TC-21: Faculty My Student  tab test case FAILED it does not contain all the tabs\n\n");
			// }
			log.info(" TC-21: Faculty My Student  tab test case PASSED \n\n");
		
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-21: Faculty My Student  tab test case FAILED \n");
		}
	}

	@Test(priority = 22)
	public static void testFacultyAttendance(String url) throws Exception {
		try {
			System.out.println("TC-22 :    Faculty Attendance Test Executation Startred ");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Automate.CallXpath(driver, ActionXpath.facattendanceforltsta, time, "click Attendance tab");
				  }
            else {
            	Automate.CallXpath(driver, ActionXpath.facatt, time, "click Attendance tab");    
            }
			Automate.CallXpath(driver, ActionXpath.faccAttendahis, time, "Click attendance history");
			log.info("TC-22 : Faculty Attendance Test Executation PASSED \n ");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-22 : Faculty Attendance Test case FAILED \n");
		}
	}

	@Test(priority = 23)
	public static void testFaculityTimetable(String url) throws Exception {
		try {
			System.out.println("TC-23 :    Faculty Timetable Test Executation Started ");
			goBackToHome(url);
			if(checkLtsta(url)) {
				Automate.CallXpath(driver, ActionXpath.facClickTimetableltsta, time, "facClickTimetable");
			} else {
            	Automate.CallXpath(driver, ActionXpath.facClickTimetable, time, "facClickTimetable");    
            }
			Automate.CallXpath(driver, ActionXpath.facttmonth, time, "facttmonth");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facttweek, time, "facttweek");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facttday, time, "facttday");
			//smallSleepBetweenClicks();
			log.info("TC-23 : Faculty Timetable test case PASSED \n ");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-23 : Faculty Timetable test case FAILED \n");
		}
	}



@Test(priority = 24)
	public static void testFacultyService(String url) throws Exception {
		try {
			System.out.println("TC-24:  Faculty Service Test case Started");
			goBackToHome(url);
			clickOnFacultyService(url);

			Automate.CallXpath(driver, ActionXpath.FacRaisebutton, time, "Click on Raise case button");
			smallSleepBetweenClicks();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,300)");
			Automate.CallXpath(driver, ActionXpath.facCancelSer, time, "cancel");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facMakedevNsom, time, "facMakedevNsom");
			smallSleepBetweenClicks();
			// Automate.CallXpath(driver, ActionXpath.facMakeRButtondevNsome, time, "make rq button");
			// smallSleepBetweenClicks();
			// Automate.CallXpath(driver, ActionXpath.facCancelSer, time, "CLick on cancel buttoncancel");
			log.info("TC-24: Faculty Service test cancel button Test case PASSED \n ");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-24: Faculty Service test cancel button Test case FAILED \n");
		}
	}

	@Test(priority = 25)
	public static void testFacultyRaiseCase(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-25 Faculty Service Raise A Case ");
			goBackToHome(url);
			clickOnFacultyService(url);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.FacRaisebutton, time, "FacRaisebutton");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.inputSub, "Regd Error on Inviligation Secation", time);
			Automate.callSendkeys(driver, ActionXpath.FacDesc,
					"while i need to regd on the inviligation section m unable to do bcz its showing the system admin Error Sever not availbale 404 error.",
					time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.SubmitRaise, time, "Submit the case");
			log.info("TC 25: Faculty service Status  Raise Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC 25: Faculty service Status  Raise Case FAILED \n");
		}
	}

	@Test(priority = 26)
	public static void testFacultyMakeRequest(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-26: Starting Faculty make a request test case");
			goBackToHome(url);
			clickOnFacultyService(url);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facMakeRButtondevNsome, time, "Click on Make a request button");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facMakeReqButtonSecond, time, "Click on Second Make a request button");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.makeSubjectIn, "5 days Leave Request ", time);
			Automate.callSendkeys(driver, ActionXpath.makedesc,
					"hi ...i want to take the 5 days leave bcz of some helath issue  m not availbe on this days some medical emergency plz approved my rqst... Thanks & regards Aditya .",
					time);
			Automate.CallXpath(driver, ActionXpath.MakeBtn, time, "Submit the Request");
			smallSleepBetweenClicks();
			log.info("TC-26: Faculty service Status  Raise Case PASSED \n");

		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-26: Faculty service Status  Raise Case FAILED \n");
		}
	}

	@Test(priority = 27)
	public static void testFacultyEvent(String url) throws Exception {
		try {
			System.out.println("TC-27: Faculty Portal Event Tab Test case Started");
			goBackToHome(url);
			if(checkLtsta(url)) {
				Automate.CallXpath(driver, ActionXpath.faccEventltsta, time, "facEvent");
			} else {
            	Automate.CallXpath(driver, ActionXpath.faccEvent, time, "facEvent");
    		}
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faceventlocation, time, "faceventlocation");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faceventlocationselect, time, "faceventlocationselect");
			Automate.callSendkeys(driver, ActionXpath.FaccSearch, "Ganesh", time);
			log.info("TC-27: Faculty Event Test case Executation PASSED \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-27: Faculty Event Test case FAILED \n");
		}
	}

	@Test(priority = 28)
	public static void testfacultyEditProfile(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-28:   Faculty Starting PersonalDetails Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "Select faculty initial icon");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "click on profile");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpersonal, time, "facpersonal");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpdedit, time, "facpdedit");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpdgender, time, "facpdgender");
			Automate.CallXpath(driver, ActionXpath.facpdgenderselect, time, "facpdgenderselect");
			Automate.callSendkeys(driver, ActionXpath.facpddob, "02-02-2020", time);
			Automate.callSendkeys(driver, ActionXpath.facpdnationality, "INDIA", time);
			Automate.CallXpath(driver, ActionXpath.facdpsave, time, "facdpsave");
			////bigSleepBetweenClicks();
			log.info("  TC-28: Faculty Starting PersonalDetails Completed test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			Thread.sleep(time);
			log.warning("TC-28: Faculty Starting PersonalDetails test case FAILED \n");
		}
	}

	@Test(priority = 29)
	public static void testfacultyEditAddress(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-29 :   Faculty Address Details Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Automate.CallXpath(driver, ActionXpath.address, time, "addressdetais");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpaddedit, time, "facdpaddedit");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdptype, time, "facdptype");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.FaccfaccTypeSelect, time, "facdptypeselect");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccAddress, "Coimbatore", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccPincode, "600001", time);
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccountry, time, "facdpcountry");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccSelectCountry, time, "facdpcountrysselect");
			//smallSleepBetweenClicks();
			//Automate.CallXpath(driver, ActionXpath.faccState, time, "facdpstate");
			//smallSleepBetweenClicks();
			//Automate.CallXpath(driver, ActionXpath.faccSelectState, time, "facdpstateselect");
			//smallSleepBetweenClicks();
			//Automate.CallXpath(driver, ActionXpath.faccCity, time, "facdpdist");
			//smallSleepBetweenClicks();
			//Automate.CallXpath(driver, ActionXpath.faccSelectCity, time, "facdpdistselect");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccSaveaddress, time, "facdpaddsave");
			////bigSleepBetweenClicks();
			log.info(" TC-29: Faculty edit Address Details Completed test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-29: Faculty edit Address Details test case FAILED \n");
		}
	}

	@Test(priority = 30)
	public static void testfacultyEditAcademicDetails(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-30:   Academic Details Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Automate.CallXpath(driver, ActionXpath.facdpacdeails, time, "facdpacdeails");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpacadd, time, "facdpacadd");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdplevel, time, "facdplevel");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdplevelselect, time, "facdplevelselect");
			Automate.CallXpath(driver, ActionXpath.facdpadcountry, time, "facdpadcountry");
			Automate.CallXpath(driver, ActionXpath.facdpadcountryselect, time, "facdpadcountryselect");
			Automate.callSendkeys(driver, ActionXpath.facdpaduniversity, "ANNA", time);
			Automate.callSendkeys(driver, ActionXpath.facdpadyear, "2020", time);
			////bigSleepBetweenClicks();
			log.info(" TC-30 : Academic Details Completed test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-30 : Academic Details test case FAILED \n");
		}
	}

	@Test(priority = 31)
	public static void testfacultyEditReaserchSupervision(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC:31 :   RESEARCH SUPERVISION Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facclickonT");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facclickonprofile, time, "facclickonprofile");
			////bigSleepBetweenClicks();
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISION, time,
					" facclickonRESEARCHSUPERVISION");
			Automate.CallXpath(driver, ActionXpath.facdpreedit, time, "facdpreedit");
			Automate.CallXpath(driver, ActionXpath.facdpreadd, time, "facdpreadd");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdprename, "Sample", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdprelink, "https://portal-dev.ken42.com", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpredesc, "Sample Desc", time);
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccSaveexp, time, "facdpresave");
			////bigSleepBetweenClicks();
			log.info(" TC-31:  Faculty edit profile RESEARCH SUPERVISION  test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-31 : Faculty edit profile RESEARCH SUPERVISION  test case test case FAILED \n");
		}
	}

	@Test(priority = 32)
	public static void testfacultyEditResearchPublication(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-32 :   RESEARCH PUBLICATION Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONpublish, time,
					"facclickonRESEARCHSUPERVISIONpublish");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpreeditpublish, time, "facdpreeditpublish");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpreaddpublish, time, "facdpreaddpublish");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdppubname, "Surya", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdppublink, "https://portal-dev.ken42.com", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdppubdesc, "Sample Desc", time);
			//smallSleepBetweenClicks();
			log.info("  TC-32: Faculty edit RESEARCH PUBLICATION  test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-32: Faculty edit RESEARCH PUBLICATION test case FAILED \n");
		}
	}

	@Test(priority = 33)
	public static void testfacultyEditConference(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-33 :   Faculty edit Conference Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONconfernece, time,
					"facclickonRESEARCHSUPERVISIONconfernece");

			////bigSleepBetweenClicks();

			Automate.CallXpath(driver, ActionXpath.facdpreeditconfernece, time, "facdpreeditconfernece");

			////bigSleepBetweenClicks();

			Automate.CallXpath(driver, ActionXpath.facdpreaddconfernece, time, "facdpreaddconfernece");

			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpconnameconfernece, "Sample", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpconlinkconfernece, "https://portal-dev.ken42.com", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpcondescconfernece, "Sample Desc", time);
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpconsaveconfernece, time, "facdpconsave");
			////bigSleepBetweenClicks();
			log.info(" TC-33 : Faculty edit Conference Completed test case PASSED \n\n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			Thread.sleep(time);
			log.warning("TC-33 : Conference test case FAILED \n\n");
		}
	}

	@Test(priority = 34)
	public static void testfacultyEditBook(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC:34 :   Book Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpbook, time, "facdpbook");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpbookedit, time, "facdpbookedit");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpbookadd, time, "facdpbookadd");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpbookname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbooklink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbookdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.faccSaveNsombm, time, "faccSaveNsombm");
			bigSleepBetweenClicks();
			log.info(" TC-34: Faculty edit Book Completed test case PASSED  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-34: Faculty edit Book test case FAILED \n");
		}
	}

	@Test(priority = 35)
	public static void testfacultyEditProfessionalAssociation(String student, String faculty, String url) throws Exception {
		try {
			goBackToHome(url);
			System.out.println(" TC-35 :   Professional Association Started  case executation");
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpprof, time, "facdpprof");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpprofedit, time, "facdpprofedit");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpprofadd, time, "facdpprofadd");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpprofname, "Sample", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpproflink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpprofdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpprofsave, time, "facdpprofsave");
			////bigSleepBetweenClicks();
			log.info("  TC-35 : Faculty profile edit Professional Association Completed test case PASSED..  \n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-35 : Faculty profile edit Professional Association test case FAILED \n\n");
		}
	}

	@Test(priority = 36)
	public static void testfacultyOthers(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-36 :   Faculty edit Others Started  case executation");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facdpother, time, "facdpother");
			Automate.CallXpath(driver, ActionXpath.facdpotheredit, time, "facdpotheredit");
			Automate.CallXpath(driver, ActionXpath.facdpotheradd, time, "facdpotheradd");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facdpothername, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherlink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpothersave, time, "facdpothersave");
			////bigSleepBetweenClicks();
      		log.info(" TC-36: Faculty edit Others Completed test case PASSED \n\n");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-36 : Faculty edit Others test case FAILED \n\n");
		}
	}

	@Test(priority = 37)
	public static void testFacultyDashboard(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-37 Faculty DASHBOARD test executation \n");
			goBackToHome(url);
			if(checkLtsta(url))
            {
                Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Automate.CallXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "Dashboard");
			////bigSleepBetweenClicks();
			// String text = driver.findElement(By.xpath(
			// 		"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div/div[1]/div[4]/div/div/div[1]/div[1]"))
			// 		.getText();
			// System.out.println(text);
			// String count = text.substring(11, text.length() - 1);
			// System.out.println(count);
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Overview") && p.contains("Activities")){
				log.info(" TC-21: Faculty My Student  tab test case PASSED \n\n");
			}else {
				log.warning(" TC-21: Faculty My Student  tab test case FAILED it does not contain all the tabs\n\n");
			}
			log.info("TC-37:  Faculty Dashboard test case PASSED \n\n");

		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-37: Faculty DASHBOARD  FAILED \n");
		}
	}

	@Test(priority = 38)
	public static void testFacultyQuestionPaper(String student, String faculty, String url) throws Exception {
		try {

			System.out.println("TC-38 Faculty QUESTION PAPER test executation \n");
			goBackToHome(url);

			if(checkLtsta(url))
            {
                Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Automate.CallXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facqb, time, "Question ");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facaddmanual, time, "Add anual");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facquesub, time, "facqueclassselect");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facquesubselect, time, "facquesub");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.faccnext, time, "Next");
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccquestion,"Question", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccquestionname,"Question time", time);
			//smallSleepBetweenClicks();
			Automate.cleartext(driver, ActionXpath.faccmarks);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccmarks, "1", time);

			Automate.callSendkeys(driver, ActionXpath.faccoption1, "modi", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.feedback1, "Super", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.faccoption2, "sachin", time);
			Automate.callSendkeys(driver, ActionXpath.feedback2, "vg", time);
			Automate.callSendkeys(driver, ActionXpath.faccoption3, "anand", time);
			Automate.callSendkeys(driver, ActionXpath.feedback3, "good", time);
			Automate.CallXpath(driver, ActionXpath.numberofchoice, time, "No of chocice");
			Automate.callSendkeys(driver, ActionXpath.feedbackofcrtans, "super", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.feefbacofincorrect, "improve", time);
			//smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.generalfeedback, "gain know", time);
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facsaveandfinish, time, "Finished");
			//smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facqueback, time, "BAck");
			log.info("TC-38: Faculty QUESTION PAPER TEST CASE PASSED \n");

		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-38: Faculty QUESTION PAPER  FAILED \n");
		}
	}

	@Test(priority = 39)
	public static void testFacultySignout(String url) throws Exception {
		try {
			System.out.println(" TC-39:  Faculty  View Profile test Executation Started");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facselectpro");
			Automate.CallXpath(driver, ActionXpath.facprofile, time, "facprofile");

			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "Click of faculty pic");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info(" TC-39: Faculty View Profile and Sign out Test Case PASSED \n ");
		} catch (Exception e) {
			printException(e);
			goBackToHome(url);
			log.warning("TC-39: Faculty View Profile and Sign out Test Case FAILED \n");
		}
	}
	//////////////////////////// FACULTY PORTAL
	//////////////////////////// FINISHED.///////////////////////////////

	///////////////////////// STARTED TEST CASE FOR BOTH PORTAL
	///////////////////////// ///////////////////////


	@Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-40:  SpreadSheet resource Create View delete Test case Started");
			if (checkBimtech(url)){
				log.info("TC-40 Spreadsheet is not supported on Bimtech");
				return;
			}
			login(faculty);
			bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facssclick, time, "facssclick");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facssadd, time, "facssadd");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Excel_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			 driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys("C:\\Users\\Public\\Documents\\demo.xlsx");
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Automate.CallXpath(driver, ActionXpath.facssopen, time, "faclinkopen");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[. ='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			// Automate.CallXpath(driver, ActionXpath.facsspublish, time, "faclinkpublish");
			log.info("Before publishing Spreadsheet");
			Automate.CallXpathWithScroll(driver, ActionXpath.facsspublish, time, "faclinkpublish");
			Automate.CallXpath(driver, ActionXpath.facsspublishyes, time, "faclinkpublishyes");
			log.info("After publishing Spreadsheet");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");

			login(student);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "Student click facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "Student click accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");

			log.info("Before viewing Spreadsheet");
			Automate.CallXpath(driver, ActionXpath.viewss, time, "viewss");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			log.info("After viewing Spreadsheet");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			smallSleepBetweenClicks();
			logout(url, "student");
			//
			login(faculty);
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			////bigSleepBetweenClicks();
			log.info("Before deleting Spreadsheet");
			Automate.CallXpathWithScroll(driver, ActionXpath.facssopen, time, "facspreadsheetopen");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facspreadsheetdelete");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facspreadsheetdelete2");
			log.info("Before deleting Spreadsheet");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			log.info("TC-40: SpreadSheet resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-40: SpreadSheet resource Create View delete Test Case FAILED \n");
		}
	}

	

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			login(faculty);
			bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			////bigSleepBetweenClicks();
			// add ppt publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpptadd, time, "facpptadd");
			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "PPT_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			// publishppt
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			log.info("Before publishing PPT");
			smallSleepBetweenClicks();
			Automate.CallXpathWithScroll(driver, ActionXpath.facpptpublish, time, "facpptpublish");
			Automate.CallXpath(driver, ActionXpath.facpptpublishyes, time, "facpptpublishyes");
			log.info("After publishing PPT");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			smallSleepBetweenClicks();
			

			login(student);
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpathWithScroll(driver, ActionXpath.viewppt, time, "viewppt");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			logout(url, "student");
			smallSleepBetweenClicks();
			
			login(faculty);
			// unpublish ppt and delete ppt
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			////bigSleepBetweenClicks();
//     Automate.CallXpath(driver, ActionXpath.facccres, time,"facccres");
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			log.info("Before deleting PPT\n");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			log.info("After deleting PPT\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			log.info("TC-41: PPT resource Create View delete Test Case PASSED \n");

		} catch (Exception e) {
			printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-41: PPT resource Create View delete Test Case FAILED \n");

		}

	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-42:  Create PDF resource publish and delete PDF");

			login(faculty);
			bigSleepBetweenClicks();
			// add pdf
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");

			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			Automate.CallXpath(driver, ActionXpath.facccresadd, time, "facccresadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "PDF_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			////bigSleepBetweenClicks();
			// publishpdf
			Automate.CallXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			log.info("Before publishing pdf\n");
			Automate.CallXpath(driver, ActionXpath.facpublishpdf, time, "facpublishpdf");
			Automate.CallXpath(driver, ActionXpath.facpublishpdf2, time, "facpublishpdf2");
			log.info("After publishing pdf\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			// signout
			logout(url, "faculty");

			//Now verify in student
			login(student);
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify PDF creation and logout
			Automate.CallXpathWithScroll(driver, ActionXpath.viewpdf, time, "viewpdf");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Automate.CallXpathWithScroll(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			log.info("After viewing PDF by student\n");
			// signout
			logout(url, "student");

			login(faculty);
			// unpublish pdf and delete pdf
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Automate.CallXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			log.info("Before deleting pdf\n");
			Automate.CallXpathWithScroll(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			log.info("After deleting pdf\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			log.info("TC-42: Create PDF resource publish and delete PDF PASSED \n");

		} catch (Exception e) {
			printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-42: Create PDF resource publish and delete PDF FAILED \n");

		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-43:  Create Video resource create view  and delete");
			login(faculty);
			bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// add video publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
			Automate.CallXpath(driver, ActionXpath.facvideoadd, time, "facvideoadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Video_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.mp4");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Automate.CallXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			log.info("Before publishing video\n");
			Automate.CallXpath(driver, ActionXpath.facvideopublish, time, "facvideopublish");
			Automate.CallXpath(driver, ActionXpath.facvideopublishyes, time, "facvideopublishyes");
			log.info("After publishing video\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");

			//Student to verify
			login(student);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify video creation and logout
			Automate.CallXpathWithScroll(driver, ActionXpath.viewvideo, time, "viewvideo");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			log.info("After viewing video by student\n");
			logout(url, "student");

			//Faculty to delete
			login(faculty);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// unpublish video and delete video
			
			Automate.CallXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			log.info("Before deleting video\n");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facvideodelete");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			log.info("After deleting video\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			log.info("TC-43: Create Video resource create view  and delete PASSED \n");
		} catch (Exception e) {
			printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-43: Create Video resource create view  and delete FAILED \n");

		}
	}

		
	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-44:  Link resource Create View delete Test case Started");
			login(faculty);
			bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// add link publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.faclinkclick, time, "faclinkclick");
			Automate.CallXpath(driver, ActionXpath.faclinkadd, time, "faclinkadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
		
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Link_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			Automate.callSendkeys(driver, ActionXpath.faclinkexternal, url, time);
			
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			
			////bigSleepBetweenClicks();
			// publishlink
			// Automate.CallXpathWithScroll(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			// ////bigSleepBetweenClicks();
			// Automate.CallXpath(driver, ActionXpath.faclinkpublish, time, "faclinkpublish");
			// Automate.CallXpath(driver, ActionXpath.faclinkpublishyes, time, "faclinkpublishyes");
			logout(url, "faculty");

			login(student);
			// Verify link creation and logout
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");

			Automate.CallXpathWithScroll(driver, ActionXpath.viewlink, time, "viewlink");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			////bigSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			logout(url, "student");

			login(faculty);
			////bigSleepBetweenClicks();
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			
			Automate.CallXpathWithScroll(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			log.info("Before deleting link\n");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "Delete Link first button");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "Delete link second button");
			log.info("Before deleting link\n");
			bigSleepBetweenClicks();
			executeLongWait(url);
			logout(url, "faculty");
			log.info("TC-44 Link resource Create View delete Test Case PASSED \n\n");
		} catch (Exception e) {
			printException(e);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED \n\n");
			quitDriver(url);
			initDriver(Browser, url, Role);

		}
	}
	

	@Test(priority = 45)
	public static void testAssessmentCreatePublishViewDelete(String student, String faculty, String url, String Browser, String Role)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-45: Assement create ,pubish & delete Test excutaion was started...");
			login(faculty);
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content");
			
			returnArray = getClassSubjectAndSection();
			String program = returnArray[0];
			String converted = returnArray[1];

			Automate.CallXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "facassessment");
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			smallSleepBetweenClicks();

			String fileName = "Assessment_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facassesmentrelative, fileName, time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			// Automate.CallXpath(driver, ActionXpath.subject, time, "click on subject");
			// driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			// Thread.sleep(2000);
			//driver.findElement(By.xpath("//li[@data-value='" + section + "']")).click();


			// Create and save assessment
			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facclinkrelative, time, "facclink");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.facurlrelative, fileName, time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsavlinrelative, time, "facsavlin");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsaverelative, time, "Save and proceed 1");
			smallSleepBetweenClicks();
			Automate.callSendkeys(driver, ActionXpath.fachourrelative, "1", time);
			Automate.CallXpath(driver, ActionXpath.fasaverelative, time, "Save and proceed 2");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.fasokrelative, time, "fasok");

			//Add question and publish
			Automate.CallXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facselectrelative, time, "Select first question");
			Automate.CallXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facAssPublish, time, "Publish Assessment");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.facAreYouSurePublish, time, "Are you sure you want to publish");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facGoToDashboard, time, "Go TO Dashboard");

			bigSleepBetweenClicks();
			logout(url, Role);
			
			// .....................................student
			login(student);
			smallSleepBetweenClicks();
			checkAcadAndClick(url);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
			Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
			smallSleepBetweenClicks();
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// driver.findElement(By.xpath(ActionXpath.Studentassessmenstrelativelexpand)).sendKeys(Keys.ESCAPE);
			smallSleepBetweenClicks();
			logout(url, Role);


			//// .........................Faculty delete assessment
			login(faculty);
			System.out.println("**************");
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
			smallSleepBetweenClicks();
			Automate.CallXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
					"Click on Assessment SVG");
			smallSleepBetweenClicks();
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();

			smallSleepBetweenClicks();

			// Automate.CallXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "Delete button 1");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement el = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2");
			bigSleepBetweenClicks();
			bigSleepBetweenClicks();
			logout(url, Role);
			log.info("TC-45 Assement create, publish & delete test Executation Was PASSED....\n");
		}

		catch (Exception e) {
			log.warning("TC-45 Assement create,publish & delete test executation was FAILED...");
			quitDriver(url);
			initDriver(Browser, url, Role);
		}
	}

	@Test(priority = 46)
	public static void testFAssignmentCreatePublishViewDelete(String student, String faculty, String url, String Browser, String Role)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-46 Assignment was Create ,publish & delete Test Excecuation Started...\n");
			login(faculty);
			smallSleepBetweenClicks();
			checkAcadAndClick(url);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "facclickcouse");

			returnArray = getClassSubjectAndSection();
			String program = returnArray[0];
			String converted = returnArray[1];

			Automate.CallXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "facassessment");
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			smallSleepBetweenClicks();

			String fileName = "Assignment_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facassesmentrelative, fileName, time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			// Automate.CallXpath(driver, ActionXpath.subject, time, "click on subject");
			// driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			// Thread.sleep(2000);
			//driver.findElement(By.xpath("//li[@data-value='" + section + "']")).click();


			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Automate.CallXpath(driver, ActionXpath.assignfaclinkrelative, time, "faclink");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave");
			Thread.sleep(2000);

			Thread.sleep(2000);
			Automate.cleartext(driver, ActionXpath.assignfactotalmarksrelative);
			Automate.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "9", time);
			Thread.sleep(2000);
			WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
			el.clear();
			el.sendKeys("9");

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacattementsrelative, time, "facattements");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacselectattemtrelative, time, "facselectattemt");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacsaveandproceedrelative, time, "facsaveandproceed");

			Thread.sleep(8000);

			Automate.CallXpath(driver, ActionXpath.assignfacokrelative, time, "facok");
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.assignexapnd1relative, time, "Exapand");
			Thread.sleep(2000);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacdot1relative, time, "Click om Assignment 3 dots");
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement element1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
			Thread.sleep(2000);
			WebDriverWait waite = new WebDriverWait(driver, 20);
			WebElement element2 = waite.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);
			Thread.sleep(8000);
			logout(url, Role);

			//Verify as student
			login(student);
			smallSleepBetweenClicks();
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement");

			Thread.sleep(2000);
			JavascriptExecutor js3 = (JavascriptExecutor) driver;
			js3.executeScript("window.scrollBy(0,2000)");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();

			smallSleepBetweenClicks();
			logout(url, student);

			//Delete code
			login(faculty);
			Thread.sleep(4000);
			checkAcadAndClick(url);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
			Automate.CallXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
			Thread.sleep(2000);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.assignfacdotrelative, time, "facdot");
			Thread.sleep(2000);

			WebDriverWait ele = new WebDriverWait(driver, 20);
			WebElement elem = ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
			Thread.sleep(10000);

			Automate.CallXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2");

			Thread.sleep(15000);
			logout(url, Role);

			log.info("TC:46 Assignment create,publish & delete Was PASSED....\n");
		} catch (Exception e) {
			printException(e);
			log.warning("TC:46 Assignment create,publish & delte was FAILED....\n");
			Thread.sleep(2000);
			quitDriver(url);
			initDriver(Browser, url, Role);

		}
	}

	@Test(priority = 47)
	public static void testForumCreatePublishViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			String returnArray[] = new String[2];
			login(faculty);
			System.out.println("TC-47 Faculty Fourm create,publish Delete test case Staerted...\n");
			Thread.sleep(3000);
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse");
			Thread.sleep(8000);
			returnArray = getClassSubjectAndSection();
			String program = returnArray[0];
			String converted = returnArray[1];

			Automate.CallXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "facassessment");
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			smallSleepBetweenClicks();

			String fileName = "Forum_" + generateRandom();
			Automate.callSendkeys(driver, ActionXpath.facassesmentrelative, fileName, time);
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			// Automate.CallXpath(driver, ActionXpath.subject, time, "click on subject");
			// driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			// Thread.sleep(2000);
			//driver.findElement(By.xpath("//li[@data-value='" + section + "']")).click();

			// new
			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");
			Automate.callSendkeys(driver, ActionXpath.relativefacforumurl1,fileName, time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave");
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok");
			Automate.CallXpath(driver, ActionXpath.relativeformexpand1, time, "formexpand");

			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript("window.scrollBy(0,2000)");

			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			smallSleepBetweenClicks();
			Automate.CallXpath(driver, ActionXpath.relativefaccformedot1, time, "faccformedot");
			smallSleepBetweenClicks();

			WebDriverWait wait5 = new WebDriverWait(driver, 20);
			WebElement element15 = wait5
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element15);
			System.out.println("click on dot and  publish 1st forum");
			Thread.sleep(10000);

			WebDriverWait waitei = new WebDriverWait(driver, 20);
			WebElement element29 = waitei
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element29);
			System.out.println("click on dot and  publish 2nd forum");
			bigSleepBetweenClicks();

			logout(url, Role);

			Thread.sleep(5000);

			// ..............Student Login forum.......................//
			login(student);
			Thread.sleep(20000);
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.relativeforumlearnltsta1, time, "Select learn");
			Automate.CallXpath(driver, ActionXpath.relativeforumaexpandltsta1, time, "expand forum");
			Thread.sleep(2000);

			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Thread.sleep(5000);
			logout(url, Role);

			//// ..................... Delete fourm.................../////
			login(faculty);
			Thread.sleep(4000);
			checkAcadAndClick(url);
			Automate.CallXpath(driver, ActionXpath.relativeforumdacclickcouse12, time, "facclickcouse");
			Automate.CallXpath(driver, ActionXpath.relativeforumdfexpandltsta12, time, "Exapand");
			Thread.sleep(3000);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Thread.sleep(2000);

			Automate.CallXpath(driver, ActionXpath.relativeforumfclickondotltsta12, time, "facdot");

			Thread.sleep(2000);
			WebDriverWait waitei1 = new WebDriverWait(driver, 20);
			WebElement element291 = waitei1
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element291);
			System.out.println("clickon 1st delete");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativedfacdele12, time, "Click on Delete 2");

			Thread.sleep(10000);

			logout(url, Role);

			log.info("TC-47 Faculty Fourm create,publish Delete test case PASSED...");

		} catch (Exception e) {
			printException(e);
			log.warning("TC-47 Faculty Fourm create,publish Delete test case FAILED... \n");
			quitDriver(url);;
		}
	}
	
	@AfterSuite
	public static void quitDriver(String Url) throws Exception {
		log.info("Completed testing of portal" + Url);
		log.info("\n\n");
		driver.quit();
	}
}