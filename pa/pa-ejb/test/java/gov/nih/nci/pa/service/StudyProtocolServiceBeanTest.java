package gov.nih.nci.pa.service;

//import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.iso.convert.InterventionalStudyProtocolConverter;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTOTest;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTOTest;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

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
        ispDTO.setIdentifier(ii);
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
        assertEquals(create.getDelayedpostingIndicator().getValue(),saved.getDelayedpostingIndicator().getValue());
        assertEquals(create.getExpandedAccessIndicator().getValue(),saved.getExpandedAccessIndicator().getValue());
        assertEquals(create.getFdaRegulatedIndicator().getValue(),saved.getFdaRegulatedIndicator().getValue());
        assertEquals(create.getOfficialTitle().getValue(),saved.getOfficialTitle().getValue());
        assertEquals(create.getPhaseCode().getCode(),saved.getPhaseCode().getCode());
        assertNotNull(saved.getIdentifier().getExtension());
    }

    @Test
    public void updateInterventionalStudyProtocol() throws Exception {
        InterventionalStudyProtocol create =
                StudyProtocolTest.createInterventionalStudyProtocolObj(new InterventionalStudyProtocol());
        
        InterventionalStudyProtocolDTO createDTO = InterventionalStudyProtocolConverter.convertFromDomainToDTO(create);

        Ii ii = remoteEjb.createInterventionalStudyProtocol(createDTO);
        assertNotNull(ii.getExtension());
        InterventionalStudyProtocolDTO saved =  remoteEjb.getInterventionalStudyProtocol(ii);

//        saved.setAcronym(StConverter.convertToSt("1234"));
//
//        InterventionalStudyProtocolDTO update =  remoteEjb.updateInterventionalStudyProtocol(saved);
//
//        assertNotNull(saved);
//        assertEquals(saved.getAccrualReportingMethodCode().getCode(), update.getAccrualReportingMethodCode().getCode());
//        assertEquals(saved.getAcronym().getValue(),update.getAcronym().getValue());
//        assertEquals(saved.getAllocationCode().getCode(),update.getAllocationCode().getCode());
//        assertEquals(saved.getDelayedpostingIndicator().getValue(),update.getDelayedpostingIndicator().getValue());
//        assertEquals(saved.getExpandedAccessIndicator().getValue(),update.getExpandedAccessIndicator().getValue());
//        assertEquals(saved.getFdaRegulatedIndicator().getValue(),update.getFdaRegulatedIndicator().getValue());
//        assertEquals(saved.getOfficialTitle().getValue(),update.getOfficialTitle().getValue());
//        assertEquals(saved.getPhaseCode().getCode(),update.getPhaseCode().getCode());
//        assertNotNull(update.getIdentifier().getExtension());
    }
    @Test
    public void getObservationalStudyProtocol() throws Exception {
        ObservationalStudyProtocolDTO create = 
            ObservationalStudyProtocolDTOTest.createObservationalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createObservationalStudyProtocol(create);
        assertNotNull(ii.getExtension());
        ObservationalStudyProtocolDTO saved =  remoteEjb.getObservationalStudyProtocol(ii);
        assertNotNull(saved);
        assertEquals(create.getStudyModelCode().getCode(),saved.getStudyModelCode().getCode());
        assertEquals(create.getTimePerspectiveCode().getCode(),saved.getTimePerspectiveCode().getCode());
        assertEquals(create.getBiospecimenDescription().getValue(),saved.getBiospecimenDescription().getValue());
        assertEquals(create.getBiospecimenRetentionCode().getCode(),saved.getBiospecimenRetentionCode().getCode());
        assertEquals(create.getNumberOfGroups().getValue() ,saved.getNumberOfGroups().getValue());
        assertNotNull(saved.getIdentifier().getExtension());
    }
    @Test
    public void updateObservationalStudyProtocol() throws Exception {
        ObservationalStudyProtocolDTO create = 
            ObservationalStudyProtocolDTOTest.createObservationalStudyProtocolDTOObj();
        Ii ii = remoteEjb.createObservationalStudyProtocol(create);
        assertNotNull(ii.getExtension());
        ObservationalStudyProtocolDTO saved =  remoteEjb.getObservationalStudyProtocol(ii);
        
        saved.setMaximumTargetAccrualNumber(IntConverter.convertToInt(1234));
        
        ObservationalStudyProtocolDTO update =  remoteEjb.updateObservationalStudyProtocol(saved);

        assertNotNull(saved);
        assertEquals(update.getStudyModelCode().getCode(),saved.getStudyModelCode().getCode());
        assertEquals(update.getTimePerspectiveCode().getCode(),saved.getTimePerspectiveCode().getCode());
        assertEquals(update.getBiospecimenDescription().getValue(),saved.getBiospecimenDescription().getValue());
        assertEquals(update.getBiospecimenRetentionCode().getCode(),saved.getBiospecimenRetentionCode().getCode());
        assertEquals(update.getNumberOfGroups().getValue() ,saved.getNumberOfGroups().getValue());
        assertEquals(update.getMaximumTargetAccrualNumber().getValue() ,saved.getMaximumTargetAccrualNumber().getValue());
        assertNotNull(update.getIdentifier().getExtension());
    }
    @Test
    public void nullInDatesTest() throws Exception {
        StudyProtocol sp = new StudyProtocol();   
        sp.setOfficialTitle("cacncer for THOLA");
        sp.setStartDate(PAUtil.dateStringToTimestamp("1/1/2000"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(PAUtil.dateStringToTimestamp("12/31/2009"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        TestSchema.addUpdObject(sp);
        
        StudyProtocolDTO spDto = remoteEjb.getStudyProtocol(IiConverter.convertToIi(sp.getId()));
        spDto.setStartDate(null);
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for null in start date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto.setStartDate(TsConverter.convertToTs(null));
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for Ts null in start date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto = remoteEjb.getStudyProtocol(IiConverter.convertToIi(sp.getId()));
        spDto.setPrimaryCompletionDate(null);
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for null in completion date.");
        } catch (PAException e) {
            // expected behavior
        }
        spDto.setPrimaryCompletionDate(TsConverter.convertToTs(null));
        try {
            remoteEjb.updateStudyProtocol(spDto);
            fail("PAException should have been thrown for Ts null in completion date.");
        } catch (PAException e) {
            // expected behavior
        }
    }
}
