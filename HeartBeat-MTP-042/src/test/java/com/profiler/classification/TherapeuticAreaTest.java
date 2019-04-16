package com.profiler.classification;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import com.profiler.utils.AppUtil;
import com.profiler.utils.Excel;
import com.profiler.utils.Functions;

public class TherapeuticAreaTest extends Functions {
	
	int ZERO = 0;
	int ONE = 1;
	int TWO = 2;
	int THREE = 3;
	int FOUR = 4;
	int FIVE = 5;
	int SIX = 6;
	int SEVEN = 7;
	int EIGHT = 8;
	int NINE = 9;
	int TEN = 10;

	String username = null;
	String password = null;
	String excel_file = "files//TestData.xlsx";
	String sheet_name = null;
	
	By more = By.xpath("(//div[@id='navWrapper']/ul[@id='sectionsNav']/descendant::a[@title='More'])");
	By more_tool = By.xpath("(//div[@id='navWrapper']/ul[@id='sectionsNav']/descendant::a[@id='section_tools'])");
	By tool_frame = By.xpath("(//*[@id='toolsContent'])");
	By therapeutic_area_link = By.xpath("(//*[@id='container']/table/descendant::div[@id='content']/table/descendant::div[@id='leftDiv']/descendant::a[contains(text(),'Therapeutic Area')])");
	
	String therapeuticareadescription="";
	
