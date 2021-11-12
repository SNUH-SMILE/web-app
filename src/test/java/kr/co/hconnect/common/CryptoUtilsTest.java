package kr.co.hconnect.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/config/context-*.xml")
public class CryptoUtilsTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CryptoUtilsTest.class);

    @Test
    public void encryptNDecrypt() {
        String plainString = "infection12#$";
        String encryptString = CryptoUtils.encrypt(plainString);
        String decryptString = CryptoUtils.decrypt(encryptString);

        LOGGER.info("Plain {}", plainString);
        LOGGER.info("Encrypt {}", encryptString);
        LOGGER.info("Decrypt {}", decryptString);

        assertEquals(plainString, decryptString);
    }

}