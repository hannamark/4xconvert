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
package gov.nih.nci.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ActivitySubcategoryCode;
import gov.nih.nci.pa.iso.convert.PlannedActivityConverter;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class MockPlannedActivityService.
 * 
 * @author hreinhart
 */
public class MockPlannedActivityService implements PlannedActivityServiceRemote {

    /** The list. */
    public static List<PlannedActivity> list;
    
    /** The converter. */
    static PlannedActivityConverter converter = new PlannedActivityConverter();
    
    /** The seq. */
    private static Long seq = 1L;
    
    static {
        list = new ArrayList<PlannedActivity>();
        PlannedActivity pa = new PlannedActivity();
        pa.setId(seq++);
        pa.setCategoryCode(ActivityCategoryCode.INTERVENTION);
        pa.setIntervention(MockInterventionService.list.get(0));
        pa.setLeadProductIndicator(true);
        pa.setStudyProtocol(MockStudyProtocolService.list.get(0));
        list.add(pa);
        pa = new PlannedActivity();
        pa.setId(seq++);
        pa.setCategoryCode(ActivityCategoryCode.ELIGIBILITY_CRITERION);
        pa.setStudyProtocol(MockStudyProtocolService.list.get(0));
        list.add(pa);
    }

    /**
     * Creates the planned eligibility criterion.
     * 
     * @param dto the dto
     * 
     * @return the planned eligibility criterion dto
     * 
     * @throws PAException the PA exception
     */
    public PlannedEligibilityCriterionDTO createPlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Delete planned eligibility criterion.
     * 
     * @param ii the ii
     * 
     * @throws PAException the PA exception
     */
    public void deletePlannedEligibilityCriterion(Ii ii) throws PAException {
        // TODO Auto-generated method stub

    }

    /**
     * Gets the by arm.
     * 
     * @param ii the ii
     * 
     * @return the by arm
     * 
     * @throws PAException the PA exception
     */
    public List<PlannedActivityDTO> getByArm(Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the planned eligibility criterion.
     * 
     * @param ii the ii
     * 
     * @return the planned eligibility criterion
     * 
     * @throws PAException the PA exception
     */
    public PlannedEligibilityCriterionDTO getPlannedEligibilityCriterion(Ii ii)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the planned eligibility criterion by study protocol.
     * 
     * @param ii the ii
     * 
     * @return the planned eligibility criterion by study protocol
     * 
     * @throws PAException the PA exception
     */
    public List<PlannedEligibilityCriterionDTO> getPlannedEligibilityCriterionByStudyProtocol(
            Ii ii) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Update planned eligibility criterion.
     * 
     * @param dto the dto
     * 
     * @return the planned eligibility criterion dto
     * 
     * @throws PAException the PA exception
     */
    public PlannedEligibilityCriterionDTO updatePlannedEligibilityCriterion(
            PlannedEligibilityCriterionDTO dto) throws PAException {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the by study protocol.
     * 
     * @param ii the ii
     * 
     * @return the by study protocol
     * 
     * @throws PAException the PA exception
     */
    public List<PlannedActivityDTO> getByStudyProtocol(Ii ii) throws PAException {
        List<PlannedActivityDTO> resultList = new ArrayList<PlannedActivityDTO>();
        for (PlannedActivity item : list) {
            if (item.getStudyProtocol().getId().equals(IiConverter.convertToLong(ii))) {
                resultList.add(converter.convertFromDomainToDto(item));
            }
        }
        return resultList;
    }
   
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#getCurrentByStudyProtocol(gov.nih.nci.coppa.iso.Ii)
     */
    public PlannedActivityDTO getCurrentByStudyProtocol(Ii studyProtocolIi) throws PAException {
        List<PlannedActivityDTO> dtoList = this.getByStudyProtocol(studyProtocolIi);
        PlannedActivityDTO result = null;
        if (!dtoList.isEmpty()) {
            result = dtoList.get(dtoList.size() - 1);
        }
        return result;
    }

    /**
     * Creates the.
     * 
     * @param dto the dto
     * 
     * @return the planned activity dto
     * 
     * @throws PAException the PA exception
     */
    public PlannedActivityDTO create(PlannedActivityDTO dto) throws PAException {
        PlannedActivity bo = converter.convertFromDtoToDomain(dto);
        if (bo.getCategoryCode().getCode().equals(ActivityCategoryCode.INTERVENTION.getCode())
                && bo.getIntervention() == null) {
            throw new PAException("Intervention type planned activities should have an associate intervention.");
        }
        bo.setId(seq++);
        list.add(bo);
        return converter.convertFromDomainToDto(bo);
    }

    /**
     * Delete.
     * 
     * @param ii the ii
     * 
     * @throws PAException the PA exception
     */
    public void delete(Ii ii) throws PAException {
        for (PlannedActivity item : list) {
            if (item.getId().equals(IiConverter.convertToLong(ii))) {
                list.remove(item);
                return;
            }
        }
        throw new PAException("Planned activity not found in mock service for id = " + IiConverter.convertToString(ii));
    }

    /**
     * Gets the.
     * 
     * @param ii the ii
     * 
     * @return the planned activity dto
     * 
     * @throws PAException the PA exception
     */
    public PlannedActivityDTO get(Ii ii) throws PAException {
        for (PlannedActivity item : list) {
            if (item.getId().equals(IiConverter.convertToLong(ii))) {
                return converter.convertFromDomainToDto(item);
            }
        }
        throw new PAException("Planned activity not found in mock service for id = " + IiConverter.convertToString(ii));
     }

    /**
     * Update.
     * 
     * @param dto the dto
     * 
     * @return the planned activity dto
     * 
     * @throws PAException the PA exception
     */
    public PlannedActivityDTO update(PlannedActivityDTO dto) throws PAException {
        for (PlannedActivity item : list) {
            if (item.getId().equals(IiConverter.convertToLong(dto.getIdentifier()))) {
                item.setLeadProductIndicator(BlConverter.covertToBoolean(dto.getLeadProductIndicator()));
                item.setSubcategoryCode(ActivitySubcategoryCode.getByCode(CdConverter.convertCdToString(dto.getSubcategoryCode())));
                item.setTextDescription(StConverter.convertToString(dto.getTextDescription()));
                return converter.convertFromDomainToDto(item);
            }
        }
        throw new PAException("Planned activity not found in mock service for id = " + IiConverter.convertToString(dto.getIdentifier()));
    }

    /**
     * copies the study protocol record from source to target.
     * 
     * @param fromStudyProtocolIi source
     * @param toStudyProtocolIi target
     * 
     * @throws PAException exception.
     */
    public void copyPlannedEligibilityStudyCriterions(Ii fromStudyProtocolIi , Ii toStudyProtocolIi) 
    throws PAException {
     // TODO Auto-generated method stub
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.StudyPaService#copy(gov.nih.nci.coppa.iso.Ii, gov.nih.nci.coppa.iso.Ii)
     */
    public Map<Ii, Ii> copy(Ii fromStudyProtocolii, Ii toStudyProtocolIi)
            throws PAException {
        // TODO Auto-generated method stub
        return null;
        
    }
    
}
