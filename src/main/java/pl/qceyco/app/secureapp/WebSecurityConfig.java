package pl.qceyco.app.secureapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@ComponentScan(basePackages = "pl.qceyco")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/").permitAll()
                .and()
                .authorizeRequests().antMatchers("/login").permitAll()
                //TEN PROBLEM ZAŁATWIĆ POPRZEZ PODANIE 2 RÓŻNYCH WIDOKÓW
                /*.authorizeRequests().antMatchers("/employees/list").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/clients/list").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/projects/list").hasAnyRole("ADMIN", "USER")
                .and()
                .authorizeRequests().antMatchers("/clients/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/employees/**").hasRole("ADMIN")
                .and()
                .authorizeRequests().antMatchers("/projects/**").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successForwardUrl("/login-success")
                .and()
                .logout().invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/logout-success").permitAll();
    }

}



