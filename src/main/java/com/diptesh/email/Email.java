package main.java.com.diptesh.email;

import java.util.ArrayList;
import java.util.List;

public final class Email {
	public static final class Builder {
		private final Email email;

		public Builder() {
			email = new Email();
		}

		public Builder addAttachment(final Attachment attach) {
			email.attachments.add(attach);
			return this;
		}

		public Builder addBcc(final Person bcc) {
			email.bccList.add(bcc);
			return this;
		}

		public Builder addCC(final Person cc) {
			email.ccList.add(cc);
			return this;
		}

		public Builder addRecepient(final Person recepient) {
			email.recepients.add(recepient);
			return this;
		}

		public Email build() {
			if (email.body == null || email.body.isEmpty()) {
				email.body = " ";
			}
			if (email.format == Format.HTML) {
				email.convertBodyToHtml();
			}
			return email;
		}

		public Builder setBody(final String body) {
			email.body = body;
			return this;
		}

		public Builder setFormat(final Format format) {
			email.format = format;
			return this;
		}

		public Builder setSender(final Person sender) {
			email.sender = sender;
			return this;
		}

		public Builder setSubject(final String subject) {
			email.subject = subject;
			return this;
		}
	};

	public static enum Format {
		HTML, TEXT
	}

	private final List<Attachment> attachments;
	private final List<Person> bccList;
	private String body;
	private final List<Person> ccList;
	private Format format;
	private final List<Person> recepients;
	private Person sender;
	private String subject;

	private Email() {
		sender = null;
		subject = null;
		body = null;
		format = null;
		recepients = new ArrayList<Person>();
		ccList = new ArrayList<Person>();
		bccList = new ArrayList<Person>();
		attachments = new ArrayList<Attachment>();
	}

	private void convertBodyToHtml() {
		final StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		for (final String s : body.split("\n")) {
			sb.append(s);
			sb.append("<br>");
		}
		sb.append("</html>");
		body = sb.toString();
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public List<Person> getBccList() {
		return bccList;
	}

	public String getBody() {
		return body;
	}

	public List<Person> getCCList() {
		return ccList;
	}

	public Format getFormat() {
		return format;
	}

	public List<Person> getRecepients() {
		return recepients;
	}

	public Person getSender() {
		return sender;
	}

	public String getSubject() {
		return subject;
	}

	public boolean isValidEmail() {
		if (subject == null || subject.isEmpty()) {
			return false;
		}
		if (sender == null) {
			return false;
		}
		if (!sender.isValidPerson()) {
			return false;
		}
		for (final Person recepient : recepients) {
			if (!recepient.isValidPerson()) {
				return false;
			}
		}
		for (final Person cc : ccList) {
			if (!cc.isValidPerson()) {
				return false;
			}
		}
		for (final Person bcc : bccList) {
			if (!bcc.isValidPerson()) {
				return false;
			}
		}
		return true;
	}
}
