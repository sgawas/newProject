package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
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
	private int rowCount(String sheetName) {
		int index = workBook.getSheetIndex(sheetName);
		if (index == -1) {
			return 0;
		} else {
			sheet = workBook.getSheetAt(index);
			int rowCount = sheet.getLastRowNum() + 1;
			return rowCount;
		}
	}

	// accessible to others
	public int getRowCount(String sheetName) {
		System.out.println("Getting row count");
		return rowCount(sheetName);
	}

	// Returns column count in a worksheet
	private int columnCount(String sheetName) {
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

	// accessible to others
	public int getColumnCount(String sheetName) {
		System.out.println("Getting column count");
		return columnCount(sheetName);
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

	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			if (rowNum <= 0) {
				return "";
			}

			int index = workBook.getSheetIndex(sheetName);
			int col_num = -1;
			if (index == -1) {
				return "";
			}

			sheet = workBook.getSheet(sheetName);
			row = sheet.getRow(0);
			for (int i = 0; i < row.getLastCellNum(); i++) {
				if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName)) {
					col_num = i;
				}
			}
			if (col_num == -1) {
				return "";
			}

			sheet = workBook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null) {
				return "";
			}
			cell = row.getCell(col_num);
			if (cell == null) {
				return "";
			}

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {

					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + 1 + "/" + cellText;

				}

				return cellText;
			}

			else if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
				return "";
			}

			else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colName + " does not exist in xls";
		}
	}
}
