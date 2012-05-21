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
        if(targetDomainObject == null) {
            return true;
        }
        Employee employee = (Employee) authentication.getPrincipal();
        if(targetDomainObject instanceof Expense) {
            return hasPermission(employee, (Expense)targetDomainObject, permission);
        }
        throw new IllegalArgumentException("targetDomainObject type is unsupported. Got "+targetDomainObject.getClass());
    }

    private boolean hasPermission(Employee employee, Expense expense, Object permission) {
        Employee reporter = expense.getReporter();
        if("read".equals(permission)) {
            return employee.equals(reporter) || employee.equals(reporter.getSupervisor());
        } else if("write".equals(permission)) {
            if(expense.getId() == null) {
                return employee.equals(reporter) || employee.equals(reporter.getSupervisor());
            }
            return employee.equals(reporter.getSupervisor());
        }
        throw new IllegalArgumentException("permission "+permission+" is not supported");
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
            Object permission) {
        throw new UnsupportedOperationException();
    }
}