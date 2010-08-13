/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
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
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
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

package gov.nih.nci.pa.service.util.report;

import gov.nih.nci.pa.service.PAException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract Class for generating the TSR Report.
 *
 * @author kkanchinadam
 */
public abstract class AbstractTsrReportGenerator {
    private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private boolean proprietaryTrial;
    private TSRReport tsrReport;
    private TSRErrorReport tsrErrorReport;
    private TSRReportTrialIdentification trialIdentification;
    private TSRReportGeneralTrialDetails generalTrialDetails;
    private TSRReportStatusDate statusDate;
    private TSRReportRegulatoryInformation regulatoryInformation;
    private TSRReportHumanSubjectSafety humanSubjectSafety;
    private List<TSRReportIndIde> indIdes = new ArrayList<TSRReportIndIde>();
    private List<TSRReportNihGrant> nihGrants = new ArrayList<TSRReportNihGrant>();
    private TSRReportSummary4Information summary4Information;
    private List<TSRReportCollaborator> collaborators = new ArrayList<TSRReportCollaborator>();
    private List<TSRReportDiseaseCondition> diseaseConditions = new ArrayList<TSRReportDiseaseCondition>();
    private TSRReportTrialDesign trialDesign;
    private TSRReportEligibilityCriteria eligibilityCriteria;
    private List<TSRReportArmGroup> armGroups = new ArrayList<TSRReportArmGroup>();
    private List<TSRReportOutcomeMeasure> primaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
    private List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures = new ArrayList<TSRReportOutcomeMeasure>();
    private List<TSRReportSubGroupStratificationCriteria> sgsCriterias =
        new ArrayList<TSRReportSubGroupStratificationCriteria>();
    private List<TSRReportParticipatingSite> participatingSites = new ArrayList<TSRReportParticipatingSite>();
    private List<TSRReportIntervention> interventions = new ArrayList<TSRReportIntervention>();

    /**
     * @param tsrReport basic information about the report.
     * @param proprietaryTrial indicator whether the trial is proprietary or not.
     */
    public AbstractTsrReportGenerator(TSRReport tsrReport, boolean proprietaryTrial) {
        super();
        this.tsrReport = tsrReport;
        this.proprietaryTrial = proprietaryTrial;
    }

    /**
     * @param tsrErrorReport error information during report generation.
     */
    public AbstractTsrReportGenerator(TSRErrorReport tsrErrorReport) {
        super();
        this.tsrErrorReport = tsrErrorReport;
    }

    @SuppressWarnings("unused")
    private AbstractTsrReportGenerator() {
        super();
    }

    /**
     * @return the proprietaryTrial
     */
    public boolean isProprietaryTrial() {
        return proprietaryTrial;
    }

    /**
     * Generates the TSR Report.
     * @return baos the byte array output stream.
     * @throws PAException the exception.
     */
    public abstract ByteArrayOutputStream generateTsrReport() throws PAException;

    /**
     * Generates the TSR Error Report.
     * @return baos the byte array output stream.
     * @throws PAException the exception.
     */
    public abstract ByteArrayOutputStream generateErrorReport() throws PAException;

    /**
     * @return the tsrErrorReport
     */
    public TSRErrorReport getTsrErrorReport() {
        return tsrErrorReport;
    }

    /**
     * @param tsrErrorReport the tsrErrorReport to set
     */
    public void setTsrErrorReport(TSRErrorReport tsrErrorReport) {
        this.tsrErrorReport = tsrErrorReport;
    }

    /**
     * @return the outputStream
     */
    public ByteArrayOutputStream getOutputStream() {
        return outputStream;
    }

    /**
     * @param outputStream the outputStream to set
     */
    public void setOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    /**
     * @param trialIdentification the trialIdentification to set
     */
    public void setTrialIdentification(TSRReportTrialIdentification trialIdentification) {
        this.trialIdentification = trialIdentification;
    }

    /**
     * @param generalTrialDetails the generalTrialDetails to set
     */
    public void setGeneralTrialDetails(TSRReportGeneralTrialDetails generalTrialDetails) {
        this.generalTrialDetails = generalTrialDetails;
    }

    /**
     * @param statusDate the statusDate to set
     */
    public void setStatusDate(TSRReportStatusDate statusDate) {
        this.statusDate = statusDate;
    }

    /**
     * @param regulatoryInformation the regulatoryInformation to set
     */
    public void setRegulatoryInformation(TSRReportRegulatoryInformation regulatoryInformation) {
        this.regulatoryInformation = regulatoryInformation;
    }

    /**
     * @param humanSubjectSafety the humanSubjectSafety to set
     */
    public void setHumanSubjectSafety(TSRReportHumanSubjectSafety humanSubjectSafety) {
        this.humanSubjectSafety = humanSubjectSafety;
    }

    /**
     * @param indIdes the indIdes to set
     */
    public void setIndIdes(List<TSRReportIndIde> indIdes) {
        this.indIdes = indIdes;
    }

    /**
     * @param nihGrants the nihGrants to set
     */
    public void setNihGrants(List<TSRReportNihGrant> nihGrants) {
        this.nihGrants = nihGrants;
    }

