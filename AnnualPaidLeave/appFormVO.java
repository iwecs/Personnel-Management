package AnnualPaidLeave;

import java.sql.Date;

public class appFormVO {
	
	private int num;
	private String title;
	private String type;
	private String useDate;
	private int emp_id;
	private String emp_name;
	private String content;
	private String writeDate;
	private String processing;
	private int leave;
	
	public appFormVO() {}
	
	public appFormVO(String title, String type, String usedate2, int emp_id, String emp_name, String content) {
		this.title = title;
		this.type = type;
		this.useDate = usedate2;
		this.emp_id = emp_id;
		this.emp_name = emp_name;
		this.content = content;
	}
	
	

	public int getLeave() {
		return leave;
	}

	public void setLeave(int leave) {
		this.leave = leave;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public void setEmp_name(String string) {
		this.emp_name = string;
	}

	public String getContent() {
		return content;
	}

	public void setContext(String content) {
		this.content = content;
	}

	public String getUseDate() {
		return useDate;
	}

	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}

	public String getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(String writeDate) {
		this.writeDate = writeDate;
	}

	public String getProcessing() {
		return processing;
	}

	public void setProcessing(String processing) {
		this.processing = processing;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	
	
	


}
