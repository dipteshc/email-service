/**
 *
 */
package test.java.com.diptesh.email;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import main.java.com.diptesh.email.Attachment;

/**
 * Unit test for {@code Attachment}.
 *
 * @author diptesh.chatterjee
 *
 */
public class AttachmentTest {

	@Test
	public void testPath() {
		Attachment attach = new Attachment("/test/path");
		assertEquals("/test/path", attach.getPath());
		assertEquals("path", attach.getName());

		attach = new Attachment("testpath");
		assertEquals("testpath", attach.getPath());
		assertEquals("testpath", attach.getName());
	}

	@Test
	public void testPathAndName() {
		final Attachment attach = new Attachment("testPath", "testName");
		assertEquals("testName", attach.getName());
		assertEquals("testPath", attach.getPath());
	}

}
