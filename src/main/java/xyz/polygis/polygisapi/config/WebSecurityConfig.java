package xyz.polygis.polygisapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.securityMatcher("/app/**")
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("/api/admin/**").hasRole("")
						.anyRequest().authenticated())
				.securityMatcher("/api/v1/**")
				.authorizeHttpRequests((authz) -> authz
						.requestMatchers("**").permitAll());
		return http.build();
	}

}