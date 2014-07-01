package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.HealthCareFacilityCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

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


}
