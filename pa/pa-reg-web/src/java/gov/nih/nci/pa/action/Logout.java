package gov.nih.nci.pa.action;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
/**
 * @author Harsha
 * @since 09/12/2008
 * copyright NCI 2007.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
public class Logout {
    /**
     * {@inheritDoc}
     */
    public String logout() {
        if (ServletActionContext.getRequest().getSession() != null) {
            ServletActionContext.getRequest().getSession().invalidate();
        }
        return Action.SUCCESS;
    }
}
