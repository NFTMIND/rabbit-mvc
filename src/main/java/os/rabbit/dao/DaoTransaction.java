package os.rabbit.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DaoTransaction implements IDaoTransaction {

	private Connection connection;

	public DaoTransaction(Connection connection) throws SQLException {
		this.connection = connection;
		connection.setAutoCommit(false);
	}

	public void commit() throws SQLException {
		connection.commit();

	}

	public void rollback() throws SQLException {
		connection.rollback();
	
	}

	public void close() throws SQLException {
		connection.close();
	

	}

	public IDaoObject getDaoObject(Class<? extends IDaoObject> daoClass) throws SQLException {

		IDaoObject daoObject;
		try {
			daoObject = (IDaoObject) daoClass.newInstance();
			daoObject.setConnection(connection);
		
			return daoObject;

		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
