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
import gov.nih.nci.pa.dto.TrialHistoryWebDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
* @author Anupama Sharma
* @since 04/16/2009
*/
public final class TrialHistoryAction extends AbstractListEditAction  implements
ServletResponseAware {
    private static final long serialVersionUID = 1876567890L;
  
    private List<TrialHistoryWebDTO> trialHistoryWebDTO;
    private TrialHistoryWebDTO trialHistoryWbDto;
    private String studyProtocolii;
    private String docii;
    private String docFileName;
    private HttpServletResponse servletResponse;
    
   
 


/**
* @return the studyProtocolii
*/
public String getStudyProtocolii() {
return studyProtocolii;
}



/**
* @param studyProtocolii the studyProtocolii to set
*/
public void setStudyProtocolii(String studyProtocolii) {
this.studyProtocolii = studyProtocolii;
}



/**
* @return the docii
*/
public String getDocii() {
return docii;
}



/**
* @param docii the docii to set
*/
public void setDocii(String docii) {
this.docii = docii;
}



/**
* @return the docFileName
*/
public String getDocFileName() {
return docFileName;
}



/**
* @param docFileName the docFileName to set
*/
public void setDocFileName(String docFileName) {
this.docFileName = docFileName;
}



/**
* @return the servletResponse
*/
public HttpServletResponse getServletResponse() {
return servletResponse;
}



   /**
    * @param servletResponse the servletResponse to set
    */
    public void setServletResponse(HttpServletResponse servletResponse) {
      this.servletResponse = servletResponse;
    }



    /**
     * @throws PAException exception
     */
    @Override
    protected void loadListForm() throws PAException {
    
    Ii studyProtocolIi = (Ii) ServletActionContext.getRequest().getSession().
        getAttribute(Constants.STUDY_PROTOCOL_II);

        StudyProtocolDTO spDTO = studyProtocolSvc.getStudyProtocol(studyProtocolIi);
        List<StudyProtocolDTO>spList = studyProtocolSvc.getStudyProtocol(spDTO);
        List<TrialHistoryWebDTO> trialHistoryWebdtos = new ArrayList<TrialHistoryWebDTO>();
        for (StudyProtocolDTO sp : spList) {
            TrialHistoryWebDTO dto =  new TrialHistoryWebDTO(sp);
            dto.setDocuments(getDocuments(sp));
            trialHistoryWebdtos.add(dto);
        }
        setTrialHistoryWebDTO(trialHistoryWebdtos);
           
        }
    
    
    
    /**
     * @param sp
     * @return
     * @throws PAException
     */
    private String getDocuments(StudyProtocolDTO sp)throws PAException {
    StringBuffer documents = new StringBuffer();
    List<DocumentDTO>documentDTO = documentSvc.getDocumentsByStudyProtocol(sp.getIdentifier());
    for (DocumentDTO docDto : documentDTO) {
        String fileName = StConverter.convertToString(docDto.getFileName());
        documents.append("<a href='#' onclick=\"handlePopup('");
        documents.append(docDto.getStudyProtocolIi().getExtension());
        documents.append("','");
        documents.append(docDto.getIdentifier().getExtension());
        documents.append("','");
        documents.append(docDto.getFileName().getValue());
        documents.append("')\">");
        documents.append(fileName);
        documents.append("</a>&nbsp;");
                
      } 
      return documents.toString();
    }
    
    /**
     * Open.
     * 
     * @return the string
     * 
     * @throws PAException the PA exception
     */
    public String open() throws PAException {
    try {
    DocumentDTO  docDTO =
            PaRegistry.getDocumentService().get(IiConverter.convertToIi(getDocii()));
        
    StudyProtocolDTO spDTO = studyProtocolSvc.getStudyProtocol(IiConverter.convertToIi(getStudyProtocolii()));
        StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
        sb.append(File.separator).append(spDTO.getAssignedIdentifier().getExtension()).append(File.separator).
            append(docDTO.getIdentifier().getExtension()).append('-').append(getDocFileName());

        File downloadFile = new File(sb.toString());
        StringBuffer fileName = new StringBuffer();
        fileName.append(spDTO.getAssignedIdentifier().getExtension()).
        append('-').append(docDTO.getFileName().getValue());

        FileInputStream fileToDownload = new FileInputStream(downloadFile);

        servletResponse.setContentType("application/octet-stream");
        servletResponse.setContentLength(fileToDownload.available());
        servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
        servletResponse.setHeader("Pragma", "public");
        servletResponse.setHeader("Cache-Control", "max-age=0");


        int data;
        ServletOutputStream out = servletResponse.getOutputStream();
        while ((data = fileToDownload.read()) != -1) {
            out.write(data);
        }
        out.flush();
        out.close();
          
        
    } catch (FileNotFoundException err) {
        LOG.error("TrialHistoryAction failed with FileNotFoundException: "
                + err);
        addActionError(err.getMessage());
        return super.execute();
        
    } catch (Exception e) {
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        return super.execute();
    }
    return NONE;
    }
   
    /**
     * @return action result
     * @throws PAException exception
     */
    @Override
    public String update() throws PAException {
        try {
            enforceBusinessRules();
            if (hasFieldErrors()) {
                return AR_EDIT;
              }
            StudyProtocolDTO sp = studyProtocolSvc.getStudyProtocol(
                IiConverter.convertToIi(trialHistoryWbDto.getIdentifier()));
           studyProtocolSvc.updateStudyProtocol(trialHistoryWbDto.getIsoDto(sp));
        } catch (PAFieldException e) {
        addActionError(e.getMessage());
            return AR_EDIT;
        } catch (PAException e) {
            addActionError(e.getMessage());
            return AR_EDIT;
        }
        return super.update();
    }

    /**
     * @throws PAException exception
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (CA_EDIT.equals(getCurrentAction())) {
         setTrialHistoryWbDto(new TrialHistoryWebDTO(
           studyProtocolSvc.getStudyProtocol(IiConverter.convertToIi(getSelectedRowIdentifier()))));
        } else {
        setTrialHistoryWbDto(new TrialHistoryWebDTO());
        }
    }


    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(trialHistoryWbDto.getAmendmentDate())) {
          addFieldError("trialHistoryWbDto.amendmentDate", getText("Amendment Date must be Entered/Selected"));
        }
        if (PAUtil.isEmpty(trialHistoryWbDto.getAmendmentReasonCode())) {
          addFieldError("trialHistoryWbDTO.amendmentReasonCode", getText("Reason code must be Selected"));
        }
    } 
/**
* @return the trialHistoryWebDTO
*/
public List<TrialHistoryWebDTO> getTrialHistoryWebDTO() {
return trialHistoryWebDTO;
}


/**
* @param trialHistoryWebDTO the trialHistoryWebDTO to set
*/
public void setTrialHistoryWebDTO(List<TrialHistoryWebDTO> trialHistoryWebDTO) {
this.trialHistoryWebDTO = trialHistoryWebDTO;
}

/**
 * @return the trialHistoryWbDto
 */
public TrialHistoryWebDTO getTrialHistoryWbDto() {
return trialHistoryWbDto;
}


/**
 * @param trialHistoryWbDto the trialHistoryWbDto to set
 */
public void setTrialHistoryWbDto(TrialHistoryWebDTO trialHistoryWbDto) {
this.trialHistoryWbDto = trialHistoryWbDto;
}
   
}
