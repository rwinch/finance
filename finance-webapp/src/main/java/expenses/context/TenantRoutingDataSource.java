package expenses.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


public class TenantRoutingDataSource extends AbstractRoutingDataSource {
    @Autowired
    private TenantContext tenantContext;

    @Override
    protected Object determineCurrentLookupKey() {
        return tenantContext.getTenantId();
    }
}