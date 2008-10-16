package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.IdentifiedOrganization;
import gov.nih.nci.po.data.bo.IdentifiedOrganizationType;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;

import java.net.URISyntaxException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

public class IdentifiedOrganizationDTOTest extends AbstractHibernateTestCase {
    private static final IdentifiedOrganizationType TYPE = new IdentifiedOrganizationType("Member");
    private static final RoleStatus STATUS = RoleStatus.ACTIVE;
    private static final long ID = 1L;

    private Ii getPlayerIi(Long id) {
        Ii ii;
        ii = new Ii();
        ii.setExtension("" + id);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        return ii;
    }

    private Ii getScoperIi(Long id) {
        Ii ii;
        ii = new Ii();
        ii.setExtension("" + id);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.ORG_ROOT);
        return ii;
    }

    @Test
    public void testCreateFullSnapshotFromModel() {
        IdentifiedOrganization orgRole = getExampleTestClass();
        IdentifiedOrganizationDTO dto = (IdentifiedOrganizationDTO) PoXsnapshotHelper.createSnapshot(orgRole);

        Ii expectedIi = new Ii();
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setExtension("" + 2);
        expectedIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORG_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getPlayerIdentifier()));

        expectedIi.setExtension("" + 3);
        expectedIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORG_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getScoperIdentifier()));

        assertEquals(STATUS.name().toLowerCase(), dto.getStatus().getCode());
        assertEquals(TYPE.getCode(), dto.getType().getCode());
        // check id
        Ii expectedIi1 = buildIdentifier(ID);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi1, ((IdentifiedOrganizationDTO) dto).getIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(buildAssignedId(), dto.getAssignedId()));
    }

    @Test
    public void testCreateFullModelFromSnapshot() throws Exception {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = orgTest.createOrganization();
        PoDto dto = (PoDto) getExampleTestClassDTO(scoperId, playerId);
        PoHibernateUtil.getCurrentSession().save(TYPE);
        IdentifiedOrganization bo = (IdentifiedOrganization) PoXsnapshotHelper.createModel(dto);

        assertEquals(ID, bo.getId().longValue());
        assertEquals(scoperId, bo.getScoper().getId().longValue());
        assertEquals(playerId, bo.getPlayer().getId().longValue());
        assertEquals(STATUS, bo.getStatus());
        assertTrue(EqualsBuilder.reflectionEquals(buildAssignedId(), bo.getAssignedIdentifier()));
    }

    @Test
    public void testSnapshotToModelToSnapshot() throws EntityValidationException {
        // verify that moving from snapshot to model and back to snapshot results in original data.
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = orgTest.createOrganization();

        IdentifiedOrganization bo = getExampleTestClass();
        bo.getPlayer().setId(playerId);
        bo.getScoper().setId(scoperId);

        IdentifiedOrganizationDTO dto = (IdentifiedOrganizationDTO) PoXsnapshotHelper.createSnapshot(bo);
        PoHibernateUtil.getCurrentSession().save(bo.getType());
        IdentifiedOrganization newBO = (IdentifiedOrganization) PoXsnapshotHelper.createModel(dto);
        IdentifiedOrganizationDTO newDto = (IdentifiedOrganizationDTO) PoXsnapshotHelper.createSnapshot(newBO);

        assertTrue(EqualsBuilder.reflectionEquals(dto.getIdentifier(), newDto.getIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getPlayerIdentifier(), newDto.getPlayerIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getScoperIdentifier(), newDto.getScoperIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getStatus(), newDto.getStatus()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getType(), newDto.getType()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getAssignedId(), newDto.getAssignedId()));
    }

    /**
     * {@inheritDoc}
     */
    protected IdentifiedOrganization getExampleTestClass() {
        IdentifiedOrganization bo = new IdentifiedOrganization();
        bo.setId(ID);
        bo.setStatus(STATUS);
        bo.setPlayer(new Organization());
        bo.getPlayer().setId(2L);
        bo.setScoper(new Organization());
        bo.getScoper().setId(3L);
        bo.setType(TYPE);
        bo.setAssignedIdentifier(buildAssignedId());
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    protected IdentifiedOrganizationDTO getExampleTestClassDTO(Long scoperId, Long playerId) throws URISyntaxException {
        IdentifiedOrganizationDTO dto = new IdentifiedOrganizationDTO();
        dto.setScoperIdentifier(getScoperIi(scoperId));
        dto.setPlayerIdentifier(getPlayerIi(playerId));

        Cd status = new Cd();
        status.setCode(STATUS.name().toLowerCase());
        dto.setStatus(status);

        Cd type = new Cd();
        type.setCode(TYPE.getCode());
        dto.setType(type);

        dto.setIdentifier(buildIdentifier(ID));
        dto.setAssignedId(buildAssignedId());
        return dto;
    }

    /**
     * Could this just use the IdConverterRegistry instead?
     * 
     * @param extensionId
     * @return
     */
    private Ii buildIdentifier(long extensionId) {
        Ii ii = new Ii();
        ii.setExtension("" + extensionId);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot(IdConverter.IDENTIFIED_ORG_ROOT);
        ii.setIdentifierName(IdConverter.IDENTIFIED_ORG_IDENTIFIER_NAME);
        return ii;
    }

    private Ii buildAssignedId() {
        Ii ii = new Ii();
        ii.setDisplayable(Boolean.TRUE);
        ii.setExtension("extension");
        ii.setIdentifierName("identifierName");
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot("root");
        ii.setScope(IdentifierScope.OBJ);
        return ii;
    }
}
