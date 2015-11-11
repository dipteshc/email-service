/**
 *
 */
package test.java.com.diptesh.email;

import static main.java.com.diptesh.email.Email.Format.HTML;
import static main.java.com.diptesh.email.Email.Format.TEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Person;

/**
 * Unit tests for {@link Email}
 *
 * @author diptesh.chatterjee
 *
 */
public class EmailTest {
	@Test
	public void testInvalidBCC() {
		Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person("foo1@bar.com"))
				.addCC(new Person("foo2@bar.com"))
				.addBcc(new Person(""))
				.build();
		assertFalse(email.isValidEmail());

		email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person("foo1@bar.com"))
				.addCC(new Person("foo2@bar.com"))
				.addBcc(new Person(null))
				.build();
		assertFalse(email.isValidEmail());
	}

	@Test
	public void testInvalidCC() {
		Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person("bar@bar.com"))
				.addCC(new Person(""))
				.build();
		assertFalse(email.isValidEmail());

		email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person("bar@bar.com"))
				.addCC(new Person(null))
				.build();
		assertFalse(email.isValidEmail());
	}

	@Test
	public void testInvalidRecepient() {
		Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person(""))
				.build();
		assertFalse(email.isValidEmail());

		email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.setSubject("New subject")
				.addRecepient(new Person(null))
				.build();
		assertFalse(email.isValidEmail());
	}

	@Test
	public void testMissingSenderOrSubject() {
		final Email email = new Email.Builder()
				.build();
		assertFalse(email.isValidEmail());
	}

	@Test
	public void testValidEmailHtml() {
		final Email email = new Email.Builder()
				.setSubject("Test")
				.setBody("Body")
				.setFormat(HTML)
				.addRecepient(new Person("foo@bar.com"))
				.addCC(new Person("foo1@bar.com"))
				.addBcc(new Person("foo2@bar.com"))
				.addAttachment(new Attachment("/test/path"))
				.build();
		assertEquals("Test", email.getSubject());
		assertEquals("<html>Body<br></html>", email.getBody());
		assertEquals(HTML, email.getFormat());
		assertEquals(1, email.getRecepients().size());
		assertEquals("foo@bar.com", email.getRecepients().get(0).getEmailAddress());
		assertEquals("", email.getRecepients().get(0).getName());
		assertEquals(1, email.getCCList().size());
		assertEquals("foo1@bar.com", email.getCCList().get(0).getEmailAddress());
		assertEquals("", email.getCCList().get(0).getName());
		assertEquals(1, email.getBccList().size());
		assertEquals("foo2@bar.com", email.getBccList().get(0).getEmailAddress());
		assertEquals("", email.getBccList().get(0).getName());
		assertEquals(1, email.getAttachments().size());
		assertEquals("/test/path", email.getAttachments().get(0).getPath());
		assertEquals("path", email.getAttachments().get(0).getName());
	}

	@Test
	public void testValidEmailText() {
		final Email email = new Email.Builder()
				.setSubject("Test")
				.setBody("Body")
				.setFormat(TEXT)
				.addRecepient(new Person("foo@bar.com"))
				.addCC(new Person("foo1@bar.com"))
				.addBcc(new Person("foo2@bar.com"))
				.addAttachment(new Attachment("/test/path"))
				.build();
		assertEquals("Test", email.getSubject());
		assertEquals("Body", email.getBody());
		assertEquals(TEXT, email.getFormat());
		assertEquals(1, email.getRecepients().size());
		assertEquals("foo@bar.com", email.getRecepients().get(0).getEmailAddress());
		assertEquals("", email.getRecepients().get(0).getName());
		assertEquals(1, email.getCCList().size());
		assertEquals("foo1@bar.com", email.getCCList().get(0).getEmailAddress());
		assertEquals("", email.getCCList().get(0).getName());
		assertEquals(1, email.getBccList().size());
		assertEquals("foo2@bar.com", email.getBccList().get(0).getEmailAddress());
		assertEquals("", email.getBccList().get(0).getName());
		assertEquals(1, email.getAttachments().size());
		assertEquals("/test/path", email.getAttachments().get(0).getPath());
		assertEquals("path", email.getAttachments().get(0).getName());
	}
}
