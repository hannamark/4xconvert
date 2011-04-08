package gov.nih.nci.pa.util;

import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareFacilityCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OrganizationalContactCorrelationServiceRemote;
import gov.nih.nci.services.correlation.OversightCommitteeCorrelationServiceRemote;
import gov.nih.nci.services.correlation.ResearchOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * A class for all Po look-ups.
 * @author NAmiruddin
 *
 */
public class PoJndiServiceLocator implements PoServiceLocator {

    /**
     * {@inheritDoc}
     */
    public OrganizationEntityServiceRemote getOrganizationEntityService() {
        String serverInfo = "/po/OrganizationEntityServiceBean/remote";
        return (OrganizationEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareFacilityCorrelationServiceRemote getHealthCareFacilityCorrelationService() {
        String serverInfo = "/po/HealthCareFacilityCorrelationServiceBean/remote";
        return (HealthCareFacilityCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public ResearchOrganizationCorrelationServiceRemote getResearchOrganizationCorrelationService() {
        String serverInfo = "/po/ResearchOrganizationCorrelationServiceBean/remote";
        return (ResearchOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public OversightCommitteeCorrelationServiceRemote getOversightCommitteeCorrelationService() {
        String serverInfo = "/po/OversightCommitteeCorrelationServiceBean/remote";
        return (OversightCommitteeCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public PersonEntityServiceRemote getPersonEntityService() {
        String serverInfo = "/po/PersonEntityServiceBean/remote";
        return (PersonEntityServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() {
        String serverInfo = "/po/ClinicalResearchStaffCorrelationServiceBean/remote";
        return (ClinicalResearchStaffCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() {
        String serverInfo = "/po/HealthCareProviderCorrelationServiceBean/remote";
        return (HealthCareProviderCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public OrganizationalContactCorrelationServiceRemote getOrganizationalContactCorrelationService() {
        String serverInfo = "/po/OrganizationalContactCorrelationServiceBean/remote";
        return (OrganizationalContactCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedOrganizationCorrelationServiceRemote getIdentifiedOrganizationEntityService() {
        String serverInfo = "/po/IdentifiedOrganizationCorrelationServiceBean/remote";
        return (IdentifiedOrganizationCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

    /**
     * {@inheritDoc}
     */
    public IdentifiedPersonCorrelationServiceRemote getIdentifiedPersonEntityService() {
        String serverInfo = "/po/IdentifiedPersonCorrelationServiceBean/remote";
        return (IdentifiedPersonCorrelationServiceRemote) JNDIUtil.lookupPo(serverInfo);
    }

}
