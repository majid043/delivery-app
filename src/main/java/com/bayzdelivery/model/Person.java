package com.bayzdelivery.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.bayzdelivery.enums.PersonType;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "person")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

	private static final long serialVersionUID = 432154291451321L;

	public Person() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "name")
	String name;

	@NotNull
	@Email
	@Column(name = "email")
	String email;

	@Column(name = "registration_number")
	String registrationNumber;

	@NotNull
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	PersonType type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public PersonType getType() {
		return type;
	}

	public void setType(PersonType type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, id, name, registrationNumber, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(email, other.email) && Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(registrationNumber, other.registrationNumber) && type == other.type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Person [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", registrationNumber=");
		builder.append(registrationNumber);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

}