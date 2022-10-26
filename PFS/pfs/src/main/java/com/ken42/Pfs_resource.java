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
	// static String SpreadSheetFile = "C:\\Users\\Public\\Documents\\demo.xlsx";
	// static String PPT_file = "C:\\Users\\Public\\Documents\\demo.pptx";
	// static String Video_file = "C:\\Users\\Public\\Documents\\demo.mp4";
	// static String PDF_file = "C:\\Users\\Public\\Documents\\demo.pdf";
	private static String PFSurl;

	@Test
	public static void resourceFacultyInitialSteps(String faculty, String url, WebDriver driver) throws Exception{
		Utils.login(driver, faculty,url);
		Utils.checkAcadAndClick(driver, url);
		Utils.clickXpath(driver, ActionXpath.faccc, time, "Click on course content");
		
    }
		
		
	

	public static void resourceSubmitForm( String faculty, String url, WebDriver driver) throws Exception{
		     

		Utils.clickXpath(driver, ActionXpath.facssadd, time, "Click of add resource");
		Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "Click on URL resource link");
		// Utils.smallSleepBetweenClicks(1);
		// Utils.clickXpath(driver, ActionXpath.facssadd, time, "facssadd");
		Utils.smallSleepBetweenClicks(1);
		Utils.clickXpath(driver, ActionXpath.facccresdescclick, time, "facccresdescclick");
		Utils.callSendkeys(driver, ActionXpath.facccresurl, "Hello", time);
		// Utils.callSendkeys(driver, "//*[@id='tinymce']//p", "Testing", time);
		Utils.clickXpath(driver, ActionXpath.facccressubmitform, time, "Save URL link button");
	}

	public static void resourcePublishAndLogout(String faculty, String url, 
		WebDriver driver, String fileName, String Role) throws Exception{
		Utils.clickXpath(driver, "//p[. ='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpath(driver, ActionXpath.facsspublish, time, "Click on publish button1");
		Utils.clickXpath(driver, ActionXpath.facsspublishyes, time, "Click on publish button2");
		Utils.logout(driver, url, Role);
	}

	public static void resourceStudentViewAndLogout(String faculty, String url, 	
		WebDriver driver, String fileName, String Role) throws Exception{

			
			Utils.clickXpath(driver, ActionXpath.subject, time, "Click on Subject");

			String returnArray[] = new String[2];
			 String program = returnArray[0];
			driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            
			
	
		Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpath(driver, ActionXpath.viewpdf2, time, "Click on View Spreadsheet");
		Utils.clickXpath(driver, ActionXpath.learn, time, "click learn");
		Utils.logout(driver, url, Role);
	}

	public static void resourceDeleteAndLogout(String faculty, String url, 
		WebDriver driver, String fileName, String Role) throws Exception{
		Utils.clickXpath(driver, "//p[.='"+fileName+"']/../../.././..//*[local-name()='svg']", time, "Select PPT file name");
		Utils.clickXpath(driver, ActionXpath.facpdfdelete, time, "Click on Delete button1");
		Utils.clickXpath(driver, ActionXpath.facpdfdelete2, time, "Click on delete button2");
		Utils.logout(driver, url, Role);
	}


    @Test(priority = 40)
	public static void testSpreadsheetCreateViewDelete(String student, String faculty, String url, 
        String Browser, String Role, WebDriver driver) throws Exception {
		try {
			String SpreadSheetFile = "";
			if (Utils.checkBimtech(url)){
				log.info("TC-40 Spreadsheet is not supported on this portal");
				return;
			}
			if (Utils.checkWindowsOs()){
				SpreadSheetFile = "C:\\Users\\Public\\Documents\\demo.xlsx";
			}
			else {
				SpreadSheetFile = "/Users/Shared/demo.xlsx";
			}
			
			System.out.println("TC-40:  SpreadSheet resource Create View delete Test case Started");
			
			resourceFacultyInitialSteps(faculty, url, driver);
			String subject;
			String[]  ProgSubj = new String [2];
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			Utils.clickXpath(driver, ActionXpath.programselect, time, "click on program select");
			String programconverted = Utils.getTEXT(driver,"(//*[. and @aria-haspopup='listbox'])[1]");
			if(Utils.checkifcourseissubject(url))
			{
				Utils.clickXpath(driver, ActionXpath.course, time, "click on subject");
				subject = Utils.getTEXT(driver, "(//li[@data-value])[1]");
				Utils.clickXpath(driver, ActionXpath.courseselect, time, "click on select subject"); 
				}
			else{
				Utils.clickXpath(driver, ActionXpath.subjectclick, time, "click on subject");
				 subject = Utils.getTEXT(driver, "(//*[@class='MuiTab-wrapper']//p)[1]");
			}
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick");
			resourceSubmitForm(faculty, url, driver);

			String fileName = "Excel_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys(SpreadSheetFile);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "Click on SS SVG");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Student part starts
			Utils.login(driver, student,url);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpath(driver, ActionXpath.program, time, "click on program");
			driver.findElement(By.xpath("//li[@data-value='" + programconverted + "']")).click();
			Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject");
			driver.findElement(By.xpath("//li[@data-value='" + subject + "']")).click();
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.viewss, time, "viewss");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);
               
			//Student part ends

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facssopen, time, "facspreadsheetopen");
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
			String PPT_file = "";
			if (Utils.checkWindowsOs()){
				PPT_file = "C:\\Users\\Public\\Documents\\demo.pptx";
			}else {
				PPT_file = "/Users/Shared/demo.pptx";
			}
			
			System.out.println("TC-41:  PPT resource Create View delete Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
			resourceSubmitForm(faculty, url, driver);
			String fileName = "PPT_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']")).sendKeys(PPT_file);
			Utils.smallSleepBetweenClicks(1);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facpptfopen, time, "facpptfopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);
			

			Utils.login(driver, student,url);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpath(driver, ActionXpath.viewppt, time, "viewppt");
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
			String PDF_file = "";
			if(Utils.checkWindowsOs()){
				PDF_file = "C:\\Users\\Public\\Documents\\demo.pdf";
			}else {
				PDF_file = "/Users/Shared/demo.pdf";
			}
			
			System.out.println("TC-42:  Create PDF resource publish and delete PDF");
			resourceFacultyInitialSteps(faculty, url, driver);			
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
			resourceSubmitForm(faculty, url, driver);
			
			String fileName = "PDF_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.pdf']"))
					.sendKeys(PDF_file);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Now verify in student
			Utils.login(driver, student,url);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpath(driver, ActionXpath.viewpdf, time, "viewpdf");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facpdfopen, time, "facpdfopen");
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
			String Video_file = "";
			if (Utils.checkWindowsOs()){
				Video_file = "C:\\Users\\Public\\Documents\\demo.mp4";
			}else {
				Video_file = "/Users/Shared/demo.mp4";
			}
			
			System.out.println("TC-43:  Create Video resource create view  and delete");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
			resourceSubmitForm(faculty, url, driver);
			String fileName = "Video_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
			driver.findElement(By.xpath("//input[@accept='.mp4']"))
					.sendKeys(Video_file);
			Utils.clickXpath(driver, ActionXpath.facccressubmit, time, "facccressubmit");
			Utils.clickXpath(driver, ActionXpath.facccressubmityes, time, "facccressubmityes");
			Utils.clickXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
			resourcePublishAndLogout(faculty, url, driver, fileName, Role);

			//Student to verify
			Utils.login(driver, student,url);
			Utils.smallSleepBetweenClicks(1);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "Click on learnlearn");
			Utils.clickXpath(driver, ActionXpath.viewvideo, time, "Click on video");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			//Faculty to delete
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facvideoopen, time, "facvideoopen");
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

			Utils.login(driver, student,url);
			Utils.checkAcadAndClick(driver, url);
			Utils.clickXpath(driver, ActionXpath.learn, time, "learn");
			Utils.clickXpath(driver, ActionXpath.viewlink, time, "viewlink");
			resourceStudentViewAndLogout(faculty, url, driver, fileName, Role);

			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.faclinkopen, time, "faclinkopen");
			resourceDeleteAndLogout(faculty, url, driver, fileName, Role);
			log.info("TC-44 Link resource Create View delete Test Case PASSED \n");
		} catch (Exception e) {
			Utils.printException(e);
			log.warning("TC-44: Link resource Create View delete Test Case FAILED \n");
			Pfs_portal.quitDriver(url);
			Pfs_portal.initDriver(Browser, url);
		}
	}
	@Test(priority = 45)
    public static void testSpreadsheetFileType(String student, String faculty, 
            String url, String Browser, String Role, WebDriver driver) throws Exception {
        try {
            System.out.println("TC-45:  Test SpreadSheet resource Create View delete Test case Started");
            if (Utils.checkBimtech(url)){
                log.info("TC-45 Test Spreadsheet is not supported on Bimtech");
                return;
            }
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
            Utils.clickXpath(driver, ActionXpath.facssclick, time, "facssclick");
			resourceSubmitForm(faculty, url, driver);

			String fileName = "Excel_" + Utils.generateRandom();
			Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
             driver.findElement(By.xpath("//input[@accept='.xlsx,.xls']")).sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
             Utils.smallSleepBetweenClicks(1);
         
            WebElement s= driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
            String kenm= s.getText();
            String noSpaceStr = kenm.replaceAll("\\s", "");   
            String name="AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.xlsx,.xlsformat.";

             if(noSpaceStr.equals(name)){
                 System.out.println("It is not the excel file");  
             }
             else{
                 System.out.println("File uploaded");
             } 
             
             driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
             Utils.logout(driver, url, faculty);
             log.info("TC-45: Test Spreadsheet File type Test Case PASSED \n");
             
   }
    catch (Exception e) {
       Utils.printException(e);
       log.warning("TC-45 Test Spreadsheet File type test case FAILED... \n");
       Pfs_portal.quitDriver(url);
   }

   }
   @Test(priority = 46)
   public static void testPPTFileType(String student, String faculty, 
		   String url, String Browser, String Role, WebDriver driver) throws Exception {
		   try {
		   System.out.println("TC-51:  Test PPT File type");
		   resourceFacultyInitialSteps(faculty, url, driver);
		   Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
		   Utils.clickXpath(driver, ActionXpath.facpptclick, time, "facpptclick");
		   Utils.smallSleepBetweenClicks(1);
		   resourceSubmitForm(faculty, url, driver);

		   String fileName = "PPT_" + Utils.generateRandom();
		   Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
		   driver.findElement(By.xpath("//input[@accept='.ppt,.pptx']")) .sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
		   Utils.smallSleepBetweenClicks(1);

		   WebElement s= driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
		   String kenm= s.getText();
		   String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method  
		   String name="AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.ppt,.pptxformat.";
			if(noSpaceStr.equals(name)){
				System.out.println("It is not the PPT file");  
			}
			else{
				System.out.println("File uploaded");
			}  
			driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
			Utils.logout(driver, url, faculty);
		   log.info("TC-46: Test PPT File type Test Case PASSED \n"); 
	   } catch (Exception e) {
		   Utils.printException(e);
		   Pfs_portal.quitDriver(url);
			log.warning("TC-46: Test PPT File type Test Case FAILED \n");
	   }
   }
   @Test(priority = 47)
   public static void testPDFFileType(String student, String faculty, 
		   String url, String Browser, String Role, WebDriver driver) throws Exception {
		 try {
		    System.out.println("TC-47:  Test PDF File type Test Case");
		    resourceFacultyInitialSteps(faculty, url, driver);
		    Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
        	Utils.clickXpath(driver, ActionXpath.facccrespdf, time, "facccrespdf");
            resourceSubmitForm(faculty, url, driver);
			String fileName = "PDF_" + Utils.generateRandom();
            Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
            driver.findElement(By.xpath("//input[@accept='.pdf']")) .sendKeys("C:\\Users\\Public\\Documents\\demo.pptx");

            WebElement s= driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
            String kenm= s.getText();
            String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method  
            String name="AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.pdfformat.";
             if(noSpaceStr.equals(name)){
                 System.out.println("It is not the PDF file");  
             }
             else{
                 System.out.println("File uploaded");
             }  
             driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
             Utils.logout(driver, url, faculty);
            log.info("TC-47: Test PDF File type Test Case PASSED \n");
        } catch (Exception e) {
            Utils.printException(e);
            Pfs_portal.quitDriver(url);
            log.warning("TC-47:Test PDF File type Test Case FAILED \n");
        }
    }
	@Test(priority = 48)
    public static void testVideoFileType(String student, String faculty, 
            String url, String Browser, String Role, WebDriver driver) throws Exception {
         try {
            System.out.println("TC-48: Test Video File type Test Case");
			resourceFacultyInitialSteps(faculty, url, driver);
		    Utils.clickXpath(driver, ActionXpath.facccres, time, "facccres");
			Utils.clickXpath(driver, ActionXpath.facvideoclick, time, "facvideoclick");
            resourceSubmitForm(faculty, url, driver);
			String fileName = "Video_" + Utils.generateRandom();
            Utils.callSendkeys(driver, ActionXpath.facpptname, fileName, time);
            driver.findElement(By.xpath("//input[@accept='.mp4']")).sendKeys("C:\\Users\\Public\\Documents\\demo.pdf");
            Utils.smallSleepBetweenClicks(2);

            
            WebElement s= driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[1]"));
            String kenm= s.getText();
            String noSpaceStr = kenm.replaceAll("\\s", ""); // using built in method  
            String name="AnErrorOccuredUnsupportedFileFormat.Pleaseuploadonly.mp4format.";
             if(noSpaceStr.equals(name)){
                 System.out.println("It is not the Video file");  
             }
             else{
                 System.out.println("File uploaded");
             }  
             driver.findElement(By.xpath("/html/body/div[5]/div[3]/div/div[2]/button/span[1]")).click();
             Utils.logout(driver, url, faculty);
            log.info("TC-48: Test Video File type Test Case PASSED \n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-48:Test Video File type Test Case FAILED \n");
        }
    }
	@Test(priority = 49)
    public static void testFacultyFilterResource(String student, String faculty, 
            String url, String Browser, String Role, WebDriver driver) throws Exception {
        try {
            System.out.println("TC-49:  PPT resource Filter Option View Test case Started");
			resourceFacultyInitialSteps(faculty, url, driver);
			Utils.bigSleepBetweenClicks(1);
			 WebElement l= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
	         	String p = l.getText();
	         	System.out.println("p"+p);
	         	if (p.contains("Pdf") && (p.contains("Presentations"))  && (p.contains("Videos")) && (p.contains("Links"))) {
	         		log.info(" it contain all the resources before filter ");
	         	}else {
					System.out.println(" All resource are not Presnet Quiting the Test. ");
					Pfs_portal.quitDriver(url);
	                log.warning("TC-49 PPt resource Filter Option View Test Case FAILED \n");
				}
			Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button");
            Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter ");
            Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button");
            Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span");
            Utils.clickXpath(driver, ActionXpath.faccPPTCheckBox, time, "select the PPT Check box ");
            Actions qwe = new Actions(driver);
         qwe.moveByOffset(40, 40).click().perform();
            
         WebElement l2= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
      	String p2 = l2.getText();
      	if (p2.contains("Presentations") && (!p2.contains("pdf")) &&  (!p2.contains("Spreadsheet")) && (!p2.contains("Videos")) && (!p2.contains("Links"))) {
      		System.out.println(" TC-49: PPT resource Filter Option Contains PPT test case PASSED \n\n");
      	}else {
				log.warning(" TC-50: PPT resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
            Utils.executeLongWait(url);
            Utils.logout(driver, url, Role);
            Utils.smallSleepBetweenClicks(1);
            log.info("TC-49: PPT resource Filter Option View Test Case PASSED \n");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-49: PPT resource Filter Option View Test Case FAILED \n");
        }
    }
	@Test(priority = 50)
    public static void testFacultyFilterPDFResource(String student, String faculty,String url, String Browser, String Role, WebDriver driver) throws Exception {
            try {
                System.out.println("TC-50:  PDF resource Filter Option View Test case Started");
				resourceFacultyInitialSteps(faculty, url, driver);
				Utils.bigSleepBetweenClicks(1);
				 WebElement l= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
		         	String p = l.getText();
		         	System.out.println("p"+p);
		         	if (p.contains("Pdf") && (p.contains("Presentations"))  && (p.contains("Videos")) && (p.contains("Links"))) {
		         		log.info(" it contain all the resources before filter ");
		         	}else {
						System.out.println(" All resource are not Presnet Quiting the Test. ");
						Pfs_portal.quitDriver(url);
		                log.warning("TC-50: PDF resource Filter Option View Test Case FAILED \n");
					}
				Utils.clickXpath(driver, ActionXpath.faccFilter, time, "click on the filter button");
				
                Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "click the clear all on the filter ");
                Utils.clickXpath(driver, ActionXpath.faccFilter, time, "click on the filter to open to apply the filter ");
                Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "Open the reosurce Filter  span opnen");
                Utils.clickXpath(driver, ActionXpath.faccPDFCheckBox, time, "click the PDF checkbox");
                Actions qwe = new Actions(driver);
             qwe.moveByOffset(40, 40).click().perform();
                
             
             WebElement l2= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
         	String p2 = l2.getText();
         	if (p2.contains("Pdf") && (!p2.contains("Presentations")) &&  (!p2.contains("Spreadsheet")) && (!p2.contains("Videos")) && (!p2.contains("Links"))) {
         		System.out.println(" TC-50: PDF resource Filter Option Contains Pdf test case PASSED \n\n");
         	}else {
				log.warning(" TC-50: PDF resource Filter Option View FAILED it does not contain all the tabs\n\n");
			}
                Utils.executeLongWait(url);
                Utils.logout(driver, url, Role);
                Utils.smallSleepBetweenClicks(1);
                log.info("TC-50: PDF resource Filter Option View Test Case PASSED \n");
            } catch (Exception e) {
                Utils.printException(e);
                Pfs_portal.quitDriver(url);
                log.warning("TC-50: PDF resource Filter Option View Test Case FAILED \n");
        }
    }
	@Test(priority = 51)
    public static void testFacultyFilterVideoResource(String student, String faculty,String url, String Browser, String Role, WebDriver driver) throws Exception {
            try {
                System.out.println("TC-51:  Video resource Filter Option View Test case Started");
                resourceFacultyInitialSteps(faculty, url, driver);
                Utils.bigSleepBetweenClicks(2);
                WebElement l= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
	         	String p = l.getText();
	         	System.out.println("p"+p);
	         	if (p.contains("Pdf") && (p.contains("Presentations"))  && (p.contains("Videos")) && (p.contains("Links"))) {
	         		log.info(" it contain all the resources before filter ");
	         	}else {
					System.out.println(" All resource are not Presnet Quiting the Test. ");
					Pfs_portal.quitDriver(url);
	                log.warning("TC-51 Video resource Filter Option View Test Case FAILED \n");
				}
				Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button");
                Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter");
                Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button");
                Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span");
                Utils.clickXpath(driver, ActionXpath.faccVideoCheckBox, time, "select the Viedo Check box");
                Actions qwe = new Actions(driver);
            	 qwe.moveByOffset(40, 40).click().perform();
                
            	 WebElement l2= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
              	String p2 = l2.getText();
              	if (p2.contains("Videos") && (!p2.contains("Presentations")) &&  (!p2.contains("Spreadsheet")) && (!p2.contains("pdf")) && (!p2.contains("Links"))) {
              		System.out.println(" TC-51: Video resource Filter Option Contains Video test case PASSED \n\n");
              	}else {
     				log.warning(" TC-51: Video resource Filter Option View FAILED it does not contain all the tabs\n\n");
     			}
                Utils.executeLongWait(url);
                Utils.logout(driver, url, Role);
                Utils.smallSleepBetweenClicks(1);
                log.info("TC-51: Video resource Filter Option View Test Case PASSED \n");
            } catch (Exception e) {
                Utils.printException(e);
                Pfs_portal.quitDriver(url);
                log.warning("TC-51: Video resource Filter Option View Test Case FAILED \n");
            }
        }
		
		@Test(priority = 52)
		public static void testFacultyFilterLinksResource(String student, String faculty,String url, String Browser, String Role, WebDriver driver) throws Exception {
				try {
					System.out.println("TC-52:  Links resource Filter Option View Test case Started");
					resourceFacultyInitialSteps(faculty, url, driver);
					Utils.bigSleepBetweenClicks(2);
					 WebElement l= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
			         	String p = l.getText();
			         	System.out.println("p"+p);
			         	if (p.contains("Pdf") && (p.contains("Presentations"))  && (p.contains("Videos")) && (p.contains("Links"))) {
			         		log.info(" it contain all the resources before filter ");
			         	}else {
							System.out.println(" All resource are not Presnet Quiting the Test. ");
							Pfs_portal.quitDriver(url);
			                log.warning("TC-52 Links resource Filter Option View Test Case FAILED \n");
						}
					Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Clik ont he Filter button");
					Utils.clickXpath(driver, ActionXpath.faccFilterClear, time, "Clear all the filter");
					Utils.clickXpath(driver, ActionXpath.faccFilter, time, "Appling the Filter to click the filter button");
					Utils.clickXpath(driver, ActionXpath.faccPPTOPen, time, "CLick on the filter Resource type span");
					JavascriptExecutor js = (JavascriptExecutor) driver; 
					js.executeScript("window.scrollBy(5,5)");
					Utils.clickXpath(driver, ActionXpath.faccLinksCheckBox, time, "Selec the Link Check Box filter");
					Actions qwe = new Actions(driver);
				    qwe.moveByOffset(40, 40).click().perform();
					
				    WebElement l2= driver.findElement(By.xpath("/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[3]/div/div/div/div[5]/div[1]/div/div/../../../.."));
	              	String p2 = l2.getText();
	              	if (p2.contains("Links") && (!p2.contains("Presentations")) &&  (!p2.contains("Spreadsheet")) && (!p2.contains("pdf")) && (!p2.contains("Videos"))) {
	              		System.out.println(" TC-52: Links resource Filter Option Contains Links test case PASSED \n\n");
	              	}else {
	     				log.warning(" TC-52: Links resource Filter Option View FAILED it does not contain all the tabs\n\n");
	     			}
					Utils.executeLongWait(url);
					Utils.logout(driver, url, Role);
					Utils.smallSleepBetweenClicks(1);
					log.info("TC-52: Links resource Filter Option View Test Case PASSED \n");
				} catch (Exception e) {
					Utils.printException(e);
					Pfs_portal.quitDriver(url);
					Pfs_portal.initDriver(Browser, url);
					log.warning("TC-52: Links resource Filter Option View Test Case FAILED \n");
				}
			}

           


           
}
