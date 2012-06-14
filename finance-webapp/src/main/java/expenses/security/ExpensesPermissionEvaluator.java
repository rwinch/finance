package expenses.security;

import java.io.Serializable;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import expenses.domain.Employee;
import expenses.domain.Expense;

@Component
public class ExpensesPermissionEvaluator implements PermissionEvaluator {

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		if (targetDomainObject == null) {
			return true;
		}
		Employee employee = (Employee) authentication.getPrincipal();
		if (targetDomainObject instanceof Expense) {
			return hasPermission(employee, (Expense) targetDomainObject, permission);
		}
		throw new IllegalArgumentException("targetDomainObject type is unsupported. Got "
				+ targetDomainObject.getClass());
	}

	private boolean hasPermission(Employee employee, Expense expense, Object permission) {
		if(!tenantPermitted(employee, expense)) {
			return false;
		}
		Long reporterId = expense.getReporter().getId();
		Long employeeId = employee.getId();
		Long supervisorId = expense.getReporter().getSupervisor().getId();
		if ("read".equals(permission)) {
			return employeeId.equals(reporterId) || employeeId.equals(supervisorId);
		}
		else if ("write".equals(permission)) {
			if (expense.getId() == null) {
				return employeeId.equals(reporterId) || employeeId.equals(supervisorId);
			}
			return employeeId.equals(supervisorId);
		}
		throw new IllegalArgumentException("permission " + permission + " is not supported");
	}
	
	private boolean tenantPermitted(Employee employee, Expense expense) {
		if(employee.getTenantId() == null) {
			return expense.getTenantId() == null;
		}
		return employee.getTenantId().equals(expense.getTenantId());
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		throw new UnsupportedOperationException();
	}
}