/**
 * 
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.LabelValueBean;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


/**
 * @author Vrushali
 *
 */
public class ChangeOwnershipAction extends ActionSupport {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Map<Long, User> csmUsers = null;
    private List<LabelValueBean> csmUserNames = null;
    
    /**
     * @return string
     */
    public String view() {
        getCsmUsers();
        return SUCCESS;
    }
    /**
     * 
     * @param csmUserNames set the list
     */
    public void setCsmUserNames(List<LabelValueBean> csmUserNames) {
        this.csmUserNames = csmUserNames;
    }
    /**
     * 
     * @return list
     */
    public List<LabelValueBean> getCsmUserNames() {
        return csmUserNames;
    }
    /**
     * 
     * @return string
     */
     public String save() {
       String csmUserEmailId;
       csmUserEmailId = ServletActionContext.getRequest().getParameter("csmUserId");

       Ii spIi = (Ii) ServletActionContext.getRequest().getSession()
           .getAttribute(Constants.STUDY_PROTOCOL_II);
       
       try {
           StudyProtocolDTO dto = PaRegistry.getStudyProtocolService().getStudyProtocol(spIi);
           String strPrevUserCreated = StConverter.convertToString(dto.getUserLastCreated());
           if (PAUtil.isNotEmpty(csmUserEmailId) && !strPrevUserCreated.equalsIgnoreCase(csmUserEmailId)) {
               //update only the userlastCreated
               dto.setUserLastCreated(StConverter.convertToSt(csmUserEmailId));
               PaRegistry.getStudyProtocolService().changeOwnership(dto);
               //send the mail
               PaRegistry.getMailManagerService().sendChangeOwnershipMail(strPrevUserCreated, spIi);
               //PaRegistry.getMailManagerService().sendChangeOwnershipMail(csmUserEmailId);
               
               StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry.getProtocolQueryService()
                   .getTrialSummaryByStudyProtocolId(IiConverter.convertToLong(spIi));
               // put an entry in the session and store StudyProtocolQueryDTO
               ServletActionContext.getRequest().getSession().setAttribute(
                       Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
           } else {
               addActionError("Please do not select the same user to change ownership.");
               return view();
           }
       } catch (PAException e) {
           addActionError(e.getLocalizedMessage());
           return view();
       }
       ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, "Ownership has been changed.");
        return view();
    }
    /**
     * 
     * @return map of users
     */
    private Map<Long, User> getCsmUsers() {
        if (csmUsers == null) {
            try {
                csmUsers = new HashMap<Long, User>();
                setCsmUserNames(new ArrayList<LabelValueBean>());
                Set<User> uSet = CSMUserService.getInstance().getCSMUsers();

                List<LabelValueBean> lvBeanList = new ArrayList<LabelValueBean>();
                for (User u : uSet) {
                    csmUsers.put(u.getUserId(), u);
                    String emailId = u.getLoginName() != null ? u.getLoginName() : " ";
                    LabelValueBean lvBean = new LabelValueBean();
                    lvBean.setId(u.getUserId());
                    lvBean.setKeyValue(emailId);
                    lvBean.setName(getFullName(u) + " - " + emailId);
                    lvBeanList.add(lvBean);
                }
                Collections.sort(lvBeanList);
                for (LabelValueBean bean : lvBeanList) {
                getCsmUserNames().add(bean);
                }
            } catch (PAException e) {
                addActionError("Error getting csm users.");
            }
        }
        return csmUsers;
    }
    /**
     * @param user The csm user object
     * @return Name of the user formated last, first.  If no name is entered use login name.
     */
    private String getFullName(User user) {
        String fullName = null;
        if (user.getLastName() != null) {
            fullName = user.getLastName();
        }
        if (user.getFirstName() != null) {
            fullName = user.getLastName() + ", " + user.getFirstName();
        }
        if (PAUtil.isEmpty(fullName)) {
            fullName = user.getLoginName();
        }
        return fullName;
    }
}