/**
 * 
 */
package gov.nih.nci.pa.service.util;

import static gov.nih.nci.iso21090.EntityNamePartType.FAM;
import static gov.nih.nci.iso21090.EntityNamePartType.GIV;
import static gov.nih.nci.iso21090.EntityNamePartType.PFX;
import static gov.nih.nci.iso21090.EntityNamePartType.SFX;
import static gov.nih.nci.pa.iso.util.EnPnConverter.convertToEnPn;
import static gov.nih.nci.pa.iso.util.EnPnConverter.getNamePart;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.EnPnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.AbstractTrialRegistrationTestBase;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

/**
 * @author Denis G. Krylov
 * 
 */
public class CTGovSyncServiceBeanTest extends AbstractTrialRegistrationTestBase {

    private CTGovSyncServiceBean serviceBean = new CTGovSyncServiceBean();

    private HttpServer server;
    public static final int CTGOV_API_MOCK_PORT = 51235;

    private static int poIdCounter = 100;

    private static final Map<String, String> poIdToNameMap = new HashMap<String, String>();

    private static final Map<String, ClinicalResearchStaffDTO> crsMap = new HashMap<String, ClinicalResearchStaffDTO>();

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        injectBeanReferences();

        setUpCtgovUser();

        setUpPoMocks();

        setUpCorrelationMocks();

        startNctApiMock();

