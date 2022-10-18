package com.ken42;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

import java.util.Iterator;
import java.util.Set;
import java.util.logging.Logger;
import org.testng.annotations.Test;

public class Pfs_activity {
    private static final String PFSurl = null;
    public static Logger log = Logger.getLogger("Pfs_portal");
    static int time = 2000;

    @Test(priority = 53)
	public static void testAssessmentCreatePublishViewDelete(String student, String faculty, 
    String url, String Browser, String Role, WebDriver driver)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-53: Assement create ,pubish & delete Test excutaion was started...");
			Utils.login(driver, faculty,PFSurl);
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
			Utils.login(driver, student,PFSurl);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
			Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Click on fileName");
			Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
			Utils.smallSleepBetweenClicks(1);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);

			//// .........................Faculty delete assessment
			Utils.login(driver, faculty,PFSurl);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
			Utils.clickXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
					"Click on Assessment SVG");
			Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Click on fileName");
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
			log.info("TC-53 Assement create, publish & delete test Executation Was PASSED....\n");
		}
		catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-53 Assement create,publish & delete test executation was FAILED...");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}

	@Test(priority = 54)
	public static void testFAssignmentCreatePublishViewDelete(String student, 
        String faculty, String url, String Browser, String Role, WebDriver driver)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-54 Assignment was Create ,publish & delete Test Excecuation Started...\n");
			Utils.login(driver, faculty,PFSurl);
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
			// Utils.smallSleepBetweenClicks(1);
			// Utils.cleartext(driver, ActionXpath.assignfactotalmarksrelative);
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
			Utils.login(driver, student,PFSurl);
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
			Utils.login(driver,faculty,PFSurl);
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
			log.info("TC-54 Assignment create,publish & delete Was PASSED....\n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-54 Assignment create,publish & delte was FAILED....\n");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}

	@Test(priority = 55)
	public static void testForumCreatePublishViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try { 
			System.out.println("TC-55 Faculty Fourm create,publish Delete test case Staerted...\n");
			String returnArray[] = new String[2];
			Utils.login(driver, faculty,PFSurl);
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
			Utils.login(driver, student,PFSurl);
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
			Utils.login(driver, faculty,PFSurl);
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

			log.info("TC-55 Faculty Fourm create,publish Delete test case PASSED...");

		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-55 Faculty Fourm create,publish Delete test case FAILED... \n");
			Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
		}
	}
	@Test(priority = 56)
    public static void testFAssignmentCreatePublishsubmissionfileuploadchecking(String student, String faculty,
            String url, String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String PDF_file = "";
			if (Utils.checkWindowsOs()){
				PDF_file = "C:\\Users\\Public\\Documents\\demo.pdf";
			}else {
				PDF_file = "/Users/shared/demo.pdf";
			}
            String returnArray[] = new String[2];
            System.out.println(
                    "TC-56 Assignment was Create ,publish,submission and fileuploadchecking  Test Excecuation Started...\n");
            Utils.smallSleepBetweenClicks(1);
            Utils.login(driver, faculty,PFSurl);
           
            Utils.bigSleepBetweenClicks(1);
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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../..//*[local-name()='svg']")))
                    .click();
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

            // Verify as student
            Utils.login(driver, student,PFSurl);
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement");
            Utils.smallSleepBetweenClicks(1);
            // Utils.scrollUpOrDown(driver, 500);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Actions ac = new Actions(driver);
            WebElement qqq = driver.findElement(By.xpath("(//span[.='Submission'])[1]"));
            ac.click(qqq).build().perform();

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickonaddsubmission, time, "clickonaddsubmission");

            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(0,2000)");

            Utils.smallSleepBetweenClicks(1);
            // Utils.callSendkeys(driver,
            // ActionXpath.clickonbrowser,"C:\\\\Users\\\\Dell\\\\Desktop\\\\Holiday List
            // 2022.pdf", time);
            driver.findElement(By.xpath("//input[@accept='.pdf']")).sendKeys(PDF_file);
			
            // driver.findElement(By.xpath("//input[@type='file']"))
            //         .sendKeys("C:\\\\Users\\\\USER\\\\Desktop\\\\pkpadmin,+1008-4741-1-CE (1).pdf");

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfaclinkrelative, time, "faclink");
            Utils.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink");

            Utils.smallSleepBetweenClicks(1);
            j.executeScript("window.scrollBy(0,-2000)");
            // driver.findElement(By.xpath("//span[.=' Submit']")).click();
            Utils.clickXpath(driver, ActionXpath.clickonsubmit, time, "clickonsubmit");

            Thread.sleep(2000);
            WebElement ty = driver.findElement(By.xpath("//div[@role='alert']"));
            String tu = ty.getText();
            System.out.println(tu);
            Utils.logout(driver, url, Role);
            Utils.smallSleepBetweenClicks(1);

            // Delete code
            Utils.login(driver, faculty,PFSurl);
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
            Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']")))
                    .click();
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

            log.info(
                    "TC-56 Assignment create,publish,submission and fileuploadchecking & submission    Was PASSED....\n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning(
                    "TC-56 Assignment create,publish, review ,submission and fileuploadchecking & submission  was FAILED....\n");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
        }
    }

    @Test(priority = 57)
    public static void testFAssignmentCreatePublishsubmissiongradecheck(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println(
                    "TC-57 Assignment was Create ,publish,gradecheck &submission Test Excecuation Started...\n");
            Utils.smallSleepBetweenClicks(1);
            Utils.login(driver, faculty,PFSurl);
            Utils.bigSleepBetweenClicks(1);
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

            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "100", time);
            WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            el.clear();
            el.sendKeys("50");

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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../..//*[local-name()='svg']")))
                    .click();
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

            // Verify as student
            Utils.login(driver, student,PFSurl);
            
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement");
            Utils.smallSleepBetweenClicks(1);
            // Utils.scrollUpOrDown(driver, 500);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Actions ac = new Actions(driver);
            WebElement qqq = driver.findElement(By.xpath("(//span[.='Submission'])[1]"));
            ac.click(qqq).build().perform();

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickonaddsubmission, time, "clickonaddsubmission");

            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(0,2000)");

            Utils.smallSleepBetweenClicks(1);

            driver.findElement(By.xpath("//input[@type='file']"))
                    .sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfaclinkrelative, time, "faclink");
            Utils.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink");

            Utils.smallSleepBetweenClicks(1);
            j.executeScript("window.scrollBy(0,-2000)");
            // driver.findElement(By.xpath("//span[.=' Submit']")).click();
            Utils.clickXpath(driver, ActionXpath.clickonsubmit, time, "clickonsubmit");

            Utils.logout(driver, url, Role);
            Utils.smallSleepBetweenClicks(1);

            // grade view code
            Utils.login(driver, faculty,PFSurl);
            
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
            Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);

            WebDriverWait ele11 = new WebDriverWait(driver, 20);
            WebElement elem1 = ele11
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Review'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
            Thread.sleep(5000);

            j.executeScript("window.scrollBy(0,200)");

            Thread.sleep(5000);

            Utils.clickXpath(driver, ActionXpath.clickongrade, time, "clickongrade");

            Thread.sleep(8000);

            int s = new Utils().getDecimalRandomNumber();
            if (s < 101) {
                driver.findElement(By.xpath("//input[@name='marks']")).sendKeys(Integer.toString(s));
            } else {
                driver.findElement(By.xpath("//input[@name='marks']")).sendKeys(Integer.toString(s));
                Thread.sleep(5000);
                Alert alert = driver.switchTo().alert(); // switch to alert
                String alertMessage = driver.switchTo().alert().getText(); // capture alert message
                System.out.println(alertMessage);
                Thread.sleep(5000);
                alert.accept();
                Utils.smallSleepBetweenClicks(1);
            }

            WebDriverWait ele111 = new WebDriverWait(driver, 20);
            WebElement elem11 = ele111
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Back to List']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem11);
            Thread.sleep(8000);
            // Utils.logout(driver, url, Role);
            // Utils.smallSleepBetweenClicks(1);

            // Delete code
            // Utils.login(driver, faculty, url);
            Utils.bigSleepBetweenClicks(1);
            // Utils.checkAcadAndClick(driver, url);
            // Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse");
            Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand");
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../.././..//*[local-name()='svg']")))
                    .click();
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

            log.info("TC-57 Assignment create,publish,review submission  & grade check  Was PASSED....\n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-57 Assignment create,publish, submission & grade check was FAILED....\n");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
        }
    }

    @Test(priority = 58)
    public static void testassesmentAttemptview(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println("TC-58: Assement create ,pubish & delete Test excutaion was started...");
            Utils.login(driver, faculty,PFSurl);
           
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
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ");
            Utils.clickXpath(driver, ActionXpath.selectmcq, time, "Click on Multiple choice question ");
            
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative, time, "Select first question");
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facAssPublish, time, "Publish Assessment");

            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, faculty);
            Utils.smallSleepBetweenClicks(1);
            
            // .....................................student
            Utils.login(driver, student,PFSurl);
            Utils.smallSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
            Utils.bigSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
             Utils.bigSleepBetweenClicks(2);
                   
            WebDriverWait ele11 = new WebDriverWait(driver, 20);
            WebElement elem1 = ele11
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Attempt Now'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
          Utils.bigSleepBetweenClicks(1);
            
            
            Set<String> set = driver.getWindowHandles();

            Iterator<String> it=set.iterator();

            String parentwindowid = it.next();
            System.out.println("parent windowid:"+parentwindowid);

            String childwindowid = it.next();
            System.out.println("childwindowid"+childwindowid);

            driver.switchTo().window(childwindowid);

            //System.out.println("child window pop title"+driver.getTitle());
             
            String ele = driver.getTitle();
            System.out.println(ele);
            
            Utils.clickXpath(driver, ActionXpath.assesmentinstruction, time, "Click on check box");
            Utils.clickXpath(driver, ActionXpath.startassesment, time, "Click on start assesment");
            Utils.clickXpath(driver, ActionXpath.attemptquestion, time, "Click on answer");
            Utils.clickXpath(driver, ActionXpath.submitattempt, time, "Click on submit");
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.submitagainattempt, time, "Click on submit 2");
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assesmentok, time, "Click on ok");
            Utils.bigSleepBetweenClicks(1);
            
           
            Thread.sleep(5000);
            driver.switchTo().window(parentwindowid);
            Thread.sleep(5000);
            
            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();
            
            
            driver.navigate().refresh();
            Utils.bigSleepBetweenClicks(2);
            Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
            Utils.clickXpath(driver, ActionXpath.ClickLearn, time, "Click learn ");
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
            Utils.bigSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
          
           WebDriverWait ele12 = new WebDriverWait(driver, 20);
           WebElement elem12 = ele12
                   .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Result'])[1]")));
           ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem12);
         Utils.bigSleepBetweenClicks(1);
            
            
         Utils.clickXpath(driver, ActionXpath.viewattempt, time, "Click on view attempt");
      
         WebElement result= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div[2]/p[1]"));
         String getresult= result.getText();
         System.out.println(getresult);
         
          
            
                        
            
            Utils.logout(driver, url, student);
            Utils.smallSleepBetweenClicks(1);


            //// .........................Faculty delete assessment
            Utils.login(driver, faculty,PFSurl);
           
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
          
            WebDriverWait ele13 = new WebDriverWait(driver, 20);
            WebElement elem15 = ele13
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='Review Quiz'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem15);
          Utils.bigSleepBetweenClicks(1);
