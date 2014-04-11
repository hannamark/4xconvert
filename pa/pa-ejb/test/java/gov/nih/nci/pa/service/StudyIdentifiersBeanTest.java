package gov.nih.nci.pa.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.dto.StudyIdentifierDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.StudyIdentifierType;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceBean;
import gov.nih.nci.pa.service.exception.PAValidationException;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.pa.util.pomock.MockOrganizationEntityService;
import gov.nih.nci.pa.util.pomock.MockPersonEntityService;
import gov.nih.nci.pa.util.pomock.MockResearchOrganizationCorrelationService;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Naveen Amiruddin
 * @since 08/26/2008
 */
public class StudyIdentifiersBeanTest extends AbstractTrialRegistrationTestBase {

    private StudyIdentifiersBeanLocal bean = new StudyIdentifiersBeanLocal();

    private final ProtocolQueryServiceLocal protocolQueryServiceLocal = mock(ProtocolQueryServiceLocal.class);

    private OrganizationCorrelationServiceBean organizationCorrelationServiceBean = new OrganizationCorrelationServiceBean();

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() throws Exception {

        bean.setMailManagerService(mailSvc);
        bean.setProtocolQueryService(protocolQueryServiceLocal);
        bean.setStudyProtocolService(studyProtocolService);
        bean.setStudySiteService(studySiteService);
        bean.setOrganizationCorrelationService(organizationCorrelationServiceBean);

        initMocks();

        changeTrialLeadOrg(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME);

        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER");
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME, "OBS");
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPE");

    }

    private void initMocks() throws PAException {
        StudyProtocolQueryDTO queryDTO = new StudyProtocolQueryDTO();
        queryDTO.setLocalStudyProtocolIdentifier("Local SP ID 02");
        queryDTO.setDcpId("DCP");
        queryDTO.setCtepId("CTEP");
        queryDTO.setNctIdentifier("OBS000000000001");
        queryDTO.setNciIdentifier(new PAServiceUtils()
                .getTrialNciId(TestSchema.studyProtocolIds.get(0)));

        when(
                protocolQueryServiceLocal
                        .getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class)))
                .thenReturn(Arrays.asList(queryDTO));

        when(poSvcLoc.getOrganizationEntityService()).thenReturn(
                new MockOrganizationEntityService());
        when(poSvcLoc.getPersonEntityService()).thenReturn(
                new MockPersonEntityService());
        when(poSvcLoc.getResearchOrganizationCorrelationService()).thenReturn(
                new MockResearchOrganizationCorrelationService());
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testGetStudyIdentifiers() throws PAException {
        List<StudyIdentifierDTO> list = bean.getStudyIdentifiers(IiConverter
                .convertToIi(TestSchema.studyProtocolIds.get(0)));
        assertEquals(7, list.size());
        assertEquals("Local SP ID 02", list.get(0).getValue());
        assertEquals(StudyIdentifierType.LEAD_ORG_ID, list.get(0).getType());
    }

    @Test
    public void testGetStudyIdentifiersNoStudy() throws PAException {
        thrown.expect(PAException.class);
        List<StudyIdentifierDTO> list = bean.getStudyIdentifiers(IiConverter
                .convertToIi(1000000L));

    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddLeadOrgId() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Lead Organization Identifier cannot be added using this method. "
                + "It is created at trial registration time and then can only be edited "
                + "on General Trial Details/Trial Acceptance screens");

        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.LEAD_ORG_ID, "ABC"));
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddCtGovId() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTGOV, "CTGOV"));
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME, "CTGOV");
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddCtep() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTEP, "CTEP"));
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTEP_ORG_NAME, "CTEP");
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddDcp() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DCP, "DCP"));
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.DCP_ORG_NAME, "DCP");
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddOtherID() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER01"));
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER01");
    }

    @Test
    public void testAddOtherIDDuplicate() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Identifier already exists");
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER001"));
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER001"));
    }

    @Test
    public void testAddDupeNCIMatchesExisting() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Duplicate NCI Identifier cannot match NCI Identifier of this trial");
        bean.add(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI,
                        new PAServiceUtils()
                                .getTrialNciId(TestSchema.studyProtocolIds
                                        .get(0))));
    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddObsoleteNCT() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OBSOLETE_CTGOV,
                        "OBSOLETE_CTGOV"));
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OBSOLETE_CTGOV");
    }

    @Test
    public void testAddObsoleteNCTMatchesExisting() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Obsolete ClinicalTrials.gov Identifier cannot match the current "
                + "ClinicalTrials.gov Identifier of this trial");
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTGOV,
                        "OBS000000000001"));
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OBSOLETE_CTGOV,
                        "OBS000000000001"));

    }

    @SuppressWarnings({ "unused", "deprecation" })
    @Test
    public void testAddDupeNCIID() throws PAException {
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI,
                        "DUPLICATE_NCI"));
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPLICATE_NCI");
    }

    @Test
    public void testAddMissingType() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Identifier type is missing");
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(null, "DUPLICATE_NCI"));
    }

    @Test
    public void testAddMissingValue() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Identifier value is required");
        bean.add(IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI, ""));
    }

    @Test
    public void testDeleteLeadOrgId() throws PAException {
        thrown.expect(PAException.class);
        thrown.expectMessage("Lead Organization Identifier cannot be removed from a trial.");

        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.LEAD_ORG_ID, "ABC"));
    }

    @Test
    public void testUpdateCtGovId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME, "CTGOV");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTGOV, "CTGOV"),
                "CTGOV2");
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME, "CTGOV2");
    }

    @Test
    public void testUpdateDcpId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.DCP_ORG_NAME, "DCP_ORG_NAME");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DCP, "DCP_ORG_NAME"),
                "DCP_ORG_NAME2");
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.DCP_ORG_NAME, "DCP_ORG_NAME2");
    }

    @Test
    public void testUpdateCtepId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTEP_ORG_NAME, "CTEP_ORG_NAME");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTEP,
                        "CTEP_ORG_NAME"), "CTEP_ORG_NAME2");
        verifyIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTEP_ORG_NAME, "CTEP_ORG_NAME2");
    }

    @Test
    public void testUpdateMissingValue() throws PAException {
        thrown.expect(PAValidationException.class);
        thrown.expectMessage("Identifier value is required");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI, ""),
                "");
    }

    @Test
    public void testUpdateLeadOrgID() throws PAException {
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.LEAD_ORG_ID,
                        "Local SP ID 02"), "Updated lead org id");
        verifyLeadOrgID(TestSchema.studyProtocolIds.get(0),
                "Updated lead org id");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.LEAD_ORG_ID,
                        "Updated lead org id"), "Local SP ID 02");
    }

    @Test
    public void testDeleteCtGovId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME, "CTGOV");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTGOV, "CTGOV"));
        verifyNoIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTGOV_ORG_NAME);
    }

    @Test
    public void testDeleteCtepId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTEP_ORG_NAME, "CTEP02");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.CTEP, "CTEP02"));
        verifyNoIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.CTEP_ORG_NAME);
    }

    @Test
    public void testDeleteDcpId() throws PAException {
        ensureIdentifierAssignerExists(TestSchema.studyProtocolIds.get(0),
                PAConstants.DCP_ORG_NAME, "DCP1111111111");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DCP, "DCP1111111111"));
        verifyNoIdentifierAssignerStudySite(TestSchema.studyProtocolIds.get(0),
                PAConstants.DCP_ORG_NAME);
    }

    @Test
    public void testDeleteOtherID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER01");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER01"));
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER01");
    }

    @Test
    public void testDeleteObsoleteNCTID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OBSOLETE_CTGOV,
                        "OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME"));
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME");
    }

    @Test
    public void testDeleteDupeNciID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME");
        bean.delete(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI,
                        "DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME"));
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME");
    }

    @Test
    public void testUpdateOtherID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER01");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OTHER, "OTHER01"),
                "OTHER02");
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER01");
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_NAME, "OTHER02");
    }

    @Test
    public void testUpdateObsoleteNctID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OTHER01");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.OBSOLETE_CTGOV,
                        "OTHER01"), "OTHER02");
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OTHER01");
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.OBSOLETE_NCT_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "OTHER02");
    }

    @Test
    public void testUpdateDupeNciID() throws PAException {
        ensureOtherIDExists(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPE001");
        bean.update(
                IiConverter.convertToIi(TestSchema.studyProtocolIds.get(0)),
                new StudyIdentifierDTO(StudyIdentifierType.DUPLICATE_NCI,
                        "DUPE001"), "DUPE002");
        verifyNoOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPE001");
        verifyOtherID(TestSchema.studyProtocolIds.get(0),
                IiConverter.DUPLICATE_NCI_STUDY_PROTOCOL_IDENTIFIER_NAME,
                "DUPE002");
    }

    private void verifyLeadOrgID(Long spID, String value) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(ss
                    .getFunctionalCode())
                    && value.equals(ss.getLocalStudyProtocolIdentifier())) {
                return;
            }
        }
        Assert.fail();

    }

    private void ensureOtherIDExists(Long spID, String identifierName,
            String value) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (Ii ii : sp.getOtherIdentifiers()) {
            if (StringUtils.equals(ii.getExtension(), value)
                    && StringUtils.equals(ii.getIdentifierName(),
                            identifierName)) {
                return;
            }
        }

        Set<Ii> newSet = new LinkedHashSet<Ii>(sp.getOtherIdentifiers());
        Ii otherID = new Ii();
        otherID.setExtension(value);
        otherID.setIdentifierName(identifierName);
        otherID.setRoot(IiConverter.STUDY_PROTOCOL_OTHER_IDENTIFIER_ROOT);

        newSet.add(otherID);
        sp.setOtherIdentifiers(newSet);
        s.save(sp);
        s.flush();
        s.clear();
    }

    private void changeTrialLeadOrg(Long spID, String orgName)
            throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.LEAD_ORGANIZATION.equals(ss
                    .getFunctionalCode())) {

                String poOrgId = organizationCorrelationServiceBean
                        .getPOOrgIdentifierByOrgName(orgName);
                Long roID = organizationCorrelationServiceBean
                        .createResearchOrganizationCorrelations(poOrgId);
                ss.setResearchOrganization((ResearchOrganization) s.load(
                        ResearchOrganization.class, roID));

                s.save(ss);
                s.flush();
                s.clear();
                return;
            }
        }

    }

    private void ensureIdentifierAssignerExists(Long spID, String orgName,
            String value) throws PAException {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.equals(ss
                    .getFunctionalCode())
                    && ss.getResearchOrganization() != null
                    && ss.getResearchOrganization().getOrganization() != null
                    && ss.getResearchOrganization().getOrganization().getName()
                            .equals(orgName)) {
                ss.setLocalStudyProtocolIdentifier(value);
                s.save(ss);
                s.flush();
                s.clear();
                return;

            }
        }

        StudySite ss = new StudySite();
        ss.setStudyProtocol(sp);
        ss.setFunctionalCode(StudySiteFunctionalCode.IDENTIFIER_ASSIGNER);
        ss.setLocalStudyProtocolIdentifier(value);
        ss.setStatusCode(FunctionalRoleStatusCode.ACTIVE);

        String poOrgId = organizationCorrelationServiceBean
                .getPOOrgIdentifierByOrgName(orgName);
        Long roID = organizationCorrelationServiceBean
                .createResearchOrganizationCorrelations(poOrgId);
        ss.setResearchOrganization((ResearchOrganization) s.load(
                ResearchOrganization.class, roID));
        s.save(ss);

        s.flush();
        s.clear();

    }

    private void verifyOtherID(Long spID, String identifierName, String value) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (Ii ii : sp.getOtherIdentifiers()) {
            if (StringUtils.equals(ii.getExtension(), value)
                    && StringUtils.equals(ii.getIdentifierName(),
                            identifierName)) {
                return;
            }
        }
        Assert.fail();
    }

    private void verifyNoOtherID(Long spID, String identifierName, String value) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (Ii ii : sp.getOtherIdentifiers()) {
            if (StringUtils.equals(ii.getExtension(), value)
                    && StringUtils.equals(ii.getIdentifierName(),
                            identifierName)) {
                Assert.fail();
            }
        }

    }

    private void verifyIdentifierAssignerStudySite(Long spID, String orgName,
            String value) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.equals(ss
                    .getFunctionalCode())
                    && ss.getResearchOrganization() != null
                    && ss.getResearchOrganization().getOrganization() != null
                    && ss.getResearchOrganization().getOrganization().getName()
                            .equals(orgName)
                    && value.equals(ss.getLocalStudyProtocolIdentifier())) {
                return;
            }
        }
        Assert.fail();
    }

    private void verifyNoIdentifierAssignerStudySite(Long spID, String orgName) {
        Session s = PaHibernateUtil.getCurrentSession();
        s.flush();
        s.clear();

        StudyProtocol sp = (StudyProtocol) s.load(StudyProtocol.class, spID);
        for (StudySite ss : sp.getStudySites()) {
            if (StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.equals(ss
                    .getFunctionalCode())
                    && ss.getResearchOrganization() != null
                    && ss.getResearchOrganization().getOrganization() != null
                    && ss.getResearchOrganization().getOrganization().getName()
                            .equals(orgName)) {
                Assert.fail();
            }
        }

    }

}
