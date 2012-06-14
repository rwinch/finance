package expenses.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Employee implements Serializable {

	@NotNull
	@Size(min = 3)
	private String firstName;

	@NotNull
	@Size(min = 3)
	private String lastName;

	@NotNull
	@Column(unique = true)
	@Size(min = 3)
	private String username;

	@NotNull
	@Size(min = 3)
	@JsonIgnore
	private String password;

	@ManyToOne
	private expenses.domain.Employee supervisor;

	@JsonIgnore
	public boolean isSupervisor() {
		return supervisor == null;
	}

	private static final long serialVersionUID = -7243832286546823896L;
}
