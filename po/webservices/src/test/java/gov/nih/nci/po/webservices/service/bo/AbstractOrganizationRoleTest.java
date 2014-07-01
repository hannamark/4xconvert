package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractIdentifiedEntity;
import gov.nih.nci.po.data.bo.AbstractOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.junit.Test;

import javax.jms.JMSException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractOrganizationRoleTest <TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        extends AbstractRoleBoServiceTest<TYPE, CR_TYPE>{

    /*
     * Override tests
     */
    @Test
    public void testUpdateOwnOverridden() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);
        ((AbstractOrganizationRole) currentInstance).setOverriddenBy(otherUser);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);
        updatedInstance.setStatus(RoleStatus.NULLIFIED);

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);


        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);

        verify(getEjbService(), never()).curate(updatedInstance);
        verify(getCrService()).create((CR_TYPE)any());

    }

    @Test
    public void testUpdateOverriddenWithNoChange() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        TYPE currentInstance = getBasicModel();
        ((AbstractRole) currentInstance).setId(1L);
        ((AbstractRole) currentInstance).setCreatedBy(me);
        ((AbstractOrganizationRole) currentInstance).setOverriddenBy(otherUser);

        TYPE updatedInstance = getBasicModel();
        ((AbstractRole) updatedInstance).setId(1L);

        when(getEjbService().getById(1L))
                .thenReturn(currentInstance);


        if (currentInstance instanceof AbstractIdentifiedEntity) {
            Organization organization = ((AbstractIdentifiedEntity)updatedInstance).getScoper();
            organization.setId(2L);
            when(serviceLocator.getOrganizationService().getById(2L))
                    .thenReturn(organization);
        }

        service.curate(updatedInstance);

        verify(getEjbService(), never()).curate(updatedInstance);
        verify(getCrService(), never()).create((CR_TYPE)any());
    }
}
