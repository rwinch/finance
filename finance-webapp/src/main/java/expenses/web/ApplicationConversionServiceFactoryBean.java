package expenses.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

import expenses.domain.Employee;

/**
 * A central place to register application converters and formatters.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    @Override
    protected void installFormatters(FormatterRegistry registry) {
        super.installFormatters(registry);
        // Register application converters and formatters
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
