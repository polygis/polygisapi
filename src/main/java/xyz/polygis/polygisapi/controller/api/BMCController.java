package xyz.polygis.polygisapi.controller.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityExistsException;
import xyz.polygis.polygisapi.model.BMCWebhookData;
import xyz.polygis.polygisapi.model.User;
import xyz.polygis.polygisapi.service.UserService;

import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/public/api/v1/bmc")
public class BMCController extends BaseController {

	@Autowired
	private UserService userService;

	public String requestMethodName() {
		return "Greetings from Spring Boot! Api v1";
	}

	@ResponseStatus(HttpStatus.CREATED) // 201
	@PostMapping("/users")
	public ResponseEntity<?> create(@RequestBody User user) throws EntityExistsException {
		try {
			return ResponseEntity.status(201).body(userService.save(user));
		} catch (Exception e) {
			return buildBadRequest();
		}
	}

	@PatchMapping(value = "/users/{id}")
	public ResponseEntity<?> updateMemberShip(@RequestBody String membership, @PathVariable("id") Long id) {

		Optional<User> user = userService.update(id, membership);
		if (user.isEmpty()) {
			return buildNotFoundBody();
		} else {
			return ResponseEntity.ok(user.get());
		}
	}

	@GetMapping("/")
	public ResponseEntity<Object> index() {
		return ResponseEntity.status(418).body(null);
	}

	@PostMapping("/")
	public ResponseEntity<Object> donationCreated(@RequestBody BMCWebhookData bmcData) {
		BMCWebhookData bd = bmcData;
		return ResponseEntity.status(200).body(null);
	}

}