package com.rumblrs.admin.web.rest.dto;

import org.hibernate.validator.constraints.Email;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.util.List;

public class UserDTO {

	public static final int PASSWORD_MIN_LENGTH = 5;
	public static final int PASSWORD_MAX_LENGTH = 100;

	@Pattern(regexp = "^[a-z0-9]*$")
	@NotNull
	@Size(min = 1, max = 50)
	private String login;

	@NotNull
	@Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
	@JsonIgnore
	private String password;

	@Size(max = 50)
	private String firstName;

	@Size(max = 50)
	private String lastName;

	@Email
	@Size(min = 5, max = 100)
	private String email;

	@Size(min = 2, max = 5)
	private String langKey;

	private List<String> roles;

	@Pattern(regexp = "[0-9]{10}")
	@Size(min = 10, max = 10)
	@NotNull
	private String mobileNo;

	private String landmark;

	private String address;

	public UserDTO() {
	}

	public UserDTO(String login, String password, String firstName, String lastName, String email, String mobileNo,
			String langKey, List<String> roles) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNo = mobileNo;
		this.langKey = langKey;
		this.roles = roles;
	}

	public String getPassword() {
		return password;
	}

	public String getLogin() {
		return login;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		if (!StringUtils.isEmpty(name)) {
			String[] names = name.split("\\s", 2);
			if (this.firstName == null && this.lastName == null) {
				this.firstName = names[0];
				this.lastName = names[1];
			}
		}
	}

	public String getEmail() {
		return email;
	}

	public String getMobileNo() {
		return this.mobileNo;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLangKey() {
		return langKey;
	}

	public List<String> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "UserDTO{" + "login='" + login + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName
				+ '\'' + ", email='" + email + '\'' + ", langKey='" + langKey + '\'' + ", roles=" + roles + '}';
	}

}
