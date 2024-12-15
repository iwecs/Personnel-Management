package Commute;

import login.LoginVO;

public class Controller {

	private DAO dao = new DAO();

	public void logIn(int id) throws Exception {

//		LoginVO login = new LoginVO(id, pw);

		dao.logIn(id);

	}

	public void logOut(int id) throws Exception {

		dao.logOut(id);

	}

}
