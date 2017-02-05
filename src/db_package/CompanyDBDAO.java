package db_package;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import exceptions.LogInException;
import exceptions.NoDataException;
import exceptions.OverallException;
import facade.CompanyFacade;
import template_method.CompanyItem;
import template_method.DBOTemplateItem;
import template_method.MethodType;
import utils.CompanyConstants;
import utils.CouponConstants;
import utils.SQLQueries;
/**
 * This class is Company DAO-layer that uses for speaking 
 * with DB and relevant tables.
 * 
 * @author  Andrey Orlov
 * @version 1.0
 */
public class CompanyDBDAO implements CompanyDAO {

	/**CouponDBDAO member for using relevant method	 */
	CouponDBDAO couponDAO;
	
	/**CompanyItem member for using relevant method	 */
	CompanyItem compItem;
	
	/**
	 * Create object for speaking with DB
	 */
	public CompanyDBDAO() 
	{
		couponDAO = new CouponDBDAO(); 
		compItem = new CompanyItem();
	}

	/**
	 * Get connection method from ConnectionPool class for speaking with DB
	 * @return connection
	 */
	private Connection getConnection() 
	{
		Connection con = null;
		con = ConnectionPool.getInstance().getConnection();
		return con;
	}

	/**
	 * Method for create Company in Company table in DB.
	 * Checking if this (Company comp) exist in DB.
	 * @param comp - Company object with filled fields
	 * @exception  OverallException - Can't create company, already exist.
	 */
	@Override
	public void createCompany(Company comp) throws Exception 
	{		
		compItem.templateCreateDBOItemMethod(SQLQueries.INSERT_INTO_COMPANY_VALUES + "(" + "'" + comp.getCompName()
		+ "','" + comp.getPassword() + "','" + comp.getEmail() + "');", 
		comp.getCompName(), MethodType.CREATE);
		
		if(!DBOTemplateItem.exceptionFlag){	
			System.out.println("Company " + comp.getCompName() + " was created");
		}
		DBOTemplateItem.exceptionFlag = false;
	}	
	
	@Override
	public void removeCompany(Company comp) throws Exception 
	{
		compItem.templateCreateDBOItemMethod(SQLQueries.SELECT_COMPANY_BY_NAME + comp.getCompName() + "'", comp.getCompName(), MethodType.REMOVE);
		
		compItem.templateCreateDBOItemMethod(SQLQueries.REMOVE_COMPANY_COUPONS + CompanyItem.getCompanyID() + ")", comp.getCompName(), MethodType.REMOVE);
		compItem.templateCreateDBOItemMethod(SQLQueries.REMOVE_FROM_CUSTOMER_COUPONS + CompanyItem.getCompanyID() + ")", comp.getCompName(), MethodType.REMOVE);
		compItem.templateCreateDBOItemMethod(SQLQueries.REMOVE_FROM_COMPANY_COUPONS + CompanyItem.getCompanyID(), comp.getCompName(), MethodType.REMOVE);
		compItem.templateCreateDBOItemMethod(SQLQueries.REMOVE_COMPANY + CompanyItem.getCompanyID(), comp.getCompName(), MethodType.REMOVE);
		if(!DBOTemplateItem.exceptionFlag){	
			System.out.println("The Company " + comp.getCompName() + " removed");
		}
		DBOTemplateItem.exceptionFlag = false;
	}

	/**
	 * Method for Update the company in DB by comp_ID, throw Exception if the Company not exist.
	 * Firstly get all companies and check if this company is exist
	 * @param comp - Company object with filled fields
	 */
	@Override
	public void updateCompany(Company comp) throws Exception 
	{
		compItem.templateCreateDBOItemMethod(SQLQueries.UPDATE_COMPANY_SET + "'" + comp.getPassword() + "', EMAIL = '" + comp.getEmail() +
				"' WHERE COMP_NAME = '" + comp.getCompName() + "'", comp.getCompName(), MethodType.UPDATE);
		if(!DBOTemplateItem.exceptionFlag){
			System.out.println("The Company " + comp.getCompName() + " was updated");
		}
		DBOTemplateItem.exceptionFlag = false;
	}

