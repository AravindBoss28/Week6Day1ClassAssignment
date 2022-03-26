package steps;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestLeafApp {
	static WebDriver driver;
	static String leadfirstName;
    static Map<String, String> addEmpId = new HashMap<String, String>();
    
	@Given("chrome browser is available and loaded with testleaf url")
	public void openBrowserAndLoadUrl() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("http://leaftaps.com/opentaps/control/main");
	}

	@When("user login with Demosalesmanager and password")
	public void user_login_with_demosalesmanager_and_password() {
		driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");	
		driver.findElement(By.className("decorativeSubmit")).click();
	}

	@When("user clicks on CRM tab")
	public void user_clicks_on_crm_tab() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(driver);
		driver.findElement(By.linkText("CRM/SFA")).click();
	}

	@Then("application navigates to home page")
	public void verifyHomepage() {
		String s = driver.getTitle();	
		Assert.assertEquals(true, s.contains("My Home | opentaps CRM"));	
	}

	@When("user clicks on {string} link")
	public void clickCreatLead(String s) {
		if(s.equals("Delete")) {
			driver.findElement(By.className("subMenuButtonDangerous")).click();
		} else {
			driver.findElement(By.linkText(s)).click();	
		}
		
	}

	@Then("application is in create lead page")
	public void verifyLeadpage() {
		String s = driver.getTitle();	
		Assert.assertEquals(true, s.contains("Create Lead | opentaps CRM"));
	}


	@Given("user is in Home Page")
	public void user_is_in_Home_page() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.get("http://leaftaps.com/opentaps/control/main");
		driver.findElement(By.id("username")).sendKeys("DemoSalesManager");
		driver.findElement(By.id("password")).sendKeys("crmsfa");	
		driver.findElement(By.className("decorativeSubmit")).click();
		driver.findElement(By.linkText("CRM/SFA")).click();
	}

	@When("user enters {string} {string} {string}")
	public void user_enter(String companyName, String firstName, String lastName) {
		driver.findElement(By.id("createLeadForm_companyName")).sendKeys(companyName);
		driver.findElement(By.id("createLeadForm_firstName")).sendKeys(firstName);
		driver.findElement(By.id("createLeadForm_lastName")).sendKeys(lastName);
		driver.findElement(By.name("departmentName")).sendKeys("Automation Testing");
		driver.findElement(By.name("description")).sendKeys("hello entering description details");
		driver.findElement(By.id("createLeadForm_primaryEmail")).sendKeys("arboss@gmail.com");
		new Select(driver.findElement(By.name("generalStateProvinceGeoId"))).selectByVisibleText("New York");	
		leadfirstName = firstName;

		
	}

	@When("users click on the save button")
	public void users_click_on_the_save_button() {
		driver.findElement(By.className("smallSubmit")).click();
		String id = driver.findElement(By.id("viewLead_companyName_sp")).getText();
		String[] s2 = id.split(" "); 
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(s2[1]);
        String empID = "";
        while(m.find()) {
        	empID = m.group(); 
        	addEmpId.put(leadfirstName, empID);
        }
     
	}

	@Then("created leads are available in application {string}")
	public void created_leads_are_available_in_application(String companyName) {
		String id = driver.findElement(By.id("viewLead_companyName_sp")).getText();
		System.out.println(id.contains(leadfirstName));
		Assert.assertEquals(id.contains(companyName), true);
	}
     
	@When("user searches with {string} and Lead ID")
	public void user_searches_with_and_lead_id(String firstName) {
		
		String xpathforLeadId = "//div[@class='x-panel-bwrap']//label[text()='Lead ID:']/following::input[@name='id']";
		String empid =  addEmpId.get(firstName);
		driver.findElement(By.xpath(xpathforLeadId)).sendKeys(empid);
		driver.findElement(By.xpath("//button[text()='Find Leads']")).click();
	}

	@When("user selects the first option")
	public void user_selects_the_first_option() {
	    // Write code here that turns the phrase above into concrete actions
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.xpath("//table/tbody/tr[1]/td[1]/div/a")).click();
	}

	@When("user modifies the details")
	public void user_modifies_the_details() {
		driver.findElement(By.name("departmentName")).sendKeys("Automation Testing");
		driver.findElement(By.name("description")).clear();
		driver.findElement(By.name("description")).sendKeys("Aravind124 modify details");
		driver.findElement(By.id("createLeadForm_primaryEmail")).clear();
		driver.findElement(By.id("createLeadForm_primaryEmail")).sendKeys("artestboss@gmail.com");
		String dup = "Duplicate Lead | opentaps CRM";
	}

	@Then("modified data is available in application for {string}")
	public void modified_data_is_available_in_application(String FirstName) {
	    Assert.assertEquals(addEmpId.containsKey(FirstName), true);
	}
}
