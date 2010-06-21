/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.util.AssignOwnershipSearchCriteria;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;


/**
 * @author Vrushali
 *
 */
public class AssignOwnershipAction extends ActionSupport implements Preparable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private List<TrialOwner> users = null;
    CorrelationUtilsRemote cUtils;
    private AssignOwnershipSearchCriteria criteria = new AssignOwnershipSearchCriteria();

    /**
     * @return string
     */
    public String view() {
        return SUCCESS;
    }

    /**
     * @return string
     */
    public String search() {
        loadRegistryUsers();
        return SUCCESS;
    }

    /**
     *
     * @return string
     */
     public String save() {
       String userId = ServletActionContext.getRequest().getParameter("userId");
       Ii spIi = (Ii) ServletActionContext.getRequest().getSession()
           .getAttribute(Constants.STUDY_PROTOCOL_II);
       try {
           if (StringUtils.isNotEmpty(userId) && PAUtil.isIiNotNull(spIi)) {
               PaRegistry.getRegisterUserService().assignOwnership(Long.parseLong(userId),
                       IiConverter.convertToLong(spIi));
           } else {
               addActionError("Please select user to change ownership.");
               return view();
           }
       } catch (PAException e) {
           addActionError(e.getLocalizedMessage());
           return view();
       }
       ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Ownership has been changed.");
       return search();
    }

    /**
     *
     * @return map of users
     */
    private void loadRegistryUsers() {
            try {
                Ii spIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
                if (PAUtil.isIiNotNull(spIi)) {
                    RegistryUser regUser = new RegistryUser();
                    regUser.setFirstName(criteria.getFirstName());
                    regUser.setLastName(criteria.getLastName());
                    regUser.setEmailAddress(criteria.getEmailAddress());
                    users = new ArrayList<TrialOwner>();
                    List<RegistryUser> regUserList = PaRegistry.getRegisterUserService().search(regUser);
                    TrialOwner owner = null;
                    for (RegistryUser rUsr : regUserList) {
                        owner = new TrialOwner();
                        owner.setRegUser(rUsr);
                        owner.setOwner(PaRegistry.getRegisterUserService().hasTrialAccess(rUsr,
                                Long.parseLong(spIi.getExtension())));
                        users.add(owner);
                    }
                }
            } catch (PAException e) {
                addActionError("Error getting csm users.");
            }
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<TrialOwner> users) {
        this.users = users;
    }

    /**
     * @return the users
     */
    public List<TrialOwner> getUsers() {
        return users;
    }

    /**
     *
     */
    public void prepare() {
        cUtils = new CorrelationUtils();
    }

    /**
     * @return the criteria
     */
    public AssignOwnershipSearchCriteria getCriteria() {
        return criteria;
    }

    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(AssignOwnershipSearchCriteria criteria) {
        this.criteria = criteria;
    }

}