/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.service.search;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SubmissionTypeCode;
import gov.nih.nci.pa.enums.UserOrgType;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.fiveamsolutions.nci.commons.search.SearchableUtils;

/**
 * @author Abraham J. Evans-EL
 */
public class StudyProtocolQueryBeanSearchCriteria extends AnnotatedBeanSearchCriteria<StudyProtocol> {
    private final StudyProtocolOptions spo;
    private static final String JOIN_CLAUSE = " left outer join obj.documentWorkflowStatuses as dws  "
        + "left outer join obj.studyMilestones as sms "
        + "left outer join obj.studyOverallStatuses as sos "
        + "left outer join obj.studyOwners as sowner ";

    /**
     * Default constructor.
     * @param o the example
     * @param spo the study protocol search options
     */
    public StudyProtocolQueryBeanSearchCriteria(StudyProtocol o, StudyProtocolOptions spo) {
        super(o);
        this.spo = spo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasOneCriterionSpecified() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query getQuery(String orderByProperty, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty, JOIN_CLAUSE,
                getSession(), new StudyProtocolHelper(getCriteria(), this.spo));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Query getQuery(String orderByProperty, String leftJoinClause, boolean isCountOnly) {
        return SearchableUtils.getQueryBySearchableFields(getCriteria(), isCountOnly, orderByProperty,
                leftJoinClause + JOIN_CLAUSE, getSession(), new StudyProtocolHelper(getCriteria(), this.spo));
    }

    /**
     * Helper that adds synonym checks to the searches.
     */
    private static class StudyProtocolHelper implements SearchableUtils.AfterIterationHelper {
       
        private static final String CITY_PARAM = "city";       
        private static final String STATES_PARAM = "states";        
        private static final String COUNTRY_NAME_PARAM = "countryName";      
        private static final String LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM = "leadOrganizationFunctionalCode";
        private static final String PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM = "participatingSitefunctionalCode";
        private static final String SOS_PARAM = "studyOverallStatusParam";
        private static final String DWS_PARAM = "documentWorkflowStatusParam";
        private static final String REJECTED_DWS_PARAM = "rejectedDocumentWorkflowStatusParam";
        private static final String SMS_PARAM = "studyMilestoneStatusParam";
        private static final String CHECKOUT_PARAM = "checkedOutUserParam";
        private static final String STUDY_OWNER_PARAM = "studyOwnerParam";
        private static final String STUDY_OWNER_TYPE_PARAM = "studyOwnerTypeParam";
        private static final String STUDY_OWNER_DWS_PARAM = "studyOwnerDWSParam";
        private static final String STUDY_OWNER_SITE_PARAM = "studyOwnerSiteFunctionalCodeParam";
        private static final String STUDY_PHASE_CODE_PARAM = "studyPhaseCodeParam";
        private static final String ANATOMIC_SITES_PARAM  = "anatomicSitesParam";
        private static final String PARTICIPATING_SITE_PARAM  = "participatingSiteParam";
        private static final String BIOMARKERS_PARAM  = "biomarkersParam";
        private static final String PDQDISEASES_PARAM  = "pdqdiseaseParam";
        private static final String INTERVENTIONS_PARAM  = "interventions";
        private static final String INTERVENTIONS_ALTERNAMES_PARAM  = "interventionAlternates";
        private static final String INTERVENTIONS_TYPES_PARAM  = "interventionTypes";
        private static final String LEAD_ORG_FUNCTIONAL_CODE_PARAM = "leadOrgFunctionalCode";
        private static final String LEAD_ORG_IDS_PARAM = "leadOrgIds";
        private static final String CTGOV_XML_REQUIRED_INDICATOR = "ctgovXmlRequiredIndicator";
        private final StudyProtocol sp;
        private final StudyProtocolOptions spo;
       

        public StudyProtocolHelper(StudyProtocol sp, StudyProtocolOptions spo) {
            this.sp = sp;
            this.spo = spo;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void afterIteration(Object obj, boolean isCountOnly, StringBuffer whereClause,
                Map<String, Object> params) {

            if (CollectionUtils.isNotEmpty(sp.getStudyOverallStatuses())) {
                String operator = determineOperator(whereClause);
                StudyStatusCode sos = sp.getStudyOverallStatuses().iterator().next().getStatusCode();
                whereClause.append(String.format(" %s sos.statusCode = :%s ", operator, SOS_PARAM));
                whereClause.append(String.format(" and sos.id = (select max(id) from %s.studyOverallStatuses)",
                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(SOS_PARAM, sos);
            }

            if (CollectionUtils.isNotEmpty(sp.getDocumentWorkflowStatuses())) {
                String operator = determineOperator(whereClause);
                Set<DocumentWorkflowStatusCode> statusCodes = new HashSet<DocumentWorkflowStatusCode>();
                for (DocumentWorkflowStatus status : sp.getDocumentWorkflowStatuses()) {
                    statusCodes.add(status.getStatusCode());
                }
                whereClause.append(String.format(" %s dws.statusCode in (:%s) ", operator, DWS_PARAM));
                whereClause.append(String.format(" and dws.id = (select max(id) from %s.documentWorkflowStatuses)",
                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(DWS_PARAM, statusCodes);
            }

            if (CollectionUtils.isNotEmpty(sp.getStudyMilestones())) {
                String operator = determineOperator(whereClause);
                MilestoneCode sms = sp.getStudyMilestones().iterator().next().getMilestoneCode();
                whereClause.append(String.format(" %s sms.milestoneCode = :%s ", operator, SMS_PARAM));
                whereClause.append(String.format(" and sms.id = (select max(id) from %s.studyMilestones)",
                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(SMS_PARAM, sms);
            }

            if (spo.isExcludeRejectedTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s dws.statusCode != :%s", operator, REJECTED_DWS_PARAM));
                whereClause.append(String.format(" and dws.id = (select max(id) from %s.documentWorkflowStatuses)",
                        SearchableUtils.ROOT_OBJ_ALIAS));
                params.put(REJECTED_DWS_PARAM, DocumentWorkflowStatusCode.REJECTED);
            }
            
            handlePhaseCodes(whereClause, params);
            handleSummary4AnatomicSites(whereClause, params);
            handleLeadOrganizationIds(whereClause, params);
            generateLocationWhereClause(whereClause, params);
            handleOtherIdentifiersAndOwnership(whereClause, params);
            handleAdditionalCriteria(whereClause, params);
            
            if(spo.getCtgovXmlRequiredIndicator()!=null){
	            String operator = determineOperator(whereClause);
	            whereClause.append(String.format(" %s %s.ctgovXmlRequiredIndicator = :%s" , operator,  
	            		SearchableUtils.ROOT_OBJ_ALIAS, CTGOV_XML_REQUIRED_INDICATOR));
	            params.put(CTGOV_XML_REQUIRED_INDICATOR, spo.getCtgovXmlRequiredIndicator());
            }
        }
        
        private void handlePhaseCodes(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getPhaseCodes())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s %s.phaseCode  in (:%s) ", operator,
                                                 SearchableUtils.ROOT_OBJ_ALIAS, STUDY_PHASE_CODE_PARAM));
                params.put(STUDY_PHASE_CODE_PARAM, spo.getPhaseCodes());
            }
        }
        
        private void handleSummary4AnatomicSites(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getSummary4AnatomicSites())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s ans.id  in (:%s) ", operator, ANATOMIC_SITES_PARAM));
                params.put(ANATOMIC_SITES_PARAM, spo.getSummary4AnatomicSites());
            }
        }

        private void handleLeadOrganizationIds(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getLeadOrganizationIds())) {
                String operator = determineOperator(whereClause);
                String fmt = " %s leadOrgSite.functionalCode = :%s and leadOrgRo.organization.id  in (:%s) ";
                whereClause.append(String.format(fmt, operator, LEAD_ORG_FUNCTIONAL_CODE_PARAM, LEAD_ORG_IDS_PARAM));
                params.put(LEAD_ORG_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                params.put(LEAD_ORG_IDS_PARAM, spo.getLeadOrganizationIds());
            }
        }
        
        private void generateLocationWhereClause(StringBuffer whereClause, Map<String, Object> params) {
            if (spo.isByLocation()) {
                String operator = determineOperator(whereClause);
                whereClause.append(" " + operator + "(");
                createWhereClauseForLeadOrganization(whereClause);
                whereClause.append(" or ");
                createWhereClauseForParticipatingSite(whereClause);
                whereClause.append(" )");
                setLocationParams(params);
            }
        }  
       

        private void createWhereClauseForLeadOrganization(StringBuffer whereClause) {
            whereClause.append("exists (select sslo.id from StudySite sslo where sslo.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS + ".id and sslo.functionalCode = :"
                    + LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM);

            if (StringUtils.isNotBlank(this.spo.getCountryName())) {
                whereClause.append(" and  sslo.researchOrganization.organization.countryName = :" + COUNTRY_NAME_PARAM);
            }

            if (CollectionUtils.isNotEmpty(this.spo.getStates())) {
                whereClause.append(" and  sslo.researchOrganization.organization.state in (:" + STATES_PARAM + ")");
            }

            if (StringUtils.isNotBlank(this.spo.getCity())) {
                whereClause.append(" and  sslo.researchOrganization.organization.city like :" + CITY_PARAM);
            }

            whereClause.append(" )");
        }

        private void createWhereClauseForParticipatingSite(StringBuffer whereClause) {
            whereClause.append("exists (select sslh.id from StudySite sslh where sslh.studyProtocol.id = "
                    + SearchableUtils.ROOT_OBJ_ALIAS + ".id and sslh.functionalCode = :"
                    + PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM);

            if (StringUtils.isNotBlank(this.spo.getCountryName())) {
                whereClause.append(" and sslh.healthCareFacility.organization.countryName = :" + COUNTRY_NAME_PARAM);
            }

            if (CollectionUtils.isNotEmpty(this.spo.getStates())) {
                whereClause.append(" and sslh.healthCareFacility.organization.state in (:" + STATES_PARAM + ")");
            }

            if (StringUtils.isNotBlank(this.spo.getCity())) {
                whereClause.append(" and sslh.healthCareFacility.organization.city like :" + CITY_PARAM);
            }

            whereClause.append(" )");
        }
        
        private void setLocationParams(Map<String, Object> params) {
            params.put(LEAD_ORGANIZATION_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
            params.put(PARTICIPATING_SITE_FUNCTIONAL_CODE_PARAM, StudySiteFunctionalCode.TREATING_SITE);
            if (StringUtils.isNotBlank(this.spo.getCountryName())) {              
                params.put(COUNTRY_NAME_PARAM, this.spo.getCountryName());
            }

            if (CollectionUtils.isNotEmpty(this.spo.getStates())) {              
                params.put(STATES_PARAM, this.spo.getStates());
            }
            
            if (StringUtils.isNotBlank(this.spo.getCity())) {                
                params.put(CITY_PARAM, "%" + this.spo.getCity() + "%");
            }
        }

        private void handleAdditionalCriteria(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getParticipatingSiteIds())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s exists (select sps2.id from StudySite sps2 "
                        + "left outer join sps2.healthCareFacility as hcf "
                        + "left outer join hcf.organization as site "
                        + "where sps2.studyProtocol.id = %s.id and site.id in (:%s))",
                        operator, SearchableUtils.ROOT_OBJ_ALIAS, PARTICIPATING_SITE_PARAM));
                        params.put(PARTICIPATING_SITE_PARAM, spo.getParticipatingSiteIds());
            }

            if (spo.isSearchOnHoldTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyOnholds where onholdDate is "
                        + "not null and offholdDate is null) > 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            }

            if (spo.getTrialSubmissionType() == SubmissionTypeCode.A) {
                String operator = determineOperator(whereClause);
                whereClause.append(String
                    .format(" %s (%s.submissionNumber > 1 and %s.amendmentDate is not null "
                                    + "and (select count(id) from %s.studyInbox where closeDate is null) = 0)",
                            operator,
                            SearchableUtils.ROOT_OBJ_ALIAS, SearchableUtils.ROOT_OBJ_ALIAS,
                            SearchableUtils.ROOT_OBJ_ALIAS));
            } else if (spo.getTrialSubmissionType() == SubmissionTypeCode.O) {
                String operator = determineOperator(whereClause);
                whereClause.append(String
                    .format(" %s (%s.submissionNumber = 1 and %s.amendmentNumber is null "
                                    + "and %s.amendmentDate is null and (select count(id) from %s.studyInbox where "
                                    + "closeDate is null) = 0)", operator, SearchableUtils.ROOT_OBJ_ALIAS,
                            SearchableUtils.ROOT_OBJ_ALIAS, SearchableUtils.ROOT_OBJ_ALIAS,
                            SearchableUtils.ROOT_OBJ_ALIAS));
            } else if (spo.getTrialSubmissionType() == SubmissionTypeCode.U) {
                String operator = determineOperator(whereClause);
                // A trial is considered update when it has at least one study inbox entry without a close date.
                whereClause.append(String.format(" %s (select count(id) from %s.studyInbox where closeDate is null) "
                        + "> 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            }

            if (spo.isLockedTrials()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyCheckout where "
                        + "userIdentifier = :%s ) > 0", operator, SearchableUtils.ROOT_OBJ_ALIAS, CHECKOUT_PARAM));
                params.put(CHECKOUT_PARAM, spo.getLockedUser());
            }

            if (spo.isInboxProcessing()) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s (select count(id) from %s.studyInbox where closeDate is null) "
                        + "> 0", operator, SearchableUtils.ROOT_OBJ_ALIAS));
            }
            
