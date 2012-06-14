package expenses.web;

import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import expenses.context.EmployeeContext;
import expenses.domain.Employee;
import expenses.repository.EmployeeRepository;
import expenses.repository.ExpenseRepository;

@Controller
abstract class AbstractExpensesController {
	@Autowired
	protected ExpenseRepository expenseRepository;

	@Autowired
	protected EmployeeRepository employeeRepository;

	@Autowired
	protected EmployeeContext employeeContext;

	void addDateTimeFormatPatterns(Model uiModel) {
		uiModel.addAttribute("expense_expensedate_date_format",
				DateTimeFormat.patternForStyle("M-", LocaleContextHolder.getLocale()));
	}

	void populateEditForm(Model uiModel, Object expense) {
		uiModel.addAttribute("expense", expense);
		addDateTimeFormatPatterns(uiModel);
		Employee currentUser = employeeContext.getCurrent();
		uiModel.addAttribute("employees", employeeRepository.findBySupervisor(currentUser));
	}
}
