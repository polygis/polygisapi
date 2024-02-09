package xyz.polygis.polygisapi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.polygis.polygisapi.model.User;
import xyz.polygis.polygisapi.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	@Autowired
	private UserService userService;

	public String requestMethodName() {
		return "Greetings from Spring Boot! Api v1";
	}

	@ResponseStatus(HttpStatus.CREATED) // 201
	@PostMapping("/user")
	public User create(@RequestBody User user) {
		return userService.save(user);
	}

	@GetMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

}