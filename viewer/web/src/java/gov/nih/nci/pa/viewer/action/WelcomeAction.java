package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.util.Constants;

import org.apache.struts2.ServletActionContext;




/**
 * @author Hugh Reinhart
 * @since 4/14/2009
 */
public class WelcomeAction extends AbstractViewerAction {
    private static final long serialVersionUID = -8671171197398815729L;

    /**
     * @throws PAException exception
     * @return action result
     */
    @Override
    public String execute() throws PAException {
        if (ServletActionContext.getRequest().isUserInRole(Constants.ROLE_CTRO)) {
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.SESSION_ATTR_ROLE, Constants.ROLE_CTRO);
            return "ctroWelcome";
        }
        if (ServletActionContext.getRequest().isUserInRole(Constants.ROLE_PUBLIC)) {
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.SESSION_ATTR_ROLE, Constants.ROLE_PUBLIC);
            return "publicWelcome";
        }
        throw new PAException("User configured improperly.  Use UPT to assign user to a valid group "
                + "for this application.");
    }
}
