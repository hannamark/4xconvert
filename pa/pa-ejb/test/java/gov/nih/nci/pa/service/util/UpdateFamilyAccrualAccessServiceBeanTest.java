package gov.nih.nci.pa.service.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

public class UpdateFamilyAccrualAccessServiceBeanTest  extends AbstractHibernateTestCase {

	private UpdateFamilyAccrualAccessServiceBean svcBean;
	
	 @Before
	    public void setUp() throws Exception {
		 svcBean = new UpdateFamilyAccrualAccessServiceBean();
		 svcBean.setFamilyService(new FamilyServiceBeanLocal());
		 svcBean.setStudySiteAccrualAccessSrv(new StudySiteAccrualAccessServiceBean());
		 DataAccessServiceLocal mockDA = mock(DataAccessServiceLocal.class);
		 svcBean.setDataAccessService(mockDA);
		 List<Object> trialsDWFS = new ArrayList<Object>();
		 Object[] testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(0).toString()), DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE };
		 trialsDWFS.add(testCase);
		 testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(1).toString()), DocumentWorkflowStatusCode.ACCEPTED};
		 trialsDWFS.add(testCase);
		 testCase = new Object[]{new BigInteger(TestSchema.studyProtocolIds.get(2).toString()), DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE};
		 trialsDWFS.add(testCase);		 
		 when(mockDA.findByQuery(any(DAQuery.class))).thenReturn(trialsDWFS);
	     TestSchema.primeData();
	 }
	 
	 @Test
	    public void testUpdateFamilyAccrualAccess() throws Exception {
		 RegistryUser user = TestSchema.getRegistryUser();
		 user.setFamilyAccrualSubmitter(true);
		 user.setAffiliatedOrganizationId(1L);
		 TestSchema.addUpdObject(user);
		 try{
			 svcBean.updateFamilyAccrualAccess();
		 } catch(Exception e) {
			 
		 }
	 }
}
