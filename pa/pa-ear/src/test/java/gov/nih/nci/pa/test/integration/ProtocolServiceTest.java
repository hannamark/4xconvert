package gov.nih.nci.pa.test.integration;

import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.SessionManagerRemote;
import gov.nih.nci.pa.util.JNDIUtil;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.After;
import org.junit.Before;

public class ProtocolServiceTest {
    
    private StudyProtocolService bean;
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


    //@Test
    public void serviceAvailableTest() throws Exception
    {
        StudyProtocolService bean = RemoteServiceHelper.getProtocolService();
        //ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        //sc.setNciIdentifier("NCI-2008-0006");
        //List<ProtocolDTO> p = bean.getProtocol(sc);
        //assertEquals(p.get(0).getLongTitleText(), "A Phase I study of Taxol in refractory leukemia in children");
        //assertNotNull(bean);
        
    }
}