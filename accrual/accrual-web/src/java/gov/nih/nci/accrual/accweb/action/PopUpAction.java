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
package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.accweb.dto.util.DiseaseWebDTO;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DiseaseParentDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
* @author Hugh Reinhart
* @since 11/31/2008
* copyright NCI 2008.  All rights reserved.
* This code may not be used without the express written permission of the
* copyright holder, NCI.
*/
@SuppressWarnings("PMD.CyclomaticComplexity")
public class PopUpAction extends AbstractAccrualAction {
    private static final long serialVersionUID = 8987838321L;

    private static final Logger LOG = Logger.getLogger(PopUpAction.class);
    private static final int MAX_SEARCH_RESULT_SIZE = 500;

    private String searchName;
    private String includeSynonym;
    private String exactMatch;
    private List<DiseaseWebDTO> disWebList = new ArrayList<DiseaseWebDTO>();


    private void loadResultList() {
        disWebList.clear();
        String tName = ServletActionContext.getRequest().getParameter("searchName");
        String includeSyn = ServletActionContext.getRequest().getParameter("includeSynonym");
        String exactMat = ServletActionContext.getRequest().getParameter("exactMatch");

        if (PAUtil.isEmpty(tName)) {
            String message = "Please enter at least one search criteria";
            addActionError(message);
            ServletActionContext.getRequest().setAttribute("failureMessage", message);
            return;
        }

        DiseaseDTO criteria = new DiseaseDTO();
        criteria.setPreferredName(StConverter.convertToSt(tName));
        criteria.setIncludeSynonym(StConverter.convertToSt(includeSyn));
        criteria.setExactMatch(StConverter.convertToSt(exactMat));

        List<DiseaseDTO> diseaseList = null;
        try {
            diseaseList = diseaseSvc.search(criteria);
        } catch (Exception e) {
            error("Exception while loading disease results.", e);
            return;
        }
        if (diseaseList.size() > MAX_SEARCH_RESULT_SIZE) {
            error("Too many diseases found.  Please narrow search.");
            return;
        }
        for (DiseaseDTO disease : diseaseList) {
            DiseaseWebDTO newRec = new DiseaseWebDTO();
            newRec.setDiseaseIdentifier(IiConverter.convertToString(disease.getIdentifier()));
            newRec.setPreferredName(StConverter.convertToString(disease.getPreferredName()));
            newRec.setCode(StConverter.convertToString(disease.getDiseaseCode()));
            newRec.setConceptId(StConverter.convertToString(disease.getNtTermIdentifier()));
            newRec.setMenuDisplayName(StConverter.convertToString(disease.getMenuDisplayName()));
            getDisWebList().add(newRec);
        }
        loadParentPreferredNames();
    }

    private void loadParentPreferredNames() {
        Ii[] iis = new Ii[disWebList.size()];
        int x = 0;
        for (DiseaseWebDTO dto : disWebList) {
            iis[x++] = IiConverter.convertToIi(dto.getDiseaseIdentifier());
        }
        List<DiseaseParentDTO> dpList;
        try {
            dpList = diseaseParentSvc.getByChildDisease(iis);
        } catch (Exception e) {
            error("Exception thrown while getting disease parents.", e);
            return;
        }
        HashMap<String, String> childParent = new HashMap<String, String>();
        for (DiseaseParentDTO dp : dpList) {
            String child = IiConverter.convertToString(dp.getIdentifier());
            DiseaseDTO parentDTO;
            try {
                parentDTO = diseaseSvc.get(dp.getParentDiseaseIdentifier());
            } catch (Exception e) {
                error("Exception throw while getting disease name for parent.", e);
                return;
            }
            if (childParent.containsKey(child)) {
                childParent.put(child, childParent.get(child) + ", "
                        + StConverter.convertToString(parentDTO.getPreferredName()));
            } else {
                childParent.put(child, StConverter.convertToString(parentDTO.getPreferredName()));
            }
        }
        for (DiseaseWebDTO dto : disWebList) {
            dto.setParentPreferredName(childParent.get(dto.getDiseaseIdentifier()));
        }
    }

    private void error(String errMsg, Throwable t) {
        LOG.error(errMsg, t);
        addActionError(errMsg);
        ServletActionContext.getRequest().setAttribute("failureMessage", errMsg);
    }

    private void error(String errMsg) {
        error(errMsg, null);
    }

    /**
     * @return result
     */
    public String displayList() {
        loadResultList();
        ServletActionContext.getRequest().setAttribute("disWebList", disWebList);
        return SUCCESS;
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
}
