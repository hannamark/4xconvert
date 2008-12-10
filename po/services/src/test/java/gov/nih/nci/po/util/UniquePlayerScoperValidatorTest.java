package gov.nih.nci.po.util;

import static org.junit.Assert.*;
import gov.nih.nci.po.data.bo.ClinicalResearchStaff;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.OrganizationalContact;
import gov.nih.nci.po.service.AbstractHibernateTestCase;

import org.junit.Test;

public class UniquePlayerScoperValidatorTest extends AbstractHibernateTestCase {

    @Test
    public void isValidType() {
        UniquePlayerScoperValidator validator = new UniquePlayerScoperValidator();
        assertFalse(validator.isValid(new HealthCareFacility()));
        assertTrue(validator.isValid(new ClinicalResearchStaff()));
        assertTrue(validator.isValid(new OrganizationalContact()));
        assertTrue(validator.isValid(new HealthCareProvider()));
    }

}
