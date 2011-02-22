/**
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.AssignOwnershipSearchCriteria;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @author Vrushali
 *
 */
public class AssignOwnershipAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private List<TrialOwner> users = null;
    private AssignOwnershipSearchCriteria criteria = new AssignOwnershipSearchCriteria();
    private Set<RegistryUser> trialOwners = null;

    private RegistryUserService regUserSvc;

    /**
     * @return string
     */
    public String view() {
        Long id = IiConverter.convertToLong(
                (Ii) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.STUDY_PROTOCOL_II));
        try {
            trialOwners = getRegistryUserService().getAllTrialOwners(id);
        } catch (PAException e) {
            addActionError("Unable to lookup trial owners for study protocol " + id);
        }

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
               getRegistryUserService().assignOwnership(Long.parseLong(userId),
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
                    List<RegistryUser> regUserList = getRegistryUserService().search(regUser);
                    TrialOwner owner = null;
                    for (RegistryUser rUsr : regUserList) {
                        owner = new TrialOwner();
                        owner.setRegUser(rUsr);
                        owner.setOwner(PaRegistry.getRegistryUserService().hasTrialAccess(rUsr,
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

    /**
     * @return the trial owners
     */
    public Set<RegistryUser> getTrialOwners() {
        return trialOwners;
    }

    private RegistryUserService getRegistryUserService() {
        if (regUserSvc == null) {
            regUserSvc = PaRegistry.getRegistryUserService();
        }
        return regUserSvc;
    }

    /**
     * Injection method for registry user service.
     * @param svc the service to set.
     */
    public void setRegistryUserService(RegistryUserService svc) {
        regUserSvc = svc;
    }

}
