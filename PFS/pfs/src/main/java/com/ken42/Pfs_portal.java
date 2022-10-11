package com.ken42;

import java.io.FileReader;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import com.opencsv.CSVReader;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.text.SimpleDateFormat;


public class Pfs_portal {
	private static final Exception Exception = null;
	private static WebDriver driver;
	static int time = 1000;
	public static Logger log = Logger.getLogger("Pfs_portal");

	public static void main(String[] args) throws Exception {
		boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		String logFileName = String.format("C:\\Users\\Public\\Documents\\Testresult_%s.HTML", timeStamp);
		FileHandler logFile = new FileHandler(logFileName, append);
        logFile.setFormatter(new MyHtmlFormatter());
        log.addHandler(logFile);
		String CSV_PATH = "C:\\Users\\Public\\Documents\\test.csv";
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
			String From = csvCell[5];
            String To = csvCell[6];
			int from = Integer.parseInt(From);
			int to = Integer.parseInt(To);
			if (from < 1 || from > 47 || to < 1 || to > 47){
				log.warning("The range specificed is incorrect it has to be between 1 and 47");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}
			if ("student".equals(Role) && to > 16){
				log.warning("The range specificed for Student profile has to be b/w 1 and 16");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}
			if ("faculty".equals(Role) && (to < 17 || to > 39)){
				log.warning("The range specificed for Faculty  profile has to be b/w 17 and 39");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}
			if ("both".equals(Role) && (to < 40 || to > 47)){
				log.warning("The range specificed for Faculty  profile has to be b/w 17 and 39");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}
			initDriver(Browser, PFSurl, Role);
			////Utils.bigSleepBetweenClicks(1);
			log.info("**********************Testing for  Portal  "+PFSurl);
			// Below If will execute all Student related test cases
			if ("student".equals(Role)) {
				Utils.login(driver, studentEmail);
				System.out.println("Executing Student portal");
				for(int i=from;i<=to;i++){  
					switch (i){
						case 1: 
							testStudent(PFSurl); //TC-1
							break;
						case 2: 
							testStudentEnrollment(PFSurl);	 //TC-2	
							break;
						case 3:
							testStudentAcademic(PFSurl);  //TC-3
							break;
						case 4:
							testStudentExamination(PFSurl);  //TC-4
							break;
						case 5:
							testStudentAttendance(PFSurl);  //TC-5
							break;
						case 6:
							testStudentTimeTable(PFSurl);  //TC-6
							break;
						case 7:
							testStudentFees(PFSurl);  //TC-7
							break;
						case 8:
							testStudentFeedback(PFSurl);  //TC-8
							break;
						case 9:
							testStudentStudentStatus(PFSurl);  //TC-9
							break;
						case 10:
							testStudentRaiseCase(studentEmail, facultyEmail, PFSurl);  //TC-10
							break;
						case 11:
							testStudentMakeRequest(studentEmail, facultyEmail, PFSurl); //TC-11
							break;
						case 12:
							testStudentEvent(studentEmail, facultyEmail, PFSurl);  //TC-12
							break;
						case 13:
							testStudentEditProfile(PFSurl);  //TC-13
							break;
						case 14:
							testStudentEditEducationDetails( PFSurl);  //TC-14
							break;
						case 15:
							testStudentEditAddress(PFSurl);  //TC-15
							break;
						case 16:
							testStudentSignout(PFSurl);  //TC-16
							break;
						default:
							throw Exception;
					}
				}
				log.info("STUDENT PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			}
			// This block will execute all facutly related test cases
			else if ("faculty".equals(Role)) {
				System.out.println("Executing Faculty portal");
				Utils.login(driver, facultyEmail);
				for(int i=from;i<=to;i++){
					switch (i){
						case 17: 
							testFaculty(PFSurl); //TC-17
							break;
						case 18:
							testFacultyQuestionBank(PFSurl); //TC-18
							break;
						case 19:
							testFacultyCourseContent(PFSurl); //TC-19
							break;
						case 20:
							testFacultyExamination(PFSurl); //TC-20
							break;
						case 21:
							testFacultyMYStudent(PFSurl); //TC-21
							break;
						case 22:
							testFacultyAttendance(PFSurl); //TC-22
							break;
						case 23:
							testFaculityTimetable(PFSurl); //TC-23
							break;
						case 24:
							testFacultyService(PFSurl); //TC-24
							break;
						case 25:
							testFacultyRaiseCase(studentEmail, facultyEmail, PFSurl); //TC-25
							break;
						case 26:
							testFacultyMakeRequest(studentEmail, facultyEmail, PFSurl); //TC-26
							break;
						case 27:
							testFacultyEvent(PFSurl); //TC-27
							break;
						case 28:
							testfacultyEditProfile(studentEmail, facultyEmail, PFSurl); //TC-28
							break;
						case 29:
							testfacultyEditAddress(studentEmail, facultyEmail, PFSurl); //TC-29
							break;
						case 30:
							testfacultyEditAcademicDetails(studentEmail, facultyEmail, PFSurl); //TC-30
							break;
						case 31:
							testfacultyEditReaserchSupervision(studentEmail, facultyEmail, PFSurl); //TC-31
							break;
						case 32:
							testfacultyEditResearchPublication(studentEmail, facultyEmail, PFSurl); //TC-32
							break;
						case 33:
							testfacultyEditConference(studentEmail, facultyEmail, PFSurl); //TC-33
							break;
						case 34:
							testfacultyEditBook(studentEmail, facultyEmail, PFSurl); //TC-34
							break;
						case 35:
							testfacultyEditProfessionalAssociation(studentEmail, facultyEmail, PFSurl); //TC-35
							break;
						case 36:
							testfacultyOthers(studentEmail, facultyEmail, PFSurl); //TC-36
							break;
						case 37:
							testFacultyDashboard(studentEmail, facultyEmail, PFSurl); //TC-37
							break;
						case 38:
							testFacultyQuestionPaper(studentEmail, facultyEmail, PFSurl); //TC-38
							break;
						case 39:
							testFacultySignout(PFSurl); //TC-39
							break;
						default:
							throw Exception;
					}
				}
				log.info("FACULTY PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
			} else if ("both".equals(Role)) {
				for(int i=from;i<=to;i++){
					switch (i){
						case 40:

					}
				}
				for(int i=from;i<=to;i++){
					switch (i){
						case 40:
							testSpreadsheetCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-40
							break;
						case 41:
							testPPTCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-41
							break;
						case 42:
							testPDFCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-42
							break;
						case 43:
							testVideoCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-43
							break;
						case 44:
							testLinkCreateViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-44
							break;
						case 45:
							testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-45
							break;
						case 46:
							testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-46
							break;
						case 47:
							testForumCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl, Browser, Role); //TC-47
							break;
						default:
							throw Exception;
					}
				}
				
			}else if ("pfs_func".equals(Role)) {
			}
			// After all test are over close the browser
			quitDriver(PFSurl);
			log.info("*****************Completed testing of portal" + PFSurl);
		}
		SendMail.sendEmail(logFileName);
	}

	@BeforeSuite
	public static void initDriver(String Browser, String url, String Role) throws Exception {
		System.out.println("Browser is "+Browser);
			System.out.println("URL is "+url);
		try {
			System.out.println("Browser is ****"+Browser);
			System.out.println("URL is "+url);
			if ("chrome".equals(Browser)) {
				System.setProperty("webdriver.chrome.driver",
						"C:\\Users\\Public\\Documents\\edgedriver_win64\\chromedriver.exe");
				ChromeOptions op = new ChromeOptions();
				op.addArguments("--disable-notifications");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(op);
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\edgedriver_win64\\msedgedriver.exe");
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\geckodriver-v0.31.0-win64\\geckodriver.exe");
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
			// SendMail.sendEmail(logFileName);
			System.exit(01);

		}
	}
	

	@Test(priority = 1)
	public static void testStudent(String url) throws Exception {
		try {
			System.out.println(" TC-1:  Student Starting Home tab  case execution");
			Utils.clickXpath(driver, ActionXpath.Stu_prName, time, "Click on Login initial");
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
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning(" TC-1: Student Home tab test case FAILED \n");
		}
	}

	@Test(priority = 2)
	public static void testStudentEnrollment(String url) throws Exception {
		try {
			System.out.println(" TC-2:  Starting Student Enrollment  case execution");
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC-2 Student Enrollement Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			Utils.goBackToHome(driver, url); 
			Utils.clickXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			Utils.clickXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
			Utils.scrollUpOrDown(driver, 2000);
			Utils.clickXpath(driver, ActionXpath.ClickOpenEnroll, time, "Go to the open Enrollement");
			Utils.clickXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			log.info("TC-2: Enrollment of the Student Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-2: Enrollment of the Student Test Case FAILED \n");
		}
	}

	@Test(priority = 3)
	public static void testStudentAcademic(String url) throws Exception {
		try {
			System.out.println("TC-3: Starting Student Academic  test case execution\n");
			Utils.goBackToHome(driver, url); 
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaAcademic, time, "Exapand Academic ");
				Utils.clickXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				Utils.scrollUpOrDown(driver, 2000);
				Utils.clickXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Utils.clickXpath(driver, ActionXpath.ltstaAcademic, time, "Close Academic Expand");
			}else {
				Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
				Utils.clickXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
				Utils.clickXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
				Utils.clickXpath(driver, ActionXpath.CloseAcademicExapand, time, "Close Academic Expand");
			}
			log.info("TC-3: Student Academic Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			driver.navigate().to(url);
			log.warning("TC-3: Student Academic Test Case FAILED \n");
		}
	}

	@Test(priority = 4)
	public static void testStudentExamination(String url) throws Exception {
		try {
			System.out.println("TC-4:   Starting Student Examination test case execution");
			Utils.goBackToHome(driver, url);
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC-4: Exam tab Skipping as this is not applicable for this portal\n");
				return;
			}
			if(Utils.checkLtsta(url)){
				//Do nothing
			}else {
				Utils.clickXpath(driver, ActionXpath.ClickExam, time, "Click Exams");
			}
			Utils.clickXpath(driver, ActionXpath.examAnnouncements, time, "Click on Announcement");
			log.info("TC-4: Student Examination Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-4: Student Examination Test Case FAILED \n");
		}
	}

