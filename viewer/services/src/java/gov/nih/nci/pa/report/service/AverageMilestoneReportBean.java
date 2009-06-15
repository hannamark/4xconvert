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

import gov.nih.nci.coppa.iso.Int;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.MilestoneCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.SubmissionTypeCriteriaDto;
import gov.nih.nci.pa.report.dto.result.AverageMilestoneResultDto;
import gov.nih.nci.pa.report.enums.SubmissionTypeCode;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.report.util.ViewerHibernateSessionInterceptor;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;

/**
* @author Hugh Reinhart
* @since 5/12/2009
*/
@Stateless
@Interceptors(ViewerHibernateSessionInterceptor.class)
public class AverageMilestoneReportBean
        extends AbstractStandardReportBean<SubmissionTypeCriteriaDto, AverageMilestoneResultDto>
        implements AverageMilestoneLocal {

    private static final int DAY01_IDX = 0;
    private static final int DAY02_IDX = 1;
    private static final int DAY03_IDX = 2;
    private static final int DAY04_IDX = 3;
    private static final int DAY05_IDX = 4;
    private static final int DAY06_IDX = 5;
    private static final int DAY07_IDX = 6;
    private static final int DAY08_IDX = 7;
    private static final int DAY09_IDX = 8;
    private static final int DAY10_IDX = 9;
    private static final int GT10_IDX = 10;
    private static final int GT10_TOTAL_IDX = 11;

    private static final String[] REPORTING_MILESTONES = {
        MilestoneCode.SUBMISSION_RECEIVED.getName(),
        MilestoneCode.SUBMISSION_ACCEPTED.getName(),
        MilestoneCode.SUBMISSION_REJECTED.getName(),
        MilestoneCode.ADMINISTRATIVE_PROCESSING_START_DATE.getName(),
        MilestoneCode.ADMINISTRATIVE_PROCESSING_COMPLETED_DATE.getName(),
        MilestoneCode.SCIENTIFIC_PROCESSING_START_DATE.getName(),
        MilestoneCode.SCIENTIFIC_PROCESSING_COMPLETED_DATE.getName(),
        MilestoneCode.READY_FOR_QC.getName(),
        MilestoneCode.QC_START.getName(),
        MilestoneCode.QC_COMPLETE.getName(),
        MilestoneCode.TRIAL_SUMMARY_SENT.getName(),
        MilestoneCode.TRIAL_SUMMARY_FEEDBACK.getName(),
        MilestoneCode.INITIAL_ABSTRACTION_VERIFY.getName()
    };

    /**
     * {@inheritDoc}
     */
    public List<AverageMilestoneResultDto> get(
            SubmissionTypeCriteriaDto criteria) throws PAException {
        SubmissionTypeCriteriaDto.validate(criteria);
        List<Counts> cList = getMilestoneList(getTrialList(criteria));
        return createResultList(createResultHash(cList));
    }

    private static Map<MilestoneCode, Integer[]> createResultHash(List<Counts> cList) {
        Map<MilestoneCode, Integer[]> resultHash = new HashMap<MilestoneCode, Integer[]>();
        for (Counts c : cList) {
            if (c.order > 1) { continue; }
            if (!resultHash.containsKey(c.code)) {
                Integer[] tempArray = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
                resultHash.put(c.code, tempArray);
            }
            Integer[] array = resultHash.get(c.code);
            if (c.days > GT10_IDX) {
                array[GT10_IDX]++;
                array[GT10_TOTAL_IDX] = array[GT10_TOTAL_IDX] + c.days;
            } else {
                array[c.days - 1]++;
            }
            resultHash.put(c.code, array);
        }
         return resultHash;
    }

    private static List<AverageMilestoneResultDto> createResultList(Map<MilestoneCode, Integer[]> resultHash) {
        List<AverageMilestoneResultDto> rList = new ArrayList<AverageMilestoneResultDto>();
        for (MilestoneCode milestone : MilestoneCode.values()) {
            if (resultHash.containsKey(milestone)) {
                AverageMilestoneResultDto r = new AverageMilestoneResultDto();
                r.setMilestoneCode(CdConverter.convertToCd(milestone));
                r.setOrder(IntConverter.convertToInt(1));
                Integer[] t = resultHash.get(milestone);
                r.setDay01(setResultDays1To10(DAY01_IDX, t));
                r.setDay02(setResultDays1To10(DAY02_IDX, t));
                r.setDay03(setResultDays1To10(DAY03_IDX, t));
                r.setDay04(setResultDays1To10(DAY04_IDX, t));
                r.setDay05(setResultDays1To10(DAY05_IDX, t));
                r.setDay06(setResultDays1To10(DAY06_IDX, t));
                r.setDay07(setResultDays1To10(DAY07_IDX, t));
                r.setDay08(setResultDays1To10(DAY08_IDX, t));
                r.setDay09(setResultDays1To10(DAY09_IDX, t));
                r.setDay10(setResultDays1To10(DAY10_IDX, t));
                r.setGtTenDays(IntConverter.convertToInt(t[GT10_IDX]));
                r.setAverage(setResultAverage(t));
                r.setHigh(setResultHigh(t));
                r.setLow(setResultLow(t));
                rList.add(r);
            }
        }
        return rList;
    }

    private static Int setResultDays1To10(int index, Integer[] array) {
        Integer result = array[index];
        return IntConverter.convertToInt(result);
    }

    static St setResultLow(Integer[] array) {
        int low = -1;
        for (int x = DAY01_IDX; x <= GT10_IDX; x++) {
            if (array[x] > 0) {
                low = x;
                break;
            }
        }
        if (low == GT10_IDX) {
            return StConverter.convertToSt(">10");
        }
        return StConverter.convertToSt(Integer.toString(low + 1));
    }

    static St setResultHigh(Integer[] array) {
        int high = -1;
        for (int x = DAY01_IDX; x <= GT10_IDX; x++) {
            if (array[x] > 0) {
                high = x;
            }
        }
        if (high == GT10_IDX) {
            return StConverter.convertToSt(">10");
        }
        return StConverter.convertToSt(Integer.toString(high + 1));
    }

    static St setResultAverage(Integer[] array) {
        int count = array[GT10_IDX];
        float total = array[GT10_TOTAL_IDX];
        for (int x = DAY01_IDX; x < DAY10_IDX; x++) {
            count = count + array[x];
            total = total + array[x] * (x + 1);
        }
        return StConverter.convertToSt(Float.toString(total / count));
    }

    /**
     * Method does report specific formating of time intervals as Integers.
     * @param t1 early date
     * @param t2 late date
     * @return normalized number of days
     */
    private static Integer intervalToInteger(Timestamp t1, Timestamp t2) {
        if (ReportUtil.invalidIntervalValues(t1, t2)) {
            return 1;
        }
        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);
        if (!c1.before(c2)) {
            return 1;
        }
        Integer daysBetween = 0;
        while (c1.before(c2)) {
            c1.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        return daysBetween;
    }

    /** Inner class used to generate output. */
    private class Counts {
        BigInteger spId;
        Timestamp date;
        MilestoneCode code;
        Integer order;
        Integer days;
    }

    private List<BigInteger> getTrialList(SubmissionTypeCriteriaDto criteria) throws PAException {
        List<BigInteger> rList = new ArrayList<BigInteger>();
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer(
                  "SELECT distinct sp.identifier "
                + "FROM study_protocol AS sp "
                + "  JOIN study_milestone AS sm ON (sm.study_protocol_identifier = sp.identifier) "
                + "WHERE sm.milestone_code IN (:REPORTING_MILESTONES) ");
            if (CdConverter.convertCdToString(criteria.getSubmissionType()).
                    equals(SubmissionTypeCode.ORIGINAL.name())) {
                sql.append("  AND sp.submission_number = 1 ");
            } else if (CdConverter.convertCdToString(criteria.getSubmissionType()).
                    equals(SubmissionTypeCode.AMENDMENT.name())) {
                sql.append("  AND sp.submission_number > 1 ");
            }
            sql.append(dateRangeSql(criteria, "sm.milestone_date"));
            sql.append(ctepSql(criteria));
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql.toString());
            setDateRangeParameters(criteria, query);
            query.setParameterList("REPORTING_MILESTONES", REPORTING_MILESTONES);
            @SuppressWarnings(UNCHECKED)
            List<BigInteger> qList = query.list();
            for (BigInteger q : qList) {
                rList.add(q);
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving AverageMilestoneReportBean.getTrialList().  Returning " + rList.size() + " trials.");
        return rList;
    }

    private List<Counts> getMilestoneList(List<BigInteger> trialList) throws PAException {
        List<Counts> rList = new ArrayList<Counts>();
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer(
                  "SELECT study_protocol_identifier, milestone_date, milestone_code "
                + "FROM study_milestone "
                + "WHERE milestone_code IN (:REPORTING_MILESTONES) "
                + "  AND study_protocol_identifier IN (:TRIAL_LIST) "
                + "ORDER BY study_protocol_identifier, milestone_date, identifier ");
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql.toString());
            query.setParameterList("REPORTING_MILESTONES", REPORTING_MILESTONES);
            query.setParameterList("TRIAL_LIST", trialList);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> qList = query.list();
            BigInteger currentId = null;
            Timestamp priorDate = null;
            HashMap<MilestoneCode, Integer> orderHash = new HashMap<MilestoneCode, Integer>();
            for (Object[] q : qList) {
                Counts r = new Counts();
                r.spId = (BigInteger) q[0];
                if (currentId != r.spId) {
                    currentId = r.spId;
                    priorDate = null;
                    orderHash.clear();
                }
                r.date = (Timestamp) q[1];
                r.code = MilestoneCode.valueOf((String) q[2]);
                if (orderHash.containsKey(r.code)) {
                    orderHash.put(r.code, orderHash.get(r.code) + 1);
                    r.order = orderHash.get(r.code);
                } else {
                    orderHash.put(r.code, 1);
                    r.order = 1;
                }
                r.days = intervalToInteger(priorDate, r.date);
                priorDate = r.date;
                rList.add(r);
            }
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving AverageMilestoneReportBean.getMilestonList().  Returning "
                + rList.size() + " milestones.");
        return rList;
    }
}
