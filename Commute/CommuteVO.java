package Commute;

import java.util.ArrayList;
import java.util.Date;

public class CommuteVO {

	private int EMP_ID;
	private String EMP_NAME;
	private Date STARTTIME;
	private Date ENDTIME;
	private String LEAVE_CODE;
	private String LEAVE_TYPE;
	private String BUSINESS_TRIP;
	private Date hire_date;
	private int workMonth;
	private int workYear;
	private int LeaveCount;
	private String comm;
	private String end;
	private String leave;
	private Date MONTH_DATE;
	private int num;

	public CommuteVO() {
	}

	public CommuteVO(int eMP_ID, String eMP_NAME, Date sTARTTIME, Date eNDTIME, String lEAVE_CODE, String lEAVE_TYPE,
			String bUSINESS_TRIP, String lEAVE, Date hire_date) {
		EMP_ID = eMP_ID;
		EMP_NAME = eMP_NAME;
		STARTTIME = sTARTTIME;
		ENDTIME = eNDTIME;
		LEAVE_CODE = lEAVE_CODE;
		LEAVE_TYPE = lEAVE_TYPE;
		BUSINESS_TRIP = bUSINESS_TRIP;
		this.hire_date = hire_date;
	}

	
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public Date getMONTH_DATE() {
		return MONTH_DATE;
	}

	public void setMONTH_DATE(Date mONTH_DATE) {
		MONTH_DATE = mONTH_DATE;
	}

	public String getComm() {
		return comm;
	}

	public void setComm(String comm) {
		this.comm = comm;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public String getLeave() {
		return leave;
	}

	public void setLeave(String leave) {
		this.leave = leave;
	}

	public int getLeaveCount() {
		return LeaveCount;
	}

	public void setLeaveCount(int leaveCount) {
		LeaveCount = leaveCount;
	}

	public int getWorkMonth() {
		return workMonth;
	}

	public void setWorkMonth(int workMonth) {
		this.workMonth = workMonth;
	}

	public String getEMP_NAME() {
		return EMP_NAME;
	}

	public void setEMP_NAME(String eMP_NAME) {
		EMP_NAME = eMP_NAME;
	}

	public String getLEAVE_CODE() {
		return LEAVE_CODE;
	}

	public void setLEAVE_CODE(String lEAVE_CODE) {
		LEAVE_CODE = lEAVE_CODE;
	}

	public String getLEAVE_TYPE() {
		return LEAVE_TYPE;
	}

	public void setLEAVE_TYPE(String lEAVE_TYPE) {
		LEAVE_TYPE = lEAVE_TYPE;
	}

	public int getEMP_ID() {
		return EMP_ID;
	}

	public void setEMP_ID(int eMP_ID) {
		EMP_ID = eMP_ID;
	}

	public Date getSTARTTIME() {
		return STARTTIME;
	}

	public void setSTARTTIME(Date sTARTTIME) {
		STARTTIME = sTARTTIME;
	}

	public Date getENDTIME() {
		return ENDTIME;
	}

	public void setENDTIME(Date eNDTIME) {
		ENDTIME = eNDTIME;
	}

	public String getBUSINESS_TRIP() {
		return BUSINESS_TRIP;
	}

	public void setBUSINESS_TRIP(String bUSINESS_TRIP) {
		BUSINESS_TRIP = bUSINESS_TRIP;
	}

	public Date getHire_date() {
		return hire_date;
	}

	public void setHire_date(Date hire_date) {
		this.hire_date = hire_date;
	}

	public int getWorkYear() {
		return workYear;
	}

	public void setWorkYear(int workYear) {
		this.workYear = workYear;
	}



}