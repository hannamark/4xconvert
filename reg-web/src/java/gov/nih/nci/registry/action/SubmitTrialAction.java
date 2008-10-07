package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;

/**
 * 
 * @author Bala Nair
 * 
 */
@Validation
public class SubmitTrialAction extends ActionSupport {
    /**
     * create protocol.
     */
    public void create() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = new InterventionalStudyProtocolDTO();
            String phaseCode = ServletActionContext.getRequest().getParameter("trialPhase");
            String officialTitle = ServletActionContext.getRequest().getParameter("trialTitle");
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(phaseCode)));
            protocolDTO.setOfficialTitle(StConverter.convertToSt(officialTitle));
            Ii isoIdentifier = RegistryServiceLocator.getStudyProtocolService()
                                    .createInterventionalStudyProtocol(protocolDTO);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(isoIdentifier));
        } catch (Exception e) {
            LOG.error("Exception occured while submitting trial");
        }
    }
}
