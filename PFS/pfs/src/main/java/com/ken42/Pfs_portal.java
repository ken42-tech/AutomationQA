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

public class Pfs_portal {
	private static WebDriver driver;
	static int time = 4000;
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
			// System.out.println(PFSurl);
			// System.out.println((facultyEmail));
			// System.out.println((studentEmail));
			// System.out.println(Role);
			// System.out.print(Browser);
			initDriver(Browser, PFSurl, Role);
			Thread.sleep(5000);
			log.info("*********Testing for   "+PFSurl);
			log.info("\n\n\n");
			// Below If will execute all Student related test cases
			if ("student".equals(Role)) {
				login(studentEmail);
				// System.out.println("Executing Student portal");
				// testStudent(PFSurl);
				// testStudentEnrollment(PFSurl);			
				// testStudentAcademic(PFSurl);
				// testStudentExamination(PFSurl);
				// testStudentAttendance(PFSurl);
				// testStudentTimeTable(PFSurl);
				// testStudentFees(PFSurl);
				// testStudentFeedback(PFSurl);
				// testStudentStudentStatus(PFSurl);
				// testStudentRaiseCase(studentEmail, facultyEmail, PFSurl);
				// testStudentMakeRequest(studentEmail, facultyEmail, PFSurl);
				// testStudentEvent(studentEmail, facultyEmail, PFSurl);
				testStudentEditProfile(PFSurl);
				// testStudentEditEducationDetails( PFSurl);
				// testStudentEditAddress(PFSurl);
				// testStudentSignout(PFSurl);
				log.info("STUDENT PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			}
			// This block will execute all facutly related test cases
			else if ("faculty".equals(Role)) {
				System.out.println("Executing Faculty portal");
				login(facultyEmail);
				// testFaculty(PFSurl);
				// testFacultyQuestionBank(PFSurl);
				// testFacultyCourseContent(PFSurl);
				// testFacultyExamination(PFSurl);
				// testFacultyMYStudent(PFSurl);
				// testFacultyAttendance(PFSurl);
				// testFaculityTimetable(PFSurl);
				testFacultyService(PFSurl);
				testFacultyRaiseCase(studentEmail, facultyEmail, PFSurl);
				testFacultyMakeRequest(studentEmail, facultyEmail, PFSurl);
				// testFacultyEvent(PFSurl);
				// testfacultyEditProfile(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditAddress(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditAcademicDetails(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditReaserchSupervision(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditResearchPublication(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditConference(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditBook(studentEmail, facultyEmail, PFSurl);
				// testfacultyEditProfessionalAssociation(studentEmail, facultyEmail, PFSurl);
				// testfacultyOthers(studentEmail, facultyEmail, PFSurl);
				testFacultyDashboard(studentEmail, facultyEmail, PFSurl);
				testFacultyQuestionPaper(studentEmail, facultyEmail, PFSurl);
				testFacultySignout(PFSurl);
				log.info("FACULTY PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			} else if ("both".equals(Role)) {
				// testSpreadsheetCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				// testPPTCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				// testPDFCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				// testVideoCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				// testLinkCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				// testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);
				// testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);
				// testForumCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);

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
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\edgedriver_win64\\msedgedriver.exe");
				EdgeOptions op = new EdgeOptions();
				// WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\geckodriver-v0.31.0-win64\\geckodriver.exe");
				// EdgeOptions op = new EdgeOptions();
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
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
			Thread.sleep(2000);
			alert.accept();
			Automate.callSendkeys(driver, ActionXpath.OtpInput, regex, time);
			Automate.CallXpath(driver, ActionXpath.submit, time, "Submit");
		} catch (Exception e) {
			log.warning("Login to portal failed \n\n\n");
			driver.quit();
			SendMail.sendEmail();
			System.exit(01);
		}
	}

	@Test
	public static void logout(String url) throws Exception {
		log.info("Logout function has been called since current test cases threw and exception \n\n");
		try {
			driver.navigate().to(url);
			Thread.sleep(5000);
			try {
				// faclogout
				Automate.CallXpath(driver, ActionXpath.facselectpro, time, "facselectpro");
				Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			} catch (ArithmeticException ex) {
				System.out.println(ex);
			}

			try {
				// studentlogout
				Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
				Automate.CallXpath(driver, ActionXpath.logout, time, "logout");
			} catch (ArrayIndexOutOfBoundsException ex) {
				System.out.println(ex);
			}
		} catch (Exception ex) {
			System.out.println(ex);
		}

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
		String urlToMatch = "bimtech|jdinstitutedelhi";
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
	public static Boolean checkLtstaltpct(String url){
		String urlToMatch = "ltsta|ltpct";
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
	public static void goBackToHome(String url) throws InterruptedException{
		Thread.sleep(7000);
		driver.navigate().to(url);
		Thread.sleep(5000);
	}

	@Test(priority = 1)
	public static void testStudent(String url) throws Exception {
		try {
			System.out.println("  TC-1:  Student Starting Home tab  case execution");
			Thread.sleep(7000);
			// Automate.CallXpath(driver, ActionXpath.Home, time, "Home button");
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "Click on Login initial");
			Thread.sleep(3000);
			//Check if Attendance, Assessments, Schedule are present in home tab.
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Attendance") && p.contains("Assessments") 
			&& p.contains("Timetable") && p.contains("Schedule") && p.contains("MY SUBJECTS")){
				log.info("  Student TC-1: Home tab test case PASSED \n\n");
			}else {
				log.warning(" TC-1: Student Home tab test case FAILED it does not contain all the tabs\n\n");
			}

		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning(" TC-1: Student Home tab test case FAILED \n\n");
		}
	}

	@Test(priority = 2)
	public static void testStudentEnrollment(String url) throws Exception {
		try {
			System.out.println(" TC-2:  Starting Student Enrollment  case execution");
			if (checkUrlToSkipTest(url)){
				log.info("Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			goBackToHome(url); 
			Automate.CallXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
			Thread.sleep(3000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.ClickOpenEnroll, time, "Go to the open Enrollement");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			log.info("TC-2: Enrollment of the Student Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-2: Enrollment of the Student Test Case FAILED \n\n");
		}
	}

	@Test(priority = 3)
	public static void testStudentAcademic(String url) throws Exception {
		try {
			System.out.println("TC-3: Starting Student Academic  test case execution");
			goBackToHome(url); 
			Thread.sleep(5000);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaAcademic, time, "Exapand Academic ");
				Thread.sleep(3000);
				Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,2000)");
				Automate.CallXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Automate.CallXpath(driver, ActionXpath.ltstaAcademic, time, "Close Academic Expand");
				Thread.sleep(2000);
			}else {
				Automate.CallXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
				Thread.sleep(3000);
				Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript("window.scrollBy(0,2000)");
				Automate.CallXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Automate.CallXpath(driver, ActionXpath.CloseAcademicExapand, time, "Close Academic Expand");
				Thread.sleep(2000);
			}
			log.info("TC-3: Student Academic Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-3: Student Academic Test Case FAILED \n\n");
		}
	}

	@Test(priority = 4)
	public static void testStudentExamination(String url) throws Exception {
		try {
			System.out.println("TC-4:   Starting Student Examination test case execution");
			goBackToHome(url);
			if (checkUrlToSkipTest(url)){
				log.info("TC-4: Exam tab Skipping as this is not applicable for this portal\n\n");
				return;
			}
			if(checkLtsta(url)){
				//Do nothing
			}else {
				Thread.sleep(5000);
				Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click Exams");
			}
			Automate.CallXpath(driver, ActionXpath.examAnnouncements, time, "Click on Announcement");
			Thread.sleep(2000);
			log.info("Student TC-4: Student Examination Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-4: Student Examination Test Case FAILED \n\n");
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
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
			Thread.sleep(2000);
			log.info("TC-5: Student Attendance Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-5: 	Student Attendance  Test Case FAILED \n\n");
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
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.TimeTableMonth, time, "Select Month view");
			Automate.CallXpath(driver, ActionXpath.TimeTableWeek, time, "Selecte Week view");
			Automate.CallXpath(driver, ActionXpath.TimeTableDay, time, "Select Day view");
			Thread.sleep(2000);
			log.info("TC-6:   Student Timetable Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-6:  Student Timetable Test Case Case FAILED \n\n");
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
			Thread.sleep(5000);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
			}else {
				Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
			}
			Thread.sleep(3000);
			Thread.sleep(2000);
			log.info("TC-7: Student Fees Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-7: Student Fees Test Case FAILED \n\n");
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
			Thread.sleep(3000);
			
			Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
			Thread.sleep(2000);
			log.info("TC-8: Student FEEDBACK tab Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-8: Student FEEDBACK tab Test Case FAILED \n\n");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus(String url) throws Exception {
		try {
			System.out.println("TC-9:  Starting  Student Services check cancel button test case execution ");
			goBackToHome(url);
			Thread.sleep(3000);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Thread.sleep(4000);
			Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
			log.info("TC-9: Student Services check cancel button Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning(" TC-9: Student Services check cancel button Test Case FAILED \n\n ");
		}
	}

	@Test(priority = 10)
	public static void testStudentRaiseCase(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-10: Starting Student Services Raise test case execution ");
			goBackToHome(url);
			Thread.sleep(3000);
			if (checkLtsta(url)){
				Automate.CallXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.inputraise, "WIfi not working ", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.description,
					"While i Select the network to check the Exam date and all that time i did not found the exam date & also tab ewas not working",
					time);
			Automate.CallXpath(driver, ActionXpath.submitofcase, time, "Submit the case");
			Thread.sleep(3000);
			log.info(" TC-10: Student Service Raise case Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning(" TC-10: Student Service Raise case Test Case FAILED \n\n");
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
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Click the button Make raise");
			Automate.CallXpath(driver, ActionXpath.makeRequest, time, "selet the Button Make request");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.enterSubject, "The Attendance Request for 2 days", time);
			Automate.callSendkeys(driver, ActionXpath.desc,
					"I need to apply the leave for the 2 days because of some personal issue so plz approve the request ",
					time);
			Automate.CallXpath(driver, ActionXpath.makeSubmit, time, "Submit the make requst option");
			Thread.sleep(3000);
			log.info("TC-11: Student services make request test case PASSED \n\n");

		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-11: Student services make request test Case FAILED \n\n");
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
			Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
			Automate.CallXpath(driver, ActionXpath.back, time, "Go back");
			log.info("TC-12: Student EVENT Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(5000);
			log.warning("TC-12: Student EVENT Test Case  FAILED \n\n");
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
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.stubasicedit, time, "Click on edit SVG");
			Automate.CallXpath(driver, ActionXpath.Stubasicgender, time, "Stubasicgender");
			// Automate.CallXpath(driver, ActionXpath.stubasicgenderselect, time, "stubasicgenderselect");
			String gender = "Female";
			driver.findElement(By.xpath("//li[@data-value='" + gender + "']")).click();
			Automate.callSendkeys(driver, ActionXpath.stubasicdob, "02-02-2022", time);
			Automate.callSendkeys(driver, ActionXpath.stubasicnation, "India", time);
			Automate.CallXpath(driver, ActionXpath.stubasicsave, time, "stubasicsave");
			Thread.sleep(6000);
			log.info("  TC-13: Student edit profile test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-13: Student edit profile test case FAILED \n\n");
			Thread.sleep(5000);
		}
	}

	@Test(priority = 14)
	public static void testStudentEditEducationDetails(String url) {
		try {
			System.out.println(" TC-14 :   Starting student edit profile education Details case execution");
			goBackToHome(url);
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "profile");
			Automate.CallXpath(driver, ActionXpath.stuprofile, time, "stuprofile");
			Thread.sleep(3000);
			if(checkDevBimtech(url)){
				//There is no edit button here so do nothing
				System.out.println("In Dev/Bimtech no need for tab button");
			}else {
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,2000)");
			Automate.CallXpath(driver, ActionXpath.stueddrop, time, "stueddrop");
			
			Automate.CallXpath(driver, ActionXpath.stued, time, "stued");
			
			Automate.callSendkeys(driver, ActionXpath.stued12school, "stpaul", time);
			Automate.callSendkeys(driver, ActionXpath.stued12country, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stued12year, "2017", time);

			Automate.callSendkeys(driver, ActionXpath.stuedclg, "SRKV", time);
			Automate.callSendkeys(driver, ActionXpath.stuedclgcountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stuedclgyear, "2020", time);

			Automate.callSendkeys(driver, ActionXpath.stuedpgclg, "SRKV", time);
			Automate.callSendkeys(driver, ActionXpath.stuedpgcountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stuedpgyear, "2022", time);

			Automate.CallXpath(driver, ActionXpath.stuedsave, time, "stuedsave");
			Thread.sleep(6000);
			// Automate.CallXpath(driver, ActionXpath.StudBEdit, time, "ClickHome");
			// Thread.sleep(4000);
			log.info("  TC-14: Student profile edit Education Details test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
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
			Thread.sleep(3000);
			if(checkDevBimtech(url)){
				//There is no edit button here so do nothing
				System.out.println("In Dev/Bimtech no need for tab button");
			}else {
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,3500)");
			Automate.CallXpath(driver, ActionXpath.stuadddrop, time, "stuadddrop");
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.stuedit, time, "stuedit");
			// Automate.CallXpath(driver, ActionXpath.StudBEdit, time, "Edit address");
			Automate.CallXpath(driver, ActionXpath.stuaddadd, time, "stuaddadd");
			Automate.callSendkeys(driver, ActionXpath.stuhouse, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.sturoad, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stusuburb, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stucountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stupincode, "600001", time);
			Automate.CallXpath(driver, ActionXpath.stusave, time, "stusave");
			// Automate.CallXpath(driver, ActionXpath.StudBEdit, time, "ClickHome");
			Thread.sleep(6000);
			log.info("  TC-15: Student profile edit address detail test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(3000);
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
			log.info(" TC-16 : Student SIGNOUT Test Case PASSED \n\n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-16 : Student SIGNOUT Test Case FAILED \n\n\n");
		}
	}

	//////////////////////// FACULTY PORTAL
	//////////////////////// STARTED/////////////////////////////////////

	@Test(priority = 17)
	public static void testFaculty(String url) throws Exception {
		try {
			System.out.println("TC-17 :     Starting FACULTY PORTAL Academic tab test case executation");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
				 }
            else {
            Automate.CallXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
            }
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "click on the dashboard");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facFilter, time, "click the dashboard activity filiter span bar");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdbfilterselect, time, "select the factivity filter option");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdbresfilter, time, "click on the resources filter");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdbrestypes, time, "click Type to open span");
			
			log.info("TC-17 : Faculty ACADEMIC Test case PASSED \n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-17 : Faculty ACADEMIC Test case FAILED");
		}
	}

	@Test(priority = 18)
	public static void testFacultyQuestionBank(String url) throws Exception {
		try {
			System.out.println(" TC-18 :    Faculty Starting QuestionBank Tab test case Executation");
			goBackToHome(url);
			if(checkLtsta(url))
           	{
				Thread.sleep(2000);
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
				 }
           	else {
           		Automate.CallXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
           	}
			Automate.CallXpath(driver, ActionXpath.facqb, time, "click  the Question bank");
			Automate.CallXpath(driver, ActionXpath.facaddque, time, "clcik on the add Question manualy");
			
			Automate.CallXpath(driver, ActionXpath.facquesub, time, "click on the span tab of the subject");
			Automate.CallXpath(driver, ActionXpath.facquesubselect, time, "slect the subject list ");
			Automate.CallXpath(driver, ActionXpath.facquesNEXT, time, "click on the next ");
			Automate.CallXpath(driver, ActionXpath.facqueback, time, "go back");
			log.info("TC-18 : Faculty QuestionBank click back button Test Case PASSED \n ");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-18: Faculty QuestionBank click back button Test Case FAILED \n");
		}
	}


	@Test(priority = 19)
	public static void testFacultyCourseContent(String url) throws Exception {
		try {
			goBackToHome(url);
			System.out.println("TC-19: Faculty Course Content Test Execution  Started ");
			Thread.sleep(2000);
			if(checkLtsta(url))
            {
                Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Automate.CallXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.faccc, time, "click on the Course content");
			Automate.CallXpath(driver, ActionXpath.facccactivity, time, "clck on activity button ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "select the activity option named was fourm");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "click to add fourm");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facccAsscancel, time, "cancel it ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.faccAssYes, time, "confirm to cancel");
			Thread.sleep(3000);
			
			Automate.CallXpath(driver, ActionXpath.faccc, time, "click on the course content");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.FaccClickResource, time, "Click Resource");
		
			Automate.CallXpath(driver, ActionXpath.facrescancel, time, "cancel the resources");
			log.info("TC-19:  Faculty Course Content Test PASSED..\n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
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
			driver.get(url);
			Thread.sleep(time);
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
			Automate.CallXpath(driver, ActionXpath.facstudrop, time, "facstudrop");
			Automate.CallXpath(driver, ActionXpath.facstudropselect, time, "facstudropselect");
		
			log.info("TC-21: Faculty My Students  Test executation PASSED \n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-21: Faculty My Students  Test executation FAILED \n");
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
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-22 : Faculty Attendance Test case FAILED \n");
		}
	}

	@Test(priority = 23)
	public static void testFaculityTimetable(String url) throws Exception {
		try {
			System.out.println("TC-23 :    Faculty Timetable Test Executation Started ");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Automate.CallXpath(driver, ActionXpath.facClickTimetableltsta, time, "facClickTimetable");
				  }
            else {
            Automate.CallXpath(driver, ActionXpath.facClickTimetable, time, "facClickTimetable");    
            }
			Automate.CallXpath(driver, ActionXpath.facttmonth, time, "facttmonth");
			Automate.CallXpath(driver, ActionXpath.facttweek, time, "facttweek");
			Automate.CallXpath(driver, ActionXpath.facttday, time, "facttday");
		
			log.info("TC-23 : Faculty Timetable test case PASSED..\n ");
		} catch (Exception e) {
		
			Thread.sleep(time);
			log.warning("TC-23 : Faculty Timetable test case FAILED \n");
		}
	}



