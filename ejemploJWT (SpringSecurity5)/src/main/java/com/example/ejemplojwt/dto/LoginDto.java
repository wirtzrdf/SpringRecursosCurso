package com.example.ejemplojwt.dto;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginDto {
	@NotBlank
	private String username;

	@NotBlank
	private String password;
}
