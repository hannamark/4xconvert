/***
 * caBIG Open Source Software License
 *
 * Copyright Notice.  Copyright 2008, ScenPro, Inc,  (caBIG Participant).   The Clinical Trials Protocol Application
 * was created with NCI funding and is part of  the caBIG initiative. The  software subject to  this notice  and license
 * includes both  human readable source code form and machine readable, binary, object code form (the caBIG Software).
 *
 * This caBIG Software License (the License) is between caBIG  Participant  and  You.  You (or Your) shall  mean a
 * person or an entity, and all other entities that control, are controlled by,  or  are under common  control  with the
 * entity.  Control for purposes of this definition means
 *
 * (i) the direct or indirect power to cause the direction or management of such entity,whether by contract
 * or otherwise,or
 *
 * (ii) ownership of fifty percent (50%) or more of the outstanding shares, or
 *
 * (iii) beneficial ownership of such entity.
 * License.  Provided that You agree to the conditions described below, caBIG Participant  grants  You a  non-exclusive,
 * worldwide, perpetual, fully-paid-up, no-charge, irrevocable, transferable  and royalty-free  right and license in its
 * rights in the caBIG Software, including any copyright or patent rights therein, to
 *
 * (i) use,install, disclose, access, operate, execute, reproduce,  copy, modify, translate,  market,  publicly display,
 * publicly perform, and prepare derivative works of the caBIG Software in any manner and for any  purpose,  and to have
 * or permit others to do so;
 *
 * (ii) make, have made, use, practice, sell, and offer  for sale,  import, and/or  otherwise  dispose of caBIG Software
 * (or portions thereof);
 *
 * (iii) distribute and have distributed  to  and by third   parties the   caBIG  Software  and any   modifications  and
 * derivative works thereof; and (iv) sublicense the foregoing rights  set  out in (i), (ii) and (iii) to third parties,
 * including the right to license such rights to further third parties.For sake of clarity,and not by way of limitation,
 * caBIG Participant shall have no right of accounting or right of payment from You or Your sub licensees for the rights
 * granted under this License.   This  License is  granted  at no  charge  to You. Your downloading, copying, modifying,
 * displaying, distributing or use of caBIG Software constitutes acceptance  of  all of the terms and conditions of this
 * Agreement.  If You do not agree to such terms and conditions, You have no right to download,  copy,  modify, display,
 * distribute or use the caBIG Software.
 *
 * 1.  Your redistributions of the source code for the caBIG Software must retain the above copyright notice, this  list
 * of conditions and the disclaimer and limitation of liability of Article 6 below.  Your redistributions in object code
 * form must reproduce the above copyright notice, this list of  conditions  and the  disclaimer  of  Article  6  in the
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
 * 4.  For sake of clarity, and not by way of limitation, You  may incorporate this caBIG Software into Your proprietary
 * programs and into any third party proprietary programs.  However, if You incorporate the  caBIG Software  into  third
 * party proprietary programs, You agree  that You are  solely responsible  for obtaining any permission from such third
 * parties required to incorporate the caBIG Software  into such third party proprietary programs and for informing Your
 * sub licensees, including without limitation Your end-users, of their obligation to  secure  any  required permissions
 * from such third parties before incorporating the caBIG Software into such third party proprietary  software programs.
 * In the event that You fail to obtain such permissions,  You  agree  to  indemnify  caBIG  Participant  for any claims
 * against caBIG Participant by such third parties, except to the extent prohibited by law,  resulting from Your failure
 * to obtain such permissions.
 *
 * 5.  For sake of clarity, and not by way of limitation, You may add Your own copyright statement to Your modifications
 * and to the derivative works, and You may provide additional  or  different  license  terms  and  conditions  in  Your
 * sublicenses of modifications of the caBIG  Software,  or  any  derivative  works  of  the caBIG Software as a whole,
 * provided Your use, reproduction, and  distribution  of the Work otherwise complies with the conditions stated in this
 * License.
 *
 * 6.  THIS caBIG SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES  ( INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY, NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED.  IN
 * NO EVENT SHALL THE ScenPro, Inc. OR ITS AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT  LIMITED  TO, PROCUREMENT OF SUBSTITUTE GOODS  OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS caBIG SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 */
package gov.nih.nci.registry.action;

import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.registry.dto.DocumentDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * The Class ManageFileAction.
 *
 * @author Kalpana Guthikonda
 */
