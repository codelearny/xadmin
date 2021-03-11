package com.enjoyu.admin.common.secure;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 信息编码工具类
 * <p>URL编码 为 HTML form编码，转换字符串到 application/x-www-form-urlencoded MIME格式。
 * [a-zA-Z0-9.-*_]字符保持不变，" "空格转换为"+"。
 * 所有其他字符都是不安全的，首先使用某种编码方案将其转换为一个或多个字节。
 * 然后每个字节由3个字符的字符串“%xy”表示，其中xy是字节的两位十六进制表示。
 * </p>
 * <p> Base64 是一种基于64个可打印字符来表示二进制数据的方法。完整的base64定义可见 RFC1421和 RFC2045。
 * <ol>
 *     <li>把3个字节变成4个字节。</li>
 *     <li>每76个字符加一个换行符。</li>
 *     <li>最后的结束符也要处理。</li>
 *     <li>码表转换。</li>
 * </ol>
 * </p>
 */
public abstract class EncodeUtil {
    private static final Charset defaultCharset = StandardCharsets.UTF_8;
    public static final String GBK = "GBK";
    public static final String GB2312 = "GB2312";

    public static String urlEncode(String message, Charset charset) throws UnsupportedEncodingException {
        return urlEncode(message, charset == null ? defaultCharset.name() : charset.name());
    }

    public static String urlEncode(String message, String charset) throws UnsupportedEncodingException {
        return URLEncoder.encode(message, charset == null ? defaultCharset.name() : charset);
    }

    public static String urlDecode(String message, Charset charset) throws UnsupportedEncodingException {
        return urlDecode(message, charset == null ? defaultCharset.name() : charset.name());
    }

    public static String urlDecode(String message, String charset) throws UnsupportedEncodingException {
        return URLDecoder.decode(message, charset == null ? defaultCharset.name() : charset);
    }

    public static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] base64Decode(String message) {
        return Base64.getDecoder().decode(message);
    }

    public static String bytesToHexString(byte[] bytes) {
        return new BigInteger(1, bytes).toString(16);
    }

}
