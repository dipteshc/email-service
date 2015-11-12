package main.java.com.diptesh.sender;

import static main.java.com.diptesh.email.Email.Format.TEXT;

import java.io.IOException;

import com.sendgrid.SendGrid;

import main.java.com.diptesh.email.Attachment;
import main.java.com.diptesh.email.Email;
import main.java.com.diptesh.email.Person;

/**
 * Implementation of Sender for interfacing with Sendgrid.
 *
 * @author diptesh.chatterjee
 *
 */
public class SendgridSender implements Sender {
	private SenderConnection connection = null;
	private EmailResponse response = null;

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
	public SenderConnection getConnection() {
		return connection;
	}

	@Override
	public EmailResponse getResponse() {
		return response;
	}

	@Override
	public void init() {
		connection = new SenderConnection(new SendGrid("dipteshc", "pikachu123"));
	}

	/*
	 * Send an email through Sendgrid.
	 */
	@Override
	public void send(final Email email) throws Exception {
		final SendGrid.Email sendgridEmail = createMimeForSendgrid(email);
		response = new EmailResponse(connection.getSendgridConn().send(sendgridEmail));
	}

}
