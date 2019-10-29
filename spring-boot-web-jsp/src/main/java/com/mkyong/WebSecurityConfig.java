package com.mkyong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import services.UserService;





@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers( "/css/**","/webjars/**").permitAll()
                
               .anyRequest().permitAll()//authenticated()
                    .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll().and().csrf().disable();
    }
    @Autowired
    private  UserService userService ;
    @Autowired
    public void configureGlocal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userService);
    }
   
}



