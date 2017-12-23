package one.two.three.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
@ComponentScan("one.two.three.*")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends  WebSecurityConfigurerAdapter {
	
	
	@Autowired
	@Qualifier("userDetailServiceImpl")
	UserDetailsService userDetailsService;
	
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
	
	@Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> configurer(){
		
		return new InMemoryUserDetailsManagerConfigurer<>();
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

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/", "registration")
			.permitAll()
			.antMatchers("/admin/**")
			.access("hasRole('ADMIN')")
			.antMatchers("/userpage/**")
			.access("hasRole('USER') or hasRole('ADMIN')")
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
			.usernameParameter("email")
			.passwordParameter("password")
			.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/");
		http.csrf().csrfTokenRepository(csrfTokenRepository());
		http.authorizeRequests().antMatchers("/resources/**").permitAll().anyRequest().permitAll();
	}
	
	private CsrfTokenRepository csrfTokenRepository() { 
	    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository(); 
	    repository.setSessionAttributeName("_csrf");
	    repository.setHeaderName("X-XSRF-TOKEN");

	    return repository; 
	}
	

}
