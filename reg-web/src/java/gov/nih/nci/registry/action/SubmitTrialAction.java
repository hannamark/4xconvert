package gov.nih.nci.registry.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.registry.dto.InterventionalStudyProtocolWebDTO;
import gov.nih.nci.registry.util.Constants;

/**
 * 
 * @author Bala Nair
 * 
 */
@Validation
public class SubmitTrialAction extends ActionSupport {
    private static final String VIEW_TRIAL = "view";
    private static final Logger LOG  = Logger.getLogger(SubmitTrialAction.class);
    /**
     * create protocol.
     * @return String
     */
    public String create() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = new InterventionalStudyProtocolDTO();
            String phaseCode = ServletActionContext.getRequest().getParameter("trialPhase");
            String officialTitle = ServletActionContext.getRequest().getParameter("trialTitle");
            protocolDTO.setPhaseCode(CdConverter.convertToCd(PhaseCode.getByCode(phaseCode)));
            protocolDTO.setOfficialTitle(StConverter.convertToSt(officialTitle));
            Ii isoIdentifier = RegistryServiceLocator.getStudyProtocolService()
                                    .createInterventionalStudyProtocol(protocolDTO);
            LOG.info("Trial is registered with ID: " + IiConverter.convertToString(isoIdentifier));
            ServletActionContext.getRequest().getSession().setAttribute(
                                        Constants.STUDY_PROTOCOL_II, IiConverter.convertToString(isoIdentifier));
            query();
        } catch (Exception e) {
            LOG.error("Exception occured while submitting trial");
        }
        return VIEW_TRIAL;
    }
    
    /**
     * query the created protocol.
     * @return String
     */
    public String query() {
        try {
            InterventionalStudyProtocolDTO protocolDTO = RegistryServiceLocator.getStudyProtocolService().
                                                             getInterventionalStudyProtocol(IiConverter.convertToIi(
                                                               (String) ServletActionContext.getRequest().getSession().
            
                                                               getAttribute(Constants.STUDY_PROTOCOL_II)));
            InterventionalStudyProtocolWebDTO protovolWebDTO =
                                                          new InterventionalStudyProtocolWebDTO(protocolDTO);
            // put an entry in the session and store InterventionalStudyProtocolDTO 
            ServletActionContext.getRequest().getSession().setAttribute(
                                    Constants.TRIAL_SUMMARY, protovolWebDTO);
            LOG.info("Trial retrieved: " + StConverter.convertToString(protocolDTO.getOfficialTitle()));
        } catch (Exception e) {
            LOG.error("Exception occured while querying trial");
        }
        return VIEW_TRIAL;
    }
}
