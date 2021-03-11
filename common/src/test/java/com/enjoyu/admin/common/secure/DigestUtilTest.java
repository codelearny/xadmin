package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

import static com.enjoyu.admin.common.secure.DigestUtil.*;

public class DigestUtilTest {

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        String message = "MD5算法";
        byte[] digest = DigestUtil.digest(message, MD5);
        System.out.println(EncodeUtil.bytesToHexString(digest));
    }

    @Test
    public void testSHA1() throws NoSuchAlgorithmException {
        String message = "SHA-1算法";
        byte[] digest = DigestUtil.digest(message, SHA_1);
        System.out.println(EncodeUtil.bytesToHexString(digest));
    }

    @Test
    public void testSHA256() throws NoSuchAlgorithmException {
        String message = "SHA-256算法";
        byte[] digest = DigestUtil.digest(message, SHA_256);
        System.out.println(EncodeUtil.bytesToHexString(digest));
    }

}
