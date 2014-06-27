package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationCR;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class IdentifiedOrganizationBoServiceTest extends AbstractRoleBoServiceTest<IdentifiedOrganization, IdentifiedOrganizationCR> {
    @Override
    protected void initServiceUnderTest() {
        this.service = new IdentifiedOrganizationBoService();
    }

    @Override
    protected IdentifiedOrganization getBasicModel() {
        return ModelUtils.getBasicIdentifiedOrganization();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<IdentifiedOrganization> getEjbService() {
        return serviceLocator.getIdentifiedOrganizationService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<IdentifiedOrganizationCR> getCrService() {
        return serviceLocator.getIdentifiedOrganizationCRService();
    }


}
