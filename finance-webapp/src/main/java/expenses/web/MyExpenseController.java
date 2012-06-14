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

@RequestMapping("/expenses/my")
@Controller
public class MyExpenseController extends AbstractExpensesController {

	@RequestMapping(value = "/{id}", produces = "text/html")
	public String show(@PathVariable("id")
	Long id, Model uiModel) {
		addDateTimeFormatPatterns(uiModel);
		uiModel.addAttribute("expense", expenseRepository.findOne(id));
		uiModel.addAttribute("itemId", id);
		return "expenses/my/show";
	}

	@RequestMapping(produces = "text/html")
	public String list(Model uiModel) {
		Employee currentUser = this.employeeContext.getCurrent();
		List<Expense> expenses = expenseRepository.findByReporter(currentUser);
		uiModel.addAttribute("expenses", expenses);
		addDateTimeFormatPatterns(uiModel);
		return "expenses/my/list";
	}

	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
	public String create(@Valid
	Expense expense, BindingResult bindingResult, Model uiModel) {
		if (bindingResult.hasErrors()) {
			populateEditForm(uiModel, expense);
			return "expenses/my/create";
		}
		expense.setApproved(false);
		Employee currentUser = employeeContext.getCurrent();
		expense.setReporter(currentUser);
		expense = expenseRepository.save(expense);
		uiModel.addAttribute("expense.id", expense.getId());
		return "redirect:/expenses/my/{expense.id}";
	}

	@RequestMapping(params = "form", produces = "text/html")
	public String createForm(Expense expense) {
		return "expenses/my/create";
	}
}
