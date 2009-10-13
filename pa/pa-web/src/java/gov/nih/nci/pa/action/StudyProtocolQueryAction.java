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

import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.dto.StudyCheckoutDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.correlation.PoPaServiceBeanLookup;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAAttributeMaxLen;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;


/**
 * 
 * @author Harsha
 * 
 */
@SuppressWarnings({ "PMD.CyclomaticComplexity", "PMD.UnusedPrivateField", "PMD.ImmutableField", "PMD.SingularField" })
public class StudyProtocolQueryAction extends ActionSupport implements ServletResponseAware {
    private static final long serialVersionUID = -2308994602660261367L;
    private List<StudyProtocolQueryDTO> records = null;
    private StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
    private Long studyProtocolId = null;
    private HttpServletResponse servletResponse;
    private boolean checkoutStatus = false;
    /**
     * @return res
     * @throws PAException exception
     */
    @Override
    public String execute() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        return SUCCESS;
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String showCriteria() throws PAException {
        if (ServletActionContext.getRequest().isUserInRole(Constants.ABSTRACTOR)) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.USER_ROLE, Constants.ABSTRACTOR);
            return "criteriaProtected";
        }
        if (ServletActionContext.getRequest().isUserInRole(Constants.REPORT_VIEWER)) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.USER_ROLE, Constants.REPORT_VIEWER);
            return "criteriaReport";
        }
        if (ServletActionContext.getRequest().isUserInRole(Constants.SUABSTRACTOR)) {
            ServletActionContext.getRequest().getSession().setAttribute(Constants.USER_ROLE, Constants.SUABSTRACTOR);
            return "criteriaProtected";
        }
        throw new PAException("User configured improperly.  Use UPT to assign user to a valid group "
                + "for this application.");
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String query() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        try {
            records = new ArrayList<StudyProtocolQueryDTO>();
            criteria.setUserLastCreated(ServletActionContext.getRequest().getUserPrincipal().getName());
            records = PaRegistry.getProtocolQueryService().getStudyProtocolByCriteria(criteria);
            return SUCCESS;
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getLocalizedMessage());
            return SUCCESS;
        }
    }


    /**
     * 
     * @return records
     */
    public List<StudyProtocolQueryDTO> getRecords() {
        return records;
    }

    /**
     * 
     * @return StudyProtocolQueryCriteria StudyProtocolQueryCriteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }

    /**
     * 
     * @param criteria
     *            StudyProtocolQueryCriteria
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * 
     * @return studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * 
     * @param studyProtocolId
     *            studyProtocolId
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return res
     * @throws PAException exception
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    public String view() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry
                    .getProtocolQueryService()
                    .getTrialSummaryByStudyProtocolId(studyProtocolId);
            // put an entry in the session and store StudyProtocolQueryDTO
            studyProtocolQueryDTO.setOfficialTitle(PAUtil.trim(studyProtocolQueryDTO.getOfficialTitle(), 
                    PAAttributeMaxLen.DISPLAY_OFFICIAL_TITLE)); 
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.TRIAL_SUMMARY, studyProtocolQueryDTO);
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.STUDY_PROTOCOL_II, IiConverter.convertToStudyProtocolIi(studyProtocolId));
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.DOC_WFS_MENU, setMenuLinks(studyProtocolQueryDTO.getDocumentWorkflowStatusCode())); 
            
            Principal userPrincipal = ServletActionContext.getRequest().getUserPrincipal();
            String loginName = userPrincipal.getName();
            
            ServletActionContext.getRequest().getSession().setAttribute(
                    Constants.LOGGED_USER_NAME, loginName);
            String role = (String) ServletActionContext.getRequest().getSession().getAttribute(Constants.USER_ROLE);
            boolean superUser = false;
            if (role != null && role.equalsIgnoreCase(Constants.SUABSTRACTOR)) {
                superUser = true;
            }
            if ((studyProtocolQueryDTO.getStudyCheckoutBy() != null && loginName != null 
                    && studyProtocolQueryDTO.getStudyCheckoutBy().equalsIgnoreCase(loginName)) || superUser) {
                setCheckoutStatus(true);
            }
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }
    
    /**
     * @return res
     * @throws PAException exception
     */
    public String viewTSR() throws PAException {
        if (!userRoleInSession()) {
            return showCriteria();
        }
        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            PoPaServiceBeanLookup.getProtocolQueryService().getTrialSummaryByStudyProtocolId(
                    Long.valueOf(pId));
                
            String htmlData = PaRegistry.getTSRReportGeneratorService().generateTSRHtml(
                    IiConverter.convertToIi(pId));

            servletResponse.setContentType("text/html;charset=ISO-8859-1");
            servletResponse.setContentLength(htmlData.length());
            ServletOutputStream servletout = servletResponse.getOutputStream();
            servletout.write(htmlData.getBytes());
            servletout.flush();
            servletout.close();
            
          } catch (Exception e) {
              LOG.error("Error while generating TSR Summary report " , e);
              return NONE;
          }
          return NONE;
    }
    

    private String setMenuLinks(DocumentWorkflowStatusCode dwsCode) {
        String action = "";
        if (DocumentWorkflowStatusCode.REJECTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.REJECTED.getCode();
        } else if (DocumentWorkflowStatusCode.SUBMITTED.equals(dwsCode)) {
            action = DocumentWorkflowStatusCode.SUBMITTED.getCode();
        } else {
            action = DocumentWorkflowStatusCode.ACCEPTED.getCode();
        }
        return action;
    }
    
    /**
     * @return res
     * @throws PAException exception
     */
    public String checkout() throws PAException {        
        try {
            StudyProtocolQueryDTO studyProtocolQueryDTO = PaRegistry
                                        .getProtocolQueryService()
                                        .getTrialSummaryByStudyProtocolId(studyProtocolId);

            Principal userPrincipal = ServletActionContext.getRequest().getUserPrincipal();
            String loginName = userPrincipal.getName();

            StudyCheckoutDTO scoDTO = new StudyCheckoutDTO();
            scoDTO.setStudyProtocolIdentifier(
                    IiConverter.convertToStudyProtocolIi(studyProtocolQueryDTO.getStudyProtocolId()));
            scoDTO.setUserIdentifier(StConverter.convertToSt(loginName));
            
            if (checkoutStatus) {
                PaRegistry.getStudyCheckoutService().create(scoDTO);   
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        getText("studyProtocol.trial.checkOut"));
            } else if (studyProtocolQueryDTO.getStudyCheckoutId() != null) {
                PaRegistry.getStudyCheckoutService().delete(
                        IiConverter.convertToIi(studyProtocolQueryDTO.getStudyCheckoutId()));                      
                ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE,
                        getText("studyProtocol.trial.checkIn"));
            }
            view(); 
            return "view";
        } catch (PAException e) {
            addActionError(e.getLocalizedMessage());
            return ERROR;
        }
    }

    /**
     * @return the servletResponse
     */
    public HttpServletResponse getServletResponse() {
        return servletResponse;
    }

    /**
     * @param response
     *            servletResponse
     */
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }    
    
    private boolean userRoleInSession() {
        return (null != ServletActionContext.getRequest().getSession().getAttribute(Constants.USER_ROLE));
    }

    /**
     * Checks if is checkout status.
     * @return true, if is checkout status
     */
    public boolean isCheckoutStatus() {
        return checkoutStatus;
    }

    /**
     * Sets the checkout status.
     * @param checkoutStatus the new checkout status
     */
    public void setCheckoutStatus(boolean checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }
}