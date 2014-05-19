package gov.nih.nci.pa.util;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.ValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerSyncWithCaDSRServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRPVSyncJobHelper {
    private ApplicationService appService;
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID = 5473L;
    private static final Integer CHUNK_SIZE = 1000;
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
    
    @SuppressWarnings("unchecked")
    public List<CaDSRDTO> getAllValuesFromCaDSR()
            throws PAException {
        List<CaDSRDTO> values = new ArrayList<CaDSRDTO>();

        appService = getApplicationService();
        try {
            DetachedCriteria detachedCrit = DetachedCriteria.forClass(DataElement.class).add(Property
                    .forName("publicID").eq(CDE_PUBLIC_ID)).add(Property.forName("latestVersionIndicator")
                            .eq("Yes"));
            detachedCrit.setFetchMode("valueDomain", FetchMode.JOIN);
            List<DataElement> results = (List<DataElement>) (List<?>) appService.query(detachedCrit);
            if (results.size() < 1) {
                throw new PAException("Search of caDSR returned no results.");
            }
            DataElement de = results.get(0);
            String vdId = ((ValueDomain) de.getValueDomain()).getId();

            DetachedCriteria dc = constructBaseCriteria(vdId);
            ProjectionList proj = Projections.projectionList();
            proj.add(Projections.min("vm.publicID"));
            proj.add(Projections.groupProperty("enumeratedValueDomain.id"));
            dc.setProjection(proj);
            List<Object> qRslt = appService.query(dc);
            Long minId = (Long) ((Object[]) qRslt.get(0))[0];

            dc = constructBaseCriteria(vdId);
            proj = Projections.projectionList();
            proj.add(Projections.max("vm.publicID"));
            proj.add(Projections.groupProperty("enumeratedValueDomain.id"));
            dc.setProjection(proj);
            qRslt = appService.query(dc);
            Long maxId = (Long) ((Object[]) qRslt.get(0))[0];

            List<Object> result = new ArrayList<Object>();
            for (Long id = minId; id <= maxId; id += CHUNK_SIZE) {
                dc = constructBaseCriteria(vdId);
                dc.add(Property.forName("vm.publicID").ge(id));
                dc.add(Property.forName("vm.publicID").lt(id + CHUNK_SIZE));
                List<Object> permissibleValues = appService.query(dc);
                result.addAll(new ArrayList<Object>(permissibleValues));
            }
            values = getSearchResults(result);
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

    private DetachedCriteria constructBaseCriteria(String vdId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ValueDomainPermissibleValue.class);
        criteria.add(Property.forName("enumeratedValueDomain.id").eq(vdId));
        criteria.setFetchMode("permissibleValue", FetchMode.JOIN);
        criteria.setFetchMode("permissibleValue.valueMeaning", FetchMode.JOIN);
        criteria.createAlias("permissibleValue", "pv").createAlias("pv.valueMeaning", "vm");
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
