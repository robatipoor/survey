package com.snap.survey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final AuthenticationEntryPoint authenticationEntryPoint;
  private final AuthorizationFilter authorizationFilter;

  @Autowired
  public WebSecurityConfig(
      @Qualifier("customUserDetailsServiceImpl") UserDetailsService userDetailsService,
      AuthenticationEntryPoint authenticationEntryPoint,
      AuthorizationFilter authorizationFilter) {
    this.userDetailsService = userDetailsService;
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.authorizationFilter = authorizationFilter;
  }

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
  }

  @Bean
  public PasswordEncoder passwordEncoderBean() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors()
        .and()
        .csrf()
        .disable()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .authorizeRequests()
        .antMatchers(Constants.PUBLIC_ACCESS_API_LIST)
        .permitAll()
        .antMatchers(Constants.LOCAL_ACCESS_API_LIST)
        .access("hasIpAddress(\"127.0.0.1\") or hasIpAddress(\"::1\")")
        .anyRequest()
        .authenticated()
        .and()
        .httpBasic()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterAt(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
  }
}
