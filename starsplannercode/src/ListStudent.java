import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;

public class ListStudent {
	
	private Student fixedStudentArray[];
	private int fixedstudentArraySize;
	
	private String currentStudentIdentity;
	private int currentStudentIndex;
	
	public ListStudent() {
		readStudentCSV();
	}
	public ListStudent(String identity) {
		currentStudentIdentity = identity;
		readStudentCSV();
	}
	public void readStudentCSV() {

		String studentFileName = "studentInformation.csv";
		String line;
        int lineIndex = 0;
        try (BufferedReader sizeReader = new BufferedReader(new FileReader(studentFileName))) {
            while ((line = sizeReader.readLine()) != null) {
            	lineIndex++;
            }
            sizeReader.close();
            this.fixedstudentArraySize = lineIndex - 1;
            this.fixedStudentArray = new Student[fixedstudentArraySize];
        } 
		catch(Exception e) {
			System.out.println("|Error: Problem with studentInformation.csv file|");
			e.printStackTrace();
		}
        lineIndex = 0;
        try (BufferedReader csvReader = new BufferedReader(new FileReader(studentFileName))) {
            while ((line = csvReader.readLine()) != null) {
            	if(lineIndex>0) {
	                String[] studentData = line.split(",");
	                boolean tempGender;
	                if(StringUtils.equals(studentData[3], "FALSE")) {
	                	tempGender = false;
	                }
	                else {
	                	tempGender = true;
	                }
	                this.fixedStudentArray[lineIndex-1] = new Student(studentData[0], studentData[1], studentData[2], tempGender, studentData[4], studentData[5], studentData[6], studentData[7], Integer.parseInt(studentData[8]));
            	}
            	lineIndex++;
            }
            lineIndex = 0;
            for(Student i : fixedStudentArray) {
            	if(StringUtils.equals(i.getIdentity(), currentStudentIdentity)) {
            		currentStudentIndex = lineIndex;
            		//System.out.println(currentStudentIndex);
            	}
            	lineIndex += 1;
            }
            csvReader.close();
        }
		catch(Exception e) {
			System.out.println("|Error: Problem with studentInformation.csv file|");
			e.printStackTrace();
		}
	}
	
	public void writeStudentCSV() {

		try {
		    BufferedWriter studentWriter = new BufferedWriter(new FileWriter("studentInformation.csv"));
		    studentWriter.write("identity,email,name,gender,school,nationality,course,index,AU" + "\n");
		    for(Student i : fixedStudentArray) {
		    String tempGender;
		    if(i.getGender()) {
		    	tempGender = "TRUE";
		    }
		    else {
		    	tempGender = "FALSE";
		    }
		    studentWriter.append(
		    	i.getIdentity() +","+ 
		    	i.getEmail() +","+ 
		    	i.getName() +","+ 
		    	tempGender +","+ 
		    	i.getSchool() +","+ 
		    	i.getNationality() +","+ 
		    	i.getCourse() +","+ 
		    	i.getIndex() +","+ 
		    	i.getAU() + "\n"
		    	);
		    }
		    studentWriter.close();
		}
		catch(Exception e) {
			System.out.println("|Error: Problem with studentInformation.csv file|");
			e.printStackTrace();
		}
		readStudentCSV();
	}
	
	public Student[] editStudentCSV() {
		readStudentCSV();
		return this.fixedStudentArray;
	}
	
