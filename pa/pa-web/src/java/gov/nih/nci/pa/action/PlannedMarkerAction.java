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
package gov.nih.nci.pa.action;

import gov.nih.nci.cadsr.domain.ValueDomainPermissibleValue;
import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.enums.AssayPurposeCode;
import gov.nih.nci.pa.enums.AssayTypeCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.system.applicationservice.ApplicationService;
import gov.nih.nci.system.client.ApplicationServiceProvider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 * Action class for listing/manipulating planned markers.
 * 
 * @author Abraham J. Evans-EL <aevansel@5amsolutions.com>
 */
@SuppressWarnings("PMD.TooManyMethods")
public class PlannedMarkerAction extends AbstractListEditAction {

    private static final long serialVersionUID = 560802697544499600L;
    private static final Logger LOG = Logger.getLogger(PlannedMarkerAction.class);

    private ApplicationService appService;
    private PlannedMarkerServiceLocal plannedMarkerService;

    private PlannedMarkerWebDTO plannedMarker = new PlannedMarkerWebDTO();
    private List<PlannedMarkerWebDTO> plannedMarkerList;
    private String cdeId;

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepare() throws PAException {
        super.prepare();
        try {
            appService = ApplicationServiceProvider.getApplicationService();
        } catch (Exception e) {
            LOG.error("Error attempting to instatiate caDSR Application Service.", e);
        }
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String add() throws PAException {
        enforceBusinessRules();
        if (!hasFieldErrors()) {
            PlannedMarkerDTO marker = populateDTO(false);
            if (StringUtils.isNotEmpty(getPlannedMarker().getMeaning())) {
                marker.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
            } else {
                marker.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.PENDING));
            }

            try {
                plannedMarkerService.create(marker);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors() || hasFieldErrors()) {
            return super.create();
        }
        return super.add();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String edit() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
            .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        plannedMarker = populateWebDTO(marker);
        return super.edit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String update() throws PAException {
        enforceBusinessRules();
        if (!hasFieldErrors()) {
            PlannedMarkerDTO marker = populateDTO(true);
            try {
                plannedMarkerService.update(marker);
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        }
        if (hasActionErrors() || hasFieldErrors()) {
            return AR_EDIT;
        }
        return super.update();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String delete() throws PAException {
        plannedMarkerService.delete(IiConverter.convertToIi(getSelectedRowIdentifier()));
        return super.delete();
    }

    /**
     * Loads and sets the various properties from caDSR.
     * @return edit
     */
    public String displaySelectedCDE() {
        try {
            ValueDomainPermissibleValue vdpv = new ValueDomainPermissibleValue();
            vdpv.setId(getCdeId());
            Collection<Object> results = appService.search(ValueDomainPermissibleValue.class, vdpv);
            ValueDomainPermissibleValue result = (ValueDomainPermissibleValue) results.iterator().next();

            PlannedMarkerWebDTO dto = new PlannedMarkerWebDTO();
            dto.setName(result.getPermissibleValue().getValue());
            dto.setDescription(result.getPermissibleValue().getValueMeaning().getDescription());
            dto.setMeaning(result.getPermissibleValue().getValueMeaning().getLongName());
            setPlannedMarker(dto);
        } catch (Exception e) {
            ServletActionContext.getRequest().setAttribute(Constants.FAILURE_MESSAGE, e.getMessage());
        }
        return AR_EDIT;

    }

    /**
     * Reloads the planned marker screen with the requested marker name and hugo code.
     * @return edit
     */
    public String displayRequestedCDE() {
        return AR_EDIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadListForm() throws PAException {
        List<PlannedMarkerWebDTO> pmList = new ArrayList<PlannedMarkerWebDTO>();
        List<PlannedMarkerDTO> results = plannedMarkerService.getByStudyProtocol(getSpIi());
        for (PlannedMarkerDTO dto : results) {
            pmList.add(populateWebDTO(dto));
        }
        setPlannedMarkerList(pmList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadEditForm() throws PAException {
        if (plannedMarker != null && plannedMarker.getId() != null) {
            PlannedMarkerDTO markerDTO = plannedMarkerService.get(IiConverter.convertToIi(plannedMarker.getId()));
            plannedMarker = populateWebDTO(markerDTO);
        }
    }

    private void enforceBusinessRules() {
        if (StringUtils.isEmpty(getPlannedMarker().getName())) {
            addFieldError("plannedMarker.name", getText("error.plannedMarker.name"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayType())) {
            addFieldError("plannedMarker.assayType", getText("error.plannedMarker.assayType"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayUse())) {
            addFieldError("plannedMarker.assayUse", getText("error.plannedMarker.assayUse"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getAssayPurpose())) {
            addFieldError("plannedMarker.assayPurpose", getText("error.plannedMarker.assayPurpose"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getTissueCollectionMethod())) {
            addFieldError("plannedMarker.tissueCollectionMethod", 
                          getText("error.plannedMarker.tissueCollectionMethod"));
        }
        enforceAdditionalBusinessRules();
    }

    private void enforceAdditionalBusinessRules() {
        if (StringUtils.equals(getPlannedMarker().getAssayType(), AssayTypeCode.OTHER.getCode())
                && StringUtils.isEmpty(getPlannedMarker().getAssayTypeOtherText())) {
            addFieldError("plannedMarker.assayTypeOtherText", getText("error.plannedMarker.assayTypeOtherText"));
        }
        if (StringUtils.equals(getPlannedMarker().getAssayPurpose(), AssayPurposeCode.OTHER.getCode())
                && StringUtils.isEmpty(getPlannedMarker().getAssayPurposeOtherText())) {
            addFieldError("plannedMarker.assayPurposeOtherText", getText("error.plannedMarker.assayPurposeOtherText"));
        }
        if (StringUtils.isEmpty(getPlannedMarker().getTissueSpecimenType())) {
            addFieldError("plannedMarker.tissueSpecimenType", getText("error.plannedMarker.tissueSpecimenType"));
        }
    }

    private PlannedMarkerWebDTO populateWebDTO(PlannedMarkerDTO markerDTO) {
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO = new PlannedMarkerWebDTO();
        webDTO.setId(IiConverter.convertToLong(markerDTO.getIdentifier()));
        webDTO.setName(StConverter.convertToString(markerDTO.getName()));
        webDTO.setMeaning(StConverter.convertToString(markerDTO.getLongName()));
        webDTO.setDescription(StConverter.convertToString(markerDTO.getTextDescription()));
        webDTO.setHugoCode(CdConverter.convertCdToString(markerDTO.getHugoBiomarkerCode()));
        webDTO.setFoundInHugo(StringUtils.isNotEmpty(CdConverter.convertCdToString(markerDTO.getHugoBiomarkerCode())));
        webDTO.setAssayType(CdConverter.convertCdToString(markerDTO.getAssayTypeCode()));
        webDTO.setAssayTypeOtherText(StConverter.convertToString(markerDTO.getAssayTypeOtherText()));
        webDTO.setAssayUse(CdConverter.convertCdToString(markerDTO.getAssayUseCode()));
        webDTO.setAssayPurpose(CdConverter.convertCdToString(markerDTO.getAssayPurposeCode()));
        webDTO.setAssayPurposeOtherText(StConverter.convertToString(markerDTO.getAssayPurposeOtherText()));
        webDTO.setTissueSpecimenType(CdConverter.convertCdToString(markerDTO.getTissueSpecimenTypeCode()));
        webDTO.setTissueCollectionMethod(CdConverter.convertCdToString(markerDTO.getTissueCollectionMethodCode()));
        webDTO.setStatus(CdConverter.convertCdToString(markerDTO.getStatusCode()));
        return webDTO;
    }

    private PlannedMarkerDTO populateDTO(boolean isEdit) {
        PlannedMarkerDTO marker = new PlannedMarkerDTO();
        marker.setIdentifier(IiConverter.convertToIi(getPlannedMarker().getId()));
        marker.setName(StConverter.convertToSt(getPlannedMarker().getName()));
        // If no meaning (i.e. long name) is provided, use the name instead.
        if (StringUtils.isEmpty(getPlannedMarker().getMeaning()) || isEdit) {
            marker.setLongName(marker.getName());
        } else {
            marker.setLongName(StConverter.convertToSt(getPlannedMarker().getMeaning()));
        }
        marker.setTextDescription(StConverter.convertToSt(getPlannedMarker().getDescription()));
        if (getPlannedMarker().isFoundInHugo()) {
            marker.setHugoBiomarkerCode(CdConverter.convertStringToCd(getPlannedMarker().getHugoCode()));
        }
        marker.setAssayTypeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayType()));
        if (StringUtils.equals(getPlannedMarker().getAssayType(), AssayTypeCode.OTHER.getCode())) {
            marker.setAssayTypeOtherText(StConverter.convertToSt(getPlannedMarker().getAssayTypeOtherText()));
        }
        marker.setAssayUseCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayUse()));
        marker.setAssayPurposeCode(CdConverter.convertStringToCd(getPlannedMarker().getAssayPurpose()));
        if (StringUtils.equals(getPlannedMarker().getAssayPurpose(), AssayPurposeCode.OTHER.getCode())) {
            marker.setAssayPurposeOtherText(StConverter.convertToSt(getPlannedMarker().getAssayPurposeOtherText()));
        }
        marker.setTissueSpecimenTypeCode(CdConverter.convertStringToCd(getPlannedMarker().getTissueSpecimenType()));
        marker.setTissueCollectionMethodCode(CdConverter.convertStringToCd(getPlannedMarker()
            .getTissueCollectionMethod()));
        marker.setStatusCode(CdConverter.convertStringToCd(getPlannedMarker().getStatus()));
        marker.setStudyProtocolIdentifier(getSpIi());
        return marker;
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
     * @return the plannedMarkerList
     */
    public List<PlannedMarkerWebDTO> getPlannedMarkerList() {
        return plannedMarkerList;
    }

    /**
     * @param plannedMarkerList the plannedMarkerList to set
     */
    public void setPlannedMarkerList(List<PlannedMarkerWebDTO> plannedMarkerList) {
        this.plannedMarkerList = plannedMarkerList;
    }

    /**
     * @return the cdeId
     */
    public String getCdeId() {
        return cdeId;
    }

    /**
     * @param cdeId the cdeId to set
     */
    public void setCdeId(String cdeId) {
        this.cdeId = cdeId;
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
     * @param plannedMarkerService the plannedMarkerService to set
     */
    public void setPlannedMarkerService(PlannedMarkerServiceLocal plannedMarkerService) {
        this.plannedMarkerService = plannedMarkerService;
    }
}
