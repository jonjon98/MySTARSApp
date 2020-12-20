
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

import javax.mail.MessagingException;

import org.apache.commons.lang3.StringUtils;

public class ListCourse {
	
	private Course fixedcourseArray[];
	private int fixedcourseArraySize;
	
	public ListCourse() {
		readCourseCSV();
	}
	
	public void readCourseCSV() {
		String courseFileName = "courseInformation.csv";
		String line;
        int lineIndex = 0;
        try (BufferedReader sizeReader = new BufferedReader(new FileReader(courseFileName))) {
            while ((line = sizeReader.readLine()) != null) {
            	lineIndex++;
            }
            sizeReader.close();
            this.fixedcourseArraySize = lineIndex - 1;
            this.fixedcourseArray = new Course[fixedcourseArraySize];
        } 
		catch(Exception e) {
			System.out.println("|Error: Problem with courseInformation.csv file|");
			e.printStackTrace();
		}
        lineIndex = 0;
        try (BufferedReader csvReader = new BufferedReader(new FileReader(courseFileName))) {
            while ((line = csvReader.readLine()) != null) {
            	if(lineIndex>0) {
	                String[] courseData = line.split(",");
	                this.fixedcourseArray[lineIndex-1] = new Course(courseData[0], Integer.parseInt(courseData[1]), courseData[2], courseData[3], courseData[4], courseData[5], Integer.parseInt(courseData[6]), Integer.parseInt(courseData[7]), Integer.parseInt(courseData[8]));
            	}
            	lineIndex++;
            }
            csvReader.close();
        }
		catch(Exception e) {
			System.out.println("|Error: Problem with courseInformation.csv file|");
			e.printStackTrace();
		}
	}
	
	public void writeCourseCSV() {
		try {
		    BufferedWriter courseWriter = new BufferedWriter(new FileWriter("courseInformation.csv"));
		    courseWriter.write("courseID,index,startTime,endTime,day,waitList,enrolled,vacancy,courseAU" + "\n");
		    for(Course i : fixedcourseArray) {
		    	
		    	courseWriter.append(i.getCourseID() +","+ 
		    	i.getCourseIndex() +","+ 
		    	i.getStartTime() +","+ 
		    	i.getEndTime() +","+ 
		    	i.getDay() +","+ 
		    	i.getWaitList() +","+ 
		    	i.getEnrolled() +","+ 
		    	i.getVacancy() +","+ 
		    	i.getCourseAU() + "\n"
		    	);
		    }
		    courseWriter.close();
		}
		catch(Exception e) {
			System.out.println("|Error: Problem with courseInformation.csv file|");
			e.printStackTrace();
		}
		readCourseCSV();
	}
	
	public Course[] editCourseCSV() {
		readCourseCSV();
		return this.fixedcourseArray;
	}
	
