package ie.irishrail.server.rest.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService    userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(MyUserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/system/recaptchaKey", "/system/health");
        web.ignoring().antMatchers("/registration/**", "/adminregistration/process");
        web.ignoring().antMatchers("/customer/**");
    }

    @Override
    protected final void configure(HttpSecurity http) throws Exception {
        http = http.cors().and().csrf().disable();

        http = http
            .sessionManagement()
            .invalidSessionUrl(SecurityConstants.LOGIN_PAGE_URL)
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and();

        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/",
                    "/*.*",
                    SecurityConstants.LOGIN_PAGE_URL,
                    "/resources/**",
                    "/assets/**",
                    "/webjars/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/system/check.htm").permitAll()
                .antMatchers("/system/**","/passwordforgotchange/**").authenticated()
                .anyRequest().authenticated();

        http.addFilterBefore(new ExceptionHandlerFilter(), JWTAuthenticationFilter.class)
            .addFilter(new JWTAuthenticationFilter(authenticationManager(), getApplicationContext()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager(), userDetailsService)).logout().clearAuthentication(true).logoutSuccessUrl(SecurityConstants.LOGIN_PAGE_URL);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
