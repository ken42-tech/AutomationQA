package com.ken42;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;

import com.opencsv.CSVReader;
import java.util.logging.*;

public class Pfs_portal {
    private static WebDriver driver;
	static int time = 4000;
	public static Logger log = Logger.getLogger("Pfs_portal");
    
    public static void main( String[] args ) throws Exception
    {
        FileHandler logFile = new FileHandler("C:\\Users\\Public\\Documents\\PFS_results.log");
	    logFile.setFormatter(new SimpleFormatter());
	    log.addHandler(logFile);
        String CSV_PATH = "C:\\Users\\Public\\Documents\\pfs3.csv";
        CSVReader csvReader;
        int count =0;
        csvReader = new CSVReader(new FileReader(CSV_PATH));
        log.info("=============Automation testing of PFS portal ============ \n\n");

        String[] csvCell;
        //This while loop will go over all the portals from CSV file and test for Student
        //and facutly logins
        while ((csvCell = csvReader.readNext()) != null) {

            if (count == 0){
                count = count + 1;
                continue;
            }
            String PFSurl = csvCell[0];
            String Email = csvCell[1];
            String Role = csvCell[2];
            String Browser = csvCell[3];
            System.out.print(csvCell[1]);
            System.out.println(PFSurl);
            System.out.println((Email));
            System.out.println(Role);
            System.out.print(Browser);
            initDriver(Browser, PFSurl, Role);
            login(Email);
            Thread.sleep(10000);
            //Below If will execute all Student related test cases
            if ("student".equals(Role)) {
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
                testStudentEvent(PFSurl);
                testStudentsignout(PFSurl);
                log.info("STUDENT PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
            }
            //This block will execute all facutly related test cases
            else if ("faculty".equals(Role)){
                System.out.println("Executing Faculty portal");
                testFaculty(PFSurl);
                testFacultyQuestionBank(PFSurl);
                testFacultyCourseContent(PFSurl);
                testFacultyExamination(PFSurl);
                testFacultyMYStudent(PFSurl);
                testFacultyAttendance(PFSurl);
                testFaculityTimetable(PFSurl);
                testFacultyService(PFSurl);
                testFacultyEvent(PFSurl);
                testFacultyViewPr(PFSurl);
                log.info("FACULTY PORTAL TEST CASES EXECUTION COMPLETED\n\n\n");
            }
            //After all test are over close the browser
            quitDriver(PFSurl);
        }
    }
    @BeforeSuite
    public static void initDriver(String Browser, String url, String Role) throws Exception {
        if("chrome".equals(Browser)){
            ChromeOptions op = new ChromeOptions();
            op.addArguments("--disable-notifications");
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(op);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        else if ("edge".equals(Browser)){
            System.setProperty("webdriver.edge.driver",
                "C:\\Users\\Public\\Documents\\msedgedriver.exe");
            EdgeOptions op = new EdgeOptions();
            // WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } else if ("firefox".equals(Browser)){
            System.setProperty("webdriver.edge.driver",
                "C:\\Users\\Public\\Documents\\geckodriver.exe");
            // EdgeOptions op = new EdgeOptions();
            FirefoxOptions fx = new FirefoxOptions();
            fx.addArguments("--disable-notifications");
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver(fx);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }

        
        log.info("Testing  "+url);
        log.info("\n");
        log.info("Testing for Role  "+Role);
        log.info("\n");
        
        // WebDriverManager.edgedriver().setup();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
       
        driver.manage().window().maximize();
    }
    @Test
    public static void login(String Email) throws Exception {
        int time=2000;
        String regex="Null";
        Automate.callSendkeys(driver, ActionXpath.email, Email,time);
		Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in");
        Automate.CallXpath(driver, ActionXpath.mobile, time, "Enter mobile Number");
        Automate.CallXpath(driver, ActionXpath.mobile2, time, "Click Mobile ");
        Automate.CallXpath(driver, ActionXpath.SignIn, time, "Sign in for otp");
        //Thread.sleep(time);
        Alert alert = driver.switchTo().alert(); // switch to alert
        String alertMessage= driver.switchTo().alert().getText(); // capture alert message
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
    }
    @Test(priority = 1)
	public static void testStudent(String url) throws Exception {
		try {
			log.info(" Student TC-1 :   Starting Home tab  case executation");
			Automate.CallXpath(driver, ActionXpath.expand, time, "Home tab expand");
			log.info(" Student TC-1 : Home tab test case PASSED..  \n");
		} catch (Exception e) {
            driver.get(url);
			log.warning("Student TC-1 : Home tab test case FAILED \n");
		}
	}
    @Test(priority = 2)
	public static void testStudentEnrollment(String url) throws  Exception {
		try {
			log.info("Student TC-2 :   Starting Student Enrollment  case Executation");
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
            log.info(" Student TC-2 : Enrollment of the Student Test Case PASSED \n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-2 : Enrollment of the Student Test Case FAILED \n");
		}
	}
    @Test(priority = 3)
	public static void testStudentAcademic(String url) throws  Exception{
		try {
			log.info("Student TC-3:  Starting Student Academic  case Executation");
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
            log.info("Student TC-3: Academic of the Student Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-3:Academic of the Student Test Case FAILED \n");
		}
	}
    @Test(priority = 4)
	public static void testStudentExamination(String url) throws Exception {
		try {
			log.info("Student TC-4:     Starting Student Examination test case Executation");
            Automate.CallXpath(driver, ActionXpath.ClickExam, time, "Click on Exam");
            // Automate.CallXpath(driver, ActionXpath.opDra, time, "Announcement");
            Automate.CallXpath(driver, ActionXpath.sortExamDate, time, "Sort Exam date");
            log.info("Student TC-4: Examination of the Student Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-4: Examination of the Student Test Case FAILED \n");
		}
	}
    @Test(priority = 5)
	public static void testStudentAttendance(String url) throws Exception {
		try {
			log.info("Student TC-5:     Starting Student Attendance test case Executation");
            Automate.CallXpath(driver, ActionXpath.ClickAttendance, time, "Select the Attendance");
            Automate.CallXpath(driver, ActionXpath.clickattendanceHistory, time, "Select the Attendance History");
            log.info("Student TC-5: Attendance of the Attendance Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-5: Attendance of the Attendance Test Case FAILED \n");
		}
	}
    @Test(priority = 6)
	public static void testStudentTimeTable(String url) throws Exception {
		try {
			log.info(" Student TC-6 :    Starting Student Timetable test case Executation ");
            Thread.sleep(2000);
            Automate.CallXpath(driver, ActionXpath.ClickTimetable, time, "Select time table");
            Automate.CallXpath(driver, ActionXpath.stunext, time, "Select slide of the Timetable");
            Automate.CallXpath(driver, ActionXpath.stubetween, time, "Selecte student Between");
            Automate.CallXpath(driver, ActionXpath.stunext, time, "time Table next");
            log.info("Student TC-6 :   Timetable of the Timetable Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-6 :  Timetable of the Timetable Test Case FAILED \n");
		}
	}
    @Test(priority = 7)
	public static void testStudentFees(String url) throws Exception {
		try {
			log.info("Student TC-7 :    Starting Student FEES test case Executation");
            Automate.CallXpath(driver, ActionXpath.ExpandFees, time, "Expand the Fees");
            Automate.CallXpath(driver, ActionXpath.clickMyCart, time, " on the My cart");
            Automate.CallXpath(driver, ActionXpath.stufeestype, time, "Select Fee type");
            Automate.CallXpath(driver, ActionXpath.stufeesamount, time, "Select fee amount");
            Automate.CallXpath(driver, ActionXpath.stufeescollect, time, "Select Fee collect");
            // Automate.CallXpath(driver, ActionXpath.stufeesschedulecurrency, time, "Select Fee Schedule currency");
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
		    log.info("Student TC-7 : FEES of the FEES Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-7 : FEES of the FEES Test Case FAILED");
		}
	}
    @Test(priority = 8)
	public static void testStudentFeedback(String url) throws Exception {
		try {
			log.info("Student TC-8 :   Starting Student FEEDBACK test case Executation");
            Automate.CallXpath(driver, ActionXpath.feedBack, time, "FeedBack");
            Automate.CallXpath(driver, ActionXpath.clickPrograme, time, "Programe Feedbcak");
            Automate.CallXpath(driver, ActionXpath.stufeedbackfaculty, time, "Feedback Faculty");
            Automate.CallXpath(driver, ActionXpath.stufeedbackfac, time, "feedBack Fac");
            Automate.CallXpath(driver, ActionXpath.stufeedbacksubject, time, " feedback Subject");
            Automate.CallXpath(driver, ActionXpath.stufeedbackcode, time, "Code");
            Automate.CallXpath(driver, ActionXpath.stufeedbackfeed, time, "Feed");
            log.info("Student TC-8 : FEEDBACK of the FEEDBACK Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-8 : FEEDBACK of the FEEDBACK Test Case FAILED \n");
		}
	}
    @Test(priority = 9)
	public static void testStudentStudentStatus(String url) throws Exception {
		try {
			log.info("Student TC-9:  Starting  Student Status test case Executation ");
            Automate.CallXpath(driver, ActionXpath.StudentService, time, "Student Status");
            Automate.CallXpath(driver, ActionXpath.Raisecase, time, "Raise case");
            Automate.CallXpath(driver, ActionXpath.MakeRaise, time, "Make Raise");
            Thread.sleep(3000);
            Automate.CallXpath(driver, ActionXpath.buttonRaisecase, time, "Button Raise");
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(0,2000)");
            Automate.CallXpath(driver, ActionXpath.cancel, time, "Cancel the raise case");
            log.info("Student TC-9: Student Status of the Student Status Test Case PASSED..\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-9:Student Status of the Student Status Test Case FAILED \n ");
		}
	}
    @Test(priority = 10)
	public static void testStudentEvent(String url) throws Exception {
		try {
			log.info("Student TC-10:     Starting Student Event case Executation ");
            Automate.CallXpath(driver, ActionXpath.Event, time, "Event");
            Automate.CallXpath(driver, ActionXpath.clcikEvent, time, "Open Event");
            Automate.CallXpath(driver, ActionXpath.back, time, "Go back");
            log.info("Student TC-10: EVENT Test Case PASSED..\n");
		}catch(Exception e){
            driver.get(url);
            Thread.sleep(time);
		    log.warning("Student TC-10: EVENT Test Case  FAILED\n");
        }
	}

	@Test(priority = 11)
	public static void testStudentsignout(String url) throws Exception {
		try {
			log.info("Student TC-11 :     Starting Student SIGNOUT  case Executation ");
            Automate.CallXpath(driver, ActionXpath.SelectPrtoSignout, time, " on the Profile on the student portal");
            Automate.CallXpath(driver, ActionXpath.signOut, time, "Signout the student portal");
            log.info("Student TC-11 : Student SIGNOUT Test Case PASSED...\n\n\n");
		}catch(Exception e) {
            driver.get(url);
            Thread.sleep(time);
			log.warning("Student TC-11 : Student SIGNOUT Test Case FAILED \n");
		}
	}

    @Test(priority = 12)
    public static void testFaculty(String url) throws Exception{
        try {
			log.info("Faculty TC-1 :     Starting FACULTY PORTAL Academic tab test case executation");
            Automate.CallXpath(driver, ActionXpath.facClickacademics, time,"facClickacademics");
            Automate.CallXpath(driver, ActionXpath.facclickdashboard, time,"facclickdashboard");		      
            Automate.CallXpath(driver, ActionXpath.facdbfilter, time,"facdbfilter");
            Automate.CallXpath(driver, ActionXpath.facdbfilterselect, time,"facdbfilterselect");
            Automate.CallXpath(driver, ActionXpath.facdbresfilter, time,"facdbresfilter");
            Automate.CallXpath(driver, ActionXpath.facdbrestypes, time,"facdbrestypes");
            Automate.CallXpath(driver, ActionXpath.facdbrestypesselect, time,"facdbrestypesselect");
            Automate.CallXpath(driver, ActionXpath.facdbresapply, time,"facdbresapply");
            log.info("Faculty TC-1 : ACADEMIC Test case PASSED \n");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-1 : ACADEMIC Test case FAILED");
        }
}
    @Test(priority = 13)
    public static void testFacultyQuestionBank(String url) throws Exception{
        try {
            log.info(" Faculty TC-2 :    Starting QuestionBank Tab test case Executation");
            Automate.CallXpath(driver, ActionXpath.facqb, time,"facqb");
            Automate.CallXpath(driver, ActionXpath.facaddque, time,"facaddque");
            Automate.CallXpath(driver, ActionXpath.facquetype, time,"facquetype");
            Automate.CallXpath(driver, ActionXpath.facquetypeselect, time,"facquetypeselect");
            Automate.CallXpath(driver, ActionXpath.facqueclass, time,"facqueclass");
            Automate.CallXpath(driver, ActionXpath.facqueclassselect, time,"facqueclassselect");
            Automate.CallXpath(driver, ActionXpath.facquesub, time,"facquesub");
            Automate.CallXpath(driver, ActionXpath.facquesubselect, time,"facquesubselect");		      
            Automate.CallXpath(driver, ActionXpath.facquesubmit, time,"facquesubmit");
            Automate.CallXpath(driver, ActionXpath.facqueback, time,"facqueback");
            log.info("Faculty TC-2 : QuestionBank Test Case PASSED \n ");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-2 :QuestionBank Test Case FAILED \n");
        }
    }

    @Test(priority = 14)
    public static void testFacultyCourseContent(String url) throws Exception{
        try {
            log.info("Faculty TC-3: Course Content Test Execution  Started ");
            // Automate.CallXpath(driver, ActionXpath.facClickacademics, time,"facClickacademics");
            Automate.CallXpath(driver, ActionXpath.faccc, time,"faccc");
            Automate.CallXpath(driver, ActionXpath.facccactivity, time,"facccactivity");
            Automate.CallXpath(driver, ActionXpath.faccform, time,"faccform");
            Automate.CallXpath(driver, ActionXpath.faccformadd, time,"faccformadd");
            Automate.CallXpath(driver, ActionXpath.facccformcancel, time,"facccformcancel");
            Automate.CallXpath(driver, ActionXpath.facccformyes, time,"facccformyes");
            Automate.CallXpath(driver, ActionXpath.facccres, time,"facccres");
            Automate.CallXpath(driver, ActionXpath.facccrespdf, time,"facccrespdf");
            Automate.CallXpath(driver, ActionXpath.facccresadd, time,"facccresadd");
            Automate.CallXpath(driver, ActionXpath.facccrescancel, time,"facccrescancel");
            Automate.CallXpath(driver, ActionXpath.facccresyes, time,"facccresyes");
            log.info("Faculty TC-3:  Course Content Test PASSED..\n");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-3:  Course Content Test FAILED \n");
        }

    }
    @Test(priority = 15)
    public static void testFacultyExamination(String url) throws Exception{
        try {
            log.info("Faculty TC-4:     Examination Test Executation Statred");
            Automate.CallXpath(driver, ActionXpath.facexam, time,"facexam");
            Automate.CallXpath(driver, ActionXpath.facexamarrow, time,"facexamarrow");
            Automate.CallXpath(driver, ActionXpath.facexamdropdown, time,"facexamdropdown");
            Automate.CallXpath(driver, ActionXpath.facexamexam, time,"facexamexam");
            Automate.CallXpath(driver, ActionXpath.facexamdate, time,"facexamdate");
            Automate.CallXpath(driver, ActionXpath.faceexamtime, time,"faceexamtime");
            log.info("Faculty TC-4: Examanation test cases PASSED... \n ");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-4: Examanation test cases FAILED \n");
        }
    }
    @Test(priority = 16)
    public static void testFacultyMYStudent(String url) throws Exception{
        try {
            log.info(" Faculty TC-5:   My Students Test Executation Started");
            Automate.CallXpath(driver, ActionXpath.facstudent, time,"facstudent");
            Automate.CallXpath(driver, ActionXpath.facstudrop, time,"facstudrop");
            Automate.CallXpath(driver, ActionXpath.facstudropselect, time,"facstudropselect");
            Automate.CallXpath(driver, ActionXpath.facstuname, time,"facstuname");
            Automate.CallXpath(driver, ActionXpath.facsturegid, time,"facsturegid");
            Automate.CallXpath(driver, ActionXpath.facstuemail, time,"facstuemail");
            Automate.CallXpath(driver, ActionXpath.facstuatt, time,"facstuatt");
            Automate.CallXpath(driver, ActionXpath.facstusearch, time,"facstusearch");
            log.info("Faculty TC-5: My Students  Test executation PASSED \n");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-5: My Students  Test executation FAILED \n");
        }
    }
    @Test(priority = 17)
    public static void testFacultyAttendance(String url) throws Exception{
        try {
            log.info("Faculty TC-6 :     Attendance Test Executation Startred ");
            Automate.CallXpath(driver, ActionXpath.facatt, time,"facatt");
            Automate.CallXpath(driver, ActionXpath.facattsub, time,"facattsub");
            Automate.CallXpath(driver, ActionXpath.facattsubselect, time,"facattsubselect");
            Automate.CallXpath(driver, ActionXpath.facattterm, time,"facattterm");
            Automate.CallXpath(driver, ActionXpath.facattdate, time,"facattdate");
            Automate.CallXpath(driver, ActionXpath.facattmark, time,"facattmark");
            Automate.CallXpath(driver, ActionXpath.facattmarkattendence, time,"facattmarkattendence");
            Automate.CallXpath(driver, ActionXpath.facatthistory, time,"facatthistory");
            Automate.CallXpath(driver, ActionXpath.facatthistsub, time,"facatthistsub");
            Automate.CallXpath(driver, ActionXpath.facatthistsubselect, time,"facatthistsubselect");
            Automate.CallXpath(driver, ActionXpath.facatthiststdate, time,"facatthiststdate");
            Automate.CallXpath(driver, ActionXpath.facatthistenddate, time,"facatthistenddate");
            Automate.CallXpath(driver, ActionXpath.facatthistterm, time,"facatthistterm");
            Automate.CallXpath(driver, ActionXpath.facatthistst, time,"facatthistst");
            Automate.CallXpath(driver, ActionXpath.facatthisend, time,"facatthisend");
            Automate.CallXpath(driver, ActionXpath.facatthistatt, time,"facatthistatt");
            log.info("Faculty TC-6 : Attendance Test Executation PASSED \n ");
            } catch (Exception e) {
                driver.get(url);
                Thread.sleep(time);
                log.warning("Faculty TC-6 : Attendance Test case FAILED \n");
        }
    }
    @Test(priority = 18)
	public static void testFaculityTimetable(String url) throws Exception {
		try {
			log.info("Faculty TC-7 :    Timetable Test Executation Started ");
            Automate.CallXpath(driver, ActionXpath.facClickTimetable, time,"facClickTimetable");
            Automate.CallXpath(driver, ActionXpath.facttmonth, time,"facttmonth");
            Automate.CallXpath(driver, ActionXpath.facttweek, time,"facttweek");
            Automate.CallXpath(driver, ActionXpath.facttday, time,"facttday");
            Automate.CallXpath(driver, ActionXpath.facnext, time,"facnext");
            Automate.CallXpath(driver, ActionXpath.facbetween, time,"facbetween");
            log.info("Faculty TC-7 : Timetable test case PASSED..\n ");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-7 :Timetable test case FAILED \n");
        }
    }
    @Test(priority = 19)
    public static void testFacultyService(String url) throws Exception{
        try {
            log.info( "Faculty TC-8:  Faculty Service Test case Started");
            Automate.CallXpath(driver, ActionXpath.facClickservice, time,"facClickservice");
            Automate.CallXpath(driver, ActionXpath.facraise, time,"facraise");
            Automate.CallXpath(driver, ActionXpath.facrequest, time,"facrequest");
            Automate.CallXpath(driver, ActionXpath.facraisecase, time,"facraisecase");
            Automate.CallXpath(driver, ActionXpath.facraisecaseno, time,"facraisecaseno");
            Automate.CallXpath(driver, ActionXpath.facraisesub, time,"facraisesub");
            Automate.CallXpath(driver, ActionXpath.facraisedesc, time,"facraisedesc");
            Automate.CallXpath(driver, ActionXpath.facraisedate, time,"facraisedate");
            Automate.CallXpath(driver, ActionXpath.facraisestatus, time,"facraisestatus");
            log.info("Faculty TC-8: Faculty Service Test case PASSED..\n ");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-8: Faculty Service Test case FAILED \n");
        }
    }
    @Test(priority = 20)
    public static void testFacultyEvent(String url) throws Exception {
        try {
            log.info( "Faculty TC-9 :  Faculty Portal Event Tab Test case Started");
            Automate.CallXpath(driver, ActionXpath.facEvent, time,"facEvent");
            Automate.CallXpath(driver, ActionXpath.faceventlocation, time,"faceventlocation");
            Automate.CallXpath(driver, ActionXpath.faceventlocationselect, time,"faceventlocationselect");
            log.info("Faculty TC-9 : Event Test case Executation PASSED \n");
        } catch (Exception e) {
            driver.get(url);
            Thread.sleep(time);
            log.warning("Faculty TC-9 : Event Test case FAILED \n");
        }
    }
    @Test(priority = 21)
    public static void testFacultyViewPr(String url) throws Exception{
        try {
            log.info(" Faculty TC-10:   View Profile test Executation Started");
            Automate.CallXpath(driver, ActionXpath.facselectpro, time,"facselectpro");
            Automate.CallXpath(driver, ActionXpath.facprofile, time,"facprofile");	      
        
            Automate.CallXpath(driver, ActionXpath.facSelectPrtoSignout, time,"facSelectPrtoSignout");
            Automate.CallXpath(driver, ActionXpath.facsignOut, time,"facsignOut");
            log.info(" Faculty TC-10: View Profile and Sign out Test Case PASSED \n ");
        } catch (Exception e) {
        log.warning("Faculty TC-10: View Profile and Sign out Test Case FAILED \n");
        }
    }
    @AfterSuite
    public static void quitDriver(String Url) throws Exception {
        Thread.sleep(3000);
        log.info("Completed testing of portal"+Url);
        log.info("\n\n");
        driver.quit();
    }
}