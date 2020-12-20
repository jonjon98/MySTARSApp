
public class Student extends Person {
	
	private String email;
	private String name;
	private boolean gender;
	private String school;
	private String nationality;
	private String course;
	private String index;
	private int au;
	
	public Student(String identity, String email, String name, boolean gender, String school, String nationality, String course, String index, int au){
		super.identity = identity;
		this.email = email;
		this.name = name;
		this.gender = gender;
		this.school = school;
		this.nationality = nationality;
		this.course = course;
		this.index = index;
		this.au = au;
	}
	
	public String getEmail() {
		return this.email;
	}
	public String getName() {
		return this.name;
	}
	public boolean getGender() {
		return this.gender;
	}
	public String getSchool() {
		return this.school;
	}
	public String getNationality() {
		return this.nationality;
	}
	public String getCourse() {
		return this.course;
	}
	public String getIndex() {
		return this.index;
	}
	public int getAU() {
		return this.au;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setGender(boolean gender) {
		this.gender = gender;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public void setAU(int au) {
		this.au = au;
	}
}
