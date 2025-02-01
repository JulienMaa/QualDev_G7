package com.iut.banque.test.selenium;

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

  @Before
  public void setUp() {
    System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
    driver = new ChromeDriver();

    driver.manage().window().setSize(new Dimension(842, 816));
  }

  @After
  public void tearDown() {
    driver.quit();
  }

  @Test
  public void testLogoExist() {
    String url = "http://localhost:8081/_00_ASBank2023/";

    assumeTrue("Skipping test as URL is unreachable", urlExists(url));

    driver.get(url);
    WebElement logo = driver.findElement(By.id("logo"));
    Assert.assertNotNull(logo);
  }

  public boolean urlExists(String url) {
    try {
      HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("HEAD");
      connection.setConnectTimeout(3000);
      connection.setReadTimeout(3000);
      int responseCode = connection.getResponseCode();
      return (200 <= responseCode && responseCode < 400);
    } catch (Exception e) {
      return false;
    }
  }
}
