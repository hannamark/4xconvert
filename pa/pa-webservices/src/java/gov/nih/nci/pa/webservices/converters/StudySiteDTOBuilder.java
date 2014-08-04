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
        final String type = PAConstants.NCT_IDENTIFIER_TYPE;
        return buildIdAssignerSite(clinicalTrialsDotGovTrialID, type);
    }

    /**
     * @param ctepID
     *            ctepID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildCtepIdAssigner(String ctepID) throws PAException {
        final String type = PAConstants.CTEP_IDENTIFIER_TYPE;
        return buildIdAssignerSite(ctepID, type);
    }

    /**
     * @param dcpID
     *            dcpID
     * @return StudySiteDTO
     * @throws PAException
     *             PAException
     */
    public StudySiteDTO buildDcpIdAssigner(String dcpID) throws PAException {
        final String type = PAConstants.DCP_IDENTIFIER_TYPE;
        return buildIdAssignerSite(dcpID, type);
    }

    /**
     * @param value
     * @param type
     * @return
     * @throws PAException
     */
    private StudySiteDTO buildIdAssignerSite(String value, final String type)
            throws PAException {
        StudySiteDTO isoDto = new StudySiteDTO();
        if (StringUtils.isNotEmpty(value)) {
            String poOrgId = PaRegistry.getOrganizationCorrelationService()
                    .getPOOrgIdentifierByIdentifierType(type);
            Ii nctROIi = PaRegistry.getOrganizationCorrelationService()
                    .getPoResearchOrganizationByEntityIdentifier(
                            IiConverter.convertToPoOrganizationIi(String
                                    .valueOf(poOrgId)));
            isoDto.setResearchOrganizationIi(nctROIi);
            isoDto.setLocalStudyProtocolIdentifier(StConverter
                    .convertToSt(value));
        }
        return isoDto;
    }

}
