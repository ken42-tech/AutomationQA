package com.ken42;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.jaxen.function.TrueFunction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import org.openqa.selenium.safari.SafariDriver;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.opencsv.CSVReader;
import java.util.logging.*;
import java.util.logging.FileHandler;
import java.text.SimpleDateFormat;


public class Pfs_portal {
	private static final Exception Exception = null;
	private static WebDriver driver;
	static int time = 1000;
	static Boolean headless;
	public static Logger log = Logger.getLogger("Pfs_portal");
	

	public static void main(String[] args) throws Exception {

		String folder = "";
		folder = getFolderPath();



		System.out.println("********Folder:"+folder);

		String CSV_PATH = "";
		String logFileName = "";
		CSV_PATH = folder+"\\pfs.csv";
		boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		logFileName = String.format(folder+"\\Testresult_%s.HTML", timeStamp);
		FileHandler logFile = new FileHandler(logFileName, append);
        logFile.setFormatter(new MyHtmlFormatter());
        log.addHandler(logFile);
		
		
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
			String facultyEmail = csvCell[1];
			String studentEmail = csvCell[2];
			String Role = "";
			String Browser = csvCell[3];
			String From = csvCell[4];
            String To = csvCell[5];
			String studentName = csvCell[6];
			// String headless = csvCell[7];
			int from = Integer.parseInt(From);
			int to = Integer.parseInt(To);
			if ((from < 1 || from > 68 || to < 1 || to > 68) || (to < from)){
				log.warning("The range specificed is incorrect it has to be between 1 and 68");
				log.warning("Please correct the From and To Columns in CSV file and run again");
				System.exit(1);
			}
			
			headless= getHeadless(csvCell);
			
			initDriver(Browser, PFSurl);
			if ((from >=1 && to <=16)){
				Utils.login(driver, studentEmail,PFSurl);
				Role = "student";
				student_login_set = true;
			} else if ((from >=17 && to <=39)){
				Utils.login(driver, facultyEmail,PFSurl);
				Role = "faculty";
				faculty_login_set = true;
			} else if ((from >=1 && to <=68) && (to >=1 && to <=68)){
				student_login_set = false;
				faculty_login_set = false;
			}
			
			
			log.info("**********************Testing for  Portal  "+PFSurl);
			// Below If will execute all Student related test cases
			for(int i=from;i<=to;i++){  
				switch (i){
					case 1: 
						if (!student_login_set){
							Utils.smallSleepBetweenClicks(i);
							Utils.login(driver, studentEmail,PFSurl);
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
							Utils.smallSleepBetweenClicks(1);
							Utils.login(driver, facultyEmail,PFSurl);
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
						Pfs_resource.testSpreadsheetFileType(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-45
						break;
					case 46:
						Pfs_resource.testPPTFileType(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-46
						break;
					case 47:
						Pfs_resource.testPDFFileType(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-47
						break;
					case 48:
						Pfs_resource.testVideoFileType(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-48
						break;
					case 49:
						Pfs_resource.testFacultyFilterResource(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-49
						break;
					case 50:
						Pfs_resource.testFacultyFilterPDFResource(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-50
						break;
					case 51:
						Pfs_resource.testFacultyFilterVideoResource(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-51
						break;
					case 52:
						Pfs_resource.testFacultyFilterLinksResource(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-52
						break;
					case 53:
						Pfs_activity.testFilterActivityAssignment(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-60
						break;
					case 54:
						Pfs_activity.testFilterActivityAssement(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-61
						break;
					case 55:
						Pfs_activity.testFilterActivityForum(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-62
						break;
					case 56:
						if(to < 57){
							Pfs_activity.testForumCreatePublishViewDelete(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver); //TC-55
						} else {
							log.info("TC-56: Skipped as it's a subset of next test");
						}
						
						break;
					case 57:
							Pfs_activity.testForumCreatePublishViewDeleteDecission(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver);//TC-59
						break;
				
					case 58:
						Pfs_activity.testForumCreatePublishEditDelete(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-63
						break;
					case 59:
						Pfs_activity.testForumCreateunPublishEditDelete(studentEmail, facultyEmail, PFSurl, 
								Browser, Role, driver); //TC-64
						break;
					
					case 60:
						Pfs_activity.testAssessmentCreatePublishViewDelete(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver); //TC-53
						break;
					case 61:
							Pfs_activity.testassesmentAttemptview(studentEmail, facultyEmail, 
									PFSurl, Browser, Role, driver);//TC-58
							break;
					
					case 62:
							Pfs_activity.testassesmenteditdelete(studentEmail, facultyEmail, PFSurl, 
									Browser, Role, driver); //TC-65
							break;
					case 63:
							Pfs_activity.testassesmentpublisheditview(studentEmail, facultyEmail, PFSurl, 
									Browser, Role, driver); //TC-66
							break;
					case 64:
							Pfs_activity.testFAssignmentCreatePublishViewDelete(studentEmail, facultyEmail, 
							PFSurl, Browser, Role, driver); //TC-54
							break;
					case 65:
								Pfs_activity.testFAssignmentCreatePublishsubmissionfileuploadchecking(studentEmail, facultyEmail, 
									PFSurl, Browser, Role, driver); //TC-56
								break;
					case 66:
								Pfs_activity.testFAssignmentCreatePublishsubmissiongradecheck(studentEmail, facultyEmail, 
									PFSurl, Browser, Role, driver,studentName); //TC-57
								break;
					case 67:
							Pfs_activity.testFAssignmentCreateEditDelete(studentEmail, facultyEmail, PFSurl, 
									Browser, Role, driver); //TC-67
							break;
					case 68:
							Pfs_activity.testFAssignmentCreatepublishEditDelete(studentEmail, facultyEmail, PFSurl, 
									Browser, Role, driver); //TC-68
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
		
		
		try {
			String folder = "";
			folder = getFolderPath();
			String ChromeDriver = folder+"\\chromedriver.exe";
			String EdgeDriver =  folder+"\\msedgedriver.exe";
			String FirefoxDriver = folder+"\\geckodriver.exe";
			System.out.println("Browser is ****"+Browser);
			System.out.println("URL is "+url);
			if ("chrome".equals(Browser)) {
				System.setProperty("webdriver.chrome.driver", ChromeDriver);
				ChromeOptions op = new ChromeOptions();
				if(headless){
					op.addArguments("--headless", "--window-size=1920,1080");
				}
				else{
				op.addArguments("--disable-notifications");
				}
				// op.addArguments("--headless");
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver(op);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("edge".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", EdgeDriver);
				driver = new EdgeDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if ("firefox".equals(Browser)) {
				System.setProperty("webdriver.edge.driver", FirefoxDriver);
				FirefoxOptions fx = new FirefoxOptions();
				fx.addArguments("--disable-notifications");
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver(fx);
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			} else if("safari".equals(Browser)){
				driver = new SafariDriver();
				driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			}
			System.out.println("********************"+url);
			driver.get(url);
			
			driver.manage().window().maximize();
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("UNABLE TO LAUNCH BROWSER");
			System.exit(01);
		}
	}

	@AfterSuite
	public static void quitDriver(String Url) throws Exception {
		// log.info("Completed testing of portal" + Url);
		driver.quit();
	}

	public static String getFolderPath() throws Exception{
		try {
			String folder = "";
			InputStream folderPath = Pfs_portal.class.getResourceAsStream("folder.csv");
			CSVReader csvFolderPath = new CSVReader(new InputStreamReader(folderPath, "UTF-8"));
			String[] csvCell_folder;
			while ((csvCell_folder = csvFolderPath.readNext()) != null) {
				folder = csvCell_folder[0];
			}
			System.out.println(folder);
			return folder;			
		}catch (Exception e){
			Utils.printException(e);
		}
		return null;
	}
	public static Boolean getHeadless(String[] csvCell) throws Exception{
		try {
			String headless=csvCell[7];
			if("TRUE".equals(headless)){
				return true;
			}
			else{
				return false;
			}
		}catch (Exception e){
			Utils.printException(e);
		}
		return false;
	}
}

