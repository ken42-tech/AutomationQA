package com.alumni;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import java.util.logging.*;
import org.openqa.selenium.JavascriptExecutor;

import java.util.logging.FileHandler;

import com.opencsv.CSVReader;

import io.github.bonigarcia.wdm.WebDriverManager;
public class Alumni_portal {
    public static WebDriver driver;
	static int time = 1000;
	//private static final Exception Exception = null;
    public static Logger log = Logger.getLogger("App_portal");


    public static void main(String[] args) throws Exception {
        String CSV_PATH = "";
        String logFileName = "";

        boolean append = false;
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        CSV_PATH = "C:\\Users\\Public\\Documents\\App.csv";
        logFileName = String.format("C:\\Users\\Public\\Documents\\Application_Test_results%s.HTML", timeStamp);
        FileHandler logFile = new FileHandler(logFileName, append);
        logFile.setFormatter(new MyHtmlFormatter());
        log.addHandler(logFile);
		CSVReader csvReader;
		int count = 0;
		csvReader = new CSVReader(new FileReader(CSV_PATH));
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	
		String[] csvCell;
		while ((csvCell = csvReader.readNext()) != null) {

			if (count == 0) {
				count = count + 1;
				continue;
			}
			String url = csvCell[0];
			String browser = csvCell[1];
			String Email = csvCell[2];
			
		
            log.info("**********************Testing for  Portal  "+url);
			driver.get(url);
			driver.manage().window().maximize();
			

			Alumni.testAppLogin(url, driver, Email);//tc-1
			Alumni.testHome(driver);
			Alumni.TestEvent(driver);
			Alumni.TestJobs(driver);
			Alumni.TestLogout(url, driver);
			((JavascriptExecutor)driver).executeScript("window.open()");
        ArrayList<String> tabs4565 = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs4565.get(1));
		driver.get("https://test.salesforce.com/");
		Alumni.TestVerificationBackend(Email, driver, Email, Email);
			SendMail.sendEmail(logFileName);
    }
}
}