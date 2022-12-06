package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;


class DESUtilTest {

    @Test
    public void testDes() throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
        String password = "凉州词二首·其一";
        String message = "黄河远上白云间，一片孤城万仞山。羌笛何须怨杨柳，春风不度玉门关。";
        byte[] encrypt = DESUtil.desEcbEncrypt(message.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8));
        System.out.println(EncodeUtil.base64Encode(encrypt));
        byte[] decrypt = DESUtil.desEcbDecrypt(encrypt, password.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decrypt, StandardCharsets.UTF_8));
        byte[] encrypt2 = DESUtil.desCbcEncrypt(message.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8));
        System.out.println(EncodeUtil.base64Encode(encrypt2));
        byte[] decrypt2 = DESUtil.desCbcDecrypt(encrypt2, password.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decrypt2, StandardCharsets.UTF_8));

    }

    @Test
    public void testEes3() throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException {
        String password = "蜀相";
        String message = "丞相祠堂何处寻？锦官城外柏森森。映阶碧草自春色，隔叶黄鹂空好音。三顾频烦天下计，两朝开济老臣心。出师未捷身先死，长使英雄泪满襟。";
        byte[] encrypt = DESUtil.des3EcbEncrypt(message.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8));
        System.out.println(EncodeUtil.base64Encode(encrypt));
        byte[] decrypt = DESUtil.des3EcbDecrypt(encrypt, password.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decrypt));
        byte[] encrypt2 = DESUtil.des3CbcEncrypt(message.getBytes(StandardCharsets.UTF_8), password.getBytes(StandardCharsets.UTF_8));
        System.out.println(EncodeUtil.base64Encode(encrypt2));
        byte[] decrypt2 = DESUtil.des3CbcDecrypt(encrypt2, password.getBytes(StandardCharsets.UTF_8));
        System.out.println(new String(decrypt2));
    }

}