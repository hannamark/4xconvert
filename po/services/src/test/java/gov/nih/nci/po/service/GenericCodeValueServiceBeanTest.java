package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.po.data.bo.AbstractCodeValue;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.Constructor;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class GenericCodeValueServiceBeanTest extends AbstractHibernateTestCase {

    @SuppressWarnings("unchecked")
    private static Class[] CLASSES = new Class[] {
            OversightCommitteeType.class, OrganizationalContactType.class, ResearchOrganizationType.class };

    private static final String TESTTYPE = "testtype";
    private final GenericCodeValueServiceBean svcBean = new GenericCodeValueServiceBean();

    @Before
    public void init() throws Exception {
        Session s = PoHibernateUtil.getCurrentSession();
        init(s, OversightCommitteeType.class);
        init(s, OrganizationalContactType.class);
        init(s, ResearchOrganizationType.class);
    }

    private void init(Session s, Class<? extends AbstractCodeValue> clz) throws Exception {
        int n = s.createQuery("FROM " + clz.getName()).list().size();
        assertEquals(0, n);

        Constructor<? extends CodeValue> constructor = clz.getConstructor(String.class);
        CodeValue newInstance = constructor.newInstance(TESTTYPE);
        s.save(newInstance);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIt() throws Exception {
        for (int i = 0; i < CLASSES.length; i++) {
            testIt(CLASSES[i]);
        }
    }

    private void testIt(Class<? extends CodeValue> clz) {
        assertNull(svcBean.getByCode(clz, "foo"));
        CodeValue oct = svcBean.getByCode(clz, TESTTYPE);
        assertNotNull(oct);
        assertEquals(TESTTYPE, oct.getCode());
    }
}
