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
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.Converters;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.convert.PlannedEligibilityCriterionConverter;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Hugh Reinhart
 * @since 10/29/2008 copyright NCI 2008. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings("PMD.CyclomaticComplexity")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Interceptors(HibernateSessionInterceptor.class)
public class PlannedActivityServiceBean
 extends AbstractStudyIsoService<PlannedActivityDTO, PlannedActivity, PlannedActivityConverter>
        implements PlannedActivityServiceRemote , PlannedActivityServiceLocal {

    @EJB
    InterventionServiceRemote interventionSrv;
    
    private void businessRules(PlannedActivityDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("PlannedActivity.studyProtocol must be set.");
        }
        if (PAUtil.isCdNull(dto.getCategoryCode())) {
            throw new PAException("PlannedActivity.categoryCode must be set.");
        }
        ActivityCategoryCode cc = ActivityCategoryCode.getByCode(CdConverter.convertCdToString(dto.getCategoryCode()));
        if (ActivityCategoryCode.INTERVENTION.equals(cc)) {
            boolean isDrug = ActivitySubcategoryCode.DRUG.getCode()
                    .equals(CdConverter.convertCdToString(dto.getSubcategoryCode()));

            if (PAUtil.isCdNull(dto.getSubcategoryCode())) {
                throw new PAException("Intervention type must be set.");
            }
            if (PAUtil.isIiNull(dto.getInterventionIdentifier())) {
                throw new PAException("An Intervention must be selected.");
            }
            if (!isDrug && (dto.getLeadProductIndicator() != null)) {
                getLogger().info("Setting lead product indicator to null for non-drug PlannedActivity.");
                dto.setLeadProductIndicator(null);
            }
            if (checkDuplicate(dto)) {
                throw new PAException("Redundancy error:  this trial already includes the selected intervention. ");
            }
            if (isDrug) {
                drugBusinessRules(dto);
            }
        }
    }
    
    private boolean checkDuplicate(PlannedActivityDTO dto) throws PAException {
        boolean duplicate = false;
        InterventionDTO iDto = interventionSrv.get(dto.getInterventionIdentifier());
        String interventionName = iDto.getName().getValue();
       
        List<PlannedActivityDTO> paList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
         for (PlannedActivityDTO padto : paList) {
          if (!PAUtil.isIiNull(padto.getInterventionIdentifier())) {
               InterventionDTO interDto = interventionSrv.get(padto.getInterventionIdentifier());
               String interName = interDto.getName().getValue();
               if (interName.equals(interventionName) 
                  && padto.getSubcategoryCode().getCode().equals(dto.getSubcategoryCode().getCode()) 
                  && ((padto.getTextDescription().getValue() == null && dto.getTextDescription().getValue() == null)
                    || (padto.getTextDescription().getValue() != null && dto.getTextDescription().getValue() != null
                        && padto.getTextDescription().getValue().equals(dto.getTextDescription().getValue())))
                  && ((PAUtil.isBlNull(dto.getLeadProductIndicator()) 
                          && PAUtil.isBlNull(padto.getLeadProductIndicator()))
                       || (!PAUtil.isBlNull(dto.getLeadProductIndicator()) 
                           && !PAUtil.isBlNull(padto.getLeadProductIndicator()) 
                           && padto.getLeadProductIndicator().getValue()
                                   .equals(dto.getLeadProductIndicator().getValue())))) {
                       duplicate = true;
                   }
                }
            }    
          return duplicate;
    }

    private void drugBusinessRules(PlannedActivityDTO dto) throws PAException {
        if (dto.getLeadProductIndicator() == null) {
            getLogger().info("Generating Bl (false) for non-drug PlannedActivity.");
            dto.setLeadProductIndicator(BlConverter.convertToBl(false));

        }
        // only one lead drug per study
        if (BlConverter.covertToBoolean(dto.getLeadProductIndicator())) {
            Long dtoId = IiConverter.convertToLong(dto.getIdentifier());
            boolean dtoIsNew = (dtoId == null);
            List<PlannedActivityDTO> paList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
            for (PlannedActivityDTO pa : paList) {
                boolean paIsLead = (null == BlConverter.covertToBoolean(pa.getLeadProductIndicator()))
                        ? false : BlConverter.covertToBoolean(pa.getLeadProductIndicator());
                if ((!PAUtil.isIiNull(pa.getInterventionIdentifier()))
                        && (dtoIsNew || !dtoId.equals(IiConverter.convertToLong(pa.getIdentifier())))
                        && paIsLead) {
                    getLogger().warn("It should throw error");
                    throw new PAException("Only one drug may be marked as lead for a given study.");
                }
            }
        }
    }

    /**
     * @param dto planned activity to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.create(dto);
    }

    /**
     * @param dto planned activity to update
     * @return the created planned activity
     * @throws PAException exception.
     */
    @Override
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        businessRules(dto);
        return super.update(dto);
    }

    /**
     * @param ii index of arm
     * @return list of planned activities associated w/arm
     * @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }
        getLogger().info("Entering getByArm.  ");

        Session session = null;
        List<PlannedActivity> queryList = new ArrayList<PlannedActivity>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedActivity pa "
                       + "join pa.arms a "
                       + "where a.id = :armId "
                       + "order by pa.id ";
            getLogger().info("query PlannedActivity = " + hql + ".  ");

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("armId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in getByArm.  ", hbe);
        }
        ArrayList<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
        for (PlannedActivity bo : queryList) {
            resultList.add(Converters.get(PlannedActivityConverter.class)
                    .convertFromDomainToDto(bo));
        }
        getLogger().info("Leaving getByArm, returning " + resultList.size() + " object(s).  ");
        return resultList;
    }
    /**
     *  @param ii study protocol index
     *  @return list of PlannedEligibilityCriterion
     *  @throws PAException exception
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(Ii ii)
            throws PAException {
        if (PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; found null.  ");
        }

        Session session = null;
        List<PlannedEligibilityCriterion> queryList = new ArrayList<PlannedEligibilityCriterion>();
        try {
            session = HibernateUtil.getCurrentSession();
            Query query = null;

            // step 1: form the hql
            String hql = "select pa "
                       + "from PlannedEligibilityCriterion pa "
                       + "join pa.studyProtocol sp "
                       + "where sp.id = :studyProtocolId "
                       + "order by pa.id ";

            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(ii));

            // step 3: query the result
            queryList = query.list();
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in getByStudyProtocol.  ", hbe);
        }
        ArrayList<PlannedEligibilityCriterionDTO> resultList = new ArrayList<PlannedEligibilityCriterionDTO>();
        for (PlannedEligibilityCriterion bo : queryList) {
            resultList.add(PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo));
        }
        return resultList;
    }

    /**
     * @param ii index
     * @return the PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; found null.  ");
        }
        PlannedEligibilityCriterionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
                    , IiConverter.convertToLong(ii));
            if (bo == null) {
                throw new PAException("Object not found using get() for id = "
                        + IiConverter.convertToString(ii) + ".  ");
            }
            resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in get().", hbe);
        }
        return resultDto;
    }
    /**
     * @param dto PlannedEligibilityCriterion to create
     * @return the created PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.  ");
        }
        if (PAUtil.isIiNull(dto.getStudyProtocolIdentifier())) {
            throw new PAException("StudyProtocol must be set.  ");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param dto PlannedEligibilityCriterion to update
     * @return the updated PlannedEligibilityCriterion
     * @throws PAException exception.
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        if (PAUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Create method should be used to modify existing.  ");
        }
        return createOrUpdatePlannedEligibilityCriterion(dto);
    }

    /**
     * @param ii index
     * @throws PAException exception.
     */
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
        if ((ii == null) || PAUtil.isIiNull(ii)) {
            throw new PAException("Check the Ii value; null found.  ");
        }
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            PlannedEligibilityCriterion bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class
                    , IiConverter.convertToLong(ii));
            session.delete(bo);
            session.flush();
        }  catch (HibernateException hbe) {
            throw new PAException(" Hibernate exception while deleting ii = "
                + IiConverter.convertToString(ii) + ".  ", hbe);
        }
    }
    private PlannedEligibilityCriterionDTO createOrUpdatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        PlannedEligibilityCriterion bo = null;
        PlannedEligibilityCriterionDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            if (PAUtil.isIiNull(dto.getIdentifier())) {
                bo = PlannedEligibilityCriterionConverter.convertFromDTOToDomain(dto);
            } else {
                bo = (PlannedEligibilityCriterion) session.get(PlannedEligibilityCriterion.class,
                        IiConverter.convertToLong(dto.getIdentifier()));

                PlannedEligibilityCriterion delta = PlannedEligibilityCriterionConverter.convertFromDTOToDomain(dto);
                bo.setCriterionName(delta.getCriterionName());
                bo.setInclusionIndicator(delta.getInclusionIndicator());
                bo.setOperator(delta.getOperator());
                bo.setCategoryCode(delta.getCategoryCode());
                bo.setEligibleGenderCode(delta.getEligibleGenderCode());
                bo.setValue(delta.getValue());
                bo.setUnit(delta.getUnit());
                bo.setTextDescription(delta.getTextDescription());
                bo.setUserLastUpdated(delta.getUserLastCreated());
            }
            bo.setDateLastUpdated(new Date());
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = PlannedEligibilityCriterionConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            throw new PAException("Hibernate exception in createOrUpdate().  ", hbe);
        }
        return resultDto;
    }

    /**
     * copies the study protocol record from source to target.
     * @param fromStudyProtocolIi source
     * @param toStudyProtocolIi target
     * @throws PAException exception.
     */
    public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi , Ii toStudyProtocolIi)
    throws PAException {
        List<PlannedEligibilityCriterionDTO> dtos = getPlannedEligibilityCriterionByStudyProtocol(fromStudyProtocolIi);
        for (PlannedEligibilityCriterionDTO dto : dtos) {
            dto.setIdentifier(null);
            dto.setStudyProtocolIdentifier(toStudyProtocolIi);
            createPlannedEligibilityCriterion(dto);
        }
    }


}
