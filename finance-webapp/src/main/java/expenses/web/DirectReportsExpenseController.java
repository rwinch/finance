package expenses.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import expenses.domain.Employee;
import expenses.domain.Expense;

@Controller
@RequestMapping("/expenses/direct-reports")
public class DirectReportsExpenseController extends AbstractExpensesController {

	@RequestMapping(value = "/{id}")
	public String show(@PathVariable("id")
	Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("expense", expenseRepository.findOne(id));
		uiModel.addAttribute("itemId", id);
		return "expenses/direct-reports/show";
	}

	@RequestMapping
	public String list(Model uiModel) {
		Employee supervisor = employeeContext.getCurrent();
		List<Expense> expenses = expenseRepository.findByReporterSupervisor(supervisor);
		uiModel.addAttribute("expenses", expenses);
		addDateTimeFormatPatterns(uiModel);
		return "expenses/direct-reports/list";
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update(@Valid
	Expense expense, BindingResult bindingResult, Model uiModel) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, expense);
			return "expenses/direct-reports/update";
		}
		expenseRepository.save(expense);
		uiModel.addAttribute("expense.id", expense.getId());
		return "redirect:/expenses/direct-reports/{expense.id}";
	}

	@RequestMapping(value = "/{id}", params = "form")
	public String updateForm(@PathVariable("id")
	Long id, Model uiModel) {
		populateEditForm(uiModel, expenseRepository.findOne(id));
		return "expenses/direct-reports/update";
	}
}