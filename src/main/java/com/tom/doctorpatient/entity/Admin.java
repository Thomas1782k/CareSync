package com.tom.doctorpatient.entity;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Entity;

@Data
@Getter
@Setter
@Entity
public class Admin {

	@Id
	private String userId;
	private String password;
	private String name;
}
