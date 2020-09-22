package resources;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ReadExcel {

	public String read(int sheetnum,int col,int row) throws IOException

	{

		File src = new File("C:\\Users\\Ashwini\\eclipse-workspace\\RestReportExtent\\Excel.xlsx");

		FileInputStream fis = new FileInputStream(src);

		XSSFWorkbook wb = new XSSFWorkbook(fis);

		XSSFSheet sh =wb.getSheetAt(sheetnum);

		int rows =sh.getFirstRowNum();

		String data =sh.getRow(row).getCell(col).getStringCellValue();

		return data;




















	}


}