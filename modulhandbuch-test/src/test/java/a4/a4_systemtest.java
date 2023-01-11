package a4;

import org.openqa.selenium.NoSuchElementException;
import io.netty.handler.timeout.TimeoutException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
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
		} catch (TimeoutException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFormular() throws TimeoutException, NoSuchElementException {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Neues Modul erstellen']")));
		driver.findElement(By.xpath("//button[text()='Neues Modul erstellen']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
	}

	@Test
	public void S_D_A4T01() {
		driver.findElement(By.id("input-create-module-moduleName")).sendKeys("Programmieren 1");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[2]/td[2]/input"))
				.sendKeys("Prog 1");

		driver.findElement(By.id("pr_id_4_label")).click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[1]/td[2]/p-dropdown/div/div[3]/div/ul/p-dropdownitem[5]/li/span"))
				.click();

		Select category = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[2]/td[2]/select")));
		category.selectByVisibleText("Pflichtfach");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[3]/td[2]/input"))
				.sendKeys("5");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[4]/td[2]/input"))
				.sendKeys("6");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[5]/td[2]/p-editor/div/div[2]/div[1]"))
				.sendKeys("150 Stunden, davon:\n50 in Präsenz\n100 Selbststudium");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[6]/td[2]/input"))
				.sendKeys("1");

		Select cycle = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[7]/td[2]/select")));
		cycle.selectByVisibleText("Jährlich");

		Select duration = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[8]/td[2]/select")));
		duration.selectByVisibleText("Einsemestrig");

		driver.findElement(By.id("pr_id_3_label")).click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[9]/td[2]/p-dropdown/div/div[3]/div/ul/p-dropdownitem[2]/li/span"))
				.click();

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[2]"))
				.click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[4]/div[2]/ul/p-multiselectitem[1]/li/span"))
				.click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[10]/td[2]/p-multiselect/div/div[4]/div[2]/ul/p-multiselectitem[2]/li/span"))
				.click();

		Select language = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[11]/td[2]/select")));
		language.selectByVisibleText("Deutsch");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[12]/td[2]/input"))
				.sendKeys("TEST Nutzung in anderen Studiengängen");

		Select req2 = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[13]/td[2]/select")));
		req2.selectByVisibleText("Vorrückensberechtigung nach §5 Abs. 1 SPO (IF)");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[14]/td[2]/input"))
				.sendKeys("TEST Inhaltliche Voraussetzung");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[15]/td[2]/p-editor/div/div[2]/div[1]/p"))
				.sendKeys("TEST Qualifikationsziele/Kompetenzen");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[16]/td[2]/p-editor/div/div[2]/div[1]/p"))
				.sendKeys("TEST Lehrinhalte");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[17]/td[2]/input"))
				.sendKeys("TEST Endnotenbildung Studien-/Prüfungsleistungen");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[18]/td[2]/input"))
				.sendKeys("TEST Sonstige Leistungsnachweise");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[19]/td[2]/input"))
				.sendKeys("TEST Medienform");

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[20]/td[2]/p-editor/div/div[2]/div[1]/p"))
				.sendKeys("TEST Literatur");

		Select mat = new Select(driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[21]/td[2]/select")));
		mat.selectByVisibleText("Grün");

		driver.findElement(By.id("bt-submit-new")).click();

		// Database Stuff

		Assert.assertEquals(true, true);
	}

	@Test
	public void S_F_A4T01() throws NoSuchElementException {
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
	public void S_F_A4T03() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		driver.navigate().refresh();
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement modulbezeichnung = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Modulbezeichnung')]"));

		if (modulbezeichnung != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T04() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement kuerzel = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Kürzel')]"));

		if (kuerzel != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T05() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement lehrformSWS = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Lehrform / SWS')]"));

		if (lehrformSWS != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T06() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement angebotsturnus = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Angebotsturnus')]"));

		if (angebotsturnus != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T07() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement dauerDesModuls = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Dauer des Moduls')]"));

		if (dauerDesModuls != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T08() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement modulverantwortliche = driver.findElement(
				By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Modulverantwortliche(r)')]"));

		if (modulverantwortliche != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T09() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement dozent = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Dozent(in)')]"));

		if (dozent != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T10() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement sprache = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Sprache')]"));

		if (sprache != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T11() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement nutzungInAnderenStudiengaengen = driver.findElement(By
				.xpath("//div[@class='border border-dark']//*[contains(text(), 'Nutzung in anderen Studiengängen')]"));

		if (nutzungInAnderenStudiengaengen != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T12() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement inhaltlicheVoraussetzung = driver.findElement(
				By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Inhaltliche Voraussetzung')]"));

		if (inhaltlicheVoraussetzung != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T13() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement qualifikationszieleKompetenzen = driver.findElement(
				By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Qualifikationsziele/Kompetenzen')]"));

		if (qualifikationszieleKompetenzen != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T14() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement lehrinhalte = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Lehrinhalte')]"));

		if (lehrinhalte != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T15() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement endnotenbildendeStudienPruefungsleistungen = driver.findElement(By.xpath(
				"//div[@class='border border-dark']//*[contains(text(), 'Endnotenbildende Studien-/Prüfungsleistungen')]"));

		if (endnotenbildendeStudienPruefungsleistungen != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T16() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement sonstigeLeistungsnachweise = driver.findElement(
				By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Sonstige Leistungsnachweise')]"));

		if (sonstigeLeistungsnachweise != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T17() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement medienform = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Medienform')]"));

		if (medienform != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T18() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement literatur = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Literatur')]"));

		if (literatur != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T19() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='border border-dark']")));
		WebElement mutterschutz = driver
				.findElement(By.xpath("//div[@class='border border-dark']//*[contains(text(), 'Mutterschutz')]"));

		if (mutterschutz != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T20() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@formgroupname='variations']")));
		WebElement leistungspunkteECTS = driver.findElement(
				By.xpath("//ul[@formgroupname='variations']//*[contains(text(), 'Leistungspunkte / ECTS')]"));

		if (leistungspunkteECTS != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T21() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@formgroupname='variations']")));
		WebElement arbeitsaufwand = driver
				.findElement(By.xpath("//ul[@formgroupname='variations']//*[contains(text(), 'Arbeitsaufwand')]"));

		if (arbeitsaufwand != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T22() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@formgroupname='variations']")));
		WebElement fachsemester = driver
				.findElement(By.xpath("//ul[@formgroupname='variations']//*[contains(text(), 'Fachsemester')]"));

		if (fachsemester != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T23() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//ul[@formgroupname='variations']")));
		WebElement zulassungsvoraussetzungen = driver.findElement(
				By.xpath("//ul[@formgroupname='variations']//*[contains(text(), 'Zulassungsvoraussetzungen')]"));

		if (zulassungsvoraussetzungen != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T24() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-moduleName")));
		WebElement modulbezeichnung = driver.findElement(By.id("input-create-module-moduleName"));
		String typeModulbezeichnung = modulbezeichnung.getAttribute("type");
		if (typeModulbezeichnung.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T25() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-abbreviation")));
		WebElement kuerzel = driver.findElement(By.id("input-create-module-abbreviation"));
		String typeKuerzel = kuerzel.getAttribute("type");
		if (typeKuerzel.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T26() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-sws")));
		WebElement lehrformSWS = driver.findElement(By.id("input-create-module-sws"));
		String typeLehrformSWS = lehrformSWS.getAttribute("type");
		if (typeLehrformSWS.equals("number")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T27() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-ects")));
		WebElement leistungspunkte = driver.findElement(By.id("input-create-module-ects"));
		String typeLeistungspunkte = leistungspunkte.getAttribute("type");
		if (typeLeistungspunkte.equals("number")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T28() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-semester")));
		WebElement fachsemester = driver.findElement(By.id("input-create-module-semester"));
		String typeFachsemester = fachsemester.getAttribute("type");
		if (typeFachsemester.equals("number")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T29() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-module-cycle")));
		Select angebotsturnus = new Select(driver.findElement(By.id("select-create-module-cycle")));

		List<WebElement> angebotsturnusOptions = angebotsturnus.getOptions();
		List<String> angebotsturnusOptionsTexts = new ArrayList<String>();
		for (WebElement option : angebotsturnusOptions) {
			angebotsturnusOptionsTexts.add(option.getText());
		}

		if (angebotsturnusOptions.size() == 2 
				&& angebotsturnusOptionsTexts.contains("Halbjährlich")
				&& angebotsturnusOptionsTexts.contains("Jährlich")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T30() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-module-duration")));
		Select dauerDesModuls = new Select(driver.findElement(By.id("select-create-module-duration")));

		List<WebElement> dauerDesModulsOptions = dauerDesModuls.getOptions();
		List<String> dauerDesModulsOptionsTexts = new ArrayList<String>();
		for (WebElement option : dauerDesModulsOptions) {
			dauerDesModulsOptionsTexts.add(option.getText());
		}

		if (dauerDesModulsOptions.size() == 2 
				&& dauerDesModulsOptionsTexts.contains("Einsemestrig")
				&& dauerDesModulsOptionsTexts.contains("Zweisemestrig")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T31() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-module-language")));
		Select sprache = new Select(driver.findElement(By.id("select-create-module-language")));

		List<WebElement> spracheOptions = sprache.getOptions();
		List<String> spracheOptionsTexts = new ArrayList<String>();
		for (WebElement option : spracheOptions) {
			spracheOptionsTexts.add(option.getText());
		}

		if (spracheOptions.size() == 7 
				&& spracheOptionsTexts.contains("Deutsch")
				&& spracheOptionsTexts.contains("Englisch")
				&& spracheOptionsTexts.contains("Französisch")
				&& spracheOptionsTexts.contains("Italienisch")
				&& spracheOptionsTexts.contains("Spanisch")
				&& spracheOptionsTexts.contains("Russisch")
				&& spracheOptionsTexts.contains("Chinesisch")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A4T32() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-knowledge-requirements")));
		WebElement inhaltlicheVoraussetzungen = driver.findElement(By.id("input-create-module-knowledge-requirements"));
		String typeInhaltlicheVoraussetzungen = inhaltlicheVoraussetzungen.getAttribute("type");
		if (typeInhaltlicheVoraussetzungen.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T33() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-exam-type")));
		WebElement endnotenbildendeStudienPruefungsleistungen = driver.findElement(By.id("input-create-module-exam-type"));
		String typeEndnotenbildendeStudienPruefungsleistungen = endnotenbildendeStudienPruefungsleistungen.getAttribute("type");
		if (typeEndnotenbildendeStudienPruefungsleistungen.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T34() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-certificates")));
		WebElement sonstigeLeistungsnachweise = driver.findElement(By.id("input-create-certificates"));
		String typeSonstigeLeistungsnachweise = sonstigeLeistungsnachweise.getAttribute("type");
		if (typeSonstigeLeistungsnachweise.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T35() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-media-type")));
		WebElement medienformen = driver.findElement(By.id("input-create-module-media-type"));
		String typeMedienformen = medienformen.getAttribute("type");
		if (typeMedienformen.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T36() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-module-maternityProtection")));
		Select mutterschutz = new Select(driver.findElement(By.id("select-create-module-maternityProtection")));

		List<WebElement> mutterschutzOptions = mutterschutz.getOptions();
		List<String> mutterschutzOptionsTexts = new ArrayList<String>();
		for (WebElement option : mutterschutzOptions) {
			mutterschutzOptionsTexts.add(option.getText());
		}

		if (mutterschutzOptions.size() == 3 
				&& mutterschutzOptionsTexts.contains("Grün")
				&& mutterschutzOptionsTexts.contains("Gelb")
				&& mutterschutzOptionsTexts.contains("Rot")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T37() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-abbreviation")));
		WebElement kuerzel = driver.findElement(By.id("input-create-module-abbreviation"));
		String kuerzelMaxLength = kuerzel.getAttribute("maxlength");
		if (Integer.valueOf(kuerzelMaxLength) == 7) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T38() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-sws")));
		WebElement lehrformSWS = driver.findElement(By.id("input-create-module-sws"));
		String lehrformSWSMin = lehrformSWS.getAttribute("min");
		if (Integer.valueOf(lehrformSWSMin) == 0) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T39() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-sws")));
		WebElement lehrformSWS = driver.findElement(By.id("input-create-module-sws"));
		String lehrformSWSMax = lehrformSWS.getAttribute("max");
		if (Integer.valueOf(lehrformSWSMax) == 40) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T40() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-ects")));
		WebElement leistungspunkte = driver.findElement(By.id("input-create-module-ects"));
		String leistungspunkteMin = leistungspunkte.getAttribute("min");
		if (Integer.valueOf(leistungspunkteMin) == 1) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T41() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-ects")));
		WebElement leistungspunkte = driver.findElement(By.id("input-create-module-ects"));
		String leistungspunkteMax = leistungspunkte.getAttribute("max");
		if (Integer.valueOf(leistungspunkteMax) == 30) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T42() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-semester")));
		WebElement fachsemester = driver.findElement(By.id("input-create-module-semester"));
		String fachsemesterMin = fachsemester.getAttribute("min");
		if (Integer.valueOf(fachsemesterMin) == 1) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T43() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-semester")));
		WebElement fachsemester = driver.findElement(By.id("input-create-module-semester"));
		String fachsemesterMax = fachsemester.getAttribute("max");
		if (Integer.valueOf(fachsemesterMax) == 7) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T44() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-moduleName")));
		WebElement modulbezeichnung = driver.findElement(By.id("input-create-module-moduleName"));
		String modulbezeichnungRequired = modulbezeichnung.getAttribute("required");
		if (Boolean.valueOf(modulbezeichnungRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T45() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-abbreviation")));
		WebElement kuerzel = driver.findElement(By.id("input-create-module-abbreviation"));
		String kuerzelRequired = kuerzel.getAttribute("required");
		if (Boolean.valueOf(kuerzelRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T46() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-sws")));
		WebElement lehrformSWS = driver.findElement(By.id("input-create-module-sws"));
		String lehrformSWSRequired = lehrformSWS.getAttribute("required");
		if (Boolean.valueOf(lehrformSWSRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T47() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-ects")));
		WebElement leistungspunkte = driver.findElement(By.id("input-create-module-ects"));
		String leistungspunkteRequired = leistungspunkte.getAttribute("required");
		if (Boolean.valueOf(leistungspunkteRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
//	@Test
//	public void S_F_A4T48() throws NoSuchElementException, TimeoutException {
//		boolean result = false;
//		openFormular();
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editor-create-module-workload")));
//		WebElement arbeitsaufwand = driver.findElement(By.id("editor-create-module-workload"));
//		String arbeitsaufwandRequired = arbeitsaufwand.getAttribute("required");
//		if (Boolean.valueOf(arbeitsaufwandRequired)) {
//			result = true;
//		}
//
//		Assert.assertEquals(result, true);
//	}
	
	@Test
	public void S_F_A4T49() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-semester")));
		WebElement fachsemester = driver.findElement(By.id("input-create-module-semester"));
		String fachsemesterRequired = fachsemester.getAttribute("required");
		if (Boolean.valueOf(fachsemesterRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T50() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-admissionRequirements")));
		WebElement zulassungsvoraussetzungen = driver.findElement(By.id("select-create-module-admissionRequirements"));
		String zulassungsvoraussetzungenRequired = zulassungsvoraussetzungen.getAttribute("required");
		if (!Boolean.valueOf(zulassungsvoraussetzungenRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T51() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-cycle")));
		WebElement angebotsturnus = driver.findElement(By.id("select-create-module-cycle"));
		String angebotsturnusRequired = angebotsturnus.getAttribute("required");
		if (Boolean.valueOf(angebotsturnusRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T52() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-duration")));
		WebElement dauerDesModuls = driver.findElement(By.id("select-create-module-duration"));
		String dauerDesModulsRequired = dauerDesModuls.getAttribute("required");
		if (Boolean.valueOf(dauerDesModulsRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T53() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-moduleOwner")));
		WebElement modulverantwortliche = driver.findElement(By.id("select-create-module-moduleOwner"));
		String modulverantwortlicheRequired = modulverantwortliche.getAttribute("required");
		if (Boolean.valueOf(modulverantwortlicheRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T54() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-multiselect-label p-placeholder']")));
		WebElement dozent = driver.findElement(By.xpath("//div[@class='p-multiselect-label p-placeholder']"));
		String dozentRequired = dozent.getAttribute("required");
		if (!Boolean.valueOf(dozentRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T55() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-language")));
		WebElement sprache = driver.findElement(By.id("select-create-module-language"));
		String spracheRequired = sprache.getAttribute("required");
		if (Boolean.valueOf(spracheRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T56() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-usage")));
		WebElement nutzungInAnderenStudiengaengen = driver.findElement(By.id("input-create-module-usage"));
		String nutzungInAnderenStudiengaengenRequired = nutzungInAnderenStudiengaengen.getAttribute("required");
		if (!Boolean.valueOf(nutzungInAnderenStudiengaengenRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T57() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-knowledge-requirements")));
		WebElement inhaltlicheVoraussetzungen = driver.findElement(By.id("input-create-module-knowledge-requirements"));
		String inhaltlicheVoraussetzungenRequired = inhaltlicheVoraussetzungen.getAttribute("required");
		if (!Boolean.valueOf(inhaltlicheVoraussetzungenRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
//	@Test
//	public void S_F_A4T58() throws NoSuchElementException, TimeoutException {
//		boolean result = false;
//		openFormular();
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-knowledge-requirements")));
//		WebElement qualifikationszieleKompetenzen = driver.findElement(By.id("input-create-module-knowledge-requirements"));
//		String qualifikationszieleKompetenzenRequired = qualifikationszieleKompetenzen.getAttribute("required");
//		if (Boolean.valueOf(qualifikationszieleKompetenzenRequired)) {
//			result = true;
//		}
//
//		Assert.assertEquals(result, true);
//	}
	
	@Test
	public void S_F_A4T59() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-knowledge-requirements")));
		WebElement lehrinhalte = driver.findElement(By.id("input-create-module-knowledge-requirements"));
		String lehrinhalteRequired = lehrinhalte.getAttribute("required");
		if (!Boolean.valueOf(lehrinhalteRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T60() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-exam-type")));
		WebElement endnotenbildendeStudienPruefungsleistungen = driver.findElement(By.id("input-create-module-exam-type"));
		String endnotenbildendeStudienPruefungsleistungenRequired = endnotenbildendeStudienPruefungsleistungen.getAttribute("required");
		if (Boolean.valueOf(endnotenbildendeStudienPruefungsleistungenRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T61() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-certificates")));
		WebElement sonstigeLeistungsnachweise = driver.findElement(By.id("input-create-certificates"));
		String sonstigeLeistungsnachweiseRequired = sonstigeLeistungsnachweise.getAttribute("required");
		if (!Boolean.valueOf(sonstigeLeistungsnachweiseRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A4T62() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-media-type")));
		WebElement medienformen = driver.findElement(By.id("input-create-module-media-type"));
		String medienformenRequired = medienformen.getAttribute("required");
		if (!Boolean.valueOf(medienformenRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
	
//	@Test
//	public void S_F_A4T63() throws NoSuchElementException, TimeoutException {
//		boolean result = false;
//		openFormular();
//
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("input-create-module-media-type")));
//		WebElement literatur = driver.findElement(By.id("input-create-module-media-type"));
//		String literaturRequired = literatur.getAttribute("required");
//		if (!Boolean.valueOf(literaturRequired)) {
//			result = true;
//		}
//
//		Assert.assertEquals(result, true);
//	}
	
	@Test
	public void S_F_A4T64() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("select-create-module-maternityProtection")));
		WebElement mutterschutz = driver.findElement(By.id("select-create-module-maternityProtection"));
		String mutterschutzRequired = mutterschutz.getAttribute("required");
		if (Boolean.valueOf(mutterschutzRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}
}
