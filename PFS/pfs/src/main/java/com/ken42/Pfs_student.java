package com.ken42;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;
import org.testng.annotations.Test;

public class Pfs_student {
	public static Logger log = Logger.getLogger("Pfs_portal");
    static int time = 2000;


    @Test(priority = 1)
	public static void testStudent(String url, WebDriver driver) throws Exception {
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
	public static void testStudentEnrollment(String url, WebDriver driver) throws Exception {
		try {
			System.out.println(" TC-2:  Starting Student Enrollment  case execution");
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC-2 Student Enrollement Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
			Utils.goBackToHome(driver, url); 
			Utils.bigSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.ClickEnroll, time, "Expand Enrollment");
			Utils.clickXpath(driver, ActionXpath.clickCompletedEnroll, time, "select the Completes Enrollment");
			// Utils.scrollUpOrDown(driver, 2000);
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
	public static void testStudentAcademic(String url, WebDriver driver) throws Exception {
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
	public static void testStudentExamination(String url, WebDriver driver) throws Exception {
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
	public static void testStudentAttendance(String url, WebDriver driver) throws Exception {
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
	public static void testStudentTimeTable(String url, WebDriver driver) throws Exception {
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
	public static void testStudentFees(String url, WebDriver driver) throws Exception {
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
	public static void testStudentFeedback(String url, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-8:   Starting Student FEEDBACK test case execution");
			Utils.goBackToHome(driver, url);
			if (Utils.checkLtsta(url)){
				Utils.clickXpath(driver, ActionXpath.ltstafeedBack, time, "FeedBack");
			}else {
				Utils.clickXpath(driver, ActionXpath.feedBack, time, "FeedBack");
			}
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
			log.info("TC-8: Student FEEDBACK tab Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-8: Student FEEDBACK tab Test Case FAILED \n");
		}
	}

	@Test(priority = 9)
	public static void testStudentStudentStatus(String url, WebDriver driver) throws Exception {
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
	public static void testStudentRaiseCase(String student, String faculty, String url, WebDriver driver) throws InterruptedException {
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
	public static void testStudentMakeRequest(String student, String faculty, String url, WebDriver driver) throws InterruptedException {
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
	public static void testStudentEvent(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testStudentEditProfile(String url, WebDriver driver) throws Exception{
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
	public static void testStudentEditEducationDetails(String url, WebDriver driver) throws Exception {
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
	public static void testStudentEditAddress(String url, WebDriver driver) throws Exception {
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
	public static void testStudentSignout(String url, WebDriver driver) throws Exception {
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

}
