
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class StudentApp {
	
	private ListStudent listofStudents;
	private ListCourse listofCourses = new ListCourse();
	private String currentIdentity;
	private ArrayList<TimeSlot> myTimetable = new ArrayList<TimeSlot>();
	private int maxAu = 21;
	Scanner sc = new Scanner(System.in);
	
	public StudentApp(String identity) {
		this.currentIdentity = identity;
		listofStudents = new ListStudent(currentIdentity);
	}
	
	public void printTimetable(String currentIdentity) {
		System.out.println((listofStudents.retrieveTimeTable(currentIdentity)));
	}
	
	public String addCourse(String courseID, int courseIndex) {
		int tempor = listofStudents.getAUofStudent(currentIdentity);
		if((tempor+listofCourses.getCourseAU(courseID))>21) {
			return "|Error: The course cannot be added because you will hit the limit AU of"+ tempor+"|";
		}
		listofCourses.writeCourseCSV();
		if(listofCourses.courseExists(courseID, courseIndex)) {
			// Clash Checking
			String temp1 = listofStudents.getRegisteredCourses();
			
			if(!(StringUtils.equals(temp1, "&"))) {
				String[] temp2 = temp1.split("&");
				String[] tempIndexes = temp2[1].split("~");
				
				String[] registeredCourses = temp2[0].split("~");
				int[] registeredIndexes = Arrays.asList(tempIndexes).stream().mapToInt(Integer::parseInt).toArray();
				
				boolean clashFlag = false;
				for(int i=0; i<registeredCourses.length; i++) {
					boolean tempFlag = listofCourses.doesItClash(registeredCourses[i], registeredIndexes[i], courseID, courseIndex);
					if(tempFlag) {
						clashFlag = true;
					}
				}
				if(clashFlag) {
					System.out.println("|Error: The specified Course/Index does not exist or there is a clash with one or more registered courses|");
					return "|Course " + courseID + ", Index " + courseIndex + ", has not been added|";
				}
			}
			if(listofStudents.courseExistenceInStudent(currentIdentity, courseID, courseIndex) || listofCourses.waitListIdentityExists(currentIdentity, courseID, courseIndex)) {
				System.out.println("|Error: The specified CourseID has already been registered or you are in the Wait List, drop the course first|");
				return "|Course " + courseID + ", Index " + courseIndex + ", has not been added|"; 
			}
			//Add course here
			//Vacancy Checking
			if(listofCourses.getAvailableSlot(courseID, courseIndex) == 0) {
				System.out.println("|The specified Course/Index has no slots, adding you to the waitList . . .|");
				//Add to waitList
				String sendWait = listofCourses.getWaitList(courseID, courseIndex);
				listofCourses.setWaitList(courseID, courseIndex, sendWait + currentIdentity + "~");
				listofStudents.waitListAdded(currentIdentity, courseID, courseIndex);
			} 
			else {
				//Add course here
				listofStudents.addStudentToCourse(currentIdentity, courseID, courseIndex);
				listofCourses.addStudentToCourse(courseID, courseIndex);
				return "|Your identity has been added to the Wait List|";
			}
		}
		else {
			System.out.println("|Error: The specified Course/Index does not exist|");
			return "|Course " + courseID + ", Index " + courseIndex + ", has not been added|";
		}
		return "|Course " + courseID + ", Index " + courseIndex + ", has been added|";
	}
	
	public boolean dropCourse(String courseID, int courseIndex) {
		listofCourses.writeCourseCSV();
		boolean flag = false;
		if(listofCourses.courseExists(courseID, courseIndex)) {
			if(listofStudents.courseExistenceInStudent(currentIdentity, courseID, courseIndex)) {
				System.out.println("|Course " + courseID + ", Index " + courseIndex + " found in enrolled Courses|");
				listofCourses.dropRegisteredCourse(courseID, courseIndex, currentIdentity);
				listofCourses.writeCourseCSV();
				flag = true;
				
			}
			else if(listofCourses.waitListIdentityExists(currentIdentity, courseID, courseIndex)) {
				System.out.println("|Course " + courseID + ", Index " + courseIndex + " found in Wait List|");
				String tempWaitList = listofCourses.getWaitList(courseID, courseIndex);
				String[] tempWaitList2 = tempWaitList.split("~");
				String newWaitList = "";
				for(String i : tempWaitList2) {
					if(!(StringUtils.equals(i, currentIdentity))) {
						newWaitList += (i+"~");
					}
				}
				listofCourses.setWaitList(courseID, courseIndex, newWaitList);
				flag = true;
			}
			else {
				System.out.println("|Error: You are not enrolled for the Course and you are not in the Wait List|");
			}
		}
		else {
			System.out.println("|Error: The Course/Index specified does not exist|");
		}
		return flag;
	}
	
	public boolean changeIndex(String courseID, int courseIndex, int targetIndex) {
		listofCourses.writeCourseCSV();
		boolean flag = false;
		if(listofCourses.courseExists(courseID, courseIndex)) {
			if(listofStudents.courseExistenceInStudent(currentIdentity, courseID, courseIndex)) {
				System.out.println("|Course " + courseID + ", Index " + courseIndex + " found in enrolled Courses|");
				if(listofCourses.getAvailableSlot(courseID, targetIndex) > 0) {
					boolean clashFlag = false;
					String[] tempList = listofStudents.getRegisteredCourses().split("&");
					String[] tempId = tempList[0].split("~");
					String[] tempIndex = tempList[1].split("~");
					for(int i=0; i<tempId.length; i++) {
						if(!(StringUtils.equals(tempId[i], courseID))) {
							if(listofCourses.doesItClash(tempId[i], Integer.parseInt(tempIndex[i]), courseID, targetIndex)) {
								clashFlag = true;
							}
						}
					}
					if(clashFlag) {
						System.out.println("|Error: The index you are trying to change to has a clash with your other registered courses|");
					}
					else {
						flag = true;
						dropCourse(courseID, courseIndex);
						addCourse(courseID, targetIndex);
						System.out.println("|No clashes detected, changing index now|");
					}
				}
				else {
					System.out.println("|Error: The index you are trying to change to has no available slots|");
				}
			}
			else {
				System.out.println("|Error: You are not enrolled for the Course|");
			}
		}
		else {
			System.out.println("|Error: The Course/Index specified does not exist|");
		}
		return flag;
	}
	
	public boolean swapIndex(String targetIdentity, String courseID) {
		listofCourses.writeCourseCSV();
		boolean flag = false;
		if(listofStudents.studentExists(targetIdentity)) {
			String tempRegisteredCurrent = listofStudents.getRegisteredCourses();
			String tempRegisteredTarget = listofStudents.getRegisteredCourses(targetIdentity);
			if(StringUtils.contains(tempRegisteredCurrent, (courseID+"~")) && StringUtils.contains(tempRegisteredTarget, (courseID+"~"))) {
				String[] registeredCurrent = tempRegisteredCurrent.split("&");
				String[] registeredTarget = tempRegisteredTarget.split("&");
				
				String[] currentID = registeredCurrent[0].split("~");
				String[] tempcurrentIndex = registeredCurrent[1].split("~");
				String[] targetID = registeredTarget[0].split("~");
				String[] temptargetIndex = registeredTarget[1].split("~");
				int[] currentIndex = Arrays.asList(tempcurrentIndex).stream().mapToInt(Integer::parseInt).toArray();
				int[] targetIndex = Arrays.asList(temptargetIndex).stream().mapToInt(Integer::parseInt).toArray();
				
				int currentFound = 0, targetFound = 0;
				for(int i=0; i<currentID.length; i++) {
					if(StringUtils.equals(courseID, currentID[i])) {
						currentFound = i;
					}
				}			
				for(int i=0; i<targetID.length; i++) {
					if(StringUtils.equals(courseID, targetID[i])){
						targetFound = i;
					}
				}
				
				if(currentIndex[currentFound] == targetIndex[targetFound]) {
					System.out.println("|Error: You and the Target Student is enrolled to the same Index|");
				}
				else { // Check clashes
					boolean clashFlag = false;
					for(int i=0; i<currentIndex.length; i++) {
						if(i != currentFound) {
							if(listofCourses.doesItClash(currentID[i], currentIndex[i], targetID[targetFound], targetIndex[targetFound])) {
								clashFlag = true;
								System.out.println("|Error: Your Courses clashes with the Target Student's Index|");
							}
						}
					}
					for(int i=0; i<targetIndex.length; i++) {
						if(i != targetFound) {
							if(listofCourses.doesItClash(targetID[i], targetIndex[i], currentID[currentFound], currentIndex[currentFound])) {
								clashFlag = true;
								System.out.println("|Error: The Target Student's courses clashes with Your Index|");
							}
						}
					}
					if(!clashFlag) {
						flag = true;
						String newCurrentID = "";
						String newCurrentIndex = "";
						String newTargetID = "";
						String newTargetIndex = "";
						for(int i=0; i<currentIndex.length; i++) {
							if(i != currentFound) {
								newCurrentID += (currentID[i]+"~");
								newCurrentIndex += (currentIndex[i]+"~");
							}
						}
						for(int i=0; i<targetIndex.length; i++) {
							if(i != targetFound) {
								newTargetID += (targetID[i]+"~");
								newTargetIndex += (targetIndex[i]+"~");
							}
						}
						newCurrentID = newCurrentID + (targetID[targetFound]+"~");
						newCurrentIndex = newCurrentIndex + (targetIndex[targetFound]+"~");
						newTargetID = newTargetID + (currentID[currentFound]+"~");
						newTargetIndex = newTargetIndex + (currentIndex[currentFound]+"~");
						listofStudents.swapIndex(currentIdentity, targetIdentity, newCurrentID, newTargetID, newCurrentIndex, newTargetIndex);
					}
				}
			}
			else {
				System.out.println("|Error: You or the Target Student have not enrolled for the Course specified");
			}
		}
		else {
			System.out.println("|Error: The Target Student does not exist|");
		}
		return flag;
	}

    public void mainStudent() {
		int choice;
		do {
			System.out.println("|Please select one of the following options|");
			System.out.println("[1] Add Course");
			System.out.println("[2] Drop Course");
			System.out.println("[3] Check the Courses you are enrolled in");
			System.out.println("[4] Check Course vacancy");
			System.out.println("[5] Change Index of Course");
			System.out.println("[6] Swap Index of Course with another Student");
			System.out.println("[7] Exit Program");
			System.out.print("Your choice: ");
			
			try {
				choice=sc.nextInt();
			}
			catch(Exception e) {
				choice=8;
				sc.next();
			}
			
			switch(choice) {
				case 1:
					/* -Add Course
					 * if student already has the course, he must drop it first even if its a different index
					 */
					int userCourseIndex1=-1;
					int userCourseIndexInput1=-1;
					boolean z;
					String middleNumbersIndex3;
					String userCourseID1Input;
					String userCourseID1="error";
					System.out.println("|Add Course|");
					System.out.print("Enter courseID: ");
					do{userCourseID1Input = sc.next();
						while(userCourseID1Input.length()<6){
							System.out.print("Wrong CourseID Input Try Again:");
							userCourseID1Input = sc.next();}
						char firstChar=userCourseID1Input.charAt(0);
						char secondChar=userCourseID1Input.charAt(1);
						middleNumbersIndex3=userCourseID1Input.substring(2,userCourseID1Input.length() - 1);
						z=(firstChar >= 'A' && firstChar <= 'Z')
								&& (secondChar >= 'A' && secondChar <= 'Z')&&userCourseID1Input.length()==6&&
								StringUtils.isNumeric(middleNumbersIndex3);
						if(z){userCourseID1=userCourseID1Input;}
						else{System.out.print("Wrong CourseID Input Try Again:");}}while(!z);
					System.out.print("Enter Course Index: ");
					do{try{
						userCourseIndexInput1=sc.nextInt();
						if(userCourseIndexInput1>=0&&userCourseIndexInput1<=50){userCourseIndex1 = userCourseIndexInput1;}
						else System.out.print("Invalid Course Index Input, try again: ");}
					catch(Exception e){sc.next(); System.out.print("Invalid Course Index Input, try again: ");
					}}while(userCourseIndexInput1<0||userCourseIndexInput1>50);
					System.out.println(addCourse(userCourseID1, userCourseIndex1));
					break;
				case 2:
					//this.dropCourse();
					int userCourseIndex2=-1;
					int userCourseIndexInput2=-1;
					String middleNumbersIndex2;
					String userCourseID2Input;
					String userCourseID2="error";
					System.out.println("|Add Course|");
					System.out.print("Enter courseID: ");
					do{userCourseID2Input = sc.next();
						while(userCourseID2Input.length()<6){
							System.out.print("Wrong CourseID Input Try Again:");
							userCourseID2Input = sc.next();}
						char firstChar=userCourseID2Input.charAt(0);
						char secondChar=userCourseID2Input.charAt(1);
						middleNumbersIndex2=userCourseID2Input.substring(2,userCourseID2Input.length() - 1);
						z=(firstChar >= 'A' && firstChar <= 'Z')
								&& (secondChar >= 'A' && secondChar <= 'Z')&&userCourseID2Input.length()==6&&
								StringUtils.isNumeric(middleNumbersIndex2);
						if(z){userCourseID2=userCourseID2Input;}
						else{System.out.print("Wrong CourseID Input Try Again:");}}while(!z);
					System.out.print("Enter Course Index: ");
					do{try{
						userCourseIndexInput2=sc.nextInt();
						if(userCourseIndexInput2>=0&&userCourseIndexInput2<=50){userCourseIndex2 = userCourseIndexInput2;}
						else System.out.print("Invalid Course Index Input, try again: ");}
					catch(Exception e){sc.next(); System.out.print("Invalid Course Index Input, try again: ");
					}}while(userCourseIndexInput2<0||userCourseIndexInput2>50);
					if(dropCourse(userCourseID2, userCourseIndex2)) {
						System.out.println("|Course " + userCourseID2 + ", Index " + userCourseIndex2 + ", has been removed|");
					}
					else {
						System.out.println("|Course " + userCourseID2 + ", Index " + userCourseIndex2 + ", has not been removed|");
					}
					break;
				
				case 3:
					/*
					 * Check courses
					 */
					System.out.println("|Check the Courses you are enrolled in|");
					System.out.println("|Timetable|");
					printTimetable(currentIdentity);
					break;
					
				case 4:
					/*
					 * Check course vacancy
					 */
					int userCourseIndex3=-1;
					int userCourseIndexInput3=-1;
					String middleNumbersIndex4;
					String userCourseID3Input;
					String userCourseID3="error";
					System.out.println("|Add Course|");
					System.out.print("Enter courseID: ");
					do{userCourseID3Input = sc.next();
						while(userCourseID3Input.length()<6){
							System.out.print("Wrong CourseID Input Try Again:");
							userCourseID3Input = sc.next();}
						char firstChar=userCourseID3Input.charAt(0);
						char secondChar=userCourseID3Input.charAt(1);
						middleNumbersIndex4=userCourseID3Input.substring(2,userCourseID3Input.length() - 1);
						z=(firstChar >= 'A' && firstChar <= 'Z')
								&& (secondChar >= 'A' && secondChar <= 'Z')&&userCourseID3Input.length()==6&&
								StringUtils.isNumeric(middleNumbersIndex4);
						if(z){userCourseID3=userCourseID3Input;}
						else{System.out.print("Wrong CourseID Input Try Again:");}}while(!z);
					System.out.print("Enter Course Index: ");
					do{try{
						userCourseIndexInput3=sc.nextInt();
						if(userCourseIndexInput3>=0&&userCourseIndexInput3<=50){userCourseIndex3 = userCourseIndexInput3;}
						else System.out.print("Invalid Course Index Input, try again: ");}
					catch(Exception e){sc.next(); System.out.print("Invalid Course Index Input, try again: ");
					}}while(userCourseIndexInput3<0||userCourseIndexInput3>50);
					int result = listofCourses.getAvailableSlot(userCourseID3, userCourseIndex3);
					if(result == -1) {
						System.out.println("|Error: Course ID or Course Index provided does not exist|");
					}
					else {
						System.out.println("|Course ID: " + userCourseID3 + ", Course Index: " + userCourseIndex3 + ", Available Slot: " + result + "|");
					}
					break;
				
				case 5:
					/*
					 * Change Index
					 */
					System.out.println("|Change Index|");
					System.out.print("Enter Course ID: ");
					String userCourseID4 = sc.next();
					System.out.print("Enter Registered Course Index: ");
					int userCourseIndex4 = sc.nextInt();
					System.out.print("Enter Target Course Index: ");
					int userIndexTarget = sc.nextInt();
					if(changeIndex(userCourseID4, userCourseIndex4, userIndexTarget)) {
						System.out.println("|Change Index successful, Course Index " + userCourseIndex4 + " -> " + userIndexTarget + "|" );
					}
					else {
						System.out.println("|Change Index unsuccessful, Course Index remains the same|");
					}
					break;
					
				case 6:
					/* SwapIndex
					 *  Assumes both parties already agreed to swap and is able to swap
					 */
					System.out.println("|SwapIndex|");
					System.out.print("Enter Target Identity: ");
					String friendIdentity = sc.next();
					System.out.println("Enter Course ID: ");
					String userCourseID5 = sc.next();
					if(swapIndex(friendIdentity, userCourseID5)) {
						System.out.println("|Swap Index successful|");
					}
					else{
						System.out.println("|Swap Index unsuccessful, Course Index remains the same|");
					}
					break;
				
				case 7:
					System.out.println("|Program terminating... Returning to login page|");
					break;
				
				default:
					System.out.println("|Error: Enter a value from 1-7|");
					break;
					
			}
		
		}while(choice != 7);
			
		
	}
}