    /**
     * @param summary4Information the summary4Information to set
     */
    public void setSummary4Information(TSRReportSummary4Information summary4Information) {
        this.summary4Information = summary4Information;
    }

    /**
     * @param collaborators the collaborators to set
     */
    public void setCollaborators(List<TSRReportCollaborator> collaborators) {
        this.collaborators = collaborators;
    }

    /**
     * @param diseaseConditions the diseaseConditions to set
     */
    public void setDiseaseConditions(List<TSRReportDiseaseCondition> diseaseConditions) {
        this.diseaseConditions = diseaseConditions;
    }

    /**
     * @param trialDesign the trialDesign to set
     */
    public void setTrialDesign(TSRReportTrialDesign trialDesign) {
        this.trialDesign = trialDesign;
    }

    /**
     * @param eligibilityCriteria the eligibilityCriteria to set
     */
    public void setEligibilityCriteria(TSRReportEligibilityCriteria eligibilityCriteria) {
        this.eligibilityCriteria = eligibilityCriteria;
    }

    /**
     * @param armGroups the armGroups to set
     */
    public void setArmGroups(List<TSRReportArmGroup> armGroups) {
        this.armGroups = armGroups;
    }

    /**
     * @param primaryOutcomeMeasures the primaryOutcomeMeasures to set
     */
    public void setPrimaryOutcomeMeasures(List<TSRReportOutcomeMeasure> primaryOutcomeMeasures) {
        this.primaryOutcomeMeasures = primaryOutcomeMeasures;
    }

    /**
     * @param secondaryOutcomeMeasures the secondaryOutcomeMeasures to set
     */
    public void setSecondaryOutcomeMeasures(List<TSRReportOutcomeMeasure> secondaryOutcomeMeasures) {
        this.secondaryOutcomeMeasures = secondaryOutcomeMeasures;
    }

    /**
     * @param sgsCriterias the sgsCriterias to set
     */
    public void setSgsCriterias(List<TSRReportSubGroupStratificationCriteria> sgsCriterias) {
        this.sgsCriterias = sgsCriterias;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(List<TSRReportParticipatingSite> participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @param interventions the interventions to set
     */
    public void setInterventions(List<TSRReportIntervention> interventions) {
        this.interventions = interventions;
    }

    /**
     * @return the tsrReport
     */
    public TSRReport getTsrReport() {
        return tsrReport;
    }

    /**
     * @return the trialIdentification
     */
    public TSRReportTrialIdentification getTrialIdentification() {
        return trialIdentification;
    }

    /**
     * @return the generalTrialDetails
     */
    public TSRReportGeneralTrialDetails getGeneralTrialDetails() {
        return generalTrialDetails;
    }

    /**
     * @return the statusDate
     */
    public TSRReportStatusDate getStatusDate() {
        return statusDate;
    }

    /**
     * @return the regulatoryInformation
     */
    public TSRReportRegulatoryInformation getRegulatoryInformation() {
        return regulatoryInformation;
    }

    /**
     * @return the humanSubjectSafety
     */
    public TSRReportHumanSubjectSafety getHumanSubjectSafety() {
        return humanSubjectSafety;
    }

    /**
     * @return the indIdes
     */
    public List<TSRReportIndIde> getIndIdes() {
        return indIdes;
    }

    /**
     * @return the nihGrants
     */
    public List<TSRReportNihGrant> getNihGrants() {
        return nihGrants;
    }

    /**
     * @return the summary4Information
     */
    public TSRReportSummary4Information getSummary4Information() {
        return summary4Information;
    }

    /**
     * @return the collaborators
     */
    public List<TSRReportCollaborator> getCollaborators() {
        return collaborators;
    }

    /**
     * @return the diseaseConditions
     */
    public List<TSRReportDiseaseCondition> getDiseaseConditions() {
        return diseaseConditions;
    }

    /**
     * @return the trialDesign
     */
    public TSRReportTrialDesign getTrialDesign() {
        return trialDesign;
    }

    /**
     * @return the eligibilityCriteria
     */
    public TSRReportEligibilityCriteria getEligibilityCriteria() {
        return eligibilityCriteria;
    }

    /**
     * @return the armGroups
     */
    public List<TSRReportArmGroup> getArmGroups() {
        return armGroups;
    }

    /**
     * @return the primaryOutcomeMeasures
     */
    public List<TSRReportOutcomeMeasure> getPrimaryOutcomeMeasures() {
        return primaryOutcomeMeasures;
    }

    /**
     * @return the secondaryOutcomeMeasures
     */
    public List<TSRReportOutcomeMeasure> getSecondaryOutcomeMeasures() {
        return secondaryOutcomeMeasures;
    }

    /**
     * @return the sgsCriterias
     */
    public List<TSRReportSubGroupStratificationCriteria> getSgsCriterias() {
        return sgsCriterias;
    }

    /**
     * @return the participatingSites
     */
    public List<TSRReportParticipatingSite> getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @return the interventions
     */
    public List<TSRReportIntervention> getInterventions() {
        return interventions;
    }

}
