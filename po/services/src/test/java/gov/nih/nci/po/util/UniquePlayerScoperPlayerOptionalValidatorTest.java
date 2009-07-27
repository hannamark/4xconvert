package gov.nih.nci.po.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.AbstractHibernateTestCase;

import org.junit.Test;

public class UniquePlayerScoperPlayerOptionalValidatorTest extends AbstractHibernateTestCase {

    @Test
    public void isValidType() {
        UniquePlayerScoperPlayerOptionalValidator validator = new UniquePlayerScoperPlayerOptionalValidator();
        assertFalse(validator.isValid(new HealthCareFacility()));
        OrganizationalContact oc = new OrganizationalContact();
        assertTrue(validator.isValid(oc));
        oc.setPlayer(new Person());
        oc.getPlayer().setId(1L);
        assertTrue(validator.isValid(oc));
    }

}
