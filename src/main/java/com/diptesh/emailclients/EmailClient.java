package main.java.com.diptesh.emailclients;

import main.java.com.diptesh.connection.EmailConnectionProvider;
import main.java.com.diptesh.email.Email;

public abstract class EmailClient {
	public static enum EmailProvider {
		MAILGUN, SENDGRID
	};

	public static final class EmailResponse {
		private final int code;
		private final String payload;

		public EmailResponse(final int code, final String payload) {
			this.code = code;
			this.payload = payload;
		}

		public int getCode() {
			return code;
		}

		public String getPayload() {
			return payload;
		}
	}

	protected EmailConnectionProvider factory = null;

	public EmailClient(final EmailConnectionProvider factory) {
		this.factory = factory;
	}

	public void getEmailConnection() {
		return;
	}

	public void resetConnection() {
		return;
	}

	public EmailResponse send(final Email email) {
		return null;
	}
}
