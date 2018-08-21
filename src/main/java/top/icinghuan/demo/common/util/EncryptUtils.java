package top.icinghuan.demo.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.regex.Pattern;

public abstract class EncryptUtils {

    private static final Pattern HEX_PATTERN = Pattern.compile("[0-9a-f]{32}");

    private static final String UTF8 = "utf-8";

    public static String aesEncrypt(byte[] bytes, byte[] key) {
        try {
            if (key.length != 16) {
                throw new Exception("incorrect key len: " + key.length);
            }
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            return Base64.encodeBase64String(cipher.doFinal(bytes));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesEncrypt(byte[] bytes, String hexKey) {
        return aesEncrypt(bytes, hexToBytes(hexKey));
    }

    public static String aesEncrypt(String value, String hexKey) {
        try {
            return aesEncrypt(value.getBytes(UTF8), hexToBytes(hexKey));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesDecrypt(String value, byte[] key) {
        try {
            byte[] data = Base64.decodeBase64(value);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            return new String(cipher.doFinal(data), UTF8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String aesDecrypt(String value, String hexKey) {
        return aesDecrypt(value, hexToBytes(hexKey));
    }

    private static byte[] hexToBytes(String hex) {
        if (hex == null || !HEX_PATTERN.matcher(hex).matches()) {
            throw new RuntimeException("invalid hex key");
        }
        try {
            return Hex.decodeHex(hex.toCharArray());
        } catch (DecoderException e) {
            throw new RuntimeException("invalid hex key string", e);
        }
    }

    public static String rsaEncrypt(String value, PrivateKey key) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(value.getBytes(UTF8));
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String rsaDecrypt(String value, PublicKey key) {
        try {
            byte[] data = Base64.decodeBase64(value);
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(data);
            return new String(bytes, UTF8);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
