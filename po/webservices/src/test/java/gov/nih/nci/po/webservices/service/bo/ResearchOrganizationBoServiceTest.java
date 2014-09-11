package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.data.bo.ResearchOrganizationType;
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
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ResearchOrganizationBoServiceTest extends AbstractEnhancedOrganizationRoleTest<ResearchOrganization, ResearchOrganizationCR>{
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

    @Test
    public void testUpdatePendingToActive() throws JMSException {
        ResearchOrganization currentResearchOrganization = getBasicModel();
        currentResearchOrganization.setStatus(RoleStatus.PENDING);
        currentResearchOrganization.setId(1L);

        ResearchOrganization updatedResearchOrganization = getBasicModel();
        updatedResearchOrganization.setStatus(RoleStatus.ACTIVE);
        updatedResearchOrganization.setId(1L);

        when(getEjbService().getById(anyLong())).thenReturn(currentResearchOrganization);

        try {
            this.service.curate(updatedResearchOrganization);
            fail("Exception expected");
        } catch(ServiceException se) {
            assertEquals("Illegal attempt to update status of ResearchOrganization 1 from PENDING to ACTIVE", se.getMessage());
        }
    }

    @Test(expected = ServiceException.class)
    public void testCreateActive() throws JMSException, EntityValidationException {
        ResearchOrganization currentResearchOrganization = getBasicModel();
        currentResearchOrganization.setStatus(RoleStatus.ACTIVE);
        currentResearchOrganization.setId(1L);

        service.create(currentResearchOrganization);
    }

}
