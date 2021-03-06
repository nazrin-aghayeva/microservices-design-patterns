package com.springboot.edgeserver.config;

import com.microservice.authentication.common.service.SharedAuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class EdgeServerWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String[] WHITELIST = {
            "/api/**",
            "/**/*.js",
            "/**/*.css",
            "/**/*.html",
            "/favicon.ico"
    };

    private final SharedAuthenticationService sharedAuthenticationService;

    @Override
    protected UserDetailsService userDetailsService() {
        return sharedAuthenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest().hasRole("ADMIN")
                .and()
                .formLogin()
                .and()
                .logout();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(WHITELIST);
    }
}
