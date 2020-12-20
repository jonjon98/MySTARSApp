import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class AdminApp {
	private ListStudent listofStudents = new ListStudent();
	private ListCourse listofCourses = new ListCourse();
	private Encryption encryption=new Encryption();
	final String secretKey = "cz2001oodp!!!!";

	Scanner sc = new Scanner(System.in);
	
	public AdminApp() {}
	
	public String editStudentAccessPeriod(int startDay, int startMonth, int startYear, int endDay, int endMonth, int endYear) {
		boolean validInput = true;
		// Input Range Catching
		if(startDay<1 || startDay>31 || startMonth<1 || endMonth>12) {
			validInput = false;}
		else {
			YearMonth startYearMonthObject = YearMonth.of(startYear, startMonth); 
			YearMonth endYearMonthObject = YearMonth.of(endYear, endMonth); 
			int startMonthDays = startYearMonthObject.lengthOfMonth(); // Actual number of days in a specific month
			int endMonthDays = endYearMonthObject.lengthOfMonth();
			if(startDay > startMonthDays) { 
				validInput = false;}
			if(endDay > endMonthDays) {
				validInput = false;}
			if(endYear-startYear < 0) {
				validInput = false;}
			else if(endYear-startYear == 0) {
				if(endMonth-startMonth < 0) {
					validInput = false;}
				else if(endMonth-startMonth == 0) {
					if(endDay - startDay < 0) {
						validInput = false;}}}
		}
		if(validInput) {
			// Edit access period
			String loginFileName = "loginInformation.csv", line;
			String entireData[] = null;
			int entireDataArraySize;
			int lineIndex = 0;
	        try (BufferedReader sizeReader = new BufferedReader(new FileReader(loginFileName))) {
	            while ((line = sizeReader.readLine()) != null) {
	            	lineIndex++; }
	            entireDataArraySize = lineIndex - 1;
	            entireData = new String[entireDataArraySize];
	            lineIndex = 0;
	            sizeReader.close();
	        } 
			catch(Exception e) {
				System.out.println("|Error: Problem with loginInformation.csv file|");
			}
	        try (BufferedReader loginReader = new BufferedReader(new FileReader(loginFileName))) {
	            while ((line = loginReader.readLine()) != null) {
	            	if(lineIndex > 0) { entireData[lineIndex-1] = line; }
	            	lineIndex++;
	            }
	            loginReader.close(); }
			catch(Exception e) {
				System.out.println("|Error: Problem with loginInformation.csv file|");
				e.printStackTrace();
			}
			try {
			    BufferedWriter loginWriter = new BufferedWriter(new FileWriter("loginInformation.csv"));
			    loginWriter.write("identity,password,admin," + startDay + "~" + startMonth + "~" + startYear + "~" + "," + endDay + "~" + endMonth + "~" + endYear + "~");
			    for(String i : entireData) {
			    	loginWriter.append("\n" + i); }
			    loginWriter.close(); }
			catch(Exception e) {
				System.out.println("|Error: Problem with loginInformation.csv file|");
				e.printStackTrace(); }
			return "|Successful, Student Access Period: " + startDay + "/" + startMonth + "/" + startYear + " to " + endDay + "/" + endMonth + "/" + endYear + "|";
		}
		else {
			return "|Error: Enter a valid range OR starting date should not be AFTER the ending date|";
		}
	}
	public boolean addStudentToCourse(String identity, String courseID, int courseIndex) {
		// Check if student exist
		if(!listofStudents.studentExists(identity)) {
			System.out.println("|Error: The Student identity specified does not exist|");
			return false;
		}
		// Check vacancy
		if(listofCourses.getAvailableSlot(courseID, courseIndex) == 0) {
			System.out.println("|Error: The CourseID/CourseIndex specified contains no available slots|");
			return false;
		}
		// Check clashes
		try{
			String[] tempString = listofStudents.getRegisteredCourses(identity).split("&");
			String[] tempIndexes = tempString[1].split("~");

			String[] registeredCourses = tempString[0].split("~");
			int[] registeredIndexes = Arrays.asList(tempIndexes).stream().mapToInt(Integer::parseInt).toArray();

			boolean clashFlag = false;
			for (int i = 0; i < registeredCourses.length; i++) {
				boolean tempFlag = listofCourses.doesItClash(registeredCourses[i], registeredIndexes[i], courseID, courseIndex);
				if (tempFlag) {
					clashFlag = true;
				}
			}
			if (clashFlag) {
				System.out.println("|Error: The specified Course/Index does not exist or there is a clash with one or more registered courses|");
				return false;
			}}
			catch(Exception e){}

		// Adding student to course
		listofStudents.addStudentToCourse(identity, courseID, courseIndex);
		listofCourses.addStudentToCourse(courseID, courseIndex);
		return true;
	}

	public void mainAdmin() {
		int choice;

		do {
			System.out.println("|Please select one of the following options|");
			System.out.println("[1] Edit student access period");
			System.out.println("[2] Add a student");
			System.out.println("[3] Add/Update a course");
			System.out.println("[4] Check available slot for an index number");
			System.out.println("[5] Print student list by index number");
			System.out.println("[6] Print student list by course");
			System.out.println("[7] Add a Student to a Course");
			System.out.println("[8] Exit Program");
			System.out.print("Your choice: ");

			try {
				choice=sc.nextInt();
			}
			catch(Exception e) {
				choice=9;
				sc.next();
			}
			
			switch(choice) {

				case 1:
					//Edit student access period
					System.out.println("|Edit student access period|");
					int userStartDay=-1;
					int userStartMonth  = -1;
					int userStartYear  = -1;
					int userEndDay  = -1;
					int userEndMonth  = -1;
					int userEndYear  = -1;

					do{try{
						System.out.print("Enter starting day(1-31): ");
						userStartDay  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userStartDay<1||userStartDay>31);
					do{try{
						System.out.print("Enter starting month(1-12): ");
						userStartMonth  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userStartMonth<1||userStartMonth>12);
					do{try{
						System.out.print("Enter starting year: ");
						userStartYear  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userStartYear<2020);
					do{try{
						System.out.print("Enter ending day(1-31): ");
						userEndDay  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userEndDay<1||userEndDay>31);
					do{try{
						System.out.print("Enter ending month(1-12): ");
						userEndMonth  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userEndMonth<1||userEndMonth>12);
					do{try{
						System.out.print("Enter ending year: ");
						userEndYear  = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userEndYear<2020);
					System.out.println(editStudentAccessPeriod(userStartDay,userStartMonth,userStartYear,userEndDay,userEndMonth,userEndYear));
					break;
				case 2://Adding a student
					System.out.println("|Add a Student|");

					//input user identity
					String userIdentity="error";
					String identity;
					char lastChar;
					String middleNumbers;
					System.out.print("Enter identity : 9 Characters long" +"\n"+
							" Starting with Capital U, ending with ANY capital letter, with only numbers in between: ");
					do{
						identity = sc.next();
						while(identity.length()<9){
							System.out.print("Length not long enough, Try Again:");
							identity = sc.nextLine();
						continue;}
						lastChar= identity.charAt(identity.length() - 1);
						middleNumbers=identity.substring(1,identity.length() - 2);
						if((identity.charAt(0)=='U')&& Character.isUpperCase(lastChar) && identity.length()==9 &&
								StringUtils.isNumeric(middleNumbers)){
							userIdentity=identity;}
						else System.out.print("Wrong identity Input Try Again:");
					}while(!(identity.charAt(0) =='U'&& Character.isUpperCase(lastChar)&& identity.length()==9));

					//input email
					String email;
					String userEmail="error";
					System.out.print("Enter NTU email (ending with @e.ntu.edu.sg) : ");
					do{ email = sc.next();
						if(email.endsWith("@e.ntu.edu.sg")){  userEmail=email;}
						else System.out.print("Wrong email Input Try Again: ");
					}while(!email.endsWith("@e.ntu.edu.sg"));

					//input name
					System.out.print("Enter name:");
					sc.nextLine();
					String userName = sc.nextLine();

					//input gender
					String userGender;
					boolean tempGender;
					System.out.print("Enter gender(M/F): ");
					do{
					userGender = sc.next();
					tempGender = false;
					if(StringUtils.contains(userGender, "M") || StringUtils.contains(userGender, "m")) {
					}
					else if(StringUtils.contains(userGender, "F") || StringUtils.contains(userGender, "f")) {
						tempGender = true;}
					else System.out.print("Wrong gender Input Try Again, input either M as Male of F as Female: ");

					}while(!(userGender.equals("M") ||userGender.equals("m") ||userGender.equals("f")||userGender.equals("F")));

					//input school
					String userSchoolinput;
					String userSchool="error";
					boolean x;
					do{ System.out.print("Enter school (List of Schools are NBS,SCSE,CEE,EEE,MSE,MAE,SOH,WKW,SOSS,ADM,LKC): ");
						userSchoolinput = sc.next();
						x=userSchoolinput.equals("NBS")||userSchoolinput.equals("SCSE")||userSchoolinput.equals("CEE")||
								userSchoolinput.equals("EEE")||userSchoolinput.equals("MSE")||userSchoolinput.equals("MAE")||userSchoolinput.equals("SOH")||
								userSchoolinput.equals("WKW")||userSchoolinput.equals("SOSS")||userSchoolinput.equals("ADM")||userSchoolinput.equals("LKC");
						if(x)
						{  userSchool=userSchoolinput;}
						else System.out.print("Wrong School Input Try Again ");

					}while(!x);

					//input nationality
					int nationality;
					String userNationality="error";
					do{
						System.out.print("Enter nationality: 1. Singaporean ,2.Malaysian,3.Others:");
						nationality= sc.nextInt();
						if(nationality==1){userNationality="Singaporean";}
						else if (nationality==2){userNationality="Malaysian";}
						else if (nationality==3){
							System.out.print("Key in Nationality (Others): ");
							userNationality=sc.next();}
						else System.out.print("Wrong Nationality Input Try Again ");
						}
					while(nationality>3||nationality<0);

					//input password
					System.out.print("Enter password: ");
					String userPassword = sc.next();
					userPassword=encryption.encrypt(userPassword,secretKey);

					System.out.println(listofStudents.addStudent(userIdentity, userEmail, userName, tempGender, userSchool, userNationality,userPassword));

					break;
				
				case 3: // Error handling for time: 1 <= first 2 digit <= 24 , 0 <= last 2 digit <= 59
					int userCourseIndex=-1;
					int userCourseIndexInput=-1;
					boolean z;
					String middleNumbersIndex3;
					String userCourseIDInput;
					String userCourseID="error";
					System.out.println("|Add/Update a Course|");
					System.out.print("Enter courseID: ");
					do{userCourseIDInput = sc.next();
						while(userCourseIDInput.length()<6){
							System.out.print("Wrong CourseID Input Try Again:");
							userCourseIDInput = sc.next();}
						char firstChar=userCourseIDInput.charAt(0);
						char secondChar=userCourseIDInput.charAt(1);
						middleNumbersIndex3=userCourseIDInput.substring(2,userCourseIDInput.length() - 1);
						z=(firstChar >= 'A' && firstChar <= 'Z')
								&& (secondChar >= 'A' && secondChar <= 'Z')&&userCourseIDInput.length()==6&&
								StringUtils.isNumeric(middleNumbersIndex3);
						if(z){userCourseID=userCourseIDInput;}
						else{System.out.print("Wrong CourseID Input Try Again:");}}while(!z);

					System.out.print("Enter Course Index: ");
					do{try{
						userCourseIndexInput=sc.nextInt();
						if(userCourseIndexInput>=0&&userCourseIndexInput<=50){userCourseIndex = userCourseIndexInput;}
						else System.out.print("Invalid Course Index Input, try again: ");}
					catch(Exception e){sc.next(); System.out.print("Invalid Course Index Input, try again: ");
					}}while(userCourseIndexInput<0||userCourseIndexInput>50);

					StringBuilder userStartTime = new StringBuilder();
					StringBuilder userEndTime = new StringBuilder();
					StringBuilder userDay = new StringBuilder();
					String tempString = "";
					System.out.print("Does the course have a lecture? (Y/N): ");
					do{	String dayInput = "error";
						String timeStartInput="error";
						String timeEndInput="error";
						tempString = sc.next();
						if(StringUtils.contains(tempString, "Y") || StringUtils.contains(tempString, "y")) {
							System.out.print("Enter day of lecture (i.e 0 = monday, sunday = 6, wednesday = 2): ");
							do{try{
								dayInput  = sc.next();
								if(Integer.parseInt(dayInput)>=0&&Integer.parseInt(dayInput)<=6 ){userDay.append(dayInput).append("~");}
								else System.out.print("Error enter day again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}} while(Integer.parseInt(dayInput)<0 ||Integer.parseInt(dayInput)>6);
							System.out.print("Enter Start-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeStartInput  = sc.next();
								if (timeStartInput.length()==4){
									if(Integer.parseInt(timeStartInput)>=0&&Integer.parseInt(timeStartInput)<=2400 )
									{userStartTime.append(timeStartInput).append("~");}}
								else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while((Integer.parseInt(timeStartInput)<0 ||Integer.parseInt(timeStartInput)>2400)||timeStartInput.length()!=4);
							System.out.print("Enter End-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeEndInput  = sc.next();
								if (timeEndInput.length()==4){
									if(Integer.parseInt(timeEndInput)>=0&&Integer.parseInt(timeEndInput)<=2400 ){userEndTime.append(timeEndInput).append("~");}}
								else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while(Integer.parseInt(timeEndInput)<0 ||Integer.parseInt(timeEndInput)>2400||timeEndInput.length()!=4);
						}
						else if (StringUtils.contains(tempString, "N") || StringUtils.contains(tempString, "n")){
							userStartTime.append(2401 + "~");
							userEndTime.append(2401 + "~");
							userDay.append(7 + "~");
						}//2401 is the timing and 7 is the day where there is no session
						else System.out.print("Wrong Input Try Again, input either Y as Yes of N as No: ");
					}while(!(tempString.equals("N") ||tempString.equals("n") ||tempString.equals("Y")||tempString.equals("y")));

					System.out.print("Does the course have a tutorial? (Y/N): ");
					do{	String dayInput = "error";
						String timeStartInput="error";
						String timeEndInput="error";
						tempString = sc.next();
						if(StringUtils.contains(tempString, "Y") || StringUtils.contains(tempString, "y")) {
							System.out.print("Enter day of tutorial (i.e 0 = monday, sunday = 6, wednesday = 2): ");
							do{try{
								dayInput  = sc.next();
								if(Integer.parseInt(dayInput)>=0&&Integer.parseInt(dayInput)<=6 ){userDay.append(dayInput).append("~");}
								else System.out.print("Error enter day again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}} while(Integer.parseInt(dayInput)<0 ||Integer.parseInt(dayInput)>6);
							System.out.print("Enter Start-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeStartInput  = sc.next();
								if (timeStartInput.length()==4){
									if(Integer.parseInt(timeStartInput)>=0&&Integer.parseInt(timeStartInput)<=2400 )
									{userStartTime.append(timeStartInput).append("~");}}
								else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while((Integer.parseInt(timeStartInput)<0 ||Integer.parseInt(timeStartInput)>2400)||timeStartInput.length()!=4);
							System.out.print("Enter End-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeEndInput  = sc.next();
								if (timeEndInput.length()==4){
									if(Integer.parseInt(timeEndInput)>=0&&Integer.parseInt(timeEndInput)<=2400 ){userEndTime.append(timeEndInput).append("~");}}
								else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while(Integer.parseInt(timeEndInput)<0 ||Integer.parseInt(timeEndInput)>2400||timeEndInput.length()!=4);
						}
						else if (StringUtils.contains(tempString, "N") || StringUtils.contains(tempString, "n")){
							userStartTime.append(2401 + "~");
							userEndTime.append(2401 + "~");
							userDay.append(7 + "~");
						}//2401 is the timing and 7 is the day where there is no session
						else System.out.print("Wrong Input Try Again, input either Y as Yes of N as No: ");
					}while(!(tempString.equals("N") ||tempString.equals("n") ||tempString.equals("Y")||tempString.equals("y")));

					System.out.print("Does the course have a lab? (Y/N): ");
					do{	String dayInput = "error";
						String timeStartInput="error";
						String timeEndInput="error";
						tempString = sc.next();
						if(StringUtils.contains(tempString, "Y") || StringUtils.contains(tempString, "y")) {
							System.out.print("Enter day of lab (i.e 0 = monday, sunday = 6, wednesday = 2): ");
							do{try{
								dayInput  = sc.next();
								if(Integer.parseInt(dayInput)>=0&&Integer.parseInt(dayInput)<=6 ){userDay.append(dayInput).append("~");}
								else System.out.print("Error enter day again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}} while(Integer.parseInt(dayInput)<0 ||Integer.parseInt(dayInput)>6);
							System.out.print("Enter Start-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeStartInput  = sc.next();
								if (timeStartInput.length()==4){
									if(Integer.parseInt(timeStartInput)>=0&&Integer.parseInt(timeStartInput)<=2400 )
										{userStartTime.append(timeStartInput).append("~");}}
								else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while((Integer.parseInt(timeStartInput)<0 ||Integer.parseInt(timeStartInput)>2400)||timeStartInput.length()!=4);
							System.out.print("Enter End-Time in 24-hour format (i.e 1.30pm = 1330, 8.30am = 830): ");
							do{try{
								timeEndInput  = sc.next();
								if (timeEndInput.length()==4){
									if(Integer.parseInt(timeEndInput)>=0&&Integer.parseInt(timeEndInput)<=2400 ){userEndTime.append(timeEndInput).append("~");}}
									else System.out.print("Error enter timing again: ");}
							catch(Exception e){sc.next();System.out.print("Error enter day again: ");}}
							while(Integer.parseInt(timeEndInput)<0 ||Integer.parseInt(timeEndInput)>2400||timeEndInput.length()!=4);
						}
						else if (StringUtils.contains(tempString, "N") || StringUtils.contains(tempString, "n")){
							userStartTime.append(2401 + "~");
							userEndTime.append(2401 + "~");
							userDay.append(7 + "~");
						}//2401 is the timing and 7 is the day where there is no session
						else System.out.print("Wrong Input Try Again, input either Y as Yes of N as No: ");
					}while(!(tempString.equals("N") ||tempString.equals("n") ||tempString.equals("Y")||tempString.equals("y")));

					int userVacancy=-1;
					do{try{
						System.out.print("Enter total vacancy for the course: ");
						userVacancy = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userVacancy<0);

					int userCourseAU=-1;
					do{try{
						System.out.print("Enter AU amount for the course: ");
						userCourseAU = sc.nextInt();}
					catch(Exception e){sc.next();}}while(userCourseAU<0);

					System.out.println(listofCourses.setCourse(userCourseID, userCourseIndex, userStartTime.toString(), userEndTime.toString(), userDay.toString(), userVacancy, userCourseAU));
					break;
					
				case 4://Check available slot for an index number
					System.out.println("|Check available slot for an index number|");
					System.out.print("Enter Course ID: ");
					String userCourseID2 = sc.next();
					System.out.print("Enter Course Index:");
					int userCourseIndex2 = sc.nextInt();
					int result = listofCourses.getAvailableSlot(userCourseID2, userCourseIndex2);
					if(result == -1) {System.out.println("|Error: Course ID or Course Index provided does not exist|");}
					else {System.out.println("|Course ID: " + userCourseID2 + ", Course Index: " + userCourseIndex2 + ", Available Slot: " + result + "|"); }
					break;
				
				case 5:
					//Print student list by index number
					String userCourseID3 = "error";
					String userCourseID3input;
					int userCourseIndex3=-1;
					int userCourseIndex3input=-1;
					String middleNumbersIndex;
					boolean y;
					System.out.println("|Print student list by index number|");
					System.out.print("Enter Course ID (Key 2 Capital Letters followed by 4 Digits): ");
					do{userCourseID3input = sc.next();
						while(userCourseID3input.length()<6){
							System.out.print("Wrong CourseID Input Try Again:");
							userCourseID3input = sc.next();}
						char firstChar=userCourseID3input.charAt(0);
						char secondChar=userCourseID3input.charAt(1);
						middleNumbersIndex=userCourseID3input.substring(2,userCourseID3input.length() - 1);
						y=(firstChar >= 'A' && firstChar <= 'Z')
								&& (secondChar >= 'A' && secondChar <= 'Z')&&userCourseID3input.length()==6&&
								StringUtils.isNumeric(middleNumbersIndex);
						if(y){userCourseID3=userCourseID3input;}
						else{System.out.print("Wrong CourseID Input Try Again:");}}while(!y);
					System.out.print("Enter Course Index: ");
					do{try{
						userCourseIndex3input=sc.nextInt();
						if(userCourseIndex3input>=0&&userCourseIndex3input<=50){userCourseIndex3 = userCourseIndex3input;}
						else System.out.print("Invalid Course Index Input, try again: ");}
					catch(Exception e){sc.next(); System.out.print("Invalid Course Index Input, try again: ");
					}}while(userCourseIndex3input<0||userCourseIndex3input>50);

					System.out.println(listofStudents.getIndexList(userCourseID3, userCourseIndex3));
					break;

				case 6:
					//Print student list by course
					System.out.println("|Print student list by course|");
					System.out.print("Enter Course ID: ");
					String userCourseID4 = sc.next();
					System.out.println(listofStudents.getCourseList(userCourseID4));
					break;

				case 7:
					System.out.println("|Add a Student to a Course|");
					System.out.print("Enter identity of student: ");
					String userIdentity2 = sc.next();
					System.out.print("Enter Course ID: ");
					String userCourseID5 = sc.next();
					System.out.print("Enter Course Index: ");
					int userCourseIndex4 = sc.nextInt();
					if(addStudentToCourse(userIdentity2, userCourseID5, userCourseIndex4)) {
						System.out.println("|The Student " + userIdentity2 + ", has been successfully added to Course " + userCourseID5 + ", Index " + userCourseIndex4 + "|"); }
					break;
				case 8:
					System.out.println("|Program terminating... Returning to login page|");
					break;
				default:
					System.out.println("|Error: Enter a value from 1-7|");
					break;
					
			}
		
		}while(choice != 8);
			
		
	}
}