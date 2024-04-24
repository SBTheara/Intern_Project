package com.intern.project.service;

import com.intern.project.entity.User;
import com.intern.project.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

  private final UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> userOptional = usersRepository.findByEmail(email);
    if (userOptional.isEmpty()) {
      throw new UsernameNotFoundException("User not exists");
    }

    User user = userOptional.get();
    List<String> roles = new ArrayList<>();
    roles.add("USER");
    return org.springframework.security.core.userdetails.User.builder()
        .username(user.getEmail())
        .password(user.getPassword())
        .roles(roles.toArray(new String[0]))
        .build();
  }
}
