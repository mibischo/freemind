package freemind.encryption;

import java.io.UnsupportedEncodingException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import freemind.tools.Base64Coding;

public abstract class DesEncrypter implements IDesEncrypter {

	private static final String SALT_PRESENT_INDICATOR = " ";
	private static final int SALT_LENGTH = 8;

	Cipher ecipher;

	Cipher dcipher;

	// 8-byte default Salt
	byte[] salt = { (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
			(byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03 };

	// Iteration count
	int iterationCount = 19;

	private final char[] passPhrase;
	private String mAlgorithm;

	public DesEncrypter(StringBuffer pPassPhrase, String pAlgorithm) {
		passPhrase = new char[pPassPhrase.length()];
		pPassPhrase.getChars(0, passPhrase.length, passPhrase, 0);
		mAlgorithm = pAlgorithm;
	}

	/**
	 */
	private void init(byte[] mSalt) {
		if (mSalt != null) {
			this.salt = mSalt;
		}
		if (ecipher == null) {
			try {
				// Create the key
				KeySpec keySpec = new PBEKeySpec(passPhrase, salt,
						iterationCount);
				SecretKey key = SecretKeyFactory.getInstance(mAlgorithm)
						.generateSecret(keySpec);
				ecipher = Cipher.getInstance(mAlgorithm);
				dcipher = Cipher.getInstance(mAlgorithm);

				// Prepare the parameter to the ciphers
				AlgorithmParameterSpec paramSpec = new PBEParameterSpec(
						salt, iterationCount);

				// Create the ciphers
				ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
				dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			} catch (java.security.InvalidAlgorithmParameterException e) {
			} catch (java.security.spec.InvalidKeySpecException e) {
			} catch (javax.crypto.NoSuchPaddingException e) {
			} catch (java.security.NoSuchAlgorithmException e) {
			} catch (java.security.InvalidKeyException e) {
			}
		}
	}

	public String encrypt(String str) {
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// determine salt by random:
			byte[] newSalt = new byte[SALT_LENGTH];
			for (int i = 0; i < newSalt.length; i++) {
				newSalt[i] = (byte) (Math.random() * 256l - 128l);
			}

			init(newSalt);
			// Encrypt
			byte[] enc = ecipher.doFinal(utf8);

			// Encode bytes to base64 to get a string
			return Base64Coding.encode64(newSalt) + SALT_PRESENT_INDICATOR
					+ Base64Coding.encode64(enc);
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}

	public String decrypt(String str) {
		if (str == null) {
			return null;
		}
		try {
			byte[] salt = null;
			// test if salt exists:
			int indexOfSaltIndicator = str.indexOf(SALT_PRESENT_INDICATOR);
			if (indexOfSaltIndicator >= 0) {
				String saltString = str.substring(0, indexOfSaltIndicator);
				str = str.substring(indexOfSaltIndicator + 1);
				salt = Base64Coding.decode64(saltString);
			}
			// Decode base64 to get bytes
			str = str.replaceAll("\\s", "");
			byte[] dec = Base64Coding.decode64(str);
			init(salt);

			// Decrypt
			byte[] utf8 = dcipher.doFinal(dec);

			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null;
	}
}
