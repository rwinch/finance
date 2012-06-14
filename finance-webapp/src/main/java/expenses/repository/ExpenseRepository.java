package expenses.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import expenses.domain.Employee;
import expenses.domain.Expense;

@RooJpaRepository(domainType = Expense.class)
public interface ExpenseRepository {

	@PostAuthorize("hasPermission(returnObject,'read')")
	@Transactional(readOnly = true)
	Expense findOne(Long id);

	@PreAuthorize("hasPermission(#expense,'write')")
	@Transactional
	Expense save(Expense expense);

	@Transactional(readOnly = true)
	List<Expense> findByReporter(Employee employee);

	@Transactional(readOnly = true)
	List<Expense> findByReporterSupervisor(Employee employee);
}
