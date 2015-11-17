package com.diptesh.emailclients;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.diptesh.email.Person;
import main.java.com.diptesh.emailclients.SenderUtils;

/*
 * Unit tests for {@link SenderUtils}
 *
 * @Author diptesh.chatterjee
 */
public class SenderUtilsTest {
	@Test
	public void testNoName() {
		final Person person = new Person("foo@bar.com");
		final String expectedAns = "<foo@bar.com>";
		assertEquals(expectedAns, SenderUtils.createMimeEntityFromPerson(person));
	}

	@Test
	public void testWithName() {
		final Person person = new Person("foo", "foo@bar.com");
		final String expectedAns = "foo <foo@bar.com>";
		assertEquals(expectedAns, SenderUtils.createMimeEntityFromPerson(person));
	}
}
