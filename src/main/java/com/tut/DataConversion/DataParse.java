package com.tut.DataConversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class DataParse {

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {
		String file = "directoryDataUpdated.txt"; 

		boolean start = true;
		String address="";
		List<Map<String, Object>> informationList = new ArrayList<Map<String, Object>>();
		Map<String, Object> info = null;
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	if (start && !line.trim().isEmpty()) {
		    		start=false;
		    		info = new HashMap<String, Object>();
		    		info.put("heading", line.trim());
				//	System.out.println("heading: "+line.trim());
					
				}else if( line.toLowerCase().indexOf("office".toLowerCase()) != -1 && (!(line.toLowerCase().indexOf("post office".toLowerCase()) != -1))){
		    		//System.out.println("office : " +  line.split(" ")[0]);
		    		info.put("office", line.split(" ")[0]);
		    		info.put("address", address.trim());
		    		address = "";
		    	}else if(line.toLowerCase().indexOf("fax".toLowerCase()) != -1 || line.toLowerCase().indexOf("FACSIMILE".toLowerCase()) != -1){
		    		
		    		if(line.toLowerCase().indexOf("fax".toLowerCase()) != -1){
		    			info.put("fax", line.split(" ")[0]);
		    		}
		    		else  if(line.toLowerCase().indexOf("FACSIMILE".toLowerCase()) != -1) {
		    			info.put("FACSIMILE ", line.split(" ")[0]);
					}
		    		
		    	}else if(line.toLowerCase().indexOf("REAL ESTATE".toLowerCase()) != -1) {
		    		if(line.toLowerCase().indexOf("[0-9]".toLowerCase()) != -1){
		    			info.put("fax", line.split(" ")[0]);
		    		}
	    			info.put("REAL ESTATE ", line.split(" ")[0]);
				}
		    	else if(line.toLowerCase().indexOf("Email".toLowerCase()) != -1 || line.toLowerCase().indexOf("WEB ADDRESS".toLowerCase()) != -1) {
					//start= true;
		    		try {
		    			if(line.toLowerCase().indexOf("@".toLowerCase()) != -1){
		    				info.put("email", line.split(":")[1]);
		    			}
		    		else if(line.toLowerCase().indexOf(".COM".toLowerCase()) != -1) {
		    			info.put("WebAddress", line.split(":")[1]);
					}
						
					} catch (Exception e) {
						System.out.println(line);
						// TODO: handle exception
					}
					
	
				}
				 else if(line.toLowerCase().indexOf("NOTES".toLowerCase()) != -1 ||line.toLowerCase().indexOf("NOTES:".toLowerCase()) != -1) {
					start= true;
				//	info.put("notes", line.split(":")[1]);
					informationList.add(info);
				//	System.out.println("notes line: "+line);		
				}
				 else {
						address+=" "+line.trim();
					//	info.put("email", line.trim());
					}
		    }
		  
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("writing: data");
		writeFile("HammadFinal3.txt", new ObjectMapper().writeValueAsString(informationList));
		 
		for(Map<String, Object> objectInfo : informationList){
			 
			System.out.println("#############################################");
			for(String key : objectInfo.keySet()){
				System.out.println(key+"....."+objectInfo.get(key));
			}
		}
		

	}
	
	// FUNCTION TO WRITE DATA
		public static void writeFile(String fileName, String data)
				throws IOException {
			FileWriter fileWriter = new FileWriter(new File(fileName));
			fileWriter.write(data);
			fileWriter.flush();
			fileWriter.close();
		}

		// Function to LOAD FILE DATA
		public static BufferedReader loadFile(String file)
				throws FileNotFoundException {

			// CSVReader reader1 = new CSVReader(new FileReader("yourfile.csv"));
			InputStream in = new FileInputStream(new File(file));
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			return reader;
		}
}