	public String addStudent(String userIdentity, String userEmail, String userName, boolean userGender, String userSchool, String userNationality, String userPassword) {

		String loginFileName = "loginInformation.csv";
		String loginData[] = null;
		String loginHeader = null;
		int loginFileSize;
		
		String line;
        int lineIndex = 0;
        try (BufferedReader sizeReader = new BufferedReader(new FileReader(loginFileName))) {
            while ((line = sizeReader.readLine()) != null) {
            	lineIndex++;
            }
            sizeReader.close();
            loginFileSize = lineIndex - 1;
            loginData = new String[loginFileSize];
            lineIndex = 0;
        } 
		catch(Exception e) {
			System.out.println("|Error: Problem with loginInformation.csv file|");
			e.printStackTrace();
		}
        try (BufferedReader loginReader = new BufferedReader(new FileReader(loginFileName))) {
            while ((line = loginReader.readLine()) != null) {
            	if(lineIndex == 0) {
            		loginHeader = line;
            	}
            	else {
            		String[] currentLogin = line.split(",");
            		if(StringUtils.equals(userIdentity, currentLogin[0])) {
            			return "|Error: Student not added. Identity already exists in loginData, Identity must be unique|";
            		}
            		else {
            			loginData[lineIndex-1] = line;
            		}
            	}
            	lineIndex++;
            }
            loginReader.close();
        } 
		catch(Exception e) {
			System.out.println("|Error: Problem with loginInformation.csv file|");
			e.printStackTrace();
		}
        
		try {
		    BufferedWriter loginWriter = new BufferedWriter(new FileWriter("loginInformation.csv"));
		    loginWriter.write(loginHeader + "\n");
		    for(String i : loginData) { 	
		    	loginWriter.append(i + "\n");
		    }
		    loginWriter.append(userIdentity + "," + userPassword + "," + "FALSE");
		    loginWriter.close();
		}
		catch(Exception e) {
			System.out.println("|Error: Problem with loginInformation.csv file|");
			e.printStackTrace();
		}
		
		try {
		    BufferedWriter studentWriter = new BufferedWriter(new FileWriter("studentInformation.csv"));
		    studentWriter.write("identity,email,name,gender,school,nationality,course,index,AU" + "\n");
		    //Student[] tempStudentArray = editStudentCSV();
		    for(Student i : fixedStudentArray) {
			    String tempGender;
			    if(i.getGender()) {
			    	tempGender = "TRUE";
			    }
			    else {
			    	tempGender = "FALSE";
			    }
			    studentWriter.append(
			    	i.getIdentity() +","+ 
			    	i.getEmail() +","+ 
			    	i.getName() +","+ 
			    	tempGender +","+ 
			    	i.getSchool() +","+ 
			    	i.getNationality() +","+ 
			    	i.getCourse() +","+ 
			    	i.getIndex() +","+ 
			    	i.getAU() + "\n"
			    	);
		    }
		    String tempGender;
		    if(userGender) {
		    	tempGender = "TRUE";
		    }
		    else {
		    	tempGender = "FALSE";
		    }
		    studentWriter.append(userIdentity + "," + userEmail + "," + userName + "," + tempGender + "," + userSchool + "," + userNationality + "," + "" + "," + ""  + "," + 0 + "\n");
		    studentWriter.close();
		    readStudentCSV();
		    System.out.println("| Identity | Email | Name | Gender | School | Nationality |");
		    for(Student i : fixedStudentArray) {
		    	String tempGender2 = "";
		    	if(i.getGender()) {
		    		tempGender2 = "Female";
		    	}
		    	else {
		    		tempGender2 = "Male";
		    	}
		    	System.out.println("| "+
		    			i.getIdentity() +" | "+
		    			i.getEmail() +" | "+
		    			i.getName() +" | "+
		    			tempGender2 +" | "+
		    			i.getSchool() +" | "+
		    			i.getNationality() +" |"
		    			);
		    }
		}
		catch(Exception e) {
			System.out.println("|Error: Problem with studentInformation.csv file|");
			e.printStackTrace();
		}
		return "|Student has been successfully added|";
	}
	
