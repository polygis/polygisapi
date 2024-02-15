package xyz.polygis.polygisapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class WebSecurityConfig {

	@Configuration
	@Order(1)
	@EnableWebSecurity()
	public class BaseSecurityConfiguration {

		@Value("${app.credentials.superadmin.password}")
		String SUPERADMIN_PASSWORD;
		@Value("${app.credentials.superadmin.username}")
		String SUPERADMIN_USERNAME;
		@Value("${app.credentials.admin.username}")
		String ADMIN_USERNAME;
		@Value("${app.credentials.admin.password}")
		String ADMIN_PASSWORD;
		@Value("${app.credentials.user.username}")
		String USER_USERNAME;
		@Value("${app.credentials.user.password}")
		String USER_PASSWORD;

		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();

		@Bean
		WebSecurityCustomizer webSecurityCustomizer() {
			return (web) -> web.ignoring()
					// Spring Security should completely ignore URLs starting with /resources/
					.requestMatchers("/resources/**", "/public/api/v1/**");
		}

		@Bean
		SecurityFilterChain securityFilterChain(HttpSecurity http, HttpServletRequest req) throws Exception {
			http.authorizeHttpRequests(request -> request
					.requestMatchers("/api/v1/bmc/**").permitAll()
					.anyRequest().hasRole("USER"))
					.formLogin(login -> login
							// set permitAll for all URLs associated with Form Login
							.permitAll())
					.csrf(AbstractHttpConfigurer::disable);
			// .sessionManagement(session -> session.disable());
			return http.build();
		}

		@Bean
		PasswordEncoder getPasswordEncoder() {
			return enc;
		}

		@Bean
		@Profile("DEV")
		UserDetailsService userDetailsService() {
			
			UserDetails superAdmin = User.withUsername(SUPERADMIN_USERNAME).password(enc.encode(SUPERADMIN_PASSWORD))
					.roles("SUPERADMIN", "ADMIN", "USER").build();
			UserDetails user = User.withUsername(USER_USERNAME).password(enc.encode(USER_PASSWORD)).roles("USER").build();
			UserDetails admin = User.withUsername(ADMIN_USERNAME).username(ADMIN_USERNAME).password(enc.encode(ADMIN_PASSWORD))
					.roles("ADMIN", "USER")
					.build();
			return new InMemoryUserDetailsManager(user, admin, superAdmin);
		}

	}
}