/*
 * Remember-me Token based (Cookie) approach aka Simple Hash based approach
 */
package com.anoopsen.RememberMe_SpringSecurityDemo.security;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.anoopsen.RememberMe_SpringSecurityDemo.model.Role;
import com.anoopsen.RememberMe_SpringSecurityDemo.model.User;
import com.anoopsen.RememberMe_SpringSecurityDemo.service.MyUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@PropertySource("classpath:application.properties")  //this will allow spring security to locate the private key mentioned in application.properties for the below @Value
@EnableWebSecurity
public class SecurityConfig {
	
	@Value("${rememberMe.privateKey}")
	private String rememberMePrivateKey; //Use @Value to load the Remember-Me private key from a configuration file instead of hard coding it in the code
	
	final static String auth_URL = "/api/v1/auth/**";
	final static String home_URL = "/api/v1/home/**";
	
	final static String registerPage_URL = "/api/v1/auth/register";
	final static String doRegister_URL = "/api/v1/auth/do-register";
	final static String loginPage_URL = "/api/v1/auth/login";
	
	final static String logoutPage_URL = "/api/v1/auth/logout";
	final static String expiryLogoutPage_URL = "/api/v1/auth/logout-expired";
	final static String loginFailurePage_URL = "/api/v1/auth/login/error";
	
	final static String dashboardPage_URL = "/api/v1/home/dashboard";
	
	//private static final String REMEMBER_ME_PRIVATE_KEY = "12345";   private key is loaded from application.properties instead
	
	@Autowired
	MyUserDetailsService userDetailsService;
	
	@Autowired
	MyPasswordEncoder passwordEncoder;
	
	@Bean
	public AuthenticationProvider daoAuthenticationProvider() {
		
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder.passwordEncoder());
		
