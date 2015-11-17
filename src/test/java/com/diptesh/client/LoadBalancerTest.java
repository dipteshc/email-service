/**
 *
 */
package com.diptesh.client;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import main.java.com.diptesh.client.LoadBalancer;
import main.java.com.diptesh.emailclients.EmailClient;
import main.java.com.diptesh.emailclients.MailgunClient;
import main.java.com.diptesh.emailclients.SendgridClient;

/**
 * Unit tests for {@link LoadBalancer}
 *
 * @author diptesh.chatterjee
 *
 */
public class LoadBalancerTest {

	@Test
	public void testAllNonzero() {
		final EmailClient c1 = new MailgunClient(null);
		final EmailClient c2 = new MailgunClient(null);
		final EmailClient c3 = new SendgridClient(null);
		final Map<EmailClient, Integer> remainingConn = new HashMap<EmailClient, Integer>();
		remainingConn.put(c1, 5);
		remainingConn.put(c2, 10);
		remainingConn.put(c3, 6);
		final LoadBalancer lb = new LoadBalancer();

		final List<EmailClient> result = lb.getOrdering(remainingConn);
		assertEquals(3, result.size());
		assertEquals(c2, result.get(0));
		assertEquals(c3, result.get(1));
		assertEquals(c1, result.get(2));
	}

	@Test
	public void testAllZero() {
		final EmailClient c1 = new MailgunClient(null);
		final EmailClient c2 = new MailgunClient(null);
		final EmailClient c3 = new SendgridClient(null);
		final Map<EmailClient, Integer> remainingConn = new HashMap<EmailClient, Integer>();
		remainingConn.put(c1, 0);
		remainingConn.put(c2, 0);
		remainingConn.put(c3, 0);
		final LoadBalancer lb = new LoadBalancer();

		final List<EmailClient> result = lb.getOrdering(remainingConn);
		assertEquals(0, result.size());
	}

	@Test
	public void testSomeNonzero() {
		final EmailClient c1 = new MailgunClient(null);
		final EmailClient c2 = new MailgunClient(null);
		final EmailClient c3 = new SendgridClient(null);
		final Map<EmailClient, Integer> remainingConn = new HashMap<EmailClient, Integer>();
		remainingConn.put(c1, 5);
		remainingConn.put(c2, 0);
		remainingConn.put(c3, 6);
		final LoadBalancer lb = new LoadBalancer();

		final List<EmailClient> result = lb.getOrdering(remainingConn);
		assertEquals(2, result.size());
		assertEquals(c3, result.get(0));
		assertEquals(c1, result.get(1));
	}
}
