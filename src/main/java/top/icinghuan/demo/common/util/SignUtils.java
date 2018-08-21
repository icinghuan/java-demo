package top.icinghuan.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Slf4j
public abstract class SignUtils {

    private static final String UTF8 = "UTF-8";
    private static final String RSA = "RSA";
    private static final String SHA1_WITH_RSA = "SHA1withRSA";
    private static final String HMAC_WITH_SHA256 = "HmacSHA256";

    public static String generateSign(String payload, PrivateKey privateKey) {
        try {
            byte[] bytes = payload.getBytes(UTF8);
            return generateSign(bytes, privateKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateSign(byte[] bytes, PrivateKey privateKey) {
        try {
            Signature instance = Signature.getInstance(SHA1_WITH_RSA);
            instance.initSign(privateKey);
            instance.update(bytes);
            byte[] signature = instance.sign();
            return Base64.encodeBase64String(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySign(String payload, PublicKey publicKey, String sign) {
        try {
            return verifySign(payload.getBytes(UTF8), publicKey, sign);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifySign(byte[] bytes, PublicKey publicKey, String sign) {
        if (StringUtils.isBlank(sign)) {
            return false;
        }
        try {
            Signature instance = Signature.getInstance(SHA1_WITH_RSA);
            instance.initVerify(publicKey);
            instance.update(bytes);
            byte[] signedBytes = Base64.decodeBase64(sign);
            return instance.verify(signedBytes);
        } catch(Throwable t) {
            log.error("Verify Sign Failed", t);
            return false;
        }
    }

    public static PublicKey getPublicKey(String keyStr) {
        try {
            byte[] bytes = Base64.decodeBase64(keyStr);
            KeySpec keySpec = new X509EncodedKeySpec(bytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKey(String keyStr) {
        try {
            KeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(keyStr));
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey readDerPublicKey(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return readDerPublicKey(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey readDerPublicKey(InputStream fis) {
        byte[] keyBytes = toByteArray(fis);
        return readDerPublicKey(keyBytes);
    }

    public static PublicKey readDerPublicKey(byte[] keyBytes) {
        try {
            KeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(RSA);
            return kf.generatePublic(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PublicKey readPublicKey(String keyString) {
        byte[] bytes = Base64.decodeBase64(keyString);
        return readDerPublicKey(bytes);
    }

    public static PrivateKey readDerPrivateKey(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            return readDerPrivateKey(fis);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey readDerPrivateKey(InputStream in) {
        byte[] keyBytes = toByteArray(in);
        return readDerPrivateKey(keyBytes);
    }

    public static PrivateKey readDerPrivateKey(byte[] keyBytes) {
        try {
            KeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance(RSA);
            return kf.generatePrivate(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] toByteArray(InputStream in) {
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buff = new byte[4096];
            int n = in.read(buff);
            while (n != -1) {
                output.write(buff, 0, n);
                n = in.read(buff);
            }
            in.close();
            return output.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String hmacSHA256Hex(String secret, String value) {
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes("UTF-8"), HMAC_WITH_SHA256);
            Mac mac = Mac.getInstance(HMAC_WITH_SHA256);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(value.getBytes(UTF8));
            return new String(Hex.encodeHex(rawHmac));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
