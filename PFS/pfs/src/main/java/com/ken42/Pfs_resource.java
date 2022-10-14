package com.ken42;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.logging.Logger;
import org.testng.annotations.Test;

public class Pfs_resource {
    public static Logger log = Logger.getLogger("Pfs_portal");
    static int time = 2000;

	@Test
	public static void resourceFacultyInitialSteps(String faculty, String url, WebDriver driver) throws Exception{
		Utils.login(driver, faculty);
		Utils.checkAcadAndClick(driver, url);
		Utils.clickXpath(driver, ActionXpath.faccc, time, "faccc");
	}

	public static void resourceSubmitForm( String faculty, String url, WebDriver driver) throws Exception{
		Utils.clickXpath(driver, ActionXpath.facssadd, time, "facssadd");
		Utils.smallSleepBetweenClicks(1);
		Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
		Utils.smallSleepBetweenClicks(1);
		Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
		Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "facccressubmitform");
	}

	public static void resourcePublishAndLogout(String faculty, String url, 
		WebDriver driver, String fileName, String Role) throws Exception{
		Utils.clickXpath(driver, "//p[. ='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpathWithScroll(driver, ActionXpath.facsspublish, time, "faclinkpublish");
		Utils.clickXpath(driver, ActionXpath.facsspublishyes, time, "faclinkpublishyes");
		Utils.logout(driver, url, Role);
	}

	public static void resourceStudentViewAndLogout(String faculty, String url, 
		WebDriver driver, String fileName, String Role) throws Exception{
		Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "View Spreadsheet");
		Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
		Utils.logout(driver, url, Role);
	}

	public static void resourceDeleteAndLogout(String faculty, String url, 
		WebDriver driver, String fileName, String Role) throws Exception{
		Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "facspreadsheetdelete");
		Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "facspreadsheetdelete2");
		Utils.logout(driver, url, Role);
	}

    @Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url, 
        String Browser, String Role, WebDriver driver) throws Exception {
		try {
			if (Utils.checkBimtech(url)){
				log.info("TC-40 Spreadsheet is not supported on this portal");
				return;
			}
			System.out.println("TC-40:  SpreadSheet resource Create View delete Test case Started");
			
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick");
			resourceSubmitForm(faculty, url, driver);

			String fileName = "Excel_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys("C:\\Users\\Public\\Documents\\demo.xlsx");
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "Click on SS SVG");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Student part starts
			Utils.login(driver, student);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpath(driver, ActionXpath.viewss, time, "viewss");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);
			//Student part ends

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpathWithScroll(driver, ActionXpath.facssopen, time, "facspreadsheetopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-40: SpreadSheet resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
			log.warning("TC-40: SpreadSheet resource Create View delete Test Case FAILED \n");
		}
	}

	

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			resourceSubmitForm(faculty, url, driver);
			String fileName = "PPT_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);
			

			Utils.login(driver, student);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpathWithScroll(driver, ActionXpath.viewppt, time, "viewppt");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);
			
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-41: PPT resource Create View delete Test Case PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
			log.warning("TC-41: PPT resource Create View delete Test Case FAILED \n");
		}
	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-42:  Create PDF resource publish and delete PDF");
			resourceFacultyInitialSteps(faculty, url, driver);			
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			resourceSubmitForm(faculty, url, driver);
			
			String fileName = "PDF_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Now verify in student
			Utils.login(driver, student);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpathWithScroll(driver, ActionXpath.viewpdf, time, "viewpdf");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpathWithScroll(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-42: Create PDF resource publish and delete PDF PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
			log.warning("TC-42: Create PDF resource publish and delete PDF FAILED \n");
		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-43:  Create Video resource create view  and delete");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
			resourceSubmitForm(faculty, url, driver);
			String fileName = "Video_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.mp4");
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Student to verify
			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "Click on learnlearn");
			Utils.clickXpathWithScroll(driver, ActionXpath.viewvideo, time, "Click on video");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			//Faculty to delete
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpathWithScroll(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-43: Create Video resource create view  and delete PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
			log.warning("TC-43: Create Video resource create view  and delete FAILED \n");
		}
	}

		
	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try {
			System.out.println("TC-44:  Link resource Create View delete Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.faclinkclick, time, "faclinkclick");
			resourceSubmitForm(faculty, url, driver);
			String fileName = "Link_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			Utils.callSendkeys(driver, ActionXpath.faclinkexternal, url, time);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.logout(driver, url, Role);

			Utils.login(driver, student);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpathWithScroll(driver, ActionXpath.viewlink, time, "viewlink");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpathWithScroll(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-44 Link resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED \n");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}
}
