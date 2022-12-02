package a1;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class a1_systemtest {
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
			driver.get("http://localhost:4200/");
			driver.manage().window().maximize();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("btn-hamburger")).click();
		driver.findElement(By.id("a-user-management")).click();
	}
	
	@Test
	public void S_F_A1T01() {
		boolean result = true;
		
		try {
			driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']"));
		}
		catch(NoSuchElementException noSuchElement) {
			result = false;
		}
		
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T02() {
		driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']")).click();
		boolean result = true;
		
		try {
			driver.findElement(By.xpath("//div[@role='dialog']"));
			WebElement dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header"));
			String dialogTitleText = dialogTitle.getText();
			result = dialogTitleText.contentEquals("Neuen Mitarbeiter anlegen");
			System.out.println(dialogTitleText);
		}
		catch(NoSuchElementException noSuchElement) {
			result = false;
		}

		Assert.assertEquals(result, true);		
	}
}
