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

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.dto.InterventionWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceRemote;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceRemote;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
* @author Hugh Reinhart
* @since 10/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD")
public class TrialInterventionsAction extends ActionSupport implements
        Preparable {
    private static final long serialVersionUID = 1876567890L;

    private static final String ACT_CREATE = "create";
    private static final String ACT_EDIT = "edit";
        
    private PlannedActivityServiceRemote paService;
    private InterventionServiceRemote iService;
    private InterventionAlternateNameServiceRemote ianService;

    private Ii spIdIi;
    private St user;
    
    private List<InterventionWebDTO> interventionsList;
    private String currentAction;
    private String interventionIdentifier;
    private String interventionType;
    private String interventionName;
    private String interventionDescription;
    private String otherName;
    private Boolean leadIndicator;    
    private String selectedRowIdentifier;


    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws Exception e
     */
    public void prepare() throws Exception {
        paService = PaRegistry.getPlannedActivityService();
        iService = PaRegistry.getInterventionService();
        ianService = PaRegistry.getInterventionAlternateNameService();

        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext
                .getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);

        spIdIi = IiConverter.convertToIi(spDTO.getStudyProtocolId());
        user = StConverter.convertToSt(ServletActionContext.getRequest().getRemoteUser());
    }

    /**
     * @return Action result.
     * @throws Exception exception.
     */
    @Override
    public String execute() throws Exception {
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String create() throws Exception {
        setCurrentAction(ACT_CREATE);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String edit() throws Exception {
        PlannedActivityDTO paDto = paService.get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        InterventionDTO i = iService.get(paDto.getInterventionIdentifier());
        List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(i.getIdentifier());
        StringBuffer onBuff = new StringBuffer("");
        for (InterventionAlternateNameDTO ian : ianList) {
            if (ianList.get(0) !=  ian) {
                onBuff.append(", ");
            }
            onBuff.append(StConverter.convertToString(ian.getName()));
        }
        setInterventionDescription(StConverter.convertToString(i.getDescriptionText()));
        setInterventionIdentifier(IiConverter.convertToString(paDto.getInterventionIdentifier()));
        setInterventionName(StConverter.convertToString(i.getName()));
        setInterventionType(CdConverter.convertCdToString(paDto.getSubcategoryCode()));
        setLeadIndicator(BlConverter.covertToBoolean(paDto.getLeadProductIndicator()));
        setOtherName(onBuff.toString());
        setCurrentAction(ACT_EDIT);
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String delete() throws Exception {
        try {
            paService.delete(IiConverter.convertToIi(getSelectedRowIdentifier()));
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        loadForm();
        return SUCCESS;
    }

    private PlannedActivityDTO generateDto() {
        PlannedActivityDTO paDto = new PlannedActivityDTO();
        paDto.setIdentifier(null);
        paDto.setStudyProtocolIdentifier(spIdIi);
        paDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.INTERVENTION));
        paDto.setInterventionIdentifier(IiConverter.convertToIi(getInterventionIdentifier()));
        paDto.setSubcategoryCode(CdConverter.convertStringToCd(getInterventionType()));
        paDto.setLeadProductIndicator(BlConverter.convertToBl(getLeadIndicator()));
        return paDto;
    }
    /**
     * @return result
     * @throws Exception exception
     */
    public String add() throws Exception {
        try {
            paService.create(generateDto());
        } catch (PAException e) {
            addActionError(e.getMessage());
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception exception
     */
    public String update() throws Exception {
        PlannedActivityDTO pa = generateDto();
        pa.setIdentifier(IiConverter.convertToIi(getSelectedRowIdentifier()));
        try {
            paService.update(pa);
        } catch (PAException e) {
            addActionError(e.getMessage());
            return ACT_EDIT;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        loadForm();
        return SUCCESS;
    }

    /**
     * @return result
     * @throws Exception on error.
     */
    public String display() throws Exception {
        loadEditForm(ServletActionContext.getRequest().getParameter("interventionId"));
        return SUCCESS;
    }

    private void loadForm() throws Exception {
        setInterventionsList(new ArrayList<InterventionWebDTO>());
        List<PlannedActivityDTO> paList = paService.getByStudyProtocol(spIdIi);
        for (PlannedActivityDTO pa : paList) {
            if (ActivityCategoryCode.INTERVENTION.equals(ActivityCategoryCode.getByCode(CdConverter
                    .convertCdToString(pa.getCategoryCode())))) {
                InterventionDTO i = iService.get(pa.getInterventionIdentifier());
                List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(i.getIdentifier());
                StringBuffer onBuff = new StringBuffer("");
                for (InterventionAlternateNameDTO ian : ianList) {
                    if (ianList.get(0) !=  ian) {
                        onBuff.append(", ");
                    }
                    onBuff.append(StConverter.convertToString(ian.getName()));
                }
                InterventionWebDTO webDto = new InterventionWebDTO();
                webDto.setOtherNames(onBuff.toString());
                webDto.setDescription(StConverter.convertToString(i.getDescriptionText()));
                webDto.setIdentifier(IiConverter.convertToString(pa.getIdentifier()));
                if ((pa.getLeadProductIndicator() == null) 
                        || (BlConverter.covertToBoolean(pa.getLeadProductIndicator()) == null)
                        || (!BlConverter.covertToBoolean(pa.getLeadProductIndicator()))) {
                    webDto.setLeadIndicator(null);
                } else {
                    webDto.setLeadIndicator("Yes");
                }            
                webDto.setName(StConverter.convertToString(i.getName()));
                webDto.setType(CdConverter.convertCdToString(pa.getSubcategoryCode()));
                getInterventionsList().add(webDto);
            }
        }
    }

    private void loadEditForm(String interventionId) throws Exception {
        if (interventionId != null) {
            Ii interventionIi = IiConverter.convertToIi(interventionId);

            InterventionDTO iDto = iService.get(IiConverter.convertToIi(interventionId));
            setInterventionIdentifier(interventionId);
            setInterventionName(StConverter.convertToString(iDto.getName()));
            setInterventionDescription(StConverter.convertToString(iDto.getDescriptionText()));
            StringBuffer onBuff = new StringBuffer("");
            List<InterventionAlternateNameDTO> ianList = ianService.getByIntervention(interventionIi);
            for (InterventionAlternateNameDTO ian : ianList) {
                if (ianList.get(0) !=  ian) {
                    onBuff.append("\n");
                }
                onBuff.append(StConverter.convertToString(ian.getName()));
            }
            setOtherName(onBuff.toString());
        }
    }

    /**
     * @return the interventionsList
     */
    public List<InterventionWebDTO> getInterventionsList() {
        return interventionsList;
    }

    /**
     * @param interventionsList the interventionsList to set
     */
    public void setInterventionsList(List<InterventionWebDTO> interventionsList) {
        this.interventionsList = interventionsList;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the interventionIdentifier
     */
    public String getInterventionIdentifier() {
        return interventionIdentifier;
    }

    /**
     * @param interventionIdentifier the interventionIdentifier to set
     */
    public void setInterventionIdentifier(String interventionIdentifier) {
        this.interventionIdentifier = interventionIdentifier;
    }

    /**
     * @return the interventionType
     */
    public String getInterventionType() {
        return interventionType;
    }

    /**
     * @param interventionType the interventionType to set
     */
    public void setInterventionType(String interventionType) {
        this.interventionType = interventionType;
    }

    /**
     * @return the interventionName
     */
    public String getInterventionName() {
        return interventionName;
    }

    /**
     * @param interventionName the interventionName to set
     */
    public void setInterventionName(String interventionName) {
        this.interventionName = interventionName;
    }

    /**
     * @return the intervetnionDescription
     */
    public String getInterventionDescription() {
        return interventionDescription;
    }

    /**
     * @param interventionDescription the intervetnionDescription to set
     */
    public void setInterventionDescription(String interventionDescription) {
        this.interventionDescription = interventionDescription;
    }

    /**
     * @return the otherName
     */
    public String getOtherName() {
        return otherName;
    }

    /**
     * @param otherName the otherName to set
     */
    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    /**
     * @return the leadIndicator
     */
    public Boolean getLeadIndicator() {
        return leadIndicator;
    }

    /**
     * @param leadIndicator the leadIndicator to set
     */
    public void setLeadIndicator(Boolean leadIndicator) {
        this.leadIndicator = leadIndicator;
    }

    /**
     * @return the selectedRowIdentifier
     */
    public String getSelectedRowIdentifier() {
        return selectedRowIdentifier;
    }

    /**
     * @param selectedRowIdentifier the selectedRowIdentifier to set
     */
    public void setSelectedRowIdentifier(String selectedRowIdentifier) {
        this.selectedRowIdentifier = selectedRowIdentifier;
    }
}
