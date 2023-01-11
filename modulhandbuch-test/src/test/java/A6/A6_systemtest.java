package A6;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.Keys;
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


public class A6_systemtest {
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
			wait.until(ExpectedConditions.elementToBeClickable(By.id("a-module-management")));
			driver.findElement(By.id("a-module-management")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFormular() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-create-module")));
			driver.findElement(By.id("btn-create-module")).click();
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
	public void S_D_A4T01() {
		boolean result = false;
        List<Boolean> resultList = new ArrayList<Boolean>();

		String module_name = "Systemtest_A6";
		String changed = "Testfield has been changed";
		String changed_re = "Systemtest_A6";

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/p-table/div/div/table/thead/tr[2]/th[1]/p-columnfilter/div/p-columnfilterformelement/input")).sendKeys(module_name);
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/p-table/div/div/table/thead/tr[2]/th[1]/p-columnfilter/div/p-columnfilterformelement/input")).sendKeys(Keys.RETURN);

		driver.findElement(By.xpath("//td[.='" + module_name + "']")).click();

		driver.findElement(By.xpath("//*[@id='btn-edit-module']")).click();
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).clear();
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).sendKeys(changed);

		//TODO: Test every field

		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();

		resultList.add(driver.getPageSource().contains(changed));

		driver.findElement(By.xpath("//*[@id='btn-edit-module']")).click();
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).clear();
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).sendKeys(changed_re);

		result = !resultList.contains((Boolean) false);
        Assert.assertEquals(result, true);
	}
    
}
