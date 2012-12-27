package gov.nih.nci.pa.util;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.pa.enums.BioMarkerAttributesCode;
import gov.nih.nci.pa.service.MarkerAttributesServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * 
 * @author Reshma Koganti
 * 
 */
public class CaDSRSyncHelper {
    private ApplicationService appService;
    /** The CDE public Id for Assay Type Attribute. */
    private static final Long CDE_PUBLIC_ID_ASSAY = 64731L;
    /** The CDE public Id for BioMarker Use Attribute. */
    private static final Long CDE_PUBLIC_ID_USE = 2939411L;
    /** The CDE public Id for BioMarker Purpose Attribute. */
    private static final Long CDE_PUBLIC_ID_PURPOSE = 2939397L;
    /** The CDE public Id for Specimen Type Attribute. */
    private static final Long CDE_PUBLIC_ID_SPECIMEN = 3111302L;
    /** The CDE public Id for Specimen Collection Attribute. */
    private static final Long CDE_PUBLIC_ID_SP_COL = 2939404L;
    /** The CDE public Id for EvaluationType Attribute. */
    private static final Long CDE_PUBLIC_ID_EVAL = 3645784L;
    /** The LOG details. */
    private static final Logger LOG = Logger.getLogger(CaDSRSyncHelper.class);

