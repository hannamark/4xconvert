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
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.dto.TrialHistoryWebDTO;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.exception.PAFieldException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * The Class TrialHistoryAction.
 *
 * @author Anupama Sharma
 * @since 04/16/2009
 */
public final class TrialHistoryAction extends AbstractListEditAction  implements
ServletResponseAware {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1876567890L;

    /** The trial history web dto. */
    private List<TrialHistoryWebDTO> trialHistoryWebDTO;

    /** The trial history wb dto. */
    private TrialHistoryWebDTO trialHistoryWbDto;

    /** The study protocolii. */
    private String studyProtocolii;

    /** The docii. */
    private String docii;

    /** The doc file name. */
    private String docFileName;

    /** The servlet response. */
    private HttpServletResponse servletResponse;

    /**
     * Gets the study protocolii.
     *
     * @return the studyProtocolii
     */
    public String getStudyProtocolii() {
        return studyProtocolii;
    }

    /**
     * Sets the study protocolii.
     *
     * @param studyProtocolii the studyProtocolii to set
     */
    public void setStudyProtocolii(String studyProtocolii) {
        this.studyProtocolii = studyProtocolii;
    }



    /**
     * Gets the docii.
     *
     * @return the docii
     */
    public String getDocii() {
        return docii;
    }



    /**
     * Sets the docii.
     *
     * @param docii the docii to set
     */
    public void setDocii(String docii) {
        this.docii = docii;
    }



    /**
     * Gets the doc file name.
     * @return the docFileName
     */
    public String getDocFileName() {
        return docFileName;
    }



    /**
     * Sets the doc file name.
     * @param docFileName the docFileName to set
     */
    public void setDocFileName(String docFileName) {
    this.docFileName = docFileName;
    }



    /**
     * Gets the servlet response.
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
    return servletResponse;
    }



   /**
    * Sets the servlet response.
    * @param servletResponse the servletResponse to set
    */
    public void setServletResponse(HttpServletResponse servletResponse) {
      this.servletResponse = servletResponse;
    }



    /**
     * Load list form.
     * @throws PAException exception
     */
    @Override
    @SuppressWarnings({"PMD" })
    protected void loadListForm() throws PAException {
        Ii studyProtocolIi =
                (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);

        StudyProtocolDTO spDTO = studyProtocolSvc.getStudyProtocol(studyProtocolIi);
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS , 0);
        List<StudyProtocolDTO> spList;
        try {
            spList = studyProtocolSvc.search(spDTO, limit);
        } catch (TooManyResultsException e) {
             throw new PAException(e.getMessage());
        }
        List<TrialHistoryWebDTO> trialHistoryWebdtos = new ArrayList<TrialHistoryWebDTO>();
        if (spList != null) {
            for (StudyProtocolDTO sp : spList) {
                TrialHistoryWebDTO dto = new TrialHistoryWebDTO(sp);
                dto.setDocuments(getDocuments(sp));
                trialHistoryWebdtos.add(dto);
            }
        }
        setTrialHistoryWebDTO(trialHistoryWebdtos);

    }



    /**
     * Gets the documents.
     * @param sp the sp
     * @return the documents
     * @throws PAException the PA exception
     */
    private String getDocuments(StudyProtocolDTO sp)throws PAException {
    StringBuffer documents = new StringBuffer();
    List<DocumentDTO>documentDTO = documentSvc.getDocumentsByStudyProtocol(sp.getIdentifier());
    for (DocumentDTO docDto : documentDTO) {
        String fileName = StConverter.convertToString(docDto.getFileName());
        documents.append("<a href='#' onclick=\"handlePopup('");
        documents.append(docDto.getStudyProtocolIdentifier().getExtension());
        documents.append("','");
        documents.append(docDto.getIdentifier().getExtension());
        documents.append("','");
        documents.append(docDto.getFileName().getValue());
        documents.append("')\">");
        documents.append(CdConverter.convertCdToString(docDto.getTypeCode()));
        documents.append("&nbsp;- <B>");
        documents.append(fileName);
        documents.append("</B></a><br>");

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

          StringBuffer fileName = new StringBuffer();
          fileName.append(spDTO.getAssignedIdentifier().getExtension()).
          append('-').append(docDTO.getFileName().getValue());

          ByteArrayInputStream bStream = new ByteArrayInputStream(docDTO.getText().getData());

          servletResponse.setContentType("application/octet-stream");
          servletResponse.setContentLength(docDTO.getText().getData().length);
          servletResponse.setHeader("Content-Disposition", "attachment; filename=\"" + fileName.toString() + "\"");
          servletResponse.setHeader("Pragma", "public");
          servletResponse.setHeader("Cache-Control", "max-age=0");

          int data;
          ServletOutputStream out = servletResponse.getOutputStream();
          while ((data = bStream.read()) != -1) {
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
        addActionError(e.getMessage());
        ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
        return super.execute();
    }
    return NONE;
    }

    /**
     * Update.
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
     * Load edit form.
     *
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


    /**
     * Enforce business rules.
     */
    private void enforceBusinessRules() {
        if (PAUtil.isEmpty(trialHistoryWbDto.getAmendmentDate())) {
          addFieldError("trialHistoryWbDto.amendmentDate", getText("Amendment Date must be Entered/Selected"));
        }
        if (PAUtil.isEmpty(trialHistoryWbDto.getAmendmentReasonCode())) {
          addFieldError("trialHistoryWbDTO.amendmentReasonCode", getText("Reason code must be Selected"));
        }
        if (!isValidDate(trialHistoryWbDto.getAmendmentDate())) {
           addFieldError("trialHistoryWbDto.amendmentDate",
                  getText("Please enter the Date in the correct format MM/dd/yyyy"));
        }
    }


    /**
     * Checks if String is valid date.
     * @param dateStr the date str
     * @return true, if is valid date
     */
    private boolean isValidDate(String dateStr) {
      if (dateStr == null) {
         return false;
      }
      //set the format to use as a constructor argument
      SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy" , Locale.getDefault());
      if (dateStr.trim().length() != dateFormat.toPattern().length()) {
        return false;
      }
      dateFormat.setLenient(false);
      try {
        //parse the dateStr parameter
        dateFormat.parse(dateStr.trim());
        }  catch (ParseException pe) {
         return false;
       }
         return true;
    }

    /**
     * Gets the trial history web dto.
     *
     * @return the trialHistoryWebDTO
     */
    public List<TrialHistoryWebDTO> getTrialHistoryWebDTO() {
        return trialHistoryWebDTO;
    }


    /**
     * Sets the trial history web dto.
     *
     * @param trialHistoryWebDTO the trialHistoryWebDTO to set
     */
    public void setTrialHistoryWebDTO(List<TrialHistoryWebDTO> trialHistoryWebDTO) {
        this.trialHistoryWebDTO = trialHistoryWebDTO;
    }

    /**
     * Gets the trial history wb dto.
     *
     * @return the trialHistoryWbDto
     */
    public TrialHistoryWebDTO getTrialHistoryWbDto() {
        return trialHistoryWbDto;
    }


    /**
     * Sets the trial history wb dto.
     *
     * @param trialHistoryWbDto the trialHistoryWbDto to set
     */
    public void setTrialHistoryWbDto(TrialHistoryWebDTO trialHistoryWbDto) {
        this.trialHistoryWbDto = trialHistoryWbDto;
    }

}
