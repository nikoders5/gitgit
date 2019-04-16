package com.profiler.scripts;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.profiler.utils.AppUtil;
import com.profiler.utils.Excel;
import com.profiler.utils.Functions;

public class AddEducationToProfile extends Functions {

	// Data :-
	String excel_file = "files//TestData.xlsx";
	String sheet_name = "ProfileEducation";

	String url = Excel.readFromExcel(excel_file, sheet_name, 1, 1);
	String username = Excel.readFromExcel(excel_file, sheet_name, 2, 1);
	String password = Excel.readFromExcel(excel_file, sheet_name, 3, 1);
	String search_profile_value = Excel.readFromExcel(excel_file, sheet_name, 4, 1);

	String instNameValue = null;
	String addedInstname_value = null;
	String viewPageInstname_value = null;

	// Locators :-
	By username_field = By.xpath("//*[@value='Username']");
	By password_field = By.xpath("//*[@class='defText-fakePassword']");
	By signin_button = By.xpath("//*[@alt='Sign In']");
	By username_homepage = By.xpath("//*[@id='linksPopupUsermenu']/div[1]/div[1]");
	By profile_tab = By.xpath("//a[@id='section_profile']");
	By search_profile_field = By.xpath("//input[@id='searchall']");
	By search_button = By.xpath("//span[@id='search_list_btn']");
	By profile_result_name_link = By
			.xpath("//table[@class='grid tblProfiles profileThumbviewTable']//a[contains(text(),'"
					+ search_profile_value + "')]");

	By personal_info_btn = By.xpath("//a[@title='Personal Info']");
	By actions_btn = By.xpath("//span[@id='actions']");
	By edit_link = By.xpath("//a[@title='Edit']");

	By new_btn = By.xpath("//span[text()='New']");
	By search_image = By.xpath("//img[@src='/profiler/_assets/newimages/search.gif']");

	By type_search = By.xpath("//div[@id='companyType_dd_div']//input[@class='dropdown dd-all']");
	By association_name = By.xpath("//a[@name='67']");
	By search_edu_btn = By.xpath("//span[@id='search_education']");
	By institution_name = By.xpath("//table[@class='grid resizeTable tblProfiles']/tbody/tr[1]/td[1]/span[1]/input[1]");
	By institution_name_value = By.xpath("//div[@id='profilerPopupDiv1']//tbody//tr[1]//td[2]//div[1]");
	By select_btn = By.xpath("//span[@id='select_education']");
	By institution_type_dd = By.xpath("//div[@id='instituteType_dd_div']//input[@class='dropdown dd-all']");
	By institution_type_value = By.xpath("//a[@name='110']");
	By save_btn = By.xpath("//span[@id='save_education']");
	By addedInstname_loc = By.xpath("//table[@class='grid resizeTable tblPublications']/tbody/tr[1]/td[2]/div");

	By finalsave_edit = By.xpath("//span[@id='save']");
	By viewPageInstname_loc = By.xpath("//table[@class='grid resizeTable tblProfiles']/tbody/tr[1]/td[1]/div");

	@Test
	public void addProfileEducation() {

		extentTest = extent.startTest("Add Profile Education ");
		AppUtil appUtil = new AppUtil();
/*		getUrl(url);
		wait(5);
		enter(username_field, username);
		wait(1);
		enter(password_field, password);
		wait(1);
		click(signin_button);
		wait(5);*/
		
		if ( appUtil.login(username, password) )	
			System.out.println("User Login SUCCESSFUL");
		 else
			Assert.fail("addProfileEducation() - User Login UNSUCCESSFUL");
		
		// System.out.println(driver.getTitle());
		// wait(5);
		click(profile_tab);
		wait(3);
		enter(search_profile_field, search_profile_value);
		click(search_button);
		wait(4);
		/*
		 * click(personal_info_field); wait(5);
		 */
		click(profile_result_name_link);
		wait(3);
		click(personal_info_btn);
		wait(3);
		click(actions_btn);
		click(edit_link);
		wait(3);
		click(new_btn);
		wait(2);
		click(search_image);
		wait(4);
		click(type_search);
		wait(3);
		click(association_name);
		click(search_edu_btn);
		wait(2);
		click(institution_name);
		instNameValue = getText(institution_name_value);
		click(select_btn);
		click(institution_type_dd);
		click(institution_type_value);
		click(save_btn);
		wait(3);

		addedInstname_value = getText(addedInstname_loc);

		try {
			if (instNameValue.contentEquals(addedInstname_value)) {
				System.out.println("Selected Education details added successfully");
			} else {
				Assert.fail("Selected Education details are not added");
			}
		} catch (Exception e) {
			Assert.fail("Exception occurred", e);
		}

		click(finalsave_edit);
		wait(2);
		viewPageInstname_value = getText(viewPageInstname_loc);
		try {
			if (instNameValue.contentEquals(viewPageInstname_value)) {
				System.out.println("Details are displayed at Education Section");
			} else {
				Assert.fail("Details are displayed at Education Section");
			}
		} catch (Exception e) {
			Assert.fail("Exception occurred", e);
		}
		appUtil.logout();
	}
}
