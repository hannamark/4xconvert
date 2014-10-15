/**
 * 
 */
package gov.nih.nci.pa.util;

import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.security.authorization.domainobjects.Application;
import gov.nih.nci.security.authorization.domainobjects.Group;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSConfigurationException;
import gov.nih.nci.security.system.ApplicationSessionFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.util.ReflectionUtils;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author dkrylov
 * 
 */
public class AbstractEjbTestCase extends AbstractHibernateTestCase {

    private EjbFactory ejbFactory;

    private ServiceLocator backupPaServiceLocator;
    private PoServiceLocator backupPoServiceLocator;
    private CSMUserUtil backupCsmService;
    private String backupUsername;

    @Before
    public final void setupEjbEnvironment() throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, IOException,
            IllegalStateException, NamingException, URISyntaxException,
            CSConfigurationException {
        ejbFactory = new EjbFactory();
        configureJndi();
        configurePoMock();
        configureCSM();
        createCsmUsers();
        TestSchema.primeData();
        setCurrentUser();
    }

    /**
     * 
     */
    private void setCurrentUser() {
        this.backupUsername = UsernameHolder.getUser();
        UsernameHolder
                .setUserCaseSensitive("/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");
    }

    @After
    public final void shutdown() {
        CSMUserService.setInstance(backupCsmService);
        PaRegistry.getInstance().setServiceLocator(backupPaServiceLocator);
        PoRegistry.getInstance().setPoServiceLocator(backupPoServiceLocator);
        UsernameHolder.setUserCaseSensitive(UsernameHolder.ANONYMOUS_USERNAME
                .equals(backupUsername) ? null : backupUsername);
    }

    private void createCsmUsers() throws CSConfigurationException {
        SessionFactory sf = ApplicationSessionFactory.getSessionFactory("pa");
        Session s = sf.openSession();

        Application app = new Application();
        app.setApplicationName("pa");
        app.setUpdateDate(new Date());
        s.saveOrUpdate(app);

        createCsmGroup(app, s, "Abstractor");
        createCsmGroup(app, s, "Submitter");
        createCsmGroup(app, s, "SuAbstractor");
        createCsmGroup(app, s, "ScientificAbstractor");
        createCsmGroup(app, s, "AdminAbstractor");

        createCsmUser(s, "Super", "Abstractor",
                "/O=caBIG/OU=caGrid/OU=Training/OU=Dorian/CN=SuAbstractor");

        s.flush();
        s.close();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void createCsmUser(Session s, String first, String last,
            String login) {
        User user = new User();
        user.setFirstName(first);
        user.setLastName(last);
        user.setLoginName(login);
        user.setGroups(new HashSet(s.createCriteria(Group.class).list()));
        user.setUpdateDate(new Date());
        s.saveOrUpdate(user);
    }

    private void createCsmGroup(Application app, Session s, String name) {
        Group g = new Group();
        g.setApplication(app);
        g.setGroupName(name);
        s.saveOrUpdate(g);
    }

    private void configureCSM() throws IOException, URISyntaxException {

        this.backupCsmService = CSMUserService.getInstance();

        CSMUserService.setInstance(new CSMUserService());

        File csmConf = (new File(getClass().getResource(
                "/ApplicationSecurityConfig.xml").toURI()));
        System.setProperty("gov.nih.nci.security.configFile",
                csmConf.getAbsolutePath());
        String hibernateConfig = IOUtils.toString(getClass()
                .getResourceAsStream("/csm.hibernate.cfg.xml"));
        File hibernateConfigFile = new File("csm.hibernate.cfg.xml");
        FileUtils.writeStringToFile(hibernateConfigFile, hibernateConfig);
        hibernateConfigFile.deleteOnExit();

    }

    @SuppressWarnings("rawtypes")
    public final Object getEjbBean(Class clazz) {
        for (Object ejb : ejbFactory.getEjbs()) {
            if (ejb.getClass().equals(clazz)) {
                return ejb;
            }
        }
        throw new RuntimeException("EJB of type " + clazz + " is not found");
    }

    private void configurePoMock() {
        this.backupPoServiceLocator = PoRegistry.getInstance()
                .getPoServiceLocator();

        PoRegistry.getInstance().setPoServiceLocator(
                new MockPoJndiServiceLocator());

        MockOrganizationEntityService.reset(1, true);
        MockPersonEntityService.reset();

    }

    private void configureJndi() throws IllegalStateException, NamingException {

        this.backupPaServiceLocator = PaRegistry.getInstance()
                .getServiceLocator();

        JndiServiceLocator loc = new JndiServiceLocator();
        Field ctx = ReflectionUtils.findField(JndiServiceLocator.class, "ctx");
        ReflectionUtils.makeAccessible(ctx);
        ReflectionUtils.setField(ctx, loc, buildJndiContext());
        PaRegistry.getInstance().setServiceLocator(loc);

    }

    private Context buildJndiContext() throws IllegalStateException,
            NamingException {
        SimpleNamingContextBuilder contextBuilder = SimpleNamingContextBuilder
                .emptyActivatedContextBuilder();
        for (Object ejb : ejbFactory.getEjbs()) {
            storeEjbInContext(ejb, contextBuilder);
        }
        return contextBuilder.createInitialContextFactory(new Hashtable<>())
                .getInitialContext(new Hashtable<>());
    }

    @SuppressWarnings("rawtypes")
    private void storeEjbInContext(Object ejb,
            SimpleNamingContextBuilder contextBuilder) {
        Class localOrRemoteInterface = getEjbImplementedInterface(ejb
                .getClass());
        String jndiURL = "java:global/pa/pa-ejb/"
                + ejb.getClass().getSimpleName() + "!"
                + localOrRemoteInterface.getName();
        System.out.println("Binding " + ejb.getClass().getSimpleName() + " as "
                + jndiURL);
        contextBuilder.bind(jndiURL, ejb);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Class getEjbImplementedInterface(Class ejbClass) {
        for (Type type : ejbClass.getGenericInterfaces()) {
            Class interfaceClass = (Class) type;
            if (interfaceClass.isAnnotationPresent(Local.class)
                    || interfaceClass.isAnnotationPresent(Remote.class)) {
                return interfaceClass;
            }
        }
        throw new RuntimeException(
                "Unable to determine which interface, Local or Remote, this EJB implements: "
                        + ejbClass);
    }

}
