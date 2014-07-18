/**
 * 
 */
package gov.nih.nci.pa.webservices.converters;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.webservices.types.CompleteTrialRegistration;

import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class StudySiteDTOBuilder {

    /**
     * @param reg
     *            CompleteTrialRegistration
     * @return StudySiteDTO
     * @throws PAException  PAException
     */
    public StudySiteDTO buildClinicalTrialsGovIdAssigner(
            CompleteTrialRegistration reg) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        if (StringUtils.isNotEmpty(reg.getClinicalTrialsDotGovTrialID())) {
            String poOrgId = PaRegistry.getOrganizationCorrelationService()
                    .getPOOrgIdentifierByIdentifierType(
                            PAConstants.NCT_IDENTIFIER_TYPE);
            Ii nctROIi = PaRegistry.getOrganizationCorrelationService()
                    .getPoResearchOrganizationByEntityIdentifier(
                            IiConverter.convertToPoOrganizationIi(String
                                    .valueOf(poOrgId)));
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter.convertToSt(reg
                    .getClinicalTrialsDotGovTrialID()));
        }
        return isoDto;
    }

}
