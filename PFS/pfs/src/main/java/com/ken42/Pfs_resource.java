package com.ken42;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Logger;
import org.testng.annotations.Test;

public class Pfs_resource {
	public static Logger log = Logger.getLogger("Pfs_portal");
	static int time = 2000;
	private static String PFSurl;

	@Test
	public static void resourceFacultyInitialSteps(String faculty, String url, WebDriver driver, Logger log) throws Exception {
		Utils.login(driver, faculty, url, log);
		Utils.checkAcadAndClick(driver, url);
		Utils.clickXpath(driver, ActionXpath.faccc, time, "Click on course content", log);
	}

	public static void resourceSubmitForm(String faculty, String url, WebDriver driver, Logger log) throws Exception {
		try {
			Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource", log);
			Utils.smallSleepBetweenClicks(2);
			Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "Click on URL resource link", log);
			// Utils.smallSleepBetweenClicks(1);
			// Utils.clickXpath(driver, ActionXpath.facssadd, time, "facssadd");
			Utils.smallSleepBetweenClicks(1);
			// Utils.clickXpath(driver, ActionXpath.facccresdescclick, time,
			// "facccresdescclick");
			Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time, log);
			// Utils.callSendkeys(driver, "//*[@id='tinymce']//p", "Testing", time);
			Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "Save URL link button", log);
			// log.info("resource create passed ");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning(" resource create FAILED  ");
			throw (e);
		}

	}

	public static void resourcePublishAndLogout(String faculty, String url,
			WebDriver driver, String fileName, String Role, Logger log) throws Exception {
		try {

			Utils.clickXpath(driver, "//p[. ='" + fileName + "']/../../.././..//*[local-name()='svg']", time,
					"Select PPT file name", log);
			Utils.clickXpath(driver, ActionXpath.facsspublish, time, "Click on publish button1", log);
			Utils.clickXpath(driver, ActionXpath.facsspublishyes, time, "Click on publish button2", log);
			Utils.bigSleepBetweenClicks(1);
			Utils.logout(driver, url, Role, log);

			// log.info(fileName + " Publish passed ");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning(fileName + " Publish FAILED  ");
			throw (e);
		}

	}

	public static void resourceStudentViewAndLogout(String faculty, String url,
			WebDriver driver, String fileName, String Role, Logger log) throws Exception {
		try {
			Utils.clickXpath(driver, "//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']", time,
					"Select PPT file name", log);
			Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "Click on View Spreadsheet", log);
			Utils.clickXpath(driver, ActionXpath.learn, time, "click learn", log);
			Utils.logout(driver, url, Role, log);
			// log.info(fileName + "Studentview passed ");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning(fileName + "Studentview FAILED  ");
			throw (e);
		}
	}

	public static void resourceDeleteAndLogout(String faculty, String url,
			WebDriver driver, String fileName, String Role, Logger log) throws Exception {
		try {
			Utils.clickXpath(driver, "//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']", time,
					"Select PPT file name", log);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "Click on Delete button1", log);
			Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "Click on delete button2", log);
			Utils.logout(driver, url, Role, log);
			// log.info(fileName + "Delete passed ");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning(fileName + "Delete FAILED  ");
			throw (e);
		}
	}

	@Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url,
			String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {

			String SpreadSheetFile = "";
			String folder = "";
			folder = Pfs_portal.getFolderPath();
			SpreadSheetFile = folder + "\\demo.xlsx";

			System.out.println("TC-40:  SpreadSheet resource Create View delete Test case Started");

			Utils.smallSleepBetweenClicks(1);
			resourceFacultyInitialSteps(faculty, url, driver, log);
			String program, subject;
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			Utils.smallSleepBetweenClicks(1);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");

			// String returnArray[] = new String[2];
			// returnArray = Utils.getClassSubjectAndSection(driver, url,"resource");
			// String programconverted = returnArray[0];
			// String subject = returnArray[1];

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick", log);
			resourceSubmitForm(faculty, url, driver, log);

			String fileName = "Excel_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time, log);
			driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys(SpreadSheetFile);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit", log);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes", log);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			Utils.smallSleepBetweenClicks(1);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "Click on SS SVG", log);

			resourcePublishAndLogout(faculty, url, driver, fileName, Role, log);

			// Student part starts
			Utils.login(driver, student, url, log);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn", log);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();

			Utils.smallSleepBetweenClicks(1);

			Utils.clickXpath(driver, ActionXpath.viewss, time, "viewss", log);
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role, log);
			// Student part ends

			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "facspreadsheetopen", log);
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role, log);
			log.info("TC-40: SpreadSheet resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-40: SpreadSheet resource Create View delete Test Case FAILED \n");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 41)
	public static void testPPTCreateViewDelete(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {

			String folder = "";
			folder = Pfs_portal.getFolderPath();
			String PPT_file = "";
			PPT_file = folder + "\\demo.pptx";

			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver,log);
			String program, subject;
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick", log);
			resourceSubmitForm(faculty, url, driver, log);
			String fileName = "PPT_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time, log);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']")).sendKeys(PPT_file);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit", log);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes", log);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen", log);
			resourcePublishAndLogout(faculty, url, driver, fileName, Role, log);

			Utils.login(driver, student, url, log);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn", log);

			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();

			Utils.clickXpath(driver, ActionXpath.viewppt, time, "viewppt", log);
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role, log);

			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen", log);
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role, log);
			log.info("TC-41: PPT resource Create View delete Test Case PASSED \n");

		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-41: PPT resource Create View delete Test Case FAILED \n");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 42)
	public static void testPDFCreateViewDelete(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			String PDF_file = "";
			String folder = "";
			folder = Pfs_portal.getFolderPath();
			PDF_file = folder + "\\demo.pdf";

			System.out.println("TC-42:  Create PDF resource publish and delete PDF");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			String program, subject;
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf", log);
			resourceSubmitForm(faculty, url, driver, log);

			String fileName = "PDF_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time, log);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys(PDF_file);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit", log);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes", log);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();
			Utils.clickXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen", log);
			resourcePublishAndLogout(faculty, url, driver, fileName, Role, log);

			// Now verify in student
			Utils.login(driver, student, url, log);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn", log);

			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();

			Utils.clickXpath(driver, ActionXpath.viewpdf, time, "viewpdf", log);
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role, log);

			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen", log);
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role, log);
			log.info("TC-42: Create PDF resource publish and delete PDF PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-42: Create PDF resource publish and delete PDF FAILED \n");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 43)
	public static void testVideoCreateViewDelete(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			String Video_file = "";
			String folder = "";
			folder = Pfs_portal.getFolderPath();
			Video_file = folder + "\\demo.mp4";

			System.out.println("TC-43:  Create Video resource create view  and delete");
			log.info("TC-43:  Create Video resource create view  and delete Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			String program, subject;
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick", log);
			resourceSubmitForm(faculty, url, driver, log);
			String fileName = "Video_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time, log);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys(Video_file);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit", log);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes", log);
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();
			Utils.clickXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen", log);
			resourcePublishAndLogout(faculty, url, driver, fileName, Role, log);

			// Student to verify
			Utils.login(driver, student, url, log);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "Click on learnlearn", log);

			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();

			Utils.clickXpath(driver, ActionXpath.viewvideo, time, "Click on video", log);
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role, log);

			// Faculty to delete
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen", log);
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role, log);
			log.info("TC-43: Create Video resource create view  and delete PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-43: Create Video resource create view  and delete FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 44)
	public static void testLinkCreateViewDelete(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-44:  Link resource Create View delete Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			String program, subject;
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select", log);
			program = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[1]");
			Utils.clickXpath(driver, ActionXpath.course, time, "click on subject", log);
			Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject", log);
			subject = Utils.getTEXT(driver, "(//*[. and @aria-haspopup='listbox'])[2]");

			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.faclinkclick, time, "faclinkclick", log);
			resourceSubmitForm(faculty, url, driver, log);
			String fileName = "Link_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time, log);
			Utils.callSendkeys(driver, ActionXpath.faclinkexternal, url, time, log);
			Utils.scrollUpOrDown(driver, time);
			Utils.scrollUpOrDown(driver, time);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit", log);
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes", log);

			if (Utils.publishlink(url)) {
				Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
				driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
				Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
				Utils.smallSleepBetweenClicks(2);
				driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();
				Utils.clickXpath(driver, ActionXpath.viewlink, time, "faclinkopen", log);
				resourcePublishAndLogout(faculty, url, driver, fileName, Role, log);
			} else {
				Utils.logout(driver, url, Role, log);
			}

			Utils.login(driver, student, url, log);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn", log);

			Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//li[text()='" + subject + "']")).click();

			Utils.clickXpath(driver, ActionXpath.viewlink, time, "viewlink", log);
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role, log);

			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen", log);
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role, log);
			log.info("TC-44 Link resource Create View delete Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 45)
	public static void testSpreadsheetFileType(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-45:  Test SpreadSheet resource Create View delete Test case Started");
			// if (Utils.checkBimtech(url)) {
			// log.info("TC-45 Test Spreadsheet is not supported on Bimtech");
			// return;
			// }
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick", log);

			Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Utils.smallSleepBetweenClicks(1);
			Utils.smallSleepBetweenClicks(2);
			WebElement s = driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
			String kenm = s.getText();
			String noSpaceStr = kenm.replaceAll("\\s", "");
			String name = "AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.xlsx,.xlsformat.";

			if (noSpaceStr.equals(name)) {
				System.out.println("It is not the excel file");
			} else {
				System.out.println("File uploaded");
			}

			driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
			Utils.logout(driver, url, faculty, log);
			log.info("TC-45: Test Spreadsheet File type Test Case PASSED");

		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-45 Test Spreadsheet File type test case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 46)
	public static void testPPTFileType(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-51:  Test PPT File type");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick", log);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource", log);
			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']"))
					.sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Utils.smallSleepBetweenClicks(2);

			WebElement s = driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
			String kenm = s.getText();
			String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method
			String name = "AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.ppt,.pptxformat.";
			if (noSpaceStr.equals(name)) {
				System.out.println("It is not the PPT file");
			} else {
				System.out.println("File uploaded");
			}
			driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
			Utils.logout(driver, url, faculty, log);
			log.info("TC-46: Test PPT File type Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-46: Test PPT File type Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 47)
	public static void testPDFFileType(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-47:  Test PDF File type Test Case");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf", log);
			Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource", log);
			Utils.smallSleepBetweenClicks(2);

			driver.findElement(By.xpath("//input[@accept='.pdf']")).sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");
			Utils.smallSleepBetweenClicks(2);
			WebElement s = driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
			String kenm = s.getText();
			String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method
			String name = "AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.pdfformat.";
			if (noSpaceStr.equals(name)) {
				System.out.println("It is not the PDF file");
			} else {
				System.out.println("File uploaded");
			}
			driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
			Utils.logout(driver, url, faculty, log);
			log.info("TC-47: Test PDF File type Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-47:Test PDF File type Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 48)
	public static void testVideoFileType(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-48: Test Video File type Test Case");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres", log);
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick", log);
			Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource", log);

			Utils.smallSleepBetweenClicks(2);
			driver.findElement(By.xpath("//input[@accept='.mp4']")).sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
			Utils.smallSleepBetweenClicks(2);

			WebElement s = driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
			String kenm = s.getText();
			String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method
			String name = "AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.mp4format.";
			if (noSpaceStr.equals(name)) {
				System.out.println("It is not the Video file");
			} else {
				System.out.println("File uploaded");
			}
			driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
			Utils.logout(driver, url, faculty, log);
			log.info("TC-48: Test Video File type Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-48:Test Video File type Test Case FAILED \n");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 49)
	public static void testFacultyFilterResource(String student, String faculty,
			String url, String Browser, String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-49:  PPT resource Filter Option View Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.bigSleepBetweenClicks(1);
			WebElement l = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p = l.getText();
			System.out.println("p" + p);
			if (p.contains("Pdf") && (p.contains("Presentations")) && (p.contains("Videos")) && (p.contains("Links"))) {

			} else {
				System.out.println(" All resource are not Presnet Quiting the Test. ");
				log.warning("TC-49 PPt resource Filter Option View Test Case FAILED \n");
				Utils.logout(driver, url, Role, log);
			}
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button", log);
			Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter ", log);
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button",
					log);
			Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span", log);
			Utils.clickXpath(driver, ActionXpath.faccPPTCheckBox, time, "select the PPT Check box ", log);
			Actions qwe = new Actions(driver);
			qwe.moveByOffset(40, 40).click().perform();

			WebElement l2 = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p2 = l2.getText();
			if (p2.contains("Presentations") && (!p2.contains("pdf")) && (!p2.contains("Spreadsheet"))
					&& (!p2.contains("Videos")) && (!p2.contains("Links"))) {

			} else {
				log.warning(" TC-50: PPT resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role, log);
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-49: PPT resource Filter Option View Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-49: PPT resource Filter Option View Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 50)
	public static void testFacultyFilterPDFResource(String student, String faculty, String url, String Browser,
			String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-50:  PDF resource Filter Option View Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.bigSleepBetweenClicks(1);
			WebElement l = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p = l.getText();
			System.out.println("p" + p);
			if (p.contains("Pdf") && (p.contains("Presentations")) && (p.contains("Videos")) && (p.contains("Links"))) {

			} else {
				System.out.println(" All resource are not Presnet Quiting the Test. ");
				Utils.logout(driver, url, Role, log);
				log.warning("TC-50: PDF resource Filter Option View Test Case FAILED");
			}
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "click on the filter button", log);

			Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "click the clear all on the filter ", log);
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "click on the filter to open to apply the filter ",
					log);
			Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "Open the reosurce Filter  span opnen", log);
			Utils.clickXpath(driver, ActionXpath.faccPDFCheckBox, time, "click the PDF checkbox", log);
			Actions qwe = new Actions(driver);
			qwe.moveByOffset(40, 40).click().perform();

			WebElement l2 = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p2 = l2.getText();
			if (p2.contains("Pdf") && (!p2.contains("Presentations")) && (!p2.contains("Spreadsheet"))
					&& (!p2.contains("Videos")) && (!p2.contains("Links"))) {

			} else {
				log.warning(" TC-50: PDF resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role, log);
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-50: PDF resource Filter Option View Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-50: PDF resource Filter Option View Test Case FAILED");
			Utils.logout(driver, Role, url, log);
		}
	}

	@Test(priority = 51)
	public static void testFacultyFilterVideoResource(String student, String faculty, String url, String Browser,
			String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-51:  Video resource Filter Option View Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.bigSleepBetweenClicks(1);
			WebElement l = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p = l.getText();
			System.out.println("p" + p);
			if (p.contains("Pdf") && (p.contains("Presentations")) && (p.contains("Videos")) && (p.contains("Links"))) {

			} else {
				System.out.println(" All resource are not Presnet Quiting the Test. ");
				log.warning("TC-51 Video resource Filter Option View Test Case FAILED \n");
				Utils.logout(driver, url, Role, log);
			}
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button", log);
			Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter", log);
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button",
					log);
			Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span", log);
			Utils.clickXpath(driver, ActionXpath.faccVideoCheckBox, time, "select the Viedo Check box", log);
			Actions qwe = new Actions(driver);
			qwe.moveByOffset(40, 40).click().perform();

			WebElement l2 = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p2 = l2.getText();
			if (p2.contains("Videos") && (!p2.contains("Presentations")) && (!p2.contains("Spreadsheet"))
					&& (!p2.contains("pdf")) && (!p2.contains("Links"))) {
				System.out.println(" TC-51: Video resource Filter Option Contains Video test case PASSED \n\n");
			} else {
				log.warning(" TC-51: Video resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
			Utils.logout(driver, url, Role, log);
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-51: Video resource Filter Option View Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-51: Video resource Filter Option View Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

	@Test(priority = 52)
	public static void testFacultyFilterLinksResource(String student, String faculty, String url, String Browser,
			String Role, WebDriver driver, Logger log) throws Exception {
		try {
			System.out.println("TC-52:  Links resource Filter Option View Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver, log);
			Utils.bigSleepBetweenClicks(1);
			WebElement l = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p = l.getText();
			System.out.println("p" + p);
			if (p.contains("Pdf") && (p.contains("Presentations")) && (p.contains("Videos")) && (p.contains("Links"))) {

			} else {
				System.out.println(" All resource are not Presnet Quiting the Test. ");
				log.warning("TC-52 Links resource Filter Option View Test Case FAILED \n");
				Utils.logout(driver, url, Role, log);
			}
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button", log);
			Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter", log);
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button",
					log);
			Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span", log);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(5,5)");
			Utils.clickXpath(driver, ActionXpath.faccLinksCheckBox, time, "Selec the Link Check Box filter", log);
			Actions qwe = new Actions(driver);
			qwe.moveByOffset(40, 40).click().perform();

			WebElement l2 = driver.findElement(By.xpath(
					"/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			String p2 = l2.getText();
			if (p2.contains("Links") && (!p2.contains("Presentations")) && (!p2.contains("Spreadsheet"))
					&& (!p2.contains("pdf")) && (!p2.contains("Videos"))) {
				System.out.println(" TC-52: Links resource Filter Option Contains Links test case PASSED \n\n");
			} else {
				log.warning(" TC-52: Links resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
			Utils.executeLongWait(url);
			Utils.logout(driver, url, Role, log);
			Utils.smallSleepBetweenClicks(1);
			log.info("TC-52: Links resource Filter Option View Test Case PASSED");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-52: Links resource Filter Option View Test Case FAILED");
			Utils.logout(driver, url, Role, log);
		}
	}

}