package gov.nih.nci.pa.test.integration;

import static org.junit.Assert.assertEquals;

import gov.nih.nci.pa.service.IProtocolService;

import java.util.Properties;

import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

public class ProtocolServiceTest {

    @Test
    public void serviceAvailableTest() throws Exception
    {
        IProtocolService bean = RemoteServiceHelper.getProtocolService();
        assertEquals(bean.getProtocolLongTitleText(1), "A Phase I study of Taxol in refractory leukemia in children");
    }
}