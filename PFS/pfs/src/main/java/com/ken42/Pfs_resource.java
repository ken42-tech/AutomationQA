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

    @Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url, 
        String Browser, String Role, WebDriver driver) throws Exception {
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
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}
}
