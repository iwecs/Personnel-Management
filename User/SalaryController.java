package User;

import java.util.List;

public class SalaryController {
    private SalaryDAO salaryDAO;

    public SalaryController() {
        this.salaryDAO = new SalaryDAO();
    }

    public SalaryVO getSalary(int emp_id) {
        return salaryDAO.Select(emp_id);
    }

    public List<Object[]> getTravelDetails(int emp_id) {
        return salaryDAO.getTravelDetails(emp_id);
    }
}
