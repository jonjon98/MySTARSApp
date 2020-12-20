public class Course {
	
	private String courseID;
	private int courseIndex;
	private String startTime;
	private String endTime;
	private String day;
	private String waitList;
	private int enrolled;
	private int vacancy;
	private int courseAU;
	
	public Course(String courseID, int courseIndex, String startTime, String endTime, String day, String waitList, int enrolled, int vacancy, int courseAU) {
		this.courseID = courseID;
		this.courseIndex = courseIndex;
		this.startTime = startTime;
		this.endTime = endTime;
		this.day = day;
		this.waitList = waitList;
		this.enrolled = enrolled;
		this.vacancy = vacancy;
		this.courseAU = courseAU;
	}
	
	public String getCourseID() {
		return this.courseID;
	}
	public int getCourseIndex() {
		return this.courseIndex;
	}
	public String getStartTime() {
		return this.startTime;
	}
	public String getEndTime() {
		return this.endTime;
	}
	public String getDay() {
		return this.day;
	}
	public String getWaitList() {
		return this.waitList;
	}
	public int getEnrolled() {
		return this.enrolled;
	}
	public int getVacancy() {
		return this.vacancy;
	}
	public int getCourseAU() {
		return this.courseAU;
	}
	
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	public void setCourseIndex(int courseIndex) {
		this.courseIndex = courseIndex;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public void setWaitList(String waitList) {
		this.waitList = waitList;
	}
	public void setEnrolled(int enrolled) {
		this.enrolled = enrolled;
	}
	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}
	public void setCourseAU(int courseAU) {
		this.courseAU = courseAU;
	}
}
