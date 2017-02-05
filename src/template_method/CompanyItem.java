package template_method;

import java.sql.SQLException;

import db_package.ClientType;
import exceptions.DuplicateDataException;
import exceptions.NoDataException;
import utils.SQLQueries;

public class CompanyItem extends DBOTemplateItem {

	private static int companyID = 0;

	/**
	 * Implementation of method that check if name of future object already exist
	 * The checking happen by method type 
	 * If the calling method is "create" and the name of object exist, 
	 * the system will throw DuplicateDataException
	 * If the calling method is "remove" the system will get the ID of future object 
	 * and thereby will check if the object is exist.
	 * Throw NoDataException if not exist.
	 * If the calling method is "update" and the name of object not exist,
	 * the system will throw NoDataException
	 * @see DuplicateDataException
	 * @see NoDataException
	 */
	@Override
	public void checkDBOItem(String name, MethodType methodType) throws SQLException {
		try {
			rSet = st.executeQuery(SQLQueries.SELECT_COMPANY_BY_NAME + name + "'");
			
			switch (methodType) {
				case CREATE:
					if (rSet.next()) 
					{
						exceptionFlag = true;
						throw new DuplicateDataException(ClientType.COMPANY);
					}
					break;
				case REMOVE:
					rSet = st.executeQuery(SQLQueries.SELECT_COMPANY_ID_BY_NAME + "'" + name + "'");
					if (rSet.next()) 
					{
						companyID = (int) rSet.getLong("ID");
						break;
					}else{
						exceptionFlag = true;
						throw new NoDataException(ClientType.COMPANY, name);
					}
	
				case UPDATE:
					if (!rSet.next()) 
					{
						exceptionFlag = true;
						throw new NoDataException(ClientType.COMPANY, name);
					}
					break;
			}
		} catch (DuplicateDataException | NoDataException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Get Object ID method
	 * @return ID of object
	 */
	public static int getCompanyID() {
		return companyID;
	}

}
