package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.dto.NCISpecificInformationWebDTO;
import gov.nih.nci.pa.dto.SummaryFourSponsorsWebDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class NCISpecificInformationActionTest extends AbstractPaActionTest {

    NCISpecificInformationAction nciSpecificInformationAction;
    NCISpecificInformationWebDTO nciSpecificInformationWebDTO;
    
    @Before
    public void setUp() throws PAException {
        nciSpecificInformationAction = new NCISpecificInformationAction();
        nciSpecificInformationWebDTO = new NCISpecificInformationWebDTO();
        nciSpecificInformationWebDTO.setAccrualReportingMethodCode("");
        nciSpecificInformationAction.setNciSpecificInformationWebDTO(nciSpecificInformationWebDTO);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
    }
    
    @Test
    public void testExecute() {
        String result = nciSpecificInformationAction.execute();
        assertEquals("success", result);
    }

    @Test
    public void testQuery() throws Exception {
    	PAServiceUtils svcUtils = mock(PAServiceUtils.class);
    	when(svcUtils.getDuplicateOrganizationIi(any(Ii.class))).thenReturn(IiConverter.convertToIi("1"));
    	Organization org = new Organization();
    	org.setId(1L);
    	when(svcUtils.getOrCreatePAOrganizationByIi(any(Ii.class))).thenReturn(org);
    	nciSpecificInformationAction.setPaServiceUtil(svcUtils);		
        String result = nciSpecificInformationAction.query();
        assertEquals("success", result);
    }

    @Test
    public void testUpdate() {
        String result = nciSpecificInformationAction.update();
        assertEquals("error", result);        
    }
    
    @Test
    public void testUpdate2() {
        nciSpecificInformationWebDTO.setProgramCodeText("PCT");
        nciSpecificInformationWebDTO.setCtroOverride(true);
        nciSpecificInformationWebDTO.setConsortiaTrialCategoryCode("CTCC");
        nciSpecificInformationWebDTO.setSummaryFourFundingCategoryCode("SFFCC");  
        nciSpecificInformationWebDTO.setAccrualReportingMethodCode("ARMC");
        nciSpecificInformationWebDTO.setCtroOverideFlagComments("This is a test comment");
        String result = nciSpecificInformationAction.update();
        assertEquals("success", result);
    }

    @Test(expected=Exception.class)
    public void testDisplayOrg() {
        String result = nciSpecificInformationAction.displayOrg();
        assertEquals("error", result);
    }
    
    @Test
    public void testDisplayOrg2() {
    	getRequest().setupAddParameter("orgId", "1");
        String result = nciSpecificInformationAction.displayOrg();
        SummaryFourSponsorsWebDTO dto = new SummaryFourSponsorsWebDTO();
        dto.setRowId("1");
        dto.setOrgId("1");
        dto.setOrgName("OrgName");
        assertEquals("displayOrgFld", result);
        getRequest().getSession().setAttribute("summary4Sponsors", Arrays.asList(dto));
        getRequest().setupAddParameter("orgId", "1");
        result = nciSpecificInformationAction.displayOrg();
        assertEquals("displayOrgFld", result);
    }

    
    @Test
    public void testDelete() {
    	getRequest().setupAddParameter("uuid", "1");
        List<SummaryFourSponsorsWebDTO> summary4SponsorsList = new ArrayList<SummaryFourSponsorsWebDTO>();
        SummaryFourSponsorsWebDTO webDto = new SummaryFourSponsorsWebDTO(); 
        webDto.setRowId("1");    
        summary4SponsorsList.add(webDto);
        webDto = new SummaryFourSponsorsWebDTO();
        webDto.setRowId("2");
        summary4SponsorsList.add(webDto);
        getSession().setAttribute("summary4Sponsors", summary4SponsorsList);
        String result = nciSpecificInformationAction.deleteSummaryFourOrg();
        assertEquals("displayOrgFld", result);
    }

    @Test
    public void testCoverage() {
        String result = nciSpecificInformationAction.lookup1();
        assertEquals("success", result);
        nciSpecificInformationAction.setChosenOrg("chosenOrg");
        assertEquals("chosenOrg", nciSpecificInformationAction.getChosenOrg());
        nciSpecificInformationAction.getNciSpecificInformationWebDTO();
    }
    

}
