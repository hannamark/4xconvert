package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.QualifiedEntity;
import gov.nih.nci.po.data.bo.QualifiedEntityType;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.QualifiedEntityDTO;

import java.net.URISyntaxException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Before;
import org.junit.Test;


public class QualifiedEntityDTOTest extends AbstractHibernateTestCase {

    private static final QualifiedEntityType TYPE = new QualifiedEntityType("MD", "Medical Doctor");
    private static final RoleStatus STATUS = RoleStatus.ACTIVE;

    @Before
    public void loadData() {
    }

    private static final long ID = 1L;

    private Ii getPlayerIi(Long id) {
        Ii ii;
        ii = new Ii();
        ii.setExtension("" + id);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        ii.setRoot(IdConverter.PERSON_ROOT);
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
        QualifiedEntity orgRole = getExampleTestClass();
        QualifiedEntityDTO dto = (QualifiedEntityDTO) PoXsnapshotHelper.createSnapshot(orgRole);

        Ii expectedIi = new Ii();
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setExtension("" + 2);
        expectedIi.setIdentifierName(IdConverter.PERSON_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.PERSON_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getPlayerIdentifier()));

        expectedIi.setExtension("" + 3);
        expectedIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORG_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getScoperIdentifier()));

        assertEquals(STATUS.name().toLowerCase(), dto.getStatus().getCode());
        assertEquals(TYPE.getCode(), dto.getTypeCode().getCode());
        assertTrue(EqualsBuilder.reflectionEquals(buildIdentifier(ID), (dto).getIdentifier()));
    }

    @Test
    public void testCreateFullModelFromSnapshot() throws Exception {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        PersonServiceBeanTest pTest = new PersonServiceBeanTest();
        // skip since same data is loaded (AbstractBeanTest)
        // pTest.loadData();
        // instead copy over relevant values
        pTest.setDefaultCountry(orgTest.getDefaultCountry());
        pTest.setOversightCommitee(orgTest.getOversightCommitee());
        pTest.setResearchOrgType(orgTest.getResearchOrgType());
        pTest.setUser(orgTest.getUser());
        pTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = pTest.createPerson();
        PoDto dto = getExampleTestClassDTO(scoperId, playerId);
        QualifiedEntity bo = (QualifiedEntity) PoXsnapshotHelper.createModel(dto);

        assertEquals(ID, bo.getId().longValue());
        assertEquals(scoperId, bo.getScoper().getId().longValue());
        assertEquals(playerId, bo.getPlayer().getId().longValue());
        assertEquals(STATUS, bo.getStatus());
    }

    @Test
    public void testSnapshotToModelToSnapshot() throws EntityValidationException {
        // verify that moving from snapshot to model and back to snapshot results in original data.
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        PersonServiceBeanTest pTest = new PersonServiceBeanTest();
        // skip since same data is loaded (AbstractBeanTest)
        // pTest.loadData();
        // instead copy over relevant values
        pTest.setDefaultCountry(orgTest.getDefaultCountry());
        pTest.setOversightCommitee(orgTest.getOversightCommitee());
        pTest.setResearchOrgType(orgTest.getResearchOrgType());
        pTest.setUser(orgTest.getUser());
        pTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = pTest.createPerson();

        QualifiedEntity bo = getExampleTestClass();
        bo.getPlayer().setId(playerId);
        bo.getScoper().setId(scoperId);

        QualifiedEntityDTO dto = (QualifiedEntityDTO) PoXsnapshotHelper.createSnapshot(bo);
        QualifiedEntity newBO = (QualifiedEntity) PoXsnapshotHelper.createModel(dto);
        QualifiedEntityDTO newDto = (QualifiedEntityDTO) PoXsnapshotHelper.createSnapshot(newBO);

        assertTrue(EqualsBuilder.reflectionEquals(dto.getIdentifier(), newDto.getIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getPlayerIdentifier(), newDto.getPlayerIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getScoperIdentifier(), newDto.getScoperIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getStatus(), newDto.getStatus()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getTypeCode(), newDto.getTypeCode()));
    }

    /**
     * {@inheritDoc}
     */
    protected QualifiedEntity getExampleTestClass() {
        QualifiedEntity bo = new QualifiedEntity();
        bo.setId(ID);
        bo.setStatus(STATUS);
        bo.setPlayer(new Person());
        bo.getPlayer().setId(2L);
        bo.setScoper(new Organization());
        bo.getScoper().setId(3L);
        bo.setTypeCode(TYPE);
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    protected QualifiedEntityDTO getExampleTestClassDTO(Long scoperId, Long playerId) throws URISyntaxException {
        QualifiedEntityDTO dto = new QualifiedEntityDTO();
        dto.setScoperIdentifier(getScoperIi(scoperId));
        dto.setPlayerIdentifier(getPlayerIi(playerId));

        Cd status = new Cd();
        status.setCode(STATUS.name().toLowerCase());
        dto.setStatus(status);

        Cd type = new Cd();
        type.setCode(TYPE.getCode());
        dto.setTypeCode(type);

        dto.setIdentifier(buildIdentifier(ID));

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
        ii.setRoot(IdConverter.QUALIFIED_ENTITY_ROOT);
        ii.setIdentifierName(IdConverter.QUALIFIED_ENTITY_IDENTIFIER_NAME);
        return ii;
    }
}
