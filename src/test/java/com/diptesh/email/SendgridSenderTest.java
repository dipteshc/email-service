package test.java.com.diptesh.email;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.com.diptesh.sender.Sender.EmailClient;
import main.java.com.diptesh.sender.Sender.SenderConnection;
import main.java.com.diptesh.sender.SendgridSender;

public class SendgridSenderTest {
	@Test
	public void testGetConnection() {
		final SendgridSender sender = new SendgridSender();
		sender.init();
		assertTrue(sender.getConnection() instanceof SenderConnection);
	}

	@Test
	public void testInit() {
		final SendgridSender sender = new SendgridSender();
		sender.init();
		final SenderConnection conn = sender.getConnection();
		assertEquals(EmailClient.SENDGRID, conn.getClient());
		assertNull(conn.getMailgunConn());
		assertNotNull(conn.getSendgridConn());
	}

	@Test
	public void testSend() {

	}
}
