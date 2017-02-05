package db_package;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import exceptions.OverallException;
import facade.AdminFacade;
import facade.CompanyFacade;
import facade.CustomerFacade;

public class MainTest {

	public static void main(String[] args) throws ParseException, SQLException {

		/*
		 * Creating Start Date and End Date for Coupon
		 */
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
	     Date endDate = dateFormat.parse("2019-07-22");
	     Date startDate = dateFormat.parse("2017-09-12");
	     
	     /*
	      * Creating Coupon
	      */
	     Coupon coupon = new Coupon();
	     coupon.setAmount(15);
	     coupon.setImage("image1");
	     coupon.setStartDate(startDate);
	     coupon.setPrice(2700);
	     coupon.setTitle("MacBook Air Pro");
	     coupon.setMessage("Model 2017 Tach Bar");
	     coupon.setType(CouponType.ELECTRICITY);
	     try {
	    	 coupon.setEndDate(endDate);
	     } catch (OverallException e) {
	    	 System.err.println(e.getMessage());
	     }
	     
	     /*
	      * Company LogIn
	      */
		 CompanyFacade companyAplied = (CompanyFacade) CouponSystem.getInstance().login("Aplied Materrials", "066", ClientType.COMPANY);		
		
		 /*
		  * Company actions
		  */
//	     companyAplied.createCoupon(coupon);
//		 companyAplied.updateCoupon(coupon);
//		 System.out.println(companyAplied.getCoupon(1023));
//		 printItemList((List<?>) companyAplied.getAllCoupons());
//		 printItemList((List<?>) companyAplied.getCouponsByType(CouponType.FOOD));
//		 companyAplied.removeCoupon(coupon);
//		 
		 
		 /*
		  * Customer LogIn
		  */
		CustomerFacade customerF = (CustomerFacade) CouponSystem.getInstance().login("Shadow", "12345", ClientType.CUSTOMER);
		
		/*
		 * Customer actions
		 */
//		 customerF.purchaseCoupon(coupon);
//		 printItemList((List<?>) customerF.getAllPurchasedCoupons());
//		 printItemList((List<?>) customerF.getAllPurchasedCouponsByPrice(2700));
//		 printItemList((List<?>) customerF.getAllPurchasedCouponsByType(CouponType.ELECTRICITY));

		/*
		 * Admin LogIn
		 */
		AdminFacade adminF = (AdminFacade) CouponSystem.getInstance().login("Admin", "1234", ClientType.ADMIN);
		
		/*
		 * Create Company
		 */
		Company company = new Company();
		company.setCompName("Microsoft");
		company.setPassword("064");
		company.setEmail("Aplied@mail");

		/*
		 * Create Customer
		 */
		Customer cost = new Customer();
		cost.setCustName("Shadow3");
		cost.setPassword("sssss");
		
		/*
		 * Admin actions
		 */
//		 adminF.createCompany(company);
//		 adminF.updateCompany(company);
//		 System.out.println(adminF.getCompany(3));
//		 printItemList((List<?>) adminF.getAllCompanies());
//		 adminF.removeCompany(company);

		
//		 adminF.createCustomer(cost);
//		 adminF.updateCustomer(cost);
//		 System.out.println(adminF.getCustomer(23));
//		 printItemList((List<?>) adminF.getAllCustomers());
//		 adminF.removeCustomer(cost);
		
		/*
		 * ShutDown of system
		 */
		 CouponSystem.getInstance().shutdown();
	}
	
	/*Method which print items of given collection*/
	private static void printItemList(List<?> list){
		for (Object object : list) {
			System.out.println(object);
		}
	}
}
