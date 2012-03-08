package gov.nih.nci.registry.action;

import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * Base class for proprietary trial management action classes.
 * 
 * @author Denis G. Krylov
 * @see
 * 
 */
public class AbstractBaseProprietaryTrialAction extends ManageFileAction
        implements ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = 3244347854526316932L;
    /**
     * Trial DTO.
     */
    private ProprietaryTrialDTO trialDTO;

    // CHECKSTYLE:ON
    private HttpServletResponse servletResponse;

    private String trialAction;

    /**
     * Gets the trial dto.
     * 
     * @return the trialDTO
     */
    public final ProprietaryTrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * Sets the trial dto.
     * 
     * @param trialDTO
     *            the trialDTO to set
     */
    public final void setTrialDTO(ProprietaryTrialDTO trialDTO) {
        this.trialDTO = trialDTO;
    }

    /**
     * @return the servletResponse
     */
    public final HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param servletResponse
     *            the servletResponse to set
     */
    public final void setServletResponse(HttpServletResponse servletResponse) {
        this.servletResponse = servletResponse;
    }

    /**
     * Gets the trial action.
     * 
     * @return the trialAction
     */
    public final String getTrialAction() {
        return trialAction;
    }

    /**
     * Sets the trial action.
     * 
     * @param trialAction
     *            the trialAction to set
     */
    public final void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

    /**
     * checkSummary4Funding.
     */
    protected void checkSummary4Funding() {
        if (!StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("summary4FundingSponsor",
                    "Select the Summary 4 Funding Sponsor");
        }
        if (StringUtils.isEmpty(trialDTO.getSummaryFourFundingCategoryCode())
                && !StringUtils.isEmpty(trialDTO.getSummaryFourOrgIdentifier())) {
            addFieldError("trialDTO.summaryFourFundingCategoryCode",
                    "Select the Trial Submission Category");
        }
    }

    /**
     * checkNctAndDoc.
     * 
     * @param session
     *            HttpSession
     */
    protected void checkNctAndDoc(HttpSession session) {
        if (StringUtils.isEmpty(trialDTO.getNctIdentifier())
                && StringUtils.isEmpty(getProtocolDocFileName())
                && session.getAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT
                        .getShortName()) == null) {
            addFieldError("trialDTO.nctIdentifier",
                    "Provide either NCT Number or Protocol Trial Template.\n");
            addFieldError("trialDTO.protocolDocFileName",
                    "Provide either NCT Number or Protocol Trial Template.\n");
        }
    }

}