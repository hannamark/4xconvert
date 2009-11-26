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

import gov.nih.nci.accrual.convert.PerformedClinicalResultConverter;
import gov.nih.nci.accrual.convert.PerformedDiagnosisConverter;
import gov.nih.nci.accrual.convert.PerformedHistopathologyConverter;
import gov.nih.nci.accrual.convert.PerformedImageConverter;
import gov.nih.nci.accrual.convert.PerformedLesionDescriptionConverter;
import gov.nih.nci.accrual.convert.PerformedMedicalHistoryResultConverter;
import gov.nih.nci.accrual.convert.PerformedObservationResultConverter;
import gov.nih.nci.accrual.dto.PerformedClinicalResultDto;
import gov.nih.nci.accrual.dto.PerformedDiagnosisDto;
import gov.nih.nci.accrual.dto.PerformedHistopathologyDto;
import gov.nih.nci.accrual.dto.PerformedImageDto;
import gov.nih.nci.accrual.dto.PerformedLesionDescriptionDto;
import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationResultDto;
import gov.nih.nci.accrual.util.AccrualHibernateSessionInterceptor;
import gov.nih.nci.accrual.util.AccrualHibernateUtil;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PerformedClinicalResult;
import gov.nih.nci.pa.domain.PerformedDiagnosis;
import gov.nih.nci.pa.domain.PerformedHistopathology;
import gov.nih.nci.pa.domain.PerformedImage;
import gov.nih.nci.pa.domain.PerformedLesionDescription;
import gov.nih.nci.pa.domain.PerformedMedicalHistoryResult;
import gov.nih.nci.pa.domain.PerformedObservationResult;
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

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Kalpana Guthikonda
 * @since 11/10/2009
 */
