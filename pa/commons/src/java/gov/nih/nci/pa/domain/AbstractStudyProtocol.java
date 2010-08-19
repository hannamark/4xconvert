/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.pa.domain;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeAdditionalQualifierCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fiveamsolutions.nci.commons.search.Searchable;

/**
 * Defines common fields of a Study Protocol.
 * @author oweisms
 *
 */
@MappedSuperclass
public abstract class AbstractStudyProtocol extends AbstractEntity {
    private static final long serialVersionUID = 1234567890L;

    private ActualAnticipatedTypeCode primaryCompletionDateTypeCode;
    private ActualAnticipatedTypeCode startDateTypeCode;
    private Timestamp startDate;
    private Boolean ctgovXmlRequiredIndicator;
    private Boolean dataMonitoringCommitteeAppointedIndicator;
    private Boolean delayedpostingIndicator;
    private Boolean fdaRegulatedIndicator;
    private Boolean proprietaryTrialIndicator;
    private Boolean section801Indicator;
    private PhaseCode phaseCode;
    private PhaseAdditionalQualifierCode phaseAdditionalQualifierCode;
    private Timestamp primaryCompletionDate;
    private PrimaryPurposeCode primaryPurposeCode;
    private PrimaryPurposeAdditionalQualifierCode primaryPurposeAdditionalQualifierCode;
    private String officialTitle;
    private String programCodeText;
    private Set<Ii> otherIdentifiers = new HashSet<Ii>();

    /**
     * @return primaryCompletionDateTypeCode
     */
    @Column(name = "PRI_COMPL_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ActualAnticipatedTypeCode getPrimaryCompletionDateTypeCode() {
        return primaryCompletionDateTypeCode;
    }

    /**
     * @param primaryCompletionDateTypeCode the primaryCompletionDateTypeCode to set
     */
    public void setPrimaryCompletionDateTypeCode(ActualAnticipatedTypeCode primaryCompletionDateTypeCode) {
        this.primaryCompletionDateTypeCode = primaryCompletionDateTypeCode;
    }

    /**
     * @return startDateTypeCode
     */
    @Column(name = "START_DATE_TYPE_CODE")
    @Enumerated(EnumType.STRING)
    public ActualAnticipatedTypeCode getStartDateTypeCode() {
        return startDateTypeCode;
    }

    /**
     * @param startDateTypeCode the startDateTypeCode to set
     */
    public void setStartDateTypeCode(ActualAnticipatedTypeCode startDateTypeCode) {
        this.startDateTypeCode = startDateTypeCode;
    }

    /**
     * @return the startDate
     */
    @Column(name = "START_DATE")
    public Timestamp getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the ctgovXmlRequiredIndicator
     */
    @Column(name = "CTGOV_XML_REQUIRED_INDICATOR")
    public Boolean getCtgovXmlRequiredIndicator() {
        return ctgovXmlRequiredIndicator;
    }

    /**
     * @param ctgovXmlRequiredIndicator the ctgovXmlRequiredIndicator to set
     */
    public void setCtgovXmlRequiredIndicator(Boolean ctgovXmlRequiredIndicator) {
        this.ctgovXmlRequiredIndicator = ctgovXmlRequiredIndicator;
    }

    /**
     * @return the dataMonitoringCommitteeAppointedIndicator
     */
    @Column(name = "DATA_MONTY_COMTY_APPTN_INDICATOR")
    public Boolean getDataMonitoringCommitteeAppointedIndicator() {
        return dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     * @param dataMonitoringCommitteeAppointedIndicator the dataMonitoringCommitteeAppointedIndicator to set
     */
    public void setDataMonitoringCommitteeAppointedIndicator(Boolean dataMonitoringCommitteeAppointedIndicator) {
        this.dataMonitoringCommitteeAppointedIndicator = dataMonitoringCommitteeAppointedIndicator;
    }

    /**
     *
     * @return delayedpostingIndicator
     */
    @Column(name = "DELAYED_POSTING_INDICATOR")
    public Boolean getDelayedpostingIndicator() {
        return delayedpostingIndicator;
    }

    /**
     * @param delayedpostingIndicator the delayedpostingIndicator to set
     */
    public void setDelayedpostingIndicator(Boolean delayedpostingIndicator) {
        this.delayedpostingIndicator = delayedpostingIndicator;
    }

    /**
     * @return the fdaRegulatedIndicator
     */
    @Column(name = "FDA_REGULATED_INDICATOR")
    public Boolean getFdaRegulatedIndicator() {
        return fdaRegulatedIndicator;
    }

    /**
     * @param fdaRegulatedIndicator the fdaRegulatedIndicator to set
     */
    public void setFdaRegulatedIndicator(Boolean fdaRegulatedIndicator) {
        this.fdaRegulatedIndicator = fdaRegulatedIndicator;
    }

    /**
     * @return the proprietaryTrialIndicator
     */
    @Column(name = "PROPRIETARY_TRIAL_INDICATOR")
    public Boolean getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }

    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(Boolean proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }

