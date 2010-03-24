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

package gov.nih.nci.accrual.outweb.action;

import gov.nih.nci.accrual.outweb.dto.util.PriorTherapiesItemWebDto;
import gov.nih.nci.accrual.outweb.dto.util.PriorTherapiesWebDto;
import gov.nih.nci.accrual.outweb.util.AccrualConstants;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.outcomes.svc.dto.AbstractPriorTherapiesItemDto;
import gov.nih.nci.outcomes.svc.dto.PatientSvcDto;
import gov.nih.nci.outcomes.svc.dto.PriorTherapySvcDto;
import gov.nih.nci.outcomes.svc.util.SvcConstants;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.PqConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 * The Class PriorTherapiesAction.
 *
 * @author Kalpana Guthikonda
 * @since 10/28/2009
 */
@SuppressWarnings({"PMD.TooManyMethods", "PMD.CyclomaticComplexity", "PMD.ExcessiveClassLength" })
public class PriorTherapiesAction extends AbstractListEditAccrualAction<PriorTherapiesWebDto> {

    private static final String SESSION_PRIOR_THERAPIES_ITEM_LIST = "priorTherapiesItemWebDto";

    private static final long serialVersionUID = 1L;

    private static final String DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST = "deleteList";

    private PriorTherapiesWebDto priors = new PriorTherapiesWebDto();
    private PriorTherapiesItemWebDto newPrior = new PriorTherapiesItemWebDto();
    private String delItem = null;

    /**
     * {@inheritDoc}
     */
    @SkipValidation
    @Override
    public String execute() {
        resetData();
        loadForm(getData());
        return super.execute();
    }

    /**
     * Load the bean for the UI.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void loadForm(PriorTherapiesWebDto db) {
        List<PriorTherapiesItemWebDto> temp = new ArrayList<PriorTherapiesItemWebDto>();
        for (PriorTherapiesItemWebDto webDto : db.getList()) {
            if (webDto.getType() != null && webDto.getType().getCode() != null 
                    && !"".endsWith(webDto.getType().getCode()) && webDto.getDescription() != null 
                    && webDto.getDescription().getValue() != null && !"".endsWith(webDto.getDescription().getValue())) {
                temp.add(webDto);
            }
        }
        newPrior = new PriorTherapiesItemWebDto();
        temp.add(newPrior);
        if (!priors.getHasPrior()) {
                priors.setHasPrior(db.getHasPrior());
        }
        priors.setIdentifier(db.getIdentifier());
        priors.setChemoRegimenNum(db.getChemoRegimenNum());
        priors.setTotalRegimenNum(db.getTotalRegimenNum());
        priors.setList(temp);
        delItem = null;
        
    }
    /**
     * Reset the memory buffers. And update the persistent copy as needed when the user
     * toggles the "has prior" checkbox.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void resetData() {
      if ("reset".equals(getCurrentAction())) {
            if (priors.getIdentifier() != null && priors.getIdentifier().getExtension() != null) {
                try {
                    PatientSvcDto svcDto = getPatientSvcDto();
                    svcDto.setPriorTherapy(new PriorTherapySvcDto());
                    svcDto.getPriorTherapy().setAction(SvcConstants.DELETE);
                    outcomesSvc.write(svcDto);
                } catch (Exception e) {
                    addActionError(e.getMessage());
                }
            }   
            priors.clear();
            newPrior = priors.getList().get(0);
            ServletActionContext.getRequest().getSession().removeAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST);
            ServletActionContext.getRequest().getSession().removeAttribute(DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST);
      }
    }

    /**
     * @return result for next action
     */
    public String cancel() {
        return execute();
    }

