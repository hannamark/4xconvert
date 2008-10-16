package gov.nih.nci.po.service.correlation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.IdentifierReliability;
import gov.nih.nci.coppa.iso.IdentifierScope;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.OrganizationResourceProvider;
import gov.nih.nci.po.data.bo.RoleStatus;
import gov.nih.nci.po.data.convert.IdConverter;
import gov.nih.nci.po.service.AbstractHibernateTestCase;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.po.service.OrganizationServiceBeanTest;
import gov.nih.nci.po.util.PoXsnapshotHelper;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.OrganizationResourceProviderDTO;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.junit.Test;

public class OrganizationResourceProviderDTOTest extends AbstractHibernateTestCase {

    private static final RoleStatus STATUS = RoleStatus.ACTIVE;

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
        OrganizationResourceProvider orgRole = getExampleTestClass();
        OrganizationResourceProviderDTO dto = (OrganizationResourceProviderDTO) PoXsnapshotHelper.createSnapshot(orgRole);

        Ii expectedIi = new Ii();
        expectedIi.setDisplayable(true);
        expectedIi.setScope(IdentifierScope.OBJ);
        expectedIi.setReliability(IdentifierReliability.ISS);
        expectedIi.setExtension("" + 2);
        expectedIi.setIdentifierName(IdConverter.ORG_IDENTIFIER_NAME);
        expectedIi.setRoot(IdConverter.ORG_ROOT);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getPlayerIdentifier()));

        expectedIi.setExtension("" + 3);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi, dto.getScoperIdentifier()));
        assertEquals(STATUS.name().toLowerCase(), dto.getStatus().getCode());

        Ii expectedIi1 = buildIdentifier(1L);
        assertTrue(EqualsBuilder.reflectionEquals(expectedIi1, ((OrganizationResourceProviderDTO) dto).getIdentifier()));
    }

    @Test
    public void testCreateFullModelFromSnapshot() throws Exception {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = orgTest.createOrganization();
        PoDto dto = (PoDto) getExampleTestClassDTO(scoperId, playerId);
        OrganizationResourceProvider bo = (OrganizationResourceProvider) PoXsnapshotHelper.createModel(dto);

        assertEquals(1L, bo.getId().longValue());
        assertEquals(scoperId, bo.getScoper().getId().longValue());
        assertEquals(playerId, bo.getPlayer().getId().longValue());
        assertEquals(STATUS, bo.getStatus());

    }

    @Test
    public void testSnapshotToModelToSnapshot() throws EntityValidationException {
        OrganizationServiceBeanTest orgTest = new OrganizationServiceBeanTest();
        orgTest.loadData();
        orgTest.setUpData();

        long scoperId = orgTest.createOrganization();
        long playerId = orgTest.createOrganization();
        OrganizationResourceProvider bo = getExampleTestClass();
        bo.getPlayer().setId(playerId);
        bo.getScoper().setId(scoperId);
        
        OrganizationResourceProviderDTO dto = (OrganizationResourceProviderDTO) PoXsnapshotHelper.createSnapshot(bo);
        OrganizationResourceProvider newBO = (OrganizationResourceProvider) PoXsnapshotHelper.createModel(dto);
        OrganizationResourceProviderDTO newDto = (OrganizationResourceProviderDTO) PoXsnapshotHelper.createSnapshot(newBO);

        assertTrue(EqualsBuilder.reflectionEquals(dto.getIdentifier(), newDto.getIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getPlayerIdentifier(), newDto.getPlayerIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getScoperIdentifier(), newDto.getScoperIdentifier()));
        assertTrue(EqualsBuilder.reflectionEquals(dto.getStatus(), newDto.getStatus()));
    }

    /**
     * {@inheritDoc}
     */
    protected OrganizationResourceProvider getExampleTestClass() {
        OrganizationResourceProvider bo = new OrganizationResourceProvider();
        bo.setId(1L);
        bo.setStatus(STATUS);
        bo.setPlayer(new Organization());
        bo.getPlayer().setId(2L);
        bo.setScoper(new Organization());
        bo.getScoper().setId(3L);
        return bo;
    }

    /**
     * {@inheritDoc}
     */
    protected OrganizationResourceProviderDTO getExampleTestClassDTO(Long scoperId, Long playerId) {
        OrganizationResourceProviderDTO dto = new OrganizationResourceProviderDTO();
        dto.setScoperIdentifier(getScoperIi(scoperId));
        dto.setPlayerIdentifier(getPlayerIi(playerId));
        
        Cd status = new Cd();
        status.setCode("active");
        dto.setStatus(status);
        Ii ii = buildIdentifier(1L);
        dto.setIdentifier(ii);

        return dto;
    }

    /**
     * Could this just use the IdConverterRegistry instead? 
     * @param extensionId
     * @return
     */
    private Ii buildIdentifier(long extensionId) {
        Ii ii = new Ii();
        ii.setExtension("" + extensionId);
        ii.setDisplayable(true);
        ii.setScope(IdentifierScope.OBJ);
        ii.setReliability(IdentifierReliability.ISS);
        ii.setRoot(IdConverter.ORG_RESOURCE_PROVIDER_ROOT);
        ii.setIdentifierName(IdConverter.ORG_RESOURCE_PROVIDER_IDENTIFIER_NAME);
        return ii;
    }

}
