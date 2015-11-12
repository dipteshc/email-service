package main.java.com.diptesh.sender;

import static main.java.com.diptesh.email.Email.Format.TEXT;

import java.io.File;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Person;

/**
 * Implementation of Sender for interfacing with Mailgun.
 *
 * @author diptesh.chatterjee
 *
 */
public final class MailgunSender implements Sender {
	private static final String API_KEY = "";
	private static final String URI = "https://api.mailgun.com/v3/sandbox434e53f77df14be581b568a4da667854.mailgun.org/messages";
	private SenderConnection connection = null;
	private EmailResponse response = null;

	@Override
	public SenderConnection getConnection() {
		return connection;
	}

	@Override
	public EmailResponse getResponse() {
		return response;
	}

	@Override
	public void init() {
		final Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", API_KEY));
		connection = new SenderConnection(client.resource(URI));
	}

	private FormDataMultiPart populateFormData(final Email email) {
		final FormDataMultiPart htmlForm = new FormDataMultiPart();
		htmlForm.field("from", SenderUtils.createMimeEntityFromPerson(email.getSender()));
		for (final Person recepient : email.getRecepients()) {
			htmlForm.field("to", SenderUtils.createMimeEntityFromPerson(recepient));
		}
		for (final Person cc : email.getCCList()) {
			htmlForm.field("cc", SenderUtils.createMimeEntityFromPerson(cc));
		}
		for (final Person bcc : email.getBccList()) {
			htmlForm.field("bcc", SenderUtils.createMimeEntityFromPerson(bcc));
		}
		htmlForm.field("subject", email.getSubject());
		htmlForm.field("text", email.getBody());
		for (final Attachment attachment : email.getAttachments()) {
			final File file = new File(attachment.getPath());
			// TODO: Based on filetype use the right media type.
			htmlForm.bodyPart(new FileDataBodyPart("attachment", file, MediaType.TEXT_PLAIN_TYPE));
		}
		return htmlForm;
	}

	private MultivaluedMapImpl populateTextFormData(final Email email) {
		final MultivaluedMapImpl mime = new MultivaluedMapImpl();
		mime.add("from", SenderUtils.createMimeEntityFromPerson(email.getSender()));
		for (final Person recepient : email.getRecepients()) {
			mime.add("to", SenderUtils.createMimeEntityFromPerson(recepient));
		}
		for (final Person cc : email.getCCList()) {
			mime.add("cc", SenderUtils.createMimeEntityFromPerson(cc));
		}
		for (final Person bcc : email.getBccList()) {
			mime.add("bcc", SenderUtils.createMimeEntityFromPerson(bcc));
		}
		mime.add("subject", email.getSubject());
		mime.add("text", email.getBody());
		return mime;
	}

	/*
	 * Send an email through Mailgun.
	 */
	@Override
	public void send(final Email email) throws Exception {
		if (email.getFormat() == TEXT) {
			final MultivaluedMapImpl formData = populateTextFormData(email);
			response = new EmailResponse(connection.getMailgunConn()
					.type(MediaType.APPLICATION_FORM_URLENCODED)
					.post(ClientResponse.class, formData));
		} else {
			final FormDataMultiPart htmlForm = populateFormData(email);
			response = new EmailResponse(connection.getMailgunConn()
					.type(MediaType.MULTIPART_FORM_DATA_TYPE)
					.post(ClientResponse.class, htmlForm));
		}
	}
}
