package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
import gov.nih.nci.po.service.GenericStructrualRoleCRServiceLocal;
import gov.nih.nci.po.service.GenericStructrualRoleServiceLocal;
import gov.nih.nci.po.webservices.service.bridg.ModelUtils;

import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ResearchOrganizationBoServiceTest extends AbstractRoleBoServiceTest<ResearchOrganization, ResearchOrganizationCR>{
    @Override
    protected void initServiceUnderTest() {
        this.service = new ResearchOrganizationBoService();

        when(serviceLocator.getGenericCodeValueService()
                .getByCode(ResearchOrganizationType.class, ModelUtils.getBasicResearchOrganizationType().getCode()))
            .thenReturn(ModelUtils.getBasicResearchOrganizationType());
    }

    @Override
    protected ResearchOrganization getBasicModel() {
        return ModelUtils.getBasicResearchOrganization();
    }

    @Override
    protected GenericStructrualRoleServiceLocal<ResearchOrganization> getEjbService() {
        return serviceLocator.getResearchOrganizationService();
    }

    @Override
    protected GenericStructrualRoleCRServiceLocal<ResearchOrganizationCR> getCrService() {
        return serviceLocator.getResearchOrganizationCRService();
    }

}
