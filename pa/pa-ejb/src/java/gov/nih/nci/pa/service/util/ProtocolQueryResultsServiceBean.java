/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2012, Mackson Consulting, LLC.   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;
import gov.nih.nci.pa.iso.convert.BaseStudyProtocolQueryConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author Hugh Reinhart
 * @since Feb 7, 2012
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Interceptors(PaHibernateSessionInterceptor.class)
public class ProtocolQueryResultsServiceBean implements ProtocolQueryResultsServiceLocal {

    @EJB
    private DataAccessServiceLocal dataAccessService;

    @EJB
    private RegistryUserServiceLocal registryUserService;
        

    /**
     * @param dataAccessService service to set (used for testing).
     */
    void setDataAccessService(DataAccessServiceLocal dataAccessService) {
        this.dataAccessService = dataAccessService;
    }

    /**
     * @param registryUserService service to set (used for testing).
     */
    void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    static final String QRY_STRING = "SELECT study_protocol_identifier,official_title"
            + ",proprietary_trial_indicator,record_verification_date,ctgov_xml_required_indicator,updating"
            + ",date_last_created,submission_number,nci_number,nct_number,lead_org_poid,lead_org_name"
            + ",lead_org_sp_identifier,current_dwf_status_code,current_dwf_status_date,current_study_overall_status"
            + ",current_admin_milestone,current_scientific_milestone,current_other_milestone,admin_checkout_identifier"
            + ",admin_checkout_user,scientific_checkout_identifier,scientific_checkout_user,study_pi_first_name"
            + ",study_pi_last_name,user_last_created_login,user_last_created_first,user_last_created_last, " 
            + "dcp_id, ctep_id,amendment_date,date_last_updated"
            + " FROM rv_search_results WHERE study_protocol_identifier IN (:ids)";

    static final String STUDY_ID_QRY_STRING = "select study_protocol.identifier, study_site_owner.user_id "
            + "from study_protocol "
            + "INNER JOIN study_site ON study_protocol.identifier=study_site.study_protocol_identifier "
            + "LEFT JOIN study_site_owner ON study_site_owner.study_site_id = study_site.identifier "
            + "where study_site.functional_code='"
            + StudySiteFunctionalCode.TREATING_SITE.name()
            + "' and "
            + "study_site.healthcare_facility_identifier in (SELECT identifier from healthcare_facility "
            + "where healthcare_facility.organization_identifier="
            + "(select organization.identifier from organization where "
            + "cast (organization.assigned_identifier as bigint)=:orgId))";
    
    static final String OTHER_IDENTIFIERS_QRY_STRING = "select study_protocol_id , extension, " 
            + "identifier_name FROM study_otheridentifiers "
            + "WHERE study_protocol_id IN (:ids)";
    
    private static final int STUDY_PROTOCOL_IDENTIFIER_IDX = 0;
    private static final int OFFICIAL_TITLE_IDX = 1;
    private static final int PROPRIETARY_TRIAL_INDICATOR_IDX = 2;
    private static final int RECORD_VERIFICATION_DATE_IDX = 3;
    private static final int CTGOV_XML_REQUIRED_INDICATOR_IDX = 4;
    private static final int UPDATING_IDX = 5;
    private static final int DATE_LAST_CREATED_IDX = 6;
    private static final int SUBMISSION_NUMBER_IDX = 7;
    private static final int NCI_NUMBER_IDX = 8;
    private static final int NCT_NUMBER_IDX = 9;
    private static final int LEAD_ORG_POID_IDX = 10;
    private static final int LEAD_ORG_NAME_IDX = 11;
    private static final int LEAD_ORG_SP_IDENTIFIER_IDX = 12;
    private static final int CURRENT_DWF_STATUS_CODE_IDX = 13;
    private static final int CURRENT_DWF_STATUS_DATE_IDX = 14;
    private static final int CURRENT_STUDY_OVERALL_STATUS_IDX = 15;
    private static final int CURRENT_ADMIN_MILESTONE_IDX = 16;
    private static final int CURRENT_SCIENTIFIC_MILESTONE_IDX = 17;
    private static final int CURRENT_OTHER_MILESTONE_IDX = 18;
    private static final int ADMIN_CHECKOUT_IDENTIFIER_IDX = 19;
    private static final int ADMIN_CHECKOUT_USER_IDX = 20;
    private static final int SCIENTIFIC_CHECKOUT_IDENTIFIER_IDX = 21;
    private static final int SCIENTIFIC_CHECKOUT_USER_IDX = 22;
    private static final int STUDY_PI_FIRST_NAME_IDX = 23;
    private static final int STUDY_PI_LAST_NAME_IDX = 24;
    private static final int USER_LAST_CREATED_LOGIN_IDX = 25;
    private static final int USER_LAST_CREATED_FIRST_IDX = 26;
    private static final int USER_LAST_CREATED_LAST_IDX = 27;
    private static final int DCP_ID_IDX = 28;
    private static final int CTEP_ID_IDX = 29;
    private static final int AMENDMENT_DATE = 30;
    private static final int DATE_LAST_UPDATED = 31;

