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
package gov.nih.nci.pa.action.popup;

import gov.nih.nci.cadsr.domain.DataElement;
import gov.nih.nci.cadsr.domain.EnumeratedValueDomain;
import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.cadsr.domain.ValueMeaning;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.CaDSRWebDTO;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.CsmHelper;
import gov.nih.nci.pa.util.ISOUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.ranking.RankBasedSorterUtils;
import gov.nih.nci.pa.util.ranking.Serializer;
import gov.nih.nci.security.authorization.domainobjects.User;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action for handling the caDSR popup for planned markers.
 *
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings({ "PMD.ExcessiveClassLength", "PMD.CyclomaticComplexity", 
       "PMD.TooManyFields", "PMD.TooManyMethods" })
public class PlannedMarkerPopupAction extends ActionSupport implements Preparable {
    private static final long serialVersionUID = 1L;
    /**
     * The Public ID of the Marker CDE.
     */
    private static final Long CDE_PUBLIC_ID = 5473L;
    private static final String CADSR_RESULTS = "results";
    private static final String MARKER_ACCEPT = "accept";
    private static final String EMAIL = "email";
    private static final String EMAIL_PENDING_PAGE = "markeremail";
    private static final String PV_VALUE = "pv.value";
    private static final String LONG_NAME = "vm.longName";
    private static final String DESCRIPTION = "vm.description";
    private static final String TRUE = "true";
    private String name;
    private String meaning;
    private String description;
    private String publicId;
    private String subject;
    private String toEmail;
    private List<CaDSRWebDTO> markers;
    private PlannedMarkerWebDTO plannedMarker = new PlannedMarkerWebDTO();
    private boolean passedValidation = false;
    private ApplicationService appService;
    private PlannedMarkerServiceLocal plannedMarkerService;
    private String selectedRowIdentifier;
    private String caDsrId;
    private String caseType;
    private String highlightRequired;
    private String showActionColumn; 
    private String fromNewRequestPage;
    private String nciIdentifier;
    private static final Logger LOG = Logger.getLogger(PlannedMarkerPopupAction.class);
    /**
     * {@inheritDoc}
     */
    public void prepare() {
        markers = new ArrayList<CaDSRWebDTO>();
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error("Error attempting to instantiate caDSR Application Service.", e);
        }  
    }

    /**
     * Performs the caDSR lookup for markers.
     * @return results
     */
    public String lookup() {
        if (validateInput()) {
            return CADSR_RESULTS;
        }
        try {
            DataElement dataElement = new DataElement();
            dataElement.setPublicID(CDE_PUBLIC_ID);
            dataElement.setLatestVersionIndicator("Yes");
            Collection<Object> results = appService.search(DataElement.class, dataElement);
            DataElement de = (DataElement) results.iterator().next();
            String vdId = ((EnumeratedValueDomain) de.getValueDomain()).getId();
            
            List<Object> permissibleValues = appService.query(constructSearchCriteria(vdId));
            List<CaDSRWebDTO> values = getSearchResults(permissibleValues);
            markers.addAll(values);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("error.plannedMarker.request.caDSRLookup"));
        }
        return CADSR_RESULTS;
    }
    
    /**
     * Changes marker status to ACTIVE.
     * @return string
     * @throws PAException exception
     */
    public String accept() throws PAException {
        setPublicId(getCaDsrId());
        lookup();
        return MARKER_ACCEPT;
    }   
    /**
     * Setup CDE creation request.
     * @return email
     * @throws PAException on error
     */
    public String setupEmailRequest() throws PAException {
        setToEmail(PaRegistry.getLookUpTableService().getPropertyValue("CDE_REQUEST_TO_EMAIL"));
        User csmUser = null;
        String returnValue = "";
        try {
            if (StringUtils.equals(TRUE, getFromNewRequestPage())) {
               CsmHelper userHelper = (CsmHelper) ServletActionContext
                        .getRequest().getSession().getAttribute("CsmHelper");
               csmUser = CSMUserService.getInstance().getCSMUser(userHelper.getUsername());
               getPlannedMarker().setName(getName());
               PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
                   .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
               if (!ISOUtil.isCdNull(marker.getHugoBiomarkerCode())) {
                     getPlannedMarker().setHugoCode(CdConverter
                                .convertCdToString(marker.getHugoBiomarkerCode()));
                     getPlannedMarker().setFoundInHugo(true);
               }
               returnValue = EMAIL_PENDING_PAGE;
             } else {
               csmUser = CSMUserService.getInstance().getCSMUser(
               ServletActionContext.getRequest().getSession()
               .getAttribute(Constants.LOGGED_USER_NAME).toString());
               returnValue = EMAIL;
             }
        } catch (PAException e) {
            LOG.info("Unable to set User", e);
        }
        if (csmUser.getEmailId() != null && !(csmUser.getEmailId().equals(""))) {
            getPlannedMarker().setFromEmail(csmUser.getEmailId());  
        } else {
        getPlannedMarker().setFromEmail(
                PaRegistry.getLookUpTableService().getPropertyValue("CDE_MARKER_REQUEST_FROM_EMAIL"));
        }
        return returnValue;
    }

    /**
     * Sends the CDE request request.
     * @return email
     * @throws PAException on error
     */
    public String sendEmailRequest() throws PAException {
        validateEmailRequest();
        if (hasFieldErrors()) {
            setToEmail(PaRegistry.getLookUpTableService().getPropertyValue("CDE_REQUEST_TO_EMAIL"));
            return EMAIL;
        }
        Ii studyProtocolIi =
            (Ii) ServletActionContext.getRequest().getSession().getAttribute(Constants.STUDY_PROTOCOL_II);
        PlannedMarkerDTO dto = new PlannedMarkerDTO();
        dto.setName(StConverter.convertToSt(getPlannedMarker().getName()));
        User csmUser = null;
        try {
            csmUser = CSMUserService.getInstance().getCSMUser(
                   ServletActionContext.getRequest().getSession().getAttribute(Constants.LOGGED_USER_NAME).toString());
        } catch (PAException e) {
            LOG.info("Unable to set User", e);
        }
        dto.setUserLastCreated(StConverter.convertToSt(csmUser.getLoginName()));
        if (getPlannedMarker().isFoundInHugo()) {
            dto.setHugoBiomarkerCode(CdConverter.convertStringToCd(getPlannedMarker().getHugoCode()));
        }
        try {
            PaRegistry.getMailManagerService().sendMarkerCDERequestMail(studyProtocolIi,
                    getPlannedMarker().getFromEmail(), dto, getPlannedMarker().getMessage());
            passedValidation = true; 
            getPlannedMarker().setDateEmailSent(new Date());
            dto.setDateEmailSent(TsConverter.convertToTs(new Date()));
        } catch (Exception e) {
            passedValidation = false;
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("error.plannedMarker.request.sendEmail"));
        }
        return EMAIL;
    }
    /**
     * sends email request and updates the marker with the email date sent
     * @throws  PAException PAException
     * @return email
     */
    public String sendEmailRequestWithMarkerUpdate() throws PAException { 
        PlannedMarkerDTO markerDto = PaRegistry.getPlannedMarkerService()
                .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        validateEmailRequest();
        Ii studyProtocolIi = IiConverter.convertToIi(getNciIdentifier()); 
        studyProtocolIi.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        StudyProtocolDTO spdto = PaRegistry.getStudyProtocolService()
                .getStudyProtocol(studyProtocolIi);
        markerDto.setName(StConverter.convertToSt(getPlannedMarker().getName()));
        User csmUser = null;
        try {
           CsmHelper userHelper = (CsmHelper) ServletActionContext
                    .getRequest().getSession().getAttribute("CsmHelper");
           csmUser = CSMUserService.getInstance().getCSMUser(userHelper.getUsername());
        } catch (PAException e) {
            LOG.info("Unable to set User", e);
        }
        markerDto.setUserLastCreated(StConverter.convertToSt(csmUser.getLoginName()));
        if (getPlannedMarker().isFoundInHugo()) {
            markerDto.setHugoBiomarkerCode(CdConverter
                .convertStringToCd(getPlannedMarker().getHugoCode()));
        }
        try {
            PaRegistry.getMailManagerService().sendMarkerCDERequestMail(spdto.getIdentifier(),
                 getPlannedMarker().getFromEmail(), markerDto, getPlannedMarker().getMessage());
            passedValidation = true; 
            getPlannedMarker().setDateEmailSent(new Date());
            markerDto.setDateEmailSent(TsConverter.convertToTs(new Date()));
            plannedMarkerService.update(markerDto);
        } catch (Exception e) {
            passedValidation = false;
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("error.plannedMarker.request.sendEmail"));
        } 
        return EMAIL;
    }

    private void validateEmailRequest() {
        if (StringUtils.isEmpty(getPlannedMarker().getName())) {
            addFieldError("plannedMarker.name", getText("error.plannedMarker.request.name"));
        }
        if (!PAUtil.isValidEmail(getPlannedMarker().getFromEmail())) {
            addFieldError("plannedMarker.fromEmail", getText("error.plannedMarker.request.fromEmail"));
        }
        if (getPlannedMarker().isFoundInHugo() && StringUtils.isEmpty(getPlannedMarker().getHugoCode())) {
            addFieldError("plannedMarker.hugoCode", getText("error.plannedMarker.request.hugoCode"));
        }
    }

    /**
     * Constructs the appropriate search criteria based on the given search parameters.
     * @param vdId the id of the value domain to search
     * @return the constructed criteria
     */
    @SuppressWarnings({ "PMD.CyclomaticComplexity" })
    private DetachedCriteria constructSearchCriteria(String vdId) {
        DetachedCriteria criteria = DetachedCriteria.forClass(ValueDomainPermissibleValue.class, "vdpv");
        criteria.add(Expression.eq("enumeratedValueDomain.id", vdId));
        criteria.createAlias("permissibleValue", "pv").createAlias("pv.valueMeaning", "vm");
        //If public id is specified we only want to search using that.
        if (StringUtils.isNotEmpty(getPublicId())) {
            criteria.add(Expression.eq("vm.publicID", Long.valueOf(getPublicId())));
            return criteria;
        }
        if (StringUtils.equals(TRUE, getCaseType())) {
           if (StringUtils.isNotEmpty(getName())) {
                String newName = getName();
                if (newName.contains("-")) {
                     newName = getName().replaceAll("-", "");
                }
               criteria.add(Expression.or(Expression.or(
                      Expression.sqlRestriction("replace(value, '-', '') like '%" + getName() + "%'"), 
                      Expression.like(PV_VALUE, getName(), MatchMode.ANYWHERE)), 
                      Expression.like(PV_VALUE, newName, MatchMode.ANYWHERE)));
            }
            if (StringUtils.isNotEmpty(getMeaning())) {
                criteria.add(Expression.like(LONG_NAME, getMeaning(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotEmpty(getDescription())) {
                criteria.add(Expression.like(DESCRIPTION, getDescription(), MatchMode.ANYWHERE));
            }
        } else {
           if (StringUtils.isNotEmpty(getName())) {
                String newName = getName();
                if (newName.contains("-")) {
                     newName = getName().replaceAll("-", "");
                }
               criteria.add(Expression.or(Expression.or(
                      Expression.sqlRestriction("replace(value, '-', '') like '%" + getName() + "%'"), 
                      Expression.ilike(PV_VALUE, getName(), MatchMode.ANYWHERE)), 
                      Expression.ilike(PV_VALUE, newName, MatchMode.ANYWHERE)));
            }
            if (StringUtils.isNotEmpty(getMeaning())) {
                criteria.add(Expression.ilike(LONG_NAME, getMeaning(), MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotEmpty(getDescription())) {
                criteria.add(Expression.ilike(DESCRIPTION, getDescription(), MatchMode.ANYWHERE));
            }
        }
        
        return criteria;
    }

    private List<CaDSRWebDTO> getSearchResults(List<Object> permissibleValues) {
        List<CaDSRWebDTO> results = new ArrayList<CaDSRWebDTO>();
        List<CaDSRWebDTO> resultsMain = new ArrayList<CaDSRWebDTO>();
        for (Object obj : permissibleValues) {
          ValueDomainPermissibleValue vdpv = (ValueDomainPermissibleValue) obj;
          CaDSRWebDTO dto = new CaDSRWebDTO();
          ValueMeaning vm = vdpv.getPermissibleValue().getValueMeaning();
          dto.setId(vdpv.getId());
          dto.setVmName(vdpv.getPermissibleValue().getValue());
          dto.setVmMeaning(vm.getLongName());
          dto.setVmDescription(vm.getDescription());
          dto.setPublicId(vm.getPublicID());
          results.add(dto);
       }
        List<CaDSRWebDTO> output = new ArrayList<CaDSRWebDTO>();
        if (getName() != null && !getName().isEmpty()) {
            // Sort the results as per PO-6898
            output = RankBasedSorterUtils.sortCaDSRResults(
               results, getName(), new Serializer<CaDSRWebDTO>() {
                public String serialize(CaDSRWebDTO object) {
                  return object.getVmName();
               }
           });
        } else {
            output = results;
        }
           // add the highlight to the search text
        if (StringUtils.equals(TRUE, getHighlightRequired())) {
              for (CaDSRWebDTO dto : output) {
                   dto.setId(dto.getId());
                   dto.setVmName(replaceWithHighlightText(
                    replaceHTMLCharacters(dto.getVmName()), getName()));
                   dto.setVmMeaning(replaceWithHighlightText(
                    replaceHTMLCharacters(dto.getVmMeaning()), getMeaning()));
                   dto.setVmDescription(replaceWithHighlightText(
                    replaceHTMLCharacters(dto.getVmDescription()), getDescription()));
                   dto.setPublicId(dto.getPublicId());
                   resultsMain.add(dto);
               }
        } else {
             return output;
        }
        return resultsMain;
    }
    
    private String replaceHTMLCharacters(String inputData) {
        String outputData = StringEscapeUtils.escapeHtml(inputData);
        return outputData;
    }
    
    private String replaceWithHighlightText(String inputData, String searchText) {
        String outputData = inputData;
        if (searchText != null && !searchText.isEmpty()) {
            String highlight = "<span class=\"highlight\">" + searchText + "</span>";
            
            if (StringUtils.equals(TRUE, getCaseType())) {
               outputData = StringUtils.replace(inputData, searchText, highlight);
            } else {
               outputData = inputData.replaceAll("(?i)" + searchText, highlight);
            }
        }
        return outputData;
    }
    /**
     * Validates search form input.
     * @return true iff a validation error has occurred
     */
    private boolean validateInput() {
        String allParams = StringUtils.join(new String[] {getMeaning(), getName(), getDescription(), getPublicId()});
        if (!StringUtils.isEmpty(getSelectedRowIdentifier()) && StringUtils.isEmpty(getPublicId())) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("plannedMarker.lookup.publicId.criteria.error"));
            return true;
        }
        if (StringUtils.isEmpty(allParams)) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("plannedMarker.lookup.criteria.error"));
            return true;
        }
        if (StringUtils.isNotEmpty(getPublicId()) && !NumberUtils.isNumber(getPublicId())) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE,
                    getText("plannedMarker.lookup.criteria.publicId.error"));
            return true;
        }
        return false;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the meaning
     */
    public String getMeaning() {
        return meaning;
    }

    /**
     * @param meaning the meaning to set
     */
    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the publicId
     */
    public String getPublicId() {
        return publicId;
    }

    /**
     * @param publicId the publicId to set
     */
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /**
     * @return the markers
     */
    public List<CaDSRWebDTO> getMarkers() {
        return markers;
    }

    /**
     * @param markers the markers to set
     */
    public void setMarkers(List<CaDSRWebDTO> markers) {
        this.markers = markers;
    }

    /**
     * @return the plannedMarker
     */
    public PlannedMarkerWebDTO getPlannedMarker() {
        return plannedMarker;
    }

    /**
     * @param plannedMarker the plannedMarker to set
     */
    public void setPlannedMarker(PlannedMarkerWebDTO plannedMarker) {
        this.plannedMarker = plannedMarker;
    }

    /**
     * @return the passedValidation
     */
    public boolean isPassedValidation() {
        return passedValidation;
    }

    /**
     * @param passedValidation the passedValidation to set
     */
    public void setPassedValidation(boolean passedValidation) {
        this.passedValidation = passedValidation;
    }

    /**
     * @return the appService
     */
    public ApplicationService getAppService() {
        return appService;
    }

    /**
     * @param appService the appService to set
     */
    public void setAppService(ApplicationService appService) {
        this.appService = appService;
    }

    /**
     * @return the subject
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return the toEmail
     */
    public String getToEmail() {
        return toEmail;
    }

    /**
     * @param toEmail the toEmail to set
     */
    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }
    

    /**
     * 
     * @return selectedRowIdentifier
     */
    public String getSelectedRowIdentifier() {
        return selectedRowIdentifier;
    }

    /**
     * 
     * @param selectedRowIdentifier selectedRowIdentifier
     */
    public void setSelectedRowIdentifier(String selectedRowIdentifier) {
        this.selectedRowIdentifier = selectedRowIdentifier;
    }
    
    /**
     * @return plannedMarkerService
     */
    public PlannedMarkerServiceLocal getPlannedMarkerService() {
        return plannedMarkerService;
    }

    /**
     * 
     * @param plannedMarkerService plannedMarkerService
     */
    public void setPlannedMarkerService(
            PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }
    
    /**
     * 
     * @return caDsrId caDsrId
     */
    public String getCaDsrId() {
        return caDsrId;
    }
    /**
     * 
     * @param caDsrId caDsrId
     */
    public void setCaDsrId(String caDsrId) {
        this.caDsrId = caDsrId;
    }
    /**
     * 
     * @return caseType caseType
     */
    public String getCaseType() {
        return caseType;
    }
    /**
     * 
     * @param caseType caseType
     */
    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
    /**
     * 
     * @return highlightRequired highlightRequired
     */
    public String getHighlightRequired() {
        return highlightRequired;
    }
    /**
     * 
     * @param highlightRequired highlightRequired
     */
    public void setHighlightRequired(String highlightRequired) {
        this.highlightRequired = highlightRequired;
    }
    /**
     * 
     * @return showActionColumn showActionColumn
     */
    public String getShowActionColumn() {
        return showActionColumn;
    }
    /**
     * 
     * @param showActionColumn showActionColumn
     */
    public void setShowActionColumn(String showActionColumn) {
        this.showActionColumn = showActionColumn;
    }
    /**
     * 
     * @return fromNewRequestPage fromNewRequestPage
     */
    public String getFromNewRequestPage() {
        return fromNewRequestPage;
    }
    /**
     * 
     * @param fromNewRequestPage fromNewRequestPage
     */
    public void setFromNewRequestPage(String fromNewRequestPage) {
        this.fromNewRequestPage = fromNewRequestPage;
    }
    /**
     * 
     * @return nciIdentifier nciIdentifier
     */
    public String getNciIdentifier() {
        return nciIdentifier;
    }
    /**
     * 
     * @param nciIdentifier nciIdentifier
     */
    public void setNciIdentifier(String nciIdentifier) {
        this.nciIdentifier = nciIdentifier;
    }
}