        PaHibernateUtil.disableAudit();
    }

    /**
     * @throws PAException
     */
    private void setUpCorrelationMocks() throws PAException {
        when(ocsr.createResearchOrganizationCorrelations(any(String.class)))
                .thenAnswer(new Answer<Long>() {
                    @Override
                    public Long answer(InvocationOnMock invocation)
                            throws Throwable {
                        String poOrgId = (String) invocation.getArguments()[0];
                        Organization org = new Organization();
                        org.setIdentifier(poOrgId);
                        org.setStatusCode(EntityStatusCode.PENDING);
                        org.setName(poIdToNameMap.get(poOrgId));
                        PaHibernateUtil.getCurrentSession().save(org);
                        PaHibernateUtil.getCurrentSession().flush();

                        ResearchOrganization ro = new ResearchOrganization();
                        ro.setOrganization(org);
                        ro.setStatusCode(StructuralRoleStatusCode.PENDING);
                        ro.setIdentifier(poOrgId);
                        PaHibernateUtil.getCurrentSession().save(ro);
                        PaHibernateUtil.getCurrentSession().flush();

                        return ro.getId();
                    }
                });
    }

    /**
     * @throws EntityValidationException
     * @throws CurationException
     * @throws NullifiedEntityException
     * @throws NullifiedRoleException
     */
    private void setUpPoMocks() throws EntityValidationException,
            CurationException, NullifiedEntityException, NullifiedRoleException {
        when(poOrgSvc.createOrganization(any(OrganizationDTO.class)))
                .thenAnswer(new Answer<Ii>() {
                    @Override
                    public Ii answer(InvocationOnMock invocation)
                            throws Throwable {
                        OrganizationDTO orgDTO = (OrganizationDTO) invocation
                                .getArguments()[0];
                        final String poOrgId = (poIdCounter++) + "";
                        poIdToNameMap.put(poOrgId, EnOnConverter
                                .convertEnOnToString(orgDTO.getName()));
                        return IiConverter.convertToPoOrganizationIi(poOrgId);
                    }
                });
        when(poOrgSvc.getOrganization(any(Ii.class))).thenAnswer(
                new Answer<OrganizationDTO>() {
                    @Override
                    public OrganizationDTO answer(InvocationOnMock invocation)
                            throws Throwable {
                        Ii ii = (Ii) invocation.getArguments()[0];
                        String poOrgId = ii.getExtension();
                        String name = poIdToNameMap.get(poOrgId);
                        if (name == null) {
                            return null;
                        }
                        OrganizationDTO dto = new OrganizationDTO();
                        dto.setName(EnOnConverter.convertToEnOn(name));
                        dto.setStatusCode(CdConverter
                                .convertToCd(EntityStatusCode.PENDING));
                        dto.setIdentifier(ii);
                        return dto;
                    }
                });
        when(poPersonSvc.getPerson(any(Ii.class))).thenAnswer(
                new Answer<PersonDTO>() {
                    @Override
                    public PersonDTO answer(InvocationOnMock invocation)
                            throws Throwable {
                        Ii ii = (Ii) invocation.getArguments()[0];
                        String poOrgId = ii.getExtension();
                        String name = poIdToNameMap.get(poOrgId);
                        if (name == null) {
                            return null;
                        }
                        PersonDTO dto = new PersonDTO();
                        dto.setName(EnPnConverter.convertToEnPn("", "", name,
                                null, null));
                        dto.setStatusCode(CdConverter
                                .convertToCd(EntityStatusCode.PENDING));
                        dto.setIdentifier(ii);
                        return dto;
                    }
                });
        when(poPersonSvc.createPerson(any(PersonDTO.class))).thenAnswer(
                new Answer<Ii>() {
                    @Override
                    public Ii answer(InvocationOnMock invocation)
                            throws Throwable {
                        PersonDTO personDTO = (PersonDTO) invocation
                                .getArguments()[0];
                        final String poPersonId = (poIdCounter++) + "";
                        String name = EnPnConverter
                                .convertToLastCommaFirstName(personDTO
                                        .getName());
                        poIdToNameMap.put(poPersonId, name);
                        return IiConverter.convertToPoPersonIi(poPersonId);
                    }
                });

        when(crsSvc.createCorrelation(any(ClinicalResearchStaffDTO.class)))
                .thenAnswer(new Answer<Ii>() {
                    @Override
                    public Ii answer(InvocationOnMock invocation)
                            throws Throwable {
                        ClinicalResearchStaffDTO crs = (ClinicalResearchStaffDTO) invocation
                                .getArguments()[0];
                        final String poId = (poIdCounter++) + "";
                        crsMap.put(poId, crs);
                        Ii ii = IiConverter
                                .convertToPoClinicalResearchStaffIi(poId);
                        DSet<Ii> dset = new DSet<Ii>();
                        dset.setItem(new java.util.HashSet<Ii>());
                        dset.getItem().add(ii);
                        crs.setIdentifier(dset);
                        crs.setStatus(CdConverter
                                .convertToCd(EntityStatusCode.PENDING));
                        return ii;
                    }
                });

        when(crsSvc.getCorrelation(any(Ii.class))).thenAnswer(
                new Answer<ClinicalResearchStaffDTO>() {
                    @Override
                    public ClinicalResearchStaffDTO answer(
                            InvocationOnMock invocation) throws Throwable {
                        Ii ii = (Ii) invocation.getArguments()[0];
                        String crsId = ii.getExtension();
                        if (crsMap.get(crsId) != null) {
                            return crsMap.get(crsId);
                        }
                        return null;
                    }
                });
    }

    /**
     * 
     */
    private void injectBeanReferences() {
        serviceBean.setLookUpTableService(lookUpTableServiceRemote);
        serviceBean.setRegulatoryAuthorityService(regulatoryInfoSvc);
        serviceBean.setTrialRegistrationService(super.bean);
        serviceBean.setStudyProtocolService(studyProtocolService);
        serviceBean.setRegistryUserService(new MockRegistryUserServiceBean());
        serviceBean.setDocumentWorkflowStatusService(documentWrkService);
        serviceBean.setPaServiceUtils(new MockPAServiceUtils());
    }

    /**
     * @throws HibernateException
     */
    private void setUpCtgovUser() throws HibernateException {
        User ctgovimportUser = new User();
        ctgovimportUser.setLoginName("ctgovimport");
        ctgovimportUser.setFirstName("ctgovimport");
        ctgovimportUser.setLastName("ctgovimport");
        ctgovimportUser.setUpdateDate(new Date());
        TestSchema.addUpdObject(ctgovimportUser);

        RegistryUser ctgovimportRegistryUser = new RegistryUser();
        ctgovimportRegistryUser.setCsmUser(ctgovimportUser);
        ctgovimportRegistryUser.setFirstName("ctgovimport");
        ctgovimportRegistryUser.setLastName("ctgovimport");
        TestSchema.addUpdObject(ctgovimportRegistryUser);

        PaHibernateUtil.getCurrentSession().flush();
        MockCSMUserService.users.add(ctgovimportUser);
        UsernameHolder.setUser(ctgovimportUser.getLoginName());
    }

    private void startNctApiMock() throws IOException {
        server = HttpServer.create(new InetSocketAddress(CTGOV_API_MOCK_PORT),
                0);
        server.createContext("/ctgov", new HttpHandler() {
            @Override
            public void handle(HttpExchange t) throws IOException {
                try {
                    String uri = t.getRequestURI().toString();
                    String nctid = uri.substring(uri.lastIndexOf("?") + 1);
                    if ("NCT404".equalsIgnoreCase(nctid)) {
                        t.sendResponseHeaders(404, 0);
                    } else if ("NCT500".equalsIgnoreCase(nctid)) {
                        t.sendResponseHeaders(500, 0);
                    } else {
                        String xml = IOUtils.toString(this.getClass()
                                .getResourceAsStream("/" + nctid + ".xml"));
                        t.sendResponseHeaders(200, 0);
                        OutputStream os = t.getResponseBody();
                        os.write(xml.getBytes("UTF-8"));
                        os.flush();
                        os.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }

            }
        });
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void done() throws Exception {
        try {
            server.stop(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PaHibernateUtil.enableAudit();
      
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getCtGovStudyByNctId(java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetCtGovStudyByNctId() throws PAException {
        assertNotNull(serviceBean.getCtGovStudyByNctId("NCT01861054"));
        assertNull(serviceBean.getCtGovStudyByNctId(""));
        assertNull(serviceBean.getCtGovStudyByNctId(null));
        assertNull(serviceBean.getCtGovStudyByNctId("NCT404"));
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getAdaptedCtGovStudyByNctId(java.lang.String)}
     * .
     * 
     * @throws PAException
     */
    @Test
    public final void testGetAdaptedCtGovStudyByNctId() throws PAException {
        CTGovStudyAdapter adapter = serviceBean
                .getAdaptedCtGovStudyByNctId("NCT01861054");
        assertEquals("Breast Cancer", adapter.getConditions());
        assertEquals("Drug: Reparixin", adapter.getInterventions());
        assertEquals("NCT01861054", adapter.getNctId());
        assertEquals("Recruiting", adapter.getStatus());
        assertEquals("Industry", adapter.getStudyCategory());
        assertEquals(
                "A Single Arm, Preoperative, Pilot Study to Evaluate the Safety and Biological Effects of Orally"
                        + " Administered Reparixin in Early Breast Cancer Patients Who Are Candidates for Surgery",
                adapter.getTitle());
    }

    @SuppressWarnings("unchecked")
    @Test
    public final void testImportFailureLogCreated() throws PAException,
            ParseException {
        final Session session = PaHibernateUtil.getCurrentSession();

        try {
            serviceBean.importTrial("NCT500");
            fail();
        } catch (PAException e) {
        }

        CTGovImportLog log = (CTGovImportLog) session.createQuery(
                " from CTGovImportLog log where log.nctID='NCT500'")
                .uniqueResult();
        assertEquals("Failure: unable to retrieve from CT.Gov",
                log.getImportStatus());
    }

    @Test
    public final void testConvertCtGovPhone() {
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609 896-9100"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609 896 9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("609-896 9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("(609) 896-9100 (U.S. Only)"));
        assertEquals("609-896-9100",
                serviceBean.convertCtGovPhone("1-609.896.9100 (U.S. Only)"));
        assertEquals("514-428-8600",
                serviceBean.convertCtGovPhone("514-428-8600 / 1-800-567-2594"));
        assertEquals("888-669-6682",
                serviceBean.convertCtGovPhone("1-888-669-6682"));
        assertEquals("800-340-6843",
                serviceBean.convertCtGovPhone("+1(800)340-6843"));
    }

    @Test
    public final void testBreakDownCtGovPersonName() throws PAException {
        EnPn name = getEnPn("Mark S. Allen, MD");
        EnPn converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Mark", getNamePart(converted, GIV, 0));
        assertEquals("Allen", getNamePart(converted, FAM));
        assertEquals("S", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));

        name = getEnPn("Lajos Pusztai, MD, DPHIL");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Lajos", getNamePart(converted, GIV, 0));
        assertEquals("Pusztai", getNamePart(converted, FAM));
        assertEquals(null, getNamePart(converted, GIV, 1));
        assertEquals("MD, DPHIL", getNamePart(converted, SFX));

        name = getEnPn("Maria E. Suarez-Almazor, MD, PhD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Maria", getNamePart(converted, GIV, 0));
        assertEquals("Suarez-Almazor", getNamePart(converted, FAM));
        assertEquals("E", getNamePart(converted, GIV, 1));
        assertEquals("MD, PhD", getNamePart(converted, SFX));

        name = getEnPn("Prof. Mary J Laughlin, MD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Mary", getNamePart(converted, GIV, 0));
        assertEquals("Laughlin", getNamePart(converted, FAM));
        assertEquals("J", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));
        assertEquals("Prof", getNamePart(converted, PFX));

        name = getEnPn("Corey Cutler, MD, MPH, FRCP(C)");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Corey", getNamePart(converted, GIV, 0));
        assertEquals("Cutler", getNamePart(converted, FAM));
        assertEquals("MD, MPH, F", getNamePart(converted, SFX));

        name = getEnPn("Laurie Grove, PA-C");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Laurie", getNamePart(converted, GIV, 0));
        assertEquals("Grove", getNamePart(converted, FAM));
        assertEquals("PA-C", getNamePart(converted, SFX));

        name = getEnPn("Ellie Guardino, MD/PhD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Ellie", getNamePart(converted, GIV, 0));
        assertEquals("Guardino", getNamePart(converted, FAM));
        assertEquals("MD/PhD", getNamePart(converted, SFX));

        name = getEnPn("Barry Skikne, M.D., FACP; FCP (SA)");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Barry", getNamePart(converted, GIV, 0));
        assertEquals("Skikne", getNamePart(converted, FAM));
        assertEquals("M.D., FACP", getNamePart(converted, SFX));

    }

    private EnPn getEnPn(String string) {
        return convertToEnPn(null, null, string, null, null);
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#importTrial(java.lang.String)}
     * .
     * 
     * @throws PAException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testImportTrial() throws PAException, ParseException {
        String nciID = serviceBean.importTrial("NCT01861054");
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = ((BigInteger) session.createSQLQuery(
                "select study_protocol_id from study_otheridentifiers where extension='"
                        + nciID + "'").uniqueResult()).longValue();
        Ii ii = IiConverter.convertToStudyProtocolIi(id);
        InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                .get(InterventionalStudyProtocol.class, id);
        assertEquals("REP0210",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getLocalStudyProtocolIdentifier());
        assertEquals("Sponsor Inc.",
                getStudySite(sp, StudySiteFunctionalCode.SPONSOR)
                        .getResearchOrganization().getOrganization().getName());
        assertTrue(sp.getProprietaryTrialIndicator());
        assertEquals(
                "Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients",
                sp.getPublicTitle());
        assertEquals(
                "A Single Arm, Preoperative, Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients Who Are Candidates for Surgery",
                sp.getOfficialTitle());
        assertTrue(sp.getDataMonitoringCommitteeAppointedIndicator());
        assertEquals(
                "Food and Drug Administration",
                regulatoryInfoSvc.get(
                        Long.parseLong(studyRegulatoryAuthorityService
                                .getCurrentByStudyProtocol(ii)
                                .getRegulatoryAuthorityIdentifier()
                                .getExtension())).getAuthorityName());
        assertEquals(
                "cancer investigating use of reparixin as single agent in the time period between clinical",
                sp.getPublicDescription());
        assertEquals(
                "According to the cancer stem cell (CSC) model, tumors are organized in a cellular hierarchy",
                sp.getScientificDescription());
        assertEquals(StudyStatusCode.ACTIVE, sp.getStudyOverallStatuses()
                .iterator().next().getStatusCode());
        assertEquals(DateUtils.parseDate("02/01/2013",
                new String[] { "MM/dd/yyyy" }), sp.getDates().getStartDate());
        assertEquals(DateUtils.parseDate("02/01/2014",
                new String[] { "MM/dd/yyyy" }), sp.getDates()
                .getPrimaryCompletionDate());
        assertEquals(ActualAnticipatedTypeCode.ANTICIPATED, sp.getDates()
                .getPrimaryCompletionDateTypeCode());
        assertEquals(PhaseCode.II, sp.getPhaseCode());
        assertEquals(StudyClassificationCode.SAFETY_OR_EFFICACY,
                sp.getStudyClassificationCode());
        assertEquals(DesignConfigurationCode.SINGLE_GROUP,
                sp.getDesignConfigurationCode());
        assertEquals(BlindingSchemaCode.OPEN, sp.getBlindingSchemaCode());
        assertEquals("TREATMENT", sp.getPrimaryPurposeCode().getName());

        List<StudyOutcomeMeasure> outcomes = session.createQuery(
                "from StudyOutcomeMeasure so where so.studyProtocol.id=" + id
                        + " order by so.id").list();
        assertEquals(18, outcomes.size());
        assertEquals(
                "Markers of Cancer Stem Cells (CSCs) in the primary tumor and the tumoral microenvironment",
                outcomes.get(0).getName());
        assertEquals("Change in markers from baseline at day 21",
                outcomes.get(0).getTimeFrame());
        assertEquals(false, outcomes.get(0).getSafetyIndicator());
        assertEquals(
                "CSCs will be measured in tissue samples by techniques that may include: ALDEFLUOR assay and assessment of CD44/CD24 by flow cytometry or examination of RNA transcripts by RT-PCR, aldehyde dehydrogenase 1 (ALDH1), CD44/CD24 and epithelial mesenchymal markers (Snail, Twist, Notch) by immunohistochemistry (IHC).",
                outcomes.get(0).getDescription());
        assertEquals(true, outcomes.get(0).getPrimaryIndicator());

        assertEquals(1, sp.getNumberOfInterventionGroups().intValue());
        assertEquals(40, sp.getMinimumTargetAccrualNumber().intValue());

        assertEquals(1, sp.getArms().size());
        assertEquals("Treated patients", sp.getArms().get(0).getName());
        assertEquals(ArmTypeCode.EXPERIMENTAL, sp.getArms().get(0)
                .getTypeCode());
        assertEquals(
                "Patients eligible will be treated with Reparixin as add-in monotherapy",
                sp.getArms().get(0).getDescriptionText());
        assertTrue(sp.getArms().get(0).getInterventions().isEmpty());

        PlannedEligibilityCriterion gender = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='GENDER' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(EligibleGenderCode.FEMALE, gender.getEligibleGenderCode());

        PlannedEligibilityCriterion age = (PlannedEligibilityCriterion) session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.criterionName='AGE' and so.studyProtocol.id="
                                + id).uniqueResult();
        assertEquals(18, age.getMinValue().intValue());
        assertEquals(999, age.getMaxValue().intValue());
        assertFalse(sp.getAcceptHealthyVolunteersIndicator());

        List<PlannedEligibilityCriterion> exclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=false and so.studyProtocol.id="
                                + id + " order by so.id").list();
        assertEquals(12, exclList.size());
        assertEquals("Male.", exclList.get(0).getTextDescription());

        List<PlannedEligibilityCriterion> inclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=true and so.criterionName is null and so.studyProtocol.id="
                                + id + " order by so.id").list();
        assertEquals(11, inclList.size());
        assertEquals("Female aged > 18 years.", inclList.get(0)
                .getTextDescription());

        StudyContact pi = getStudyContact(sp,
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        assertEquals("Goldstein, Lori J", pi.getClinicalResearchStaff()
                .getPerson().getLastName());

        StudyContact cc = getStudyContact(sp,
                StudyContactRoleCode.CENTRAL_CONTACT);
        assertEquals("Ruffini, Pieradelchi", cc.getClinicalResearchStaff()
                .getPerson().getLastName());
        assertEquals(
                "Cancer Stem Cells, Novel targeted therapy, CXCR1/2 Inhibitors",
                sp.getKeywordText());
        assertTrue(sp.getFdaRegulatedIndicator());
        assertTrue(sp.getExpandedAccessIndicator());

        CTGovImportLog log = (CTGovImportLog) session.createQuery(
                " from CTGovImportLog log where log.nciID='" + nciID + "'")
                .uniqueResult();
        assertEquals("NCT01861054", log.getNctID());
        assertEquals("Success", log.getImportStatus());
    }

    private StudyContact getStudyContact(InterventionalStudyProtocol sp,
            StudyContactRoleCode code) {
        for (StudyContact ss : sp.getStudyContacts()) {
            if (code.equals(ss.getRoleCode())) {
                return ss;
            }
        }
        return null;
    }

    private StudySite getStudySite(InterventionalStudyProtocol sp,
            StudySiteFunctionalCode code) {
        for (StudySite ss : sp.getStudySites()) {
            if (code.equals(ss.getFunctionalCode())) {
                return ss;
            }
        }
        return null;
    }

    /**
     * Test method for
     * {@link gov.nih.nci.pa.service.util.CTGovSyncServiceBean#getLogEntries(java.util.Date, java.util.Date)}
     * .
     * 
     * @throws ParseException
     * @throws PAException
     */
    @Test
    public final void testGetLogEntries() throws ParseException, PAException {

        final Session session = PaHibernateUtil.getCurrentSession();

        CTGovImportLog log1 = new CTGovImportLog();
        log1.setNciID("NCI1");
        log1.setNctID("NCT1");
        log1.setTitle("Title1");
        log1.setAction("New Trial");
        log1.setImportStatus("Failure");
        log1.setUserCreated("User1");
        log1.setDateCreated(DateUtils.parseDate("08/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log1);

        CTGovImportLog log2 = new CTGovImportLog();
        log2.setNciID("NCI2");
        log2.setNctID("NCT2");
        log2.setTitle("Title2");
        log2.setAction("New Trial");
        log2.setImportStatus("Failure");
        log2.setUserCreated("User2");
        log2.setDateCreated(DateUtils.parseDate("07/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log2);
        session.flush();

        List<CTGovImportLog> all = serviceBean.getLogEntries(new Date(0),
                new Date(System.currentTimeMillis()));
        assertEquals(2, all.size());
        assertEquals("NCI1", all.get(0).getNciID());
        assertEquals("NCI2", all.get(1).getNciID());

        List<CTGovImportLog> nci1 = serviceBean
                .getLogEntries(DateUtils.parseDate("07/30/2013",
                        new String[] { "MM/dd/yyyy" }), DateUtils.parseDate(
                        "08/01/2013", new String[] { "MM/dd/yyyy" }));
        assertEquals(1, nci1.size());
        assertEquals("NCI1", nci1.get(0).getNciID());

    }

}
