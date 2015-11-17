package main.java.com.diptesh.connection;

import javax.ws.rs.core.MediaType;

import com.sendgrid.SendGrid;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class EmailConnectionProvider {
	private static final String MAILGUN_API_KEY = "";
	private static final String MAILGUN_URI = "https://api.mailgun.com/v3/sandbox434e53f77df14be581b568a4da667854.mailgun.org/messages";
	private static WebResource.Builder mailgunHtmlConnBuilder = null;
	private static WebResource.Builder mailgunTextConnBuilder = null;
	private static final String SENDGRID_PASS = "pikachu123";
	private static final String SENDGRID_USER = "dipteshc";
	private static SendGrid sendGridConn = null;

	public WebResource.Builder getMailgunBuilderHTMLConn() {
		if (mailgunHtmlConnBuilder == null) {
			mailgunHtmlConnBuilder = getUriResource().type(MediaType.MULTIPART_FORM_DATA_TYPE);
		}
		return mailgunHtmlConnBuilder;
	}

	public WebResource.Builder getMailgunBuilderTextConn() {
		if (mailgunTextConnBuilder == null) {
			mailgunTextConnBuilder = getUriResource().type(MediaType.APPLICATION_FORM_URLENCODED);
		}
		return mailgunTextConnBuilder;
	}

	public WebResource.Builder getNewMailgunBuilderHTMLConn() {
		mailgunHtmlConnBuilder = null;
		return getMailgunBuilderHTMLConn();
	}

	public WebResource.Builder getNewMailgunBuilderTextConn() {
		mailgunTextConnBuilder = null;
		return getMailgunBuilderTextConn();
	}
	
	public SendGrid getNewSendGridConn() {
		sendGridConn = null;
		return getSendGridConn();
	}

	public SendGrid getSendGridConn() {
		if (sendGridConn == null) {
			sendGridConn = new SendGrid(SENDGRID_USER, SENDGRID_PASS);
		}
		return sendGridConn;
	}

	private WebResource getUriResource() {
		final Client client = Client.create();
		client.addFilter(new HTTPBasicAuthFilter("api", MAILGUN_API_KEY));
		return client.resource(MAILGUN_URI);
	}
}
