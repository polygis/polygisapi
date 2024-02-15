package xyz.polygis.polygisapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.bmc.secret")
public class ApplicationPropertiesConfig {

	private String bmcWebHookSecret;

	public String getBmcWebHookSecret() {
		return this.bmcWebHookSecret;
	}

	public void setBmcWebHookSecret(String bmcWebHookSecret) {
		this.bmcWebHookSecret = bmcWebHookSecret;
	}
}
