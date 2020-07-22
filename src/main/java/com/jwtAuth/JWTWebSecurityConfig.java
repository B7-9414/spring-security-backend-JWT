package com.jwtAuth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtUnAuthorizedResponseAuthenticationEntryPoint jwtUnAuthorizedResponseAuthenticationEntryPoint;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtTokenAuthorizationOncePerRequestFilter jwtAuthenticationTokenFilter;

	@Autowired
	private DataSource dataSource;

	@Value("${jwt.get.token.uri}")
	private String authenticationPath;
	
	@Value("${jwt.signing.key.secret.rememberme}")
	private String secret;
	
	@Value("${jwt.token.expiration.in.rememberme.seconds}")
	private int expiration;
	
	
	@Bean
	public PasswordEncoder passwordEncoderBean() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
	/*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
		auth.inMemoryAuthentication().withUser("test").password(passwordEncoderBean().encode("1234")).roles("USER");

	}

	
	
	
 	 * new for register the user to the database => 
	 *	    .authorizeRequests().antMatchers("/authenticate","/register").permitAll().anyRequest().authenticated().and().  
	 * 
	 * new for remember me with persistentTokenRepository to save the token in database  => 
	 * 		.and().rememberMe().tokenValiditySeconds(2592000).key("Password").tokenRepository(persistentTokenRepository()); 
	 */
	
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests().antMatchers("/register", "/hello-world").permitAll()
				.anyRequest().authenticated().and().exceptionHandling()
				.authenticationEntryPoint(jwtUnAuthorizedResponseAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests().anyRequest()
				.authenticated();
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

		/*
		httpSecurity.authorizeRequests().anyRequest().hasAnyRole("ADMIN", "USER")
		    .and()
		    .authorizeRequests().antMatchers("/login**").permitAll()
		    .and()
		    .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
		    .and()
		    .logout().logoutSuccessUrl("/login").permitAll()
		    .and()
		    .rememberMe().rememberMeParameter("remember-me").key(secret).tokenValiditySeconds(expiration)
		    .tokenRepository(persistentTokenRepository()).userDetailsService(userDetailsService())
		    .and()
		    .csrf().disable();
		*/
		
		/* new for H2 Console Needs this setting =>
		 * 		httpSecurity.headers().frameOptions().sameOrigin().cacheControl();
		 */
	}

	@Override
	public void configure(WebSecurity webSecurity) throws Exception {
		webSecurity.ignoring().antMatchers(HttpMethod.POST, authenticationPath).antMatchers(HttpMethod.OPTIONS, "/**")
				.and().ignoring().antMatchers(HttpMethod.GET, "/" // Other Stuff You want to Ignore
				).and().ignoring();

	}

	// in remomber me 
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		final JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}

}
