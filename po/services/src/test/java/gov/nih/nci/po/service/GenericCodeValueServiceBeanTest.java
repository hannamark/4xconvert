package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.po.data.bo.AbstractCodeValue;
import gov.nih.nci.po.data.bo.CodeValue;
import gov.nih.nci.po.data.bo.FundingMechanism;
import gov.nih.nci.po.data.bo.OrganizationalContactType;
import gov.nih.nci.po.data.bo.OversightCommitteeType;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.data.bo.FundingMechanism.FundingMechanismStatus;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.lang.reflect.Constructor;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class GenericCodeValueServiceBeanTest extends AbstractHibernateTestCase {

    @SuppressWarnings("unchecked")
    private static Class<? extends AbstractCodeValue>[] CLASSES = new Class[] {
            OversightCommitteeType.class,
            OrganizationalContactType.class,
            ResearchOrganizationType.class,
            FundingMechanism.class};

    private static final String CODE = "TT";
    private static final String DESC = "Test Type";
    private static final String ORDER_BY = "description";
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
        if (clz.equals(FundingMechanism.class)) {
            constructor = clz.getConstructor(String.class, String.class, String.class, FundingMechanismStatus.class);
            newInstance = constructor.newInstance(CODE, DESC, "cat", FundingMechanismStatus.ACTIVE);

        } else if (clz.equals(ResearchOrganizationType.class)) {
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

    @Test
    @SuppressWarnings("unchecked")
    public void testOrderedList() throws Exception {
        String[][] researchOrgTypeData = {{"BB", "B"}, {"CC", "C"}, {"AA", "A"}}; 
       
        Session session = PoHibernateUtil.getCurrentSession();
        for (String[] sArr : researchOrgTypeData) {
            session.save(new ResearchOrganizationType(sArr[0], sArr[1]));
        }
        
        List<ResearchOrganizationType> orderedList = svcBean.list(ResearchOrganizationType.class, ORDER_BY);
        List<ResearchOrganizationType> unorderedList = svcBean.list(ResearchOrganizationType.class);
        assertEquals(unorderedList.size(), orderedList.size());
        TreeSet<ResearchOrganizationType> ts = new TreeSet(new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((ResearchOrganizationType)o1).getDescription().toLowerCase().compareTo(((ResearchOrganizationType)o2).getDescription().toLowerCase());
            }
        });
        ts.addAll(unorderedList);

        Iterator<ResearchOrganizationType> orderedListIterator = orderedList.iterator(),
            tsIterator = ts.iterator();
        while(orderedListIterator.hasNext()) {
          assertEquals(orderedListIterator.next(), tsIterator.next());
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
