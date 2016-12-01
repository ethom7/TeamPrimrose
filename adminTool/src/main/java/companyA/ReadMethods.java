package companyA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadMethods {
	
	
	// if data were stored in ArrayList<HashMap<String, String>> data; access would be gained by data[row number]['column name']
	
	public static ArrayList<HashMap<String, String>> createListFromCSV(String fileName, String delimiter) {
		ArrayList<HashMap<String, String>> arrList = new ArrayList<HashMap<String, String>>();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String record = null;
			
			
			String line = reader.readLine();
			String[] header = line.split(delimiter);
				
			while((record = reader.readLine()) != null) {
				String[] parts = record.split(delimiter);
				HashMap<String, String> hm = new HashMap<String, String>();
				for (int i = 0; i < parts.length; i++) {
					hm.put(header[i], parts[i]);
				}
					
				arrList.add(hm);
			}

			reader.close();
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return arrList;
	}
	
	
	// stored in ArrayList<String[]> data; access would be gained by data.get(row number)[column index]
	public static ArrayList<String[]> createListFromCSV_noHeader(String fileName, String delimiter) {
		ArrayList<String[]> arrList = new ArrayList<String[]>();
		
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			
			String record = null;
			
				
			while((record = reader.readLine()) != null) {
				String[] parts = record.split(delimiter);
				
				arrList.add(parts);
			}
			
			
			reader.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return arrList;
}
}