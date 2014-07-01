package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.HealthCareProvider;
import gov.nih.nci.po.data.bo.HealthCareProviderCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class HealthCareProviderBoServiceTest extends AbstractRoleBoServiceTest<HealthCareProvider, HealthCareProviderCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new HealthCareProviderBoService();
    }

    @Override
    protected HealthCareProvider getBasicModel() {
        return ModelUtils.getBasicHealthCareProvider();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<HealthCareProvider> getEjbService() {
        return serviceLocator.getHealthCareProviderService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<HealthCareProviderCR> getCrService() {
        return serviceLocator.getHealthCareProviderCRService();
    }


}
