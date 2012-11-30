package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.AssignmentActionCode;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.AbstractHibernateTestCase;
import gov.nih.nci.pa.util.MockCSMUserService;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.TestSchema;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class FamilyServiceTest extends AbstractHibernateTestCase {

    private FamilyServiceBeanLocal ejb;
    private OrganizationEntityServiceRemote oes;
    private FamilyServiceRemote fs;
    RegistryUserBeanLocal registryUserEjb;
    StudySiteAccrualAccessServiceBean siteAccessEjb;
    ParticipatingOrgServiceBean partOrgEjb;
    private RegistryUser ru;
    Session sess;

    @Before
    public void setUp() throws Exception {
        PoServiceLocator psl = mock(PoServiceLocator.class);
        oes = mock(OrganizationEntityServiceRemote.class);
        when(psl.getOrganizationEntityService()).thenReturn(oes);
        fs = mock(FamilyServiceRemote.class);
        when(psl.getFamilyService()).thenReturn(fs);
        PoRegistry.getInstance().setPoServiceLocator(psl);
        TestSchema.primeData();
        sess = PaHibernateUtil.getCurrentSession();
        ru = (RegistryUser) sess.get(RegistryUser.class, TestSchema.registryUserIds.get(0));

        CSMUserService.setInstance(new MockCSMUserService());
        ejb = new FamilyServiceBeanLocal();
        registryUserEjb = new RegistryUserBeanLocal();
        siteAccessEjb = new StudySiteAccrualAccessServiceBean();
        partOrgEjb = new ParticipatingOrgServiceBean();
        siteAccessEjb.setParticipatingOrgServiceLocal(partOrgEjb);
        ejb.setRegistryUserService(registryUserEjb);
        ejb.setStudySiteAccess(siteAccessEjb);
        ejb.setDwsService(mock(DocumentWorkflowStatusServiceLocal.class));
        ejb.setParticipatingOrgService(partOrgEjb);
    }

    @Test
    public void getByOrgIdNullTest() throws Exception {
        assertTrue(FamilyHelper.getByOrgId(null).isEmpty());
    }

    @Test
    public void getByOrgIdNotFoundTest() throws Exception {
        assertTrue(FamilyHelper.getByOrgId(1L).isEmpty());
    }

    @Test(expected = PAException.class)
    public void getByOrgIdTooManyResultsTest() throws Exception {
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(1));
        FamilyHelper.getByOrgId(1L);
    }

    @Test
    public void getByOrgIdTest() throws Exception {
        // no family associated with org
        OrganizationDTO org  = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        assertTrue(FamilyHelper.getByOrgId(1L).isEmpty());

        // one family
        Set<Ii> familySet = new HashSet<Ii>();
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        dset.setItem(familySet);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        assertEquals(1, FamilyHelper.getByOrgId(1L).size());

        // n results
        familySet.add(IiConverter.convertToPoFamilyIi("2"));
        familyMap.put(IiConverter.convertToPoFamilyIi("2"), getPoFamilyDTO(2L));
        assertEquals(2, FamilyHelper.getByOrgId(1L).size());
    }

    @Test
    public void getAllRelatedOrgsNull() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs((Long) null).isEmpty());
    }

    @Test
    public void getAllRelatedOrgsNotFound() throws Exception {
        assertTrue(FamilyHelper.getAllRelatedOrgs(1L).isEmpty());
    }

    @Test(expected = PAException.class)
    public void getAllRelatedOrgsTooManyResultsTest() throws Exception {
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenThrow(new TooManyResultsException(1));
        FamilyHelper.getAllRelatedOrgs((Long) 1L);
    }


    @Test
    public void getAllRelatedOrgsTest() throws Exception {
        OrganizationDTO org  = new OrganizationDTO();
        DSet<Ii> dset = new DSet<Ii>();
        Set<Ii> familySet = new HashSet<Ii>();
        dset.setItem(familySet);
        familySet.add(IiConverter.convertToPoFamilyIi("1"));
        familySet.add(IiConverter.convertToPoFamilyIi("2"));
        org.setFamilyOrganizationRelationships(dset);
        List<OrganizationDTO> result = new ArrayList<OrganizationDTO>();
        result.add(org);
        when(oes.search(any(OrganizationDTO.class), any(LimitOffset.class))).thenReturn(result);
        Map<Ii, FamilyDTO> familyMap = new HashMap<Ii, FamilyDTO>();
        familyMap.put(IiConverter.convertToPoFamilyIi("1"), getPoFamilyDTO(1L));
        familyMap.put(IiConverter.convertToPoFamilyIi("2"), getPoFamilyDTO(2L));
        when(fs.getFamilies(any(Set.class))).thenReturn(familyMap);
        
        // 2 families 1 common org and 1 unique per family
        when(fs.getActiveRelationships(1L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 3L}));
        assertEquals(3, FamilyHelper.getAllRelatedOrgs(1L).size());

        // 2 families with the same 2 members
        when(fs.getActiveRelationships(2L)).thenReturn(getRelationships(new Long[] {1L, 2L}));
        assertEquals(2, FamilyHelper.getAllRelatedOrgs(1L).size());
    }

    private FamilyDTO getPoFamilyDTO(Long id) {
        FamilyDTO family = new FamilyDTO();
        family.setIdentifier(IiConverter.convertToPoFamilyIi(id.toString()));
        family.setName(EnOnConverter.convertToEnOn("family" + id));
        return family;
    }

    private List<FamilyOrganizationRelationshipDTO> getRelationships(Long[] orgIds) {
        List<FamilyOrganizationRelationshipDTO> result = new ArrayList<FamilyOrganizationRelationshipDTO>();
        for (Long orgId : orgIds) {
            FamilyOrganizationRelationshipDTO rel = new FamilyOrganizationRelationshipDTO();
            rel.setOrgIdentifier(IiConverter.convertToPaOrganizationIi(orgId));
            result.add(rel);
        }
        return result;
    }

    @Test
    public void assignFamilyAccrualAccessNullUserTest() throws Exception {
        ejb.assignFamilyAccrualAccess(null, ru, null);
    }

    @Test
    public void assignFamilyAccrualAccessNullCreatorTest() throws Exception {
        ejb.assignFamilyAccrualAccess(ru, null, null);
    }

    @Test
    public void assignFamilyAccrualAccessNoTrialsTest() throws Exception {
        assertFalse(ru.getFamilyAccrualSubmitter());
        ejb.assignFamilyAccrualAccess(ru, ru, null);
        assertTrue(ru.getFamilyAccrualSubmitter());
    }

    @Test
    public void unassignAllAccrualAccessNullUserTest() throws Exception {
        ejb.unassignAllAccrualAccess(null, ru);
    }

    @Test(expected = PAException.class)
    public void unassignAllAccrualAccessNullCreatorTest() throws Exception {
        ejb.unassignAllAccrualAccess(ru, null);
    }

    @Test
    public void unassignAllAccrualAccessNoExistingAccessTest() throws Exception {
        ru.setFamilyAccrualSubmitter(true);
        ru.setSiteAccrualSubmitter(true);
        registryUserEjb.updateUser(ru);
        RegistryUser updated = registryUserEjb.getUserById(ru.getId());
        assertTrue(updated.getFamilyAccrualSubmitter());
        assertTrue(updated.getSiteAccrualSubmitter());
        ejb.unassignAllAccrualAccess(ru, ru);
        updated = registryUserEjb.getUserById(ru.getId());
        assertFalse(updated.getFamilyAccrualSubmitter());
        assertFalse(updated.getSiteAccrualSubmitter());
    }

    @Test
    public void unassignAllAccrualAccessExistingAccessCompleteTest() throws Exception {
        StudyProtocol sp = createComplete();
        assignAccess(sp);
        assertEquals(1, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        ejb.unassignAllAccrualAccess(ru, ru);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
    }

    @Test
    public void unassignAllAccrualAccessExistingAccessAbbreviatedTest() throws Exception {
        StudyProtocol sp = createAbbreviated();
        assignAccess(sp);
        assertEquals(1, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
        ejb.unassignAllAccrualAccess(ru, ru);
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.ASSIGNED));
        assertEquals(0, studyAccessCount(ActiveInactiveCode.ACTIVE, AssignmentActionCode.UNASSIGNED));
        assertEquals(1, studyAccessCount(ActiveInactiveCode.INACTIVE, AssignmentActionCode.UNASSIGNED));
    }

    @Test
    public void updateSiteAndFamilyPermissionsNullTest() throws Exception {
        ejb.updateSiteAndFamilyPermissions(null);
    }

    @Test
    public void updateSiteAndFamilyPermissionsTest() throws Exception {
        ejb.updateSiteAndFamilyPermissions(TestSchema.studyProtocolIds.get(0));
    }

    private StudyProtocol createComplete() throws Exception {
        sess.createQuery("DELETE FROM StudySiteAccrualAccess").executeUpdate();
        sess.createQuery("DELETE FROM StudyAccrualAccess").executeUpdate();
        StudyProtocol sp = TestSchema.createStudyProtocolObj();
        StudyRecruitmentStatus recruitmentStatus = TestSchema
                .createStudyRecruitmentStatus(sp);
        TestSchema.addUpdObject(recruitmentStatus);
        return sp;
    }

    private StudyProtocol createAbbreviated() throws Exception {
        StudyProtocol sp = createComplete();
        sp.setProprietaryTrialIndicator(true);
        TestSchema.addUpdObject(sp);
        return sp;
    }

    private void assignAccess(StudyProtocol sp) throws Exception {
        siteAccessEjb.assignTrialLevelAccrualAccess(ru, AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, 
                Arrays.asList(sp.getId()), "TEST", ru);
    }

    private long studyAccessCount(ActiveInactiveCode statusCode, AssignmentActionCode actionCode) throws Exception {
        Query qry = sess.createQuery("FROM StudyAccrualAccess saa "
                + "WHERE saa.statusCode = :statusCode AND saa.actionCode = :actionCode");
        qry.setParameter("actionCode", actionCode);
        qry.setParameter("statusCode", statusCode);
        return qry.list().size();
    }
}
