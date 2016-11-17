package utils;

import java.util.Hashtable;

import core.Core;

public class Util extends Core {

	public static boolean isTCExecutable(String testCaseName) {

		String sheetName = "TestCases";
		int rows = coreExcel.getRowCount(sheetName);
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			if (coreExcel.getCellData(sheetName, 0, rowNum).equalsIgnoreCase(testCaseName)) {
				if (coreExcel.getCellData(sheetName, "Runmode", rowNum).equalsIgnoreCase("Y")
						|| coreExcel.getCellData(sheetName, "Runmode", rowNum).equalsIgnoreCase("Yes")) {
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isSuiteExecutable(String suiteName) {

		String sheetName = "TestSuitesRunMode";
		int rows = coreExcel.getRowCount(sheetName);
		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			if (coreExcel.getCellData(sheetName, 0, rowNum).equalsIgnoreCase(suiteName)) {
				if (coreExcel.getCellData(sheetName, "Runmode", rowNum).equalsIgnoreCase("Y")
						|| coreExcel.getCellData(sheetName, "Runmode", rowNum).equalsIgnoreCase("Yes")) {
					return true;
				} else {
					return false;
				}
			}
		}
		return false;
	}

	@SuppressWarnings("null")
	public static Object[][] getTestData(String sheetName) {

		Object[][] data = null;
		int rowStart = 1;
		int rows = 0;
		while (!dataExcel.getCellData(sheetName, "RunMode", rowStart + rows).equals("")) {
			rows++;
		}
		int cols = 0;
		while (!dataExcel.getCellData(sheetName, cols, 1).equals("")) {
			cols++;
		}

		Hashtable<String, String> table = null;
		for (int rowNum = 2; rowNum <= rows; rowNum++) {

			table = new Hashtable<String, String>();

			for (int colNum = 0; colNum < cols; colNum++) {
				table.put(dataExcel.getCellData(sheetName, colNum, 1),
						dataExcel.getCellData(sheetName, colNum, rowNum));
			}
			data[rowNum - 2][0] = table;
		}

		return data;
	}
}
