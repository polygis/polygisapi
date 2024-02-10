package xyz.polygis.polygisapi.dto;

import lombok.Builder;
import lombok.Value;

/**
 * DTO representing a generic API error response - similar to the default
 * error message provided by the framework
 */

@Value
@Builder
public class ErrorMessage {

	private final int status;

	private final String error;

	private final String message;

}
