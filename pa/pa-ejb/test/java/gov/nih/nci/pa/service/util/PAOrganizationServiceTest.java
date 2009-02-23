package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyParticipation;
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.domain.StudyParticipationTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.dto.OrganizationDTO;
import gov.nih.nci.pa.enums.StatusCode;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.TestSchema;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class PAOrganizationServiceTest {

    private PAOrganizationServiceBean bean = new PAOrganizationServiceBean();
    private PAOrganizationServiceRemote remoteEjb = bean;

    @Before
    public void setUp() throws Exception {
        TestSchema.reset();
        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = PersonTest.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StatusCode.ACTIVE);
        TestSchema.addUpdObject(ro);
        StudyParticipation spc = StudyParticipationTest.createStudyParticipationObj(sp, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        
        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);
        StudyParticipationContact spcc = new StudyParticipationContact();
        spcc.setClinicalResearchStaff(crs);
        spcc.setRoleCode(StudyParticipationContactRoleCode.SUBMITTER);
        spcc.setStudyParticipation(spc);
        spcc.setStudyProtocol(sp);
        spcc.setStatusCode(StatusCode.ACTIVE);
        TestSchema.addUpdObject(spcc);
    }
    
    @Test
    public void getOrganizationsAssociatedWithStudyProtocolTest() throws Exception {
        List<OrganizationDTO> data = remoteEjb.getOrganizationsAssociatedWithStudyProtocol();
        assertNotNull(data);
        assertEquals("Size does not match  " , data.size(), 1);
        assertEquals(" name does not match   " , data.get(0).getName(), "Mayo University");
    }
    
    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        remoteEjb.getOrganizationByIndetifers(new Organization());
    }

    @Test
    public void getOrganizationByIndetifersTest() throws Exception {
        Organization o = new Organization();
        o.setId(Long.valueOf(1));
        Organization data = remoteEjb.getOrganizationByIndetifers(o);
        assertNotNull(data);
        assertEquals(" name does not match   " , data.getName(), "Mayo University");
        
    }

        

}
