package main.java.com.diptesh.emailclients;

import static main.java.com.diptesh.email.Email.Format.TEXT;

import java.io.IOException;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Response;
import com.sendgrid.SendGridException;

import main.java.com.diptesh.connection.EmailConnectionProvider;
import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Person;

public class SendgridClient extends EmailClient {
	private SendGrid conn = null;

	public SendgridClient(final EmailConnectionProvider factory) {
		super(factory);
	}

	private SendGrid.Email createMimeForSendgrid(final Email email) {
		final SendGrid.Email sendgridEmail = new SendGrid.Email();
		sendgridEmail.setFrom(SenderUtils.createMimeEntityFromPerson(email.getSender()));
		for (final Person recepient : email.getRecepients()) {
			sendgridEmail.addTo(SenderUtils.createMimeEntityFromPerson(recepient));
		}
		for (final Person cc : email.getCCList()) {
			sendgridEmail.addCc(SenderUtils.createMimeEntityFromPerson(cc));
		}
		for (final Person bcc : email.getBccList()) {
			sendgridEmail.addBcc(SenderUtils.createMimeEntityFromPerson(bcc));
		}
		sendgridEmail.setSubject(email.getSubject());
		if (email.getFormat() == TEXT) {
			sendgridEmail.setText(email.getBody());
		} else {
			sendgridEmail.setHtml(email.getBody());
			for (final Attachment attachment : email.getAttachments()) {
				try {
					sendgridEmail.addAttachment(attachment.getName(), attachment.getPath());
				} catch (final IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sendgridEmail;
	}

	@Override
	public void getEmailConnection() {
		conn = factory.getSendGridConn();
	}

	@Override
	public void resetConnection() {
		conn = factory.getNewSendGridConn();
	}

	@Override
	public EmailResponse send(final Email email) {
		if (conn == null) {
			getEmailConnection();
		}
		EmailResponse res = null;
		try {
			final Response resp = conn.send(createMimeForSendgrid(email));
			res = new EmailResponse(resp.getCode(), resp.getMessage());
		} catch (final SendGridException e) {
			e.printStackTrace();
		}
		return res;
	}
}
