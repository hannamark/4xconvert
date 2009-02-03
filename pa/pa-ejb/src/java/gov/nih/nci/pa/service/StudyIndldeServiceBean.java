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
import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.iso.convert.StudyIndldeConverter;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyIndldeServiceBean extends AbstractStudyPaService<StudyIndldeDTO>
implements StudyIndldeServiceRemote, StudyIndldeServiceLocal {

    private static final Logger LOG  = Logger.getLogger(StudyIndldeServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param ii Index
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Check the Ii value; null found ");
        }
        StudyIndldeDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyIndlde bo = (StudyIndlde) session.get(StudyIndlde.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyIndldeConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO create(
            StudyIndldeDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        StudyIndldeDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyIndlde bo = StudyIndldeConverter.convertFromDTOToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyIndldeConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyIndlde ", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO update(StudyIndldeDTO dto)
            throws PAException {
        if (dto == null) {
            serviceError("StudyIndldeDTO should not be null ");
        }

        Session session = null;
        StudyIndldeDTO resultDto = null;
        List<StudyIndlde> queryList = new ArrayList<StudyIndlde>();

        try {
            session = HibernateUtil.getCurrentSession();
            String hql = "select si "
                       + "from StudyIndlde si "
                       + "where si.id =  " + Long.valueOf(dto.getIdentifier().getExtension());
            LOG.info(" query StudyIndlde = " + hql);

            Query query = session.createQuery(hql);
            queryList = query.list();

            StudyIndlde sp = queryList.get(0);
            StudyIndlde spNew = StudyIndldeConverter.convertFromDTOToDomain(dto);

            sp.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            sp.setUserLastUpdated(spNew.getUserLastUpdated());
            sp.setExpandedAccessStatusCode(spNew.getExpandedAccessStatusCode());
            sp.setExpandedAccessIndicator(spNew.getExpandedAccessIndicator());
            sp.setGrantorCode(spNew.getGrantorCode());
            sp.setNihInstHolderCode(spNew.getNihInstHolderCode());
            sp.setNciDivProgHolderCode(spNew.getNciDivProgHolderCode());
            sp.setHolderTypeCode(spNew.getHolderTypeCode());
            sp.setIndldeNumber(spNew.getIndldeNumber());
            sp.setIndldeTypeCode(spNew.getIndldeTypeCode());
            session.update(sp);
            session.flush();
            resultDto =  StudyIndldeConverter.convertFromDomainToDTO(sp);
        }  catch (HibernateException hbe) {
            serviceError("Hibernate exception while updating StudyIndlde for id = "
                    + IiConverter.convertToString(dto.getIdentifier()) + ".  ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii Index of StudyIndlde object
     * @throws PAException PAException
     */
    @Override
    public void delete(Ii ii)
            throws PAException {
        LOG.info("Entering delete");
        
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError("Ii has null value ");
        }
        LOG.info("Entering delete().");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyIndlde bo = (StudyIndlde) session.get(StudyIndlde.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting "
                + "StudyIndlde for pid = " + ii.getExtension(), hbe);
        }
        LOG.info("Leaving delete().");
    }


    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    @Override
    public List<StudyIndldeDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getStudyIndldeByStudyProtocol");
        Session session = null;
        List<StudyIndlde> queryList = new ArrayList<StudyIndlde>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = "select s "
                       + "from StudyIndlde s "
                       + "join s.studyProtocol spro "
                       + "where spro.id = :studyProtocolId "
                       + " order by s.id ";
            
            LOG.info(" query StudyIndlde = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyIndlde for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyIndldeDTO> resultList = new ArrayList<StudyIndldeDTO>();
        for (StudyIndlde sp : queryList) {
            resultList.add(StudyIndldeConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }
}
