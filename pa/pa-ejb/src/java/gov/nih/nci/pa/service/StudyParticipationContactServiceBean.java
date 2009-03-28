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
import gov.nih.nci.pa.domain.StudyParticipationContact;
import gov.nih.nci.pa.enums.StudyParticipationContactRoleCode;
import gov.nih.nci.pa.iso.convert.StudyParticipationContactConverter;
import gov.nih.nci.pa.iso.dto.StudyParticipationContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
 * @since 09/30/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.ExcessiveMethodLength" , "PMD.CyclomaticComplexity" })
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class StudyParticipationContactServiceBean extends AbstractBasePaService<StudyParticipationContactDTO> implements
        StudyParticipationContactServiceRemote , StudyParticipationContactServiceLocal {
    private static final Logger LOG = Logger.getLogger(StudyParticipationContactServiceBean.class);

    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() {
        return LOG;
    }

    /**
     * @param dto dto
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO create(StudyParticipationContactDTO dto) throws PAException {
//        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
//            serviceError(" Update method should be used to modify existing. ");
//        }
        StudyParticipationContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipationContact bo = StudyParticipationContactConverter.convertFromDtoToDomain(dto);
            bo.setDateLastCreated(new Timestamp((new Date()).getTime()));
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyParticipationContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyParticipation ", hbe);
        }
        return resultDto;
    }

    /**
     * @param ii index
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO get(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
        }
        StudyParticipationContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyParticipationContact bo = (StudyParticipationContact) session.get(StudyParticipationContact.class,
                    IiConverter.convertToLong(ii));
            if (bo == null) {
                serviceError("Object not found using get() for id = " + IiConverter.convertToString(ii));
            }
            resultDto = StudyParticipationContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }

    /**
     * @param dto dto
     * @exception PAException exception
     * @return dto
     */
    @Override
    public StudyParticipationContactDTO update(StudyParticipationContactDTO dto) throws PAException {
        // enforce business rules
        if (dto == null) {
            this.serviceError("dto should not be null.");
        }
        Session session = null;
        StudyParticipationContactDTO resultDto = null;
        try {
            session = HibernateUtil.getCurrentSession();
            StudyParticipationContact sc = (StudyParticipationContact) session.load(StudyParticipationContact.class,
                Long.valueOf(dto.getIdentifier().getExtension()));         
            sc = StudyParticipationContactConverter.convertFromDtoToDomain(dto , sc);
            sc.setDateLastUpdated(new Timestamp((new Date()).getTime()));
            session.update(sc);
            session.flush();
            resultDto = StudyParticipationContactConverter.convertFromDomainToDTO(sc);
          } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in updateStudyContact ", hbe);
          }
          
       return resultDto;   
        
    }

    /**
     * @param ii index of StudyParticipationContact to be deleted.
     * @throws PAException exception
     */
    public void delete(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering delete().");
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyParticipationContact bo = (StudyParticipationContact) session.get(StudyParticipationContact.class,
                    IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception while deleting " + "StudyParticipation for pid = " + ii.getExtension(),
                    hbe);
        }
        LOG.info("Leaving delete().");
    }

    /**
     * @param studyParticipationIi id of protocol
     * @return list StudyParticipationContactDTO
     * @throws PAException on error
     */
    public List<StudyParticipationContactDTO> getByStudyParticipation(Ii studyParticipationIi) throws PAException {
        if ((studyParticipationIi == null) || PAUtil.isIiNull(studyParticipationIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getByStudyParticipation");
        Session session = null;
        List<StudyParticipationContact> queryList = new ArrayList<StudyParticipationContact>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;
            // step 1: form the hql
            String hql = "select spartcontact " + "from StudyParticipationContact spartcontact "
                    + "join spartcontact.studyParticipation spart where spart.id = :studyPartId "
                    + "order by spartcontact.id ";
            LOG.info(" query StudyParticipationContact = " + hql);
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyPartId", IiConverter.convertToLong(studyParticipationIi));
            queryList = query.list();
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving " + "StudyParticipation for pid = "
                    + studyParticipationIi.getExtension(), hbe);
        }
        List<StudyParticipationContactDTO> resultList = new ArrayList<StudyParticipationContactDTO>();
        for (StudyParticipationContact sp : queryList) {
            resultList.add(StudyParticipationContactConverter.convertFromDomainToDTO(sp));
        }
        LOG.info("Leaving getByStudyParticipation");
        return resultList;
    }
    
    /**
     * Get list of StudyParticipations for a given protocol having
     * a given functional code.
     * @param studyProtocolIi id of protocol
     * @param scDTO StudyContactDTO with the functional code criteria
     * @return list StudyContactDTO
     * @throws PAException on error
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , StudyParticipationContactDTO scDTO) throws PAException {
        List <StudyParticipationContactDTO> scDtoList = new ArrayList<StudyParticipationContactDTO>();
        scDtoList.add(scDTO);
        return getByStudyProtocol(studyProtocolIi, scDtoList);
    }

    /**
     * Get list of StudyParticipations for a given protocol having
     * functional codes from a list.
     * @param studyProtocolIi id of protocol
     * @param scDTOList List containing desired functional codes
     * @return list StudyParticipationDTO
     * @throws PAException on error
     */
    public List<StudyParticipationContactDTO> getByStudyProtocol(
            Ii studyProtocolIi , List<StudyParticipationContactDTO> scDTOList) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError("Ii is null ");
        }
        if ((scDTOList == null) || (scDTOList.isEmpty())) {
            getLogger().info("Using method getByStudyProtocol(Ii).  ");
            //return getByStudyProtocol(studyProtocolIi);
        }
        getLogger().info("Entering getByStudyProtocol(Ii, List<DTO>).  ");
        StringBuffer criteria = new StringBuffer();
        Session session = null;
        List<StudyParticipationContact> queryList = new ArrayList<StudyParticipationContact>();
        try {
            session = HibernateUtil.getCurrentSession();
            StringBuffer hql = new StringBuffer("select spart "
                               + "from StudyParticipationContact spart "
                               + "join spart.studyProtocol spro "
                               + "where spro.id = :studyProtocolId ");
            boolean first = true;
            for (StudyParticipationContactDTO crit : scDTOList) {
                if (first) {
                    hql.append("and ( ");
                    first = false;
                } else {
                    criteria.append("or ");
                }
                criteria.append("spart.roleCode = '"
                    + StudyParticipationContactRoleCode.getByCode(crit.getRoleCode().getCode()) + "' ");
            }
            hql.append(criteria);
            hql.append(") order by spart.id ");
            getLogger().info(" query StudyContact = " + hql);

            Query query = session.createQuery(hql.toString());
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();

        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyParticipation for pid = " + studyProtocolIi.getExtension() , hbe);
        }

        List<StudyParticipationContactDTO> resultList = new ArrayList<StudyParticipationContactDTO>();
        for (StudyParticipationContact sc : queryList) {
            resultList.add(StudyParticipationContactConverter.convertFromDomainToDTO(sc));
        }
        getLogger().info("Leaving getByStudyProtocol() for (" + criteria + ").  ");
        getLogger().info("Returning " + resultList.size() + " object(s).  ");
        return resultList;
    }

    
}
