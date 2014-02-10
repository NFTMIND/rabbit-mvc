package os.rabbit.components.form;

import java.io.File;

public class FileItem {
	private String id;
	private String name;
	private File file;

	public FileItem(String id, String name, File file) {
		super();
		this.id = id;
		this.name = name;
		this.file = file;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}
