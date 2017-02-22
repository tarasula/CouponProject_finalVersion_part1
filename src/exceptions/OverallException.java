package exceptions;

/**
 * This is exception class that uses for create new exceptions
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class OverallException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create exception object
	 * 
	 * @param massage - Exception description
	 */
	public OverallException(String massage) 
	{
		super(massage);
	}
}
