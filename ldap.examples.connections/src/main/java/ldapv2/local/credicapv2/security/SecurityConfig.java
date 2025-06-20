package ldapv2.local.credicapv2.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;

//import org.springframework.security.config.Customizer;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	    @Bean
	    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    	
	        http.csrf(crsf -> crsf.disable()).formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/", true)
	        .permitAll())
	        .authorizeHttpRequests(auth -> 
	        auth.requestMatchers("/logger","/login.js", "/index.js", "/index.css", "/login.css", "/images/**")
	        .permitAll()
	        .anyRequest().authenticated())
	        .logout(logout -> logout
	    	.logoutUrl("/logout") 
	    	.logoutSuccessUrl("/login?logout")
	    	.permitAll());
	    	 
	        return http.build();
	      
	    }

	    @Bean
	    LdapContextSource contextSource() {
	    	
	        LdapContextSource contextSource = new LdapContextSource();
	        contextSource.setUrl("");
	        contextSource.setBase("DC=credicap,DC=local");
	        contextSource.setUserDn("CN=Kassio Fogaca Administrador,OU=Usuarios Administrativos,OU=TI,OU=Credicap,DC=credicap,DC=local");
	        contextSource.setPassword("");
	        
	        return contextSource;
	    }

	    @Bean
	    AuthenticationManager authenticationManager(BaseLdapPathContextSource contextSource) {
	    	
	        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
	        factory.setUserSearchBase("OU=Usuarios Administrativos,OU=TI,OU=Credicap");
	        factory.setUserSearchFilter("(sAMAccountName={0})");
	        
	        return factory.createAuthenticationManager();
	    }
	
}
