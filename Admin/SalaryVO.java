package Admin;

public class SalaryVO {
	private int emp_id;
	private String emp_name;
	private int salary;
	private int monthly_pay;
	private double bonus;
    private int transport_allowance;  
    private int travel_allowance; 
    
	public SalaryVO() {
	}

	public SalaryVO(int emp_id, int salary, int monthly_pay, double bonus) {
		this.emp_id = emp_id;
		this.salary = salary;
		this.monthly_pay = monthly_pay;
		this.bonus = bonus;
	}
	
	




	public SalaryVO(int emp_id, String emp_name, int salary, int monthly_pay, double bonus) {
		super();
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.salary = salary;
		this.monthly_pay = monthly_pay;
		this.bonus = bonus;
	}

	public int getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(int emp_id) {
		this.emp_id = emp_id;
	}

	public String getEmp_name() {
		return emp_name;
	}

	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
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

	@Override
	public String toString() {
		return "SalaryVO [emp_id=" + emp_id + ", emp_name=" + emp_name + ", salary=" + salary + ", monthly_pay="
				+ monthly_pay + ", bonus=" + bonus + ", transport_allowance=" + transport_allowance
				+ ", travel_allowance=" + travel_allowance + "]";
	}

	
	
	
	
	
}

