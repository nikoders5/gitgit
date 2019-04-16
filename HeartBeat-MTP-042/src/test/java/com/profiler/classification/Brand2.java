package com.profiler.classification;

import static org.testng.Assert.assertTrue;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.profiler.utils.AppUtil;
import com.profiler.utils.Excel;
import com.profiler.utils.Functions;

public class Brand2 extends Functions {

	// Locators :-
	By tools_arrow = By.xpath("//div[@id='navWrapper']/ul/li[@class='moreMenuParent']");
	By tools_field = By.xpath("//a[contains(text(),'Tools')]");
	By alltools_field = By.xpath(
			"//div[@id='treeFrameDiv']/div/div[@id='contentDiv']/table/tbody/tr/td/a[contains(text(),'All Tools')]");
	By brand = By.xpath(
			"//table/tbody/tr/td[1]/table/tbody/tr[2]/td/div/table[2]/tbody/tr/td[2]/table/tbody/tr[2]/td/table/tbody/tr[2]/td/a[1]");
	By new_brand = By.xpath("//*[@id='menu']/li/a/span");
	By brand_name = By.xpath("//*[@id='description']");
	By save_button = By.xpath("//img[@alt='Save']");
	By select_brand = By.xpath("//div[contains(text(),'aaa')]");
	By delete_button = By.xpath("//*[@id='menu']/li/a/span/following::span");
	By profile = By.xpath("//*[@id='section_profile']");
	By search_results1 = By.xpath("//*[@id='resultsetFacetedSearchTableBody']/tr[1]/td[3]/h3/a");
	By personal_info = By.xpath("//*[@id='boxProfileOverview']/div[2]/div[3]/div/h4/a");
	By actions = By.xpath("//*[@id='actions']");
	By actionedit = By.xpath("//*[@id='linksPopupActions']/div[1]/div[2]/ul/li[1]/a");
	By new_classification = By.xpath("//*[contains(@id,'header_javascript:newClassification')]");
	By select_brand1 = By.xpath("//*[@id='classification1_dd_label']");
	By verifybrand = By.xpath("//div[contains(text(),'aaa')]");
	By verifybrand1 = By.xpath("//label[contains(text(),'aaa')]");
	By closebuton = By.xpath("//*[@id='POPUP_content']/div/div[1]/div/a/img");
	By cancel = By.xpath("//*[@id='cancel']");
	By createnew = By.xpath("//*[@id='popup_create_new']");
	By createprofile = By.xpath("//*[@id='global_create_new_profile']");
	By firstname = By.xpath("//*[@id='firstName']");
	By lastname = By.xpath("//*[@id='lastName']");
	By next = By.xpath("//*[@id='profile_next']");
	By nextlookup = By.xpath("//*[@id='profile_lookup_next']");
	By selecttemplate = By.xpath("//*[@id='newProfileTemplateId_dd_div']/input");
	By templatename = By.xpath("//a[@class='dropdownOpt'][1]");
	By select_brand2 = By.xpath("//*[@id='classification1_dd_label']");
	By closebutton = By.xpath("//*[@id='POPUP_content']/div/div[1]/div/a/img");
	By select_brand3 = By.xpath("//div[text()='aaa']/parent::td/parent::tr//input");
	By delete_brand = By.xpath("//*[@id='menuTable']/tbody/tr/td[2]");
	By select_brandlist = By.xpath("//*[@id='classification1_dd_options']/ul/li");
	By select_brandval = By.xpath("//*[@id='classification1_dd_grid']/li[2]/label");
	By select_profile = By.xpath("//*[@id='classification3_dd_label']");
	By select_profileval = By.xpath("//*[@id='classification3_dd_grid']/li[2]/label");
	By select_project = By.xpath("//*[@id='classification5_dd_label']");
	By select_projectval = By.xpath("//*[@id='classification5_dd_grid']/li[2]/label");
	By select_region = By.xpath("//*[@id='classification2_dd_label']");
	By select_regionval = By.xpath("//*[@id='classification2_dd_grid']/li[2]/label");
	By select_Therapeutic = By.xpath("//*[@id='classification4_dd_label']");
	By select_Therapeuticval = By.xpath("//*[@id='classification4_dd_grid']/li[2]/label");
	By save = By.xpath("//*[@id='save_classification']");
	By save1 = By.xpath("//*[@id='save']");
	// By deactivate = By.xpath("//*[@class='ico-deactivate']/parent::div[contains(text(),'aaa')]");
	//By deactivate = By.xpath("//*[@id='highlightkeyword']/img");
	By deactivate = By.xpath("//*[@id='classificationDiv']/table/tbody/tr/td[2]/following::div[@id='highlightkeyword']/following::img)[1]");
	By del_classification = By.xpath("//*[@class='ico ico-delete']");
	By no_results = By.xpath("//*[@id='classificationDiv']/table/thead/tr/td");
	By eleclick = By.xpath("//*[@id='POPUP_content']/div/div[1]/h3");
	By verifybrand2 = By.xpath("//*[@id='cu_classificationDiv']/th[1]/div/following::div[contains(text(),'aaa')]");

