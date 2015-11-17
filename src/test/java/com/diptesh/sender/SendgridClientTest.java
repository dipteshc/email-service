package test.java.com.diptesh.sender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.mockito.Mockito;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

import main.java.com.diptesh.connection.EmailConnectionProvider;
import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Email.Format;
import main.java.com.diptesh.email.Person;
import main.java.com.diptesh.sender.EmailClient.EmailResponse;
import main.java.com.diptesh.sender.SendgridClient;

/**
 * Unit tests for {@link SendgridClient}
 *
 * @author diptesh.chatterjee
 *
 */
public class SendgridClientTest {

	@Test
	public void testSendException() throws SendGridException {
		final EmailConnectionProvider mockFactory = Mockito.mock(EmailConnectionProvider.class);
		final SendGrid mockConn = mock(SendGrid.class);
		when(mockFactory.getSendGridConn()).thenReturn(mockConn);
		doThrow(new SendGridException(new Exception("Test"))).when(mockConn).send(isA(SendGrid.Email.class));

		final Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.addRecepient(new Person("foo1@bar.com"))
				.setBody("body")
				.setSubject("subject")
				.addCC(new Person("foo2@bar.com"))
				.addBcc(new Person("foo3@bar.com"))
				.addAttachment(new Attachment("path"))
				.setFormat(Format.HTML)
				.build();
		final SendgridClient client = new SendgridClient(mockFactory);
		final EmailResponse res = client.send(email);
		assertNull(res);
	}

	@Test
	public void testSendNoException() throws SendGridException {
		final EmailConnectionProvider mockFactory = Mockito.mock(EmailConnectionProvider.class);
		final SendGrid mockConn = mock(SendGrid.class);
		when(mockFactory.getSendGridConn()).thenReturn(mockConn);
		when(mockConn.send(isA(SendGrid.Email.class))).thenReturn(new Response(200, "OK"));
		final Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.addRecepient(new Person("foo1@bar.com"))
				.setBody("body")
				.setSubject("subject")
				.setFormat(Format.TEXT)
				.build();
		final SendgridClient client = new SendgridClient(mockFactory);
		final EmailResponse response = client.send(email);
		assertEquals(200, response.getCode());
		assertEquals("OK", response.getPayload());
	}
}