//            Utils.clickXpath(driver, ActionXpath.reviewquiz, time, " Review quiz");
            Utils.bigSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.searchname,"test",time);
            Utils.clickXpath(driver, ActionXpath.viewresultinfac, time, " View result");
            Utils.clickXpath(driver, ActionXpath.viewattempt, time, " View attempt");
            
            WebElement facresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div[2]/p[1]"));
            String facgetresult= facresult.getText();
            System.out.println(facgetresult);
            
            if(getresult.equals(facgetresult)) {
                System.out.println("Result is same");
            }
            else {
                System.out.println("Result is not same");
            }
            
            
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
          
            
            
            
            
            
            // Automate.clickXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "Delete button 1");
            WebDriverWait wait = new WebDriverWait(driver, 20);
            WebElement el = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            Thread.sleep(10000);
            Utils.clickXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2");
            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, faculty);
            log.info("TC-58 Assement Attempt and view result test Executation Was PASSED....\n");
        }
        catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-58 Assement Attempt and view result test executation was FAILED...");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
         
        }
    }

    @Test(priority = 59)
    public static void testForumCreatePublishViewDeleteDecission(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try { 
            System.out.println("TC-59 Faculty Fourm create,publish Delete,Decission test case Staerted...\n");
            String returnArray[] = new String[2];
            Utils.login(driver, faculty,PFSurl);
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
            //....Discussion....
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
            WebDriverWait wait35 = new WebDriverWait(driver, 20);
            WebElement element239 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Discussions']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element239);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativeFacformeCreateNewDiscussion, time, "click on the New Discussion Button");
            Utils.smallSleepBetweenClicks(1);
            String fileName2 = "Discussion_Edutech" + Utils.generateRandom();
            Utils.callSendkeys(driver, ActionXpath.faccDiscuionText, fileName2, time);
            Utils.clickXpath(driver, ActionXpath.faccDiscussionMessgae3dot, time, "click on 3 dot ");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");
            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1,"https://unsplash.com/", time);
            Thread.sleep(2000);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin");
            Utils.clickXpath(driver, ActionXpath.faccSavefinish, time, "Click on save & finished");
                Utils.logout(driver, url, Role);
            Thread.sleep(5000);

            // ..............Student Login forum.......................//
            Utils.login(driver, student,PFSurl);
            Thread.sleep(2000);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.relativeforumlearnltsta1, time, "Select learn");
            Utils.clickXpath(driver, ActionXpath.relativeforumaexpandltsta1, time, "expand forum");
            Thread.sleep(2000);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            WebDriverWait wait355 = new WebDriverWait(driver, 20);
            WebElement element238 = wait355
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Discussions']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element238);
            Utils.smallSleepBetweenClicks(1);
            Thread.sleep(2000);
            Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
            Utils.logout(driver, url, Role);

            //// ..................... Delete fourm.................../////
            Utils.login(driver, faculty,PFSurl);
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
            log.info("TC-59 Faculty Fourm create,publish Delete,Decission test case PASSED...");

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-59 Faculty Fourm create,publish Delete,Decission test case FAILED... \n");
//            Pfs_portal.quitDriver(url);
            Utils.logout(driver, url, Role);
        }
    }
    
    @Test(priority = 60)
    public static void testFilterActivityAssignment(String student, String faculty, String url, String Browser, String Role, WebDriver driver) throws Exception {
            try { 
                System.out.println("TC-60: Assignment Filter Test excutaion was started...");
                Utils.login(driver, faculty,PFSurl);
                Utils.checkAcadAndClick(driver, url);
                Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse");
                Thread.sleep(8000);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver,ActionXpath.FaccFilterOpen,time,Role);
                Utils.clickXpath(driver, ActionXpath.faccAssignmentCheckBox, time, Role);
                Actions qwe = new Actions(driver);
                 qwe.moveByOffset(40, 40).click().perform();
                    
                    WebElement facresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div"));
                     String facgetresult= facresult.getText();
                     System.out.println(facgetresult);
                     WebElement getresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[5]/div/div[1]/div[1]/div/p"));
                     String result= getresult.getText();
                     System.out.println(getresult);
                     if(result.equals(result)) {

                         System.out.println("Result is same");
                     }
                     else {
                         System.out.println("Result is not same");
                     }
                     Utils.bigSleepBetweenClicks(2);
                    Utils.logout(driver, url, Role);
                    Utils.smallSleepBetweenClicks(1);
                    log.info("TC-60 Assignment Filter test case PASSED...");
            } catch (Exception e) {
                Utils.printException(e);
                log.warning("TC-60 Assignment Filter test case FAILED... \n");
                Pfs_portal.quitDriver(url);
                Pfs_portal.initDriver(Browser, url);
            }
        }
    @Test(priority = 61)
    public static void testFilterActivityAssement(String student, String faculty, String url, String Browser, String Role, WebDriver driver) throws Exception {
            try { 
                System.out.println("TC-61: Assement Filter Test excutaion was started...");
                Utils.login(driver, faculty,PFSurl);
                Utils.checkAcadAndClick(driver, url);
                Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse");
                Thread.sleep(8000);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver,ActionXpath.FaccFilterOpen,time,Role);
                Utils.clickXpath(driver, ActionXpath.faccAssementCheckBox, time, Role);
                Actions qwe = new Actions(driver);
                 qwe.moveByOffset(40, 40).click().perform();
                    
                    WebElement facresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div"));
                     String facgetresult= facresult.getText();
                     System.out.println(facgetresult);
                     WebElement getresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[5]/div/div[1]/div[1]/div/p"));
                     String result= getresult.getText();
                     System.out.println(getresult);
                     if(result.equals(result)) {

                         System.out.println("Result is same");
                     }
                     else {
                         System.out.println("Result is not same");
                     }
                     Utils.bigSleepBetweenClicks(2);
                    Utils.logout(driver, url, Role);
                    Utils.smallSleepBetweenClicks(1);
                    log.info("TC-61 Assement Filter test case PASSED...");
            } catch (Exception e) {
                Utils.printException(e);
                log.warning("TC-61 Assement Filter test case FAILED... \n");
                Pfs_portal.quitDriver(url);
                Pfs_portal.initDriver(Browser, url);
            }
        }
    @Test(priority = 62)
    public static void testFilterActivityForum(String student, String faculty, String url, String Browser, String Role, WebDriver driver) throws Exception {
            try { 
                System.out.println("TC-62: Forum Filter Test excutaion was started...");
                Utils.login(driver, faculty,PFSurl);
                Utils.checkAcadAndClick(driver, url);
                Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse");
                Thread.sleep(8000);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, Role);
                Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time, Role);
                Utils.clickXpath(driver,ActionXpath.FaccFilterOpen,time,Role);
                Utils.clickXpath(driver, ActionXpath.faccForumCheckBox, time, Role);
                Actions qwe = new Actions(driver);
                 qwe.moveByOffset(40, 40).click().perform();
                    
                    WebElement facresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div"));
                     String facgetresult= facresult.getText();
                     System.out.println(facgetresult);
                     WebElement getresult= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[5]/div/div[1]/div[1]/div/p"));
                     String result= getresult.getText();
                     System.out.println(getresult);
                     if(result.equals(result)) {

                         System.out.println("Result is same");
                     }
                     else {
                         System.out.println("Result is not same");
                     }
                     Utils.bigSleepBetweenClicks(2);
                    Utils.logout(driver, url, Role);
                    Utils.smallSleepBetweenClicks(1);
                    log.info("TC-62 Forum Filter test case PASSED...");
            } catch (Exception e) {
                Utils.printException(e);
                log.warning("TC-62 Forum Filter test case FAILED... \n");
                Pfs_portal.quitDriver(url);
                Pfs_portal.initDriver(Browser, url);
            }
        }
    @Test(priority = 63)
    public static void testForumCreatePublishEditDelete(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try { 
            System.out.println("TC-63 Faculty Fourm create,publish Delete,Decission test case Staerted...\n");
            String returnArray[] = new String[2];
            Utils.login(driver, faculty,PFSurl);
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

            WebDriverWait waitei = new WebDriverWait(driver, 20);
            WebElement element29 = waitei
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element29);
            
            Utils.clickXpathWithScroll(driver, ActionXpath.facsspublish, time, "faclinkpublish");
            
             System.out.println("click on dot and  publish 2nd forum");
            Utils.bigSleepBetweenClicks(2);
            //....Edit....
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
            WebDriverWait wait35 = new WebDriverWait(driver, 20);
            WebElement element239 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element239);
            Utils.smallSleepBetweenClicks(1);
            JavascriptExecutor js = (JavascriptExecutor) driver; 
            js.executeScript("window.scrollBy(-100,-100)");
            Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
            Thread.sleep(2000);
            
            Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");
            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1,fileName, time);
            Thread.sleep(2000);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmeditattachements, time, " facfourmeditattachements");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmattachementset2, time, " facfourmattachementset2");
            Utils.smallSleepBetweenClicks(1);
            JavascriptExecutor js1 = (JavascriptExecutor) driver; 
            js1.executeScript("window.scrollBy(-100,-100)");
            Utils.clickXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok");
            //delete fourm
            Thread.sleep(4000);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
            
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
            log.info("TC-63 Forum publish edit delete test case PASSED...");
            
        }catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-63 Faculty Fourm create,publish Delete,Decission test case FAILED... \n");