		return authProvider;
	}
	
	/*
	@Bean
    public AuthenticationManager myAuthenticationManager(HttpSecurity http) throws Exception {
		
        AuthenticationManagerBuilder authenticationManagerBuilder = 
        		http.getSharedObject(AuthenticationManagerBuilder.class);
        
        //adding authentication providers
        authenticationManagerBuilder.authenticationProvider(this.daoAuthenticationProvider());
        //authenticationManagerBuilder.authenticationProvider(this.rememberMeAuthenticationProvider());
        
        AuthenticationManager authManager = authenticationManagerBuilder.build(); 
        return authManager; 
    }*/
	
	@Bean
	public AuthenticationManager myAuthenticationManager() throws Exception{
		ProviderManager providerManager = new ProviderManager(Collections.singletonList(this.daoAuthenticationProvider()));
		return providerManager;
	}
	
	/*
	@Bean
	public InMemoryUserDetailsManager inMemoryUserDetailsService() {
		
		UserDetails admin = User.builder()
								.username("Anoop")
								.password(passwordEncoder.passwordEncoder().encode("12345"))
								.role(Role.ADMIN)
								.build()
								;
		
		InMemoryUserDetailsManager inMemoryUserDetails = new InMemoryUserDetailsManager(admin);
		return inMemoryUserDetails;
		
	}*/
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, RememberMeServices rememberMeServices) throws Exception{
	
		
		http
				.csrf(csrf -> csrf.disable()) //disables csrf protection, by removing csrf token in POST requests
				.authorizeHttpRequests(
						(httpRequest) -> httpRequest
										.requestMatchers(
												new AntPathRequestMatcher("/loginPage.jsp"),
												new AntPathRequestMatcher("/registerPage.jsp"),
												new AntPathRequestMatcher(auth_URL)
										)
										.permitAll()
										
										.requestMatchers(
												new AntPathRequestMatcher("/index.jsp"),
												new AntPathRequestMatcher("/getUsers.jsp"),
												new AntPathRequestMatcher(home_URL)
										)
										.authenticated()
										
										.anyRequest()
										.denyAll()
										
				)
				.userDetailsService(userDetailsService)
				.authenticationProvider(this.daoAuthenticationProvider())
				//.exceptionHandling(e -> e.accessDeniedHandler(null)    //try to create an unauthorized page, if unauthenticated user is accessing home resources, instaed of login page to pop up
				
				
				.formLogin(
						form -> form
									.loginPage(loginPage_URL)
									//.usernameParameter("username")
									//.passwordParameter("password")
									.loginProcessingUrl("/doLogin")
									.defaultSuccessUrl(dashboardPage_URL, true)
									.permitAll()
									.failureUrl(loginFailurePage_URL)
				)
				.logout(
						(logout) -> logout

									.logoutUrl("/do-logout") //this is the URL to logout a user (mentioned as logout href in index.jsp)
									.invalidateHttpSession(true)
									//.clearAuthentication(true)
									.deleteCookies("JSESSIONID")
									//.logoutRequestMatcher(new AntPathRequestMatcher("/do-logout", "GET")) //since, CSRF is disabled
									//.addLogoutHandler(new MyRememberMeLogoutHandler(rememberMeCookieName))
									.logoutSuccessUrl(logoutPage_URL) //this the URL path where the request is sent to mentioned page, when the above logoutUrl is called 
									.permitAll()
									 
									//.addLogoutHandler(this.cookieClearingLogoutHandler())
									
				)
				
				.rememberMe(
						(rememberMe) -> rememberMe
										.rememberMeServices(this.rememberMeServices())
										//.rememberMeServices(rememberMeServices)   //calls the in-built Spring Security Bean for rememberMeServices
										
										/*
										 In the Remember-Me configuration, Spring Security automatically looks for the 
										 RememberMeServices bean in the application context and uses it for Remember-Me functionality.
										 This is why you don't need to pass your custom bean as a parameter when configuring Remember-Me in your
										 security configuration. Spring Security handles the dependency injection automatically and uses the 
										 custom rememberMeServices bean you've defined.
										*/
										
										
										//.userDetailsService(userDetailsService)
										//.key("12345")
										//.rememberMeParameter("remember-me")
										//.rememberMeCookieName("remember-me")
										//.tokenValiditySeconds(60 * 5)
										
				)
				
				.sessionManagement(   //Remember-Me(Persistent-login) is a stateless session protocol
						session -> session
									  //.sessionCreationPolicy(SessionCreationPolicy.STATELESS)  //since we are making use of tomcat server to store the user credentials as session state, we should follow stateful protocol (this is needed for regular authentication, not remember-me authentication)
									  .invalidSessionUrl(expiryLogoutPage_URL)  //once the remember-me cookie has expired, this page is rendered
									  //.sessionFixation(obj -> obj.migrateSession())
									 // .maximumSessions(1)   //only 1 session is created with client, So trying to create another session in another browser(client) is not possible (to allow that instead of 1 put 2)
									  //NOTE: Spring Security session registry must note the creation of multiple concurrent sessions of same user, with the help of HttpSessionEventPublisher bean
									  
									  
				)
				/*
				 	Youtube video on Session timeout: https://youtu.be/5IuK5emO_t4?si=hLZmcR0Y29SVVwEa
				 	Article on Session control: https://www.baeldung.com/spring-security-session#:~:text=Configure%20the%20Session%20Timeout%20With%20Spring%20Boot&text=If%20we%20don't%20specify,after%20this%20period%20of%20time.
				 */
				.headers(header -> header.frameOptions(fo -> fo.sameOrigin()));
		
		
				
		
		DefaultSecurityFilterChain filterChain = http.build();
		return filterChain;
				
	}
	
	
	@Bean
    public CookieClearingLogoutHandler cookieClearingLogoutHandler() {
        return new CookieClearingLogoutHandler("JSESSIONID");
    }
	
	@Bean
	public HttpSessionEventPublisher httpSessionEventPublisher() {    //This bean is used to notify the Spring Security session registry, if the session is created or destroyed
	    return new HttpSessionEventPublisher();                       //This is important for creation of multiple concurrent sessions
	}
	
	final String rememberMeCookieName = "remember-me";
	
	@Bean
	public RememberMeAuthenticationFilter rememberMeFilter() throws Exception{
		
	    RememberMeAuthenticationFilter rememberMeFilter 
	    						= new RememberMeAuthenticationFilter(
	    								this.myAuthenticationManager(),
	    								this.rememberMeServices()
	    							);
	    
	    return rememberMeFilter;
	}
	
	
	@Bean
	public RememberMeServices rememberMeServices() {
		
		RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
		
		
		TokenBasedRememberMeServices rememberMe = //Ctrl + SHift + t, and search for TokenBasedRememberMeServices
				new TokenBasedRememberMeServices(
						rememberMePrivateKey,
						userDetailsService, 
						encodingAlgorithm
					);
		
		rememberMe.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
		rememberMe.setParameter("remember-me");  //if the checkbox "remember-me" from loginPage.jsp is checked then, the remember-me functionality is used
		rememberMe.setAlwaysRemember(false);    //we don't want user sessions to be remembered, even if the remember-me checkbox is unchecked in loginPage.jsp
		rememberMe.setCookieName(rememberMeCookieName);
		rememberMe.setCookieDomain("localhost");
	    rememberMe.setTokenValiditySeconds(60 * 1);  //1 minute
	    
		return rememberMe;
	}
	
	@Bean
	public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
	    RememberMeAuthenticationProvider rememberMeAuthenticationProvider 
	    					= new RememberMeAuthenticationProvider(rememberMePrivateKey);
	    
	    return rememberMeAuthenticationProvider;
	}
}
