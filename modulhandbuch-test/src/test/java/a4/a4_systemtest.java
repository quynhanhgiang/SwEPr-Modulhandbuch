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
		WebElement modulbezeichnung = driver.findElement(By.xpath("//input[@formcontrolname='moduleName']"));
		boolean modulbezeichnungRequired = Boolean.valueOf(modulbezeichnung.getAttribute("required"));

		boolean result = modulbezeichnungRequired;
		Assert.assertEquals(result, true);
	}

	@Test

	public void S_F_A4T03() {
		WebElement kuerzel = driver.findElement(By.xpath("//input[@formcontrolname='abbreviation']"));
		boolean kuerzelRequired = Boolean.valueOf(kuerzel.getAttribute("required"));

		boolean result = kuerzelRequired;
		Assert.assertEquals(result, true);
	}

	@Test

	public void S_F_A4T04() {
		WebElement kategorie = driver.findElement(By.xpath("//select[@formcontrolname='category']"));
		boolean kategorieRequired = Boolean.valueOf(kategorie.getAttribute("required"));

		boolean result = kategorieRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T05() {
		WebElement sws = driver.findElement(By.xpath("//input[@formcontrolname='sws']"));
		boolean swsRequired = Boolean.valueOf(sws.getAttribute("required"));
		
		boolean result = swsRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T06() {
		WebElement ects = driver.findElement(By.xpath("//input[@formcontrolname='ects']"));
		boolean ectsRequired = Boolean.valueOf(ects.getAttribute("required"));
		
		boolean result = ectsRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T07() {
		WebElement fachsemester = driver.findElement(By.xpath("//input[@formcontrolname='semester']"));
		boolean fachsemesterRequired = Boolean.valueOf(fachsemester.getAttribute("required"));
		
		boolean result = fachsemesterRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T08() {
		WebElement angebotsturnus = driver.findElement(By.xpath("//select[@formcontrolname='cycle']"));
		boolean angebotsturnusRequired = Boolean.valueOf(angebotsturnus.getAttribute("required"));
		
		boolean result = angebotsturnusRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T09() {
		WebElement dauerDesModuls = driver.findElement(By.xpath("//select[@formcontrolname='duration']"));
		boolean dauerDesModulsRequired = Boolean.valueOf(dauerDesModuls.getAttribute("required"));
		
		boolean result = dauerDesModulsRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T10() {
		WebElement dozent = driver.findElement(By.xpath("//div[@class='p-element p-multiselect-label-container ng-tns-c60-2']"));
		boolean dozentRequired = Boolean.valueOf(dozent.getAttribute("required"));
		
		boolean result = dozentRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T11() {
		WebElement sprache = driver.findElement(By.xpath("//select[@formcontrolname='language']"));
		boolean spracheRequired = Boolean.valueOf(sprache.getAttribute("required"));
		
		boolean result = spracheRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T12() {
		WebElement pruefungsleistungen = driver.findElement(By.xpath("//input[@formcontrolname='examType']"));
		boolean pruefungsleistungenRequired = Boolean.valueOf(pruefungsleistungen.getAttribute("required"));
		
		boolean result = pruefungsleistungenRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T13() {
		WebElement mutterschutz = driver.findElement(By.xpath("//select[@formcontrolname='maternityProtection']"));
		boolean mutterschutzRequired = Boolean.valueOf(mutterschutz.getAttribute("required"));
		
		boolean result = mutterschutzRequired;
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T14() {
		WebElement kuerzel = driver.findElement(By.xpath("//input[@formcontrolname='abbreviation']"));
		short kuerzelMaxLength = Short.valueOf(kuerzel.getAttribute("maxlength"));
		
		boolean result = (kuerzelMaxLength == 10);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T15() {
	WebElement sws = driver.findElement(By.xpath("//input[@formcontrolname='sws']"));
		short swsMin = Short.valueOf(sws.getAttribute("min"));
		
		boolean result = (swsMin == 0);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T16() {
	WebElement sws = driver.findElement(By.xpath("//input[@formcontrolname='sws']"));
		short swsMax = Short.valueOf(sws.getAttribute("max"));
		
		boolean result = (swsMax == 40);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T17() {
		WebElement ects = driver.findElement(By.xpath("//input[@formcontrolname='ects']"));
		short ectsMin = Short.valueOf(ects.getAttribute("min"));
		
		boolean result = (ectsMin == 1);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T18() {
		WebElement ects = driver.findElement(By.xpath("//input[@formcontrolname='ects']"));
		short ectsMax = Short.valueOf(ects.getAttribute("max"));
		
		boolean result = (ectsMax == 30);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T19() {
		WebElement fachsemester = driver.findElement(By.xpath("//input[@formcontrolname='semester']"));
		short fachsemesterMin = Short.valueOf(fachsemester.getAttribute("min"));
		
		boolean result = (fachsemesterMin == 1);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T20() {
		WebElement fachsemester = driver.findElement(By.xpath("//input[@formcontrolname='semester']"));
		short fachsemesterMax = Short.valueOf(fachsemester.getAttribute("max"));
		
		boolean result = (fachsemesterMax == 7);
		Assert.assertEquals(result, true);
	}
	
	@Test
	
	public void S_F_A4T21() {
		driver.findElement(By.id("btn-remove-variations")).click();
		boolean result = true;
		
		try{
			Alert alert = driver.switchTo().alert();
			alert.accept();
		}
		catch(NoAlertPresentException noAlertEx) {
			result = false;
		}
		Assert.assertEquals(result, true);		
	}

}
