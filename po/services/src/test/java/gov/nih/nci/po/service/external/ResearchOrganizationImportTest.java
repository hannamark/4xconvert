package gov.nih.nci.po.service.external;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.data.bo.ResearchOrganizationCR;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.GenericOrganizationRoleServiceLocal;
import gov.nih.nci.po.service.ResearchOrganizationServiceLocal;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;

import javax.jms.JMSException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public class ResearchOrganizationImportTest extends CtepOrganizationRoleImportTest<ResearchOrganization> {

    @Override
    protected AbstractEnhancedOrganizationRole getRoleUnderTest() {
        return this.localResearchOrganization;
    }


    @Override
    protected void verifyRoleServiceCurateCalled() throws JMSException, EntityValidationException {
        verify(serviceLocator.getResearchOrganizationService()).curate(any(ResearchOrganization.class));
    }

    @Override
    protected void verifyRoleChangeRequestCreated() throws EntityValidationException {
//        verify(serviceLocator.getResearchOrganizationCRService()).create(any(ResearchOrganizationCR.class));
    }


    @Override
    protected void verifyRoleServiceCurateNotCalled() throws JMSException, EntityValidationException {
        verify(serviceLocator.getResearchOrganizationService(), never()).create(any(ResearchOrganization.class));
    }

    @Override
    protected AbstractEnhancedOrganizationRoleDTO getCtepDto() {
        return this.ctepReseachOrganizationDto;
    }
}
