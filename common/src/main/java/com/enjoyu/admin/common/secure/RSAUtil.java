package com.enjoyu.admin.common.secure;

import javax.crypto.Cipher;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * jdk生成keystore
 * keytool -genkeypair -alias x_admin_ca -storetype PKCS12 -storepass whoareyou -keyalg RSA -keysize 1024 -sigalg SHA256withRSA -keystore xadmin.keystore  -dname "CN=www.xadmin.com, OU=xadmin.com, O=xadmin, L=BJ, ST=BJ, C=CN"
 * 查看证书库
 * keytool -list -v -keystore xadmin.keystore
 * 导出证书
 * keytool -export -alias x_admin_ca -file x_admin_ca.crt -keystore xadmin.keystore
 */
public abstract class RSAUtil {

    /**
     * 数字签名 密钥算法
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 数字签名 签名/验证算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    /**
     * 密钥长度，DH算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到65536位之间
     */
    private static final int KEY_SIZE = 1024;
    /**
     * -alias 别名
     */
    public static final String ALIAS = "x_admin_ca";
    /**
     * store密码
     */
    public static final String STOREPASS = "whoareyou";
    /**
     * 密钥库文件
     */
    public static final String KEY_STORE_FILE = "xadmin.keystore";

    /**
     * 加载证书存储
     *
     * @param keyStoreFile 密钥库文件路径
     * @param storepass    -storepass 密码
     * @return KeyStore
     */
    public static KeyStore loadKeyStore(String keyStoreFile, String storepass) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        InputStream ins = RSAUtil.class.getClassLoader().getResourceAsStream(keyStoreFile);
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(ins, storepass.toCharArray());
        return keyStore;
    }

    /**
     * 从密钥库加载私钥
     *
     * @param keyStore 密钥库
     * @param alias    别名
     * @param keypass  -keypass 密钥密码 PKCS12格式只有storepass
     * @return 私钥
     */
    public static PrivateKey loadPrivateKey(KeyStore keyStore, String alias, String keypass) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (PrivateKey) keyStore.getKey(alias, keypass.toCharArray());
    }

    /**
     * 从密钥库加载证书
     *
     * @param keyStore 密钥库
     * @param alias    别名
     * @return 证书
     */
    public static X509Certificate loadCert(KeyStore keyStore, String alias) throws UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {
        return (X509Certificate) keyStore.getCertificate(alias);
    }

    /**
     * 初始化RSA密钥对
     *
     * @return RSA密钥对
     */
    public static RsaKeyPair initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        keygen.initialize(KEY_SIZE);
        KeyPair keys = keygen.genKeyPair();
        return new RsaKeyPair(keys.getPublic(), keys.getPrivate());
    }

    /**
     * 根据字符串生成私钥
     *
     * @param keyString 私钥字符串 base64编码
     * @return 私钥
     */
    public static PrivateKey buildPrivateKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = EncodeUtil.base64Decode(keyString);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        return keyFactory.generatePrivate(pkcs8KeySpec);
    }

    /**
     * 根据字符串生成公钥
     *
     * @param keyString 公钥字符串 base64编码
     * @return 公钥
     */
    public static PublicKey buildPublicKey(String keyString) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes = EncodeUtil.base64Decode(keyString);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        return keyFactory.generatePublic(x509KeySpec);
    }


    /**
     * 私钥签名
     *
     * @param data       消息
     * @param privateKey 私钥字符串 base64编码
     * @return 签名字符串 base64编码
     */
    public static String sign(String data, String privateKey) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] byteData = data.getBytes(StandardCharsets.UTF_8);
        return EncodeUtil.base64Encode(sign(byteData, buildPrivateKey(privateKey), SIGNATURE_ALGORITHM));
    }


    /**
     * 私钥证书签名
     * 证书中取得签名算法
     *
     * @param data        消息字节数组
     * @param privateKey  私钥
     * @param certificate 证书
     * @return 签名字节数组
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey, X509Certificate certificate) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        return sign(data, privateKey, certificate.getSigAlgName());
    }

    /**
     * 私钥签名
     *
     * @param data       消息字节数组
     * @param privateKey 私钥
     * @param algorithm  指定签名算法
     * @return 签名字节数组
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥验签
     *
     * @param data      消息
     * @param sign      签名字符串 base64编码
     * @param publicKey 公钥字符串 base64编码
     * @return 是否通过验证
     */
    public static boolean verify(String data, String sign, String publicKey) throws InvalidKeySpecException, NoSuchAlgorithmException, SignatureException, InvalidKeyException {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] decodeSign = EncodeUtil.base64Decode(sign);
        return verify(dataBytes, decodeSign, buildPublicKey(publicKey), SIGNATURE_ALGORITHM);
    }

    /**
     * 证书验签
     *
     * @param data        消息字节数组
     * @param sign        签名字节数组
     * @param certificate 证书
     * @return 是否通过验证
     */
    public static boolean verify(byte[] data, byte[] sign, X509Certificate certificate) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(certificate.getSigAlgName());
        signature.initVerify(certificate);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 公钥验签
     *
     * @param data      消息字节数组
     * @param sign      签名字节数组
     * @param publicKey 公钥
     * @param algorithm 指定签名算法
     * @return 是否通过验证
     */
    public static boolean verify(byte[] data, byte[] sign, PublicKey publicKey, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initVerify(publicKey);
        signature.update(data);
        return signature.verify(sign);
    }

    /**
     * 证书加密
     *
     * @param data        明文字节数组
     * @param certificate 证书
     * @return 密文字节数组
     */
    public static byte[] encrypt(byte[] data, X509Certificate certificate) throws Exception {
        Cipher cipher = Cipher.getInstance(certificate.getPublicKey().getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, certificate);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     *
     * @param data      明文
     * @param publicKey 公钥字符串 base64编码
     * @return 密文 base64编码
     */
    public static String encrypt(String data, String publicKey) throws Exception {
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedData = encrypt(dataBytes, buildPublicKey(publicKey));
        return EncodeUtil.base64Encode(encryptedData);
    }

    /**
     * 公钥加密
     *
     * @param data      明文字节数组
     * @param publicKey 公钥
     * @return 密文字节数组
     */
    public static byte[] encrypt(byte[] data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     *
     * @param data       密文 base64编码
     * @param privateKey 私钥字符串 base64编码
     * @return 明文
     */
    public static String decrypt(String data, String privateKey) throws Exception {
        byte[] dataBytes = EncodeUtil.base64Decode(data);
        byte[] design = decrypt(dataBytes, buildPrivateKey(privateKey));
        return new String(design, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密
     *
     * @param data       密文字节数组
     * @param privateKey 私钥
     * @return 明文字节数组
     */
    public static byte[] decrypt(byte[] data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * RSA密钥对
     */
    public static class RsaKeyPair {

        private final PublicKey publicKey;
        private final PrivateKey privateKey;

        public RsaKeyPair(PublicKey aPublic, PrivateKey aPrivate) {
            this.publicKey = aPublic;
            this.privateKey = aPrivate;
        }

        /**
         * @return 公钥
         */
        public PublicKey getPublicKey() {
            return publicKey;
        }

        /**
         * @return 公钥字节数组
         */
        public byte[] getPublicKeyBytes() {
            return publicKey.getEncoded();
        }

        /**
         * @return 公钥字符串 base64编码
         */
        public String getPublicKeyBase64() {
            return EncodeUtil.base64Encode(getPublicKeyBytes());
        }

        /**
         * @return 私钥
         */
        public PrivateKey getPrivateKey() {
            return privateKey;
        }

        /**
         * @return 私钥字节数组
         */
        public byte[] getPrivateKeyBytes() {
            return privateKey.getEncoded();
        }

        /**
         * @return 私钥字符串 base64编码
         */
        public String getPrivateKeyBase64() {
            return EncodeUtil.base64Encode(getPrivateKeyBytes());
        }
    }
}