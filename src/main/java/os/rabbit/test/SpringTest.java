package os.rabbit.test;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import os.rabbit.components.SpringBeanSupportComponent;
import os.rabbit.dao.IDaoManager;
import os.rabbit.parser.Tag;

public class SpringTest extends SpringBeanSupportComponent {

	public SpringTest(Tag tag) {
		super(tag);
	
	}

	@Autowired
	private IDaoManager daoManager;
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void beforeRender() {
		super.beforeRender();
		
	}
}