public class ManageFileAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final String SUBMIT_TRIAL_PAGE = "submitTrial";
    private static final String AMEND_TRIAL_PAGE = "amendTrial";

    private final DocumentDTO protocolDocument = new DocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT, Arrays.asList(
            AMEND_TRIAL_PAGE, SUBMIT_TRIAL_PAGE), true, "trialDTO.protocolDocFileName");
    private final DocumentDTO irbApprovalDocument = new DocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT,
            Arrays.asList(AMEND_TRIAL_PAGE, SUBMIT_TRIAL_PAGE), true, "trialDTO.irbApprovalFileName");
    private final DocumentDTO participatingSitesDocument = new DocumentDTO(DocumentTypeCode.PARTICIPATING_SITES, false,
            "trialDTO.participatingSitesFileName");
    private final DocumentDTO informedConsentDocument = new DocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT,
            false, "trialDTO.informedConsentDocumentFileName");
    private final DocumentDTO otherDocument = new DocumentDTO(DocumentTypeCode.OTHER, false,
            "trialDTO.otherDocumentFileName");
    private final DocumentDTO protocolHighlightDocument = new DocumentDTO(
            DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT, Arrays.asList(AMEND_TRIAL_PAGE), true,
            "trialDTO.protocolHighlightDocumentFileName");
    private final DocumentDTO changeMemoDocument = new DocumentDTO(DocumentTypeCode.CHANGE_MEMO_DOCUMENT,
            Arrays.asList(AMEND_TRIAL_PAGE), true, "trialDTO.changeMemoDocFileName");
    private String pageFrom;
    private String typeCode;

    /**
     * Remove document from session.
     *
     * @return the string
     */
    public String deleteDocument() {
        getSession().removeAttribute(DocumentTypeCode.getByCode(typeCode).getShortName());
        return SUCCESS;
    }

    /**
     * Adds the errors.
     *
     * @param err the err
     */
    public void addErrors(Map<String, String> err) {
        if (!err.isEmpty()) {
            for (String msg : err.keySet()) {
                addFieldError(msg, err.get(msg));
            }
        }
    }

    /**
     * validate the submit trial form elements.
     * @throws IOException on document error
     */
    public void validateDocuments() throws IOException {
        Map<String, String> documentValidationErrors = new HashMap<String, String>();

        documentValidationErrors.putAll(validateDocument(protocolDocument, protocolDocument.isRequired(),
                "error.submit.protocolDocument"));
        documentValidationErrors.putAll(validateDocument(irbApprovalDocument, irbApprovalDocument.isRequired(),
                "error.submit.irbApproval"));
        documentValidationErrors.putAll(validateDocument(participatingSitesDocument,
                participatingSitesDocument.isRequired(), ""));
        documentValidationErrors.putAll(validateDocument(informedConsentDocument, informedConsentDocument.isRequired(),
                ""));
        documentValidationErrors.putAll(validateDocument(otherDocument, otherDocument.isRequired(), ""));
        documentValidationErrors.putAll(validateDocument(protocolHighlightDocument,
                isDocumentMissing(changeMemoDocument), "error.submit.changeMemoOrProtocolHighlight"));
        documentValidationErrors.putAll(validateDocument(changeMemoDocument,
                isDocumentMissing(protocolHighlightDocument), "error.submit.changeMemoOrProtocolHighlight"));
        addErrors(documentValidationErrors);
    }

    private HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }
    
    private boolean isDocumentMissing(DocumentDTO document) {
        return StringUtils.isEmpty(document.getFileName()) && !isDocumentInSession(document);
    }
    
    private Map<String, String> validateDocument(DocumentDTO document, boolean isRequired, String errorMessageKey)
            throws IOException {
        Map<String, String> errors = new HashMap<String, String>();
        if (needsValidation(document)) {
            errors = DocumentValidator.validateDocument(document, isRequired, errorMessageKey);
        }
        if (errors.isEmpty()) {
            storeInSessionIfNecessary(document);
        }
        return errors;
    }

    /**
     * Validates the protocol document format, and store in session.
     * @return map of validation errors
     * @throws IOException if the file can't be accessed.
     */
    protected Map<String, String> validateProtocolDoc()
        throws IOException {
        return validateDocument(protocolDocument, protocolDocument.isRequired(), "error.submit.protocolDocument");
    }
    
    private boolean needsValidation(DocumentDTO document) {
        return !isDocumentInSession(document)
                && (document.getFromPages().isEmpty() || document.getFromPages().contains(pageFrom));
    }
    private boolean isDocumentInSession(DocumentDTO document) {
        return getSession().getAttribute(document.getSessionAttributeName()) != null;
    }

    private void storeInSessionIfNecessary(DocumentDTO document) throws IOException {
        if (checkWhetherToAddDocumentToSession(document)) {
            addDocumentToSession(document);
        }
    }
    private boolean checkWhetherToAddDocumentToSession(DocumentDTO document) {
        return StringUtils.isNotEmpty(document.getFileName())
                && !isDocumentInSession(document);
    }

    private void addDocumentToSession(DocumentDTO document) throws IOException {
        TrialDocumentWebDTO webDto = new TrialUtil().convertToDocumentDTO(document.getDocumentCode(),
                document.getFileName(), document.getFile());
        getSession().setAttribute(document.getSessionAttributeName(), webDto);
    }

    /**
     * Sets the documents in session.
     *
     * @param trialDTO the new documents in session
     */
    public void setDocumentsInSession(TrialDTO trialDTO) {
        if (trialDTO != null && trialDTO.getDocDtos() != null && !trialDTO.getDocDtos().isEmpty()) {
            for (TrialDocumentWebDTO webDto : trialDTO.getDocDtos()) {
                getSession().setAttribute(DocumentTypeCode.getByCode(webDto.getTypeCode()).getShortName(), webDto);
            }
        }
    }

    /**
     * Gets all uploaded trial documents.
     * @return a list of all uploaded trial documents
     */
    public List<TrialDocumentWebDTO> getTrialDocuments() {
        List<TrialDocumentWebDTO> documents = new ArrayList<TrialDocumentWebDTO>();
        documents.add(getDocumentFromSession(protocolDocument));
        documents.add(getDocumentFromSession(irbApprovalDocument));
        documents.add(getDocumentFromSession(informedConsentDocument));
        documents.add(getDocumentFromSession(participatingSitesDocument));
        documents.add(getDocumentFromSession(otherDocument));
        documents.add(getDocumentFromSession(changeMemoDocument));
        documents.add(getDocumentFromSession(protocolHighlightDocument));

        Set<TrialDocumentWebDTO> set = new HashSet<TrialDocumentWebDTO>(documents);
        set.remove(null);
        documents.clear();
        documents.addAll(set);
        return documents;
    }

    private TrialDocumentWebDTO getDocumentFromSession(DocumentDTO document) {
        return (TrialDocumentWebDTO) getSession().getAttribute(document.getSessionAttributeName());
    }

    /**
     * Validate other document for updates.
     * @throws IOException on document conversion error
     */
    public void validateOtherDocUpdate() throws IOException {
        Map<String, String> errMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(otherDocument.getFileName())
                && !isDocumentInSession(otherDocument)) {
            errMap = DocumentValidator.validateDocument(otherDocument.getFileName(), otherDocument.getFile(), false,
                    "trialDTO.otherDocumentFileName", "");
            addErrors(errMap);
        }
        if (errMap.isEmpty()) {
            storeInSessionIfNecessary(otherDocument);
        }
    }

    /**
     * Validate protocol document for updates.
     * @throws IOException on document conversion error
     */
    public void validateProtocolDocUpdate() throws IOException {
        Map<String, String> errMap = new HashMap<String, String>();
        if (StringUtils.isNotEmpty(protocolDocument.getFileName())
                && !isDocumentInSession(protocolDocument)) {
            errMap = DocumentValidator.validateDocument(protocolDocument.getFileName(), protocolDocument.getFile(),
                    false, "trialDTO.protocolDocFileName", "");
            addErrors(errMap);
        }
        if (errMap.isEmpty()) {
            storeInSessionIfNecessary(protocolDocument);
        }
    }

    /**
     * @return protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDocument.getFile();
    }
    /**
     * @param protocolDoc protocolDoc
     */
    public void setProtocolDoc(File protocolDoc) {
        this.protocolDocument.setFile(protocolDoc);
    }
    /**
     * @return protocolDocFileName
     */
    public String getProtocolDocFileName() {
        return protocolDocument.getFileName();
    }
    /**
     * @param protocolDocFileName protocolDocFileName
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocument.setFileName(protocolDocFileName);
    }
    /**
     * @return irbApproval
     */
    public File getIrbApproval() {
        return irbApprovalDocument.getFile();
    }
    /**
     * @param irbApproval irbApproval
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApprovalDocument.setFile(irbApproval);
    }
    /**
     * @return irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalDocument.getFileName();
    }
    /**
     * @param irbApprovalFileName irbApprovalFileName
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalDocument.setFileName(irbApprovalFileName);
    }
    /**
     * @return the informedConsentDocument
     */
    public File getInformedConsentDocument() {
        return informedConsentDocument.getFile();
    }
    /**
     * @param informedConsentDocument the informedConsentDocument to set
     */
    public void setInformedConsentDocument(File informedConsentDocument) {
        this.informedConsentDocument.setFile(informedConsentDocument);
    }
    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocument.getFileName();
    }
    /**
     * @param informedConsentDocumentFileName the informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(String informedConsentDocumentFileName) {
        this.informedConsentDocument.setFileName(informedConsentDocumentFileName);
    }
    /**
     * @return the otherDocument
     */
    public File getOtherDocument() {
        return otherDocument.getFile();
    }
    /**
     * @param otherDocument the otherDocument to set
     */
    public void setOtherDocument(File otherDocument) {
        this.otherDocument.setFile(otherDocument);
    }
    /**
     * @return the otherDocumentFileName
     */
    public String getOtherDocumentFileName() {
        return otherDocument.getFileName();
    }
    /**
     * @param otherDocumentFileName the otherDocumentFileName to set
     */
    public void setOtherDocumentFileName(String otherDocumentFileName) {
        this.otherDocument.setFileName(otherDocumentFileName);
    }
    /**
     * @return the participatingSites
     */
    public File getParticipatingSites() {
        return participatingSitesDocument.getFile();
    }
    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(File participatingSites) {
        this.participatingSitesDocument.setFile(participatingSites);
    }
    /**
     * @return the participatingSitesFileName
     */
    public String getParticipatingSitesFileName() {
        return participatingSitesDocument.getFileName();
    }
    /**
     * @param participatingSitesFileName the participatingSitesFileName to set
     */
    public void setParticipatingSitesFileName(String participatingSitesFileName) {
        this.participatingSitesDocument.setFileName(participatingSitesFileName);
    }
    /**
     * @return the protocolHighlightDocument
     */
    public File getProtocolHighlightDocument() {
        return protocolHighlightDocument.getFile();
    }
    /**
     * @param protocolHighlightDocument the protocolHighlightDocument to set
     */
    public void setProtocolHighlightDocument(File protocolHighlightDocument) {
        this.protocolHighlightDocument.setFile(protocolHighlightDocument);
    }
    /**
     * @return the protocolHighlightDocumentFileName
     */
    public String getProtocolHighlightDocumentFileName() {
        return protocolHighlightDocument.getFileName();
    }
    /**
     * @param protocolHighlightDocumentFileName the protocolHighlightDocumentFileName to set
     */
    public void setProtocolHighlightDocumentFileName(String protocolHighlightDocumentFileName) {
        this.protocolHighlightDocument.setFileName(protocolHighlightDocumentFileName);
    }
    /**
     * @return the changeMemoDoc
     */
    public File getChangeMemoDoc() {
        return changeMemoDocument.getFile();
    }
    /**
     * @param changeMemoDoc the changeMemoDoc to set
     */
    public void setChangeMemoDoc(File changeMemoDoc) {
        this.changeMemoDocument.setFile(changeMemoDoc);
    }
    /**
     * @return the changeMemoDocFileName
     */
    public String getChangeMemoDocFileName() {
        return changeMemoDocument.getFileName();
    }
    /**
     * @param changeMemoDocFileName the changeMemoDocFileName to set
     */
    public void setChangeMemoDocFileName(String changeMemoDocFileName) {
        this.changeMemoDocument.setFileName(changeMemoDocFileName);
    }
    /**
     * @return the typeCode
     */
    public String getTypeCode() {
        return typeCode;
    }
    /**
     * @param typeCode the typeCode to set
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    /**
     * @return pageFrom
     */
    public String getPageFrom() {
        return pageFrom;
    }
    /**
     * @param pageFrom pageFrom to set
     */
    public void setPageFrom(String pageFrom) {
        this.pageFrom = pageFrom;
    }
}