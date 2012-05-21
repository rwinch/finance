package expenses.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

public interface TenantAware {

    @JsonIgnore
    String getTenantId();

    void setTenantId(String tenantId);
}
