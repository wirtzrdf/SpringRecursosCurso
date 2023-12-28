package com.example.ejemplojwt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.ejemplojwt.model.Rol;
import com.example.ejemplojwt.model.Usuario;
import com.example.ejemplojwt.repository.UserRepository;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner initData(
			UserRepository userRepository, PasswordEncoder encoder) {
		return args -> {
			Usuario usuario = new Usuario("admin", encoder.encode("1234"), "admin@gmail.com", Rol.ADMIN);
			userRepository.save(usuario);
		};
	}

}
