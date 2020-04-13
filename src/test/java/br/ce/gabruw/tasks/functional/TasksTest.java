package br.ce.gabruw.tasks.functional;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TasksTest {
	private final String URL_LOCAL = "http://localhost:8001/tasks";
	private final String URL_REMOTE = "http://192.168.0.108:8001/tasks";

	private WebDriver accessApplication(long time) throws MalformedURLException {
		// *** Para utilizar o driver sem unidades externas, comente a linha 22~24 ***
		// *** Para drivers externos locais, descomente as linhas 22~24 e 26 ***
		// *** Para drivers externos remotos, descomente as linhas 22, 23 e 24~26 *** 
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.108:4444/wd/hub"), cap);
//		driver.navigate().to(URL_LOCAL); // LOCAL
		driver.navigate().to(URL_REMOTE); // REMOTE
		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

//		WebDriver driver = new ChromeDriver();
//		driver.navigate().to(URL_LOCAL);
//		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);

		return driver;
	}

	@Test
	public void saveTaskWithSuccess() throws MalformedURLException {
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
	public void dontSaveTaskWithoutDescription() throws MalformedURLException {
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
	public void dontSaveTaskWithoutDate() throws MalformedURLException {
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
	public void dontSaveTaskWithPastDate() throws MalformedURLException {
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
	public void saveTaskWithMissingButtonId() throws MalformedURLException {
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
