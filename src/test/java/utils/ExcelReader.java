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
		try{
			fileIn = new FileInputStream(path);
			workBook = new XSSFWorkbook(fileIn);
			sheet = workBook.getSheetAt(0);
			fileIn.close();
		}
		catch(Exception e){
			System.out.println("Exception in ExcelReader: "+e.getStackTrace());
		}
	}
	
	

}
