package os.rabbit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRequestCycleListener {

	void requestStart(HttpServletRequest request, HttpServletResponse response);

	void requestEnd(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);

}
