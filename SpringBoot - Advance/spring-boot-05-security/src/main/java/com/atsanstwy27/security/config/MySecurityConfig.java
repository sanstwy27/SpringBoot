package com.atkjs927.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser("admin").password("{noop}XXXX").roles("VIP1","VIP2","VIP3")
                .and()
                .withUser("test1").password("{noop}XXXX").roles("VIP1")
                .and()
                .withUser("test2").password("{noop}XXXX").roles("VIP2")
                .and()
                .withUser("test3").password("{noop}XXXX").roles("VIP3");;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http.authorizeRequests().antMatchers("/").permitAll()
            .antMatchers("/level1/**").hasRole("VIP1")
            .antMatchers("/level2/**").hasRole("VIP2")
            .antMatchers("/level3/**").hasRole("VIP3");

        http.formLogin().usernameParameter("user").passwordParameter("pwd")
                .loginPage("/userlogin");

        http.logout().logoutSuccessUrl("/");

        http.rememberMe().rememberMeParameter("remeber");
    }

}