    /**
     * Save user entries.
     * @return result for next action
     */
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    public String save() {
        List<PriorTherapiesItemWebDto> sessionListOfPriorTherapies = (List<PriorTherapiesItemWebDto>)
            ServletActionContext.getRequest().getSession().getAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST);
        validateForm(sessionListOfPriorTherapies);
        if (hasErrors()) {
            List<PriorTherapiesItemWebDto> tempList = new ArrayList<PriorTherapiesItemWebDto>();
            tempList.addAll(sessionListOfPriorTherapies);
            newPrior = new PriorTherapiesItemWebDto();
            tempList.add(newPrior);
            priors.setList(tempList);
            return SUCCESS;
        }
        priors.setList(sessionListOfPriorTherapies);
        calcTotals();
        PatientSvcDto svcDto = getPatientSvcDto();
        svcDto.setPriorTherapy(priors.getSvcDto());
        if (PAUtil.isIiNotNull(priors.getIdentifier())) {
            svcDto.getPriorTherapy().setAction(SvcConstants.UPDATE);
        } else {
            svcDto.getPriorTherapy().setAction(SvcConstants.CREATE); 
        }
        try {
            outcomesSvc.write(svcDto);
        } catch (Exception re) {
            addActionError("Error saving the Prior Therapies." + re.getLocalizedMessage());
        }
        ServletActionContext.getRequest().setAttribute(AccrualConstants.SUCCESS_MESSAGE,
                AccrualConstants.SAVE_MESSAGE);
        return execute();
    }
    /**
     * @return s
     */
    @Override
    public String add() {
        if (getCurrentAction().equalsIgnoreCase("addPrior")) {
            validateAddPrior(newPrior);
            List<PriorTherapiesItemWebDto> sessionListOfPriorTherapies = (List<PriorTherapiesItemWebDto>)
            ServletActionContext.getRequest().getSession().getAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST);
            if (sessionListOfPriorTherapies == null) {
                sessionListOfPriorTherapies = new ArrayList<PriorTherapiesItemWebDto>();
            } 
            PriorTherapiesItemWebDto webDto;
            if (!sessionListOfPriorTherapies.isEmpty()) {
                for (Iterator<PriorTherapiesItemWebDto> iter = sessionListOfPriorTherapies.iterator(); 
                            iter.hasNext();) {
                    PriorTherapiesItemWebDto tempDto = iter.next();
                    if (tempDto.getType() == null || tempDto.getType().getCode() == null 
                            || tempDto.getDescription() == null 
                            || tempDto.getDescription().getValue() == null
                            || "".equals(tempDto.getType().getCode()) 
                            || "".equals(tempDto.getDescription().getValue())) {
                        iter.remove();
                    }    
                }
            }
            List<PriorTherapiesItemWebDto>  tempList = new ArrayList<PriorTherapiesItemWebDto>();
            tempList.addAll(sessionListOfPriorTherapies); 
            if (!hasErrors() && newPrior.getType() != null && newPrior.getType().getCode() != null 
                    && !"".equals(newPrior.getType().getCode()) && newPrior.getDescription() != null 
                    && newPrior.getDescription().getValue() != null 
                    && !"".equals(newPrior.getDescription().getValue())) {
                webDto = new PriorTherapiesItemWebDto();
                webDto.setType(newPrior.getType());
                webDto.setDescription(newPrior.getDescription());
                sessionListOfPriorTherapies.add(webDto);
                tempList.add(webDto);
            }
            ServletActionContext.getRequest().getSession().setAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST,
                    sessionListOfPriorTherapies);
            newPrior = new PriorTherapiesItemWebDto();
            tempList.add(newPrior);
            priors.setList(tempList);
        }
        return SUCCESS;       
    }
  /**
     * Save changes for all the descriptions.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void getUpdatedDesc(List<PriorTherapiesItemWebDto> toUpdateList) {
        if (toUpdateList != null && !toUpdateList.isEmpty()) {
            for (PriorTherapiesItemWebDto item : toUpdateList) {
                String desc = ServletActionContext.getRequest().getParameter("desc_" 
                        + item.getIdentifier().getExtension());
                String type = ServletActionContext.getRequest().getParameter("type_" 
                        + item.getIdentifier().getExtension());
                St descrip = StConverter.convertToSt(desc);
                Cd typeCd =  CdConverter.convertStringToCd(type);
                item.setDescription(descrip);
                item.setType(typeCd);
            }
        }
    }
   

    /**
     * @param priors the priors to set
     */
    public void setPriors(PriorTherapiesWebDto priors) {
        this.priors = priors;
    }

    /**
     * @return the priors
     */
    public PriorTherapiesWebDto getPriors() {
        return priors;
    }

    /**
     * @param newPrior the newPrior to set
     */
    public void setNewPrior(PriorTherapiesItemWebDto newPrior) {
        this.newPrior = newPrior;
    }

    /**
     * @return the newPrior
     */
    public PriorTherapiesItemWebDto getNewPrior() {
        return newPrior;
    }

    /**
     * @param delItem the delItem to set
     */
    public void setDelItem(String delItem) {
        this.delItem = delItem;
    }

    /**
     * @return the delItem
     */
    public String getDelItem() {
        return delItem;
    }
    
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    private PriorTherapiesWebDto getData() {
        PriorTherapiesWebDto dbPriors = new PriorTherapiesWebDto();
        try {
            PatientSvcDto svcDto = getPatientSvcDto();
            svcDto.setPriorTherapy(new PriorTherapySvcDto());
            List<PatientSvcDto> pSvcList = outcomesSvc.get(svcDto);
            PriorTherapySvcDto priorTherapySvcDto = pSvcList.get(0).getPriorTherapy();
            if (priorTherapySvcDto != null) {
                dbPriors.setIdentifier(priorTherapySvcDto.getIdentifier());
                dbPriors.setHasPrior(priorTherapySvcDto.getHasPrior());
                dbPriors.setTotalRegimenNum(priorTherapySvcDto.getTotalRegimenNum());
                dbPriors.setChemoRegimenNum(priorTherapySvcDto.getChemoRegimenNum());
                List<PriorTherapiesItemWebDto> list = new ArrayList<PriorTherapiesItemWebDto>();
                PriorTherapiesItemWebDto  itemWebDto = null;
                if (priorTherapySvcDto.getItemList() == null) {
                    dbPriors.setList(new ArrayList<PriorTherapiesItemWebDto>());
                } else {
                    for (AbstractPriorTherapiesItemDto itemDto : priorTherapySvcDto.getItemList()) {
                        itemWebDto = new PriorTherapiesItemWebDto();
                        itemWebDto.setDescription(itemDto.getDescription());
                        itemWebDto.setType(itemDto.getType());
                        itemWebDto.setIdentifier(itemDto.getIdentifier());
                        list.add(itemWebDto);
                    }
                
                    dbPriors.setList(list);
                }
            }
            ServletActionContext.getRequest().getSession().setAttribute(
                    SESSION_PRIOR_THERAPIES_ITEM_LIST, dbPriors.getList());
            ServletActionContext.getRequest().getSession().setAttribute(
                    DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST, new ArrayList<PriorTherapiesItemWebDto>());
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
        }
        return dbPriors;
    }
    /**
     * 
     */
    @Override
    public void loadDisplayList() {
        setDisplayTagList(new ArrayList<PriorTherapiesWebDto>());
        //getDisplayTagList().add(getData());
    }
    /**
     * remove values from sessionList.
     * @return s
     */
    @SuppressWarnings("unchecked")
    public String delete() {
        if (delItem != null && delItem.length() > 0) {
            List<PriorTherapiesItemWebDto> sessionList = (List<PriorTherapiesItemWebDto>)
                ServletActionContext.getRequest().getSession().getAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST);
            
            List<PriorTherapiesItemWebDto> deleteSessionList = (List<PriorTherapiesItemWebDto>)
            ServletActionContext.getRequest().getSession().getAttribute(DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST);
            if (deleteSessionList == null) {
                deleteSessionList = new ArrayList<PriorTherapiesItemWebDto>();
            }
            List<PriorTherapiesItemWebDto>  tempList = new ArrayList<PriorTherapiesItemWebDto>();
            for (int i = 0; i < sessionList.size(); i++) {
                PriorTherapiesItemWebDto item = sessionList.get(i);
                if (delItem.equals(item.getIdentifier().getExtension())) {
                    sessionList.remove(i);
                    deleteSessionList.add(item);
                    break;
                }
            }
            
            ServletActionContext.getRequest().getSession().setAttribute(SESSION_PRIOR_THERAPIES_ITEM_LIST,
                    sessionList);
            ServletActionContext.getRequest().getSession().setAttribute(DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST,
                    deleteSessionList);
            
            newPrior = new PriorTherapiesItemWebDto();
            tempList.addAll(sessionList);
            tempList.add(newPrior);
            priors.setList(tempList);
        }
        return SUCCESS;
    }
    /**
     * 
     */
    private void validateForm(List<PriorTherapiesItemWebDto> toValidateDtoList) {
        getUpdatedDesc(toValidateDtoList);
        if (toValidateDtoList != null && !toValidateDtoList.isEmpty()) {
            for (PriorTherapiesItemWebDto toValidateDto : toValidateDtoList) {
                validateAddPrior(toValidateDto);
            }
        }
    }

    /**
     * @param toValidateDto
     */
    @SuppressWarnings({"PMD.NPathComplexity" })
    private void validateAddPrior(PriorTherapiesItemWebDto toValidateDto) {
        if (toValidateDto.getType() == null || toValidateDto.getType().getCode() == null
                || toValidateDto.getDescription() == null || toValidateDto.getDescription().getValue() == null 
                || toValidateDto.getDescription().getValue().equals("")
                || toValidateDto.getType().getCode().equals("")) {
            addActionError("Both: Prior Therapy Type and Prior Therapy Description values"
                    + " must be assigned for saving record.\n");
        }
/*        if ((toValidateDto.getType() == null || toValidateDto.getType().getCode() == null 
                || toValidateDto.getType().getCode().equals(""))
                && toValidateDto.getDescription() != null && toValidateDto.getDescription().getValue() != null 
                && !toValidateDto.getDescription().getValue().equals("")) {
            addActionError("Prior Therapy Type is required.\nModify: Both: Prior Therapy Type"
                    + " and Prior Therapy Description values must be assigned for saving record.\n");
        }
        if (toValidateDto.getType() != null && toValidateDto.getType().getCode() != null
                && !toValidateDto.getType().getCode().equals("") && (toValidateDto.getDescription() == null 
                || toValidateDto.getDescription().getValue() == null 
                || toValidateDto.getDescription().getValue().equals(""))) {
            addActionError("Prior Therapy Description is required.\n Modify: Both: Prior Therapy Type"
                    + " and Prior Therapy Description values must be assigned for saving record.\n");
        }*/
    }
    /**
     * Calculate the numeric totals.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void calcTotals() {
        String strUnitary = "Unitary";
        if (priors.getList() != null) {
            priors.setTotalRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(priors.getList().size()), strUnitary));
            int chemoCnt = 0;
            for (PriorTherapiesItemWebDto item : priors.getList()) {
                if (item.getType().getCode().startsWith("Chemo")) {
                    ++chemoCnt;
                }
            }
            priors.setChemoRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(chemoCnt), strUnitary));
        } else {
            priors.setTotalRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(0), strUnitary));
            priors.setChemoRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(0), strUnitary));
        }

        

    }
}
