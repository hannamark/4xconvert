/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
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
*/
package gov.nih.nci.pa.report.service;

import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.report.dto.criteria.AbstractStandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.InstitutionCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.StandardCriteriaDto;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialListResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.report.util.ViewerHibernateSessionInterceptor;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

/**
* @author Hugh Reinhart
* @since 4/10/2009
*/
@Stateless
@Interceptors(ViewerHibernateSessionInterceptor.class)
public class TrialListReportBean extends AbstractStandardReportBean<AbstractStandardCriteriaDto, TrialListResultDto>
        implements TrialListLocal {

    /** The value indicating results should not be filtered by organization. */
    public static final String ALL_ORGANIZATIONS_KEY = "1";

    private static final int IDENT_IDX = 0;
    private static final int SUBMISSION_NUM_IDX = 1;
    private static final int SUBMITTER_IDX = 2;
    private static final int SUB_DATE_IDX = 3;
    private static final int DWS_IDX = 4;
    private static final int DWS_DATE_IDX = 5;
    private static final int MS_IDX = 6;
    private static final int MS_DATE_IDX = 7;
    private static final int SP_KEY_IDX = 8;

    /**
     * {@inheritDoc}
     */
    public List<TrialListResultDto> get(AbstractStandardCriteriaDto criteria) throws PAException {
        AbstractStandardCriteriaDto.validate(criteria);
        try {
            Method validate = criteria.getClass().getMethod("validate", Object.class);
            validate.invoke(criteria.getClass(), criteria);
        } catch (InvocationTargetException e) {
            throw new PAException(e.getTargetException().getMessage(), e);
        } catch (Exception e) {
            throw new PAException("Exception in " + this.getClass(), e);
        }
        List<TrialListResultDto> rList = null;
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer(
                "SELECT sp.assigned_identifier, sp.submission_number, cm.organization, sp.date_last_created "
              + "    , dws.status_code, dws.status_date_range_low "
              + "    , sm.milestone_code, sm.milestone_date, sp.identifier "
              + "FROM study_protocol AS sp "
              + "INNER JOIN document_workflow_status AS dws ON (sp.identifier = dws.study_protocol_identifier) "
              + "INNER JOIN study_milestone AS sm ON (sp.identifier = sm.study_protocol_identifier) "
              + "LEFT OUTER JOIN csm_user AS cm ON (sp.user_last_created = cm.login_name) "
              + "WHERE dws.identifier in "
              + "      ( select max(identifier) "
              + "        from document_workflow_status "
              + "        group by study_protocol_identifier ) "
              + "  AND sm.identifier in "
              + "      ( select max(identifier) "
              + "        from study_milestone "
              + "        group by study_protocol_identifier ) ");
            sql.append(dateRangeSql(criteria, "sp.date_last_created"));
            sql.append(ctepSql(criteria));
            sql.append(submissionTypeSql(criteria));
            sql.append(submitterOrgSql(criteria));
            sql.append(statusSql(criteria));
            sql.append("ORDER BY cm.organization, sp.date_last_created, sp.identifier ");
            query = session.createSQLQuery(sql.toString());
            setDateRangeParameters(criteria, query);
            setSubmitterOrgParameter(criteria, query);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            rList = getResultList(queryList);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving get(TrialListCriteriaDto), returning " + rList.size() + " object(s).");
        return rList;
    }

    private static String submissionTypeSql(AbstractStandardCriteriaDto criteria) {
        String result = "";
        if (criteria instanceof SubmissionTypeCriteriaDto) {
            SubmissionTypeCode type = SubmissionTypeCode.valueOf(
                    CdConverter.convertCdToString(((SubmissionTypeCriteriaDto) criteria).getSubmissionType()));
            if (SubmissionTypeCode.AMENDMENT.equals(type)) {
                result = "AND sp.submission_number > 1 ";
            } else if (SubmissionTypeCode.ORIGINAL.equals(type)) {
                result = "AND sp.submission_number = 1 ";
            }
        }
        return result;
    }

    private static boolean filterBySubmitterOrg(AbstractStandardCriteriaDto criteria) {
        if (criteria instanceof InstitutionCriteriaDto) {
            Set<String> orgs = ReportUtil.convertToString(((InstitutionCriteriaDto) criteria).getInstitutions());
            if (!orgs.contains(ALL_ORGANIZATIONS_KEY)) {
                return true;
            }
        }
        return false;
    }

    private static String submitterOrgSql(AbstractStandardCriteriaDto criteria) {
        if (filterBySubmitterOrg(criteria)) {
            return "AND cm.organization IN (:ORGS) ";
        }
        return "";
    }

    private static void setSubmitterOrgParameter(AbstractStandardCriteriaDto criteria, SQLQuery query) {
        if (filterBySubmitterOrg(criteria)) {
            Set<String> orgs = ReportUtil.convertToString(((InstitutionCriteriaDto) criteria).getInstitutions());
            query.setParameterList("ORGS", orgs);
        }
    }

    private static String statusSql(AbstractStandardCriteriaDto criteria) {
        if (criteria instanceof StandardCriteriaDto
                && BlConverter.covertToBool(((StandardCriteriaDto) criteria).getActiveOnly())) {
            return "AND sp.status_code = '" + ActStatusCode.ACTIVE.getName() + "' ";
        } else {
            return "";
        }
    }

    private List<TrialListResultDto> getResultList(List<Object[]> queryList) throws PAException {
        ArrayList<TrialListResultDto> rList = new ArrayList<TrialListResultDto>();
        for (Object[] q : queryList) {
            TrialListResultDto rdto = new TrialListResultDto();
            rdto.setAssignedIdentifier(StConverter.convertToSt((String) q[IDENT_IDX]));
            rdto.setDateLastCreated(TsConverter.convertToTs((Timestamp) q[SUB_DATE_IDX]));
            rdto.setDws(CdConverter.convertStringToCd((String) q[DWS_IDX]));
            rdto.setDwsDate(TsConverter.convertToTs((Timestamp) q[DWS_DATE_IDX]));
            LeadOrgInfo loi = getLeadOrganization((BigInteger) q[SP_KEY_IDX]);
            rdto.setLeadOrg(StConverter.convertToSt(loi.name));
            rdto.setLeadOrgTrialIdentifier(StConverter.convertToSt(loi.localSpIdentifier));
            rdto.setMilestone(CdConverter.convertStringToCd((String) q[MS_IDX]));
            rdto.setMilestoneDate(TsConverter.convertToTs((Timestamp) q[MS_DATE_IDX]));
            rdto.setSubmissionNumber(IntConverter.convertToInt((Integer) q[SUBMISSION_NUM_IDX]));
            rdto.setSubmitterOrg(StConverter.convertToSt((String) q[SUBMITTER_IDX]));
            rList.add(rdto);
        }
        return rList;
    }
}
