package ru.paskal.MantisManager.security.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.paskal.MantisManager.services.UserService;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserService service;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = service.getOne(username).orElseThrow(() -> new UsernameNotFoundException("user with username " + username + " not found"));
    return UserPrincipal.builder()
        .email(user.getEmail())
        .username(username)
        .id(user.getId())
        .authorities(List.of(new SimpleGrantedAuthority(user.getSimpleRole())))
        .password(user.getPassword())
        .build();
  }
}
