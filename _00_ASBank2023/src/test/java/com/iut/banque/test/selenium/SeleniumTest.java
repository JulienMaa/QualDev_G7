package com.iut.banque.test.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

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
    driver.get("http://localhost:8081/_00_ASBank2023/");

    WebElement logo = driver.findElement(By.cssSelector("img"));

    Assert.assertTrue(logo.isDisplayed());
  }

  
}
