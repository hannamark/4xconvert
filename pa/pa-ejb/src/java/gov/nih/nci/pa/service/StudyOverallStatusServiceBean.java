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
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyOverallStatus;
import gov.nih.nci.pa.domain.StudyRecruitmentStatus;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.convert.StudyOverallStatusConverter;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 08/22/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
public class StudyOverallStatusServiceBean
        extends AbstractStudyPaService<StudyOverallStatusDTO>
        implements StudyOverallStatusServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyOverallStatusServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * Method used to update the StudyOverallStatus and StudyRecruitmentStatus.
     * Note that this is the only method which does this.  StudyRecruitmentStatusService
     * is used for reporting only.
     *
     * @param dto studyOverallStatusDTO
     * @return StudyOverallStatusDTO
     * @throws PAException PAException
     */
    @SuppressWarnings({"PMD.NPathComplexity", "PMD.ExcessiveMethodLength" })
    public StudyOverallStatusDTO create(
            StudyOverallStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            String errMsg = " Existing StudyOverallStatus objects cannot be modified.  Append new object instead. ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        StudyOverallStatusDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();

            // enforce business rules
            List<StudyOverallStatusDTO> oldStatus = getCurrentByStudyProtocol(
                    dto.getStudyProtocolIi());

            StudyStatusCode oldCode = null;
            Timestamp oldDate = null;
            if (!oldStatus.isEmpty()) {
                oldCode = StudyStatusCode.getByCode(oldStatus.get(0).getStatusCode().getCode());
                oldDate = TsConverter.convertToTimestamp(oldStatus.get(0).getStatusDate());
            }
            StudyStatusCode newCode = StudyStatusCode.getByCode(dto.getStatusCode().getCode());
            Timestamp newDate = TsConverter.convertToTimestamp(dto.getStatusDate());
            if (newCode == null) {
                throw new PAException("Study status must be set.  ");
            }
            if (newDate == null) {
                throw new PAException("Study status date must be set.  ");
            }
            if ((oldCode != null) && !oldCode.canTransitionTo(newCode)) {
                throw new PAException("Illegal study status transition from " + oldCode.getCode()
                        + " to " + newCode.getCode() + ".  ");
            }
            if ((oldDate != null) && newDate.before(oldDate)) {
                throw new PAException("New current status date should be bigger/same as old date.  ");
            }

            StudyOverallStatus bo = StudyOverallStatusConverter.convertFromDtoToDomain(dto);
            if (StudyStatusCode.WITHDRAWN.equals(bo.getStatusCode())
               || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.equals(bo.getStatusCode())
               || StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.equals(bo.getStatusCode())
               || StudyStatusCode.ADMINISTRATIVELY_COMPLETE.equals(bo.getStatusCode())) {
                if ((bo.getCommentText() == null) || (bo.getCommentText().length() < 1)) {
                    serviceError("A reason must be entered when the study status is set to "
                             + bo.getStatusCode().getCode() + ".  ");
                }
            } else {
                bo.setCommentText(null);
            }

            // update
            session.saveOrUpdate(bo);
            StudyRecruitmentStatus srs = StudyRecruitmentStatusServiceBean.create(bo);
            if (srs != null) {
                session.saveOrUpdate(StudyRecruitmentStatusServiceBean.create(bo));
            }
            session.flush();
            resultDto = StudyOverallStatusConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyOverallStatus ", hbe);
        }
        return resultDto;
    }

    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List.
     * @throws PAException Exception.
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyOverallStatusDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            String errMsg = " Ii should not be null ";
            LOG.error(errMsg);
            throw new PAException(errMsg);
        }
        LOG.info("Entering getStudyOverallStatusByStudyProtocol");

        Session session = null;
        List<StudyOverallStatus> queryList = new ArrayList<StudyOverallStatus>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select sos "
                       + "from StudyOverallStatus sos "
                       + "join sos.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by sos.id ";
            LOG.info(" query StudyOverallStatus = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in getStudyProtocolByCriteria.  ", hbe);
        }
        ArrayList<StudyOverallStatusDTO> resultList = new ArrayList<StudyOverallStatusDTO>();
        for (StudyOverallStatus bo : queryList) {
            resultList.add(StudyOverallStatusConverter.convertFromDomainToDTO(bo));
        }

        LOG.info("Leaving getStudyOverallStatusByStudyProtocol, returning " + resultList.size() + " object(s).");
        return resultList;
    }

    /**
     * @param studyProtocolIi Primary key assigned to a StudyProtocl.
     * @return List Current status StudyOverllStatusDTO.
     * @throws PAException Exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyOverallStatusDTO> getCurrentByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        List<StudyOverallStatusDTO> sosList = this.getByStudyProtocol(studyProtocolIi);
        List<StudyOverallStatusDTO> resultList = new ArrayList<StudyOverallStatusDTO>();
        if (!sosList.isEmpty()) {
            resultList.add(sosList.get(sosList.size() - 1));
        }
        return resultList;
    }
}
