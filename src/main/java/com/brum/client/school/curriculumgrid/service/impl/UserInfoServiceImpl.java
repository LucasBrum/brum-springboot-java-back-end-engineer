package com.brum.client.school.curriculumgrid.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.core.impl.ResourceOwner;
import com.brum.client.school.curriculumgrid.entity.User;
import com.brum.client.school.curriculumgrid.enums.ExceptionMessageEnum;
import com.brum.client.school.curriculumgrid.repository.UserRepository;
import com.brum.client.school.curriculumgrid.service.UserInfoService;

@Service
public class UserInfoServiceImpl implements UserDetailsService, UserInfoService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> user = this.userRepository.findByEmail(email);

		if (user.isPresent()) {
			UserDetails userDetails = new ResourceOwner(user.get());
			return userDetails;
		} else {
			throw new UsernameNotFoundException(ExceptionMessageEnum.USERNAME_NOTFOUND.getValue());
		}
	}
	
	public User getAuthenticatedUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> user = this.userRepository.findByEmail(auth.getName());

		if (!user.isPresent()) {
			throw new Exception();
		}
		return user.get();
	}
}
