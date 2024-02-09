package xyz.polygis.polygisapi.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "extension-users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	public static enum Membership {
		ACTIVE("ACTIVE"), DISABLED("DISABLED"), FREEMIUM("FREEMIUM");

		private final String status;

		Membership(String status) {
			this.status = status;
		}

		public String isCommon() {
			return status;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "chromeId", nullable = false)
	private String chromeId;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "membership", nullable = false, updatable = true)
	@Enumerated(EnumType.STRING)
	private Membership membership;

	// for JPA only, no use
	public void User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChromeId() {
		return this.chromeId;
	}

	public void setChromeId(String chromeId) {
		this.chromeId = chromeId;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Membership getMembership() {
		return this.membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

}
