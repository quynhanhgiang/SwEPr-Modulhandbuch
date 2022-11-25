package a2;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class a2_systemtest {
	private WebDriver driver;
	@BeforeTest
	public void openWebsite() {
		boolean localTest = false;
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
			driver.findElement(By.id("details-button")).click();
			driver.findElement(By.id("proceed-link")).click();
		}
	}
	
	@Test
	public void S_F_A2T01() {
		String expectedUrl = "https://85.214.225.164/dev/home";
		Assert.assertEquals(expectedUrl, driver.getCurrentUrl());
	}
	
	@Test
	public void S_F_A2T02() {
		boolean hamburgerMenuExist = !driver.findElements(By.id("btn-hamburger")).isEmpty();
		Assert.assertEquals(hamburgerMenuExist, true);
	}
	
	@Test
	public void S_F_A2T03() {
		WebElement modulhandbuecher = driver.findElement(By.xpath("//a[text()=' Modulhandbücher ']"));
		Assert.assertNotNull(modulhandbuecher);
	}
	
	@Test
	public void S_F_A2T04() {
		WebElement modulverwaltung = driver.findElement(By.xpath("//a[text()=' Modulverwaltung ']"));
		Assert.assertNotNull(modulverwaltung);
	}
}