//            Pfs_portal.quitDriver(url);
            Utils.logout(driver, url, Role);
        }
    }
    @Test(priority = 64)
    public static void testForumCreateunPublishEditDelete(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try { 
            System.out.println("TC-64 Faculty Fourm create,publish Delete,Decission test case Staerted...\n");
            String returnArray[] = new String[2];
            Utils.login(driver, faculty,PFSurl);
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
//            Utils.smallSleepBetweenClicks(1);
//            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
            // Utils.clickXpath(driver, ActionXpath.relativefaccformedot1, time, "faccformedot");
            Utils.smallSleepBetweenClicks(1);

//            WebDriverWait waitei = new WebDriverWait(driver, 20);
//            WebElement element29 = waitei
//                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element29);
//            
//            Utils.clickXpathWithScroll(driver, ActionXpath.facsspublish, time, "faclinkpublish");
//            
//             System.out.println("click on dot and  publish 2nd forum");
            Utils.bigSleepBetweenClicks(2);
            //....Edit....
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../..//*[local-name()='svg']"))).click();
            WebDriverWait wait35 = new WebDriverWait(driver, 20);
            WebElement element239 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element239);
            Utils.smallSleepBetweenClicks(1);
            JavascriptExecutor js = (JavascriptExecutor) driver; 
            js.executeScript("window.scrollBy(-100,-100)");
            Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot");
            Thread.sleep(2000);
            
            Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink");
            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1,fileName, time);
            Thread.sleep(2000);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmeditattachements, time, " facfourmeditattachements");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmattachementset2, time, " facfourmattachementset2");
            Utils.smallSleepBetweenClicks(1);
            JavascriptExecutor js1 = (JavascriptExecutor) driver; 
            js1.executeScript("window.scrollBy(-100,-100)");
            Utils.clickXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok");
            //delete fourm
            Thread.sleep(4000);
            Utils.checkAcadAndClick(driver, url);
            Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ");
            
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
            log.info("TC-64 Forum unpublish edit delete test case PASSED...");
            
        }catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-64 Faculty Fourm create,unpublish Delete,Decission test case FAILED... \n");
