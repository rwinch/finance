package expenses.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import expenses.context.TenantContext;
import expenses.domain.Employee;
import expenses.repository.EmployeeRepository;

@Component
public class ExpensesAuthenticationProvider implements AuthenticationProvider {
    private EmployeeRepository employeeRepository;
    private TenantContext tenantContext;

    @Autowired
    public ExpensesAuthenticationProvider(EmployeeRepository employeeRepository, TenantContext tenantContext) {
        this.employeeRepository = employeeRepository;
        this.tenantContext = tenantContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        Employee employee = employeeRepository.findOneByUsername(username);

        if(employee == null) {
            throw new UsernameNotFoundException("could not find "+username);
        }

        if(!employee.getPassword().equals(token.getCredentials())) {
            throw new BadCredentialsException("Bad credentials");
        }

        String tenantId = tenantContext.getTenantId();
        employee.setTenantId(tenantId);
        return new UsernamePasswordAuthenticationToken(employee,
                employee.getPassword(),
                authorities(employee));
    }

    private List<GrantedAuthority> authorities(Employee employee) {
        List<GrantedAuthority> result = new ArrayList<GrantedAuthority>();
        result.add(new SimpleGrantedAuthority("ROLE_USER"));
        if(employee.isSupervisor()) {
            result.add(new SimpleGrantedAuthority("ROLE_SUPERVISOR"));
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }

}
