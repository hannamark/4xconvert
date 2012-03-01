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

import gov.nih.nci.pa.dto.PlannedMarkerWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.ActiveInactivePendingCode;
import gov.nih.nci.pa.iso.dto.PlannedMarkerDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedMarkerServiceLocal;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.security.authorization.domainobjects.User;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * 
 * @author Gaurav Gupta
 *
 */
public class BioMarkersQueryAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = -2137469104765932059L;
    /**
     * to view edit marker name page.
     */
    protected static final String MARKER_EDIT = "edit";
    /**
     * to submit marker question.
     */
    protected static final String MARKER_QUESTION = "question";

    private PlannedMarkerServiceLocal plannedMarkerService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private List<PlannedMarkerDTO> plannedMarkers;
    private List<PlannedMarkerWebDTO> plannedMarkerList;
    private PlannedMarkerWebDTO plannedMarker = new PlannedMarkerWebDTO();

    private String selectedRowIdentifier;

    @Override
    public void prepare() {
        plannedMarkerService = PaRegistry.getPlannedMarkerService();
        protocolQueryService = PaRegistry.getProtocolQueryService();
    }

    @Override
    public String execute() throws PAException {
        List<PlannedMarkerWebDTO> pmList = new ArrayList<PlannedMarkerWebDTO>();
        plannedMarkers = plannedMarkerService.getPlannedMarkers();
        for (PlannedMarkerDTO dto : plannedMarkers) {
            pmList.add(populateWebDTO(dto));
        }    
        setPlannedMarkerList(pmList);
        return SUCCESS;
    }

    /**
     * 
     * @return string marker edit jsp
     * @throws PAException exception
     */
    public String edit() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        plannedMarker = populateWebDTO(marker);
        return MARKER_EDIT;
    }

    /**
     * 
     * @return string submit marker question jsp
     * @throws PAException exception
     */
    public String sendQuestion() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        plannedMarker = populateWebDTO(marker);
        return MARKER_QUESTION;
    }

    /**
     * 
     * @return string marker view jsp
     * @throws PAException exception
     */
    public String sendQuestionMail() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(plannedMarker.getId()));
        PlannedMarkerWebDTO webDTO = populateWebDTO(marker);
        try {
            PaRegistry.getMailManagerService().sendMarkerQuestionToCTROMail(webDTO.getNciIdentifier(),
                    webDTO.getCsmUserEmailId(), marker, plannedMarker.getQuestion());
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return execute();
    }

    /**
     * Updates the marker name and change status to ACTIVE.
     * @return string
     * @throws PAException exception
     */
    public String update() throws PAException {
            PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
            .get(IiConverter.convertToIi(getPlannedMarker().getId()));
            PlannedMarkerWebDTO webDTO = populateWebDTO(marker);
            
            List<PlannedMarkerDTO> markerDTOs = plannedMarkerService.getPendingPlannedMarkersWithName(
                    StConverter.convertToString(marker.getLongName()));
            for (PlannedMarkerDTO markerDTO : markerDTOs) {
                markerDTO.setLongName(StConverter.convertToSt(plannedMarker.getMeaning()));
                markerDTO.setName(StConverter.convertToSt(plannedMarker.getMeaning()));
                markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
                plannedMarkerService.update(markerDTO);
            }           
            try {                                
                PaRegistry.getMailManagerService().sendMarkerAcceptanceMailToCDE(
                        webDTO.getNciIdentifier(), webDTO.getCsmUserEmailId(), marker);           
            } catch (PAException e) {
                addActionError(e.getMessage());
            }
        return execute();
    }

    /**
     * Changes marker status to ACTIVE.
     * @return string
     * @throws PAException exception
     */
    public String accept() throws PAException {
        PlannedMarkerDTO marker = PaRegistry.getPlannedMarkerService()
        .get(IiConverter.convertToIi(getSelectedRowIdentifier()));
        PlannedMarkerWebDTO webDTO = populateWebDTO(marker);
        
        List<PlannedMarkerDTO> markerDTOs = plannedMarkerService.getPendingPlannedMarkersWithName(
                StConverter.convertToString(marker.getLongName()));
        for (PlannedMarkerDTO markerDTO : markerDTOs) {
            markerDTO.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
            plannedMarkerService.update(markerDTO);
        }
        marker.setStatusCode(CdConverter.convertToCd(ActiveInactivePendingCode.ACTIVE));
        try {            
            PaRegistry.getMailManagerService().sendMarkerAcceptanceMailToCDE(
                    webDTO.getNciIdentifier(), webDTO.getCsmUserEmailId(), marker);
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return execute();
    }        

    /**
     * Creates plannedMarkerWebDTO from plannedMarkerDTO.
     * @param markerDTO plannedMarkerDTO
     * @return plannedMarkerWebDTO
     * @throws PAException exception
     */
    private PlannedMarkerWebDTO populateWebDTO(PlannedMarkerDTO markerDTO) throws PAException {
        PlannedMarkerWebDTO webDTO = new PlannedMarkerWebDTO();
        webDTO.setId(IiConverter.convertToLong(markerDTO.getIdentifier()));
        webDTO.setName(StConverter.convertToString(markerDTO.getName()));
        webDTO.setMeaning(StConverter.convertToString(markerDTO.getLongName()));
        webDTO.setStatus(CdConverter.convertCdToString(markerDTO.getStatusCode()));

        String nciIdentifier = "";
        String userId = "";
        User csmUser;
        String emailId = "";
        if (markerDTO.getStudyProtocolIdentifier() != null && markerDTO.getUserLastCreated() != null) {
            StudyProtocolQueryDTO studyProtocolQueryDTO = protocolQueryService.getTrialSummaryByStudyProtocolId(
                    IiConverter.convertToLong(markerDTO.getStudyProtocolIdentifier()));
            nciIdentifier = studyProtocolQueryDTO.getNciIdentifier();
            userId = StConverter.convertToString(markerDTO.getUserLastCreated());
            csmUser = CSMUserService.getInstance().getCSMUserById(Long.valueOf(userId));
            emailId = csmUser.getEmailId();
        }           
        webDTO.setQuestion("");
        webDTO.setNciIdentifier(nciIdentifier);
        webDTO.setCsmUserEmailId(emailId);

        return webDTO;
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
     * @return plannedMarkerList
     */
    public List<PlannedMarkerWebDTO> getPlannedMarkerList() {
        return plannedMarkerList;
    }

    /**
     * 
     * @param plannedMarkerList plannedMarkerList
     */
    public void setPlannedMarkerList(List<PlannedMarkerWebDTO> plannedMarkerList) {
        this.plannedMarkerList = plannedMarkerList;
    }

    /**
     * 
     * @return plannedMarkers 
     */
    public List<PlannedMarkerDTO> getPlannedMarkers() {
        return plannedMarkers;
    }

    /**
     * 
     * @param plannedMarkers plannedMarkers
     */
    public void setPlannedMarkers(List<PlannedMarkerDTO> plannedMarkers) {
        this.plannedMarkers = plannedMarkers;
    }

    /**
     *  
     * @return protocolQueryService
     */
    public ProtocolQueryServiceLocal getProtocolQueryService() {
        return protocolQueryService;
    }

    /**
     * 
     * @param protocolQueryService protocolQueryService
     */
    public void setProtocolQueryService(
            ProtocolQueryServiceLocal protocolQueryService) {
        this.protocolQueryService = protocolQueryService;
    }

    /**
     * 
     * @return PlannedMarkerWebDTO
     */
    public PlannedMarkerWebDTO getPlannedMarker() {
        return plannedMarker;
    }

    /**
     * 
     * @param plannedMarker PlannedMarkerWebDTO
     */
    public void setPlannedMarker(PlannedMarkerWebDTO plannedMarker) {
        this.plannedMarker = plannedMarker;
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
}