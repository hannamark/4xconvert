package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.CtrpHibernateHelper;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.MockPaRegistryServiceLocator;
import gov.nih.nci.pa.util.MockPoServiceLocator;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.TestHibernateHelper;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.Test;

public class ParticipatingSiteServiceTest {

    private final ParticipatingSiteBeanLocal bean = new ParticipatingSiteBeanLocal();
    StudyProtocolServiceLocal studyProtocolService = new StudyProtocolBeanLocal();
    StudySiteServiceLocal studySiteService = new StudySiteBeanLocal();
    StudySiteContactServiceLocal studySiteContactService = new StudySiteContactBeanLocal(); 
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService = new StudySiteAccrualStatusBeanLocal();
    OrganizationCorrelationServiceRemote ocsr = new OrganizationCorrelationServiceBean();
    private static CtrpHibernateHelper testHelper = new TestHibernateHelper();
    @Before
    public void setUp() throws Exception {
        CSMUserService.setRegistryUserService(new MockCSMUserService());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
        PaRegistry.getInstance().setServiceLocator(new MockPaRegistryServiceLocator());
        bean.setStudyProtocolService(studyProtocolService);
        bean.setStudySiteService(studySiteService);
        bean.setStudySiteContactService(studySiteContactService);
        bean.setStudySiteAccrualStatusService(studySiteAccrualStatusService);
        bean.setOcsr(ocsr);
        HibernateUtil.setTestHelper(testHelper);
        Session session = HibernateUtil.getCurrentSession();
        session.clear();
    }
    
    @Test
    public void createAndUpdateSiteForPropTrialTest() throws Exception {
        
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        
        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        session.saveOrUpdate(user);
        transaction.commit();
        
        
        List<String> telList = new ArrayList<String>();
        telList.add("111-222-3333");
        List<String> emailList = new ArrayList<String>();
        emailList.add("aaa@bbb.com");
        
        StudyProtocol sp = new InterventionalStudyProtocol();
        sp.setOfficialTitle("cacncer for THOLA");
        sp.setStartDate(ISOUtil.dateStringToTimestamp("1/1/2000"));
        sp.setStartDateTypeCode(ActualAnticipatedTypeCode.ACTUAL);
        sp.setPrimaryCompletionDate(ISOUtil.dateStringToTimestamp("12/31/2009"));
        sp.setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode.ANTICIPATED);
        sp.setPrimaryPurposeCode(PrimaryPurposeCode.TREATMENT);
        sp.setAccrualReportingMethodCode(AccrualReportingMethodCode.ABBREVIATED);
        Set<Ii> studySecondaryIdentifiers =  new HashSet<Ii>();
        Ii spSecId = new Ii();
        spSecId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        spSecId.setIdentifierName(IiConverter.STUDY_PROTOCOL_IDENTIFIER_NAME);
        spSecId.setExtension("NCI-2010-00099");
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(Boolean.TRUE);
        sp.setCtgovXmlRequiredIndicator(Boolean.TRUE);
        sp.setStatusCode(ActStatusCode.ACTIVE);
        
        transaction = session.beginTransaction();
        session.saveOrUpdate(sp);
        transaction.commit();
        
        Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(sp.getId());
        StudyProtocolDTO spDTO = studyProtocolService.getStudyProtocol(studyProtocolIi);
        assertEquals(spDTO.getOfficialTitle().getValue(), "cacncer for THOLA");
        
        DocumentWorkflowStatus dws = new DocumentWorkflowStatus();
        dws.setStatusCode(DocumentWorkflowStatusCode.SUBMITTED);
        dws.setStudyProtocol(sp);
        
        transaction = session.beginTransaction();
        session.saveOrUpdate(dws);
        transaction.commit();
        
        OrganizationDTO org = new OrganizationDTO();
        org.setName(EnOnConverter.convertToEnOn("my org"));
        org.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        org.setPostalAddress(
                AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                        "Rockville", "MD", "20855", "USA"));
        org.setTelecomAddress(
                DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        org.setTelecomAddress(
                DSetConverter.convertListToDSet(emailList, "EMAIL", org.getTelecomAddress()));
        
        
        PersonDTO person = new PersonDTO();
        person.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person.setName(EnPnConverter.convertToEnPn("first", "middle", "lastName", "prefix", "suffix"));
        person.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                "Rockville", "MD", "20855", "USA"));
        person.setSexCode(CdConverter.convertStringToCd("MALE"));
        person.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person.setTelecomAddress(
                DSetConverter.convertListToDSet(emailList, "EMAIL", org.getTelecomAddress()));
        
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime() - Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LOCAL SP ID"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("PROGRAM CODE"));
        
        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));
        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - Long.valueOf("300000000"))));
        
        Ii studySiteIi = bean.createStudySiteParticipantForPropTrial(spSecId, org,
                studySiteDTO, currentStatus, person);
        
        StudySiteDTO freshSiteDTO = studySiteService.get(studySiteIi);
        assertEquals("Treating Site", freshSiteDTO.getFunctionalCode().getCode());
        
        
        StudySiteAccrualStatusDTO freshAccStatus = 
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(studySiteIi);
        assertEquals(RecruitmentStatusCode.RECRUITING.getCode(), freshAccStatus.getStatusCode().getCode());
        
        List<StudySiteContactDTO> contList = studySiteContactService.getByStudySite(studySiteIi);
        assertEquals(1, contList.size());
        
        // mimic ctep synch and update
        
        HealthCareFacilityDTO hcfDTO = 
            PoRegistry.getHealthCareFacilityCorrelationService().getCorrelation(freshSiteDTO.getHealthcareFacilityIi());
        hcfDTO.setIdentifier(new DSet<Ii>());
        hcfDTO.getIdentifier().setItem(new HashSet<Ii>());
        Ii ctepIdForOrg = new Ii();
        ctepIdForOrg.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepIdForOrg.setExtension("TEST_CTEP_ORG1");
        hcfDTO.getIdentifier().getItem().add(ctepIdForOrg);
        PoRegistry.getHealthCareFacilityCorrelationService().updateCorrelation(hcfDTO);
        
        
        // test update.
        studySiteDTO.setIdentifier(null);
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                .convertToIvl(new Timestamp(new Date().getTime() - Long.valueOf("300000000")),
                        new Timestamp(new Date().getTime() - Long.valueOf("200000000"))));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("changedLocalSp"));
        currentStatus.setIdentifier(null);
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.COMPLETED.getCode()));
        
        PersonDTO person2 = new PersonDTO();
        person2.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person2.setName(EnPnConverter.convertToEnPn("first2", "middle2", "lastName2", "prefix", "suffix"));
        person2.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.", 
                "Rockville", "MD", "20855", "USA"));
        person2.setSexCode(CdConverter.convertStringToCd("MALE"));
        person2.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person2.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person2.setTelecomAddress(
                DSetConverter.convertListToDSet(emailList, "EMAIL", org.getTelecomAddress()));
                   
        bean.updateStudySiteParticipantForPropTrial(spSecId, ctepIdForOrg,
                studySiteDTO, currentStatus, person2);
        
        StudySiteDTO fresherSiteDTO = studySiteService.get(studySiteIi);
        assertEquals("changedLocalSp", fresherSiteDTO.getLocalStudyProtocolIdentifier().getValue());
        
        StudySiteAccrualStatusDTO fresherAccStatus = 
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(studySiteIi);
        assertEquals(RecruitmentStatusCode.COMPLETED.getCode(), fresherAccStatus.getStatusCode().getCode());
          
        assertTrue(bean.isParticipatingSite(spSecId, ctepIdForOrg).getValue());
    }

}
