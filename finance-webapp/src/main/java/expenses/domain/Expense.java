package expenses.domain;

import java.util.Calendar;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Expense {

	@NotNull
	@Size(min = 3)
	private String description;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Calendar expenseDate;

	@Min(0L)
	@NotNull
	@Digits(integer = Integer.MAX_VALUE, fraction = 2)
	private Double ammount;

	@ManyToOne
	private Employee reporter;

	private Boolean approved;
}
