
public class TimeSlot {
	
	private String courseID;
	private int courseIndex;
	private int startTime;
	private int endTime;
	private int day;
	private String type;
	private boolean waiting;
	
	public TimeSlot(String courseID, int courseIndex, int startTime, int endTime, int day, String type, boolean waiting) {
		this.courseID = courseID;
		this.courseIndex = courseIndex;
		this.startTime = startTime;
		this.endTime = endTime;
		this.type = type;
		this.waiting = waiting;
		this.day = day;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public boolean getWaiting() {
		return waiting;
	}

	public void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public int getCourseIndex() {
		return courseIndex;
	}

	public void setCourseIndex(int courseIndex) {
		this.courseIndex = courseIndex;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
