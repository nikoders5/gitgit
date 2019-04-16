package com.profiler.scripts;

import com.profiler.utils.Excel;
import com.profiler.utils.RoleManagerUtil;
import com.relevantcodes.extentreports.LogStatus;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserManager extends RoleManagerUtil {
	
	String excel_file = null;
	String sheet_name = null;
	String newUser = null;
	String newRole = null;
	String username = null, password = null;
		
	
	@Test 
	public void testCreateUser()	{
		
		extentTest = extent.startTest("Create User");		
		excel_file = "files//TestData.xlsx";
		sheet_name = "Application";

		username = Excel.readFromExcel(excel_file, sheet_name, 2, 1); 
		password = Excel.readFromExcel(excel_file, sheet_name, 3, 1); 
	
		if ( login(username, password) )	{
			
			if( gotoToolsPage() )	{
				
				if ( (newUser = createUser())!= null )	
					extentTest.log(LogStatus.INFO, "User Created - " +newUser);
				else {
					Assert.fail("Tools page not opened");
					getScreenshot(driver, "testCreateUser_ToolsPageNotOpened");
				}
				
			} else {
				Assert.fail("Tools page not opened");
				getScreenshot(driver, "testCreateUser_ToolsPageNotOpened");
			}
		 		
			wait(2);
			driver.switchTo().defaultContent();
			logout();
			
		} else	{
			Assert.fail("Login UNSUCCESSFUL");
			getScreenshot(driver, "testCreateUser_LoginFailed");
		}
	}
	
	
	@Test (dependsOnMethods= {"testCreateUser"})
	public void testSearchUser()	{
		
		extentTest = extent.startTest("Search User");		
		excel_file = "files//TestData.xlsx";
		sheet_name = "Application";

		username = Excel.readFromExcel(excel_file, sheet_name, 2, 1); 
		password = Excel.readFromExcel(excel_file, sheet_name, 3, 1); 
					
		login(username, password);
		gotoToolsPage();
		
		
		if( ! searchUserFromToolsPage(newUser) )		{			
			Assert.fail("Search user failed");
			getScreenshot(driver, "testSearchUser_SearchUserFailed");
		}
		
		wait(2);
		driver.switchTo().defaultContent();
		logout();				
	}
	
	
	@Test (dependsOnMethods= {"testSearchUser"})
	public void testEditUser()	{
		
		extentTest = extent.startTest("Edit User");		
		excel_file = "files//TestData.xlsx";
		sheet_name = "Application";

		username = Excel.readFromExcel(excel_file, sheet_name, 2, 1); 
		password = Excel.readFromExcel(excel_file, sheet_name, 3, 1);
		
		String fname_new	= "fname_updated";
		By fname_field 		= By.id("firstName");
		By username_link 	= By.xpath("//a[@title='"  +newUser  +"']");
		By save_button 		= By.xpath("//input[@alt='Save']");
		By fname_updated_link = By.xpath("//div[contains(text(),'" +fname_new +"')]");
							
		login(username, password);
		gotoToolsPage();
		searchUserFromToolsPage(newUser);
		click(username_link); 
		wait(2);
		
		driver.findElement(fname_field).clear();
		enter(fname_field, fname_new);
		click(save_button);
		wait(13);
		
		try {
			driver.switchTo().alert().accept();
		}catch(NoAlertPresentException e)	{
			extentTest.log(LogStatus.INFO, "Your request is in queue. alert not found.");
		}
		
		driver.switchTo().defaultContent();
		gotoToolsPage();
		searchUserFromToolsPage(newUser);
		
		if(! isElementPresent(driver, fname_updated_link))	{
			Assert.fail("Edit user failed");
			getScreenshot(driver, "testEditUser_EditUserFailed");
		}
		
		wait(2);
		driver.switchTo().defaultContent();
		logout();	
	}
	
	
	@Test (dependsOnMethods= {"testEditUser"})
	public void testDeleteUser()	{
		
		extentTest = extent.startTest("Delete User");		
		excel_file = "files//TestData.xlsx";
		sheet_name = "Application";

		username = Excel.readFromExcel(excel_file, sheet_name, 2, 1); 
		password = Excel.readFromExcel(excel_file, sheet_name, 3, 1);
		
		By select_user_checkbox = By.xpath("//input[@type='checkbox' and @name='selectedItems']");
		By delete_button 		= By.xpath("//span[text()='Delete']");
		By username_link 	= By.xpath("//a[@title='"  +newUser  +"']");
		
		login(username, password);
		gotoToolsPage();
		
		if( searchUserFromToolsPage(newUser) && driver.findElements(select_user_checkbox).size()==1 )	{
			
			click(select_user_checkbox);
			click(delete_button);	
			
			try {
				driver.switchTo().alert().accept();
				wait(30);
				driver.switchTo().alert().accept();
				
			}catch(NoAlertPresentException e)	{
				extentTest.log(LogStatus.INFO, "Your request is in queue. alert not found.");
			}
			
			if(isElementPresent(driver, username_link))	{
				Assert.fail("Delete user failed. User could not be deleted.");
				getScreenshot(driver, "testDeleteUser_DeleteUserFailed");
			}
		
		} else {
			Assert.fail("Delete user failed or Multiple users listed for deletion");
			getScreenshot(driver, "testDeleteUser_DeleteUserFailed");
		}
		
		wait(2);
		driver.switchTo().defaultContent();
		logout();
	}		
}