@Stateless
@Interceptors(AccrualHibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
 @SuppressWarnings({"unchecked", "PMD" })
public class PerformedObservationResultBeanLocal
        extends AbstractBaseAccrualBean<PerformedObservationResultDto, PerformedObservationResult, 
        PerformedObservationResultConverter> implements PerformedObservationResultService {
      
    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto getPerformedHistopathology(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedHistopathologyDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedHistopathology bo = (PerformedHistopathology) session.get(PerformedHistopathology.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedHistopathology() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedHistopathologyConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedHistopathology().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedHistopathologyDto> getPerformedHistopathologyByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedHistopathology> queryList = new ArrayList<PerformedHistopathology>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select ph "
            + "from PerformedHistopathology ph "
            + "join ph.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by ph.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedHistopathologyDto> resultList = new ArrayList<PerformedHistopathologyDto>();
        for (PerformedHistopathology bo : queryList) {
            try {
                resultList.add(PerformedHistopathologyConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedHistopathologyByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto createPerformedHistopathology(
            PerformedHistopathologyDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedHistopathology(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedHistopathologyDto updatePerformedHistopathology(
            PerformedHistopathologyDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedHistopathology(dto);
    }

    private PerformedHistopathologyDto createOrUpdatePerformedHistopathology(
            PerformedHistopathologyDto dto) throws RemoteException, DataFormatException {
        PerformedHistopathology bo = null;
        PerformedHistopathologyDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedHistopathologyConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedHistopathology) session.load(PerformedHistopathology.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedHistopathology delta = PerformedHistopathologyConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedHistopathology) session.merge(bo);
        session.flush();
        resultDto = PerformedHistopathologyConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto getPerformedImage(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedImageDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedImage bo = (PerformedImage) session.get(PerformedImage.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedImage() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedImageConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedImage().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedImageDto> getPerformedImageByPerformedActivity(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedImage> queryList = new ArrayList<PerformedImage>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pi "
            + "from PerformedImage pi "
            + "join pi.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by pi.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedImageDto> resultList = new ArrayList<PerformedImageDto>();
        for (PerformedImage bo : queryList) {
            try {
                resultList.add(PerformedImageConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedImageByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto createPerformedImage(PerformedImageDto dto)
    throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedImage(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedImageDto updatePerformedImage(PerformedImageDto dto)
    throws RemoteException, DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedImage(dto);
    }

    private PerformedImageDto createOrUpdatePerformedImage(
            PerformedImageDto dto) throws RemoteException, DataFormatException {
        PerformedImage bo = null;
        PerformedImageDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedImageConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedImage) session.load(PerformedImage.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedImage delta = PerformedImageConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedImage) session.merge(bo);
        session.flush();
        resultDto = PerformedImageConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto getPerformedDiagnosis(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedDiagnosisDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedDiagnosis bo = (PerformedDiagnosis) session.get(PerformedDiagnosis.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedDiagnosis() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedDiagnosisConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedDiagnosis().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedDiagnosisDto> getPerformedDiagnosisByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedDiagnosis> queryList = new ArrayList<PerformedDiagnosis>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pd "
            + "from PerformedDiagnosis pd "
            + "join pd.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by pd.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedDiagnosisDto> resultList = new ArrayList<PerformedDiagnosisDto>();
        for (PerformedDiagnosis bo : queryList) {
            try {
                resultList.add(PerformedDiagnosisConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedDiagnosisByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto createPerformedDiagnosis(
            PerformedDiagnosisDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedDiagnosis(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedDiagnosisDto updatePerformedDiagnosis(
            PerformedDiagnosisDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedDiagnosis(dto);
    }

    private PerformedDiagnosisDto createOrUpdatePerformedDiagnosis(
            PerformedDiagnosisDto dto) throws RemoteException, DataFormatException {
        PerformedDiagnosis bo = null;
        PerformedDiagnosisDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedDiagnosisConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedDiagnosis) session.load(PerformedDiagnosis.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedDiagnosis delta = PerformedDiagnosisConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedDiagnosis) session.merge(bo);
        session.flush();
        resultDto = PerformedDiagnosisConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto getPerformedClinicalResult(Ii ii)
    throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedClinicalResultDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedClinicalResult bo = (PerformedClinicalResult) session.get(PerformedClinicalResult.class
                , IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedClinicalResult() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedClinicalResultConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedClinicalResult().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedClinicalResultDto> getPerformedClinicalResultByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedClinicalResult> queryList = new ArrayList<PerformedClinicalResult>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pcr "
            + "from PerformedClinicalResult pcr "
            + "join pcr.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by pcr.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedClinicalResultDto> resultList = new ArrayList<PerformedClinicalResultDto>();
        for (PerformedClinicalResult bo : queryList) {
            try {
                resultList.add(PerformedClinicalResultConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedClinicalResultByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto createPerformedClinicalResult(
            PerformedClinicalResultDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedClinicalResult(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedClinicalResultDto updatePerformedClinicalResult(
            PerformedClinicalResultDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedClinicalResult(dto);
    }

    private PerformedClinicalResultDto createOrUpdatePerformedClinicalResult(
            PerformedClinicalResultDto dto) throws RemoteException, DataFormatException {
        PerformedClinicalResult bo = null;
        PerformedClinicalResultDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedClinicalResultConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedClinicalResult) session.load(PerformedClinicalResult.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedClinicalResult delta = PerformedClinicalResultConverter.convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedClinicalResult) session.merge(bo);
        session.flush();
        resultDto = PerformedClinicalResultConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto getPerformedMedicalHistoryResult(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedMedicalHistoryResultDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedMedicalHistoryResult bo = (PerformedMedicalHistoryResult) session.get(
                PerformedMedicalHistoryResult.class, IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedMedicalHistoryResult() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedMedicalHistoryResultConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedMedicalHistoryResult().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedMedicalHistoryResultDto> getPerformedMedicalHistoryResultByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedMedicalHistoryResult> queryList = new ArrayList<PerformedMedicalHistoryResult>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pmhr "
            + "from PerformedMedicalHistoryResult pmhr "
            + "join pmhr.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by pmhr.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedMedicalHistoryResultDto> resultList = new 
        ArrayList<PerformedMedicalHistoryResultDto>();
        for (PerformedMedicalHistoryResult bo : queryList) {
            try {
                resultList.add(PerformedMedicalHistoryResultConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedMedicalHistoryResultByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto createPerformedMedicalHistoryResult(
            PerformedMedicalHistoryResultDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedMedicalHistoryResult(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedMedicalHistoryResultDto updatePerformedMedicalHistoryResult(
            PerformedMedicalHistoryResultDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedMedicalHistoryResult(dto);
    }

    private PerformedMedicalHistoryResultDto createOrUpdatePerformedMedicalHistoryResult(
            PerformedMedicalHistoryResultDto dto) throws RemoteException, DataFormatException {
        PerformedMedicalHistoryResult bo = null;
        PerformedMedicalHistoryResultDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedMedicalHistoryResultConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedMedicalHistoryResult) session.load(PerformedMedicalHistoryResult.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedMedicalHistoryResult delta = PerformedMedicalHistoryResultConverter
            .convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedMedicalHistoryResult) session.merge(bo);
        session.flush();
        resultDto = PerformedMedicalHistoryResultConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto getPerformedLesionDescription(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }
        PerformedLesionDescriptionDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        PerformedLesionDescription bo = (PerformedLesionDescription) session.get(
                PerformedLesionDescription.class, IiConverter.convertToLong(ii));
        if (bo == null) {
            throw new RemoteException("Object not found using getPerformedLesionDescription() for id = "
                    + IiConverter.convertToString(ii));
        }
        try {
            resultDto = PerformedLesionDescriptionConverter.convertFromDomainToDto(bo);
        } catch (DataFormatException e) {
            throw new RemoteException("Iso conversion exception in getPerformedLesionDescription().", e);
        }
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedLesionDescriptionDto> getPerformedLesionDescriptionByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedLesionDescription> queryList = new ArrayList<PerformedLesionDescription>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select pld "
            + "from PerformedLesionDescription pld "
            + "join pld.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by pld.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedLesionDescriptionDto> resultList = new 
        ArrayList<PerformedLesionDescriptionDto>();
        for (PerformedLesionDescription bo : queryList) {
            try {
                resultList.add(PerformedLesionDescriptionConverter.convertFromDomainToDto(bo));
            } catch (DataFormatException e) {
                throw new RemoteException("Iso conversion exception in "
                        + " getPerformedLesionDescriptionByPerformedActivity().", e);
            }
        }
        return resultList;
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto updatePerformedLesionDescription(
            PerformedLesionDescriptionDto dto) throws RemoteException,
            DataFormatException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new RemoteException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePerformedLesionDescription(dto);
    }

    /**
     * {@inheritDoc}
     */
    public PerformedLesionDescriptionDto createPerformedLesionDescription(
            PerformedLesionDescriptionDto dto) throws RemoteException, DataFormatException {
        if (PAUtil.isIiNotNull(dto.getIdentifier())) {
            throw new RemoteException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new RemoteException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePerformedLesionDescription(dto);
    }

    private PerformedLesionDescriptionDto createOrUpdatePerformedLesionDescription(
            PerformedLesionDescriptionDto dto) throws RemoteException, DataFormatException {
        PerformedLesionDescription bo = null;
        PerformedLesionDescriptionDto resultDto = null;
        Session session = null;
        session = AccrualHibernateUtil.getCurrentSession();
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            bo = PerformedLesionDescriptionConverter.convertFromDtoToDomain(dto);
        } else {
            bo = (PerformedLesionDescription) session.load(PerformedLesionDescription.class,
                    IiConverter.convertToLong(dto.getIdentifier()));

            PerformedLesionDescription delta = PerformedLesionDescriptionConverter
            .convertFromDtoToDomain(dto);
            bo = delta;
            bo.setDateLastUpdated(new Date());
            session.evict(bo);
        }

        bo = (PerformedLesionDescription) session.merge(bo);
        session.flush();
        resultDto = PerformedLesionDescriptionConverter.convertFromDomainToDto(bo);
        return resultDto;
    }

    /**
     * {@inheritDoc}
     */
    public List<PerformedObservationResultDto> getPerformedObservationResultByPerformedActivity(
            Ii ii) throws RemoteException {
        if (PAUtil.isIiNull(ii)) {
            return null;
        }

        Session session = null;
        List<PerformedObservationResult> queryList = new ArrayList<PerformedObservationResult>();
        session = AccrualHibernateUtil.getCurrentSession();
        Query query = null;

        // step 1: form the hql
        String hql = "select por "
            + "from PerformedObservationResult por "
            + "join por.performedObservation po "
            + "where po.id = :performedActivityId "
            + "order by por.id ";

        // step 2: construct query object
        query = session.createQuery(hql);
        query.setParameter("performedActivityId", IiConverter.convertToLong(ii));

        // step 3: query the result
        queryList = query.list();
        ArrayList<PerformedObservationResultDto> resultList = new ArrayList<PerformedObservationResultDto>();
        for (PerformedObservationResult bo : queryList) {
                PerformedObservationResultConverter converter = new PerformedObservationResultConverter();
                resultList.add(converter.convertFromDomainToDto(bo));
        }
        return resultList;
    }
}
