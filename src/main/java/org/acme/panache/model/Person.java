package org.acme.panache.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Person extends PanacheEntity {

	public static enum MartialStatus {
		MARRIED,SINGLE;
	};
	
	@NotEmpty(message = "Name required")
	public String name;
	@NotNull(message = "birth required")
	public LocalDate birth;
	public MartialStatus martialStatus;
}
