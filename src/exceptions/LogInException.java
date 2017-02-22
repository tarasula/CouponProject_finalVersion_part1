package exceptions;

import db_package.ClientType;
import utils.AdminConstants;
import utils.CompanyConstants;
import utils.CustomerConstants;

public class LogInException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LogInException(ClientType type){
		switch (type) {
		case CUSTOMER:
			super(CustomerConstants.LOGIN_FAILED);
			break;
		case COMPANY:
			super(CompanyConstants.LOGIN_FAILED);
			break;
		case ADMINISTRATOR:
			super(AdminConstants.LOGIN_FAILED);
			break;
		case ADMIN:
			super(AdminConstants.LOGIN_FAILED);
			break;
		}
	}
	
	public LogInException(String message){
		super(message);
	}
}
