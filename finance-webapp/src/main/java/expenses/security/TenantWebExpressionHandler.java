package expenses.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.AbstractSecurityExpressionHandler;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.stereotype.Component;

import expenses.context.TenantContext;
import expenses.domain.TenantAware;

@Component
public class TenantWebExpressionHandler extends AbstractSecurityExpressionHandler<FilterInvocation> {
	private final TenantContext tenantContext;

	@Autowired
	public TenantWebExpressionHandler(TenantContext tenantContext) {
		this.tenantContext = tenantContext;
	}

	@Override
	protected SecurityExpressionRoot createSecurityExpressionRoot(Authentication authentication,
			FilterInvocation invocation) {
		String currentTenant = tenantContext.getTenantId();
		TenantWebSecurityExpressionRoot result = new TenantWebSecurityExpressionRoot(authentication, invocation,
				currentTenant);
		result.setPermissionEvaluator(getPermissionEvaluator());
		return result;
	}

	private static class TenantWebSecurityExpressionRoot extends WebSecurityExpressionRoot {
		private final String currentTenantId;

		public TenantWebSecurityExpressionRoot(Authentication a, FilterInvocation fi, String currentTenantId) {
			super(a, fi);
			this.currentTenantId = currentTenantId;
		}

		@SuppressWarnings("unused")
		public boolean isTenantAllowed() {
			TenantAware tenantAware = (TenantAware) getPrincipal();
			String usersTenantId = tenantAware.getTenantId();
			if (usersTenantId == null) {
				return currentTenantId == null;
			}
			return usersTenantId.equals(currentTenantId);
		}
	}
}
