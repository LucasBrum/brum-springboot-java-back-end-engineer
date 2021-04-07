package com.brum.client.school.curriculumgrid.test;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CurriculumGridApplicationTests {
	
	@Autowired
	private PasswordEncoder pass;

	@Test
	void contextLoads() {
		//generate password
		System.out.println(pass.encode("senha123"));
	}
	
}
