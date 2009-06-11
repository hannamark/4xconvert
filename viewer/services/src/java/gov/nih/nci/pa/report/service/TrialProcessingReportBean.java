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

import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.AssignedIdentifierCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialProcessingHeaderResultDto;
import gov.nih.nci.pa.report.dto.result.TrialProcessingResultDto;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.report.util.ViewerHibernateSessionInterceptor;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

/**
* @author Hugh Reinhart
* @since 06/04/2009
 */
@Stateless
@Interceptors(ViewerHibernateSessionInterceptor.class)
public class TrialProcessingReportBean
        extends AbstractReportBean<AssignedIdentifierCriteriaDto, TrialProcessingResultDto>
        implements TrialProcessingLocal {

    private static final int HDR_FIRST_NAME = 1;
    private static final int HDR_LAST_NAME = 2;
    private static final int HDR_ASSIGNED_IDENTIFIER = 3;
    private static final int HDR_OFFICIAL_TITLE = 4;
    private static final int HDR_STATUS_CODE = 5;
    private static final int HDR_SP_ID = 6;

    private static final String[] REPORTING_MILESTONES = {
        MilestoneCode.SUBMISSION_RECEIVED.getName(),
        MilestoneCode.SUBMISSION_ACCEPTED.getName(),
        MilestoneCode.SUBMISSION_REJECTED.getName(),
        MilestoneCode.READY_FOR_QC.getName(),
        MilestoneCode.QC_START.getName(),
        MilestoneCode.QC_COMPLETE.getName(),
        MilestoneCode.TRIAL_SUMMARY_SENT.getName(),
        MilestoneCode.TRIAL_SUMMARY_FEEDBACK.getName(),
        MilestoneCode.INITIAL_ABSTRACTION_VERIFY.getName(),
        MilestoneCode.ONGOING_ABSTRACTION_VERIFICATION.getName()
    };

    /**
     * {@inheritDoc}
     */
    public TrialProcessingHeaderResultDto getHeader(AssignedIdentifierCriteriaDto criteria) throws PAException {
        AssignedIdentifierCriteriaDto.validate(criteria);
        TrialProcessingHeaderResultDto result = null;
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer(
                  "SELECT cm.organization, cm.first_name, cm.last_name, sp.assigned_identifier "
                + "       , sp.official_title, dws.status_code, sp.identifier "
                + "FROM study_protocol AS sp "
                + "INNER JOIN document_workflow_status AS dws ON (sp.identifier = dws.study_protocol_identifier) "
                + "LEFT OUTER JOIN csm_user AS cm ON (sp.user_last_created = cm.login_name) "
                + "WHERE dws.identifier in "
                + "      ( SELECT MAX(identifier) "
                + "        FROM document_workflow_status "
                + "        GROUP BY study_protocol_identifier ) "
                + "  AND sp.identifier = "
                + "     ( SELECT MAX(identifier) "
                + "       FROM study_protocol "
                + "       WHERE assigned_identifier = :ASSIGNED_IDENTIFIER ) ");
            query = session.createSQLQuery(sql.toString());
            setStParameter(query, "ASSIGNED_IDENTIFIER", criteria.getAssignedIdentifier());
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            if (queryList.size() < 1) {
                throw new PAException("ERROR:  Trial not found.");
            }
            for (Object[] sr : queryList) {
                result = new TrialProcessingHeaderResultDto();
                result.setAssignedIdentifier(StConverter.convertToSt((String) sr[HDR_ASSIGNED_IDENTIFIER]));
                result.setOfficialTitle(StConverter.convertToSt((String) sr[HDR_OFFICIAL_TITLE]));
                result.setLeadOrganization(
                        StConverter.convertToSt(getLeadOrganization((BigInteger) sr[HDR_SP_ID]).name));
                result.setStatusCode(CdConverter.convertStringToCd((String) sr[HDR_STATUS_CODE]));
                String name = (String) sr[HDR_FIRST_NAME];
                if (name == null || name.length() == 0) {
                    result.setUserLastCreated(StConverter.convertToSt((String) sr[HDR_LAST_NAME]));
                } else {
                    result.setUserLastCreated(StConverter.convertToSt(name + " " + (String) sr[HDR_LAST_NAME]));
                }
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving getHeader(St).");
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public List<TrialProcessingResultDto> get(AssignedIdentifierCriteriaDto criteria) throws PAException {
        AssignedIdentifierCriteriaDto.validate(criteria);
        List<TrialProcessingResultDto> rList = new ArrayList<TrialProcessingResultDto>();
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            String sql = "SELECT sp.submission_number, sm.milestone_code, sm.milestone_date "
                       + "FROM study_milestone AS sm "
                       + "  INNER JOIN study_protocol AS sp ON (sm.study_protocol_identifier = sp.identifier) "
                       + "WHERE sp.assigned_identifier = :ASSIGNED_IDENTIFIER "
                       + "  AND sm.milestone_code IN (:REPORTING_MILESTONES) "
                       + "ORDER BY sp.submission_number, sm.identifier ";
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql);
            setStParameter(query, "ASSIGNED_IDENTIFIER", criteria.getAssignedIdentifier());
            query.setParameterList("REPORTING_MILESTONES", REPORTING_MILESTONES);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            Integer submission = null;
            Timestamp submissionStart = null;
            Timestamp previousDate = null;
            for (Object[] sr : queryList) {
                TrialProcessingResultDto rDto = new TrialProcessingResultDto();
                if (submission == null || !submission.equals(sr[0])) {
                    submission = (Integer) sr[0];
                    submissionStart = (Timestamp) sr[2];
                    previousDate = submissionStart;
                }
                rDto.setCumulativeDays(intervalToSt(submissionStart, (Timestamp) sr[2]));
                rDto.setMilestoneCode(CdConverter.convertStringToCd((String) sr[1]));
                rDto.setMilestoneDays(intervalToSt(previousDate, (Timestamp) sr[2]));
                rDto.setSubmissionNumber(IntConverter.convertToInt(submission));
                rList.add(rDto);
                previousDate = (Timestamp) sr[2];
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving get(St), returning " + rList.size() + " object(s).");
        return rList;
    }

    /**
     * Method does report specific formating of time intervals as strings.
     * @param t1 early date
     * @param t2 late date
     * @return iso St
     */
    static St intervalToSt(Timestamp t1, Timestamp t2) {
        if (ReportUtil.invalidIntervalValues(t1, t2)) {
            return StConverter.convertToSt(null);
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);
        long daysBetween = 0;
        while (c1.before(c2)) {
            c1.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        if (daysBetween == 1 && !c1.equals(c2)) {
            return StConverter.convertToSt("<1");
        }
        return StConverter.convertToSt(Long.toString(daysBetween));
    }
}
