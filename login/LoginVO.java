package login;

import java.time.LocalDateTime;

public class LoginVO {
	
	private int id;
	private String pw;
	
	public LoginVO() {}

	public LoginVO(int id, String pw) {
		this.id = id;
		this.pw = pw;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Object getToday() {
		LocalDateTime now = LocalDateTime.now();
		return now;
	}
	
}
