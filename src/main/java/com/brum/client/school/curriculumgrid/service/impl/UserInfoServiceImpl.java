package com.brum.client.school.curriculumgrid.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.brum.client.school.curriculumgrid.core.impl.ResourceOwner;
import com.brum.client.school.curriculumgrid.entity.User;
import com.brum.client.school.curriculumgrid.enums.ExceptionMessageEnum;
import com.brum.client.school.curriculumgrid.repository.UserRepository;

@Service
public class UserInfoServiceImpl implements UserDetailsService {

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
}
