package com.enjoyu.admin.common.secure;

import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import static com.enjoyu.admin.common.secure.HmacUtil.HMAC_SHA256;

/**
 * openssl 的密码加密流程
 * #include <openssl/evp.h>
 * <p>
 * int EVP_BytesToKey(const EVP_CIPHER *type, const EVP_MD *md,
 * const unsigned char *salt,
 * const unsigned char *data, int datal, int count,
 * unsigned char *key, unsigned char *iv);
 * <p>
 * EVP_BytesToKey() derives a key and IV from various parameters. type is the cipher to derive the key and IV for. md is the message digest to use. The salt parameter is used as a salt in the derivation: it should point to an 8 byte buffer or NULL if no salt is used. data is a buffer containing datal bytes which is used to derive the keying data. count is the iteration count to use. The derived key and IV will be written to key and iv respectively.
 * A typical application of this function is to derive keying material for an encryption algorithm from a password in the data parameter.
 * Increasing the count parameter slows down the algorithm which makes it harder for an attacker to perform a brute force attack using a large number of candidate passwords.
 * If the total key and IV length is less than the digest length and MD5 is used then the derivation algorithm is compatible with PKCS#5 v1.5 otherwise a non standard extension is used to derive the extra data.
 * Newer applications should use a more modern algorithm such as PBKDF2 as defined in PKCS#5v2.1 and provided by PKCS5_PBKDF2_HMAC.
 * <p>
 * 密钥生成算法
 * The key and IV is derived by concatenating D_1, D_2, etc until enough data is available for the key and IV. D_i is defined as:
 * D_i = HASH^count(D_(i-1) || data || salt)
 * where || denotes concatenation, D_0 is empty, HASH is the digest algorithm in use, HASH^1(data) is simply HASH(data), HASH^2(data) is HASH(HASH(data)) and so on.
 * <p>
 * The initial bytes are used for the key and the subsequent bytes for the IV.
 */
public class OpensslAesUtil {
    public static final String PKCS5_PBKDF2_HMAC_SHA256 = "PBKDF2WithHmacSHA256";
    public static final int HASH_ITERATIONS = 10000;
    public static final int KEY_LENGTH = 32;
    public static final int KEY_BITS = KEY_LENGTH << 3;


    public static byte[] pbkdf2(byte[] pass, byte[] salt, int iter, int len) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] result = new byte[len];
        Mac mac = Mac.getInstance(HMAC_SHA256);
        mac.init(new SecretKeySpec(pass, HMAC_SHA256));
        byte[] saltcnt = Arrays.copyOf(salt, salt.length + 4);
        while (len > 0) {
            for (int o = saltcnt.length; --o >= saltcnt.length - 4; ) {
                if (++saltcnt[o] != 0) {
                    break;
                }
            }
            int macLength = mac.getMacLength();
            byte[] u = saltcnt, x = new byte[macLength];
            for (int i = 1; i <= iter; i++) {
                u = mac.doFinal(u);
                for (int o = 0; o < x.length; o++) {
                    x[o] ^= u[o];
                }
            }
            int len2 = Math.min(len, x.length);
            System.arraycopy(x, 0, result, result.length - len, len2);
            len -= len2;
        }
        return result;
    }

    public static byte[] keyDerivationAlgorithm(String password, byte[] saltBytes, int iterations) throws NoSuchAlgorithmException, InvalidKeySpecException {
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray(), saltBytes, iterations, KEY_BITS);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PKCS5_PBKDF2_HMAC_SHA256);
        return keyFactory.generateSecret(pbeKeySpec).getEncoded();
    }


}
