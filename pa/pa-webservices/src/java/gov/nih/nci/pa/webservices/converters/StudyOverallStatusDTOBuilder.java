/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;

/**
 * @author dkrylov
 * 
 */
public class StudyOverallStatusDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudyOverallStatusDTO
     */
    public StudyOverallStatusDTO build(CompleteTrialRegistration reg) {
        StudyOverallStatusDTO isoDto = new StudyOverallStatusDTO();
        isoDto.setStatusCode(CdConverter.convertToCd(StudyStatusCode
                .getByCode(reg.getTrialStatus().value())));
        isoDto.setReasonText(StConverter.convertToSt(reg.getWhyStopped()));
        isoDto.setStatusDate(TsConverter.convertToTs(reg.getTrialStatusDate()
                .toGregorianCalendar().getTime()));
        return isoDto;
    }

}
