package com.example.ejemplojwt.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ejemplojwt.dto.LoginDto;
import com.example.ejemplojwt.dto.SignupDto;
import com.example.ejemplojwt.dto.UsuarioJwt;
import com.example.ejemplojwt.model.Rol;
import com.example.ejemplojwt.model.Usuario;
import com.example.ejemplojwt.repository.UserRepository;
import com.example.ejemplojwt.security.JwtUtils;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String jwt = jwtUtils.generateJwtToken(authentication);
    Usuario usuario = userRepository.findByUsername(userDetails.getUsername());
    return ResponseEntity.ok()
        .body(new UsuarioJwt(jwt, "Bearer", usuario.getId(), usuario.getUsername(), usuario.getEmail(),
            usuario.getRol().toString()));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupDto signupDto) {
    if (userRepository.existsByUsername(signupDto.getUsername())) {
      return ResponseEntity.badRequest().body("Error: Ya existe ese nombre de usuario!");
    }

    if (userRepository.existsByEmail(signupDto.getEmail())) {
      return ResponseEntity.badRequest().body("Error: Ya existe esa dirección de email!");
    }

    Rol userRol;
    try {
      userRol = Rol.valueOf(signupDto.getRol());
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body("Error: Nombre de rol incorrecto!");
    }
    Usuario user = new Usuario(signupDto.getUsername(),
        encoder.encode(signupDto.getPassword()),
        signupDto.getEmail(),
        userRol);
    userRepository.save(user);

    return ResponseEntity.ok("User registered successfully!");
  }
}
