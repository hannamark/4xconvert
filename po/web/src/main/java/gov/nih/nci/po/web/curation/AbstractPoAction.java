package gov.nih.nci.po.web.curation;

import gov.nih.nci.security.SecurityServiceProvider;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.security.exceptions.CSException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Base Action class for the entities.
 */
public class AbstractPoAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    /**
     * This method is used to return 'CreatedBy' user.
     * 
     * @return User
     * @throws CSException
     *             CSException
     */
    public User getCreatedBy() throws CSException {
        return SecurityServiceProvider.getUserProvisioningManager("po")
                .getUser(ServletActionContext.getRequest().getRemoteUser());
    }
}
