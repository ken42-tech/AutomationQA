package com.ken42;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.logging.Logger;

import org.testng.Reporter;
import org.testng.annotations.Test;
public class Pfs_faculty {
    public static Logger log = Logger.getLogger("Pfs_portal");
    static int time = 2000;

    @Test(priority = 17)
	public static void testFaculty(String url, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-17:  Starting FACULTY PORTAL Academic tab test case executation\n");
			Utils.goBackToHome(driver, url);
			Utils.bigSleepBetweenClicks(2);
			Utils.bigSleepBetweenClicks(2);
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Students") && p.contains("Classes Conducted") 
			&& p.contains("Assignments") && p.contains("Schedule") && p.contains("MY CLASSES")){
				log.info(" TC-17: Faculty Home tab test case PASSED");
			}else {
				log.warning(" TC-17: Faculty Home tab test case FAILED it does not contain all the tabs\n\n");
			}
		} catch (Exception e) {
			Utils.printException(e);
			driver.get(url);
			Thread.sleep(time);
			log.warning("TC-17: Faculty ACADEMIC Test case FAILED \n");
		}
	}

	@Test(priority = 18)
	public static void testFacultyQuestionBank(String url, WebDriver driver) throws Exception {
		try {
			System.out.println(" TC-18:  Faculty Starting QuestionBank Tab test case Executation");
			Utils.goBackToHome(driver, url);
			if(Utils.checkLtsta(url))
           	{
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
	public static void testFacultyCourseContent(String url, WebDriver driver) throws Exception {
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
			Utils.clickXpath(driver, ActionXpath.faccc, time, "click on the Course content");
			Utils.clickXpath(driver, ActionXpath.facccactivity, time, "clck on activity button ");
			Utils.clickXpath(driver, ActionXpath.facassessmentrelative, time, "select the activity option named was fourm");
			Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "click to add fourm");
			Utils.clickXpath(driver, ActionXpath.facccAsscancel, time, "cancel it ");
			Utils.clickXpath(driver, ActionXpath.faccAssYes, time, "confirm to cancel");
			Utils.clickXpath(driver, ActionXpath.faccc, time, "click on the course content");
			Utils.clickXpath(driver, ActionXpath.FaccClickResource, time, "Click Resource");
			Utils.clickXpath(driver, ActionXpath.facrescancel, time, "cancel the resources");
			log.info("TC-19:  Faculty Course Content Test PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-19:  Faculty Course Content Test FAILED \n");
		}
	}

	@Test(priority = 20)
	public static void testFacultyExamination(String url, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-20:     Faculty Examination Test Executation Statred");
			Utils.goBackToHome(driver, url);
			if (Utils.checkUrlToSkipTest(url)){
				log.info("TC_20 Faculty Examination Skipping this test as this is not applicable for this portal\n\n");
				return;
			}
				Utils.clickXpath(driver, ActionXpath.facexam, time, "Click on the Examination span");
				// Utils.clickXpath(driver, ActionXpath.facexamarrow, time, "facexamarrow");
				// Utils.clickXpath(driver, ActionXpath.facexamdropdown, time, "Examination naroow dropdown");
				// Utils.clickXpath(driver, ActionXpath.facexamexam, time, "facexamexam");
				// Utils.clickXpath(driver, ActionXpath.facexamdate, time, "facexamdate");
				// Utils.clickXpath(driver, ActionXpath.faceexamtime, time, "faceexamtime");
				log.info("TC-20: Faculty Examanation test cases PASSED... \n ");
			} catch (Exception e) {
				Utils.printException(e);
				Utils.goBackToHome(driver, url);
				log.warning("TC-20: Faculty Examanation test cases FAILED \n");
			}
	}

	@Test(priority = 21)
	public static void testFacultyMYStudent(String url, WebDriver driver) throws Exception {
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
			Utils.bigSleepBetweenClicks(2);
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			// log.info(p);
			System.out.println(p);
			if (p.contains("My Students") && p.contains("Subjects")){
				log.info(" TC-21: Faculty My Student  tab test case PASSED \n\n");
			}else {
				log.warning(" TC-21: Faculty My Student  tab test case FAILED it does not contain all the tabs\n\n");
			}
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-21: Faculty My Student  tab test case FAILED \n");
		}
	}

	@Test(priority = 22)
	public static void testFacultyAttendance(String url, WebDriver driver) throws Exception {
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
	public static void testFaculityTimetable(String url, WebDriver driver) throws Exception {
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
	public static void testFacultyService(String url, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-24:  Faculty Service Test case Started");
			Utils.goBackToHome(driver, url);
			// Utils.bigSleepBetweenClicks(1);
			Utils.clickOnFacultyService(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.FacRaisebutton, time, "Click on Raise case button");
			Utils.smallSleepBetweenClicks(1);
			// Utils.scrollUpOrDown(driver, 300);
			// Utils.clickXpath(driver, ActionXpath.facCancelSer, time, "cancel");
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-24: Faculty Service test cancel button Test case PASSED \n ");
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-24: Faculty Service test cancel button Test case FAILED \n");
		}
	}

	@Test(priority = 25)
	public static void testFacultyRaiseCase(String student, String faculty, String url, WebDriver driver) throws InterruptedException {
		try {
			System.out.println("TC-25 Faculty Service Raise A Case ");
			Utils.goBackToHome(driver, url);
			Utils.clickOnFacultyService(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.FacRaisebutton, time, "Raise a case button");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Raisebutton, time, "Raise case button");
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
	public static void testFacultyMakeRequest(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testFacultyEvent(String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditProfile(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditAddress(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditAcademicDetails(String student, String faculty, String url, WebDriver driver) throws Exception {
		try {

			System.out.println(" TC-30:   Academic Details Started  case executation");
			Utils.goBackToHome(driver, url);
			Utils.smallSleepBetweenClicks(2);
			Utils.clickXpath(driver, ActionXpath.FCCportal, time, "facSelectPrtoSignout");
			Utils.clickXpath(driver, ActionXpath.faccProfile, time, "facprofile");
			Utils.clickXpath(driver, ActionXpath.facdpacdeails, time, "facdpacdeails");
			Utils.smallSleepBetweenClicks(2);
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
	public static void testfacultyEditReaserchSupervision(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditResearchPublication(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditConference(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditBook(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyEditProfessionalAssociation(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testfacultyOthers(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testFacultyDashboard(String student, String faculty, String url, WebDriver driver) throws Exception {
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
			Utils.clickXpath(driver, ActionXpath.clickFacDashdevnosbm, time, "Dashboard");
			Utils.bigSleepBetweenClicks(1);
			WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Overview") && p.contains("Activities")){
				log.info(" TC-37: Faculty My Student  tab test case PASSED \n\n");
			}else {
				log.warning(" TC-37: Faculty My Student  tab test case FAILED it does not contain all the tabs\n\n");
			}
		} catch (Exception e) {
			Utils.printException(e);
			Utils.goBackToHome(driver, url);
			log.warning("TC-37: Faculty DASHBOARD  FAILED \n");
		}
	}

	@Test(priority = 38)
	public static void testFacultyQuestionPaper(String student, String faculty, String url, WebDriver driver) throws Exception {
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
	public static void testFacultySignout(String url, WebDriver driver) throws Exception {
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
    
}
