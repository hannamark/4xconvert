package gov.nih.nci.registry.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.registry.rest.exception.BadRequestException;
import gov.nih.nci.registry.rest.exception.BadRequestExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

public class StudyProtocolRsrcTest {
    ProtocolQueryServiceLocal pcs;
    StudyProtocolQueryDTO resultDto;

    /**
     * Set up services.
     */
    @Before
    public void setUpServices() throws Exception {
        resultDto = new StudyProtocolQueryDTO();
        resultDto.setNciIdentifier("nciIdentifier");
        resultDto.setOfficialTitle("officialTitls");
        resultDto.setPhaseName("phaseName");
        resultDto.setLeadOrganizationName("leadOrganizationName");
        resultDto.setPiFullName("piFullName");
        resultDto.setStudyStatusCode(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION);
        resultDto.setNctIdentifier("nctIdentifier");
        resultDto.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE);
        List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
        result.add(resultDto);

        pcs = mock(ProtocolQueryServiceLocal.class);
        when(pcs.getStudyProtocolByAgentNsc(anyString())).thenReturn(result);
        ServiceLocator sl = mock(ServiceLocator.class);
        when(sl.getProtocolQueryService()).thenReturn(pcs);
        PaRegistry.getInstance().setServiceLocator(sl);
    }

    @Test
    public void getStudiesXMLFound() throws Exception {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML("123456");
        assertEquals(200, resp.getStatus());
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLNullTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML(null);
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLEmptyTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML("");
    }

    @Test(expected = BadRequestException.class)
    public void getStudiesXMLAlphaTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        rsrc.getStudiesXML("a");
    }
    
    @Test
    public void getStudiesXMLExceptionTest() {
        try {
            StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
            rsrc.getStudiesXML("a");
        } catch (BadRequestException bre) {
            BadRequestExceptionHandler breh = new BadRequestExceptionHandler();
            Response test = breh.toResponse(bre);
            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), test.getStatus());
        }
    }

    @Test
    public void getStudiesXMLInternalServerErrorTest() throws Exception {
        when(pcs.getStudyProtocolByAgentNsc(anyString())).thenThrow(new RuntimeException());
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML("123456");
        assertEquals(500, resp.getStatus());
    }
}
