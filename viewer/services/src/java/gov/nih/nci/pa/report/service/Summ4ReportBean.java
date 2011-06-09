/*
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

package gov.nih.nci.pa.report.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractStandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
/**
* @author Max Shestopalov
*/
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
public class Summ4ReportBean extends AbstractStandardReportBean<Summ4RepCriteriaDto, Summ4RepResultDto>
        implements Summ4RepLocal {

    @EJB private StudyProtocolServiceLocal studyProtocolService = null;
    @EJB private ProtocolQueryServiceLocal protocolQueryService = null;
    private static final int SPN_IDX = 0;
    private static final int PID_IDX = 1;
    private static final int PI_IDX = 2;
    private static final int PC_IDX = 3;
    private static final int OD_IDX = 4;
    private static final int CD_IDX = 5;
    private static final int PHS_IDX = 6;
    private static final int TYP_IDX = 7;
    private static final int TTL_IDX = 8;
    private static final int TRG_IDX = 9;
    private static final int AC12_IDX = 10;
    private static final int ACTD_IDX = 11;
    private static final int SC_IDX = 12;
    private static final int SSC_IDX = 13;
    private static final int TRIAL_IDX = 14;
    private static final int NCI_ID_IDX = 15;
    private static final int LEAD_ORG_IDX = 16;
    private static final int NCT_IDX = 17;
    private static final int CTEP_IDX = 18;
    private static final int HCFORG_IDX = 19;
    private static final int ROORG_IDX = 20;
    private static final String SP_PROP_TRIAL_CLAUSE
        = "WHEN sp.proprietary_trial_indicator = FALSE AND ss.functional_code = '";


    /** Db returned value for Epidemiologic/Other/Outcome. */
    public static final String EPIDEM_OUTCOME = "Epidemiologic/Other/Outcome";
    /** Db returned value for Agent/Device. */
    public static final String AGENT_DEVICE = "Agent/Device";
    /** Db returned value for Other Intervention. */
    public static final String OTHER_INTERVENTION = "Other Intervention";
    /** Db returned value for Ancillary/Correlative. */
    public static final String ANCILLARY_CORRELATIVE = "Ancillary/Correlative";

    /**
     * @return the studyProtocolService
     */
    public StudyProtocolServiceLocal getStudyProtocolService() {
        return studyProtocolService;
    }
    /**
     * @param studyProtocolService the studyProtocolService to set
     */
    public void setStudyProtocolService(StudyProtocolServiceLocal studyProtocolService) {
        this.studyProtocolService = studyProtocolService;
    }

    /**
     * Query for pulling summ4 data.
     * @param criteria dto.
     * @return results.
     */
    @SuppressWarnings("PMD.ExcessiveMethodLength")
    protected StringBuffer generateSqlQuery(Summ4RepCriteriaDto criteria) {
        StringBuffer sql = new StringBuffer(
        "select distinct "
        + "trial_list.Sponsor, "
        + "trial_list.Proto_Id, "
        + "trial_list.Pi, "
        + "trial_list.program_code, "
        + "trial_list.open_date, "
        + "trial_list.closed_date, "
        + "trial_list.phase, "
        + "trial_list.type, "
        + "trial_list.tittle, "
        + "trial_list.target, "
        + "(select count(sub1.patient_identifier) from study_subject sub1 "
        + "inner join performed_activity AS perAct ON perAct.study_subject_identifier = sub1.identifier "
        + "AND perAct.study_protocol_identifier = sub1.study_protocol_identifier "
        + "where sub1.study_site_identifier = trial_list.Study_Site_id "
        + "and sub1.study_protocol_identifier = trial_list.trial_id "
        + "and sub1.status_code <> 'PENDING' ");
        sql.append(dateRangeSql(criteria, "perAct.registration_date"));
        String accrualCenterToDate = ") as accrual_center_12m, "
            + "(select count(sub1.patient_identifier) from study_subject sub1  "
            + "inner join performed_activity AS perAct ON perAct.study_subject_identifier = sub1.identifier  "
            + "AND perAct.study_protocol_identifier = sub1.study_protocol_identifier "
            + "where sub1.study_site_identifier = trial_list.Study_Site_id  "
            + "and sub1.study_protocol_identifier = trial_list.trial_id "
            + "and sub1.status_code <> 'PENDING'  "
            + "and perAct.registration_date <= now() "
            + ") as accrual_center_todate, ";
        sql.append(accrualCenterToDate
        + "trial_list.sort_category, "
        + "trial_list.sort_sub_category, "
        + "trial_list.trial_id, "
        + "trial_list.NciIdentifier, "
        + "(select org_lo.name from study_site ss_lo, research_organization ro_lo, organization org_lo "
        + "where ss_lo.research_organization_identifier = ro_lo.identifier "
        + "AND ro_lo.organization_identifier = org_lo.identifier "
        + "AND ss_lo.functional_code = '" + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName()
        + "' AND ss_lo.study_protocol_identifier = trial_list.trial_id "
        + ") as lead_org_name, "
        + "(select ss_nct.local_sp_indentifier from study_site ss_nct, research_organization ro_nct, "
        + "organization org_nct where ss_nct.research_organization_identifier = ro_nct.identifier "
        + "AND ro_nct.organization_identifier = org_nct.identifier "
        + "AND ss_nct.functional_code = '" + StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getName()
        + "' AND ss_nct.study_protocol_identifier = trial_list.trial_id "
        + "AND org_nct.name = 'ClinicalTrials.gov' "
        + ") as nct_identifier, "
        + "(select ss_ctep.local_sp_indentifier from study_site ss_ctep, research_organization ro_ctep, "
        + "organization org_ctep where ss_ctep.research_organization_identifier = ro_ctep.identifier "
        + "AND ro_ctep.organization_identifier = org_ctep.identifier "
        + "AND ss_ctep.functional_code = '" + StudySiteFunctionalCode.IDENTIFIER_ASSIGNER.getName()
        + "' AND ss_ctep.study_protocol_identifier = trial_list.trial_id "
        + "AND org_ctep.name = 'Cancer Therapy Evaluation Program' "
        + ") as ctep_identifier, "
        + "trial_list.hcf_org_name,  "
        + "trial_list.ro_org_name  "
        + "from  "
        + "(select distinct "
        + "sOi.extension as NciIdentifier, "
        + "sponsor_org.name as Sponsor, "
        + "sp.identifier as trial_id, "
        + "ss.identifier as Study_Site_Id, "
        + "ss.local_sp_indentifier as Proto_Id, "
        + "ss.functional_code as Func_Code, "
        + "CASE WHEN sp.study_protocol_type='InterventionalStudyProtocol' THEN hcp_p.last_name||', '||hcp_p.first_name "
        + "WHEN sp.study_protocol_type='ObservationalStudyProtocol' "
        + "THEN crs_p.last_name||', '||crs_p.first_name END as Pi, "
        + "sp.program_code_text as program_code,  "
        + "CASE  "
        + "WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = '"
        + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName()
        + "' THEN null WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = '"
        + StudySiteFunctionalCode.TREATING_SITE.getName() + "' THEN ss.accrual_date_range_low "
        + SP_PROP_TRIAL_CLAUSE
        + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName() + "' THEN sp.start_date "
        + SP_PROP_TRIAL_CLAUSE
        + StudySiteFunctionalCode.TREATING_SITE.getName() + "' and "
        + "(ssAs.status_code = '" + RecruitmentStatusCode.ACTIVE.getName() + "' or ssAs.status_code = '"
        + RecruitmentStatusCode.ENROLLING_BY_INVITATION.getName() + "')  THEN ssAs.status_date "
        + "END as open_date, "
        + "CASE WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = '"
        + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName() + "' THEN null "
        + "WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = '"
        + StudySiteFunctionalCode.TREATING_SITE.getName() + "' THEN "
        + "ss.accrual_date_range_high "
        + SP_PROP_TRIAL_CLAUSE
        + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName() + "' AND "
        + "ssOv.status_code = '" + StudyStatusCode.CLOSED_TO_ACCRUAL.getName() + "' THEN ssOv.status_date "
        + SP_PROP_TRIAL_CLAUSE
        + StudySiteFunctionalCode.TREATING_SITE.getName() + "' AND "
        + "ssAs.status_code = '" + RecruitmentStatusCode.CLOSED_TO_ACCRUAL.getName() + "' THEN ssAs.status_date "
        + "END as closed_date, "
        + "sp.phase_code as phase, "
        + "sp.primary_purpose_code as type, "
        + "sp.public_tittle as tittle, "
        + "sp.max_target_accrual_num as target, "
        + "CASE WHEN sindide.indlde_type_code = 'IDE' OR sindide.indlde_type_code = 'IND' THEN :AGENT_DEVICE  "
        + "WHEN plnAct.category_code = 'INTERVENTION' THEN :OTHER_INTERVENTION  "
        + "WHEN sp.primary_purpose_code = 'EPIDEMIOLOGIC' OR sp.primary_purpose_code = 'OTHER' OR "
        + "sp.primary_purpose_code = 'OUTCOME' THEN :EPIDEM_OTHER_OUTCOME "
        + "WHEN sp.primary_purpose_code = 'ANCILLARY' OR sp.primary_purpose_code = 'CORRELATIVE' "
        + "THEN :ANCILLARY_CORRELATIVE "
        + "END as sort_category, "
        + "sr.type_code as sort_sub_category, "
        + "hcf_org.name as hcf_org_name, "
        + "ro_org.name as ro_org_name "
        + "from study_protocol sp  "
        + "inner JOIN document_workflow_status AS dws ON sp.identifier = dws.study_protocol_identifier "
        + "inner JOIN study_milestone AS sm ON sp.identifier = sm.study_protocol_identifier  "
        + "inner JOIN study_resourcing AS sr ON sp.identifier = sr.study_protocol_identifier  "
        + "inner JOIN study_otheridentifiers sOi ON sp.identifier = sOi.study_protocol_id "
        + "inner JOIN study_site AS ss ON sp.identifier = ss.study_protocol_identifier "
        + "left JOIN research_organization AS ro ON ss.research_organization_identifier = ro.identifier "
        + "left JOIN organization AS ro_org ON ro.organization_identifier = ro_org.identifier "
        + "left JOIN healthcare_facility AS hcf ON ss.healthcare_facility_identifier = hcf.identifier "
        + "left JOIN organization AS hcf_org ON hcf.organization_identifier = hcf_org.identifier "
        + "left JOIN study_contact AS sc ON sc.study_protocol_identifier = sp.identifier "
        + "left JOIN clinical_research_staff AS crs ON sc.clinical_research_staff_identifier = crs.identifier "
        + "left JOIN person AS crs_p ON crs.person_identifier = crs_p.identifier "
        + "left JOIN healthcare_provider AS hcp ON sc.healthcare_provider_identifier = hcp.identifier "
        + "left JOIN person AS hcp_p ON hcp.person_identifier = hcp_p.identifier "
        + "left JOIN study_site_overall_status AS ssOv ON ssOv.study_site_identifier = ss.identifier "
        + "left join study_site_accrual_status ssAs ON ssAs.study_site_identifier = ss.identifier "
        + "left join organization as sponsor_org ON sponsor_org.identifier = "
        + "CAST(sr.organization_identifier AS INTEGER)  "
        + "left join study_indlde AS sindide ON sp.identifier = sindide.study_protocol_identifier "
        + "left join planned_activity AS plnAct ON sp.identifier = plnAct.study_protocol_identifier "
        + "left join study_site AS ss2 ON ss2.functional_code = '"
        + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName() + "' and "
        + "ss2.identifier = ss.identifier  "
        + "where sp.status_code = '" + StudyStatusCode.ACTIVE.getName() + "'  "
        + "and sm.milestone_code = '" + MilestoneCode.READY_FOR_TSR.getName() + "' "
        + "and dws.status_code in ('" + DocumentWorkflowStatusCode.ABSTRACTED.getName()
        + "',' " + DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_NORESPONSE.getName() + "',"
        + "'" + DocumentWorkflowStatusCode.ABSTRACTION_VERIFIED_RESPONSE.getName()
        + "','" + DocumentWorkflowStatusCode.AMENDMENT_SUBMITTED.getName() + "') "
        + "and sr.summ_4_rept_indicator = TRUE "
        + "and ss.functional_code in ('" + StudySiteFunctionalCode.LEAD_ORGANIZATION.getName() + "', '"
        + StudySiteFunctionalCode.TREATING_SITE.getName() + "') ");
        sql.append(generateOrgClause(criteria));
        sql.append("and sp.start_date >= :LOW "
        + "and sp.pri_compl_date <= :HIGH "
        + ") trial_list  "
        + "where "
        + "trial_list.Pi is not null "
        + "and sort_category is not null "
        + "order by sort_category, sort_sub_category, pi");
        return sql;
    }

    private String generateOrgClause(Summ4RepCriteriaDto criteria) {
        int size = criteria.getOrgNames().size();
        StringBuffer orgNameClause = new StringBuffer("and (");
        for (int i = 0; i < size; i++) {
            final String paramName = ":ORG_NAME" + i;
            orgNameClause.append("(ro_org.name = " + paramName + " or hcf_org.name = " + paramName + ")");
            if (i != size - 1) {
                orgNameClause.append(" or ");
            }
        }
        orgNameClause.append(") ");
        return orgNameClause.toString();
    }

    /**
     * {@inheritDoc}
     */
    public List<Summ4RepResultDto> get(Summ4RepCriteriaDto criteria) throws PAException {
        AbstractStandardCriteriaDto.validate(criteria);
        try {
            Method validate = criteria.getClass().getMethod("validate", Object.class);
            validate.invoke(criteria.getClass(), criteria);
        } catch (InvocationTargetException e) {
            throw new PAException(e.getTargetException().getMessage(), e);
        } catch (Exception e) {
            throw new PAException("Exception in " + this.getClass(), e);
        }
        List<Summ4RepResultDto> rList = null;
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = generateSqlQuery(criteria);
            query = session.createSQLQuery(sql.toString());
            setDateRangeParameters(criteria, query);
            setNameParameters(criteria, query);

            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query
                .list();
            rList = getResultList(queryList);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        return rList;
    }

    private static void setNameParameters(Summ4RepCriteriaDto criteria, SQLQuery query) {
        for (int i = 0; i < criteria.getOrgNames().size(); i++) {
            query.setString("ORG_NAME" + i, StConverter.convertToString(criteria.getOrgNames().get(i)));
        }

        query.setString("EPIDEM_OTHER_OUTCOME", EPIDEM_OUTCOME);
        query.setString("AGENT_DEVICE", AGENT_DEVICE);
        query.setString("OTHER_INTERVENTION", OTHER_INTERVENTION);
        query.setString("ANCILLARY_CORRELATIVE", ANCILLARY_CORRELATIVE);
    }

    private List<Summ4RepResultDto> getResultList(List<Object[]> queryList) throws PAException {
        ArrayList<Summ4RepResultDto> rList = new ArrayList<Summ4RepResultDto>();
        for (Object[] q : queryList) {
            Summ4RepResultDto rdto = new Summ4RepResultDto();
            rdto.setSponsor(StConverter.convertToSt((String) q[SPN_IDX]));
            rdto.setProtoId(StConverter.convertToSt((String) q[PID_IDX]));
            rdto.setPi(StConverter.convertToSt((String) q[PI_IDX]));
            rdto.setProgramCode(StConverter.convertToSt((String) q[PC_IDX]));
            rdto.setOpenDate(TsConverter.convertToTs((Timestamp) q[OD_IDX]));
            rdto.setClosedDate(TsConverter.convertToTs((Timestamp) q[CD_IDX]));
            rdto.setPhase(StConverter.convertToSt((String) q[PHS_IDX]));
            rdto.setType(StConverter.convertToSt((String) q[TYP_IDX]));
            rdto.setTitle(StConverter.convertToSt((String) q[TTL_IDX]));

            rdto.setTarget(IntConverter.convertToInt(convertToInteger(q[TRG_IDX])));
            rdto.setAccrualCenter12m(IntConverter.convertToInt(convertToInteger(q[AC12_IDX])));
            rdto.setAccrualCenterToDate(IntConverter.convertToInt(convertToInteger(q[ACTD_IDX])));
            rdto.setSortCriteria(StConverter.convertToSt((String) q[SC_IDX]));
            rdto.setSubSortCriteria(StConverter.convertToSt((String) q[SSC_IDX]));
            rdto.setNciIdentifier(StConverter.convertToSt((String) q[NCI_ID_IDX]));
            rdto.setLeadOrgName(StConverter.convertToSt((String) q[LEAD_ORG_IDX]));
            rdto.setNctIdentifier(StConverter.convertToSt((String) q[NCT_IDX]));
            rdto.setCtepIdentifier(StConverter.convertToSt((String) q[CTEP_IDX]));

            Ii studyProtocolIi = IiConverter.convertToStudyProtocolIi(((BigInteger) q[TRIAL_IDX]).longValue());
            StudyProtocolDTO spDTO = studyProtocolService
            .getStudyProtocol(studyProtocolIi);
            rdto.setAnatomicSiteCodes(spDTO.getSummary4AnatomicSites());


            // Either HCF Org Name is set or RO Org Name, but not both.
            // if func_code = lead_org then, then ro org name is set,
            // else if func_code = treating site, then hcf org name is set
            String orgMember = (StringUtils.isBlank((String) q[HCFORG_IDX]) ? (String) q[ROORG_IDX]
                    : (String) q[HCFORG_IDX]);
            rdto.setOrgMember(StConverter.convertToSt(orgMember));
            rList.add(rdto);
        }
        return rList;
    }

    private Integer convertToInteger(Object obj) {
        Integer returnVal = null;
        if (obj != null) {
            returnVal = Integer.parseInt(obj.toString());
        }
        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    public List<String> searchPoOrgNames(String partial, int maxLimit) throws PAException,
        TooManyResultsException {

        List<String> returnVal = new ArrayList<String>();
        LimitOffset limit = new LimitOffset(maxLimit, 0);

        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(partial));

        List<OrganizationDTO> orgList =
            PoRegistry.getOrganizationEntityService().search(criteria, limit);
        for (OrganizationDTO item : orgList) {
            returnVal.add(EnOnConverter.convertEnOnToString(item.getName()));
        }

        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> getFamilies(int maxLimit) throws TooManyResultsException {
        FamilyDTO familyDTO = new FamilyDTO();
        familyDTO.setStatusCode(CdConverter.convertStringToCd("ACTIVE"));
        Map<String, String> returnMap = new TreeMap<String, String>();
        for (FamilyDTO item : PoRegistry.getFamilyService().search(familyDTO, new LimitOffset(maxLimit, 0))) {
            String name = EnOnConverter.convertEnOnToString(item.getName());
            returnMap.put(item.getIdentifier().getExtension(), name);
        }
        return returnMap;

    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> getOrganizations(String familyId, int maxLimit) throws TooManyResultsException {
        Map<String, String> returnMap = new TreeMap<String, String>();
        LimitOffset limit = new LimitOffset(maxLimit, 0);

        FamilyDTO familyDTO = PoRegistry.getFamilyService().getFamily(IiConverter.convertToPoFamilyIi(familyId));
        List<OrganizationDTO> list = PoRegistry.getOrganizationEntityService().search(new OrganizationDTO(),
                familyDTO.getName(), limit);
        List<FamilyOrganizationRelationshipDTO> relationshipDTOs = PoRegistry.getFamilyService()
                .getActiveRelationships(IiConverter.convertToLong(familyDTO.getIdentifier()));
        for (OrganizationDTO dto : list) {
            String functionalRelationship = "";
            for (FamilyOrganizationRelationshipDTO relationshipDTO : relationshipDTOs) {
                if (relationshipDTO.getOrgIdentifier().getExtension().equals(dto.getIdentifier().getExtension())) {
                    functionalRelationship = CdConverter.convertCdToString(relationshipDTO.getFunctionalType());
                }
            }
            String orgName = EnOnConverter.convertEnOnToString(dto.getName());
            returnMap
                    .put(orgName, functionalRelationship);
        }
        return returnMap;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }
    /**
     * @return the protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }
}
