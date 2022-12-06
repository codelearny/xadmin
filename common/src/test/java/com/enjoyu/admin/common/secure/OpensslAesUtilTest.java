package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class OpensslAesUtilTest {

    @Test
    void testPbkdf2() throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        String password = "skdfiwneflksjlkf&^F&3fwb";
        byte[] saltBytes = PBEUtil.randomSalt(8);
        System.out.println(EncodeUtil.bytes2Hex(saltBytes));
        byte[] bytes1 = OpensslAesUtil.keyDerivationAlgorithm(password, saltBytes, OpensslAesUtil.HASH_ITERATIONS);
        byte[] bytes2 = OpensslAesUtil.pbkdf2(password.getBytes(StandardCharsets.UTF_8), saltBytes, OpensslAesUtil.HASH_ITERATIONS, OpensslAesUtil.KEY_LENGTH);
        Assertions.assertArrayEquals(bytes1, bytes2);
        System.out.println(EncodeUtil.bytes2Hex(bytes1));
        System.out.println(bytes1.length);
    }
}