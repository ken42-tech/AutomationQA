package com.ken42;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

import com.opencsv.CSVReader;
import java.util.logging.*;
import java.util.logging.FileHandler;

public class Pfs_portal {
	private static WebDriver driver;
	static int time = 4000;
	public static Logger log = Logger.getLogger("Pfs_portal");

	public static void main(String[] args) throws Exception {
		boolean append = false;
		FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\pfs_results.log", append);
		logFile.setFormatter(new SimpleFormatter());
		log.addHandler(logFile);
		String CSV_PATH = "C:\\Users\\Public\\Documents\\pfs2.csv";
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
			System.out.println(PFSurl);
			System.out.println((facultyEmail));
			System.out.println((studentEmail));
			System.out.println(Role);
			System.out.print(Browser);

			initDriver(Browser, PFSurl, Role);
			Thread.sleep(5000);
			log.info("*********Testing for   "+PFSurl);
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
				testForumCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);
				testPPTCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				testPDFCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				testVideoCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				testLinkCreateViewDelete(studentEmail, facultyEmail, PFSurl);
				testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);
				testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, PFSurl);
			}
			// After all test are over close the browser
			quitDriver(PFSurl);
		}
		SendMail.sendEmail();
	}

	@BeforeSuite
	public static void initDriver(String Browser, String url, String Role) throws Exception {
		try {
			if ("chrome".equals(Browser)) {
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
		} catch (Exception e) {
			log.warning("UNABLE TO LAUNCH BROWSER \n\n\n");
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
		}
	}

	@Test
	public static void logout(String url) throws Exception {
		log.info("Logout function has been called since current test cases threw and exception \n\n")
		try {
			driver.navigate().to(url);
			Thread.sleep(5000);
			try {
				// faclogout
				Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
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

	@Test(priority = 1)
	public static void testStudent(String url) throws Exception {
		try {
			log.info("  TC-1 :   Student Starting Home tab  case execution");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.expand, time, "Home tab expand");
			log.info("  Student TC-1 : Home tab test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning(" TC-1 : Student Home tab test case FAILED \n\n");
		}
	}

	@Test(priority = 2)
	public static void testStudentEnrollment(String url) throws Exception {
		try {
			log.info(" TC-2 :   Starting Student Enrollment  case execution");
			// Automate.CallXpath(driver, ActionXpath.expand, time, "Home tab expand");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
			Thread.sleep(3000);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.ClickOpenEnroll, time, "Go to the open Enrollement");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.SelectOpenSubject, time, "Select subject");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.ClickView, time, "Click view button");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.ClickOk, time, "Click ok ");
			Automate.CallXpath(driver, ActionXpath.CloseExapnd, time, "Close the Exapnd of the Enrollment");
			log.info("TC-2 : Enrollment of the Student Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-2 : Enrollment of the Student Test Case FAILED \n\n");
		}
	}

	@Test(priority = 3)
	public static void testStudentAcademic(String url) throws Exception {
		try {
			log.info("TC-3:  Starting Student Academic  test case execution");
			Automate.CallXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.ClickDashboard, time, "Click on dashboard");
			Automate.CallXpath(driver, ActionXpath.ClickNotCompleted, time, "Select the not complted acadmic");
			Automate.CallXpath(driver, ActionXpath.clickToLearn, time, "navigate to the leran Section");
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,2000)");
			Automate.CallXpath(driver, ActionXpath.openAssign, time, "Open Assign ");
			Automate.CallXpath(driver, ActionXpath.openResources, time, "Open Resources");
			Automate.CallXpath(driver, ActionXpath.CloseAcademicExapand, time, "Close Academic Expand");
			log.info("TC-3: Student Academic Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-3: Student Academic Test Case FAILED \n\n");
		}
	}

	@Test(priority = 4)
	public static void testStudentExamination(String url) throws Exception {
		try {
			log.info("TC-4:     Starting Student Examination test case execution");
			Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click on Exam");
			// Automate.CallXpath(driver, ActionXpath.opDra, time, "Announcement");
			Automate.CallXpath(driver, ActionXpath.sortExamDate, time, "Sort Exam date");
			log.info("Student TC-4: Student Examination Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-4: Student Examination Test Case FAILED \n\n");
		}
	}

	@Test(priority = 5)
	public static void testStudentAttendance(String url) throws Exception {
		try {
			log.info("TC-5:    Starting Student Attendance test case execution");
			Automate.CallXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
			Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
			log.info("TC-5: Student Attendance Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-5: 	Student Attendance  Test Case FAILED \n\n");
		}
	}

	@Test(priority = 6)
	public static void testStudentTimeTable(String url) throws Exception {
		try {
			log.info("TC-6 :    Starting Student Timetable test case execution ");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
			Automate.CallXpath(driver, ActionXpath.stunext, time, "Select slide of the Timetable");
			Automate.CallXpath(driver, ActionXpath.stubetween, time, "Selecte student Between");
			Automate.CallXpath(driver, ActionXpath.stunext, time, "time Table next");
			log.info("TC-6 :   Student Timetable Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-6 :  Student Timetable Test Case Case FAILED \n\n");
		}
	}

	@Test(priority = 7)
	public static void testStudentFees(String url) throws Exception {
		try {
			log.info("TC-7 :    Starting Student FEES test case execution");
			Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
			Automate.CallXpath(driver, ActionXpath.clickMyCart, time, " on the My cart");
			Automate.CallXpath(driver, ActionXpath.stufeestype, time, "Select Fee type");
			Automate.CallXpath(driver, ActionXpath.stufeesamount, time, "Select fee amount");
			Automate.CallXpath(driver, ActionXpath.stufeescollect, time, "Select Fee collect");
			// Automate.CallXpath(driver, ActionXpath.stufeesschedulecurrency, time, "Select
			// Fee Schedule currency");
			Automate.CallXpath(driver, ActionXpath.stufeesschedulefees, time, "Select fee schedule fees");
			Automate.CallXpath(driver, ActionXpath.stufeesscheduledate, time, "Select the Fees Schedule date");
			Automate.CallXpath(driver, ActionXpath.stufeesschedulepaid, time, "Fee schudule paid");
			Automate.CallXpath(driver, ActionXpath.stufeesscheduledue, time, "Fees Schedule");
			Automate.CallXpath(driver, ActionXpath.stufeesscheduleremaning, time, "Maning");
			Automate.CallXpath(driver, ActionXpath.clickFeePayment, time, " on the Fees payment");
			Automate.CallXpath(driver, ActionXpath.stufeedue, time, "Fees Due");
			Automate.CallXpath(driver, ActionXpath.stulastfee, time, "Last Fees");
			Automate.CallXpath(driver, ActionXpath.clickManualpayment, time, " on the Manual Payment");
			Automate.CallXpath(driver, ActionXpath.ClickMyTranscetion, time, " on the My Transcetion");
			Automate.CallXpath(driver, ActionXpath.stutransno, time, "Transection Number");
			Automate.CallXpath(driver, ActionXpath.stutrantype, time, "Transcetion Type");
			Automate.CallXpath(driver, ActionXpath.stutranparticular, time, "Transection Particular");
			Automate.CallXpath(driver, ActionXpath.stutrandate, time, "Transcetion Date");
			Automate.CallXpath(driver, ActionXpath.stutranpaid, time, "Transcetion paid");
			Automate.CallXpath(driver, ActionXpath.stutranremain, time, "Reamian");
			Automate.CallXpath(driver, ActionXpath.stutranrecipt, time, "TRans-Recepit");
			Automate.CallXpath(driver, ActionXpath.stutrantranscation, time, "Transcetion");
			Automate.CallXpath(driver, ActionXpath.stutranpayment, time, "payment");
			Automate.CallXpath(driver, ActionXpath.closeExpandFees, time, "CloseExpandFess");
			log.info("TC-7 : Student Fees Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-7 : Student Fees Test Case FAILED \n\n");
		}
	}

	@Test(priority = 8)
	public static void testStudentFeedback(String url) throws Exception {
		try {
			log.info("TC-8 :   Starting Student FEEDBACK test case execution");
			Automate.CallXpath(driver, ActionXpath.feedBack, time, "FeedBack");
			Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
			Automate.CallXpath(driver, ActionXpath.stufeedbackfaculty, time, "Feedback Faculty");
			Automate.CallXpath(driver, ActionXpath.stufeedbackfac, time, "feedBack Fac");
			Automate.CallXpath(driver, ActionXpath.stufeedbacksubject, time, " feedback Subject");
			Automate.CallXpath(driver, ActionXpath.stufeedbackcode, time, "Code");
			Automate.CallXpath(driver, ActionXpath.stufeedbackfeed, time, "Feed");
			log.info("TC-8 : Student FEEDBACK Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-8 : Student FEEDBACK Test Case FAILED \n\n");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus(String url) throws Exception {
		try {
			log.info("TC-9:  Starting  Student Services test case execution ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Thread.sleep(4000);
			Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
			log.info("TC-9: Student Services Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning(" TC-9: Student Services Test Case FAILED \n\n ");
		}
	}

	@Test(priority = 10)
	public static void testStudentRaiseCase(String student, String faculty, String url) {
		try {

			Thread.sleep(5000);
			log.info("TC-10 Starting Student Services Raise test case execution ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
			Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");

			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.inputraise, "WIfi not working ", time);
			Automate.callSendkeys(driver, ActionXpath.description,
					"While i Select the network to check the Exam date and all that time i did not found the exam date & also tab ewas not working",
					time);
			Automate.CallXpath(driver, ActionXpath.submitofcase, time, "Submit the case");
			Thread.sleep(3000);

			log.info(" TC-10: Student Service Raise case Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning(" TC-10: Student Service Raise case Test Case FAILED \n\n");
		}
	}

	@Test(priority = 11)
	public static void testStudentMakeRequest(String student, String faculty, String url) {
		try {

			Thread.sleep(5000);
			log.info("TC-11 Starting Student Services make request test case execution ");
			Thread.sleep(2500);
			Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
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
			log.info("TC-11 :Student services make request test case PASSED \n\n");

		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-11 :Student services make request test Case FAILED \n\n");
		}
	}

	@Test(priority = 12)
	public static void testStudentEvent(String student, String faculty, String url) throws Exception {
		try {
			Thread.sleep(5000);
			log.info("TC-12:     Starting Student Event case Execution ");
			Automate.CallXpath(driver, ActionXpath.Event, time, "Event");
			Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
			Automate.CallXpath(driver, ActionXpath.back, time, "Go back");
			log.info("TC-12: Student EVENT Test Case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-12: Student EVENT Test Case  FAILED \n\n");
		}
	}

	@Test(priority = 13)
	public static void testStudentEditProfile(String url) {
		try {
			Thread.sleep(10000);
			log.info("TC-13 : Starting Student ProfileEdit test case Execution");
			Automate.CallXpath(driver, ActionXpath.Stu_prName, time, "profile");
			Automate.CallXpath(driver, ActionXpath.stuprofile, time, "stuprofile");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.stubasicedit, time, "stubasicedit");
			Automate.CallXpath(driver, ActionXpath.Stubasicgender, time, "Stubasicgender");
			Automate.CallXpath(driver, ActionXpath.stubasicgenderselect, time, "stubasicgenderselect");
			Automate.callSendkeys(driver, ActionXpath.stubasicdob, "02-02-2022", time);
			Automate.callSendkeys(driver, ActionXpath.stubasicnation, "India", time);
			Automate.CallXpath(driver, ActionXpath.stubasicsave, time, "stubasicsave");
			Thread.sleep(6000);
			log.info("  TC-13 : Student edit profile test case PASSED \n\n");
		} catch (Exception e) {

			log.warning("TC-13 : Student edit profile test case FAILED \n\n");
		}
	}

	@Test(priority = 14)
	public static void testStudentEditEducationDetails(String url) {
		try {
			Thread.sleep(10000);
			log.info(" TC-14 :   Starting student edit profile education Details case execution");
			Automate.CallXpath(driver, ActionXpath.stueddrop, time, "stueddrop");
			Automate.CallXpath(driver, ActionXpath.stued, time, "stued");
			Thread.sleep(6000);
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
			Automate.CallXpath(driver, ActionXpath.stueddrop, time, "stueddrop");
			Thread.sleep(6000);
			log.info("  TC-14 : Student profile edit Education Details test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			log.warning("TC-14 : Student profile edit Education Details test case FAILED \n");
		}
	}

	@Test(priority = 15)
	public static void testStudentEditAddress(String url) throws Exception {
		try {
			Thread.sleep(10000);
			log.info(" TC-15 :   Starting student prfofile edit Address test case execution");
			Automate.CallXpath(driver, ActionXpath.stuadddrop, time, "stuadddrop");
			Thread.sleep(6000);

			Automate.CallXpath(driver, ActionXpath.stuedit, time, "stuedit");
			// Automate.CallXpath(driver, ActionXpath.StudBEdit, time, "Edit address");
			Automate.CallXpath(driver, ActionXpath.stuaddadd, time, "stuaddadd");
			Automate.callSendkeys(driver, ActionXpath.stuhouse, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.sturoad, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stusuburb, "SAMPLE", time);
			Automate.callSendkeys(driver, ActionXpath.stucountry, "India", time);
			Automate.callSendkeys(driver, ActionXpath.stupincode, "600001", time);
			Automate.CallXpath(driver, ActionXpath.stusave, time, "stusave");
			Thread.sleep(6000);
			log.info("  TC-15 : Student profile edit address detail test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(3000);
			log.warning("TC-15 : Student profile edit address detail test case FAILED \n");
		}
	}

	@Test(priority = 16)
	public static void testStudentSignout(String url) throws Exception {
		try {
			log.info("TC-16 :     Starting Student SIGNOUT  case execution ");
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
			Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
			log.info(" TC-16 : Student SIGNOUT Test Case PASSED \n\n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-16 : Student SIGNOUT Test Case FAILED \n\n\n");
		}
	}

	////////////////////// STUDENT PORTAL
	////////////////////// FINISHED////////////////////////////////////////////////////////

	//////////////////////// FACULTY PORTAL
	//////////////////////// STARTED/////////////////////////////////////

	@Test(priority = 17)
	public static void testFaculty(String url) throws Exception {
		try {
			log.info("TC-17 :     Starting FACULTY PORTAL Academic tab test case execution");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.facclickdashboard, time, "facclickdashboard");
			Automate.CallXpath(driver, ActionXpath.facdbfilter, time, "facdbfilter");
			Automate.CallXpath(driver, ActionXpath.facdbfilterselect, time, "facdbfilterselect");
			Automate.CallXpath(driver, ActionXpath.facdbresfilter, time, "facdbresfilter");
			Automate.CallXpath(driver, ActionXpath.facdbrestypes, time, "facdbrestypes");
			Automate.CallXpath(driver, ActionXpath.facdbrestypesselect, time, "facdbrestypesselect");
			Automate.CallXpath(driver, ActionXpath.facdbresapply, time, "facdbresapply");
			log.info("TC-17 : Faculty ACADEMIC Test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-17 : Faculty ACADEMIC Test case FAILED \n\n");
		}
	}

	@Test(priority = 18)
	public static void testFacultyQuestionBank(String url) throws Exception {
		try {
			log.info(" TC-18 :    Faculty Starting QuestionBank Tab test case execution");
			Automate.CallXpath(driver, ActionXpath.facqb, time, "facqb");
			Automate.CallXpath(driver, ActionXpath.facaddque, time, "facaddque");
			Automate.CallXpath(driver, ActionXpath.facquetype, time, "facquetype");
			Automate.CallXpath(driver, ActionXpath.facquetypeselect, time, "facquetypeselect");
			Automate.CallXpath(driver, ActionXpath.facqueclass, time, "facqueclass");
			Automate.CallXpath(driver, ActionXpath.facqueclassselect, time, "facqueclassselect");
			Automate.CallXpath(driver, ActionXpath.facquesub, time, "facquesub");
			Automate.CallXpath(driver, ActionXpath.facquesubselect, time, "facquesubselect");
			Automate.CallXpath(driver, ActionXpath.facquesubmit, time, "facquesubmit");
			Automate.CallXpath(driver, ActionXpath.facqueback, time, "facqueback");
			log.info("TC-18 : Faculty QuestionBank Test Case PASSED \n\n ");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-18: Faculty QuestionBank Test Case FAILED \n\n");
		}
	}

	@Test(priority = 19)
	public static void testFacultyCourseContent(String url) throws Exception {
		try {
			log.info("TC-19: Faculty Course Content Test Execution  Started ");
			// Automate.CallXpath(driver, ActionXpath.facClickacademics,
			// time,"facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Automate.CallXpath(driver, ActionXpath.facccactivity, time, "facccactivity");
			Automate.CallXpath(driver, ActionXpath.faccform, time, "faccform");
			Automate.CallXpath(driver, ActionXpath.faccformadd, time, "faccformadd");
			Automate.CallXpath(driver, ActionXpath.facccformcancel, time, "facccformcancel");
			Automate.CallXpath(driver, ActionXpath.facccformyes, time, "facccformyes");
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			Automate.CallXpath(driver, ActionXpath.facccresadd, time, "facccresadd");
			Automate.CallXpath(driver, ActionXpath.facccrescancel, time, "facccrescancel");
			Automate.CallXpath(driver, ActionXpath.facccresyes, time, "facccresyes");
			log.info("TC-19:  Faculty Course Content Test PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-19:  Faculty Course Content Test FAILED \n\n");
		}
	}

	@Test(priority = 20)
	public static void testFacultyExamination(String url) throws Exception {
		try {
			log.info("TC-20:     Faculty Examination Test execution Statred");
			Automate.CallXpath(driver, ActionXpath.facexam, time, "facexam");
			Automate.CallXpath(driver, ActionXpath.facexamarrow, time, "facexamarrow");
			Automate.CallXpath(driver, ActionXpath.facexamdropdown, time, "facexamdropdown");
			Automate.CallXpath(driver, ActionXpath.facexamexam, time, "facexamexam");
			Automate.CallXpath(driver, ActionXpath.facexamdate, time, "facexamdate");
			Automate.CallXpath(driver, ActionXpath.faceexamtime, time, "faceexamtime");
			log.info("TC-20: Faculty Examanation test cases PASSED... \n ");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-20: Faculty Examanation test cases FAILED \n");
		}
	}

	@Test(priority = 21)
	public static void testFacultyMYStudent(String url) throws Exception {
		try {
			log.info("TC-21:   Faculty My Students Test execution Started");
			Automate.CallXpath(driver, ActionXpath.facstudent, time, "facstudent");
			Automate.CallXpath(driver, ActionXpath.facstudrop, time, "facstudrop");
			Automate.CallXpath(driver, ActionXpath.facstudropselect, time, "facstudropselect");
			Automate.CallXpath(driver, ActionXpath.facstuname, time, "facstuname");
			Automate.CallXpath(driver, ActionXpath.facsturegid, time, "facsturegid");
			Automate.CallXpath(driver, ActionXpath.facstuemail, time, "facstuemail");
			Automate.CallXpath(driver, ActionXpath.facstuatt, time, "facstuatt");
			Automate.CallXpath(driver, ActionXpath.facstusearch, time, "facstusearch");
			log.info("TC-21: Faculty My Students  Test execution PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-21: Faculty My Students  Test execution FAILED \n\n");
		}
	}

	@Test(priority = 22)
	public static void testFacultyAttendance(String url) throws Exception {
		try {
			log.info("TC-22 :    Faculty Attendance Test execution Startred ");
			Automate.CallXpath(driver, ActionXpath.facatt, time, "facatt");
			Automate.CallXpath(driver, ActionXpath.facattsub, time, "facattsub");
			Automate.CallXpath(driver, ActionXpath.facattsubselect, time, "facattsubselect");
			Automate.CallXpath(driver, ActionXpath.facattterm, time, "facattterm");
			Automate.CallXpath(driver, ActionXpath.facattdate, time, "facattdate");
			Automate.CallXpath(driver, ActionXpath.facattmark, time, "facattmark");
			Automate.CallXpath(driver, ActionXpath.facattmarkattendence, time, "facattmarkattendence");
			Automate.CallXpath(driver, ActionXpath.facatthistory, time, "facatthistory");
			Automate.CallXpath(driver, ActionXpath.facatthistsub, time, "facatthistsub");
			Automate.CallXpath(driver, ActionXpath.facatthistsubselect, time, "facatthistsubselect");
			Automate.CallXpath(driver, ActionXpath.facatthiststdate, time, "facatthiststdate");
			Automate.CallXpath(driver, ActionXpath.facatthistenddate, time, "facatthistenddate");
			Automate.CallXpath(driver, ActionXpath.facatthistterm, time, "facatthistterm");
			Automate.CallXpath(driver, ActionXpath.facatthistst, time, "facatthistst");
			Automate.CallXpath(driver, ActionXpath.facatthisend, time, "facatthisend");
			Automate.CallXpath(driver, ActionXpath.facatthistatt, time, "facatthistatt");
			log.info("TC-22 : Faculty Attendance Test execution PASSED \n\n ");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-22 : Faculty Attendance Test case FAILED \n\n");
		}
	}

	@Test(priority = 23)
	public static void testFaculityTimetable(String url) throws Exception {
		try {
			log.info("TC-23 :    Faculty Timetable Test execution Started ");
			Automate.CallXpath(driver, ActionXpath.facClickTimetable, time, "facClickTimetable");
			Automate.CallXpath(driver, ActionXpath.facttmonth, time, "facttmonth");
			Automate.CallXpath(driver, ActionXpath.facttweek, time, "facttweek");
			Automate.CallXpath(driver, ActionXpath.facttday, time, "facttday");
			Automate.CallXpath(driver, ActionXpath.facnext, time, "facnext");
			Automate.CallXpath(driver, ActionXpath.facbetween, time, "facbetween");
			log.info("TC-23 : Faculty Timetable test case PASSED \n\n ");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-23 : Faculty Timetable test case FAILED \n\n");
		}
	}

	@Test(priority = 24)
	public static void testFacultyService(String url) throws Exception {
		try {
			log.info("TC-24:  Faculty Service Test case Started");
			Automate.CallXpath(driver, ActionXpath.facClickservice, time, "facClickservice");
			Automate.CallXpath(driver, ActionXpath.facraise, time, "facraise");
			Automate.CallXpath(driver, ActionXpath.facrequest, time, "facrequest");
			Automate.CallXpath(driver, ActionXpath.facraisecase, time, "facraisecase");
			Automate.CallXpath(driver, ActionXpath.facraisecaseno, time, "facraisecaseno");
			Automate.CallXpath(driver, ActionXpath.facraisesub, time, "facraisesub");
			Automate.CallXpath(driver, ActionXpath.facraisedesc, time, "facraisedesc");
			Automate.CallXpath(driver, ActionXpath.facraisedate, time, "facraisedate");
			Automate.CallXpath(driver, ActionXpath.facraisestatus, time, "facraisestatus");
			log.info("TC-24: Faculty Service Test case PASSED \n\n ");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-24: Faculty Service Test case FAILED \n\n");
		}
	}

	@Test(priority = 25)
	public static void testFacultyRaiseCase(String student, String faculty, String url) {
		try {

			Thread.sleep(5000);
			log.info("TC-24 Starting Faculty Service Raise Case execution");
			Thread.sleep(30000);
			Automate.CallXpath(driver, ActionXpath.facClickservice, time, "select the faculty service");
			Automate.CallXpath(driver, ActionXpath.facraise, time, "Select the Faculty Raise Case");
			Automate.CallXpath(driver, ActionXpath.RasieBtn, time, "click the Raise case");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.inputSub, "Regd Error on Inviligation Secation", time);
			Automate.callSendkeys(driver, ActionXpath.FacDesc,
					"while i need to regd on the inviligation section m unable to do bcz its showing the system admin Error Sever not availbale 404 error.",
					time);
			Automate.CallXpath(driver, ActionXpath.SubmitRaise, time, "Submit the case");
			Thread.sleep(3000);
			log.info("TC 25: Faculty service  Raise Case PASSED \n\n");

		} catch (Exception e) {
			driver.get(url);
			log.warning("TC 25: Faculty service Raise Case FAILED \n\n");
		}
	}

	@Test(priority = 26)
	public static void testFacultyMakeRequest(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(5000);
			log.info("TC-26:  Faculty Service Make Raise Request ");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facClickservice, time, "select the faculty service");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facraise, time, "Select the Faculty Make request Case");
			Automate.CallXpath(driver, ActionXpath.facrequest, time, "facrequest");
			Automate.CallXpath(driver, ActionXpath.MakeRqstbtn, time, "click the Make request case");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.makeSubjectIn, "5 days Leave Request ", time);
			Automate.callSendkeys(driver, ActionXpath.makedesc,
					"hi ...i want to take the 5 days leave bcz of some helath issue  m not availbe on this days some medical emergency plz approved my rqst... Thanks & regards Aditya .",
					time);
			Automate.CallXpath(driver, ActionXpath.MakeBtn, time, "Submit the Request");
			Thread.sleep(3000);
			log.info("TC-26: Faculty service Status  Raise Case PASSED \n\n");

		} catch (Exception e) {

			Thread.sleep(3000);
			log.warning("TC-26: Faculty service Status  Raise Case FAILED \n\n");
		}
	}

	@Test(priority = 27)
	public static void testFacultyEvent(String url) throws Exception {
		try {
			log.info("TC-27 :  Starting Faculty Portal Event Tab Test case execution");
			Automate.CallXpath(driver, ActionXpath.facEvent, time, "facEvent");
			Automate.CallXpath(driver, ActionXpath.faceventlocation, time, "faceventlocation");
			Automate.CallXpath(driver, ActionXpath.faceventlocationselect, time, "faceventlocationselect");
			log.info("TC-27 : Faculty Event Test case execution PASSED \n\n");
		} catch (Exception e) {
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-27 : Faculty Event Test case FAILED \n\n");
		}
	}

	@Test(priority = 28)
	public static void testfacultyEditProfile(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(5000);
			log.info(" TC-28 :   Faculty Starting PersonalDetails Started  case execution");
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facprofile, time, "facprofile");
			Automate.CallXpath(driver, ActionXpath.facpersonal, time, "facpersonal");
			Automate.CallXpath(driver, ActionXpath.facpdedit, time, "facpdedit");
			Automate.CallXpath(driver, ActionXpath.facpdgender, time, "facpdgender");
			Automate.CallXpath(driver, ActionXpath.facpdgenderselect, time, "facpdgenderselect");
			Automate.callSendkeys(driver, ActionXpath.facpddob, "02-02-2020", time);
			Automate.callSendkeys(driver, ActionXpath.facpdnationality, "INDIA", time);
			Automate.CallXpath(driver, ActionXpath.facdpsave, time, "facdpsave");
			Thread.sleep(6000);
			log.info("  TC-28 : Faculty edit PersonalDetails test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-28 : Faculty edit PersonalDetails test case FAILED \n\n");
		}
	}

	@Test(priority = 29)
	public static void testfacultyEditAddress(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(10000);
			log.info(" TC-29 :   Starting faculty edit Address Details test case execution");
			Automate.CallXpath(driver, ActionXpath.address, time, "addressdetais");
			Automate.CallXpath(driver, ActionXpath.facdpaddedit, time, "facdpaddedit");
			Automate.CallXpath(driver, ActionXpath.facdptype, time, "facdptype");
			Automate.CallXpath(driver, ActionXpath.facdptypeselect, time, "facdptypeselect");
			Automate.callSendkeys(driver, ActionXpath.facdpadd, "Coimbatore", time);
			Automate.callSendkeys(driver, ActionXpath.facdppin, "600001", time);
			Automate.CallXpath(driver, ActionXpath.facdpcountry, time, "facdpcountry");
			Automate.CallXpath(driver, ActionXpath.facdpcountrysselect, time, "facdpcountrysselect");
			Automate.CallXpath(driver, ActionXpath.facdpstate, time, "facdpstate");
			Automate.CallXpath(driver, ActionXpath.facdpstateselect, time, "facdpstateselect");
			Automate.CallXpath(driver, ActionXpath.facdpdist, time, "facdpdist");
			Automate.CallXpath(driver, ActionXpath.facdpdistselect, time, "facdpdistselect");
			Automate.CallXpath(driver, ActionXpath.facdpaddsave, time, "facdpaddsave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpadddelete, time, "facdpadddelete");
			Thread.sleep(6000);
			log.info(" TC-29 : Address Details Completed test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-29 : Address Details test case FAILED \n\n");
		}
	}

	@Test(priority = 30)
	public static void testfacultyEditAcademicDetails(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(10000);
			log.info(" TC-30 :   Starting Academic Details Started  case execution");
			Automate.CallXpath(driver, ActionXpath.facdpacdeails, time, "facdpacdeails");
			Automate.CallXpath(driver, ActionXpath.facdpacadd, time, "facdpacadd");
			Automate.CallXpath(driver, ActionXpath.facdplevel, time, "facdplevel");
			Automate.CallXpath(driver, ActionXpath.facdplevelselect, time, "facdplevelselect");
			Automate.CallXpath(driver, ActionXpath.facdpadcountry, time, "facdpadcountry");
			Automate.CallXpath(driver, ActionXpath.facdpadcountryselect, time, "facdpadcountryselect");
			Automate.callSendkeys(driver, ActionXpath.facdpaduniversity, "ANNA", time);
			Automate.callSendkeys(driver, ActionXpath.facdpadyear, "2020", time);
			Automate.CallXpath(driver, ActionXpath.facdpadsave, time, "facdpadsave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpaddelete, time, "facdpaddelete");
			Thread.sleep(6000);
			log.info(" TC-30 : Faculty profile Academic Details test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-30 : Faculty profile Academic Details test case FAILED \n\n");
		}
	}

	@Test(priority = 31)
	public static void testfacultyEditReaserchSupervision(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(10000);
			log.info(" TC:31 :   Starting Faculty profile RESEARCH SUPERVISION  execution");
			Automate.CallXpath(driver, ActionXpath.facdpre, time, "facdpre");
			Automate.CallXpath(driver, ActionXpath.facdpreedit, time, "facdpreedit");
			Automate.CallXpath(driver, ActionXpath.facdpreadd, time, "facdpreadd");
			Automate.callSendkeys(driver, ActionXpath.facdprename, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdprelink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpredesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpresave, time, "facdpresave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpredelete, time, "facdpredelete");
			Thread.sleep(6000);
			log.info(" TC-31 : Faculty profile RESEARCH SUPERVISION test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-31 : Faculty profile RESEARCH SUPERVISION test case FAILED \n\n");
		}
	}

	@Test(priority = 32)
	public static void testfacultyEditResearchPublication(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(5000);
			log.info(" TC-32 :  Starting  profile edit RESEARCH PUBLICATION Started  test case execution");
			Automate.CallXpath(driver, ActionXpath.facdppub, time, "facdppub");
			Automate.CallXpath(driver, ActionXpath.facdppubedit, time, "facdppubedit");
			Automate.CallXpath(driver, ActionXpath.facdppubadd, time, "facdppubadd");
			Automate.callSendkeys(driver, ActionXpath.facdppubname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdppublink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdppubdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdppubsave, time, "facdppubsave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdppubdelete, time, "facdppubdelete");
			Thread.sleep(6000);
			log.info("  TC-32 : Faculty RESEARCH PUBLICATION Completed test case PASSED \n\n");
		} catch (Exception e) {

			Thread.sleep(time);
			log.warning("TC-32 : Faculty RESEARCH PUBLICATION test case FAILED \n\n");
		}
	}

	@Test(priority = 33)
	public static void testfacultyEditConference(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(10000);
			log.info(" TC-33 :   Starting Faculty profile edit Conference details execution");
			Automate.CallXpath(driver, ActionXpath.facdpcon, time, "facdpcon");
			Automate.CallXpath(driver, ActionXpath.facdpconedit, time, "facdpconedit");
			Automate.CallXpath(driver, ActionXpath.facdpconadd, time, "facdpconadd");
			Automate.callSendkeys(driver, ActionXpath.facdpconname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpconlink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpcondesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpconsave, time, "facdpconsave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpcondelete, time, "facdpcondelete");
			Thread.sleep(6000);
			log.info(" TC-33 :Faculty profile edit Conference details test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("Tc-33 :Faculty profile edit Conference details test case FAILED \n\n");
		}
	}

	@Test(priority = 34)
	public static void testfacultyEditBook(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(5000);
			log.info(" TC:34 :   Starting Faculty profile edit Book test case execution");
			Automate.CallXpath(driver, ActionXpath.facdpbook, time, "facdpbook");
			Automate.CallXpath(driver, ActionXpath.facdpbookedit, time, "facdpbookedit");
			Automate.CallXpath(driver, ActionXpath.facdpbookadd, time, "facdpbookadd");
			Automate.callSendkeys(driver, ActionXpath.facdpbookname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbooklink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpbookdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpbooksave, time, "facdpbooksave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpbookdelete, time, "facdpbookdelete");
			Thread.sleep(6000);
			log.info(" TC:34 :Faculty profile edit Book test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("Tc:34 : Faculty profile edit Book test case FAILED \n\n");
		}
	}

	@Test(priority = 35)
	public static void testfacultyEditProfessionalAssociation(String student, String faculty, String url) throws Exception {
		try {

			Thread.sleep(5000);
			log.info(" TC-35 :  Starting Faculty profile edit Professional Association  execution");
			Automate.CallXpath(driver, ActionXpath.facdpprof, time, "facdpprof");
			Automate.CallXpath(driver, ActionXpath.facdpprofedit, time, "facdpprofedit");
			Automate.CallXpath(driver, ActionXpath.facdpprofadd, time, "facdpprofadd");
			Automate.callSendkeys(driver, ActionXpath.facdpprofname, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpproflink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpprofdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpprofsave, time, "facdpprofsave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpprofdelete, time, "facdpprofdelete");
			Thread.sleep(6000);
			log.info("  TC-35 : Faculty profile edit Professional Association test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-35 : Faculty profile edit Professional Association test case FAILED \n\n");
		}
	}

	@Test(priority = 36)
	public static void testfacultyOthers(String student, String faculty, String url) throws Exception {
		try {
			Thread.sleep(5000);
			log.info(" TC-36 : Starting Faculty profile edit  Others section case execution");
			Automate.CallXpath(driver, ActionXpath.facdpother, time, "facdpother");
			Automate.CallXpath(driver, ActionXpath.facdpotheredit, time, "facdpotheredit");
			Automate.CallXpath(driver, ActionXpath.facdpotheradd, time, "facdpotheradd");
			Automate.callSendkeys(driver, ActionXpath.facdpothername, "Sample", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherlink, "https://portal-dev.ken42.com", time);
			Automate.callSendkeys(driver, ActionXpath.facdpotherdesc, "Sample Desc", time);
			Automate.CallXpath(driver, ActionXpath.facdpothersave, time, "facdpothersave");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facdpotherdelete, time, "facdpotherdelete");
			Thread.sleep(6000);

			log.info(" TC-36 : Faculty profile edit Others section test case PASSED \n\n");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("TC-36 : Faculty profile edit Others section test case FAILED \n\n");
		}
	}

	@Test(priority = 37)
	public static void testFacultyDashboard(String student, String faculty, String url) throws Exception {
		try {

			log.info("TC-37: Starting Faculty DASHBOARD test execution \n");
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "Click Academics");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facclickdashboard, time, "Dashboard");
			Thread.sleep(5000);
			String text = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div/div[1]/div[4]/div/div/div[1]/div[1]"))
					.getText();
			System.out.println(text);
			String count = text.substring(11, text.length() - 1);
			System.out.println(count);
			log.info("TC-37:  Faculty Dashboard test case PASSED \n\n");

		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(time);
			log.warning("Tc-37:Faculty DASHBOARD  test case FAILED \n\n");
		}
	}

	@Test(priority = 38)
	public static void testFacultyQuestionPaper(String student, String faculty, String url) throws Exception {
		try {

			log.info("TC-38 Starting Faculty QUESTION PAPER test execution \n");
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "Click Academics");
			Automate.CallXpath(driver, ActionXpath.facquestion, time, "Question ");
			Automate.CallXpath(driver, ActionXpath.facaddmanual, time, "Add anual");
			Automate.CallXpath(driver, ActionXpath.facclicksubject, time, "Click Subject");
			Automate.CallXpath(driver, ActionXpath.faccsubject, time, "Subject");
			Automate.CallXpath(driver, ActionXpath.faccnext, time, "Next");
			Automate.CallXpath(driver, ActionXpath.faccquestion, time, "Question");
			Automate.CallXpath(driver, ActionXpath.faccquestionname, time, "Question time");
			Thread.sleep(2000);
			Automate.cleartext(driver, ActionXpath.faccmarks);
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.faccmarks, "1", time);

			Automate.callSendkeys(driver, ActionXpath.faccoption1, "modi", time);
			Automate.callSendkeys(driver, ActionXpath.feedback1, "Super", time);
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
			Automate.CallXpath(driver, ActionXpath.facback, time, "BAck");
			log.info("Tc-38: Faculty QUESTION PAPER TEST CASE PASSED \n\n");

		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(2000);
			log.warning("TC-38: Faculty QUESTION PAPER  test case FAILED \n\n");
		}
	}

	@Test(priority = 39)
	public static void testFacultySignout(String url) throws Exception {
		try {

			log.info(" TC-39:  Starting Faculty  View Profile test execution Started");
			Automate.CallXpath(driver, ActionXpath.facselectpro, time, "facselectpro");
			Automate.CallXpath(driver, ActionXpath.facprofile, time, "facprofile");

			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info(" TC-39: Faculty View Profile and Sign out Test Case PASSED \n\n\n ");
		} catch (Exception e) {
			driver.navigate().to(url);
			Thread.sleep(3000);
			log.warning("TC-39: Faculty View Profile and Sign out Test Case FAILED \n\n\n");
		}
	}

	//////////////////////////// FACULTY PORTAL
	//////////////////////////// FINISHED.///////////////////////////////

	///////////////////////// STARTED TEST CASE FOR BOTH PORTAL
	///////////////////////// ///////////////////////

	@Test(priority = 40)
	public static void testForumCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			login(faculty);
			Thread.sleep(10000);
			log.info("TC-40 Starting Faculty Fourm create,publish Delete test case Staerted...\n");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facforumClickacademics, time, "facforumClickacademics");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facforumclickcouse, time, "facforumclickcouse");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.facforumactivity, time, "facforumactivity");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facforum, time, "facforum");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facfforumaddactivity, time, "facfforumaddactivity");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facforumname, "hitest", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facforumclink, time, "facforumclink");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facforumurl, "suriw", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facforumsavlin, time, "facforumsavlin");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facforumsave, time, " facforumsave");
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement ele = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Save and Proceed']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.faforumsave, time, "faforumsave");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.faforumok, time, "faforumok");
			Thread.sleep(3000);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			js2.executeScript("window.scrollBy(0,3000)");

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.formexpand, time, "formexpand");
			Thread.sleep(5000);
			JavascriptExecutor js3 = (JavascriptExecutor) driver;
			WebElement el = driver.findElement(By.xpath("//p[.='hitest']"));
			js3.executeScript("arguments[0].scrollIntoView(true);", el);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.faccformedot, time, "faccformedot");
			Thread.sleep(5000);
			WebDriverWait wait1 = new WebDriverWait(driver, 20);
			WebElement elem = wait1.until(ExpectedConditions
					.elementToBeClickable(By.xpath("/html/body/div[12]/div[3]/div/nav/div[3]/div[2]/span")));
			((JavascriptExecutor) driver).executeScript("argument[0].click();", elem);
			Thread.sleep(5000);
			WebDriverWait wait2 = new WebDriverWait(driver, 20);
			WebElement eleme = wait2.until(
					ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div[3]/div/div[3]/button[2]")));
			((JavascriptExecutor) driver).executeScript("argument[0].click();", eleme);
			Thread.sleep(8000);

			WebDriverWait wait3 = new WebDriverWait(driver, 20);
			WebElement elemen = wait3.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div/button/span[1]")));
			((JavascriptExecutor) driver).executeScript("argument[0].click();", elemen);

			Thread.sleep(2000);

			WebDriverWait wait4 = new WebDriverWait(driver, 20);
			WebElement element = wait4.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[.='Sign Out']")));
			((JavascriptExecutor) driver).executeScript("argument[0].click();", element);

			Thread.sleep(5000);

			// Student Login
			login(student);
			Thread.sleep(20000);
			Automate.CallXpath(driver, ActionXpath.saccltsta, time, "acc");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.forumlearnltsta, time, "Select learn");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.forumassessmentexpltsta, time, "expand forum");
			Thread.sleep(2000);
			JavascriptExecutor js34 = (JavascriptExecutor) driver;
			js34.executeScript("window.scrollBy(0,2000)");
			Thread.sleep(2000);
			JavascriptExecutor js26 = (JavascriptExecutor) driver;
			WebElement element36 = driver.findElement(By.xpath("//p[.='hitest12']"));

			js26.executeScript("arguments[0].scrollIntoView(true);", element36);
			Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, "click profile");
			Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout");

			// Delete fourm
			login(faculty);
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.forumdaccltsta, time, "facClickacademics");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.forumdacclickcouse, time, "facclickcouse");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.forumdfassessmentexpltsta, time, "Exapand");
			Thread.sleep(3000);
			JavascriptExecutor js27 = (JavascriptExecutor) driver;
			js27.executeScript("window.scrollBy(0,3000)");

			Thread.sleep(2000);
			JavascriptExecutor js21 = (JavascriptExecutor) driver;
			WebElement element33 = driver.findElement(By.xpath("//p[.='autoteststart11']"));
			js21.executeScript("arguments[0].scrollIntoView(true);", element33);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.forumfclickondotltsta, time, "facdot");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.dfacdelete, time, "facdelete");

			Thread.sleep(2000);

			Automate.CallXpath(driver, ActionXpath.dfacdele, time, "facdele");
			Thread.sleep(2000);

			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-40:  Faculty Fourm create,publish Delete test case PASSED \n\n");

		} catch (Exception e) {
			log.warning("TC-40 Faculty Fourm create,publish Delete test case FAILED \n\n");
			logout(url);
		}
	}

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC-41: Starting create PPT publish view delete test case execution");
			login(faculty);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Thread.sleep(15000);
			// add ppt publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			Automate.CallXpath(driver, ActionXpath.facpptadd, time, "facpptadd");
			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample PPT", time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\samplepptx.pptx");
			Thread.sleep(25000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishppt
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Automate.CallXpath(driver, ActionXpath.facpptpublish, time, "facpptpublish");
			Automate.CallXpath(driver, ActionXpath.facpptpublishyes, time, "facpptpublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify ppt creation and logout
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.viewppt, time, "viewppt");
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			// unpublish ppt and delete ppt
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Thread.sleep(8000);
//     Automate.CallXpath(driver, ActionXpath.facccres, time,"facccres");
			Automate.CallXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			Automate.CallXpath(driver, ActionXpath.facppt3dot, time, "facppt3dot");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-41: PPT create, publish, view, Delete Test Case PASSED \n\n");
		} catch (Exception e) {
			Thread.sleep(time);
			logout(url);
			log.warning("TC-41: PPT create, publish, view, Delete Test Case FAILED \n\n");

		}

	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC-42:  Starting PDF Create, View, Delete execution ");

			login(faculty);
			// add pdf
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");

			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			Automate.CallXpath(driver, ActionXpath.facccresadd, time, "facccresadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample PDF", time);
			driver.findElement(By.xpath("//input[@accept='.pdf']")).sendKeys("C:\\Users\\Public\\Documents\\ken42.pdf");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishpdf
			Automate.CallXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Automate.CallXpath(driver, ActionXpath.facpublishpdf, time, "facpublishpdf");
			Automate.CallXpath(driver, ActionXpath.facpublishpdf2, time, "facpublishpdf2");
			Thread.sleep(25000);
			Thread.sleep(6000);
			// signout
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify PDF creation and logout
			Automate.CallXpath(driver, ActionXpath.viewpdf, time, "viewpdf");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// signout
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			// unpublish pdf and delete pdf
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			Automate.CallXpath(driver, ActionXpath.fac3dot, time, "fac3dot");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facpdfdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facpdfdelete2");
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-42: PDF create, view, delete test cases PASSED \n\n");

		} catch (Exception e) {
			Thread.sleep(time);
			logout(url);
			log.warning("TC-42: PDF create, view, delete test cases FAILED \n");

		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC-43:  Starting Video create, view, Delete test case execution");
			login(faculty);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// add video publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
			Automate.CallXpath(driver, ActionXpath.facvideoadd, time, "facvideoadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample Video", time);
			driver.findElement(By.xpath("//input[@accept='.mp4']")).sendKeys("C:\\Users\\Public\\Documents\\test.mp4");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishvideo
			Automate.CallXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Automate.CallXpath(driver, ActionXpath.facvideopublish, time, "facvideopublish");
			Automate.CallXpath(driver, ActionXpath.facvideopublishyes, time, "facvideopublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			// Verify video creation and logout
			Automate.CallXpath(driver, ActionXpath.viewvideo, time, "viewvideo");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewpdf2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// unpublish video and delete video
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			Automate.CallXpath(driver, ActionXpath.facvideo3dot, time, "facvideo3dot");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "facvideodelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.warning("TC-43: Video create, view, Delete test case PASSED \n\n");
		} catch (Exception e) {
			Thread.sleep(time);
			logout(url);
			log.warning("TC-43: Video create, view, Delete test case FAILED \n\n");

		}
	}

	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC-44:  Starting Link resource Create, View, Delete test case execution");
			login(faculty);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// add link publish and signout
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.faclinkclick, time, "faclinkclick");
			Automate.CallXpath(driver, ActionXpath.faclinkadd, time, "faclinkadd");

			Automate.CallXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
			Automate.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
			Automate.callSendkeys(driver, ActionXpath.facpptname, "Sample Link", time);
			Automate.CallXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");

			Automate.callSendkeys(driver, ActionXpath.faclinkexternal, url, time);
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Thread.sleep(8000);
			// publishlink
			Automate.CallXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			Automate.CallXpath(driver, ActionXpath.faclinkpublish, time, "faclinkpublish");
			Automate.CallXpath(driver, ActionXpath.faclinkpublishyes, time, "faclinkpublishyes");
			Thread.sleep(25000);
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			// Verify link creation and logout
			Automate.CallXpath(driver, ActionXpath.accademics, time, "accademics");
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");

			Automate.CallXpath(driver, ActionXpath.viewlink, time, "viewlink");
			Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			Automate.CallXpath(driver, ActionXpath.viewpdf2, time, "viewlink2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.learn, time, "learn");
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Automate.CallXpath(driver, ActionXpath.faccc, time, "faccc");
			// delete link
			Automate.CallXpath(driver, ActionXpath.facccres, time, "facccres");
			Automate.CallXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			Automate.CallXpath(driver, ActionXpath.faclink3dot, time, "faclink3dot");
			Automate.CallXpath(driver, ActionXpath.facpdfdelete, time, "faclinkdelete");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facpdfdelete2, time, "facvideodelete2");
			Thread.sleep(6000);
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC-44:  Link resource Create, View, Delete test case PASSED \n\n");
		} catch (Exception e) {
			Thread.sleep(time);
			logout(url);
			log.warning("TC-35: Link resource Create, View, Delete test case FAILED \n\n");

		}
	}

	@Test(priority = 45)
	public static void testAssessmentCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC-45: Starting Assessment create, publish, view, delete test case execution");
			login(faculty);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.facclickcouse, time, "facclickcouse");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facactivity, time, "facactivity");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facassessment, time, "facassessment");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facaddactivity, time, "facaddactivity");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facassesment, "sachins", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facclink, time, "facclink");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facurl, "suriw", time);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsavlin, time, "facsavlin");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsave, time, "facsave");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.fachour, "3", time);

			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement ele = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Save and Proceed']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.fasok, time, "fasok");
			Automate.CallXpath(driver, ActionXpath.fasquestion, time, "fasquestion");
			Automate.CallXpath(driver, ActionXpath.facselect, time, "facselect");
			Automate.CallXpath(driver, ActionXpath.facaddselect, time, "facaddselect");
			WebElement ele1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='Preview']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele1);

			Thread.sleep(4000);
			WebElement ele2 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish Assessment']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", ele2);

			Thread.sleep(15000);

			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");

			login(student);
			Thread.sleep(20000);
			Automate.CallXpath(driver, ActionXpath.faccltsta, time, "faccltsta");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.flearnltsta, time, "flearnltsta");
			Automate.CallXpath(driver, ActionXpath.fassessmentexpltsta, time, "fassessmentexpltsta");
			Thread.sleep(2000);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//p[.='dsadadsad awdwdasdadasdad']"));
			js2.executeScript("arguments[0].scrollIntoView(true);", element);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.fsubltstasign, time, "fsubltstasign");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			Thread.sleep(20000);
			Automate.CallXpath(driver, ActionXpath.faccltsta, time, "faccltsta");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fassacclickcouse, time, "fassacclickcouse");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fassessmentexpltsta, time, "fassessmentexpltsta");
			Thread.sleep(2000);
			JavascriptExecutor js21 = (JavascriptExecutor) driver;
			WebElement element21 = driver.findElement(By.xpath("//p[.='Quiz 24']"));
			js21.executeScript("arguments[0].scrollIntoView(true);", element21);
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fclickondotltsta, time, "fclickondotltsta");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fsubltstadelete, time, "fsubltstadelete");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.fsubltstadelete1, time, " fsubltstadelete1");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.fsubltstasign, time, "fclickons");
			Thread.sleep(4000);
			Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time, "facSelectPrtoSignout");
			Automate.CallXpath(driver, ActionXpath.facsignOut, time, "facsignOut");
			log.info("TC:45 Assessment create, publish, view, delete test case PASSED....\n");
		} catch (Exception e) {

			logout(url);
			log.warning("TC:45 Assessment create, publish, view, delete test case FAILED...");
		}
	}

	@Test(priority = 46)
	public static void testFAssignmentCreatePublishViewDelete(String student, String faculty, String url) throws Exception {
		try {
			log.info("TC:46 Starting Assignment Create, Publish, View, Delete test case execution");
			Thread.sleep(4000);
			login(faculty);
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facclickcouse, time, "facclickcouse");
			Thread.sleep(15000);
			Automate.CallXpath(driver, ActionXpath.facactivity, time, "facactivity");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facassignment, time, "facassignment");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facaddactivity, time, "facaddactivity");
			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facassignmentName, "spru", time);

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.faclink, time, "faclink");

			Thread.sleep(2000);
			Automate.callSendkeys(driver, ActionXpath.facurl, "https://portal-dev.ken42.com/", time);

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsavlin, time, "facsavlink");

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsave, time, " facsave");
			Thread.sleep(2000);
			Thread.sleep(2000);
			Automate.cleartext(driver, ActionXpath.factotalmarks);
			Automate.callSendkeys(driver, ActionXpath.factotalmarks, "9", time);
			Thread.sleep(2000);
			WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
			el.clear();
			el.sendKeys("9");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facattements, time, "facattements");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facselectattemt, time, "facselectattemt");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facsaveandproceed, time, "facsaveandproceed");
			Thread.sleep(8000);
			Automate.CallXpath(driver, ActionXpath.facok, time, "facok");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.exapnd, time, "Exapand");
			Thread.sleep(2000);
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			WebElement element = driver.findElement(By.xpath("//p[.='spru']"));
			js2.executeScript("arguments[0].scrollIntoView(true);", element);
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facdot, time, "facdot");
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 20);
			WebElement element1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element1);
			Thread.sleep(2000);
			WebDriverWait waite = new WebDriverWait(driver, 20);
			WebElement element2 = waite.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element2);
			Thread.sleep(8000);
			Thread.sleep(2000);
			WebDriverWait waie = new WebDriverWait(driver, 20);
			WebElement elemen2 = waie.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div/button/span[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemen2);
			Thread.sleep(2000);
			WebDriverWait wae = new WebDriverWait(driver, 20);
			WebElement eleme2 = wae.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[.='Sign Out']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", eleme2);
			Thread.sleep(5000);

			login(student);
			Thread.sleep(20000);
			Automate.CallXpath(driver, ActionXpath.assignaccltsta, time, "acc");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.assignlearnltsta, time, "Select learn");
			Thread.sleep(10000);
			Automate.CallXpath(driver, ActionXpath.assignexpltsta, time, "expand assign");
			Thread.sleep(2000);
			JavascriptExecutor js22 = (JavascriptExecutor) driver;
			WebElement element22 = driver.findElement(By.xpath("//p[.='spoon']"));

			js22.executeScript("arguments[0].scrollIntoView(true);", element22);

			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.assignltstasign, time, "acc");
			Thread.sleep(5000);
			Automate.CallXpath(driver, ActionXpath.profile, time, "profile");
			Automate.CallXpath(driver, ActionXpath.logout, time, "logout");

			login(faculty);
			Thread.sleep(4000);
			Automate.CallXpath(driver, ActionXpath.facClickacademics, time, "facClickacademics");
			Thread.sleep(3000);
			Automate.CallXpath(driver, ActionXpath.facclickcouse, time, "facclickcouse");
			Thread.sleep(5000);

			Automate.CallXpath(driver, ActionXpath.exapnd, time, "Exapand");
			Thread.sleep(2000);
			JavascriptExecutor js23 = (JavascriptExecutor) driver;
			WebElement element23 = driver.findElement(By.xpath("//p[.='spru']"));
			js23.executeScript("arguments[0].scrollIntoView(true);", element23);

			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facdot, time, "facdot");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facdelete1, time, "facdelete");
			Thread.sleep(2000);
			Automate.CallXpath(driver, ActionXpath.facdele1, time, "facdele");
			Thread.sleep(8000);
			Thread.sleep(2000);

			WebDriverWait waie22 = new WebDriverWait(driver, 20);
			WebElement elemen24 = waie22.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div/button/span[1]")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", elemen24);
			Thread.sleep(2000);

			WebDriverWait wae21 = new WebDriverWait(driver, 20);
			WebElement eleme25 = wae21.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[.='Sign Out']")));
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", eleme25);

			Thread.sleep(5000);
			log.info("TC:46 Starting Assignment Create, Publish, View, Delete test case execution PASSED \n\n");
		} catch (Exception e) {
			log.warning("TC:46 Starting Assignment Create, Publish, View, Delete test case execution FAILED \n\n");
			Thread.sleep(2000);
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