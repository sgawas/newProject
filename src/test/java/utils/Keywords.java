package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import core.Core;

public class Keywords extends Core {

	public boolean openBrowser(String browser) {
		try {
			if (driver == null) {
				if (config.getProperty(browser).equalsIgnoreCase("chrome")) {
					System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
					driver = new ChromeDriver();
					return true;
				} else if (config.getProperty(browser).equalsIgnoreCase("IE")) {
					System.setProperty("webdriver.ie.driver", "IEDriverSever.exe");
					driver = new InternetExplorerDriver();
					return true;
				} else if (config.getProperty(browser).equalsIgnoreCase("firefox")) {
					driver = new FirefoxDriver();
					return true;
				}
			}
		} catch (Exception e) {
			System.out.println("Exception while opening browser: " + e.getMessage());
		}
		return false;
	}

	public boolean closeBrowser() {
		if (driver != null) {
			driver.close();
			return true;
		}
		return false;
	}

	public boolean navigate(String url) {
		if (driver != null) {
			driver.get(config.getProperty(url));
			return true;
		}
		return false;
	}
	
	public boolean waitFor(String wait) throws InterruptedException{
		if(driver!=null){
			int time = Integer.parseInt(wait);
			Thread.sleep(time);
			return true;
		}
		return false;
	}
	
	public boolean click(String element, String locator){
		
		try{
			if(locator.equalsIgnoreCase("id")){
				driver.findElement(By.id(object.getProperty(element))).click();
				return true;
			}
			else if(locator.equalsIgnoreCase("xpath")){
				driver.findElement(By.xpath(object.getProperty(element))).click();
				return true;
			}
			else if(locator.equalsIgnoreCase("name")){
				driver.findElement(By.name(object.getProperty(element))).click();
				return true;
			}
			else if(locator.equalsIgnoreCase("className")){
				driver.findElement(By.className(object.getProperty(element))).click();
				return true;
			}
		}
		catch(Exception e){
			System.out.println("Exception in clicking the element : "+e.getMessage());
		}
		System.out.println("not able to find element");
		return false;
	}
}