    private static final int ACCESS_NO = 0;
    private static final int ACCESS_ADMIN = 1;
    private static final int ACCESS_OWNER = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public List<StudyProtocolQueryDTO> getResults(List<Long> protocols, boolean myTrialsOnly, Long userId)
            throws PAException {
        if (protocols == null) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        RegistryUser user = registryUserService.getUserById(userId);
        Map<Long, Integer> ownerMap = getOwnerMapAndFilterTrials(protocols, myTrialsOnly, userId, user);
        if (ownerMap.size() > PAConstants.MAX_SEARCH_RESULTS) {
            throw new PAException("Results exceed " + PAConstants.MAX_SEARCH_RESULTS
                    + ". Please refine the search criteria.");
        }
        if (ownerMap.isEmpty()) {
            return new ArrayList<StudyProtocolQueryDTO>();
        }
        Map<Long, Boolean> studyIDAndSiteOwnershipMap = getStudiesOnWhichUserHasSite(user);
        List<String> rssOrgs = getRSSOrganizationNames();
        DAQuery query = new DAQuery();
        query.setSql(true);
        query.setText(QRY_STRING);
        query.addParameter("ids", ownerMap.keySet());
        List<Object[]> queryList = dataAccessService.findByQuery(query);
         
        List<StudyProtocolQueryDTO> dtoList = convertResults(queryList, 
                ownerMap, myTrialsOnly, user, studyIDAndSiteOwnershipMap, rssOrgs);
        
        query = new DAQuery();
        query.setSql(true);
        query.setText(OTHER_IDENTIFIERS_QRY_STRING);
        query.addParameter("ids", ownerMap.keySet());       
        List<Object[]> otherIdentifierQueryList = dataAccessService.findByQuery(query);
        
        for (Object[] obj : otherIdentifierQueryList) { 
            Long studyprotocolId = ((BigInteger) obj[0]).longValue();
            for (StudyProtocolQueryDTO dto : dtoList) {                
               if (dto.getStudyProtocolId().equals(studyprotocolId)) {
                   dto.getOtherIdentifiers().add((String) obj[1]);
               }
            }
        }
        
        return dtoList;
    }
    
    /**
     * @return {@link List} list of RSS organization names.
     * @throws PAException PAException
     */
    private List<String> getRSSOrganizationNames() throws PAException {
        String[] rssOrgs = PaRegistry.getLookUpTableService()
                .getPropertyValue("rss.leadOrgs").split(",");
        return Arrays.asList(rssOrgs);
    }
    

    /**
     * @param userId
     * @return IDs of studies on which the user's affiliated organization is a participating site.
     * @throws PAException
     */
    Map<Long, Boolean> getStudiesOnWhichUserHasSite(RegistryUser user)
            throws PAException {
        Map<Long, Boolean> map = new HashMap<Long, Boolean>();        
        if (user != null && user.getAffiliatedOrganizationId() != null) {
            DAQuery query = new DAQuery();
            query.setSql(true);
            query.setText(STUDY_ID_QRY_STRING);
            query.addParameter("orgId", user.getAffiliatedOrganizationId());
            List<Object[]> queryList = dataAccessService.findByQuery(query);
            for (Object[] row : queryList) {
                BigInteger studyId = (BigInteger) row[0];
                BigInteger siteOwnerId = (BigInteger) row[1];
                Boolean isOwner = siteOwnerId == null
                        || (siteOwnerId != null && siteOwnerId.longValue() == user.getId()
                                .longValue())
                        || Boolean.TRUE.equals(map.get(studyId.longValue()));
                map.put(studyId.longValue(), isOwner);
            }
        }
        return map;
    }

    private Map<Long, Integer> getOwnerMapAndFilterTrials(
            List<Long> protocols, boolean myTrialsOnly, Long userId,
            RegistryUser user) throws PAException {
        Set<Long> ownedStudies = getOwnedStudies(userId);
        Map<Long, Integer> ownerMap = new HashMap<Long, Integer>();
        final boolean isAdmin = isAdmin(user);
        for (Long spID : protocols) {
            Integer access = ACCESS_NO;
            if (ownedStudies.contains(spID)) {
                access = ACCESS_OWNER;
            } else {                
                if (isAdmin) {
                    access = ACCESS_ADMIN;
                }
            }
            if (access != ACCESS_NO || !myTrialsOnly) {
                ownerMap.put(spID, access);
            }
        }
        return ownerMap;
    }

