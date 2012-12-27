package gov.nih.nci.po.web.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.OrganizationSearchCriteria;
import gov.nih.nci.po.service.PersonSearchCriteria;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;


/**
 *
 * @author gax
 */
public class PoHttpSessionUtilTest extends AbstractPoTest {

    @Test
    public void testAddAttribute_Organization() {
        new PoHttpSessionUtil();
        Organization o1 = new Organization();
        Organization o2 = new Organization();
        String result1 = PoHttpSessionUtil.addAttribute(o1);
        String result2 = PoHttpSessionUtil.addAttribute(o2);
        assertFalse(result1.equals(result2));
        assertSame(o1, PoHttpSessionUtil.getSession().getAttribute(result1));
        assertSame(o2, PoHttpSessionUtil.getSession().getAttribute(result2));
    }

    @Test
    public void testAddAttribute_Person() {
        Person p1 = new Person();
        Person p2 = new Person();
        String result1 = PoHttpSessionUtil.addAttribute(p1);
        String result2 = PoHttpSessionUtil.addAttribute(p2);
        assertFalse(result1.equals(result2));
        assertSame(p1, PoHttpSessionUtil.getSession().getAttribute(result1));
        assertSame(p2, PoHttpSessionUtil.getSession().getAttribute(result2));
    }

    @Test
    public void testAddAttribute_SOSC() {
        OrganizationSearchCriteria x = new OrganizationSearchCriteria();
        OrganizationSearchCriteria y = new OrganizationSearchCriteria();
        String resultX = PoHttpSessionUtil.addAttribute(x);
        String resultY = PoHttpSessionUtil.addAttribute(y);
        assertFalse(resultX.equals(resultY));
        assertSame(x, PoHttpSessionUtil.getSession().getAttribute(resultX));
        assertSame(y, PoHttpSessionUtil.getSession().getAttribute(resultY));
    }

    @Test
    public void testAddAttribute_SPSC() {
        PersonSearchCriteria x = new PersonSearchCriteria();
        PersonSearchCriteria y = new PersonSearchCriteria();
        String resultX = PoHttpSessionUtil.addAttribute(x);
        String resultY = PoHttpSessionUtil.addAttribute(y);
        assertFalse(resultX.equals(resultY));
        assertSame(x, PoHttpSessionUtil.getSession().getAttribute(resultX));
        assertSame(y, PoHttpSessionUtil.getSession().getAttribute(resultY));
    }

    @Test
    public void testAddAttribute_CRS() {
        ClinicalResearchStaff x = new ClinicalResearchStaff();
        ClinicalResearchStaff y = new ClinicalResearchStaff();
        String resultX = PoHttpSessionUtil.addAttribute(x);
        String resultY = PoHttpSessionUtil.addAttribute(y);
        assertFalse(resultX.equals(resultY));
        assertSame(x, PoHttpSessionUtil.getSession().getAttribute(resultX));
        assertSame(y, PoHttpSessionUtil.getSession().getAttribute(resultY));
    }

    @Test
    public void testAddAttribute_HCP() {
        HealthCareProvider x = new HealthCareProvider();
        HealthCareProvider y = new HealthCareProvider();
        String resultX = PoHttpSessionUtil.addAttribute(x);
        String resultY = PoHttpSessionUtil.addAttribute(y);
        assertFalse(resultX.equals(resultY));
        assertSame(x, PoHttpSessionUtil.getSession().getAttribute(resultX));
        assertSame(y, PoHttpSessionUtil.getSession().getAttribute(resultY));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveAttribute() {
        // make sure the session is clean to start
        getSession().clearAttributes();

        Person p1 = new Person();
        Person p2 = new Person();
        Person p3 = new Person();
        String key1 = PoHttpSessionUtil.addAttribute(p1);
        PoHttpSessionUtil.addAttribute(p2);
        PoHttpSessionUtil.addAttribute(p3);
        Enumeration<String> en = PoHttpSessionUtil.getSession().getAttributeNames();
        List<String> names = new ArrayList<String>();
        CollectionUtils.addAll(names, en);
        assertEquals(3, names.size());

        PoHttpSessionUtil.removeAttribute(key1);

        en = PoHttpSessionUtil.getSession().getAttributeNames();
        names = new ArrayList<String>();
        CollectionUtils.addAll(names, en);
        assertEquals(2, names.size());

        PoHttpSessionUtil.removeAttribute("p-");

        en = PoHttpSessionUtil.getSession().getAttributeNames();
        names = new ArrayList<String>();
        CollectionUtils.addAll(names, en);
        assertEquals(0, names.size());
    }

    @Test
    public void testValidateSessionKeyValidKey() {
        PoHttpSessionUtil.validateSessionKey("abc-123");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateSessionKeyInvalidKey() {
        PoHttpSessionUtil.validateSessionKey("abc-123");
        getSession().clearAttributes();
        PoHttpSessionUtil.validateSessionKey("abc-123");
    }
}
