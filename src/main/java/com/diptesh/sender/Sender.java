package main.java.com.diptesh.sender;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Response;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import main.java.com.diptesh.email.Email;

/*
 * Interface to be implemented by any sender module. There will be one sender per email service
 * provider.
 *
 * @author diptesh.chatterjee
 */
public interface Sender {
	public static enum EmailClient {
		MAILGUN, SENDGRID
	};

	public static final class EmailResponse {
		private final EmailClient client;
		private final ClientResponse mailgunResponse;
		private final Response sendgridResponse;

		public EmailResponse(final ClientResponse mailgunResponse) {
			client = EmailClient.MAILGUN;
			this.mailgunResponse = mailgunResponse;
			sendgridResponse = null;
		}

		public EmailResponse(final Response sendgridResponse) {
			client = EmailClient.SENDGRID;
			this.sendgridResponse = sendgridResponse;
			mailgunResponse = null;
		}

		public EmailClient getClient() {
			return client;
		}

		public ClientResponse getMailgunResponse() {
			return mailgunResponse;
		}

		public Response getSendgridResponse() {
			return sendgridResponse;
		}
	}

	public static final class SenderConnection {
		private final EmailClient client;
		private final WebResource mailgunConnection;
		private final SendGrid sendgridConnection;

		public SenderConnection(final SendGrid conn) {
			client = EmailClient.SENDGRID;
			mailgunConnection = null;
			sendgridConnection = conn;
		}

		public SenderConnection(final WebResource conn) {
			client = EmailClient.MAILGUN;
			mailgunConnection = conn;
			sendgridConnection = null;
		}

		public EmailClient getClient() {
			return client;
		}

		public WebResource getMailgunConn() {
			return mailgunConnection;
		}

		public SendGrid getSendgridConn() {
			return sendgridConnection;
		}
	};

	/*
	 * Returns a connection object for the client.
	 */
	public SenderConnection getConnection();

	/*
	 * Returns a response object for the specified sender.
	 */
	public EmailResponse getResponse();

	/*
	 * Each sender must call this method first in order to create a
	 * authenticated connection to the corresponding SMTP server.
	 */
	public void init();

	/*
	 * Each sender will implement this method in order to use the proper API
	 * calls for sending emails.
	 */
	public void send(final Email email) throws Exception;
}
