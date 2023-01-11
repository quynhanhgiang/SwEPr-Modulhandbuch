package a4;

import org.openqa.selenium.NoSuchElementException;
import io.netty.handler.timeout.TimeoutException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import java.util.Date;
import org.openqa.selenium.Keys;
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
		owner.selectByIndex(0);

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/app-create-module/p-dialog/div/div/div[3]/form/div[1]/div[6]/div[2]/p-multiselect/div/div[2]/div")).click();
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/app-create-module/p-dialog/div/div/div[3]/form/div[1]/div[6]/div[2]/p-multiselect/div/p-overlay/div/div/div/div[2]/ul/p-multiselectitem[1]/li/span")).click();
		
		Select language = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-language']")));
		language.selectByIndex(0);

		driver.findElement(By.xpath("//*[@id='input-create-module-usage']")).sendKeys(usage);
		driver.findElement(By.xpath("//*[@id='input-create-module-knowledge-requirements']")).sendKeys(kr);
		driver.findElement(By.xpath("//*[@id='input-create-module-exam-type']")).sendKeys(exam);
		driver.findElement(By.xpath("//*[@id='input-create-certificates']")).sendKeys(cert);
		driver.findElement(By.xpath("//*[@id='input-create-module-media-type']")).sendKeys(cert);

		Select mat = new Select(driver.findElement(By.xpath("//*[@id='select-create-module-maternityProtection']")));
		mat.selectByVisibleText(mat_string);

		// Database Stuff

		driver.findElement(By.xpath("//*[@id='btn-remove-variations']")).click();
		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();

		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/p-table/div/div/table/thead/tr[2]/th[1]/p-columnfilter/div/p-columnfilterformelement/input")).sendKeys(module_name);
		driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/div/p-table/div/div/table/thead/tr[2]/th[1]/p-columnfilter/div/p-columnfilterformelement/input")).sendKeys(Keys.ENTER);
	
		resultList.add(driver.getPageSource().contains(module_name));

		result = !resultList.contains((Boolean) false);
        Assert.assertEquals(result, true);
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
