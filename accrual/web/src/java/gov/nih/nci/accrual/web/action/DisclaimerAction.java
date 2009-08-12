package gov.nih.nci.accrual.web.action;

import org.apache.struts2.ServletActionContext;

/**
 * @author rajubabulingar
 *
 */
public class DisclaimerAction extends AbstractAccrualAction {
    
private static final long serialVersionUID = 5729242514602833613L;
private String actionName;
    /**
     * 
     * @return s
     */
    @Override
public String execute() {
        actionName =  ServletActionContext.getRequest().getParameter("actionName");
        return "show_Disclaimer_Page";
    }
    /**
     * @return s
     */
public String accept() {
        ServletActionContext.getRequest().getSession().setAttribute("disclaimer", "accept");
       return "publicWelcome";
}
    /**
     * @return the actionName
     */
    public String getActionName() {
        return actionName;
    }
    /**
     * @param actionName the actionName to set
     */
    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}