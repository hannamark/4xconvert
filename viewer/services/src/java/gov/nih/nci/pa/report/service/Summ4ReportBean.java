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
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractStandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.Summ4RepCriteriaDto;
import gov.nih.nci.pa.report.dto.result.Summ4RepResultDto;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
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

    private static final Logger LOG = Logger.getLogger(Summ4ReportBean.class);

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
    
    /** Db returned value for Epidemiologic/Other/Outcome. */
    public static final String EPIDEM_OUTCOME = "Epidemiologic/Other/Outcome";
    /** Db returned value for Agent/Device. */
    public static final String AGENT_DEVICE = "Agent/Device";
    /** Db returned value for Other Intervention. */
    public static final String OTHER_INTERVENTION = "Other Intervention";
    /** Db returned value for Ancillary/Correlative. */
    public static final String ANCILLARY_CORRELATIVE = "Ancillary/Correlative";
    
    /** Db returned value for NATIONAL. */
    public static final String NATIONAL = "NATIONAL";
    /** Db returned value for INDUSTRIAL. */
    public static final String INDUSTRIAL = "INDUSTRIAL";
    /** Db returned value for INSTITUTIONAL. */
    public static final String INSTITUTIONAL = "INSTITUTIONAL";
    /** Db returned value for EXTERNALLY_PEER_REVIEWED. */
    public static final String EXTERNALLY_PEER_REVIEWED = "EXTERNALLY_PEER_REVIEWED";
    
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
        sql.append(") as accrual_center_12m, "
        + "(select count(sub1.patient_identifier) from study_subject sub1  "
        + "inner join performed_activity AS perAct ON perAct.study_subject_identifier = sub1.identifier  "
        + "AND perAct.study_protocol_identifier = sub1.study_protocol_identifier "
        + "where sub1.study_site_identifier = trial_list.Study_Site_id  "
        + "and sub1.study_protocol_identifier = trial_list.trial_id "
        + "and sub1.status_code <> 'PENDING'  "
        + "and perAct.registration_date <= now() "
        + ") as accrual_center_todate, "        
        + "trial_list.sort_category, "
        + "trial_list.sort_sub_category  "
        + "from  "
        + "(select distinct "
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
        + "WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = 'LEAD_ORGANIZATION' THEN null  "
        + "WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = 'TREATING_SITE' " 
        + "THEN ss.accrual_date_range_low "
        + "WHEN sp.proprietary_trial_indicator = FALSE AND ss.functional_code = 'LEAD_ORGANIZATION' THEN sp.start_date "
        + "WHEN sp.proprietary_trial_indicator = FALSE AND ss.functional_code = 'TREATING_SITE' and " 
        + "ssAs.status_code = 'RECRUITING' THEN ssAs.status_date "
        + "END as open_date, "
        + "CASE WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = 'LEAD_ORGANIZATION' THEN null "
        + "WHEN sp.proprietary_trial_indicator = TRUE AND ss.functional_code = 'TREATING_SITE' THEN " 
        + "ss.accrual_date_range_high "
        + "WHEN sp.proprietary_trial_indicator = FALSE AND ss.functional_code = 'LEAD_ORGANIZATION' AND " 
        + "ssOv.status_code = 'CLOSED_TO_ACCRUAL' THEN ssOv.status_date "
        + "WHEN sp.proprietary_trial_indicator = FALSE AND ss.functional_code = 'TREATING_SITE' AND " 
        + "ssAs.status_code = 'ACTIVE_NOT_RECRUITING' THEN ssAs.status_date "
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
        + "sr.type_code as sort_sub_category "
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
        + "left join study_site AS ss2 ON ss2.functional_code = 'LEAD_ORGANIZATION' and " 
        + "ss2.identifier = ss.identifier  "
        + "where sp.status_code = 'ACTIVE'  "
        + "and sm.milestone_code = 'QC_COMPLETE' "
        + "and dws.status_code in ('ABSTRACTED','ABSTRACTION_VERIFIED_NORESPONSE'," 
        + "'ABSTRACTION_VERIFIED_RESPONSE','AMENDMENT_SUBMITTED') "
        + "and sr.summ_4_rept_indicator = TRUE "
        + "and ss.functional_code in ('LEAD_ORGANIZATION', 'TREATING_SITE') "
        + "and (ro_org.name = :ORG_NAME or hcf_org.name = :ORG_NAME) "
        + "and sp.start_date >= :LOW "
        + "and sp.pri_compl_date <= :HIGH "
        + ") trial_list  "
        + "where "
        + "trial_list.Pi is not null "
        + "and sort_category is not null "
        + "order by sort_category, sort_sub_category, pi"); 
        return sql;
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
        LOG.debug("Leaving get(Summ4RepCriteriaDto), returning " + rList.size() + " object(s).");
        return rList;
    }

    private static void setNameParameters(Summ4RepCriteriaDto criteria, SQLQuery query) {
        String org = StConverter.convertToString(criteria.getOrgName());
       
        query.setParameter("ORG_NAME", org, Hibernate.STRING);
        query.setParameter("EPIDEM_OTHER_OUTCOME", EPIDEM_OUTCOME, Hibernate.STRING);
        query.setParameter("AGENT_DEVICE", AGENT_DEVICE, Hibernate.STRING);
        query.setParameter("OTHER_INTERVENTION", OTHER_INTERVENTION, Hibernate.STRING);
        query.setParameter("ANCILLARY_CORRELATIVE", ANCILLARY_CORRELATIVE, Hibernate.STRING);
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
     * @throws TooManyResultsException 
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
    
}
