package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
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

	// returns the data from a cell
	@SuppressWarnings("deprecation")
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			if (rowNum <= 0)
				return "";

			int index = workBook.getSheetIndex(sheetName);

			if (index == -1)
				return "";

			sheet = workBook.getSheetAt(index);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				return "";
			cell = row.getCell(colNum);
			if (cell == null)
				return "";

			if (cell.getCellType() == Cell.CELL_TYPE_STRING)
				return cell.getStringCellValue();
			else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {

				String cellText = String.valueOf(cell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// format in form of M/D/YY
					double d = cell.getNumericCellValue();

					Calendar cal = Calendar.getInstance();
					cal.setTime(HSSFDateUtil.getJavaDate(d));
					cellText = (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
					cellText = cal.get(Calendar.MONTH) + 1 + "/" + cal.get(Calendar.DAY_OF_MONTH) + "/" + cellText;

				}

				return cellText;
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				return "";
			else
				return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {

			e.printStackTrace();
			return "row " + rowNum + " or column " + colNum + " does not exist  in xls";
		}
	}

	public boolean addSheet(String sheetName) {

		FileOutputStream fileout;
		try {
			workBook.createSheet(sheetName);
			fileout = new FileOutputStream(this.path);
			workBook.write(fileout);
			fileout.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for (int i = 2; i <= getRowCount(sheetName); i++) {
			if(getCellData(sheetName,colName,i).equalsIgnoreCase(cellValue)){
				return i;
			}
		}
		return -1;
	}
	
	// returns true if data is set successfully else false
		public boolean setCellData(String sheetName, String colName, int rowNum, String data, String url) {

			try {
				fileIn = new FileInputStream(path);
				workBook = new XSSFWorkbook(fileIn);

				if (rowNum <= 0)
					return false;

				int index = workBook.getSheetIndex(sheetName);
				int colNum = -1;
				if (index == -1)
					return false;

				sheet = workBook.getSheetAt(index);

				row = sheet.getRow(0);
				for (int i = 0; i < row.getLastCellNum(); i++) {

					if (row.getCell(i).getStringCellValue().trim().equalsIgnoreCase(colName))
						colNum = i;
				}

				if (colNum == -1)
					return false;
				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(rowNum - 1);
				if (row == null)
					row = sheet.createRow(rowNum - 1);

				cell = row.getCell(colNum);
				if (cell == null)
					cell = row.createCell(colNum);

				cell.setCellValue(data);
				XSSFCreationHelper createHelper = workBook.getCreationHelper();

				// cell style for hyperlinks

				CellStyle hlink_style = workBook.createCellStyle();
				XSSFFont hlink_font = workBook.createFont();
				hlink_font.setUnderline(XSSFFont.U_SINGLE);
				hlink_font.setColor(IndexedColors.BLUE.getIndex());
				hlink_style.setFont(hlink_font);
				// hlink_style.setWrapText(true);

				@SuppressWarnings("deprecation")
				XSSFHyperlink link = createHelper.createHyperlink(XSSFHyperlink.LINK_FILE);
				link.setAddress(url);
				cell.setHyperlink(link);
				cell.setCellStyle(hlink_style);

				fileOut = new FileOutputStream(path);
				workBook.write(fileOut);

				fileOut.close();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
		
		// returns true if data is set successfully else false
		public boolean setCellData(String sheetName, String colName, int rowNum, String data) {
			try {
				fileIn = new FileInputStream(path);
				workBook = new XSSFWorkbook(fileIn);

				if (rowNum <= 0)
					return false;

				int index = workBook.getSheetIndex(sheetName);
				int colNum = -1;
				if (index == -1)
					return false;

				sheet = workBook.getSheetAt(index);

				row = sheet.getRow(0);
				for (int i = 0; i < row.getLastCellNum(); i++) {
					// System.out.println(row.getCell(i).getStringCellValue().trim());
					if (row.getCell(i).getStringCellValue().trim().equals(colName))
						colNum = i;
				}
				if (colNum == -1)
					return false;

				sheet.autoSizeColumn(colNum);
				row = sheet.getRow(rowNum - 1);
				if (row == null)
					row = sheet.createRow(rowNum - 1);

				cell = row.getCell(colNum);
				if (cell == null)
					cell = row.createCell(colNum);

				cell.setCellValue(data);

				fileOut = new FileOutputStream(path);

				workBook.write(fileOut);

				fileOut.close();

			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
}
