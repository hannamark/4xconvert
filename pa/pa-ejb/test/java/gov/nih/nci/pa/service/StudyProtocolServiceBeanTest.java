package gov.nih.nci.pa.service;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
//import gov.nih.nci.coppa.iso.Ii;
//import gov.nih.nci.pa.domain.StudyProtocol;
//import gov.nih.nci.pa.domain.StudyProtocolTest;
//import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTOTest;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.TestSchema;
//import gov.nih.nci.pa.util.IsoConverter;
//import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class StudyProtocolServiceBeanTest {

    private StudyProtocolServiceRemote remoteEjb = new StudyProtocolServiceBean();
    
    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
    }
    
    //@Test(expected=PAException.class) 
    public void nullParameter() throws Exception {
        //InterventionalStudyProtocolDTO ispDTO ;
        remoteEjb.createInterventionalStudyProtocol(null);
    }
    
    //@Test(expected=PAException.class) 
    public void nullExtension() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = new InterventionalStudyProtocolDTO();
        Ii ii = new Ii();
        ii.setExtension("xxx");
        ispDTO.setIi(ii);
        remoteEjb.createInterventionalStudyProtocol(ispDTO);
    }
    @Test
    public void createInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocolDTO ispDTO = 
                InterventionalStudyProtocolDTOTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(ispDTO);
        assertNotNull(ii.getExtension());
    }
    
    @Test
    public void getInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocolDTO create = 
                InterventionalStudyProtocolDTOTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(create);
        assertNotNull(ii.getExtension());
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);
        assertNotNull(saved);
        assertEquals(create.getAccrualReportingMethodCode().getCode(), saved.getAccrualReportingMethodCode().getCode());
        assertEquals(create.getAcronym().getValue(),saved.getAcronym().getValue());
        assertEquals(create.getAllocationCode().getCode(),saved.getAllocationCode().getCode());
        assertEquals(create.getDataMonitoringCommitteInd().getValue(),saved.getDataMonitoringCommitteInd().getValue());
        assertEquals(create.getDelayedpostingIndicator().getValue(),saved.getDelayedpostingIndicator().getValue());
        assertEquals(create.getExpandedAccessIndicator().getValue(),saved.getExpandedAccessIndicator().getValue());
        assertEquals(create.getFdaRegulatedIndicator().getValue(),saved.getFdaRegulatedIndicator().getValue());
        assertEquals(create.getIndIdeIndicator().getValue(),saved.getIndIdeIndicator().getValue());
        assertEquals(create.getOfficialTitle().getValue(),saved.getOfficialTitle().getValue());
        assertEquals(create.getPhaseCode().getCode(),saved.getPhaseCode().getCode());
        assertNotNull(saved.getIdentifier().getExtension());
    }
    
    @Test
    public void updateInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocolDTO create = 
                InterventionalStudyProtocolDTOTest.createInterventionalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createInterventionalStudyProtocol(create);
        assertNotNull(ii.getExtension());
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);
        
        saved.setAcronym(StConverter.convertToSt("1234"));
        
        InterventionalStudyProtocolDTO update =  remoteEjb.updateInterventionalStudyProtocol(saved);

        assertNotNull(saved);
        assertEquals(saved.getAccrualReportingMethodCode().getCode(), update.getAccrualReportingMethodCode().getCode());
        assertEquals(saved.getAcronym().getValue(),update.getAcronym().getValue());
        assertEquals(saved.getAllocationCode().getCode(),update.getAllocationCode().getCode());
        assertEquals(saved.getDataMonitoringCommitteInd().getValue(),update.getDataMonitoringCommitteInd().getValue());
        assertEquals(saved.getDelayedpostingIndicator().getValue(),update.getDelayedpostingIndicator().getValue());
        assertEquals(saved.getExpandedAccessIndicator().getValue(),update.getExpandedAccessIndicator().getValue());
        assertEquals(saved.getFdaRegulatedIndicator().getValue(),update.getFdaRegulatedIndicator().getValue());
        assertEquals(saved.getIndIdeIndicator().getValue(),update.getIndIdeIndicator().getValue());
        assertEquals(saved.getOfficialTitle().getValue(),update.getOfficialTitle().getValue());
        assertEquals(saved.getPhaseCode().getCode(),update.getPhaseCode().getCode());
        assertNotNull(update.getIdentifier().getExtension());
    }

}
