package User;

import java.time.LocalDateTime;

public class UserVO {

	private int EMP_ID;
	private String EMP_NAME;
	private String EMP_BIRTH;
	private String EMP_EMAIL;
	private String EMP_PHONE;
	private String DEPT_CODE;
	private String JOB_CODE;
	
	private String DEPT_ID;
	
	private String Dept_name;
	private String POSITION;
	
	
	public int getEMP_ID() {
		return EMP_ID;
	}
	public void setEMP_ID(int eMP_ID) {
		EMP_ID = eMP_ID;
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
	public String getEMP_EMAIL() {
		return EMP_EMAIL;
	}
	public void setEMP_EMAIL(String eMP_EMAIL) {
		EMP_EMAIL = eMP_EMAIL;
	}
	public String getEMP_PHONE() {
		return EMP_PHONE;
	}
	public void setEMP_PHONE(String eMP_PHONE) {
		EMP_PHONE = eMP_PHONE;
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
	public String getDept_name() {
		return Dept_name;
	}
	public void setDept_name(String dept_name) {
		Dept_name = dept_name;
	}
	public String getPOSITION() {
		return POSITION;
	}
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}
	
	public UserVO() {}
	
	public UserVO(int eMP_ID, String eMP_NAME, String eMP_BIRTH, String eMP_EMAIL, String eMP_PHONE) {
		super();
		EMP_ID = eMP_ID;
		EMP_NAME = eMP_NAME;
		EMP_BIRTH = eMP_BIRTH;
		EMP_EMAIL = eMP_EMAIL;
		EMP_PHONE = eMP_PHONE;
	}
	
	@Override
	public String toString() {
		return "UserVO [EMP_ID=" + EMP_ID + ", EMP_NAME=" + EMP_NAME + ", EMP_BIRTH=" + EMP_BIRTH + ", EMP_EMAIL="
				+ EMP_EMAIL + ", EMP_PHONE=" + EMP_PHONE + ", DEPT_CODE=" + DEPT_CODE + ", JOB_CODE=" + JOB_CODE
				+ ", DEPT_ID=" + DEPT_ID + ", Dept_name=" + Dept_name + ", POSITION=" + POSITION + "]";
	}
	public UserVO(String dEPT_CODE, int eMP_ID) {
		DEPT_CODE = dEPT_CODE;
		EMP_ID = eMP_ID;
	}
	public UserVO(int eMP_ID, String jOB_CODE) {
		EMP_ID = eMP_ID;
		JOB_CODE = jOB_CODE;
	}
	public UserVO(String pOSITION) {
		POSITION = pOSITION;
	}
	public UserVO(int eMP_ID, String eMP_NAME, String pOSITION, String dept_name, String eMP_BIRTH,  String eMP_PHONE, String eMP_EMAIL)
			 {
		EMP_ID = eMP_ID;
		EMP_NAME = eMP_NAME;
		POSITION = pOSITION;
		Dept_name = dept_name;
		EMP_BIRTH = eMP_BIRTH;
		EMP_PHONE = eMP_PHONE;
		EMP_EMAIL = eMP_EMAIL;
	}
	
	
	
	
	
	
	
	

	
}
