package main.java.com.diptesh.email;

public class Person {
	private final String emailAddress;
	private final String name;

	public Person(final String emailAddress) {
		this.emailAddress = emailAddress;
		name = "";
	}

	public Person(final String name, final String emailAddress) {
		this.name = name;
		this.emailAddress = emailAddress;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getName() {
		return name;
	}

	public boolean isValidPerson() {
		return emailAddress != null && !emailAddress.isEmpty();
	}
}