	By therapeutic_areas_text = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+therapeuticareadescription+"')])");
	
	By therapeutic_area_exists_close = By.xpath("(//*[@id='resultDiv']/div[@id='messageDiv']/descendant::a[@title='close']["+ONE+"])");
	
	By therapeutic_area_new_box_input = By.xpath("(//*[@id='profilerPopupDiv']/descendant::input[@id='description'])");
	
	By therapeutic_area_new_box_save = By.xpath("(//*[@id='profilerPopupDiv']/div[@id='profilerPopupMenu']/table/descendant::a/img[@alt='Save'])");
	
	By tools_new_therapeutic_area_link = By.xpath("(//*[@id='menuTable']/descendant::a[starts-with(@href,'javascript:openProfilerPopup')])");
	
	By therapeutic_area_exists_description = By.xpath("(//*[@id='messageDiv']/descendant::div[contains(text(),'Entity with this Description already exists')])");
	
	By tools_therapeutic_areas_select = By.xpath("(//*[@id='jumpTo'])");
	
	By segmentation_results = By.xpath("(//*[@id='homePageRisingStars']/descendant::span[contains(text(),'TOP SEGMENTATION RESULTS')])");
	
	By tools_therapeutic_areas_forward_link = By.xpath("(//img[@name='forward'])");
	
	By tools_therapeutic_areas_text_delete = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ therapeuticareadescription +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+FIVE+"]/a["+ONE+"])");
	
	String deletetherapeuticareatext = "Delete 1 Therapeutic Area?";
	
	String processingmessagetext = "Your request is in queue. For more details check \"Profile Data Management->Realtime Mass Update tracker\" screen";
	
	By tools_therapeutic_areas_text_edit = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ therapeuticareadescription +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+FIVE+"]/a["+TWO+"])");
	
	By tools_therapeutic_areas_text_checkbox = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+therapeuticareadescription+"')]/parent::td/preceding-sibling::td/descendant::input[@type='checkbox'])");
	
	By tools_delete_therapeutic_area_link = By.xpath("(//*[@id='menuTable']/descendant::a[starts-with(@href,'javascript:sortableListHandler_delete')])");
	
	String deletemultipletherapeuticareatext = "Delete 3 Therapeutic Areas?";
	
	By tools_therapeutic_areas_page_select = By.xpath("(//select[@id='bbox'])");
	
	String navigatetotools = "action=newFrameset&sourceTab=tools&siteArea=admin&isToolsNavigation=true";
			
	@Test
	public void testCreateTherapeuticArea(){
		
		try {
			extentTest = extent.startTest("createTherapeuticArea ");
			sheet_name = "CreateTherapeuticArea";
			
			if(username == null){
				username = getUserName(sheet_name);
				password = getUserPassword(sheet_name);
			}

			driver = driver.switchTo().window(driver.getWindowHandle());
			AppUtil appUtil = new AppUtil();
			if(appUtil.login(username, password)){
				
				//displayOnConsole("testCreateTherapeuticArea() - User login SUCCESSFUL ");
				
				navigateToTherapeuticArea();
				//read therapeutic area description from test data
				therapeuticareadescription = Excel.readFromExcel(excel_file, sheet_name, FOUR, ONE);
				
				boolean noerror = therapeuticAreaCreating(therapeuticareadescription);
				if(noerror == true){
					if(isElementPresent(driver, therapeutic_area_exists_description)){
						
						String details = "therapeutic area already exists, give another therapeutic area description ";
						//displayOnConsole("testCreateTherapeuticArea() -  "+details);
						String screenshot = getScreenshot(driver, "Therapeutic_Area_Exists");
						Actions actions = new Actions(driver);
						Action action = actions.moveToElement(driver.findElement(therapeutic_area_exists_close),TWO,ZERO).click().build();
						action.perform();
						logInfoMessageInExtentTest(details);
						logFailMessageInExtentTest("testCreateTherapeuticArea() - Creating of new therapeutic area description UNSUCCESSFUL ");
					}else{
						
						boolean therapeuticarea = lookupTherapeuticAreaDescription();
						if(therapeuticarea){
							
							//displayOnConsole("Added new therapeutic area ");
							logInfoMessageInExtentTest("testCreateTherapeuticArea() - Added new therapeutic area ");
						}
						
						logPassMessageInExtentTest("testCreateTherapeuticArea() - Creating of new therapeutic area description SUCCESSFUL ");
					}
				}else{
					
					logFailMessageInExtentTest("testCreateTherapeuticArea() - Creating of new therapeutic area description UNSUCCESSFUL ");
				}
				driver = driver.switchTo().defaultContent();
				logInfoMessageInExtentTest("testCreateTherapeuticArea() - user logout started ");
				appUtil.logout();

			} else {
				
				//displayOnConsole("testCreateTherapeuticArea() - User login UNSUCCESSFUL ");
				logFailMessageInExtentTest("testCreateTherapeuticArea() - User login UNSUCCESSFUL, creating of new therapeutic area description UNSUCCESSFUL ");
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("testCreateTherapeuticArea() - "+problem.fillInStackTrace());
		}
	}

	boolean lookupTherapeuticAreaDescription() {
		
		boolean therapeuticarea = false;
		
		try {
			String filter = String.valueOf(therapeuticareadescription.charAt(ZERO)).toUpperCase();
			
			boolean selectvalue = selectByValue(driver, tools_therapeutic_areas_select, filter);
			wait(TWO);
			
			therapeuticareadescription = therapeuticareadescription.trim();
			therapeutic_areas_text = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+therapeuticareadescription+"')])");
			
			filter = String.valueOf(ZERO);
			
			List<WebElement> navigationoptions = null;
			try
			{
				navigationoptions = driver.findElements(By.xpath("(//select[@id='bbox']//*[self::option])"));
			}catch(Exception problem){
				logFailMessageInExtentTest("problem  " + problem.fillInStackTrace());
			}
			
			for(int pages = TWO; pages <= navigationoptions.size(); pages++){
				if((therapeuticarea = isElementPresent(driver, therapeutic_areas_text)) == true){
					break;
				}else{
					click(By.xpath("(//table[@id='ct']/thead/tr["+ONE+"]/td["+TWO+"]/a)"));
					therapeuticarea = isElementPresent(driver, therapeutic_areas_text);
					if(therapeuticarea == true)
						break;
				}
				filter = String.valueOf(pages);
				selectvalue = selectByValue(driver, tools_therapeutic_areas_page_select, filter);
				wait(ONE);
			}
			//displayOnConsole("elements "+navigationoptions.size());
			if(navigationoptions.size() == ONE)
			{
				therapeuticarea = isElementPresent(driver, therapeutic_areas_text);
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("problem  " + problem.fillInStackTrace());
		}
		
		return therapeuticarea;
	}

	void loginUnsuccesful() {
		extentTest.log(LogStatus.FAIL,"User login UNSUCCESSFUL ");
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

	void displayOnConsole(String consoleMessage) {
		System.out.println(consoleMessage);
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

	boolean therapeuticAreaCreating(String therapeuticareadescription) {
		boolean noerror = true;

		try {
			

			//displayOnConsole(sheet_name + " " + therapeuticareadescription + " description ");
			
			//click new therapeutic area
			click(tools_new_therapeutic_area_link);
			wait(ONE);
			
			click(therapeutic_area_new_box_save);
			Alert alertbox = driver.switchTo().alert();
			String alerttext = alertbox.getText();
			boolean textalert = alerttext.contains("Therapeutic Area is required field. Please enter a value for Therapeutic Area.");
			alertbox.accept();
			wait(ONE);
			
			//enter therapeutic area description
			click(therapeutic_area_new_box_input);
			driver.findElement(therapeutic_area_new_box_input).clear();
			enter(therapeutic_area_new_box_input, therapeuticareadescription);
			
			//click save
			click(therapeutic_area_new_box_save);
			wait(TWO);
			
		} catch (Exception problem) {
			noerror=false;
			logInfoMessageInExtentTest("Problem observed "+ problem.fillInStackTrace());
		}
		
		return noerror;
	}

	void navigateToTherapeuticArea() {
		//driver = driver.switchTo().defaultContent();
		
		//click for more tools
		//waitForElementtoBeVisible(segmentation_results);
		//clickMoreTool();
		
		//Above actions were temporarily not working correctly in these automated tests , so using below workaround to navigate
		driver.navigate().to(driver.getCurrentUrl()+navigatetotools);
		wait(TWO);
		
		//displayOnConsole("Adding new therapeutic area ");
		
		//go to internal html
		driver = driver.switchTo().frame(driver.findElement(tool_frame));
		
		
		//click therapeutic area link
		click(therapeutic_area_link);
		//wait
		wait(ONE);
	}
	
	@Test
	public void testDeleteTherapeuticArea(){
		
		try {
			//starting of test 
			extentTest = extent.startTest("testDeleteTherapeuticArea ");
			
			//delete therapeutic area test data
			sheet_name = "CreateTherapeuticArea";
			
			if(username == null){
				username = getUserName(sheet_name);
				password = getUserPassword(sheet_name);
			}
			
			driver = driver.switchTo().window(driver.getWindowHandle());
			AppUtil apputil = new AppUtil();
			if(apputil.login(username, password)){
			
				//displayOnConsole("testDeleteTherapeuticArea() - User login SUCCESSFUL ");
				
				navigateToTherapeuticArea();
				
				//read therapeutic area description from test data
				therapeuticareadescription = Excel.readFromExcel(excel_file, sheet_name, FIVE, ONE);

				boolean noerror = therapeuticAreaCreating(therapeuticareadescription);
				
				if(noerror == true){
					
					if(isElementPresent(driver, therapeutic_area_exists_description)){

						String details = "therapeutic area already exists, deleting existing therapeutic area description ";
						//displayOnConsole("testDeleteTherapeuticArea() -  "+details);
						String screenshot = getScreenshot(driver, "Therapeutic_Area_Exists");
						Actions actions = new Actions(driver);
						Action action = actions.moveToElement(driver.findElement(therapeutic_area_exists_close),TWO,ZERO).click().build();
						action.perform();
						logInfoMessageInExtentTest("testDeleteTherapeuticArea() - "+details);
										
					}else{
						
						logPassMessageInExtentTest("testDeleteTherapeuticArea() - Creating of new therapeutic area description for deleting SUCCESSFUL ");

					}
					
					boolean lookupProfile = lookupTherapeuticAreaDescription();
					
					if(lookupProfile){

						boolean deletingprofilerole = therapeuticAreaDeleting();
						if(deletingprofilerole){
							lookupProfile = lookupTherapeuticAreaDescription();
							if(lookupProfile == false)
								logPassMessageInExtentTest("testDeleteTherapeuticArea() - delete of therapeutic area description SUCCESSFUL ");
							else
								logFailMessageInExtentTest("testDeleteTherapeuticArea() - delete of therapeutic area description UNSUCCESSFUL ");
							
						}else{
							
							logFailMessageInExtentTest("testDeleteTherapeuticArea() - delete of therapeutic area description UNSUCCESSFUL ");
							
						}
					}
					
				}else{
					
					logFailMessageInExtentTest("testDeleteTherapeuticArea() - Creating of new therapeutic area description for deleting UNSUCCESSFUL ");
					logFailMessageInExtentTest("testDeleteTherapeuticArea() - delete of therapeutic area description UNSUCCESSFUL ");
					
				}
				driver = driver.switchTo().defaultContent();
				logInfoMessageInExtentTest("testDeleteTherapeuticArea() - user logout started ");
				apputil.logout();
			}else{
				
				//displayOnConsole("testDeleteTherapeuticArea() - User login UNSUCCESSFUL ");
				logFailMessageInExtentTest("testDeleteTherapeuticArea() - User login UNSUCCESSFUL, delete of therapeutic area description UNSUCCESSFUL ");
			
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("testCreateTherapeuticArea() - "+problem.fillInStackTrace());
		}
		

	}

	boolean therapeuticAreaDeleting() {
		
		boolean deleteprofilerole = true;
		
		try {
			
			//delete of therapeutic area description
			therapeuticareadescription = therapeuticareadescription.trim();
			tools_therapeutic_areas_text_delete = By.xpath("(//descendant::div[@id='highlightkeyword'][contains(text(),'"+ therapeuticareadescription +"')]/parent::td/preceding-sibling::td/parent::tr/td["+FIVE+"]/a["+ONE+"]/img)");
			tools_therapeutic_areas_text_checkbox = By.xpath("(//descendant::div[@id='highlightkeyword'][contains(text(),'"+ therapeuticareadescription +"')]/parent::td/preceding-sibling::td/descendant::input[@type='checkbox'])");
			click(tools_therapeutic_areas_text_checkbox);
			wait(ONE);
			click(tools_therapeutic_areas_text_delete);
			wait(ONE);
			
			//accept delete therapeutic area description alerts
			Alert alertbox = driver.switchTo().alert();
			String alertmessagetext = alertbox.getText();
			boolean textcontent = alertmessagetext.contains(deletetherapeuticareatext);
			if(textcontent){
				logInfoMessageInExtentTest(deletetherapeuticareatext + " message shown ");
			}
			
			//cancel delete
			alertbox.dismiss();
			wait(ONE);
			
			//again select therapeutic area row for delete
			click(tools_therapeutic_areas_text_checkbox);
			wait(ONE);
			click(tools_therapeutic_areas_text_delete);
			wait(ONE);

			alertbox.accept();
			wait(TWO);
			
			alertbox = driver.switchTo().alert();
			alertmessagetext = alertbox.getText();
			textcontent = alertmessagetext.contains(processingmessagetext);
			if(textcontent){
				logInfoMessageInExtentTest(processingmessagetext + " message shown ");
			}
			alertbox.accept();
			wait(TWO);
			
		} catch (Exception problem) {
			
			deleteprofilerole = false;
			logInfoMessageInExtentTest("Problem observed "+ problem.fillInStackTrace());
		}
		
		return deleteprofilerole;
		
	}
	
	@Test
	public void testEditTherapeuticArea(){
		
		try {
			//start of test
			extentTest = extent.startTest("testEditTherapeuticArea ");
			
			//edit therapeutic area test data
			sheet_name = "CreateTherapeuticArea";
			
			//application username, password
			if(username == null){
				
				username = getUserName(sheet_name);
				password = getUserPassword(sheet_name);
				
			}
			
			driver = driver.switchTo().window(driver.getWindowHandle());
			
			AppUtil apputil = new AppUtil();
			if(apputil.login(username, password)){
				
				//displayOnConsole("testEditTherapeuticArea() - user login SUCCESSFUL ");
				
				navigateToTherapeuticArea();
				
				//read therapeutic area description from test data
				therapeuticareadescription = Excel.readFromExcel(excel_file, sheet_name, SIX, ONE);

				boolean noerror = therapeuticAreaCreating(therapeuticareadescription);
				if(noerror == true){
					
					if(isElementPresent(driver, therapeutic_area_exists_description)){
						
						String details = "therapeutic area already exists, editing existing therapeutic area description ";
						//displayOnConsole("testTherapeuticAreaEditing - "+details);
						String screenshot = getScreenshot(driver, "Therapeutic_Area_Exists");
						Actions actions = new Actions(driver);
						Action action = actions.moveToElement(driver.findElement(therapeutic_area_exists_close),TWO,ZERO).click().build();
						action.perform();
						logInfoMessageInExtentTest("testEditTherapeuticArea() - "+details);
						
					} else {
						
						 logInfoMessageInExtentTest("testEditTherapeuticArea() - Creating of new therapeutic area description for editing SUCCESSFUL ");
						 
					}
					
					boolean lookupProfile = lookupTherapeuticAreaDescription();
					
					if(lookupProfile){

						boolean profileroleedit = therapeuticAreaEditing();
						
						if(profileroleedit){
							lookupProfile = lookupTherapeuticAreaDescription();
							if(lookupProfile == true)
								logPassMessageInExtentTest("testEditTherapeuticArea() - editing of therapeutic area description SUCCESSFUL ");
							else
								logFailMessageInExtentTest("testEditTherapeuticArea() - editing of therapeutic area description UNSUCCESSFUL ");
							
						}else{
							
							String details = "therapeutic area already exists, provide another therapeutic area description ";
							String screenshot = getScreenshot(driver, "Therapeutic_Area_Exists");
							Actions actions = new Actions(driver);
							Action action = actions.moveToElement(driver.findElement(therapeutic_area_exists_close),TWO,ZERO).click().build();
							action.perform();
							logInfoMessageInExtentTest("testEditTherapeuticArea() - "+details);							
							logFailMessageInExtentTest("testEditTherapeuticArea() - editing of therapeutic area description UNSUCCESSFUL ");
						}
					}
					
				} else {
					
					logFailMessageInExtentTest("testEditTherapeuticArea() - Creating of new therapeutic area description for editing UNSUCCESSFUL ");
					logFailMessageInExtentTest("testEditTherapeuticArea() - editing of therapeutic area description UNSUCCESSFUL ");
				}
				driver = driver.switchTo().defaultContent();
				logInfoMessageInExtentTest("testEditTherapeuticArea() - user logout started ");
				apputil.logout();
			}else{
				
				//displayOnConsole("testEditTherapeuticArea() - user login UNSUCCESSFUL ");
				logFailMessageInExtentTest("testEditTherapeuticArea() - User login UNSUCCESSFUL, editing of therapeutic area description UNSUCCESSFUL ");
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("testEditTherapeuticArea() - "+problem.fillInStackTrace());
		}
	}
	
	boolean therapeuticAreaEditing(){
		
		boolean therapeuticareaediting = true;
		
		try {
			
			//read therapeutic area description from test data
			String edittherapeuticareadescription = Excel.readFromExcel(excel_file, sheet_name, SEVEN, ONE);

			//edit therapeutic area description
			therapeuticareadescription = therapeuticareadescription.trim();
			tools_therapeutic_areas_text_edit = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+ therapeuticareadescription +"')]/parent::td/preceding-sibling::td/parent::tr//*[self::td]["+FIVE+"]/a["+TWO+"]/img)");
			click(tools_therapeutic_areas_text_edit);
			wait(ONE);
			
			//enter therapeutic area description
			click(therapeutic_area_new_box_input);
			driver.findElement(therapeutic_area_new_box_input).clear();
			enter(therapeutic_area_new_box_input, edittherapeuticareadescription);
			
			//click save
			click(therapeutic_area_new_box_save);
			wait(ONE);
			
			//accept edit therapeutic area description alerts
			Alert alertbox = driver.switchTo().alert();
			String alertmessagetext = alertbox.getText();
			
			boolean textcontent = alertmessagetext.contains(processingmessagetext);
			if(textcontent){
				
				logInfoMessageInExtentTest(processingmessagetext + " message shown ");
				
			}
			
			alertbox.accept();
			wait(TWO);
			
			therapeuticareadescription = edittherapeuticareadescription.trim();
			
		} catch (Exception problem) {

			therapeuticareaediting = false;
			logInfoMessageInExtentTest("Problem observed " + problem.fillInStackTrace());
			
		}
		
		return therapeuticareaediting;
	}
	
	@Test
	public void testMultipleTherapeuticAreasDelete()
	{
		try {
			//start of test
			extentTest = extent.startTest("testMultipleTherapeuticAreasDelete ");
			
			//multiple therapeutic area test data
			sheet_name = "CreateTherapeuticArea";
			
			//application username, password
			if(username == null)
			{
				username = getUserName(sheet_name);
				password = getUserPassword(sheet_name);
			}
			
			driver = driver.switchTo().window(driver.getWindowHandle());
			
			AppUtil apputil = new AppUtil();
			if(apputil.login(username, password)){
				
				navigateToTherapeuticArea();
				
				String therapeuticareaone = checkingTherapeuticAreaCreate(EIGHT,ONE);
				String therapeuticareatwo = checkingTherapeuticAreaCreate(NINE,ONE);
				String therapeuticareathree = checkingTherapeuticAreaCreate(TEN,ONE);
				
				String filter = String.valueOf(therapeuticareaone.charAt(ZERO)).toUpperCase();
				
				boolean selectvalue = selectByValue(driver, tools_therapeutic_areas_select, filter);
				wait(TWO);
				
				therapeuticareaone = therapeuticareaone.trim();
				therapeuticareatwo = therapeuticareatwo.trim();
				therapeuticareathree = therapeuticareathree.trim();
				
				//added 
				click(tools_delete_therapeutic_area_link);
				Alert alertbox = driver.switchTo().alert();
				String alertmessagetext = alertbox.getText();
				boolean textcontent = alertmessagetext.contains("No Therapeutic Areas are selected. Please select Therapeutic Areas to delete.");
				alertbox.accept();
				wait(ONE);
				
				checkTherapeuticAreaRow(therapeuticareaone);
				checkTherapeuticAreaRow(therapeuticareatwo);
				checkTherapeuticAreaRow(therapeuticareathree);
				
				//delete selected multiple therapeutic areas
				click(tools_delete_therapeutic_area_link);
				
				//accept delete therapeutic area description alerts
				alertbox = driver.switchTo().alert();
				alertmessagetext = alertbox.getText();
				textcontent = alertmessagetext.contains(deletemultipletherapeuticareatext);
				if(textcontent){
					logInfoMessageInExtentTest(deletemultipletherapeuticareatext + " message shown ");
				}
				alertbox.accept();
				wait(TWO);
				
				alertbox = driver.switchTo().alert();
				alertmessagetext = alertbox.getText();
				textcontent = alertmessagetext.contains(processingmessagetext);
				if(textcontent){
					logInfoMessageInExtentTest(processingmessagetext + " message shown ");
				}
				alertbox.accept();
				wait(TWO);
				
				logInfoMessageInExtentTest("testMultipleTherapeuticAreasDelete() - deleted multiple therapeutic areas ");
				driver = driver.switchTo().defaultContent();
				logInfoMessageInExtentTest("testMultipleTherapeuticAreasDelete() - user logout started ");
				
					apputil.logout();
			}else{
				
				//displayOnConsole("testMultipleTherapeuticAreasDelete() - User login UNSUCCESSFUL ");
				logFailMessageInExtentTest("testMultipleTherapeuticAreasDelete() - User login UNSUCCESSFUL, delete of multiple therapeutic areas description UNSUCCESSFUL ");
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("testMultipleTherapeuticAreasDelete() - "+problem.fillInStackTrace());
		}

	}

	void clickMoreTool() {
		Actions actions = new Actions(driver);
		Action action = actions.click(driver.findElement(more)).pause(ONE).moveToElement(driver.findElement(more_tool)).click().build();
		action.perform();
		//Actions actionsmore = new Actions(driver);
		//Action actionmore = actionsmore.moveToElement(driver.findElement(more_tool)).clickAndHold().pause(TWO).release().build();
		//actionmore.perform();
	}

	void checkTherapeuticAreaRow(String therapeuticareadescription) {
		
		boolean therapeuticarea = false;
		try {
			therapeutic_areas_text = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+therapeuticareadescription+"')])");
				
			String filter = String.valueOf(ZERO);
			List<WebElement> navigationoptions = driver.findElements(By.xpath("(//select[@id='bbox']//*[self::option])"));
			
			for(int pages = TWO; pages <= navigationoptions.size(); pages++){
				if((therapeuticarea = isElementPresent(driver, therapeutic_areas_text)) == true){
					break;
				}else{
					click(By.xpath("(//table[@id='ct']/thead/tr["+ONE+"]/td["+TWO+"]/a)"));
					therapeuticarea = isElementPresent(driver, therapeutic_areas_text);
					if(therapeuticarea == true)
						break;
				}
				filter = String.valueOf(pages);
				boolean selectvalue = selectByValue(driver, tools_therapeutic_areas_page_select, filter);
				wait(ONE);
			}
			
			//displayOnConsole("elements "+navigationoptions.size());
			if(navigationoptions.size() == ONE)
			{
				therapeuticarea = isElementPresent(driver, therapeutic_areas_text);
				
			}
			
			if(therapeuticarea == true){
				tools_therapeutic_areas_text_checkbox = By.xpath("(//*[@id='highlightkeyword'][contains(text(),'"+therapeuticareadescription+"')]/parent::td/preceding-sibling::td/descendant::input[@type='checkbox'])");
				click(tools_therapeutic_areas_text_checkbox);
				wait(ONE);
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("problem  " + problem.fillInStackTrace());
		}
	}

	String checkingTherapeuticAreaCreate(int datarow, int datacolumn) {
		
		String []noerror = therapeuticAreaCreating(datarow,datacolumn);
		
		try {
			if(noerror[ZERO].contains("true")){
				
				if(isElementPresent(driver, therapeutic_area_exists_description)){

					String details = "therapeutic area already exists, deleting existing therapeutic area description ";
					//displayOnConsole("testDeleteMultipleTherapeuticAreas() -  "+details);
					String screenshot = getScreenshot(driver, "Therapeutic_Area_Exists");
					Actions actions = new Actions(driver);
					Action action = actions.moveToElement(driver.findElement(therapeutic_area_exists_close),TWO,ZERO).click().build();
					action.perform();
					logInfoMessageInExtentTest("testMultipleTherapeuticAreasDelete() - "+details);
									
				}else{
					
					logPassMessageInExtentTest("testMultipleTherapeuticAreasDelete() - Creating of new therapeutic area description for deleting SUCCESSFUL ");
				}

			}else{
				logFailMessageInExtentTest("testMultipleTherapeuticAreasDelete() - Creating of new therapeutic area description for deleting UNSUCCESSFUL ");
			}
		} catch (Exception problem) {
			logFailMessageInExtentTest("problem  " + problem.fillInStackTrace());
		}
		
		return noerror[ONE];
	}

	String[] therapeuticAreaCreating(int datarow, int datacolumn) {
		String noerror[] = {"true",""};
	
		try {

			//displayOnConsole("Adding new therapeutic area ");
						
			//read therapeutic area description from test data
			therapeuticareadescription = Excel.readFromExcel(excel_file, sheet_name, datarow, datacolumn);
			noerror[ONE] = therapeuticareadescription;
			
			//displayOnConsole(sheet_name + " " + therapeuticareadescription + " description ");
			
			//click new therapeutic area
			click(tools_new_therapeutic_area_link);
			wait(ONE);
			
			//enter therapeutic area description
			click(therapeutic_area_new_box_input);
			driver.findElement(therapeutic_area_new_box_input).clear();
			enter(therapeutic_area_new_box_input, therapeuticareadescription);
			
			//click save
			click(therapeutic_area_new_box_save);
			wait(TWO);
			
		} catch (Exception problem) {
			noerror[ZERO]="false";
			logInfoMessageInExtentTest("Problem observed "+ problem.fillInStackTrace());
		}
		
		return noerror;
	}
}