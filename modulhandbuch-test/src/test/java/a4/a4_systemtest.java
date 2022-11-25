package a4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class a4_systemtest {
	private WebDriver driver;

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
		} else {
			driver = new ChromeDriver();
			driver.get("https://85.214.225.164/dev");
			driver.manage().window().maximize();
		}
		driver.findElement(By.id("details-button")).click();
		driver.findElement(By.id("proceed-link")).click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("btn-hamburger")).click();
		driver.findElement(By.id("a-module-management")).click();
		driver.findElement(By.id("bt-create-module")).click();
	}

	@Test
	public void S_D_A4T01() {
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[1]/td[2]/input"))
				.sendKeys("Programmieren 1");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[2]/td[2]/input"))
				.sendKeys("Prog 1");

		Select spo = new Select(driver.findElement(By.id("pr_id_4_label")));
		spo.selectByVisibleText("B ZT");

		Assert.assertEquals(true, true);
	}
	
	@Test
	public void S_F_A4T01() {
		WebElement dialog = driver.findElement(By.xpath("//div[@role='dialog']"));
		String dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header")).getText();
		boolean result = !dialog.equals(null) && dialogTitle.contentEquals("Neues Modul erstellen");
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T02() {
		WebElement submitCloseButton = driver.findElement(By.id("bt-submit-close"));
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		submitCloseButton.click();
		boolean result = true;
	    try 
	    { 
	        driver.switchTo().alert(); 
	    }   
	    catch (NoAlertPresentException NoAlertEx) 
	    { 
            result = false;
	    }
	    Assert.assertEquals(result, true);
	}

}
