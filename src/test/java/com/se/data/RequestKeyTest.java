package com.se.data;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.se.data.InvalidKeyFormatException;
import com.se.data.RequestKey;

public class RequestKeyTest {

	@Test
	public void toStringReturnsExpectedString() {
		RequestKey key1 = new RequestKey(1001L);
		assertThat(key1.toString()).isEqualTo("1001");
		RequestKey key2 = new RequestKey(10001L, "sam");
		assertThat(key2.toString()).isEqualTo("10001/sam");
	}
	
	@Test
	public void parseKeyHandlesKeyFormat() {
		RequestKey key1 = RequestKey.parseKey("1001");
		assertThat(key1.getId()).isEqualTo(1001L);
		RequestKey key2 = RequestKey.parseKey("10001/sam");
		assertThat(key2.getId()).isEqualTo(10001L);
		assertThat(key2.getTag()).isEqualTo("sam");
	}
	
	@Test
	public void parseKeyHandlesEmptyFormat() {
		try {
			RequestKey.parseKey("");
			Assertions.fail("Expected InvalidKeyFormatException");
		}
		catch (InvalidKeyFormatException e) {
			// expected
		}
	}

	@Test
	public void parseKeyHandlesTooManyTokens() {
		try {
			RequestKey.parseKey("101/foo/bar");
			Assertions.fail("Expected InvalidKeyFormatException");
		}
		catch (InvalidKeyFormatException e) {
			// expected
		}
	}
		
	@Test
	public void parseKeyHandlesNonNumericId() {
		// non-numeric id
		try {
			RequestKey.parseKey("foo/bar");
			Assertions.fail("Expected InvalidKeyFormatException");
		}
		catch (InvalidKeyFormatException e) {
			// expected
		}
	}
}
