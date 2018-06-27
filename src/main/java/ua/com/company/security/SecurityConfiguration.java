package ua.com.company.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@ComponentScan("ua.com.company.*")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter {
	
	private static final String USER_ROLE = "hasRole('ROLE_USER')";
	private static final String ADMIN_ROLE = "hasRole('ROLE_ADMIN')";
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
	
	@Autowired
	public void globalConfigure(AuthenticationManagerBuilder managerBuilder, AuthenticationProvider provider) {
		try {
			configurer()
			.withUser("root")
			.password("root")
			.roles("ADMIN")
			.and()
			.configure(managerBuilder);
			managerBuilder.authenticationProvider(provider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
	    return super.userDetailsServiceBean();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer(){
		
		return new InMemoryUserDetailsManagerConfigurer<>();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.formLogin()
			.loginPage("/login").permitAll()
			.failureUrl("/loginFail").permitAll()
			.usernameParameter("email")
			.passwordParameter("password")
			.defaultSuccessUrl("/")
		.and()
		.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/")
		.and()
		.authorizeRequests()
			.antMatchers("/", "/registration", "/information", "/indexPage", "/translate", "/userReg").permitAll()
			.antMatchers("/admin-*", "/admin").access(ADMIN_ROLE)
			.antMatchers("/usr-*", "/**").access(ADMIN_ROLE + " or " + USER_ROLE)
		.anyRequest().authenticated();
		http.csrf().csrfTokenRepository(csrfTokenRepository());
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		web.ignoring().antMatchers("/style/**");
		web.ignoring().antMatchers("/jscript/**");
		
	}
	
	private CsrfTokenRepository csrfTokenRepository() { 
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
	    repository.setSessionAttributeName("_csrf");
	    repository.setHeaderName("X-XSRF-TOKEN");

	    return repository; 
	}
	

}
