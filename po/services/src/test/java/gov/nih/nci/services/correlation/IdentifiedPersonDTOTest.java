package gov.nih.nci.services.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.IdentifierReliability;
import gov.nih.nci.iso21090.IdentifierScope;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.po.data.bo.IdentifiedPerson;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.data.convert.IiConverter;
import gov.nih.nci.po.data.convert.IiDsetConverter;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.service.PersonServiceBeanTest;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;

import javax.jms.JMSException;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

public class IdentifiedPersonDTOTest extends AbstractHibernateTestCase {
    private static final RoleStatus STATUS = RoleStatus.ACTIVE;
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
        IdentifiedPerson orgRole = getExampleTestClass();
        IdentifiedPersonDTO dto = (IdentifiedPersonDTO) PoXsnapshotHelper.createSnapshot(orgRole);

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
        // check id
        Ii expectedIi1 = buildIdentifier(ID);
        Ii actualIi = IiDsetConverter.convertToIi(dto.getIdentifier());
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi1, actualIi));
        assertTrue(EqualsBuilder.reflectionEquals(buildAssignedId(), dto.getAssignedId()));
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
        IdentifiedPerson bo = (IdentifiedPerson) PoXsnapshotHelper.createModel(dto);

        assertEquals(ID, bo.getId().longValue());
        assertEquals(scoperId, bo.getScoper().getId().longValue());
        assertEquals(playerId, bo.getPlayer().getId().longValue());
        assertEquals(STATUS, bo.getStatus());
        assertTrue(EqualsBuilder.reflectionEquals(buildAssignedId(), bo.getAssignedIdentifier()));
    }

    @Test
    public void testSnapshotToModelToSnapshot() throws EntityValidationException, JMSException {
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

        IdentifiedPerson bo = getExampleTestClass();
        bo.getPlayer().setId(playerId);
        bo.getScoper().setId(scoperId);
        IdentifiedPersonDTO dto = (IdentifiedPersonDTO) PoXsnapshotHelper.createSnapshot(bo);
        IdentifiedPerson newBO = (IdentifiedPerson) PoXsnapshotHelper.createModel(dto);
        IdentifiedPersonDTO newDto = (IdentifiedPersonDTO) PoXsnapshotHelper.createSnapshot(newBO);

        assertTrue(EqualsBuilder.reflectionEquals(IiDsetConverter.convertToIi(dto.getIdentifier()),
                IiDsetConverter.convertToIi(newDto.getIdentifier())));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getPlayerIdentifier(), newDto.getPlayerIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getScoperIdentifier(), newDto.getScoperIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getStatus(), newDto.getStatus()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getAssignedId(), newDto.getAssignedId()));
    }

    protected IdentifiedPerson getExampleTestClass() {
        IdentifiedPerson bo = new IdentifiedPerson();
        bo.setId(ID);
        bo.setStatus(STATUS);
        bo.setPlayer(new Person());
        bo.getPlayer().setId(2L);
        bo.setScoper(new Organization());
        bo.getScoper().setId(3L);
        bo.setAssignedIdentifier(buildAssignedId());
        return bo;
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

    protected IdentifiedPersonDTO getExampleTestClassDTO(Long scoperId, Long playerId) {
        IdentifiedPersonDTO dto = new IdentifiedPersonDTO();
        dto.setScoperIdentifier(getScoperIi(scoperId));
        dto.setPlayerIdentifier(getPlayerIi(playerId));

        Cd status = new Cd();
        status.setCode(STATUS.name().toLowerCase());
        dto.setStatus(status);

        dto.setIdentifier(IiConverter.convertToDsetIi(buildIdentifier(ID)));
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
        ii.setRoot(IdConverter.IDENTIFIED_PERSON_ROOT);
        ii.setIdentifierName(IdConverter.IDENTIFIED_PERSON_IDENTIFIER_NAME);
        return ii;
    }
}
