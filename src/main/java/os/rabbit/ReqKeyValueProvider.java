package os.rabbit;

import javax.servlet.http.HttpServletRequest;

public class ReqKeyValueProvider implements IKeyValueProvider {
	private HttpServletRequest request;

	public ReqKeyValueProvider(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public Object get(String name) {
		String[] values = request.getParameterValues(name + "[]");
		if (values == null) {
			values = request.getParameterValues(name);
		}
		if (values != null) {
			if (values.length == 1)
				return values[0];
			return values;
		}
		return request.getParameter(name);
	}
}
