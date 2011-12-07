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

import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.AnatomicSite;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.noniso.dto.InterventionShortRecord;
import gov.nih.nci.pa.noniso.dto.PDQDiseaseNode;
import gov.nih.nci.pa.report.service.Summary4ReportLocal;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PDQDiseaseServiceLocal;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.LookUpTableServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.TSRReportGeneratorServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.viewer.dto.result.KeyValueDTO;
import gov.nih.nci.pa.viewer.util.ViewerServiceLocator;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.Preparable;

/**
 * @author Max Shestopalov
 */
public class AdHocReportAction extends AbstractReportAction<StudyProtocolQueryCriteria, StudyProtocolQueryDTO>
        implements Preparable, ServletResponseAware {

    private static final long serialVersionUID = -8539599386849881611L;
    private static final int MAX_LIMIT = 100;
    
    private PDQDiseaseServiceLocal diseaseService;
    private InterventionServiceLocal interventionService;
    private LookUpTableServiceRemote lookUpTableService;
    private PAOrganizationServiceRemote paOrganizationService;
    private PlannedMarkerServiceLocal plannedMarkerService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private Summary4ReportLocal summary4ReportService;
    private TSRReportGeneratorServiceRemote tsrReportGeneratorService;
    

    private StudyProtocolQueryCriteria criteria;
    private String identifier;
    private HttpServletResponse servletResponse;
    
    private List<KeyValueDTO> families;
    private Map<String, String> organizations;  
    private Map<String, String> participatingSites;  

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() {
        setDiseaseService(PaRegistry.getDiseaseService());
        setInterventionService(PaRegistry.getInterventionService());
        setLookUpTableService(PaRegistry.getLookUpTableService());
        setPaOrganizationService(PaRegistry.getPAOrganizationService());
        setPlannedMarkerService(PaRegistry.getPlannedMarkerService());
        setProtocolQueryService(PaRegistry.getProtocolQueryService());
        setSummary4ReportService(ViewerServiceLocator.getInstance().getSummary4ReportService());
        setTsrReportGeneratorService(PaRegistry.getTSRReportGeneratorService());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String execute() {
        setCriteria(new StudyProtocolQueryCriteria());
        loadFamilies();
        loadOrganizations();
        loadParticipatingSites();
        return super.execute();
    }
    
    /**
     * Load the families.
     */
    void loadFamilies() {
        try {
            families = new ArrayList<KeyValueDTO>();
            Map<String, String> familyMap = summary4ReportService.getFamilies(MAX_LIMIT);
            for (Map.Entry<String, String> entry : familyMap.entrySet()) {
                KeyValueDTO keyValueDTO = new KeyValueDTO(Long.valueOf(entry.getKey()), entry.getValue());
                families.add(keyValueDTO);
            }
            Collections.sort(families);
        } catch (TooManyResultsException e) {
            addActionError(e.getMessage());
        }
    }

    /**
     * Get organizations based on family name.
     * @return list
     */
    public String loadOrganizations() {
        organizations = new TreeMap<String, String>();
        if (criteria != null && !criteria.getFamilyId().equals("0")) {
            try {
                Map<String, String> orgMap = summary4ReportService.getOrganizations(criteria.getFamilyId(), MAX_LIMIT);
                for (String orgName : orgMap.keySet()) {
                    getOrganizations().put(orgName, orgName);
                }
            } catch (TooManyResultsException e) {
                addActionError(e.getMessage());
            }
        }
        return super.getReport();
    }
    
    /**
     * Get participating sites based on family name.
     * @return list
     */
    public String loadParticipatingSites() {
        participatingSites = new TreeMap<String, String>();
        if (criteria != null && !criteria.getParticipatingSiteFamilyId().equals("0")) {
            try {
                Map<String, String> orgMap = summary4ReportService.getOrganizations(criteria
                    .getParticipatingSiteFamilyId(), MAX_LIMIT);
                for (String orgName : orgMap.keySet()) {
                    getParticipatingSites().put(orgName, orgName);
                }
            } catch (TooManyResultsException e) {
                addActionError(e.getMessage());
            }
        }
        return super.getReport();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String getReport() {
        loadFamilies();
        loadOrganizations();
        loadParticipatingSites();
        if (isReportInError()) {
            return super.execute();
        }
        return super.getReport();
    }

    /**
     * Generates the report.
     * @return true if there are errors.
     */
    boolean isReportInError() {
        validateIdentifierSearchParameters();
        if (hasFieldErrors()) {
            return true;
        }
        try {
            populateIdentifierSearchParameters();
            setResultList(protocolQueryService.getStudyProtocolByCriteriaForReporting(criteria));
        } catch (PAException e) {
            addActionError(e.getMessage());
            return true;
        }
        return false;
    }

    /**
     * Validates the identifier portion of the search. Checks that both the identifier type and the identifier are 
     * provider when at least one of them is provided.
     */
    void validateIdentifierSearchParameters() {
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
    void populateIdentifierSearchParameters() {
        if (criteria.getIdentifierType() != null && StringUtils.isNotEmpty(getIdentifier())) {
            criteria.setIdentifier(getIdentifier());
        }
    }

    /**
     * @return res
     * @throws PAException exception
     */
    public String viewTSR() throws PAException {
        try {
            Long pId = Long.parseLong(ServletActionContext.getRequest().getParameter("studyProtocolId"));
            ByteArrayOutputStream reportData =
                    tsrReportGeneratorService.generateRtfTsrReport(IiConverter.convertToStudyProtocolIi(pId));
            servletResponse.setHeader("Content-disposition", "inline; filename=TsrReport.rtf");
            servletResponse.setContentType("application/rtf;");
            servletResponse.setContentLength(reportData.size());
            ServletOutputStream servletout = servletResponse.getOutputStream();
            reportData.writeTo(servletout);
            servletout.flush();
        } catch (Exception e) {
            LOG.error("Error while generating TSR Summary report ", e);
        }
        return NONE;
    }

    /**
     * Return a list of lead orgs.
     * @return list of lead orgs.
     * @throws PAException on error
     */
    public List<KeyValueDTO> getLeadOrgList() throws PAException {
        List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
        for (PaOrganizationDTO dto : paOrganizationService
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.LEAD_ORGANIZATION)) {
            result.add(new KeyValueDTO(Long.parseLong(dto.getId()), dto.getName()));
        }
        return result;
    }

    /**
     * Return a list of summary 4 funding sponsors.
     * @return list of summary 4 funding sponsors.
     * @throws PAException on error
     */
    public List<PaOrganizationDTO> getSumm4FundingSponsorsList() throws PAException {
        return paOrganizationService.getOrganizationsAssociatedWithStudyProtocol(PAConstants.SUMM4_SPONSOR);
    }

    /**
     * Return a list of participating site orgs.
     * @return list of participating site orgs.
     * @throws PAException on error
     */
    public List<KeyValueDTO> getParticipatingSiteList() throws PAException {
        List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
        for (PaOrganizationDTO dto : paOrganizationService
            .getOrganizationsAssociatedWithStudyProtocol(PAConstants.PARTICIPATING_SITE)) {
            result.add(new KeyValueDTO(Long.parseLong(dto.getId()), dto.getName()));
        }
        return result;
    }

    /**
     * Return a list of anatomic sites.
     * @return list of anatomic sites.
     * @throws PAException on error
     */
    public List<AnatomicSite> getAnatomicSitesList() throws PAException {
        return lookUpTableService.getAnatomicSites();
    }

    /**
     * Return a list of planned markers.
     * @return list of planned markers.
     * @throws PAException on error
     */
    public List<KeyValueDTO> getPlannedMarkersList() throws PAException {
        List<KeyValueDTO> result = new ArrayList<KeyValueDTO>();
        for (PlannedMarkerDTO dto : plannedMarkerService.getAll()) {
            KeyValueDTO keyValue =
                    new KeyValueDTO(IiConverter.convertToLong(dto.getIdentifier()), StConverter.convertToString(dto
                        .getLongName()));
            result.add(keyValue);
        }
        return result;
    }

    /**
     * Gets the PDQDiseases for the disease section as a JSON collection.
     * @return The result name
     * @throws JSONException JSON Translation exception
     */
    public String getDiseaseTree() throws JSONException {
        List<PDQDiseaseNode> diseaseTree = diseaseService.getDiseaseTree();
        return JSONUtil.serialize(diseaseTree);
    }

    /**
     * Gets the Interventions for the interventions section as a JSON collection.
     * @return The result name
     * @throws JSONException JSON Translation exception
     * @throws PAException on error 
     */
    public String getInterventions() throws JSONException, PAException {
        List<InterventionShortRecord> interventions = interventionService.getInterventionShortRecords();
        return JSONUtil.serialize(interventions);
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
     * @return the families
     */
    public List<KeyValueDTO> getFamilies() {
        return families;
    }

    /**
     * @return the organizations
     */
    public Map<String, String> getOrganizations() {
        return organizations;
    }     
    
    /**
     * @return the participatingSites
     */
    public Map<String, String> getParticipatingSites() {
        return participatingSites;
    }

    /**
     * @param participatingSites the participatingSites to set
     */
    public void setParticipatingSites(Map<String, String> participatingSites) {
        this.participatingSites = participatingSites;
    }

    /**
     * @param response servletResponse
     */
    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.servletResponse = response;
    }

    /**
     * @param diseaseService the diseaseService to set
     */
    public void setDiseaseService(PDQDiseaseServiceLocal diseaseService) {
        this.diseaseService = diseaseService;
    }

    /**
     * @param interventionService the interventionService to set
     */
    public void setInterventionService(InterventionServiceLocal interventionService) {
        this.interventionService = interventionService;
    }

    /**
     * @param lookUpTableService the lookUpTableService to set
     */
    public void setLookUpTableService(LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param paOrganizationService the paOrganizationService to set
     */
    public void setPaOrganizationService(PAOrganizationServiceRemote paOrganizationService) {
        this.paOrganizationService = paOrganizationService;
    }

    /**
     * @param plannedMarkerService the plannedMarkerService to set
     */
    public void setPlannedMarkerService(PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }

    /**
     * @param protocolQueryService the protocolQueryService to set
     */
    public void setProtocolQueryService(ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * @param summary4ReportService the summary4ReportService to set
     */
    public void setSummary4ReportService(Summary4ReportLocal summary4ReportService) {
        this.summary4ReportService = summary4ReportService;
    }

    /**
     * @param tsrReportGeneratorService the tsrReportGeneratorService to set
     */
    public void setTsrReportGeneratorService(TSRReportGeneratorServiceRemote tsrReportGeneratorService) {
        this.tsrReportGeneratorService = tsrReportGeneratorService;
    }

}