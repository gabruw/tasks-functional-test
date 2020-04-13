package br.ce.gabruw.tasks.functional;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TasksTest {
	private final String URL = "http://localhost:8001/tasks";

	private WebDriver accessApplication(long time) {
		System.setProperty("webdriver.chrome.driver",
				"C:/Users/snyp_/OneDrive/Documents/ChromeDriver/80/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.navigate().to(URL);
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

		return driver;
	}

	@Test
	public void saveTaskWithSuccess() {
		WebDriver driver = accessApplication(10);

		try {
			// Clicar no botão "Add Todo"
			driver.findElement(By.id("addTodo")).click();

			// Adicionar texto no input "Descrição"
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// Adicionar texto no input "Data"
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2100");

			// Clicar no botão em "Salvar"
			driver.findElement(By.id("saveButton")).click();
		} finally {
			// Fechar
			driver.quit();
		}
	}

	@Test
	public void dontSaveTaskWithoutDescription() {
		WebDriver driver = accessApplication(10);

		try {
			// Clicar no botão "Add Todo"
			driver.findElement(By.id("addTodo")).click();

			// Adicionar texto no input "Data"
			driver.findElement(By.id("dueDate")).sendKeys("10/10/1999");

			// Clicar no botão em "Salvar"
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the task description", message);

		} finally {
			// Fechar
			driver.quit();
		}
	}

	@Test
	public void dontSaveTaskWithoutDate() {
		WebDriver driver = accessApplication(10);

		try {
			// Clicar no botão "Add Todo"
			driver.findElement(By.id("addTodo")).click();

			// Adicionar texto no input "Descrição"
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// Clicar no botão em "Salvar"
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Fill the due date", message);

		} finally {
			// Fechar
			driver.quit();
		}
	}

	@Test
	public void dontSaveTaskWithPastDate() {
		WebDriver driver = accessApplication(10);

		try {
			// Clicar no botão "Add Todo"
			driver.findElement(By.id("addTodo")).click();

			// Adicionar texto no input "Descrição"
			driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

			// Adicionar texto no input "Data"
			driver.findElement(By.id("dueDate")).sendKeys("10/10/1999");

			// Clicar no botão em "Salvar"
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Due date must not be in past", message);
		} finally {
			// Fechar
			driver.quit();
		}
	}

	@Test
	public void saveTaskWithMissingButtonId() {
		WebDriver driver = accessApplication(5);

		try {
			// Clicar no botão "Add Todo"
			driver.findElement(By.id("batata")).click();

			// Adicionar texto no input "Descrição"
			driver.findElement(By.id("tasks")).sendKeys("Teste via Selenium");

			// Adicionar texto no input "Data"
			driver.findElement(By.id("dueDate")).sendKeys("10/10/2100");

			// Clicar no botão em "Salvar"
			driver.findElement(By.id("saveButton")).click();

			// Validar mensagem de sucesso
			String message = driver.findElement(By.id("message")).getText();
			Assert.assertEquals("Success!", message);
		} catch (Exception e) {
			Assert.assertTrue(true);
		} finally {
			// Fechar
			driver.quit();
		}
	}
}
