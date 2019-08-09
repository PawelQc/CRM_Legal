package pl.qceyco.app.secureapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.employee.EmployeeRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Employee employee = employeeRepository.findFirstByEmailLogin(username);
        if (employee == null) {
            throw new UsernameNotFoundException("Employee not found");
        }

        return new UserPrincipal(employee);
    }

}