	/**
	 * Method for get Company by ID from Company table in DB.
	 * @param l - Company ID 
	 * @return Company object with filled fields
	 */
	@Override
	public Company getCompany(long l) throws Exception 
	{
		Company company = new Company();
		ResultSet rs = null;
		ResultSet rsCoup = null;
		Statement st;
		Connection con;
		ArrayList<Coupon> coupons = new ArrayList<>();
			con = getConnection();
			st = con.createStatement();
			if (st != null) 
			{
				rs = st.executeQuery(SQLQueries.SELECT_COMPANY_BY_ID + l);
				company.setCompName(CompanyConstants.EMPTY);
				while (rs.next()) 
				{
					company.setId(rs.getLong(CompanyConstants.COMPANY_ID));
					company.setCompName(rs.getString(CompanyConstants.COMPANY_COMP_NAME));
					company.setPassword(rs.getString(CompanyConstants.COMPANY_PASSWORD));
					company.setEmail(rs.getString(CompanyConstants.COMPANY_EMAIL));
					
				}
				rsCoup = st.executeQuery(SQLQueries.SELECT_COMPANY_COUPONS + l + ")");
				while(rsCoup.next())
				{
					Coupon coupon = new Coupon();
					coupon.setId(rsCoup.getLong(CouponConstants.ID));
					coupon.setTitle(rsCoup.getString(CouponConstants.COUPON_TITLE));
					coupon.setStartDate(rsCoup.getDate(CouponConstants.COUPON_START_DATE));
					coupon.setEndDate(rsCoup.getDate(CouponConstants.COUPON_END_DATE));
					coupon.setAmount(rsCoup.getInt(CouponConstants.COUPON_AMOUNT));
					String typeFromDB = rsCoup.getString(CouponConstants.COUPON_TYPE);
					CouponType type = CouponType.valueOf(typeFromDB);
					coupon.setType(type);
					coupon.setMessage(rsCoup.getString(CouponConstants.COUPON_MESSAGE));
					coupon.setPrice(rsCoup.getDouble(CouponConstants.COUPON_PRICE));
					coupon.setImage(rsCoup.getString(CouponConstants.COUPON_IMAGE));
					coupons.add(coupon);
				}
				company.setCoupon(coupons);
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
		return company;
	}
	
	

	/**
	 * Method for get all Companies from Company table in DB.
	 * @return Company list with filled fields
	 */
	@Override
	public Collection<Company> getAllCompanies() throws Exception 
	{
		ResultSet rs = null;
		ArrayList<Company> companyList = new ArrayList<>();
		Statement st;
		Connection con;
			con = getConnection();
			st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (st != null) 
			{
				rs = st.executeQuery(CompanyConstants.SELECT_ALL_ID_FROM_COMPANY);
				if(!rs.next())
				{
					return companyList;
				}
				rs.absolute(0);
				while (rs.next()) 
				{					
					Company comp = new Company();
					comp = getCompany(rs.getLong(CompanyConstants.COMPANY_ID));
					companyList.add(comp);
				}
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
		return companyList;
	}

	/**
	 * Method for get all Coupon that Company was created.
	 * @return Coupon list with filled fields
	 * @throws NoDataException Throw when Company no have Coupon 
	 */
	@Override
	public List<Coupon> getCoupons() throws Exception 
	{
		ResultSet rs = null;
		ArrayList<Coupon> couponList = new ArrayList<>();
		Statement stGetAll;
		Connection con;
			con = getConnection();
			stGetAll = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			if (stGetAll != null) 
			{
				rs = stGetAll.executeQuery(SQLQueries.SELECT_COUPON_ID_FROM_COMPANY_COUPON + CompanyFacade.getCompanyName() + "');");
				if(!rs.next())
				{
					if(couponList.isEmpty())
					{
						throw new NoDataException(CompanyConstants.COMPANY_DOES_NOT_HAVE_COUPONS);
					}
					return couponList;
				}
				rs.absolute(0);
				while (rs.next()) {
					Coupon coupon;
					coupon = couponDAO.getCoupon(rs.getLong(CouponConstants.COUPON_ID));
					couponList.add(coupon);
				}
			}
			rs.close();
			stGetAll.close();
			ConnectionPool.getInstance().returnConnection(con);
		
		return couponList;
	}
	
	/**
	 * Method to login with Company credential
	 * @param compName - Company name
	 * @param password - Company password
	 * @return CompanyFacade Object
	 * @exception  OverallException - if log in failed
	 */
	@Override
	public boolean login(String compName, String password) throws Exception 
	{
		ResultSet rs = null;
		Statement st;
		Connection con;
			con = getConnection();
			st = con.createStatement();
			if (st != null) 
			{
				rs = st.executeQuery(SQLQueries.SELECT_COMPANY_PASSWORD_BY_NAME + "'" + compName + "'");
				while (rs.next()) 
				{
					if (password.equals(rs.getString(CompanyConstants.COMPANY_PASSWORD).trim())) 
					{
						System.out.println(CompanyConstants.LOGIN_SUCCESS);
						return true;
					}
				}
			}
			rs.close();
			st.close();
			ConnectionPool.getInstance().returnConnection(con);
			throw new LogInException(ClientType.COMPANY);
	}

}
