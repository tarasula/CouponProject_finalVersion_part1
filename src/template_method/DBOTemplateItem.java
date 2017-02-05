package template_method;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db_package.ConnectionPool;
import exceptions.DuplicateDataException;


public abstract class DBOTemplateItem {
	
	/**ResultSet member using for this class*/
	protected ResultSet rSet = null;
	
	/**Connection member using for get connection to DB*/
	protected Connection con = null;
	
	/**Statement member using for execute query*/
	protected static Statement st = null;
	
	/**Boolean member which checking if already was exception*/
	public static boolean exceptionFlag = false;
	/**
	 * Get connection method
	 * @see ConnectionPool
	 */
	public void getConnection(){
		con = ConnectionPool.getInstance().getConnection();
	}
	
	/**
	 * Create statement method
	 */
	public void createStatement(){
		try 
		{
			st = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Execute query method 
	 * @param query for execute
	 */
	public void executeQuery(String query){
		try 
		{
			st.execute(query);
			exceptionFlag = false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Abstract method to check DB item of different objects
	 * @param name of specific object that will be created
	 * @param methodType Type of method who call this method (create, remove, update)
	 * @throws SQLException Some SQL Exception
	 * @throws DuplicateDataException when the data is duplicate
	 */
	public abstract void checkDBOItem(String name, MethodType methodType) throws SQLException, DuplicateDataException;
	
	
	/**
	 * Return connection method
	 * @param con connection for return
	 * @see ConnectionPool
	 */
	public void returnConnection(Connection con){
		ConnectionPool.getInstance().returnConnection(con);
	}
	
	/**
	 * Template method that call to all method in this class
	 * @param query Query for execute 
	 * @param name Name of object that will be craeted
	 * @param methodType The type of method who call this method
	 * @throws SQLException Some SQL Exception
	 * @throws DuplicateDataException When the data is duplicate
	 */
	public void templateCreateDBOItemMethod(String query, String name , MethodType methodType) throws SQLException, DuplicateDataException{
		getConnection();
		createStatement();
		checkDBOItem(name, methodType);
		if(!exceptionFlag){
			executeQuery(query);
		}
		returnConnection(con);
	}

	
}
