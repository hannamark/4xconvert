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

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.DiseaseWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseDTO;
import gov.nih.nci.pa.iso.dto.PDQDiseaseParentDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseParentServiceRemote;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
* @author Hugh Reinhart
* @since 11/31/2008
*/
public class PopUpDisAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 8987838321L;

    private static final Logger LOG = Logger.getLogger(PopUpDisAction.class);

    private PDQDiseaseParentServiceRemote pdqDiseaseParentService;
    private PDQDiseaseServiceLocal pdqDiseaseService;
    private StudyDiseaseServiceLocal studyDiseaseService;

    private String searchName;
    private String includeSynonym;
    private String exactMatch;
    private List<DiseaseWebDTO> disWebList;
    private Long diseaseId;
    private boolean includeXml = true;
    private List<Long> pdqDiseases = new ArrayList<Long>();
    private String diseaseIds; 

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        pdqDiseaseParentService = PaRegistry.getDiseaseParentService();
        pdqDiseaseService = PaRegistry.getDiseaseService();
        studyDiseaseService = PaRegistry.getStudyDiseaseService();
    }
    
    
    /**
     * Gets the PDQDiseases for the disease section as a JSON collection.
     * @return The result name
     * @throws JSONException JSON Translation exception
     */
    public String getDiseaseTree() throws JSONException {
        List<PDQDiseaseNode> diseaseTree = PaRegistry.getDiseaseService().getDiseaseTree();
        return JSONUtil.serialize(diseaseTree);
    }

    /**
     * Search the diseases according to the user criteria and loads them in the diseaseWebList.
     * @return The result name
     */
    public String displayList() {
        disWebList = new ArrayList<DiseaseWebDTO>();
        if (StringUtils.isEmpty(searchName)) {
            String message = "Please enter at least one search criteria";
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, message);
            return "displayList";
        }
        try {
        Map<Ii, StudyDiseaseDTO> existingDiseaseIis = getExistingDiseases(getStudyProtocolIi());
        List<PDQDiseaseDTO> pdqDiseaseDTOs = searchDiseases();
        disWebList = getDiseaseWebList(existingDiseaseIis, pdqDiseaseDTOs);
        loadParentPreferredNames();
        } catch (PAException e) {
            error(e.getMessage(), e);
        } catch (Exception e) {
            error("Exception while loading disease results.", e);
        }
        return "displayList";
    }
    
    /**
     * 
     * @return The disease widget jsp
     */
    public String displayDiseaseWidget() {
        return "diseaseWidget";
    }
    

    /**
     * Gets the study protocol Ii from the session.
     * @return The study protocol Ii
     */
    Ii getStudyProtocolIi() {
        HttpSession session = ServletActionContext.getRequest().getSession();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) session.getAttribute(Constants.TRIAL_SUMMARY);
        return IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
    }

    /**
     * Gets the Map of StudyDiseaseDTO keyed by disease Ii.
     * @param spIi The study protocol Ii
     * @return The Map of StudyDiseaseDTO keyed by disease Ii.
     * @throws PAException If an error occurs
     */
    Map<Ii, StudyDiseaseDTO> getExistingDiseases(Ii spIi) throws PAException {
        Map<Ii, StudyDiseaseDTO> diseaseIis = new HashMap<Ii, StudyDiseaseDTO>();
        List<StudyDiseaseDTO> studyDiseaseDTOs = studyDiseaseService.getByStudyProtocol(spIi);
        for (StudyDiseaseDTO studyDiseaseDTO : studyDiseaseDTOs) {
            diseaseIis.put(studyDiseaseDTO.getDiseaseIdentifier(), studyDiseaseDTO);
        }
        return diseaseIis;
    }

    /**
     * Search the diseases corresponding to the criteria entered by the user.
     * @return The list of diseases corresponding to the criteria entered by the user
     * @throws PAException If an error occurs.
     */
    List<PDQDiseaseDTO> searchDiseases() throws PAException {
        PDQDiseaseDTO criteria = new PDQDiseaseDTO();
        criteria.setPreferredName(StConverter.convertToSt(searchName));
        criteria.setIncludeSynonym(StConverter.convertToSt(includeSynonym));
        criteria.setExactMatch(StConverter.convertToSt(exactMatch));
        return pdqDiseaseService.search(criteria);
    }

    /**
     * Get the disease webdto list.
     * @param existingDiseases The Map of disease Iis to StudyDisease for diseases already associated with the study
     *        protocol
     * @param searchResult The list of diseases corresponding to the criteria entered by the user
     * @return The disease webdto list.
     */
    List<DiseaseWebDTO> getDiseaseWebList(Map<Ii, StudyDiseaseDTO> existingDiseases, List<PDQDiseaseDTO> searchResult) {
        List<DiseaseWebDTO> result = new ArrayList<DiseaseWebDTO>();
        for (PDQDiseaseDTO disease : searchResult) {
            DiseaseWebDTO diseaseWebDTO = new DiseaseWebDTO();
            diseaseWebDTO.setDiseaseIdentifier(IiConverter.convertToString(disease.getIdentifier()));
            diseaseWebDTO.setPreferredName(StConverter.convertToString(disease.getPreferredName()));
            diseaseWebDTO.setCode(StConverter.convertToString(disease.getDiseaseCode()));
            diseaseWebDTO.setConceptId(StConverter.convertToString(disease.getNtTermIdentifier()));
            diseaseWebDTO.setMenuDisplayName(StConverter.convertToString(disease.getDisplayName()));
            diseaseWebDTO.setSelected(existingDiseases.containsKey(disease.getIdentifier()));
            if (diseaseWebDTO.isSelected()) {
                StudyDiseaseDTO studyDiseaseDTO = existingDiseases.get(disease.getIdentifier());
                diseaseWebDTO.setStudyDiseaseIdentifier(IiConverter.convertToString(studyDiseaseDTO.getIdentifier()));
                boolean xmlIndicator = !ISOUtil.isBlNull(studyDiseaseDTO.getCtGovXmlIndicator())
                        && BlConverter.convertToBoolean(studyDiseaseDTO.getCtGovXmlIndicator());
                diseaseWebDTO.setCtGovXmlIndicator((xmlIndicator) ? "Yes" : "No");
            }
            result.add(diseaseWebDTO);
        }
        return result;
    }

    /**
     * load the parent disease preferred names.
     */
    void loadParentPreferredNames() {
        List<PDQDiseaseParentDTO> dpList = getDiseaseParents();
        HashMap<String, String> childParent = new HashMap<String, String>();
        for (PDQDiseaseParentDTO dp : dpList) {
            String child = IiConverter.convertToString(dp.getIdentifier());
            PDQDiseaseDTO parentDTO;
            try {
                parentDTO = pdqDiseaseService.get(dp.getParentDiseaseIdentifier());
            } catch (Exception e) {
                error("Exception thrown while getting disease name for parent.", e);
                return;
            }
            if (childParent.containsKey(child)) {
                childParent.put(child,
                                childParent.get(child) + ", "
                                        + StConverter.convertToString(parentDTO.getPreferredName()));
            } else {
                childParent.put(child, StConverter.convertToString(parentDTO.getPreferredName()));
            }
        }
        for (DiseaseWebDTO dto : disWebList) {
            dto.setParentPreferredName(childParent.get(dto.getDiseaseIdentifier()));
        }
    }

    /**
     * Get the parent diseases of the diseases already in the disease web list.
     * @return The parent diseases of the diseases already in the disease web list.
     */
    @SuppressWarnings("deprecation")
    List<PDQDiseaseParentDTO> getDiseaseParents() {
        Ii[] iis = new Ii[disWebList.size()];
        int x = 0;
        for (DiseaseWebDTO dto : disWebList) {
            iis[x++] = IiConverter.convertToIi(dto.getDiseaseIdentifier());
        }
        try {
            return pdqDiseaseParentService.getByChildDisease(iis);
        } catch (Exception e) {
            error("Exception thrown while getting disease parents.", e);
            return new ArrayList<PDQDiseaseParentDTO>();
        }
    }

    private void error(String errMsg, Throwable t) {
        LOG.error(errMsg, t);
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, errMsg);
    }

    /**
     * Add a disease to the study protocol.
     * @return result
     */
    public String addDisease() {
        try {
            StudyDiseaseDTO sdDto = getStudyDisease();
            studyDiseaseService.create(sdDto);
        } catch (Exception e) {
            addActionError(e.getMessage());
        }
        return displayList();
    }
    
    /**
     * Creates the new study disease dto to add.
     * @return the new study disease dto.
     */
    @SuppressWarnings("deprecation")
    StudyDiseaseDTO getStudyDisease() {
        StudyDiseaseDTO sdDto = new StudyDiseaseDTO();
        sdDto.setDiseaseIdentifier(IiConverter.convertToIi(diseaseId));
        sdDto.setCtGovXmlIndicator(BlConverter.convertToBl(includeXml));
        sdDto.setStudyProtocolIdentifier(getStudyProtocolIi());
        return sdDto;
    }    
    

    /**
     * Add diseases to the study protocol.
     * @return result
     */
    public String addDiseases() {
        if (StringUtils.isEmpty(diseaseIds)) {
            diseaseIds = ServletActionContext.getRequest().getParameter("selectedDiseaseIds");
        }
        if (StringUtils.isNotEmpty(diseaseIds)) {
            String[] tempArr = StringUtils.splitByWholeSeparator(diseaseIds, ",");
            List<Long> diesaseIdList = new ArrayList<Long>();
            for (String id : tempArr) { 
                diesaseIdList.add(Long.parseLong(id));
            }
            setPdqDiseases(diesaseIdList);
            for (Long id : getPdqDiseases()) {
                try {
                    studyDiseaseService.create(getStudyDisease(id));
                } catch (Exception e) {
                    LOG.debug(e);
                } 
            }
        }
        return SUCCESS;
    }
    

    /**
     * Creates the new study disease dto with the provided diseaseId.
     * @return the new study disease dto.
     */
    @SuppressWarnings("deprecation")
    StudyDiseaseDTO getStudyDisease(Long disId) {
        StudyDiseaseDTO sdDto = new StudyDiseaseDTO();
        sdDto.setDiseaseIdentifier(IiConverter.convertToIi(disId));
        sdDto.setCtGovXmlIndicator(BlConverter.convertToBl(includeXml));
        sdDto.setStudyProtocolIdentifier(getStudyProtocolIi());
        return sdDto;
    }

    /**
     * Remove a disease from the study protocol.
     * @return result
     */
    public String remove() {
        try {
            studyDiseaseService.delete(IiConverter.convertToIi(diseaseId));
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return displayList();
    }

    /**
     * @return the searchName
     */
    public String getSearchName() {
        return searchName;
    }

    /**
     * @param searchName the searchName to set
     */
    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    /**
     * @return the includeSynonym
     */
    public String getIncludeSynonym() {
        return includeSynonym;
    }

    /**
     * @param includeSynonym the includeSynonym to set
     */
    public void setIncludeSynonym(String includeSynonym) {
        this.includeSynonym = includeSynonym;
    }

    /**
     * @return the exactMatch
     */
    public String getExactMatch() {
        return exactMatch;
    }

    /**
     * @param exactMatch the exactMatch to set
     */
    public void setExactMatch(String exactMatch) {
        this.exactMatch = exactMatch;
    }

    /**
     * @return the disWebList
     */
    public List<DiseaseWebDTO> getDisWebList() {
        return disWebList;
    }

    /**
     * @param disWebList the disWebList to set
     */
    public void setDisWebList(List<DiseaseWebDTO> disWebList) {
        this.disWebList = disWebList;
    }

    /**
     * @return the diseaseId
     */
    public Long getDiseaseId() {
        return diseaseId;
    }

    /**
     * @param diseaseId the diseaseId to set
     */
    public void setDiseaseId(Long diseaseId) {
        this.diseaseId = diseaseId;
    }

    /**
     * @return the includeXml
     */
    public boolean isIncludeXml() {
        return includeXml;
    }

    /**
     * @param includeXml the includeXml to set
     */
    public void setIncludeXml(boolean includeXml) {
        this.includeXml = includeXml;
    }

    /**
     * @param pdqDiseaseParentService the pdqDiseaseParentService to set
     */
    public void setPdqDiseaseParentService(PDQDiseaseParentServiceRemote pdqDiseaseParentService) {
        this.pdqDiseaseParentService = pdqDiseaseParentService;
    }
    
    /**
     * @param pdqDiseaseService the pdqDiseaseService to set
     */
    public void setPdqDiseaseService(PDQDiseaseServiceLocal pdqDiseaseService) {
        this.pdqDiseaseService = pdqDiseaseService;
    }

    /**
     * @param studyDiseaseService the studyDiseaseService to set
     */
    public void setStudyDiseaseService(StudyDiseaseServiceLocal studyDiseaseService) {
        this.studyDiseaseService = studyDiseaseService;
    }

    /**
     * @return the pdqDiseases
     */
    public List<Long> getPdqDiseases() {
        return pdqDiseases;
    }

    /**
     * @param pdqDiseaseIds the pdqDiseases to set
     */
    public void setPdqDiseases(List<Long> pdqDiseaseIds) {
        List<Long> result = new ArrayList<Long>();
        if (CollectionUtils.isNotEmpty(pdqDiseaseIds)) {
            Set<Long> existingIds = new HashSet<Long>();
            for (Long id : pdqDiseaseIds) {
                if (id != null && !existingIds.contains(id)) {
                    result.add(id);
                    existingIds.add(id);
                }
            }
        }
        this.pdqDiseases = result;
    }

    /**
     * 
     * @return comma separated diseaseids
     */
    public String getDiseaseIds() {
        return diseaseIds;
    }

    /**
     * 
     * @param diseaseIds comma separated diseaseids to set
     */
    public void setDiseaseIds(String diseaseIds) {
        this.diseaseIds = diseaseIds;
    }
}
