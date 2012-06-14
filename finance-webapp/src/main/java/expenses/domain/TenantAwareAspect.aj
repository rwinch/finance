package expenses.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import expenses.context.TenantContext;

privileged aspect TenantAwareAspect {
	declare parents : expenses.domain.Employee implements TenantAware;
	declare @type: expenses.domain.Employee: @Configurable;

	declare parents : expenses.domain.Expense implements TenantAware;
	declare @type: expenses.domain.Expense: @Configurable;

	declare parents : org.springframework.security.core.userdetails.User implements TenantAware;
	declare @type: org.springframework.security.core.userdetails.User: @Configurable;

	private transient String TenantAware.tenantId;

	@JsonIgnore
	@Autowired
	public void TenantAware.setTenantContext(TenantContext tenantContext) {
		this.tenantId = tenantContext.getTenantId();
	}

	@JsonIgnore
	public void TenantAware.setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	@JsonProperty
	public String TenantAware.getTenantId() {
		return tenantId;
	}
}