    /**
     * @param user
     * @return
     */
    private boolean isAdmin(RegistryUser user) {
        return user == null ? false : UserOrgType.ADMIN.equals(user.getAffiliatedOrgUserType());
    }

    private Set<Long> getOwnedStudies(Long userId) {
        Set<Long> ownedStudies = new HashSet<Long>();
        if (userId != null) {

            DAQuery query = new DAQuery();
            query.setSql(true);
            query.setText("SELECT study_id FROM study_owner WHERE user_id = :userId");
            query.addParameter("userId", userId);
            List<BigInteger> queryList = dataAccessService.findByQuery(query);
            for (BigInteger studyId : queryList) {
                ownedStudies.add(studyId.longValue());
            }
        }
        return ownedStudies;
    }

    /**
     * @param qryList
     * @param ownerMap
     * @param myTrialsOnly
     * @param userId
     * @param rssOrgs 
     * @param studyIDs IDs of studies on which the user's affiliated organization is a participating site.
     * @return
     * @throws PAException
     */
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveParameterList" })
    private List<StudyProtocolQueryDTO> convertResults(List<Object[]> qryList,
            Map<Long, Integer> ownerMap, boolean myTrialsOnly, RegistryUser user,
            Map<Long, Boolean> studyIDAndSiteOwnershipMap, List<String> rssOrgs) throws PAException {
        String affiliatedOrg = getAffiliatedOrg(user);
        List<StudyProtocolQueryDTO> result = new ArrayList<StudyProtocolQueryDTO>();
        for (Object[] row : qryList) {
            StudyProtocolQueryDTO dto = convertRow(row, rssOrgs);
            int access = ownerMap.get(dto.getStudyProtocolId());
            switch (access) {
            case ACCESS_OWNER:
                dto.setSearcherTrialOwner(true);
                result.add(dto);
                break;
            case ACCESS_ADMIN:
                String poid = (String) row[LEAD_ORG_POID_IDX];
                dto.setSearcherTrialOwner(StringUtils.equals(affiliatedOrg, poid));
                if (!myTrialsOnly || dto.isSearcherTrialOwner()) {
                    result.add(dto);
                }
                break;
            default:
                dto.setSearcherTrialOwner(false);
                result.add(dto);
            }            
            if (studyIDAndSiteOwnershipMap.containsKey(dto.getStudyProtocolId())) {
                dto.setCurrentUserHasSite(true);
                dto.setCurrentUserIsSiteOwner(studyIDAndSiteOwnershipMap
                        .get(dto.getStudyProtocolId()));
            } 
        }
        return result;
    }

    private String getAffiliatedOrg(RegistryUser user) throws PAException {
        String affiliatedOrg = "";        
        if (user != null) {
            affiliatedOrg = user.getAffiliateOrg();
        }
        return affiliatedOrg;
    }

    private StudyProtocolQueryDTO convertRow(Object[] row, List<String> rssOrgs) {
        StudyProtocolQueryDTO dto = new StudyProtocolQueryDTO();
        loadGeneralData(dto, row);
        loadSubmissionType(dto, row);
        loadStatusData(dto, row);
        loadCheckoutData(dto, row);
        setFlags(dto, row, rssOrgs);
        return dto;
    }

    @SuppressWarnings("PMD.CyclomaticComplexity")
    void setFlags(StudyProtocolQueryDTO dto, Object[] row,
            List<String> rssOrgs) {
        if (dto.isProprietaryTrial()
                && !rssOrgs.contains(row[LEAD_ORG_NAME_IDX])
                && !(dto.getStudyStatusCode() != null && !dto
                        .getStudyStatusCode()
                        .isEligibleForSiteSelfRegistration())
                && dto.getDocumentWorkflowStatusCode() != null
                && dto.getDocumentWorkflowStatusCode().isAcceptedOrAbove()) {
            dto.setSiteSelfRegistrable(true);
        }
    }

