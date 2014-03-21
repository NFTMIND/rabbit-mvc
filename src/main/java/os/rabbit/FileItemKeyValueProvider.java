package os.rabbit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileItemKeyValueProvider implements IKeyValueProvider {
	class LinkedValue {
		public Object value;
		public LinkedValue next;
		public LinkedValue prev;
	}
	private HttpServletRequest request;
	private HashMap<String, LinkedValue> objectMap = new HashMap<String, LinkedValue>();

	public FileItemKeyValueProvider(HttpServletRequest request) {
		this.request = request;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = (FileItem) iter.next();

				LinkedValue newValue = new LinkedValue();
				LinkedValue object = objectMap.get(item.getFieldName());
				if(object != null) {
					object.next = newValue;
					newValue.prev = object;
				}
				objectMap.put(item.getFieldName(), newValue);
				if (item.isFormField()) {
					newValue.value = item.getString("utf-8");
				} else {
					if (item.getSize() > 0) {
						UploadFile file = new UploadFile();
						file.setData(item.get());
						file.setSize(item.getSize());
						file.setContentType(item.getContentType());
						file.setFileName(item.getName());
						newValue.value = file;
					}
				}
				
				
				
//				
//				if (item.isFormField()) {
//				//	System.out.println(item.getFieldName() + ":" + item.getString("utf-8"));
//					if(objectMap.containsKey(item.getFieldName())) {
//					
//					} else {
//						LinkedValue value = new LinkedValue();
//						value.value = item.getString("utf-8");
//						objectMap.put(item.getFieldName(), value);
//					}
//				} else {
//			
//					if (item.getSize() > 0) {
//						UploadFile file = new UploadFile();
//						file.setData(item.get());
//						file.setSize(item.getSize());
//						file.setContentType(item.getContentType());
//						file.setFileName(item.getName());
//						objectMap.put(item.getFieldName(), file);
//					}
//				}
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
		LinkedValue value = objectMap.get(name);
		if(value == null) {
			String[] values = request.getParameterValues(name);
			if(values != null) {
				if(values.length > 1) {
					return values;
				}
				return values[0];
			} else {
				return null;
			}
		}

		if(value.prev != null) {
			LinkedList<Object> list = new LinkedList<Object>();
			while(value != null) {
				list.addFirst(value.value);
				value = value.prev;
			}
			if(list.getFirst() instanceof String) {
				String[] valueList = new String[list.size()];
				for(int i = 0; i < valueList.length; i++) {
					valueList[i] = (String)list.get(i);
				}
				return valueList;
			} else if(list.getFirst() instanceof UploadFile) {
				UploadFile[] valueList = new UploadFile[list.size()];
				for(int i = 0; i < valueList.length; i++) {
					valueList[i] = (UploadFile)list.get(i);
				}
				return valueList;
			}
			
			
		}
		return value.value;
	}
}
