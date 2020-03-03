package es.GameSquare.GameSquareApp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
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

		 //Private pages
		 http.authorizeRequests().anyRequest().authenticated();
		 
		// Login form
		 http.formLogin().loginPage("/login");
		 http.formLogin().usernameParameter("username");
		 http.formLogin().passwordParameter("password");
		 http.formLogin().defaultSuccessUrl("/");
		 http.formLogin().failureUrl("/loginerror");
		 
		// Logout
		 http.logout().logoutUrl("/logout");
		 http.logout().logoutSuccessUrl("/");
		 
		// Disable CSRF at the moment
		 http.csrf().disable();

	}

}
