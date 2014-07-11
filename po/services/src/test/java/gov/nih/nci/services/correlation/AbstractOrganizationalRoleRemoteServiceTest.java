package gov.nih.nci.services.correlation;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.CorrelationChangeRequest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.UserProvisioningManager;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;
import gov.nih.nci.services.CorrelationService;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(SecurityServiceProvider.class)
public abstract class AbstractOrganizationalRoleRemoteServiceTest<T extends AbstractOrganizationRoleDTO,
    CR extends CorrelationChangeRequest<?>> extends AbstractStructrualRoleRemoteServiceTest<T, CR> {


    @Before
    public void mockSecurity() {
        UserProvisioningManager userProvisioningManager = mock(UserProvisioningManager.class);
        when(userProvisioningManager.getUser(anyString())).thenAnswer(
                new Answer<User>() {
                    @Override
                    public User answer(InvocationOnMock invocation) throws Throwable {
                        return (User) PoHibernateUtil.getCurrentSession().createCriteria(User.class)
                                .add(Restrictions.eq("loginName", invocation.getArguments()[0])).uniqueResult();
                    }
                }
        );


        mockStatic(SecurityServiceProvider.class);
        try {
            PowerMockito.when(SecurityServiceProvider.getUserProvisioningManager("po")).thenReturn(userProvisioningManager);
        } catch (CSException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCreatedBySetOnCreation() throws Exception {
        T dto = getSampleDto();
        CorrelationService<T> service = getCorrelationService();
        Ii id1 = service.createCorrelation(dto);

        PoHibernateUtil.getCurrentSession().flush();
        PoHibernateUtil.getCurrentSession().clear();

        verifyCreatedBy(id1);

    }


    protected abstract void verifyCreatedBy(Ii id1);
}
