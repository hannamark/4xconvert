package gov.nih.nci.pa.util;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;

import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRPVSyncJobHelper {
    private ApplicationService appService;
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID = 5473L;
    /** The LOG details. */
    private static final Logger LOG = Logger
            .getLogger(CaDSRPVSyncJobHelper.class);
    private PlannedMarkerServiceLocal plannedMarkerService;
    private PlannedMarkerSyncWithCaDSRServiceLocal permissibleService;
    /**
     * updates the planned marker sync table
     * @throws PAException exception
     */
    public void updatePlannedMarkerSyncTable() throws PAException {
        permissibleService = PaRegistry.getPMWithCaDSRService();
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        List<CaDSRDTO> values = getAllValuesFromCaDSR();
        permissibleService.syncTableWithCaDSR(values);
    }

    /**
     * 
     * @return map<Stirng, String> map
     * @throws PAException
     *             exception
     */
    
    public List<CaDSRDTO> getAllValuesFromCaDSR()
            throws PAException {
        List<CaDSRDTO> values = new ArrayList<CaDSRDTO>();
        
            appService = getApplicationService();
            try {
                DataElement dataElement = new DataElement();
                dataElement.setPublicID(CDE_PUBLIC_ID);
                dataElement.setLatestVersionIndicator("Yes");
                Collection<Object> results = appService.search(DataElement.class, dataElement);
                DataElement de = (DataElement) results.iterator().next();
                String vdId = ((EnumeratedValueDomain) de.getValueDomain()).getId();
                List<Object> permissibleValues = appService.query(constructSearchCriteria(vdId));
                values = getSearchResults(permissibleValues);  
            } catch (Exception e) {
                LOG.error("Error while querying caDSR", e);
            }
        return values;
    }
    /**
     * 
     * @return ApplicationService appService
     */
    public ApplicationService getApplicationService() {
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error(
                    "Error attempting to instantiate caDSR Application Service.",
                    e);
        }
        return appService;
    }
    private DetachedCriteria constructSearchCriteria(String vdId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(
                ValueDomainPermissibleValue.class, "vdpv");
        criteria.add(Expression.eq("enumeratedValueDomain.id", vdId));
        return criteria;
    }
    /**
     * gets the results 
     * @param permissibleValues permissibleValues
     * @return List resultsList
     */
    protected List<CaDSRDTO> getSearchResults(
            List<Object> permissibleValues) {
        List<CaDSRDTO> resultsList = new ArrayList<CaDSRDTO>();
        for (Object obj : permissibleValues) {
            ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) obj;
            CaDSRDTO dto = new CaDSRDTO();
            ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
            dto.setId(vdpv.getId());
            dto.setVmName(vdpv.getPermissibleValue().getValue());
            dto.setVmMeaning(vm.getLongName());
            dto.setVmDescription(vm.getDescription());
            dto.setPublicId(vm.getPublicID());
            resultsList.add(dto);  
        }
        return resultsList;
    }

    /**
     * @return the appService
     */
    public ApplicationService getAppService() {
        return appService;
    }

    /**
     * @param appService
     *            the appService to set
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }

    /**
     * @return the plannedMarkerServiceLocal
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * @param plannedMarkerService
     *            the plannedMarkerService to set
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * @return the permissibleService
     */
    public PlannedMarkerSyncWithCaDSRServiceLocal getPermissibleService() {
        return permissibleService;
    }

    /**
     * @param permissibleService
     *            the permissibleService to set
     */
    public void setPermissibleService(
            PlannedMarkerSyncWithCaDSRServiceLocal permissibleService) {
        this.permissibleService = permissibleService;
    }

}
