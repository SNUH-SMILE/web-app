package kr.co.hconnect.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/context-*.xml")
public class ApplicationContextProviderTest {

    @Test
    public void getApplicationContext() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        assertNotNull(context);
    }

    @Test
    public void getPropertyValue() {
        ApplicationContext context = ApplicationContextProvider.getApplicationContext();
        // String propertyValue = context.getEnvironment().getProperty("crypto.algorithmKey");
        // String propertyValue = context.getEnvironment().resolvePlaceholders("crypto.algorithmKey");

        String propertyValue = ((GenericApplicationContext) context).getBeanFactory()
            .resolveEmbeddedValue("${crypto.algorithmKey}");
        assertEquals("infectious-disease", propertyValue);
    }

}
