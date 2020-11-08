package br.ce.gabruw.tasks.health;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

	@Test
	public void healthCheck() throws MalformedURLException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("disable-infobars");
		options.addArguments("--disable-extensions");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--no-sandbox");

		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.105:4444/wd/hub"), options);

		try {
			driver.navigate().to("http://192.168.0.105:8080/tasks");
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			String text = driver.findElement(By.id("addTodo")).getText();
			Assert.assertTrue(text.startsWith("Add"));
		} finally {
			driver.quit();
		}
	}
}