	@Test(priority = 5)
	public static void testStudentAttendance(String url) throws Exception {
		try {
			System.out.println("TC-5: Starting Student Attendance test case execution");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaAttendance, time, "Select the Attendance");
			}else {
				Utils.clickXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
			}
			Utils.clickXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
			log.info("TC-5: Student Attendance Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-5: 	Student Attendance  Test Case FAILED \n");
		}
	}

	@Test(priority = 6)
	public static void testStudentTimeTable(String url) throws Exception {
		try {
			System.out.println("TC-6: Starting Student Timetable test case execution ");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaTimetable, time, "Select time table");
			}else {
				Utils.clickXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
			}
			Utils.clickXpath(driver, ActionXpath.TimeTableMonth, time, "Select Month view");
			Utils.clickXpath(driver, ActionXpath.TimeTableWeek, time, "Selecte Week view");
			Utils.clickXpath(driver, ActionXpath.TimeTableDay, time, "Select Day view");
			log.info("TC-6:   Student Timetable Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-6:  Student Timetable Test Case Case FAILED \n");
		}
	}

	@Test(priority = 7)
	public static void testStudentFees(String url) throws Exception {
		try {
			System.out.println("TC-7:  Starting Student FEES test case execution");
			Utils.goBackToHome(driver, url);
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC-7: Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
				Utils.clickXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				Utils.clickXpath(driver, ActionXpath.ltstaFees, time, "Expand the Fees");
			}else {
				Utils.clickXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
				Utils.clickXpath(driver, ActionXpath.clickFeeSchedule, time, " Click Fee Schedule");
				Utils.clickXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
			}
			log.info("TC-7: Student Fees Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-7: Student Fees Test Case FAILED \n");
		}
	}

	@Test(priority = 8)
	public static void testStudentFeedback(String url) throws Exception {
		try {
			System.out.println("TC-8:   Starting Student FEEDBACK test case execution");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstafeedBack, time, "FeedBack");
			}else {
				Utils.clickXpath(driver, ActionXpath.feedBack, time, "FeedBack");
			}
			Utils.clickXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
			log.info("TC-8: Student FEEDBACK tab Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-8: Student FEEDBACK tab Test Case FAILED \n");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus(String url) throws Exception {
		try {
			System.out.println("TC-9:  Starting  Student Services check cancel button test case execution ");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Utils.clickXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			Utils.clickXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
			Utils.clickXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Utils.clickXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Utils.clickXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
			log.info("TC-9: Student Services check cancel button Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning(" TC-9: Student Services check cancel button Test Case FAILED \n ");
		}
	}

	@Test(priority = 10)
	public static void testStudentRaiseCase(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-10: Starting Student Services Raise test case execution \n");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Utils.clickXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			
			Utils.clickXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Utils.clickXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Utils.callSendkeys(driver, ActionXpath.inputraise, "WIfi not working ", time);
			Utils.callSendkeys(driver, ActionXpath.description,
					"While i Select the network to check the Exam date and all that time i did not found the exam date & also tab ewas not working",
					time);
			Utils.clickXpath(driver, ActionXpath.submitofcase, time, "Submit the case");
			log.info(" TC-10: Student Service Raise case Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning(" TC-10: Student Service Raise case Test Case FAILED \n");
		}
	}

	@Test(priority = 11)
	public static void testStudentMakeRequest(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-11 Starting Student Services make request test case execution ");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaService, time, "Student Status");
			}else {
				Utils.clickXpath(driver, ActionXpath.StudentService, time, "Student Status");
			}
			Utils.clickXpath(driver, ActionXpath.MakeRaise, time, "Click the button Make raise");
			Utils.clickXpath(driver, ActionXpath.makeRequest, time, "selet the Button Make request");
			Utils.callSendkeys(driver, ActionXpath.enterSubject, "The Attendance Request for 2 days", time);
			Utils.callSendkeys(driver, ActionXpath.desc,
					"I need to apply the leave for the 2 days because of some personal issue so plz approve the request ",
					time);
			Utils.clickXpath(driver, ActionXpath.makeSubmit, time, "Submit the make requst option");
			log.info("TC-11: Student services make request test case PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-11: Student services make request test Case FAILED \n");
		}
	}

	@Test(priority = 12)
	public static void testStudentEvent(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-12:  Starting Student Event case Execution ");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstaEvent, time, "Event");
			}else {
				Utils.clickXpath(driver, ActionXpath.Event, time, "Event");
			}
			Utils.clickXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
			Utils.clickXpath(driver, ActionXpath.back, time, "Go back");
			log.info("TC-12: Student EVENT Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-12: Student EVENT Test Case  FAILED \n");
		}
	}

	@Test(priority = 13)
	public static void testStudentEditProfile(String url) throws Exception{
		try {
			System.out.println("TC-13: Starting execution of edit basic details of student profile");
			Utils.goBackToHome(driver, url);
			Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Stu_prName, time, "Click on Initial icon");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.stuprofile, time, "Click on profile button");
			if(Utils.checkLtsta(url)){
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.stubasicedit, time, "Click on edit SVG");
			Utils.clickXpath(driver, ActionXpath.Stubasicgender, time, "Stubasicgender");
			// Automate.clickXpath(driver, ActionXpath.stubasicgenderselect, time, "stubasicgenderselect");
			String gender = "Female";
			driver.findElement(By.xpath("//li[@data-value='" + gender + "']")).click();
			Utils.callSendkeys(driver, ActionXpath.stubasicdob, "02-02-2022", time);
			Utils.callSendkeys(driver, ActionXpath.stubasicnation, "India", time);
			Utils.clickXpath(driver, ActionXpath.stubasicsave, time, "Click on Save");
			Utils.bigSleepBetweenClicks(1);
			log.info("  TC-13: Student edit profile test case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-13: Student edit profile test case FAILED \n");
		}
	}

	@Test(priority = 14)
	public static void testStudentEditEducationDetails(String url) throws Exception {
		try {
			System.out.println(" TC-14 :   Starting student edit profile education Details case execution");
			Utils.goBackToHome(driver, url);
			Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Stu_prName, time, "Click on profile Icon");
			Utils.clickXpath(driver, ActionXpath.stuprofile, time, "Click on profile button");
			//Utils.smallSleepBetweenClicks(1);
			if(Utils.checkLtsta(url)){
				driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
			}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,2000)");
			Utils.clickXpath(driver, ActionXpath.stueddrop, time, "Click on education");
			Utils.clickXpath(driver, ActionXpath.stued, time, "CLick on edit SVG");
			Utils.callSendkeys(driver, ActionXpath.stued12school, "stpaul", time);
			Utils.callSendkeys(driver, ActionXpath.stued12country, "India", time);
			Utils.callSendkeys(driver, ActionXpath.stued12year, "2017", time);
			Utils.callSendkeys(driver, ActionXpath.stuedclg, "SRKV", time);
			Utils.callSendkeys(driver, ActionXpath.stuedclgcountry, "India", time);
			Utils.callSendkeys(driver, ActionXpath.stuedclgyear, "2020", time);
			Utils.callSendkeys(driver, ActionXpath.stuedpgclg, "SRKV", time);
			Utils.callSendkeys(driver, ActionXpath.stuedpgcountry, "India", time);
			Utils.callSendkeys(driver, ActionXpath.stuedpgyear, "2022", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.stuedsave, time, "Click on save");
			Utils.smallSleepBetweenClicks(1);
			Utils.bigSleepBetweenClicks(1);
			log.info("  TC-14: Student profile edit Education Details test case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-14: Student profile edit Education Details test case FAILED \n");
		}
	}

	@Test(priority = 15)
	public static void testStudentEditAddress(String url) throws Exception {
		try {
			if (Utils.checkltpct(url)){
				log.info("TC-15: Student Address Edit is not supported on this portal");
				return;
			}
			System.out.println(" TC-15:   Starting student prfofile edit Address test case execution");
			
			Utils.goBackToHome(driver, url);
			Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Stu_prName, time, "profile");
			Utils.clickXpath(driver, ActionXpath.stuprofile, time, "stuprofile");
			//Utils.smallSleepBetweenClicks(1);
            if(Utils.checkLtsta(url)){
                driver.findElement(By.xpath(ActionXpath.stuprofile)).sendKeys(Keys.TAB);
          	}
			JavascriptExecutor js14 = (JavascriptExecutor) driver;
			js14.executeScript("window.scrollBy(0,3500)");
			Utils.clickXpath(driver, ActionXpath.stuadddrop, time, "stuadddrop");
			////Utils.bigSleepBetweenClicks(1);

			Utils.clickXpath(driver, ActionXpath.stuedit, time, "stuedit");
			Utils.clickXpath(driver, ActionXpath.stuaddadd, time, "stuaddadd");
			Utils.callSendkeys(driver, ActionXpath.stuhouse, "SAMPLE", time);
			Utils.callSendkeys(driver, ActionXpath.sturoad, "SAMPLE", time);
			Utils.callSendkeys(driver, ActionXpath.stusuburb, "SAMPLE", time);
			Utils.callSendkeys(driver, ActionXpath.stucountry, "India", time);
			Utils.callSendkeys(driver, ActionXpath.stupincode, "600001", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.stusave, time, "stusave");
			Utils.smallSleepBetweenClicks(1);
			Utils.bigSleepBetweenClicks(1);
			log.info("  TC-15: Student profile edit address detail test case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-15: Student profile edit address detail test case FAILED \n");
		}
	}

	@Test(priority = 16)
	public static void testStudentSignout(String url) throws Exception {
		try {
			System.out.println("TC-16 :     Starting Student SIGNOUT  case execution ");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
			Utils.clickXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
			log.info(" TC-16: Student SIGNOUT Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-16: Student SIGNOUT Test Case FAILED \n");
		}
	}

	//////////////////////// FACULTY PORTAL
	//////////////////////// STARTED/////////////////////////////////////

	@Test(priority = 17)
	public static void testFaculty(String url) throws Exception {
		try {
			System.out.println("TC-17:  Starting FACULTY PORTAL Academic tab test case executation\n");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
            {
				Utils.clickXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
			} else {
            	Utils.clickXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
            }
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "click on the dashboard");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facFilter, time, "click the dashboard activity filiter span bar");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdbfilterselect, time, "select the factivity filter option");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdbresfilter, time, "click on the resources filter");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdbrestypes, time, "click Type to open span");
			
			log.info("TC-17: Faculty ACADEMIC Test case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-17: Faculty ACADEMIC Test case FAILED \n");
		}
	}

	@Test(priority = 18)
	public static void testFacultyQuestionBank(String url) throws Exception {
		try {
			System.out.println(" TC-18:  Faculty Starting QuestionBank Tab test case Executation");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
           	{
				//Utils.smallSleepBetweenClicks(1);
				Utils.clickXpath(driver, ActionXpath.facClickacademicsltsta, time, "open the span on Academics");
				 }
           	else {
           		Utils.clickXpath(driver, ActionXpath.openFacdevnosbm, time, "open the acadmics for nsom & bmtech");    
           	}
			Utils.clickXpath(driver, ActionXpath.facqb, time, "click  the Question bank");
			Utils.clickXpath(driver, ActionXpath.facaddque, time, "clcik on the add Question manualy");
			Utils.clickXpath(driver, ActionXpath.facqueback, time, "go back");
			log.info("TC-18 : Faculty QuestionBank click BACK button Test Case PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-18: Faculty QuestionBank click BACK button Test Case FAILED \n");
		}
	}


	@Test(priority = 19)
	public static void testFacultyCourseContent(String url) throws Exception {
		try {
			Utils.goBackToHome(driver, url);
			System.out.println("TC-19: Faculty Course Content Test Execution  Started ");
			if(Utils.checkLtsta(url))
            {
                Utils.clickXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Utils.clickXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "click on the Course content");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccactivity, time, "clck on activity button ");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facassessmentrelative, time, "select the activity option named was fourm");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "click to add fourm");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccAsscancel, time, "cancel it ");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faccAssYes, time, "confirm to cancel");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "click on the course content");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.FaccClickResource, time, "Click Resource");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facrescancel, time, "cancel the resources");
			log.info("TC-19:  Faculty Course Content Test PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-19:  Faculty Course Content Test FAILED \n");
		}
	}

	@Test(priority = 20)
	public static void testFacultyExamination(String url) throws Exception {
		try {
			System.out.println("TC-20:     Faculty Examination Test Executation Statred");
			Utils.goBackToHome(driver, url);
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC_20 Faculty Examination Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
				Utils.clickXpath(driver, ActionXpath.facexam, time, "Click on the Examination span");
				Utils.clickXpath(driver, ActionXpath.facexamarrow, time, "facexamarrow");
				Utils.clickXpath(driver, ActionXpath.facexamdropdown, time, "Examination naroow dropdown");
				Utils.clickXpath(driver, ActionXpath.facexamexam, time, "facexamexam");
				Utils.clickXpath(driver, ActionXpath.facexamdate, time, "facexamdate");
				Utils.clickXpath(driver, ActionXpath.faceexamtime, time, "faceexamtime");
				log.info("TC-20: Faculty Examanation test cases PASSED... \n ");
			} catch (Exception e) {
				Utils.printException(e);
				Utils.goBackToHome(driver, url);
				log.warning("TC-20: Faculty Examanation test cases FAILED \n");
			}
	}

	@Test(priority = 21)
	public static void testFacultyMYStudent(String url) throws Exception {
		try {
			System.out.println("TC-21:   Faculty My Students Test Executation Started");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
            {
				Utils.clickXpath(driver, ActionXpath.faccMyStudentltsta, time, "open the my student on ltsta");
				  }
            else {
            	Utils.clickXpath(driver, ActionXpath.faccMyStudent, time, "open the commom for all portal expect ltsta");    
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
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-21: Faculty My Student  tab test case FAILED \n");
		}
	}

	@Test(priority = 22)
	public static void testFacultyAttendance(String url) throws Exception {
		try {
			System.out.println("TC-22 :    Faculty Attendance Test Executation Startred ");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
            {
				Utils.clickXpath(driver, ActionXpath.facattendanceforltsta, time, "click Attendance tab");
				  }
            else {
            	Utils.clickXpath(driver, ActionXpath.facatt, time, "click Attendance tab");    
            }
			Utils.clickXpath(driver, ActionXpath.faccAttendahis, time, "Click attendance history");
			log.info("TC-22 : Faculty Attendance Test Executation PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-22 : Faculty Attendance Test case FAILED \n");
		}
	}

	@Test(priority = 23)
	public static void testFaculityTimetable(String url) throws Exception {
		try {
			System.out.println("TC-23 :    Faculty Timetable Test Executation Started ");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url)) {
				Utils.clickXpath(driver, ActionXpath.facClickTimetableltsta, time, "facClickTimetable");
			} else {
            	Utils.clickXpath(driver, ActionXpath.facClickTimetable, time, "facClickTimetable");    
            }
			Utils.clickXpath(driver, ActionXpath.facttmonth, time, "facttmonth");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facttweek, time, "facttweek");
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facttday, time, "facttday");
			//Utils.smallSleepBetweenClicks(1);
			log.info("TC-23 : Faculty Timetable test case PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-23 : Faculty Timetable test case FAILED \n");
		}
	}

	@Test(priority = 24)
	public static void testFacultyService(String url) throws Exception {
		try {
			System.out.println("TC-24:  Faculty Service Test case Started");
			Utils.goBackToHome(driver, url);
			Utils.bigSleepBetweenClicks(1);
			Utils.clickOnFacultyService(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.FacRaisebutton, time, "Click on Raise case button");
			Utils.smallSleepBetweenClicks(1);
			// Utils.scrollUpOrDown(driver, 300);
			Utils.clickXpath(driver, ActionXpath.facCancelSer, time, "cancel");
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-24: Faculty Service test cancel button Test case PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-24: Faculty Service test cancel button Test case FAILED \n");
		}
	}

	@Test(priority = 25)
	public static void testFacultyRaiseCase(String student, String faculty, String url) throws InterruptedException {
		try {
			System.out.println("TC-25 Faculty Service Raise A Case ");
			Utils.goBackToHome(driver, url);
			Utils.clickOnFacultyService(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.FacRaisebutton, time, "Raise a case button");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.inputSub, "Regd Error on Inviligation Secation", time);
			Utils.callSendkeys(driver, ActionXpath.FacDesc,
					"while i need to regd on the inviligation section m unable to do bcz its showing the system admin Error Sever not availbale 404 error.",
					time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.SubmitRaise, time, "Submit the case");
			log.info("TC 25: Faculty service Status  Raise Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC 25: Faculty service Status  Raise Case FAILED \n");
		}
	}

	@Test(priority = 26)
	public static void testFacultyMakeRequest(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-26: Starting Faculty make a request test case");
			Utils.goBackToHome(driver, url);
			Utils.clickOnFacultyService(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facMakeRButtondevNsome, time, "Click on Make a request button");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facMakeReqButtonSecond, time, "Click on Second Make a request button");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.makeSubjectIn, "5 days Leave Request ", time);
			Utils.callSendkeys(driver, ActionXpath.makedesc,
					"hi ...i want to take the 5 days leave bcz of some helath issue  m not availbe on this days some medical emergency plz approved my rqst... Thanks & regards Aditya .",
					time);
			Utils.clickXpath(driver, ActionXpath.MakeBtn, time, "Submit the Request");
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-26: Faculty service Make a request PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-26: Faculty service make a request Case FAILED \n");
		}
	}

	@Test(priority = 27)
	public static void testFacultyEvent(String url) throws Exception {
		try {
			System.out.println("TC-27: Faculty Portal Event Tab Test case Started");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url)) {
				Utils.clickXpath(driver, ActionXpath.faccEventltsta, time, "facEvent");
			} else {
            	Utils.clickXpath(driver, ActionXpath.faccEvent, time, "facEvent");
    		}
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faceventlocation, time, "faceventlocation");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faceventlocationselect, time, "faceventlocationselect");
			Utils.callSendkeys(driver, ActionXpath.FaccSearch, "Ganesh", time);
			log.info("TC-27: Faculty Event Test case Executation PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-27: Faculty Event Test case FAILED \n");
		}
	}

	@Test(priority = 28)
	public static void testfacultyEditProfile(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-28:   Faculty Starting PersonalDetails Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "Select faculty initial icon");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "click on profile");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpersonal, time, "facpersonal");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdedit, time, "facpdedit");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdgender, time, "facpdgender");
			Utils.clickXpath(driver, ActionXpath.facpdgenderselect, time, "facpdgenderselect");
			Utils.callSendkeys(driver, ActionXpath.facpddob, "02-02-2020", time);
			Utils.callSendkeys(driver, ActionXpath.facpdnationality, "INDIA", time);
			Utils.clickXpath(driver, ActionXpath.facdpsave, time, "facdpsave");
			////Utils.bigSleepBetweenClicks(1);
			log.info("  TC-28: Faculty Starting PersonalDetails Completed test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			Thread.sleep(time);
			log.warning("TC-28: Faculty Starting PersonalDetails test case FAILED \n");
		}
	}

	@Test(priority = 29)
	public static void testfacultyEditAddress(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-29 :   Faculty Address Details Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.address, time, "addressdetais");
			Utils.clickXpath(driver, ActionXpath.facdpaddedit, time, "facdpaddedit");
			Utils.clickXpath(driver, ActionXpath.facdptype, time, "facdptype");
			Utils.clickXpath(driver, ActionXpath.FaccfaccTypeSelect, time, "facdptypeselect");
			Utils.callSendkeys(driver, ActionXpath.faccAddress, "Coimbatore", time);
			Utils.callSendkeys(driver, ActionXpath.faccPincode, "600001", time);
			Utils.clickXpath(driver, ActionXpath.facccountry, time, "facdpcountry");
			Utils.clickXpath(driver, ActionXpath.faccSelectCountry, time, "facdpcountrysselect");
			//Automate.clickXpath(driver, ActionXpath.faccState, time, "facdpstate");
			//Utils.smallSleepBetweenClicks(1);
			//Automate.clickXpath(driver, ActionXpath.faccSelectState, time, "facdpstateselect");
			//Utils.smallSleepBetweenClicks(1);
			//Automate.clickXpath(driver, ActionXpath.faccCity, time, "facdpdist");
			//Utils.smallSleepBetweenClicks(1);
			//Automate.clickXpath(driver, ActionXpath.faccSelectCity, time, "facdpdistselect");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.faccSaveaddress, time, "facdpaddsave");
			////Utils.bigSleepBetweenClicks(1);
			log.info(" TC-29: Faculty edit Address Details Completed test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-29: Faculty edit Address Details test case FAILED \n");
		}
	}

	@Test(priority = 30)
	public static void testfacultyEditAcademicDetails(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-30:   Academic Details Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.facdpacdeails, time, "facdpacdeails");
			Utils.clickXpath(driver, ActionXpath.facdpacadd, time, "facdpacadd");
			Utils.clickXpath(driver, ActionXpath.facdplevel, time, "facdplevel");
			Utils.clickXpath(driver, ActionXpath.facdplevelselect, time, "facdplevelselect");
			Utils.clickXpath(driver, ActionXpath.facdpadcountry, time, "facdpadcountry");
			Utils.clickXpath(driver, ActionXpath.facdpadcountryselect, time, "facdpadcountryselect");
			Utils.callSendkeys(driver, ActionXpath.facdpaduniversity, "ANNA", time);
			Utils.callSendkeys(driver, ActionXpath.facdpadyear, "2020", time);
			Utils.smallSleepBetweenClicks(1);
			log.info(" TC-30 : Academic Details Completed test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-30 : Academic Details test case FAILED \n");
		}
	}

	@Test(priority = 31)
	public static void testfacultyEditReaserchSupervision(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC:31 :   RESEARCH SUPERVISION Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facclickonT");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facclickonprofile, time, "facclickonprofile");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISION, time,
					" facclickonRESEARCHSUPERVISION");
			Utils.clickXpath(driver, ActionXpath.facdpreedit, time, "facdpreedit");
			Utils.clickXpath(driver, ActionXpath.facdpreadd, time, "facdpreadd");
			Utils.callSendkeys(driver, ActionXpath.facdprename, "Sample", time);
			Utils.callSendkeys(driver, ActionXpath.facdprelink, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdpredesc, "Sample Desc", time);
			Utils.clickXpath(driver, ActionXpath.faccSaveexp, time, "facdpresave");
			Utils.smallSleepBetweenClicks(1);
			log.info(" TC-31:  Faculty edit profile RESEARCH SUPERVISION  test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-31 : Faculty edit profile RESEARCH SUPERVISION  test case test case FAILED \n");
		}
	}

	@Test(priority = 32)
	public static void testfacultyEditResearchPublication(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-32 :   RESEARCH PUBLICATION Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONpublish, time,
					"facclickonRESEARCHSUPERVISIONpublish");
			Utils.clickXpath(driver, ActionXpath.facdpreeditpublish, time, "facdpreeditpublish");
			Utils.clickXpath(driver, ActionXpath.facdpreaddpublish, time, "facdpreaddpublish");
			Utils.callSendkeys(driver, ActionXpath.facdppubname, "Surya", time);
			Utils.callSendkeys(driver, ActionXpath.facdppublink, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdppubdesc, "Sample Desc", time);
			Utils.smallSleepBetweenClicks(1);
			log.info("  TC-32: Faculty edit RESEARCH PUBLICATION  test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-32: Faculty edit RESEARCH PUBLICATION test case FAILED \n");
		}
	}

	@Test(priority = 33)
	public static void testfacultyEditConference(String student, String faculty, String url) throws Exception {
		try {

			System.out.println(" TC-33 :   Faculty edit Conference Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			
			Utils.clickXpath(driver, ActionXpath.facclickonRESEARCHSUPERVISIONconfernece, time,
					"facclickonRESEARCHSUPERVISIONconfernece");
			Utils.clickXpath(driver, ActionXpath.facdpreeditconfernece, time, "facdpreeditconfernece");
			Utils.clickXpath(driver, ActionXpath.facdpreaddconfernece, time, "facdpreaddconfernece");
			Utils.callSendkeys(driver, ActionXpath.facdpconnameconfernece, "Sample", time);
			Utils.callSendkeys(driver, ActionXpath.facdpconlinkconfernece, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdpcondescconfernece, "Sample Desc", time);
			Utils.clickXpath(driver, ActionXpath.facdpconsaveconfernece, time, "facdpconsave");
			Utils.smallSleepBetweenClicks(1);
			log.info(" TC-33 : Faculty edit Conference Completed test case PASSED \n\n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			Thread.sleep(time);
			log.warning("TC-33 : Conference test case FAILED \n\n");
		}
	}

	@Test(priority = 34)
	public static void testfacultyEditBook(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC:34 :   Book Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdpbook, time, "facdpbook");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdpbookedit, time, "facdpbookedit");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facdpbookadd, time, "facdpbookadd");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.facdpbookname, "Sample", time);
			Utils.callSendkeys(driver, ActionXpath.facdpbooklink, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdpbookdesc, "Sample Desc", time);
			Utils.clickXpath(driver, ActionXpath.faccSaveNsombm, time, "faccSaveNsombm");
			Utils.bigSleepBetweenClicks(1);
			log.info(" TC-34: Faculty edit Book Completed test case PASSED  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-34: Faculty edit Book test case FAILED \n");
		}
	}

	@Test(priority = 35)
	public static void testfacultyEditProfessionalAssociation(String student, String faculty, String url) throws Exception {
		try {
			Utils.goBackToHome(driver, url);
			System.out.println(" TC-35 :   Professional Association Started  case executation");
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.facdpprof, time, "facdpprof");
			Utils.clickXpath(driver, ActionXpath.facdpprofedit, time, "facdpprofedit");
			Utils.clickXpath(driver, ActionXpath.facdpprofadd, time, "facdpprofadd");
			Utils.callSendkeys(driver, ActionXpath.facdpprofname, "Sample", time);
			Utils.callSendkeys(driver, ActionXpath.facdpproflink, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdpprofdesc, "Sample Desc", time);
			Utils.clickXpath(driver, ActionXpath.facdpprofsave, time, "facdpprofsave");
			Utils.smallSleepBetweenClicks(1);
			log.info("  TC-35 : Faculty profile edit Professional Association Completed test case PASSED..  \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-35 : Faculty profile edit Professional Association test case FAILED \n\n");
		}
	}

	@Test(priority = 36)
	public static void testfacultyOthers(String student, String faculty, String url) throws Exception {
		try {
			System.out.println(" TC-36 :   Faculty edit Others Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.facdpother, time, "facdpother");
			Utils.clickXpath(driver, ActionXpath.facdpotheredit, time, "facdpotheredit");
			Utils.clickXpath(driver, ActionXpath.facdpotheradd, time, "facdpotheradd");
			Utils.callSendkeys(driver, ActionXpath.facdpothername, "Sample", time);
			Utils.callSendkeys(driver, ActionXpath.facdpotherlink, "https://portal-dev.ken42.com", time);
			Utils.callSendkeys(driver, ActionXpath.facdpotherdesc, "Sample Desc", time);
			Utils.clickXpath(driver, ActionXpath.facdpothersave, time, "facdpothersave");
			Utils.smallSleepBetweenClicks(1);
      		log.info(" TC-36: Faculty edit Others Completed test case PASSED \n\n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-36 : Faculty edit Others test case FAILED \n\n");
		}
	}

	@Test(priority = 37)
	public static void testFacultyDashboard(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-37 Faculty DASHBOARD test executation \n");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
            {
                Utils.clickXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Utils.clickXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			//Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "Dashboard");
			
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			// if (p.contains("Overview") && p.contains("Activities")){
			// 	log.info(" TC-37: Faculty My Student  tab test case PASSED \n\n");
			// }else {
			// 	log.warning(" TC-37: Faculty My Student  tab test case FAILED it does not contain all the tabs\n\n");
			// }
			// log.info("TC-37:  Faculty Dashboard test case PASSED \n\n");

		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-37: Faculty DASHBOARD  FAILED \n");
		}
	}

	@Test(priority = 38)
	public static void testFacultyQuestionPaper(String student, String faculty, String url) throws Exception {
		try {
			System.out.println("TC-38 Faculty QUESTION PAPER test executation \n");
			Utils.goBackToHome(driver, url);

			if(Utils.checkLtsta(url))
            {
                Utils.clickXpath(driver, ActionXpath.facClickacademicsltsta, time, "open academics sapn on the ltsta");    
            }
            else {
            	Utils.clickXpath(driver, ActionXpath.openFacltsta, time, "open span on acadmics on the ltsta");    
            }
			Utils.clickXpath(driver, ActionXpath.facqb, time, "Question ");
			Utils.clickXpath(driver, ActionXpath.facaddmanual, time, "Add anual");
			Utils.clickXpath(driver, ActionXpath.facquesub, time, "facqueclassselect");

			Utils.clickXpath(driver, ActionXpath.facquesubselect, time, "Question Bank Select SUbject ");
			Utils.clickXpath(driver, ActionXpath.faccnext, time, "Next");
			Utils.callSendkeys(driver, ActionXpath.faccquestion,"Question", time);
			Utils.callSendkeys(driver, ActionXpath.faccquestionname,"Question time", time);
			Utils.cleartext(driver, ActionXpath.faccmarks);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.faccmarks, "1", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.faccoption1, "modi", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.feedback1, "Super", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.faccoption2, "sachin", time);
			Utils.callSendkeys(driver, ActionXpath.feedback2, "vg", time);
			Utils.callSendkeys(driver, ActionXpath.faccoption3, "anand", time);
			Utils.callSendkeys(driver, ActionXpath.feedback3, "good", time);
			Utils.clickXpath(driver, ActionXpath.numberofchoice, time, "No of chocice");
			Utils.callSendkeys(driver, ActionXpath.feedbackofcrtans, "super", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.feefbacofincorrect, "improve", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.generalfeedback, "gain know", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facsaveandfinish, time, "Finished");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facqueback, time, "BAck");
			log.info("TC-38: Faculty QUESTION PAPER TEST CASE PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-38: Faculty QUESTION PAPER  FAILED \n");
		}
	}

	@Test(priority = 39)
	public static void testFacultySignout(String url) throws Exception {
		try {
			System.out.println(" TC-39:  Faculty  View Profile test Executation Started");
			Utils.goBackToHome(driver, url);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facselectpro");
			Utils.clickXpath(driver, ActionXpath.facprofile, time, "facprofile");

			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "Click of faculty pic");
			Utils.clickXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info(" TC-39: Faculty View Profile and Sign out Test Case PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
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
			if (Utils.checkBimtech(url)){
				log.info("TC-40 Spreadsheet is not supported on this portal");
				return;
			}
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facssadd, time, "facssadd");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Excel_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			 driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys("C:\\Users\\Public\\Documents\\demo.xlsx");
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "Click on SS SVG");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[. ='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			// Automate.clickXpath(driver, ActionXpath.facsspublish, time, "faclinkpublish");
			Utils.clickXpathWithScroll(driver, ActionXpath.facsspublish, time, "faclinkpublish");
			Utils.clickXpath(driver, ActionXpath.facsspublishyes, time, "faclinkpublishyes");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");

			Utils.clickXpath(driver, ActionXpath.viewss, time, "viewss");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "View Spreadsheet");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.smallSleepBetweenClicks(1);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);
			//
			Utils.login(driver, faculty);
			////Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpathWithScroll(driver, ActionXpath.facssopen, time, "facspreadsheetopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "facspreadsheetdelete");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "facspreadsheetdelete2");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			log.info("TC-40: SpreadSheet resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-40: SpreadSheet resource Create View delete Test Case FAILED \n");
		}
	}

	

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			////Utils.bigSleepBetweenClicks(1);
			// add ppt publish and signout
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpptadd, time, "facpptadd");
			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Utils.bigSleepBetweenClicks(2);
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "PPT_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			// publishppt
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpathWithScroll(driver, ActionXpath.facpptpublish, time, "facpptpublish");
			Utils.clickXpath(driver, ActionXpath.facpptpublishyes, time, "facpptpublishyes");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);
			

			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpathWithScroll(driver, ActionXpath.viewppt, time, "viewppt");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.logout(driver, url, Role);
			Utils.bigSleepBetweenClicks(1);
			
			Utils.login(driver,faculty);
			// unpublish ppt and delete ppt
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			log.info("TC-41: PPT resource Create View delete Test Case PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-41: PPT resource Create View delete Test Case FAILED \n");

		}

	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-42:  Create PDF resource publish and delete PDF");

			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			// add pdf
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			Utils.clickXpath(driver, ActionXpath.facccresadd, time, "facccresadd");

			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "PDF_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Thread.sleep(15000);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			// publishpdf
			Utils.clickXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpublishpdf, time, "facpublishpdf");
			Utils.clickXpath(driver, ActionXpath.facpublishpdf2, time, "facpublishpdf2");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			// signout
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//Now verify in student
			Utils.login(driver, student);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			// Verify PDF creation and Utils.logout
			Utils.clickXpathWithScroll(driver, ActionXpath.viewpdf, time, "viewpdf");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpathWithScroll(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			// signout
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			Utils.login(driver, faculty);
			// unpublish pdf and delete pdf
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			Utils.clickXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpathWithScroll(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			log.info("TC-42: Create PDF resource publish and delete PDF PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-42: Create PDF resource publish and delete PDF FAILED \n");

		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-43:  Create Video resource create view  and delete");
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			// add video publish and signout
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
			Utils.clickXpath(driver, ActionXpath.facvideoadd, time, "facvideoadd");

			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Video_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.mp4");
			Thread.sleep(15000);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facvideopublish, time, "facvideopublish");
			Utils.clickXpath(driver, ActionXpath.facvideopublishyes, time, "facvideopublishyes");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//Student to verify
			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.smallSleepBetweenClicks(1);
			// Verify video creation
			Utils.clickXpathWithScroll(driver, ActionXpath.viewvideo, time, "viewvideo");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//Faculty to delete
			Utils.login(driver, faculty);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			// unpublish video and delete video
			
			Utils.clickXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "facvideodelete");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			log.info("TC-43: Create Video resource create view  and delete PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			quitDriver(url);
			initDriver(Browser, url, Role);
			log.warning("TC-43: Create Video resource create view  and delete FAILED \n");

		}
	}

		
	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try {
			System.out.println("TC-44:  Link resource Create View delete Test case Started");
			Utils.login(driver,faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			// add link publish and signout
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.faclinkclick, time, "faclinkclick");
			Utils.clickXpath(driver, ActionXpath.faclinkadd, time, "faclinkadd");

			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
		
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			String fileName = "Link_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.faclinkexternal, url, time);
			Utils.smallSleepBetweenClicks(1);
			// Utils.scrollUpOrDown(driver, -100);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			
			////Utils.bigSleepBetweenClicks(1);
			// publishlink
			// Automate.clickXpathWithScroll(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// Automate.clickXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			// ////Utils.bigSleepBetweenClicks(1);
			// Automate.clickXpath(driver, ActionXpath.faclinkpublish, time, "faclinkpublish");
			// Automate.clickXpath(driver, ActionXpath.faclinkpublishyes, time, "faclinkpublishyes");
			Utils.logout(driver, url, Role);

			Utils.login(driver, student);
			// Verify link creation and Utils.logout
			////Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");

			Utils.clickXpathWithScroll(driver, ActionXpath.viewlink, time, "viewlink");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			// Automate.clickXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			////Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.logout(driver, url, Role);
			Utils.bigSleepBetweenClicks(1);

			Utils.login(driver, faculty);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			
			Utils.clickXpathWithScroll(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "Delete Link first button");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "Delete link second button");
			Utils.bigSleepBetweenClicks(1);
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role);
			log.info("TC-44 Link resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED \n");
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
			Utils.login(driver, faculty);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content");
			returnArray = Utils.getClassSubjectAndSection(driver);
			String program = returnArray[0];
			String converted = returnArray[1];
			
			Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.facassessmentrelativeltsta, time, "Click on assessment image");
			} else {
				Utils.clickXpath(driver, ActionXpath.facassessmentrelative, time, "Click omn Assesment");
			}
			
			Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			Utils.smallSleepBetweenClicks(1);

			

			String fileName = "Assessment_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facassesmentrelative, fileName, time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject");
			driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			Thread.sleep(2000);
			//driver.findElement(By.xpath("//li[@data-value='" + section + "']")).click();


			// Create and save assessment
			Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.facurlrelative, fileName, time);
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.facsavlinrelative, time, "facsavlin");
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.facsaverelative, time, "Save and proceed 1");
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.fachourrelative, "1", time);
			Utils.clickXpath(driver, ActionXpath.fasaverelative, time, "Save and proceed 2");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.fasokrelative, time, "fasok");

			//Add question and publish
			Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facselectrelative, time, "Select first question");
			Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facAssPublish, time, "Publish Assessment");

			Utils.bigSleepBetweenClicks(2);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);
			
			// .....................................student
			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
			Utils.smallSleepBetweenClicks(1);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);


			//// .........................Faculty delete assessment
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
					"Click on Assessment SVG");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();

			Utils.smallSleepBetweenClicks(1);

			// Automate.clickXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "Delete button 1");
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement el = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
			Thread.sleep(10000);
			Utils.clickXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2");
			Utils.bigSleepBetweenClicks(2);
			Utils.logout(driver, url, Role);
			log.info("TC-45 Assement create, publish & delete test Executation Was PASSED....\n");
		}
		catch (Exception e) {
			Utils.printException(e);
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
			Utils.login(driver, faculty);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "Click on course content");

			returnArray = Utils.getClassSubjectAndSection(driver);
			String program = returnArray[0];
			String converted = returnArray[1];

			Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			Utils.clickXpath(driver, ActionXpath.assignfacassignmentrelative, time, "Click on Assignment");
			Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			Utils.smallSleepBetweenClicks(1);

			String fileName = "Assignment_" + Utils.generateRandom();
			Utils.smallSleepBetweenClicks(1);
			Utils.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative, fileName, time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject");
			driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			Utils.smallSleepBetweenClicks(1);

			Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Utils.clickXpath(driver, ActionXpath.assignfaclinkrelative, time, "faclink");
			Utils.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave");
			Utils.smallSleepBetweenClicks(1);
			Utils.cleartext(driver, ActionXpath.assignfactotalmarksrelative);
			Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "9", time);
			WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
			el.clear();
			el.sendKeys("9");

			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacattementsrelative, time, "facattements");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacselectattemtrelative, time, "facselectattemt");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacsaveandproceedrelative, time, "facsaveandproceed");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacokrelative, time, "facok");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignexapnd1relative, time, "Exapand Assigment");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement element2 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);
			Thread.sleep(2000);
			WebDriverWait waite = new WebDriverWait(driver, 20);
			WebElement element3 = waite.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element3);
			Utils.bigSleepBetweenClicks(2);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//Verify as student
			Utils.login(driver, student);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement");
			Utils.smallSleepBetweenClicks(1);
			// Utils.scrollUpOrDown(driver, 500);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//Delete code
			Utils.login(driver,faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
			Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.smallSleepBetweenClicks(1);

			WebDriverWait ele = new WebDriverWait(driver, 20);
			WebElement elem = ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2");
			Utils.bigSleepBetweenClicks(2);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-46 Assignment create,publish & delete Was PASSED....\n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-46 Assignment create,publish & delte was FAILED....\n");
			quitDriver(url);
			initDriver(Browser, url, Role);
		}
	}

	@Test(priority = 47)
	public static void testForumCreatePublishViewDelete(String student, String faculty, String url, String Browser, String Role) throws Exception {
		try { 
			System.out.println("TC-47 Faculty Fourm create,publish Delete test case Staerted...\n");
			String returnArray[] = new String[2];
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse");
			Thread.sleep(8000);

			returnArray = Utils.getClassSubjectAndSection(driver);
			String program = returnArray[0];
			String converted = returnArray[1];

			Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity");
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.relativefacforum1ltsta, time, "Click on Forum");
			}else {
				Utils.clickXpath(driver, ActionXpath.relativefacforum1, time, "Click on Forum");
			}
			
			Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity");
			Utils.smallSleepBetweenClicks(1);

			String fileName = "Forum_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.relativefacforumname1, fileName, time);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject");
			driver.findElement(By.xpath("//li[@data-value='" + converted + "']")).click();
			//driver.findElement(By.xpath("//li[@data-value='" + section + "']")).click();

			// new Forum creation 
			Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");
			Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1,fileName, time);
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok");
			Utils.clickXpath(driver, ActionXpath.relativeformexpand1, time, "fourme expand click on arrow SVG");

			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
			// Utils.clickXpath(driver, ActionXpath.relativefaccformedot1, time, "faccformedot");
			Utils.smallSleepBetweenClicks(1);

			WebDriverWait wait5 = new WebDriverWait(driver, 20);
			WebElement element15 = wait5
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element15);
			System.out.println("click on dot and  publish 1st forum");
			Utils.smallSleepBetweenClicks(1);

			WebDriverWait waitei = new WebDriverWait(driver, 20);
			WebElement element29 = waitei
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element29);
			System.out.println("click on dot and  publish 2nd forum");
			Utils.bigSleepBetweenClicks(2);
			Utils.logout(driver, url, Role);
			Thread.sleep(5000);

			// ..............Student Login forum.......................//
			Utils.login(driver, student);
			Thread.sleep(2000);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.relativeforumlearnltsta1, time, "Select learn");
			Utils.clickXpath(driver, ActionXpath.relativeforumaexpandltsta1, time, "expand forum");
			Thread.sleep(2000);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Thread.sleep(2000);
			Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
			Utils.logout(driver, url, Role);

			//// ..................... Delete fourm.................../////
			Utils.login(driver, faculty);
			Thread.sleep(4000);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.relativeforumdacclickcouse12, time, "facclickcouse");
			Utils.clickXpath(driver, ActionXpath.relativeforumdfexpandltsta12, time, "Exapand");
			Thread.sleep(3000);
			// new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Thread.sleep(2000);

			// Utils.clickXpath(driver, ActionXpath.relativeforumfclickondotltsta12, time, "facdot");
			//Below line to click on 3 dots
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();


			Thread.sleep(2000);
			WebDriverWait waitei1 = new WebDriverWait(driver, 20);
			WebElement element291 = waitei1
					.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element291);
			System.out.println("clickon 1st delete");
			Thread.sleep(2000);
			Utils.clickXpath(driver, ActionXpath.relativedfacdele12, time, "Click on Delete 2");

			Thread.sleep(10000);

			Utils.logout(driver, url, Role);

			log.info("TC-47 Faculty Fourm create,publish Delete test case PASSED...");

		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-47 Faculty Fourm create,publish Delete test case FAILED... \n");
			quitDriver(url);;
		}
	}
	
	@AfterSuite
	public static void quitDriver(String Url) throws Exception {
		// log.info("Completed testing of portal" + Url);
		log.info("\n");
		driver.quit();
	}
}