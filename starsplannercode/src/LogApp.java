import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import java.time.LocalDate;
import java.util.Date;

//NOTE: For log in details, please refer to csv loginInformation(before encryption)
public class LogApp {
	static String accessStart, accessEnd;
	public static int verifyLogin(String identity, String password) { // returns -> 1: StudentApp, 2: AdminApp, 3: Wrong password, try again
		String loginFileName = "loginInformation.csv", line;
		int result = 3, lineIndex = 0;
		try (BufferedReader loginReader = new BufferedReader(new FileReader(loginFileName))) {
			while ((line = loginReader.readLine()) != null) {
				if (lineIndex > 0) {
					String[] loginData = line.split(",");
					if(StringUtils.isEmpty(loginData[0])||StringUtils.isEmpty(loginData[1])||StringUtils.isEmpty(loginData[2])) return 4;
					if (StringUtils.equals(loginData[0], identity) && StringUtils.equals(loginData[1], password))
					{
						if (StringUtils.equals(loginData[2], "FALSE")) {
							return 1;
						} else if (StringUtils.equals(loginData[2], "TRUE")) {
							return 2;
						}
						//else{return 4;}
					}
				}
				lineIndex++;
			}
		} catch (Exception e) {

			return 5;

		}
		return result;
	}
	public static boolean verifyTime() {
		String loginFileName = "loginInformation.csv", line;
		int lineIndex = 0;
		LocalDate localDataObject = LocalDate.now();
		int currentDay = localDataObject.getDayOfMonth();
		int currentMonth = localDataObject.getMonthValue();
		int currentYear = localDataObject.getYear();
		try (BufferedReader loginReader = new BufferedReader(new FileReader(loginFileName))) {
			while ((line = loginReader.readLine()) != null) {
				if(lineIndex==0) {
					String[] loginData = line.split(",");
					String[] startDate = loginData[3].split("~");
					String[] endDate = loginData[4].split("~");
					accessStart = startDate[0] + "/" + startDate[1] + "/" + startDate[2];
					accessEnd = endDate[0] + "/" + endDate[1] + "/" + endDate[2];
					if(currentYear < Integer.parseInt(startDate[2]) || currentYear > Integer.parseInt(endDate[2])) {
						return false;
					}
					else if(currentMonth < Integer.parseInt(startDate[1]) || currentMonth > Integer.parseInt(endDate[1])) {

						return false;
					}
					else if(currentDay < Integer.parseInt(startDate[0]) || currentDay > Integer.parseInt(endDate[0])) {
						return false;
					}
				}
			}
		}
		catch(Exception e) {
		}
		return true;
	}
	public static void passwordMasking() {
		for(int i=0; i<99999; i++) {
			System.out.println("\n");
		}
	}
	
	public static void main(String[] args) {
		Date date = new Date();
		Scanner sc = new Scanner(System.in);
		Encryption encryption=new Encryption();
		final String secretKey = "cz2001oodp!!!!";

		int logChoice = 0;
		System.out.println("|Welcome to MySTARS!|");

		do {System.out.println("[1] Login");
			System.out.println("[2] Exit");
			System.out.print("Your choice: ");
			try {
				logChoice = sc.nextInt();

			} catch (Exception e) {
				sc.next();
			}
			if (logChoice == 1) {

				System.out.println("|Enter your login information|");
				System.out.print("Identity: ");
				String userIdentity = sc.next();
				System.out.print("Password: ");
				String userPassword = sc.next();
				userPassword=encryption.encrypt(userPassword,secretKey);
				//System.out.println(userPassword);

				if(verifyLogin(userIdentity,userPassword) == 1) {
					if(verifyTime()) {
						passwordMasking();
						System.out.println("|Login successful, Welcome Student|");
						StudentApp studApp = new StudentApp(userIdentity);
						studApp.mainStudent();
					}
					else {
						passwordMasking();
						System.out.println("|Welcome Student, you are not allowed to access outside of Access Period.|");
						System.out.println("|Access Period: " + accessStart + " to " + accessEnd + "|");
						System.out.printf("Current Date-Time: "+date+"\n");
					}
				}
				else if(verifyLogin(userIdentity,userPassword) == 2) {
					passwordMasking();
					System.out.println("|Login successful, Welcome Admin|");
					AdminApp AdApp = new AdminApp();
					AdApp.mainAdmin();

				} else if (verifyLogin(userIdentity, userPassword) == 3) {
					if (userIdentity != null && userPassword != null) {
						passwordMasking();
						System.out.println("|Error: Login unsuccessful because of mismatch in password and username, please try again");
						logChoice=0;
					}
				}

				else if(verifyLogin(userIdentity, userPassword) == 4){
					passwordMasking();
					System.out.println("|Error: loginInformation.csv is in wrong format, please check file and try again, note the following:" +"\n"+
							"there are 5 inputs required in this file, identity,password,isAdmin, start and end date for access period |");
					logChoice=0;

				}
				else if(verifyLogin(userIdentity, userPassword) == 5){
					passwordMasking();
					System.out.println("|Error: loginInformation.csv file is missing, please input file and try again|");
					logChoice=0;
				}

			}

			else if (logChoice == 2) {
				System.out.println("|MySTARS Exited!|");
			} else {
				System.out.println("|Error: Invalid Choice, Try Again!|");
			}

		} while (logChoice != 2);
	}
}