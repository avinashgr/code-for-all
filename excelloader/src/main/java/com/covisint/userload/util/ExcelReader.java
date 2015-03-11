package com.covisint.userload.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class ExcelReader {
	private static ExcelReader instance;
	
	private ExcelReader(){};
	
	public static ExcelReader getInstance(){
		if(null==instance){
			instance = new ExcelReader();			
		}
		return instance; 
	}
	/**
	 * Reads the sheet
	 * @param sheet
	 * @return
	 */
	private List<Map<String, Object>> readSheet(XSSFSheet sheet){
		// Iterate through each rows one by one

		int i=0;
		Map<String,Object> rowMapper =null;
		List<String> _headers = new ArrayList<String>();
		List<Map<String,Object>> rows= new ArrayList<Map<String,Object>>();
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			rowMapper = new HashMap<String,Object>();
			// For each row, iterate through all the columns
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if(i==0){
					_headers.add(cell.getStringCellValue());
				}else{
					rowMapper.put(_headers.get(cell.getColumnIndex()), getCellValue(cell));
				}
			}
			if(i>0){
			   rows.add(row.getRowNum()-1,rowMapper);
			}
			i++;
		}
		return rows;
	}
	/**
	 * Gets the cell value based on the Cell type
	 * @param cell
	 * @return
	 */
	private static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		case Cell.CELL_TYPE_STRING:
			return (cell.getStringCellValue());
		default:					
		
		}
        return null;
	}
	/**
	 * Reads the xlsx file to sheets
	 * @param input
	 * @return
	 */
	public  XSSFSheet[] readFile(String input) {
		try {
			FileInputStream file = new FileInputStream(new File(input));
			XSSFWorkbook workbook;
			XSSFSheet[] sheets;
			workbook = new XSSFWorkbook(file);
			int nSheets = workbook.getNumberOfSheets();
			if(0<nSheets){
				sheets =  new XSSFSheet[nSheets];
				for (int i = 0; i < nSheets; i++) {
					sheets[i] = workbook.getSheetAt(i);
				}
				return sheets;
			}
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	public void writeToFile(String filePath,String output) {
		// Create Workbook instance holding reference to .xlsx file
		try {
			FileOutputStream file = new FileOutputStream(new File(filePath));
			Writer out = new BufferedWriter(new OutputStreamWriter(file,"utf-8"));
			out.append(output);
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * Reads sheets and creates the json object based on the rows
	 * @param sheets
	 * @return
	 */
	public String getJSON(XSSFSheet[] sheets) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>() ;
		for(XSSFSheet sheet:sheets){
			 list.addAll(getInstance().readSheet(sheet));
		}
		return JSONUtils.getInstance().toString(list);
	}
}
