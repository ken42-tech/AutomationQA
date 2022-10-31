package com.alumni;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.ui.WebDriverWait;

import com.opencsv.CSVReader;

import org.openqa.selenium.support.ui.ExpectedConditions;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;


public class Utils {
    static Logger log = Logger.getLogger(Utils.class.getName());
	static int time = 1000;
    public static CharSequence result;
    public static void clickXpath(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver; 
        int count = 0;
		int maxTries = 4;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(500);
				log.warning("Failed to Click on the :"+msg);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}
    private static void printException(Exception e) {
        log.warning("Exception is  "+e);

    }
    public static void callSendkeys(WebDriver driver,String Xpath, String Value, String string) throws Exception {
		int count = 0;
		int maxTries = 4;
		while (true){
			try {
				log.info("Entering value"+Value);
				Thread.sleep(1000);
				new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath(Xpath))).sendKeys(Value);
				Thread.sleep(500);
				
				break;
			} catch (Exception e) {
				Thread.sleep(250);
				log.warning("Failed to send value  "+Value);
				if (++count == maxTries) {
					Utils.printException(e);
					throw e;
				}
			}
		}
	}
    public static void getAndSentOTP(WebDriver driver) throws Exception{
        String OTP = "";
        OTP= readOTP.check("imap.gmail.com", "imap", "sprutirajtest@gmail.com", "cgdizxcledbpwzxn");
          
                    Thread.sleep(40000);
                    System.out.println("OTP ***** " +OTP);
                   // Utils.callSendkeys(null, ActionXpath.Inputotp, OTP, OTP);
                 Utils.callSendkeys(driver,ActionXpath.Inputotp, OTP, "OTP");
                
    }
    @Test
	public static void bigSleepBetweenClicks(int loop) throws InterruptedException{
		int total_time = 7000 * loop;
		System.out.println("Sleeping for "+total_time);
		Thread.sleep(7000 * loop);
	}
    @Test
	public static void smallSleepBetweenClicks(int loop) throws InterruptedException{
		int total_time = 2000 * loop;
		System.out.println("Sleeping for "+total_time);
		Thread.sleep(2000 * loop);
	}
    @Test
	public static void scrollUpOrDown(WebDriver driver, int pixel){
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,pixel)");
	}
    @Test
	public static void logout(String url, WebDriver driver) throws Exception {
		try {
			smallSleepBetweenClicks(1);
			
			Utils.clickXpath(driver, ActionXpath.ClickProfile, time, "Click on  initial");
			Utils.clickXpath(driver, ActionXpath.clickLogout, time, "click on signout");
			
		} catch (Exception e) {
			Utils.printException(e);
			System.out.println("Failure in logout function");
			log.info("Failure in Logout function");
			throw(e);
		}
	}

}
