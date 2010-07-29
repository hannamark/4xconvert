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
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.util.TrialUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * The Class ManageFileAction.
 *
 * @author Kalpana Guthikonda
 * @since April 23 2010
 */
public class ManageFileAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(ManageFileAction.class);

    private String pageFrom;
    private File protocolDoc;
    private String protocolDocFileName;
    private File irbApproval;
    private String irbApprovalFileName;
    private File participatingSites;
    private String participatingSitesFileName;
    private File informedConsentDocument;
    private String informedConsentDocumentFileName;
    private File otherDocument;
    private String otherDocumentFileName;
    private File protocolHighlightDocument;
    private String protocolHighlightDocumentFileName;
    private File changeMemoDoc;
    private String changeMemoDocFileName;

    private static final String PROTOCOLDOC = DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName();
    private static final String IRBAPPROVALDOC = DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getShortName();
    private static final String PARTICIPATINGSITESDOC = DocumentTypeCode.PARTICIPATING_SITES.getShortName();
    private static final String INFORMEDCONSENTDOC = DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getShortName();
    private static final String CHANGEMEMODOC = DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getShortName();
    private static final String PROTOCOLHIGHDOC = DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getShortName();
    private static final String OTHERDOC = DocumentTypeCode.OTHER.getShortName();

    /**
     * Delete document.
     *
     * @return the string
     */
    public String deleteDocument() {
        LOG.debug("Entering deleteDocument()");
        String typeCode = ServletActionContext.getRequest().getParameter("typeCode");
        ServletActionContext.getRequest().getSession()
                            .removeAttribute(DocumentTypeCode.getByCode(typeCode).getShortName());
        LOG.debug("Leaving deleteDocument()");
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
     */
    public void validateDocuments() {
        TrialValidator validator = new TrialValidator();
        HttpSession session = ServletActionContext.getRequest().getSession();
        Map<String, String> err = new HashMap<String, String>();

        checkProtocolDoc(validator, session, err);
        checkIrbApprovalDoc(validator, session, err);
        if (session.getAttribute(PARTICIPATINGSITESDOC) == null) {
            err.putAll(validator.validateDocument(participatingSitesFileName, participatingSites,
                                                  "trialDTO.participatingSitesFileName", ""));
        }
        if (session.getAttribute(INFORMEDCONSENTDOC) == null) {
            err.putAll(validator.validateDocument(informedConsentDocumentFileName, informedConsentDocument,
                                                  "trialDTO.informedConsentDocumentFileName", ""));
        }
        if (session.getAttribute(OTHERDOC) == null) {
            err.putAll(validator.validateDocument(otherDocumentFileName, otherDocument,
                                                  "trialDTO.otherDocumentFileName", ""));
        }
        if (session.getAttribute(PROTOCOLHIGHDOC) == null) {
            err.putAll(validator.validateDocument(protocolHighlightDocumentFileName, protocolHighlightDocument,
                                                  "trialDTO.protocolHighlightDocumentFileName", ""));
        }
        checkChangeMemoDoc(validator, session, err);
        addErrors(err);
    }

    private void checkChangeMemoDoc(TrialValidator validator, HttpSession session, Map<String, String> err) {
        if (session.getAttribute(CHANGEMEMODOC) == null && "amendTrial".equals(pageFrom)) {
            err.putAll(validator.validateDocument(changeMemoDocFileName, changeMemoDoc,
                                                  "trialDTO.changeMemoDocFileName", "error.submit.changeMemo"));
        }
    }

    private void checkProtocolDoc(TrialValidator validator, HttpSession session, Map<String, String> err) {
        if (session.getAttribute(PROTOCOLDOC) == null && "amendTrial".equals(pageFrom)
                || session.getAttribute(PROTOCOLDOC) == null && "submitTrial".equals(pageFrom)) {
            err.putAll(validator.validateDocument(protocolDocFileName, protocolDoc, "trialDTO.protocolDocFileName",
                                                  "error.submit.protocolDocument"));
        }
    }

    private void checkIrbApprovalDoc(TrialValidator validator, HttpSession session, Map<String, String> err) {
        if (session.getAttribute(IRBAPPROVALDOC) == null && "amendTrial".equals(pageFrom)
                || session.getAttribute(IRBAPPROVALDOC) == null && "submitTrial".equals(pageFrom)) {
            err.putAll(validator.validateDocument(irbApprovalFileName, irbApproval, "trialDTO.irbApprovalFileName",
                                                  "error.submit.irbApproval"));
        }
    }

    /**
     * Adds the doc dto to list.
     *
     * @return the list< trial document web dt o>
     *
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public List<TrialDocumentWebDTO> addDocDTOToList() throws IOException {
        TrialUtil util = new TrialUtil();
        List<TrialDocumentWebDTO> docDTOList = new ArrayList<TrialDocumentWebDTO>();
        HttpSession session = ServletActionContext.getRequest().getSession();
        getProtocolDoc(util, docDTOList, session);
        getIrbApprovalDoc(util, docDTOList, session);
        getInformedConsentDoc(util, docDTOList, session);
        getParticipatingSitesDoc(util, docDTOList, session);
        getOtherDoc(util, docDTOList, session);
        getChangeMemoDoc(util, docDTOList, session);
        getProtocolHighlightDoc(util, docDTOList, session);
        return docDTOList;
    }

    private void getProtocolHighlightDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(protocolHighlightDocumentFileName)
                && session.getAttribute(PROTOCOLHIGHDOC) == null) {
            TrialDocumentWebDTO webDto =
                util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_HIGHLIGHTED_DOCUMENT.getCode(),
                                                                   protocolHighlightDocumentFileName,
                                                                   protocolHighlightDocument);
            docDTOList.add(webDto);
            session.setAttribute(PROTOCOLHIGHDOC, webDto);
        }
    }

    private void getChangeMemoDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(changeMemoDocFileName) && session.getAttribute(CHANGEMEMODOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode(),
                                                                   changeMemoDocFileName, changeMemoDoc);
            docDTOList.add(webDto);
            session.setAttribute(CHANGEMEMODOC, webDto);
        }
    }

    private void getOtherDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(otherDocumentFileName) && session.getAttribute(OTHERDOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.OTHER.getCode(),
                                                                   otherDocumentFileName, otherDocument);
            docDTOList.add(webDto);
            session.setAttribute(OTHERDOC, webDto);
        }
    }

    private void getParticipatingSitesDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(participatingSitesFileName) && session.getAttribute(PARTICIPATINGSITESDOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.PARTICIPATING_SITES.getCode(),
                                                                   participatingSitesFileName, participatingSites);
            docDTOList.add(webDto);
            session.setAttribute(PARTICIPATINGSITESDOC, webDto);
        }
    }

    private void getInformedConsentDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(informedConsentDocumentFileName)
                && session.getAttribute(INFORMEDCONSENTDOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.INFORMED_CONSENT_DOCUMENT.getCode(),
                                                                   informedConsentDocumentFileName,
                                                                   informedConsentDocument);
            docDTOList.add(webDto);
            session.setAttribute(INFORMEDCONSENTDOC, webDto);
        }
    }

    private void getIrbApprovalDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(irbApprovalFileName) && session.getAttribute(IRBAPPROVALDOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode(),
                                                                   irbApprovalFileName, irbApproval);
            docDTOList.add(webDto);
            session.setAttribute(IRBAPPROVALDOC, webDto);
        }
    }

    private void getProtocolDoc(TrialUtil util, List<TrialDocumentWebDTO> docDTOList, HttpSession session)
            throws IOException {
        if (StringUtils.isNotEmpty(protocolDocFileName) && session.getAttribute(PROTOCOLDOC) == null) {
            TrialDocumentWebDTO webDto = util.convertToDocumentDTO(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode(),
                                                                   protocolDocFileName, protocolDoc);
            docDTOList.add(webDto);
            session.setAttribute(PROTOCOLDOC, webDto);
        }
    }

    /**
     * Sets the documents in session.
     *
     * @param trialDTO the new documents in session
     */
    public void setDocumentsInSession(TrialDTO trialDTO) {
        if (trialDTO != null && trialDTO.getDocDtos() != null && !trialDTO.getDocDtos().isEmpty()) {
            for (TrialDocumentWebDTO webDto : trialDTO.getDocDtos()) {
                ServletActionContext.getRequest()
                                    .getSession()
                                    .setAttribute(DocumentTypeCode.getByCode(webDto.getTypeCode()).getShortName(),
                                                  webDto);
            }
        }
    }

    /**
     * Populate the documents List.
     *
     * @param docDTOList the doc dto list
     *
     */
    public void populateList(List<TrialDocumentWebDTO> docDTOList) {
        final HttpSession session = ServletActionContext.getRequest().getSession();
        TrialDocumentWebDTO protocolDocument = (TrialDocumentWebDTO) session.getAttribute(PROTOCOLDOC);
        TrialDocumentWebDTO irbApprovalDocument = (TrialDocumentWebDTO) session.getAttribute(IRBAPPROVALDOC);
        TrialDocumentWebDTO informedConsentDoc = (TrialDocumentWebDTO) session.getAttribute(INFORMEDCONSENTDOC);
        TrialDocumentWebDTO participatingSitesDocument =
            (TrialDocumentWebDTO) session.getAttribute(PARTICIPATINGSITESDOC);
        TrialDocumentWebDTO otherDoc = (TrialDocumentWebDTO) session.getAttribute(OTHERDOC);
        TrialDocumentWebDTO changeMemoDocument = (TrialDocumentWebDTO) session.getAttribute(CHANGEMEMODOC);
        TrialDocumentWebDTO protocolHighlightDoc = (TrialDocumentWebDTO) session.getAttribute(PROTOCOLHIGHDOC);

        docDTOList.add(protocolDocument);
        docDTOList.add(irbApprovalDocument);
        docDTOList.add(informedConsentDoc);
        docDTOList.add(participatingSitesDocument);
        docDTOList.add(otherDoc);
        docDTOList.add(changeMemoDocument);
        docDTOList.add(protocolHighlightDoc);

        Set<TrialDocumentWebDTO> set = new HashSet<TrialDocumentWebDTO>(docDTOList);
        set.remove(null);
        docDTOList.clear();
        docDTOList.addAll(set);
    }

    /**
     * Check other doc.
     * @param session the session
     * @param validator the validator
     */
    public void checkOtherDoc(HttpSession session, TrialValidator validator) {
        Map<String, String> errMap;
        if (StringUtils.isNotEmpty(otherDocumentFileName)
                && session.getAttribute(DocumentTypeCode.OTHER.getShortName()) == null) {
            errMap = new HashMap<String, String>();
            errMap = validator.validateDocument(otherDocumentFileName, otherDocument, "trialDTO.otherDocumentFileName",
                                                "");
            addErrors(errMap);
        }
    }

    /**
     * Check protocol doc.
     * @param session the session
     * @param validator the validator
     */
    public void checkProtocolDoc(HttpSession session, TrialValidator validator) {
        Map<String, String> errMap;
        if (StringUtils.isNotEmpty(protocolDocFileName)
                && session.getAttribute(DocumentTypeCode.PROTOCOL_DOCUMENT.getShortName()) == null) {
            errMap = new HashMap<String, String>();
            errMap = validator.validateDocument(protocolDocFileName, protocolDoc, "trialDTO.protocolDocFileName", "");
            addErrors(errMap);
        }
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

    /**
     * @return protocolDoc
     */
    public File getProtocolDoc() {
        return protocolDoc;
    }

    /**
     * @param protocolDoc protocolDoc
     */
    public void setProtocolDoc(File protocolDoc) {
        this.protocolDoc = protocolDoc;
    }

    /**
     * @return protocolDocFileName
     */
    public String getProtocolDocFileName() {
        return protocolDocFileName;
    }

    /**
     * @param protocolDocFileName protocolDocFileName
     */
    public void setProtocolDocFileName(String protocolDocFileName) {
        this.protocolDocFileName = protocolDocFileName;
    }

    /**
     * @return irbApproval
     */
    public File getIrbApproval() {
        return irbApproval;
    }

    /**
     * @param irbApproval irbApproval
     */
    public void setIrbApproval(File irbApproval) {
        this.irbApproval = irbApproval;
    }

    /**
     * @return irbApprovalFileName
     */
    public String getIrbApprovalFileName() {
        return irbApprovalFileName;
    }

    /**
     * @param irbApprovalFileName irbApprovalFileName
     */
    public void setIrbApprovalFileName(String irbApprovalFileName) {
        this.irbApprovalFileName = irbApprovalFileName;
    }

    /**
     * @return the informedConsentDocument
     */
    public File getInformedConsentDocument() {
        return informedConsentDocument;
    }

    /**
     * @param informedConsentDocument the informedConsentDocument to set
     */
    public void setInformedConsentDocument(File informedConsentDocument) {
        this.informedConsentDocument = informedConsentDocument;
    }

    /**
     * @return the informedConsentDocumentFileName
     */
    public String getInformedConsentDocumentFileName() {
        return informedConsentDocumentFileName;
    }

    /**
     * @param informedConsentDocumentFileName the informedConsentDocumentFileName to set
     */
    public void setInformedConsentDocumentFileName(String informedConsentDocumentFileName) {
        this.informedConsentDocumentFileName = informedConsentDocumentFileName;
    }

    /**
     * @return the otherDocument
     */
    public File getOtherDocument() {
        return otherDocument;
    }

    /**
     * @param otherDocument the otherDocument to set
     */
    public void setOtherDocument(File otherDocument) {
        this.otherDocument = otherDocument;
    }

    /**
     * @return the otherDocumentFileName
     */
    public String getOtherDocumentFileName() {
        return otherDocumentFileName;
    }

    /**
     * @param otherDocumentFileName the otherDocumentFileName to set
     */
    public void setOtherDocumentFileName(String otherDocumentFileName) {
        this.otherDocumentFileName = otherDocumentFileName;
    }

    /**
     * @return the participatingSites
     */
    public File getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(File participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @return the participatingSitesFileName
     */
    public String getParticipatingSitesFileName() {
        return participatingSitesFileName;
    }

    /**
     * @param participatingSitesFileName the participatingSitesFileName to set
     */
    public void setParticipatingSitesFileName(String participatingSitesFileName) {
        this.participatingSitesFileName = participatingSitesFileName;
    }

    /**
     * @return the protocolHighlightDocument
     */
    public File getProtocolHighlightDocument() {
        return protocolHighlightDocument;
    }

    /**
     * @param protocolHighlightDocument the protocolHighlightDocument to set
     */
    public void setProtocolHighlightDocument(File protocolHighlightDocument) {
        this.protocolHighlightDocument = protocolHighlightDocument;
    }

    /**
     * @return the protocolHighlightDocumentFileName
     */
    public String getProtocolHighlightDocumentFileName() {
        return protocolHighlightDocumentFileName;
    }

    /**
     * @param protocolHighlightDocumentFileName the protocolHighlightDocumentFileName to set
     */
    public void setProtocolHighlightDocumentFileName(String protocolHighlightDocumentFileName) {
        this.protocolHighlightDocumentFileName = protocolHighlightDocumentFileName;
    }

    /**
     * @return the changeMemoDoc
     */
    public File getChangeMemoDoc() {
        return changeMemoDoc;
    }

    /**
     * @param changeMemoDoc the changeMemoDoc to set
     */
    public void setChangeMemoDoc(File changeMemoDoc) {
        this.changeMemoDoc = changeMemoDoc;
    }

    /**
     * @return the changeMemoDocFileName
     */
    public String getChangeMemoDocFileName() {
        return changeMemoDocFileName;
    }

    /**
     * @param changeMemoDocFileName the changeMemoDocFileName to set
     */
    public void setChangeMemoDocFileName(String changeMemoDocFileName) {
        this.changeMemoDocFileName = changeMemoDocFileName;
    }
}
