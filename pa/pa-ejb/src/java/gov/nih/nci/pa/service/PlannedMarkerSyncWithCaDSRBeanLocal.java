package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.PlannedMarkerSyncWithCaDSR;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.convert.PlannedMarkerSyncWithCaDSRConverter;
import gov.nih.nci.pa.iso.dto.CaDSRDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerSyncWithCaDSRDTO;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PaRegistry;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * 
 * @author Reshma.Koganti
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(PaHibernateSessionInterceptor.class)
@SuppressWarnings("PMD.ExcessiveParameterList")
public class PlannedMarkerSyncWithCaDSRBeanLocal
        extends
        AbstractBaseIsoService<PlannedMarkerSyncWithCaDSRDTO, 
        PlannedMarkerSyncWithCaDSR, PlannedMarkerSyncWithCaDSRConverter>
        implements PlannedMarkerSyncWithCaDSRServiceLocal {

    private PlannedMarkerServiceLocal plannedMarkerService;

    // private static final String CSM_LOOKUP_ERR_MSG =
    // "CSM exception while retrieving CSM user: ";
    private static final String NAME = "name";

    private static final String CADSRID = "cadsrId";

    /**
     * @param valuesList
     *            valuesList
     * @throws PAException
     *             on error.
     */
    public void syncTableWithCaDSR(List<CaDSRDTO> valuesList)
            throws PAException {
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        // update and insert
        insertAndUpdateLogic(valuesList);
        // deletion
        changeStatusCodeValue(valuesList);
    }

    private Long maxIdentifierValue() throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        List<Number> queryList = null;
        SQLQuery query = session.createSQLQuery("select MAX(identifier) from planned_marker_sync_cadsr");
        queryList = query.list();
        return queryList.get(0).longValue();
    }

    private void insertAndUpdateLogic(List<CaDSRDTO> valuesList)
            throws PAException {
        for (CaDSRDTO value : valuesList) {
            Long caDSRId = value.getPublicId();
            String name = value.getVmName();
            String meaning = value.getVmMeaning();
            String description = value.getVmDescription();
            List<Number> idList = (List<Number>) getIdentifierByCadsrId(caDSRId);
            if (!idList.isEmpty()) { 
                Long newId = idList.get(0).longValue();
                List<Number> nameList = getIdentifierByName(name);
                if (!nameList.isEmpty()) {
                    Long oldId = nameList.get(0).longValue();
                    if (!newId.equals(oldId)) {
                        deleteById(oldId);
                        plannedMarkerService.updateStatusOldIDByPMSynID(oldId,
                                newId,
                                ActiveInactivePendingCode.ACTIVE.getName());
                    }
                }

                updateValues(caDSRId, name, meaning, description,
                        ActiveInactivePendingCode.ACTIVE.getName());

            } else {
                // for checking if the name is in pending status or not.

                List<Number> nameList = getIdentifierByName(name);
                if (nameList.isEmpty()) {
                    insertValues(caDSRId, name, meaning, description,
                            ActiveInactivePendingCode.ACTIVE.getName());
                } else {
                    updateValueByName(caDSRId, name, meaning, description,
                            ActiveInactivePendingCode.ACTIVE.getName());
                    plannedMarkerService.updateStatusByPMSynID(nameList.get(0)
                            .longValue(), ActiveInactivePendingCode.ACTIVE
                            .getName());

                }
            }
        }
    }
    /**
     * deletes PlannedMarkerSyncWithCaDSRDTO values based on identifier.
     * @param identifier identifier
     */
    public void deleteById(Long identifier) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("Delete from planned_marker_sync_cadsr where identifier=:identifier");
        query.setParameter("identifier", identifier);
        query.executeUpdate();
    }

    private void changeStatusCodeValue(List<CaDSRDTO> valuesList)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query1 = session
                .createSQLQuery("Select cadsrid from planned_marker_sync_cadsr "
                        + " where cadsrid is not null");
        List<Number> queryList1 = query1.list();
        Long idValue = 0L;
        List<Long> caDSRValues = new ArrayList<Long>();
        for (CaDSRDTO value : valuesList) {
            caDSRValues.add(value.getPublicId());
        } 
        for (Number oArr : queryList1) {
                idValue = oArr.longValue();
                if (!caDSRValues.contains(idValue)) {
                    updateStatusCode(idValue,
                            ActiveInactivePendingCode.INACTIVE.getName());
                    List<Number> identifier = getIdentifierByCadsrId(idValue);
                    plannedMarkerService.updateStatusByPMSynID(identifier
                            .get(0).longValue(),
                            ActiveInactivePendingCode.DELECTED_IN_CADSR
                                    .getName());
                }
        }
    }

    /**
     * Updated the values
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     */

    public void updateValues(Long caDSRId, String name, String meaning,
            String description, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set name =:name, meaning =:caDSRmeaning,"
                        + " description =:caDSRdescription, status_code =:caDSRstatusCode"
                        + "  where cadsrid =:cadsrId");
        query.setParameter(NAME, name);
        query.setParameter("caDSRmeaning", meaning);
        query.setParameter("caDSRdescription", description);
        query.setParameter(CADSRID, caDSRId);
        query.setParameter("caDSRstatusCode", statusCode);
        query.executeUpdate();
    }

    /**
     * Updated the values
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     */
    public void updateValueByName(Long caDSRId, String name, String meaning,
            String description, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set meaning =:meaning,"
                        + " description =:description, status_code =:caDSRstatusCode, cadsrid =:cadsrId"
                        + "  where name =:name");
        query.setParameter(NAME, name);
        query.setParameter("meaning", meaning);
        query.setParameter("description", description);
        query.setParameter(CADSRID, caDSRId);
        query.setParameter("caDSRstatusCode", statusCode);
        query.executeUpdate();
    }

    /**
     * 
     * @param caDSRId
     *            caDSRID
     * @param statusCode
     *            statusCode
     */
    public void updateStatusCode(Long caDSRId, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set status_code = :statusCode"
                        + "  where cadsrid = :cadsrId");
        query.setParameter("cadsrId", caDSRId);
        query.setParameter("statusCode", statusCode);
        query.executeUpdate();
    }

    /**
     * 
     * @param caDSRId
     *            caDSRId
     * @param name
     *            name
     * @param meaning
     *            meaning
     * @param description
     *            description
     * @param statusCode
     *            statusCode
     */
    public void insertValues(Long caDSRId, String name, String meaning,
            String description, String statusCode) {
        Session session = PaHibernateUtil.getCurrentSession();
        if (caDSRId != null) {
            SQLQuery query = session
                    .createSQLQuery("insert into planned_marker_sync_cadsr"
                            + " (NAME, MEANING, DESCRIPTION, CADSRID, STATUS_CODE)"
                            + " values (:name, :meaning, :description, :cadsrId, :statusCode)");
            query.setParameter(NAME, name);
            query.setParameter("meaning", meaning);
            query.setParameter("description", description);
            query.setParameter(CADSRID, caDSRId);
            query.setParameter("statusCode", statusCode);
            query.executeUpdate();
        } else {
            SQLQuery query = session
                    .createSQLQuery("insert into planned_marker_sync_cadsr"
                            + " (NAME, MEANING, DESCRIPTION, CADSRID, STATUS_CODE)"
                            + " values (:name, :meaning, :description, null , :statusCode)");
            query.setParameter(NAME, name);
            query.setParameter("meaning", meaning);
            query.setParameter("description", description);
            query.setParameter("statusCode", statusCode);
            query.executeUpdate();
        }
    }

    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values.
     * 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    public List<PlannedMarkerSyncWithCaDSRDTO> getValuesById(Long id)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarkerSyncWithCaDSR as pm where pm.id=:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        List<PlannedMarkerSyncWithCaDSR> values = query.list();
        return (List<PlannedMarkerSyncWithCaDSRDTO>) convertFromDomainToDTOs(values);
    }

    /**
     * Gets the List of all PlannedMarkerSyncWithCaDSRDTO values.
     * 
     * @return the list of all PlannedMarkerSyncWithCaDSRDTO values
     * @throws PAException
     *             on error.
     * @param name
     *            name
     */
    public List<PlannedMarkerSyncWithCaDSRDTO> getValuesByName(String name)
            throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        String hql = "from PlannedMarkerSyncWithCaDSR as pm where pm.name=:name";
        Query query = session.createQuery(hql);
        query.setParameter(NAME, name);
        List<PlannedMarkerSyncWithCaDSR> values = query.list();
        return (List<PlannedMarkerSyncWithCaDSRDTO>) convertFromDomainToDTOs(values);
    }

    /**
     * Gets the List of all Integer values.
     * 
     * @return the list of all Integer values
     * @throws PAException
     *             on error.
     * @param id
     *            id
     */
    public List<Number> getIdentifierByCadsrId(Long id) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_sync_cadsr where cadsrid=:id");
        query.setParameter("id", id);
        return (List<Number>) query.list();
    }

    /**
     * Gets the List of all name values.
     * 
     * @return the list of all name values
     * @throws PAException
     *             on error.
     * @param name
     *            name
     */
    public List<Number> getPendingIdentifierByCadsrName(String name)
            throws PAException {
        List<Number> values = getIdentifierByName(name);
        if (values.isEmpty()) {
            insertValues(null, name, name, null,
                    ActiveInactivePendingCode.PENDING.getName());
            Long maxValue = maxIdentifierValue();
            values.add(maxValue.intValue());
        }
        return values;
    }

    private List<Number> getIdentifierByName(String name) throws PAException {
        Session session = PaHibernateUtil.getCurrentSession();
        session.flush();
        SQLQuery query = session
                .createSQLQuery("select identifier from planned_marker_sync_cadsr where name=:name");
        query.setParameter(NAME, name);
        return (List<Number>) query.list();
    }

    /**
     * Updated the values
     * 
     * @param name
     *            name
     * @param id
     *            id
     * 
     */
    public void updateValueById(String name, Long id) {
        Session session = PaHibernateUtil.getCurrentSession();
        SQLQuery query = session
                .createSQLQuery("update planned_marker_sync_cadsr set meaning =:name,"
                        + " name =:name where identifier =:id");
        query.setParameter(NAME, name);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    /**
     * @return plannedMarkerService
     * 
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * @param plannedMarkerService
     *            plannedMarkerService
     * 
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

}
