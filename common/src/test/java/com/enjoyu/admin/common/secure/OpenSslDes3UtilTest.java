package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

class OpenSslDes3UtilTest {

    @Test
    void testDecode() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException, InvalidKeyException, URISyntaxException {
        String password = "赋得古原草送别";
        String text = "离离原上草，一岁一枯荣。野火烧不尽，春风吹又生。远芳侵古道，晴翠接荒城。又送王孙去，萋萋满别情。";
        Path path = Paths.get(ClassLoader.getSystemResource("").toURI());
        Path path1 = path.resolve("grass.txt");
        Path path2 = path.resolve("grass.txt.des3");
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        Files.write(path1, bytes, StandardOpenOption.CREATE);
        byte[] encode = OpenSslDes3Util.encode(path1.toFile(), password);
        Files.write(path2, encode, StandardOpenOption.CREATE);
        byte[] decode = OpenSslDes3Util.decode(path2.toFile(), password);
        System.out.println(new String(decode));
    }
}
