package os.rabbit.test;

import os.rabbit.components.Label;
import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.parser.Tag;

/**
 * @author Allen
 * @date 2014/2/21 下午2:40:01
 */
public class ADLogin extends SpringBeanSupportComponent {
	private Label lblMsg;

	public ADLogin(Tag tag) {
		super(tag);
	}

	@Override
	protected void beforeRender() {
		super.beforeRender();
//		IUserService userService = (IUserService) getBean("userService");
//		ValidateObj vo = userService.validateAccount(getPage().getRequest(), getPage().getResponse());
//		if (vo == null) {
//			System.out.println("111  ");
//		} else {
//			if (vo.isPass()) {
//				String url = vo.getUrl();
//				if (url == null)
//					url = this.getTag().getAttribute("url");
//
////				getPage().setRedirect(url);
////				throw new RenderInterruptedException();
//			} else {
//				lblMsg.setValue(vo.getMsg());
//			}
//		}

	}

	@Override
	protected void initial() {
		super.initial();

	}

}
