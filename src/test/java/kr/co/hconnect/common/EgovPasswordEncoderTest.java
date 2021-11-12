package kr.co.hconnect.common;

import egovframework.rte.fdl.cryptography.EgovCryptoService;
import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/config/context-*.xml")
public class EgovPasswordEncoderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EgovPasswordEncoderTest.class);

    @Test
    public void createHashedPassword() {
        String algorithm = "SHA-256";
        String algorithmKey = "infectious-disease";

        EgovPasswordEncoder encoder = new EgovPasswordEncoder();
        encoder.setAlgorithm(algorithm);

        LOGGER.info("Hashed password {}", encoder.encryptPassword(algorithmKey));
    }

    @Resource(name = "ariaCryptoService")
    private EgovCryptoService cryptoService;

    @Value("${crypto.algorithmKey}")
    private String password;

    @Test
    public void ariaEncryptText() {
        String[] text = { "This is testing text...\nHello!", "한글 테스트", "!@#$%^&*()" };
        for (String string : text) {
            byte[] encrypted = cryptoService.encrypt(string.getBytes(StandardCharsets.UTF_8), password);
            byte[] decrypted = cryptoService.decrypt(encrypted, password);
            assertEquals(string, new String(decrypted, StandardCharsets.UTF_8));
        }
    }

/*
    #context-common.xml 파일에 아래 빈등록후 테스트 가능
    <bean id="digestService" class="egovframework.rte.fdl.cryptography.impl.EgovDigestServiceImpl">
        <property name="algorithm" value="SHA-256" />
        <property name="plainDigest" value="false" />
    </bean>

    @Resource(name = "digestService")
    private EgovDigestService digestService;

    @Test
    public void digestService() {
        String string = "infectious-disease";
        byte[] digested = digestService.digest(string.getBytes(StandardCharsets.UTF_8));
        assertTrue(digestService.matches(string.getBytes(StandardCharsets.UTF_8), digested));
    }
*/

/*
    #context-common.xml 파일에 아래 빈등록후 테스트 가능
    <bean id="generalCryptoService" class="egovframework.rte.fdl.cryptography.impl.EgovGeneralCryptoServiceImpl">
        <property name="passwordEncoder" ref="passwordEncoder" />
        <property name="algorithm" value="PBEWithSHA1AndDESede" />
        <property name="blockSize" value="1024" />
    </bean>

    @Resource(name = "generalCryptoService")
    private EgovCryptoService generalCryptoService;

    @Test
    public void generalCryptoServiceEncryptText() {
        String[] text = { "This is testing text...\nHello!", "한글 테스트", "!@#$%^&*()" };
        for (String string : text) {
            byte[] encrypted = generalCryptoService.encrypt(string.getBytes(StandardCharsets.UTF_8), password);
            byte[] decrypted = generalCryptoService.decrypt(encrypted, password);
            assertEquals(string, new String(decrypted, StandardCharsets.UTF_8));
        }
    }
*/

/*
    #context-common.xml 파일에 아래 빈등록후 테스트 가능
    <bean id="egovEnvConfig" class="egovframework.rte.fdl.property.impl.EgovPropertyServiceImpl">
        <property name="extFileName">
            <set>
                <map>
                    <entry key="encoding" value="UTF-8"/>
                    <entry key="filename" value="classpath:/property/dev-config.properties"/>
                </map>
            </set>
        </property>
    </bean>
    <bean id="egovEnvCryptoService" class="egovframework.rte.fdl.cryptography.impl.EgovEnvCryptoServiceImpl">
        <property name="passwordEncoder" ref="passwordEncoder"/>
        <property name="cryptoService" ref="ariaCryptoService"/>
        <property name="cryptoConfigurer" ref="egovEnvConfig"/>
        <property name="crypto" value="true"/>
        <property name="cryptoAlgorithm" value="${crypto.algorithm}"/>
        <property name="cyptoAlgorithmKey" value="${crypto.algorithmKey}"/>
        <property name="cyptoAlgorithmKeyHash" value="${crypto.algorithmKeyHash}"/>
        <property name="cryptoBlockSize" value="1024"/>
    </bean>

    @Resource(name = "egovEnvCryptoService")
    private EgovEnvCryptoService egovEnvCryptoService;

    @Test
    public void egovEnvCryptoServiceImpl() {
        String dbPassword = "infection12#$";

        LOGGER.info("Plain text {}, Encryption text {}, Encryption(none) text {}"
            , dbPassword
            , egovEnvCryptoService.encrypt(dbPassword)
            , egovEnvCryptoService.encryptNone(dbPassword));
    }
*/

}