	public void addStudentToCourse(String identity, String courseID, int courseIndex) {
		int studentIndex = 0, foundIndex = 0;
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(identity, i.getIdentity())){
				foundIndex = studentIndex;
			}
			studentIndex++;
		}
		
		String tempCourses = fixedStudentArray[foundIndex].getCourse();
		String tempIndexes = fixedStudentArray[foundIndex].getIndex();
		fixedStudentArray[foundIndex].setCourse(tempCourses + courseID + "~");
		fixedStudentArray[foundIndex].setIndex(tempIndexes + courseIndex + "~");
		ListCourse tempListCourse = new ListCourse();
		int courseAU = tempListCourse.findAU(courseID, courseIndex);
		fixedStudentArray[foundIndex].setAU(courseAU + fixedStudentArray[foundIndex].getAU());
		writeStudentCSV();
	}
	
	public String getIndexList(String courseID, int courseIndex) {
		System.out.print("|Students Enrolled for CourseID " + courseID + ", courseIndex " + courseIndex + "|\n");
		String temp1 = "| Name | Gender | Nationality |\n";
		String temp2 = "";
		for(Student i : fixedStudentArray) {
			String[] tempStudentCourse = i.getCourse().split("~");
			int tempStudentDataIndex = 0;
			int tempFoundCourseID = 0;
			for(String j : tempStudentCourse) {
				if(StringUtils.equals(j, courseID)) {
					tempFoundCourseID = tempStudentDataIndex;
					String[] tempStudentIndex = i.getIndex().split("~");
					if(Integer.parseInt(tempStudentIndex[tempFoundCourseID]) == courseIndex) {
						String tempGender;
						if(i.getGender()) {
							tempGender = "Female";
						}
						else {
							tempGender = "Male";
						}
						temp2 += "| " + i.getName() + " | " + tempGender + " | " + i.getNationality() + " |\n";
					}
				}
				tempStudentDataIndex++;
			}
		}
		if(StringUtils.equals(temp2, "")) {
			temp2 = "| None |";
		}
		return temp1 + temp2;
	}
	
	public String getCourseList(String courseID) {

		System.out.print("|Students Enrolled for CourseID " + courseID + "|\n");
		String temp1 = "| Name | Gender | Nationality |\n";
		String temp2 = "";
		for(Student i : fixedStudentArray) {
			String[] tempStudentCourse = i.getCourse().split("~");
			int tempFoundCourseID = 0;
			for(String j : tempStudentCourse) {
				if(StringUtils.equals(j, courseID)) {
					String tempGender;
					if(i.getGender()) {
						tempGender = "Female";
					}
					else {
						tempGender = "Male";
					}
					temp2 += "| " + i.getName() + " | " + tempGender + " | " + i.getNationality() + " |\n";
				}
			}
		}
		if(StringUtils.equals(temp2, "")) {
			temp2 = "| None |";
		}
		return temp1 + temp2;
	}
	
	public String getRegisteredCourses() {
		return fixedStudentArray[currentStudentIndex].getCourse() + "&" + fixedStudentArray[currentStudentIndex].getIndex();
	}
	
	public String getRegisteredCourses(String identity) {
		String tempString = "";
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(i.getIdentity(), identity)) {
				tempString = i.getCourse() + "&" + i.getIndex();
			}
		}
		return tempString;
	}
	
	public void dropRegisteredCourse(String identity, String courseID, int courseIndex) {
		int tempIndex = 0;
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(i.getIdentity(), identity)) {
				String[] tempCourseList = i.getCourse().split("~");
				String[] tempIndexList = i.getIndex().split("~");
				String newCourseList = "";
				String newIndexList = "";
				for(int j=0; j<tempCourseList.length; j++) {
					if(!(StringUtils.equals(tempCourseList[j], courseID))) {
						newCourseList += (tempCourseList[j]+"~");
						newIndexList += (tempIndexList[j]+"~");
					}
				}
				if((!(StringUtils.equals(newCourseList, "~")) && (!(StringUtils.equals(newIndexList, "~"))))){
					fixedStudentArray[tempIndex].setCourse(newCourseList);
					fixedStudentArray[tempIndex].setIndex(newIndexList);
					writeStudentCSV();
					String wer = fixedStudentArray[tempIndex].getCourse();
					//System.out.println("!!!!!!!YES" + wer);
				}
				else {
					fixedStudentArray[tempIndex].setCourse("");
					fixedStudentArray[tempIndex].setIndex("");
					//System.out.println("!!!!HIT2" + newCourseList);
					writeStudentCSV(); 
				}
				ListCourse tempListCourse = new ListCourse();
				//System.out.println("!!!!HIT3," + tempListCourse.findAU(courseID, courseIndex) + i.getAU());
				fixedStudentArray[tempIndex].setAU(i.getAU() - tempListCourse.findAU(courseID, courseIndex));
			}
			tempIndex++;
		}
		writeStudentCSV();
	}
	
	public void waitListNotify(String identity, String courseID, int courseIndex){
		// javamail
		try {
			javamailutil sending = new javamailutil();
			javamailutil.sendMail("2002object@gmail.com", "WaitList Notification", "Congrats, You have been added to " + courseID + ", Index " + courseIndex);
		}
		catch(Exception e) {
			System.out.println("|Something went wrong|");
		}
	}
	
	public void waitListAdded(String identity, String courseID, int courseIndex){
		// javamail
		try {
			javamailutil sending = new javamailutil();
			javamailutil.sendMail("2002object@gmail.com", "WaitList Notification", "You have been added to the wait list of Course " + courseID + ", Index " + courseIndex);
		}
		catch(Exception e) {
			System.out.println("|Something went wrong|");
		}
	}
	
	public boolean studentExists(String identity) {
		boolean flag = false;
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(i.getIdentity(), identity)) {
				flag = true;
			}
		}
		return flag;
	}

	public boolean courseExistenceInStudent (String identity, String courseID, int courseIndex) {

		boolean flag = false;
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(i.getIdentity(), identity)) {
				String temp = i.getCourse();
				if(!(StringUtils.isBlank(temp))) {
					String tempCourse[] = temp.split("~");
					for(String j : tempCourse) {
						if(StringUtils.equals(j, courseID)) {
							flag = true;
						}
					}
				}
			}
		}
		return flag;
	}
	
	public void swapIndex(String currentIdentity, String targetIdentity, String currentCourse, String targetCourse, String currentIndex, String targetIndex) {

		int student1Index = 0;
		int student2Index = 0;
		int count = 0;
		for(Student i : fixedStudentArray) {
			if(StringUtils.equals(currentIdentity, i.getIdentity())) {
				student1Index = count;
			}
			if(StringUtils.equals(targetIdentity, i.getIdentity())) {
				student2Index = count;
			}
			count++;
		}
		fixedStudentArray[student1Index].setCourse(currentCourse);
		fixedStudentArray[student1Index].setIndex(currentIndex);
		fixedStudentArray[student2Index].setCourse(targetCourse);
		fixedStudentArray[student2Index].setCourse(targetIndex);
		writeStudentCSV();
	}
	
	public String retrieveTimeTable(String identity) {


		writeStudentCSV();
		ArrayList<TimeSlot> myTimetable = new ArrayList<TimeSlot>();
		ListCourse listofCourses = new ListCourse();
		String tempWaitList = listofCourses.studentExistenceInWaitList(identity);
		String waitListID = "";
		String waitListIndex = "";
		if(!(StringUtils.equals(tempWaitList, "&"))) {
			String[] tempWaitList2 =  tempWaitList.split("&");
			waitListID = tempWaitList2[0];
			waitListIndex = tempWaitList2[1];
		}
		else {
			tempWaitList = "";
		}
		String temp12 = getRegisteredCourses();
		if(!(StringUtils.equals(temp12, "&"))) {
			String[] tempRetrieve1 = temp12.split("&");
			String[] tempRegisteredID = (tempRetrieve1[0]+waitListID).split("~");
			String[] tempRegisteredIndex = (tempRetrieve1[1]+waitListIndex).split("~");
			int tempSize = tempRegisteredID.length;
			
			int[] lecStart = new int[tempSize];
			int[] lecEnd = new int[tempSize];
			int[] lecDay = new int[tempSize];
			
			int[] tutStart = new int[tempSize];
			int[] tutEnd = new int[tempSize];
			int[] tutDay = new int[tempSize];
					
			int[] labStart = new int[tempSize];
			int[] labEnd = new int[tempSize];
			int[] labDay = new int[tempSize];
			
			for(int i=0; i<tempSize; i++) {
				String tempString1 = listofCourses.getTimeDay(tempRegisteredID[i], Integer.parseInt(tempRegisteredIndex[i]));
				String[] tempString2 = tempString1.split("&");
				String[] tempStringStartTime = tempString2[0].split("~");
				String[] tempStringEndTime = tempString2[1].split("~");
				String[] tempStringDay = tempString2[2].split("~");
				
				lecStart[i] = Integer.parseInt(tempStringStartTime[0]);
				lecEnd[i] = Integer.parseInt(tempStringEndTime[0]);
				lecDay[i] = Integer.parseInt(tempStringDay[0]);
				
				tutStart[i] = Integer.parseInt(tempStringStartTime[1]);
				tutEnd[i] = Integer.parseInt(tempStringEndTime[1]);
				tutDay[i] = Integer.parseInt(tempStringDay[1]);
				
				labStart[i] = Integer.parseInt(tempStringStartTime[2]);
				labEnd[i] = Integer.parseInt(tempStringEndTime[2]);
				labDay[i] = Integer.parseInt(tempStringDay[2]);
			}                                          
			for(int i=0; i<tempSize; i++) {
				boolean waiting = false;
				if(StringUtils.contains(waitListID, tempRegisteredID[i])) {
					waiting = true;
				}
				else {
					waiting = false;
				}
				if(lecStart[i] != 2401) {
					TimeSlot tempTimeSlot = new TimeSlot(tempRegisteredID[i], Integer.parseInt(tempRegisteredIndex[i]), lecStart[i], lecEnd[i], lecDay[i], "lec", waiting);
					myTimetable.add(tempTimeSlot);
				}
				if(tutStart[i] != 2401) {
					TimeSlot tempTimeSlot = new TimeSlot(tempRegisteredID[i], Integer.parseInt(tempRegisteredIndex[i]), tutStart[i], tutEnd[i], tutDay[i], "tut", waiting);
					myTimetable.add(tempTimeSlot);
				}
				if(labStart[i] != 2401) {
					TimeSlot tempTimeSlot = new TimeSlot(tempRegisteredID[i], Integer.parseInt(tempRegisteredIndex[i]), labStart[i], labEnd[i], labDay[i], "lab", waiting);
					myTimetable.add(tempTimeSlot);
				}
			}
		}
			myTimetable.sort(new TimeSlotStartTimeSorter());
			
			Iterator<TimeSlot> timetableIterator = myTimetable.iterator();
			String monday = "[Monday]";
			String tuesday = "\n[Tuesday]";
			String wednesday = "\n[Wednesday]";
			String thursday = "\n[Thursday]";
			String friday = "\n[Friday]";
			String saturday = "\n[Saturday]";
			String sunday = "\n[Sunday]";
			while (timetableIterator.hasNext()) {
				TimeSlot tempTimeSlot = timetableIterator.next();
				String isItWaiting = "";
				if(tempTimeSlot.getWaiting()) {
					isItWaiting = "/WaitList";
				}
				else {
					isItWaiting = "";
				}
				if(tempTimeSlot.getDay() == 0) {
					monday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 1) {
					tuesday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 2) {
					wednesday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 3) {
					thursday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 4) {
					friday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 5) {
					saturday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				if(tempTimeSlot.getDay() == 6) {
					sunday += "\t[" + tempTimeSlot.getStartTime() + "\t" + tempTimeSlot.getCourseID()+"/"+tempTimeSlot.getCourseIndex()+"/"+tempTimeSlot.getType()+ isItWaiting + "\t" + tempTimeSlot.getEndTime() + "]";
				}
				timetableIterator.remove();
			}
			myTimetable.clear();
			return monday + tuesday + wednesday + thursday + friday + saturday + sunday + "\n";
	}
	
	public int getAUofStudent (String identity) {
		int temp = 0;
		for(Student i: fixedStudentArray) {
			if(StringUtils.equals(identity, i.getIdentity())) {
				temp = i.getAU();
			}
		}
		return temp;
	}
}
