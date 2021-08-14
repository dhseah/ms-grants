package com.example.msgrants;

import com.example.msgrants.controller.GrantDisbursementController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MsGrantsApplicationTests {

	@Autowired
	private GrantDisbursementController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
