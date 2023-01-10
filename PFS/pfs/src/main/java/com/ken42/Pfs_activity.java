package com.ken42;

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

    // function
    public static String[] assesmentcreate(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content", log);

            String returnAssement[] = new String[5];
            String returnArray[] = new String[4];
            returnArray = Utils.getClassSubjectAndSection(driver, url, "activity");

            String program1 = returnArray[0];
            String program2 = returnArray[1];
            String subject1 = returnArray[2];
            String subject2 = returnArray[3];

            Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity", log);
            if (Utils.checkLtsta(url)) {
                Utils.clickXpath(driver, ActionXpath.facassessmentrelativeltsta, time, "Click on assessment image",
                        log);
            } else {
                Utils.clickXpath(driver, ActionXpath.facassessmentrelative, time, "Click omn Assesment", log);
            }
            Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity", log);
            Utils.smallSleepBetweenClicks(1);
            String fileName = "Assessment_" + Utils.generateRandom();
            Utils.smallSleepBetweenClicks(2);
            Utils.callSendkeys(driver, ActionXpath.facassesmentrelative, fileName, time, log);
            Utils.smallSleepBetweenClicks(1);
            if (Utils.skipsubject(url)) {
                System.out.println("Subject is not avilable ");
            } else {
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
                Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
                Utils.smallSleepBetweenClicks(2);

                driver.findElement(By.xpath("//li[@data-value='" + program1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
                Utils.smallSleepBetweenClicks(2);
                driver.findElement(By.xpath("//li[@data-value='" + subject1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.smallSleepBetweenClicks(1);
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
            }

            Boolean appPresent = false;
            String dot3 = "//*[@class='tox-tbtn' and @title='More...']";
            appPresent = driver.findElements(By.xpath(dot3)).size() > 0;
            if (appPresent) {
                Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot", log);
                Utils.smallSleepBetweenClicks(1);
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            } else {
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            }
            Utils.callSendkeys(driver, ActionXpath.facurlrelative, fileName, time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facsavlinrelative, time, "facsavlin", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facsaverelative, time, "Save and proceed 1", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.fachourrelative, "1", time, log);
            Utils.clickXpath(driver, ActionXpath.fasaverelative, time, "Save and proceed 2", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.fasokrelative, time, "fasok", log);
            returnAssement[0] = fileName;
            returnAssement[1] = program1;
            returnAssement[2] = program2;
            returnAssement[3] = subject1;
            returnAssement[4] = subject2;

            return (returnAssement);
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesment Fuction FAILED  ");
            throw (e);
        }

    }

    public static void assesmentpublish(String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {
            Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative, time, "Select first question", log);
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facAssPublish, time, "Publish Assessment", log);
            Utils.bigSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Publish Fuction FAILED  ");
            throw (e);
        }
    }

    public static String[] publishassesmentedit(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {
            String filename = returnAssement[0];
            // Utils.checkAcadAndClick(driver, Role, url, log);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content", log);
            Utils.clickXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.bigSleepBetweenClicks(1);
            WebDriverWait wait35 = new WebDriverWait(driver, 20);

            WebElement element238 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element238);
            Utils.smallSleepBetweenClicks(1);
            // edit
            Utils.bigSleepBetweenClicks(1);

            Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ", log);
            if (Utils.checkLtsta(url)) {
                Utils.clickXpath(driver, ActionXpath.selectmcq2, time, "Click on Multiple choice question ", log);
            } else {
                Utils.clickXpath(driver, ActionXpath.selectmcq1, time, "Click on Multiple choice question ", log);
            }

            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative2, time, "Select first question", log);
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select", log);
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(-100,-100)");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Publishassesmentedit Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static String[] unpubishassesmentedit(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {
            String filename = returnAssement[0];
            // Utils.checkAcadAndClick(driver, Role, url, log);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelative, time, "Click on course content", log);
            Utils.clickXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.bigSleepBetweenClicks(1);
            WebDriverWait wait35 = new WebDriverWait(driver, 20);

            WebElement element238 = wait35
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element238);
            Utils.smallSleepBetweenClicks(1);
            // edit
            Utils.bigSleepBetweenClicks(2);

            Utils.clickXpath(driver, ActionXpath.fasquestionrelative, time, "Click on question bank ", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.clickquestiontype, time, "Click on Questiontype ", log);
            Utils.clickXpath(driver, ActionXpath.selectmcq2, time, "Click on Multiple choice question ", log);

            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facselectrelative2, time, "Select first question", log);
            Utils.clickXpath(driver, ActionXpath.facaddselectrelative, time, "Click Add Select", log);
            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(-100,-100)");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.preview, time, "Click on preview", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Unpubishassesmentedit Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static void assesmentviewstudent(String[] returnAssement,
            String Student, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {

            int len = returnAssement.length;
            System.out.println("Length is:" + len);
            for (int i = 0; i < len; i++) {

                System.out.println(returnAssement[i]);
            }

            String program = returnAssement[3];
            String Subject = returnAssement[4];
            String filename = returnAssement[0];

            Utils.smallSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, Student, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time, "flearnltsta", log);
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='" + Subject + "']")).click();
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG",
                    log);
            Utils.clickXpath(driver, "//p[.='" + filename + "']/../../.././..//*[local-name()='svg']", time,
                    "Click on fileName", log);
            Actions qq = new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();
            Utils.smallSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentviewstudent Fuction FAILED  ");
            throw (e);
        }
    }

    public static void assesmentviewstudentwithoutfilename(String[] returnAssement,
            String Student, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            Utils.checkAcadAndClick(driver, Student, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.learn, time, "Click on course content", log);

            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='2022-23-PGDM-Core-Sem-3']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='Managerial Economics - A']")).click();

            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG",
                    log);
            Utils.clickXpath(driver, "//p[.='Sample21']/../../.././..//*[local-name()='svg']", time,
                    "Click on fileName", log);

            Utils.smallSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentviewstudent Fuction FAILED  ");
            throw (e);
        }
    }

    public static String assesmentattempt(String faculty, String student,
            String url, String Browser, String Role, WebDriver driver, String[] returnAssement, Logger log,
            String[] csvCell)
            throws Exception {
        try {
            String program = returnAssement[3];
            String Subject = returnAssement[4];
            String filename = returnAssement[0];
            Utils.smallSleepBetweenClicks(1);
            // Utils.checkAcadAndClick(driver, Role, url, log);
            Utils.smallSleepBetweenClicks(1);
            // Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelearn, time,
            // "flearnltsta");
            // Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time,
            // "Click on Assesment SVG");
            // Utils.bigSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            // Utils.bigSleepBetweenClicks(2);

            WebDriverWait ele11 = new WebDriverWait(driver, 20);
            WebElement elem1 = ele11
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Attempt Now'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
            Utils.bigSleepBetweenClicks(1);

            Set<String> set = driver.getWindowHandles();

            Iterator<String> it = set.iterator();

            String parentwindowid = it.next();
            System.out.println("parent windowid:" + parentwindowid);

            String childwindowid = it.next();
            System.out.println("childwindowid" + childwindowid);

            driver.switchTo().window(childwindowid);

            // System.out.println("child window pop title"+driver.getTitle());

            String ele = driver.getTitle();
            System.out.println(ele);

            Utils.clickXpath(driver, ActionXpath.assesmentinstruction, time, "Click on check box", log);
            Utils.clickXpath(driver, ActionXpath.startassesment, time, "Click on start assesment", log);
            Utils.clickXpath(driver, ActionXpath.attemptquestion, time, "Click on answer", log);
            Utils.clickXpath(driver, ActionXpath.submitattempt, time, "Click on submit", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.submitagainattempt, time, "Click on submit 2", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assesmentok, time, "Click on ok", log);
            Utils.bigSleepBetweenClicks(1);

            Utils.smallSleepBetweenClicks(2);
            driver.switchTo().window(parentwindowid);
            Utils.smallSleepBetweenClicks(2);

            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();

            driver.navigate().refresh();
            Utils.bigSleepBetweenClicks(2);
            Utils.checkAcadAndClick(driver, student, url, log, csvCell);

            // Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic
            // ", log);
            Utils.clickXpath(driver, ActionXpath.ClickLearn, time, "Click learn ", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='" + Subject + "']")).click();
            Utils.bigSleepBetweenClicks(2);
            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG",
                    log);
            Utils.bigSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.bigSleepBetweenClicks(2);
            if (Utils.viewresult(url)) {
                Utils.clickXpathWithJavascript(driver, ActionXpath.clickresult2, time, "result button");
                Utils.bigSleepBetweenClicks(1);
            } else {
                Utils.clickXpathWithJavascript(driver, ActionXpath.clickresult1, time, "result button 1");
                Utils.bigSleepBetweenClicks(1);
            }

            Utils.clickXpath(driver, ActionXpath.viewattempt, time, "Click on view attempt", log);
            Utils.bigSleepBetweenClicks(1);

            String getresult = Utils.getTEXT(driver, ActionXpath.getmark, log, "getmark");

            System.out.println(getresult);
            return (getresult);
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentattempt Fuction FAILED  ");
            throw (e);

        }
    }

    public static String assesmentattemptwithotfilename(String faculty,
            String url, String Browser, String Role, WebDriver driver, String[] returnAssement, Logger log)
            throws Exception {
        try {

            WebDriverWait ele11 = new WebDriverWait(driver, 20);
            WebElement elem1 = ele11
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Attempt Now'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
            Utils.bigSleepBetweenClicks(1);

            Set<String> set = driver.getWindowHandles();

            Iterator<String> it = set.iterator();

            String parentwindowid = it.next();
            System.out.println("parent windowid:" + parentwindowid);

            String childwindowid = it.next();
            System.out.println("childwindowid" + childwindowid);

            driver.switchTo().window(childwindowid);

            // System.out.println("child window pop title"+driver.getTitle());

            String ele = driver.getTitle();
            System.out.println(ele);

            Utils.clickXpath(driver, ActionXpath.assesmentinstruction, time, "Click on check box", log);
            Utils.clickXpath(driver, ActionXpath.startassesment, time, "Click on start assesment", log);
            Utils.clickXpath(driver, ActionXpath.attemptquestion, time, "Click on answer", log);
            Utils.clickXpath(driver, ActionXpath.submitattempt, time, "Click on submit", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.submitagainattempt, time, "Click on submit 2", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assesmentok, time, "Click on ok", log);
            Utils.bigSleepBetweenClicks(1);

            Utils.smallSleepBetweenClicks(2);
            driver.switchTo().window(parentwindowid);
            Utils.smallSleepBetweenClicks(2);

            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();

            driver.navigate().refresh();
            Utils.bigSleepBetweenClicks(2);
            Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic ", log);
            Utils.clickXpath(driver, ActionXpath.ClickLearn, time, "Click learn ", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='2022-23-PGDM-Core-Sem-3']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='Managerial Economics - A']")).click();

            Utils.clickXpath(driver, ActionXpath.Studentassessmenstrelativelexpand, time, "Click on Assesment SVG",
                    log);
            Utils.bigSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='Sample21']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.bigSleepBetweenClicks(2);

            if (Utils.viewresult(url)) {
                WebDriverWait ele12 = new WebDriverWait(driver, 20);
                WebElement elem12 = ele12
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Result'])[2]/../..")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem12);
                Utils.bigSleepBetweenClicks(1);
            } else {
                WebDriverWait ele12 = new WebDriverWait(driver, 20);
                WebElement elem12 = ele12
                        .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Result'])[1]")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem12);
                Utils.bigSleepBetweenClicks(1);
            }

            Utils.clickXpath(driver, ActionXpath.viewattempt, time, "Click on view attempt", log);
            Utils.bigSleepBetweenClicks(1);
            WebElement result = driver.findElement(By.xpath(
                    "/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[3]/div[1]/div/div[1]/div[2]/div/div[2]/p[1]"));
            String getresult = result.getText();
            System.out.println(getresult);
            return (getresult);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentattempt Fuction FAILED  ");
            throw (e);

        }
    }

    public static void assesmentcheckresult(String student,
            String url, String Browser, String Role, WebDriver driver, String[] returnAssement, String getresult,
            Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, student, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.bigSleepBetweenClicks(2);

            WebDriverWait ele13 = new WebDriverWait(driver, 20);
            WebElement elem15 = ele13
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[text()='Review Quiz'])[1]")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem15);
            Utils.bigSleepBetweenClicks(1);
            // Utils.clickXpath(driver, ActionXpath.reviewquiz, time, " Review quiz");
            Utils.bigSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.searchname, "test", time, log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.viewresultinfac, time, " View result", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.viewattempt, time, " View attempt", log);
            Utils.bigSleepBetweenClicks(1);
            WebElement facresult = driver.findElement(By.xpath(
                    "/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[1]/div/div[1]/div[2]/div/div[2]/p[1]"));
            String facgetresult = facresult.getText();
            System.out.println(facgetresult);

            if (getresult.equals(facgetresult)) {
                System.out.println("Result is same");
            } else {
                System.out.println("Result is not same");
            }

        }

        catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentcheckresult Fuction FAILED  ");
            throw (e);
        }
    }

    public static String[] assesmentdelete(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.facclickcouserelativedelete, time, "Click on course content", log);
            Utils.clickXpath(driver, ActionXpath.facultyassessmenstrelativelexpandtodelete, time,
                    "Click on Assessment SVG", log);
            Utils.clickXpath(driver, "//p[.='" + filename + "']/../../.././..//*[local-name()='svg']", time,
                    "Click on fileName", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithJavascript(driver, ActionXpath.fsubltstadeleterelativedelete, time, "Delete button ");
            Utils.clickXpath(driver, ActionXpath.fsubltstadelete1relativedelete2, time, " Delete Assessment 2", log);
            Utils.bigSleepBetweenClicks(2);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assesmentdelete Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static String[] assignmentcreate(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {

            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "Click on course content", log);
            String returnAssement[] = new String[5];
            String returnArray[] = new String[4];
            returnArray = Utils.getClassSubjectAndSection(driver, url, "activity");
            String program1 = returnArray[0];
            String program2 = returnArray[1];
            String subject1 = returnArray[2];
            String subject2 = returnArray[3];

            Utils.smallSleepBetweenClicks(1);
            // if (Utils.skipsubject(url)) {
            // } else {
            // returnArray = Utils.getClassSubjectAndSection(driver, url, "activity");
            // }
            String program = returnArray[0];
            String converted = returnArray[1];

            Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity", log);
            Utils.clickXpath(driver, ActionXpath.assignfacassignmentrelative, time, "Click on Assignment", log);
            Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity", log);
            Utils.smallSleepBetweenClicks(1);

            String fileName = "Assignment_" + Utils.generateRandom();
            Utils.smallSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative, fileName, time, log);
            Utils.smallSleepBetweenClicks(1);
            if (Utils.skipsubject(url)) {
                System.out.println("Subject is not avilable in essci");
            } else {
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
                Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
                Utils.smallSleepBetweenClicks(2);

                driver.findElement(By.xpath("//li[@data-value='" + program1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
                Utils.smallSleepBetweenClicks(2);
                driver.findElement(By.xpath("//li[@data-value='" + subject1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.smallSleepBetweenClicks(1);
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
            }
            Utils.smallSleepBetweenClicks(1);
            System.out.println("program1 is:" + program1);
            // System.out.println("Subject1 is:" + subject1);

            Boolean appPresent = false;
            String dot3 = "//*[@class='tox-tbtn' and @title='More...']";
            appPresent = driver.findElements(By.xpath(dot3)).size() > 0;
            if (appPresent) {
                Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot", log);
                Utils.smallSleepBetweenClicks(1);
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            } else {
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            }

            Utils.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave", log);
            // Utils.smallSleepBetweenClicks(1);
            // Utils.cleartext(driver, ActionXpath.assignfactotalmarksrelative);
            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "100", time, log);
            WebElement el = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            el.clear();
            el.sendKeys("9");

            Utils.smallSleepBetweenClicks(1);
            if (Utils.checkattempt(url)) {
                System.out.println("nsom,essci,ltsta,ltpct and sbmppsjal not have the attempt");
            } else {
                Utils.clickXpath(driver, ActionXpath.assignfacattementsrelative, time, "facattements", log);
                Utils.smallSleepBetweenClicks(1);
                Utils.clickXpath(driver, ActionXpath.assignfacselectattemtrelative, time, "facselectattemt", log);
                Utils.smallSleepBetweenClicks(1);
            }
            Utils.clickXpath(driver, ActionXpath.assignfacsaveandproceedrelative, time, "facsaveandproceed", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacokrelative, time, "facok", log);

            returnAssement[0] = fileName;
            returnAssement[1] = program1;
            returnAssement[2] = program2;
            returnAssement[3] = subject1;
            returnAssement[4] = subject2;

            return (returnAssement);
            // return(returnAssement);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assignmentcreate Fuction FAILED  ");
            throw (e);
        }
        // return(returnAssement);

    }

    public static String[] assignmentpublish(String[] returnAssement, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {

            String returnArray[] = new String[4];
            // returnAssement[0] = filename;
            String filename = returnAssement[0];

            Utils.clickXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "Click on course content", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignexapnd1relative, time, "Exapand Assigment", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacpublish, time, "Publish assignment");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacpublishrelative, time, "Publish");
            Utils.bigSleepBetweenClicks(1);

            return returnAssement;

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assignmentpublish Fuction FAILED  ");
            throw (e);
        }
        // return returnAssement;
    }

    public static void assignmentviewstudent(String[] returnAssement,
            String Student, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, Student, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn", log);
            Utils.bigSleepBetweenClicks(1);
            String program = returnAssement[3];
            String Subject = returnAssement[4];
            String filename = returnAssement[0];
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='" + Subject + "']")).click();
            Utils.clickXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Actions qq = new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assignmentviewstudent Fuction FAILED  ");
            throw (e);

        }
    }

    public static void assignmentsubmission(String[] returnAssement,
            String Student, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            String program = returnAssement[3];
            String Subject = returnAssement[4];
            Utils.checkAcadAndClick(driver, Student, url, log, csvCell);

            String PDF_file = "";
            if (Utils.checkWindowsOs()) {
                PDF_file = "C:\\Users\\Public\\Documents\\demo.pdf";
            } else {
                PDF_file = "/Users/shared/demo.pdf";
            }

            Utils.bigSleepBetweenClicks(1);
            // Utils.checkAcadAndClick(driver, Role, url, log);
            Utils.clickXpath(driver, ActionXpath.assignlearnltstastudentrelative, time, "Select learn", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='" + Subject + "']")).click();
            Utils.clickXpath(driver, ActionXpath.assignexpandltstastudentrelative, time, "expand Assignement", log);
            Utils.smallSleepBetweenClicks(1);
            // Utils.scrollUpOrDown(driver, 500);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Actions ac = new Actions(driver);
            WebElement qqq = driver.findElement(By.xpath("(//span[.='Submission'])[1]"));
            ac.click(qqq).build().perform();

            Utils.smallSleepBetweenClicks(1);
            // Utils.clickXpath(driver, ActionXpath.clickonaddsubmission, time,
            // "clickonaddsubmission");

            JavascriptExecutor j = (JavascriptExecutor) driver;
            j.executeScript("window.scrollBy(0,2000)");

            Utils.smallSleepBetweenClicks(1);
            Utils.bigSleepBetweenClicks(1);
            // Utils.callSendkeys(driver,
            // ActionXpath.clickonbrowser,"C:\\\\Users\\\\Dell\\\\Desktop\\\\Holiday List
            // 2022.pdf", time);
            driver.findElement(By.xpath("//input[@type='file']")).sendKeys(PDF_file);

            // driver.findElement(By.xpath("//input[@type='file']"))
            // .sendKeys("C:\\\\Users\\\\USER\\\\Desktop\\\\pkpadmin,+1008-4741-1-CE
            // (1).pdf");

            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfaclinkrelative, time, "faclink", log);
            Utils.callSendkeys(driver, ActionXpath.assignfacurlrelative, "https://portal-dev.ken42.com/", time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacsavlinrelative, time, "facsavlink", log);

            Utils.smallSleepBetweenClicks(1);
            j.executeScript("window.scrollBy(0,-2000)");
            // driver.findElement(By.xpath("//span[.=' Submit']")).click();
            Utils.clickXpath(driver, ActionXpath.clickonsubmit, time, "clickonsubmit", log);

            String tu = Utils.getTEXT(driver, ActionXpath.alertmsg, log, "alertmsg");
            System.out.println(tu);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assignmentsubmission Fuction FAILED  ");
            throw (e);
        }
    }

    public static void assignmentreview(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, String studentName, Logger log,
            String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            driver.navigate().refresh();
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            // Utils.clickXpath(driver, ActionXpath.ExpandAcademic, time, "Exapand Academic
            // ", log);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse", log);
            Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand", log);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.clickXpathWithJavascript(driver, ActionXpath.reviewassign, time, "Review button");

            // WebDriverWait ele11 = new WebDriverWait(driver, 20);
            // WebElement elem1 = ele11
            // .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Review'])[1]/..")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem1);
            Utils.bigSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.assignsearch, studentName, time, log);
            Utils.clickXpath(driver, ActionXpath.assigngrade, time, "click on grade", log);
            Utils.smallSleepBetweenClicks(3);
            Utils.smallSleepBetweenClicks(2);
            int s = new Utils().getDecimalRandomNumber();

            driver.findElement(By.xpath("//input[@name='marks']")).sendKeys(Integer.toString(s));

            WebDriverWait ele111 = new WebDriverWait(driver, 20);
            WebElement elem11 = ele111
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Back to List']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem11);

            Utils.bigSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assignmentreview Fuction FAILED  ");
            throw (e);
        }
    }

    public static void assigmnenteditview(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            driver.navigate().refresh();
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouse1relative, time, "Click on course content", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignexapnd1relative, time, "Exapand Assigment", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Utils.clickXpathWithJavascript(driver, ActionXpath.assignedit, time, "Edit Assigment");

            // WebDriverWait ele11w = new WebDriverWait(driver, 20);
            // WebElement elem111 = ele11w
            // .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Edit'])[1]")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
            // elem111);

            // Utils.smallSleepBetweenClicks(1);
            Utils.bigSleepBetweenClicks(1);
            // WebElement elee = driver.findElement(By.name("assignmentName"));
            // elee.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            // Utils.bigSleepBetweenClicks(1);
            // String fileName1 = "Assignment_" + Utils.generateRandom();
            // Utils.smallSleepBetweenClicks(1);
            // Utils.callSendkeys(driver, ActionXpath.assignfacassignmentNamerelative,
            // fileName1, time);
            // Utils.smallSleepBetweenClicks(1);

            JavascriptExecutor jwe = (JavascriptExecutor) driver;
            jwe.executeScript("window.scrollBy(0,-200)");

            Utils.clickXpath(driver, ActionXpath.assignfacsaverelative, time, " facsave", log);
            Utils.smallSleepBetweenClicks(1);
            WebElement www = driver.findElement(By.name("totalMarks"));
            www.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            Utils.callSendkeys(driver, ActionXpath.assignfactotalmarksrelative, "200", time, log);
            Utils.smallSleepBetweenClicks(1);
            WebElement dss = driver.findElement(By.name("gradetopass"));
            dss.sendKeys(Keys.CONTROL, "a", Keys.DELETE);
            WebElement elq = driver.findElement(By.xpath("//input[@name='gradetopass']"));
            elq.clear();
            elq.sendKeys("90");

            Utils.smallSleepBetweenClicks(1);
            if (Utils.checkattempt(url)) {
                System.out.println("nsom and essci not have the attempt");
            } else {
                Utils.clickXpath(driver, ActionXpath.assignfacattementsrelative, time, "facattements", log);
                Utils.smallSleepBetweenClicks(1);
                Utils.clickXpath(driver, ActionXpath.assignfacselectattemtrelative, time, "facselectattemt", log);
                Utils.smallSleepBetweenClicks(1);
            }
            Utils.clickXpath(driver, ActionXpath.assignfacsaveandproceedrelative, time, "facsaveandproceed", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacokrelative, time, "facok", log);
            Utils.smallSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assigmnenteditview Fuction FAILED  ");
            throw (e);
        }
    }

    public static String[] assignmentdelete(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse", log);
            Utils.clickXpath(driver, ActionXpath.assignexapndrelative, time, "Exapand", log);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);

            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacdelerelative, time, "Delete");

            // WebDriverWait ele = new WebDriverWait(driver, 20);
            // WebElement elem =
            // ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2", log);
            Utils.bigSleepBetweenClicks(2);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Assigmnentdelete Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static String[] forumcreate(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            // String returnArray[] = new String[2];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse", log);
            Utils.bigSleepBetweenClicks(1);
            String returnAssement[] = new String[5];
            String returnArray[] = new String[4];
            returnArray = Utils.getClassSubjectAndSection(driver, url, "activity");
            String program1 = returnArray[0];
            String program2 = returnArray[1];
            String subject1 = returnArray[2];
            String subject2 = returnArray[3];
            // returnArray = Utils.getClassSubjectAndSection(driver, url,"activity");
            String program = returnArray[0];
            String converted = returnArray[1];

            Utils.clickXpath(driver, ActionXpath.facactivityrelative, time, "facactivity", log);
            if (Utils.checkLtsta(url)) {
                Utils.clickXpath(driver, ActionXpath.relativefacforum1ltsta, time, "Click on Forum", log);
            } else {
                Utils.clickXpath(driver, ActionXpath.relativefacforum1, time, "Click on Forum", log);
            }

            Utils.clickXpath(driver, ActionXpath.facaddactivityrelative, time, "facaddactivity", log);
            Utils.smallSleepBetweenClicks(1);

            String fileName = "Forum_" + Utils.generateRandom();
            Utils.bigSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.relativefacforumname1, fileName, time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            if (Utils.skipsubject(url)) {
                System.out.println("The subject is not avialble in essci samsung");
            } else {
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
                Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
                Utils.smallSleepBetweenClicks(2);

                driver.findElement(By.xpath("//li[@data-value='" + program1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
                Utils.smallSleepBetweenClicks(2);
                driver.findElement(By.xpath("//li[@data-value='" + subject1 + "']")).click();
                Utils.smallSleepBetweenClicks(2);

                Utils.smallSleepBetweenClicks(1);
                System.out.println("program1 is:" + program1);
                System.out.println("Subject1 is:" + subject1);
            }
            // new Forum creation
            Boolean appPresent = false;
            String dot3 = "//*[@class='tox-tbtn' and @title='More...']";
            appPresent = driver.findElements(By.xpath(dot3)).size() > 0;
            if (appPresent) {
                Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot", log);
                Utils.smallSleepBetweenClicks(1);
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            } else {
                Utils.clickXpath(driver, ActionXpath.facclinkrelative, time, "facclink", log);
                Utils.smallSleepBetweenClicks(1);
            }
            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1, fileName, time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok", log);

            returnAssement[0] = fileName;
            returnAssement[1] = program1;
            returnAssement[2] = program2;
            returnAssement[3] = subject1;
            returnAssement[4] = subject2;

            return (returnAssement);
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumcreate Fuction FAILED  ");
            throw (e);

        }

    }

    public static String[] forumpublish(String[] returnAssement, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {
            String returnArray[] = new String[4];
            // returnAssement[0] = filename;
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativeformexpand1, time, "fourme expand click on arrow SVG", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacpublish, time, "Publish");
            System.out.println("click on dot and  publish 1st forum");
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacpublish, time, "Publish");
            System.out.println("click on dot and  publish 2nd forum");
            Utils.bigSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumpublish Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static String[] forumdiscussion(String[] returnAssement, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log)
            throws Exception {
        try {
            String filename = returnAssement[0];
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);

            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacDiscussions, time, "Discussions");
            // WebDriverWait wait35 = new WebDriverWait(driver, 20);
            // WebElement element239 = wait35
            // .until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Discussions'])[1]")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
            // element239);
            // Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativeFacformeCreateNewDiscussion, time,
                    "click on the New Discussion Button", log);
            Utils.smallSleepBetweenClicks(1);
            String fileName2 = "Discussion_Edutech" + Utils.generateRandom();
            Utils.callSendkeys(driver, ActionXpath.faccDiscuionText, fileName2, time, log);
            Utils.clickXpath(driver, ActionXpath.faccDiscussionMessgae3dot, time, "click on 3 dot ", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink", log);
            Utils.bigSleepBetweenClicks(2);

            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1, "https://unsplash.com/", time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin", log);
            Utils.clickXpath(driver, ActionXpath.faccSavefinish, time, "Click on save & finished", log);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumdiscussion Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    public static void forumviewstudent(String[] returnAssement,
            String Student, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            Utils.smallSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, Student, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativeforumlearnltsta1, time, "Select learn", log);
            String program = returnAssement[3];
            String Subject = returnAssement[4];
            String filename = returnAssement[0];
            Utils.clickXpath(driver, ActionXpath.program, time, "click on program", log);
            driver.findElement(By.xpath("//li[@data-value='" + program + "']")).click();
            Utils.clickXpath(driver, ActionXpath.subject, time, "click on subject", log);
            Utils.smallSleepBetweenClicks(2);
            driver.findElement(By.xpath("//li[text()='" + Subject + "']")).click();
            Utils.clickXpath(driver, ActionXpath.relativeforumaexpandltsta1, time, "expand forum", log);
            Utils.smallSleepBetweenClicks(1);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Actions qq = new Actions(driver);
            qq.moveByOffset(40, 40).click().perform();

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumviewstudent Fuction FAILED  ");
            throw (e);
        }
    }

    public static String[] forumedit(String[] returnAssement, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativeformexpand1, time, "fourme expand click on arrow SVG", log);
            Utils.smallSleepBetweenClicks(1);

            String filename = returnAssement[0];
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../..//*[local-name()='svg']")))
                    .click();
            Utils.clickXpathWithJavascript(driver, ActionXpath.assignedit, time, "Edit");

            // WebDriverWait wait35 = new WebDriverWait(driver, 20);
            // WebElement element239 = wait35
            // .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[.='Edit']")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
            // element239);
            Utils.bigSleepBetweenClicks(1);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(-100,-100)");
            // Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time,
            // "facinstruction3dot", log);
            Utils.smallSleepBetweenClicks(1);

            boolean facinstruction3dot = false;
            facinstruction3dot = driver.findElements(By.xpath("//*[@title='More...']")).size() > 0;
            if (facinstruction3dot) {
                Utils.clickXpath(driver, ActionXpath.facinstruction3dot, time, "facinstruction3dot", log);
            } else {

            }
            Utils.clickXpath(driver, ActionXpath.relativefacforumclink1, time, "facforumclink", log);
            Utils.bigSleepBetweenClicks(1);
            Utils.callSendkeys(driver, ActionXpath.relativefacforumurl1, filename, time, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsavlin1, time, "facforumsavlin", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefacforumsave1, time, " facforumsave", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmeditattachements, time, " facfourmeditattachements", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.facfourmattachementset2, time, " facfourmattachementset2", log);
            Utils.smallSleepBetweenClicks(1);
            JavascriptExecutor js1 = (JavascriptExecutor) driver;
            js1.executeScript("window.scrollBy(-100,-100)");
            Utils.clickXpath(driver, ActionXpath.relativefaforumsave1, time, "faforumsave", log);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.relativefaforumok1, time, "faforumok", log);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumedit Fuction FAILED  ");
            throw (e);

        }
        return returnAssement;
    }

    public static String[] forumdelete(String[] returnAssement,
            String faculty, String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String filename = returnAssement[0];
            Utils.bigSleepBetweenClicks(1);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacclickcouserelative, time, "facclickcouse", log);
            Utils.clickXpath(driver, ActionXpath.relativeforumdfexpandltsta12, time, "Exapand", log);
            new WebDriverWait(driver, 25).until(ExpectedConditions
                    .elementToBeClickable(By.xpath("//p[.='" + filename + "']/../../.././..//*[local-name()='svg']")))
                    .click();
            Utils.smallSleepBetweenClicks(1);
            Utils.smallSleepBetweenClicks(1);

            Utils.clickXpathWithJavascript(driver, ActionXpath.assignfacdelerelative, time, "Delete");

            // WebDriverWait ele = new WebDriverWait(driver, 20);
            // WebElement elem =
            // ele.until(ExpectedConditions.elementToBeClickable(By.xpath("(//span[.='Delete'])[1]")));
            // ((JavascriptExecutor) driver).executeScript("arguments[0].click();", elem);
            Utils.smallSleepBetweenClicks(1);
            Utils.clickXpath(driver, ActionXpath.assignfacdelerelative, time, "Delete button 2", log);
            Utils.bigSleepBetweenClicks(1);

        } catch (Exception e) {
            Utils.printException(e);
            log.warning("\t\tCreate Forumdelete Fuction FAILED  ");
            throw (e);
        }
        return returnAssement;
    }

    @Test(priority = 53)
    public static void testFilterActivityAssignment(String student, String faculty, String url, String Browser,
            String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            System.out.println("TC-53: Assignment Filter Test excutaion   started ");
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse", log);
            Utils.bigSleepBetweenClicks(1);
            String p = Utils.getTEXT(driver, ActionXpath.filterassignment, log, "filterassigment");
            System.out.println("p" + p);
            if (p.contains("Assignments") && (p.contains("Assessments")) && (p.contains("Forum"))) {
            } else {
                System.out.println(" All Activity are not Presnet Quiting the Test. ");
                log.warning("TC-53 Assignment Activity Filter Option View Test Case FAILED  ");
                Utils.logout(driver, url, Role, log);
            }
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Click the filter button on activity section", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, "Clear all the fileter ", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Again clcik the fileter button to apply the filter", log);
            Utils.clickXpath(driver, ActionXpath.FaccFilterOpen, time, "Click the Activuty type span to open ", log);
            Utils.clickXpath(driver, ActionXpath.faccAssignmentCheckBox, time, "Select the Assignments Check box ",
                    log);
            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();
            String p2 = Utils.getTEXT(driver, ActionXpath.filterassignment, log, "filter assignment");

            if (p2.contains("Assignments") && (!p2.contains("Assessments")) && (!p2.contains("Forum"))) {
                System.out.println(
                        " TC-53: Assignments Activity Filter Option Contains Assignments View test case PASSED   ");
            } else {
                log.warning(
                        " TC-53: Assignments Activity Filter Option View FAILED it does not contain all the tabs  ");
            }
            Utils.bigSleepBetweenClicks(1);
            Utils.logout(driver, url, Role, log);
            Utils.smallSleepBetweenClicks(1);
            log.info("TC-53 Assignment Filter test case PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-53 Assignment Filter Test Case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 54)
    public static void testFilterActivityAssement(String student, String faculty, String url, String Browser,
            String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            System.out.println("TC-54: Assement Filter Test excutaion   started ");
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse", log);
            Utils.bigSleepBetweenClicks(1);
            String p = Utils.getTEXT(driver, ActionXpath.filterassessment, log, "filter assesment");

            System.out.println("p" + p);
            if (p.contains("Assignments") && (p.contains("Assessments")) && (p.contains("Forum"))) {
            } else {
                System.out.println(" All Activity are not Presnet Quiting the Test. ");
                log.warning("TC-54 Assement Activity Filter Option View Test Case FAILED");
                Utils.logout(driver, url, Role, log);
            }
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Click the filter button on activity section", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, "Clear all the fileter", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Again clcik the fileter button to apply the filter", log);
            Utils.clickXpath(driver, ActionXpath.FaccFilterOpen, time, "Click the Activuty type span to open", log);
            Utils.clickXpath(driver, ActionXpath.faccAssementCheckBox, time, "Select the Assessments Check box", log);
            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();
            String p2 = Utils.getTEXT(driver, ActionXpath.filterforum, log, "filter assessment");

            if (p2.contains("Assessments") && (!p2.contains("Assignments")) && (!p2.contains("Forum"))) {
                System.out.println(
                        " TC-54: Assement Activity Filter Option Option Contains Assessments test case PASSED   ");
            } else {
                log.warning(" TC-54: Assement Activity Filter Option View FAILED it does not contain all the tabs  ");
            }
            Utils.bigSleepBetweenClicks(1);
            Utils.logout(driver, url, Role, log);
            Utils.smallSleepBetweenClicks(1);
            log.info("TC-54 Assement Filter Test Case PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-54 Assement Filter Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 55)
    public static void testFilterActivityForum(String student, String faculty, String url, String Browser, String Role,
            WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            System.out.println("TC-55: Forum Filter Test excutaion   started ");
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.checkAcadAndClick(driver, faculty, url, log, csvCell);
            Utils.clickXpath(driver, ActionXpath.relativefacforumclickcouse1, time, "facforumclickcouse", log);
            Utils.bigSleepBetweenClicks(1);
            String p = Utils.getTEXT(driver, ActionXpath.filterforum, log, "filter foroum");
            System.out.println("p" + p);
            if (p.contains("Assignments") && (p.contains("Assessments")) && (p.contains("Forum"))) {
            } else {
                System.out.println(" All Activity are not Presnet Quiting the Test. ");
                Utils.logout(driver, url, Role, log);
                log.warning("TC-55 Forum Activity Filter Option View Test Case FAILED ");
            }
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Click the filter button on activity section", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignmnetClear, time, "Clear all the fileter", log);
            Utils.clickXpath(driver, ActionXpath.faccFilterassignment, time,
                    "Again clcik the fileter button to apply the filter", log);
            Utils.clickXpath(driver, ActionXpath.FaccFilterOpen, time, "Click the Activuty type span to open", log);
            Utils.clickXpath(driver, ActionXpath.faccForumCheckBox, time, "Select the Assessments Check box", log);
            Actions qwe = new Actions(driver);
            qwe.moveByOffset(40, 40).click().perform();

            String p2 = Utils.getTEXT(driver, ActionXpath.filterassessment, log, "filter forum");

            if (p2.contains("Forum") && (!p2.contains("Assignments")) && (!p2.contains("Assessments"))) {
                System.out.println(" TC-55: Forum Activity Filter Option Contains Forum filter  test case PASSED   ");
            } else {
                log.warning(" TC-55: Forum Activity Filter Option View FAILED it does not contain all the tabs  ");
            }
            Utils.bigSleepBetweenClicks(1);
            Utils.logout(driver, url, Role, log);
            Utils.smallSleepBetweenClicks(1);
            log.info("TC-55 Forum Filter test case PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-55 Forum Filter Test Case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 56)
    public static void testForumCreatePublishViewDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            System.out.println("Faculty Fourm create,publish Delete test case Staerted  ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = forumpublish(returnAssement, faculty, url, Browser, Role, driver, log);
            // forumpublish(faculty, url, Browser, Role, driver,Filenameassesment);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            forumviewstudent(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            // forumdelete(faculty, url, Browser, Role, driver,Filenameassesment);
            Utils.logout(driver, url, Role, log);
            log.info("TC-56 Faculty Fourm create,publish,delete test case PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-56 Faculty Fourm create,publish,delete Test Case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 57)
    public static void testForumCreatePublishViewDeleteDiscussion(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            System.out.println("TC-57 Faculty Fourm create,publish Delete,Discussion test case Staerted  ");
            Utils.login(driver, faculty, url, log, csvCell);

            returnAssement = forumcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = forumpublish(returnAssement, faculty, url, Browser, Role, driver, log);

            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            forumviewstudent(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            returnAssement = forumdiscussion(returnAssement, faculty, url, Browser, Role, driver, log);

            Utils.logout(driver, url, Role, log);

            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-57 Faculty Fourm Create,Publish,View,Dicussion,Delete test case PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-57 Faculty Fourm Create,Publish,View,Dicussion,Delete Test Case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 58)
    public static void testForumCreatePublishEditDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-58: Faculty Fourm create,publish,edit, Delete, test case Staerted  ");
            Utils.login(driver, faculty, url, log, csvCell);
            // String Filenameassesment= forumcreate(student, faculty, url, Browser, Role,
            // driver);
            returnAssement = forumcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = forumpublish(returnAssement, faculty, url, Browser, Role, driver, log);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            // forumpublish(faculty, url, Browser, Role, driver,Filenameassesment);
            returnAssement = forumedit(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            // forumedit(faculty, url, Browser, Role, driver, Filenameassesment);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-58 Forum Create,Publish,Edit,Delete test case PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-58 Faculty Fourm Create,Publish,Edit,Delete Test Case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 59)
    public static void testForumCreateunPublishEditDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            Utils.login(driver, faculty, url, log, csvCell);
            // String Filenameassesment= forumcreate(student, faculty, url, Browser, Role,
            // driver);
            returnAssement = forumcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumedit(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            // forumedit(faculty, url, Browser, Role, driver,Filenameassesment);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = forumdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-59 Forum Create,Unpublish,Edit,Delete test case PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-59 Faculty Fourm Create,Unpublish,Edit,Delete test case FAILED   ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 60)
    public static void testAssessmentCreatePublishViewDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-60: Assement create ,pubish & delete Test excutaion started ");
            Utils.login(driver, faculty, url, log, csvCell);
            Thread.sleep(6000);
            returnAssement = assesmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            String Filenameassesment = returnAssement[0];
            assesmentpublish(faculty, url, Browser, Role, driver, log);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            assesmentviewstudent(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-60 Assement Create,Publish,View,Delete test Executation PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-60 Assement Create,Publish,View,Delete executation Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 61)
    public static void testassesmentAttemptview(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            System.out.println("TC-61: Assement create ,pubish & delete Test excutaion   started  ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            assesmentpublish(faculty, url, Browser, Role, driver, log);

            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            assesmentviewstudent(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            assesmentattempt(student, faculty, url, Browser, Filenameassesment, driver, returnAssement, log, csvCell);

            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            // assesmentcheckresult(faculty, url, Browser, Role, driver, returnAssement,
            // Filenameassesment);
            // Utils.logout(driver, url, Role);
            // Utils.login(driver, faculty,url);
            returnAssement = assesmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-61 Assement Create,Publish,View,Attempt and view result,Delete test Executation   PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning(
                    "TC-61 Assement Create,Publish,View,Attempt and view result,Delete executation Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 62)
    public static void testassesmenteditdelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            System.out.println("TC-62: Assement create ,pubish & delete Test excutaion   started ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = unpubishassesmentedit(returnAssement, faculty, url, Browser, Role, driver, log);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-62 Assement Create,Unpublish,Edit,Delete test Executation PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-62 Assement Create,Unpublish,Edit,Delete executation Test Case FAILED ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 63)
    public static void testassesmentpublisheditview(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-63: Assement create ,pubish & delete Test excutaion   started ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            assesmentpublish(faculty, url, Browser, Role, driver, log);
            returnAssement = publishassesmentedit(returnAssement, faculty, url, Browser, Role, driver, log);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assesmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-63 Assement Create,Publish,Edit,Delete test Executation PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-63 Assement Create,Publish,Edit,Delete executation Test Case FAILED ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 64)
    public static void testFAssignmentCreatePublishViewDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell)
            throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-64: Assignment create ,pubish & delete Test excutaion started ");
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            returnAssement = assignmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = assignmentpublish(returnAssement, faculty, url, Browser, Role, driver, log);
            Utils.smallSleepBetweenClicks(1);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            assignmentviewstudent(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            returnAssement = assignmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-64 Assignment Create,Publish,View,Delete PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-64 Assignment Create,Publish,View,Delete Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 65)
    public static void testFAssignmentCreatePublishsubmissionfileuploadchecking(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = assignmentpublish(returnAssement, faculty, url, Browser, Role, driver, log);

            Utils.logout(driver, url, Role, log);
            Utils.login(driver, student, url, log, csvCell);
            assignmentsubmission(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info(
                    "TC-65 Assignment Create,Publish,View,Fileuploadchecking,Submission,Delete PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning(
                    "TC-65 Assignment Create,Publish,View,Fileuploadchecking,Submission,Delete Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 66)
    public static void testFAssignmentCreatePublishsubmissiongradecheck(String student, String faculty, String url,
            String Browser, String Role, WebDriver driver, String studentName, Logger log, String[] csvCell)
            throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println(
                    "TC-66 Assignment   Create ,publish,gradecheck &submission Test Excecuation Started  ");
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            returnAssement = assignmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = assignmentpublish(returnAssement, faculty, url, Browser, Role, driver, log);
            Utils.logout(driver, url, Role, log);

            Utils.login(driver, student, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            assignmentsubmission(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            assignmentreview(returnAssement, faculty, url, Browser, Role, driver, studentName, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            Utils.smallSleepBetweenClicks(1);
            returnAssement = assignmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-66 Assignment Create,Publish,Submission,Review,Delete PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-66 Assignment  Create,Publish,Submission,Review,Delete Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }

    }

    @Test(priority = 67)
    public static void testFAssignmentCreateEditDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-67 Assignment   Create ,edit and delete Test Excecuation Started  ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            assigmnenteditview(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-67 Assignment Create,Unpublish,Edit,Delete check PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-67 Assignment Create,Unpublish,Edit,Delete check Test Case FAILED ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 68)
    public static void testFAssignmentCreatepublishEditDelete(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            System.out.println("TC-68 Assignment   Create ,publish and delete Test Excecuation Started  ");
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentcreate(student, faculty, url, Browser, Role, driver, log, csvCell);
            returnAssement = assignmentpublish(returnAssement, faculty, url, Browser, Role, driver, log);
            assigmnenteditview(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            Utils.login(driver, faculty, url, log, csvCell);
            returnAssement = assignmentdelete(returnAssement, faculty, url, Browser, Role, driver, log, csvCell);
            Utils.logout(driver, url, Role, log);
            log.info("TC-68 Assignment Create,Publish,Edit,Delete PASSED ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning("TC-68 Assignment Create,Publish,Edit,Delete check Test Case FAILED ");
            Utils.logout(driver, url, Role, log);
        }
    }

    @Test(priority = 69)
    public static void testAttemptview(String student, String faculty,
            String url, String Browser, String Role, WebDriver driver, Logger log, String[] csvCell) throws Exception {
        try {
            String returnAssement[] = new String[3];
            String Filenameassesment = returnAssement[0];
            System.out.println("TC-69: Student view and attempt started  ");

            Utils.login(driver, student, url, log, csvCell);
            assesmentviewstudentwithoutfilename(returnAssement, student, url, Browser, Role, driver, log, csvCell);
            assesmentattemptwithotfilename(faculty, url, Browser, Role, driver, returnAssement, log);
            Utils.logout(driver, url, Role, log);

            log.info("TC-69 Assement Student view and attempt test Executation PASSED  ");
        } catch (Exception e) {
            Utils.printException(e);
            log.warning(
                    "TC-69 Assement Student view and attempt executation Test Case FAILED  ");
            Utils.logout(driver, url, Role, log);
        }
    }

}