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

import gov.nih.nci.accrual.convert.PerformedActivityConverter;
import gov.nih.nci.accrual.convert.PerformedImagingConverter;
import gov.nih.nci.accrual.convert.PerformedObservationConverter;
import gov.nih.nci.accrual.convert.PerformedProcedureConverter;
import gov.nih.nci.accrual.convert.PerformedRadiationAdministrationConverter;
import gov.nih.nci.accrual.convert.PerformedSubjectMilestoneConverter;
import gov.nih.nci.accrual.convert.PerformedSubstanceAdministrationConverter;
import gov.nih.nci.accrual.dto.PerformedActivityDto;
import gov.nih.nci.accrual.dto.PerformedImagingDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.dto.PerformedProcedureDto;
import gov.nih.nci.accrual.dto.PerformedRadiationAdministrationDto;
import gov.nih.nci.accrual.dto.PerformedSubjectMilestoneDto;
import gov.nih.nci.accrual.dto.PerformedSubstanceAdministrationDto;
import gov.nih.nci.accrual.util.AccrualHibernateSessionInterceptor;
import gov.nih.nci.accrual.util.AccrualHibernateUtil;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PerformedProcedure;
import gov.nih.nci.pa.domain.PerformedActivity;
import gov.nih.nci.pa.domain.PerformedImaging;
import gov.nih.nci.pa.domain.PerformedObservation;
import gov.nih.nci.pa.domain.PerformedRadiationAdministration;
import gov.nih.nci.pa.domain.PerformedSubjectMilestone;
import gov.nih.nci.pa.domain.PerformedSubstanceAdministration;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since Aug 13, 2009
 */
