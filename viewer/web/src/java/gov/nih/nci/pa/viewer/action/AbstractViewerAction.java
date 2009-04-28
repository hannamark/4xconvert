package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.viewer.util.Constants;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @author Hugh Reinhart
 * @since 4/16/2009
 */
public abstract class AbstractViewerAction extends ActionSupport {
    private static final long serialVersionUID = -5423491292515161915L;

    /**
     * @return the role from the session
     */
    protected String getUserRole() {
        return (String) ServletActionContext.getRequest().getSession().getAttribute(Constants.SESSION_ATTR_ROLE);
    }

    /**
     * Default implementation throws derived exception.
     *
     * @throws PAException exception
     * @return action result
     */
    @Override
    public String execute() throws PAException {
        return "success";
    }

}
