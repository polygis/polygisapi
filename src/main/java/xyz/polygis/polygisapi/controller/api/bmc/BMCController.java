package xyz.polygis.polygisapi.controller.api.bmc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.polygis.polygisapi.controller.api.BaseController;

// import xyz.polygis.polygisapi.model.BMCWebhookData;

@RestController
@RequestMapping("/api/v1/bmc")
public class BMCController extends BaseController {

	@Value("${app.bmc.secret}")
	String hmacSecret;

	// @Autowired
	// private UserService userService;

	@PostMapping()
	// @CheckHmac("app.bmc.secret")
	public ResponseEntity<Object> donationCreated(@RequestBody String bmcData) {
		return ResponseEntity.status(200).body("OK");
	}
}