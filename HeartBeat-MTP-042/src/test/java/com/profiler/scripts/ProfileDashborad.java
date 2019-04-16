package com.profiler.scripts;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.profiler.utils.AppUtil;
import com.profiler.utils.Excel;
import com.profiler.utils.Functions;

public class ProfileDashborad extends Functions {

	
	// Data :-
		String excel_file = "files//TestData.xlsx";
		String sheet_name = "Application";
		String sheet_name2 = "SearchProfile";
		
		String url = Excel.readFromExcel(excel_file, sheet_name, 1, 1); 
		String username = Excel.readFromExcel(excel_file, sheet_name, 2, 1); 
		String password = Excel.readFromExcel(excel_file, sheet_name, 3, 1); 

		String ProfilesSearch = Excel.readFromExcel(excel_file,sheet_name2, 2,1);

		// Locators :-	
		By username_field = By.xpath("//*[@value='Username']");
		By password_field = By.xpath("//*[@class='defText-fakePassword']");
		By signin_button = By.xpath("//*[@alt='Sign In']");
		By username_homepage = By.xpath("//*[@id='linksPopupUsermenu']/div[1]/div[1]");
		By profile_tab = By.xpath("//a[@id='section_profile']");
		By SearchProfiles = By.xpath("//input[@id='searchall']");
		By SearchButton = By.xpath("//span[@id='search_list_btn']");

		By profileLink = By.xpath("//*[contains(text(),'"+ProfilesSearch  +"')]");
		By DashboardTab = By.xpath("//a[@title='Dashboard']");
		AppUtil appUtil = new AppUtil();
		
		
		// Locators :-	
		String BrandElement = Excel.readFromExcel(excel_file,sheet_name2, 1,1);
		By RefineBy = By.xpath("//div[@class='marketAccess-filter-current'][contains(text(),'Brand')]");
		By RefineByElement = By.xpath("//input[@value='ACTIQ']");
		
		By profilelist=By.xpath("//div[contains(text(),'" +BrandElement     +"')]");	
		By clearAll_button = By.id("refine_clear_all");
	
	
	@Test

	public void profileDashboard() {

		extentTest = extent.startTest("profileDashboard");
		getUrl(url);
		enter(username_field, username);
		wait(1);
		enter(password_field, password);
		wait(1);
		click(signin_button);
		wait(5);
		click(profile_tab);
		wait(5);
		
		click(clearAll_button);
		wait(2);
		
		click(SearchProfiles);
		wait(5);
		enter(SearchProfiles, ProfilesSearch);
		wait(5);
		click(SearchButton);
		wait(5);
		click(profileLink);
		wait(5);
		click(DashboardTab);

		appUtil.logout();

	}
		
	@Test

	public void profilesTab() {

		extentTest = extent.startTest("profilesTab");
		getUrl(url);
		enter(username_field, username);
		wait(1);
		enter(password_field, password);
		wait(1);
		click(signin_button);
		wait(5);
		click(profile_tab);
		wait(5);
		
		click(clearAll_button);
		wait(2);
		
		click(RefineBy);
		wait(5);

		WebElement checkbox = driver.findElement(RefineByElement);
		if (!checkbox.isSelected()) {
			checkbox.click();
		}

		wait(5);
		click(RefineBy);
		wait(5);

		int profilecount = driver.findElements(profilelist).size();
		System.out.println("profilecount----" + profilecount);

		for (int i = 0; i < profilecount; i++) {
			String actual = driver.findElements(profilelist).get(i).getText();

			if (actual.contains(BrandElement))
				Assert.assertEquals(true, true);
			else
				Assert.assertEquals(true, false);

		}
		
		appUtil.logout();
	}
				
		
}
