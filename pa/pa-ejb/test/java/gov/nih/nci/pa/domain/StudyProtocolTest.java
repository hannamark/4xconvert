package gov.nih.nci.pa.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.Serializable;

import gov.nih.nci.pa.enums.AllocationCode;
import gov.nih.nci.pa.enums.BiospecimenRetentionCode;
import gov.nih.nci.pa.enums.ControlConcurrencyTypeCode;
import gov.nih.nci.pa.enums.ControlTypeCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.InterventionTypeCode;
import gov.nih.nci.pa.enums.MonitorCode;
import gov.nih.nci.pa.enums.PhaseCode;


import org.junit.Test;

/**
 * 
 * @author NAmiruddin
 *
 */
public class StudyProtocolTest extends CommonTest {
    
    /**
     * 
     */
    @Test
    public void createStudyProtocolTest() {
        
        StudyProtocol create = new StudyProtocol();
        try {
            create.setOfficialTitle("Breast Cancer..");
            create.setMonitorCode(MonitorCode.CCR);
            create.setPhaseCode(PhaseCode.I);
            create.setMonitorCode(MonitorCode.CCR);
            create.setPhaseCode(PhaseCode.I);
            create.setNumberOfInterventionGroups(new Integer(1));
            create.setControlConcurrencyTypeCode(ControlConcurrencyTypeCode.CONCURRENT);
            create.setAllocationCode(AllocationCode.NON_RANDOMIZED_TRIAL);
            create.setDelayedpostingIndicator(new Boolean(false));
            create.setDesignConfigurationCode(DesignConfigurationCode.FACTORIAL);
            create.setControlTypeCode(ControlTypeCode.PLACEBO);
            create.setFdaRegulatedIndicator(new Boolean(true));
            create.setSection801Indicator(new Boolean(true));
            create.setInterventionTypeCode(InterventionTypeCode.RADIATION);
            create.setBiospecimenDescription("blah blah");
            create.setBiospecimenRetentionCode(BiospecimenRetentionCode.RETAINED);
            create.setGroupNumber(new Integer(1));
            create.setStudyModelCode("SMC");
            create.setTimePerspectiveCode("TPC");
            
            Serializable cid = session.save(create);
            assertNotNull(cid);
            StudyProtocol saved = (StudyProtocol) session.load(StudyProtocol.class, cid);
            assertNotNull(saved);
            assertEquals("Official Title does not match " , create.getOfficialTitle() , saved.getOfficialTitle());
            assertEquals("Acronym does not match " , create.getAcronym(), saved.getAcronym());
            assertEquals("Monitor code does not match " , create.getMonitorCode(), saved.getMonitorCode());
            assertEquals("Phase code does not match " , create.getPhaseCode(), saved.getPhaseCode());
            assertEquals("Allocation code does not match " , 
                    create.getAllocationCode().getCode(), saved.getAllocationCode().getCode());
            assertEquals("Control Concurrency Type code does not match " , 
                    create.getControlConcurrencyTypeCode().getCode(), 
                    saved.getControlConcurrencyTypeCode().getCode());
            
            assertEquals("Control type code Does not match ", create.getControlTypeCode().getCode() , 
                                saved.getControlTypeCode().getCode());
            assertEquals("Delayed Posting Indicator does not match ", create.getDelayedpostingIndicator()
                    , saved.getDelayedpostingIndicator());
            assertEquals("Design Configuration Code Does not match ", create.getDesignConfigurationCode().getCode()
                    , saved.getDesignConfigurationCode().getCode());
            assertEquals("Fda Regulated Indicator Does not match ", create.getFdaRegulatedIndicator()
                    , saved.getFdaRegulatedIndicator());
            assertEquals("Intervention Type code Does not match ", create.getInterventionTypeCode().getCode()
                    , saved.getInterventionTypeCode().getCode());
            assertEquals("Number Of Intervention Groups Does not match ", create.getNumberOfInterventionGroups()
                    , saved.getNumberOfInterventionGroups());
            assertEquals("Section 801 Indicator Does not match ", create.getSection801Indicator()
                    , saved.getSection801Indicator());
            assertEquals("Bio Speciment Description does not match " , 
                    create.getBiospecimenDescription(), saved.getBiospecimenDescription());
            assertEquals("Bio Speciment Retention Code does not match " , 
                    create.getBiospecimenRetentionCode().getCode(), saved.getBiospecimenRetentionCode().getCode());
            assertEquals("Group Number Does not match ", create.getGroupNumber() , saved.getGroupNumber());
            assertEquals("Study Model code Does not match ", create.getStudyModelCode()
                    , saved.getStudyModelCode());
            assertEquals("Time Prespective code Does not match ", create.getTimePerspectiveCode()
                    , saved.getTimePerspectiveCode());
            
            
            System.out.println("..end..");
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static StudyProtocol createStudyProtocolObj() {
        StudyProtocol create = new StudyProtocol();
        create.setOfficialTitle("Breast Cancer..");
        create.setMonitorCode(MonitorCode.CCR);
        create.setPhaseCode(PhaseCode.I);
        create.setMonitorCode(MonitorCode.CCR);
        create.setPhaseCode(PhaseCode.I);
        create.setNumberOfInterventionGroups(new Integer(1));
        create.setControlConcurrencyTypeCode(ControlConcurrencyTypeCode.CONCURRENT);
        create.setAllocationCode(AllocationCode.NON_RANDOMIZED_TRIAL);
        create.setDelayedpostingIndicator(new Boolean(false));
        create.setDesignConfigurationCode(DesignConfigurationCode.FACTORIAL);
        create.setControlTypeCode(ControlTypeCode.PLACEBO);
        create.setFdaRegulatedIndicator(new Boolean(true));
        create.setSection801Indicator(new Boolean(true));
        create.setInterventionTypeCode(InterventionTypeCode.RADIATION);
        create.setBiospecimenDescription("blah blah");
        create.setBiospecimenRetentionCode(BiospecimenRetentionCode.RETAINED);
        create.setGroupNumber(new Integer(1));
        create.setStudyModelCode("SMC");
        create.setTimePerspectiveCode("TPC");
        
        return create ;
    }
}
