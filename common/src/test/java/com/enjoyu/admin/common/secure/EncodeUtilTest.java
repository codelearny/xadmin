package com.enjoyu.admin.common.secure;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class EncodeUtilTest {

    @Test
    public void urlEncode() throws UnsupportedEncodingException {
        String message = "生涯岂料承优诏，世事空知学醉歌。\n江上月明胡雁过，淮南木落楚山多。\n寄身且喜沧洲近，顾影无如白发何。\n今日龙钟人共弃，愧君犹遣慎风波。";
        System.out.printf("message %n%s%n", message);
        String urlEncode = EncodeUtil.urlEncode(message, StandardCharsets.UTF_8);
        System.out.printf("urlEncode %s%n", urlEncode);
    }

    @Test
    public void urlDecode() throws UnsupportedEncodingException {
        String message = "url%E7%BC%96%E7%A0%81%E8%A7%A3%E7%A0%81";
        String urlDecode = EncodeUtil.urlDecode(message, StandardCharsets.UTF_8);
        System.out.println(urlDecode);
    }

    @Test
    public void charEncode() throws UnsupportedEncodingException {
        String message = "字符编码转换";
        System.out.println(Arrays.toString(message.getBytes()));
        System.out.println(Arrays.toString(message.getBytes(StandardCharsets.US_ASCII)));
        System.out.println(Arrays.toString(message.getBytes(EncodeUtil.GBK)));
        System.out.println(Arrays.toString(message.getBytes(EncodeUtil.GB2312)));
    }

    @Test
    public void base64() {
        String message = "渔翁夜傍西岩宿，晓汲清湘燃楚竹。\n烟销日出不见人，欸乃一声山水绿。\n回看天际下中流，岩上无心云相逐。";
        System.out.printf("message %n%s%n", message);
        String encode = EncodeUtil.base64Encode(message.getBytes());
        System.out.printf("encode %s%n", encode);
        byte[] decode = EncodeUtil.base64Decode(encode);
        System.out.printf("decode %n%s%n", new String(decode));
    }
}
