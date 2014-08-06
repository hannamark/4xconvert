package gov.nih.nci.po.service.external;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;

import javax.jms.JMSException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareFacilityImportTest extends CtepOrganizationRoleImportTest<HealthCareFacility> {
    @Override
    protected AbstractEnhancedOrganizationRole getRoleUnderTest() {
        return this.localHealthCareFacility;
    }

    @Override
    protected void verifyRoleServiceCurateCalled() throws JMSException, EntityValidationException {
        verify(serviceLocator.getHealthCareFacilityService()).curate(any(HealthCareFacility.class));
    }

    @Override
    protected void verifyRoleChangeRequestCreated() throws EntityValidationException {
//        verify(serviceLocator.getHealthCareFacilityCRService()).create(any(HealthCareFacilityCR.class));
    }

    @Override
    protected void verifyRoleServiceCurateNotCalled() throws EntityValidationException, JMSException {
//        verify(serviceLocator.getHealthCareFacilityService(), never()).curate(any(HealthCareFacility.class));
    }

    @Override
    protected AbstractEnhancedOrganizationRoleDTO getCtepDto() {
        return this.ctepHealthCareFacilityDto;
    }
}
