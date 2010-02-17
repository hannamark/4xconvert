/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.TempStudyFunding;
import gov.nih.nci.pa.domain.TempStudyProtocol;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.iso.dto.TempStudyFundingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

/**
 * @author Vrushali
 *
 */
public class TempStudyFundingConverter {

    /**
    *
    * @param tempStudyFunding  tempStudyFunding
    * @return tempStudyFundingDTO
    */
   public static TempStudyFundingDTO convertFromDomainToDTO(TempStudyFunding tempStudyFunding) {
       return convertFromDomainToDTO(tempStudyFunding, new TempStudyFundingDTO());
   }
   
 /**
   *
   * @param tempStudyFundingDTO tempStudyFundingDTO
   * @return StudyProtocol StudyProtocol
   */
  public static TempStudyFunding convertFromDTOToDomain(TempStudyFundingDTO tempStudyFundingDTO) {
      return convertFromDTOToDomain(tempStudyFundingDTO , new TempStudyFunding());
  }

  private static TempStudyFundingDTO convertFromDomainToDTO(
          TempStudyFunding tempStudyFunding,
          TempStudyFundingDTO tempStudyFundingDTO) {
     tempStudyFundingDTO.setFundingMechanismCode(CdConverter.convertStringToCd(
             tempStudyFunding.getFundingMechanismCode()));
     tempStudyFundingDTO.setNciDivisionProgramCode(CdConverter.convertToCd(
             tempStudyFunding.getNciDivisionProgramCode()));
     tempStudyFundingDTO.setNihInstitutionCode(CdConverter.convertStringToCd(tempStudyFunding.getNihInstituteCode()));
     tempStudyFundingDTO.setSerialNumber(StConverter.convertToSt(tempStudyFunding.getSerialNumber()));
     tempStudyFundingDTO.setTempStudyProtocolIi(IiConverter.convertToIi(
             tempStudyFunding.getTempStudyProtocol().getId()));
      return tempStudyFundingDTO;
  }

  private static TempStudyFunding convertFromDTOToDomain(
        TempStudyFundingDTO tempStudyFundingDTO,
        TempStudyFunding tempStudyFunding) {
    
        tempStudyFunding.setFundingMechanismCode(CdConverter.convertCdToString(
                tempStudyFundingDTO.getFundingMechanismCode()));
        tempStudyFunding.setNciDivisionProgramCode(NciDivisionProgramCode.getByCode(CdConverter.convertCdToString(
                tempStudyFundingDTO.getNciDivisionProgramCode())));
        tempStudyFunding.setNihInstituteCode(CdConverter.convertCdToString(
                tempStudyFundingDTO.getNihInstitutionCode()));
        tempStudyFunding.setSerialNumber(StConverter.convertToString(tempStudyFundingDTO.getSerialNumber()));
        TempStudyProtocol tempSpBo = new TempStudyProtocol();
        tempSpBo.setId(IiConverter.convertToLong(tempStudyFundingDTO.getTempStudyProtocolIi()));
        tempStudyFunding.setTempStudyProtocol(tempSpBo);
        return tempStudyFunding;
  }
}
