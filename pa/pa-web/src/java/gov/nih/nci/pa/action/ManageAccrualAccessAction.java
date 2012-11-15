/*
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Protocol  Abstraction (PA) Application
 * was created with NCI funding and is part of the caBIG initiative. The software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are  controlled by,  or  are under common  control  with
 * the entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable,  transferable  and royalty-free right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate,  execute, reproduce,  copy, modify, translate,  market, publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the  foregoing rights set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License  is  granted  at no  charge to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions, You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below. Your redistributions in object code
 * form must reproduce the above copyright notice,  this list of  conditions  and the disclaimer  of  Article  6  in the
 * documentation and/or other materials provided with the distribution, if any.
 *
 * 2.  Your end-user documentation included with the redistribution, if any, must include the  following acknowledgment:
 * This product includes software developed by ScenPro, Inc.   If  You  do not include such end-user documentation, You
 * shall include this acknowledgment in the caBIG Software itself, wherever such third-party acknowledgments normally
 * appear.
 *
 * 3.  You may not use the names ScenPro, Inc., The National Cancer Institute, NCI, Cancer Bioinformatics Grid or
 * caBIG to endorse or promote products derived from this caBIG Software.  This License does not authorize You to use
 * any trademarks, service marks, trade names, logos or product names of either caBIG Participant, NCI or caBIG, except
 * as required to comply with the terms of this License.
 *
 * 4.  For sake of clarity, and not by way of limitation, You may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs. However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs,  You agree that You are solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to  secure  any required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5. For sake of clarity, and not by way of limitation, You may add Your own copyright statement  to Your modifications
 * and to the derivative works, and You may provide  additional or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction,  and  distribution of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS;OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.pa.action;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.StudySiteAccrualAccessWebDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualAccessDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceBean;
import gov.nih.nci.pa.service.util.StudySiteAccrualAccessServiceLocal;
import gov.nih.nci.pa.util.CsmUserUtil;
import gov.nih.nci.pa.util.LabelValueBean;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

/**
 * @author Hugh Reinhart
 * @since Sep 3, 2009
 */
@SuppressWarnings("PMD.TooManyMethods")
public class ManageAccrualAccessAction extends AbstractListEditAction {

    private static final long serialVersionUID = -1924093330034538567L;

    private RegistryUserServiceLocal registryUserService;
    private StudySiteAccrualAccessServiceLocal accrualAccessService;
    private StudySiteAccrualStatusServiceLocal accrualStatusService;
    private ManageAccrualAccessHelper accrualAccessHelper;

