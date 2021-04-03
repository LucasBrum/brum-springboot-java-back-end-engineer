package com.brum.client.school.curriculumgrid.service;

import com.brum.client.school.curriculumgrid.entity.User;

public interface UserInfoService {

	User getAuthenticatedUser() throws Exception;
}