@Stateless
@Interceptors(AccrualHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
 @SuppressWarnings({"unchecked", "PMD" })
public class PerformedActivityBeanLocal
        extends AbstractBaseAccrualStudyBean<PerformedActivityDto, PerformedActivity, PerformedActivityConverter>
implements PerformedActivityService {

    /**
     * {@inheritDoc}
     */   
    public List<PerformedSubjectMilestoneDto> getByStudySubject(Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            throw new RemoteException("Called getByStudySubject() with Ii == null.");
        }
        getLogger().info("EnteringgetByStudySubject().");

        Session session = null;
        List<PerformedSubjectMilestone> queryList = new ArrayList<PerformedSubjectMilestone>();
        try {
            session = AccrualHibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select psm "
                + "from PerformedSubjectMilestone psm "
                + "join psm.studySubject ssub "
                + "where ssub.id = :studySubjectId "
                + "order by psm.id ";

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studySubjectId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new RemoteException("Hibernate exception in getByStudyProtocol().", hbe);
        }
        ArrayList<PerformedSubjectMilestoneDto> resultList = new ArrayList<PerformedSubjectMilestoneDto>();
        for (PerformedSubjectMilestone bo : queryList) {
            try {
                resultList.add((PerformedSubjectMilestoneConverter.convertFromDomainToDto(bo)));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in getByStudySubject().", e);
            }
        }
        getLogger().info("Leaving getByStudySubject, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
    
    /**
     * {@inheritDoc}
     */
    public PerformedSubjectMilestoneDto getPerformedSubjectMilestone(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedSubjectMilestoneDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedSubjectMilestone bo = (PerformedSubjectMilestone) session.get(PerformedSubjectMilestone.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedSubjectMilestone() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedSubjectMilestoneConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedSubjectMilestone().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedSubjectMilestoneDto> getPerformedSubjectMilestoneByStudyProtocol(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedSubjectMilestone> queryList = new ArrayList<PerformedSubjectMilestone>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedSubjectMilestone pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedSubjectMilestoneDto> resultList = new ArrayList<PerformedSubjectMilestoneDto>();
        for (PerformedSubjectMilestone bo : queryList) {
            try {
                resultList.add(PerformedSubjectMilestoneConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedSubjectMilestoneByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedSubjectMilestoneDto createPerformedSubjectMilestone(
            PerformedSubjectMilestoneDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedSubjectMilestone(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedSubjectMilestoneDto updatePerformedSubjectMilestone(
            PerformedSubjectMilestoneDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedSubjectMilestone(dto);
    }

    private PerformedSubjectMilestoneDto createOrUpdatePerformedSubjectMilestone(
            PerformedSubjectMilestoneDto dto) throws RemoteException, DataFormatException {
        PerformedSubjectMilestone bo = null;
        PerformedSubjectMilestoneDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedSubjectMilestoneConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedSubjectMilestone) session.load(PerformedSubjectMilestone.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedSubjectMilestone delta = PerformedSubjectMilestoneConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedSubjectMilestone) session.merge(bo);
        session.flush();
        resultDto = PerformedSubjectMilestoneConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImagingDto getPerformedImaging(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedImagingDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedImaging bo = (PerformedImaging) session.get(PerformedImaging.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedImaging() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedImagingConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedImaging().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedImagingDto> getPerformedImagingByStudyProtocol(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedImaging> queryList = new ArrayList<PerformedImaging>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedImaging pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedImagingDto> resultList = new ArrayList<PerformedImagingDto>();
        for (PerformedImaging bo : queryList) {
            try {
                resultList.add(PerformedImagingConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedImagingByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImagingDto createPerformedImaging(PerformedImagingDto dto)
    throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedImaging(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImagingDto updatePerformedImaging(PerformedImagingDto dto)
    throws RemoteException, DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedImaging(dto);
    }

    private PerformedImagingDto createOrUpdatePerformedImaging(
            PerformedImagingDto dto) throws RemoteException, DataFormatException {
        PerformedImaging bo = null;
        PerformedImagingDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedImagingConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedImaging) session.load(PerformedImaging.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedImaging delta = PerformedImagingConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedImaging) session.merge(bo);
        session.flush();
        resultDto = PerformedImagingConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedObservationDto getPerformedObservation(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedObservationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedObservation bo = (PerformedObservation) session.get(PerformedObservation.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedObservation() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedObservationConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedObservation().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedObservationDto> getPerformedObservationByStudyProtocol(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedObservation> queryList = new ArrayList<PerformedObservation>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedObservation pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedObservationDto> resultList = new ArrayList<PerformedObservationDto>();
        for (PerformedObservation bo : queryList) {
            try {
                resultList.add(PerformedObservationConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedObservationByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedObservationDto createPerformedObservation(
            PerformedObservationDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedObservation(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedObservationDto updatePerformedObservation(
            PerformedObservationDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedObservation(dto);
    }

    private PerformedObservationDto createOrUpdatePerformedObservation(
            PerformedObservationDto dto) throws RemoteException, DataFormatException {
        PerformedObservation bo = null;
        PerformedObservationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedObservationConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedObservation) session.load(PerformedObservation.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedObservation delta = PerformedObservationConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedObservation) session.merge(bo);
        session.flush();
        resultDto = PerformedObservationConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedProcedureDto getPerformedProcedure(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedProcedureDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedProcedure bo = (PerformedProcedure) session.get(PerformedProcedure.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedProcedure() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedProcedureConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedProcedure().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedProcedureDto> getPerformedProcedureByStudyProtocol(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedProcedure> queryList = new ArrayList<PerformedProcedure>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedProcedure pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedProcedureDto> resultList = new ArrayList<PerformedProcedureDto>();
        for (PerformedProcedure bo : queryList) {
            try {
                resultList.add(PerformedProcedureConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedProcedureByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedProcedureDto createPerformedProcedure(
            PerformedProcedureDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedProcedure(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedProcedureDto updatePerformedProcedure(
            PerformedProcedureDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedProcedure(dto);
    }

    private PerformedProcedureDto createOrUpdatePerformedProcedure(
            PerformedProcedureDto dto) throws RemoteException, DataFormatException {
        PerformedProcedure bo = null;
        PerformedProcedureDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedProcedureConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedProcedure) session.load(PerformedProcedure.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedProcedure delta = PerformedProcedureConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedProcedure) session.merge(bo);
        session.flush();
        resultDto = PerformedProcedureConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedRadiationAdministrationDto getPerformedRadiationAdministration(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedRadiationAdministrationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedRadiationAdministration bo = (PerformedRadiationAdministration) session.get(
                PerformedRadiationAdministration.class, IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedRadiationAdministration() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedRadiationAdministrationConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedRadiationAdministration().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedRadiationAdministrationDto> getPerformedRadiationAdministrationByStudyProtocol(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedRadiationAdministration> queryList = new ArrayList<PerformedRadiationAdministration>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedRadiationAdministration pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedRadiationAdministrationDto> resultList = new 
        ArrayList<PerformedRadiationAdministrationDto>();
        for (PerformedRadiationAdministration bo : queryList) {
            try {
                resultList.add(PerformedRadiationAdministrationConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedRadiationAdministrationByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedRadiationAdministrationDto createPerformedRadiationAdministration(
            PerformedRadiationAdministrationDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedRadiationAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedRadiationAdministrationDto updatePerformedRadiationAdministration(
            PerformedRadiationAdministrationDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedRadiationAdministration(dto);
    }

    private PerformedRadiationAdministrationDto createOrUpdatePerformedRadiationAdministration(
            PerformedRadiationAdministrationDto dto) throws RemoteException, DataFormatException {
        PerformedRadiationAdministration bo = null;
        PerformedRadiationAdministrationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedRadiationAdministrationConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedRadiationAdministration) session.load(PerformedRadiationAdministration.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedRadiationAdministration delta = PerformedRadiationAdministrationConverter
            .convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedRadiationAdministration) session.merge(bo);
        session.flush();
        resultDto = PerformedRadiationAdministrationConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedSubstanceAdministrationDto getPerformedSubstanceAdministration(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedSubstanceAdministrationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedSubstanceAdministration bo = (PerformedSubstanceAdministration) session.get(
                PerformedSubstanceAdministration.class, IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedSubstanceAdministration() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedSubstanceAdministrationConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedSubstanceAdministration().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedSubstanceAdministrationDto> getPerformedSubstanceAdministrationByStudyProtocol(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedSubstanceAdministration> queryList = new ArrayList<PerformedSubstanceAdministration>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pa "
            + "from PerformedSubstanceAdministration pa "
            + "join pa.studyProtocol sp "
            + "where sp.id = :studyProtocolId "
            + "order by pa.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedSubstanceAdministrationDto> resultList = new 
        ArrayList<PerformedSubstanceAdministrationDto>();
        for (PerformedSubstanceAdministration bo : queryList) {
            try {
                resultList.add(PerformedSubstanceAdministrationConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedSubstanceAdministrationByStudyProtocol().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedSubstanceAdministrationDto updatePerformedSubstanceAdministration(
            PerformedSubstanceAdministrationDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedSubstanceAdministration(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedSubstanceAdministrationDto createPerformedSubstanceAdministration(
            PerformedSubstanceAdministrationDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedSubstanceAdministration(dto);
    }

    private PerformedSubstanceAdministrationDto createOrUpdatePerformedSubstanceAdministration(
            PerformedSubstanceAdministrationDto dto) throws RemoteException, DataFormatException {
        PerformedSubstanceAdministration bo = null;
        PerformedSubstanceAdministrationDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedSubstanceAdministrationConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedSubstanceAdministration) session.load(PerformedSubstanceAdministration.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedSubstanceAdministration delta = PerformedSubstanceAdministrationConverter
            .convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedSubstanceAdministration) session.merge(bo);
        session.flush();
        resultDto = PerformedSubstanceAdministrationConverter.convertFromDomainToDto(bo);
        return resultDto;
    }
}
