package main.java.com.diptesh.email;

public final class Attachment {
	private final String name;
	private final String path;

	public Attachment(final String path) {
		this.path = path;
		name = getAttachmentName(path);
	}

	public Attachment(final String path, final String name) {
		this.name = name;
		this.path = path;
	}

	private String getAttachmentName(final String dirpath) {
		final String[] splitPath = dirpath.split("/");
		return splitPath[splitPath.length - 1];
	}

	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}
}
