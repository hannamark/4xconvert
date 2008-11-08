package gov.nih.nci.pa.iso.convert;

import gov.nih.nci.pa.domain.StudyIndlde;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.enums.ExpandedAccessStatusCode;
import gov.nih.nci.pa.enums.GrantorCode;
import gov.nih.nci.pa.enums.HolderTypeCode;
import gov.nih.nci.pa.enums.IndldeTypeCode;
import gov.nih.nci.pa.enums.ProgramCodesForNCI;
import gov.nih.nci.pa.enums.ProgramCodesForNIH;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.util.Date;

/**
 * 
 * @author Kalpana Guthikonda
 * @since 10/31/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI. 
 */
@SuppressWarnings({  "PMD.NPathComplexity" , "PMD.CyclomaticComplexity" })
public class StudyIndldeConverter {
    /**
     * @param si StudyIndlde
     * @return StudyIndldeDTO
     */
    public static StudyIndldeDTO convertFromDomainToDTO(StudyIndlde si) {
        StudyIndldeDTO siDTO = new StudyIndldeDTO();
        siDTO.setIdentifier(IiConverter.convertToIi(si.getId()));
        siDTO.setStudyProtocolIi(IiConverter.convertToIi(si.getStudyProtocol().getId()));
        siDTO.setExpandedAccessStatusCode(CdConverter.convertToCd(si.getExpandedAccessStatusCode()));
        siDTO.setExpandedAccessIndicator(BlConverter.convertToBl(si.getExpandedAccessIndicator()));
        siDTO.setGrantorCode(CdConverter.convertToCd(si.getGrantorCode()));
        siDTO.setNihInstHolderCode(CdConverter.convertToCd(si.getNihInstHolderCode()));
        siDTO.setNciDivProgHolderCode(CdConverter.convertToCd(si.getNciDivProgHolderCode()));
        siDTO.setHolderTypeCode(CdConverter.convertToCd(si.getHolderTypeCode()));
        siDTO.setIndldeNumber(StConverter.convertToSt(si.getIndldeNumber()));
        siDTO.setIndldeTypeCode(CdConverter.convertToCd(si.getIndldeTypeCode()));
        return siDTO;
    }


    /**
     * @param siDTO StudyIndldeDTO
     * @return StudyIndlde
     */
    public static StudyIndlde convertFromDTOToDomain(StudyIndldeDTO siDTO) {
        StudyIndlde si = new StudyIndlde();

        StudyProtocol spBo = new StudyProtocol();
        spBo.setId(IiConverter.convertToLong(siDTO.getStudyProtocolIi()));
        si.setDateLastUpdated(new Date());
        si.setStudyProtocol(spBo);
        if (siDTO.getIdentifier() != null) {
            si.setId(IiConverter.convertToLong(siDTO.getIdentifier()));
        }
        if (siDTO.getUserLastUpdated() != null) {
            si.setUserLastUpdated(siDTO.getUserLastUpdated().getValue());
        }
        if (siDTO.getExpandedAccessStatusCode() != null) {
            si.setExpandedAccessStatusCode(
                    ExpandedAccessStatusCode.getByCode(siDTO.getExpandedAccessStatusCode().getCode()));
        }
        if (siDTO.getExpandedAccessIndicator() != null) {
            si.setExpandedAccessIndicator(siDTO.getExpandedAccessIndicator().getValue());
        }
        if (siDTO.getGrantorCode() != null) {
            si.setGrantorCode(GrantorCode.getByCode(siDTO.getGrantorCode().getCode()));
        }
        if (siDTO.getHolderTypeCode() != null && siDTO.getHolderTypeCode().getCode().equals("NIH")) {
            si.setNihInstHolderCode(ProgramCodesForNIH.getByCode(siDTO.getNihInstHolderCode().getCode()));
        }
        if (siDTO.getHolderTypeCode() != null && siDTO.getHolderTypeCode().getCode().equals("NCI")) {
            si.setNciDivProgHolderCode(ProgramCodesForNCI.getByCode(siDTO.getNciDivProgHolderCode().getCode()));
        }
        if (siDTO.getHolderTypeCode() != null) {
            si.setHolderTypeCode(HolderTypeCode.getByCode(siDTO.getHolderTypeCode().getCode()));
        }
        if (siDTO.getIndldeNumber() != null) {
            si.setIndldeNumber(siDTO.getIndldeNumber().getValue());
        }
        if (siDTO.getIndldeTypeCode() != null) {
            si.setIndldeTypeCode(IndldeTypeCode.getByCode(siDTO.getIndldeTypeCode().getCode()));
        }
        return si;
    }
}
