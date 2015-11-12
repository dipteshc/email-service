package main.java.com.diptesh.sender;

import main.java.com.diptesh.email.Person;

public class SenderUtils {
	public static String createMimeEntityFromPerson(final Person person) {
		final StringBuilder sb = new StringBuilder();
		if (!person.getName().equals("")) {
			sb.append(person.getName())
			.append(" ");
		}
		return sb.append("<")
				.append(person.getEmailAddress())
				.append(">")
				.toString();
	}
}
