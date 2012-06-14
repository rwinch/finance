package expenses.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;
import org.springframework.stereotype.Component;

import expenses.domain.Employee;
import expenses.repository.EmployeeRepository;
import expenses.repository.ExpenseRepository;

/**
 * A central place to register application converters and formatters.
 */
@Component
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {
    @Autowired
    private EmployeeRepository employeeRepository;

	@Override
	protected void installFormatters(FormatterRegistry registry) {
		super.installFormatters(registry);
		registry.addConverter(getStringToEmployeeConverter());
		registry.addConverter(getIdToEmployeeConverter());
		registry.addConverter(getEmployeeToStringConverter());
	}

	public Converter<String, Employee> getStringToEmployeeConverter() {
		return new org.springframework.core.convert.converter.Converter<String,Employee>() {
			public Employee convert(String id) {
				return  getObject().convert(getObject().convert(id, Long.class), Employee.class);
			}
		};
	}
	
	public Converter<Long, Employee> getIdToEmployeeConverter() {
        return new org.springframework.core.convert.converter.Converter<Long,Employee>() {
            public Employee convert(Long id) {
                return  employeeRepository.findOne(id);
            }
        };
    }
	
	public Converter<Employee, String> getEmployeeToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<expenses.domain.Employee, java.lang.String>() {
            public String convert(Employee employee) {
                return new StringBuilder().append(employee.getLastName()).append(", ").append(employee.getFirstName())
                        .toString();
            }
        };
    }
}
