package a1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;
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
			driver.get("https://85.214.225.164/dev/home");
		} else {
			driver = new ChromeDriver();
			driver.get("https://85.214.225.164/dev/home");
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
		} catch (TimeoutException | NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void openFormular() throws TimeoutException, NoSuchElementException {
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Mitarbeiter anlegen']")));
		driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
	}

	public Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
		Class.forName("org.mariadb.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mariadb://85.214.225.164:3306/swepr_test",
				"read_only_user", "sweprmodulhandbuch2022readonly");
		return connection;
	}

	@Test
	public void S_D_A1T01() throws SQLException, ClassNotFoundException {
		boolean result = false;
		List<Boolean> resultList = new ArrayList<Boolean>();
		openFormular();

		Date date = new Date();
		String d = date.toString().replaceAll(" ", "_").replaceAll(":", "_");

		String testMail = "Test_Mail_" + d + "@test.de";
		String testFirstName = "Test_First_Name_" + d;
		String testLastName = "Test_Last_Name_" + d;

		Select genderSelect = new Select(driver.findElement(By.xpath("//*[@id='select-create-employee-gender']")));
		genderSelect.selectByVisibleText("Frau");

		driver.findElement(By.xpath("//*[@id='input-first-name']")).sendKeys(testFirstName);
		driver.findElement(By.xpath("//*[@id='input-last-name']")).sendKeys(testLastName);

		driver.findElement(By.xpath("//*[@id='input-email']")).sendKeys(testMail);

		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/div[2]/div"))
				.click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/p-overlay/div/div/div/div[2]/ul/p-multiselectitem[1]/li"))
				.click();
		driver.findElement(By.xpath(
				"/html/body/app-root/main/div/div/app-get-college-employees/div/app-create-college-employee/p-dialog/div/div/div[3]/form/div[1]/div[2]/p-multiselect/div/p-overlay/div/div/div/div[1]/button/span"))
				.click();

		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();

		Connection connection = getDatabaseConnection();

		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * " + "FROM college_employee " + "WHERE first_name='" + testFirstName + "'")) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String first_name = resultSet.getString("first_name");
				resultList.add(first_name.equals(testFirstName));

				String last_name = resultSet.getString("last_name");
				resultList.add(last_name.equals(testLastName));

				String gender = resultSet.getString("gender");
				resultList.add(gender.equals("Frau"));

				String mail = resultSet.getString("email");
				resultList.add(mail.equals(testMail));

				String title = resultSet.getString("title");
				resultList.add(title.equals("Prof."));
			}
		}
		result = !resultList.contains((Boolean) false);
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_D_A1T02() throws SQLException, ClassNotFoundException {
		boolean result = false;
		List<Boolean> resultList = new ArrayList<Boolean>();
		openFormular();

		Date date = new Date();
		String d = date.toString().replaceAll(" ", "_").replaceAll(":", "_");

		String testMail = "Test_Mail_" + d + "@test.de";
		String testFirstName = "Test_First_Name_" + d;
		String testLastName = "Test_Last_Name_" + d;

		Select genderSelect = new Select(driver.findElement(By.xpath("//*[@id='select-create-employee-gender']")));
		genderSelect.selectByVisibleText("Frau");

		driver.findElement(By.xpath("//*[@id='input-first-name']")).sendKeys(testFirstName);
		driver.findElement(By.xpath("//*[@id='input-last-name']")).sendKeys(testLastName);

		driver.findElement(By.xpath("//*[@id='input-email']")).sendKeys(testMail);

		driver.findElement(By.xpath("//*[@id='btn-submit-close']")).click();

		Connection connection = getDatabaseConnection();

		try (PreparedStatement statement = connection.prepareStatement(
				"SELECT * " + "FROM college_employee " + "WHERE first_name='" + testFirstName + "'")) {
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String first_name = resultSet.getString("first_name");
				resultList.add(first_name.equals(testFirstName));

				String last_name = resultSet.getString("last_name");
				resultList.add(last_name.equals(testLastName));

				String gender = resultSet.getString("gender");
				resultList.add(gender.equals("Frau"));

				String mail = resultSet.getString("email");
				resultList.add(mail.equals(testMail));

				String title = resultSet.getString("title");
				resultList.add(title.equals(""));
			}
		}
		result = !resultList.contains((Boolean) false);
		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T01() throws NoSuchElementException {
		boolean result = false;

		WebElement mitarbeiterAnlegenButton = driver.findElement(By.xpath("//button[text()='Mitarbeiter anlegen']"));
		if (mitarbeiterAnlegenButton != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T02() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
		driver.findElement(By.xpath("//div[@role='dialog']"));
		WebElement dialogTitle = driver.findElement(By.cssSelector(".p-dialog .p-dialog-header"));
		String dialogTitleText = dialogTitle.getText();
		result = dialogTitleText.contentEquals("Neuen Mitarbeiter anlegen");

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T03() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement andere = driver.findElement(By.id("select-create-employee-gender"));
		if (andere != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T04() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		if (titel != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T05() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement vorname = driver.findElement(By.xpath("//label[text()='Vorname des Mitarbeiters']"));
		if (vorname != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T06() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement nachname = driver.findElement(By.xpath("//label[text()='Nachname des Mitarbeiters']"));
		if (nachname != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T07() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement email = driver.findElement(By.xpath("//label[text()='Email des Mitarbeiters']"));
		if (email != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T08() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("select-create-employee-gender")));
		WebElement andere = driver.findElement(By.id("select-create-employee-gender"));
		andere.click();

		List<WebElement> andereOptions = andere.findElements(By.xpath("//option"));
		WebElement herr = andere.findElement(By.xpath("//option[@value='Herr']"));
		WebElement frau = andere.findElement(By.xpath("//option[@value='Frau']"));
		WebElement divers = andere.findElement(By.xpath("//option[@value='Divers']"));

		if (andereOptions.size() == 3 && herr != null && frau != null && divers != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T09() throws NoSuchElementException, TimeoutException {
		boolean result = false;
		openFormular();

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='Titel auswählen']")));
		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();

		List<WebElement> titelOptions = titel.findElements(By.xpath("//p-multiselectitem"));
		WebElement prof = titel.findElement(By.xpath("//li[@aria-label='Prof.']"));
		WebElement dr = titel.findElement(By.xpath("//li[@aria-label='Dr.']"));
		WebElement dipl = titel.findElement(By.xpath("//li[@aria-label='Dipl.']"));

		if (titelOptions.size() == 3 && prof != null && dr != null && dipl != null) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T10() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		String typeOfVorname = vorname.getAttribute("type");
		if (typeOfVorname.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T11() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		String typeOfNachname = nachname.getAttribute("type");
		if (typeOfNachname.equals("text")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T12() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement email = driver.findElement(By.id("input-email"));
		String typeOfEmail = email.getAttribute("type");
		if (typeOfEmail.equals("email")) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T13() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement andere = driver.findElement(By.id("select-create-employee-gender"));
		String andereRequired = andere.getAttribute("required");
		if (Boolean.valueOf(andereRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T14() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		String titelRequired = titel.getAttribute("required");
		if (!Boolean.valueOf(titelRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T15() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		String vornameRequired = vorname.getAttribute("required");
		if (Boolean.valueOf(vornameRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T16() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		String nachnameRequired = nachname.getAttribute("required");
		if (Boolean.valueOf(nachnameRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T17() throws NoSuchElementException {
		boolean result = false;
		openFormular();

		WebElement email = driver.findElement(By.id("input-email"));
		String emailRequired = email.getAttribute("required");
		if (Boolean.valueOf(emailRequired)) {
			result = true;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T18() throws UnexpectedTagNameException, NoSuchElementException, StaleElementReferenceException,
			TimeoutException {
		boolean result = false;
		openFormular();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String input = timeStamp.replace("_", "");

		Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
		andere.selectByValue("Herr");

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();
		titel.findElement(By.xpath("//li[@aria-label='Prof.']")).click();
		titel.findElement(By.xpath("//li[@aria-label='Dr.']")).click();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		vorname.sendKeys("testVorname" + input);

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		nachname.sendKeys("testNachname" + input);

		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("testEmail" + input + "@hs-coburg.de");

		WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
		buttonSpeichern.submit();

		WebElement formularNeuenMitarbeiterAnlegen = null;

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
			formularNeuenMitarbeiterAnlegen = driver.findElement(By.xpath("//div[@role='dialog']"));
		} catch (TimeoutException | NoSuchElementException e) {
			if (formularNeuenMitarbeiterAnlegen == null) {
				result = true;
			}

			String currentUrl = driver.getCurrentUrl();
			String expectedUrl = "https://85.214.225.164/dev/user-management";
			if (!currentUrl.equals(expectedUrl)) {
				result = false;
			}
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T19() throws UnexpectedTagNameException, NoSuchElementException, StaleElementReferenceException,
			TimeoutException {
		boolean result = false;
		openFormular();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String input = timeStamp.replace("_", "");

		Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
		andere.selectByValue("Frau");

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		vorname.sendKeys("testVorname" + input);

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		nachname.sendKeys("testNachname" + input);

		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("testEmail" + input + "@hs-coburg.de");

		WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
		buttonSpeichern.submit();

		WebElement formularNeuenMitarbeiterAnlegen = null;

		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
			formularNeuenMitarbeiterAnlegen = driver.findElement(By.xpath("//div[@role='dialog']"));
		} catch (TimeoutException | NoSuchElementException e) {
			if (formularNeuenMitarbeiterAnlegen == null) {
				result = true;
			}

			String currentUrl = driver.getCurrentUrl();
			String expectedUrl = "https://85.214.225.164/dev/user-management";
			if (!currentUrl.equals(expectedUrl)) {
				result = false;
			}
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T20() throws UnexpectedTagNameException, NoSuchElementException, StaleElementReferenceException,
			TimeoutException {

		boolean result = false;
		openFormular();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String input = timeStamp.replace("_", "");

		Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
		andere.selectByValue("Herr");

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();
		titel.findElement(By.xpath("//li[@aria-label='Prof.']")).click();
		titel.findElement(By.xpath("//li[@aria-label='Dr.']")).click();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		vorname.sendKeys("testVorname" + input);

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		nachname.sendKeys("testNachname" + input);

		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("Volkhard.Pfeiffer@hs-coburg.de");

		WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
		buttonSpeichern.submit();

		try {
			Alert alert = driver.switchTo().alert();
			alert.accept();
			if (alert != null) {
				result = true;
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//button[@class='p-ripple p-element ng-tns-c61-0 p-dialog-header-icon p-dialog-header-close p-link ng-star-inserted']")));
			WebElement closeButton = driver.findElement(By.xpath(
					"//button[@class='p-ripple p-element ng-tns-c61-0 p-dialog-header-icon p-dialog-header-close p-link ng-star-inserted']"));
			closeButton.click();
		} catch (NoAlertPresentException e) {
			result = false;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T21() throws UnexpectedTagNameException, NoSuchElementException, StaleElementReferenceException,
			TimeoutException {
		boolean result = false;
		driver.navigate().refresh();
		openFormular();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String input = timeStamp.replace("_", "");

		Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
		andere.selectByValue("Herr");

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();
		titel.findElement(By.xpath("//li[@aria-label='Prof.']")).click();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		vorname.sendKeys("Volkhard");

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		nachname.sendKeys("Pfeiffer");

		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("testEmail" + input + "@hs-coburg.de");

		WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
		buttonSpeichern.submit();

		WebElement formularNeuenMitarbeiterAnlegen = null;

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[text()='Namensdopplung Bestätigen']")));
			WebElement namensdopplungBestätigen = driver
					.findElement(By.xpath("//span[text()='Namensdopplung Bestätigen']"));
			if (namensdopplungBestätigen != null) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@label='Yes']")));
				WebElement acceptButton = driver.findElement(By.xpath("//button[@label='Yes']"));
				acceptButton.click();

				try {
					wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@role='dialog']")));
					formularNeuenMitarbeiterAnlegen = driver.findElement(By.xpath("//div[@role='dialog']"));
				} catch (TimeoutException | NoSuchElementException e) {
					if (formularNeuenMitarbeiterAnlegen == null) {
						result = true;
					}

					String currentUrl = driver.getCurrentUrl();
					String expectedUrl = "https://85.214.225.164/dev/user-management";
					if (!currentUrl.equals(expectedUrl)) {
						result = false;
					}
				}

			}
		} catch (TimeoutException | NoSuchElementException e) {
			result = false;
		}

		Assert.assertEquals(result, true);
	}

	@Test
	public void S_F_A1T22() throws UnexpectedTagNameException, NoSuchElementException, StaleElementReferenceException,
			TimeoutException {
		boolean result = false;
		openFormular();

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String input = timeStamp.replace("_", "");

		Select andere = new Select(driver.findElement(By.id("select-create-employee-gender")));
		andere.selectByValue("Herr");

		WebElement titel = driver.findElement(By.xpath("//div[text()='Titel auswählen']"));
		titel.click();
		titel.findElement(By.xpath("//li[@aria-label='Prof.']")).click();

		WebElement vorname = driver.findElement(By.id("input-first-name"));
		vorname.sendKeys("Volkhard");

		WebElement nachname = driver.findElement(By.id("input-last-name"));
		nachname.sendKeys("Pfeiffer");

		WebElement email = driver.findElement(By.id("input-email"));
		email.sendKeys("testEmail" + input + "@hs-coburg.de");

		WebElement buttonSpeichern = driver.findElement(By.id("btn-submit-close"));
		buttonSpeichern.submit();

		WebElement formularNeuenMitarbeiterAnlegen = null;

		try {
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//span[text()='Namensdopplung Bestätigen']")));
			WebElement namensdopplungBestätigen = driver
					.findElement(By.xpath("//span[text()='Namensdopplung Bestätigen']"));
			if (namensdopplungBestätigen != null) {
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@label='No']")));
				WebElement backButton = driver.findElement(By.xpath("//button[@label='Yes']"));
				backButton.click();

				try {
					wait.until(ExpectedConditions
							.invisibilityOfElementLocated(By.xpath("//span[text()='Namensdopplung Bestätigen']")));
					formularNeuenMitarbeiterAnlegen = driver.findElement(By.xpath("//div[@role='dialog']"));
					if (formularNeuenMitarbeiterAnlegen != null) {
						result = true;
					}
				} catch (TimeoutException | NoSuchElementException e) {
					if (formularNeuenMitarbeiterAnlegen == null) {
						result = false;
					}
				}
			}
		} catch (TimeoutException | NoSuchElementException e) {
			result = false;
		}

		Assert.assertEquals(result, true);
	}
}
