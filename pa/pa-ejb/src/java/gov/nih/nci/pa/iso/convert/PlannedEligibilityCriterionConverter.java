package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.domain.PlannedActivity;
import gov.nih.nci.pa.domain.PlannedEligibilityCriterion;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
*
* @author Kalpana Guthikonda
* @since 11/10/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
public class PlannedEligibilityCriterionConverter extends PlannedActivityConverter {
    /**
    *
    * @param pec PlannedEligibilityCriterion
    * @return PlannedEligibilityCriterionDTO PlannedEligibilityCriterionDTO
    */
   public static PlannedEligibilityCriterionDTO convertFromDomainToDTO(PlannedEligibilityCriterion pec) {
       PlannedEligibilityCriterionDTO pecDTO = (PlannedEligibilityCriterionDTO) 
               PlannedActivityConverter.convertFromDomainToDTO(
                       (PlannedActivity) pec, (PlannedActivityDTO) new PlannedEligibilityCriterionDTO());
       pecDTO.setCriterionName(StConverter.convertToSt(pec.getCriterionName()));
       pecDTO.setInclusionIndicator(BlConverter.convertToBl(Boolean.valueOf(pec.getInclusionIndicator())));
       pecDTO.setOperator(StConverter.convertToSt(pec.getOperator()));
       pecDTO.setEligibleGenderCode(CdConverter.convertToCd(pec.getEligibleGenderCode()));
       Pq pq = new Pq();       
       pq.setValue(pec.getValue()); 
       if (pec.getUnit() != null) {
         pq.setUnit(pec.getUnit());
       }
       pecDTO.setValue(pq);
       //pecDTO.setValue(PqvConverter.convertToPqv(pec.getValue()));
       //pecDTO.setUnit(CdConverter.convertToCd(pec.getUnit()));
       return pecDTO;
   }

   /**
    *
    * @param pecDTO InterventionalStudyProtocolDTO
    * @return InterventionalStudyProtocol InterventionalStudyProtocol
    */
   public static PlannedEligibilityCriterion convertFromDTOToDomain(PlannedEligibilityCriterionDTO pecDTO) {
       PlannedEligibilityCriterion pec =  (PlannedEligibilityCriterion) PlannedActivityConverter.convertFromDTOToDomain(
                   (PlannedActivityDTO) pecDTO , (PlannedActivity) new PlannedEligibilityCriterion());
       pec.setCriterionName(StConverter.convertToString(pecDTO.getCriterionName()));
       pec.setInclusionIndicator(BlConverter.covertToBoolean(pecDTO.getInclusionIndicator()));
       pec.setOperator(StConverter.convertToString(pecDTO.getOperator()));
       pec.setEligibleGenderCode(EligibleGenderCode.getByCode(
               CdConverter.convertCdToString(pecDTO.getEligibleGenderCode())));
       //pec.setAgeValue(StConverter.convertToString(pecDTO.getAgeValue()));
       //pec.setUnit(UnitsCode.getByCode(CdConverter.convertCdToString(pecDTO.getUnit())));
       if (pecDTO.getValue() != null) {
         pec.setValue(pecDTO.getValue().getValue());
         pec.setUnit(pecDTO.getValue().getUnit());
       }
       return pec;
   }
}
