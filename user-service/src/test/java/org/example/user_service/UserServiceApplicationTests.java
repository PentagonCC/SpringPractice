package org.example.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}

    @Test
    void main_startApplication() {
        UserServiceApplication.main(new String[] {});
    }

}
