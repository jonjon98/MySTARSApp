
public abstract class Person {
	
	protected String identity;
	
	public Person() {}
	public Person(String identity){
		this.identity = identity;
	}
	
	public String getIdentity() {
		return this.identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
}
