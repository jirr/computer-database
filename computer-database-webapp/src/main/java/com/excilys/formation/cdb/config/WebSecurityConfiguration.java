package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan("com.excilys.formation.cdb")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    /*private UserDetailsService userDetailsService;
    
    public WebSecurityConfiguration(UserService userService) {
        this.userDetailsService = userService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
          = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }
     
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(10);
    }*/
    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

            .authorizeRequests()
                .anyRequest().authenticated()
                .and()

            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
        
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                //.logoutSuccessHandler(logoutSuccessHandler)
                .invalidateHttpSession(true);
                //.addLogoutHandler(logoutHandler)
                //.deleteCookies(cookieNamesToClear);
    }
}