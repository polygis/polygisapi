package xyz.polygis.polygisapi.config.security;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

@RestControllerAdvice("xyz.polygis.polygisapi.controller.api.bmc")
@Component
@SuppressWarnings("null")
public class BmcControllerAdvice implements RequestBodyAdvice {
	@Value("${app.bmc.signature-header}")
	private String SIGNATURE_HEADER_NAME;

	@Value("${app.bmc.secret}")
	private String BMC_HMAC_SECRET;

	BmcControllerAdvice() {

	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<Object> handleAccessDeniedException(
			Exception ex, WebRequest request) {
		return new ResponseEntity<Object>(
				ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		System.out.println("Run into supports method");
		return true;
	}

	@Override
	public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter,
			Type targetType, Class<? extends HttpMessageConverter<?>> converterType)
			throws IOException {
		System.out.println("Run into beforeBodyRead method");
		return inputMessage;
	}

	@Override
	public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
			MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		String hmacSignature = inputMessage.getHeaders().getFirst(SIGNATURE_HEADER_NAME);
		System.out.println("Run into afterBodyRead method");
		try {
			if (!getHmac((String) body, BMC_HMAC_SECRET).equals(hmacSignature)) {
				throw new AccessDeniedException("HMAC Not match");
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new AccessDeniedException("HMAC Not match");
		}
		return body;
	}

	@Override
	public Object handleEmptyBody(Object body, HttpInputMessage inputMessage,
			MethodParameter parameter, Type targetType,
			Class<? extends HttpMessageConverter<?>> converterType) {
		System.out.println("Run into handleEmptyBody method");
		return body;
	}

	/**
	 * This method calculates the HMAC (Hash-based Message Authentication Code)
	 * using SHA-256 algorithm.
	 * It takes the data and a secret key as input and returns the calculated HMAC
	 * as a hexadecimal string.
	 *
	 * @param data      The data for which HMAC needs to be calculated.
	 * @param secretKey The secret key used for HMAC calculation.
	 * @return The HMAC calculated for the given data and secret key.
	 * @throws NoSuchAlgorithmException If the specified algorithm (SHA-256) for
	 *                                  HMAC calculation is not available.
	 * @throws InvalidKeyException      If the provided secret key is invalid.
	 */
	public String getHmac(String data, String secretKey)
			throws NoSuchAlgorithmException, InvalidKeyException {
		String hmac = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey).hmacHex(data);
		return hmac;
	}
}
