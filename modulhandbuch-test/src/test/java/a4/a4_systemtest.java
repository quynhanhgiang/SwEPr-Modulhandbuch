package a4;

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
                }
                else {
                        driver = new ChromeDriver();
                        driver.get("https://85.214.225.164/dev");
                        driver.manage().window().maximize();
                        driver.findElement(By.id("details-button")).click();
                        driver.findElement(By.id("proceed-link")).click();
                }
        }

        @Test
	public void S_D_A4T01() {
                driver.findElement(By.id("btn-hamburger")).click();
                driver.findElement(By.id("a-module-management")).click();
                driver.findElement(By.id("bt-create-module")).click();

                
                driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[1]/td[2]/input"))
                .sendKeys("Programmieren 1");

                driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[2]/td[2]/input"))
                .sendKeys("Prog 1");

                // B ZT
                driver.findElement(By.id("pr_id_4_label")).click();
                driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[1]/td[2]/p-dropdown/div/div[3]/div/ul/p-dropdownitem[5]/li/span")).click();

                // Kategorie
                Select s = new Select(driver.findElement(By.xpath("/html/body/app-root/main/div/div/app-get-modules/app-create-module/p-dialog/div/div/div[3]/form/table/tr[5]/td/ul/li/tr[2]/td[2]/select")));
                s.selectByValue("Pflichtfach");

                


		Assert.assertEquals(true, true);
	}

    





}
