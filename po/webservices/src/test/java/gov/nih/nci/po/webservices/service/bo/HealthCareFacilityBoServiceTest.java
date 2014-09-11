package gov.nih.nci.po.webservices.service.bo;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;
import gov.nih.nci.po.webservices.service.exception.ServiceException;
import org.junit.Test;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareFacilityBoServiceTest extends AbstractEnhancedOrganizationRoleTest<HealthCareFacility, HealthCareFacilityCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new HealthCareFacilityBoService();
    }

    @Override
    protected HealthCareFacility getBasicModel() {
        return ModelUtils.getBasicHealthCareFacility();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<HealthCareFacility> getEjbService() {
        return serviceLocator.getHealthCareFacilityService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<HealthCareFacilityCR> getCrService() {
        return serviceLocator.getHealthCareFacilityCRService();
    }

    @Test
    public void testUpdatePendingToActive() throws JMSException {
        HealthCareFacility currentHealthCareFacility = getBasicModel();
        currentHealthCareFacility.setStatus(RoleStatus.PENDING);
        currentHealthCareFacility.setId(1L);

        HealthCareFacility updatedHealthCareFacility = getBasicModel();
        updatedHealthCareFacility.setStatus(RoleStatus.ACTIVE);
        updatedHealthCareFacility.setId(1L);

        when(getEjbService().getById(anyLong())).thenReturn(currentHealthCareFacility);

        updatedHealthCareFacility.setStatus(RoleStatus.ACTIVE);
        try {
            this.service.curate(updatedHealthCareFacility);
            fail("Exception expected");
        } catch(ServiceException se) {
            assertEquals("Illegal attempt to update status of HealthCareFacility 1 from PENDING to ACTIVE", se.getMessage());
        }
    }

    @Test(expected = ServiceException.class)
    public void testCreateActive() throws JMSException, EntityValidationException {
        HealthCareFacility currentHealthCareFacility = getBasicModel();
        currentHealthCareFacility.setStatus(RoleStatus.ACTIVE);
        currentHealthCareFacility.setId(1L);

        service.create(currentHealthCareFacility);
    }
}
