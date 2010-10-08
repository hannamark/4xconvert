package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.IdentifierReliability;
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
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.iso.dto.ParticipatingSiteDTO;
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
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
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
    ParticipatingSiteServiceLocal localBean = null;
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
        bean.setSessionContext(new MockStatelessContext());
        localBean = bean;
        HibernateUtil.setTestHelper(testHelper);
        Session session = HibernateUtil.getCurrentSession();
        session.clear();
    }

    private Ii prepareStudyProtocol(String nciId, boolean isPropTrial)
        throws PAException {
        Session session = HibernateUtil.getCurrentSession();
        Transaction transaction = session.beginTransaction();

        User user = new User();
        user.setLoginName("Abstractor: " + new Date());
        user.setFirstName("Joe");
        user.setLastName("Smith");
        user.setUpdateDate(new Date());
        session.saveOrUpdate(user);
        transaction.commit();

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
        spSecId.setExtension(nciId);
        studySecondaryIdentifiers.add(spSecId);
        sp.setOtherIdentifiers(studySecondaryIdentifiers);
        sp.setSubmissionNumber(Integer.valueOf(1));
        sp.setProprietaryTrialIndicator(isPropTrial);
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

        return spSecId;

    }

    private OrganizationDTO getOrg1() {
        List<String> telList = new ArrayList<String>();
        telList.add("111-222-3333");
        List<String> emailList = new ArrayList<String>();
        emailList.add("aaa@bbb.com");


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
        return org;
    }

    private PersonDTO getPerson1() {
        List<String> telList = new ArrayList<String>();
        telList.add("222-333-4444");
        List<String> emailList = new ArrayList<String>();
        emailList.add("bbb@ccc.com");


        PersonDTO person = new PersonDTO();
        person.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person.setName(EnPnConverter.convertToEnPn("first", "middle", "lastName", "prefix", "suffix"));
        person.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.",
                "Rockville", "MD", "20855", "USA"));
        person.setSexCode(CdConverter.convertStringToCd("MALE"));
        person.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person.setTelecomAddress(
                DSetConverter.convertListToDSet(emailList, "EMAIL", person.getTelecomAddress()));
        return person;
    }

    private PersonDTO getPerson2() {
        List<String> telList = new ArrayList<String>();
        telList.add("333-444-5555");
        List<String> emailList = new ArrayList<String>();
        emailList.add("ccc@ddd.com");

        PersonDTO person2 = new PersonDTO();
        person2.setBirthDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
        person2.setName(EnPnConverter.convertToEnPn("first2", "middle2", "lastName2", "prefix", "suffix"));
        person2.setPostalAddress(AddressConverterUtil.create("1000 Some St.", "1000 Some St.",
                "Rockville", "MD", "20855", "USA"));
        person2.setSexCode(CdConverter.convertStringToCd("MALE"));
        person2.setStatusCode(CdConverter.convertStringToCd("PENDING"));
        person2.setTelecomAddress(DSetConverter.convertListToDSet(telList, "PHONE", new DSet<Tel>()));
        person2.setTelecomAddress(
                DSetConverter.convertListToDSet(emailList, "EMAIL", person2.getTelecomAddress()));
        return person2;
    }

    private StudySiteDTO getBasicStudySiteDTO(Ii spIi) {
        StudySiteDTO studySiteDTO = new StudySiteDTO();
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime() - Long.valueOf("300000000")), null));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("LOCAL SP ID"));
        studySiteDTO.setProgramCodeText(StConverter.convertToSt("PROGRAM CODE"));
        studySiteDTO.setStudyProtocolIdentifier(spIi);
        return studySiteDTO;
    }

    private StudySiteAccrualStatusDTO getStatusDTO() {
        StudySiteAccrualStatusDTO currentStatus = new StudySiteAccrualStatusDTO();
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.RECRUITING.getCode()));

        currentStatus.setStatusDate(TsConverter.convertToTs(new Timestamp(new Date().getTime() - Long.valueOf("300000000"))));
        return currentStatus;
    }

    private Ii mimicCtepSynch(Ii hcfIi, String ctepExt)
    throws NullifiedRoleException, PAException, EntityValidationException {
        // mimic ctep synch and update

        HealthCareFacilityDTO hcfDTO =
            PoRegistry.getHealthCareFacilityCorrelationService()
            .getCorrelation(hcfIi);
        hcfDTO.setIdentifier(new DSet<Ii>());
        hcfDTO.getIdentifier().setItem(new HashSet<Ii>());
        Ii ctepIdForOrg = new Ii();
        ctepIdForOrg.setRoot(IiConverter.CTEP_ORG_IDENTIFIER_ROOT);
        ctepIdForOrg.setExtension(ctepExt);
        ctepIdForOrg.setReliability(IdentifierReliability.ISS);
        hcfDTO.getIdentifier().getItem().add(ctepIdForOrg);
        PoRegistry.getHealthCareFacilityCorrelationService().updateCorrelation(hcfDTO);
        return ctepIdForOrg;
    }


    @Test
    public void createAndUpdateSiteForPropTrialTest() throws Exception {

        Ii spSecId = prepareStudyProtocol("NCI-2010-00088", true);
        OrganizationDTO org = getOrg1();
        PersonDTO person = getPerson1();
        StudySiteDTO studySiteDTO = getBasicStudySiteDTO(spSecId);
        StudySiteAccrualStatusDTO currentStatus = getStatusDTO();

        ParticipatingSiteDTO dto = localBean.createStudySiteParticipant(studySiteDTO, currentStatus, org, null);
        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null, person,
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());

        StudySiteDTO freshSiteDTO = studySiteService.get(dto.getIdentifier());

        assertEquals("Treating Site", freshSiteDTO.getFunctionalCode().getCode());

        StudySiteAccrualStatusDTO freshAccStatus =
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.RECRUITING.getCode(), freshAccStatus.getStatusCode().getCode());

        List<StudySiteContactDTO> contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(1, contList.size());

        // mimic ctep synch and update
        Ii ctepIdForOrg = mimicCtepSynch(freshSiteDTO.getHealthcareFacilityIi(), "TEST_CTEP_ORG1");

        // test update.
        studySiteDTO.setIdentifier(null);
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                .convertToIvl(new Timestamp(new Date().getTime() - Long.valueOf("300000000")),
                        new Timestamp(new Date().getTime() - Long.valueOf("200000000"))));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("changedLocalSp"));
        studySiteDTO.setStudyProtocolIdentifier(spSecId);
        currentStatus.setIdentifier(null);
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.COMPLETED.getCode()));

        PersonDTO person2 = getPerson2();

        Ii oldIi = localBean.getParticipatingSiteIi(spSecId, ctepIdForOrg);
        studySiteDTO.setIdentifier(oldIi);

        localBean.updateStudySiteParticipant(studySiteDTO, currentStatus);
        localBean.addStudySitePrimaryContact(oldIi, null, null, person2, null);


        StudySiteDTO fresherSiteDTO = studySiteService.get(dto.getIdentifier());
        assertEquals("changedLocalSp", fresherSiteDTO.getLocalStudyProtocolIdentifier().getValue());

        StudySiteAccrualStatusDTO fresherAccStatus =
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.COMPLETED.getCode(), fresherAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(2, contList.size());

        List<ParticipatingSiteDTO> sites = localBean.getParticipatingSitesByStudyProtocol(spSecId);
        assertTrue(sites.size() == 1);
        assertEquals(oldIi.getExtension(), sites.get(0).getIdentifier().getExtension());
    }

    @Test
    public void createAndUpdateSiteForNonPropTrialTest() throws Exception {

        Ii spSecId = prepareStudyProtocol("NCI-2010-00099", false);
        OrganizationDTO org = getOrg1();
        PersonDTO person = getPerson1();
        PersonDTO person2 = getPerson2();
        StudySiteDTO studySiteDTO = getBasicStudySiteDTO(spSecId);
        StudySiteAccrualStatusDTO currentStatus = getStatusDTO();

        ParticipatingSiteDTO dto = localBean.createStudySiteParticipant(studySiteDTO, currentStatus, org, null);
        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null,
                person, StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode());
        List<StudySiteContactDTO> contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(1, contList.size());

        localBean.addStudySiteInvestigator(dto.getIdentifier(), null, null,
                person2, StudySiteContactRoleCode.SUB_INVESTIGATOR.getCode());
        List<ClinicalResearchStaffDTO> crsDTOs =
            PoRegistry.getClinicalResearchStaffCorrelationService().getCorrelationsByPlayerIds(new Ii[]{person2.getIdentifier()});
        List<HealthCareProviderDTO> hcpDTOs =
            PoRegistry.getHealthCareProviderCorrelationService().getCorrelationsByPlayerIds(new Ii[]{person2.getIdentifier()});
        localBean.addStudySitePrimaryContact(dto.getIdentifier(), crsDTOs.get(0), hcpDTOs.get(0), null, person2.getTelecomAddress());

        StudySiteDTO freshSiteDTO = studySiteService.get(dto.getIdentifier());

        assertEquals("Treating Site", freshSiteDTO.getFunctionalCode().getCode());

        StudySiteAccrualStatusDTO freshAccStatus =
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.RECRUITING.getCode(), freshAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        // 1 princ, 1 sub, 1 prim
        assertEquals(3, contList.size());

        // mimic ctep synch and update
        Ii ctepIdForOrg = mimicCtepSynch(freshSiteDTO.getHealthcareFacilityIi(), "TEST_CTEP_ORG2");

        // test update.
        studySiteDTO.setIdentifier(null);
        studySiteDTO.setAccrualDateRange(IvlConverter.convertTs()
                .convertToIvl(new Timestamp(new Date().getTime() - Long.valueOf("300000000")),
                        new Timestamp(new Date().getTime() - Long.valueOf("200000000"))));
        studySiteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt("changedLocalSp"));
        studySiteDTO.setStudyProtocolIdentifier(spSecId);
        currentStatus.setIdentifier(null);
        currentStatus.setStatusCode(CdConverter.convertStringToCd(RecruitmentStatusCode.COMPLETED.getCode()));



        Ii oldIi = localBean.getParticipatingSiteIi(spSecId, ctepIdForOrg);
        studySiteDTO.setIdentifier(oldIi);

        localBean.updateStudySiteParticipant(studySiteDTO, currentStatus);
        localBean.addStudySitePrimaryContact(oldIi, null, null, person, null);


        StudySiteDTO fresherSiteDTO = studySiteService.get(dto.getIdentifier());
        assertEquals("changedLocalSp", fresherSiteDTO.getLocalStudyProtocolIdentifier().getValue());

        StudySiteAccrualStatusDTO fresherAccStatus =
            studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(dto.getIdentifier());
        assertEquals(RecruitmentStatusCode.COMPLETED.getCode(), fresherAccStatus.getStatusCode().getCode());

        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(3, contList.size());
        // check that the primary contact has been overwritten w/ generic data.
        OrganizationalContactDTO orgDTO = new OrganizationalContactDTO();
        orgDTO.setTitle(StConverter.convertToSt("titleOrgContact"));
        localBean.addStudySiteGenericContact(oldIi, orgDTO,
                true, null);
        contList = studySiteContactService.getByStudySite(dto.getIdentifier());
        assertEquals(3, contList.size());
        for (StudySiteContactDTO item : contList) {
            if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().equals(item.getRoleCode().getCode())) {
                assertNull(item.getClinicalResearchStaffIi());
                assertNull(item.getHealthCareProviderIi());
                assertNotNull(item.getOrganizationalContactIi());
            }
        }
    }

}
