package test.java.com.diptesh.sender;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;
import org.mockito.Mockito;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;

import main.java.com.diptesh.connection.EmailConnectionProvider;
import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Email.Format;
import main.java.com.diptesh.email.Person;
import main.java.com.diptesh.sender.EmailClient.EmailResponse;
import main.java.com.diptesh.sender.MailgunClient;

public class MailgunClientTest {

	@Test
	public void testSendHtml() {
		final EmailConnectionProvider mockFactory = Mockito.mock(EmailConnectionProvider.class);
		final WebResource.Builder mockTextConn = Mockito.mock(WebResource.Builder.class);
		final WebResource.Builder mockHtmlConn = Mockito.mock(WebResource.Builder.class);
		final InputStream responseStream = new ByteArrayInputStream("OK".getBytes());
		final ClientResponse testResponse = new ClientResponse(
				200, new InBoundHeaders(), responseStream, null);

		when(mockFactory.getMailgunBuilderHTMLConn()).thenReturn(mockHtmlConn);
		when(mockFactory.getMailgunBuilderTextConn()).thenReturn(mockTextConn);
		when(mockHtmlConn.post((Class<ClientResponse>) any(Class.class),
				isA(FormDataMultiPart.class))).thenReturn(testResponse);

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
		final MailgunClient testClient = new MailgunClient(mockFactory);
		final EmailResponse actualResponse = testClient.send(email);
		assertEquals(200, actualResponse.getCode());
		assertEquals("OK", actualResponse.getPayload());
	}

	@Test
	public void testSendText() {
		final EmailConnectionProvider mockFactory = Mockito.mock(EmailConnectionProvider.class);
		final WebResource.Builder mockTextConn = Mockito.mock(WebResource.Builder.class);
		final WebResource.Builder mockHtmlConn = Mockito.mock(WebResource.Builder.class);
		final InputStream responseStream = new ByteArrayInputStream("OK".getBytes());
		final ClientResponse testResponse = new ClientResponse(
				200, new InBoundHeaders(), responseStream, null);

		when(mockFactory.getMailgunBuilderHTMLConn()).thenReturn(mockHtmlConn);
		when(mockFactory.getMailgunBuilderTextConn()).thenReturn(mockTextConn);
		when(mockTextConn.post((Class<ClientResponse>) any(Class.class),
				isA(MultivaluedMapImpl.class))).thenReturn(testResponse);

		final Email email = new Email.Builder()
				.setSender(new Person("foo@bar.com"))
				.addRecepient(new Person("foo1@bar.com"))
				.setBody("body")
				.setSubject("subject")
				.addCC(new Person("foo2@bar.com"))
				.addBcc(new Person("foo3@bar.com"))
				.setFormat(Format.TEXT)
				.build();
		final MailgunClient testClient = new MailgunClient(mockFactory);
		final EmailResponse actualResponse = testClient.send(email);
		assertEquals(200, actualResponse.getCode());
		assertEquals("OK", actualResponse.getPayload());
	}
}