@Test(priority = 24)
	public static void testFacultyService(String url) throws Exception {
		try {
			System.out.println("TC-24:  Faculty Service Test case Started");
			goBackToHome(url);
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
			
			Automate.CallXpath(driver, ActionXpath.FacRaisebutton, time, "FacRaisebutton");

			Automate.CallXpath(driver, ActionXpath.facCancelSer, time, "cancel");

			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facMakedevNsom, time, "facMakedevNsom");
			Automate.CallXpath(driver, ActionXpath.facMakeRButtondevNsome, time, "make rq button");
			Automate.CallXpath(driver, ActionXpath.facCancelSer, time, "cancel");
			log.info("TC-24: Faculty Service Test case PASSED..\n ");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-24: Faculty Service Test case FAILED \n");
		}
	}

	@Test(priority = 25)
	public static void testFacultyRaiseCase(String student, String faculty, String url) {
		try {
			System.out.println("TC-25 Faculty Service Raise Case ");
			goBackToHome(url);
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
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.FacRaisebutton, time, "FacRaisebutton");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.inputSub, "Regd Error on Inviligation Secation", time);
			Automate.callSendkeys(driver, ActionXpath.FacDesc,
					"while i need to regd on the inviligation section m unable to do bcz its showing the system admin Error Sever not availbale 404 error.",
					time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.SubmitRaise, time, "Submit the case");
			log.info("TC 25: Faculty service Status  Raise Case PASSED \n");
		} catch (Exception e) {
//			driver.get(url);
			log.warning("TC 25: Faculty service Status  Raise Case FAILED \n");
		}
	}

	@Test(priority = 26)
	public static void testFacultyMakeRequest(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-26: Starting Faculty make a request test case");
			goBackToHome(url);
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
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facMakeRButtondevNsome, time, "make rq button");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.makeSubjectIn, "5 days Leave Request ", time);
			Automate.callSendkeys(driver, ActionXpath.makedesc,
					"hi ...i want to take the 5 days leave bcz of some helath issue  m not availbe on this days some medical emergency plz approved my rqst... Thanks & regards Aditya .",
					time);
			Automate.CallXpath(driver, ActionXpath.MakeBtn, time, "Submit the Request");
			Thread.sleep(3000);
			log.info("TC-26: Faculty service Status  Raise Case PASSED \n");

		} catch (Exception e) {

			Thread.sleep(3000);
			log.warning("TC-26: Faculty service Status  Raise Case FAILED \n");
		}
	}

	@Test(priority = 27)
	public static void testFacultyEvent(String url) throws Exception {
		try {
			System.out.println("TC-27 :  Faculty Portal Event Tab Test case Started");
			goBackToHome(url);
			if(checkLtsta(url))
            {
				Automate.CallXpath(driver, ActionXpath.faccEventltsta, time, "facEvent");
				  }
            else {
            	Automate.CallXpath(driver, ActionXpath.faccEvent, time, "facEvent");
    			 }
			Automate.CallXpath(driver, ActionXpath.faceventlocation, time, "faceventlocation");
			Automate.CallXpath(driver, ActionXpath.faceventlocationselect, time, "faceventlocationselect");
		Automate.callSendkeys(driver, ActionXpath.FaccSearch, "Ganesh", time);
			log.info("TC-27 : Faculty Event Test case Executation PASSED \n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-27 : Faculty Event Test case FAILED \n");
		}
	}

	@Test(priority = 28)
	public static void testfacultyEditProfile(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-28 :   Faculty Starting PersonalDetails Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Automate.CallXpath(driver, ActionXpath.facpersonal, time, "facpersonal");
			Automate.CallXpath(driver, ActionXpath.facpdedit, time, "facpdedit");
			Automate.CallXpath(driver, ActionXpath.facpdgender, time, "facpdgender");
			Automate.CallXpath(driver, ActionXpath.facpdgenderselect, time, "facpdgenderselect");
			Automate.callSendkeys(driver, ActionXpath.facpddob, "02-02-2020", time);
			Automate.callSendkeys(driver, ActionXpath.facpdnationality, "INDIA", time);
			Automate.CallXpath(driver, ActionXpath.facdpsave, time, "facdpsave");
			Thread.sleep(6000);
			log.info("  TC-28 : Faculty Starting PersonalDetails Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-28 : Faculty Starting PersonalDetails test case FAILED \n");
		}
	}

	@Test(priority = 29)
	public static void testfacultyEditAddress(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(20000);
			System.out.println(" Tc-29 :   Address Details Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.address, time, "addressdetais");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdpaddedit, time, "facdpaddedit");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdptype, time, "facdptype");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.FaccfaccTypeSelect, time, "facdptypeselect");
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.faccAddress, "Coimbatore", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.faccPincode, "600001", time);
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facccountry, time, "facdpcountry");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.faccSelectCountry, time, "facdpcountrysselect");
			Thread.sleep(3000);
			//Automate.CallXpath(driver, ActionXpath.faccState, time, "facdpstate");
			Thread.sleep(3000);
			//Automate.CallXpath(driver, ActionXpath.faccSelectState, time, "facdpstateselect");
			Thread.sleep(3000);
			//Automate.CallXpath(driver, ActionXpath.faccCity, time, "facdpdist");
			Thread.sleep(3000);
			//Automate.CallXpath(driver, ActionXpath.faccSelectCity, time, "facdpdistselect");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.faccSaveaddress, time, "facdpaddsave");
			
			log.info(" Tc:29 : Address Details Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("Tc:29 : Address Details test case FAILED \n");
		}
	}

	@Test(priority = 30)
	public static void testfacultyEditAcademicDetails(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(20000);
			System.out.println(" TC-30 :   Academic Details Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facdpacdeails, time, "facdpacdeails");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdpacadd, time, "facdpacadd");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdplevel, time, "facdplevel");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facdplevelselect, time, "facdplevelselect");
			Automate.CallXpath(driver, ActionXpath.facdpadcountry, time, "facdpadcountry");
			Automate.CallXpath(driver, ActionXpath.facdpadcountryselect, time, "facdpadcountryselect");
			Automate.callSendkeys(driver, ActionXpath.facdpaduniversity, "ANNA", time);
			Automate.callSendkeys(driver, ActionXpath.facdpadyear, "2020", time);
			
			Thread.sleep(6000);
			log.info(" Tc:30 : Academic Details Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("Tc:30 : Academic Details test case FAILED \n");
		}
	}

	@Test(priority = 31)
	public static void testfacultyEditReaserchSupervision(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(10000);
			System.out.println(" TC:31 :   RESEARCH SUPERVISION Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facclickonT");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facclickonprofile, time, "facclickonprofile");
			Thread.sleep(5000);
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISION, time,
					" facclickonRESEARCHSUPERVISION");
			Automate.CallXpath(driver, ActionXpath.facdpreedit, time, "facdpreedit");
			Automate.CallXpath(driver, ActionXpath.facdpreadd, time, "facdpreadd");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdprename, "Sample", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdprelink, "https://portal-dev.ken42.com", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdpredesc, "Sample Desc", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.faccSaveexp, time, "facdpresave");

			log.info(" Tc:31 :RESEARCH SUPERVISION Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC:31 : RESEARCH SUPERVISION test case FAILED \n");
		}
	}

	@Test(priority = 32)
	public static void testfacultyEditResearchPublication(String student, String faculty, String url) throws Exception {
		try {

	Thread.sleep(20000);
	System.out.println(" TC-32 :   RESEARCH PUBLICATION Started  case executation");
	Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
	Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
	

			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONpublish, time,
					"facclickonRESEARCHSUPERVISIONpublish");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facdpreeditpublish, time, "facdpreeditpublish");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facdpreaddpublish, time, "facdpreaddpublish");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdppubname, "Surya", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdppublink, "https://portal-dev.ken42.com", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdppubdesc, "Sample Desc", time);
			Thread.sleep(2000);
			log.info("  TC-32 :RESEARCH PUBLICATION Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("Tc-32 : RESEARCH PUBLICATION test case FAILED \n");
		}
	}

	@Test(priority = 33)
	public static void testfacultyEditConference(String student, String faculty, String url) throws Exception {
		try {

		Thread.sleep(10000);
			System.out.println(" Tc-33 :   Conference Started  case executation");

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONconfernece, time,
					"facclickonRESEARCHSUPERVISIONconfernece");

			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.facdpreeditconfernece, time, "facdpreeditconfernece");

			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.facdpreaddconfernece, time, "facdpreaddconfernece");

			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdpconnameconfernece, "Sample", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdpconlinkconfernece, "https://portal-dev.ken42.com", time);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facdpcondescconfernece, "Sample Desc", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facdpconsaveconfernece, time, "facdpconsave");
			Thread.sleep(6000);
			log.info(" TC-33 :Conference Completed test case PASSED \n\n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-33 : Conference test case FAILED \n\n");
		}
	}

	@Test(priority = 34)
	public static void testfacultyEditBook(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(20000);
			System.out.println(" TC:34 :   Book Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facdpbook, time, "facdpbook");
			Automate.CallXpath(driver, ActionXpath.facdpbookedit, time, "facdpbookedit");
			Automate.CallXpath(driver, ActionXpath.facdpbookadd, time, "facdpbookadd");
			Automate.callSendkeys(driver, ActionXpath.facdpbookname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbooklink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbookdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.faccSaveNsombm, time, "faccSaveNsombm");
				log.info(" TC:34 :Book Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-34 : Book test case FAILED \n");
		}
	}

	@Test(priority = 35)
	public static void testfacultyEditProfessionalAssociation(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(20000);
			System.out.println(" TC-35 :   Professional Association Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facdpprof, time, "facdpprof");
			Automate.CallXpath(driver, ActionXpath.facdpprofedit, time, "facdpprofedit");
			Automate.CallXpath(driver, ActionXpath.facdpprofadd, time, "facdpprofadd");
			Automate.callSendkeys(driver, ActionXpath.facdpprofname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpproflink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpprofdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpprofsave, time, "facdpprofsave");
			log.info("  TC-35 :Professional Association Completed test case PASSED..  \n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-35 : Professional Association test case FAILED \n\n");
		}
	}

	@Test(priority = 36)
	public static void testfacultyOthers(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(20000);
			System.out.println(" TC-36 :   Faculty edit Others Started  case executation");
			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Automate.CallXpath(driver, ActionXpath.facdpother, time, "facdpother");
			Automate.CallXpath(driver, ActionXpath.facdpotheredit, time, "facdpotheredit");
			Automate.CallXpath(driver, ActionXpath.facdpotheradd, time, "facdpotheradd");
			Automate.callSendkeys(driver, ActionXpath.facdpothername, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherlink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpothersave, time, "facdpothersave");
      		log.info(" TC-36 :Faculty edit Others Completed test case PASSED \n\n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-36 : Others test case FAILED \n\n");
		}
	}

	@Test(priority = 37)
	public static void testFacultyDashboard(String student, String faculty, String url) throws Exception {
		try {

			System.out.println("TC-37 Faculty DASHBOARD test executation \n");
			goBackToHome(url);
			if(checkLtstaltpct(url)){
				Automate.CallXpath(driver, ActionXpath.openFacltsta, time, "Click Academics");
			}else {
				Automate.CallXpath(driver, ActionXpath.openFacdevnosbm, time, "Click Academics");
			}
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "Dashboard");
			Thread.sleep(5000);
			String text = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div/div[1]/div[4]/div/div/div[1]/div[1]"))
					.getText();
			System.out.println(text);
			String count = text.substring(11, text.length() - 1);
			System.out.println(count);
			log.info("TC-37 Faculty Dashboard test case Was PASSED \n\n");

		} catch (Exception e) {
			Thread.sleep(time);
			log.warning("TC-37:Faculty DASHBOARD  FAILED...\n");
		}
	}

	@Test(priority = 38)
	public static void testFacultyQuestionPaper(String student, String faculty, String url) throws Exception {
		try {

			System.out.println("TC-38 Faculty QUESTION PAPER test executation \n");
			goBackToHome(url);
			if(checkLtsta(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facqb, time, "Question ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facaddmanual, time, "Add anual");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facquesub, time, "facqueclassselect");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facquesubselect, time, "facquesub");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.faccnext, time, "Next");
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.faccquestion,"Question", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.faccquestionname,"Question time", time);
			Thread.sleep(2000);
			Automate.cleartext(driver, ActionXpath.faccmarks);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.faccmarks, "1", time);

			Automate.callSendkeys(driver, ActionXpath.faccoption1, "modi", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.feedback1, "Super", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.faccoption2, "sachin", time);
			Automate.callSendkeys(driver, ActionXpath.feedback2, "vg", time);
			Automate.callSendkeys(driver, ActionXpath.faccoption3, "anand", time);
			Automate.callSendkeys(driver, ActionXpath.feedback3, "good", time);
			Automate.CallXpath(driver, ActionXpath.numberofchoice, time, "No of chocice");
			Automate.callSendkeys(driver, ActionXpath.feedbackofcrtans, "super", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.feefbacofincorrect, "improve", time);
			Thread.sleep(3000);
			Automate.callSendkeys(driver, ActionXpath.generalfeedback, "gain know", time);
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facsaveandfinish, time, "Finished");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facqueback, time, "BAck");
		
			log.info("Tc-38 Faculty QUESTION PAPER TEST CASE PASSED...\n");

		} catch (Exception e) {
			
			Thread.sleep(2000);
			log.warning("Tc-38:Faculty QUESTION PAPER  FAILED...");
		}
	}

	@Test(priority = 39)
	public static void testFacultySignout(String url) throws Exception {
		try {
			System.out.println(" TC-39:  Faculty  View Profile test Executation Started");
			Automate.CallXpath(driver, ActionXpath.facselectpro, time, "facselectpro");
			Automate.CallXpath(driver, ActionXpath.facprofile, time, "facprofile");

			Automate.CallXpath(driver, ActionXpath.facSeleectpic, time, "Click of faculty pic");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info(" TC-39: Faculty View Profile and Sign out Test Case PASSED \n ");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(3000);
			log.warning("TC-39: Faculty View Profile and Sign out Test Case FAILED \n");
		}
	}
	//////////////////////////// FACULTY PORTAL
	//////////////////////////// FINISHED.///////////////////////////////

	///////////////////////// STARTED TEST CASE FOR BOTH PORTAL
	///////////////////////// ///////////////////////


	@Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-40:  SpreadSheet resource Create View delete Test case Started");
			login(faculty);
			Thread.sleep(10000);
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
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facssclick, time, "facssclick");
			Automate.CallXpath(driver, ActionXpath.facssadd, time, "facssadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
		
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample Link", time);
			 driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys("C:\\Users\\USER\\Public Document\\demo.xlsx");
		      Thread.sleep(15000);	
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishlink
			Automate.CallXpath(driver, ActionXpath.facssopen, time, "faclinkopen");
			Automate.CallXpath(driver, ActionXpath.facss3dot, time, "faclink3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facsspublish, time, "faclinkpublish");
			Automate.CallXpath(driver, ActionXpath.facsspublishyes, time, "faclinkpublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			// Verify link creation and logout
			Thread.sleep(10000);
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");

			Automate.CallXpath(driver, ActionXpath.viewss, time, "viewss");
			Automate.CallXpath(driver, ActionXpath.facss3dot, time, "faclink3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "SelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			Thread.sleep(10000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facssopen, time, "facspreadsheetopen");
			Automate.CallXpath(driver, ActionXpath.facss3dot, time, "facspreadsheet3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facspreadsheetdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facspreadsheetdelete2");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-40: SpreadSheet resource Create View delete Test Case PASSED....\n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-40: SpreadSheet resource Create View delete Test Case FAILED \n");

		}
	}

	

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			login(faculty);
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Thread.sleep(15000);
			// add ppt publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			Automate.CallXpath(driver, ActionXpath.facpptadd, time, "facpptadd");
			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Thread.sleep(5000);
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample PPT", time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");
			Thread.sleep(25000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishppt
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Automate.CallXpath(driver, ActionXpath.facpptpublish, time, "facpptpublish");
			Automate.CallXpath(driver, ActionXpath.facpptpublishyes, time, "facpptpublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "FCCportal");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify ppt creation and logout
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.viewppt, time, "viewppt");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "SelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			// unpublish ppt and delete ppt
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Thread.sleep(8000);
//     Automate.CallXpath(driver, ActionXpath.facccres, time,"facccres");
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-41: PPT resource Create View delete Test Case PASSED \n");

		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-41: PPT resource Create View delete Test Case FAILED \n");

		}

	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-42:  Create PDF resource publish and delete PDF");

			login(faculty);
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
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample PDF", time);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishpdf
			Automate.CallXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpublishpdf, time, "facpublishpdf");
			Automate.CallXpath(driver, ActionXpath.facpublishpdf2, time, "facpublishpdf2");
			Thread.sleep(25000);
			Thread.sleep(6000);
			// signout
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
				Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify PDF creation and logout
			Automate.CallXpath(driver, ActionXpath.viewpdf, time, "viewpdf");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// signout
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "SelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

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
			Automate.CallXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-42: Create PDF resource publish and delete PDF PASSED \n\n");

		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-42: Create PDF resource publish and delete PDF FAILED \n\n");

		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-43:  Create Video respuce publish and delete");
			login(faculty);
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
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample Video", time);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.mp4");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishvideo
			Automate.CallXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facvideopublish, time, "facvideopublish");
			Automate.CallXpath(driver, ActionXpath.facvideopublishyes, time, "facvideopublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify video creation and logout
			Automate.CallXpath(driver, ActionXpath.viewvideo, time, "viewvideo");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "SelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

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
			
			Automate.CallXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facvideodelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-43: Create Video respuce publish and delete PASSED \n\n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-43: Create Video respuce publish and delete FAILED \n\n");

		}
	}

		
	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-44:  Link resource Create View delete Test case Started");
			login(faculty);
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
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample Link", time);
			Automate.callSendkeys(driver, ActionXpath.faclinkexternal, url, time);
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishlink
			// Automate.CallXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			// Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			// Thread.sleep(5000);
			// Automate.CallXpath(driver, ActionXpath.faclinkpublish, time, "faclinkpublish");
			// Automate.CallXpath(driver, ActionXpath.faclinkpublishyes, time, "faclinkpublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			// Verify link creation and logout
			Thread.sleep(15000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");

			Automate.CallXpath(driver, ActionXpath.viewlink, time, "viewlink");
			Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "SelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.facClickacademicsltsta, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");	
			}
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			
			Automate.CallXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "faclinkdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-44 Link resource Create View delete Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED \n\n");

		}
	}
	

	@Test(priority = 45)
	public static void testAssessmentCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-45: Assement create ,pubish & delete Test excutaion was started...");
			login(faculty);
			Thread.sleep(10000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.facclickcouserelative, time, "facclickcouse");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facassessmentrelative, time, "facassessment");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facassesmentrelative, "sachinautoengineer", time);
			Thread.sleep(2000);
			//new
			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Thread.sleep(2000);
			//new over
			Automate.CallXpath(driver, ActionXpath.facclinkrelative, time, "facclink");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facurlrelative, "suriw", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsavlinrelative, time, "facsavlin");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsaverelative, time, "facsave");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.fachourrelative, "3", time);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement ele = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Save and Proceed']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.fasokrelative, time, "fasok");
			Automate.CallXpath(driver, ActionXpath.fasquestionrelative, time, "fasquestion");
			Automate.CallXpath(driver, ActionXpath.facselectrelative, time, "facselect");
			Automate.CallXpath(driver, ActionXpath.facaddselectrelative, time, "facaddselect");
			Thread.sleep(10000);
			WebElement ele1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='Preview']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele1);

			Thread.sleep(4000);
			WebElement ele2 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish Assessment']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele2);

			Thread.sleep(15000);

			Automate.CallXpath(driver, ActionXpath. facSelectPrtoSignoutrealtive, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOutrealtive, time, "facsignOut");
			
            //.....................................student assessment.............................////
			login(student);
			Thread.sleep(20000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativeacademic, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
			Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "fassessmentexpltsta");
			Thread.sleep(2000);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//p[.='sachinautoengineer']"));
			js2.executeScript("arguments[0].scrollIntoView(true);", element);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.StudentassessmenstrelativeclickonA, time, "StudentassessmenstrelativeclickonA");
			Thread.sleep(5000);
		Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativesignout, time, "Studentassessmenstrelativesignout");
	        ////.........................Faculty assessment delete.............................////
			login(faculty);
			Thread.sleep(20000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}
			Automate.CallXpath(driver, ActionXpath.facclickcouserelativedelete, time, "fassacclickcouse");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time, "fassessmentexpltsta");
			Thread.sleep(2000);
			JavascriptExecutor js21 = (JavascriptExecutor) driver;
			WebElement element21 = driver.findElement(By.xpath("//p[.='sachinautoengineer']"));
			js21.executeScript("arguments[0].scrollIntoView(true);", element21);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fclickondotltstarelativedelete, time, "fclickondotltsta");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "fsubltstadelete");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " fsubltstadelete1");
		//	Thread.sleep(10000);

		Thread.sleep(15000);
		Automate.CallXpath(driver, ActionXpath. facSelectPrtoSignoutrealtive, time, "facSelectPrtoSignout");
		Automate.CallXpath(driver, ActionXpath.facsignOutrealtive, time, "facsignOut");
		log.info("TC:45 Assement create, publish & delete test Executation Was PASSED....\n");
		} catch (Exception e) {

			logout(url);
			log.warning("TC:45 Assement create,publish & delete test executation was FAILED...");
		}
	}

	@Test(priority = 46)
	public static void testFAssignmentCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC:46 Assignment was Create ,publish & delete Test Excecuation Started...\n");
			Thread.sleep(4000);
			login(faculty);
			Thread.sleep(3000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "facclickcouse");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.assignfacactivityrelative, time, "facactivity");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacassignmentrelative, time, "facassignment");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.assignfacaddactivityrelative, time, "facaddactivity");
			Thread.sleep(2000);

			Automate.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative, "varunautomation", time);
			//new
			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Thread.sleep(2000);
			//new over
			Thread.sleep(2000);
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
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//p[.='varunautomation']"));

			js2.executeScript("arguments[0].scrollIntoView(true);", element);

			Thread.sleep(2000);

			Automate.CallXpath(driver, ActionXpath.assignfacdot1relative, time, "facdot");

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

			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath. facSelectPrtoSignoutrealtive, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOutrealtive, time, "facsignOut");
			
			
			Thread.sleep(5000);

			login(student);
			Thread.sleep(20000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativeacademic, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn");

			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand forum");

			Thread.sleep(2000);
			JavascriptExecutor js3 = (JavascriptExecutor) driver;
			js3.executeScript("window.scrollBy(0,2000)");
			Thread.sleep(2000);
			JavascriptExecutor js20 = (JavascriptExecutor) driver;
			WebElement element0 = driver.findElement(By.xpath("//p[.='varunautomation']"));

			js20.executeScript("arguments[0].scrollIntoView(true);", element0);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.StudentassessmenstrelativeclickonA, time, "StudentassessmenstrelativeclickonA");
			Thread.sleep(5000);
		Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativesignout, time, "Studentassessmenstrelativesignout");
	    
		Thread.sleep(5000);
