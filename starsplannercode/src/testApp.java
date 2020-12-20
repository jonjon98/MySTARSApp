
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class testApp {
	public static void main(String[] args) {
		
		// Delimiter "~" test for course and course index
		/*
		String testest = "CE2020~CE2021~";
		String arraytest[] = new String[99];
		arraytest = testest.split("~");
		for(String i : arraytest) {
			System.out.println(i);
		}
		*/
		
		// Hashmap testing
		/*
		HashMap<Integer, Integer> testmap = new HashMap<Integer, Integer>();
		testmap.put(1, 2);
		testmap.put(2, 3);
		testmap.put(2, 10);
		for(int i : testmap.keySet()) {
			System.out.println(testmap.get(i));
		}
		*/
		/*
		Student fixedstudentArray[];
		try {
			int tempCount = 0;
			File studentCSV = new File("studentInformation.csv");
			Scanner csvReader = new Scanner(studentCSV);
			Scanner sizeReader = new Scanner(studentCSV);
			while(sizeReader.hasNextLine()) {
				sizeReader.nextLine();
				tempCount++;
				//System.out.println(tempCount); // Check number of lines for CSV
			}
			fixedstudentArray = new Student[tempCount-1];
			sizeReader.close();
			tempCount = 0;
			while(csvReader.hasNextLine()) {
				String studentData[] = csvReader.nextLine().split(",");
				if(tempCount > 1){
					for(String i : studentData) {
						fixedstudentArray[tempCount-1] = new Student();
					}
				}
				tempCount++;
			}
			csvReader.close();
		}
		catch(Exception e) {
			System.out.println("Error reading studentCSV file!");
		}*/
		/*
		try {
		    String str = "Hello,whoareyou,yoyo";
		    BufferedWriter writer = new BufferedWriter(new FileWriter("testWrite.csv"));
		    writer.write(str);
		    writer.append("\n" + str);
		    writer.close();
		}
		catch(Exception e) {
			System.out.println("Error writing file!");
		}
		*/
		/*
		String yolo = "sian";
		int temp = 85;
		for(int i=0; i<5; i++) {
			yolo = yolo + temp + "~";
		}
		System.out.println(yolo);
		*/
		/*
		System.out.println("| " + "Courses" 	+ "\t| " + "Index" 	+ "\t|");
		System.out.println("| " + "CE2004" 		+ "\t| " + "1" 		+ "\t|");
		System.out.println("| " + "CE2107" 		+ "\t| " + "13" 	+ "\t|");
		*/
		/*
		System.out.println("|Monday\t\t|");
		System.out.println("|Tuesday\t|");
		System.out.println("|Wednesday\t|");
		System.out.println("|Thursday\t|");
		System.out.println("|Friday\t\t|");
		System.out.println("|Saturday\t|");
		System.out.println("|Sunday\t\t|");
		*/
		/*
		 * 			        Iterator<TimeSlot> iter = myTimeTable.iterator(); 
			  
			        System.out.println("\nThe iterator values"
			                           + " of list are: "); 
			        while (iter.hasNext()) { 
			            System.out.print(iter.next().getType() + " "); 
			        } 
		 */
		String identity = "ML0001~";
		String temp = "U12345~U1234~";
		String temp2 = "";
		//System.out.println(StringUtils.contains(temp, identity + "~"));
		System.out.println(temp + identity);
	}
}
