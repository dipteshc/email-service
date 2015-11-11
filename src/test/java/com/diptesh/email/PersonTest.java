package test.java.com.diptesh.email;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.diptesh.email.Person;

public class PersonTest {
	@Test
	public void testEmailOnly() {
		final Person person = new Person("foo@bar.com");
		assertEquals("foo@bar.com", person.getEmailAddress());
		assertEquals("", person.getName());
		assertTrue(person.isValidPerson());
	}

	@Test
	public void testInvalidPerson() {
		final Person person = new Person("");
		assertFalse(person.isValidPerson());
		final Person person1 = new Person(null);
		assertFalse(person1.isValidPerson());
	}

	@Test
	public void testNameAndEmail() {
		final Person person = new Person("Foo Bar", "foo@bar.com");
		assertEquals("Foo Bar", person.getName());
		assertEquals("foo@bar.com", person.getEmailAddress());
		assertTrue(person.isValidPerson());
	}
}