//
			login(faculty);
			Thread.sleep(4000);
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			}Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
			Thread.sleep(2000);
			JavascriptExecutor js23 = (JavascriptExecutor) driver;
			WebElement element3 = driver.findElement(By.xpath("//p[.='varunautomation']"));

			js23.executeScript("arguments[0].scrollIntoView(true);", element3);

			Thread.sleep(10000);

			Automate.CallXpath(driver, ActionXpath.assignfacdotrelative, time, "facdot");

			
			Thread.sleep(2000);
		
					WebDriverWait ele = new WebDriverWait(driver, 20);
					WebElement elem = ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@role='button'])[22]")));
					((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
					
					Thread.sleep(10000);

					Automate.CallXpath(driver, ActionXpath.assignfacdelerelative, time, "facdelete");

			
		Thread.sleep(15000);
		Automate.CallXpath(driver, ActionXpath. facSelectPrtoSignoutrealtive, time, "facSelectPrtoSignout");
		Automate.CallXpath(driver, ActionXpath.facsignOutrealtive, time, "facsignOut");
		
			log.info("TC:46 Assignment create,publish & delete Was PASSED....\n");
		} catch (Exception e) {
			log.warning("TC:46 Assignment create,publish & delte was FAILED....\n");
			Thread.sleep(2000);
			logout(url);

		}
	}

	@Test(priority = 47)
	public static void testForumCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			login(faculty);
			Thread.sleep(5000);
			System.out.println("TC-47 Faculty Fourm create,publish Delete test case Staerted...\n");
			
			if(checkAcad(url))
			{
				Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "Click on academics tab");	
			}
			else {
			Automate.CallXpath(driver, ActionXpath.accademics, time, "Click on academics tab");	
			}
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "CLick on course content");

			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumactivity1, time, "Click on activity");

			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.relativefacforum1, time, "Click on Forum");

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativefacfforumaddactivity1, time, "Click on Forum Add activity");

			Thread.sleep(2000);

			Automate.callSendkeys(driver, ActionXpath.relativefacforumname1, "spruthirajautomation", time);
			//new
			Automate.CallXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Thread.sleep(2000);
			//new over
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");

			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.relativefacforumurl1, "suri", time);

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumsavlin1, time, "CLick on Save link");

			Thread.sleep(4000);
			Automate.CallXpath(driver, ActionXpath.relativefacforumsave1, time, " CLick on Save button1");

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.relativefaforumsave1, time, "CLick on Save button2");

			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.relativefaforumok1, time, "Click on OK button");

			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.relativeformexpand1, time, "Click on Forum open");

			Thread.sleep(5000);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript("window.scrollBy(0,500)");

			Thread.sleep(5000);
			JavascriptExecutor js3 = (JavascriptExecutor) driver;
			WebElement el = driver.findElement(By.xpath("//p[.='spruthirajautomation']"));
			js3.executeScript("arguments[0].scrollIntoView(true);", el);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.relativefaccformedot1, time, "CLick on 3 dots");
			// Automate.CallXpath(driver, ActionXpath.relativefacformepublish11, time, "Publish on 3 dots");
			// Automate.CallXpath(driver, ActionXpath.relativefacformepublish12, time, "Final Publish button");

			Thread.sleep(10000);


			WebDriverWait wait5 = new WebDriverWait(driver, 20);
			WebElement element15 = wait5
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element15);

			Thread.sleep(10000);

			WebDriverWait waitei = new WebDriverWait(driver, 20);
			WebElement element29 = waitei
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element29);

			Thread.sleep(8000);
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.relativefacformeclickonp1, time, "clickonT");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.relativefacformesignout1, time, "Signout");

			// Thread.sleep(5000);

			// // ..............Student Login forum.......................//
			// login(student);
			// Thread.sleep(20000);
			// if(checkAcad(url))
			// {
			// 	Automate.CallXpath(driver, ActionXpath.Studentassessmenstrelativeacademic, time, "facClickacademics");	
			// }
			// else {
			// Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			// }Thread.sleep(10000);
			// Automate.CallXpath(driver, ActionXpath.relativeforumlearnltsta1, time, "Select learn");
			// Thread.sleep(10000);
			// Automate.CallXpath(driver, ActionXpath.relativeforumaexpandltsta1, time, "expand forum");
			// Thread.sleep(2000);
			// JavascriptExecutor js34 = (JavascriptExecutor) driver;
			// js34.executeScript("window.scrollBy(0,2000)");
			// Thread.sleep(2000);
			// JavascriptExecutor js26 = (JavascriptExecutor) driver;
			// WebElement element36 = driver.findElement(By.xpath("//p[.='spruthirajautomation']"));

			// js26.executeScript("arguments[0].scrollIntoView(true);", element36);
			// Thread.sleep(5000);
			// Automate.CallXpath(driver, ActionXpath.relativeforumsubltstasign1, time, "click profile");
			// Automate.CallXpath(driver, ActionXpath.relativeforumsubltstasignout1, time, "Signout");
			// Thread.sleep(5000);
			// //// ..................... Delete fourm.................../////
			// login(faculty);
			// Thread.sleep(4000);
			// if(checkAcad(url))
			// {
			// 	Automate.CallXpath(driver, ActionXpath.ltstaaccademics, time, "facClickacademics");	
			// }
			// else {
			// Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");	
			// }
			// Automate.CallXpath(driver, ActionXpath.relativeforumdacclickcouse12, time, "facclickcouse");
			// Thread.sleep(5000);

			// Automate.CallXpath(driver, ActionXpath.relativeforumdfexpandltsta12, time, "Exapand");

			// Thread.sleep(3000);
			// JavascriptExecutor js269 = (JavascriptExecutor) driver;
			// js269.executeScript("window.scrollBy(0,3000)");

			// Thread.sleep(2000);
			// JavascriptExecutor js21 = (JavascriptExecutor) driver;
			// WebElement element = driver.findElement(By.xpath("//p[.='spruthirajautomation']"));
			// // xpath("//[@id='u"+s+"']"));

			// js21.executeScript("arguments[0].scrollIntoView(true);", element);

			// Thread.sleep(2000);

			// Automate.CallXpath(driver, ActionXpath.relativeforumfclickondotltsta12, time, "facdot");

			// Thread.sleep(2000);

			// Automate.CallXpath(driver, ActionXpath.relativedfacdelete12, time, "facdelete");

			// Thread.sleep(2000);

			// Automate.CallXpath(driver, ActionXpath.relativedfacdele12, time, "facdele");

			// // Thread.sleep(8000);

			// Thread.sleep(15000);

			// Thread.sleep(5000);

			// Automate.CallXpath(driver, ActionXpath.relativedfacclickonp12, time, "clickonT");
			// Thread.sleep(5000);
			// Automate.CallXpath(driver, ActionXpath.relativedfacconsignout12, time, "Signout");

			log.info("TC-47: Faculty Fourm create,publish Delete test case PASSED...");

		} catch (Exception e) {
			log.warning("TC-47: Faculty Fourm create,publish Delete test case FAILED... \n");
			logout(url);
		}
	}
	
	@AfterSuite
	public static void quitDriver(String Url) throws Exception {
		Thread.sleep(3000);
		log.info("Completed testing of portal" + Url);
		log.info("\n\n");
		driver.quit();
	}
}