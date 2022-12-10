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
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.netty.handler.timeout.TimeoutException;

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
			driver.get("https://85.214.225.164/dev");
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
			wait.until(ExpectedConditions.elementToBeClickable(By.id("a-user-management")));
			driver.findElement(By.id("a-user-management")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void openFormular() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Mitarbeiter anlegen']")));
			driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Button to open the form does not work");
			e.printStackTrace();
		}
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
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
			driver.findElement(By.xpath("//div[@role='dialog']"));
			WebElement dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header"));
			String dialogTitleText = dialogTitle.getText();
			result = dialogTitleText.contentEquals("Neuen Mitarbeiter anlegen");
		} catch (NoSuchElementException | TimeoutException e) {
			result = false;
		}
		
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T03() {
		boolean result = true;
		openFormular();
		
		try {
			driver.findElement(By.id("select-create-employee-gender"));
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
		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-employee-gender")));
		WebElement andere = driver.findElement(By.id("select-create-employee-gender"));
		andere.click();

		try {
			List<WebElement> andereOptions = andere.findElements(By.xpath("//option"));
			if (andereOptions.size() != 3) {
				result = false;
			}
			andere.findElement(By.xpath("//option[@value='Herr']"));
			andere.findElement(By.xpath("//option[@value='Frau']"));
			andere.findElement(By.xpath("//option[@value='Diverse']"));

		} catch (NoSuchElementException noSuchElement) {
			result = false;
		}
		
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T09() {
		boolean result = true;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Titel auswählen']")));
		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();
		
		try {
			List<WebElement> titelOptions = titel.findElements(By.xpath("//p-multiselectitem"));
			System.out.println(titelOptions.size());
			if (titelOptions.size() != 3) {
				result = false;
			}
			titel.findElement(By.xpath("//li[@aria-label='Prof.']"));
			titel.findElement(By.xpath("//li[@aria-label='Dr.']"));
			titel.findElement(By.xpath("//li[@aria-label='Dipl.']"));

		} catch (NoSuchElementException noSuchElement) {
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
		
		WebElement andere = driver.findElement(By.id("select-create-employee-gender"));
		String andereRequired = andere.getAttribute("required");
		if (!Boolean.valueOf(andereRequired)) {
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
	
	@Test
	public void S_F_A1T18() {
		boolean result = true;
		openFormular();
		
		try {
			Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
			andere.selectByValue("Herr");
			
			WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
			titel.click();
			titel.findElement(By.xpath("//li[@aria-label='Prof.']")).click();
			titel.findElement(By.xpath("//li[@aria-label='Dr.']")).click();
			
			WebElement vorname = driver.findElement(By.id("input-first-name"));
			vorname.sendKeys("Florian");
			
			WebElement nachname = driver.findElement(By.id("input-last-name"));
			nachname.sendKeys("Mittag");
			
			WebElement email = driver.findElement(By.id("input-email"));
			email.sendKeys("Florian.Mittag@hs-coburg.de");
			
			WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
			buttonSpeichern.submit();
		} catch (Exception e) {
			result = false;
		}
		
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
			WebElement formularNeuenMitarbeiterAnlegen = driver.findElement(By.xpath("//div[@role='dialog']"));
			if(formularNeuenMitarbeiterAnlegen != null) {
				result = false;
			}
		} catch (NoSuchElementException | TimeoutException e) {
			String currentUrl = driver.getCurrentUrl();
			String expectedUrl = "https://85.214.225.164/dev/user-management";
			if(!currentUrl.equals(expectedUrl)) {
				result = false;
			}
		}
		
		Assert.assertEquals(result, true);
	}
}