    /**
     * @return the section801Indicator
     */
    @Column(name = "SECTION801_INDICATOR")
    public Boolean getSection801Indicator() {
        return section801Indicator;
    }

    /**
     * @param section801Indicator the section801Indicator to set
     */
    public void setSection801Indicator(Boolean section801Indicator) {
        this.section801Indicator = section801Indicator;
    }

    /**
     * @return the phaseCode
     */
    @Column(name = "PHASE_CODE")
    @Enumerated(EnumType.STRING)
    @Searchable
    public PhaseCode getPhaseCode() {
        return phaseCode;
    }

    /**
     * @param phaseCode the phaseCode to set
     */
    public void setPhaseCode(PhaseCode phaseCode) {
        this.phaseCode = phaseCode;
    }

    /**
     * @return the phaseAdditionalQualifierCode
     */
    @Column (name = "PHASE_ADDITIONAL_QUALIFIER_CODE")
    @Enumerated(EnumType.STRING)
    @Searchable
    public PhaseAdditionalQualifierCode getPhaseAdditionalQualifierCode() {
        return phaseAdditionalQualifierCode;
    }

    /**
     * @param phaseAdditionalQualifierCode the phaseAdditionalQualifierCode to set
     */
    public void setPhaseAdditionalQualifierCode(
            PhaseAdditionalQualifierCode phaseAdditionalQualifierCode) {
        this.phaseAdditionalQualifierCode = phaseAdditionalQualifierCode;
    }

    /**
     * @return the primaryCompletionDate
     */
    @Column (name = "PRI_COMPL_DATE")
    public Timestamp getPrimaryCompletionDate() {
        return primaryCompletionDate;
    }
    /**
     * @param primaryCompletionDate the primaryCompletionDate to set
     */
    public void setPrimaryCompletionDate(Timestamp primaryCompletionDate) {
        this.primaryCompletionDate = primaryCompletionDate;
    }

    /**
     * @return the primaryPurposeCode
     */
    @Column(name = "PRIMARY_PURPOSE_CODE")
    @Enumerated(EnumType.STRING)
    @Searchable
    public PrimaryPurposeCode getPrimaryPurposeCode() {
        return primaryPurposeCode;
    }

    /**
     * @param primaryPurposeCode the primaryPurposeCode to set
     */
    public void setPrimaryPurposeCode(PrimaryPurposeCode primaryPurposeCode) {
        this.primaryPurposeCode = primaryPurposeCode;
    }

    /**
     * @return the primaryPurposeAdditionalQualifierCode
     */
    @Column(name = "PRIMARY_PURPOSE_ADDITIONAL_QUALIFIER_CODE")
    @Enumerated(EnumType.STRING)
    public PrimaryPurposeAdditionalQualifierCode getPrimaryPurposeAdditionalQualifierCode() {
        return primaryPurposeAdditionalQualifierCode;
    }

    /**
     * @param primaryPurposeAdditionalQualifierCode the primaryPurposeAdditionalQualifierCode to set
     */
    public void setPrimaryPurposeAdditionalQualifierCode(
            PrimaryPurposeAdditionalQualifierCode primaryPurposeAdditionalQualifierCode) {
        this.primaryPurposeAdditionalQualifierCode = primaryPurposeAdditionalQualifierCode;
    }
    /**
     * @return the officialTitle
     */
    @Column(name = "OFFICIAL_TITLE")
    @Searchable(matchMode = Searchable.MATCH_MODE_CONTAINS)
    public String getOfficialTitle() {
        return officialTitle;
    }

    /**
     * @param officialTitle the officialTitle to set
     */
    public void setOfficialTitle(String officialTitle) {
        this.officialTitle = officialTitle;
    }

    /**
     * @return the programCodeText
     */
    @Column(name = "PROGRAM_CODE_TEXT")
    public String getProgramCodeText() {
        return programCodeText;
    }

    /**
     * @param programCodeText the programCodeText to set
     */
    public void setProgramCodeText(String programCodeText) {
        this.programCodeText = programCodeText;
    }

    /**
     * Gets the other identifiers.
     *
     * @return the other identifiers
     */
    @Transient
    public Set<Ii> getOtherIdentifiers() {
        return this.otherIdentifiers;
    }

    /**
     * @param otherIdentifiers the otherIdentifiers to set
     */
    public void setOtherIdentifiers(Set<Ii> otherIdentifiers) {
        this.otherIdentifiers = otherIdentifiers;
    }
}
