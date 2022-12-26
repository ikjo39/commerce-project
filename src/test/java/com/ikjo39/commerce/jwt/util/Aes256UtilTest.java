package com.ikjo39.commerce.jwt.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.ikjo39.commerce.auth.util.Aes256Util;

class Aes256UtilTest {
	@Test
	void encrypt() {
		// given
		String encrypt = Aes256Util.encrypt("Hello World");
		// when
		// then
		assertEquals(Aes256Util.decrypt(encrypt), "Hello World");
	}
}