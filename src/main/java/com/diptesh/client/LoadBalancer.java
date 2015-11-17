package main.java.com.diptesh.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import main.java.com.diptesh.emailclients.EmailClient;

/*
 * Load balancer class that takes in a list of {@code EmailClient} instances and their limits. It
 * load balances an email that needs to be sent. Returns an ordered list of EmailClient instances
 * for querying. Querying must be done in that order of preference.
 */
public class LoadBalancer {
	private class MapEntryComparatorReverse implements Comparator<Entry<EmailClient, Integer>> {

		public int compare(final Entry<EmailClient, Integer> o1, final Entry<EmailClient, Integer> o2) {
			return Integer.compare(o2.getValue(), o1.getValue());
		}

	}

	public List<EmailClient> getOrdering(
			final Map<EmailClient, Integer> remainingConnections) {
		final List<Entry<EmailClient, Integer>> entryList = new ArrayList<Entry<EmailClient, Integer>>(remainingConnections.entrySet());
		Collections.sort(entryList, new MapEntryComparatorReverse());
		final List<EmailClient> clientOrdering = new ArrayList<EmailClient>();
		for (final Entry<EmailClient, Integer> entry : entryList) {
			if (entry.getValue() > 0) {
				clientOrdering.add(entry.getKey());
			}
		}
		return clientOrdering;
	}
}