    private List<StudySiteAccrualAccessWebDTO> accessList;
    private StudySiteAccrualAccessWebDTO access = new StudySiteAccrualAccessWebDTO();
    private Map<Long, RegistryUser> regUsers;
    private List<LabelValueBean> regUserNames;
    private Map<Long, String> sites;
    private String email;
    private String phone;
    private String siteRecruitmentStatus;
    private Long registryUserId;
    private final Map<Long, RegistryUser> registryUserMap = new HashMap<Long, RegistryUser>();
    private final Map<Ii, StudySiteAccrualStatusDTO> ssAccrualStatusMap = new HashMap<Ii, StudySiteAccrualStatusDTO>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        registryUserService = PaRegistry.getRegistryUserService();
        accrualAccessService = PaRegistry.getStudySiteAccrualAccessService();
        accrualStatusService = PaRegistry.getStudySiteAccrualStatusService();
        accrualAccessHelper = new ManageAccrualAccessHelper(accrualAccessService);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String create() throws PAException {
        if (!getSpDTO().getDocumentWorkflowStatusCode().isEligibleForAccrual()) {
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
            if (ManageAccrualAccessHelper.ALL_TREATING_SITES_ID.equals(getAccess().getStudySiteId())) {
                accrualAccessHelper.addMultipleTreatingSitesAccess(getAccess(), getSites());
                getAccess().setStudySiteId(ManageAccrualAccessHelper.ALL_TREATING_SITES_ID);
            } else {
                accrualAccessHelper.addTreatingSiteAccess(getAccess());
            }
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
            accrualAccessHelper.updateTreatingSiteAccess(getAccess());
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
            StudySiteAccrualAccessDTO dto = accrualAccessService.get(IiConverter
                .convertToIi(getSelectedRowIdentifier()));
            StudySiteAccrualAccessWebDTO webDTO = populateWebDTO(dto);
            setAccess(webDTO);
            setEmail(webDTO.getEmailAddress());
            setPhone(webDTO.getPhoneNumber());
            setSiteRecruitmentStatus(webDTO.getSiteRecruitmentStatus());
        } else {
            setAccess(new StudySiteAccrualAccessWebDTO());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadListForm() throws PAException {
        List<StudySiteAccrualAccessDTO> dtos = accrualAccessService.getByStudyProtocol(getSpDTO().getStudyProtocolId());
        List<StudySiteAccrualAccessWebDTO> webDtos = new ArrayList<StudySiteAccrualAccessWebDTO>();
        for (StudySiteAccrualAccessDTO dto : dtos) {
            webDtos.add(populateWebDTO(dto));
        }
        setAccessList(webDtos);
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadEmail() throws PAException {
        RegistryUser user = getRegUsers().get(getRegistryUserId());
        if (user != null) {
            setEmail(user.getEmailAddress());
        }
        return SUCCESS;
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadPhone() throws PAException {
        RegistryUser user = getRegUsers().get(getRegistryUserId());
        if (user != null) {
            setPhone(user.getPhone());
        }
        return SUCCESS;
    }

    /**
     * @return action result
     * @throws PAException exception
     */
    public String loadSiteRecruitmentStatus() throws PAException {
        Long studySiteId = null;
        try {
            studySiteId = Long.valueOf(ServletActionContext.getRequest().getParameter("studySiteId"));
        } catch (NumberFormatException e) {
            setSiteRecruitmentStatus(null);
        }

        if (studySiteId != null) {
            StudySiteAccrualStatusDTO iso = accrualStatusService
                .getCurrentStudySiteAccrualStatusByStudySite(IiConverter
                    .convertToStudySiteIi(studySiteId));
            setSiteRecruitmentStatus(iso == null ? null : CdConverter.convertCdToString(iso.getStatusCode()));
        }

        return SUCCESS;
    }

    /**
     * @return the accessList
     */
    public List<StudySiteAccrualAccessWebDTO> getAccessList() {
        return accessList;
    }

    /**
     * @param accessList the accessList to set
     */
    public void setAccessList(List<StudySiteAccrualAccessWebDTO> accessList) {
        this.accessList = accessList;
    }

    /**
     * @return the access
     */
    public StudySiteAccrualAccessWebDTO getAccess() {
        return access;
    }

    /**
     * @param access the access to set
     */
    public void setAccess(StudySiteAccrualAccessWebDTO access) {
        this.access = access;
    }

    /**
     * @return the sites
     * @throws PAException exception
     */
    public Map<Long, String> getSites() throws PAException {
        if (sites == null) {
            sites = new LinkedHashMap<Long, String>();
            sites.put(ManageAccrualAccessHelper.ALL_TREATING_SITES_ID, "All Sites");
            sites.putAll(accrualAccessService.getTreatingSites(getSpDTO().getStudyProtocolId()));
        }
        return sites;
    }

    /**
     * @return the csmUsers
     */
    public Map<Long, RegistryUser> getRegUsers() {
        if (regUsers == null) {
            try {
                regUsers = new HashMap<Long, RegistryUser>();
                regUserNames = new ArrayList<LabelValueBean>();
                List<LabelValueBean> lvBeanList = getDisplayUsersList();
                Collections.sort(lvBeanList);
                for (LabelValueBean bean : lvBeanList) {
                    regUserNames.add(bean);
                }
            } catch (PAException e) {
                addActionError("Error getting csm users.");
            }
        }
        return regUsers;
    }

    /**
     * @return
     * @throws PAException
     */
    private List<LabelValueBean> getDisplayUsersList() throws PAException {
        Set<User> uSet = accrualAccessService.getSubmitters();

        List<LabelValueBean> lvBeanList = new ArrayList<LabelValueBean>();
        for (User u : uSet) {
            RegistryUser usr = getRegistryUser(u);
            if (usr != null) {
                regUsers.put(usr.getId(), usr);
                String loginId = u.getLoginName() != null ? u.getLoginName() : " ";
                LabelValueBean lvBean = new LabelValueBean();
                lvBean.setId(usr.getId());
                lvBean.setName(StudySiteAccrualAccessServiceBean.getFullName(usr) + " - "
                            + CsmUserUtil.getGridIdentityUsername(loginId));
                lvBeanList.add(lvBean);
            }
        }
        return lvBeanList;
    }
    
    private RegistryUser getRegistryUser(User user) {
        if (user == null) {
            return null;
        }
        try {
            RegistryUser regUser = registryUserService.getUser(user.getLoginName());
            if (regUser == null) {
                return null;
            }
            return regUser;
        } catch (PAException e) {
            addActionError("Error getting csm users email.");
            return null;
        }
    }

    private StudySiteAccrualAccessWebDTO populateWebDTO(StudySiteAccrualAccessDTO dto) {
        StudySiteAccrualAccessWebDTO webDTO = new StudySiteAccrualAccessWebDTO();
        webDTO.setIdentifier(IiConverter.convertToLong(dto.getIdentifier()));
        webDTO.setStudySiteId(IiConverter.convertToLong(dto.getStudySiteIdentifier()));
        webDTO.setRegistryUserId(IiConverter.convertToLong(dto.getRegistryUserIdentifier()));
        webDTO.setSource(CdConverter.convertCdToString(dto.getSource()));
        webDTO.setRequestDetails(StConverter.convertToString(dto.getRequestDetails()));
        webDTO.setStatusCode(CdConverter.convertCdToString(dto.getStatusCode()));
        try {
            RegistryUser ru = registryUserMap.get(webDTO.getRegistryUserId());
            if (ru == null) {
                ru = registryUserService.getUserById(webDTO.getRegistryUserId());
                registryUserMap.put(webDTO.getRegistryUserId(), ru);
            }
            webDTO.setEmailAddress(ru.getEmailAddress());
            webDTO.setPhoneNumber(ru.getPhone());
            webDTO.setUserName(CsmUserUtil.getGridIdentityUsername(ru
                    .getCsmUser() != null ? ru.getCsmUser().getLoginName() : ""));
        } catch (PAException e) {
            LOG.error("Error retrieving registry user with id " + webDTO.getRegistryUserId() + ".");
        }

        try {
            StudySiteAccrualStatusDTO siteStatus = ssAccrualStatusMap.get((dto.getStudySiteIdentifier()));
            if (siteStatus == null) {
                siteStatus = accrualStatusService.getCurrentStudySiteAccrualStatusByStudySite(dto
                        .getStudySiteIdentifier());
                ssAccrualStatusMap.put(dto.getStudySiteIdentifier(), siteStatus);
            }
            webDTO.setSiteRecruitmentStatus(CdConverter.convertCdToString(siteStatus.getStatusCode()));
            webDTO.setSiteName(getSites().get(webDTO.getStudySiteId()));
        } catch (Exception e) {
            LOG.error("Error retrieving status for study site with id " + webDTO.getStudySiteId() + ".");
        }
        return webDTO;
    }

    /**
     * @return the regUserNames
     */
    public List<LabelValueBean> getRegUserNames() {
        if (regUserNames == null) {
            getRegUsers();
        }
        return regUserNames;
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

    /**
     * @return the registryUserId
     */
    public Long getRegistryUserId() {
        return registryUserId;
    }

    /**
     * @param registryUserId the registryUserId to set
     */
    public void setRegistryUserId(Long registryUserId) {
        this.registryUserId = registryUserId;
    }

    /**
     * @param registryUserService the registryUserService to set
     */
    public void setRegistryUserService(RegistryUserServiceLocal registryUserService) {
        this.registryUserService = registryUserService;
    }

    /**
     * @param accrualAccessService the accrualAccessService to set
     */
    public void setAccrualAccessService(StudySiteAccrualAccessServiceLocal accrualAccessService) {
        this.accrualAccessService = accrualAccessService;
    }

    /**
     * @param accrualStatusService the accrualStatusService to set
     */
    public void setAccrualStatusService(StudySiteAccrualStatusServiceLocal accrualStatusService) {
        this.accrualStatusService = accrualStatusService;
    }

    /**
     * @param accrualAccessHelper the accrualAccessHelper to set
     */
    public void setAccrualAccessHelper(ManageAccrualAccessHelper accrualAccessHelper) {
        this.accrualAccessHelper = accrualAccessHelper;
    }
    
    @Override
    public void deleteObject(Long objectId) throws PAException {
        throw new UnsupportedOperationException();
    }
    
}
