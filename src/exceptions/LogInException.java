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
	private String message;
	
	public LogInException(ClientType type){
		switch (type) {
		case CUSTOMER:
			this.message = CustomerConstants.LOGIN_FAILED;
			break;
		case COMPANY:
			this.message = CompanyConstants.LOGIN_FAILED;
			break;
		case ADMINISTRATOR:
			this.message = AdminConstants.LOGIN_FAILED;
			break;
		case ADMIN:
			this.message = AdminConstants.LOGIN_FAILED;
			break;
		}
	}
	
	public LogInException(String message){
		this.message = message;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() {
		return message;
	}
}
