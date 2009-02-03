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
package gov.nih.nci.pa.dto;


/**
 * this class holds the general information of protocol.
 * @author Naveen Amiruddin
 * @since 10/20/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@SuppressWarnings({ "PMD.TooManyFields", "PMD.ExcessiveClassLength" })
public class GeneralTrialDesignWebDTO {

    private String acronym;
    private String allocationCode;
    private String accrualReportingMethodCode;
    private String assignedIdentifier; // used to store nci-accession number
    private String officialTitle;
    private String phaseCode;
    private String phaseOtherText;
    private String primaryPurposeCode;
    private String primaryPurposeOtherText;
    private String publicTitle;
    private String publicDescription;
    private String scientificDescription;
    private String keywordText;
    private String localProtocolIdentifier;
    private String leadOrganizationIdentifier;
    private String leadOrganizationName;
    private String piIdentifier;
    private String piName;
    private String summaryFourOrgIdentifier;
    private String summaryFourOrgName;
    private String summaryFourFundingCategoryCode;
    private String sponsorName;
    private String sponsorIdentifier;
    private String responsiblePartyType;
    private String responsiblePersonName;
    private String responsiblePersonIdentifier;
    private String contactPhone;
    private String contactEmail;
    private String centralContactPhone;
    private String centralContactEmail;
    private String centralContactName;
    private String centralContactIdentifier;
    private String nctIdentifier;
    private String commentText;
    
    /**
     * 
     * @return acronym acronym
     */
    public String getAcronym() {
        return acronym;
    }
    /**
     * 
     * @param acronym acronym
     */
    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }
    /**
     * 
     * @return allocationCode
     */
    public String getAllocationCode() {
        return allocationCode;
    }
    /**
     * 
     * @param allocationCode allocationCode
     */
    public void setAllocationCode(String allocationCode) {
        this.allocationCode = allocationCode;
    }
    /**
     * 
     * @return accrualReportingMethodCode
     */
    public String getAccrualReportingMethodCode() {
        return accrualReportingMethodCode;
    }
    /**
     * 
     * @param accrualReportingMethodCode accrualReportingMethodCode
     */
    public void setAccrualReportingMethodCode(String accrualReportingMethodCode) {
        this.accrualReportingMethodCode = accrualReportingMethodCode;
    }
    /**
     * 
     * @return assignedIdentifier
     */
    public String getAssignedIdentifier() {
        return assignedIdentifier;
    }
    /**
     * 
     * @param assignedIdentifier assignedIdentifier
     */
    public void setAssignedIdentifier(String assignedIdentifier) {
        this.assignedIdentifier = assignedIdentifier;
    }
    
    /**
     * 
     * @return officialTitle
     */
    public String getOfficialTitle() {
        return officialTitle;
    }
    /**
     * 
     * @param officialTitle officialTitle
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }
    /**
     * 
     * @return phaseCode
     */
    public String getPhaseCode() {
        return phaseCode;
    }
    /**
     * 
     * @param phaseCode phaseCode
     */
    public void setPhaseCode(String phaseCode) {
        this.phaseCode = phaseCode;
    }
    /**
     * 
     * @return phaseOtherText
     */
    public String getPhaseOtherText() {
        return phaseOtherText;
    }
    /**
     * 
     * @param phaseOtherText phaseOtherText
     */
    public void setPhaseOtherText(String phaseOtherText) {
        this.phaseOtherText = phaseOtherText;
    }
    /**
     * 
     * @return primaryPurposeCode
     */
    public String getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }
    /**
     * 
     * @param primaryPurposeCode primaryPurposeCode
     */
    public void setPrimaryPurposeCode(String primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }
    /**
     * 
     * @return primaryPurposeOtherText
     */
    public String getPrimaryPurposeOtherText() {
        return primaryPurposeOtherText;
    }
    /**
     * 
     * @param primaryPurposeOtherText primaryPurposeOtherText
     */
    public void setPrimaryPurposeOtherText(String primaryPurposeOtherText) {
        this.primaryPurposeOtherText = primaryPurposeOtherText;
    }
    /**
     * @return publicTitle
     */
    public String getPublicTitle() {
        return publicTitle;
    }
    /**
     * @param publicTitle publicTitle
     */
    public void setPublicTitle(String publicTitle) {
        this.publicTitle = publicTitle;
    }
    /**
     * @return publicDescription
     */
    public String getPublicDescription() {
        return publicDescription;
    }
    /**
     * @param publicDescription publicDescription
     */
    public void setPublicDescription(String publicDescription) {
        this.publicDescription = publicDescription;
    }
    /**
     * @return scientificDescription
     */
    public String getScientificDescription() {
        return scientificDescription;
    }
    /**
     * @param scientificDescription scientificDescription
     */
    public void setScientificDescription(String scientificDescription) {
        this.scientificDescription = scientificDescription;
    }
    /**
     * @return keywordText
     */
    public String getKeywordText() {
        return keywordText;
    }
    /**
     * @param keywordText keywordText
     */
    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }
    /**
     * 
     * @return localProtocolIdentifier
     */
    public String getLocalProtocolIdentifier() {
        return localProtocolIdentifier;
    }
    /**
     * 
     * @param localProtocolIdentifier localProtocolIdentifier
     */
    public void setLocalProtocolIdentifier(String localProtocolIdentifier) {
        this.localProtocolIdentifier = localProtocolIdentifier;
    }
    /**
     * 
     * @return leadOrganizationIdentifier
     */
    public String getLeadOrganizationIdentifier() {
        return leadOrganizationIdentifier;
    }
    /**
     * 
     * @param leadOrganizationIdentifier leadOrganizationIdentifier
     */
    public void setLeadOrganizationIdentifier(String leadOrganizationIdentifier) {
        this.leadOrganizationIdentifier = leadOrganizationIdentifier;
    }
    /**
     * 
     * @return leadOrganizationName
     */
    public String getLeadOrganizationName() {
        return leadOrganizationName;
    }
    /**
     * 
     * @param leadOrganizationName leadOrganizationName
     */
    public void setLeadOrganizationName(String leadOrganizationName) {
        this.leadOrganizationName = leadOrganizationName;
    }
    /**
     * 
     * @return piIdentifier
     */
    public String getPiIdentifier() {
        return piIdentifier;
    }
    /**
     * 
     * @param piIdentifier piIdentifier
     */
    public void setPiIdentifier(String piIdentifier) {
        this.piIdentifier = piIdentifier;
    }
    /**
     * 
     * @return piName
     */
    public String getPiName() {
        return piName;
    }
    /**
     * 
     * @param piName piName
     */
    public void setPiName(String piName) {
        this.piName = piName;
    }
    /**
     * 
     * @return summaryFourOrgIdentifier
     */
    public String getSummaryFourOrgIdentifier() {
        return summaryFourOrgIdentifier;
    }
    /**
     * 
     * @param summaryFourOrgIdentifier summaryFourOrgIdentifier
     */
    public void setSummaryFourOrgIdentifier(String summaryFourOrgIdentifier) {
        this.summaryFourOrgIdentifier = summaryFourOrgIdentifier;
    }
    /**
     * 
     * @return summaryFourOrgName
     */
    public String getSummaryFourOrgName() {
        return summaryFourOrgName;
    }
    /**
     * 
     * @param summaryFourOrgName summaryFourOrgName
     */
    public void setSummaryFourOrgName(String summaryFourOrgName) {
        this.summaryFourOrgName = summaryFourOrgName;
    }
    /**
     * 
     * @return summaryFourFundingCategoryCode
     */
    public String getSummaryFourFundingCategoryCode() {
        return summaryFourFundingCategoryCode;
    }
    /**
     * 
     * @param summaryFourFundingCategoryCode summaryFourFundingCategoryCode
     */
    public void setSummaryFourFundingCategoryCode(
            String summaryFourFundingCategoryCode) {
        this.summaryFourFundingCategoryCode = summaryFourFundingCategoryCode;
    }
    /**
     * 
     * @return sponsorName
     */
    public String getSponsorName() {
        return sponsorName;
    }
    /**
     * 
     * @param sponsorName sponsorName
     */
    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
    /**
     * 
     * @return sponsorIdentifier
     */
    public String getSponsorIdentifier() {
        return sponsorIdentifier;
    }
    /**
     * 
     * @param sponsorIdentifier sponsorIdentifier
     */
    public void setSponsorIdentifier(String sponsorIdentifier) {
        this.sponsorIdentifier = sponsorIdentifier;
    }
    /**
     * 
     * @return responsiblePartyType
     */
    public String getResponsiblePartyType() {
        return responsiblePartyType;
    }
    /**
     * 
     * @param responsiblePartyType responsiblePartyType
     */
    public void setResponsiblePartyType(String responsiblePartyType) {
        this.responsiblePartyType = responsiblePartyType;
    }
    /**
     * 
     * @return responsiblePersonName
     */
    public String getResponsiblePersonName() {
        return responsiblePersonName;
    }
    /**
     * 
     * @param responsiblePersonName responsibleName
     */
    public void setResponsiblePersonName(String responsiblePersonName) {
        this.responsiblePersonName = responsiblePersonName;
    }
    /**
     * 
     * @return  responsiblePersonIdentifier
     */
    public String getResponsiblePersonIdentifier() {
        return responsiblePersonIdentifier;
    }
    /**
     * 
     * @param responsiblePersonIdentifier responsiblePersonIdentifier
     */
    public void setResponsiblePersonIdentifier(String responsiblePersonIdentifier) {
        this.responsiblePersonIdentifier = responsiblePersonIdentifier;
    }
    /**
     * 
     * @return contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }
    /**
     * 
     * @param contactPhone contactPhone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    /**
     * 
     * @return contactEmail
     */
    public String getContactEmail() {
        return contactEmail;
    }
    /**
     * 
     * @param contactEmail contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    /**
     * 
     * @return centralContactPhone
     */
    public String getCentralContactPhone() {
        return centralContactPhone;
    }
    /**
     * 
     * @param centralContactPhone centralContactPhone
     */
    public void setCentralContactPhone(String centralContactPhone) {
        this.centralContactPhone = centralContactPhone;
    }
    /**
     * 
     * @return centralContactEmail
     */
    public String getCentralContactEmail() {
        return centralContactEmail;
    }
    /**
     * 
     * @param centralContactEmail centralContactEmail
     */
    public void setCentralContactEmail(String centralContactEmail) {
        this.centralContactEmail = centralContactEmail;
    }
    /**
     * 
     * @return centralContactName
     */
    public String getCentralContactName() {
        return centralContactName;
    }
    /**
     * 
     * @param centralContactName centralContactName
     */
    public void setCentralContactName(String centralContactName) {
        this.centralContactName = centralContactName;
    }
    /**
     * 
     * @return centralContactIdentifier
     */
    public String getCentralContactIdentifier() {
        return centralContactIdentifier;
    }
    /**
     * 
     * @param centralContactIdentifier centralContactIdentifier
     */
    public void setCentralContactIdentifier(String centralContactIdentifier) {
        this.centralContactIdentifier = centralContactIdentifier;
    }
    /**
     * 
     * @return nctIdentifier
     */
    public String getNctIdentifier() {
        return nctIdentifier;
    }
    /**
     * 
     * @param nctIdentifier nctIdentifier
     */
    public void setNctIdentifier(String nctIdentifier) {
        this.nctIdentifier = nctIdentifier;
    }
    
    /**
     * @return commentText
     */
    public String getCommentText() {
      return commentText;
    }
    /**
     * @param commentText commentText
     */
    public void setCommentText(String commentText) {
      this.commentText = commentText;
    }
    
}
