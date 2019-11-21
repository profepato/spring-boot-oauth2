package com.example.demo.service;

import com.example.demo.model.AuthUserDetail;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("Username or password wrong"));

        UserDetails userDetails = new AuthUserDetail(optionalUser.get());

        //new AccountStatusUserDetailsChecker().check(userDetails);

        return userDetails;
    }
}
