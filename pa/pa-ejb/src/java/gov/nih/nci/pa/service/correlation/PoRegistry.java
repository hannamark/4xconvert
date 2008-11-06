package gov.nih.nci.pa.service.correlation;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffCorrelationServiceRemote;
import gov.nih.nci.services.correlation.HealthCareProviderCorrelationServiceRemote;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;
import gov.nih.nci.services.person.PersonEntityServiceRemote;

/**
 * po ejbs.
 *
 * @author Naveen Amiruddin
 * @since 08/26/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */

public final class PoRegistry {

    private static final PoRegistry PO_REGISTRY = new PoRegistry();
    private PoServiceLocator poServiceLocator;

    /**
     * Constructor for the singleton instance.
     */
    private PoRegistry() {
        this.poServiceLocator = new PoJndiServiceLocator();
    }

    /**
     * @return the PO_REGISTRY
     */
    public static PoRegistry getInstance() {
        return PO_REGISTRY;
    }
    
    /**
     * 
     * @return OrganizationEntityServiceRemote
     * @throws PAException on error
     */
    public static OrganizationEntityServiceRemote getOrganizationEntityService() throws PAException {
        return getInstance().getPoServiceLocator().getOrganizationEntityService();
    }

    
    /**
     * 
     * @return PersonEntityServiceRemote
     * @throws PAException on error 
     */
    public static PersonEntityServiceRemote getPersonEntityService() throws PAException {
        return getInstance().getPoServiceLocator().getPersonEntityService();
    }
    
    /**
     * @return ClinicalResearchStaffCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static ClinicalResearchStaffCorrelationServiceRemote getClinicalResearchStaffCorrelationService() 
    throws PAException {
        return getInstance().getPoServiceLocator().getClinicalResearchStaffCorrelationService();
    }
    
    /**
     * @return HealthCareProviderCorrelationServiceRemote
     * @throws PAException on error 
     */
    public static HealthCareProviderCorrelationServiceRemote getHealthCareProviderCorrelationService() 
        throws PAException {
        return getInstance().getPoServiceLocator().getHealthCareProviderCorrelationService();
    }

    /**
     * 
     * @return pos
     */
    public PoServiceLocator getPoServiceLocator() {
        return poServiceLocator;
    }

    /**
     * 
     * @param poServiceLocator pos
     */
    public void setPoServiceLocator(PoServiceLocator poServiceLocator) {
        this.poServiceLocator = poServiceLocator;
    }
    
    
}
