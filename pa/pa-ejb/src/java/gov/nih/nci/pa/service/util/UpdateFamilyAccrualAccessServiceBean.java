package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.enums.AccrualAccessSourceCode;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.fiveamsolutions.nci.commons.util.UsernameHolder;

/**
 * @author Kalpana Guthikonda
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "unchecked", "PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength" })
public class UpdateFamilyAccrualAccessServiceBean implements UpdateFamilyAccrualAccessServiceLocal {

    private static final Logger LOG = Logger.getLogger(UpdateFamilyAccrualAccessServiceBean.class);
    private static final String CTEP_DCP_TRIALID_SQRY = "SELECT DISTINCT sp.identifier FROM study_protocol sp" 
            + " WHERE sp.status_code = 'ACTIVE' AND  (exists (select study_protocol_identifier from rv_ctep_id"
            + " where study_protocol_identifier=sp.identifier and local_sp_indentifier is not null) or exists"
            + " (select study_protocol_identifier from rv_dcp_id where"
            + " study_protocol_identifier=sp.identifier and local_sp_indentifier is not null))";

    @EJB
    private StudySiteAccrualAccessServiceLocal studySiteAccrualAccessSrv;
    @EJB
    private FamilyServiceLocal familyService;
    @EJB
    private DataAccessServiceLocal dataAccessService;
    
    /**
     * {@inheritDoc}
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public void updateFamilyAccrualAccess() throws PAException {
        try {
            LOG.info("Starting UpdateFamilyAccrualAccessServiceBean");
            UsernameHolder.setUser("pauser");
            Session session = PaHibernateUtil.getCurrentSession();
            List<Long> ctepDcpTrialIdsList = new ArrayList<Long>();        
            SQLQuery query = session.createSQLQuery(CTEP_DCP_TRIALID_SQRY);
            List<BigInteger> queryList = query.list();
            for (BigInteger obj : queryList) {
                Long studyProtocolId = obj.longValue();
                ctepDcpTrialIdsList.add(studyProtocolId);
            }
            String familyAccessUsersQuery = "from RegistryUser where familyAccrualSubmitter = 'true'";
            Query queryObject = session.createQuery(familyAccessUsersQuery);
            List<RegistryUser> rUsers = queryObject.list();
            Map<RegistryUser, Set<Long>> userTrials = new HashMap<RegistryUser, Set<Long>>();

            for (RegistryUser ru : rUsers) {
                Set<Long> siteAccrualTrials = familyService.getSiteAccrualTrials(ru.getAffiliatedOrganizationId());
                Map<Long, String> trialsWorkFlowStatus = new HashMap<Long, String>();
                String qry = "select study_protocol_identifier, status_code from rv_dwf_current "
                       + "where study_protocol_identifier in (:spIds)";
                DAQuery qr = new DAQuery();
                qr.setSql(true);
                qr.setText(qry);
                qr.addParameter("spIds", siteAccrualTrials);
                List<Object[]> trialsDWFS = dataAccessService.findByQuery(qr);
                for (Object[] row : trialsDWFS) {
                    Long studyId = ((BigInteger) row[0]).longValue();
                    String status = row[1].toString();
                    trialsWorkFlowStatus.put(studyId, status);
                }
                Set<Long> trialsWithoutCTEPOrDCPId = new HashSet<Long>();
                for (Long trialId : siteAccrualTrials) {
                   if (!ctepDcpTrialIdsList.contains(trialId) && isEligibleForAccrual(trialId, trialsWorkFlowStatus)) {
                       trialsWithoutCTEPOrDCPId.add(trialId);
                   }
                }
                userTrials.put(ru, trialsWithoutCTEPOrDCPId);
                LOG.info("the trial size is: " + trialsWithoutCTEPOrDCPId.size() + " and uname:" + ru.getFullName());
            }
            for (Map.Entry<RegistryUser, Set<Long>> user : userTrials.entrySet()) {
                List<Long> currentTrialAccess = studySiteAccrualAccessSrv.getActiveTrialLevelAccrualAccess(
                        user.getKey());
                Set<Long> idsForAccess = new HashSet<Long>();
                for (Long trialID : user.getValue()) {
                    if (!currentTrialAccess.contains(trialID)) {
                        idsForAccess.add(trialID);
                    } else {
                        studySiteAccrualAccessSrv.synchronizeSiteAccrualAccess(trialID, user.getKey());
                    }
                }
                if (CollectionUtils.isNotEmpty(idsForAccess)) {
                    studySiteAccrualAccessSrv.assignTrialLevelAccrualAccessNoTransaction(user.getKey(),
                            AccrualAccessSourceCode.REG_FAMILY_ADMIN_ROLE, idsForAccess, null, null);
                    LOG.info("the user: " + user.getKey().getFullName() + " and the idsForAccess value: " 
                            + StringUtils.join(idsForAccess, "\t "));
                }
            }
            LOG.info("Ending UpdateFamilyAccrualAccessServiceBean");
        } finally {
            UsernameHolder.setUser(null);
        }
    }

    private boolean isEligibleForAccrual(Long trialId, Map<Long, String> trialsWorkFlowStatus) throws PAException {
        boolean result = false;
        DocumentWorkflowStatusCode code = DocumentWorkflowStatusCode.valueOf(trialsWorkFlowStatus.get(trialId));
        result = code.isEligibleForAccrual();
        return result;
    }

    /**
     * @param studySiteAccrualAccessSrv the studySiteAccrualAccessSrv to set
     */
    public void setStudySiteAccrualAccessSrv(
            StudySiteAccrualAccessServiceLocal studySiteAccrualAccessSrv) {
        this.studySiteAccrualAccessSrv = studySiteAccrualAccessSrv;
    }

    /**
     * @param familyService the familyService to set
     */
    public void setFamilyService(FamilyServiceLocal familyService) {
        this.familyService = familyService;
    }
    
    /**
     * @param dataAccessService service to set (used for testing).
     */
    void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

}
