/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The viewer
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This viewer Software License (the License) is between NCI and You. You (or
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
 * its rights in the viewer Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the viewer Software; (ii) distribute and
 * have distributed to and by third parties the viewer Software and any
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
package gov.nih.nci.pa.viewer.action;

import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 * @author Max Shestopalov
 */
public class AdHocReportAction
        extends AbstractReportAction <StudyProtocolQueryCriteria, StudyProtocolQueryDTO>
    implements ServletResponseAware {

    private static final long serialVersionUID = 7222372874396709972L;
    private StudyProtocolQueryCriteria criteria;
    private String identifier;
    private HttpServletResponse servletResponse;
    private String diseaseName;

    /**
     * Limit of search results.
     */
    public static final int MAX_LIMIT = 100;

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        setCriteria(new StudyProtocolQueryCriteria());
        return super.execute();
    }

    /**
     * Fill the id and name of the disease chosen on search form.
     * @return success
     */
    public String fillInDiseaseId() {
        return SUCCESS;
    }

    private boolean isReportInError() {
        validateIdentifierSearchParameters();
        if (hasFieldErrors()) {
            return true;
        }
        ProtocolQueryServiceLocal local = ViewerServiceLocator.getInstance().getProtocolQueryService();
        try {
            populateIdentifierSearchParameters();
            setResultList(local.getStudyProtocolByCriteriaForReporting(criteria));
        } catch (PAException e) {
            addActionError(e.getMessage());
            return true;
        }
        return false;
    }

    /**
     * Validates the identifier portion of the search.
     */
    public void validateIdentifierSearchParameters() {
        if (criteria.getIdentifierType() != null && StringUtils.isEmpty(getIdentifier())) {
            addFieldError("identifier", getText("error.studyProtocol.identifier"));
        }
        if (StringUtils.isNotEmpty(getIdentifier()) && criteria.getIdentifierType() == null) {
            addFieldError("criteria.identifierType", getText("error.studyProtocol.identifierType"));
        }
    }

    /**
     * Populates the identifier search parameters.
     */
    public void populateIdentifierSearchParameters() {
        if (criteria.getIdentifierType() != null && StringUtils.isNotEmpty(getIdentifier())) {
            checkTrialSearchIdentifiers();
            checkLeadOrgIdentifier();
            checkOtherIdentifier();
        }
    }

    private void checkTrialSearchIdentifiers() {
        if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.NCI.getCode())) {
            criteria.setNciIdentifier(getIdentifier());
        } else if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.DCP.getCode())) {
            criteria.setDcpIdentifier(getIdentifier());
        } else if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.CTEP.getCode())) {
            criteria.setCtepIdentifier(getIdentifier());
        } else if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.NCT.getCode())) {
            criteria.setNctNumber(getIdentifier());
        }
    }

    private void checkLeadOrgIdentifier() {
        if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.LEAD_ORG.getCode())) {
            criteria.setLeadOrganizationTrialIdentifier(getIdentifier());
        }
    }

    private void checkOtherIdentifier() {
        if (StringUtils.equals(criteria.getIdentifierType(), IdentifierType.OTHER_IDENTIFIER.getCode())) {
            criteria.setOtherIdentifier(getIdentifier());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {

        if (isReportInError()) {
            return super.execute();
        }
        return super.getReport();
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String viewTSR() throws PAException {

        try {
            String pId = ServletActionContext.getRequest().getParameter("studyProtocolId");
            ByteArrayOutputStream reportData =
                PaRegistry.getTSRReportGeneratorService().generateRtfTsrReport(IiConverter.convertToIi(pId));
            servletResponse.setHeader("Content-disposition", "inline; filename=TsrReport.rtf");
            servletResponse.setContentType("application/rtf;");
            servletResponse.setContentLength(reportData.size());
            ServletOutputStream servletout = servletResponse.getOutputStream();
            reportData.writeTo(servletout);
            servletout.flush();
          } catch (Exception e) {
              LOG.error("Error while generating TSR Summary report " , e);
              return NONE;
          }
          return NONE;
    }
    
    /**
     * Return a list of lead orgs.
     * @return list of lead orgs.
     * @throws PAException on error
     */
    public List<PaOrganizationDTO> getLeadOrgList() throws PAException {
        return PaRegistry.getPAOrganizationService()
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.LEAD_ORGANIZATION);
    }

    /**
     * Return a list of summary 4 funding sponsors.
     * @return list of summary 4 funding sponsors.
     * @throws PAException on error
     */
    public List<PaOrganizationDTO> getSumm4FunsingSponsorsList() throws PAException {
        return PaRegistry.getPAOrganizationService()
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.SUMM4_SPONSOR);
    }
    
    /**
     * Return a list of participating site orgs.
     * @return list of participating site orgs.
     * @throws PAException on error
     */
    public List<PaOrganizationDTO> getParticipatingSiteList() throws PAException {
        return PaRegistry.getPAOrganizationService()
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.PARTICIPATING_SITE);
    }
    
    /**
     * Return a list of anatomic sites.
     * @return ist of anatomic sites.
     * @throws PAException on error
     */
    public List<AnatomicSite> getAnatomicSitesList() throws PAException {
        return  PaRegistry.getLookUpTableService().getAnatomicSites();
    }

    /**
     * @return the criteria
     */
    public StudyProtocolQueryCriteria getCriteria() {
        return criteria;
    }
    /**
     * @param criteria the criteria to set
     */
    public void setCriteria(StudyProtocolQueryCriteria criteria) {
        this.criteria = criteria;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @param diseaseName the diseaseName to set
     */
    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    /**
     * @return the diseaseName
     */
    public String getDiseaseName() {
        return diseaseName;
    }   

}