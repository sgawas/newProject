package core;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.ExcelReader;

public class Core {
	public static ExcelReader coreExcel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\Core.xlsx");
	public static ExcelReader dataExcel = new ExcelReader(System.getProperty("user.dir")+"\\src\\test\\java\\excel\\testData.xlsx");
	public static WebDriver driver = null;
	public static Properties config = null;
	public static Properties object = null;
	public static WebDriverWait wait = null;
	public static String timeStamp = null;

	public static final Logger logger = Logger.getLogger("devpinoyLogger");

	public static void startAutomation() {
		FileInputStream fileIn = null;
		try {

			logger.info("Starting automation");
			fileIn = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\properties\\Config.properties");
			config.load(fileIn);
			logger.info("Config properties file loaded");
			fileIn = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\java\\properties\\Object.properties");
			object.load(fileIn);
			logger.info("Object properties file loaded");
		} catch (Exception e) {

		}
	}

	public static void closeAutomation() {
		driver.quit();
		logger.info("Quiting the driver");
	}

}