	public String setCourse(String courseID, int courseIndex, String startTime, String endTime, String day, int vacancy, int courseAU) {
		int lineIndex = 0;
		int arrayIndex = 0;
		boolean existFlag = false;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)) {
				if(i.getCourseIndex() == courseIndex) {
					arrayIndex = lineIndex;
					existFlag = true;
				}
			}
			lineIndex ++;
		}
		if(existFlag) {
			if(StringUtils.isAllBlank(fixedcourseArray[arrayIndex].getWaitList()) && fixedcourseArray[arrayIndex].getEnrolled()==0) {
				fixedcourseArray[arrayIndex].setStartTime(startTime);
				fixedcourseArray[arrayIndex].setEndTime(endTime);
				fixedcourseArray[arrayIndex].setDay(day);
				fixedcourseArray[arrayIndex].setVacancy(vacancy);
				fixedcourseArray[arrayIndex].setCourseAU(courseAU);
				writeCourseCSV();
				readCourseCSV();
				return "|The Course exists and has been updated by the input|";
			}
			else {
				return "|The Course exists and cannot be updated. The WaitList must be empty and 0 students enrolled|";
			}
		}
		else {
			try {
				Course tempcourseArray[] = editCourseCSV();
			    BufferedWriter courseWriter = new BufferedWriter(new FileWriter("courseInformation.csv"));
			    courseWriter.write("courseID" + "," + "index" + "," + "startTime" + "," + "endTime" + "," + "day" + "," + "waitList" + "," + "enrolled" + "," + "vacancy" + "," + "courseAU" + "\n");
			    for(Course i : tempcourseArray) {
			    	courseWriter.append(
			    	i.getCourseID() +","+
			    	i.getCourseIndex() +","+
			    	i.getStartTime() +","+
			    	i.getEndTime() +","+
			    	i.getDay() +","+
			    	i.getWaitList() +","+
			    	i.getEnrolled() +","+
			    	i.getVacancy() +","+
			    	i.getCourseAU() +","+
			    	"\n");
			    }
		    	courseWriter.append(
		    	courseID +","+
		    	courseIndex +","+
		    	startTime +","+
		    	endTime +","+
		    	day +","+
		    	"" +","+
		    	"0" +","+
		    	vacancy +","+
		    	courseAU +","+
		    	"\n");
			    courseWriter.close();
			    readCourseCSV();
			    
			    System.out.println("| Course ID | Index | Start Time (lec~tut~lab) | End Time (lec~tut~lab) | Day (lec~tut~lab) | Course AU |");
			    for(Course i : fixedcourseArray) {
			    	System.out.println("| "+
			    			i.getCourseID() +" | "+
			    			i.getCourseIndex() +" | "+
			    			i.getStartTime() +" | "+
			    			i.getEndTime() +" | "+
			    			i.getDay() +" | "+
			    			i.getCourseAU() +" |"
			    			);
			    }
			    return "|The Course does not exist and has been added|";
			}
			catch(Exception e) {
				System.out.println("|Error: Problem with courseInformation.csv file|");
				e.printStackTrace();
			}
		}
		return "|Error: Problem with setCourse method|";
	}
	
	public int getAvailableSlot(String courseID, int courseIndex) {
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)) {
				if(i.getCourseIndex() == courseIndex) {
					return (i.getVacancy()-i.getEnrolled());
				}
			}
		}
		return -1;
	}
	
	public String getTimeDay(String courseID, int courseIndex) {

		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(courseID, i.getCourseID()) && courseIndex == i.getCourseIndex()){
				return i.getStartTime() + "&" + i.getEndTime() + "&" + i.getDay();
			}
		}
		return "";
	}
	
	public void dropRegisteredCourse(String courseID, int courseIndex, String identity){
		int tempIndex = 0;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(courseID, i.getCourseID())) {
				if(i.getCourseIndex() == courseIndex) {
					ListStudent tempListStudent = new ListStudent();
					if(StringUtils.isBlank(i.getWaitList())) {	// No one in Wait List
						fixedcourseArray[tempIndex].setEnrolled(i.getEnrolled() - 1);
						writeCourseCSV();
						tempListStudent.dropRegisteredCourse(identity, courseID, courseIndex);			
					}
					else {	// Add Wait List to Course
						String[] tempWaitList = fixedcourseArray[tempIndex].getWaitList().split("~");
						String newWaitList = "";
						if(tempWaitList.length > 1) {
							for(int j=1; j<tempWaitList.length; j++) {
								newWaitList += (tempWaitList[j] + "~");
							}
						}
						System.out.println("|" + courseID + "," + courseIndex + " next in queue: Student " + tempWaitList[0] + ", will be added to the Course|");
						fixedcourseArray[tempIndex].setWaitList(newWaitList);
						tempListStudent.addStudentToCourse(tempWaitList[0], courseID, courseIndex);
						tempListStudent.dropRegisteredCourse(identity, courseID, courseIndex);
						tempListStudent.waitListNotify(identity, courseID, courseIndex);
					}
				}
			}
			tempIndex++;
		}
		writeCourseCSV();
	}
	
	public int getCourseAU(String courseID) {

		int tempInt = 0;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(courseID, i.getCourseID())) {
				tempInt = i.getCourseAU();
			}
		}
		return tempInt;
	}
	
	public boolean doesItClash(String myCourseID, int myCourseIndex, String targetCourseID, int targetCourseIndex) {
		boolean result = false;
		int myArrayIndex = -1, targetArrayIndex = -1;
		
		for(int i=0; i<fixedcourseArray.length; i++) {
			if(StringUtils.equals(fixedcourseArray[i].getCourseID(), myCourseID)) {
				if(fixedcourseArray[i].getCourseIndex() == myCourseIndex) {
					myArrayIndex = i;
				}
			}
			if(StringUtils.equals(fixedcourseArray[i].getCourseID(), targetCourseID)) {
				if(fixedcourseArray[i].getCourseIndex() == targetCourseIndex) {
					targetArrayIndex = i;
				}
			}
		}
		if(myArrayIndex == -1 || targetArrayIndex == -1) {
			//System.out.println("|Error: The specified courses or indexes does not exist|");
			//result = 1;
			result = true;
			return result;
		}
		
		String temp1 = fixedcourseArray[myArrayIndex].getStartTime();
		String temp2 = fixedcourseArray[myArrayIndex].getEndTime();
		String temp3 = fixedcourseArray[myArrayIndex].getDay();
		String temp4 = fixedcourseArray[targetArrayIndex].getStartTime();
		String temp5 = fixedcourseArray[targetArrayIndex].getEndTime();
		String temp6 = fixedcourseArray[targetArrayIndex].getDay();
		
		String[] tempMyStartTime = temp1.split("~");
		String[] tempMyEndTime = temp2.split("~");
		String[] tempMyDay = temp3.split("~");
		String[] tempTargetStartTime = temp4.split("~");
		String[] tempTargetEndTime = temp5.split("~");
		String[] tempTargetDay = temp6.split("~");
		
		int[] myStartTime = Arrays.asList(tempMyStartTime).stream().mapToInt(Integer::parseInt).toArray();
		int[] myEndTime = Arrays.asList(tempMyEndTime).stream().mapToInt(Integer::parseInt).toArray();
		int[] myDay = Arrays.asList(tempMyDay).stream().mapToInt(Integer::parseInt).toArray();
		int[] targetStartTime = Arrays.asList(tempTargetStartTime).stream().mapToInt(Integer::parseInt).toArray();
		int[] targetEndTime = Arrays.asList(tempTargetEndTime).stream().mapToInt(Integer::parseInt).toArray();
		int[] targetDay = Arrays.asList(tempTargetDay).stream().mapToInt(Integer::parseInt).toArray();
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				if(myDay[i] == targetDay[j]) {
					if((myStartTime[i] >= targetStartTime[j] && myStartTime[i] < targetEndTime[j]) || (myEndTime[i] > targetStartTime[j] && myEndTime[i]<=targetEndTime[j])) {
						result = true;
						String firstType = "", secondType = "";
						if(i == 0) {
							firstType = "Lecture";
						}
						if(j == 0) {
							secondType = "Lecture";
						}
						if(i == 1) {
							firstType = "Tutorial";
						}
						if(j == 1) {
							secondType = "Tutorial";
						}
						if(i == 2) {
							firstType = "Lab";
						}
						if(j == 2) {
							secondType = "Lab";
						}
						System.out.println("|There is a clash|");
						System.out.println("|1st CourseID: " + myCourseID + ", Index: " + myCourseIndex + ", Type: " + firstType + ", Time: " + myStartTime[i] + "-" + myEndTime[i] + "|");
						System.out.println("|2nd CourseID: " + targetCourseID + ", Index: " + targetCourseIndex + ", Type: " + secondType + ", Time: " + targetStartTime[j] + "-" + targetEndTime[j] + "|");
					}
				}		
			}
		}
		if(!result) {
			System.out.println("|There is no clash between " + myCourseID + ", " + myCourseIndex + " and " + targetCourseID + ", " + targetCourseIndex + "|");
		}
		return result;
	}
	
	public void addStudentToCourse(String courseID, int courseIndex) {
		int tempIndex = 0, foundIndex = 0;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(courseID, i.getCourseID())) {
				if(courseIndex == i.getCourseIndex()) {
					foundIndex = tempIndex;
				}
			}
			tempIndex++;
		}
		int tempInt = fixedcourseArray[foundIndex].getEnrolled();
		fixedcourseArray[foundIndex].setEnrolled(tempInt+1);
		writeCourseCSV();
	}
	
	public int findAU(String courseID, int courseIndex) {
		int tempIndex = 0, foundIndex = 0;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(courseID, i.getCourseID())) {
				if(courseIndex == i.getCourseIndex()) {
					foundIndex = tempIndex;
				}
			}
			tempIndex++;
		}
		return fixedcourseArray[foundIndex].getCourseAU();
	}
	
	public boolean courseExists(String courseID, int courseIndex) {

		boolean found = false;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)) {
				if(i.getCourseIndex() == courseIndex) {
					found = true;
				}
			}
		}
		return found;
	}
	
	public String getWaitList(String courseID, int courseIndex) {
		String temp = "";
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)) {
				if(i.getCourseIndex() == courseIndex) {
					temp = i.getWaitList();
				}
			}
		}
		return temp;
	}
	
	public void setWaitList(String courseID, int courseIndex, String waitList) {
		// waitList must be in "xx~xx~xx~" format
		int lineIndex = 0;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)){
				if(i.getCourseIndex() == courseIndex) {
					fixedcourseArray[lineIndex].setWaitList(waitList);
					writeCourseCSV();
				}
			}
			lineIndex++;
		}
	}
	
	public boolean waitListIdentityExists(String identity, String courseID, int courseIndex) {
		boolean flag = false;
		for(Course i : fixedcourseArray) {
			if(StringUtils.equals(i.getCourseID(), courseID)) {
				if(i.getCourseIndex() == courseIndex) {
					if(!(StringUtils.isBlank(i.getWaitList()))) {
						String[] tempString = i.getWaitList().split("~");
						for(String j : tempString) {
							if(StringUtils.equals(j, identity)) {
								flag = true;
							}
						}
						
					}
				}
			}
		}
		return flag;
	}
	
	public String studentExistenceInWaitList(String identity) {

		//returns a String of courseIDs separated by "~" that the student is in, follow by "&", then courseIndex in similar format
		String temp = "";
		for(Course i : fixedcourseArray) {
			if(StringUtils.contains(i.getWaitList(), identity + "~")) {
				temp += i.getCourseID() + "~";
			}
		}
		temp += "&";
		for(Course i : fixedcourseArray) {
			if(StringUtils.contains(i.getWaitList(), identity + "~")) {
				temp += i.getCourseIndex() + "~";
			}
		}
		return temp;
	}

}
