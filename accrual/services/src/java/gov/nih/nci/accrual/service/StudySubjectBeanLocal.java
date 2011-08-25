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

package gov.nih.nci.accrual.service;

import gov.nih.nci.accrual.convert.StudySubjectConverter;
import gov.nih.nci.accrual.dto.StudySubjectDto;
import gov.nih.nci.accrual.util.CaseSensitiveUsernameHolder;
import gov.nih.nci.accrual.util.PaServiceLocator;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.domain.StudySubject;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;
import gov.nih.nci.pa.util.PaHibernateUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author Hugh Reinhart
 * @since Aug 29, 2009
 */
@Stateless
@Interceptors(PaHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySubjectBeanLocal extends
        AbstractBaseAccrualStudyBean<StudySubjectDto, StudySubject, StudySubjectConverter> implements
        StudySubjectServiceLocal {

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudySubjectDto> getByStudySite(Ii ii) throws PAException {
        if (ISOUtil.isIiNull(ii)) {
            throw new PAException("Called getByStudySite() with Ii == null.");
        }
        try {
            Session session = PaHibernateUtil.getCurrentSession();
            String hql = "select ssub from StudySubject ssub join ssub.studySite ssite where ssite.id = :studySiteId "
                    + "order by ssub.id ";
            Query query = session.createQuery(hql);
            query.setParameter("studySiteId", IiConverter.convertToLong(ii));
            List<StudySubject> queryList = query.list();

            return convertFromBoListToDtoList(queryList);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in getByStudyProtocol().", hbe);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudySubjectDto> getStudySubjects(String assignedIdentifier, Long studySiteId, Date birthDate,
            FunctionalRoleStatusCode statusCode) throws PAException {
        try {
            Criteria criteria = PaHibernateUtil.getCurrentSession().createCriteria(StudySubject.class);
            populateCriteria(assignedIdentifier, studySiteId, birthDate, statusCode, criteria);
            List<StudySubject> subjects = criteria.list();
            return convertFromBoListToDtoList(subjects);
        } catch (HibernateException e) {
            throw new PAException("Error retrieving study subjects", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudySubjectDto> search(Long studyIdentifier, Long participatingSiteIdentifier, Timestamp startDate,
            Timestamp endDate, LimitOffset pagingParams) throws PAException {
        verifyStudyIdentifier(studyIdentifier);

        List<StudySubject> studySubjects = searchStudySubject(studyIdentifier, participatingSiteIdentifier, startDate,
                                                          endDate, pagingParams);
        
        calculateAccessibleStudySubjects(studySubjects, getUserId());
        
        return convertFromBoListToDtoList(studySubjects);
        
    }

    void verifyStudyIdentifier(Long studyIdentifier) throws PAException {
        if (getStudyProtocolService().getStudyProtocol(IiConverter.convertToStudyProtocolIi(studyIdentifier)) == null) {
            throw new PAException("No study with the given identifier.");
        }
    }

    StudyProtocolService getStudyProtocolService() {
        return PaServiceLocator.getInstance().getStudyProtocolService();
    }
    
    Long getUserId() throws PAException {
        return PaServiceLocator.getInstance().getRegistryUserService().getUser(CaseSensitiveUsernameHolder.getUser())
            .getId();
    }
    
    void calculateAccessibleStudySubjects(List<StudySubject> studySubjects, Long userId) {
        for (Iterator<StudySubject> it = studySubjects.iterator(); it.hasNext();) {
            StudySubject studySubject = it.next();
            boolean found = false;
            for (StudySiteAccrualAccess access : studySubject.getStudySite().getStudySiteAccrualAccess()) {
                if (access.getRegistryUser().getId() == userId) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                it.remove();
            }
        }

    }

    @SuppressWarnings("unchecked")
    List<StudySubject> searchStudySubject(Long studyIdentifier, Long participatingSiteIdentifier,
            Timestamp startDate, Timestamp endDate, LimitOffset pagingParams) throws PAException {

        try {
            String str = createSearchQueryString(participatingSiteIdentifier, startDate, endDate);

            Query query = createSearchQuery(new CreateSearchQueryParameterBean(studyIdentifier,
                    participatingSiteIdentifier, startDate, endDate, pagingParams), str);

            return query.list();
           
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in search().", hbe);
        }
    }
    
    private String createSearchQueryString(Long participatingSiteIdentifier, Timestamp startDate,
            Timestamp endDate) {
        StringBuilder str = new StringBuilder();
        str.append("select ssub from StudySubject ssub");

        str.append(" where ssub.studyProtocol.id = :studyProtocolId ");

        if (participatingSiteIdentifier != null) {
            str.append(" and ssub.studySite.id = :studySiteId");
        }

        if (startDate != null || endDate != null) {
            str.append(" and exists (from PerformedSubjectMilestone psm where psm.studySubject.id = ssub.id");
            if (startDate != null) {
                str.append(" and psm.registrationDate>= :startDate");
            }
            if (endDate != null) {
                str.append(" and psm.registrationDate<= :endDate");
            }
            str.append(")");
        }

        str.append(" order by ssub.id ");
        return str.toString();
    }

    private Query createSearchQuery(CreateSearchQueryParameterBean parameterObject, String str) {
        Query query = PaHibernateUtil.getCurrentSession().createQuery(str);
        if (parameterObject.getPagingParams() != null) {
            query.setFirstResult(parameterObject.getPagingParams().getOffset());
            query.setMaxResults(parameterObject.getPagingParams().getLimit());
        }
        
        query.setParameter("studyProtocolId", parameterObject.getStudyIdentifier());
        if (parameterObject.getParticipatingSiteIdentifier() != null) {
            query.setParameter("studySiteId", parameterObject.getParticipatingSiteIdentifier());
        }
        if (parameterObject.getStartDate() != null) {
            query.setParameter("startDate", parameterObject.getStartDate());
        }
        if (parameterObject.getEndDate() != null) {
            query.setParameter("endDate", parameterObject.getEndDate());
        }
        return query;
    }

    List<StudySubjectDto> convertFromBoListToDtoList(List<StudySubject> queryList) {
        List<StudySubjectDto> resultList = new ArrayList<StudySubjectDto>();
        for (StudySubject bo : queryList) {
            resultList.add(convertFromDomainToDto(bo));
        }
        return resultList;
    }  

    private void populateCriteria(String assignedIdentifier, Long studySiteId, Date birthDate,
            FunctionalRoleStatusCode statusCode, Criteria criteria) {
        if (StringUtils.isNotEmpty(assignedIdentifier)) {
            criteria.add(Restrictions.ilike("assignedIdentifier", assignedIdentifier, MatchMode.ANYWHERE));
        }
        if (statusCode != null) {
            criteria.add(Restrictions.eq("statusCode", statusCode));
        }
        if (studySiteId != null) {
            criteria.createCriteria("studySite", "ss").add(Restrictions.eq("ss.id", studySiteId));
        }
        if (birthDate != null) {
            criteria.createCriteria("patient", "p").add(Restrictions.eq("p.birthDate", birthDate));
        }
    }
    
    /**
     * @author merenkoi
     */
    private static class CreateSearchQueryParameterBean {

        private final Long studyIdentifier;

        private final Long participatingSiteIdentifier;

        private final Timestamp startDate;

        private final Timestamp endDate;

        private final LimitOffset pagingParams;

        public CreateSearchQueryParameterBean(Long studyIdentifier, Long participatingSiteIdentifier,
                Timestamp startDate, Timestamp endDate, LimitOffset pagingParams) {
            this.studyIdentifier = studyIdentifier;
            this.participatingSiteIdentifier = participatingSiteIdentifier;
            this.startDate = startDate;
            this.endDate = endDate;
            this.pagingParams = pagingParams;
        }

        /**
         * @return the studyIdentifier
         */
        public Long getStudyIdentifier() {
            return studyIdentifier;
        }

        /**
         * @return the participatingSiteIdentifier
         */
        public Long getParticipatingSiteIdentifier() {
            return participatingSiteIdentifier;
        }

        /**
         * @return the startDate
         */
        public Timestamp getStartDate() {
            return startDate;
        }

        /**
         * @return the endDate
         */
        public Timestamp getEndDate() {
            return endDate;
        }

        /**
         * @return the pagingParams
         */
        public LimitOffset getPagingParams() {
            return pagingParams;
        }

    }
}
