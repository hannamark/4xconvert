package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.po.data.bo.AbstractCodeValue;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.QualifiedEntityType;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class GenericCodeValueServiceBeanTest extends AbstractHibernateTestCase {

    @SuppressWarnings("unchecked")
    private static Class<? extends AbstractCodeValue>[] CLASSES = new Class[] {
            OversightCommitteeType.class,
            OrganizationalContactType.class,
            ResearchOrganizationType.class,
            QualifiedEntityType.class };

    private static final String CODE = "TT";
    private static final String DESC = "Test Type";
    private final GenericCodeValueServiceBean svcBean = new GenericCodeValueServiceBean();

    @Before
    public void init() throws Exception {
        Session s = PoHibernateUtil.getCurrentSession();
        for (Class<? extends AbstractCodeValue> c : CLASSES){
            init(s, c);
        }
    }

    private void init(Session s, Class<? extends AbstractCodeValue> clz) throws Exception {
        int n = s.createQuery("FROM " + clz.getName()).list().size();
        assertEquals(0, n);
        Constructor<? extends CodeValue> constructor = null;
        CodeValue newInstance = null;
        if (clz.equals(ResearchOrganizationType.class) || clz.equals(QualifiedEntityType.class)) {
            constructor = clz.getConstructor(String.class, String.class);
            newInstance = constructor.newInstance(CODE, DESC);
        }else {
            constructor = clz.getConstructor(String.class);
            newInstance = constructor.newInstance(CODE);
        }
        s.save(newInstance);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIt() throws Exception {
        for (Class c : CLASSES) {
            testIt(c);
        }
    }

    private void testIt(Class<? extends CodeValue> clz) {
        try{
            svcBean.getByCode(clz, "foo");
        }catch(IllegalArgumentException iae) {
            // expected
        }
        CodeValue oct = svcBean.getByCode(clz, CODE);
        assertNotNull(oct);
        assertEquals(CODE, oct.getCode());
        
        Cd cd = new Cd();
        cd.setCode(CODE);
        oct =svcBean.getByCode(clz, cd);
        assertNotNull(oct);
        assertEquals(CODE, oct.getCode());
        
        List<? extends CodeValue> list = svcBean.list(clz);
        assertEquals(1, list.size());
        Iterator<? extends CodeValue> iterator = list.iterator();
        CodeValue next = iterator.next();
        assertEquals(CODE, next.getCode());
    }
}
