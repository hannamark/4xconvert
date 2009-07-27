package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.AbstractHibernateTestCase;

import org.junit.Test;

public class RequiredOrganizationalContactTitleOrPersonValidatorTest extends AbstractHibernateTestCase {

    @Test
    public void isValidType() {
        RequiredOrganizationalContactTitleOrPersonValidator validator =
                new RequiredOrganizationalContactTitleOrPersonValidator();
        assertFalse(validator.isValid(new HealthCareFacility()));

        OrganizationalContact oc = new OrganizationalContact();
        assertFalse(validator.isValid(oc));

        oc.setPlayer(new Person());
        assertFalse(validator.isValid(oc));

        oc.getPlayer().setId(1L);
        assertTrue(validator.isValid(oc));

        oc.setPlayer(null);
        oc.setTitle("");
        assertFalse(validator.isValid(oc));

        oc.setTitle("  ");
        assertFalse(validator.isValid(oc));

        oc.setTitle("test title");
        assertTrue(validator.isValid(oc));

        oc.setPlayer(new Person());
        assertTrue(validator.isValid(oc));

        oc.getPlayer().setId(1L);
        assertTrue(validator.isValid(oc));
    }

}
