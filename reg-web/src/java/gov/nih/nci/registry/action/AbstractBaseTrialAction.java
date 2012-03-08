/**
 * 
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.registry.dto.TrialDTO;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * Base class for non-proprietary trial management actions.
 * 
 * @author Denis G. Krylov
 * 
 */
abstract class AbstractBaseTrialAction extends ManageFileAction implements
        ServletResponseAware {

    /**
     * 
     */
    private static final long serialVersionUID = 4836330822566103985L;

    private TrialDTO trialDTO;

    private HttpServletResponse servletResponse;

    private String trialAction;

    /**
     * @return the trialDTO
     */
    public final TrialDTO getTrialDTO() {
        return trialDTO;
    }

    /**
     * @param trialDTO
     *            the trialDTO to set
     */
    public final void setTrialDTO(TrialDTO trialDTO) {
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
     * @return the trialAction
     */
    public final String getTrialAction() {
        return trialAction;
    }

    /**
     * @param trialAction
     *            the trialAction to set
     */
    public final void setTrialAction(String trialAction) {
        this.trialAction = trialAction;
    }

}
