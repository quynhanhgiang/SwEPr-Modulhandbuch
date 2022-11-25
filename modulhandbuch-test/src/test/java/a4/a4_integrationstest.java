package a4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class a4_integrationstest {
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
	public void I_A2A4T01() {
		driver.findElement(By.id("btn-hamburger")).click();	
		driver.findElement(By.xpath("//a[text()=' Modulverwaltung ']")).click();
	    WebElement neuesModulButton = driver.findElement(By.xpath("//button[text()='Neues Modul erstellen']"));
	    Assert.assertNotNull(neuesModulButton);
	}

}
