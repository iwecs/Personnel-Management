package AnnualPaidLeave;

import java.sql.Date;

public class AnnualPVO {

	private String leave_Code;
	private int LEAVE;
	private int OVERTIME;
	private int ANNUALPAIDLEAVE;
	private int BUSINESS_TRIP;
	private Date DATE;
	
	
	public AnnualPVO() {}
	
	public AnnualPVO(int lEAVE, int oVERTIME, int aNNUALPAIDLEAVE, int bUSINESS_TRIP, Date dATE, String leave_Code) {
		LEAVE = lEAVE;
		OVERTIME = oVERTIME;
		ANNUALPAIDLEAVE = aNNUALPAIDLEAVE;
		BUSINESS_TRIP = bUSINESS_TRIP;
		DATE = dATE;
		leave_Code = leave_Code;
	}

	public int getLEAVE() {
		return LEAVE;
	}

	public void setLEAVE(int lEAVE) {
		LEAVE = lEAVE;
	}

	public int getOVERTIME() {
		return OVERTIME;
	}

	public void setOVERTIME(int oVERTIME) {
		OVERTIME = oVERTIME;
	}

	public int getANNUALPAIDLEAVE() {
		return ANNUALPAIDLEAVE;
	}

	public void setANNUALPAIDLEAVE(int aNNUALPAIDLEAVE) {
		ANNUALPAIDLEAVE = aNNUALPAIDLEAVE;
	}

	public int getBUSINESS_TRIP() {
		return BUSINESS_TRIP;
	}

	public void setBUSINESS_TRIP(int bUSINESS_TRIP) {
		BUSINESS_TRIP = bUSINESS_TRIP;
	}

	public Date getDATE() {
		return DATE;
	}

	public void setDATE(Date dATE) {
		DATE = dATE;
	}

	public AnnualPVO(String leave_Code) {
		this.leave_Code = leave_Code;
	}




}
