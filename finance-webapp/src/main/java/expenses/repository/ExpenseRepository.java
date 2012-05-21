package expenses.repository;

import java.util.List;

import org.springframework.roo.addon.layers.repository.jpa.RooJpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;

import expenses.domain.Employee;
import expenses.domain.Expense;

@RooJpaRepository(domainType = Expense.class)
public interface ExpenseRepository {

    @PostAuthorize("hasPermission(returnObject,'read')")
    Expense findOne(Long id);

    @PreAuthorize("hasPermission(#expense,'write')")
    Expense save(Expense expense);

    List<Expense> findByReporter(Employee employee);

    List<Expense> findByReporterSupervisor(Employee employee);
}
