package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;

public class RSAUtilTest {
    @Test
    public void sign() throws Exception {
        String data = "岐王宅里寻常见，崔九堂前几度闻。正是江南好风景，落花时节又逢君。";
        System.out.printf("data: %s%n", data);
        RSAUtil.RsaKeyPair rsaKeyPair = RSAUtil.initKey();
        String sign = RSAUtil.sign(data, rsaKeyPair.getPrivateKeyBase64());
        System.out.printf("sign: %s%n", sign);
        boolean verify = RSAUtil.verify(data, sign, rsaKeyPair.getPublicKeyBase64());
        Assertions.assertTrue(verify);
    }

    @Test
    public void encrypt() throws Exception {
        String data = "青山隐隐水迢迢，秋尽江南草未凋。二十四桥明月夜，玉人何处教吹箫？";
        System.out.printf("data: %s%n", data);
        RSAUtil.RsaKeyPair rsaKeyPair = RSAUtil.initKey();
        String encrypt = RSAUtil.encrypt(data, rsaKeyPair.getPublicKeyBase64());
        System.out.printf("encrypt: %s%n", encrypt);
        String decrypt = RSAUtil.decrypt(encrypt, rsaKeyPair.getPrivateKeyBase64());
        System.out.printf("decrypt: %s%n", decrypt);
        Assertions.assertEquals(data, decrypt);
    }

    @Test
    public void encryptBase64() throws Exception {
        String data = "烟笼寒水月笼沙，夜泊秦淮近酒家。商女不知亡国恨，隔江犹唱后庭花。";
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        System.out.printf("dataBytes: %s%n", Arrays.toString(dataBytes));
        RSAUtil.RsaKeyPair rsaKeyPair = RSAUtil.initKey();
        byte[] encryptBytes = RSAUtil.encrypt(dataBytes, rsaKeyPair.getPublicKey());
        System.out.printf("encryptBytes: %s%n", Arrays.toString(encryptBytes));
        byte[] decryptBytes = RSAUtil.decrypt(encryptBytes, rsaKeyPair.getPrivateKey());
        System.out.printf("decryptBytes: %s%n", Arrays.toString(decryptBytes));
        Assertions.assertArrayEquals(dataBytes, decryptBytes);
    }

    @Test
    public void certSign() throws Exception {
        String data = "君问归期未有期，巴山夜雨涨秋池。何当共剪西窗烛，却话巴山夜雨时。";
        System.out.printf("data: %s%n", data);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        KeyStore keyStore = RSAUtil.loadKeyStore(RSAUtil.KEY_STORE_FILE, RSAUtil.STOREPASS);
        System.out.printf("keyStore: %s%n", keyStore);
        PrivateKey privateKey = RSAUtil.loadPrivateKey(keyStore, RSAUtil.ALIAS, RSAUtil.STOREPASS);
        X509Certificate cert = RSAUtil.loadCert(keyStore, RSAUtil.ALIAS);
        System.out.printf("X509Certificate: %s%n", cert);
        byte[] sign = RSAUtil.sign(dataBytes, privateKey, cert);
        System.out.printf("sign: %s%n", EncodeUtil.base64Encode(sign));
        boolean verify = RSAUtil.verify(dataBytes, sign, cert);
        Assertions.assertTrue(verify);
    }

    @Test
    public void certEncrypt() throws Exception {
        String data = "故园东望路漫漫，双袖龙钟泪不干。马上相逢无纸笔，凭君传语报平安。";
        System.out.printf("data: %s%n", data);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        KeyStore keyStore = RSAUtil.loadKeyStore(RSAUtil.KEY_STORE_FILE, RSAUtil.STOREPASS);
        System.out.printf("keyStore: %s%n", keyStore);
        PrivateKey privateKey = RSAUtil.loadPrivateKey(keyStore, RSAUtil.ALIAS, RSAUtil.STOREPASS);
        X509Certificate cert = RSAUtil.loadCert(keyStore, RSAUtil.ALIAS);
        System.out.printf("X509Certificate: %s%n", cert);
        byte[] encrypt = RSAUtil.encrypt(dataBytes, cert);
        System.out.printf("encrypt: %s%n", Arrays.toString(encrypt));
        byte[] decrypt = RSAUtil.decrypt(encrypt, privateKey);
        System.out.printf("decrypt: %s%n", Arrays.toString(decrypt));
        Assertions.assertArrayEquals(dataBytes, decrypt);
    }
}