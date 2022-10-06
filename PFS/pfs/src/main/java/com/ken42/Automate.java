package com.ken42;
import java.util.logging.*;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class Automate {
	static Logger log = Logger.getLogger(Automate.class.getName());
    public static void CallXpath(WebDriver driver,String xpath, int time,String msg) throws Exception {
		JavascriptExecutor js3 = (JavascriptExecutor) driver; 
        int count = 0;
		int maxTries = 2;
		while (true){
			try {
				Thread.sleep(1000);
				log.info("Click on the:"+msg);
				new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath(xpath))).click();
				break;
			} catch (Exception e) {
				Thread.sleep(1000);
				log.warning("Failed to Click on the :"+msg);
				if (++count == maxTries) throw e;
			}
		}
		
	}
public static void callSendkeys(WebDriver driver,String Xpath, String Value, int time1) throws Exception {
	int count = 0;
	int maxTries = 2;
	while (true){
		try {
			log.info("Entering value"+Value);
			// driver.findElement(By.xpath(Xpath)).sendKeys(Value);
			new WebDriverWait(driver, 15).until(ExpectedConditions.elementToBeClickable(By.xpath(Xpath))).sendKeys(Value);
			// Thread.sleep(time1);
			break;
		} catch (Exception e) {
			Thread.sleep(1000);
			log.warning("Failed to send value  "+Value);
			if (++count == maxTries) throw e;
		}
	}
	
}
public static void cleartext(WebDriver driver, String faccmarks) {
}
public static void CallXpathWithScroll(WebDriver driver,String xpath, int time,String msg) throws Exception {
	JavascriptExecutor js = (JavascriptExecutor) driver; 
	int count = 0;
	int maxTries = 2;
	while (true){
		try {
			Thread.sleep(1000);
			log.info("Click on the:"+msg);
			WebElement el = driver.findElement(By.xpath(xpath));
			js.executeScript("arguments[0].scrollIntoView({ behavior: 'smooth', block: 'start' });", el);
			el.click();
			break;
		} catch (Exception e) {
			Thread.sleep(1000);
			log.warning("Failed to Click on the :"+msg);
			if (++count == maxTries) throw e;
		}
	}
	
}
}