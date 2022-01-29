package com.ktinsta.server

import com.ktinsta.server.controllers.AuthController
import com.ktinsta.server.controllers.PostController
import com.ktinsta.server.controllers.UserController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class `Base test` {

	@Autowired
	private val authController: AuthController? = null
	@Autowired
	private val postController: PostController? = null
	@Autowired
	private val userController: UserController? = null

	@Test
	fun `Find all controllers`() {
		assertThat(authController).isNotNull
		assertThat(postController).isNotNull
		assertThat(userController).isNotNull
	}
}
