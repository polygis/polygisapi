package xyz.polygis.polygisapi.web.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.polygis.polygisapi.model.User;
import xyz.polygis.polygisapi.service.UserService;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	public String requestMethodName() {
		return "Greetings from Spring Boot! Api v1";
	}

	@ResponseStatus(HttpStatus.CREATED) // 201
	@PostMapping("/users")
	public User create(@RequestBody User user) {
		return userService.save(user);
	}

	@PatchMapping(value = "/users/{id}")
	public ResponseEntity<?> updateMemberShip(@RequestBody String membership, @PathVariable("id") Long id) {

		Optional<User> user = userService.update(id, membership);
		if (user.isEmpty()) {
			return buildNotFoundBody();
		} else {
			System.out.println(String.format("Current id is %s", id));
			return ResponseEntity.ok(user.get());
		}
	}

	@GetMapping()
	public String index() {
		return "Greetings from Spring Boot!";
	}

}