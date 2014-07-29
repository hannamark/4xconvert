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

import org.apache.commons.lang.StringUtils;

/**
 * @author dkrylov
 * 
 */
public class StudySiteDTOBuilder {

    /**
     * @param clinicalTrialsDotGovTrialID
     *            clinicalTrialsDotGovTrialID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildClinicalTrialsGovIdAssigner(
            String clinicalTrialsDotGovTrialID) throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        if (StringUtils.isNotEmpty(clinicalTrialsDotGovTrialID)) {
            String poOrgId = PaRegistry.getOrganizationCorrelationService()
                    .getPOOrgIdentifierByIdentifierType(
                            PAConstants.NCT_IDENTIFIER_TYPE);
            Ii nctROIi = PaRegistry.getOrganizationCorrelationService()
                    .getPoResearchOrganizationByEntityIdentifier(
                            IiConverter.convertToPoOrganizationIi(String
                                    .valueOf(poOrgId)));
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(clinicalTrialsDotGovTrialID));
        }
        return isoDto;
    }

}
