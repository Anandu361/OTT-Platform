package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.backend.models.UserModel;
import com.example.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetail(user);
    }
    
    public boolean updatePassword(UserModel user, String oldPass, String newPass) {

        if (!passwordEncoder.matches(oldPass, user.getPassword())) {
            return false;
        }

        user.setPassword(passwordEncoder.encode(newPass));
        userRepository.save(user);

        return true;
    }

	public UserModel getUser() {
		// TODO Auto-generated method stub
		return null;
	}

}