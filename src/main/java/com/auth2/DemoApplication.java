package com.auth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication
@RestController
@EnableOAuth2Sso // To make the link to Facebook
public class DemoApplication extends WebSecurityConfigurerAdapter  {
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		return principal;
	}
	// 34an yrooh 3 sf7a el home( el login ) alawal myla3y el f el ant matcher
	// lw 4ltha byrooh 3 saf7a ellogin bta3 el face 3tool
	//To make the link visible we also need to switch off the security on the home page by adding this
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.antMatcher("/**")
				.authorizeRequests()
				.antMatchers("/", "/login**", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated()
				.and()
				.logout().logoutSuccessUrl("/").permitAll()
				//Spring Security has built in for a /logout (clear the session and invalidate the cookie)
				.and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
//to protect the user from Cross Site Request Forgery , it requires a token to be included in the request

	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
}
