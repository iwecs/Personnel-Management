package BusinessTrip;

public class BusinessTripVO {
	
	private int num;
	private int emp_id;
	private String type;
	private String whatDay;
	private String emp_name;
	private String place;
	private String PlaceCode;
	private String WHETHER;
	private String processing;
	
	public BusinessTripVO() {}
	
	public BusinessTripVO(int emp_id, String emp_name, String type, String wHATDAY) {
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.type = type;
		this.whatDay = wHATDAY;
	}

	public BusinessTripVO(int emp_id,  String emp_name, String type, String pLACE_CODE,String wHATDAY, 
			String wHETHER) {
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.type = type;
		this.PlaceCode = pLACE_CODE;
		this.whatDay = wHATDAY;
		this.WHETHER = wHETHER;
	}

	
	
	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWHATDAY() {
		return whatDay;
	}

	public void setWHATDAY(String wHATDAY) {
		whatDay = wHATDAY;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}

	public String getPlaceCode() {
		return PlaceCode;
	}

	public void setPlaceCode(String pLACE_CODE) {
		PlaceCode = pLACE_CODE;
	}

	public String getWHETHER() {
		return WHETHER;
	}

	public void setWHETHER(String wHETHER) {
		WHETHER = wHETHER;
	}

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	
	
	

}
