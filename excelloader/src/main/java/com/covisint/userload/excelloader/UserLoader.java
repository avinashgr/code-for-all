package com.covisint.userload.excelloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.covisint.userload.util.ExcelReader;
import com.covisint.userload.util.JSONUtils;
import com.covisint.userload.vo.UserVO;

/**
 * Reads the excel and updates the user bean
 * @author cmiagr0
 *
 */
public class UserLoader {
	public static void main(String[] args) {
		try {

			  boolean moreRequired = true;
			  while(moreRequired){
				  String requireMore;
				  try{
				  processFileInput();
				  }catch(Exception e){
					 System.err.println("Something went wrong! Try another maybe?");
				  }
				  requireMore = getUserInput("Any more?",new String[]{"y", "n"});
				  if(requireMore.equalsIgnoreCase("y")){
					  moreRequired = true;
				  }else{
					  moreRequired = false;
				  }
			  }
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static String getUserInput(String value, String[] validValues) {
		  String input = "";
		//  prompt the user to enter their name
		  System.out.print(String.format("%s ",value));
		  if(null!=validValues){
			  System.out.print(String.format("%s or %s:", validValues));
		  }

		  //  open up standard input
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  //  read the username from the command-line; need to use try/catch with the
		  //  readLine() method
		  try {
			  input = br.readLine();
			  boolean validValueEntered=false;
			  while(!validValueEntered){
				  validValueEntered = checkForValidInput(input, validValues);
			  };		
		  } catch (IOException ioe) {
		     System.out.println(String.format("IO error trying to read the %s ",value));
		     System.exit(1);
		  }
		return input;
	}

	
	private static void processFileInput() throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter excel file (with path):");
		String input = sc.next();
		if(input.contains("\"")){
		input = input.replace("\"", "");
		 System.out.println("input fixed to:"+input);
		}
		ExcelReader reader = ExcelReader.getInstance();
		XSSFSheet[] sheets = reader.readFile(input);
		String json= reader.getJSON(sheets);
		reader.writeToFile(input+".txt", json);	
		JSONUtils utils = JSONUtils.getInstance();			
		List<UserVO> vo = utils.<UserVO>getListOfVO(json);
		System.out.println("Done! you can find the json formatted file  at: "+input+".txt");
	}

	private static boolean checkForValidInput(String input, String[] validValues) {
		boolean validValueEntered = false;
		if(null!=validValues){
		  for(String validValue: validValues){
			  if(validValue.equalsIgnoreCase(input)){
				  validValueEntered =true;
				  break;
			  }
		  }
		}else{
			validValueEntered = true;
		}
		return validValueEntered;
	}
	
}
