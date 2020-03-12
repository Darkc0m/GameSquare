package es.GameSquare.GameSquareApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Override
	 protected void configure(HttpSecurity http) throws Exception {
		
		// Public pages
		 http.authorizeRequests().antMatchers("/").permitAll();
		 http.authorizeRequests().antMatchers("/login").permitAll();
		 http.authorizeRequests().antMatchers("/register").permitAll();
		 http.authorizeRequests().antMatchers("/all_games{page}").permitAll();
		 http.authorizeRequests().antMatchers("/all_mods{page}").permitAll();
		 http.authorizeRequests().antMatchers("/games/{game_id}").permitAll();
		 http.authorizeRequests().antMatchers("/mods/{mod_id}").permitAll();
		 http.authorizeRequests().antMatchers("/search{name}{game_page}{mod_page}").permitAll();
		 
		 // Css
		 http.authorizeRequests().antMatchers("/styles.css").permitAll();

		 //Private Pages
		http.authorizeRequests().antMatchers("/profile").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/profile/modify").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/profile/modified").hasAnyRole("USER");
		http.authorizeRequests().antMatchers("/publish").hasAnyRole("USER");

		 //Private pages
		 http.authorizeRequests().anyRequest().authenticated();
		 
		 // Login - logout
		 //http.formLogin().loginPage("/login");
		 http.formLogin().loginProcessingUrl("/login");
		 http.formLogin().defaultSuccessUrl("/");
		 
		 http.logout().logoutUrl("/logout");
		 http.logout().logoutSuccessUrl("/");
		 
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}
	
}
