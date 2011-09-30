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
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.correlation.FamilyOrganizationRelationshipDTO;
import gov.nih.nci.services.family.FamilyDTO;
import gov.nih.nci.services.organization.OrganizationDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 * @author Max Shestopalov
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
public class Summ4ReportBean extends AbstractStandardReportBean<Summ4RepCriteriaDto, Summ4RepResultDto> implements
        Summ4RepLocal {

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
    private static final int ACLO12_IDX = 10;
    private static final int ACTS12_IDX = 11;
    private static final int ACLOTD_IDX = 12;
    private static final int ACTSTD_IDX = 13;
    private static final int SSC_IDX = 14;
    //private static final int TRIAL_IDX = 15;
    private static final int NCI_ID_IDX = 16;
    private static final int LEAD_ORG_IDX = 17;
    private static final int NCT_IDX = 18;
    private static final int CTEP_IDX = 19;
    private static final int AS_IDX = 20;

    /**
     * Query for pulling summ4 data.
     * 
     * @param criteria dto.
     * @return results.
     */
    @SuppressWarnings("PMD")
    protected StringBuffer generateSqlQuery(Summ4RepCriteriaDto criteria) {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DISTINCT t.sponsor, t.proto_id, t.pi, t.program_code, t.open_date,");
        sql.append(" (SELECT MAX(closed_date) FROM");
        sql.append(" (SELECT CASE WHEN ct.proprietary_trial_indicator THEN ct.accrual_date_range_high");
        sql.append(" ELSE ct.status_date END as closed_date FROM");
        sql.append(" (SELECT isp.proprietary_trial_indicator, iss.accrual_date_range_high, ssos.status_date");
        sql.append(" FROM study_protocol isp ");
        sql.append(" INNER JOIN study_site iss ON isp.identifier = iss.study_protocol_identifier");
        sql.append(" LEFT JOIN study_site_overall_status ssos ON ssos.study_site_identifier = iss.identifier ");
        sql.append(" AND ssos.status_code = 'CLOSED_TO_ACCRUAL'");
        sql.append(" WHERE isp.identifier = t.trial_id) AS ct) AS tct), ");
        sql.append("t.phase, t.type, t.official_title, t.target, ");
        sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
        sql.append(" INNER JOIN study_site AS ss1 ON sb.study_site_identifier = ss1.identifier");
        sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
        sql.append(" INNER JOIN healthcare_facility AS hf");
        sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
        sql.append(" INNER JOIN study_site AS ss2");
        sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
        sql.append(" INNER JOIN research_organization AS ro");
        sql.append(" ON ss2.research_organization_identifier = ro.identifier");
        sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
        sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
        sql.append(" AND hf.organization_identifier =  ro.organization_identifier");
        sql.append(" AND pa.registration_date >= :LOW");
        sql.append(" AND pa.registration_date < :HIGH ) as accrual_center_leadorg_12m,");
        sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
        sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
        sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
        sql.append(" INNER JOIN healthcare_facility AS hf");
        sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
        sql.append(" INNER JOIN study_site as ss2");
        sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
        sql.append(" INNER JOIN research_organization AS ro");
        sql.append(" ON ss2.research_organization_identifier = ro.identifier");
        sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
        sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
        sql.append(" AND hf.organization_identifier !=  ro.organization_identifier");
        sql.append(" AND pa.registration_date >= :LOW");
        sql.append(" AND pa.registration_date < :HIGH ) as accrual_center_treatorg_12m,");
        sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
        sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
        sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
        sql.append(" INNER JOIN healthcare_facility as hf");
        sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
        sql.append(" INNER JOIN study_site as ss2");
        sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
        sql.append(" INNER JOIN research_organization as ro");
        sql.append(" ON ss2.research_organization_identifier = ro.identifier");
        sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
        sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
        sql.append(" AND hf.organization_identifier =  ro.organization_identifier");
        sql.append(" AND pa.registration_date <= NOW()) AS accrual_center_leadorg_todate,");
        sql.append("(SELECT COUNT(sb.patient_identifier) FROM study_subject sb");
        sql.append(" INNER JOIN study_site as ss1 ON sb.study_site_identifier = ss1.identifier");
        sql.append(" AND ss1.functional_code = 'TREATING_SITE'");
        sql.append(" INNER JOIN healthcare_facility as hf");
        sql.append(" ON hf.identifier = ss1.healthcare_facility_identifier");
        sql.append(" INNER JOIN study_site as ss2");
        sql.append(" ON ss2.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" AND ss2.functional_code = 'LEAD_ORGANIZATION'");
        sql.append(" INNER JOIN research_organization as ro");
        sql.append(" ON ss2.research_organization_identifier = ro.identifier");
        sql.append(" INNER JOIN performed_activity AS pa ON pa.study_subject_identifier = sb.identifier");
        sql.append(" AND pa.study_protocol_identifier = sb.study_protocol_identifier");
        sql.append(" WHERE sb.study_protocol_identifier = t.trial_id");
        sql.append(" AND hf.organization_identifier !=  ro.organization_identifier");
        sql.append(" AND pa.registration_date <= NOW()) AS accrual_center_treatorg_todate,");
        sql.append("(SELECT DISTINCT type_code FROM study_resourcing");
        sql.append(" WHERE study_protocol_identifier = trial_id ");
        sql.append(" AND summ_4_rept_indicator = TRUE) AS sort_sub_category,");
        sql.append(" t.trial_id, t.nciidentifier,");
        sql.append("(SELECT o.name FROM study_site ss, research_organization ro, organization o");
        sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
        sql.append(" AND ro.organization_identifier = o.identifier");
        sql.append(" AND ss.functional_code = 'LEAD_ORGANIZATION'");
        sql.append(" AND ss.study_protocol_identifier = t.trial_id ) AS lead_org_name,");
        sql.append("(SELECT ss.local_sp_indentifier");
        sql.append(" FROM study_site ss, research_organization ro, organization o");
        sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
        sql.append(" AND ro.organization_identifier = o.identifier");
        sql.append(" AND ss.functional_code = 'IDENTIFIER_ASSIGNER'");
        sql.append(" AND ss.study_protocol_identifier = t.trial_id");
        sql.append(" AND o.name = 'ClinicalTrials.gov' ) AS nct_identifier,");
        sql.append("(SELECT ss.local_sp_indentifier");
        sql.append(" FROM study_site ss, research_organization ro, organization o");
        sql.append(" WHERE ss.research_organization_identifier = ro.identifier");
        sql.append(" AND ro.organization_identifier = o.identifier");
        sql.append(" AND ss.functional_code = 'IDENTIFIER_ASSIGNER'");
        sql.append(" AND ss.study_protocol_identifier = t.trial_id");
        sql.append(" AND o.name = 'Cancer Therapy Evaluation Program' ) AS ctep_identifier, ");
        sql.append("(SELECT a.atomic_site FROM (SELECT s.study_protocol_identifier, ");
        sql.append(" array_to_string(array_agg(a.display_name),', ') as atomic_site ");
        sql.append(" FROM study_anatomic_site s ");
        sql.append(" INNER JOIN anatomic_sites As a ON s.anatomic_sites_identifier = a.identifier ");
        sql.append(" WHERE s.study_protocol_identifier = t.trial_id ");
        sql.append(" GROUP BY s.study_protocol_identifier) AS a) AS atomic_sites ");
        sql.append("FROM");
        sql.append(" (SELECT DISTINCT soi.extension AS nciidentifier, so.name AS sponsor,");
        sql.append(" sp.identifier AS trial_id, ss.identifier AS study_site_id,");
        sql.append(" ss.local_sp_indentifier AS proto_id, ss.functional_code AS func_code,");
        sql.append(" p.last_name||', '||p.first_name AS pi,");
        sql.append(" sp.program_code_text AS program_code, sp.phase_code AS phase,");
        sql.append(" (CASE WHEN sp.proprietary_trial_indicator THEN (SELECT MIN(accrual_date_range_low)");
        sql.append("  FROM study_site WHERE study_protocol_identifier = sp.identifier)");
        sql.append("  ELSE sp.start_date END) AS open_date,");
        sql.append(" sp.primary_purpose_code AS type, sp.official_title AS official_title,");
        sql.append(" sp.min_target_accrual_num AS target FROM study_protocol sp");
        sql.append(" INNER JOIN study_milestone AS sm ON sp.identifier = sm.study_protocol_identifier");
        sql.append(" INNER JOIN study_site AS ss ON sp.identifier = ss.study_protocol_identifier");
        sql.append(" INNER JOIN study_otheridentifiers soi ON sp.identifier = soi.study_protocol_id");
        sql.append(" AND soi.root = :NCI_II_ROOT");
        sql.append(" INNER JOIN study_resourcing AS sr ON sp.identifier = sr.study_protocol_identifier");
        sql.append(" LEFT JOIN organization AS so");
        sql.append(" ON CAST(sr.organization_identifier AS INTEGER) = so.identifier");
        sql.append(" LEFT JOIN research_organization AS ro");
        sql.append(" ON ss.research_organization_identifier = ro.identifier");
        sql.append(" LEFT JOIN organization AS roo ON ro.organization_identifier = roo.identifier");
        sql.append(" LEFT JOIN healthcare_facility AS hf ON ss.healthcare_facility_identifier = hf.identifier");
        sql.append(" LEFT JOIN organization AS hfo ON hf.organization_identifier = hfo.identifier");
        sql.append(" LEFT JOIN study_contact AS sc ON sc.study_protocol_identifier = sp.identifier");
        sql.append(" LEFT JOIN healthcare_provider AS hp ON sc.healthcare_provider_identifier = hp.identifier");
        sql.append(" LEFT JOIN person AS p ON hp.person_identifier = p.identifier");
        sql.append(" WHERE sm.milestone_code = 'SUBMISSION_ACCEPTED'");
        sql.append(" AND ss.functional_code in ('LEAD_ORGANIZATION','TREATING_SITE')");
        sql.append(" AND sp.status_code = 'ACTIVE'");
        sql.append(generateOrgClause(criteria));
        sql.append("WHERE CHAR_LENGTH(TRIM(BOTH ' ' FROM proto_id)) > 0 ");
        sql.append("AND CHAR_LENGTH(TRIM(BOTH ' ' FROM sponsor)) > 0 ");
        sql.append("AND CHAR_LENGTH(TRIM(BOTH ' ' FROM pi)) > 0 ");
        sql.append("ORDER BY sort_sub_category, nciidentifier, pi ");

        return sql;
    }

    private String generateOrgClause(Summ4RepCriteriaDto criteria) {
        int size = criteria.getOrgNames().size();
        StringBuffer sb = new StringBuffer("AND (");
        for (int i = 0; i < size; i++) {
            final String paramName = ":ORG_NAME" + i;
            sb.append("(roo.name = ");
            sb.append(paramName);
            sb.append(" OR hfo.name = ");
            sb.append(paramName);
            sb.append(')');
            if (i != size - 1) {
                sb.append(" OR ");
            }
        }
        sb.append(")) t ");

        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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

            query = session.createSQLQuery(generateSqlQuery(criteria).toString());
            setDateRangeParameters(criteria, query);
            setNameParameters(criteria, query);

            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
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
        query.setString("NCI_II_ROOT", IiConverter.STUDY_PROTOCOL_ROOT);
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
            rdto.setAccrualCenterLeadOrg12m(IntConverter.convertToInt(convertToInteger(q[ACLO12_IDX])));
            rdto.setAccrualCenterTreatOrg12m(IntConverter.convertToInt(convertToInteger(q[ACTS12_IDX])));
            rdto.setAccrualCenterLeadOrgToDate(IntConverter.convertToInt(convertToInteger(q[ACLOTD_IDX])));
            rdto.setAccrualCenterTreatOrgToDate(IntConverter.convertToInt(convertToInteger(q[ACTSTD_IDX])));
            rdto.setSubSortCriteria(StConverter.convertToSt((String) q[SSC_IDX]));
            rdto.setNciIdentifier(StConverter.convertToSt((String) q[NCI_ID_IDX]));
            rdto.setLeadOrgName(StConverter.convertToSt((String) q[LEAD_ORG_IDX]));
            rdto.setNctIdentifier(StConverter.convertToSt((String) q[NCT_IDX]));
            rdto.setCtepIdentifier(StConverter.convertToSt((String) q[CTEP_IDX]));
            rdto.setAnatomicSiteCodes(StConverter.convertToSt((String) q[AS_IDX]));

            rList.add(rdto);
        }
        return rList;
    }

    /**
     * Converts the String into an Integer.
     * 
     * @param obj the String.
     * @return the converted Integer
     */
    protected Integer convertToInteger(Object obj) {
        Integer returnVal = null;
        if (obj != null) {
            returnVal = Integer.parseInt(obj.toString());
        }
        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> searchPoOrgNames(String partial, int maxLimit) throws PAException, TooManyResultsException {

        List<String> returnVal = new ArrayList<String>();
        LimitOffset limit = new LimitOffset(maxLimit, 0);

        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setName(EnOnConverter.convertToEnOn(partial));

        List<OrganizationDTO> orgList = PoRegistry.getOrganizationEntityService().search(criteria, limit);
        for (OrganizationDTO item : orgList) {
            returnVal.add(EnOnConverter.convertEnOnToString(item.getName()));
        }

        return returnVal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
    @Override
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
            returnMap.put(orgName, functionalRelationship);
        }
        return returnMap;
    }

}
