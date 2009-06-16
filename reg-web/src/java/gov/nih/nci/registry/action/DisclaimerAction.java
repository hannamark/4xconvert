package gov.nih.nci.registry.action;

import java.io.IOException;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Vrushali
 *
 */
public class DisclaimerAction extends ActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(DisclaimerAction.class);
    private String actionName;
    /**
     * 
     * @return s
     */
    public String execute() {
        actionName =  (String) ServletActionContext.getRequest().getParameter("actionName");
        return "show_Disclaimer_Page";
    }
    /**
     * 
     */
    public void accept() {
        ServletActionContext.getRequest().getSession().setAttribute("disclaimer", "accept");
        if ("".equals(actionName)) {
            actionName = "searchTrial.action";
        }
        try {
            ServletActionContext.getRequest().getRequestDispatcher(actionName)
                .forward(ServletActionContext.getRequest(), ServletActionContext.getResponse());
        } catch (ServletException e) {
            LOG.error(e.getMessage());
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
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
