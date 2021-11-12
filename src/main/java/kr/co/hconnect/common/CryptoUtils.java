package kr.co.hconnect.common;

import egovframework.rte.fdl.cryptography.EgovCryptoService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 암/복호화 유틸리티 클래스
 */
public class CryptoUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtils.class);

    /**
     * 알고리즘키
     */
    private static String algorithmKey;

    /**
     * 암호화
     * @param plainString 평문 문자열
     * @return 암호화된 문자열
     */
    public static String encrypt(String plainString) {
        // 유효성 검사
        if (StringUtils.isEmpty(plainString))
            throw new IllegalArgumentException("plainString is null or empty");

        // 암호화
        try {
            EgovCryptoService cryptoService = getCryptoService();
            return URLEncoder.encode(
                new String(
                    new Base64().encode(
                        cryptoService.encrypt(plainString.getBytes(StandardCharsets.UTF_8), algorithmKey))), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 복호화
     * @param encryptString 암호화된 문자열
     * @return 복호화된 문자열
     */
    public static String decrypt(String encryptString) {
        // 유효성 검사
        if (StringUtils.isEmpty(encryptString))
            throw new IllegalArgumentException("encryptString is null or empty");

        // 복호화
        try {
            EgovCryptoService cryptoService = getCryptoService();
            return new String(
                cryptoService.decrypt(
                    new Base64().decode(
                        URLDecoder.decode(encryptString, "UTF-8").getBytes(StandardCharsets.UTF_8)), algorithmKey));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * EgovCryptoService 조회
     * @return EgovCryptoService 객체
     */
    private static EgovCryptoService getCryptoService() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();

        // 알고리즘키 설정
        if (algorithmKey == null) {
            algorithmKey = ((ConfigurableApplicationContext) context).getBeanFactory()
                .resolveEmbeddedValue("${crypto.algorithmKey}");
        }

        // EgovCryptoService 객체 조회
        return context.getBean(EgovCryptoService.class);
    }

}
