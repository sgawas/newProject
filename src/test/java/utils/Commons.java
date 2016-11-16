package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import core.Core;

public class Commons extends Core {

	private String mailScreenShotPath = null;

	public void captureScreenShot() {
		String timeStamp = getTimeStamp();
		String screenShotPath = System.getProperty("user.dir") + "\\Screenshot\\" + timeStamp;
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			mailScreenShotPath = screenShotPath + ".jpeg";
			FileUtils.copyFile(scrFile, new File(mailScreenShotPath));
		} catch (Exception e) {
		}
	}

	private String setTimeStamp() {

		String timeStamp = null;
		Calendar calendar = new GregorianCalendar();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		String monthString = "" + month;
		if (month < 10) {
			monthString = "0" + month;
		}

		String dateString = "" + date;
		if (date < 10) {
			dateString = "0" + date;
		}

		String hourString = "" + hour;
		if (hour < 10) {
			hourString = "0" + hour;
		}

		String minuteString = "" + minute;
		if (minute < 10) {
			minuteString = "0" + minute;
		}

		String secondString = "" + second;
		if (second < 10) {
			secondString = "0" + second;
		}
		timeStamp = year + monthString + dateString + hourString + minuteString + secondString;

		return timeStamp;
	}

	public String getTimeStamp() {
		return setTimeStamp();
	}

	public void copyFiles() {
		String timeStamp = getTimeStamp();
		String appLogPath = System.getProperty("user.dir") + "\\Logs\\Application-" + timeStamp + ".txt";
		String selLogPath = System.getProperty("user.dir") + "\\Logs\\Selenium-" + timeStamp + ".txt";
		FileInputStream input = null;
		FileOutputStream output = null;
		try {
			File appFile = new File(System.getProperty("user.dir") + "\\src\\test\\java\\logs\\Application.log");
			File selFile = new File(System.getProperty("user.dir") + "\\src\\test\\java\\logs\\Selenium.log");

			File tempAppFile = new File(appLogPath);
			File tempSelFile = new File(selLogPath);
			input = new FileInputStream(appFile);
			output = new FileOutputStream(tempAppFile);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			input.close();
			output.close();

			input = new FileInputStream(selFile);
			output = new FileOutputStream(tempSelFile);
			buffer = new byte[1024];
			length = 0;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
			input.close();
			output.close();

		} catch (Exception e) {
			System.out.println("Error in coping log files : " + e.getMessage());
		}

	}

}
