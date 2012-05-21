package expenses.context.web;

import static org.fest.assertions.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import expenses.context.web.TenantFilter;

public class TenantFilterTest {
    private MockHttpServletRequest request;
    private TenantFilter tenantFilter;

    @Before
    public void setup() {
        request = new MockHttpServletRequest();
        tenantFilter = new TenantFilter();
    }

    @Test
    public void expenses() {
        request.setServletPath("/expenses");
        assertThat(tenantFilter.ignoreMatcher.matches(request)).isTrue();
        request.setServletPath("/expenses/");
        assertThat(tenantFilter.ignoreMatcher.matches(request)).isTrue();
    }

}
