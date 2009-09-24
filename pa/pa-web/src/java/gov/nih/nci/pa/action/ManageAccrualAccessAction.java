/*
* caBIG Open Source Software License
*
* Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
* was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
* includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
*
* This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
* person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with the
* entity.  Control for purposes of this definition means
*
* (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
* or otherwise,or
*
* (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
*
* (iii) beneficial ownership of such entity.
* License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
* worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free  right and license in its
* rights in the caBIG Software, including any copyright or patent rights therein, to
*
* (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market,  publicly display,
* publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
* or permit others to do so;
*
* (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
* (or portions thereof);
*
* (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
* derivative works thereof; and (iv) sublicense the  foregoing rights  set  out in (i), (ii) and (iii) to third parties,
* including the right to license such rights to further third parties. For sake of clarity,and not by way of limitation,
* caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
* granted under this License.   This  License  is  granted  at no  charge  to You. Your downloading, copying, modifying,
* displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
* Agreement.  If You do not agree to such terms and conditions,  You have no right to download,  copy,  modify, display,
* distribute or use the caBIG Software.
*
* 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
* of conditions and the disclaimer and limitation of liability of Article 6 below.   Your redistributions in object code
* form must reproduce the above copyright notice,  this list of  conditions  and the  disclaimer  of  Article  6  in the
* documentation and/or other materials provided with the distribution, if any.
*
* 2.  Your end-user documentation included with the redistribution, if any,  must include the  following acknowledgment:
* This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
* shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
* appear.
*
* 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
* caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
* any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
* as required to comply with the terms of this License.
*
* 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
* programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
* party proprietary programs,  You agree  that You are  solely responsible  for obtaining any permission from such third
* parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
* sub licensees, including without limitation Your end-users, of their obligation  to  secure  any  required permissions
* from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
* In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
* against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
* to obtain such permissions.
*
* 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
* and to the derivative works, and You may provide  additional  or  different  license  terms  and  conditions  in  Your
* sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
* provided Your use, reproduction,  and  distribution  of the Work otherwise complies with the conditions stated in this
* License.
*
* 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
* THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
* NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
* OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO,  PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
* DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
* IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
*
*/
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceBean;
import gov.nih.nci.pa.util.LabelValueBean;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Sep 3, 2009
 */
public class ManageAccrualAccessAction extends AbstractListEditAction {

    private static final long serialVersionUID = -4655853779195760379L;

    private List<StudySiteAccrualAccessDTO> accessList;
    private StudySiteAccrualAccessDTO access;
    private Map<Long, User> csmUsers = null;
    private List<LabelValueBean> csmUserNames = null;
    private Map<Long, String> sites = null;
    private String email;
    private String phone;
    private String siteRecruitmentStatus;


    /**
     * {@inheritDoc}
     */
    @Override
    public String create() throws PAException {
        if (!spDTO.getDocumentWorkflowStatusCode().isEligibleForAccrual()) {
            addActionError("An abstraction must be verified before users can be authorized to submit accruals.");
            return super.execute();
        }
        return super.create();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws PAException {
        try {
            accrualAccessSvc.create(access);
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() throws PAException {
        try {
            accrualAccessSvc.update(access);
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (CA_EDIT.equals(getCurrentAction())) {
            setAccess(accrualAccessSvc.get(Long.valueOf(getSelectedRowIdentifier())));
            setEmail(getAccess().getEmail());
            setPhone(getAccess().getPhone());
            setSiteRecruitmentStatus(getAccess().getSiteRecruitmentStatus());
        } else {
            setAccess(new StudySiteAccrualAccessDTO());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadListForm() throws PAException {
        setAccessList(accrualAccessSvc.getByStudyProtocol(spDTO.getStudyProtocolId()));
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadEmail() throws PAException {
        Long csmUserId;
        try {
            csmUserId = Long.valueOf(ServletActionContext.getRequest().getParameter("csmUserId"));
        } catch (NumberFormatException e) {
            csmUserId = null;
        }
        User user = getCsmUsers().get(csmUserId);
        setEmail(user == null ? null : user.getLoginName());
        return SUCCESS;
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadPhone() throws PAException {
        Long csmUserId;
        try {
            csmUserId = Long.valueOf(ServletActionContext.getRequest().getParameter("csmUserId"));
        } catch (NumberFormatException e) {
            csmUserId = null;
        }
        User user = getCsmUsers().get(csmUserId);
        setPhone(user == null ? null : user.getPhoneNumber());
        return SUCCESS;
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadSiteRecruitmentStatus() throws PAException {
        Long studySiteId;
        try {
            studySiteId = Long.valueOf(ServletActionContext.getRequest().getParameter("studySiteId"));
        } catch (NumberFormatException e) {
            studySiteId = null;
        }
        StudySiteAccrualStatusDTO iso = accrualStatusSvc.getCurrentStudySiteAccrualStatusByStudySite(
                IiConverter.convertToStudySiteIi(studySiteId));
        setSiteRecruitmentStatus(iso == null ? null : CdConverter.convertCdToString(iso.getStatusCode()));
        return SUCCESS;
    }

    /**
     * @return the accessList
     */
    public List<StudySiteAccrualAccessDTO> getAccessList() {
        return accessList;
    }

    /**
     * @param accessList the accessList to set
     */
    public void setAccessList(List<StudySiteAccrualAccessDTO> accessList) {
        this.accessList = accessList;
    }

    /**
     * @return the access
     */
    public StudySiteAccrualAccessDTO getAccess() {
        return access;
    }

    /**
     * @param access the access to set
     */
    public void setAccess(StudySiteAccrualAccessDTO access) {
        this.access = access;
    }

    /**
     * @return the sites
     * @throws PAException exception
     */
    public Map<Long, String> getSites() throws PAException {
        if (sites == null) {
            sites = accrualAccessSvc.getTreatingSites(spDTO.getStudyProtocolId());
        }
        return sites;
    }

    /**
     * @return the csmUsers
     */
    public Map<Long, User> getCsmUsers() {
        if (csmUsers == null) {
            try {
                csmUsers = new HashMap<Long, User>();
                csmUserNames = new ArrayList<LabelValueBean>();
                Set<User> uSet = accrualAccessSvc.getSubmitters();
                
                List<LabelValueBean> lvBeanList = new ArrayList<LabelValueBean>();
                for (User u : uSet) {
                    csmUsers.put(u.getUserId(), u);
                    String emailId = u.getLoginName() != null ? u.getLoginName() : " ";
                    LabelValueBean lvBean = new LabelValueBean();
                    lvBean.setId(u.getUserId());
                    lvBean.setName(StudySiteAccrualAccessServiceBean.getFullName(u) + " " + emailId);
                    lvBeanList.add(lvBean);
                }
                Collections.sort(lvBeanList);
                for (LabelValueBean bean : lvBeanList) {
                csmUserNames.add(bean);
                }
            } catch (PAException e) {
                addActionError("Error getting csm users.");
            }
        }
        return csmUsers;
    }

    /**
     * @return the csmUserNames
     */
    public List<LabelValueBean> getCsmUserNames() {
        if (csmUserNames == null) {
            getCsmUsers();
        }
        return csmUserNames;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the siteRecruitmentStatus
     */
    public String getSiteRecruitmentStatus() {
        return siteRecruitmentStatus;
    }

    /**
     * @param siteRecruitmentStatus the siteRecruitmentStatus to set
     */
    public void setSiteRecruitmentStatus(String siteRecruitmentStatus) {
        this.siteRecruitmentStatus = siteRecruitmentStatus;
    }
}
