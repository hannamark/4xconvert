/**
 * 
 */
package gov.nih.nci.pa.iso.convert;

import java.util.Date;

import gov.nih.nci.pa.domain.TempStudyIndIde;
import gov.nih.nci.pa.domain.TempStudyProtocol;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.dto.TempStudyIndIdeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.ISOUtil;


/**
 * @author Vrushali
 *
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.NPathComplexity" })
public class TempStudyIndIdeConverter {
    /**
    *
    * @param tempStudyIndIde tempStudyIndIde
    * @return tempStudyIndIdeDTO
    */
   public static TempStudyIndIdeDTO convertFromDomainToDTO(TempStudyIndIde tempStudyIndIde) {
       return convertFromDomainToDTO(tempStudyIndIde, new TempStudyIndIdeDTO());
   }
   /**
   *
   * @param tempStudyIndIdeDTO tempStudyIndIdeDTO
   * @return tempStudyIndIde tempStudyIndIde
   */
  public static TempStudyIndIde convertFromDTOToDomain(TempStudyIndIdeDTO tempStudyIndIdeDTO) {
      return convertFromDTOToDomain(tempStudyIndIdeDTO , new TempStudyIndIde());
  }

  private static TempStudyIndIdeDTO convertFromDomainToDTO(
          TempStudyIndIde tempStudyIndIde, TempStudyIndIdeDTO tempStudyIndIdeDTO) {
      tempStudyIndIdeDTO.setIdentifier(IiConverter.convertToStudyIndIdeIi(tempStudyIndIde.getId()));
      tempStudyIndIdeDTO.setTempStudyProtocolIi(IiConverter.convertToIi(
              tempStudyIndIde.getTempStudyProtocol().getId()));
      tempStudyIndIdeDTO.setExpandedAccessStatusCode(CdConverter.convertToCd(
              tempStudyIndIde.getExpandedAccessStatusCode()));
      tempStudyIndIdeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(
              tempStudyIndIde.getExpandedAccessIndicator()));
      tempStudyIndIdeDTO.setGrantorCode(CdConverter.convertToCd(tempStudyIndIde.getGrantorCode()));
      tempStudyIndIdeDTO.setNihInstHolderCode(CdConverter.convertToCd(tempStudyIndIde.getNihInstHolderCode()));
      tempStudyIndIdeDTO.setNciDivProgHolderCode(CdConverter.convertToCd(
              tempStudyIndIde.getNciDivPrgHolderCode()));
      tempStudyIndIdeDTO.setHolderTypeCode(CdConverter.convertToCd(tempStudyIndIde.getHolderTypeCode()));
      tempStudyIndIdeDTO.setIndldeNumber(StConverter.convertToSt(tempStudyIndIde.getIndIdeNumber()));
      tempStudyIndIdeDTO.setIndldeTypeCode(CdConverter.convertToCd(tempStudyIndIde.getIndldeTypeCode()));
      return tempStudyIndIdeDTO;
  }
  private static TempStudyIndIde convertFromDTOToDomain(
          TempStudyIndIdeDTO tempStudyIndIdeDTO, TempStudyIndIde tempStudyIndIde) {
      TempStudyProtocol tempSpBo = new TempStudyProtocol();
      tempSpBo.setId(IiConverter.convertToLong(tempStudyIndIdeDTO.getTempStudyProtocolIi()));
      tempStudyIndIde.setDateLastUpdated(new Date());
      tempStudyIndIde.setTempStudyProtocol(tempSpBo);
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getExpandedAccessStatusCode())) {
          tempStudyIndIde.setExpandedAccessStatusCode(
                  ExpandedAccessStatusCode.getByCode(tempStudyIndIdeDTO.getExpandedAccessStatusCode().getCode()));
      }
      if (!ISOUtil.isBlNull(tempStudyIndIdeDTO.getExpandedAccessIndicator())) {
          tempStudyIndIde.setExpandedAccessIndicator(tempStudyIndIdeDTO.getExpandedAccessIndicator().getValue());
      }
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getGrantorCode())) {
          tempStudyIndIde.setGrantorCode(GrantorCode.getByCode(tempStudyIndIdeDTO.getGrantorCode().getCode()));
      }
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getHolderTypeCode()) 
              && tempStudyIndIdeDTO.getHolderTypeCode().getCode().equals("NIH")) {
          tempStudyIndIde.setNihInstHolderCode(NihInstituteCode.getByCode(
                  tempStudyIndIdeDTO.getNihInstHolderCode().getCode()));
      }
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getHolderTypeCode()) 
              && tempStudyIndIdeDTO.getHolderTypeCode().getCode().equals("NCI")) {
          tempStudyIndIde.setNciDivPrgHolderCode(NciDivisionProgramCode.getByCode(
                  tempStudyIndIdeDTO.getNciDivProgHolderCode().getCode()));
      }
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getHolderTypeCode())) {
          tempStudyIndIde.setHolderTypeCode(HolderTypeCode.getByCode(tempStudyIndIdeDTO.getHolderTypeCode().getCode()));
      }
      if (!ISOUtil.isStNull(tempStudyIndIdeDTO.getIndldeNumber())) {
          tempStudyIndIde.setIndIdeNumber(tempStudyIndIdeDTO.getIndldeNumber().getValue());
      }
      if (!ISOUtil.isCdNull(tempStudyIndIdeDTO.getIndldeTypeCode())) {
          tempStudyIndIde.setIndldeTypeCode(IndldeTypeCode.getByCode(tempStudyIndIdeDTO.getIndldeTypeCode().getCode()));
      }
      return tempStudyIndIde;
  }

}
