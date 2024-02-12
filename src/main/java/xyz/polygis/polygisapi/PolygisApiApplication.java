package xyz.polygis.polygisapi;

// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import xyz.polygis.polygisapi.repository.UserRepository;
// import xyz.polygis.polygisapi.service.UserService;

@SpringBootApplication
public class PolygisApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(PolygisApiApplication.class, args);
	}

	@Autowired
	UserRepository userRepository;

	@Bean
	@ConditionalOnProperty(prefix = "app", name = "db.init.enabled", havingValue = "true")
	public CommandLineRunner demoCommandLineRunner() {
		return args -> {

			System.out.println("Running.....");
		};
	}
	// @Bean
	// public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
	// return args -> {

	// System.out.println("Let's inspect the beans provided by Spring Boot:");

	// String[] beanNames = ctx.getBeanDefinitionNames();
	// Arrays.sort(beanNames);
	// for (String beanName : beanNames) {
	// System.out.println(beanName);
	// }

	// };
	// }
}
