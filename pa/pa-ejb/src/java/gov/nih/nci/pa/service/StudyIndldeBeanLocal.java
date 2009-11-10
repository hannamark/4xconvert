/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.NciDivisionProgramCode;
import gov.nih.nci.pa.enums.NihInstituteCode;
import gov.nih.nci.pa.iso.convert.StudyIndldeConverter;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.PAUtil;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

/**
 * @author asharma
 *
 */
@Stateless
@Interceptors({ HibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength", "PMD.NPathComplexity" })

 public class StudyIndldeBeanLocal extends
  AbstractStudyIsoService<StudyIndldeDTO, StudyIndlde, StudyIndldeConverter> implements StudyIndldeServiceLocal {
   private static final int IND_FIELD_COUNT = 5;
   private static final String VALIDATION_EXCEPTION = "Validation Exception ";
   
   /**
    * @param dto StudyIndldeDTO
    * @return StudyIndldeDTO
    * @throws PAException PAException
    */
    @Override
    public StudyIndldeDTO create(StudyIndldeDTO dto) throws PAException {
      validate(dto);
      return super.create(dto);
    }

    /**
     * @param dto StudyIndldeDTO
     * @return StudyIndldeDTO
     * @throws PAException PAException
     */
    @Override
    public StudyIndldeDTO update(StudyIndldeDTO dto) throws PAException {
      validate(dto);
      return super.update(dto);
    }


    @SuppressWarnings({"PMD" })
    private void enforceNoDuplicate(StudyIndldeDTO dto) throws PAException {
      String newType = dto.getIndldeTypeCode().getCode();
      String newNumber = dto.getIndldeNumber().getValue();
      String newGrantor = dto.getGrantorCode().getCode();
      List<StudyIndldeDTO> spList = getByStudyProtocol(dto.getStudyProtocolIdentifier());
       for (StudyIndldeDTO sp : spList) {
         boolean sameType = newType.equals(sp.getIndldeTypeCode().getCode());
         boolean sameNumber = newNumber.equals(sp.getIndldeNumber().getValue());
         boolean sameGrantor = newGrantor.equals(sp.getGrantorCode().getCode());
          if (sameType && sameNumber && sameGrantor) {
            if (dto.getIdentifier() == null
                || (!dto.getIdentifier().getExtension().equals(sp.getIdentifier().getExtension()))) {
                 throw new PADuplicateException("Duplicates Ind/Ide are not allowed.");
            }
          }
       }
    }
    
     /**
      * @param studyIndldeDTO dto
      * @throws PAException e
      */
      public void validate(StudyIndldeDTO studyIndldeDTO) throws PAException {
        StringBuffer errorMsg = new StringBuffer();
        errorMsg.append(validateIndIdeObject(studyIndldeDTO));
        if (errorMsg.length() > 0) {
          throw new PAException(VALIDATION_EXCEPTION + errorMsg.toString());
        }
        if (PAUtil.isIiNotNull(studyIndldeDTO.getStudyProtocolIdentifier())) {
          enforceNoDuplicate(studyIndldeDTO);    
        }
      }

     /**
      * @param studyIndldeDTO
      * @return errorMsg
      */
      @SuppressWarnings({"PMD.ExcessiveMethodLength", "PMD.NPathComplexity" })
      private String validateIndIdeObject(StudyIndldeDTO studyIndldeDTO) {
        StringBuffer errorMsg = new StringBuffer();
        if (!isIndIdeContainsAllInfo(studyIndldeDTO)) {
          errorMsg.append("All IND/IDE values are required.\n");
        } else {
        if (!PAUtil.isBlNull(studyIndldeDTO.getExpandedAccessIndicator())  
            && BlConverter.covertToBool(studyIndldeDTO.getExpandedAccessIndicator()) 
            && PAUtil.isCdNull(studyIndldeDTO.getExpandedAccessStatusCode())) { 
           errorMsg.append("Expanded Access Status value is required.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getHolderTypeCode()) 
            && HolderTypeCode.NIH.getCode().equalsIgnoreCase(studyIndldeDTO.getHolderTypeCode().getCode())
            && PAUtil.isCdNull(studyIndldeDTO.getNihInstHolderCode())) {
             errorMsg.append("NIH Institution value is required.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getHolderTypeCode()) 
            && HolderTypeCode.NCI.getCode().equalsIgnoreCase(studyIndldeDTO.getHolderTypeCode().getCode())
            && PAUtil.isCdNull(studyIndldeDTO.getNciDivProgHolderCode())) {
             errorMsg.append("NCI Division/Program Code value is required.\n");
        }         
       }
        //Validate List of values
        if (!PAUtil.isCdNull(studyIndldeDTO.getIndldeTypeCode()) 
            && null == IndldeTypeCode.getByCode(studyIndldeDTO.getIndldeTypeCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getHolderTypeCode()) 
            && null == HolderTypeCode.getByCode(studyIndldeDTO.getHolderTypeCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE Holder Type.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getGrantorCode()) 
             && null == GrantorCode.getByCode(studyIndldeDTO.getGrantorCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE Grantor.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getIndldeTypeCode()) 
            && IndldeTypeCode.IDE.getCode().equals(studyIndldeDTO.getIndldeTypeCode().getCode())
            && !PAUtil.isCdNull(studyIndldeDTO.getGrantorCode()) 
            && !GrantorCode.CDRH.getCode().equals(studyIndldeDTO.getGrantorCode().getCode())) {
              errorMsg.append("IDE Grantor can have only CDRH value.\n");
        }
        if (!PAUtil.isCdNull(studyIndldeDTO.getExpandedAccessStatusCode()) 
            && null == ExpandedAccessStatusCode.getByCode(studyIndldeDTO.getExpandedAccessStatusCode().getCode())) {
              errorMsg.append("Please enter valid value for IND/IDE Expanded Access Status.\n");
        }
        //validate NIH Institution values
        if (!PAUtil.isCdNull(studyIndldeDTO.getHolderTypeCode()) 
            && studyIndldeDTO.getHolderTypeCode().getCode().equalsIgnoreCase(HolderTypeCode.NIH.getCode())
            && !PAUtil.isCdNull(studyIndldeDTO.getNihInstHolderCode()) 
            && null == NihInstituteCode.getByCode(studyIndldeDTO.getNihInstHolderCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE NIH Institution.\n");
        }
        //validate NCI Division values
        if (!PAUtil.isCdNull(studyIndldeDTO.getHolderTypeCode()) 
            && studyIndldeDTO.getHolderTypeCode().getCode().equalsIgnoreCase(HolderTypeCode.NCI.getCode())
            && !PAUtil.isCdNull(studyIndldeDTO.getNciDivProgHolderCode()) 
            && null == NciDivisionProgramCode.getByCode(studyIndldeDTO.getNciDivProgHolderCode().getCode())) {
             errorMsg.append("Please enter valid value for IND/IDE NCI Division /Program.\n");
        }
         return errorMsg.toString();
      }
      
      private boolean isIndIdeContainsAllInfo(StudyIndldeDTO dto) {
        int nullCount = 0;
        if (PAUtil.isCdNull(dto.getIndldeTypeCode())) {
          nullCount += 1;
        }
        if (PAUtil.isStNull(dto.getIndldeNumber())) {
          nullCount += 1;
        }
        if (PAUtil.isCdNull(dto.getGrantorCode())) {
          nullCount += 1;
        }
        if (PAUtil.isCdNull(dto.getHolderTypeCode())) {
          nullCount += 1;
        }
        if (PAUtil.isBlNull(dto.getExpandedAccessIndicator())) {
          nullCount += 1;
        }
        if (nullCount == 0) {
          return true;
        }
        if (nullCount == IND_FIELD_COUNT) {
          return true;
        }
        return false;
     }

}
