package os.rabbit.dao;

import java.sql.SQLException;

public interface IDaoTransaction {

	public void commit() throws SQLException;

	public void rollback() throws SQLException;

	public void close() throws SQLException;

	public IDaoObject getDaoObject(Class<? extends IDaoObject> daoClass) throws SQLException;
}
