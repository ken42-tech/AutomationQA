package com.alumni;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.logging.Logger;

import org.testng.annotations.Test;
public class Alumni {
    public static Logger log = Logger.getLogger("App_portal");
    static int time = 2000;
    @Test(priority = 1)
	public static void testAppLogin(String url,WebDriver driver, String Email) throws Exception {
        try {
            Thread.sleep(3000);
            System.out.println("TC-1: Starting Alumini Login  test case execution ");
            Utils.clickXpath(driver, ActionXpath.login, time, "Login click");
            Utils.callSendkeys(driver, ActionXpath.InputEmail, "sprutirajtest@gmail.com", "Pass the Email");
            Utils.clickXpath(driver, ActionXpath.SignIn, time, "Click On sign in");
            Utils.smallSleepBetweenClicks(3);
            Utils.getAndSentOTP(driver);
            Utils.smallSleepBetweenClicks(3);
            Thread.sleep(50000);
            Utils.clickXpath(driver, ActionXpath.Verifylogin, time, "Verify the login ");
            log.info("  TC-1: Alumni login test case PASSED \n");

        } catch (Exception e) {
            Thread.sleep(time);

			log.warning("TC-1: Alumini login test case FAILED \n");
		}
}
@Test(priority = 2)
public static void testHome(WebDriver driver) throws Exception{
    try{
        System.out.println("home Tab Checking test executaion ");
        Utils.clickXpath(driver, ActionXpath.openDrawer, time, "open drawer");
        Thread.sleep(2000);
        WebElement l= driver.findElement(By.tagName("body"));
        	String p = l.getText();
			if (p.contains("Alumni Corner") && p.contains("Alumni of the Month") 
			&& p.contains("Job Openings") && p.contains("Events & Update") && p.contains("Updates")){
				log.info(" TC-2:  Home tab contain all Tab test case PASSED \n\n");
			}else {
				log.warning(" TC-2:  Home tab test case FAILED it does not contain all the tabs\n\n");
			}
            log.info("TC-2: Home Alumni Test case PASSED \n");
		} catch (Exception e) {
			Thread.sleep(time);
			log.warning("TC-2: Home  Alumini Test case FAILED \n");
		}
    }

    @Test(priority = 3)
public static void TestEvent(WebDriver driver) throws Exception{
    try{
        System.out.println("Event tab Tesr executation Started");
        Utils.clickXpath(driver, ActionXpath.ClickEvent, time, "Click on the Event");
        Utils.callSendkeys(driver, ActionXpath.searchEvent, "Alumni Meet 2023", null);
        Thread.sleep(2000);
       
                Utils.clickXpath(driver, ActionXpath.ClickKnowMorw, time, "Click on know more");
                Utils.bigSleepBetweenClicks(2);
                 log.info("TC-3: Event View Test Case PASSED \n");
                } catch (Exception e) {
                    Thread.sleep(time);

                    log.warning("TC-3: Event View Test Case Test Case FAILED \n");
                }
}
@Test(priority = 4)
public static void TestJobs(WebDriver driver) throws Exception{
    try{
        System.out.println("JObs tab Tesr executation Started");
        Utils.clickXpath(driver, ActionXpath.clickJob, time, "Click on job tab");
        Utils.clickXpath(driver, ActionXpath.clickKnow, time, "know more");
        Utils.smallSleepBetweenClicks(3);
log.info("TC-4: JOBS View Test Case PASSED \n");
                } catch (Exception e) {
                    Thread.sleep(time);

                    log.warning("TC-4: JOBS View Test Case Test Case FAILED \n");
                }
}
@Test(priority = 5)
public static void TestLogout(String url ,WebDriver driver) throws InterruptedException{
    System.out.println("Logout tab Tesr executation Started");
    try{
        Utils.logout(url, driver);

        log.info("TC-5: Logout View Test Case PASSED \n");
    } catch (Exception e) {
        Thread.sleep(time);

        log.warning("TC-5: Logout View Test Case Test Case FAILED \n");
    }
}
@Test(priority = 6)
public static void TestVerificationBackend(String SfUrl,WebDriver driver,String username,String password) throws Exception{
    try{
        System.out.println("salesforce login tab Test executation Started");
       Utils.callSendkeys(driver, ActionXpath.EnterUSer, "implementations@ken42.com.nitte.nittenew", "enter the username");
       Utils.callSendkeys(driver, ActionXpath.EnterPassword, "vQBcDRFtgz5VR23cDbGaVbAWzfcsfqD2","entert the password");
       Utils.clickXpath(driver, ActionXpath.LoginSlaesforce, time, "click the login salesforce");
       Thread.sleep(3000);
       Utils.clickXpath(driver, ActionXpath.clickStudentFaculities, time, "Click on the student & faculties Tab");
       Utils.callSendkeys(driver, ActionXpath.Searchalumni, "spruti", password);
       Actions qwe = new Actions(driver);
         qwe.moveByOffset(40, 40).click().perform();
         Utils.clickXpath(driver, ActionXpath.clickOnName, time, "Select the name");
         Utils.clickXpath(driver, ActionXpath.ClickOnDetailsTab, time, "clcik on details Tab");
         Utils.clickXpath(driver, ActionXpath.OpenDropDown, time, "open the drop down to delete");
         Utils.clickXpath(driver, ActionXpath.ClickOnDelete, time, "Click on delete ");
         Thread.sleep(3000);
         Utils.clickXpath(driver, ActionXpath.clikOnimg, time, "Click on the profile");
         Utils.clickXpath(driver, ActionXpath.logoutSalesforce, time, "Click on logout salesforce");
         
       log.info("TC-6: salesforceVerifiucation Test Case PASSED \n");
    }catch(Exception e){
        Thread.sleep(time);

        log.warning("TC-6: salesforceVerifiucation Test Case FAILED \n");
    }

}
}


