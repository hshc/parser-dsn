package fr.hsh.dsn.tests.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.hsh.utils.Cipherer;

public class CiphererTest {

	private Cipherer ciph;
	
	@Before
	public void setUp() throws Exception {
		ciph = new Cipherer("toto", "mySalt".getBytes());
	}

	@After
	public void tearDown() throws Exception {
		ciph = null;
	}

	@Test
	public void testAesEncode() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		assertTrue("aes encode ne fonctionne plus", "99A6253DA78A37C187B925134D881101".equals(ciph.aesEncode("testAesEncode")));
	}

	@Test
	public void testAesDecode() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		assertTrue("aes decode ne fonctionne plus", "testAesDecode".equals(ciph.aesDecode("57659B3645F65A296CBAC514BDA42451")));
	}

}
