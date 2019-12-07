package com.ydp.ez.user.common.util;

import com.ydp.ez.user.common.exception.UserErrorCode;
import com.ydp.ez.user.common.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author yedp
 * 编码解码工具
 */
@Slf4j
public class CodecUtil {

    /**
     * 算法名称
     */
    private static final String ALGORITHM_NAME = "SHA-256";

    /**
     * 获取指定长度字符串
     *
     * @param length
     * @return
     */
    public static String generateRandomStr(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    /**
     * 加密字符串
     *
     * @param content
     * @param salt
     * @return
     */
    public static String encodeSha256Hash(String content, String salt) throws UserException {
        try {
            if (StringUtils.isBlank(content)) {
                return "";
            }
            MessageDigest digest = MessageDigest.getInstance(ALGORITHM_NAME);
            if (salt != null) {
                digest.reset();
                digest.update(salt.getBytes());
            }
            byte[] hashed = digest.digest(content.getBytes());
            return Base64.getEncoder().encodeToString(hashed);
        } catch (NoSuchAlgorithmException e) {
            log.error("verifySha256Hash.NoSuchAlgorithmException:{}", e);
            throw new UserException(UserErrorCode.SYSTEM_ERROR_WITH_MSG,"获取算法失败");
        }
    }

    /**
     * 验证密码
     *
     * @param content
     * @param salt
     * @param password
     * @return
     */
    public static boolean verifySha256Hash(String content, String salt, String password) throws UserException {
        if (StringUtils.isBlank(content) || StringUtils.isBlank(password)) {
            return false;
        }
        return password.equals(encodeSha256Hash(content, salt));
    }
}
