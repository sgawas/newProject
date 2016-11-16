package utils;

public class Configs {

	private static String SERVER = "smtp.gmail.com";
	private static String FROM_EMAIL = "w2aispg@gmail.com";
	private static String PASSWORD = "Ispg$123";
	private static String[] TO_EMAIL = { "seleniumcoaching@gmail.com", "trainer@way2automation.com" };
	private static String SUBJECT = "Test Report";

	private static String MESSAGE_BODY = "TestMessage";
	// public static String attachmentPath=TestCore.path;
	// public static String attachmentName="Report_"+TestCore.timeStamp+".html";

	// SQL DATABASE DETAILS
	private static String DRIVER = "net.sourceforge.jtds.jdbc.Driver";
	private static String DB_CONNECTION_URL = "jdbc:jtds:sqlserver://192.101.44.22;DatabaseName=monitor_eval";
	private static String DB_USERNAME = "sa";
	private static String DB_PASSWORD = "$ql$!!1";

	// MYSQL DATABASE DETAILS
	private static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	private static String MYSQL_USERNAME = "root";
	private static String MYSQL_PASSWORD = "selenium";
	private static String MYSQL_URL = "jdbc:mysql://localhost:3306/gaurav";

	public static String getSERVER() {
		return SERVER;
	}

	public static String getFROM_EMAIL() {
		return FROM_EMAIL;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static String[] getTO_EMAIL() {
		return TO_EMAIL;
	}

	public static String getSUBJECT() {
		return SUBJECT;
	}

	public static String getMESSAGE_BODY() {
		return MESSAGE_BODY;
	}

	public static String getDRIVER() {
		return DRIVER;
	}

	public static String getDB_CONNECTION_URL() {
		return DB_CONNECTION_URL;
	}

	public static String getDB_USERNAME() {
		return DB_USERNAME;
	}

	public static String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public static String getMYSQL_DRIVER() {
		return MYSQL_DRIVER;
	}

	public static String getMYSQL_USERNAME() {
		return MYSQL_USERNAME;
	}

	public static String getMYSQL_PASSWORD() {
		return MYSQL_PASSWORD;
	}

	public static String getMYSQL_URL() {
		return MYSQL_URL;
	}

}
