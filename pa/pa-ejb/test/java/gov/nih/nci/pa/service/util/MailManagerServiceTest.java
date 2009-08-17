/**
 * 
 */
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.ClinicalResearchStaff;
import gov.nih.nci.pa.domain.ClinicalResearchStaffTest;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.CountryTest;
import gov.nih.nci.pa.domain.DocumentWorkFlowStatusTest;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.HealthCareFacilityTest;
import gov.nih.nci.pa.domain.HealthCareProvider;
import gov.nih.nci.pa.domain.HealthCareProviderTest;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationTest;
import gov.nih.nci.pa.domain.PAProperties;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.PersonTest;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyContactTest;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteTest;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocolTest;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatusTest;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceBean;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.TestSchema;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Vrushali
 *
 */
public class MailManagerServiceTest {
    private MailManagerServiceBean  bean = new MailManagerServiceBean ();
    ProtocolQueryServiceLocal protocolQrySrv = new ProtocolQueryServiceBean();
    RegistryUserServiceRemote registryUserSrv = new RegistryUserServiceBean();
    CTGovXmlGeneratorServiceRemote ctGovXmlSrv = new CTGovXmlGeneratorServiceBean ();
    TSRReportGeneratorServiceRemote tsrReptSrv = new TSRReportGeneratorServiceBean();
    LookUpTableServiceRemote lookUpTableSrv = new LookUpTableServiceBean();
    DocumentWorkflowStatusServiceRemote docWrkStatSrv = new DocumentWorkflowStatusServiceBean();
    @Before
    public void setUp() throws Exception {  
        TestSchema.reset();
        bean.ctGovXmlGeneratorService = ctGovXmlSrv;
        bean.protocolQueryService = protocolQrySrv;
        bean.registryUserService = registryUserSrv;
        bean.tsrReportGeneratorService = tsrReptSrv;
        bean.lookUpTableService = lookUpTableSrv;
        bean.docWrkflStatusSrv = docWrkStatSrv;

        StudyProtocol sp = StudyProtocolTest.createStudyProtocolObj();
        TestSchema.addUpdObject(sp);
        Organization o = OrganizationTest.createOrganizationObj();
        TestSchema.addUpdObject(o);
        Person p = PersonTest.createPersonObj();
        p.setIdentifier("11");
        TestSchema.addUpdObject(p);
        HealthCareFacility hcf = HealthCareFacilityTest.createHealthCareFacilityObj(o);
        TestSchema.addUpdObject(hcf);
        
        HealthCareProvider hcp = HealthCareProviderTest.createHealthCareProviderObj(p, o);
        TestSchema.addUpdObject(hcp);
        
        Country c = CountryTest.createCountryObj();
        TestSchema.addUpdObject(c);
        
        ClinicalResearchStaff crs = ClinicalResearchStaffTest.createClinicalResearchStaffObj(o, p);
        TestSchema.addUpdObject(crs);

        StudyContact sc = StudyContactTest.createStudyContactObj(sp, c, hcp, crs);
        TestSchema.addUpdObject(sc);
        
        ResearchOrganization ro = new ResearchOrganization();
        ro.setOrganization(o);
        ro.setStatusCode(StructuralRoleStatusCode.ACTIVE);
        ro.setIdentifier("abc");
        TestSchema.addUpdObject(ro);
        StudySite spc = StudySiteTest.createStudySiteObj(sp, hcf);
        spc.setResearchOrganization(ro);
        TestSchema.addUpdObject(spc);
        StudyRecruitmentStatus studyRecStatus = StudyRecruitmentStatusTest.createStudyRecruitmentStatusobj(sp);
        TestSchema.addUpdObject(studyRecStatus);
        
        PAProperties prop = new PAProperties();
        prop.setName("tsr.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${leadOrgTrialId}, ${trialTitle}, (${fileName}), ${fileName2}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("rejection.body");
        prop.setValue("${leadOrgTrialId} ${reasoncode}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.amend.reject.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate},${reasonForRejection}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.register.body");
        prop.setValue("${SubmitterName} ${nciTrialIdentifier}, ${leadOrgTrialIdentifier}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.register.subject");
        prop.setValue("trial.register.subject.");
        TestSchema.addUpdObject(prop);

        prop = new PAProperties();
        prop.setName("trial.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.amend.accept.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);
        
        
        prop = new PAProperties();
        prop.setName("trial.amend.accept.subject");
        prop.setValue("trial.amend.accept.subject.");
        TestSchema.addUpdObject(prop);
        
        
        prop = new PAProperties();
        prop.setName("trial.amend.body");
        prop.setValue("${CurrentDate} ${SubmitterName}${nciTrialIdentifier}, ${title}, (${amendmentNumber}), ${amendmentDate}.");
        TestSchema.addUpdObject(prop);
        
        prop = new PAProperties();
        prop.setName("trial.amend.subject");
        prop.setValue("trial.amend.subject.");
        TestSchema.addUpdObject(prop);
        
        RegistryUser registryUser = new RegistryUser();
        registryUser.setFirstName("firstName");
        registryUser.setLastName("lastName");
        registryUser.setUserLastCreated("Abstractor");
        TestSchema.addUpdObject(registryUser);
        
        DocumentWorkflowStatus docWrk = DocumentWorkFlowStatusTest.createDocumentWorkflowStatus(sp);
        TestSchema.addUpdObject(docWrk);

    }
    @Test (expected=PAException.class)
    public void testSendTSREmail() throws PAException{
        bean.sendTSREmail(IiConverter.convertToIi(1L));
    }
    @Test (expected=PAException.class)
    public void testSendAcceptEmail() throws PAException{
        bean.sendAcceptEmail(IiConverter.convertToIi(1L));
    }
    @Test (expected=PAException.class)
    public void testSendAmendAcceptEmail() throws PAException{
        bean.sendAmendAcceptEmail(IiConverter.convertToIi(1L));
    }
    @Test (expected=PAException.class)
    public void testSendAmendNotificationEmail() throws PAException{
        bean.sendAmendNotificationMail(IiConverter.convertToIi(1L));
    }
    @Test (expected=PAException.class)
    public void testSendAmendRejectEmail() throws PAException{
        bean.sendAmendRejectEmail(IiConverter.convertToIi(1L), "rejectReason");
    }
    @Test (expected=PAException.class)
    public void testSendNotificationMail() throws PAException{
        bean.sendNotificationMail(IiConverter.convertToIi(1L));
    }
    @Test (expected=PAException.class)
    public void testSendRejectionEmail() throws PAException{
        bean.sendRejectionEmail(IiConverter.convertToIi(1L));
    }
}
