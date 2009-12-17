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

package gov.nih.nci.accrual.web.action;

import gov.nih.nci.accrual.dto.PerformedMedicalHistoryResultDto;
import gov.nih.nci.accrual.dto.PerformedObservationDto;
import gov.nih.nci.accrual.web.dto.util.PriorTherapiesItemWebDto;
import gov.nih.nci.accrual.web.dto.util.PriorTherapiesWebDto;
import gov.nih.nci.accrual.web.dto.util.PriorTherapyTypesWebDto;
import gov.nih.nci.accrual.web.util.AccrualConstants;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.pa.enums.ActivityNameCode;
import gov.nih.nci.pa.enums.PerformedObservationResultTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.PqConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

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
    private String lookupItem = null;

    private List<PriorTherapyTypesWebDto> disWebList = new ArrayList<PriorTherapyTypesWebDto>();
    private PriorTherapyTypesWebDto priorTherapy = new PriorTherapyTypesWebDto();
    private String searchTherapy = null;

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
        if (db.getIdHasPrior() != null && db.getIdHasPrior().getExtension() != null) {
            priors.setHasPrior(db.getHasPrior());
        } 
        priors.setId(db.getId());
        priors.setIdTotalRegimenNum(db.getIdTotalRegimenNum());
        priors.setIdHasPrior(db.getIdHasPrior());
        priors.setIdChemoRegimenNum(db.getIdChemoRegimenNum());
        priors.setList(temp);
        priors.setChemoRegimenNum(db.getChemoRegimenNum());
        priors.setTotalRegimenNum(db.getTotalRegimenNum());
        delItem = null;
        lookupItem = null;
        
    }
    /**
     * Reset the memory buffers. And update the persistent copy as needed when the user
     * toggles the "has prior" checkbox.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void resetData() {
      if ("reset".equals(getCurrentAction())) {
            if (priors.getId() != null && priors.getId().getExtension() != null) {
                try {
                    performedActivitySvc.delete(priors.getId());
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
     * Apply the Therapy Type selection. After performing a lookup the selection is
     * applied immediately to the target item.
     *
     * @param db the memory buffer containing the persisted data
     */
    @SuppressWarnings("PMD")
    private void applyTypeSelection(PriorTherapiesWebDto db) {
        if (priorTherapy.getId().getExtension() != null && lookupItem != null) {
            if (lookupItem.equals(newPrior.getId().getExtension())) {
                newPrior.setType(priorTherapy.getType());
            } else {
                for (PriorTherapiesItemWebDto item : db.getList()) {
                    if (lookupItem.equals(item.getId().getExtension())) {
                        item.setType(priorTherapy.getType());
                        calcTotals(db);
                        break;
                    }
                }
            }
            priorTherapy = new PriorTherapyTypesWebDto();
            lookupItem = null;
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
        try {
            PerformedObservationDto dto = new PerformedObservationDto();
            dto.setNameCode(CdConverter.convertToCd(ActivityNameCode.PRIOR_THERAPIES));
            dto.setStudyProtocolIdentifier(getSpIi());
            dto.setStudySubjectIdentifier(getParticipantIi());
            PerformedObservationDto dbPerformedObservationDTO;
            if (priors.getId() != null && priors.getId().getExtension() != null) {
                dto.setIdentifier(priors.getId());
                dbPerformedObservationDTO = performedActivitySvc.updatePerformedObservation(dto);
            } else {
                dbPerformedObservationDTO = performedActivitySvc.createPerformedObservation(dto);
            }
            createOrUpdateHasPrior(priors, dbPerformedObservationDTO.getIdentifier());
            //this is update/create or delete for List
            deletesPriorTherapyType();
            createOrUpdatePriorTherapyType(sessionListOfPriorTherapies, dbPerformedObservationDTO.getIdentifier());
            calcTotals(getData());
            PerformedMedicalHistoryResultDto perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
            perMedicalHistoryDTO.setPerformedObservationIdentifier(dbPerformedObservationDTO.getIdentifier());
            perMedicalHistoryDTO.setStudyProtocolIdentifier(getSpIi());
            perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                    PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_THERAPIES));
            perMedicalHistoryDTO.setResultQuantity(priors.getTotalRegimenNum());
            if (priors.getIdTotalRegimenNum() != null &&  priors.getIdTotalRegimenNum().getExtension() != null) {
                perMedicalHistoryDTO.setIdentifier(priors.getIdTotalRegimenNum());
                performedObservationResultSvc.updatePerformedMedicalHistoryResult(perMedicalHistoryDTO);
            } else {
                performedObservationResultSvc.createPerformedMedicalHistoryResult(perMedicalHistoryDTO);
            }
            perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
            perMedicalHistoryDTO.setPerformedObservationIdentifier(dbPerformedObservationDTO.getIdentifier());
            perMedicalHistoryDTO.setStudyProtocolIdentifier(getSpIi());
            perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                    PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_CHEMOTHERAPY_REGIMENS));
            perMedicalHistoryDTO.setResultQuantity(priors.getChemoRegimenNum());
            if (priors.getIdChemoRegimenNum() != null &&  priors.getIdChemoRegimenNum().getExtension() != null) {
                perMedicalHistoryDTO.setIdentifier(priors.getIdChemoRegimenNum());
                performedObservationResultSvc.updatePerformedMedicalHistoryResult(perMedicalHistoryDTO);
            } else {
                performedObservationResultSvc.createPerformedMedicalHistoryResult(perMedicalHistoryDTO);
            }
        } catch (RemoteException e) {
            addActionError(e.getMessage());
        } catch (DataFormatException e) {
            addActionError(e.getMessage());
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
     * @param newPriorsRec insertDto
     * @param performedObservId ii
     * @return dto
     * @throws RemoteException
     * @throws DataFormatException
     */
    private PerformedMedicalHistoryResultDto createOrUpdateHasPrior(
            PriorTherapiesWebDto newPriorsRec , Ii performedObservId) throws RemoteException,
            DataFormatException {
        PerformedMedicalHistoryResultDto perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
        perMedicalHistoryDTO.setPerformedObservationIdentifier(performedObservId);
        perMedicalHistoryDTO.setStudyProtocolIdentifier(getSpIi());
        perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                PerformedObservationResultTypeCode.HAD_PRIOR_THERAPIES));
        perMedicalHistoryDTO.setResultIndicator(BlConverter.convertToBl(newPriorsRec.getHasPrior()));
        if (newPriorsRec.getIdHasPrior() != null && newPriorsRec.getIdHasPrior().getExtension() != null) {
            perMedicalHistoryDTO.setIdentifier(newPriorsRec.getIdHasPrior());
            performedObservationResultSvc.updatePerformedMedicalHistoryResult(perMedicalHistoryDTO);
        } else {
            performedObservationResultSvc.createPerformedMedicalHistoryResult(perMedicalHistoryDTO);
        }
        return perMedicalHistoryDTO;
    }

    @SuppressWarnings("unchecked")
    private void createOrUpdatePriorTherapyType(List<PriorTherapiesItemWebDto> therapiesDto ,
            Ii performedObserId) throws RemoteException, DataFormatException {
        getUpdatedDesc(therapiesDto);
        PerformedMedicalHistoryResultDto perMedicalHistoryDTO; 
        if (therapiesDto != null && !therapiesDto.isEmpty()) {
                for (PriorTherapiesItemWebDto exitingPriorWebDto : therapiesDto) {
                    perMedicalHistoryDTO = new PerformedMedicalHistoryResultDto();
                    perMedicalHistoryDTO.setPerformedObservationIdentifier(performedObserId);
                    perMedicalHistoryDTO.setStudyProtocolIdentifier(getSpIi());
                    perMedicalHistoryDTO.setTypeCode(CdConverter.convertToCd(
                            PerformedObservationResultTypeCode.PRIOR_THERAPY));
                    perMedicalHistoryDTO.setResultCode(exitingPriorWebDto.getType());
                    perMedicalHistoryDTO.setDescription(exitingPriorWebDto.getDescription());
                    if (exitingPriorWebDto.getId() != null && exitingPriorWebDto.getId().getExtension() != null 
                          && exitingPriorWebDto.getId().getIdentifierName() == null) {
                        performedObservationResultSvc.createPerformedMedicalHistoryResult(perMedicalHistoryDTO);  
                    } else {
                         perMedicalHistoryDTO.setIdentifier(exitingPriorWebDto.getId());
                         performedObservationResultSvc.updatePerformedMedicalHistoryResult(perMedicalHistoryDTO);
                    } 
                    
                }
        }
    }

    /**
     * Calculate the numeric totals.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void calcTotals(PriorTherapiesWebDto db) {
        priors.setTotalRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(db.getList().size()), "Unitary"));

        int chemoCnt = 0;
        for (PriorTherapiesItemWebDto item : db.getList()) {
            if (item.getType().getCode().startsWith("Chemo")) {
                ++chemoCnt;
            }
        }
        priors.setChemoRegimenNum(PqConverter.convertToPq(BigDecimal.valueOf(chemoCnt), "Unitary"));

        db.setHasPrior(priors.getHasPrior());
        db.setTotalRegimenNum(priors.getTotalRegimenNum());
        db.setChemoRegimenNum(priors.getChemoRegimenNum());
    }

    /**
     * Process delete actions.
     *
     * @param db the memory buffer containing the persisted data
     * @throws RemoteException e 
     */
    private void deletesPriorTherapyType() throws RemoteException {
        List<PriorTherapiesItemWebDto> deleteSsessionListOfPriorTherapies = (List<PriorTherapiesItemWebDto>)
            ServletActionContext.getRequest().getSession().getAttribute(DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST);
            if (deleteSsessionListOfPriorTherapies != null) {
                for (PriorTherapiesItemWebDto item : deleteSsessionListOfPriorTherapies) {
                    if (item.getId() != null && item.getId().getExtension() != null 
                            && item.getId().getIdentifierName() != null) {
                        performedObservationResultSvc.delete(item.getId());
                    }
                }
            }
    }

    /**
     * Save changes for all the descriptions.
     *
     * @param db the memory buffer containing the persisted data
     */
    private void getUpdatedDesc(List<PriorTherapiesItemWebDto> toUpdateList) {
        if (toUpdateList != null && !toUpdateList.isEmpty()) {
            for (PriorTherapiesItemWebDto item : toUpdateList) {
                String desc = ServletActionContext.getRequest().getParameter("desc_" + item.getId().getExtension());
                String type = ServletActionContext.getRequest().getParameter("type_" + item.getId().getExtension());
                St descrip = StConverter.convertToSt(desc);
                Cd typeCd =  CdConverter.convertStringToCd(type);
                item.setDescription(descrip);
                item.setType(typeCd);
            }
        }
    }
    /**
     * @return result for next action
     */
    @SuppressWarnings("PMD")
    public String lookup() {
        //TODO test data population - needs real search
        if (searchTherapy != null && searchTherapy.length() > 0) {
            disWebList = new ArrayList<PriorTherapyTypesWebDto>();
            PriorTherapyTypesWebDto temp;

            if ("*".equals(searchTherapy) || "%".equals(searchTherapy) || searchTherapy.toLowerCase().startsWith("c")) {
                temp = new PriorTherapyTypesWebDto();
                temp.setId(new Ii());
                temp.setType(CdConverter.convertStringToCd("Chemo"));
                temp.getId().setExtension("1");
                disWebList.add(temp);
            }

            if ("*".equals(searchTherapy) || "%".equals(searchTherapy) || searchTherapy.toLowerCase().startsWith("d")) {
                temp = new PriorTherapyTypesWebDto();
                temp.setId(new Ii());
                temp.setType(CdConverter.convertStringToCd("Drug 1"));
                temp.getId().setExtension("2");
                disWebList.add(temp);

                temp = new PriorTherapyTypesWebDto();
                temp.setId(new Ii());
                temp.setType(CdConverter.convertStringToCd("Drug 2"));
                temp.getId().setExtension("3");
                disWebList.add(temp);
            }

            if ("*".equals(searchTherapy) || "%".equals(searchTherapy) || searchTherapy.toLowerCase().startsWith("s")) {
                temp = new PriorTherapyTypesWebDto();
                temp.setId(new Ii());
                temp.setType(CdConverter.convertStringToCd("Surgery 1"));
                temp.getId().setExtension("4");
                disWebList.add(temp);

                temp = new PriorTherapyTypesWebDto();
                temp.setId(new Ii());
                temp.setType(CdConverter.convertStringToCd("Surgery 2"));
                temp.getId().setExtension("5");
                disWebList.add(temp);
            }
        }
        return super.execute();
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

    /**
     * @param disWebList the disWebList to set
     */
    public void setDisWebList(List<PriorTherapyTypesWebDto> disWebList) {
        this.disWebList = disWebList;
    }

    /**
     * @return the disWebList
     */
    public List<PriorTherapyTypesWebDto> getDisWebList() {
        return disWebList;
    }

    /**
     * @param searchTherapy the searchTherapy to set
     */
    public void setSearchTherapy(String searchTherapy) {
        this.searchTherapy = searchTherapy;
    }

    /**
     * @return the searchTherapy
     */
    public String getSearchTherapy() {
        return searchTherapy;
    }

    /**
     * @param priorTherapy the priorTherapy to set
     */
    public void setPriorTherapy(PriorTherapyTypesWebDto priorTherapy) {
        this.priorTherapy = priorTherapy;
    }

    /**
     * @return the priorTherapy
     */
    public PriorTherapyTypesWebDto getPriorTherapy() {
        return priorTherapy;
    }

    /**
     * @param lookupItem the lookupItem to set
     */
    public void setLookupItem(String lookupItem) {
        this.lookupItem = lookupItem;
    }

    /**
     * @return the lookupItem
     */
    public String getLookupItem() {
        return lookupItem;
    }
    @SuppressWarnings({ "PMD.ExcessiveMethodLength" })
    private PriorTherapiesWebDto getData() {
        PriorTherapiesWebDto dbPriors = new PriorTherapiesWebDto();      
        dbPriors.setId(null);
        dbPriors.setList(new ArrayList<PriorTherapiesItemWebDto>());
        try {
            List<PerformedObservationDto> paList = performedActivitySvc.getPerformedObservationByStudySubject(
                    getParticipantIi());
            List<PriorTherapiesItemWebDto> priorTherapiesItemWebList 
                    = new ArrayList<PriorTherapiesItemWebDto>();
            PriorTherapiesItemWebDto priorTherapiesItemWebDto;
            for (PerformedObservationDto pp : paList) {
                if (pp.getNameCode() != null && pp.getNameCode().getCode() != null
                        && pp.getNameCode().getCode().equals(ActivityNameCode.PRIOR_THERAPIES.getCode())) {
                    List<PerformedMedicalHistoryResultDto> medicalHisDTOS = performedObservationResultSvc
                            .getPerformedMedicalHistoryResultByPerformedActivity(pp.getIdentifier());
                    dbPriors.setId(pp.getIdentifier());
                    for (PerformedMedicalHistoryResultDto medicalHisDto : medicalHisDTOS) {
                        if (medicalHisDto.getResultIndicator() != null 
                                && medicalHisDto.getResultIndicator().getValue() != null) {
                            dbPriors.setHasPrior(BlConverter.covertToBoolean(medicalHisDto.getResultIndicator()));
                            dbPriors.setIdHasPrior(medicalHisDto.getIdentifier());
                        }
                        if (medicalHisDto.getResultQuantity() != null 
                                && medicalHisDto.getResultQuantity().getValue() != null) {
                            if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_THERAPIES.getCode()
                                    .equalsIgnoreCase(medicalHisDto.getTypeCode().getCode())) {
                                dbPriors.setTotalRegimenNum(medicalHisDto.getResultQuantity());
                                dbPriors.setIdTotalRegimenNum(medicalHisDto.getIdentifier());
                            } 
                            if (PerformedObservationResultTypeCode.NUMBER_OF_PRIOR_CHEMOTHERAPY_REGIMENS.getCode()
                                    .equalsIgnoreCase(medicalHisDto.getTypeCode().getCode())) {
                                dbPriors.setChemoRegimenNum(medicalHisDto.getResultQuantity());
                                dbPriors.setIdChemoRegimenNum(medicalHisDto.getIdentifier());
                            }
                        }
                        if (medicalHisDto.getResultCode() != null && medicalHisDto.getResultCode().getCode() != null) {
                            priorTherapiesItemWebDto = new PriorTherapiesItemWebDto();
                            priorTherapiesItemWebDto.setId(medicalHisDto.getIdentifier());
                            priorTherapiesItemWebDto.setType(medicalHisDto.getResultCode());
                            priorTherapiesItemWebDto.setDescription(medicalHisDto.getDescription());
                            priorTherapiesItemWebList.add(priorTherapiesItemWebDto);   
                        }
                    }
                    dbPriors.setList(priorTherapiesItemWebList);
                    ServletActionContext.getRequest().getSession().setAttribute(
                                SESSION_PRIOR_THERAPIES_ITEM_LIST, priorTherapiesItemWebList);
                    ServletActionContext.getRequest().getSession().setAttribute(
                            DELETE_SESSION_PRIOR_THERAPIES_ITEM_LIST, new ArrayList<PriorTherapiesItemWebDto>());
                }
            }
        } catch (RemoteException e) {
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
        getDisplayTagList().add(getData());
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
                if (delItem.equals(item.getId().getExtension())) {
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
            addActionError("Prior Therapy Type and Description is required.\n");
        }
        if ((toValidateDto.getType() == null || toValidateDto.getType().getCode() == null 
                || toValidateDto.getType().getCode().equals(""))
                && toValidateDto.getDescription() != null && toValidateDto.getDescription().getValue() != null 
                && !toValidateDto.getDescription().getValue().equals("")) {
            addActionError("Prior Therapy Type is required.Modify: Both: Prior Therapy Type"
                    + " and Prior Therapy Description values must be assigned for saving record.\n");
        }
        if (toValidateDto.getType() != null && toValidateDto.getType().getCode() != null
                && !toValidateDto.getType().getCode().equals("") && (toValidateDto.getDescription() == null 
                || toValidateDto.getDescription().getValue() == null 
                || toValidateDto.getDescription().getValue().equals(""))) {
            addActionError("Prior Therapy Description is required. Modify: Both: Prior Therapy Type"
                    + " and Prior Therapy Description values must be assigned for saving record.\n");
        }
    }
}
