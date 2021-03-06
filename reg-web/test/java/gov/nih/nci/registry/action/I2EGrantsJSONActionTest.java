package gov.nih.nci.registry.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.service.util.I2EGrantsServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.mockrunner.mock.web.MockHttpServletRequest;

public class I2EGrantsJSONActionTest extends AbstractRegWebTest {

    I2EGrantsJSONAction action;
    I2EGrantsServiceLocal i2eSvc;
    MockHttpServletRequest req;

    @Before
    public void setup() throws Exception {
        action = new I2EGrantsJSONAction();
        req = (MockHttpServletRequest) ServletActionContext.getRequest();
        req.clearParameters();
        PaRegistry.getInstance().setServiceLocator(mock(ServiceLocator.class));
        i2eSvc = mock(I2EGrantsServiceLocal.class);
        when(PaRegistry.getInstance().getI2EGrantsService()).thenReturn(i2eSvc);
        when(i2eSvc.getBySerialNumber(eq("123"))).thenAnswer(new Answer<List<I2EGrantsServiceLocal.I2EGrant>>() {
            @Override
            public List<I2EGrantsServiceLocal.I2EGrant> answer(InvocationOnMock invocation) throws Throwable {
                List<I2EGrantsServiceLocal.I2EGrant> result = new ArrayList<I2EGrantsServiceLocal.I2EGrant>();
                I2EGrantsServiceLocal.I2EGrant grant = new I2EGrantsServiceLocal.I2EGrant();
                grant.setSerialNumber("123456");
                result.add(grant);
                return result;
            }
        } );
     }


    @Test
    public void loadSerialNumbersTest() {
        req.setupAddParameter("serialNumberMatchTerm", "123");
        action.loadSerialNumbers();
        assertEquals(1, action.getSerialNumbers().size());
    }

    @Test
    public void loadSerialNumbersNotFoundTest() {
        req.setupAddParameter("serialNumberMatchTerm", "111");
        action.loadSerialNumbers();
        assertTrue(action.getSerialNumbers().isEmpty());
    }

    @Test
    public void loadSerialNumbersNoQryString() {
        action.loadSerialNumbers();
        assertTrue(action.getSerialNumbers().isEmpty());
    }

}
