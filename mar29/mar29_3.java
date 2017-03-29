package mar29;

public class mar29_3 {
	interface PersonFactory<P extends person> {
		P create(String firstName, String lastName);
	}
	
	public static void main(String[] args) {
		PersonFactory<person> factory = person::new;
		person person = factory.create("peter", "Jackson");
		System.out.println(person.toString());
	}

}

class person {
	String firstName;
	String lastName;
	public person(String firstName, String lastName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public person() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "person [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
}
