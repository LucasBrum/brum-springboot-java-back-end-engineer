package com.brum.client.school.curriculumgrid.core.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.brum.client.school.curriculumgrid.entity.User;

public class ResourceOwner implements UserDetails{

	private User user;
	
	public ResourceOwner(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> roles = new ArrayList<>();
		
		roles.add(new SimpleGrantedAuthority(this.user.getRole()));
		
		return roles;
	}

	@Override
	public String getPassword() {
		return this.user.getCredential().getPassword();
	}

	@Override
	public String getUsername() {
		return this.user.getCredential().getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
