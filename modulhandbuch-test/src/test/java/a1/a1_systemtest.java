package a1;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

public class a1_systemtest {
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
		} else {
			driver = new ChromeDriver();
			driver.get("http://localhost:4200/home");
			driver.manage().window().maximize();
		}
		wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		wait.until(ExpectedConditions.elementToBeClickable(By.id("btn-hamburger")));
		driver.findElement(By.id("btn-hamburger")).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("a-user-management")));
		driver.findElement(By.id("a-user-management")).click();
	}

	public void openFormular() {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Mitarbeiter anlegen']")));
		driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']")).click();
	}

	@Test
	public void S_F_A1T01() {
		boolean result = true;

		try {
			driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T02() {
		boolean result = true;

		openFormular();
		try {
			driver.findElement(By.xpath("//div[@role='dialog']"));
			WebElement dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header"));
			String dialogTitleText = dialogTitle.getText();
			result = dialogTitleText.contentEquals("Neuen Mitarbeiter anlegen");
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T03() {
		boolean result = true;
		openFormular();
		try {
			driver.findElement(By.xpath("//span[text()='Herr']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T04() {
		boolean result = true;
		openFormular();

		try {
			driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T05() {
		boolean result = true;
		openFormular();

		try {
			driver.findElement(By.xpath("//label[text()='Vorname des Mitarbeiters']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T06() {
		boolean result = true;
		openFormular();

		try {
			driver.findElement(By.xpath("//label[text()='Nachname des Mitarbeiters']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T07() {
		boolean result = true;
		openFormular();

		try {
			driver.findElement(By.xpath("//label[text()='Email des Mitarbeiters']"));
		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T08() {
		boolean result = true;
		openFormular();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("pr_id_2_label")));
		WebElement andere = driver.findElement(By.id("pr_id_2_label"));
		andere.click();

		try {
			List<WebElement> andereOptions = andere.findElements(By.xpath("//li[@role='option']"));
			if (andereOptions.size() != 3) {
				result = false;
			}
			andere.findElement(By.xpath("//li[@aria-label='Herr']"));
			andere.findElement(By.xpath("//li[@aria-label='Frau']"));
			andere.findElement(By.xpath("//li[@aria-label='Diverse']"));

		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T09() {
		boolean result = true;
		openFormular();

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		String typeOfTitel = titel.getAttribute("type");
		if (typeOfTitel == null || !typeOfTitel.equals("text")) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T10() {
		boolean result = true;
		openFormular();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		String typeOfVorname = vorname.getAttribute("type");
		if (typeOfVorname == null || !typeOfVorname.equals("text")) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T11() {
		boolean result = true;
		openFormular();

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		String typeOfNachname = nachname.getAttribute("type");
		if (typeOfNachname == null || !typeOfNachname.equals("text")) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T12() {
		boolean result = true;
		openFormular();

		WebElement email = driver.findElement(By.id("input-email"));
		String typeOfEmail = email.getAttribute("type");
		if (typeOfEmail == null || !typeOfEmail.equals("email")) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T13() {
		boolean result = true;
		openFormular();
		
		WebElement andere = driver.findElement(By.id("pr_id_2_label"));
		String emailRequired = andere.getAttribute("required");
		if (!Boolean.valueOf(emailRequired)) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T14() {
		boolean result = true;
		openFormular();
		
		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		String titelRequired = titel.getAttribute("required");
		if (Boolean.valueOf(titelRequired)) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T15() {
		boolean result = true;
		openFormular();
		
		WebElement vorname = driver.findElement(By.id("input-first-name"));
		String vornameRequired = vorname.getAttribute("required");
		if (!Boolean.valueOf(vornameRequired)) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T16() {
		boolean result = true;
		openFormular();
		
		WebElement nachname = driver.findElement(By.id("input-last-name"));
		String nachnameRequired = nachname.getAttribute("required");
		if (!Boolean.valueOf(nachnameRequired)) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
	
	@Test
	public void S_F_A1T17() {
		boolean result = true;
		openFormular();
		
		WebElement email = driver.findElement(By.id("input-email"));
		String emailRequired = email.getAttribute("required");
		if (!Boolean.valueOf(emailRequired)) {
			result = false;
		}
		Assert.assertEquals(result, true);
	}
}
