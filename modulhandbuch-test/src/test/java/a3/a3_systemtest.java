package a3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;


public class a3_systemtest {
	private WebDriver driver;
	WebDriverWait wait;

	@BeforeTest
	public void openWebsite() {

		boolean localTest = true;
		if (!localTest) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("disable-gpu");
			driver = new ChromeDriver(chromeOptions);
			driver.get("https://85.214.225.164/dev/home");
		} else {
			driver = new ChromeDriver();
			driver.get("https://85.214.225.164/dev/home");
			driver.manage().window().maximize();
			driver.findElement(By.id("details-button")).click();
			driver.findElement(By.id("proceed-link")).click();
		}
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-hamburger")));
			driver.findElement(By.id("btn-hamburger")).click();
			wait.until(ExpectedConditions.elementToBeClickable(By.id("a-user-management")));
			driver.findElement(By.id("a-user-management")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFormular() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Mitarbeiter anlegen']")));
			driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Button to open the form does not work");
			e.printStackTrace();
		}
	}

	public Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection(
			"jdbc:mariadb://85.214.225.164:3306/swepr_test",
			"read_only_user_local_host", "car_tree_moon"
		);
		return connection;
	}

    @Test
	public void S_D_A1T01() {
		boolean result = true;
		openFormular();

		Date date = new Date();
		String d = date.toString().replaceAll(" ", "_").replaceAll(":", "_");

		String testMail = "Test_Mail_" + d + "@test.de";
		String testFirstName = "Test_First_Name_" + d;
		String testLastName = "Test_Last_Name_" + d;

		Select gender = new Select(driver.findElement(By.xpath("//*[@id='select-create-employee-gender']")));
		gender.selectByVisibleText("Herr");

		driver.findElement(By.xpath("//*[@id='input-first-name']")).sendKeys(testFirstName);
		driver.findElement(By.xpath("//*[@id='input-last-name']")).sendKeys(testLastName);

		driver.findElement(By.xpath("//*[@id='input-email']")).sendKeys(testMail);

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/div[2]/div")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/div[4]/div[2]/ul/p-multiselectitem[1]/li/span")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/div[4]/div[1]/button/span")).click();

		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();
		
		try {
			driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-college-employees/div/p-dataview/div/p-paginator/div")).click();
		} catch(ElementClickInterceptedException e) {
			
		}
		
		try {
			driver.findElement(By.name("Prof. " + testFirstName + " " + testLastName));
			driver.findElement(By.name(testMail));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_D_A1T02() {
		boolean result = true;
		openFormular();

		Date date = new Date();
		String d = date.toString().replaceAll(" ", "_").replaceAll(":", "_");

		String testMail = "Test_Mail_" + d + "@test.de";
		String testFirstName = "Test_First_Name_" + d;
		String testLastName = "Test_Last_Name_" + d;

		Select gender = new Select(driver.findElement(By.xpath("//*[@id='select-create-employee-gender']")));
		gender.selectByVisibleText("Herr");

		driver.findElement(By.xpath("//*[@id='input-first-name']")).sendKeys(testFirstName);
		driver.findElement(By.xpath("//*[@id='input-last-name']")).sendKeys(testLastName);

		driver.findElement(By.xpath("//*[@id='input-email']")).sendKeys(testMail);

		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();

		try {
			driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-college-employees/div/p-dataview/div/p-paginator/div")).click();
		} catch(ElementClickInterceptedException e) {
			
		}
		
		try {
			driver.findElement(By.name("Prof. " + testFirstName + " " + testLastName));
			driver.findElement(By.name(testMail));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		
		Assert.assertEquals(result, true);
	}


}