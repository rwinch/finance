package expenses.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import expenses.domain.Employee;

@Component
public class SpringSecurityUserContext implements EmployeeContext {

	@Override
	public Employee getCurrent() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		}
		return (Employee) authentication.getPrincipal();
	}
}