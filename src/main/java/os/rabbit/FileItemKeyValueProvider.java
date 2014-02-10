package os.rabbit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileItemKeyValueProvider implements IKeyValueProvider {
	private HttpServletRequest request;
	private HashMap<String, Object> objectMap = new HashMap<String, Object>();

	public FileItemKeyValueProvider(HttpServletRequest request) {
		this.request = request;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				if (item.isFormField()) {
			
					objectMap.put(item.getFieldName(), item.getString("utf-8"));
				} else {
			
					if (item.getSize() > 0) {
						UploadFile file = new UploadFile();
						file.setData(item.get());
						file.setSize(item.getSize());
						file.setContentType(item.getContentType());
						file.setFileName(item.getName());
						objectMap.put(item.getFieldName(), file);
					}
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Object get(String name) {
		Object value = objectMap.get(name);
		if(value == null) {
			String[] values = request.getParameterValues(name);
			if(values != null) {
				if(values.length > 1) {
					return values;
				}
				return values[0];
			}
		}
		return value;
	}
}
