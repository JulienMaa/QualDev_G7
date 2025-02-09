package com.iut.banque.test.selenium;

import com.iut.banque.test.utils.ConfigReader;
import org.junit.Assert;
import static org.junit.Assume.assumeTrue;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import java.net.HttpURLConnection;
import java.net.URL;

public class SeleniumTest {
  private WebDriver driver;
  private String baseUrl;
  private String loginPage;
  private String username;
  private String password;
  private String loginButtonName;
  private String usernameFieldName;
  private String passwordFieldName;
  private String successElementId;

  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    driver = new ChromeDriver();
    driver.manage().window().setSize(new Dimension(842, 816));

    // Charger les valeurs de config.properties
    baseUrl = ConfigReader.getProperty("baseUrl");
    loginPage = ConfigReader.getProperty("loginPage");
    username = ConfigReader.getProperty("username");
    password = ConfigReader.getProperty("password");
    loginButtonName = ConfigReader.getProperty("loginButtonName");
    usernameFieldName = ConfigReader.getProperty("usernameFieldName");
    passwordFieldName = ConfigReader.getProperty("passwordFieldName");
    successElementId = ConfigReader.getProperty("successElementId");
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testLogoExist() {
    String url = baseUrl;

    assumeTrue("Skipping test as URL is unreachable", urlExists(url));

    driver.get(url);
    WebElement logo = driver.findElement(By.id("logo"));
    Assert.assertNotNull(logo);
  }

  @Test
  public void testLogin() {
    // Aller à la page de connexion
    driver.get(baseUrl + loginPage);

    // Vérifier si la page s'est bien chargée
    assumeTrue("Skipping test as URL is unreachable", urlExists(baseUrl + loginPage));

    // Remplir le formulaire de connexion
    WebElement usernameInput = driver.findElement(By.name(usernameFieldName));
    WebElement passwordInput = driver.findElement(By.name(passwordFieldName));
    WebElement loginButton = driver.findElement(By.name(loginButtonName));

    usernameInput.sendKeys(username);
    passwordInput.sendKeys(password);
    loginButton.click();

    // Vérifier si la connexion a réussi en recherchant un élément spécifique sur la page
    WebElement successElement = driver.findElement(By.id(successElementId));
    Assert.assertNotNull("Connexion échouée, l'élément de confirmation n'a pas été trouvé", successElement);
  }

  public boolean urlExists(String url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("HEAD");
      connection.setConnectTimeout(3000);
      connection.setReadTimeout(3000);

      int responseCode = connection.getResponseCode();
      return (responseCode == 200);
    } catch (Exception e) {
      return false;
    }
  }
}
