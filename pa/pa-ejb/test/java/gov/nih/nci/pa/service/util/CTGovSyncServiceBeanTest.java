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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.EnPn;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.CTGovImportLog;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.InterventionalStudyProtocol;
import gov.nih.nci.pa.domain.NonInterventionalStudyProtocol;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.domain.StudyInbox;
import gov.nih.nci.pa.domain.StudyOutcomeMeasure;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.BlindingSchemaCode;
import gov.nih.nci.pa.enums.DesignConfigurationCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyClassificationCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyInboxTypeCode;
import gov.nih.nci.pa.enums.StudyModelCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.AbstractTrialRegistrationTestBase;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Closure;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.After;
import org.junit.Assert;
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

    private static final Map<String, ClinicalResearchStaffDTO> crsMap = new HashMap<String, ClinicalResearchStaffDTO>();
    
    private static final MockOrganizationEntityService ORGANIZATION_ENTITY_SERVICE = new MockOrganizationEntityService();
    private static final MockPersonEntityService PERSON_ENTITY_SERVICE = new MockPersonEntityService();
    
    private static final ProtocolComparisonServiceBean COMPARISON_SERVICE_LOCAL = new ProtocolComparisonServiceBean();
    
    private User ctgovimportUser;
    private User notCtgovimportUser;

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
        when(ocsr.getPOOrgIdentifierByIdentifierType(PAConstants.NCT_IDENTIFIER_TYPE)).thenCallRealMethod();
        when(ocsr.createResearchOrganizationCorrelations(any(String.class)))
                .thenAnswer(new Answer<Long>() {
                    @Override
                    public Long answer(InvocationOnMock invocation)
                            throws Throwable {
                        final Session session = PaHibernateUtil.getCurrentSession();
                        
                        String poOrgId = (String) invocation.getArguments()[0];
                        Organization org = (Organization) session
                                .createCriteria(Organization.class)
                                .add(Restrictions.eq("identifier", poOrgId))
                                .uniqueResult();
                        if (org == null) {
                            org = new Organization();
                            org.setIdentifier(poOrgId);
                            org.setStatusCode(EntityStatusCode.PENDING);
                            org.setName(EnOnConverter
                                    .convertEnOnToString(MockOrganizationEntityService.STORE
                                            .get(poOrgId).getName()));

                            session.save(org);
                            session.flush();
                        }

                        ResearchOrganization ro = new ResearchOrganization();
                        ro.setOrganization(org);
                        ro.setStatusCode(StructuralRoleStatusCode.PENDING);
                        ro.setIdentifier(poOrgId);
                        session.save(ro);
                        session.flush();

                        return ro.getId();
                    }
                });
        
        ocsr.createResearchOrganizationCorrelations(MockOrganizationEntityService.CT_GOV_ID+""); // <-- CT.Gov
    }

    /**
     * @throws EntityValidationException
     * @throws CurationException
     * @throws NullifiedEntityException
     * @throws NullifiedRoleException
     */
    private void setUpPoMocks() throws EntityValidationException,
            CurationException, NullifiedEntityException, NullifiedRoleException {
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(ORGANIZATION_ENTITY_SERVICE);
        when(poSvcLoc.getPersonEntityService()).thenReturn(PERSON_ENTITY_SERVICE);
        
        when(crsSvc.createCorrelation(any(ClinicalResearchStaffDTO.class)))
                .thenAnswer(new Answer<Ii>() {
                    @Override
                    public Ii answer(InvocationOnMock invocation)
                            throws Throwable {
                        ClinicalResearchStaffDTO crs = (ClinicalResearchStaffDTO) invocation
                                .getArguments()[0];
                        final String poId = (MockOrganizationEntityService.PO_ID_SEQ++) + "";
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
        
        COMPARISON_SERVICE_LOCAL.setPlannedActivityService(plannedActivityService);
        COMPARISON_SERVICE_LOCAL.setStudyOutcomeMeasureService(studyOutcomeMeasureService);
        COMPARISON_SERVICE_LOCAL.setStudyRegulatoryAuthorityService(studyRegulatoryAuthorityService);
        COMPARISON_SERVICE_LOCAL.setRegulatoryInformationService(regulatoryInfoSvc);

        
        serviceBean.setLookUpTableService(lookUpTableServiceRemote);
        serviceBean.setRegulatoryAuthorityService(regulatoryInfoSvc);
        serviceBean.setTrialRegistrationService(super.bean);
        serviceBean.setStudyProtocolService(studyProtocolService);
        serviceBean.setRegistryUserService(new MockRegistryUserServiceBean());
        serviceBean.setDocumentWorkflowStatusService(documentWrkService);
        serviceBean.setPaServiceUtils(new MockPAServiceUtils());
        serviceBean.setProtocolComparisonService(COMPARISON_SERVICE_LOCAL);
        serviceBean.setStudyMilestoneService(studyMilestoneSvc);
        serviceBean.setStudyInboxService(studyInboxSvc);
    }

    /**
     * @throws HibernateException
     */
    private void setUpCtgovUser() throws HibernateException {
        ctgovimportUser = new User();
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
        
        notCtgovimportUser = new User();
        notCtgovimportUser.setLoginName("notCtgovimportUser");
        notCtgovimportUser.setFirstName("notCtgovimportUser");
        notCtgovimportUser.setLastName("notCtgovimportUser");
        notCtgovimportUser.setUpdateDate(new Date());
        TestSchema.addUpdObject(notCtgovimportUser);

        RegistryUser notCtgovimportUser = new RegistryUser();
        notCtgovimportUser.setCsmUser(ctgovimportUser);
        notCtgovimportUser.setFirstName("notCtgovimportUser");
        notCtgovimportUser.setLastName("notCtgovimportUser");
        TestSchema.addUpdObject(notCtgovimportUser);

        PaHibernateUtil.getCurrentSession().flush();
        MockCSMUserService.users.add(ctgovimportUser);
        MockCSMUserService.users.add(this.notCtgovimportUser);
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
        assertEquals("Failure: unable to retrieve from ClinicalTrials.gov",
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
        assertEquals("301-728-7094",
                serviceBean.convertCtGovPhone("3017287094"));
    }
    
    @Test
    public final void testDropLeftOver() {
        assertEquals(
                "Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin.",
                serviceBean
                        .dropLeftOver("Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin.\r\n\r\n        Details of the above and additional inclusion and exclusion criteria can be discussed with"));

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
        assertEquals("Prof.", getNamePart(converted, PFX));

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
        
        name = getEnPn("Drhubo J Ghaal, MD");
        converted = serviceBean.breakDownCtGovPersonName(name);
        assertEquals("Drhubo", getNamePart(converted, GIV, 0));
        assertEquals("Ghaal", getNamePart(converted, FAM));
        assertEquals("J", getNamePart(converted, GIV, 1));
        assertEquals("MD", getNamePart(converted, SFX));
        assertEquals(null, getNamePart(converted, PFX));

    }

    private EnPn getEnPn(String string) {
        return convertToEnPn(null, null, string, null, null);
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6438EligCriteriaHandling() throws PAException, ParseException {
        String nciID = serviceBean.importTrial("NCT00324155");
        assertTrue(StringUtils.isNotEmpty(nciID));
        
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        
        final long id = getProtocolIdByNciId(nciID, session);
        Ii ii = IiConverter.convertToStudyProtocolIi(id);
        
        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(4, exclList.size());
        assertEquals("Pregnant / nursing", exclList.get(0).getTextDescription());
        assertEquals("Primary ocular or mucosal melanoma", exclList.get(3).getTextDescription());

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(7, inclList.size());
        assertEquals("Informed Consent", inclList.get(0)
                .getTextDescription());
        assertEquals("Prior therapy restriction (adjuvant only)", inclList.get(6)
                .getTextDescription());
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6467EligCriteriaHandling() throws PAException, ParseException {
        String nciID = serviceBean.importTrial("NCT01023880");
        assertTrue(StringUtils.isNotEmpty(nciID));
        
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        
        final long id = getProtocolIdByNciId(nciID, session);
        
        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(1, exclList.size());
        assertTrue(exclList.get(0).getTextDescription().trim().startsWith("Key Inclusion Criteria:"));
        

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(1, inclList.size());
        assertTrue(inclList.get(0).getTextDescription().trim().startsWith("Key Inclusion Criteria:"));
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public final void testPO6548EligCriteriaHandling() throws PAException, ParseException {
        String nciID = serviceBean.importTrial("NCT00653939");
        assertTrue(StringUtils.isNotEmpty(nciID));
        
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        
        final long id = getProtocolIdByNciId(nciID, session);
        
        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(6, inclList.size());
        assertEquals(
                "Pathologically confirmed Stage IIIB NSCLC with malignant pleural   effusion, or Stage IV disease",
                inclList.get(0).getTextDescription());
        assertEquals(
                "Subjects or their legal representatives must be able to read, understand and provide written informed consent to participate in the trial.",
                inclList.get(5).getTextDescription());
        
        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(10, exclList.size());
        assertEquals("Predominant Squamous Cell NSCLC histology.", exclList
                .get(0).getTextDescription());
        assertEquals(
                "Known hypersensitivity to any of the components of CA4P, paclitaxel, carboplatin, bevacizumab, or radiologic contrast dyes.",
                exclList.get(9).getTextDescription());

       
        
    }

    /**
     * @param session
     * @param spID
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<PlannedEligibilityCriterion> getInclusionCriteriaList(
            final Session session, final long spID) throws HibernateException {
        List<PlannedEligibilityCriterion> inclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=true and so.criterionName is null and so.studyProtocol.id="
                                + spID + " order by so.displayOrder").list();
        return inclList;
    }

    /**
     * @param session
     * @param spID
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private List<PlannedEligibilityCriterion> getExclusionCriteriaList(
            final Session session, final long spID) throws HibernateException {
        List<PlannedEligibilityCriterion> exclList = session
                .createQuery(
                        "from PlannedEligibilityCriterion so where so.inclusionIndicator=false and so.studyProtocol.id="
                                + spID + " order by so.displayOrder").list();
        return exclList;
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
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            checkNCT01861054PersonOrgData(sp, "Sponsor Inc.", "Sponsor Inc.");
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            
        } finally {
            deactivateTrial(session, id);
        }
    }
    
    @Test
    public final void testImportFailureDoubleMasking() throws PAException,
            ParseException {

        thrown.expect(PAException.class);
        thrown.expectMessage("At least two masking roles must be specified for \"Double Blind\" masking.");

        final String nctID = "NCT1111111";
        serviceBean.importTrial(nctID);

    }
    
    @Test
    public final void testUpdateTrial() throws PAException, ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);
        
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        
        // Change NCT identifier to be able to update this protocol with a different ClinicalTrials.gov XML that
        // actually belongs to a different trial.
        changeNCTNumber("NCT01440088", "NCT01861054");
        
        nctID = "NCT01861054";
        
        // Apply update on top of the existing record.
        String newNciID = serviceBean.importTrial(nctID);
        final long newId = getProtocolIdByNciId(newNciID, session);
        
        session.flush();
        session.clear();
        
        // Make sure we didn't create two protocols.
        assertEquals(nciID, newNciID);
        assertEquals(id, newId);
        
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            // The update should have altered all fields except the Lead Org (by design).
            checkNCT01861054PersonOrgData(sp, "Threshold Pharmaceuticals", "Sponsor Inc.");
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            checkInboxEntry(sp);
            
        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }
    
    private void checkInboxEntry(InterventionalStudyProtocol sp) {
        StudyInbox inbox = sp.getStudyInbox().iterator().next();
        assertEquals(
                "Trial has been updated from ClinicalTrials.gov\rDetailed Description changed\rEligibility Criteria changed",
                inbox.getComments());
        assertNull(inbox.getCloseDate());
        assertEquals(StudyInboxTypeCode.UPDATE, inbox.getTypeCode());

    }

    /**
     * PO-6482: For existing trials in CTRP where the submitter is NOT Clinicaltrials.gov, DO NOT UPDATE these with the data from Clinicaltrials.gov XML
     * -    Sponsor
     * -   Summary4 Funding Sponsor
     * -   Lead Org
     * @throws PAException
     * @throws ParseException
     */
    @Test
    public final void testUpdateNonCtgovSubmittedTrial() throws PAException, ParseException {
        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);
        
        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        
        // Change NCT identifier to be able to update this protocol with a different ClinicalTrials.gov XML that
        // actually belongs to a different trial.
        changeNCTNumber("NCT01440088", "NCT01861054");
        
        // Change submitter to a different user other than ctgovimportuser in order to trigger the behavior
        // described in PO-6482.
        changeSubmitter(id, notCtgovimportUser);
        
        // Switch to the mode described in PO-6482. Import orgs, skip persons.
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "false";       
       
        nctID = "NCT01861054";
        // Apply update on top of the existing record.
        String newNciID = serviceBean.importTrial(nctID);
        final long newId = getProtocolIdByNciId(newNciID, session);
        
        session.flush();
        session.clear();
        
        // Make sure we didn't create two protocols.
        assertEquals(nciID, newNciID);
        assertEquals(id, newId);
        
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            // The update should have altered all fields except the Lead Org (by design).
            checkNCT01861054OrgData(sp, "Threshold Pharmaceuticals", "Threshold Pharmaceuticals");
           
            // respossible party should have remained the same: Sponsor
            StudySite rp = getStudySite(sp, StudySiteFunctionalCode.RESPONSIBLE_PARTY_SPONSOR);
            assertNotNull(rp);
            
            // Other data come from update.
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            checkInboxEntry(sp);
            
        } finally {
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";       
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }
    
    
    private void changeSubmitter(long id, User u) {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update study_protocol set user_last_created_id="
                        + u.getUserId()).executeUpdate();
        session.flush();

    }

    private void changeNCTNumber(String from, String to) {
        final Session session = PaHibernateUtil.getCurrentSession();
        session.createSQLQuery(
                "update study_site set local_sp_indentifier='" + to
                        + "' where local_sp_indentifier='" + from + "'")
                .executeUpdate();
        session.flush();
    }

    @Test
    public final void testImportTrialNoPoData() throws PAException, ParseException {
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "false";        
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "false";
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            checkNCT01861054EmptyPersonOrgData(sp);
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            
        } finally {
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";        
            deactivateTrial(session, id);
        }
    }
    
    @Test
    public final void testImportTrialOrgsNoPersonsData() throws PAException, ParseException {
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "false";
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            checkNCT01861054OrgData(sp, "Sponsor Inc.", "Sponsor Inc.");
            checkNCT01861054EmptyPersonData(sp);
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            
        } finally {
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";        
            deactivateTrial(session, id);
        }
    }
    
    @Test
    public final void testImportTrialPersonsNoOrgData() throws PAException, ParseException {
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "false";        
        MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";
        final String nctID = "NCT01861054";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        try {
            InterventionalStudyProtocol sp = (InterventionalStudyProtocol) session
                    .get(InterventionalStudyProtocol.class, id);
            
            checkNCT01861054PersonData(sp);
            checkNCT01861054EmptyOrgData(sp);
            checkNCT01861054OtherData(session, sp);    
            checkSuccessfulImportLogEntry(nctID, nciID, session);
            
        } finally {
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_ORGS = "true";        
            MockLookUpTableServiceBean.CTGOV_SYNC_IMPORT_PERSONS = "true";        
            deactivateTrial(session, id);
        }
    }

    /**
     * @param session
     * @param id
     * @throws HibernateException
     */
    private void deactivateTrial(final Session session, final long id)
            throws HibernateException {
            session.createSQLQuery(
                    "update study_protocol set status_code='INACTIVE' where identifier="
                            + id).executeUpdate();
            session.flush();
    }

    /**
     * @param nctID
     * @param nciID
     * @param session
     * @throws HibernateException
     */
    private void checkSuccessfulImportLogEntry(final String nctID,
            String nciID, final Session session) throws HibernateException {
        CTGovImportLog log = (CTGovImportLog) session.createQuery(
                " from CTGovImportLog log where log.nciID='" + nciID + "' order by log.dateCreated desc")
                .list().get(0);
        assertEquals(nctID, log.getNctID());
        assertEquals("Success", log.getImportStatus());
    }

    /**
     * @param nciID
     * @param session
     * @return
     * @throws HibernateException
     */
    private long getProtocolIdByNciId(String nciID, final Session session)
            throws HibernateException {
        return ((BigInteger) session.createSQLQuery(
                "select study_protocol_id from study_otheridentifiers where extension='"
                        + nciID + "'").uniqueResult()).longValue();
    }

    /**
     * @param session
     * @param id
     * @param sp
     * @throws PAException
     * @throws NumberFormatException
     * @throws ParseException
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    private void checkNCT01861054OtherData(final Session session,
            InterventionalStudyProtocol sp) throws PAException,
            NumberFormatException, ParseException, HibernateException {
        final long id = sp.getId();
        Ii ii = IiConverter.convertToStudyProtocolIi(id);
        assertEquals("REP0210",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getLocalStudyProtocolIdentifier());
        assertTrue(sp.getProprietaryTrialIndicator());
        assertEquals(
                "Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients",
                sp.getPublicTitle());
        assertEquals(
                "A Single Arm, Preoperative, Pilot Study to Evaluate the Safety and Biological Effects of Orally Administered Reparixin in Early Breast Cancer Patients Who Are Candidates for Surgery",
                sp.getOfficialTitle());
        assertTrue(sp.getDataMonitoringCommitteeAppointedIndicator());
        
        checkRegulatoryInformation(ii);
        
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
        
        assertEquals(BlindingSchemaCode.DOUBLE_BLIND, sp.getBlindingSchemaCode());
        assertEquals(BlindingRoleCode.CAREGIVER, sp.getBlindingRoleCodeCaregiver());
        assertEquals(BlindingRoleCode.SUBJECT, sp.getBlindingRoleCodeSubject());
        assertNull(sp.getBlindingRoleCodeInvestigator());
        assertNull(sp.getBlindingRoleCodeOutcome());
        
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

        List<PlannedEligibilityCriterion> exclList = getExclusionCriteriaList(
                session, id);
        assertEquals(12, exclList.size());
        assertEquals("Male.", exclList.get(0).getTextDescription());
        verifyDisplayOrder(exclList);

        List<PlannedEligibilityCriterion> inclList = getInclusionCriteriaList(
                session, id);
        assertEquals(11, inclList.size());
        assertEquals("Female aged > 18 years.", inclList.get(0)
                .getTextDescription());
        verifyDisplayOrder(inclList);
      
        assertEquals(
                "Cancer Stem Cells, Novel targeted therapy, CXCR1/2 Inhibitors",
                sp.getKeywordText());
        assertTrue(sp.getFdaRegulatedIndicator());
        assertTrue(sp.getExpandedAccessIndicator());
    }

    // PO-6570
    private void verifyDisplayOrder(final List<PlannedEligibilityCriterion> list) {
        CollectionUtils.forAllDo(list, new Closure() {
            @Override
            public void execute(Object obj) {
                PlannedEligibilityCriterion pec = (PlannedEligibilityCriterion) obj;
                Assert.assertTrue(pec.getDisplayOrder() != null);
            }
        });
    }

    /**
     * @param spID
     * @throws PAException
     * @throws NumberFormatException
     */
    private void checkRegulatoryInformation(Ii spID) throws PAException,
            NumberFormatException {
        assertEquals(
                "Food and Drug Administration",
                regulatoryInfoSvc.get(
                        Long.parseLong(studyRegulatoryAuthorityService
                                .getCurrentByStudyProtocol(spID)
                                .getRegulatoryAuthorityIdentifier()
                                .getExtension())).getAuthorityName());
        // Check only one record in study_regulatory_authority table.
        assertEquals(1, studyRegulatoryAuthorityService
                                .getByStudyProtocol(spID).size());
    }

    /**
     * @param sp
     */
    private void checkNCT01861054PersonOrgData(InterventionalStudyProtocol sp, String leadOrgName, String sponsorName) {
        checkNCT01861054OrgData(sp, leadOrgName, sponsorName);        
        checkNCT01861054PersonData(sp);        
        checkNCT01861054RespPartyData(sp);
    }

    /**
     * @param sp
     */
    private void checkNCT01861054RespPartyData(InterventionalStudyProtocol sp) {
        StudyContact rp = getStudyContact(sp,
                StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        assertEquals("Goldstein", rp.getClinicalResearchStaff()
                .getPerson().getLastName());
        assertEquals("Lori", rp.getClinicalResearchStaff()
                .getPerson().getFirstName());
        assertEquals("Associate professor of pediatrics", rp.getTitle());
        assertEquals("Children's Hospital Boston", rp.getClinicalResearchStaff()
                .getOrganization().getName());
    }

    /**
     * @param sp
     */
    private void checkNCT01861054PersonData(InterventionalStudyProtocol sp) {
        StudyContact pi = getStudyContact(sp,
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        assertEquals("Goldstein", pi.getClinicalResearchStaff()
                .getPerson().getLastName());
        assertEquals("Lori", pi.getClinicalResearchStaff()
                .getPerson().getFirstName());
        assertEquals("J", pi.getClinicalResearchStaff()
                .getPerson().getMiddleName());
       

        StudyContact cc = getStudyContact(sp,
                StudyContactRoleCode.CENTRAL_CONTACT);
        assertEquals("Ruffini", cc.getClinicalResearchStaff()
                .getPerson().getLastName());
        assertEquals("Pieradelchi", cc.getClinicalResearchStaff()
                .getPerson().getFirstName());
    }

    /**
     * @param sp
     */
    private void checkNCT01861054OrgData(InterventionalStudyProtocol sp, String leadOrgName, String sponsorName) {
        assertEquals(leadOrgName,
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getResearchOrganization().getOrganization().getName());
        assertEquals(sponsorName,
                getStudySite(sp, StudySiteFunctionalCode.SPONSOR)
                        .getResearchOrganization().getOrganization().getName());
        assertEquals("National Institutes of Health (NIH)",
                getStudySite(sp, StudySiteFunctionalCode.LABORATORY)
                        .getResearchOrganization().getOrganization().getName());
        
        StudyResourcing summary4 = sp.getStudyResourcings().get(0);
        assertTrue(summary4.getSummary4ReportedResourceIndicator());
        assertEquals(SummaryFourFundingCategoryCode.INDUSTRIAL,
                summary4.getTypeCode());
        assertEquals(
                sponsorName,
                ((Organization)PaHibernateUtil.getCurrentSession().get(Organization.class,
                        new Long(summary4.getOrganizationIdentifier()))).getName());
    }

    @Test
    public final void testAttemptUpdateCompleteTrial() throws PAException,
            ParseException {

        thrown.expect(PAException.class);
        thrown.expectMessage("Complete trials cannot be updated from ClinicalTrials.gov");

        final StudyProtocolServiceLocal mock = mock(StudyProtocolServiceLocal.class);
        final StudyProtocolDTO dto = new StudyProtocolDTO();
        dto.setIdentifier(TestSchema.nonPropTrialData());

        final String nctID = "NCT01861054";
        when(mock.getStudyProtocolsByNctId(nctID)).thenReturn(
                Arrays.asList(dto));

        try {
            serviceBean.setStudyProtocolService(mock);
            serviceBean.importTrial(nctID);
        } finally {
            serviceBean.setStudyProtocolService(studyProtocolService);
        }
    }
    
    @Test
    public final void testImportNCT00760500_PO_6462() throws PAException,
            ParseException {
        final String nctID = "NCT00760500";
        String nciID = serviceBean.importTrial(nctID);
        assertTrue(StringUtils.isNotEmpty(nciID));

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();

        final long id = getProtocolIdByNciId(nciID, session);
        NonInterventionalStudyProtocol sp = (NonInterventionalStudyProtocol) session
                .get(NonInterventionalStudyProtocol.class, id);
        assertEquals(StudyModelCode.ECOLOGIC_OR_COMMUNITY_STUDIES,
                sp.getStudyModelCode());

    }
    
    private void checkNCT01861054EmptyPersonOrgData(
            InterventionalStudyProtocol sp) {
        checkNCT01861054EmptyOrgData(sp);
        checkNCT01861054EmptyPersonData(sp);
    }

    /**
     * @param sp
     */
    private void checkNCT01861054EmptyPersonData(InterventionalStudyProtocol sp) {
        StudyContact pi = getStudyContact(sp,
                StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR);
        assertNull(pi);

        StudyContact rp = getStudyContact(
                sp,
                StudyContactRoleCode.RESPONSIBLE_PARTY_STUDY_PRINCIPAL_INVESTIGATOR);
        assertNull(rp);

        StudyContact cc = getStudyContact(sp,
                StudyContactRoleCode.CENTRAL_CONTACT);
        assertNull(cc);
    }

    /**
     * @param sp
     */
    private void checkNCT01861054EmptyOrgData(InterventionalStudyProtocol sp) {
        assertEquals("CTRO Replace This Field",
                getStudySite(sp, StudySiteFunctionalCode.LEAD_ORGANIZATION)
                        .getResearchOrganization().getOrganization().getName());
        assertNull(getStudySite(sp, StudySiteFunctionalCode.SPONSOR));
        assertNull(getStudySite(sp, StudySiteFunctionalCode.LABORATORY));        
        assertTrue(sp.getStudyResourcings().isEmpty());
       
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
        log1.setTitle("Title : Trial 1");
        log1.setAction("New Trial");
        log1.setImportStatus("Success");
        log1.setUserCreated("User1");
        log1.setDateCreated(DateUtils.parseDate("08/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log1);

        CTGovImportLog log2 = new CTGovImportLog();
        log2.setNciID("NCI2");
        log2.setNctID("NCT2");
        log2.setTitle("Title : Trial 2");
        log2.setAction("Update");
        log2.setImportStatus("Failure : Exception");
        log2.setUserCreated("User1");
        log2.setDateCreated(DateUtils.parseDate("07/15/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log2);
        session.flush();
        
        CTGovImportLog log3 = new CTGovImportLog();
        log3.setNciID("NCI3");
        log3.setNctID("NCT3");
        log3.setTitle("Title : Trial 3");
        log3.setAction("Update");
        log3.setImportStatus("Success");
        log3.setUserCreated("User2");
        log3.setDateCreated(DateUtils.parseDate("07/01/2013",
                new String[] { "MM/dd/yyyy" }));
        session.save(log3);
        session.flush();

        //exercise start and end dates are specified
        List<CTGovImportLog> entries = serviceBean.getLogEntries(null, null, null, null, null, null, new Date(0), 
                new Date(System.currentTimeMillis()));
        assertEquals(3, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        assertEquals("NCI3", entries.get(2).getNciID());
        
        //exercise start and end dates are specified
        entries = serviceBean.getLogEntries(null, null, null, null, null, null, DateUtils.parseDate("07/30/2013", 
                        new String[] { "MM/dd/yyyy" }), DateUtils.parseDate(
                                "08/01/2013", new String[] { "MM/dd/yyyy" }));
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        
        //exercise start date is specified
        entries = serviceBean.getLogEntries(null, null, null, null, null, null, DateUtils.parseDate("07/30/2013", 
                new String[] { "MM/dd/yyyy" }), null);
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        
        //exercise end date is specified
        entries = serviceBean.getLogEntries(null, null, null, null, null, null, null, DateUtils.parseDate("07/30/2013", 
                new String[] { "MM/dd/yyyy" }));
        assertEquals(2, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());

        //exercise NCI identifier is specified
        entries = serviceBean.getLogEntries("NCI3", null, null, null, null, null, null, null);
        assertEquals(1, entries.size());
        assertEquals("NCI3", entries.get(0).getNciID());
        
        //exercise NCT identifier is specified
        entries = serviceBean.getLogEntries(null, "NCT2", null, null, null, null, null, null);
        assertEquals(1, entries.size());
        assertEquals("NCT2", entries.get(0).getNctID());
        
        //exercise title is specified
        entries = serviceBean.getLogEntries(null, null, "Title :", null, null, null, null, null);
        assertEquals(3, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        assertEquals("NCI3", entries.get(2).getNciID());
        
        //exercise action is specified
        entries = serviceBean.getLogEntries(null, null, null, "New Trial", null, null, null, null);
        assertEquals(1, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        
        entries = serviceBean.getLogEntries(null, null, null, "Update", null, null, null, null);
        assertEquals(2, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());
        
        //exercise import status is specified
        entries = serviceBean.getLogEntries(null, null, null, null, "Success", null, null, null);
        assertEquals(2, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI3", entries.get(1).getNciID());
        
        entries = serviceBean.getLogEntries(null, null, null, null, "Failure", null, null, null);
        assertEquals(1, entries.size());
        assertEquals("NCI2", entries.get(0).getNciID());
        
        //exercise user is specified
        entries = serviceBean.getLogEntries(null, null, null, null, null, "User1", null, null);
        assertEquals(2, entries.size());
        assertEquals("NCI1", entries.get(0).getNciID());
        assertEquals("NCI2", entries.get(1).getNciID());
        
        entries = serviceBean.getLogEntries(null, null, null, null, null, "User2", null, null);
        assertEquals(1, entries.size());
        assertEquals("NCI3", entries.get(0).getNciID());
    }
    
    @Test
    public final void testUpdateRejectedTrialFails() throws Exception {

        thrown.expect(PAException.class);
        thrown.expectMessage("Updates to rejected trials are not allowed");

        // Create protocol by performing a new trial import.
        String nctID = "NCT01440088";
        String nciID = serviceBean.importTrial(nctID);
        long id = 0;

        final Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        session.clear();
        try {
            id = getProtocolIdByNciId(nciID, session);

            StudyProtocol sp = (StudyProtocol) session.get(StudyProtocol.class,
                    id);
            DocumentWorkflowStatus dws = TestSchema
                    .createDocumentWorkflowStatus(sp);
            dws.setStatusCode(DocumentWorkflowStatusCode.REJECTED);
            session.save(dws);
            session.flush();

            serviceBean.importTrial(nctID);
        } finally {
            // Delete the trial.
            deactivateTrial(session, id);
        }
    }
    @Test
    public void isNctIdValidTest() {
        assertEquals(true, serviceBean.isNctIdValid("NCT1"));
        assertEquals(true, serviceBean.isNctIdValid("NCT12344444"));
        assertEquals(false, serviceBean.isNctIdValid("1NCT12344444"));
        assertEquals(false, serviceBean.isNctIdValid("N12344444"));
        assertEquals(false, serviceBean.isNctIdValid("NCT"));
    }

}
