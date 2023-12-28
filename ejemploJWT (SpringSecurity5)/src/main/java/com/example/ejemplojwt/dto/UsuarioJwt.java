package com.example.ejemplojwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioJwt {
	private String token;
	private String type;
	private Long id;
	private String username;
	private String email;
	private String rol;
}
