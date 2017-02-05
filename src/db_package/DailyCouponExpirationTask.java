package db_package;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * This class that uses for expiration task that check every day, dates of coupons.
 * @author  Andrey Orlov
 * @version 1.0
 * 
 */
public class DailyCouponExpirationTask implements Runnable {

	/**Link to Coupon DAO-layer*/
	private CouponDBDAO couponDAO;
	
	/**Boolean flag for stop the task*/
	private boolean quitFlag = false;
	
	/**
	 * Create object for speaking with DAO-layer
	 */
	public DailyCouponExpirationTask() 
	{
		couponDAO = new CouponDBDAO();		
	}

	/**
	 * Method that check end dates of all coupons in DB, if coupon end date after current date, 
	 * the specified coupon will be removed.
	 */
	@Override
	public void run() {
		
		Calendar calendar = Calendar.getInstance();
		Date today = calendar.getTime();
		
		while (!Thread.currentThread().isInterrupted()) 
		{
			ArrayList<Coupon> allCouponsList = null;
			try {
				allCouponsList = (ArrayList<Coupon>) couponDAO.getAllCoupon();
				
				for (Coupon coupon : allCouponsList) 
				{
					if (!quitFlag) {
						if (coupon.getEndDate().before(today)) {
							couponDAO.removeCoupon(coupon);
						}
					} else {
						return;
					}
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
				try {
					TimeUnit.DAYS.sleep(1);
				} catch (InterruptedException e) {
					System.err.println(e.getMessage());
				}
			} 
	}

	/**
	 * Method for stop expiration task
	 */
	public void stopTask()
	{
		Thread.currentThread().interrupt();
		quitFlag = true;
		System.out.println("Deily task is stopped.");
	}
}
