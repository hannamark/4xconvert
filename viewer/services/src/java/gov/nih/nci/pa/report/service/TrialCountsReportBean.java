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

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.report.dto.criteria.TrialCountsCriteriaDto;
import gov.nih.nci.pa.report.dto.result.TrialCountsResultDto;
import gov.nih.nci.pa.report.enums.TimeUnitsCode;
import gov.nih.nci.pa.report.util.ReportUtil;
import gov.nih.nci.pa.report.util.ViewerHibernateSessionInterceptor;
import gov.nih.nci.pa.report.util.ViewerHibernateUtil;
import gov.nih.nci.pa.service.PAException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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
public class TrialCountsReportBean extends AbstractBaseReportBean<TrialCountsCriteriaDto, TrialCountsResultDto>
        implements TrialCountsLocal {

    /** Enumerator used to sort by submission type. */
    private static enum SubmissionType { INITIAL, AMENDMENT, INVALID_UNUSED };
    private static final int HASH_CONSTANT = 33;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TrialCountsResultDto> get(TrialCountsCriteriaDto criteria)
            throws PAException {
        super.get(criteria);
        TimeUnitsCode timeUnits =  TimeUnitsCode.valueOf(CdConverter.convertCdToString(criteria.getGroupByTimeUnit()));
        Map<Group, Integer> counts;
        try {
            session = ViewerHibernateUtil.getCurrentSession();
            String sss = CdConverter.convertCdToString(criteria.getGroupByTimeUnit());
            TimeUnitsCode.valueOf(sss);
            IvlConverter.convertTs().convertLow(criteria.getTimeInterval());
            IvlConverter.convertTs().convertHigh(criteria.getTimeInterval());
            SQLQuery query = null;
            StringBuffer sql = new StringBuffer("SELECT organization, date_last_created, submission_number ");
            sql.append("FROM study_protocol AS sp "
                     + "LEFT OUTER JOIN csm_user AS cm ON (sp.user_last_created = cm.login_name) "
                     + "WHERE 1=1 ");
            sql.append(getDateRangeClauses(criteria, "sp.date_last_created"));
            logger.info("query = " + sql);
            query = session.createSQLQuery(sql.toString());
            setDateRangeParameters(criteria, query);
            @SuppressWarnings(UNCHECKED)
            List<Object[]> queryList = query.list();
            counts = generateCounts(queryList,
                    TimeUnitsCode.valueOf(CdConverter.convertCdToString(criteria.getGroupByTimeUnit())),
                    BlConverter.covertToBoolean(criteria.getGroupBySubmissionType()));
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in " + this.getClass(), hbe);
        }
        logger.info("Leaving get(TrialCountsCriteriaDto), returning " + counts.size() + " object(s).");
        return generateIsoResultList(counts, timeUnits);
    }

    /**
     * Used to group output for summation.
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    class Group implements Comparable<TrialCountsReportBean.Group> {

        private final String org;
        private final Timestamp ts;
        private final SubmissionType st;

        /**
         * @param org organization
         * @param ts creation date
         * @param tu time units
         */
        Group(String org, Timestamp ts, Integer sn, TimeUnitsCode tu, boolean groupByType) {
            int year = 1;
            int month = 1;
            int day = 1;
            if (TimeUnitsCode.DAY.equals(tu)) {
                year = ReportUtil.getYear(ts);
                month = ReportUtil.getMonth(ts);
                day = ReportUtil.getDay(ts);
            }
            if (TimeUnitsCode.MONTH.equals(tu)) {
                year = ReportUtil.getYear(ts);
                month = ReportUtil.getMonth(ts);
            }
            if (TimeUnitsCode.YEAR.equals(tu)) {
                year = ReportUtil.getYear(ts);
            }
            this.org = (org == null) ? "" : org.trim();
            if (groupByType && (sn != null)) {
                if (sn == 1) {
                    this.st = SubmissionType.INITIAL;
                } else if (sn > 1) {
                    this.st = SubmissionType.AMENDMENT;
                } else  {
                    this.st = SubmissionType.INVALID_UNUSED;
                }
            } else {
                this.st = SubmissionType.INVALID_UNUSED;
            }
            this.ts = ReportUtil.makeTimestamp(year, month, day);
        }
        /**
         * @return the org
         */
        String getOrg() {
            return org;
        }
        /**
         * @return the ts
         */
        Timestamp getTs() {
            return ts;
        }
        /**
         * @return the st
         */
        public SubmissionType getSt() {
            return st;
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if ((obj == null) || (obj.getClass() != this.getClass())) {
                return false;
            }
            Group test = (Group) obj;
            return ((ts == test.ts) || (ts != null && ts.equals(test.ts))
                    && (org == test.org || (org != null && org.equals(test.org)))
                    && (st == test.st));
        }
        /**
         * {@inheritDoc}
         */
        @Override
        public int hashCode() {
            int hash = st.hashCode();
            hash += HASH_CONSTANT * ts.hashCode();
            hash += org.hashCode();
            return hash;
        }
        /**
         * {@inheritDoc}
         */
        public int compareTo(Group o) {
            if (this.org.compareTo(o.org) != 0) {
                return this.org.compareTo(o.org);
            }
            if (this.ts.compareTo(o.ts) != 0) {
                return this.ts.compareTo(o.ts);
            }
            return this.st.compareTo(o.st);
        }
    }

    private Map<Group, Integer> generateCounts(List<Object[]> sqlList, TimeUnitsCode timeUnits, boolean groupByType) {
        HashMap<Group, Integer> hm = new HashMap<Group, Integer>();
        for (Object[] sr : sqlList) {
            Group gp = new TrialCountsReportBean.Group((String) sr[0], (Timestamp) sr[1], (Integer) sr[2],
                    timeUnits, groupByType);
            if (hm.containsKey(gp)) {
                hm.put(gp, hm.get(gp) + 1);
            } else {
                hm.put(gp, 1);
            }
        }
        return hm;
    }

    @SuppressWarnings("unchecked")
    private List<TrialCountsResultDto> generateIsoResultList(Map<Group, Integer> counts, TimeUnitsCode timeUnits)
            throws PAException {
        ArrayList<Group> group = new ArrayList<Group>();
        group.addAll(counts.keySet());
        Collections.sort(group);

        ArrayList<TrialCountsResultDto> isoList = new ArrayList<TrialCountsResultDto>();
        for (Group g : counts.keySet()) {
            TrialCountsResultDto iso = new TrialCountsResultDto();
            iso.setCount(IntConverter.convertToInt(counts.get(g)));
            if (timeUnits == TimeUnitsCode.DAY) {
                iso.setDay(IntConverter.convertToInt(ReportUtil.getDay(g.getTs())));
                iso.setMonth(IntConverter.convertToInt(ReportUtil.getMonth(g.getTs())));
                iso.setYear(IntConverter.convertToInt(ReportUtil.getYear(g.getTs())));
            }
            if (timeUnits == TimeUnitsCode.MONTH) {
                iso.setMonth(IntConverter.convertToInt(ReportUtil.getMonth(g.getTs())));
                iso.setYear(IntConverter.convertToInt(ReportUtil.getYear(g.getTs())));
            }
            if (timeUnits == TimeUnitsCode.YEAR) {
                iso.setYear(IntConverter.convertToInt(ReportUtil.getYear(g.getTs())));
            }
            iso.setOrganization(StConverter.convertToSt(g.getOrg()));
            iso.setInitialSubmission(convertSubmissionTypeToInitialSubmissionBl(g.getSt()));
            isoList.add(iso);
        }
        return isoList;
    }

    private Bl convertSubmissionTypeToInitialSubmissionBl(SubmissionType st) {
        if (SubmissionType.INITIAL.equals(st)) {
            return BlConverter.convertToBl(true);
        } else if (SubmissionType.AMENDMENT.equals(st)) {
            return BlConverter.convertToBl(false);
        } else {
            return BlConverter.convertToBl(null);
        }
    }
}
