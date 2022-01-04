package com.enjoyu.admin.components.shiro;

import com.enjoyu.admin.common.exception.ErrEnum;
import com.enjoyu.admin.common.exception.ServiceException;
import com.enjoyu.admin.components.mbp.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import java.util.Objects;

public class ShiroUtil {
    public static final String alg = Sha256Hash.ALGORITHM_NAME;
    public static final boolean isHex = false;
    public static final int iterations = 1024;

    public static String randomSalt() {
        ByteSource byteSource = new SecureRandomNumberGenerator().nextBytes();
        if (isHex) {
            return byteSource.toHex();
        } else {
            return byteSource.toBase64();
        }
    }

    public static String encrypt(String source, String salt) {
        SimpleHash simpleHash = new SimpleHash(alg, source, salt, iterations);
        if (isHex) {
            return simpleHash.toHex();
        } else {
            return simpleHash.toBase64();
        }
    }

    public static boolean matchEncrypt(String source, String salt, String target) {
        String encrypt = encrypt(source, salt);
        return Objects.equals(encrypt, target);
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public static User currentUser() {
        if (isUser()) {
            return (User) getSubject().getPrincipal();
        }
        throw new ServiceException(ErrEnum.ACCOUNT_NOT_LOGIN);
    }

    public static Session getSession() {
        return getSubject().getSession();
    }


    public static boolean isUser() {
        return getSubject() != null && getSubject().getPrincipal() != null;
    }

    public static boolean isGuest() {
        return !isUser();
    }


}
