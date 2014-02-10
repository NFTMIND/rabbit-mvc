package os.rabbit.dao;

import java.sql.SQLException;

public interface IDaoManager {
	public IDaoTransaction beginTransaction() throws SQLException;
	public IDaoObject getDaoObject(Class<?> interfaces, Class<? extends IDaoObject> daoClass) throws SQLException;
}
