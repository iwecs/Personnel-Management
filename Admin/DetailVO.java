package Admin;

public class DetailVO {
	private int EMP_id;
	private String EMP_NAME      ;
	private String EMP_BIRTH	 ;
	private String EMAIL         ;
	private String PHONE         ;
	private String DEPT_CODE     ;
	private String JOB_CODE      ;
	private String HIRE_DATE     ;
	private int salary;
	private int monthly_pay;
	private double bonus;
    private int transport_allowance;  
    private int travel_allowance;
    
    
    
    
	public DetailVO() {
		super();
	}
	public DetailVO(int eMP_id, String eMP_NAME, String eMP_BIRTH, String eMAIL, String pHONE, String dEPT_CODE,
			String jOB_CODE, String hIRE_DATE, int salary, int monthly_pay, double bonus, int transport_allowance,
			int travel_allowance) {
		super();
		EMP_id = eMP_id;
		EMP_NAME = eMP_NAME;
		EMP_BIRTH = eMP_BIRTH;
		EMAIL = eMAIL;
		PHONE = pHONE;
		DEPT_CODE = dEPT_CODE;
		JOB_CODE = jOB_CODE;
		HIRE_DATE = hIRE_DATE;
		this.salary = salary;
		this.monthly_pay = monthly_pay;
		this.bonus = bonus;
		this.transport_allowance = transport_allowance;
		this.travel_allowance = travel_allowance;
	}
	public int getEMP_id() {
		return EMP_id;
	}
	public void setEMP_id(int eMP_id) {
		EMP_id = eMP_id;
	}
	public String getEMP_NAME() {
		return EMP_NAME;
	}
	public void setEMP_NAME(String eMP_NAME) {
		EMP_NAME = eMP_NAME;
	}
	public String getEMP_BIRTH() {
		return EMP_BIRTH;
	}
	public void setEMP_BIRTH(String eMP_BIRTH) {
		EMP_BIRTH = eMP_BIRTH;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getJOB_CODE() {
		return JOB_CODE;
	}
	public void setJOB_CODE(String jOB_CODE) {
		JOB_CODE = jOB_CODE;
	}
	public String getHIRE_DATE() {
		return HIRE_DATE;
	}
	public void setHIRE_DATE(String hIRE_DATE) {
		HIRE_DATE = hIRE_DATE;
	}
	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public int getMonthly_pay() {
		return monthly_pay;
	}
	public void setMonthly_pay(int monthly_pay) {
		this.monthly_pay = monthly_pay;
	}
	public double getBonus() {
		return bonus;
	}
	public void setBonus(double bonus) {
		this.bonus = bonus;
	}
	public int getTransport_allowance() {
		return transport_allowance;
	}
	public void setTransport_allowance(int transport_allowance) {
		this.transport_allowance = transport_allowance;
	}
	public int getTravel_allowance() {
		return travel_allowance;
	}
	public void setTravel_allowance(int travel_allowance) {
		this.travel_allowance = travel_allowance;
	} 
}
