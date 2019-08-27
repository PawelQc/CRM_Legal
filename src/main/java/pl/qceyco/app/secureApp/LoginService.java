package pl.qceyco.app.secureApp;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.qceyco.app.employee.Employee;
import pl.qceyco.app.secureApp.config.UserPrincipal;

@Service
public class LoginService {

    public Employee getLoggedInUser() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        validatePrincipal(authentication.getPrincipal());
        return ((UserPrincipal) authentication.getPrincipal()).getUserDetails();
    }

    private void validatePrincipal(Object principal) {
        if (!(principal instanceof UserPrincipal)) {
            throw new IllegalArgumentException("Principal can not be null!");
        }
    }

}


