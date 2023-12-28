package com.example.ejemplojwt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ejemplojwt.model.Usuario;
import com.example.ejemplojwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = userRepository.findByUsername(username);
    if (usuario == null)
      throw (new UsernameNotFoundException("User Not Found with username: " + username));
    return User
        .withUsername(username)
        .roles(usuario.getRol().toString())
        .password(usuario.getPassword())
        .build();
  }
}
