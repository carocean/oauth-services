package com.fs.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@EnableWebSecurity
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private SuccessAuthentication successAuthentication;
    @Autowired
    private FailureAuthentication failureAuthentication;
    @Autowired
    private UnauthorizedEntryPoint unauthorizedEntryPoint;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/assets/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.formLogin()
//                .loginPage("/login")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().csrf().disable().cors();

        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().successHandler(successAuthentication).failureHandler(failureAuthentication);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsServiceBean() {
        Collection<UserDetails> users = buildUsers();

        return new InMemoryUserDetailsManager(users);
    }

    private Collection<UserDetails> buildUsers() {
        String password = passwordEncoder().encode("123456");

        List<UserDetails> users = new ArrayList<>();

        UserDetails user_admin = User.withUsername("admin").password(password).authorities("ADMIN", "USER").build();
        UserDetails user_user1 = User.withUsername("user 1").password(password).authorities("USER").build();

        users.add(user_admin);
        users.add(user_user1);

        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
