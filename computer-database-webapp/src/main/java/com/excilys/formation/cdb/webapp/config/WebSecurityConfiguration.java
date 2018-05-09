package com.excilys.formation.cdb.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.excilys.formation.cdb.service.UserService;

@Configuration
@EnableWebMvc
@EnableWebSecurity
@ComponentScan("com.excilys.formation.cdb")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    public WebSecurityConfiguration(UserService userService) {
        this.userDetailsService = userService;
    }

    @Bean
    public DigestAuthenticationEntryPoint digestAuthenticationEntryPoint() {
        DigestAuthenticationEntryPoint authenticationEntryPoint = new DigestAuthenticationEntryPoint();
        authenticationEntryPoint.setKey("key");
        authenticationEntryPoint.setRealmName("realm");
        return authenticationEntryPoint;
    }

    @Bean
    public DigestAuthenticationFilter digestAuthenticationFilter() {
        DigestAuthenticationFilter authenticationFilter = new DigestAuthenticationFilter();
        authenticationFilter.setPasswordAlreadyEncoded(true);
        authenticationFilter.setAuthenticationEntryPoint(digestAuthenticationEntryPoint());
        authenticationFilter.setUserDetailsService(userDetailsService);
        return authenticationFilter;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/computer/edit", "/computer/add", "/computer/delete").hasRole("ADMIN")
                .antMatchers("/computer/dashboard").hasAnyRole("USER", "ADMIN").and()

                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .defaultSuccessUrl("/computer/dashboard")
                    .and()

                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .and()

                .exceptionHandling().accessDeniedPage("/403").and()

                .csrf();
    }
}