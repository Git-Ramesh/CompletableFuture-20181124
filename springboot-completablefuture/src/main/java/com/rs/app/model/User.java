package com.rs.app.model;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	private static final long serialVersionUID = -8544062217539925932L;
	private long id;
	private String name;
	private String email;
	private LocalDate dateOfBirth;
	private String address;
	
}