    private void loadGeneralData(StudyProtocolQueryDTO dto, Object[] row) {
        dto.setOfficialTitle((String) row[OFFICIAL_TITLE_IDX]);
        dto.setCtgovXmlRequiredIndicator((Boolean) row[CTGOV_XML_REQUIRED_INDICATOR_IDX]);
        dto.getLastCreated().setDateLastCreated((Date) row[DATE_LAST_CREATED_IDX]);
        User user = new User();
        user.setFirstName((String) row[USER_LAST_CREATED_FIRST_IDX]);
        user.setLastName((String) row[USER_LAST_CREATED_LAST_IDX]);
        user.setLoginName((String) row[USER_LAST_CREATED_LOGIN_IDX]);
        dto.getLastCreated().setUserLastCreated(user.getLoginName());
        dto.getLastCreated().setUserLastDisplayName(CsmUserUtil.getDisplayUsername(user));
        dto.setLeadOrganizationName((String) row[LEAD_ORG_NAME_IDX]);
        dto.setLocalStudyProtocolIdentifier((String) row[LEAD_ORG_SP_IDENTIFIER_IDX]);
        dto.setNciIdentifier((String) row[NCI_NUMBER_IDX]);
        dto.setNctIdentifier((String) row[NCT_NUMBER_IDX]);
        String piFirst = (String) row[STUDY_PI_FIRST_NAME_IDX];
        String piLast = (String) row[STUDY_PI_LAST_NAME_IDX];
        dto.setPiFullName(piLast + (StringUtils.isEmpty(piFirst) ? "" : ", " + piFirst));
        dto.setProprietaryTrial(BooleanUtils.toBoolean((Boolean) row[PROPRIETARY_TRIAL_INDICATOR_IDX]));
        dto.setRecordVerificationDate((Date) row[RECORD_VERIFICATION_DATE_IDX]);
        dto.setStudyProtocolId(((BigInteger) row[STUDY_PROTOCOL_IDENTIFIER_IDX]).longValue());
        dto.setDcpId((String) row[DCP_ID_IDX]);
        dto.setCtepId((String) row[CTEP_ID_IDX]);
        dto.setAmendmentDate((Date) row[AMENDMENT_DATE]); 
        dto.setUpdatedDate((Date) row[DATE_LAST_UPDATED]);
    }

    private void loadSubmissionType(StudyProtocolQueryDTO dto, Object[] row) {
        Boolean updating = (Boolean) row[UPDATING_IDX];
        if (!(updating == null)) {
            dto.setSubmissionTypeCode(SubmissionTypeCode.U);
        } else {
            Integer submissionNum = (Integer) row[SUBMISSION_NUMBER_IDX];
            if (submissionNum != null) {
                dto.setSubmissionTypeCode(submissionNum == 1 ? SubmissionTypeCode.O : SubmissionTypeCode.A);
            }
        }
    }

    private void loadStatusData(StudyProtocolQueryDTO dto, Object[] row) {
        String tstr = (String) row[CURRENT_DWF_STATUS_CODE_IDX];
        dto.setDocumentWorkflowStatusCode(getEnumFromString(DocumentWorkflowStatusCode.class, tstr));
        dto.setViewTSR(!BaseStudyProtocolQueryConverter.NON_TSR_DWF.contains(dto.getDocumentWorkflowStatusCode()));
        dto.setDocumentWorkflowStatusDate((Date) row[CURRENT_DWF_STATUS_DATE_IDX]);
        tstr = (String) row[CURRENT_ADMIN_MILESTONE_IDX];
        dto.getMilestones().getAdminMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        tstr = (String) row[CURRENT_SCIENTIFIC_MILESTONE_IDX];
        dto.getMilestones().getScientificMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        tstr = (String) row[CURRENT_OTHER_MILESTONE_IDX];
        dto.getMilestones().getStudyMilestone().setMilestone(getEnumFromString(MilestoneCode.class, tstr));
        tstr = (String) row[CURRENT_STUDY_OVERALL_STATUS_IDX];
        dto.setStudyStatusCode(tstr == null ? null : StudyStatusCode.valueOf(tstr));
    }

    private static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (string != null) {
            return Enum.valueOf(c, string);
        }
        return null;
    }

    private void loadCheckoutData(StudyProtocolQueryDTO dto, Object[] row) {
        Integer adminCid = (Integer) row[ADMIN_CHECKOUT_IDENTIFIER_IDX];
        dto.getAdminCheckout().setCheckoutId(adminCid == null ? null : adminCid.longValue());
        dto.getAdminCheckout().setCheckoutBy((String) row[ADMIN_CHECKOUT_USER_IDX]);
        Integer scientificCid = (Integer) row[SCIENTIFIC_CHECKOUT_IDENTIFIER_IDX];
        dto.getScientificCheckout().setCheckoutId(scientificCid == null ? null : scientificCid.longValue());
        dto.getScientificCheckout().setCheckoutBy((String) row[SCIENTIFIC_CHECKOUT_USER_IDX]);
    }
}
