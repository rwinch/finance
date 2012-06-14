package expenses.context.web;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.RegexRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import expenses.context.TenantContext;

/**
 * Obtains the tenant id as the first path after the context root and contains a / after it. If the tenant is found, it
 * will then replace the {@link HttpServletRequest} with {@link TenantAwareHttpServletRequest}. A few examples where
 * /context is always the context root:
 * 
 * <ul>
 * <li>/context/ - null tenant</li>
 * <li>/context/a - null tenant because there is no trailing slash afterwards. This allows for URL's with no tenant that
 * do not have a folder. For example, a logout URL.</li>
 * <li>/context/a/ - tenant id of a</li>
 * <li>/context/tenant/b/c/def.png - tenant id of tenant</li>
 * </ul>
 * 
 * 
 * @author Rob Winch
 * 
 */
@Component
public class TenantFilter extends OncePerRequestFilter implements TenantContext {
	private static ThreadLocal<String> TENANT_ID = new ThreadLocal<String>();

	RequestMatcher ignoreMatcher = new RegexRequestMatcher("^/(admin|oauth|employee|expenses|resources).*", null);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (ignoreMatcher.matches(request)) {
			filterChain.doFilter(request, response);
			return;
		}
		String tenantId = getTenantId(request);
		if (tenantId == null) {
			filterChain.doFilter(request, response);
			return;
		}
		try {
			TENANT_ID.set(tenantId);
			filterChain.doFilter(new TenantAwareHttpServletRequest(request, tenantId), response);
		}
		finally {
			TENANT_ID.remove();
		}
	}

	public String getTenantId() {
		return TENANT_ID.get();
	}

	private String getTenantId(HttpServletRequest request) {
		String tenantId = getTenantId();
		if (tenantId != null) {
			return tenantId;
		}

		String requestUrl = currentUrl(request);

		if ("".equals(requestUrl) || "/".equals(requestUrl)) {
			return null;
		}
		StringTokenizer tokens = new StringTokenizer(requestUrl, "/");
		if (tokens.hasMoreTokens()) {
			String result = tokens.nextToken();
			if (tokens.hasMoreTokens() || requestUrl.endsWith("/")) {
				return result;
			}
		}
		return null;
	}

	private String currentUrl(HttpServletRequest request) {
		StringBuilder url = new StringBuilder();
		url.append(request.getServletPath());

		String pathInfo = request.getPathInfo();
		if (pathInfo != null) {
			url.append(pathInfo);
		}
		return url.toString();
	}

	/**
	 * A wrapper for the HttpServletRequest that includes the tenant id in the context path. Note that we do not cache
	 * the servletPath or the contextPath as this can lead to problem with forwarding requests.
	 * 
	 * @author Rob Winch
	 * 
	 */
	private static class TenantAwareHttpServletRequest extends HttpServletRequestWrapper {
		private final String tenantId;

		public TenantAwareHttpServletRequest(HttpServletRequest request, String tenantId) {
			super(request);
			this.tenantId = tenantId;
		}

		@Override
		public String getServletPath() {
			String servletPath = super.getServletPath();
			int start = servletPath.indexOf(tenantId);
			if (start < 0) {
				return servletPath;
			}
			int end = start + tenantId.length();
			return servletPath.substring(end);
		}

		@Override
		public String getContextPath() {
			return super.getContextPath() + "/" + tenantId;
		}
	}
}
