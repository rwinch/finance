package expenses.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;

import expenses.domain.Employee;

@RooJpaRepository(domainType = Employee.class)
public interface EmployeeRepository {
    List<Employee> findBySupervisor(Employee employee);
    Employee findOneByUsername(String username);
}
