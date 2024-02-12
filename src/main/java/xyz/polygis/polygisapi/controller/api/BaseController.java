package xyz.polygis.polygisapi.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import xyz.polygis.polygisapi.dto.ErrorMessage;

/**
 * Super class for all REST API controllers - includes basic common. func.
 */

abstract class BaseController {

	protected ResponseEntity<?> buildSuccessBody(Map<String, ?> data) {
		var body = new HashMap<String, Object>();
		body.putAll(data);
		body.put("status", "ok");
		return ResponseEntity.ok(body);
	}

	protected ResponseEntity<?> buildCreatedBody(Map<String, ?> data) {
		var body = new HashMap<String, Object>();
		body.putAll(data);
		body.put("status", "ok");
		return ResponseEntity.status(HttpStatus.CREATED).body(body);
	}

	protected ResponseEntity<?> buildNotFoundBody() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(ErrorMessage.builder()
						.status(404)
						.error("Not Found")
						.message("Not Found")
						.build());
	}

	protected ResponseEntity<?> buildDuplicateEntry() {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(ErrorMessage.builder()
						.status(409)
						.error("Duplicate key")
						.message("Duplicate entry on column")
						.build());
	}

	protected ResponseEntity<?> buildBadRequest() {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(ErrorMessage.builder()
						.status(400)
						.error("Bad request")
						.message("Bad request")
						.build());
	}

}
