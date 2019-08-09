package pl.qceyco.app.secureapp;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.qceyco.app.employee.Employee;

import java.util.Collection;
import java.util.Collections;

public class UserPrincipal implements UserDetails {

    private Employee employee;

    public UserPrincipal(Employee employee) {
        super();
        this.employee = employee;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority("USER"));

    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return employee.getPassword();
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return employee.getEmailLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return true;
    }

}
