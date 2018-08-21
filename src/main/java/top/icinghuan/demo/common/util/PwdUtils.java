package top.icinghuan.demo.common.util;

import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Random;

public abstract class PwdUtils {

    private static final String MAGIC_PREFIX = "EcRPt:";

    //private static PublicKey RSA_PUBLIC_KEY = SignUtils.readDerPublicKey(getInputStream("prod_public_key.der"));
    private static PublicKey RSA_PUBLIC_KEY = new PublicKey() {
        @Override
        public String getAlgorithm() {
            return null;
        }

        @Override
        public String getFormat() {
            return null;
        }

        @Override
        public byte[] getEncoded() {
            return new byte[0];
        }
    };

    public static String decrypt(String pwd) {
        if (pwd == null || !pwd.startsWith(MAGIC_PREFIX)) {
            return pwd;
        }
        String payload = pwd.substring(MAGIC_PREFIX.length());
        return EncryptUtils.rsaDecrypt(payload, RSA_PUBLIC_KEY);
    }

    private static InputStream getInputStream(String s) {
        return PwdUtils.class.getClassLoader().getResourceAsStream(s);
    }

    public static String encrypt(String pwd, String privateKeyFile) {
        PrivateKey privateKey = SignUtils.readDerPrivateKey(new File(privateKeyFile));
        return MAGIC_PREFIX + EncryptUtils.rsaEncrypt(pwd, privateKey);
    }

    /**
     * NOTE: 如果想生成的字符串末尾没有等号填充,请传入3的整数倍的数字. 最终生成的字符串长度为 ((bytes / 3) * 4)
     * @param bytes
     * @return
     */
    public static String genRandomString(int bytes) {
        byte[] array = new byte[bytes];
        new Random().nextBytes(array);
        return Base64.encodeBase64URLSafeString(array);
    }

}
