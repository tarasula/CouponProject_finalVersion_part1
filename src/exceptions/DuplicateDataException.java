
package exceptions;

import db_package.ClientType;

/**
 * @author Andrey Orlov
 *
 */
public class DuplicateDataException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**Template message for Exception*/
	private String message = "Can't create  ,is already exist!";

	/**
	 * Create exception object and generate exception message
	 * @param message - Exception description
	 */
	public DuplicateDataException(String message) {
		this.message = message;
	}
	
	/**
	 * Create exception object and generate exception message
	 * @param type - Exception description by type
	 */
	public DuplicateDataException(ClientType type) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(13, type.toString());
		message = stringBuffer.toString();
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
