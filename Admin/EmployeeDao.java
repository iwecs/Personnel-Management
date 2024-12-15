package Admin;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import login.DBUtil;
import login.LoginVO;



public class EmployeeDao {

	static Connection conn;
	static PreparedStatement pt;
	static ResultSet rs;

	public static ArrayList<EmployeeVO> select() {
		
		System.out.println("Dao클래스 select()");
		ArrayList<EmployeeVO> list = new ArrayList<EmployeeVO>();

			try {
			conn = DBUtil.getConnection();
			System.out.println(conn);
			String sql = "select * from employee e\r\n"
					+ "join DEPARTMENT d on e.dept_code = d.dept_code\r\n"
					+ "join POSITION_RANK p on e.job_code = p.job_code order by EMP_ID ";
					
			pt = conn.prepareStatement(sql);
			rs = pt.executeQuery();
	
			while(rs.next()) {
				
				EmployeeVO temp = new EmployeeVO();
				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				temp.setEMAIL(rs.getString("EMP_EMAIL"));
				temp.setPHONE(rs.getString("EMP_PHONE"));
				temp.setDEPT_CODE(rs.getString("DEPT_CODE"));
				temp.setDept_name(rs.getString("DEPT_NAME"));
				temp.setJOB_CODE(rs.getString("JOB_CODE"));
				temp.setJOB_NAME(rs.getString("POSITION"));
				//temp.setAUTHORITY(rs.getString("AUTHORITY"));
				temp.setHIRE_DATE(rs.getString("HIRE_DATE"));
				list.add(temp);
				
			}
			
			
			}catch(Exception e) {
				e.printStackTrace();
			}		
			return list;
		}
	
	
	public static void insert(EmployeeVO vo) {
		 try {
			 //1. db연결
	            conn = DBUtil.getConnection();
	          //2. sql 명령문 
	          // 사원번호는 자동으로 증가 후 정렬!
	          String sql = "\r\n" + 
	            		"INSERT INTO EMPLOYEE (\r\n" + 
	            		"    EMP_ID, EMP_NAME, EMP_PHONE, EMP_BIRTH, EMP_EMAIL, DEPT_CODE, JOB_CODE, AUTHORITY, HIRE_DATE\r\n" + 
	            		") VALUES (\r\n" + 
	            		"    (SELECT NVL(MAX(EMP_ID), 0) + 1 FROM EMPLOYEE),\r\n" + 
	            		"    ?, ?, ?, ?, ?, ?, ?, TO_DATE(?)) ";
	            
	            // 3. 임시 전송
	            pt = conn.prepareStatement(sql);
	           
	            pt.setString(1, vo.getEMP_NAME());
	            pt.setString(2, vo.getEMP_BIRTH());
	            pt.setString(3, vo.getEMAIL()); 
	            pt.setString(4, vo.getPHONE()); 
	            pt.setString(5, vo.getDEPT_CODE()); 
	            pt.setString(6, vo.getJOB_CODE());
	            pt.setString(7, "User");
	            pt.setString(8, vo.getHIRE_DATE()); 
	            
	         
	            int result = pt.executeUpdate();
	            System.out.println("전송 :" + result);
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (pt != null) pt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	}
	public static void insertSalary(SalaryVO salaryvo) {
		 try {
			 //1. db연결
	            conn = DBUtil.getConnection();
	          //2. sql 명령문 
	          //연봉,월봉,보너스
	          System.out.println("EmployeeDao의 insertSalary");
	          String sql = "INSERT INTO salary \r\n"
	          		+ "(EMP_ID, SALARY, monthly_pay, BONUS, \r\n"
	          		+ "travel_allowance,transport_allowance)\r\n"
	          		+ "VALUES (?,?,?,?,200000,200000) ";
	            
	            // 3. 임시 전송
	            pt = conn.prepareStatement(sql);
	           
	            pt.setInt(1, salaryvo.getEmp_id());
	            pt.setInt(2, salaryvo.getSalary());
	            pt.setInt(3, salaryvo.getMonthly_pay());
	            pt.setDouble(4, salaryvo.getBonus()); 

	            
	            int result = pt.executeUpdate();
	            System.out.println("전송 :" + result);
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (pt != null) pt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	}
	public static boolean checkName(String name) {

		boolean result = false;
		
		try {
		conn = DBUtil.getConnection();
		String sql = "select EMP_NAME from employee order by EMP_ID ";
		pt = conn.prepareStatement(sql);
		rs = pt.executeQuery();

		while(rs.next()) {
			
			if(rs.getString("EMP_NAME").equals(name)) {
				result = true;
				System.out.println("찾았습니다." + rs.getString("EMP_NAME"));
			}
		}
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static boolean checkFirstName(String Firstname) {
		
		boolean result = false;
		
		try {
		conn = DBUtil.getConnection();
		String sql = "select EMP_NAME from employee WHERE emp_name LIKE ? || '%' order by EMP_ID ";
		pt = conn.prepareStatement(sql);
		pt.setString(1, Firstname);
		rs = pt.executeQuery();

		System.out.println(rs.next());
		while(rs.next()) {
			System.out.println((rs.getString("EMP_NAME")).substring(0));
			if((rs.getString("EMP_NAME")).substring(0,1).equals(Firstname)) {
				result = true;
				System.out.println("찾았습니다." + rs.getString("EMP_NAME"));
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public static ArrayList<EmployeeVO> search(String name) {
		
		ArrayList<EmployeeVO> searchNamelist = new ArrayList<EmployeeVO>();
		
		try {
			
			System.out.println("EmployeeService의 search 함수 실행");
			conn = DBUtil.getConnection();
			
			String sql = "select * from employee e\r\n" + 
					"join department d on e.dept_code = d.dept_code\r\n" + 
					"join position_rank p on e.job_code = p.job_code\r\n" + 
					"where emp_name = ? \r\n" + 
					"order by EMP_ID ";
			
			pt = conn.prepareStatement(sql);
			pt.setString(1,name);
			
			rs = pt.executeQuery();
			
			while(rs.next()) {
				
				EmployeeVO temp = new EmployeeVO();
				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				temp.setEMAIL(rs.getString("EMP_EMAIL"));
				temp.setPHONE(rs.getString("EMP_PHONE"));
				temp.setDEPT_CODE(rs.getString("DEPT_CODE"));
				temp.setDept_name(rs.getString("DEPT_NAME"));
				temp.setJOB_CODE(rs.getString("JOB_CODE"));
				temp.setJOB_NAME(rs.getString("POSITION"));
				temp.setHIRE_DATE(rs.getString("HIRE_DATE"));
				searchNamelist.add(temp);
				
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		return searchNamelist;
	}
	public static ArrayList<EmployeeVO> searchFirstname(String Firstname) {
		
		ArrayList<EmployeeVO> searchFirstlist = new ArrayList<EmployeeVO>();
		try {
			
			System.out.println("EmployeeService의 searchFirstname 함수 실행");
			conn = DBUtil.getConnection();
			
			String sql = "select * from employee e\r\n" + 
					"join department d on e.dept_code = d.dept_code\r\n" + 
					"join position_rank p on e.job_code = p.job_code\r\n" + 
					"WHERE emp_name LIKE ? || '%' order by EMP_ID ";
			
			pt = conn.prepareStatement(sql);
			pt.setString(1,Firstname);
			
			rs = pt.executeQuery();
			
			while(rs.next()) {
				
				EmployeeVO temp = new EmployeeVO();
				System.out.println(rs.getString("EMP_NAME"));
				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				temp.setEMAIL(rs.getString("EMP_EMAIL"));
				temp.setPHONE(rs.getString("EMP_PHONE"));
				temp.setDEPT_CODE(rs.getString("DEPT_CODE"));
				temp.setDept_name(rs.getString("DEPT_NAME"));
				temp.setJOB_CODE(rs.getString("JOB_CODE"));
				temp.setJOB_NAME(rs.getString("POSITION"));
				temp.setHIRE_DATE(rs.getString("HIRE_DATE"));

				searchFirstlist.add(temp);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return searchFirstlist;
	}
	public static ArrayList<EmployeeVO> searchDept(String dept) {
		
		ArrayList<EmployeeVO> searchDeptlist = new ArrayList<EmployeeVO>();
		try {
			
			System.out.println("EmployeeService의 searchFirstname 함수 실행");
			conn = DBUtil.getConnection();
			
			String sql = "select * from employee e\r\n" + 
					"join department d on e.dept_code = d.dept_code \r\n" + 
					"join position_rank p on e.job_code = p.job_code\r\n" + 
					"where e.dept_code = ? \r\n" + 
					"order by emp_id ";
			
			pt = conn.prepareStatement(sql);
			pt.setString(1,dept);
			
			rs = pt.executeQuery();
			
			while(rs.next()) {
				
				EmployeeVO temp = new EmployeeVO();
				System.out.println(rs.getString("EMP_NAME"));
				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				temp.setEMAIL(rs.getString("EMP_EMAIL"));
				temp.setPHONE(rs.getString("EMP_PHONE"));
				temp.setDEPT_CODE(rs.getString("DEPT_CODE"));
				temp.setDept_name(rs.getString("DEPT_NAME"));
				temp.setJOB_CODE(rs.getString("JOB_CODE"));
				temp.setJOB_NAME(rs.getString("POSITION"));
				temp.setHIRE_DATE(rs.getString("HIRE_DATE"));

				searchDeptlist.add(temp);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return searchDeptlist;
	}
	
	public static int update(String name,String dept) {
		
		conn = DBUtil.getConnection();
		int result = 0;
		
		try {
			System.out.println("EmployeeDao의 부서update함수 실행");
			String sql = "update employee ";
			sql += " set DEPT_CODE =  ? ";
			sql += " where EMP_NAME = ?";
			
			pt = conn.prepareStatement(sql);
			
			pt.setString(1, dept);
			pt.setString(2, name);
			
			result = pt.executeUpdate();
			
			System.out.println(result);
			System.out.println("업데이트 성공");
			
					
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	
	public static int updateJob(String name,String job) {
		
		conn = DBUtil.getConnection();
		int result = 0;
		
		try {
			System.out.println("EmployeeDao의 부서update함수 실행");
			String sql = "update employee ";
			sql += " set JOB_CODE =  ? ";
			sql += " where EMP_NAME = ?";
			
			pt = conn.prepareStatement(sql);
			
			pt.setString(1, job);
			pt.setString(2, name);
			
			result = pt.executeUpdate();
			
			System.out.println(result);
			System.out.println("업데이트 성공");
			
					
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return result;
	}
	//id로 검색
	public static EmployeeVO searchID(int id) {
		
		EmployeeVO vo = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			
			
			
			
			String sql = "select * from employee e\r\n" + 
					"join department d on e.dept_code = d.dept_code \r\n" + 
					"join position_rank p on e.job_code = p.job_code\r\n" + 
					"where emp_id =  ? \r\n" + 
					" order by EMP_ID ";
			pt = conn.prepareStatement(sql);
			pt.setInt(1,id);
			
			rs = pt.executeQuery();
			
			if(rs.next()) {
				EmployeeVO temp = new EmployeeVO();

				temp.setEMP_ID(rs.getInt("EMP_ID"));
				temp.setEMP_NAME(rs.getString("EMP_NAME"));
				temp.setEMP_BIRTH(rs.getString("EMP_BIRTH"));
				temp.setEMAIL(rs.getString("EMP_EMAIL"));
				temp.setPHONE(rs.getString("EMP_PHONE"));
				temp.setDEPT_CODE(rs.getString("DEPT_CODE"));
				temp.setDept_name(rs.getString("DEPT_NAME"));
				temp.setJOB_CODE(rs.getString("JOB_CODE"));
				temp.setJOB_NAME(rs.getString("POSITION"));
				temp.setHIRE_DATE(rs.getString("HIRE_DATE"));
				
				vo = new EmployeeVO(
						rs.getInt("EMP_ID"),
						rs.getString("EMP_NAME"),
						rs.getString("EMP_BIRTH"),
						rs.getString("EMP_EMAIL"),
						rs.getString("EMP_PHONE"),
						rs.getString("DEPT_CODE"),
						rs.getString("DEPT_NAME"),
						rs.getString("JOB_CODE"),
						rs.getString("HIRE_DATE")
				);				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}


	public static int delete(EmployeeVO empvo) {
		
		conn = DBUtil.getConnection();
		int result = 0;
		try {
			System.out.println(empvo.getEMP_ID());
			String sql = "delete from employee where emp_id = ?";
			pt = conn.prepareStatement(sql);
			pt.setInt(1, empvo.getEMP_ID());	
			result = pt.executeUpdate();
			System.out.println("삭제 완료");
	}catch (Exception e) {
		e.printStackTrace();
	}	
	return result;
}

	
	static LoginVO passwordFind(int id) {
		
		LoginVO vo = null;
		
		try {
			
			conn = DBUtil.getConnection();
			
			String sql = "select * from login where emp_id = ? ";
			pt = conn.prepareStatement(sql);
			pt.setInt(1,id);
			
			rs = pt.executeQuery();
			
			if(rs.next()) {
				LoginVO temp = new LoginVO();

				temp.setId(rs.getInt(1));
				temp.setPw(rs.getString(2));
				
				vo = new LoginVO(
						rs.getInt(1),
						rs.getString(2)
				);				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return vo;
	}



}