            if (CollectionUtils.isNotEmpty(spo.getBioMarkers())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s exists (select plm.id from PlannedMarker plm "
                                                         + "where plm.studyProtocol.id = %s.id and plm.id in (:%s))",
                                                 operator,
                                                 SearchableUtils.ROOT_OBJ_ALIAS, BIOMARKERS_PARAM));
                params.put(BIOMARKERS_PARAM, spo.getBioMarkers());
            }
            
            if (CollectionUtils.isNotEmpty(spo.getPdqDiseases())) {
                String operator = determineOperator(whereClause);
                whereClause.append(String
                    .format(" %s exists (select sd.id from StudyDisease sd "
                                    + "where sd.studyProtocol.id = %s.id and sd.disease.id in (:%s))", operator,
                            SearchableUtils.ROOT_OBJ_ALIAS, PDQDISEASES_PARAM));
                params.put(PDQDISEASES_PARAM, spo.getPdqDiseases());
            }
            
            searchByIntervention(whereClause, params);
        }

        private void searchByIntervention(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(spo.getInterventionIds())
                    || CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())
                    || CollectionUtils.isNotEmpty(spo.getInterventionTypes())) {
                StringBuilder sql = createSqlForSearchByIntervention(whereClause, params);
                whereClause.append(sql.toString());
            }
        }

        private StringBuilder createSqlForSearchByIntervention(StringBuffer whereClause, Map<String, Object> params) {
            String operator = determineOperator(whereClause);

            StringBuilder sql = new StringBuilder(" ");

            sql.append(operator);

            sql.append(" exists (select pa.id from PlannedActivity pa");

            if (CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())) {
                sql.append(" join pa.intervention intr left join intr.interventionAlternateNames aln ");
            }

            sql.append(" where pa.studyProtocol.id = " + SearchableUtils.ROOT_OBJ_ALIAS + ".id and (0=1");

            if (CollectionUtils.isNotEmpty(spo.getInterventionTypes())) {
                sql.append(" or pa.intervention.typeCode in (:" + INTERVENTIONS_TYPES_PARAM + ") ");
                params.put(INTERVENTIONS_TYPES_PARAM, spo.getInterventionTypes());
            }

            if (CollectionUtils.isNotEmpty(spo.getInterventionIds())) {
                sql.append(" or pa.intervention.id in (:" + INTERVENTIONS_PARAM + ") ");
                params.put(INTERVENTIONS_PARAM, spo.getInterventionIds());
            }

            if (CollectionUtils.isNotEmpty(spo.getInterventionAlternateNameIds())) {
                sql.append(" or aln.id in (:" + INTERVENTIONS_ALTERNAMES_PARAM + ")");
                params.put(INTERVENTIONS_ALTERNAMES_PARAM, spo.getInterventionAlternateNameIds());
            }

            sql.append("))");
            return sql;
        }

        private void handleOtherIdentifiersAndOwnership(StringBuffer whereClause, Map<String, Object> params) {
            if (CollectionUtils.isNotEmpty(sp.getOtherIdentifiers())) {
                String operator = determineOperator(whereClause);
                Iterator<Ii> ids = sp.getOtherIdentifiers().iterator();
                whereClause.append(String.format("%s ( ", operator));
                while (ids.hasNext()) {
                    Ii id = ids.next();
                    whereClause.append("exists (select oi.extension from obj.otherIdentifiers oi where oi.root = '"
                            + id.getRoot() + "' and upper(oi.extension) like '%" + id.getExtension().toUpperCase()
                            + "%') ");
                    if (ids.hasNext()) {
                        whereClause.append(" or ");
                    } else {
                        whereClause.append(") ");
                    }
                }
            }

            if (spo.isMyTrialsOnly() && spo.getUserId() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format(" %s  ( sowner.id = :%s or :%s in (select id from RegistryUser where "
                        + "affiliatedOrgUserType = :%s and str(affiliatedOrganizationId) in ( select "
                        + "ss.researchOrganization.organization.identifier from  %s.studySites ss where "
                        + "ss.functionalCode = :%s ))) and dws.statusCode  != :%s ", operator, STUDY_OWNER_PARAM,
                        STUDY_OWNER_PARAM, STUDY_OWNER_TYPE_PARAM, SearchableUtils.ROOT_OBJ_ALIAS,
                        STUDY_OWNER_SITE_PARAM, STUDY_OWNER_DWS_PARAM));
                params.put(STUDY_OWNER_PARAM, spo.getUserId());
                params.put(STUDY_OWNER_TYPE_PARAM, UserOrgType.ADMIN);
                params.put(STUDY_OWNER_SITE_PARAM, StudySiteFunctionalCode.LEAD_ORGANIZATION);
                params.put(STUDY_OWNER_DWS_PARAM, DocumentWorkflowStatusCode.REJECTED);
            } else if (!spo.isMyTrialsOnly() && spo.getUserId() != null) {
                String operator = determineOperator(whereClause);
                whereClause.append(String.format("%s (sowner.id = :%s or (sowner.id <> :%s and dws.statusCode != :%s))",
                        operator, STUDY_OWNER_PARAM, STUDY_OWNER_PARAM, STUDY_OWNER_DWS_PARAM));
                params.put(STUDY_OWNER_PARAM, spo.getUserId());
                params.put(STUDY_OWNER_DWS_PARAM, DocumentWorkflowStatusCode.SUBMITTED);
            }
        }

        private static String determineOperator(final StringBuffer whereClause) {
            String operator = "";
            if (StringUtils.isBlank(whereClause.toString())) {
                operator = SearchableUtils.WHERE;
            } else if (!SearchableUtils.WHERE.equals(whereClause.toString())) {
                //handle case when no criterion are provided.
                operator = SearchableUtils.AND;
            }
            return operator;
        }
    }
}