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
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.domain.StudyResourcing;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.convert.StudyResourcingConverter;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Naveen Amiruddin
 * @since 09/11/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({  "PMD.ExcessiveMethodLength" , "PMD.AvoidDuplicateLiterals",
  "PMD.CyclomaticComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class StudyResourcingServiceBean
            implements StudyResourcingServiceRemote, StudyResourcingServiceLocal {

    private static final Logger LOG  = Logger.getLogger(StudyResourcingServiceBean.class);

    private SessionContext ejbContext;

    @Resource
    void setSessionContext(SessionContext ctx) {
    this.ejbContext = ctx;
    }

    /**
     * @param studyProtocolIi Ii
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getsummary4ReportedResource(Ii studyProtocolIi) throws PAException {

        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        LOG.info("Entering getsummary4ReportedResource");
        StudyResourcingDTO studyResourcingDTO = null;
        Session session = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
                       + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query studyResourcing = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();

            if (queryList.size() > 1) {
                session.flush();
                LOG.error(" Summary 4 Reported Sourcing should not be more than 1 record ");
                throw new PAException(" Summary 4 Reported Sourcing should not be more than 1 record ");

            }
        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getsummary4ReportedResource" , hbe);
            throw new PAException(" Hibernate exception while retrieving getsummary4ReportedResource "  , hbe);
        }

        if (!queryList.isEmpty()) {
            studyResourcing = queryList.get(0);
            studyResourcingDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);

        }
        session.flush();
        LOG.info("Leaving getsummary4ReportedResource");
        return studyResourcingDTO;
    }

    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    public StudyResourcingDTO updateStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering updateStudyResourcing ");
        Session session = null;
        StudyResourcing studyResourcing = null;
        StudyResourcingDTO studyResourcingRetDTO = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();

        if (studyResourcingDTO.getSerialNumber() != null
            && studyResourcingDTO.getSerialNumber().getValue() != null) {
          String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
          if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
            throw new PAException("Serial number can be numeric with 5 or 6 digits");
          }
          if (!isNumeric(snValue)) {
              throw new PAException("Serial number should have numbers from [0-9]");
          }
        }
        if (!PAUtil.isBlNull(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                && BlConverter.covertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                    .equals(Boolean.FALSE)) {
                enforceNoDuplicate(studyResourcingDTO);
        }

        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            studyResourcing = queryList.get(0);
            // set the values from paramter
            studyResourcing.setTypeCode(SummaryFourFundingCategoryCode.getByCode(
                    studyResourcingDTO.getTypeCode().getCode()));
            studyResourcing.setOrganizationIdentifier(IiConverter.convertToString(
                    studyResourcingDTO.getOrganizationIdentifier()));
            studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            if (ejbContext != null) {
                studyResourcing.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            studyResourcing.setFundingMechanismCode(CdConverter.convertCdToString(
                    studyResourcingDTO.getFundingMechanismCode()));
            studyResourcing.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(
                        studyResourcingDTO.getNciDivisionProgramCode().getCode()));
            studyResourcing.setNihInstituteCode(studyResourcingDTO.getNihInstitutionCode().getCode());
            studyResourcing.setSerialNumber(StConverter.convertToString(studyResourcingDTO.getSerialNumber()));
            session.update(studyResourcing);
            session.flush();
            studyResourcingRetDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);

        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getsummary4ReportedResource" , hbe);
            throw new PAException(" Hibernate exception while retrieving getsummary4ReportedResource "  , hbe);
        }
        LOG.debug("Leaving updateStudyResourcing ");
        return studyResourcingRetDTO;
    }

    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyProtocolDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("PMD.NPathComplexity")
    public StudyResourcingDTO createStudyResourcing(StudyResourcingDTO studyResourcingDTO) throws PAException {
        final int serialNumMin = 5;
        final int serialNumMax = 6;
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        if (!PAUtil.isBlNull(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                && BlConverter.covertToBoolean(studyResourcingDTO.getSummary4ReportedResourceIndicator())
                    .equals(Boolean.FALSE)) {
                enforceNoDuplicate(studyResourcingDTO);
        }
        
        LOG.debug("Entering createStudyResourcing ");
        Session session = null;

        if (studyResourcingDTO.getSerialNumber() != null) {
          String snValue = studyResourcingDTO.getSerialNumber().getValue().toString();
          if (snValue.length() < serialNumMin || snValue.length() > serialNumMax) {
            throw new PAException("Serial number can be numeric with 5 or 6 digits");
          }
          if (!isNumeric(snValue)) {
              throw new PAException("Serial number should have numbers from [0-9]");
          }
        }
        StudyResourcing studyResourcing = StudyResourcingConverter.convertFromDTOToDomain(studyResourcingDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        studyResourcing.setDateLastCreated(now);
        if (ejbContext != null) {
            studyResourcing.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
        }
        // create Protocol Obj
        StudyProtocol studyProtocol = new StudyProtocol();
        studyProtocol.setId(IiConverter.convertToLong(studyResourcingDTO.getStudyProtocolIdentifier()));


        studyResourcing.setStudyProtocol(studyProtocol);
        studyResourcing.setActiveIndicator(true);
        try {
            session = HibernateUtil.getCurrentSession();
            session.save(studyResourcing);
            session.flush();
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while createStudyResourcing " , hbe);
            throw new PAException(" Hibernate exception while createStudyResourcing " , hbe);
        }
        LOG.debug("Leaving createStudyResourcing ");
        return StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);
    }

    /**
     * @param studyProtocolIi Ii
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<StudyResourcingDTO> getstudyResourceByStudyProtocol(Ii studyProtocolIi)
            throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        LOG.info("Entering getstudyResourceByStudyProtocol");
        Session session = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " join sr.studyProtocol sp "
                       + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
                       + " and sr.summary4ReportedResourceIndicator =  '" + Boolean.FALSE + "'"
                       + " and sr.activeIndicator =  '" + Boolean.TRUE + "'";

           LOG.info(" query getstudyResourceByStudyProtocol = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();


        }  catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving getstudyResourceByStudyProtocol" , hbe);
            throw new PAException(" Hibernate exception while retrieving getstudyResourceByStudyProtocol "  , hbe);
        }

        ArrayList<StudyResourcingDTO> resultList = new ArrayList<StudyResourcingDTO>();
        for (StudyResourcing bo : queryList) {
            resultList.add(StudyResourcingConverter.convertFromDomainToDTO(bo));
        }
        session.flush();
        LOG.info("Leaving getstudyResourceByStudyProtocol");
        return resultList;
    }
    /**
     * @param studyResourceIi Ii
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public StudyResourcingDTO getStudyResourceByID(Ii studyResourceIi)
            throws PAException {
        if (PAUtil.isIiNull(studyResourceIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        LOG.info("Entering getStudyResourceByID");
        Session session = null;
        StudyResourcingDTO studyResourcingDTO = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourceIi);

           LOG.info(" query getStudyResourceByID = " + hql);

            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();


        }  catch (HibernateException hbe) {
            LOG.error(" Hibernate exception while retrieving getStudyResourceByID" , hbe);
            session.flush();
            throw new PAException(" Hibernate exception while retrieving getStudyResourceByID "  , hbe);
        }

        if (!queryList.isEmpty()) {
            studyResourcing = queryList.get(0);
            studyResourcingDTO = StudyResourcingConverter.convertFromDomainToDTO(studyResourcing);

        }
        session.flush();
        LOG.info("Leaving getStudyResourceByID");
        return studyResourcingDTO;
    }
    /**
     *
     * @param studyResourcingDTO StudyResourcingDTO
     * @return StudyResourcingDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    public Boolean deleteStudyResourceByID(StudyResourcingDTO studyResourcingDTO) throws PAException {
        if (studyResourcingDTO == null) {
            LOG.error(" studyResourcingDTO should not be null ");
            throw new PAException(" studyResourcingDTO should not be null ");
        }
        LOG.debug("Entering deleteStudyResourceByID ");
        Boolean result = false;
        Session session = null;
        StudyResourcing studyResourcing = null;
        List<StudyResourcing> queryList = new ArrayList<StudyResourcing>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;

            // step 1: form the hql
            String hql = " select sr "
                       + " from StudyResourcing sr "
                       + " where sr.id = " + IiConverter.convertToLong(studyResourcingDTO.getIdentifier());
            // step 2: construct query object
            query = session.createQuery(hql);
            queryList = query.list();
            studyResourcing = queryList.get(0);
            // set the values from paramter
            studyResourcing.setActiveIndicator(false);
            studyResourcing.setInactiveCommentText(StConverter.convertToString(
                    studyResourcingDTO.getInactiveCommentText()));
            studyResourcing.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
            if (ejbContext != null) {
                studyResourcing.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
            }
            session.update(studyResourcing);
            session.flush();
            result = true;
        } catch (HibernateException hbe) {
            session.flush();
            LOG.error(" Hibernate exception while retrieving deleteStudyResourceByID" , hbe);
            throw new PAException(" Hibernate exception while retrieving deleteStudyResourceByID "  , hbe);
        }
        LOG.debug("Leaving deleteStudyResourceByID ");
        return result;
    }
    

    @SuppressWarnings({"PMD" })
        private void enforceNoDuplicate(StudyResourcingDTO dto) throws PAException {
          String newSerialNumber = dto.getSerialNumber().getValue();
          String newFundingMech = dto.getFundingMechanismCode().getCode();
          String newNciDivCode = dto.getNciDivisionProgramCode().getCode();
          String newNihInstCode = dto.getNihInstitutionCode().getCode();
          List<StudyResourcingDTO> spList = getstudyResourceByStudyProtocol(dto.getStudyProtocolIdentifier());
          for (StudyResourcingDTO sp : spList) {
              boolean sameSerialNumber = newSerialNumber.equals(sp.getSerialNumber().getValue());
              boolean sameFundingMech = newFundingMech.equals(sp.getFundingMechanismCode().getCode());
              boolean sameNciDivCode = newNciDivCode.equals(sp.getNciDivisionProgramCode().getCode());
              boolean sameNihInstCode = newNihInstCode.equals(sp.getNihInstitutionCode().getCode());
              
              if (sameSerialNumber && sameFundingMech && sameNciDivCode && sameNihInstCode) {
                  if (dto.getIdentifier() == null
                      || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension()))) {
                    throw new PADuplicateException("Duplicate Grants are not allowed.");
                  }
              }
          }
      }
    
    @SuppressWarnings({"PMD" })
    private boolean isNumeric(String number) {
        boolean isValid = false;   
        //Initialize reg ex for numeric data.
        String expression = "^[0-9]*[0-9]+$";
        CharSequence inputStr = number;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
      }
    /**
     * @param studyResourcingDTO dto
     * @return 
     * @throws PAException e
     */
    public void validate(StudyResourcingDTO studyResourcingDTO)
            throws PAException {
        enforceNoDuplicate(studyResourcingDTO);
    }
    
}
