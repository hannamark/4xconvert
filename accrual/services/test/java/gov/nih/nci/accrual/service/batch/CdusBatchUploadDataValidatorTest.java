package gov.nih.nci.accrual.service.batch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.service.CSMUserUtil;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.organization.OrganizationSearchCriteriaDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
/**
 * @author Hugh Reinhart
 */
public class CdusBatchUploadDataValidatorTest extends AbstractBatchUploadReaderTest {

    CdusBatchUploadDataValidator v = cdusBatchUploadDataValidator;
    String loginName = "Abstractor: test";
    CSMUserUtil mockcsm = mock(CSMUserUtil.class);
    OrganizationEntityServiceRemote mockorg = mock(OrganizationEntityServiceRemote.class);

    @Before
    public void init () throws Exception {
        when(mockcsm.isUserInGroup(anyString(), anyString())).thenReturn(true);
        CSMUserService.setInstance(mockcsm);
        when(mockorg.search(any(OrganizationSearchCriteriaDTO.class), any(LimitOffset.class))).thenAnswer(
                new Answer<List<OrganizationDTO>>() {
            public List<OrganizationDTO> answer(InvocationOnMock invocation) throws Throwable {
                List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
                result.add(new OrganizationDTO());
                return result;
            }
        });
        when(poServiceLoc.getOrganizationEntityService()).thenReturn(mockorg);

        RegistryUser ru = new RegistryUser();
        User cu = new User();
        ru.setCsmUser(cu);
        v.setRu(ru);
    }

    @Test
    public void  testInitializeOrganizationListsSuAbstractor() throws Exception {
        v.initializeOrganizationLists();
        assertEquals(4, v.getListOfBlankIds().size());
        assertTrue(v.getListOfBlankIds().containsKey(null));
        assertTrue(v.getListOfBlankIds().containsKey(""));
        assertTrue(v.getListOfBlankIds().containsKey("00000"));
        assertTrue(v.getListOfBlankIds().containsKey("CTSU"));
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        assertEquals(PAUtil.dateStringToDateTime("3/1/2014"), v.getBlanksOrgDate());
    }

    @Test
    public void  testInitializeOrganizationListsSubmitter() throws Exception {
        when(mockcsm.isUserInGroup(anyString(), anyString())).thenReturn(false);
        v.initializeOrganizationLists();
        assertEquals(0, v.getListOfBlankIds().size());
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        assertEquals(sdf.format(new Date(0L)), sdf.format(v.getBlanksOrgDate()));
    }

    @Test
    public void  testInitializeOrganizationListsError() throws Exception {
        when(paSvcLocator.getLookUpTableService().getPropertyValue(REJECT_BLANKS_DATE)).thenReturn("xyzzy");
        v.initializeOrganizationLists();
        assertEquals(4, v.getListOfBlankIds().size());
        assertTrue(v.getListOfBlankIds().containsKey(null));
        assertTrue(v.getListOfBlankIds().containsKey(""));
        assertTrue(v.getListOfBlankIds().containsKey("00000"));
        assertTrue(v.getListOfBlankIds().containsKey("CTSU"));
        assertEquals(0, v.getListOfCtepIds().size());
        assertEquals(0, v.getListOfOrgIds().size());
        assertEquals(0, v.getListOfPoIds().size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        assertEquals(sdf.format(new Date(0L)), sdf.format(v.getBlanksOrgDate()));
    }
}
