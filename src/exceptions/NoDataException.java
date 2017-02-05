package exceptions;

import db_package.ClientType;

public class NoDataException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message = "The  is not exist!";
	
	/**
	 * Create exception object and generate exception message
	 * @param type - Client type
	 * @param name - The name of client 
	 */
	public NoDataException(ClientType type, String name) {
		StringBuffer stringBuffer = new StringBuffer(message);
		stringBuffer.insert(4, type.toString() + " " + name);
		message = stringBuffer.toString();
	}

	/**
	 * Create exception object and generate exception message
	 * @param message - Description of Exception 
	 */
	public NoDataException(String message) 
	{
		this.message = message;
	}
	
	/**
	 * (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage() 
	{
		return message;
	}

}
