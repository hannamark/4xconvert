package gov.nih.nci.pa.test.integration;

import static org.junit.Assert.assertEquals;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.service.IProtocolService;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.service.SessionManagerRemote;
import gov.nih.nci.pa.test.integration.RemoteServiceHelper;
import gov.nih.nci.pa.util.JNDIUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProtocolServiceTest {
    
    private IProtocolService bean;
    private SessionManagerRemote sessionManager;
    private InitialContext ctx;
    
    @Before
    public void init() throws NamingException {
        bean = RemoteServiceHelper.getProtocolService();
        
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.SECURITY_PRINCIPAL, RemoteServiceHelper.username);
        env.put(Context.SECURITY_CREDENTIALS, RemoteServiceHelper.password);
        env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.security.jndi.JndiLoginInitialContextFactory");
        ctx = new InitialContext(env);

        sessionManager = (SessionManagerRemote) JNDIUtil.lookup(ctx, "pa/SessionManagerBean/remote");
        String retURL = "http://www.google.com/";
        sessionManager.startSession("username", retURL);
    }

    @After
    public void cleanup() throws NamingException {
        RemoteServiceHelper.close();
    }


    @Test
    public void serviceAvailableTest() throws Exception
    {
        IProtocolService bean = RemoteServiceHelper.getProtocolService();
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        sc.setNciIdentifier("NCI-2008-0006");
        List<ProtocolDTO> p = bean.getProtocol(sc);
        assertEquals(p.get(0).getLongTitleText(), "A Phase I study of Taxol in refractory leukemia in children");
    }
}