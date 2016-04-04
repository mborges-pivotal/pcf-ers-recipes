package io.pivotal.pcf.ers.recipes.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "id")
public class Person {
	
	private Long id;
	
	private String name;
	private String emailAddress;
	private String password;

}
