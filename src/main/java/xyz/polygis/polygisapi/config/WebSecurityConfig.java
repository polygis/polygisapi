package xyz.polygis.polygisapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Order(10)
@EnableWebSecurity()
public class WebSecurityConfig {
	public class MyWebSecurityConfiguration {

		@Bean
		public WebSecurityCustomizer webSecurityCustomizer() {
			return (web) -> web.ignoring()
					// Spring Security should completely ignore URLs starting with /resources/
					.requestMatchers("/resources/**", "/public/**");
		}

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(requests -> requests
										.anyRequest()
                    .hasRole("USER"))
                    // Possibly more configuration ...
                    .formLogin(login -> login // enable form based log in
                            // set permitAll for all URLs associated with Form Login
                            .permitAll());
			return http.build();
		}

		@Bean
		public UserDetailsService userDetailsService() {
			UserDetails user = User.withDefaultPasswordEncoder()
					.username("user")
					.password("password")
					.roles("USER")
					.build();
			UserDetails admin = User.withDefaultPasswordEncoder()
					.username("admin")
					.password("password")
					.roles("ADMIN", "USER")
					.build();
			return new InMemoryUserDetailsManager(user, admin);
		}
		// Possibly more bean methods ...
	}
	// @Bean
	// public SecurityFilterChain securityFilterChain(HttpSecurity http) throws
	// Exception {
	// // return http
	// // .authorizeHttpRequests(requests -> requests
	// // .requestMatchers("/api/v1/**").permitAll()
	// // .anyRequest().authenticated())
	// // .csrf(csrf -> csrf.ignoringRequestMatchers("/api/v1/**"))
	// // .sessionManagement(management -> management.disable())
	// // .build();
	// }
}