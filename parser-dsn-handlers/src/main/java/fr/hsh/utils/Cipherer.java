package fr.hsh.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class Cipherer {

	private static final String wrappedKey="039E68A355057012D107B388071C742A3A7B10ABFBBEB0BE";
	private static final String pwd ="toto";
	private static final byte[] salt = "my8lSalt".getBytes();

	private final Key aesKey;

	public Cipherer(final String pPassword, final byte[] pSalt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		this.aesKey = getAesKey(pPassword, pSalt);
	}

	private static Key getAesKey(final String pPassword, final byte[] pSalt) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
		String TRANSFORMATION_TYPE = "PBEWithMD5AndDES";

		// generation de la clef enveloppante
		SecretKeyFactory kf = SecretKeyFactory.getInstance(TRANSFORMATION_TYPE);
		KeySpec keySpec = new PBEKeySpec(pPassword.toCharArray());
		Key clePourChiffrer = kf.generateSecret(keySpec);
		//			System.out.println("cle de chiffre: " + bytesToHex(clePourChiffrer.getEncoded()));

		// generation du chiffreur de l'enveloppe
		AlgorithmParameterSpec params = new PBEParameterSpec(salt, 1000);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION_TYPE);
		cipher.init(Cipher.UNWRAP_MODE, clePourChiffrer, params);

		// unwrap de la clef
		Key key = cipher.unwrap(hexToBytes(wrappedKey), "AES", Cipher.SECRET_KEY);
		//			System.out.println("cle unwrapped: " + bytesToHex(key.getEncoded()));
		return key;
	}

	public String aesEncode(final String pClearMsg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, this.aesKey);
		String encryptedMsg = bytesToHex(cipher.doFinal(pClearMsg.getBytes()));
		return encryptedMsg;
	}

	public String aesDecode(final String pCrytpedMsg) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, this.aesKey);
		String cleardMsg = new String(cipher.doFinal(hexToBytes(pCrytpedMsg)));
		return cleardMsg;
	}

	public static String bytesToHex(byte[] b) {
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		StringBuilder buf = new StringBuilder();
		for (byte lElement : b) {
			buf.append(hexDigit[(lElement >> 4) & 0x0f]);
			buf.append(hexDigit[lElement & 0x0f]);
		}
		return buf.toString();
	}

	public static byte[] hexToBytes(String h) {
		byte[] out = new byte[h.length()/2];
		char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A',
				'B', 'C', 'D', 'E', 'F' };
		for (int i = 0; i < h.length(); i+=2) {
			char firstLetter = h.charAt(i);
			char secondLetter = h.charAt(i+1);
			int firstDigit;
			int secondDigit;
			for (firstDigit = 0; firstDigit < hexDigit.length; firstDigit++) {
				if (hexDigit[firstDigit] == firstLetter) {
					break;
				}
			}
			for (secondDigit = 0; secondDigit < hexDigit.length; secondDigit++) {
				if (hexDigit[secondDigit] == secondLetter) {
					break;
				}
			}
			byte toByte = (byte) ((firstDigit << 4) | secondDigit);
			out[i/2] = toByte;
		}
		return out;
	}

	public static void main(String[] args) {
		try {
			getAesKey(pwd, salt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String msg = "Foo Bar open";
		System.out.println("Message: "+msg);
		Cipherer ciph;
		try {
			ciph = new Cipherer(pwd, salt);
			msg = ciph.aesEncode(msg);
			System.out.println("Encoded Msg: "+msg);
			msg = ciph.aesDecode(msg);
			System.out.println("Decoded Msg: "+msg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
