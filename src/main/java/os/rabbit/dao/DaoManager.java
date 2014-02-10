package os.rabbit.dao;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DaoManager implements IDaoManager {

	public DataSource dataSource;

	public DaoManager() {
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public IDaoTransaction beginTransaction() throws SQLException {
		return new DaoTransaction(dataSource.getConnection());
	}

	public IDaoObject getDaoObject(Class<?> interfaces, Class<? extends IDaoObject> daoClass) throws SQLException {

		try {
			IDaoObject daoObject = (IDaoObject) daoClass.newInstance();
			return (IDaoObject) Proxy.newProxyInstance(DaoManager.class.getClassLoader(), 
					new Class[] { IDaoObject.class, interfaces }, 
					new InvokeProxy(daoObject));
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return null;
	}

	class InvokeProxy implements InvocationHandler {
		private IDaoObject daoObject;

		public InvokeProxy(IDaoObject daoObject) {
			this.daoObject = daoObject;
		}

	
		public Object invoke(Object object, Method method, Object[] params) throws Throwable {

			if (method.getName().equals("setConnection")) {

				Object returnObject = method.invoke(object, params);
				return returnObject;
			}

			Connection con = null;
			try {
				synchronized (object) {
					con = dataSource.getConnection();
					daoObject.setConnection(con);
					return method.invoke(daoObject, params);
	
				}
			} finally {
				if (con != null && !con.isClosed())
					con.close();
			}

		}
	}
}