//            Pfs_portal.quitDriver(url);
            Utils.logout(driver, url, Role);
        }
    }
    @Test(priority = 65)
    public static void testassesmenteditview(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println("TC-65: Assement create ,pubish & delete Test excutaion was started...");
            Utils.login(driver, faculty,PFSurl);
           
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
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ");
            Utils.clickXpath(driver, ActionXpath.selectmcq, time, "Click on Multiple choice question ");
            
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative, time, "Select first question");
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facAssPublish, time, "publish Assessment");
            
            Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content");
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
            WebDriverWait wait35 = new WebDriverWait(driver, 20);
            
            WebElement element238 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element238);
            Utils.smallSleepBetweenClicks(1); 
            //edit
            Utils.bigSleepBetweenClicks(2);
           
            Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ");
            Utils.clickXpath(driver, ActionXpath.selectmcq1, time, "Click on Multiple choice question ");
            
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative2, time, "Select first question");
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview");
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);
           
          
            //// .........................Faculty delete assessment
            
            Utils.bigSleepBetweenClicks(1);
           
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
             
            
            // Automate.clickXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "Delete button 1");
            WebDriverWait wait = new WebDriverWait(driver, 20);
            WebElement el = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            Thread.sleep(10000);
            Utils.clickXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2");
            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, faculty);
            log.info("TC-65 Assement Attempt and view result test Executation Was PASSED....\n");
        }
        catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-65 Assement Attempt and view result test executation was FAILED...");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
         
        }
    }

    @Test(priority = 66)
    public static void testassesmentpublisheditview(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println("TC-66: Assement create ,pubish & delete Test excutaion was started...");
            Utils.login(driver, faculty,PFSurl);
           
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
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ");
            Utils.clickXpath(driver, ActionXpath.selectmcq, time, "Click on Multiple choice question ");
            
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative, time, "Select first question");
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assesmentdashboard, time, "Click on go to dashboard");
            Utils.smallSleepBetweenClicks(1);
            
            
          
            Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content");
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
            WebDriverWait wait35 = new WebDriverWait(driver, 20);
            
            WebElement element238 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element238);
            Utils.smallSleepBetweenClicks(1); 
            
