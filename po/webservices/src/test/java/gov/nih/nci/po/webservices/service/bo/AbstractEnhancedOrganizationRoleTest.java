package gov.nih.nci.po.webservices.service.bo;

import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Correlation;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import org.junit.Test;

import javax.jms.JMSException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Extension of {@link AbstractRoleBoServiceTest} that tests aliases.
 *
 * @author Jason Aliyetti <jason.aliyetti@semanticbits.com>
 */
public abstract class AbstractEnhancedOrganizationRoleTest<TYPE extends Correlation, CR_TYPE extends ChangeRequest<TYPE>>
        extends AbstractOrganizationRoleTest<TYPE, CR_TYPE> {


    /*
        ALIAS TESTS
     */
    @Test
    public void testUpdateAlias() throws JMSException, EntityValidationException {
        AbstractEnhancedOrganizationRole currentInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(me);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        AbstractEnhancedOrganizationRole updatedInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        updatedInstance.setId(2L);
        updatedInstance.setName("delta");

        when(getEjbService().getById(2L)).thenReturn((TYPE) currentInstance);

        service.curate((TYPE) updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(2, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        assertEquals("delta", updatedInstance.getAlias().get(1).getValue());
        verify(getEjbService()).curate((TYPE) any());
        verify(getCrService(), never()).create((CR_TYPE) any());
    }

    @Test
    public void testUpdateSomeoneElsesAlias() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        AbstractEnhancedOrganizationRole currentInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(otherUser);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        AbstractEnhancedOrganizationRole updatedInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        updatedInstance.setId(2L);
        updatedInstance.setName("delta");

        when(getEjbService().getById(2L)).thenReturn((TYPE) currentInstance);

        service.curate((TYPE) updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(2, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        assertEquals("delta", updatedInstance.getAlias().get(1).getValue());
        verify(getEjbService(), never()).curate((TYPE) any());
        verify(getCrService()).create((CR_TYPE) any());
    }

    @Test
    public void testDuplicateAlias() throws JMSException, EntityValidationException {
        AbstractEnhancedOrganizationRole currentInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(me);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        AbstractEnhancedOrganizationRole updatedInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        updatedInstance.setId(2L);
        updatedInstance.setName("alpha");

        when(getEjbService().getById(2L)).thenReturn((TYPE) currentInstance);

        service.curate((TYPE) updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(1, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        verify(getEjbService(), never()).curate((TYPE) any());
        verify(getCrService(), never()).create((CR_TYPE) any());
    }

    @Test
    public void testDuplicateAliasOnSomeoneElsesOrganization() throws JMSException, EntityValidationException {
        User otherUser = new User();
        otherUser.setLoginName("otherUser");

        AbstractEnhancedOrganizationRole currentInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        currentInstance.setId(2L);
        currentInstance.setCreatedBy(otherUser);
        currentInstance.setName("beta");
        currentInstance.getAlias().add(new Alias("alpha"));


        AbstractEnhancedOrganizationRole updatedInstance = (AbstractEnhancedOrganizationRole) getBasicModel();
        updatedInstance.setId(2L);
        updatedInstance.setName("alpha");

        when(getEjbService().getById(2L)).thenReturn((TYPE) currentInstance);

        service.curate((TYPE) updatedInstance);

        //assert that the updated instance has aliases set
        assertEquals("beta", updatedInstance.getName());
        assertEquals(1, updatedInstance.getAlias().size());
        assertEquals("alpha", updatedInstance.getAlias().get(0).getValue());
        verify(getEjbService(), never()).curate((TYPE) any());
        verify(getCrService(), never()).create((CR_TYPE) any());
    }




}
