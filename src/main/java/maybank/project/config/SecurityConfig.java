package maybank.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
 
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//    	http
//		.csrf().disable()
//		.authorizeRequests().antMatchers(
//				 "/registration**",
//				 "/api/**",
//				 "/swagger-ui**",
//				 "/webjars/**",
//	             "/js/**",
//	             "/css/**",
//	             "/img/**",
//	             "/images/**",
//	             "/resources/**").permitAll()
//		.antMatchers(HttpMethod.POST, "/api/**").permitAll()
//		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//		.anyRequest().authenticated()
//		.and()
//		.formLogin()
//		.loginPage("/login")
//		.permitAll()
//		.and()
//		.logout()
//		.invalidateHttpSession(true)
//		.clearAuthentication(true)
//		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//		.logoutSuccessUrl("/login?logout")
//		.permitAll();
    	
    	http.csrf().disable()
    		.authorizeRequests().anyRequest().authenticated()
    		.and()
    		.httpBasic();
    }
  
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
    		.withUser("admin")
            .password("{noop}password")
            .roles("ADMIN");
    }
}