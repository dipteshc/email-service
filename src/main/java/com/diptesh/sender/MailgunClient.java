package main.java.com.diptesh.sender;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

import main.java.com.diptesh.connection.EmailConnectionProvider;
import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Email.Format;
import main.java.com.diptesh.email.Person;

public class MailgunClient extends EmailClient {
	private WebResource.Builder htmlConn = null;
	private WebResource.Builder textConn = null;

	public MailgunClient(final EmailConnectionProvider factory) {
		super(factory);
	}

	@Override
	public void getEmailConnection() {
		htmlConn = factory.getMailgunBuilderHTMLConn();
		textConn = factory.getMailgunBuilderTextConn();
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

	private String readInputStream(final InputStream stream) {
		int byteRead;
		final StringBuilder sb = new StringBuilder();
		try {
			while ((byteRead = stream.read()) != -1) {
				sb.append((char) byteRead);
			}
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	@Override
	public void resetConnection() {
		htmlConn = factory.getNewMailgunBuilderHTMLConn();
		textConn = factory.getNewMailgunBuilderTextConn();
	}

	@Override
	public EmailResponse send(final Email email) {
		if (email.getFormat() == Format.HTML) {
			if (htmlConn == null) {
				getEmailConnection();
			}
			final FormDataMultiPart formData = populateFormData(email);
			final ClientResponse clientRes = htmlConn.post(ClientResponse.class, formData);
			return new EmailResponse(
					clientRes.getStatus(), readInputStream(clientRes.getEntityInputStream()));

		} else {
			if (textConn == null) {
				getEmailConnection();
			}
			final MultivaluedMapImpl textFormat = populateTextFormData(email);
			final ClientResponse clientRes = textConn.post(ClientResponse.class, textFormat);
			return new EmailResponse(
					clientRes.getStatus(), readInputStream(clientRes.getEntityInputStream()));
		}
	}
}
