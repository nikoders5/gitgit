package com.profiler.data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import com.profiler.utils.AppUtil;
import com.profiler.utils.Excel;
import com.profiler.utils.Functions;
import com.relevantcodes.extentreports.LogStatus;

public class InfluenceStrength extends Functions {
	
	byte ZERO = 0;
	byte ONE = 1;
	byte TWO = 2;
	byte THREE = 3;
	byte FOUR = 4;
	byte FIVE = 5;
	byte SIX = 6;
	byte SEVEN = 7;
	byte EIGHT = 8;
	byte NINE = 9;
	
	String SEPERATOR = ",";
			
	String username = null;
	String password = null;
	String excel_file = "files//TestData.xlsx";
	String sheet_name = null;
	
	String strTarget="";
	By lookUp_entity = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+strTarget+"')])");
	
	By tools_filterWithIntials_select = By.xpath("//select[@id='jumpTo']");
	By tools_condn_page_select = By.xpath("//select[@id='bbox']");
	
	By tool_frame = By.xpath("(//*[@id='toolsContent'])");
	
	By toOpen_Link_InputData = By.xpath("(//*[@id='container']/table/descendant::div[@id='content']/table/descendant::div[@id='leftDiv']/descendant::a[contains(text(),'Influence Strengths')])");
	By toOpen_Create_NewPage = By.xpath("(//*[@id=\"menu\"]/li/a/span[1]//img[@src='/newimages/icons/new.gif'])");
	
	By roleDropDown = By.xpath("//*[@id='roleId_dd_label']");
	
	By popUp_Exists_Description = By.xpath("(//*[@id='messageDiv']/descendant::div[contains(text(),'Entity with this Description already exists')])");
	By popUp_Exists_Close = By.xpath("(//*[@id='resultDiv']/div[@id='messageDiv']/descendant::a[@title='close']["+ONE+"])");
	
	//Create
	By toInput_Data_txtBox = By.xpath("(//*[@id='profilerPopupDiv']/descendant::input[@id='description'])");
	By toSave_Create_NewPage = By.xpath("(//*[@id='profilerPopupDiv']/div[@id='profilerPopupMenu']/table/descendant::a/img[@alt='Save'])");
	
	//Delete
	By tools_condn_text_delete = By.xpath("//*[@id='highlightkeyword'][contains(text(),'"+ strTarget +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+FIVE+"]/a["+ONE+"]");
	By tools_condn_text_checkbox = By.xpath("//*[@id='highlightkeyword'][contains(text(),'"+strTarget+"')]/parent::td/preceding-sibling::td/descendant::input[@type='checkbox']");
	
	String deletePopUpTextMsg = "Delete 1 Influence Strengths?";
	String delCondnProcessingMsgText = "Your request is in queue. For more details check \"Profile Data Management->Realtime Mass Update tracker\" screen";
	
	//Edit
	By tools_condn_text_edit = By.xpath("//*[@id='highlightkeyword'][contains(text(),'"+ strTarget +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+FIVE+"]/a["+TWO+"]");
	By condn_new_box_input = By.xpath("(//*[@id='profilerPopupDiv']/descendant::input[@id='description'])");
	By condn_new_box_save = By.xpath("(//*[@id='profilerPopupDiv']/div[@id='profilerPopupMenu']/table/descendant::a/img[@alt='Save'])");
	
	String txtMsg = "Adding new Influence Strengths ";
	List<String> roleList = new LinkedList<String>();
		
	@Test
	public void testCreateInflncStrngth(){
		
		extentTest = extent.startTest("testCreateInflncStrngth");
		logInfoMessageInExtentTest(" ********** Starting testing : Influence Strengths - Create flow *************");
		
		sheet_name = "InfluenceStrength";
		
		if(username == null){
			username = getUserName(sheet_name);
			password = getUserPassword(sheet_name);
		}
		
		//Influence Strengths - Name : For Creation
		strTarget = Excel.readFromExcel(excel_file, sheet_name, FOUR, ONE);
				
		//Influence Strengths - Role : For Creation
		String addRoles = Excel.readFromExcel(excel_file, sheet_name, FIVE, ONE);
		
		List<String> roleList = new LinkedList<String>();
		
		if(addRoles != null) {
			if(addRoles.contains(SEPERATOR))
				roleList = Arrays.asList(addRoles.split(","));
			else
				roleList.add(addRoles);
		}
		
		AppUtil appUtil = new AppUtil();
		if(appUtil.login(username, password)){
			
			displayOnConsole("testCreateInflncStrngth() - User login SUCCESSFUL ");
			logInfoMessageInExtentTest(" ********** User Login Successful *************");
			
			driver = driver.switchTo().window(driver.getWindowHandle());
			
			wait(ONE);
			
			boolean noerror = InflncStrngthCreating (txtMsg, strTarget, roleList, toOpen_Link_InputData, toOpen_Create_NewPage);
			
			if(noerror == true){
				if(isElementPresent(driver, popUp_Exists_Description)){
					displayOnConsole("testCreateInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
					logFailMessageInExtentTest("testCreateInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
					
					String details = "Influence Strengths : Entity with this Description already exists. Please, give another description ";
					displayOnConsole("testCreateInflncStrngth() -  "+details);
					
					String screenshot = getScreenshot(driver, "Profile_Condn_Exists");
					Actions actions = new Actions(driver);
					Action action = actions.moveToElement(driver.findElement(popUp_Exists_Close),TWO,ZERO).click().build();
					action.perform();
					logInfoMessageInExtentTest(details);
				} else {
					displayOnConsole("testCreateInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
					logInfoMessageInExtentTest("testCreateInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
					
					//Verifying for the added Condition
					displayOnConsole("testCreateInflncStrngth() - Verifying the new added Influence Strengths.");
					boolean condnDescFlag = lookupDataOptionDescription(strTarget);
					if(condnDescFlag){
						displayOnConsole("testCreateInflncStrngth() - Verifying new Influence Strengths SUCCESSFUL ");
						logPassMessageInExtentTest("testCreateInflncStrngth() - Verifying new Influence Strengths SUCCESSFUL ");
					}
				}
			} else {
						logFailMessageInExtentTest("testCreateInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
			}
			
			driver = driver.switchTo().defaultContent();
			appUtil.logout();
		} else {
					displayOnConsole("testCreateInflncStrngth() -  User login UNSUCCESSFUL, Creating new Influence Strengths UNSUCCESSFUL ");
					logFailMessageInExtentTest("testCreateInflncStrngth() - User login UNSUCCESSFUL, Creating new Influence Strengths UNSUCCESSFUL ");
		}
		logInfoMessageInExtentTest(" ********** Ending testing : Influence Strengths : Create Flow *************");
	}
	
	@Test
	public void testDeleteInflncStrngth(){
		
		//starting of test 
		extentTest = extent.startTest("testDeleteInflncStrngth ");
		logInfoMessageInExtentTest(" ********** Starting testing : Influence Strengths - Delete flow *************");
		
		sheet_name = "InfluenceStrength";
		
		if(username == null){
			username = getUserName(sheet_name);
			password = getUserPassword(sheet_name);
		}
		
		//Influence Strengths - Name : For Creation
		String strToDelete = Excel.readFromExcel(excel_file, sheet_name, EIGHT, ONE);
				
		//Influence Strengths - Role : For Creation
		String addRoles = Excel.readFromExcel(excel_file, sheet_name, NINE, ONE);
		
		List<String> roleList = new LinkedList<String>();
		
		if(addRoles != null) {
			if(addRoles.contains(SEPERATOR))
				roleList = Arrays.asList(addRoles.split(","));
			else
				roleList.add(addRoles);
		}
		
		AppUtil apputil = new AppUtil();
		if(apputil.login(username, password)){

			displayOnConsole("testDeleteInflncStrngth() - User login SUCCESSFUL ");
			logInfoMessageInExtentTest(" ********** User Login Successful *************");
			
			driver = driver.switchTo().defaultContent();

			boolean noerror = InflncStrngthCreating (txtMsg, strToDelete, roleList, toOpen_Link_InputData, toOpen_Create_NewPage);

			if(noerror == true){

				if (isElementPresent(driver, popUp_Exists_Description)){
						displayOnConsole("testDeleteInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
						logInfoMessageInExtentTest("testDeleteInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
						
						String details = "Influence Strengths : Entity with this Description already exists. Deleting existing one.";
						displayOnConsole("testDeleteInflncStrngth() -  "+details);
						
						String screenshot = getScreenshot(driver, "Profile_Condn_Exists");
						Actions actions = new Actions(driver);
						Action action = actions.moveToElement(driver.findElement(popUp_Exists_Close),TWO,ZERO).click().build();
						action.perform();
						logInfoMessageInExtentTest(details);
				} else {
							displayOnConsole("testDeleteInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
							logInfoMessageInExtentTest("testDeleteInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
				}
				
				//Finding the Entity for deleting
				displayOnConsole("testDeleteInflncStrngth() - Finding the Influence Strengths to delete.");
				boolean lookupProfile = lookupDataOptionDescription(strToDelete);
				
				if(lookupProfile){

					boolean deletingprofilerole = InflncStrngthDeleting(strToDelete);
					
					if(deletingprofilerole){
						displayOnConsole("testDeleteInflncStrngth() - Deleting Influence Strengths SUCCESSFUL ");
						logInfoMessageInExtentTest("testDeleteInflncStrngth() - Deleting Influence Strengths SUCCESSFUL ");
						
						displayOnConsole("testDeleteInflncStrngth() - Verifying the deleted Influence Strengths.");
						
						//Verifying whether deleted or not.
						lookupProfile =  lookupDataOptionDescription(strToDelete);
						if(lookupProfile == false) {
								displayOnConsole("testDeleteInflncStrngth() - Verifying for deleting Influence Strengths SUCCESSFUL ");
								logPassMessageInExtentTest("testDeleteInflncStrngth() - Verifying for deleting Influence Strengths SUCCESSFUL ");
						}
						else {
								displayOnConsole("testDeleteInflncStrngth() - Verifying for deleting Influence Strengths UNSUCCESSFUL ");
								logFailMessageInExtentTest("testDeleteInflncStrngth() - Verifying for deleting Influence Strengths UNSUCCESSFUL ");
						}
					} else {
								displayOnConsole("testDeleteInflncStrngth() - Deleting Influence Strengths UNSUCCESSFUL ");
								logFailMessageInExtentTest("testDeleteInflncStrngth() - Deleting Influence Strengths UNSUCCESSFUL ");
					}
				} else {
					displayOnConsole("testDeleteInflncStrngth() - Search for Influence Strengths itself UNSUCCESSFUL, so, can't procced to delete. ");
					logFailMessageInExtentTest("testDeleteInflncStrngth() - Search for Influence Strengths itself UNSUCCESSFUL, so, can't procced to delete. ");
				}
				
			} else{
					displayOnConsole("testDeleteInflncStrngth() - Creating new Influence Strengths itself UNSUCCESSFUL, so, can't procced to delete. ");
					logFailMessageInExtentTest("testDeleteInflncStrngth() - Creating new Influence Strengths itself UNSUCCESSFUL, so, can't procced to delete. ");
			}
			
			driver = driver.switchTo().defaultContent();
			apputil.logout();

		}else{
			  	displayOnConsole("testDeleteInflncStrngth() - User login UNSUCCESSFUL, deleting Influence Strengths UNSUCCESSFUL ");
			  	logFailMessageInExtentTest("testDeleteInflncStrngth() - User login UNSUCCESSFUL, deleting Influence Strengths UNSUCCESSFUL ");
		}
		
		logInfoMessageInExtentTest(" ********** Ending testing : Influence Strengths - Delete flow *************");
	}

	@Test
	public void testEditInflncStrngth(){
		
		//start of test
		extentTest = extent.startTest("testEditInflncStrngth ");
		logInfoMessageInExtentTest(" ********** Starting testing : Influence Strengths - Edit flow *************");
		
		sheet_name = "InfluenceStrength";
		
		//application username, password
		if(username == null){
			username = getUserName(sheet_name);
			password = getUserPassword(sheet_name);
		}
		
		/*Reading data from excel-data sheet - "InfluenceStrength" */
		
		//Influence Strengths - Name : For Creation
		strTarget = Excel.readFromExcel(excel_file, sheet_name, FOUR, ONE);
		
		//Influence Strengths - Role : For Creation
		String addRoles = Excel.readFromExcel(excel_file, sheet_name, FIVE, ONE);
		
		List<String> roleList = new LinkedList<String>();
				
		if(addRoles != null) {
			if(addRoles.contains(SEPERATOR))
				roleList = Arrays.asList(addRoles.split(","));
			else
				roleList.add(addRoles);
		} else {
			roleList.add("");
		}
		
		
		//Influence Strengths - New name : For Editing
		String strReplacement = Excel.readFromExcel(excel_file, sheet_name, SIX, ONE);
				
		AppUtil apputil = new AppUtil();
		if(apputil.login(username, password)){
			
			displayOnConsole("testEditInflncStrngth() - User login SUCCESSFUL ");
			logInfoMessageInExtentTest(" ********** User Login Successful *************");
			
			boolean noerror = InflncStrngthCreating (txtMsg, strTarget, roleList, toOpen_Link_InputData, toOpen_Create_NewPage);
			if(noerror == true){
				
				if(isElementPresent(driver, popUp_Exists_Description)){
					displayOnConsole("testEditInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
					logInfoMessageInExtentTest("testEditInflncStrngth() - Creating new Influence Strengths UNSUCCESSFUL ");
					
					String details = "Influence Strengths already exists, editing existing one ";
					displayOnConsole("testProfileRoleEditing - "+details);
					
					String screenshot = getScreenshot(driver, "Profile_Role_Exists");
					Actions actions = new Actions(driver);
					Action action = actions.moveToElement(driver.findElement(popUp_Exists_Close),TWO,ZERO).click().build();
					action.perform();
					logInfoMessageInExtentTest("testEditInflncStrngth() - " + details);
					
				} else {
						displayOnConsole("testEditInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
						logInfoMessageInExtentTest("testEditInflncStrngth() - Creating new Influence Strengths SUCCESSFUL ");
				}
				
				//Finding for editing
				displayOnConsole("testEditInflncStrngth() - Finding the Influence Strengths to Edit.");
				boolean lookupProfile = lookupDataOptionDescription(strTarget);
				
				if(lookupProfile){
					displayOnConsole("testEditInflncStrngth() - Finding the Influence Strengths to Edit SUCCESSFUL.");
					logInfoMessageInExtentTest("testEditInflncStrngth() - Finding the Influence Strengths to Edit SUCCESSFUL.");
					
					boolean profileroleedit = InflncStrngthEditing(strTarget, strReplacement);
					
					if(profileroleedit){
						
						if(isElementPresent(driver, popUp_Exists_Description)){
							
							String details = "Influence Strengths with same name already exists, Editing Influence Strengths UNSUCCESSFUL ";
							displayOnConsole("testEditInflncStrngth() : "+details);
							
							String screenshot = getScreenshot(driver, "Profile_Role_Exists");
							Actions actions = new Actions(driver);
							Action action = actions.moveToElement(driver.findElement(popUp_Exists_Close),TWO,ZERO).click().build();
							action.perform();
							logInfoMessageInExtentTest("testEditInflncStrngth() - " + details);
							logFailMessageInExtentTest("testEditInflncStrngth() - Editing Condition UNSUCCESSFUL ");
							
						} else {
							displayOnConsole("testEditInflncStrngth() - Editing Influence Strengths SUCCESSFUL ");
							logInfoMessageInExtentTest("testEditInflncStrngth() - Editing Influence Strengths SUCCESSFUL ");
							
							displayOnConsole("testEditInflncStrngth() - Verifying the Edited Influence Strengths.");
							logInfoMessageInExtentTest("testEditInflncStrngth() - Verifying the Edited Influence Strengths.");
							
							//Verifying whether edited or not.
							lookupProfile = lookupDataOptionDescription(strReplacement);
							if(lookupProfile == true) {
								displayOnConsole("testEditInflncStrngth() - Verifying Edited Influence Strengths SUCCESSFUL ");
								logPassMessageInExtentTest("testEditInflncStrngth() - Verifing Edited Influence Strengths SUCCESSFUL ");
							}
							else {
								displayOnConsole("testEditInflncStrngth() - Verifying Edited Influence Strengths UNSUCCESSFUL ");
								logFailMessageInExtentTest("testEditInflncStrngth() - Edited Influence Strengths UNSUCCESSFUL ");
							}	
						}
					} else{
								displayOnConsole("testEditInflncStrngth() - Editing Influence Strengths UNSUCCESSFUL ");
								logPassMessageInExtentTest("testEditInflncStrngth() - Editing Influence Strengths UNSUCCESSFUL ");
					}
				} else {
							displayOnConsole("testEditInflncStrngth() - Search for Influence Strengths itself UNSUCCESSFUL, so, can't procced to edit. ");
							logFailMessageInExtentTest("testEditInflncStrngth() - Search for Influence Strengths itself UNSUCCESSFUL, so, can't procced to edit. ");
				}
				
			} else {
						displayOnConsole("testEditInflncStrngth() - Creating new Influence Strengths itself UNSUCCESSFUL, so, can't procced to edit. ");
						logFailMessageInExtentTest("testEditInflncStrngth() - Creating new Influence Strengths itself UNSUCCESSFUL, so, can't procced to edit. ");
			}
			
			driver = driver.switchTo().defaultContent();
			apputil.logout();

		} else{
			
			displayOnConsole("testEditInflncStrngth() - User login UNSUCCESSFUL, Editing Influence Strengths UNSUCCESSFUL ");
			logFailMessageInExtentTest("testEditInflncStrngth() - User login UNSUCCESSFUL, Editing Influence Strengths UNSUCCESSFUL ");
		}
		
		logInfoMessageInExtentTest(" ********** Ending testing : Influence Strengths - Edit flow *************");
	}

	String getUserPassword(String sheet_name) {
		
		String password = Excel.readFromExcel(excel_file, sheet_name, THREE, ONE);
		if(password == null || password.isEmpty() || password.equalsIgnoreCase("")){
			password = Excel.readFromExcel(excel_file, "Application", THREE, ONE);
		}
		
		return password;
	}

	String getUserName(String sheet_name) {
		String user = Excel.readFromExcel(excel_file, sheet_name, TWO, ONE);
		if(user == null || user.isEmpty() || user.equalsIgnoreCase("")){
			user = Excel.readFromExcel(excel_file, "Application", TWO, ONE);
		}
		return user;	
	}
	
	void displayOnConsole(String consoleMessage) {
		System.out.println(consoleMessage);
	}
	
	void logFailMessageInExtentTest(String details) {
		extentTest.log(LogStatus.FAIL, details);
	}
	
	void logPassMessageInExtentTest(String details) {
		extentTest.log(LogStatus.PASS, details);
	}

	void logInfoMessageInExtentTest(String details) {
		extentTest.log(LogStatus.INFO, details);
	}
	
	boolean InflncStrngthCreating (String txtMsg, String strTarget, List<String> roleList, By toOpen_Link_InputData, By toOpen_Create_NewPage) {
		boolean noerror = true;

		try {
			
			//click for more tools
			//By more = By.xpath("(//div[@id='navWrapper']/ul[@id='sectionsNav']/descendant::a[@title='More'])");
			//By more_tool = By.xpath("(//*[@id='section_tools'])");
			//click(more);
			//wait(TWO);
			//click(more_tool);
			
			//Above was temporarily not working , so using below workaround to navigate
			driver.navigate().to(driver.getCurrentUrl()+"action=newFrameset&sourceTab=tools&siteArea=admin&isToolsNavigation=true");
			wait(ONE);
			
			displayOnConsole("InflncStrngthCreating() - "+txtMsg);
			logInfoMessageInExtentTest(" ********** " + txtMsg + "*************");
			
			//go to internal html
			driver = driver.switchTo().frame(driver.findElement(tool_frame));
			
			wait(ONE);
			//click Influence Strengths link
			click(toOpen_Link_InputData);
			//wait
			wait(ONE);
			
			displayOnConsole("InflncStrngthCreating() - Description read from excel-sheet '" + sheet_name +"' is '" +strTarget + "'");
			logInfoMessageInExtentTest(" InflncStrngthCreating() - Description read from excel-sheet '" + sheet_name +"' is '" +strTarget + "'");
			//click to open new Influence Strengths popup
			click(toOpen_Create_NewPage);
			wait(ONE);
			
			//Enter Name
			click(toInput_Data_txtBox);
			driver.findElement(toInput_Data_txtBox).clear();
			enter(toInput_Data_txtBox, strTarget);
			
			WebElement dropDown = driver.findElement(By.xpath("//*[@id='roleId_dd_main']//div[contains(text(),'Select Role')]"));
			dropDown.click();
			
			//Enter Roles
			int roleAddCount = roleList.size();
			
			List<WebElement> chk = driver.findElements(By.xpath("//*[@id='roleId_dd_options']//input[@type='checkbox'] "));
			int totalChkBoxOptions = chk.size();
			
			WebDriverWait wait = new WebDriverWait(driver,300);
			for (int i = 0, j = 0 ; (i < totalChkBoxOptions && j < roleAddCount ); i++) {
				 //System.out.println("roleAddCount :: "+ j);
			    WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
			    		driver.findElements(By.name("roleId")).get(i)));
			    String roleElement = driver.findElements(By.name("roleId")).get(i).getAttribute("id").toString();
			      if (!ele.isSelected() && roleList.contains(roleElement)) {
			    	   j++;
			           ele.click();
			       }
			 }
			
			//click save
			click(toSave_Create_NewPage);
			wait(TWO);
			
		} catch (Exception problem) {
			noerror=false;
			logFailMessageInExtentTest("InflncStrngthCreating() :: Problem : "+ problem.fillInStackTrace());
		}
		
		return noerror;
	}
	
	boolean InflncStrngthDeleting(String strToDelete) {
		boolean deleteDataOptionFlag = true;
		
		try {
			
			//Entity : to Delete
			strToDelete = strToDelete.trim();
			
			displayOnConsole("InflncStrngthDeleting() - Deleting Entity : "+ strToDelete);
			logInfoMessageInExtentTest("InflncStrngthDeleting() - Deleting Entity : "+ strToDelete);
			
			tools_condn_text_delete = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ strToDelete +"')]/parent::td/preceding-sibling::td/parent::tr/td["+SIX+"]/a["+ONE+"]/img)");
			tools_condn_text_checkbox = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ strToDelete +"')]/parent::td/preceding-sibling::td/descendant::input[@type='checkbox'])");
			
			//Selecting checkbox
			click(tools_condn_text_checkbox);
			wait(ONE);
			
			//Clicking Delete image
			click(tools_condn_text_delete);
			wait(ONE);
			
			//Capturing delete text msg from delete pop-up
			Alert alertbox = driver.switchTo().alert();
			String alertmessagetext = alertbox.getText();
			boolean textcontent = alertmessagetext.contains(deletePopUpTextMsg);
			if(textcontent){
				logInfoMessageInExtentTest("Delete pop-up message : " + deletePopUpTextMsg);
			}
			
			alertbox.accept();
			wait(TWO);
						
		} catch (Exception problem) {
			
			deleteDataOptionFlag = false;
			logInfoMessageInExtentTest("InflncStrngthDeleting() :: Problem : "+ problem.getMessage());
		}
		
		return deleteDataOptionFlag;
	}
	
	boolean InflncStrngthEditing(String strTarget, String strReplacement) {
		boolean editDataOptionFlag = true;
		
		try {
			
			displayOnConsole("InflncStrngthEditing() - Editing Entity : "+ strTarget);
			logInfoMessageInExtentTest("InflncStrngthEditing() - Editing Entity : "+ strTarget);
			
			//Influence Strengths - New Roles : For Editing
			String editRoles = Excel.readFromExcel(excel_file, sheet_name, SEVEN, ONE);
			
			List<String> editRoleList = new LinkedList<String>();
			
			if(editRoles != null) {
				if(editRoles.contains(SEPERATOR))
					editRoleList = Arrays.asList(editRoles.split(","));
				else
					editRoleList.add(editRoles);
			}	
			
			//Editing existing Condition description
			strTarget = strTarget.trim();
			tools_condn_text_edit = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ strTarget +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+SIX+"]/a["+TWO+"]/img)");
			click(tools_condn_text_edit);
			wait(ONE);
			
			//enter profile role description
			click(condn_new_box_input);
			driver.findElement(condn_new_box_input).clear();
			enter(condn_new_box_input, strReplacement);
			
			//Role
			WebElement dropDown = driver.findElement(By.xpath("//*[@id='roleId_dd_main']//div[contains(@caption,'Select Role')]"));
			dropDown.click();
			
			int roleEditCount = editRoleList.size();
			
			// Nothing to be changed while editing i.e. editing for role field should be ignored.
			if(editRoleList.size() == 0) {
				dropDown.click();
			} else { 
				//Editing the old roles
				
				List<WebElement> chk = driver.findElements(By.xpath("//*[@id='roleId_dd_options']//input[@type='checkbox'] "));
				int totalChkBoxOptions = chk.size();
			
				WebDriverWait wait = new WebDriverWait(driver,300);
				//for (int i = 0, j = roleEditCount ; (i < totalChkBoxOptions && j > 0 ); i++) {
				for (int i = 0 ; i < totalChkBoxOptions ; i++) {
				    WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(
				    		driver.findElements(By.name("roleId")).get(i)));
				    
				    String roleElement = driver.findElements(By.name("roleId")).get(i).getAttribute("id").toString();
				    
				    //Adding the new roles
				    if (!ele.isSelected() && editRoleList.contains(roleElement))
			        { 
				    	//j-- ;
				    	ele.click(); 
			        } // Removing the existing one as not required after edit 
				    else if (ele.isSelected() && !editRoleList.contains(roleElement) ) {
				    	//j-- ;
			        	ele.click();
			        } else if (ele.isSelected() && editRoleList.contains(roleElement) ) {
			        	//j-- ;
			        }
				}
			}
			//click save
			click(condn_new_box_save);
			wait(ONE);
						
		} catch (Exception problem) {

			editDataOptionFlag = false;
			logInfoMessageInExtentTest("InflncStrngthEditing :: Problem " + problem.fillInStackTrace());
			
		}

		return editDataOptionFlag;
	}
	
	boolean lookupDataOptionDescription(String lookForString) {
		
		boolean lookUpDataFlag = false;
		
		String filter = String.valueOf(lookForString.charAt(ZERO)).toUpperCase();
		
		boolean selectvalue = selectByValue(driver, tools_filterWithIntials_select, filter);
		wait(TWO);
		
		lookForString = lookForString.trim();
		lookUp_entity = By.xpath("//*[@id='highlightkeyword'][contains(text(),'"+lookForString+"')]");
		
	
		filter = String.valueOf(ONE);
		
		List<WebElement> navigationoptions = null;
		try
		{
			navigationoptions = driver.findElements(By.xpath("//select[@id='bbox']//*[self::option]"));
		}catch(Exception problem){
			logInfoMessageInExtentTest("lookupDataOptionDescription() :: problem : " + problem.getMessage());
		}
		
		//displayOnConsole("lookupDataOptionDescription() - Total pages : "+navigationoptions.size());
		for(byte pages = TWO; pages <= navigationoptions.size(); pages++){
			if((lookUpDataFlag = isElementPresent(driver, lookUp_entity)) == true){
				break;
			}else{
				click(By.xpath("//table[@id='cu']/thead/tr["+ONE+"]/td["+TWO+"]/a"));
				lookUpDataFlag = isElementPresent(driver, lookUp_entity);
				if(lookUpDataFlag == true)
					break;
			}
			
			filter = String.valueOf(pages);
			selectvalue = selectByValue(driver, tools_condn_page_select, filter);
			wait(ONE);
		}
		
		if(lookUpDataFlag == true)
			displayOnConsole("lookupDataOptionDescription() - Searched profile found at page no. : "+filter);
		
		if(navigationoptions.size() == ZERO || navigationoptions.size() == ONE)
		{
			lookUpDataFlag = isElementPresent(driver, lookUp_entity);
		}
		
		return lookUpDataFlag;
	}

}
