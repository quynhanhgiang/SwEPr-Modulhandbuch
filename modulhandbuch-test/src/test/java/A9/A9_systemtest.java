package A9;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


public class A9_systemtest {
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
			wait.until(ExpectedConditions.elementToBeClickable(By.id("a-module-manuals")));
			driver.findElement(By.id("a-module-manuals")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFormular() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-create-module-manual")));
			driver.findElement(By.id("btn-create-module-manual")).click();
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
    public void S_D_A9T01() {
		openFormular();
        boolean result = false;
        List<Boolean> resultList = new ArrayList<Boolean>();

		Date date_d = new Date();
		String d = date_d.toString().replaceAll(" ", "_").replaceAll(":", "_");

        String module_manual_name = "Sommersemester 9000";

		String date = "01012000";
		String date_year = "2000";
		String date_month = "Januar";
		String date_day = "1.";
		String degree = "Bachelor";
		String course = "Informatik_Systemtest_A9_"+d;

		Select semester_spo = new Select(driver.findElement(By.xpath("//*[@id='select-semester-type']")));
		semester_spo.selectByValue("Sommersemester");

		driver.findElement(By.xpath("//*[@id='input-semester-year']")).sendKeys("9000");
		driver.findElement(By.xpath("//*[@id='input-spo-link']")).sendKeys("https://85.214.225.164/dev/home");
		
		Select degree_select = new Select(driver.findElement(By.xpath("//*[@id='select-degree']")));
		degree_select.selectByValue(degree);

		driver.findElement(By.xpath("//*[@id='input-course']")).sendKeys(course);
		driver.findElement(By.xpath("//*[@id='check-end-date']")).click();

		driver.findElement(By.xpath("//*[@id='date-start']")).sendKeys(date);

		driver.findElement(By.xpath("//*[@id='bt-submit-close']")).click();


		driver.findElement(By.id("input-search"))
		.sendKeys(course);

		resultList.add(driver.getPageSource().contains(module_manual_name));
		resultList.add(driver.getPageSource().contains(date_day));
		resultList.add(driver.getPageSource().contains(date_month));
		resultList.add(driver.getPageSource().contains(date_year));
		resultList.add(driver.getPageSource().contains(degree));
		resultList.add(driver.getPageSource().contains(course));

		result = !resultList.contains((Boolean) false);
        Assert.assertEquals(result, true);
    }

}