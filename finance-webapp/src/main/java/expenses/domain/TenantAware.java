package expenses.domain;


public interface TenantAware {

	String getTenantId();

	void setTenantId(String tenantId);
}
