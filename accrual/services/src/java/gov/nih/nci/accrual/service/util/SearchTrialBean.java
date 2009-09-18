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
*
*
*/
package gov.nih.nci.accrual.service.util;


import gov.nih.nci.accrual.dto.util.SearchTrialCriteriaDto;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.util.AccrualHibernateSessionInterceptor;
import gov.nih.nci.accrual.util.AccrualHibernateUtil;
import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.enums.ActStatusCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Aug 17, 2009
 */

@Stateless
@Interceptors(AccrualHibernateSessionInterceptor.class)
@SuppressWarnings("PMD.CyclomaticComplexity")
public class SearchTrialBean implements SearchTrialService {

    private static final int SP_IDENTIFIER_IDX = 0;
    private static final int ORG_NAME_IDX = 1;
    private static final int SS_IDENTIFIER = 2;
    private static final int SP_TITLE_IDX = 3;
    private static final int SP_ID_IDX = 4;
    private static final int SOS_STATUS_IDX = 5;
    private static final int PERSON_IDX = 6;

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<SearchTrialResultDto> search(SearchTrialCriteriaDto criteria,  St authorizedUser)
            throws RemoteException {
        List<SearchTrialResultDto> result = new ArrayList<SearchTrialResultDto>();
        if (criteria != null && !PAUtil.isStNull(authorizedUser)) {
            Session session = null;
            try {
                session = AccrualHibernateUtil.getCurrentSession();
                Query query = null;
                String hql = generateStudyProtocolQuery(criteria);
                query = session.createQuery(hql);
                List<Long> queryList = query.list();
                Set<Long> authIds = getAuthorizedTrials(authorizedUser);
                for (Long id : queryList) {
                    if (authIds.contains(id)) {
                        result.add(getTrialSummaryByStudyProtocolIi(IiConverter.convertToStudyProtocolIi(id)));
                    }
                }
            } catch (HibernateException hbe) {
                throw new RemoteException(
                        "Hibernate exception in SearchTrialBean.search().", hbe);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public Bl isAuthorized(Ii studyProtocolIi, St authorizedUser) throws RemoteException {
        return BlConverter.convertToBl(getAuthorizedTrials(authorizedUser).
                contains(IiConverter.convertToLong(studyProtocolIi)));
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public SearchTrialResultDto getTrialSummaryByStudyProtocolIi(Ii studyProtocolIi) throws RemoteException {
        SearchTrialResultDto result = new SearchTrialResultDto();
        Session session = null;
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            Query query = null;
            String hql =
                " select sp.identifier, org.name, ss.localStudyProtocolIdentifier, sp.officialTitle, "
                + "      sp.id, sos.statusCode, per "
                + "from StudyProtocol as sp "
                + "left outer join sp.studyOverallStatuses as sos "
                + "left outer join sp.studyContacts as sc "
                + "left outer join sc.clinicalResearchStaff as hcp "
                + "left outer join hcp.person as per "
                + "left outer join sp.studySites as ss  "
                + "left outer join ss.researchOrganization as ro "
                + "left outer join ro.organization as org "
                + "where sp.id = " + IiConverter.convertToString(studyProtocolIi)
                + "  and (ss.functionalCode ='" + StudySiteFunctionalCode.LEAD_ORGANIZATION + "' "
                + "       or ss.functionalCode is null) "
                + "  and (sc.roleCode ='" + StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR + "' "
                + "       or sc.roleCode is null) "
                + "  and (sos.id in (select max(id) from StudyOverallStatus as sos1 "
                + "                where sos.studyProtocol = sos1.studyProtocol ) "
                + "       or sos.id is null) ";
            query = session.createQuery(hql);
            List<Object> queryList = query.list();
            if (queryList.size() < 1) {
                throw new RemoteException("Trial not found in SearchTrialBean.getTrialSummaryByStudyProtocolIi().");
            }
            Object[] qArr = (Object[]) queryList.get(0);
            result.setAssignedIdentifier(StConverter.convertToSt((String) qArr[SP_IDENTIFIER_IDX]));
            result.setLeadOrgName(StConverter.convertToSt((String) qArr[ORG_NAME_IDX]));
            result.setLeadOrgTrialIdentifier(StConverter.convertToSt((String) qArr[SS_IDENTIFIER]));
            result.setOfficialTitle(StConverter.convertToSt((String) qArr[SP_TITLE_IDX]));
            result.setStudyProtocolIdentifier(IiConverter.convertToStudyProtocolIi((Long) qArr[SP_ID_IDX]));
            result.setStudyStatusCode(CdConverter.convertToCd((StudyStatusCode) qArr[SOS_STATUS_IDX]));
            Person person = (Person) qArr[PERSON_IDX];
            result.setPrincipalInvestigator(StConverter.convertToSt(person == null ? null : person.getFullName()));
        } catch (HibernateException hbe) {
            throw new RemoteException(
                    "Hibernate exception in SearchTrialBean.getTrialSummaryByStudyProtocolIi().", hbe);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    private Set<Long> getAuthorizedTrials(St user) throws RemoteException {
        Set<Long> result = new HashSet<Long>();
        User csmUser = AccrualCsmUtil.getInstance().getCSMUser(StConverter.convertToString(user));

        if (csmUser != null) {
            Session session = null;
            try {
                session = AccrualHibernateUtil.getCurrentSession();
                Query query = null;
                String hql = "select distinct sp.id "
                    + "from StudySiteAccrualAccess ssaa "
                    + "join ssaa.studySite ss "
                    + "join ss.studyProtocol sp "
                    + "where ssaa.csmUserId = :csmUserId "
                    + "  and ssaa.statusCode = '" + ActiveInactiveCode.ACTIVE.getName() + "' ";
                query = session.createQuery(hql);
                query.setParameter("csmUserId", csmUser.getUserId());
                List<Long> queryList = query.list();
                result.addAll(queryList);
            } catch (HibernateException hbe) {
                throw new RemoteException("Hibernate exception in SearchTrialBean.getAuthorizedTrials().", hbe);
            }
        }
        return result;
    }

    private String generateStudyProtocolQuery(SearchTrialCriteriaDto criteria) throws RemoteException {
        StringBuffer hql = new StringBuffer();
        try {
            hql.append("select distinct sp.id "
                            + "from StudyProtocol as sp "
                            + "left outer join sp.studySites as sps ");
            hql.append(generateWhereClause(criteria));
        } catch (Exception e) {
            throw new RemoteException("Exception thrown in SearchTrialBean.generateStudyProtocolQuery().", e);
        }
        return hql.toString();
    }

    private String generateWhereClause(SearchTrialCriteriaDto criteria) throws RemoteException {
        String assignedIdentifier = StConverter.convertToString(criteria.getAssignedIdentifier());
        String leadOrgTrialIdentifier = StConverter.convertToString(criteria.getLeadOrgTrialIdentifier());
        String officialTitle = StConverter.convertToString(criteria.getOfficialTitle());
        StringBuffer where = new StringBuffer();

        try {
            where.append("where sp.statusCode = '" + ActStatusCode.ACTIVE.name() + "' ");
            if (PAUtil.isNotEmpty(assignedIdentifier)) {
                where.append(" and upper(sp.identifier)  like '%"
                        + assignedIdentifier.toUpperCase(Locale.US).trim().replaceAll("'", "''")
                        + "%'");
            }
            if (PAUtil.isNotEmpty(officialTitle)) {
                where.append(" and upper(sp.officialTitle)  like '%"
                        + officialTitle.toUpperCase(Locale.US).trim().replaceAll("'", "''")
                        + "%'");
            }
            if (PAUtil.isNotEmpty(leadOrgTrialIdentifier)) {
                where.append(" and upper(sps.localStudyProtocolIdentifier) like '%"
                                + leadOrgTrialIdentifier.toUpperCase(Locale.US).trim().replaceAll("'", "''") + "%'");
            }
        } catch (Exception e) {
            throw new RemoteException("Exception thrown in SearchTrialBean.generateWhereClause().", e);
        }
        return where.toString();
    }
}
