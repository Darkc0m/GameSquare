package es.GameSquare.GameSquareApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UsersRepository UserRPO;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		 auth.inMemoryAuthentication().withUser("user").password("pass")
		 .roles("USER");

		 auth.inMemoryAuthentication().withUser("admin").password("adminpass")
		 .roles("USER", "ADMIN");		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//Private Pages
		http.authorizeRequests().antMatchers("/profile").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/publish").hasAnyRole("ADMIN");
		
		//Public pages
		http.authorizeRequests().anyRequest().permitAll();
		
		http.csrf().disable();

	}
}
