package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private String path;
	private FileInputStream fileIn = null;
	private FileOutputStream fileOut = null;
	private XSSFWorkbook workBook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;

	public ExcelReader(String path) {
		this.path = path;
		try {
			fileIn = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileIn);
			sheet = workBook.getSheetAt(0);
			fileIn.close();
		} catch (Exception e) {
			System.out.println("Exception in ExcelReader: " + e.getStackTrace());
		}
	}

	// returns row count in a worksheet
	public int getRowCount(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workBook.getSheetAt(index);
			int rowCount = sheet.getLastRowNum() + 1;
			return rowCount;
		}
	}

	// Returns column count in a worksheet
	public int getColumnCount(String sheetName) {
		if (!isSheetExist(sheetName)) {
			return -1;
		}

		sheet = workBook.getSheet(sheetName);
		row = sheet.getRow(0);
		if (row == null) {
			return -1;
		}
		return row.getLastCellNum();
	}

	// check if sheet exists
	private boolean isSheetExist(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1) {
			index = workBook.getSheetIndex(sheetName.toUpperCase());
			if (index == -1) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

}
