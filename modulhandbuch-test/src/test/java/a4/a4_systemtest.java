package a4;

import org.openqa.selenium.NoSuchElementException;
import io.netty.handler.timeout.TimeoutException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class a4_systemtest {
	private WebDriver driver;
	private WebDriverWait wait;

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
	
	public void openFormular() throws TimeoutException, NoSuchElementException {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Neues Modul erstellen']")));
		driver.findElement(By.xpath("//button[text()='Neues Modul erstellen']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
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
		openFormular();
		boolean result = false;
        List<Boolean> resultList = new ArrayList<Boolean>();

		Date date_d = new Date();
		String d = date_d.toString().replaceAll(" ", "_").replaceAll(":", "_");


		String abb = "Abb_ST_A4";
		String module_name = "Module_Systemtest_A4" + d;
		String cycle_string = "Jährlich";
		String duration_string = "Einsemestrig";
		String module_owner = "Prof. Volkhard Pfeiffer";
		String language_string = "Deutsch";
		String usage = "Systemtest_A4_Usage";
		String kr = "Systemtest_A4_KnwoledgeRequirements";
		String exam = "Systemtest_A4_Exam";
		String cert = "Systemtest_A4_Certificates";
		String media = "Systemtest_A4_media_type";
		String mat_string = "Grün";



		driver.findElement(By.id("input-create-module-moduleName")).sendKeys(module_name);
		driver.findElement(By.xpath("//*[@id='input-create-module-abbreviation']")).sendKeys(abb);
		Select cycle = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-cycle']")));
		cycle.selectByValue(cycle_string);
		Select duration = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-duration']")));
		duration.selectByValue(duration_string);

		Select owner = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-moduleOwner']")));
		owner.selectByValue(module_owner);

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/app-create-module/p-dialog/div/div/div[3]/form/div[1]/div[6]/div[2]/p-multiselect/div/div[2]/div")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/app-create-module/p-dialog/div/div/div[3]/form/div[1]/div[6]/div[2]/p-multiselect/div/p-overlay/div/div/div/div[2]/ul/p-multiselectitem[1]/li/span")).click();
		
		Select language = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-moduleOwner']")));
		language.selectByValue(language_string);

		driver.findElement(By.xpath("//*[@id='input-create-module-usage']")).sendKeys(usage);
		driver.findElement(By.xpath("//*[@id='input-create-module-knowledge-requirements']")).sendKeys(kr);
		driver.findElement(By.xpath("//*[@id='input-create-module-exam-type']")).sendKeys(exam);
		driver.findElement(By.xpath("//*[@id='input-create-module-certificates']")).sendKeys(cert);
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).sendKeys(cert);

		Select mat = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-moduleOwner']")));
		mat.selectByValue(mat_string);
	
		resultList.add(driver.getPageSource().contains(module_name));

		result = !resultList.contains((Boolean) false);
        Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T01() throws NoSuchElementException{
		boolean result = false;

		WebElement modulErstellenButton = driver.findElement(By.xpath("//button[text()='Neues Modul erstellen']"));
		if (modulErstellenButton != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T02() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
		driver.findElement(By.xpath("//div[@role='dialog']"));
		WebElement dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header"));
		String dialogTitleText = dialogTitle.getText();
		result = dialogTitleText.contentEquals("Neues Modul erstellen");

		Assert.assertEquals(result, true);
	}

	@Test

	public void S_F_A4T03() {
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
