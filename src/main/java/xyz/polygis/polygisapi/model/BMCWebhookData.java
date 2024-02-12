package xyz.polygis.polygisapi.model;
// import java.io.Serializable;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Table;

// @Entity
// @Table(name = "extension-users")
public class BMCWebhookData {
	String type;
	Boolean live_mode;
	Integer attempt;
	Integer created;
	Integer event_id;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean isLive_mode() {
		return this.live_mode;
	}

	public Boolean getLive_mode() {
		return this.live_mode;
	}

	public void setLive_mode(Boolean live_mode) {
		this.live_mode = live_mode;
	}

	public Integer getAttempt() {
		return this.attempt;
	}

	public void setAttempt(Integer attempt) {
		this.attempt = attempt;
	}

	public Integer getCreated() {
		return this.created;
	}

	public void setCreated(Integer created) {
		this.created = created;
	}

	public Integer getEvent_id() {
		return this.event_id;
	}

	public void setEvent_id(Integer event_id) {
		this.event_id = event_id;
	}

}
