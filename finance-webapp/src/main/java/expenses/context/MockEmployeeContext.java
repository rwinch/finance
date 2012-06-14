package expenses.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import expenses.domain.Employee;
import expenses.repository.EmployeeRepository;

@Component
public class MockEmployeeContext implements EmployeeContext {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Employee getCurrent() {
		return employeeRepository.findOne(100L);
	}
}