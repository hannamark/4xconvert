package gov.nih.nci.pa.report.util;

import javax.interceptor.InvocationContext;

import org.junit.Before;
import org.junit.Test;

public class ViewerHibernateSessionInterceptorTest {
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }

    @Test (expected=NullPointerException.class)
    public void criteriaValidateTest() throws Exception {
        ViewerHibernateSessionInterceptor i = new ViewerHibernateSessionInterceptor();
        InvocationContext ctx = new MockInvocationContext();
        i.manageHibernateSession(ctx);
    }
}