    private MarkerAttributesServiceLocal markerAttributesService;
    private PlannedMarkerServiceLocal plannedMarkerService;
    /**
     * updates Marker Tables.
     * 
     * @throws PAException
     *             exception
     */
    public void updateMarkerTables() throws PAException {
        markerAttributesService = PaRegistry.getMarkerAttributesService();
        Map<Long, Map<String, String>> map = getCaDSRValues(CDE_PUBLIC_ID_ASSAY);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.ASSAY_TYPE, map);
        }
        map = getCaDSRValues(CDE_PUBLIC_ID_USE);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.BIOMARKER_USE, map);
        }

        map = getCaDSRValues(CDE_PUBLIC_ID_PURPOSE);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.BIOMARKER_PURPOSE, map);
        }

        map = getCaDSRValues(CDE_PUBLIC_ID_SPECIMEN);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.SPECIMEN_TYPE, map);
        }

        map = getCaDSRValues(CDE_PUBLIC_ID_SP_COL);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.SPECIMEN_COLLECTION, map);
        }

        map = getCaDSRValues(CDE_PUBLIC_ID_EVAL);
        if (!map.isEmpty()) {
            markerAttributesService.updateMarker(
                    BioMarkerAttributesCode.EVALUATION_TYPE, map);
        }
    }

    /**
     * get CaDSR values.
     * 
     * @param publicId
     *            publicId
     * @return map<Stirng, String> map
     */
    public Map<Long, Map<String, String>> getCaDSRValues(Long publicId) {
        Map<Long, Map<String, String>> values = new HashMap<Long, Map<String, String>>();
        try {
            appService = ApplicationServiceProvider.getApplicationService();
            try {
                DataElement dataElement = new DataElement();
                dataElement.setPublicID(publicId);
                dataElement.setLatestVersionIndicator("Yes");
                Collection<Object> results = appService.search(
                        DataElement.class, dataElement);
                DataElement de = (DataElement) results.iterator().next();
                String vdId = ((EnumeratedValueDomain) de.getValueDomain())
                        .getId();
                List<Object> permissibleValues = appService
                        .query(constructSearchCriteria(vdId));
                values = getSearchResults(permissibleValues);
            } catch (Exception e) {
                LOG.error("Error while querying caDSR", e);
            }
        } catch (Exception e) {
            LOG.error(
                    "Error attempting to instantiate caDSR Application Service.",
                    e);
        }
        return values;
    }

    private DetachedCriteria constructSearchCriteria(String vdId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(
                ValueDomainPermissibleValue.class, "vdpv");
        criteria.add(Expression.eq("enumeratedValueDomain.id", vdId));
        criteria.createAlias("permissibleValue", "pv").createAlias(
                "pv.valueMeaning", "vm");
        return criteria;
    }

    private Map<Long, Map<String, String>> getSearchResults(
            List<Object> permissibleValues) {
        Map<Long, Map<String, String>> results = new HashMap<Long, Map<String, String>>();
        for (Object obj : permissibleValues) {
            ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) obj;
            ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
            vm.getPublicID();
            Map<String, String> map = new HashMap<String, String>();
            map.put(vdpv.getPermissibleValue().getValue(), vm.getLongName());
            results.put(vm.getPublicID(), map);
        }
        return results;
    }

    /**
     * sync planned Marker tables values.
     * @throws PAException exception
     *
     */
    public void syncPlannedMarkerAttributes() throws PAException {
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        markerAttributesService = PaRegistry.getMarkerAttributesService();
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.ASSAY_TYPE,
                CDE_PUBLIC_ID_ASSAY, BioMarkerAttributesCode.ASSAY_TYPE_CODE);
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.BIOMARKER_PURPOSE,
                CDE_PUBLIC_ID_PURPOSE, BioMarkerAttributesCode.ASSAY_PURPOSE_CODE);
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.BIOMARKER_USE,
                CDE_PUBLIC_ID_USE, BioMarkerAttributesCode.ASSAY_USE_CODE);
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.EVALUATION_TYPE,
                CDE_PUBLIC_ID_EVAL, BioMarkerAttributesCode.EVALUATION_TYPE_CODE);
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.SPECIMEN_TYPE,
                CDE_PUBLIC_ID_SPECIMEN, BioMarkerAttributesCode.TISSUE_SPECIMEN_TYPE_CODE);
        updatePlannedMarkerAttributes(BioMarkerAttributesCode.SPECIMEN_COLLECTION,
                CDE_PUBLIC_ID_SP_COL, BioMarkerAttributesCode.TISSUE_COLLECTION_METHOD_CODE);
    }
    
    private void updatePlannedMarkerAttributes(BioMarkerAttributesCode code, 
            Long publicId, BioMarkerAttributesCode attributeCode) throws PAException {
        Map<Long, Map<String, String>> map = 
            markerAttributesService.attributeValuesWithCaDSR(code);
        Map<Long , Map<String, String>> mapCaDSR =  getCaDSRValues(publicId);
        Iterator<Map.Entry<Long, Map<String, String>>> itr = map.entrySet().iterator();
       
        while (itr.hasNext()) {
            Map.Entry<Long, Map<String, String>> entry = itr.next();
           if (mapCaDSR.containsKey(entry.getKey())) {
               Map<String, String> caDSRValue = mapCaDSR.get(entry.getKey());
               Map<String, String> value = entry.getValue();      
               Iterator<Map.Entry<String, String>> newCadsrValue = caDSRValue.entrySet().iterator();
               Iterator<Map.Entry<String, String>> oldValue = value.entrySet().iterator();
               while (newCadsrValue.hasNext()) {
                   Map.Entry<String, String> newCadsrentry = newCadsrValue.next();  
                   String oldName = "";
                   if (oldValue.hasNext()) {
                       Map.Entry<String, String> newOldValue = oldValue.next();
                       oldName = newOldValue.getKey().toString();
                   }              
                  if (!value.containsKey(newCadsrentry.getKey()) || !value.containsValue(newCadsrentry.getValue())) {
                      plannedMarkerService.updatePlannedMarkerAttributeValues(attributeCode,
                              oldName, newCadsrentry.getKey().toString());        
                   }     
               }     
           }      
        }
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
     * @return the markerAttributesService
     */
    public MarkerAttributesServiceLocal getMarkerAttributesService() {
        return markerAttributesService;
    }

    /**
     * @param markerAttributesService
     *            the markerAttributesService to set
     */
    public void setMarkerAttributesService(
            MarkerAttributesServiceLocal markerAttributesService) {
        this.markerAttributesService = markerAttributesService;
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
    
}
