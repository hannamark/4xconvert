/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The accrual
 * Software was developed in conjunction with the National Cancer Institute 
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent 
 * government employees are authors, any rights in such works shall be subject 
 * to Title 17 of the United States Code, section 105. 
 *
 * This accrual Software License (the License) is between NCI and You. You (or 
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
 * its rights in the accrual Software to (i) use, install, access, operate, 
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the accrual Software; (ii) distribute and 
 * have distributed to and by third parties the accrual Software and any 
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
package gov.nih.nci.accrual.accweb.action;

import gov.nih.nci.accrual.accweb.util.AccrualConstants;
import gov.nih.nci.accrual.dto.util.SearchTrialResultDto;
import gov.nih.nci.accrual.util.AccrualServiceLocator;
import gov.nih.nci.pa.domain.StudySiteSubjectAccrualCount;
import gov.nih.nci.pa.enums.AccrualSubmissionTypeCode;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Preparable;

/**
 * @author moweis
 *
 */
public class IndustrialPatientAction extends AbstractAccrualAction implements Preparable {

    private static final long serialVersionUID = 1L;
    private Long studyProtocolId;
    private List<StudySiteSubjectAccrualCount> studySiteCounts;
    private List<Long> sitesToSave;
    private List<Long> submittedSiteIds = new ArrayList<Long>();
    private List<String> submittedCounts = new ArrayList<String>();
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        super.prepare();
        try {
            if (getStudyProtocolId() != null) {
                loadTrialSummaryIntoSession();
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
    }

    private void loadTrialSummaryIntoSession() throws PAException {
        setSpIi(IiConverter.convertToStudyProtocolIi(getStudyProtocolId()));
        SearchTrialResultDto trialSummary = getSearchTrialSvc().getTrialSummaryByStudyProtocolIi(getSpIi());
        ServletActionContext.getRequest().getSession().setAttribute("trialSummary", trialSummary);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        if (isSessionTrialNotIndustrial()) {
            addActionError(getText("error.invalidaccess.nonindustrial"));
            return "invalid";
        }
        loadSiteSubjectAccrualCount();
        return super.execute();
    }
    
    private void loadSiteSubjectAccrualCount() {
        try {
            setStudySiteCounts(getCounts());
        } catch (PAException e) {
            addActionError(e.getMessage());
        }        
    }

    private boolean isSessionTrialNotIndustrial() {
        SearchTrialResultDto trialSummary = (SearchTrialResultDto) ServletActionContext.getRequest().getSession()
                .getAttribute("trialSummary");
        return !BlConverter.convertToBoolean(trialSummary.getIndustrial());
    }

    /**
     * Update the subject accrual counts.
     * @return result name
     */
    public String update() {
        try {
            if (getSitesToSave() != null) {
                List<StudySiteSubjectAccrualCount> counts = getCounts();
                parseCounts(counts);
                AccrualServiceLocator.getInstance().getSubjectAccrualCountService().save(counts);
                ServletActionContext.getRequest().setAttribute(AccrualConstants.SUCCESS_MESSAGE,
                        AccrualConstants.UPDATE_MESSAGE);
                setSitesToSave(null);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
            loadSiteSubjectAccrualCount();
            return "input";
        }
        return "saved";
    }

    private void parseCounts(List<StudySiteSubjectAccrualCount> counts) throws PAException {
        for (int i = 0; i < getSubmittedSiteIds().size(); i++) {
            if (getSitesToSave().contains(getSubmittedSiteIds().get(i))) {
                parseCount(counts, i);
            }
        }
    }

    private void parseCount(List<StudySiteSubjectAccrualCount> counts, int countIndex) throws PAException {
        StudySiteSubjectAccrualCount count = getSiteCount(counts, getSubmittedSiteIds().get(countIndex));
        updateCountForSiteIfSet(count, getSubmittedCounts().get(countIndex));
    }

    StudySiteSubjectAccrualCount getSiteCount(List<StudySiteSubjectAccrualCount> counts, Long siteId)
            throws PAException {
        for (StudySiteSubjectAccrualCount count : counts) {
            if (count.getStudySite().getId().equals(siteId)) {
                return count;
            }
        }
        throw new PAException("Invalid site id for study - (Study Protocol Id, Site Id): (" + getStudyProtocolId()
                + ", " + siteId);
    }

    void updateCountForSiteIfSet(StudySiteSubjectAccrualCount count, String submittedCount) throws PAException {
        if (StringUtils.isNotBlank(submittedCount)) {
            assertValidCount(submittedCount);
            count.setAccrualCount(Integer.parseInt(submittedCount));
            count.setSubmissionTypeCode(AccrualSubmissionTypeCode.UI);
        }
    }

    void assertValidCount(String submittedCount) throws PAException {
        try {
            if (Integer.parseInt(submittedCount) < 0) {
                throw new PAException(getText("error.accrual.count.invalid"));
            }
        } catch (NumberFormatException e) {
            throw new PAException(getText("error.accrual.count.invalid"), e);
        }
    }

    private List<StudySiteSubjectAccrualCount> getCounts() throws PAException {
        return AccrualServiceLocator.getInstance().getSubjectAccrualCountService().getCounts(getSpIi());
    }
    
    /**
     * @return the studyProtocolId
     */
    public Long getStudyProtocolId() {
        return studyProtocolId;
    }

    /**
     * @param studyProtocolId the studyProtocolId to set
     */
    public void setStudyProtocolId(Long studyProtocolId) {
        this.studyProtocolId = studyProtocolId;
    }

    /**
     * @return the studySiteCounts
     */
    public List<StudySiteSubjectAccrualCount> getStudySiteCounts() {
        return studySiteCounts;
    }

    /**
     * @param studySiteCounts the studySiteCounts to set
     */
    public void setStudySiteCounts(List<StudySiteSubjectAccrualCount> studySiteCounts) {
        this.studySiteCounts = studySiteCounts;
        ServletActionContext.getRequest().setAttribute("studySiteCounts", getStudySiteCounts());
    }

    /**
     * @param submittedSiteIds the submittedSiteIds to set
     */
    public void setSubmittedSiteIds(List<Long> submittedSiteIds) {
        this.submittedSiteIds = submittedSiteIds;
    }

    /**
     * @return the submittedSiteIds
     */
    public List<Long> getSubmittedSiteIds() {
        return submittedSiteIds;
    }

    /**
     * @return the submittedCounts
     */
    public List<String> getSubmittedCounts() {
        return submittedCounts;
    }

    /**
     * @param submittedCounts the submittedCounts to set
     */
    public void setSubmittedCounts(List<String> submittedCounts) {
        this.submittedCounts = submittedCounts;
    }

    /**
     * @return the sitesToSave
     */
    public List<Long> getSitesToSave() {
        return sitesToSave;
    }

    /**
     * @param sitesToSave the sitesToSave to set
     */
    public void setSitesToSave(List<Long> sitesToSave) {
        this.sitesToSave = sitesToSave;
    }
}