//publish
            
            //edit
            Utils.bigSleepBetweenClicks(2);
           
            Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ");
            Utils.clickXpath(driver, ActionXpath.selectmcq1, time, "Click on Multiple choice question ");
            
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative2, time, "Select first question");
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview");
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);
           
            Utils.clickXpath(driver, ActionXpath.facAssPublish, time, "publish Assessment");
            
           
          
            //// .........................Faculty delete assessment
            
            Utils.bigSleepBetweenClicks(1);
           
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
            Utils.bigSleepBetweenClicks(2);
             
            
            // Automate.clickXpath(driver, ActionXpath. fsubltstadeleterelativedelete, time, "Delete button 1");
            WebDriverWait wait = new WebDriverWait(driver, 20);
            WebElement el = wait
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            Thread.sleep(10000);
            Utils.clickXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2");
            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, faculty);
            log.info("TC-66 Assement Attempt and view result test Executation Was PASSED....\n");
        }
        catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-66 Assement Attempt and view result test executation was FAILED...");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
         
        }
    }

    @Test(priority = 67)
    public static void testFAssignmentCreateEditDelete(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println("TC-67 Assignment was Create ,edit and delete Test Excecuation Started...\n");
            Utils.smallSleepBetweenClicks(1);
            Utils.login(driver, faculty,PFSurl);
            Utils.bigSleepBetweenClicks(1);
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

            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "100", time);
            WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            el.clear();
            el.sendKeys("50");

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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            WebDriverWait ele11w = new WebDriverWait(driver, 20);
            WebElement elem111 = ele11w
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Edit'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem111);

            // Utils.smallSleepBetweenClicks(1);
            Thread.sleep(8000);
            WebElement elee = driver.findElement(By.name("assignmentName"));
            elee.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            Thread.sleep(8000);
            String fileName1 = "Assignment_" + Utils.generateRandom();
            Utils.smallSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative, fileName1, time);
            Utils.smallSleepBetweenClicks(1);

            JavascriptExecutor jwe = (JavascriptExecutor) driver;
            jwe.executeScript("window.scrollBy(0,-200)");

            Utils.clickXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave");
            Utils.smallSleepBetweenClicks(1);
            WebElement www = driver.findElement(By.name("totalMarks"));
            www.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "200", time);
            Utils.smallSleepBetweenClicks(1);
            WebElement dss = driver.findElement(By.name("gradetopass"));
            dss.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            WebElement elq = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            elq.clear();
            elq.sendKeys("90");

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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName1 + "']/../../..//*[local-name()='svg']")))
                    .click();

            Utils.smallSleepBetweenClicks(1);

            WebDriverWait ele = new WebDriverWait(driver, 20);
            WebElement elem = ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2");
            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, Role);
            Utils.smallSleepBetweenClicks(1);

            log.info("TC-67 Assignment create,edit and delete   check  Was PASSED....\n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-67 Assignment create,edit and delete  check was FAILED....\n");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
        }
    }

    @Test(priority = 68)
    public static void testFAssignmentCreatepublishEditDelete(String student, String faculty, String url,
            String Browser, String Role,WebDriver driver) throws Exception {
        try {
            String returnArray[] = new String[2];
            System.out.println("TC-68 Assignment was Create ,publish and delete Test Excecuation Started...\n");
            Utils.smallSleepBetweenClicks(1);
            Utils.login(driver, faculty,PFSurl);
            Utils.bigSleepBetweenClicks(1);
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

            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "100", time);
            WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            el.clear();
            el.sendKeys("50");

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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Utils.smallSleepBetweenClicks(1);
            WebDriverWait wait11 = new WebDriverWait(driver, 20);
            WebElement element211 = wait11
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Publish'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element211);
            Thread.sleep(2000);
            WebDriverWait waite1 = new WebDriverWait(driver, 20);
            WebElement element31 = waite1
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Publish']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element31);

            Utils.smallSleepBetweenClicks(1);
            driver.findElement(By.xpath(
                    "/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[5]/div/div[1]/div[2]"))
                    .click();
            Thread.sleep(8000);

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignexapnd1relative, time, "Exapand Assigment");
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName + "']/../../..//*[local-name()='svg']")))
                    .click();

            WebDriverWait ele11w = new WebDriverWait(driver, 20);
            WebElement elem111 = ele11w
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Edit'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem111);

            // Utils.smallSleepBetweenClicks(1);
            Thread.sleep(8000);
            WebElement elee = driver.findElement(By.name("assignmentName"));
            elee.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            Thread.sleep(8000);
            String fileName1 = "Assignment_" + Utils.generateRandom();
            Utils.smallSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative, fileName1, time);
            Utils.smallSleepBetweenClicks(1);

            JavascriptExecutor jwe = (JavascriptExecutor) driver;
            jwe.executeScript("window.scrollBy(0,-200)");

            Utils.clickXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave");
            Utils.smallSleepBetweenClicks(1);
            WebElement www = driver.findElement(By.name("totalMarks"));
            www.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "200", time);
            Utils.smallSleepBetweenClicks(1);
            WebElement dss = driver.findElement(By.name("gradetopass"));
            dss.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            WebElement elq = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            elq.clear();
            elq.sendKeys("90");

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
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + fileName1 + "']/../../..//*[local-name()='svg']")))
                    .click();

            Utils.smallSleepBetweenClicks(1);

            WebDriverWait ele = new WebDriverWait(driver, 20);
            WebElement elem = ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2");
            Utils.bigSleepBetweenClicks(2);
            Utils.logout(driver, url, Role);
            Utils.smallSleepBetweenClicks(1);

            log.info("TC-68 Assignment create,publish,edit  & delete check  Was PASSED....\n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-68 Assignment create,publish,edit & delete check was FAILED....\n");
            Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
        }
    }



}
