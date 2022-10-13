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
		String facultyEmail = "test.faculty@ken42.com";
		String studentEmail = "test.student@ken42.com";
		String Role = "";
		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));

		String[] csvCell;
		while ((csvCell = csvReader.readNext()) != null) {
			boolean faculty_login_set = false;
			boolean student_login_set = false;
			if (count == 0) {
				count = count + 1;
				continue;
			}
			String PFSurl = csvCell[0];
			String Browser = csvCell[1];
			String From = csvCell[2];
            String To = csvCell[3];
			int from = Integer.parseInt(From);
			int to = Integer.parseInt(To);
			if ((from < 1 || from > 47 || to < 1 || to > 47) || (to < from)){
				log.warning("The range specificed is incorrect it has to be between 1 and 47");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}

			initDriver(Browser, PFSurl);
			if ((from >=1 && to <=16)){
				Utils.login(driver, studentEmail);
				Role = "student";
				faculty_login_set = true;
			} else if ((from >=17 && to <=39)){
				Utils.login(driver, facultyEmail);
				Role = "faculty";
				faculty_login_set = true;
			} else if ((from >=1 && to <=47) && (to >=1 && to <=47)){
				student_login_set = false;
				faculty_login_set = false;
			}
			
			
			log.info("**********************Testing for  Portal  "+PFSurl);
			// Below If will execute all Student related test cases
			for(int i=from;i<=to;i++){  
				switch (i){
					case 1: 
						if (!student_login_set){
							// Utils.logout(driver, PFSurl, Role);
							Utils.smallSleepBetweenClicks(i);
							Utils.login(driver, studentEmail);
						}
						Pfs_student.testStudent(PFSurl, driver); //TC-1
						break;
					case 2: 
						Pfs_student.testStudentEnrollment(PFSurl, driver);	 //TC-2	
						break;
					case 3:
						Pfs_student.testStudentAcademic(PFSurl, driver);  //TC-3
						break;
					case 4:
						Pfs_student.testStudentExamination(PFSurl, driver);  //TC-4
						break;
					case 5:
						Pfs_student.testStudentAttendance(PFSurl, driver);  //TC-5
						break;
					case 6:
						Pfs_student.testStudentTimeTable(PFSurl, driver);  //TC-6
						break;
					case 7:
						Pfs_student.testStudentFees(PFSurl, driver);  //TC-7
						break;
					case 8:
						Pfs_student.testStudentFeedback(PFSurl, driver);  //TC-8
						break;
					case 9:
						Pfs_student.testStudentStudentStatus(PFSurl, driver);  //TC-9
						break;
					case 10:
						Pfs_student.testStudentRaiseCase(studentEmail, facultyEmail, PFSurl, driver);  //TC-10
						break;
					case 11:
						Pfs_student.testStudentMakeRequest(studentEmail, facultyEmail, PFSurl, driver); //TC-11
						break;
					case 12:
						Pfs_student.testStudentEvent(studentEmail, facultyEmail, PFSurl, driver);  //TC-12
						break;
					case 13:
						Pfs_student.testStudentEditProfile(PFSurl, driver);  //TC-13
						break;
					case 14:
						Pfs_student.testStudentEditEducationDetails( PFSurl, driver);  //TC-14
						break;
					case 15:
						Pfs_student.testStudentEditAddress(PFSurl, driver);  //TC-15
						break;
					case 16:
						Pfs_student.testStudentSignout(PFSurl, driver);  //TC-16
						break;
					case 17: 
						if (!faculty_login_set){
							// Utils.logout(driver, PFSurl, Role);
							Utils.smallSleepBetweenClicks(i);
							Utils.login(driver, facultyEmail);
						}
						Pfs_faculty.testFaculty(PFSurl, driver); //TC-17
						break;
					case 18:
						Pfs_faculty.testFacultyQuestionBank(PFSurl, driver); //TC-18
						break;
					case 19:
						Pfs_faculty.testFacultyCourseContent(PFSurl, driver); //TC-19
						break;
					case 20:
						Pfs_faculty.testFacultyExamination(PFSurl, driver); //TC-20
						break;
					case 21:
						Pfs_faculty.testFacultyMYStudent(PFSurl, driver); //TC-21
						break;
					case 22:
						Pfs_faculty.testFacultyAttendance(PFSurl, driver); //TC-22
						break;
					case 23:
						Pfs_faculty.testFaculityTimetable(PFSurl, driver); //TC-23
						break;
					case 24:
						Pfs_faculty.testFacultyService(PFSurl, driver); //TC-24
						break;
					case 25:
						Pfs_faculty.testFacultyRaiseCase(studentEmail, facultyEmail, PFSurl, driver); //TC-25
						break;
					case 26:
						Pfs_faculty.testFacultyMakeRequest(studentEmail, facultyEmail, PFSurl, driver); //TC-26
						break;
					case 27:
						Pfs_faculty.testFacultyEvent(PFSurl, driver); //TC-27
						break;
					case 28:
						Pfs_faculty.testfacultyEditProfile(studentEmail, facultyEmail, PFSurl, driver); //TC-28
						break;
					case 29:
						Pfs_faculty.testfacultyEditAddress(studentEmail, facultyEmail, PFSurl, driver); //TC-29
						break;
					case 30:
						Pfs_faculty.testfacultyEditAcademicDetails(studentEmail, facultyEmail, PFSurl, driver); //TC-30
						break;
					case 31:
						Pfs_faculty.testfacultyEditReaserchSupervision(studentEmail, facultyEmail, PFSurl, driver); //TC-31
						break;
					case 32:
						Pfs_faculty.testfacultyEditResearchPublication(studentEmail, facultyEmail, PFSurl, driver); //TC-32
						break;
					case 33:
						Pfs_faculty.testfacultyEditConference(studentEmail, facultyEmail, PFSurl, driver); //TC-33
						break;
					case 34:
						Pfs_faculty.testfacultyEditBook(studentEmail, facultyEmail, PFSurl, driver); //TC-34
						break;
					case 35:
						Pfs_faculty.testfacultyEditProfessionalAssociation(studentEmail, facultyEmail, PFSurl, driver); //TC-35
						break;
					case 36:
						Pfs_faculty.testfacultyOthers(studentEmail, facultyEmail, PFSurl, driver); //TC-36
						break;
					case 37:
						Pfs_faculty.testFacultyDashboard(studentEmail, facultyEmail, PFSurl, driver); //TC-37
						break;
					case 38:
						Pfs_faculty.testFacultyQuestionPaper(studentEmail, facultyEmail, PFSurl, driver); //TC-38
						break;
					case 39:
						Pfs_faculty.testFacultySignout(PFSurl, driver); //TC-39
						break;
					case 40:
						Pfs_resource.testSpreadsheetCreateViewDelete(studentEmail, 
							facultyEmail, PFSurl, Browser, Role, driver); //TC-40
						break;
					case 41:
						Pfs_resource.testPPTCreateViewDelete(studentEmail, facultyEmail, PFSurl, 
							Browser, Role, driver); //TC-41
						break;
					case 42:
						Pfs_resource.testPDFCreateViewDelete(studentEmail, facultyEmail, PFSurl, 
							Browser, Role, driver); //TC-42
						break;
					case 43:
					Pfs_resource.testVideoCreateViewDelete(studentEmail, facultyEmail, PFSurl, 
						Browser, Role, driver); //TC-43
						break;
					case 44:
						Pfs_resource.testLinkCreateViewDelete(studentEmail, facultyEmail, PFSurl, 
							Browser, Role, driver); //TC-44
						break;
					case 45:
						Pfs_activity.testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver); //TC-45
						break;
					case 46:
						Pfs_activity.testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, 
						PFSurl, Browser, Role, driver); //TC-46
						break;
					case 47:
						Pfs_activity.testForumCreatePublishViewDelete(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver); //TC-47
						break;
					default:
						throw Exception;
				}
			}
			quitDriver(PFSurl);
			log.info("***************** COMPLETED TESTTING OF PORTAL" + PFSurl);
		}
		SendMail.sendEmail(logFileName);
	}

	@BeforeSuite
	public static void initDriver(String Browser, String url) throws Exception {
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
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\edgedriver_win64\\msedgedriver.exe");
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver",
						"C:\\Users\\Public\\Documents\\geckodriver-v0.31.0-win64\\geckodriver.exe");
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(45, TimeUnit.SECONDS);
			}
			driver.get(url);
			driver.manage().window().maximize();
		} catch (Exception e) {
			log.warning("UNABLE TO LAUNCH BROWSER \n\n\n");
			System.exit(01);
		}
	}

	@AfterSuite
	public static void quitDriver(String Url) throws Exception {
		// log.info("Completed testing of portal" + Url);
		log.info("\n");
		driver.quit();
	}
}
	

	
	