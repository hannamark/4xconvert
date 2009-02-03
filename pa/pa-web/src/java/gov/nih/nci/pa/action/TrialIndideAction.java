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
import gov.nih.nci.pa.dto.StudyIndldeWebDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.Validation;
/**
 * @author Hong Gao
 *
 */
@Validation
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.ExcessiveMethodLength" })
public class TrialIndideAction extends ActionSupport {
    private static final String QUERY_RESULT = "query";
    private static final String EDIT_RESULT = "edit";
    private static final Logger LOG  = Logger.getLogger(TrialIndideAction.class);
    private StudyIndldeWebDTO studyIndldeWebDTO = new StudyIndldeWebDTO();
    private List<StudyIndldeWebDTO> studyIndideList;
    private IndIdeHolder holder = new IndIdeHolder();
    private Long cbValue;
    private String page;
    
    /**  
     * @return result
     */
    public String displayJs() {
        return SUCCESS;
    }
   
    /**  
     * @return result
     */
    public String query()  {
        LOG.info("Entering query");
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);             
            List<StudyIndldeDTO> isoList = PaRegistry.getStudyIndldeService().getByStudyProtocol(studyProtocolIi);
            if (!(isoList.isEmpty())) {                
                studyIndideList = new ArrayList<StudyIndldeWebDTO>();                
              for (StudyIndldeDTO dto : isoList) {
                  studyIndideList.add(new StudyIndldeWebDTO(dto));
              }
            } else {
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, 
                        getText("No IND/IDE records exist on the trial"));
            }
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return QUERY_RESULT;
        }
    }
    
    /**
     * @return result
     */
    public String create()  {
        
        LOG.info("Entering create");
        if (hasFieldErrors()) {
            return ERROR;
        }
        try {            
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II);
            StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();
            studyIndldeDTO.setStudyProtocolIi(studyProtocolIi);
           /* if (holder.getGroup4().equalsIgnoreCase("Yes")) {
              holder.setGroup4("true");
            } else {
              holder.setGroup4("false");
            }*/
            studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(holder.getGroup4())));
            studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(holder.getExpandedStatus()));
            studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd(holder.getSubCat()));
            studyIndldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(holder.getHolderType()));
            studyIndldeDTO.setIndldeNumber(StConverter.convertToSt(holder.getIndidenumber()));
            if (holder.getHolderType().equalsIgnoreCase("NIH")) {
                studyIndldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenihselectedvalue()));
             }
            if (holder.getHolderType().equalsIgnoreCase("NCI")) {
                studyIndldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenciselectedvalue()));
            }
            studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(holder.getGroup3()));      
            PaRegistry.getStudyIndldeService().create(studyIndldeDTO);
            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
            return QUERY_RESULT;
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    /**
     * @return result
     */
    public String update()  {
        
        LOG.info("Entering update");
        if (hasFieldErrors()) {
            return ERROR;
        }
        try { 
            Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
            getAttribute(Constants.STUDY_PROTOCOL_II); 
            StudyIndldeDTO studyIndldeDTO = new StudyIndldeDTO();            
            studyIndldeDTO = PaRegistry.getStudyIndldeService().get(IiConverter.convertToIi(cbValue)); 
            studyIndldeDTO.setStudyProtocolIi(studyProtocolIi);
            if (holder.getGroup4().equalsIgnoreCase("Yes")) {
              holder.setGroup4("true");
            } else {
              holder.setGroup4("false");
            }
            studyIndldeDTO.setExpandedAccessIndicator(BlConverter.convertToBl(Boolean.valueOf(holder.getGroup4())));
            studyIndldeDTO.setExpandedAccessStatusCode(CdConverter.convertStringToCd(holder.getExpandedStatus()));
            studyIndldeDTO.setGrantorCode(CdConverter.convertStringToCd(holder.getSubCat()));
            studyIndldeDTO.setHolderTypeCode(CdConverter.convertStringToCd(holder.getHolderType()));
            studyIndldeDTO.setIndldeNumber(StConverter.convertToSt(holder.getIndidenumber()));
            if (holder.getHolderType().equalsIgnoreCase("NIH")) {
                studyIndldeDTO.setNihInstHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenihselectedvalue()));
             }
            if (holder.getHolderType().equalsIgnoreCase("NCI")) {
                studyIndldeDTO.setNciDivProgHolderCode(CdConverter.convertStringToCd(
                        holder.getProgramcodenciselectedvalue()));
            }
            studyIndldeDTO.setIndldeTypeCode(CdConverter.convertStringToCd(holder.getGroup3()));  
            PaRegistry.getStudyIndldeService().update(studyIndldeDTO);
            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
            return QUERY_RESULT;    

        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**
     * @return result
     */
    public String delete()  {

        LOG.info("Entering delete from SubGroupsAction");
        try {
            PaRegistry.getStudyIndldeService().delete(IiConverter.convertToIi(cbValue));
            query();
            ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
            return QUERY_RESULT;

        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return SUCCESS;
        }
    }

    /**  
     * @return result
     */
    public String edit()  {
        LOG.info("Entering editValues");
        try {
            StudyIndldeDTO studyIndlde = PaRegistry.getStudyIndldeService().get(IiConverter.convertToIi(cbValue)); 
            holder = new IndIdeHolder(studyIndlde);
            return EDIT_RESULT;    
        } catch (Exception e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }


    /**
     * @return cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue cbValue
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return page
     */
    public String getPage() {
        return page;
    }

    /**
     * @param page page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     * @return the studyIndldeWebDTO
     */
    public StudyIndldeWebDTO getStudyIndldeWebDTO() {
        return studyIndldeWebDTO;
    }

    /**
     * @param studyIndldeWebDTO the studyIndldeWebDTO to set
     */
    public void setStudyIndldeWebDTO(StudyIndldeWebDTO studyIndldeWebDTO) {
        this.studyIndldeWebDTO = studyIndldeWebDTO;
    }

    /**
     * @return the studyIndideList
     */
    public List<StudyIndldeWebDTO> getStudyIndideList() {
        return studyIndideList;
    }

    /**
     * @param studyIndideList the studyIndideList to set
     */
    public void setStudyIndideList(List<StudyIndldeWebDTO> studyIndideList) {
        this.studyIndideList = studyIndideList;
    }

    /**
     * @return the holder
     */
    public IndIdeHolder getHolder() {
        return holder;
    }

    /**
     * @param holder the holder to set
     */
    public void setHolder(IndIdeHolder holder) {
        this.holder = holder;
    }
}
