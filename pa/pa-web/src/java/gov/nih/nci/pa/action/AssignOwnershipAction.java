/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.TrialOwner;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.RegistryUserService;
import gov.nih.nci.pa.util.ActionUtils;
import gov.nih.nci.pa.util.AssignOwnershipSearchCriteria;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

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
    private OrganizationEntityServiceRemote orgEntSvc;

    /**
     * @return string
     */
    public String view() {
        Long id = IiConverter.convertToLong((Ii) ServletActionContext.getRequest().getSession()
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
        view();
        loadRegistryUsers();
        return SUCCESS;
    }

    /**
     *
     * @return string
     */
     public String save() {
         return changeOwnership(true);
    }

    /**
    * Remove owner of trial.
    * @return string
    */
    public String remove() {
        return changeOwnership(false);
    }

    private String changeOwnership(boolean assign) {
        String userId = ServletActionContext.getRequest().getParameter("userId");
        Ii spIi = (Ii) ServletActionContext.getRequest().getSession()
            .getAttribute(Constants.STUDY_PROTOCOL_II);
        String successMessage = null;
        try {
            if (StringUtils.isNotEmpty(userId) && !ISOUtil.isIiNull(spIi)) {
                successMessage = changeOwnershipHelper(assign, Long.parseLong(userId),
                        IiConverter.convertToLong(spIi));
            } else {
                addActionError(getText("assignOwnership.user.error"));
                return view();
            }
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return view();
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, successMessage);
        return search();
     }

    private String changeOwnershipHelper(boolean assign, Long userId, Long trialId) throws PAException {
       if (assign) {
           PaRegistry.getRegistryUserService().assignOwnership(userId, trialId);
           return getText("assignOwnership.assign.success");
       }
       PaRegistry.getRegistryUserService().removeOwnership(userId, trialId);
       return getText("assignOwnership.remove.success");
    }

    /**
     *
     * @return map of users
     */
    private void loadRegistryUsers() {
        try {
            Ii spIi = (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
            if (!ISOUtil.isIiNull(spIi)) {
                RegistryUser regUser = new RegistryUser();
                regUser.setFirstName(criteria.getFirstName());
                regUser.setLastName(criteria.getLastName());
                regUser.setEmailAddress(criteria.getEmailAddress());
                regUser.setAffiliatedOrganizationId(criteria.getAffiliatedOrgId());
                users = new ArrayList<TrialOwner>();
                List<RegistryUser> regUserList = getRegistryUserService().search(regUser);
                TrialOwner owner = null;
                for (RegistryUser rUsr : regUserList) {
                    if (rUsr.getCsmUser() != null) {
                        String loginName = rUsr.getCsmUser().getLoginName();
                        if (ActionUtils.checkUserHasReadWritePrivilege(loginName)) {
                            owner = new TrialOwner();
                            owner.setRegUser(rUsr);
                            owner.setOwner(PaRegistry.getRegistryUserService()
                                    .isTrialOwner(rUsr.getId(),
                                            Long.parseLong(spIi.getExtension())));
                            users.add(owner);
                        }
                    }
               }
           }
        } catch (PAException e) {
            addActionError("Error getting csm users.");
        }
    }

   /**
    * @return result
    */
   public String displayAffiliatedOrganization() {
       String orgId = ServletActionContext.getRequest().getParameter("orgId");
       OrganizationDTO orgCriteria = new OrganizationDTO();
       orgCriteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));
       LimitOffset limit = new LimitOffset(1, 0);
       try {
           OrganizationDTO selectedOrg = getOrgEntitySvc().search(orgCriteria, limit).get(0);
           criteria.setAffiliatedOrgId(Long.valueOf(selectedOrg.getIdentifier().getExtension()));
           criteria.setAffiliatedOrgName(selectedOrg.getName().getPart().get(0).getValue());
       } catch (PAException e) {
           LOG.error(e.getMessage());
           addActionError("There was an unexpected error with the search.");
       } catch (TooManyResultsException e) {
           addActionError("Too many results, please narrow your search.");
       }
       return "display_affiliated_org";
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

    private OrganizationEntityServiceRemote getOrgEntitySvc() throws PAException {
        if (orgEntSvc == null) {
            orgEntSvc = PoRegistry.getOrganizationEntityService();
        }
        return orgEntSvc;
    }

    /**
     * Injection Method for Org Entity Service.
     * @param svc The service to set.
     */
    public void setOrgEntitySvc(OrganizationEntityServiceRemote svc) {
        this.orgEntSvc = svc;
    }

    /**
     * Injection method for registry user service.
     * @param svc the service to set.
     */
    public void setRegistryUserService(RegistryUserService svc) {
        regUserSvc = svc;
    }

}
