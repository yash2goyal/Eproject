package com.yash.project.security;
//import com.yash.springcloud.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic();
        //http.formLogin();
//        http.authorizeRequests().mvcMatchers(HttpMethod.GET,"/show").hasAnyRole("CUSTOMER","ADMIN").mvcMatchers(HttpMethod.GET,"/user").hasRole("CUSTOMER").and().csrf().disable();
        http.authorizeRequests().antMatchers("/ViewProfileAsSeller").permitAll().mvcMatchers(HttpMethod.GET,"/viewAllCustomer").hasRole("ADMIN").mvcMatchers(HttpMethod.GET,"/viewAllSeller").hasRole("ADMIN").antMatchers("/loginPage").permitAll().antMatchers("/registerAsSeller").permitAll().antMatchers("/registerAsCustomer").permitAll().mvcMatchers(HttpMethod.GET,"/show").hasAnyRole("CUSTOMER","ADMIN").and().csrf().disable();

        //http.authorizeRequests().mvcMatchers(HttpMethod.GET,"/couponapi/coupons").;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}



