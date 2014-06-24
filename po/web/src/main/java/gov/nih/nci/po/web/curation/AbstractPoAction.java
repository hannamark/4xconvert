package gov.nih.nci.po.web.curation;

import gov.nih.nci.po.web.util.POUtils;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action class for the entities.
 * @author Rohit Gupta
 */
public class AbstractPoAction extends ActionSupport {
    private static final long serialVersionUID = 1L;

    /**
     * This method is used to return the logged-in user.
     * 
     * @return User
     * @throws CSException
     *             CSException
     */
    public User getLoggedInUser() throws CSException {
        return POUtils.getLoggedInUser();
    }
}
