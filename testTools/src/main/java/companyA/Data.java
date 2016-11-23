package companyA;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Data {
	
	private ArrayList<String> header;
	private HashMap<String, ArrayList<String>> data;
	
	public Data() {
		this.header = new ArrayList<>();
		this.data = new HashMap<>();
	}
	
	public Data(String fileLocation, String delimiter) {
		
		this.data = new HashMap<>();
		
		this.header = generateHeader(fileLocation, delimiter);
		
		ArrayList<ArrayList<String>> li = generateData(header, fileLocation, delimiter);
		
		
		
	}
	
	
	public ArrayList<String> getHeader() {
		return header;
	}
	public HashMap<String, ArrayList<String>> getData() {
		return data;
	}
	private void setHeader(ArrayList<String> header) {
		this.header = header;
	}
	
	private void setData(HashMap<String, ArrayList<String>> data) {
		this.data = data;
	}
	
	
	public void addToHashMap(String key, ArrayList<String> value) {
		this.data.put(key, value);
	}
	
	public ArrayList<String> generateHeader(String fileLocation, String delimiter) {
		
		ArrayList<String> outList = new ArrayList<String>();
		
		try {
            
            BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
            
            String record = reader.readLine();  // disgard first line of file as it is the header
            
            String[] parts = record.split(delimiter);
            
            for (int i = 0; i < parts.length; i++) {
            	outList.add(parts[i]);
            }
            
            
        }
        catch (Exception ex) {
           ex.printStackTrace();
        }
		
		return outList;
	}
	
	
	public ArrayList<ArrayList<String>> generateData(ArrayList<String> head, String fileLocation, String delimiter) {
		
		ArrayList<ArrayList<String>> columnList = new ArrayList<>();
		
		try {
	        
	        BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
	        
	        String record = reader.readLine();  // disgard first line of file as it is the header
	        
	        ArrayList<String[]> rowList = new ArrayList<>();
	        
	        while ((record = reader.readLine()) != null) { // read lines in
	        	
	        	String[] parts = record.split(delimiter);
	        	
	        	rowList.add(parts);
	            
	        }
	        
	        for (int row = 0; row < rowList.get(0).length; row++) {
	        	ArrayList<String> tempList = new ArrayList<String>();
	    		for (int col = 0; col < rowList.size()-1; col++) {
	    			String[] temp = rowList.get(col);
	    			tempList.add(temp[row]);
	    		}
	        	
	        	columnList.add(tempList);
	        	
	        	
	    	}
	        
	        
	    }
	    catch (Exception ex) {
	       ex.printStackTrace();
	    }
		
		return columnList;
		
		
	}
	

}
