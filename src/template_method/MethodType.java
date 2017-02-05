package template_method;

public enum MethodType {

	CREATE("create"),
	UPDATE("update"),
	REMOVE("remove");
	
	/**Type field */
	private String type;
	
	/**
	 * Create object constructor
	 * @param type - Type of Coupon
	 */
	MethodType(String type) {
		this.type = type;
	}
	
	/**
	 * Get Type method
	 * @return type of Coupon
	 */
	public String getType() {
		return type;
	}
}

