package com.ken42;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.util.logging.Logger;
import org.testng.annotations.Test;

public class Pfs_activity {
    public static Logger log = Logger.getLogger("Pfs_portal");
    static int time = 2000;

    @Test(priority = 45)
	public static void testAssessmentCreatePublishViewDelete(String student, String faculty, 
    String url, String Browser, String Role, WebDriver driver)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-45: Assement create ,pubish & delete Test excutaion was started...");
			Utils.login(driver, faculty);
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
			Utils.login(driver, student);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta");
			Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();
			Actions qq=new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
			Utils.smallSleepBetweenClicks(1);
			Utils.logout(driver, url, Role);
			Utils.smallSleepBetweenClicks(1);


			//// .........................Faculty delete assessment
			Utils.login(driver, faculty);
			Utils.bigSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content");
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpathWithScroll(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
					"Click on Assessment SVG");
			Utils.smallSleepBetweenClicks(1);
			new WebDriverWait(driver, 25).until(ExpectedConditions.elementToBeClickable(By.xpath("//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']"))).click();

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
			log.info("TC-45 Assement create, publish & delete test Executation Was PASSED....\n");
		}
		catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-45 Assement create,publish & delete test executation was FAILED...");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}

	@Test(priority = 46)
	public static void testFAssignmentCreatePublishViewDelete(String student, 
        String faculty, String url, String Browser, String Role, WebDriver driver)
			throws Exception {
		try {
			String returnArray[] = new String[2];
			System.out.println("TC-46 Assignment was Create ,publish & delete Test Excecuation Started...\n");
			Utils.login(driver, faculty);
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
			Utils.login(driver, student);
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
			Utils.login(driver,faculty);
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
			log.info("TC-46 Assignment create,publish & delete Was PASSED....\n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-46 Assignment create,publish & delte was FAILED....\n");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}

	@Test(priority = 47)
	public static void testForumCreatePublishViewDelete(String student, String faculty, 
        String url, String Browser, String Role, WebDriver driver) throws Exception {
		try { 
			System.out.println("TC-47 Faculty Fourm create,publish Delete test case Staerted...\n");
			String returnArray[] = new String[2];
			Utils.login(driver, faculty);
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
			Utils.login(driver, student);
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
			Utils.login(driver, faculty);
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

			log.info("TC-47 Faculty Fourm create,publish Delete test case PASSED...");

		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-47 Faculty Fourm create,publish Delete test case FAILED... \n");
			Pfs_portal.quitDriver(url);
            Pfs_portal.initDriver(Browser, url);
		}
	}
}
