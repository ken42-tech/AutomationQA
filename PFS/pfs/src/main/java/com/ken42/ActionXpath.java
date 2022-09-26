package com.ken42;

public class ActionXpath {
//Login related Xpaths
public static final String OTP = null;
static String email = "//input[@placeholder='Email Address']";
static String SignIn = "/html/body/div/div/div/div/main/div[2]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div/button";
static String mobile = "/html/body/div/div/div/div/main/div[2]/div[2]/div/div[2]/div[2]/div/div[1]/div[2]/div/div[2]/div";
static String mobile2= "/html/body/div[2]/div[3]/ul/li";
static String SignIn2 = "/html/body/div/div/div/div/main/div[2]/div[2]/div/div[2]/div[2]/div/div[2]/div[1]/div/button/span[1]";
static String OtpInput ="//input[@placeholder='Enter Otp']";
static String submit ="/html/body/div/div/div/div/main/div[2]/div[2]/div/div[2]/div[2]/div/div[2]/div[2]/button";  
//Login Xpaths ends here
   

//Student Test case 1
static String Home = "(//span[. and text()='Home'])[2]/..";
// static String Home = "(//span[@class='MuiTypography-root jss62 MuiTypography-body1 MuiTypography-displayBlock' //*text()='Home'])[2]";
// static String Home = "//*[@id=\"app\"]/div/div/div/main/div[2]/div[1]/div/div[1]/div/div[2]/div/div[1]/a/div/div[2]/span | (//span[. and text()='Home'])[2]/..";
// static String Home = "(//span[@class='MuiTypography-root jss124 MuiTypography-body1 MuiTypography-displayBlock' and text()='Home'])[1]/..";
static String Stu_prName = "(//span[@class='MuiIconButton-label'])[3]";
//Student test case 1 ends here


// Student Test case 2 Enrollments
static String ClickEnroll="(//span[text()='Enrollments'])[2]/..";
static String clickCompletedEnroll="//span[text()='Completed Enrollments']";
static String ClickOpenEnroll = "//span[text()='Open Enrollments']";
//Student End of Test case 2


//Student Test case 3 Academics
static String ExpandAcademic="(//span[. and text()='Academics'])[2]/..";
static String ltstaAcademic="(//span[. and text()='Academics'])[1]";
static String ClickDashboard="(//span[. and text()='Dashboard'])[1]";
static String ClickLearn="(//span[. and text()='Learn'])[1]";
static String CloseAcademicExapand="(//span[. and text()='Academics'])[2]";
//Student Portal Academics Xpath ends here


//Student test case 4 Examinations
static String ClickExam="(//span[. and text()='Examinations'])[2]";
static String ltstaExam="(//span[. and text()='Examinations'])[1]";
static String examAnnouncements ="//*[@id=\"panel1a-header\"]/div[1]/h6";
//Student Portal Examination Xpath ends here


//Student Test case 5 Attendance 
static String ClickAttendance ="(//span[text()='Attendance'])[2]";
static String ltstaAttendance ="(//span[text()='Attendance'])[1]";
static String clickattendanceHistory="//span[text()='Attendance History']";
 //Student Test case 5  Attendance Xpath ends here


//Student Test case 6 TimeTable 
static String ClickTimetable="(//span[text()='Timetable'])[2]";
static String ltstaTimetable="(//span[text()='Timetable'])[1]";
static String TimeTableMonth="(//button[text()='month'])[1]";
static String TimeTableWeek = "(//button[text()='week'])[1]";
static String TimeTableDay="(//button[text()='day'])[1]";
//Student Portal Timetable Xpath ends here


//Student test case 7 Fees
static String ExpandFees="(//span[text()=' Fees'])[2]";
static String ltstaFees="(//span[text()=' Fees'])[1]";
static String clickMyCart="//span[. and text()='My Cart']";
static String clickFeeSchedule="//span[. and text()='Fee Schedule']";
static String clickFeePayment="//span[. and text()='Fee Payment']";
static String clickManualpayment="//span[. and text()='Manual Payment']";
static String ClickMyTranscetion="//span[. and text()='My Transactions']";
static String closeExpandFees="(//span[text()=' Fees'])[2]";
//Student Test case 7 Portal Fees Xpath ends here


//Student Test case 8 Feedback
static String feedBack="(//span[. and text()='Feedback'])[2]";
static String ltstafeedBack="(//span[. and text()='Feedback'])[1]";
static String clickPrograme="//span[. and text()='Program Feedback']";
//Student Portal Feedback Xpath ends here


//Student Test case 9 Student service cancel button
// static String StudentService="(//span[. and text()='Student Services'])[2]";
static String StudentService="(//span[. and text()='Student Services'])[2] | (//span[. and text()='Support Services'])[2] | (//span[. and text()='Trainer Services'])[2]";
static String ltstaService="(//span[. and text()='Student Services'])[2] | (//span[. and text()='Support Services'])[2] | (//span[. and text()='Trainer Services'])[1]";
static String Raisecase="(//p [. and text()='Raise A Case'])[1]";
static String MakeRaise="(//p [. and text()='Make A Request'])[1]";
static String buttonRaisecase="//button[@label='Raise Case']";
static String cancel="//button[@label='Cancel']";
//Student Test case 9 ends here


//Student Service Test case 10 Raise a case
//// static String StudentService="(//span[. and text()='Student Services'])[2]";
//// static String Raisecase="(//p [. and text()='Raise A Case'])[1]";
//// static String buttonRaisecase="//button[@label='Raise Case']";
static String inputraise="//input[@name='Subject']";
static String description="//textarea[@placeholder='Type your description here...']";
static String submitofcase ="//button[@label='Submit']";
//Student Service Test case 10 ends here


//Statudent Service Tets case 11 Make a request
//// static String StudentService="(//span[. and text()='Student Services'])[2]";
//// static String MakeRaise="(//p [. and text()='Make A Request'])[1]";
static String makeRequest ="//button[@label=' Make a Request']";
static String enterSubject="//input[@name='Subject']";
static String desc = "//textarea[@placeholder='Type your description here...']";
static String makeSubmit ="//button[@label='Submit']";
//Student Service Test case 11 ends here

//Student Test case 12 Event
static String Event="(//span[. and text()='Events'])[2]";
static String ltstaEvent="(//span[. and text()='Events'])[1]";
static String clcikEvent="//*[@id=\"app\"]/div/div/div/main/div[2]/div[2]/div[2]/div[4]/div/div[1]/div";
static String back="//span[text()='Back']";
//Student Test case Feedback ends here
  
   
///Student Test case 13 edit Profile Xpath
//// static String Stu_prName = "(//span[@class='MuiIconButton-label'])[3]";
static String stuprofile="//a[. and text()='Profile']";
static String stubasicedit="(//*[name()='svg'and@height='23'])[1]";
static String Stubasicgender="//*[@id=\"mui-component-select-Gender\"]";
static String stubasicgenderselect="//li[@data-value='Male']";
static String stubasicdob="//*[@name='BirthDate']";
static String stubasicnation="//*[@name='CountryOfResidence']";
static String stubasicsave="//*[@id=\"app\"]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div/div/div[1]/div/div[2]/div/div[3]/div/div[9]/div[1]/button";
//Student test case 13 edit Profile Xpath ends here
   

//Student Test case 14 student profile edit education details
//12
static String stueddrop="//*[@id=\"panel1a-header\"]/div[2]";
static String stued="(//*[name()='svg'and@height='23'])[1]";
static String stued12school="(//*[@name='hed__Educational_Institution_Name__c'])[1]";
static String stued12country="(//*[@name='Country__c'])[1]";
static String stued12year="(//*[@name='Year_of_Passing__c'])[1]";
//clg
static String stuedclg="(//*[@name='hed__Educational_Institution_Name__c'])[2]";
static String stuedclgcountry="(//*[@name='Country__c'])[2]";
static String stuedclgyear="(//*[@name='Year_of_Passing__c'])[2]";
//pg
static String stuedpgclg="(//*[@name='hed__Educational_Institution_Name__c'])[3]";
static String stuedpgcountry="(//*[@name='Country__c'])[3]";
static String stuedpgyear="(//*[@name='Year_of_Passing__c'])[3]";
//save
static String stuedsave="(//*[@id=\"panel1a-content\"]/div/div/div[2]/button/span[1])[1]";
static String stueddropup="//*[@id=\"panel1a-header\"]/div[2]";
//Student Test case 14 ends here
   

//Student Test case 15 Student edit address 
static String stuadddrop="//p[. and text()='ADDRESS DETAILS']";
// static String stuadddrop="//*[@id=\"panel1a-header\"]/div[2]/span[1]/svg/path";
   // static String stuedit="(//*[name()='svg'and@height='23'])[2]";
   static String stuedit="(//p[. and text()='ADDRESS DETAILS']/../../..//*[local-name()='svg'])[2]";
   // static String stuaddadd="//*[@id=\"panel1a-content\"]/div/div/div[4]/button";
   static String stuaddadd="//span[. and text()='Add Address']/..";
   static String stuhouse="//*[@name='House_Flat_No__c']";
   static String sturoad="//*[@name='hed__MailingStreet__c']";
   static String stusuburb="//*[@name='hed__MailingCity__c']";
   static String stucountry="//*[@name='hed__MailingCountry__c']";
   static String stupincode="//*[@name='hed__MailingPostalCode__c']";
   static String stusave="//p[. and text()='ADDRESS DETAILS']/../../..//span[. and text()='Save']";
   static String StudBEdit="(//p[. and text()='Home'])[1]";
//Student Test case 15 Student edit address ends here

//Student Test case 16 Signout
static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
static String signOut="//*[.='Sign Out']";
//Student Test case 16 Signout ends here

//Student Removed Xpaths
// static String StudBEdit="(//*[@fill='currentColor'])[8]";
static String exapnd = "/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[5]/div/div[1]/div[2]/span[1]";
static String forumdacclickcouse = "//span[.='Course Content']"; 
static String dexapnd = "/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div[2]/div/div/div/div[7]/div/div[1]/div[2]/span[1]";		static String dfacdot = "//p[.='chaisacq']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String dfacdelete = "/html/body/div[13]/div[3]/div/nav/div[2]/div[2]";
static String dfacdele = "/html/body/div[4]/div[3]/div/div[3]/button[2]";	
static String dfacclickonp = "//div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div";
static String selectSubject="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[1]/div[1]/div/div/div/div/table/thead/tr/th[1]";
static String SelectOpenSubject="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[3]/div/div/div[2]/div/div/div/div/div/div/div/div/table/thead/tr/th[2]";
static String ClickView="//button[@label='View']";
static String ClickOk="/html/body/div[4]/div[3]/div/div[3]/button";
static String CloseExapnd="/html/body/div[1]/div/div/div/main/div[2]/div[1]/div[2]/div[1]/div/div[2]/div/div[2]/div[1]/div[2]/span";
static String profile="/html/body/div[2]/div[3]/ul/li[1]";
static String OpenExpanEducation="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/div/div[2]/div/p/div/div[2]/div[1]/div[2]";
static String CloseExpanEducation="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/div/div[2]/div/p/div/div[2]/div[1]/div[2]";
static String OpenExpanAddress="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/div/div[2]/div/p/div/div[3]/div[1]/div[2]";
static String CloseExpanAddress="/html/body/div[1]/div/div/div/main/div[2]/div[2]/div[2]/div[2]/div/div/div[2]/div/div/div/div[2]/div/p/div/div[3]/div[1]/div[2]";
static String Drawer ="/html/body/div[1]/div/div/div/main/div[2]/div[1]/div[2]/div[1]/div/div[1]/button/span[1]\r\n";
	
//All Student test cases Xpath should be before this line
//**************************************************************************************************** */
//************************************************************************************* */


//Faculty Role Xpaths begin here*********************************************************
// TC:17 faculty academics
static String openFacdevnosbm = "(//span[text()='Academics'])[2]";
static String clickFacDashdevnosbm = "//span[text()='Dashboard']";
static String facdbfilterselect = "//*[@id=\"menu-\"]/div[3]/ul/li[4]";
static String facFilter = "//div[@class='MuiSelect-root MuiSelect-select MuiSelect-selectMenu MuiInputBase-input MuiInput-input MuiInputBase-inputMarginDense MuiInput-inputMarginDense']";
static String facdbresfilter = "(//*[@xmlns='http://www.w3.org/2000/svg'])[2]";
static String facdbrestypes = "//*[@id=\"alert-dialog-description\"]/div/div[2]/div[1]/div[2]";
static String facdbrestypesselect = "//input[@name='ppt' and @type='checkbox']";
static String facdbresapply = "//span[text()='Apply Filter']";

// TC:18 faculty Question
static String facqb = "//span[text()='Question Bank']";
static String facaddque = "//span[text()='Add Question Manually']";
static String facquetype = "//*[@id=\"mui-component-select-questionType\"]";
static String facquetypeselect = "//*[@id=\"menu-questionType\"]/div[3]/ul/li[1]";
static String facqueclass = "//*[@id=\"mui-component-select-classSelected\"]";
static String facqueclasselect = "//*[@id=\"menu-classSelected\"]/div[3]/ul/li[2]";
static String facquesub = "//*[@id=\"mui-component-select-subjectSelected\"]";
static String facquesubselect = "//*[@id=\"menu-subjectSelected\"]/div[3]/ul/li";
static String facquesNEXT = "//span[text()='Next']";
static String facqueback = "(//span[text()='Question Bank'])[2]";

// TC:19 faculty COurse
static String faccc = "//span[text()='Course Content']";
static String facccactivity = "//span[text()='Activity']";
static String facassessmentrelative = "(//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-2'])[1]";
static String facaddactivityrelative = "//span[.='Add Activity']";
static String facccAsscancel = "//span[text()='Cancel']";
static String faccAssYes = "//span[text()='Yes']";
static String faccAssopen = "(//span[@class='MuiIconButton-label'])[5]";
static String FaccClickResource = "//span[text()='Resource']";
static String facrescancel = "//span[text()='Cancel']";
//TC:20 faculty Examination
static String facexam = "//*[@id=\"app\"]/div/div/div/main/div[2]/div[1]/div[2]/div[1]/div/div[2]/div/div[3]/a/div";
static String facexamarrow = "//*[@id=\"panel1a-header\"]/div[2]";
static String facexamdropdown = "//*[@id=\"simple-tabpanel-0\"]/div/p/div/div/div/div[1]/div/div/table/tbody/tr[1]/td[1]/button";
static String facexamexam = "//*[@id=\"simple-tabpanel-0\"]/div/p/div/div/div/div[1]/div/div/table/thead/tr/th[1]";
static String facexamdate = "//*[@id=\"simple-tabpanel-0\"]/div/p/div/div/div/div[1]/div/div/table/thead/tr/th[2]";
static String faceexamtime = "//*[@id=\"simple-tabpanel-0\"]/div/p/div/div/div/div[1]/div/div/table/thead/tr/th[3]";
// TC:21 faculty My student
static String faccMyStudent = "(//span[text()='My Students'])[2]";
static String facstudrop = "//*[@id=\"app\"]/div/div/div/main/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div/div[2]/div";
static String facstudropselect = "//*[@id=\"menu-\"]/div[3]/ul/li";
// TC:22 faculty Attendance
static String facatt = "(//span[text()='Attendance'])[2]";
static String faccAttendahis = "//span[text()='Attendance History']";
static String faccView = "(//span[text()='View'])[2]";
static String faccEdit = "(//span[text()='Edit'])[2]";
static String faccBack = "//span[text()='BACK']";
// TC:23 Faculty Timetable
static String facClickTimetable = "(//span[text()='Timetable'])[2]";
static String facttmonth = "//button[text()='month']";
static String facttweek = "//button[text()='week']";
static String facttday = "//button[text()='day']";

// TC-24:New change faculty portal Services
static String facServicespfsbmtnsom = "(//span[text()='Faculty Services' or text()='Support Services'])[2]";
static String FacRaisecasedevbmtech = "//span[text()='Raise a Case']";
static String FacRaisebutton = "//span[text()='Raise Case']";
static String facCancelSer = "//span[text()='Cancel']";
static String facMakedevNsom = "//p[text()='Make A Request']";
static String facMakeRButtondevNsome = "//span[text()=' Make a Request']";

// TC:25:faculty Raise request
static String inputSub = "//input[@name='Subject']";
static String SubmitRaise = "//button[@label='Submit']";
static String FacDesc = "//textarea[@placeholder='Type your description here...']";
//Tc:26 faculty make request
static String makeSubjectIn = "//input[@name='Subject']";
static String MakeBtn = "//button[@label='Submit']";
static String makedesc="//textarea[@placeholder='Type your description here...']";
//TC:27 Event
static String faccEvent = "(//span[text()='Events'])[2]";
static String faceventlocation = "//div[@aria-labelledby='Location']";
static String faceventlocationselect = "//input[@placeholder='Location' and @value='All']";
//TC:28faculty profile faculty
static String facSeleectpic = "(//span[@class='MuiIconButton-label'])[3]";
static String faccProfile = "//a[text()='Profile']";
static String facpersonal = "(//*[@id=\"panel1a-header\"])[1]";
static String facpdedit = "//*[name()='svg'and@height='23']";
static String Faced = "(//*[@xmlns='http://www.w3.org/2000/svg'])[8]";
static String facpdgender = "//*[@id=\"mui-component-select-Gender\"]";
static String facpdgenderselect = "//li[@data-value='Male']";
static String facpddob = "//input[@name='BirthDate']";
static String facpdnationality = "//input[@name='CountryOfResidence']";
static String facdpsave = "(//span[text()='Save'])[1]";
// TC:29 faculty address
static String address = "(//*[@id='panel1a-header'])[2]";
static String addressadd = "//*[@id=\"panel1a-content\"]/div/div/div/div[2]/div[1]/button";
static String facdpaddedit = "//*[@xmlns='http://www.w3.org/2000/svg']";
static String facdptype = "//div[@id='mui-component-select-Addresstype']";
static String FaccfaccTypeSelect = "//li[@data-value='Home']";
static String faccAddress = "//input[@name='address']";
static String faccPincode = "//input[@name='pincode']";
static String facccountry = "//div[@id='mui-component-select-Country']";
static String faccSelectCountry = "//li[@data-value='IN']";
static String faccState = "//div[@id='mui-component-select-state']";
static String faccSelectState = "//li[@data-value='KA' and text()='Karnataka']";
static String faccCity = "//div[@id='mui-component-select-distract']";
static String faccSelectCity = "//li[@data-value='Bangalore Urban' and text()='Bangalore Urban']";
static String faccSaveaddress = "(//span[text()='Save'])[2]";
// TC:30 faculty Acadmic profile edit
static String facdpacdeails = "(//*[@id=\"panel1a-header\"])[3]";
static String facdpacadd = "//*[@id=\"panel1a-content\"]/div/div/div[6]/div/div[1]/button";
static String facdplevel = "//*[@id=\"mui-component-select-level\"]";
static String facdplevelselect = "//*[@id=\"menu-level\"]/div[3]/ul/li[1]";
static String facdpadcountry = "//*[@id=\"mui-component-select-country\"]";
static String facdpadcountryselect = "//*[@id=\"menu-country\"]/div[3]/ul/li[101]";
static String facdpaduniversity = "//*[@name='university']";
static String facdpadyear = "//*[@name='year']";
static String facdpadsave = "(//span[text()='Save'])[3]";

//TC:31 faculty Reserch supervision edit
static String facdpre = "//h6[text()='RESEARCH SUPERVISION']/..";
static String facdpreedit = "//*[name()='svg'and@height='23']";
static String facdpreadd = "//*[@id=\"panel1a-content\"]/div/div/div/div/div/div[2]/div/div[1]/button";
static String facdprename = "//*[@name='name']";
static String facdprelink = "//*[@name='linkURL']";
static String facdpredesc = "//*[@name='description']";
static String faccSaveexp = "(//span[text()='Save'])[6]";
//TC:32 testfacultyRESEARCHPUBLICATION
static String facdppub = "//h6[text()='RESEARCH & PUBLICATIONS']/..";
static String facdppubedit = "//*[name()='svg'and@height='23']";
static String faccAddrowrese = "(//span[text()='Add Row'])[6]";
static String facdppubname = "(//input[@name='name'])[2]";
static String facdppublink = "(//input[@name='linkURL'])[2]";
static String facdppubdesc = "(//input[@name='description'])[2]";
static String faccSaveRes = "(//span[text()='Save'])[7]";

//TC:33 faculty confernece
static String faccConOpen = "//h6[text()='CONFERENCES']/..";
static String facdpconedit = "//*[name()='svg'and@height='23']";
static String faccaddrowcon = "(//span[text()='Add Row'])[7]";
static String facdpconname = "(//input[@name='name'])[3]";
static String facdpconlink = "(//input[@name='linkURL'])[3]";
static String facdpcondesc = "(//input[@name='description'])[3]";
static String facdpconsave = "(//span[text()='Save'])[8]";

//TC:34 faculty Book
static String facdpbook = "//h6[text()='BOOK']/..";
static String facdpbookedit = "//*[name()='svg'and@height='23']";
static String facdpbookadd = "(//span[text()='Add Row'])[8]";
static String facdpbookname = "(//input[@name='name'])[4]";
static String facdpbooklink = "(//input[@name='linkURL'])[4]";
static String facdpbookdesc = "(//input[@name='description'])[4]";
static String facdpbooksave = "(//span[text()='Save'])[9]";

//TC:35 faculty professionl association
static String facdpprof = "//h6[text()='PROFESSIONAL ASSOCIATION']/..";
static String facdpprofedit = "//*[name()='svg'and@height='23']";
static String facdpprofadd = "(//span[text()='Add Row'])[9]";
static String facdpprofname = "(//input[@name='name'])[5]";
static String facdpproflink = "(//input[@name='linkURL'])[5]";
static String facdpprofdesc = "(//input[@name='linkURL'])[5]";
static String facdpprofsave = "(//span[text()='Save'])[10]";

//TC:36 faculty Others
static String facdpother = "//h6[text()='OTHERS']/..";
static String facdpotheredit = "//*[name()='svg'and@height='23']";
static String facdpotheradd = "(//span[text()='Add Row'])[10]";
static String facdpothername = "(//input[@name='name'])[6]";
static String facdpotherlink = "(//input[@name='linkURL'])[6]";
static String facdpotherdesc = "(//input[@name='linkURL'])[6]";
static String facdpothersave = "(//span[text()='Save'])[11]";

//TC:37 Fcaulty Dashboard
//	static String openFacdevnosbm = "(//span[text()='Academics'])[2]";
//	static String clickFacDashdevnosbm = "//span[text()='Dashboard']";


//TC:38 faculty QuestionBank
static String facquestion = "//span[.='Question Bank']";
static String facaddmanual = "//button[@label='Add Question Manually']";
static String facclicksubject = "//div[@id='mui-component-select-subjectSelected']";
static String faccsubject = "//li[.='2022-AUG-BBA-Finance']";
static String faccnext = "//button[@label='Next']";
static String faccquestion = "//input[@name='questionText']";
static String faccquestionname = "//input[@name='questionName']";
static String faccmarks = "//input[@name='marks']";
static String faccoption1 = "//input[@name='questionText']";
static String feedback1 = "//input[@placeholder='Type your feedback here']";
static String faccoption2 = "(//input[@placeholder='Enter option'])[2]";
static String feedback2 = "(//input[@placeholder='Type your feedback here'])[2]";
static String faccoption3 = "(//input[@placeholder='Enter option'])[2]";
static String feedback3 = "(//input[@placeholder='Type your feedback here'])[3]";
static String numberofchoice = "//input[@value='ABCD']";

static String feedbackofcrtans = "//input[@name=\"correctAnswerFeedback\"]";

static String feefbacofincorrect = "//input[@name=\"wrongAnswerFeedback\"]";
static String generalfeedback = "//textarea[@name='generalFeedback']";
static String facsaveandfinish = "//p[text()='Save and Finish']";
static String facback = "//span[.='Back']";

// TC-39
static String facselectpro = "//*[@id=\"app\"]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div";
static String facprofile = "//*[@id=\"menu-appbar\"]/div[3]/ul/li[1]";



//******************************************************************************************** */
//******************************************************************************************** */
//Both role test cases begin here

//Test case no 40 testFacultyStudentFOURM Relative Xpath..............////////
////.............. faculty forum add activity RelativeXpath xpath...................////
static String relativefacforumClickacademics1 = "//span[.='Academics']";
static String relativefacforumclickcouse1 = "//span[.='Course Content']";
static String relativefacforumactivity1 = "//span[.='Activity']";
static String relativefacforum1 = "(//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-2'])[3]";
static String relativefacfforumaddactivity1 = "//span[.='Add Activity']";
static String relativefacforumname1 = "//input[@name='forumName']";
// static String facforummore = "//button[@title='More...']";
static String relativefacforumclink1 = "//button[@title='Insert/edit link']";
static String relativefacforumurl1 = "//input[@type='url']";
static String relativefacforumsavlin1 = "//button[@title='Save']";
static String relativefacforumsave1 = "//span[.='Save and Proceed to Forum Settings']";
static String relativefaforumsave1 = "//span[.='Save and Proceed']";
static String relativefaforumok1 = "//span[.='OK']";
static String relativeformexpand1 = "(//div[@id='panel1a-header'])[3]";
static String relativefaccformedot1 = "//p[.='spruthirajautomation']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String relativefacformepublish11 = "//span[.='Publish']";
static String relativefacformepublish12 = "//span[.='Publish']";
static String relativefacformeclickonp1 = "(//div[.='T'])[2]";
static String relativefacformesignout1 = "(//li[@role='menuitem'])[2]";
////............................ student forum login and view relative xpath.................////
static String relativestudentacadamicsltsta1 = "//span[.='Academics']";
static String relativeforumlearnltsta1 = "//span[.='Learn']";
static String relativeforumaexpandltsta1 = "(//div[@id='panel1a-header'])[3]";
static String relativeforumsubltstasign1 = "(//span[@class='MuiIconButton-label'])[3]";
static String relativeforumsubltstasignout1 = "(//li[@role='menuitem'])[2]";
////................................... faculty delete forum Relative xpath..................////
static String relativeforumdaccltsta12 = "//span[.='Academics']";
static String relativeforumdacclickcouse12 = "//span[.='Course Content']";
static String relativeforumdfexpandltsta12 = "(//div[@id='panel1a-header'])[3]";
static String relativeforumfclickondotltsta12 = "//p[.='spruthirajautomation']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String relativedfacdelete12 = "(//div[@role='button'])[22]";
static String relativedfacdele12 = "//span[.='Delete']";
static String relativedfacclickonp12 = "(//div[.='T'])[2]";
static String relativedfacconsignout12 = "(//li[@role='menuitem'])[2]";
//Test case 40 Xpaths ends here


//TC-41 PPT Xpath Started
//ppt upload
static String facClickacademics="(//span[text()='Academics'])[2]";
//// static String faccc="//span[text()='Course Content']";
static String facccres="//span[text()='Resource']";
static String facpptclick="//figcaption[text()='Presentation']/..";
static String facpptadd="//span[text()='Add Resource']";
static String facccresdescclick="//button[@title='Insert/edit link']";
static String facccresurl="//input[@type='url']";
static String facccressubmitform="//*[.='Save']";
static String facpptname="//input[@placeholder='Enter name']";
static String facccressubmit="//span[text()='Save Resource(s)']";
static String facccressubmityes="//span[text()='OK']";
//ltsta xpath
static String facClickacademicsltsta="//span[text()='Academics']";
//ppt publish
static String facpptfopen="(//div[@id='panel1a-header'])[4]";
static String facppt3dot="(//div[@class='MuiCardContent-root'])[8]";
static String facpptpublish="//span[text()='Publish']";
static String facpptpublishyes="//span[text()='Publish']";
////static String facSelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
static String facsignOut="//*[.='Sign Out']"; 
//student portal start
static String ltstaaccademics="//span[text()='Academics']";
static String accademics="(//span[text()='Academics'])[2]";
static String learn="//span[text()='Learn']";
static String viewppt="(//div[@id='panel1a-header'])[4]";
static String viewpdf2="//span[text()='View']";
////static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
// static String signOut="//*[.='Sign Out']";
//ppt delete
static String facpdfdelete="//span[text()='Delete']";
static String facpdfdelete2="//span[text()='Delete']";
static String logout="//*[@id=\"menu-appbar\"]/div[3]/ul/li[2]";
//TC-41 PPT Xpath Completed
   	
   	
//TC-42 PDF Xpath Started
////static String facClickacademics="(//span[text()='Academics'])[2]";
////static String faccc="//span[text()='Course Content']";
////static String facccres="//span[text()='Resource']"; 
//pdf upload
static String facccrespdf="//figcaption[text()='PDF']/..";
static String facccresadd="//span[text()='Add Resource']";
////static String facccresdescclick="//button[@title='Insert/edit link']";
////static String facccresurl="//input[@type='url']";
////static String facccressubmitform="//*[.='Save']";
////static String facpptname="//input[@placeholder='Enter name']";
////static String facccressubmit="//span[text()='Save Resource(s)']";
////static String facccressubmityes="//span[text()='OK']";
//ltsta xpath
////static String facClickacademicsltsta="//span[text()='Academics']";
//pdf publish
static String facpdfopen="(//div[@id='panel1a-header'])[6]";
static String fac3dot="(//div[@class='MuiCardContent-root'])[16]";
static String facpublishpdf="//span[text()='Publish']";
static String facpublishpdf2="//span[text()='Publish']";
////static String facSelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String facsignOut="//*[.='Sign Out']";   
//student portal start
////static String ltstaaccademics="//span[text()='Academics']";
////static String accademics="(//span[text()='Academics'])[2]";
////static String learn="//span[text()='Learn']";
static String viewpdf="(//div[@id='panel1a-header'])[6]";
////static String viewpdf2="//span[text()='View']";
////static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String signOut="//*[.='Sign Out']";
//pdf delete
////static String facpdfdelete="//span[text()='Delete']";
////static String facpdfdelete2="//span[text()='Delete']";   	
//TC-42 PDF Xpath Completed
   	

//TC-43 Video Xpath Started
////static String facClickacademics="(//span[text()='Academics'])[2]";
////static String faccc="//span[text()='Course Content']";
////static String facccres="//span[text()='Resource']"; 
//video upload
static String facvideoclick="//figcaption[text()='Video']/..";
static String facvideoadd="//span[text()='Add Resource']";
////static String facccresdescclick="//button[@title='Insert/edit link']";
////static String facccresurl="//input[@type='url']";
////static String facccressubmitform="//*[.='Save']";
////static String facpptname="//input[@placeholder='Enter name']";
////static String facccressubmit="//span[text()='Save Resource(s)']";
////static String facccressubmityes="//span[text()='OK']";
//ltsta xpath
////static String facClickacademicsltsta="//span[text()='Academics']";
//video publish
static String facvideoopen="(//div[@id='panel1a-header'])[7]";
static String facvideo3dot="(//div[@class='MuiCardContent-root'])[20]";
static String facvideopublish="//span[text()='Publish']";
static String facvideopublishyes="//span[text()='Publish']";
////static String facSelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String facsignOut="//*[.='Sign Out']";   
//student portal start
////static String ltstaaccademics="//span[text()='Academics']";
////static String accademics="(//span[text()='Academics'])[2]";
////static String learn="//span[text()='Learn']";
static String viewvideo="(//div[@id='panel1a-header'])[7]";
////static String viewpdf2="//span[text()='View']";
////static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String signOut="//*[.='Sign Out']";   
//Video delete
////static String facpdfdelete="//span[text()='Delete']";
////static String facpdfdelete2="//span[text()='Delete']";   	
//TC-43 Video Xpath Completed
   	

//TC-44 Link Xpath Started
////static String facClickacademics="(//span[text()='Academics'])[2]";
////static String faccc="//span[text()='Course Content']";
////static String facccres="//span[text()='Resource']";
//link upload no publish for link
static String faclinkclick="//figcaption[text()='Links']/..";
static String faclinkadd="//span[text()='Add Resource']";
static String faclinkexternal="//input[@name='externalUrl']";
////static String facccresdescclick="//button[@title='Insert/edit link']";
////static String facccresurl="//input[@type='url']";
////static String facccressubmitform="//*[.='Save']";
////static String facpptname="//input[@placeholder='Enter name']";
////static String facccressubmit="//span[text()='Save Resource(s)']";
////static String facccressubmityes="//span[text()='OK']";
//ltsta xpath
////static String facClickacademicsltsta="//span[text()='Academics']";
////static String facSelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String facsignOut="//*[.='Sign Out']";   
//student portal start
////static String ltstaaccademics="//span[text()='Academics']";
////static String accademics="(//span[text()='Academics'])[2]";
////static String learn="//span[text()='Learn']";
static String viewlink="(//div[@id='panel1a-header'])[8]";  
////static String viewpdf2="//span[text()='View']";
////static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String signOut="//*[.='Sign Out']";
//Link delete
////static String facpdfdelete="//span[text()='Delete']";
////static String facpdfdelete2="//span[text()='Delete']";  
static String faclink3dot="(//div[@class='MuiCardContent-root'])[24]";
static String faclinkopen="(//div[@id='panel1a-header'])[8]";
//TC-44 Link Xpath Completed
   

//// ................Test case no 45
//// testStudentFacultyASSEMENT.................................////
////.........................Faculty portal Assement test case path .................................////
static String facClickacademicsrelative = "//span[.='Academics']";
static String facclickcouserelative = "//span[.='Course Content']";
static String facactivityrelative = "//span[.='Activity']";
//// static String facassessmentrelative = "(//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-2'])[1]";
////static String facaddactivityrelative = "//span[.='Add Activity']";
static String facassesmentrelative = "//input[@name='assessmentName']";
static String facmorerelative = "//button[@title='More...']";
static String facclinkrelative = "//button[@title='Insert/edit link']";
static String facurlrelative = "//input[@type='url']";
static String facsavlinrelative = "//button[@title='Save']";
static String facsaverelative = "//span[.='Save and Proceed to Assessment Settings']";
static String fachourrelative = "//input[@name='timeLimitHours']";
static String fasaverelative = "//span[.='Save and Proceed']";
static String fasokrelative = "//span[.='OK']";
static String fasquestionrelative = "//p[.='From question bank']";
static String facselectrelative = "(//input[@type='checkbox'])[7]";
static String facaddselectrelative = "//span[.='Add Selected']";
static String facprevirelative = "//p[.='Preview']";
//	static String facSelectPrtoSignoutrealtive = "(//div[.='S'])[2]";
//	static String facsignOutrealtive = "(//li[@role='menuitem'])[2]";
////..........................Student login Assessment view relative xpath........................../////
// static String facforummore = "//button[@title='More...']";
static String Studentassessmenstrelativeacademic = "//span[.='Academics']";
static String Studentassessmenstrelativelearn = "//span[.='Learn']";
static String Studentassessmenstrelativelexpand = "(//span[@class='MuiIconButton-label'])[5]";
static String StudentassessmenstrelativeclickonA = "(//span[@class='MuiIconButton-label'])[3]";
static String Studentassessmenstrelativesignout = "(//li[@role='menuitem'])[2]";
////.......................Faculty delete Assessment relative xpath................................//			
static String facClickacademicsrelativedelete = "//span[.='Academics']";
static String facclickcouserelativedelete = "//span[.='Course Content']";
static String facultyassessmenstrelativelexpandtodelete = "(//span[@class='MuiIconButton-label'])[5]";
static String fclickondotltstarelativedelete = "//p[.='sachinautoengineer']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String fsubltstadeleterelativedelete = "(//div[@role='button'])[23]";
static String fsubltstadelete1relativedelete2 = "//span[.='Delete']";
static String facSelectPrtoSignoutrealtivedelete = "(//div[.='S'])[2]";
static String facsignOutrealtivedelete = "(//li[@role='menuitem'])[2]";
////................................end of assessment xpath.....................................//////			
static String facinstruction3dot="//*[@class='tox-tbtn' and @title='More...']";
static String facSelectPrtoSignoutrealtive="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
static String facsignOutrealtive="//*[.='Sign Out']"; 
//TC-45 Xpaths ends here


////.............................Test case no 46 testFacultyStudentASSIGNMENT RelativeXpath.........../////
////............................. assignment faculty add assign Relative Xpath...................//////
static String assignfacClickacademics1Relative = "//span[.='Academics']";
static String assignfacclickcouse1relative = "//span[.='Course Content']";
static String assignfacactivityrelative = "//span[.='Activity']";
static String assignfacassignmentrelative = "(//div[@class='MuiGrid-root MuiGrid-item MuiGrid-grid-xs-2'])[2]";
static String assignfacaddactivityrelative = "//span[.='Add Activity']";
static String assignfacassignmentNamerelative = "//input[@name='assignmentName']";
static String assignfaclinkrelative = "//button[@title='Insert/edit link']";
static String assignfacurlrelative = "//input[@type='url']";
static String assignfacsavlinrelative = "//button[@title='Save']";
static String assignfacsaverelative = "//span[.='Save and Proceed to Assignment Settings']";
static String assignfacdaterelative = "//input[@type='datetime-local']";
static String assignfactotalmarksrelative = "//input[@name=\"totalMarks\"]";
static String assignfacgraderelative = "//input[@name='gradetopass']";
static String assignfacattementsrelative = "//div[@aria-labelledby='mui-component-select-attemptsAllowed']";
static String assignfacselectattemtrelative = "//li[.='2 Attempts']";
static String assignfacsaveandproceedrelative = "//span[.='Save and Proceed']";
static String assignfacokrelative = "//span[.='OK']";
static String assignexapnd1relative = "(//div[@id='panel1a-header'])[1]";
static String assignfacdot1relative = "//p[.='varunautomation']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String assignfacpublishrelative = "//span[.='Publish']";
static String assignfacpublish1relative = "//span[.='Publish']";
static String assignfacclickonp1relative = "(//div[.='T'])[2]";
static String assignfacconsignout1relative = "(//li[@role='menuitem'])[2]";
//// ................. ..student assignment view relative xpath................................////
static String assignacadmicsltstastudentrelative = "//span[.='Academics']";
static String assignlearnltstastudentrelative = "//span[.='Learn']";
static String assignexpandltstastudentrelative = "(//div[@id='panel1a-header'])[1]";
static String assignltstasignstudentrelative = "(//span[@class='MuiIconButton-label'])[3]";
static String assignltstasignoutstudentrelative = "(//li[@role='menuitem'])[2]";
//// ........... assignment delete faculty Relative xpath..............................////
static String assignfacClickacademicsrelative = "//span[.='Academics']";
static String assignfacclickcouserelative = "//span[.='Course Content']";
static String assignexapndrelative = "(//div[@id='panel1a-header'])[1]";
static String assignfacdotrelative = "//p[.='varunautomation']/ancestor::div[@class='MuiGrid-root MuiGrid-container MuiGrid-spacing-xs-2 MuiGrid-justify-content-xs-space-around']/descendant::h5";
static String assignfacdeleterelative = "(//div[@role='button'])[23]";
static String assignfacdelerelative = "//span[.='Delete']";
static String assignfacclickonprelative = "(//div[.='T'])[2]";
static String assignfacconsignoutrelative = "(//li[@role='menuitem'])[2]";
//// TC-46 ends here.......................end of Assignment Realtive......................./////


   
   	
//TC-47 Spreadsheet Xpath Started
////static String facClickacademics="(//span[text()='Academics'])[2]";
////static String faccc="//span[text()='Course Content']";
////static String facccres="//span[text()='Resource']"; 
//spreadsheet upload
static String facssclick="//figcaption[text()='Spreadsheet']/..";
static String facssadd="//span[text()='Add Resource']";
////static String facccresdescclick="//button[@title='Insert/edit link']";
////static String facccresurl="//input[@type='url']";
////static String facccressubmitform="//*[.='Save']";
////static String facpptname="//input[@placeholder='Enter name']";
////static String facccressubmit="//span[text()='Save Resource(s)']";
////static String facccressubmityes="//span[text()='OK']";
//ltsta xpath
////static String facClickacademicsltsta="//span[text()='Academics']";
//spreadsheet publish
static String facssopen="(//div[@id='panel1a-header'])[5]";
static String facss3dot="(//div[@class='MuiCardContent-root'])[12]";
static String facsspublish="//span[text()='Publish']";
static String facsspublishyes="//span[text()='Publish']";
////static String facSelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String facsignOut="//*[.='Sign Out']"; 
//student portal start
////static String ltstaaccademics="//span[text()='Academics']";
////static String accademics="(//span[text()='Academics'])[2]";
////static String learn="//span[text()='Learn']";
static String viewss="(//div[@id='panel1a-header'])[5]";
////static String viewpdf2="//span[text()='View']";
////static String SelectPrtoSignout="/html/body/div[1]/div/div/div/main/div[2]/div[2]/header/div/header/div[1]/div[2]/div/div";
////static String signOut="//*[.='Sign Out']";
//Spreadsheet delete
////static String facpdfdelete="//span[text()='Delete']";
////static String facpdfdelete2="//span[text()='Delete']";   	
//TC-47 Spreadsheet Xpath Completed

}