	// Data :-
	String excel_file = "files//TestData.xlsx";
	String sheet_name = "AddBrand";
	String actualbrand = Excel.readFromExcel(excel_file, sheet_name, 1, 1);
	String expmessage = Excel.readFromExcel(excel_file, sheet_name, 2, 1);
	String noresultsmes = Excel.readFromExcel(excel_file, sheet_name, 3, 1);

	AppUtil appUtil = new AppUtil();

	// Add Brand
	@Test(priority = 1)
	public void addBrand() throws InterruptedException {

		extentTest = extent.startTest("add Brand ");

		String username = Excel.readFromExcel(excel_file, "Application", 2, 1);
		String password = Excel.readFromExcel(excel_file, "Application", 3, 1);

		if (!appUtil.login(username, password)) {
			Assert.fail("User login failed.");
		}

		click(tools_arrow);
		wait(2);
		click(tools_field);
		driver.switchTo().frame("toolsContent");
		wait(5);
		click(brand);
		wait(2);
		click(new_brand);
		wait(2);
		enter(brand_name, actualbrand);
		wait(2);
		click(save_button);
		wait(2);
		String expected = driver.findElement(verifybrand).getText();

		try {
			if (actualbrand.equalsIgnoreCase(expected)) {
				System.out.println("Actual & expected Brand name matched on Brand Page!");
			} else {
				Assert.fail("Actual & expected Brand name NOT matched not Brand page!");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}
	}

	// Added Classification using new brand
	@Test(priority = 2)
	public void verifyBrand() throws InterruptedException {

		extentTest = extent.startTest("verify Brand ");
		driver.switchTo().defaultContent();
		click(profile);
		wait(2);
		click(search_results1);
		wait(2);
		click(personal_info);
		wait(2);
		click(actions);
		wait(2);
		click(actionedit);
		wait(2);
		click(new_classification);
		wait(2);
		WebElement ele = driver.findElement(eleclick);

		Actions builder = new Actions(driver);
		Action mouseOverHome = builder.moveToElement(ele).click().build();

		wait(5);
		click(select_brand1);
		wait(2);
		click(select_brandval);
		mouseOverHome.perform();
		wait(2);
		click(select_profile);
		wait(2);
		click(select_profileval);
		mouseOverHome.perform();
		wait(2);
		click(select_project);
		wait(2);
		click(select_projectval);
		mouseOverHome.perform();
		wait(2);
		click(select_region);
		wait(2);
		click(select_regionval);
		mouseOverHome.perform();
		wait(2);
		click(select_Therapeutic);
		wait(2);
		click(select_Therapeuticval);
		mouseOverHome.perform();
		wait(2);
		click(save);
		wait(5);
		click(save1);
		wait(5);

		try {
			if (actualbrand.equalsIgnoreCase(driver.findElement(verifybrand2).getText())) {
				System.out.println("Actual & expected Brand name matched in New Classification section!");
			} else {
				Assert.fail("Actual & expected Brand name NOT matched in New Classification section!");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}
	}

	// Verify that add brand exists on the Profile Template List page
	@Test(priority = 3)
	public void verifyBrndProfile() throws InterruptedException {

		// click(cancel);
		extentTest = extent.startTest("verify brand profile ");
		wait(5);
		click(createnew);
		wait(2);
		click(createprofile);
		wait(2);
		enter(firstname, "sudhanshu");
		wait(2);
		enter(lastname, "sharma");
		wait(2);
		click(next);
		wait(2);
		click(nextlookup);
		wait(2);
		click(selecttemplate);
		wait(2);
		click(templatename);
		wait(2);
		click(select_brand2);

		// To verify if the actual brand added exists in the Classification section
		String expected1 = driver.findElement(verifybrand1).getText();

		try {
			if (actualbrand.equalsIgnoreCase(expected1)) {
				System.out.println("Actual & expected Brand name matched in Profile Template List page!");
			} else {
				Assert.fail("Actual & expected Brand name NOT matched in Profile Template List page!");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		} finally {
			wait(2);
			click(closebuton);
		}
	}

	// Verify that add brand is deleted successfully or not
	@Test(priority = 4)
	public void deleteBrand() throws InterruptedException {

		extentTest = extent.startTest("delete Brand ");
		click(tools_arrow);
		wait(2);
		click(tools_field);
		driver.switchTo().frame("toolsContent");
		wait(5);
		click(brand);
		wait(2);
		click(select_brand3);
		wait(2);
		click(delete_brand);

		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert1 = driver.switchTo().alert();
		alert1.accept();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert2 = driver.switchTo().alert();
		String actmessage = alert2.getText();
		alert2.accept();

		// To verify if the delete message displayed is correct or not

		try {
			if (expmessage.equalsIgnoreCase(actmessage)) {
				System.out.println("Brand deleted successfully!");
			} else {
				Assert.fail("Brand not deleted!");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}
	}

	// Verify that Added Brand not showing on New Classification page
 /* debug pending*/
	@Test(priority = 5)
	
	
	public void verifyBrandDelFromClassification() throws InterruptedException {

		extentTest = extent.startTest("verify brand deletion from classification");
		driver.switchTo().defaultContent();
		click(profile);
		wait(2);
		click(search_results1);
		wait(2);
		click(personal_info);
		wait(2);
		click(actions);
		wait(2);
		click(actionedit);
		wait(2);
		WebElement deactivate1 = driver.findElement(deactivate);
		String str = deactivate1.getText();
		System.out.println("String value: " + str);

		if (deactivate1.isDisplayed()) {
			System.out.println("Status changed to deactivated. Teststep passed!");
			// To verify if the record got deleted successfully

			try {
				click(del_classification);
				wait.until(ExpectedConditions.alertIsPresent());
				Alert alert1 = driver.switchTo().alert();
				alert1.accept();
				wait(5);
				String actualmessage = driver.findElement(no_results).getText();
				if (noresultsmes.equalsIgnoreCase(actualmessage)) {
					System.out.println("Record deleted successfully!");
				} else {
					Assert.fail("Record not deleted!");
				}
			} catch (Exception e) {
				Assert.fail("Exception occured", e);
			}
		} else {
			System.out.println("Status not changed to deactivated. Teststep failed!");
		}

		click(new_classification);
		wait(2);
		click(select_brand1);

		// To verify if the actual brand added not showing in the list

		WebElement select = driver.findElement(select_brand1);
		List<WebElement> options = select.findElements(By.xpath("//label[contains(text(),'aaa')]"));
		wait(2);

		try {
			if (options.size() == 0) {
				System.out.println("Added Brand not showing on New Classification page now. Deletion Success!");
			} else {
				System.out.println("Added Brand still showing on New Classification page now. Deletion Unsuccessful!");
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		} finally {
			wait(2);
			click(closebuton);
		}
	}

	// Verify that Added Brand not showing on Profile Template page
	@Test(priority = 6)
	public void verifyBrandDelFromProfile() throws InterruptedException {

		extentTest = extent.startTest("verify brand deletion from profile");
		click(cancel);
		wait(5);
		click(createnew);
		wait(2);
		click(createprofile);
		wait(5);
		enter(firstname, "sudhanshu");
		wait(2);
		enter(lastname, "sharma");
		wait(5);
		click(next);
		wait(2);
		click(nextlookup);
		wait(2);
		click(selecttemplate);
		wait(2);
		click(templatename);
		wait(2);
		click(select_brand2);
		wait(2);

		// To verify if the actual brand added not showing in the list
		WebElement select = driver.findElement(select_brand2);
		List<WebElement> options = select.findElements(By.xpath("//label[contains(text(),'aaa')]"));
		wait(2);

		try {
			if (options.size() == 0) {
				System.out.println("Added Brand not showing on Profile Template list page. Deletion Success!");

				click(closebuton);
			} else {
				System.out.println("Added Brand still showing on Profile Template List page. Test case failed!");
				click(closebuton);
			}
		} catch (Exception e) {
			Assert.fail("Exception occured", e);
		}

		appUtil.logout();
	}
}
