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

		driver.findElement(By.id("pr_id_4_label")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[1]/td[2]/p-dropdown/div/div[3]/div/ul/p-dropdownitem[5]/li/span")).click();

		Select category = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[2]/td[2]/select")));
		category.selectByVisibleText("Pflichtfach");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[3]/td[2]/input"))
			.sendKeys("5");
		
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[4]/td[2]/input"))
			.sendKeys("6");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[5]/td[2]/p-editor/div/div[2]/div[1]"))
			.sendKeys("150 Stunden, davon:\n50 in Präsenz\n100 Selbststudium");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[6]/td[2]/input"))
			.sendKeys("1");

		Select cycle = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[7]/td[2]/select")));
		cycle.selectByVisibleText("Jährlich");

		Select duration = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[8]/td[2]/select")));
		duration.selectByVisibleText("Einsemestrig");

		driver.findElement(By.id("pr_id_3_label")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[9]/td[2]/p-dropdown/div/div[3]/div/ul/p-dropdownitem[2]/li/span")).click();

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[2]")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[4]/div[2]/ul/p-multiselectitem[1]/li/span")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[4]/div[2]/ul/p-multiselectitem[2]/li/span")).click();

		Select language = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[11]/td[2]/select")));
		language.selectByVisibleText("Deutsch");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[12]/td[2]/input"))
			.sendKeys("TEST Nutzung in anderen Studiengängen");

		Select req2 = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[13]/td[2]/select")));
		req2.selectByVisibleText("Vorrückensberechtigung nach §5 Abs. 1 SPO (IF)");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[14]/td[2]/input"))
			.sendKeys("TEST Inhaltliche Voraussetzung");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[15]/td[2]/p-editor/div/div[2]/div[1]/p"))
			.sendKeys("TEST Qualifikationsziele/Kompetenzen");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[16]/td[2]/p-editor/div/div[2]/div[1]/p"))
			.sendKeys("TEST Lehrinhalte");

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[17]/td[2]/input"))
			.sendKeys("TEST Endnotenbildung Studien-/Prüfungsleistungen");






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

	public void S_F_A4T03a() {
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
		WebElement dozent = driver
				.findElement(By.xpath("//div[@class='p-element p-multiselect-label-container ng-tns-c60-2']"));
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

		kuerzel.sendKeys("Programmierung");
		String currentInput = kuerzel.getAttribute("value");

		boolean result = (kuerzelMaxLength == 10) && (currentInput.length() == 10);
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

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
		} catch (NoAlertPresentException noAlertEx) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T22() {
		WebElement sws = driver.findElement(By.xpath("//input[@formcontrolname='sws']"));
		String swsType = sws.getAttribute("type");
		boolean result = swsType.equals("number");

		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T23() {
		WebElement ects = driver.findElement(By.xpath("//input[@formcontrolname='ects']"));
		String ectsType = ects.getAttribute("type");
		boolean result = ectsType.equals("number");

		Assert.assertEquals(result, true);
	}
	
	@Test

	public void S_F_A4T24() {
		WebElement fachsemester = driver.findElement(By.xpath("//input[@formcontrolname='semester']"));
		String fachsemesterType = fachsemester.getAttribute("type");
		boolean result = fachsemesterType.equals("number");

		Assert.assertEquals(result, true);
	}

}
