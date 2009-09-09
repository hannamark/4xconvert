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

package gov.nih.nci.pa.service.util;

import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.domain.StudySiteAccrualAccess;
import gov.nih.nci.pa.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Sep 2, 2009
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudySiteAccrualAccessServiceBean implements StudySiteAccrualAccessServiceLocal {

    private static final long REFRESH_TIME = 1000 * 60 * 10;  // 10 minutes
    static Set<User> submitterList = null;
    static Timestamp lastUpdate = null;

    private SessionContext ejbContext;

    @EJB
    StudySiteAccrualStatusServiceLocal studySiteAccrualStatusService;

    @Resource
    void setSessionContext(SessionContext ctx) {
        ejbContext = ctx;
    }

    /**
     * {@inheritDoc}
     */
    public Set<User> getSubmitters() throws PAException {
        if (lastUpdate == null
                || lastUpdate.getTime() + REFRESH_TIME < new Timestamp(new Date().getTime()).getTime()) {
            submitterList = CSMUserService.getInstance().getCSMUsers();
        }
        return submitterList;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public Map<Long, String> getTreatingSites(Long studyProtocolId) throws PAException {
        Session session = null;
        List<Object[]> queryList = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select ss.id, org.name "
                       + "from StudyProtocol sp "
                       + "join sp.studySites ss "
                       + "join ss.healthCareFacility hcf "
                       + "join hcf.organization org "
                       + "where sp.id = :spId "
                       + "  and ss.functionalCode = '" + StudySiteFunctionalCode.TREATING_SITE.getName() + "' "
                       + "order by org.name, ss.id ";
            query = session.createQuery(hql);
            query.setParameter("spId", studyProtocolId);
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.TreatingSites().", hbe);
        }
        Map<Long, String> result = new LinkedHashMap<Long, String>();
        for (Object[] oArr : queryList) {
            result.put((Long) oArr[0], (String) oArr[1]);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessDTO create(StudySiteAccrualAccessDTO access) throws PAException {
        if (!(access.getId() == null)) {
            throw new PAException("Id is not null when calling StudySiteAccrualAccess.create().");
        }
        validateElibibleForCreate(access);
        Session session = null;
        StudySiteAccrualAccess bo = null;
        try {
            session = HibernateUtil.getCurrentSession();
            bo = access.getDomainObject();
            validateValues(bo);
            session.saveOrUpdate(bo);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.create().", hbe);
        }
        return getDTO(bo);
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessDTO get(Long accessId) throws PAException {
        StudySiteAccrualAccess access = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            access = (StudySiteAccrualAccess) session.get(StudySiteAccrualAccess.class, accessId);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.get().", hbe);
        }
        return getDTO(access);
    }

    /**
     * {@inheritDoc}
     */
    public List<StudySiteAccrualAccessDTO> getByStudyProtocol(Long studyProtocolId) throws PAException {
        List<StudySiteAccrualAccess> ssaaList = getBosByStudyProtocol(studyProtocolId);
        List<StudySiteAccrualAccessDTO> rList = new ArrayList<StudySiteAccrualAccessDTO>();
        for (StudySiteAccrualAccess ssaa : ssaaList) {
            rList.add(getDTO(ssaa));
        }
        return rList;
    }

    /**
     * {@inheritDoc}
     */
    public StudySiteAccrualAccessDTO update(StudySiteAccrualAccessDTO access) throws PAException {
        if (access.getId() == null) {
            throw new PAException("Id is null when calling StudySiteAccrualAccess.update().");
        }
        Session session = null;
        StudySiteAccrualAccess bo = null;
        try {
            session = HibernateUtil.getCurrentSession();
            bo = (StudySiteAccrualAccess) session.get(StudySiteAccrualAccess.class, access.getId());
            bo.setRequestDetails(access.getRequestDetails());
            bo.setStatusCode(access.getStatusCode());
            validateValues(bo);
            session.saveOrUpdate(bo);
        } catch (HibernateException hbe) {
            throw new PAException(" Hibernate exception in StudySiteAccrualAccess.update().", hbe);
        }
        return getDTO(bo);
    }

    @SuppressWarnings("unchecked")
    private List<StudySiteAccrualAccess> getBosByStudyProtocol(Long studyProtocolId) throws PAException {
        Session session = null;
        List<StudySiteAccrualAccess> queryList = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select ssaa "
                       + "from StudyProtocol sp "
                       + "join sp.studySites ss "
                       + "join ss.studySiteAccrualAccess ssaa "
                       + "where sp.id = :spId "
                       + "order by ss.id, ssaa.id ";
            query = session.createQuery(hql);
            query.setParameter("spId", studyProtocolId);
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.getBosByStudyProtocol().", hbe);
        }
        return queryList;
    }

    @SuppressWarnings("unchecked")
    private List<StudySiteAccrualAccess> getBosByStudySite(Long studySiteId) throws PAException {
        Session session = null;
        List<StudySiteAccrualAccess> queryList = null;
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            String hql = "select ssaa "
                       + "from StudySite ss "
                       + "join ss.studySiteAccrualAccess ssaa "
                       + "where ss.id = :ssId "
                       + "order by ss.id, ssaa.id ";
            query = session.createQuery(hql);
            query.setParameter("ssId", studySiteId);
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.getBosByStudySite().", hbe);
        }
        return queryList;
    }

    private StudySiteAccrualAccessDTO getDTO(StudySiteAccrualAccess bo) throws PAException {
        User csmUser = new User();
        for (User u : getSubmitters()) {
            if (u.getUserId().equals(bo.getCsmUserId())) {
                csmUser = u;
            }
        }
        StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(
                IiConverter.converToStudySiteIi(bo.getStudySite().getId()));
        StudySiteAccrualAccessDTO result = new StudySiteAccrualAccessDTO();
        result.setCsmUserId(bo.getCsmUserId());
        result.setDateLastCreated(bo.getDateLastCreated());
        result.setDateLastUpdated(bo.getDateLastUpdated());
        result.setEmail(csmUser.getLoginName());
        result.setId(bo.getId());
        result.setPhone(csmUser.getPhoneNumber());
        result.setRequestDetails(bo.getRequestDetails());

        StudySite ss;
        try {
            ss = (StudySite) HibernateUtil.getCurrentSession().get(StudySite.class, bo.getStudySite().getId());
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in StudySiteAccrualAccess.getDTO().", hbe);
        }
        result.setSiteName(ss.getHealthCareFacility() ==  null ? null
                : ss.getHealthCareFacility().getOrganization().getName());
        result.setSiteRecruitmentStatus(ssas == null ? null : CdConverter.convertCdToString(ssas.getStatusCode()));

        result.setStatusCode(bo.getStatusCode());
        result.setStatusDateRangeLow(bo.getStatusDateRangeLow());
        result.setStudySiteId(bo.getStudySite().getId());
        result.setUserLastCreated(bo.getUserLastCreated());
        result.setUserLastUpdated(bo.getUserLastUpdated());
        result.setUserName(getFullName(csmUser));
        return result;
    }

    private void validateElibibleForCreate(StudySiteAccrualAccessDTO access) throws PAException {
        StudySiteAccrualStatusDTO ssas = studySiteAccrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(
                IiConverter.converToStudySiteIi(access.getStudySiteId()));
        RecruitmentStatusCode recruitmentStatus = null;
        if (ssas != null) {
            recruitmentStatus = RecruitmentStatusCode.getByCode(ssas.getStatusCode().getCode());
            if (!recruitmentStatus.isEligibleForAccrual()) {
                throw new PAException("Access can only be granted for sites which are accruing.  Current status is '"
                        + recruitmentStatus.getCode() + "'.");
            }
        }
        List<StudySiteAccrualAccess> aList = getBosByStudySite(access.getStudySiteId());
        for (StudySiteAccrualAccess a : aList) {
            if (a.getStudySite().getId().equals(access.getStudySiteId())
                    && a.getCsmUserId().equals(access.getCsmUserId())) {
                throw new PAException("User has already been assigned to the study site.  "
                        + "To update click the edit link in existing accrual users list.");
            }
        }
    }

    private void validateValues(StudySiteAccrualAccess bo) throws PAException {
        checkNull(bo.getCsmUserId(), "User Name must be set.");
        checkNull(bo.getStudySite(), "Accessing Site must be set.");
        checkNull(bo.getStatusCode(), "Access Status must be set.");
        bo.setStatusDateRangeLow(new Timestamp(new Date().getTime()));
        if (bo.getId() == null) {
            bo.setUserLastCreated(ejbContext != null ? ejbContext.getCallerPrincipal().getName() : "not logged");
            bo.setDateLastCreated(new Date());
            bo.setUserLastUpdated(null);
            bo.setDateLastUpdated(null);
        } else {
            bo.setUserLastUpdated(ejbContext != null ? ejbContext.getCallerPrincipal().getName() : "not logged");
            bo.setDateLastUpdated(new Date());
        }
    }

    private static void checkNull(Object obj, String errMsg) throws PAException {
        if (obj == null) {
            throw new PAException(errMsg);
        }
    }

    /**
     * @param user The csm user object
     * @return Name of the user formated last, first.  If no name is entered use login name.
     */
    public static String getFullName(User user) {
        String fullName = null;
        if (user.getLastName() != null) {
            fullName = user.getLastName();
        }
        if (user.getFirstName() != null) {
            fullName = user.getLastName() + ", " + user.getFirstName();
        }
        if (PAUtil.isEmpty(fullName)) {
            fullName = user.getLoginName();
        }
        return fullName;
    }

}
