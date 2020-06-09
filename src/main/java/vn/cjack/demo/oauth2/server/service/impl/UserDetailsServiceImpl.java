package vn.cjack.demo.oauth2.server.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.cjack.demo.oauth2.server.model.CustomUserDetails;
import vn.cjack.demo.oauth2.server.repository.UserRepository;
import vn.cjack.demo.oauth2.server.repository.entity.User;

import java.util.*;

@Service("userDetailsServiceImpl")
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("(loadUserByUsername)");
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(!userOpt.isPresent()) throw new UsernameNotFoundException("User not found");
        List<String> roles = Arrays.asList("ROLE_ADMIN");
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new CustomUserDetails(userOpt.get(), roles);

    }
}
