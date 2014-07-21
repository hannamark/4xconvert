package gov.nih.nci.webservices.rest.client.util;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.po.ws.common.types.EntityStatus;
import gov.nih.nci.po.ws.common.types.Family;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.family.FamilyServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;
import gov.nih.nci.webservices.rest.client.FamilyRestServiceClient;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Mock Po RestServiceLocator
 * 
 * @author vinodh.rc
 * 
 */
public class MockPoRestServiceLocator implements PoServiceLocator {

    private static final Logger LOG = Logger
            .getLogger(MockPoRestServiceLocator.class);
    private static final String ERROR_MSG = "An error occurred during mock set up process.";

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PersonEntityServiceRemote getPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyServiceRemote getFamilyServiceRemote() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FamilyRestServiceClient getFamilyService() {
        FamilyRestServiceClient client = mock(FamilyRestServiceClient.class);
        try {
            Family family = new Family();
            family.setName("some family name");
            family.setStatus(EntityStatus.ACTIVE);
            family.setId(1L);
            List<Family> familyList = new ArrayList<Family>();
            familyList.add(family);
            when(client.search(any(Family.class))).thenReturn(familyList);
        } catch (Exception e) {
            LOG.error(ERROR_MSG, e);
        }
        return client;
    }

}
