package com.tut.DataConversion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * Hello world!
 *
 */
public class App {
	static BufferedReader reader;

	public static void main(String[] args) throws IOException {
	
		// loading file
		reader = loadFile("directoryDataUpdated.txt");
		boolean check = true ;
		String line = "";
		List<String> body = new ArrayList<String>();
		List<String> heading = new ArrayList<String>();

		int fst = 0;
		int c = 0;
		while ((line = reader.readLine()) != null) {
			
			if(line.trim().isEmpty()){
				check = true;
				System.out.println("line :" + c + " data: " + line);
				//body.add(line);
				continue;
			}
			else if (check) {
				body.add("<key>"+line+"</key>");
				heading.add("<key>"+line+"</key>");
				System.out.println("line :" + c + " data: " + line);
				check = false;
				fst = 0;
			}
			else {
				
				if(fst==0){
					System.out.println("line :" + c + " data: " + line);
					body.add("<string>"+line);
					fst=1;
				}
				
				else if(line.toLowerCase().indexOf("Notes:".toLowerCase())!=-1 || line.toLowerCase().indexOf("Notes".toLowerCase())!=-1){
					System.out.println("line :" + c + " data: " + line);
					body.add("");
					body.add(line+"</string>");
				}
				else {
					
					System.out.println("line :" + c + " data: " + line);
					body.add(line);
					//check = false;
				}
				
				
			}

		//	System.out.println("line :" + c + " data: " + line);
			c++;
		}
		

		String resp = new ObjectMapper().writeValueAsString(heading);
		System.out.println("whole data String : " + resp);
		
		String resp1 = new ObjectMapper().writeValueAsString(body);
		System.out.println("whole data heading : " + resp1);

		writeFile("completed.txt", resp1);
		
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
