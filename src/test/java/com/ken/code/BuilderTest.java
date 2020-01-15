package com.ken.code;

import com.ken.domain.User;
import com.ken.utils.Builder;
import org.junit.Test;

public class BuilderTest {

	@Test
	public void test() {

		User userInfo = Builder.of(User::new)
				.with(User::setName, "ken")
				.with(User::setCode, "K")
				.with(User::setRoleId, 1)
				.build();
		System.out.println(userInfo);
	}
}
