package com.ken42;
import java.io.IOException;
import java.util.logging.*;
import java.util.logging.SimpleFormatter;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class Automate {
	static Logger log = Logger.getLogger(Automate.class.getName());
    public static void CallXpath(WebDriver driver,String xpath, int time,String msg) throws Exception {
        
        int count = 0;
		int maxTries = 2;
		while (true){
			try {
				Thread.sleep(3000);
				log.info("Click on the:"+msg);
				driver.findElement(By.xpath(xpath)).click();
				break;
			} catch (Exception e) {
				Thread.sleep(3000);
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
			driver.findElement(By.xpath(Xpath)).sendKeys(Value);
			Thread.sleep(time1);
			break;
		} catch (Exception e) {
			if (++count == maxTries) throw e;
		}
	}
	
}
}