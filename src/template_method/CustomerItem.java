package template_method;

import java.sql.SQLException;

import db_package.ClientType;
import exceptions.DuplicateDataException;
import exceptions.NoDataException;
import utils.SQLQueries;

public class CustomerItem extends DBOTemplateItem {

	
	/**
	 * Implementation of method that check if name of future object already exist
	 * The checking happen by method type 
	 * If the calling method is "create" and the name of object exist, 
	 * the system will throw DuplicateDataException
	 * If the calling method is "remove" or "update" and the name of object not exist,
	 * the system will throw NoDataException
	 * @see DuplicateDataException
	 * @see NoDataException
	 */
	@Override
	public void checkDBOItem(String name, MethodType methodType) throws SQLException {

		try {
			rSet = st.executeQuery(SQLQueries.SELECT_CUSTOMER_BY_NAME + name + "'");
			
			switch (methodType) {
			case CREATE:
				if (rSet.next()) {
					exceptionFlag = true;
					throw new DuplicateDataException(ClientType.CUSTOMER);
				}
				break;
			case REMOVE:
			case UPDATE:
				if (!rSet.next()) {
					exceptionFlag = true;
					throw new NoDataException(ClientType.CUSTOMER, name);
				}
				break;
			}
		} catch (DuplicateDataException | NoDataException e) {
			System.err.println(e.getMessage());
		}
	}

}
