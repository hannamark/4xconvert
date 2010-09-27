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
package gov.nih.nci.pa.action;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.dto.PaOrganizationDTO;
import gov.nih.nci.pa.dto.PaPersonDTO;
import gov.nih.nci.pa.dto.ParticipatingOrganizationsTabWebDTO;
import gov.nih.nci.pa.dto.StudyOverallStatusWebDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.FunctionalRoleStatusCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ParticipatingSiteServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.CorrelationUtilsRemote;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.util.Constants;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PADomainUtils;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.po.data.CurationException;
import gov.nih.nci.po.service.EntityValidationException;
import gov.nih.nci.services.correlation.ClinicalResearchStaffDTO;
import gov.nih.nci.services.correlation.HealthCareFacilityDTO;
import gov.nih.nci.services.correlation.HealthCareProviderDTO;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.correlation.OrganizationalContactDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

/**
 * Action class for viewing and editing the participating organizations.
 *
 * @author Hugh Reinhart, Harsha
 * @since 08/20/2008
 */
public class ParticipatingOrganizationsAction extends ActionSupport implements Preparable {
    private static final String REC_STATUS_DATE = "recStatusDate";
    private static final long serialVersionUID = 123412653L;
    private static final Logger LOG = Logger.getLogger(ParticipatingOrganizationsAction.class);
    static final String ACT_FACILITY_SAVE = "facilitySave";
    static final String ACT_EDIT = "edit";
    static final String ACT_DELETE = "delete";
    private StudySiteServiceLocal sPartService;
    private StudySiteAccrualStatusServiceLocal ssasService;
    private StudySiteContactServiceLocal sPartContactService;
    private ParticipatingSiteServiceLocal partSiteService;
    private CorrelationUtilsRemote correlationUtils;
    private StudyProtocolServiceLocal studyProtocolService;
    private Ii spIi;
    private List<PaOrganizationDTO> organizationList;
    private OrganizationDTO selectedOrgDTO;
    private Organization editOrg;
    private Long cbValue;
    private String recStatus;
    private String recStatusDate;
    private PersonDTO selectedPersTO = null;
    private List<StudySiteContactDTO> spContactDTO;
    private List<PaPersonDTO> personWebDTOList;
    private boolean newParticipation = false;
    private PaPersonDTO personContactWebDTO;
    private String organizationName;
    private PaOrganizationDTO orgFromPO = new PaOrganizationDTO();
    private String currentAction = "create";
    private String targetAccrualNumber;
    private String proprietaryTrialIndicator = "false";
    private String siteLocalTrialIdentifier;
    private String siteProgramCodeText;
    private String dateOpenedForAccrual;
    private String dateClosedForAccrual;
    private Long studySiteIdentifier;
    private String programCode;

    private static final String DISPLAY_SP_CONTACTS = "display_StudyPartipants_Contacts";
    private static final String DISPLAY_PRIM_CONTACTS = "display_primContacts";
    private static final String DISPLAY_SPART_CONTACTS = "display_spContacts";
    private static final String DISPLAYJSP = "displayJsp";
    private static final String DISPLAY_STUDY_PART_CONTACTS = "display_StudyPartipants";
    private static final String ERROR_PRIMARY_CONTACTS = "error_prim_contacts";


    private String statusCode;
    private List<StudyOverallStatusWebDTO> overallStatusList;
    /**
     * @see com.opensymphony.xwork2.Preparable#prepare()
     * @throws PAException on error
     */
    public void prepare() throws PAException {
        sPartService = PaRegistry.getStudySiteService();
        ssasService = PaRegistry.getStudySiteAccrualStatusService();
        sPartContactService = PaRegistry.getStudySiteContactService();
        correlationUtils = new CorrelationUtils();
        partSiteService = PaRegistry.getParticipatingSiteService();
        StudyProtocolQueryDTO spDTO = (StudyProtocolQueryDTO) ServletActionContext.getRequest().getSession()
                .getAttribute(Constants.TRIAL_SUMMARY);
        spIi = IiConverter.convertToStudyProtocolIi(spDTO.getStudyProtocolId());
        studyProtocolService =  PaRegistry.getStudyProtocolService();
    }

    /**
     * @return Action result.
     *
     */
    @Override
    public String execute() {
        String retString = SUCCESS;
        try {
        loadForm();

        if (proprietaryTrialIndicator != null
                && proprietaryTrialIndicator.equalsIgnoreCase("true")) {
            retString =  "proprietaryList";
        }
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        }
        return retString;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String create() throws PAException {
        ServletActionContext.getRequest().getSession().removeAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        loadForm();
        setNewParticipation(true);
        return Action.SUCCESS;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilitySave() throws PAException {
        facilitySaveOrUpdate();
        if (hasFieldErrors()) {
            return ERROR;
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.CREATE_MESSAGE);
        return ACT_FACILITY_SAVE;
    }

    /**
     * @return result
     * @throws PAException exception
     */
    public String facilityUpdate() throws PAException {
        setRecStatus(ServletActionContext.getRequest().getParameter("recStatus"));
        setRecStatusDate(ServletActionContext.getRequest().getParameter(REC_STATUS_DATE));
        setTargetAccrualNumber(ServletActionContext.getRequest().getParameter("targetAccrualNumber"));
        setProgramCode(ServletActionContext.getRequest().getParameter("programCode"));
        setSiteLocalTrialIdentifier(ServletActionContext.getRequest().getParameter("localProtocolIdenfier"));
        facilitySaveOrUpdate();
        if (hasFieldErrors()) {
            return "error_edit";
        }
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.UPDATE_MESSAGE);
        return ACT_EDIT;
    }

    /**
     * @throws PAException exception
     */
    public void facilitySaveOrUpdate() throws PAException {
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        if (tab != null) {
            Organization org = tab.getFacilityOrganization();
            orgFromPO.setCity(org.getCity());
            orgFromPO.setCountry(org.getCountryName());
            orgFromPO.setName(org.getName());
            orgFromPO.setZip(org.getPostalCode());
        }
        enforceBusinessRules();
        if (hasFieldErrors()) {
            return;
        }
        StudySiteDTO sp = null;
        String errorOrgName = "editOrg.name";
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(recStatus)));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(recStatusDate)));
        Ii ssIi = null;
        if (tab.getStudyParticipationId() != null) {
            sp = saveNonPropWithCurrentSite(ssas, tab, errorOrgName);
            ssIi = sp.getIdentifier();
        } else {
            sp = new StudySiteDTO();
            ssIi = saveNonPropWithNewSite(sp, ssas, tab, errorOrgName);
        }
        
        tab.setStudyParticipationId(IiConverter.convertToLong(ssIi));
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (StringUtils.isNotEmpty(tab.getFacilityOrganization().getName())) {
            setOrganizationName("for " + tab.getFacilityOrganization().getName());
        }
        setStatusCode(sp.getStatusCode().getCode());
        setCurrentAction("edit");
    }
    
    private StudySiteDTO saveNonPropWithCurrentSite(StudySiteAccrualStatusDTO ssas, 
            ParticipatingOrganizationsTabWebDTO tab, 
            String errorOrgName) throws PAException {
        StudySiteDTO sp = sPartService.get(IiConverter.convertToIi(tab.getStudyParticipationId()));
        
        
        boolean spUpdated = isUpdated(sp);
        
        if (spUpdated) {
            try {
                partSiteService.updateStudySiteParticipant(sp, ssas);
            } catch (PADuplicateException e) {
                addFieldError(errorOrgName, e.getMessage());
            }
        }
        return sp;
    }
    
    private boolean isUpdated(StudySiteDTO sp) {
        String prgCode = (getProgramCode() == null) ? null : getProgramCode();
        Integer iTargetAccrual = (targetAccrualNumber == null) ? null : Integer.parseInt(targetAccrualNumber);
        String localId = (getSiteLocalTrialIdentifier() == null) ? null : getSiteLocalTrialIdentifier();
        boolean spUpdated = 
            isUpdated1(sp, prgCode, iTargetAccrual) && isUpdated2(sp, localId); 
        
        
        return spUpdated;
    }
    
    private boolean isUpdated1(StudySiteDTO sp, String prgCode, Integer iTargetAccrual) {
        boolean spUpdated = false;
        if (IntConverter.convertToInteger(sp.getTargetAccrualNumber()) != iTargetAccrual) {
            sp.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
            spUpdated = true;
        }
        if (PAUtil.isStNull(sp.getProgramCodeText())
            || (!PAUtil.isStNull(sp.getProgramCodeText())
                && !StConverter.convertToString(sp.getProgramCodeText()).equalsIgnoreCase(prgCode))) {
           sp.setProgramCodeText(StConverter.convertToSt(getProgramCode()));
           spUpdated = true;
        }      
        return spUpdated;
    }
    
    private boolean isUpdated2(StudySiteDTO sp, String localId) {
        boolean spUpdated = false;
        if (PAUtil.isStNull(sp.getLocalStudyProtocolIdentifier()) || (!PAUtil.isStNull(
                sp.getLocalStudyProtocolIdentifier()) && !StConverter.convertToString(
                sp.getLocalStudyProtocolIdentifier()).equalsIgnoreCase(localId))) {
            sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt(getSiteLocalTrialIdentifier()));
            spUpdated = true;
        }
        return spUpdated;
    }
    
    private Ii saveNonPropWithNewSite(StudySiteDTO sp, StudySiteAccrualStatusDTO ssas, 
            ParticipatingOrganizationsTabWebDTO tab, 
            String errorOrgName) throws PAException {
        Ii ssIi = null;
        String poOrgId = tab.getFacilityOrganization().getIdentifier();
        Ii poHcfIi = getPoHcfIi(poOrgId);
       
        sp.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.TREATING_SITE));
        sp.setIdentifier(null);
        sp.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.ACTIVE));
        sp.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        sp.setStudyProtocolIdentifier(spIi);
        sp.setTargetAccrualNumber(IntConverter.convertToInt(getTargetAccrualNumber()));
        sp.setProgramCodeText(StConverter.convertToSt(getProgramCode()));
        sp.setLocalStudyProtocolIdentifier(StConverter.convertToSt(siteLocalTrialIdentifier));
        
        try {
            ssIi = 
                partSiteService.createStudySiteParticipant(sp, ssas, poHcfIi);
        } catch (PADuplicateException e) {
            addFieldError(errorOrgName, e.getMessage());
        }
        
        return ssIi;
    }

    /**
     * @return result
     * @throws PAException  exception
     */
    public String edit() throws PAException {
        setCurrentAction("edit");
        StudySiteDTO spDto = sPartService.get(IiConverter.convertToIi(cbValue));
        editOrg = correlationUtils.getPAOrganizationByIi(spDto.getHealthcareFacilityIi());
        orgFromPO.setCity(editOrg.getCity());
        orgFromPO.setCountry(editOrg.getCountryName());
        orgFromPO.setName(editOrg.getName());
        orgFromPO.setState(editOrg.getState());
        orgFromPO.setZip(editOrg.getPostalCode());
        StudySiteAccrualStatusDTO status = ssasService
                .getCurrentStudySiteAccrualStatusByStudySite(spDto.getIdentifier());
        if (status != null) {
            this.setRecStatus(status.getStatusCode().getCode());
            this.setRecStatusDate(TsConverter.convertToTimestamp(status.getStatusDate()).toString());
        }
        if (IntConverter.convertToInteger(spDto.getTargetAccrualNumber()) == null) {
            setTargetAccrualNumber(null);
        } else {
            setTargetAccrualNumber(IntConverter.convertToInteger(spDto.getTargetAccrualNumber()).toString());
        }
        if (StConverter.convertToString(spDto.getProgramCodeText()) == null) {
            setProgramCode(null);
        } else {
            setProgramCode(StConverter.convertToString(spDto.getProgramCodeText()));
        }
        if (!PAUtil.isStNull(spDto.getLocalStudyProtocolIdentifier())) {
            setSiteLocalTrialIdentifier(StConverter.convertToString(spDto.getLocalStudyProtocolIdentifier()));
        } else {
            setSiteLocalTrialIdentifier(null);
        }
        setStatusCode(spDto.getStatusCode().getCode());
        setNewParticipation(false);
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setStudyParticipationId(cbValue);
        tab.setFacilityOrganization(editOrg);
        tab.setPoHealthCareFacilityIi(null);
        tab.setPoOrganizationIi(null);
        tab.setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        if (editOrg.getName() != null) {
            organizationName = "for " + editOrg.getName();
        }
        // prepare the other two variables for display [personWebDTOList and
        // personWebDTO]
        personWebDTOList = new ArrayList<PaPersonDTO>();
        List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(tab.getStudyParticipationId(),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                tab.getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add(principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add(subInvresults.get(i));
        }
        getStudyParticipationPrimContact();
        return ACT_EDIT;
    }

    /**
     * @return result
     * @throws PAException  exception
     */
    public String delete() throws PAException {
        clearErrorsAndMessages();
        sPartService.delete(IiConverter.convertToIi(cbValue));
        ServletActionContext.getRequest().setAttribute(Constants.SUCCESS_MESSAGE, Constants.DELETE_MESSAGE);
        loadForm();
        if (proprietaryTrialIndicator != null
                && proprietaryTrialIndicator.equalsIgnoreCase("true")) {
            return "proprietaryList";
        }

        return ParticipatingOrganizationsAction.ACT_DELETE;
    }

    private void loadForm() throws PAException {
        loadProprietaryIndicator();
        organizationList = new ArrayList<PaOrganizationDTO>();
        StudySiteDTO srDTO = new StudySiteDTO();
        srDTO.setFunctionalCode(CdConverter.convertStringToCd(StudySiteFunctionalCode.TREATING_SITE
                        .getCode()));
        List<StudySiteDTO> spList = sPartService.getByStudyProtocol(spIi, srDTO);
        for (StudySiteDTO sp : spList) {
            StudySiteAccrualStatusDTO ssas = ssasService
                    .getCurrentStudySiteAccrualStatusByStudySite(sp.getIdentifier());
            Organization orgBo = correlationUtils.getPAOrganizationByIi(sp.getHealthcareFacilityIi());
            PaOrganizationDTO orgWebDTO = new PaOrganizationDTO();
            orgWebDTO.setId(IiConverter.convertToString(sp.getIdentifier()));
            orgWebDTO.setName(orgBo.getName());
            orgWebDTO.setNciNumber(orgBo.getIdentifier());
            if (ssas == null || ssas.getStatusCode() == null || ssas.getStatusDate() == null) {
                orgWebDTO.setRecruitmentStatus("unknown");
                orgWebDTO.setRecruitmentStatusDate("unknown");
            } else {
                orgWebDTO.setRecruitmentStatus(CdConverter.convertCdToString(ssas.getStatusCode()));
                orgWebDTO.setRecruitmentStatusDate(PAUtil.normalizeDateString(TsConverter.convertToTimestamp(
                        ssas.getStatusDate()).toString()));
            }
            if (IntConverter.convertToInteger(sp.getTargetAccrualNumber()) == null) {
                orgWebDTO.setTargetAccrualNumber(null);
            } else {
                orgWebDTO.setTargetAccrualNumber(IntConverter.convertToInteger(sp.getTargetAccrualNumber()).toString());
            }
          // setStatusCode(sp.getStatusCode().getCode());
            orgWebDTO.setStatus(sp.getStatusCode().getCode());
            orgWebDTO.setProgramCode(StConverter.convertToString(sp.getProgramCodeText()));
            List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(Long.valueOf(sp.getIdentifier().getExtension().toString()),
                    StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
            List<PaPersonDTO> sublInvresults = PaRegistry.getPAHealthCareProviderService()
            .getPersonsByStudySiteId(Long.valueOf(sp.getIdentifier().getExtension().toString()),
                StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
            List<PaPersonDTO> primInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(
                        Long.valueOf(sp.getIdentifier().getExtension().toString()),
                                            StudySiteContactRoleCode.PRIMARY_CONTACT.getName());
            StringBuffer invList = new StringBuffer();
            StringBuffer primContactList = new StringBuffer();
            String stStartB = " [ ";
            String stDash = " - ";
            if (!principalInvresults.isEmpty()) {
                for (PaPersonDTO per : principalInvresults) {
                   String fullName = per.getFullName() != null ? per.getFullName() : "";
                   String roleName = per.getRoleName() != null ? per.getRoleName().getCode() : "";
                   String status = per.getStatusCode() != null ? per.getStatusCode().getCode() : "";
                  invList.append(stStartB + fullName + stDash + roleName + " , "
                            + status + " ]<br>");
                   }
            }
            if (!sublInvresults.isEmpty()) {
                for (PaPersonDTO per : sublInvresults) {
                    String fullName = per.getFullName() != null ? per.getFullName() : "";
                    String roleName = per.getRoleName() != null ? per.getRoleName().getCode() : "";
                    String status = per.getStatusCode() != null ? per.getStatusCode().getCode() : "";
                    invList.append(stStartB + fullName + stDash + roleName + " , "
                            + status + " ]<br>");

                }
            }

            if (!primInvresults.isEmpty()) {
                for (PaPersonDTO per : primInvresults) {
                    String fullName = per.getFullName() != null ? per.getFullName() : "";
                    String status = per.getStatusCode() != null ? per.getStatusCode().getCode() : "";
                    primContactList.append(stStartB).append(fullName).append(stDash).append(status).append(" ]");
                }
            }
            //check if primary contact is title
            if (primInvresults.isEmpty()) {
                StudySiteContactDTO siteConDto = new StudySiteContactDTO();
                List<StudySiteContactDTO> siteContactDtos = sPartContactService.getByStudySite(sp.getIdentifier());
                for (StudySiteContactDTO cDto : siteContactDtos) {
                    if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                            equalsIgnoreCase(cDto.getRoleCode().getCode())) {
                        siteConDto = cDto;
                    }
                }
                if (!PAUtil.isIiNull(siteConDto.getOrganizationalContactIi())) {
                    try {
                        PAContactDTO paDto = correlationUtils.getContactByPAOrganizationalContactId((
                            Long.valueOf(siteConDto.getOrganizationalContactIi().getExtension())));
                        if (paDto.getTitle() != null)  {
                            primContactList.append(stStartB).append(paDto.getTitle()).append(stDash);
                            String statusCodeDispName =
                                StConverter.convertToString(siteConDto.getStatusCode().getDisplayName());
                            primContactList.append(statusCodeDispName);
                            primContactList.append(" ]");
                        }
                    } catch (NullifiedRoleException e) {
                        LOG.error("NullifiedRoleException while getting site contact Info" + e.getMessage());
                    }
                }
            }
            orgWebDTO.setInvestigator(invList.toString());
            orgWebDTO.setPrimarycontact(primContactList.toString());
            organizationList.add(orgWebDTO);
        }
    }

    /**
     * @throws PAException
     */
    private void loadProprietaryIndicator() throws PAException {
        StudyProtocolDTO studyProtocolDTO = studyProtocolService.getStudyProtocol(spIi);
        if (!PAUtil.isBlNull(studyProtocolDTO.getProprietaryTrialIndicator())
            && studyProtocolDTO.getProprietaryTrialIndicator().getValue()) {
            setProprietaryTrialIndicator("true");
        } else {
            setProprietaryTrialIndicator("false");
        }
    }

    /**
     * @return the organizationList
     * @throws PAException on error.
     */
    public String displayOrg() throws PAException {
        loadProprietaryIndicator();
        //gov.nih.nci.pa.dto.OrganizationDTO paOrgDTO = new gov.nih.nci.pa.dto.OrganizationDTO();
        PaOrganizationDTO paOrgDTO = new PaOrganizationDTO();
        clearErrorsAndMessages();
        String orgId = ServletActionContext.getRequest().getParameter("orgId");
        OrganizationDTO criteria = new OrganizationDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(orgId)));

        LimitOffset limit = new LimitOffset(1, 0);
        try {
            selectedOrgDTO = PoRegistry.getOrganizationEntityService().search(criteria, limit).get(0);
        } catch (TooManyResultsException e) {
            throw new PAException(e);
        }

        // convert the PO DTO to the pa domain
        paOrgDTO = PADomainUtils.convertPoOrganizationDTO(selectedOrgDTO, null);

        // store selection
        Organization org = new Organization();
        org.setCity(paOrgDTO.getCity());
        org.setCountryName(paOrgDTO.getCountry());
        org.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        org.setName(paOrgDTO.getName());
        org.setPostalCode(paOrgDTO.getZip());
        editOrg = new Organization();
        editOrg.setCity(paOrgDTO.getCity());
        editOrg.setCountryName(paOrgDTO.getCountry());
        editOrg.setIdentifier(IiConverter.convertToString(selectedOrgDTO.getIdentifier()));
        editOrg.setName(paOrgDTO.getName());
        editOrg.setPostalCode(paOrgDTO.getZip());
        // setting the org values to the member var
        orgFromPO.setCity(paOrgDTO.getCity());
        orgFromPO.setCountry(paOrgDTO.getCountry());
        orgFromPO.setState(paOrgDTO.getState());
        orgFromPO.setName(paOrgDTO.getName());
        orgFromPO.setZip(paOrgDTO.getZip());
        ParticipatingOrganizationsTabWebDTO tab = new ParticipatingOrganizationsTabWebDTO();
        tab.setPoOrganizationIi(selectedOrgDTO.getIdentifier());
        tab.setFacilityOrganization(org);
        setNewParticipation(false);
        ServletActionContext.getRequest().getSession().setAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB, tab);
        return DISPLAYJSP;
    }

    /**
     * This method is called upon clicking the second tab (Investigators).
     *
     * @return result
     * @throws PAException on error
     */
    public String getStudyParticipationContacts() throws PAException {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        returnDisplaySPContacts(tab);
        return DISPLAY_STUDY_PART_CONTACTS;
    }

    /**
     * @return the result
     */
    public String saveStudyParticipationContact() {
        clearErrorsAndMessages();
        
        String roleCode = ServletActionContext.getRequest().getParameter("rolecode");
        String persId = ServletActionContext.getRequest().getParameter("persid");
        if (selectedPersTO == null) {
            try {
              selectedPersTO = PoRegistry.getPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(persId)));
            } catch (NullifiedEntityException ne) {
                addActionError(Constants.FAILURE_MESSAGE + "This person is longer available");
                return ERROR_PRIMARY_CONTACTS;
            } catch (NumberFormatException e) {
                addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
                return ERROR_PRIMARY_CONTACTS;
            } catch (PAException e) {
                addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
                return ERROR_PRIMARY_CONTACTS;
            }
        }
        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        try {
            Ii ssIi = IiConverter.convertToStudySiteIi(tab.getStudyParticipationId());
            String poOrgId = tab.getFacilityOrganization().getIdentifier();
            addInvestigator(ssIi, selectedPersTO.getIdentifier(), roleCode, poOrgId);
        } catch (PAException e) {
            if (StringUtils.isNotEmpty(e.getLocalizedMessage())) {
                addActionError(e.getLocalizedMessage());
            } else {
                addActionError("Exception:Investigator can not be added to the Nullified Org"
                        + e.getLocalizedMessage());
            }
            return DISPLAY_SPART_CONTACTS;
        }
        
        // This makes a fresh db call to show the result on the JSP
        return returnDisplaySPContacts(tab);       
    }

    /**
     * @return the result
     * @throws PAException on error
     */
    public String deleteStudyPartContact() throws PAException {
        clearErrorsAndMessages();
        String invId = ServletActionContext.getRequest().getParameter("persid");
        //StudyParticipationContactDTO contactDTO = sPartContactService.get(IiConverter.convertToIi(invId));
        // Long identifier = IiConverter.convertToLong(contactDTO.getHealthCareProviderIi());
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        sPartContactService.delete(IiConverter.convertToIi(invId));
        // If a primary contact also exists delete that as well - Changed the requirement DO NOT DELETE*
        //        List returnlist = PaRegistry.getPAHealthCareProviderService().getPersonsByStudyParticpationId(
        //                orgsTabWebDTO.getStudyParticipationId(), Constants.STUDY_PRIMARY_CONTACT);
        //        if (!returnlist.isEmpty()) {
        //            StudyParticipationContactDTO contactDTODB = sPartContactService.get(IiConverter
        //                    .convertToIi(((PaPersonDTO) returnlist.get(0)).getId()));
        //            Long identifierDB = IiConverter.convertToLong(contactDTODB.getClinicalResearchStaffIi());
        //            if (identifier.equals(identifierDB)) {

        //            }
        //        }
        return returnDisplaySPContacts(orgsTabWebDTO);
    }

    /**
     * Gets the value from Database.
     * @return the result
     */
    private String getStudyParticipationPrimContact() {
        clearErrorsAndMessages();
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        try {
        List<PaPersonDTO> resultsList = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                orgsTabWebDTO.getStudyParticipationId(), StudySiteContactRoleCode.PRIMARY_CONTACT.getName());
        if (resultsList != null && !resultsList.isEmpty()) {
            personContactWebDTO = resultsList.get(0);
        } else {
           StudySiteContactDTO siteConDto = new StudySiteContactDTO();
           List<StudySiteContactDTO> siteContactDtos = sPartContactService.getByStudySite(
                   IiConverter.convertToIi(orgsTabWebDTO.getStudyParticipationId()));
           for (StudySiteContactDTO cDto : siteContactDtos) {
               if (StudySiteContactRoleCode.PRIMARY_CONTACT.getCode().
                       equalsIgnoreCase(cDto.getRoleCode().getCode())) {
                   siteConDto = cDto;
               }
           }
           if (siteConDto != null && !PAUtil.isIiNull(siteConDto.getOrganizationalContactIi())) {

                   PAContactDTO paDto = correlationUtils.getContactByPAOrganizationalContactId((
                            Long.valueOf(siteConDto.getOrganizationalContactIi().getExtension())));
                   if (paDto.getTitle() != null)  {
                       personContactWebDTO = new PaPersonDTO();
                       personContactWebDTO.setTitle(paDto.getTitle());
                       personContactWebDTO.setTelephone(DSetConverter.getFirstElement(
                               siteConDto.getTelecomAddresses(), "PHONE"));
                       personContactWebDTO.setEmail(DSetConverter.getFirstElement(
                               siteConDto.getTelecomAddresses(), "EMAIL"));
                       personContactWebDTO.setId(Long.valueOf(paDto.getSrIdentifier().getExtension()));
                       personContactWebDTO.setStatusCode(FunctionalRoleStatusCode.
                               getByCode(CdConverter.convertCdToString(siteConDto.getStatusCode())));
                   }
           }
         }
        } catch (NullifiedRoleException e) {
            LOG.error("NullifiedRoleException while getting site contact Info" + e.getMessage());
            addActionError("NullifiedRoleException while getting site contact Info: " + e.getMessage());
        } catch (PAException e) {
            LOG.error("exception: " + e.getMessage());
            addActionError("Exception: " + e.getMessage());
        }
        return DISPLAY_SP_CONTACTS;
    }

    private String returnDisplaySPContacts(ParticipatingOrganizationsTabWebDTO orgsTabWebDTO) {
        personWebDTOList = new ArrayList<PaPersonDTO>();
        try {
        List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(orgsTabWebDTO.getStudyParticipationId(),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                orgsTabWebDTO.getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add(principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add(subInvresults.get(i));
        }
        } catch (PAException e) {
            addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
        }
        return DISPLAY_SPART_CONTACTS;
    }

    /**
     * @return the result
     * @throws PAException on error
     */
    public String getDisplaySPContacts() throws PAException {
        ParticipatingOrganizationsTabWebDTO orgsTabWebDTO = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
                .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        personWebDTOList = new ArrayList<PaPersonDTO>();
        List<PaPersonDTO> principalInvresults = PaRegistry.getPAHealthCareProviderService()
                .getPersonsByStudySiteId(orgsTabWebDTO.getStudyParticipationId(),
                        StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getName());
        List<PaPersonDTO> subInvresults = PaRegistry.getPAHealthCareProviderService().getPersonsByStudySiteId(
                orgsTabWebDTO.getStudyParticipationId(), StudySiteContactRoleCode.SUB_INVESTIGATOR.getName());
        for (int i = 0; i < principalInvresults.size(); i++) {
            personWebDTOList.add(principalInvresults.get(i));
        }
        for (int i = 0; i < subInvresults.size(); i++) {
            personWebDTOList.add(subInvresults.get(i));
        }
        return DISPLAY_SPART_CONTACTS;
    }

    /**
     * @return the result
     * @throws PAException on error.
     * @throws NullifiedEntityException on deletes
     */
    public String saveStudyParticipationPrimContact() throws PAException, NullifiedEntityException {
        clearErrorsAndMessages();
        String persId = ServletActionContext.getRequest().getParameter("contactpersid");
        String email = ServletActionContext.getRequest().getParameter("email");
        String telephone = ServletActionContext.getRequest().getParameter("tel");

        if (persId == null || "".equals(persId)) {
            addFieldError("personContactWebDTO.firstName", getText("Please lookup and select person"));
        }
        if (StringUtils.isEmpty(email)) {
            addFieldError("personContactWebDTO.email", getText("error.enterEmailAddress"));
        }
        if (StringUtils.isNotEmpty(email) && (!PAUtil.isValidEmail(email))) {
            addFieldError("personContactWebDTO.email", getText("error.enterValidEmail"));
        }
        if (StringUtils.isEmpty(telephone)) {
            addFieldError("personContactWebDTO.telephone", getText("error.enterPhoneNumber"));
        }
        if (hasFieldErrors()) {
            personContactWebDTO = new PaPersonDTO();
            personContactWebDTO.setEmail(email);
            personContactWebDTO.setTelephone(telephone);
            if (persId != null && !persId.equals("")) {
                personContactWebDTO.setSelectedPersId(Long.valueOf(persId));
            }
            if (selectedPersTO != null && selectedPersTO.getName() != null) {
                gov.nih.nci.pa.dto.PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(selectedPersTO);
                personContactWebDTO.setFirstName(personDTO.getFirstName());
                personContactWebDTO.setLastName(personDTO.getLastName());
                personContactWebDTO.setMiddleName(personDTO.getMiddleName());
            }
            return ERROR_PRIMARY_CONTACTS;
        }

        ParticipatingOrganizationsTabWebDTO tab = (ParticipatingOrganizationsTabWebDTO) ServletActionContext
            .getRequest().getSession().getAttribute(Constants.PARTICIPATING_ORGANIZATIONS_TAB);
        try {
            Ii selectedPersTOIi  = null;
            PersonDTO isoPerDTO = PoRegistry.getPersonEntityService().getPerson(
                    IiConverter.convertToPoPersonIi(persId));
            if (isoPerDTO == null) {
                    DSet<Ii> iiDset = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(
                        IiConverter.convertToPoOrganizationalContactIi(persId)).getIdentifier();
                    selectedPersTOIi = DSetConverter.convertToIi(iiDset);
            } else {
                selectedPersTOIi = isoPerDTO.getIdentifier();
            }

            
            Ii ssIi = IiConverter.convertToStudySiteIi(tab.getStudyParticipationId());
            String poOrgId = tab.getFacilityOrganization().getIdentifier();
            telephone = telephone.replaceAll(" ", "");
            ArrayList<String> emailList = new ArrayList<String>();
            ArrayList<String> telList = new ArrayList<String>();
            emailList.add(email);
            telList.add(telephone);
            DSet<Tel> list = new DSet<Tel>();
            list = DSetConverter.convertListToDSet(emailList, "EMAIL", list);
            list = DSetConverter.convertListToDSet(telList, "PHONE", list);
            
            if (IiConverter.ORGANIZATIONAL_CONTACT_ROOT.equals(selectedPersTOIi.getRoot())) {
                addGenericContact(ssIi, selectedPersTOIi, list);    
            } else if (IiConverter.PERSON_ROOT.equals(selectedPersTOIi.getRoot())) {
                addPrimaryContact(ssIi, selectedPersTOIi, poOrgId, list);
            }
            //refesh
            getStudyParticipationPrimContact();
        } catch (PAException e) {
            addActionError("Exception:Investigator can not be added to the Nullified Org" + e.getLocalizedMessage());
            return DISPLAY_SPART_CONTACTS;
        } catch (NullifiedRoleException e) {
            addActionError(Constants.FAILURE_MESSAGE + e.getMessage());
        }
        return DISPLAY_PRIM_CONTACTS;
    }
    /**
     * @return the result
     * @throws PAException
     *             on error.
     */
    public String refreshPrimaryContact() throws PAException {
        getStudyParticipationPrimContact();
        return DISPLAY_PRIM_CONTACTS;
    }

    /**
     * This method is called when a primary contact is chosen.
     * @return the result
     * @throws PAException  on error.
     *  * @throws NullifiedEntityException on deletes
     */
    public String displayStudyParticipationPrimContact() throws PAException, NullifiedEntityException {
        clearErrorsAndMessages();
        String contactPersId = ServletActionContext.getRequest().getParameter("contactpersid");
        String editmode = ServletActionContext.getRequest().getParameter("editmode");
        PaPersonDTO webDTO = null;
        personContactWebDTO = new PaPersonDTO();
        if ("yes".equals(editmode)) {
            webDTO = PaRegistry.getPAHealthCareProviderService().getIdentifierBySPCId(Long.valueOf(contactPersId));
            personContactWebDTO.setFirstName(webDTO.getFirstName());
            personContactWebDTO.setLastName(webDTO.getLastName());
            personContactWebDTO.setMiddleName(webDTO.getMiddleName());
            personContactWebDTO.setSelectedPersId(webDTO.getSelectedPersId());

        } else {
            selectedPersTO = PoRegistry.getPersonEntityService().getPerson(
                    EnOnConverter.convertToOrgIi(Long.valueOf(contactPersId)));
            personContactWebDTO.setSelectedPersId(Long.valueOf(selectedPersTO.getIdentifier().getExtension()));
            gov.nih.nci.pa.dto.PaPersonDTO personDTO = PADomainUtils.convertToPaPersonDTO(selectedPersTO);
            personContactWebDTO.setFirstName(personDTO.getFirstName());
            personContactWebDTO.setLastName(personDTO.getLastName());
            personContactWebDTO.setMiddleName(personDTO.getMiddleName());
        }
        String email = (String) ServletActionContext.getRequest().getSession().getAttribute("emailEntered");
        String telephone = (String) ServletActionContext.getRequest().getSession().getAttribute("telephoneEntered");
        if (email != null) {
            personContactWebDTO.setEmail(email);
        }
        if (telephone != null) {
            personContactWebDTO.setTelephone(telephone);
        }
        return DISPLAY_PRIM_CONTACTS;
    }


    /**
     * This method is used to enforce the business rules which are form specific or based on an interaction between
     * services.
     */
    private void enforceBusinessRules() {
        clearErrorsAndMessages();
        if (StringUtils.isEmpty(getRecStatus())) {
            addFieldError("recStatus", getText("error.participatingStatus"));
        }
        if (StringUtils.isEmpty(getRecStatusDate())) {
            addFieldError(REC_STATUS_DATE, getText("error.participatingStatusDate"));
        }
        if (StringUtils.isEmpty(orgFromPO.getName())) {
            addFieldError("editOrg.name", "Please choose an organization");
        }
        if (StringUtils.isNotEmpty(getRecStatusDate())) {
            Timestamp newDate = PAUtil.dateStringToTimestamp(getRecStatusDate());
            if (newDate.after(new Timestamp(new Date().getTime()))) {
                addFieldError(REC_STATUS_DATE, getText("error.participatingStatusDateCheck"));
            }
        }
    }

    /**
     * @return the organizationList
     */
    public List<PaOrganizationDTO> getOrganizationList() {
        return organizationList;
    }

    /**
     * @param organizationList the organizationList to set
     */
    public void setOrganizationList(List<PaOrganizationDTO> organizationList) {
        this.organizationList = organizationList;
    }

    /**
     * @return the selectedOrgDTO
     */
    public OrganizationDTO getSelectedOrgDTO() {
        return selectedOrgDTO;
    }

    /**
     * @param selectedOrgDTO the selectedOrgDTO to set
     */
    public void setSelectedOrgDTO(OrganizationDTO selectedOrgDTO) {
        this.selectedOrgDTO = selectedOrgDTO;
    }

    /**
     * @return the cbValue
     */
    public Long getCbValue() {
        return cbValue;
    }

    /**
     * @param cbValue the cbValue to set
     */
    public void setCbValue(Long cbValue) {
        this.cbValue = cbValue;
    }

    /**
     * @return the recStatus
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * @param recStatus the recStatus to set
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * @return the recStatusDate
     */
    public String getRecStatusDate() {
        return recStatusDate;
    }

    /**
     * @param recStatusDate  the recStatusDate to set
     */
    public void setRecStatusDate(String recStatusDate) {
        this.recStatusDate = PAUtil.normalizeDateString(recStatusDate);
    }

    /**
     * @return the spContactDTO
     */
    public List<StudySiteContactDTO> getSpContactDTO() {
        return spContactDTO;
    }

    /**
     * @param spContactDTO  the spContactDTO to set
     */
    public void setSpContactDTO(List<StudySiteContactDTO> spContactDTO) {
        this.spContactDTO = spContactDTO;
    }

    /**
     * @return the newParticipation
     */
    public boolean isNewParticipation() {
        return newParticipation;
    }

    /**
     * @param newParticipation the newParticipation to set
     */
    public void setNewParticipation(boolean newParticipation) {
        this.newParticipation = newParticipation;
    }

    /**
     * @return the editOrg
     */
    public Organization getEditOrg() {
        return editOrg;
    }

    /**
     * @param editOrg  the editOrg to set
     */
    public void setEditOrg(Organization editOrg) {
        this.editOrg = editOrg;
    }

    /**
     * @return the personWebDTOList
     */
    public List<PaPersonDTO> getPersonWebDTOList() {
        return personWebDTOList;
    }

    /**
     * @param personWebDTOList  the personWebDTOList to set
     */
    public void setPersonWebDTOList(List<PaPersonDTO> personWebDTOList) {
        this.personWebDTOList = personWebDTOList;
    }

    /**
     * @return the personContactWebDTO
     */
    public PaPersonDTO getPersonContactWebDTO() {
        return personContactWebDTO;
    }

    /**
     * @param personContactWebDTO
     *            the personContactWebDTO to set
     */
    public void setPersonContactWebDTO(PaPersonDTO personContactWebDTO) {
        this.personContactWebDTO = personContactWebDTO;
    }

    /**
     * @return the organizationName
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * @param organizationName the organizationName to set
     */
    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    /**
     * @return the orgFromPO
     */
    public PaOrganizationDTO getOrgFromPO() {
        return orgFromPO;
    }

    /**
     * @param orgFromPO the orgFromPO to set
     */
    public void setOrgFromPO(PaOrganizationDTO orgFromPO) {
        this.orgFromPO = orgFromPO;
    }

    /**
     * @return the currentAction
     */
    public String getCurrentAction() {
        return currentAction;
    }

    /**
     * @param currentAction the currentAction to set
     */
    public void setCurrentAction(String currentAction) {
        this.currentAction = currentAction;
    }

    /**
     * @return the targetAccrualNumber
     */
    public String getTargetAccrualNumber() {
        return targetAccrualNumber;
    }

    /**
     * @param targetAccrualNumber the targetAccrualNumber to set
     */
    public void setTargetAccrualNumber(String targetAccrualNumber) {
        Integer tInt;
        try {
            tInt = Integer.parseInt(targetAccrualNumber);
            this.targetAccrualNumber = tInt.toString();
        } catch (NumberFormatException e) {
            this.targetAccrualNumber = null;
        }
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    /**
     *
     * @return s
     */
    public String proprietaryEdit() {
        setCurrentAction(ACT_EDIT);
        StudySiteDTO spDto;
        try {
            selectedPersTO = null;
            selectedOrgDTO = null;
            spDto = sPartService.get(IiConverter.convertToIi(cbValue));
            studySiteIdentifier = cbValue;
            editOrg = new Organization();
            editOrg = correlationUtils.getPAOrganizationByIi(spDto.getHealthcareFacilityIi());
            orgFromPO = new PaOrganizationDTO();
            orgFromPO.setName(editOrg.getName());

            siteLocalTrialIdentifier = StConverter.convertToString(
                    spDto.getLocalStudyProtocolIdentifier());
            siteProgramCodeText = StConverter.convertToString(spDto.getProgramCodeText());
            dateOpenedForAccrual = IvlConverter.convertTs().convertLowToString(spDto.getAccrualDateRange());
            dateClosedForAccrual = IvlConverter.convertTs().convertHighToString(spDto.getAccrualDateRange());

            //get the person info
            personContactWebDTO = new PaPersonDTO();
            StudySiteContactDTO criteriaDTO = new StudySiteContactDTO();
            criteriaDTO.setRoleCode(CdConverter.convertToCd(StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR));
            List<StudySiteContactDTO> resultDTOList = sPartContactService.getByStudyProtocol(
                    spDto.getStudyProtocolIdentifier(), criteriaDTO);
            Ii crsIi = null;
            for (StudySiteContactDTO dto : resultDTOList) {
                if (dto.getStudySiteIi().getExtension().equals(studySiteIdentifier.toString())) {
                    crsIi = dto.getClinicalResearchStaffIi();
                }
            }
            Person per = correlationUtils.getPAPersonByIi(crsIi);
            personContactWebDTO.setSelectedPersId(Long.valueOf(per.getIdentifier()));
            personContactWebDTO.setFullName(per.getFullName());

            StudySiteAccrualStatusDTO status = ssasService
            .getCurrentStudySiteAccrualStatusByStudySite(IiConverter.convertToStudySiteAccuralStatusIi(
                    studySiteIdentifier));
            if (status != null) {
                this.setRecStatus(status.getStatusCode().getCode());
                this.setRecStatusDate(TsConverter.convertToTimestamp(status.getStatusDate()).toString());
            }
        } catch (PAException e) {
            LOG.equals(e);
        }


     return "proprietaryEdit";
    }
    /**
     *
     * @return s
     */
    public String proprietaryCreate() {
        try {
            loadProprietaryIndicator();
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        }
        return "proprietaryCreate";
    }
    /**
     * @return the siteLocalTrialIdentifier
     */
    public String getSiteLocalTrialIdentifier() {
        return siteLocalTrialIdentifier;
    }

    /**
     * @param siteLocalTrialIdentifier the siteLocalTrialIdentifier to set
     */
    public void setSiteLocalTrialIdentifier(String siteLocalTrialIdentifier) {
        this.siteLocalTrialIdentifier = siteLocalTrialIdentifier;
    }

    /**
     * @return the siteProgramCodeText
     */
    public String getSiteProgramCodeText() {
        return siteProgramCodeText;
    }

    /**
     * @param siteProgramCodeText the siteProgramCodeText to set
     */
    public void setSiteProgramCodeText(String siteProgramCodeText) {
        this.siteProgramCodeText = siteProgramCodeText;
    }

    /**
     * @return the dateOpenedForAccrual
     */
    public String getDateOpenedForAccrual() {
        return dateOpenedForAccrual;
    }

    /**
     * @param dateOpenedForAccrual the dateOpenedForAccrual to set
     */
    public void setDateOpenedForAccrual(String dateOpenedForAccrual) {
        this.dateOpenedForAccrual = dateOpenedForAccrual;
    }

    /**
     * @return the dateClosedForAccrual
     */
    public String getDateClosedForAccrual() {
        return dateClosedForAccrual;
    }

    /**
     * @param dateClosedForAccrual the dateClosedForAccrual to set
     */
    public void setDateClosedForAccrual(String dateClosedForAccrual) {
        this.dateClosedForAccrual = dateClosedForAccrual;
    }

    /**
     * @return the proprietaryTrialIndicator
     */
    public String getProprietaryTrialIndicator() {
        return proprietaryTrialIndicator;
    }

    /**
     * @param proprietaryTrialIndicator the proprietaryTrialIndicator to set
     */
    public void setProprietaryTrialIndicator(String proprietaryTrialIndicator) {
        this.proprietaryTrialIndicator = proprietaryTrialIndicator;
    }

    /**
     * @return the studySiteIdentifier
     */
    public Long getStudySiteIdentifier() {
        return studySiteIdentifier;
    }

    /**
     * @param studySiteIdentifier the studySiteIdentifier to set
     */
    public void setStudySiteIdentifier(Long studySiteIdentifier) {
        this.studySiteIdentifier = studySiteIdentifier;
    }

    /**
     *
     * @return s
     */
    public String displayPerson() {
        String perId = ServletActionContext.getRequest().getParameter("perId");
        PersonDTO criteria = new PersonDTO();
        criteria.setIdentifier(EnOnConverter.convertToOrgIi(Long.valueOf(perId)));
        LimitOffset limit = new LimitOffset(PAConstants.MAX_SEARCH_RESULTS, 0);
        PersonDTO perDTO;
        try {
            perDTO = PoRegistry.getPersonEntityService().search(criteria, limit).get(0);
            // convert the PO DTO to the pa domain
            personContactWebDTO = PADomainUtils.convertToPaPersonDTO(perDTO);
            personContactWebDTO.setSelectedPersId(personContactWebDTO.getId());
        } catch (PAException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        } catch (TooManyResultsException e) {
            LOG.error(e);
            addActionError(e.getMessage());
        }
        return "displayPerson";
    }
    /**
     *
     * @return s
     */
   public String proprietarySave() {
       clearErrorsAndMessages();
       enforceBusinessRulesForProprietary();
       if (hasErrors()) {
           return "error_proprietary";
       }
       try {
           save();
       } catch (PAException e) {
           LOG.error(e);
           addActionError(e.getMessage());
       }
       return execute();
   }

    private Ii getPoHcfIi(String poOrgId) throws PAException {
        Ii poHcfIi = null;
             
        try {
            List<HealthCareFacilityDTO> poHcfList =
                 PoRegistry.getHealthCareFacilityCorrelationService()
                .getCorrelationsByPlayerIds(new Ii[]{IiConverter.convertToPoOrganizationIi(poOrgId)});
            if (poHcfList.isEmpty()) {
                OrganizationDTO orgDTO = PoRegistry.getOrganizationEntityService()
                    .getOrganization(IiConverter.convertToPoOrganizationIi(poOrgId));
                HealthCareFacilityDTO hcfDTO = new HealthCareFacilityDTO();
                hcfDTO.setPlayerIdentifier(orgDTO.getIdentifier());
                poHcfIi = PoRegistry.getHealthCareFacilityCorrelationService().createCorrelation(hcfDTO);
            } else {
                poHcfIi = DSetConverter.convertToIi(poHcfList.get(0).getIdentifier());
            }
        } catch (NullifiedRoleException e) {
            throw new PAException("The HCF for po Org id: " + poOrgId + " no longer exists.", e);
        } catch (EntityValidationException e) {
            throw new PAException("HCF could not pass validation for po Org with id: " + poOrgId, e);
        } catch (CurationException e) {
            throw new PAException("Could not curate HCF for po Org with id" + poOrgId, e);
        } catch (NullifiedEntityException e) {
            throw new PAException(PAUtil.handleNullifiedEntityException(e), e);
        }
        return poHcfIi;
    }
    
    private ClinicalResearchStaffDTO getCrsDTO(Ii investigatorIi, String poOrgId) 
    throws PAException {
        ClinicalResearchStaffDTO crsDTO = null;
        List<ClinicalResearchStaffDTO> poCrsList;
        try {
            poCrsList = PoRegistry.getClinicalResearchStaffCorrelationService()
            .getCorrelationsByPlayerIds(new Ii[]{investigatorIi});
            if (poCrsList.isEmpty()) {
                crsDTO = new ClinicalResearchStaffDTO();
                crsDTO.setPlayerIdentifier(investigatorIi);
                crsDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId));
                Ii newCrsIi = PoRegistry.getClinicalResearchStaffCorrelationService()
                    .createCorrelation(crsDTO);
                crsDTO = PoRegistry.getClinicalResearchStaffCorrelationService()
                .getCorrelation(newCrsIi);
            } else {
                crsDTO = poCrsList.get(0);
            }
        } catch (NullifiedRoleException e) {
            throw new PAException("The CRS for Person with id: " + investigatorIi + " no longer exists.", e);
        } catch (EntityValidationException e) {
            throw new PAException("CRS failed validation for Person id: " + investigatorIi, e);
        } catch (CurationException e) {
            throw new PAException("Could not curate CRS for Person id: " + investigatorIi, e);
        }
        return crsDTO;
    }
    
    private HealthCareProviderDTO getHcpDTO(String trialType, Ii investigatorIi, String poOrgId) throws PAException {
        HealthCareProviderDTO hcpDTO = null;
        if (trialType.startsWith("Interventional")) {
            List<HealthCareProviderDTO> poHcpList;
            try {
                poHcpList = PoRegistry.getHealthCareProviderCorrelationService()
                .getCorrelationsByPlayerIds(new Ii[]{investigatorIi});
                if (poHcpList.isEmpty()) {
                    hcpDTO = new HealthCareProviderDTO();
                    hcpDTO.setPlayerIdentifier(investigatorIi);
                    hcpDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi(poOrgId));
                    Ii newHcpIi = PoRegistry.getHealthCareProviderCorrelationService()
                        .createCorrelation(hcpDTO);
                    hcpDTO = PoRegistry.getHealthCareProviderCorrelationService()
                        .getCorrelation(newHcpIi);
                } else {
                    hcpDTO = poHcpList.get(0);
                }
            } catch (NullifiedRoleException e) {
                throw new PAException("The Person no longer exists.", e);
            } catch (EntityValidationException e) {
                throw new PAException("HCP failed validation.", e);
            } catch (CurationException e) {
                throw new PAException("Could not curate HCP", e);
            }
        }
        return hcpDTO;
    }
   
    private void save() throws PAException {
        StudySiteDTO siteDTO = new StudySiteDTO();
        String poOrgId = editOrg.getIdentifier();

        
        Ii poHcfIi = getPoHcfIi(poOrgId);
        siteDTO.setStatusCode(CdConverter.convertToCd(FunctionalRoleStatusCode.PENDING));
        siteDTO.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp(new Date().getTime()), null));
        siteDTO.setStudyProtocolIdentifier(spIi);
        siteDTO.setLocalStudyProtocolIdentifier(StConverter.convertToSt(siteLocalTrialIdentifier));
        siteDTO.setProgramCodeText(StConverter.convertToSt(siteProgramCodeText));
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)
                && StringUtils.isNotEmpty(dateClosedForAccrual)) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dateOpenedForAccrual,
                    dateClosedForAccrual));
        }
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)
               && StringUtils.isEmpty(dateClosedForAccrual)) {
            siteDTO.setAccrualDateRange(IvlConverter.convertTs().convertToIvl(dateOpenedForAccrual,
                    null));
        }
        
        selectedPersTO = new PersonDTO();
        selectedPersTO.setIdentifier(IiConverter.convertToPoPersonIi(
                personContactWebDTO.getSelectedPersId().toString()));
             
        StudySiteAccrualStatusDTO ssas = new StudySiteAccrualStatusDTO();
        ssas.setIdentifier(IiConverter.convertToIi((Long) null));
        ssas.setStatusCode(CdConverter.convertToCd(RecruitmentStatusCode.getByCode(recStatus)));
        ssas.setStatusDate(TsConverter.convertToTs(PAUtil.dateStringToTimestamp(recStatusDate)));
        
        
        Ii ssIi = IiConverter.convertToStudySiteIi(studySiteIdentifier);
        if (currentAction.equalsIgnoreCase("create")) {

            ssIi = 
                partSiteService.createStudySiteParticipant(siteDTO, ssas, poHcfIi);      
        } else {          
           siteDTO.setIdentifier(ssIi);
           partSiteService.updateStudySiteParticipant(siteDTO, ssas);
           clearInvestigatorsForPropTrialSite(ssIi);
        }

        Ii investigatorIi = IiConverter.convertToPoPersonIi(
                personContactWebDTO.getSelectedPersId().toString());
        addInvestigator(ssIi, investigatorIi, 
                StudySiteContactRoleCode.PRINCIPAL_INVESTIGATOR.getCode(), poOrgId);  
    }
    
    private void clearInvestigatorsForPropTrialSite(Ii ssIi) 
        throws  PAException {
        List<StudySiteContactDTO> ssContDtoList 
            = sPartContactService.getByStudySite(ssIi);
        for (StudySiteContactDTO item : ssContDtoList) {
            sPartContactService.delete(item.getIdentifier());
        }
    }
    
    private void addInvestigator(Ii ssIi, Ii investigatorIi, String role, String poOrgId) throws PAException {
        ClinicalResearchStaffDTO crsDTO = getCrsDTO(investigatorIi, poOrgId); 
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(spIi);
        HealthCareProviderDTO hcpDTO = getHcpDTO(spDTO.getStudyProtocolType().getValue(), investigatorIi, poOrgId);
        partSiteService.addStudySiteInvestigator(ssIi, crsDTO, hcpDTO, null, 
                role);  
    }
    
    private void addPrimaryContact(Ii ssIi, Ii investigatorIi, String poOrgId, DSet<Tel> list) throws PAException {
        ClinicalResearchStaffDTO crsDTO = getCrsDTO(investigatorIi, poOrgId); 
        StudyProtocolDTO spDTO = PaRegistry.getStudyProtocolService().getStudyProtocol(spIi);
        HealthCareProviderDTO hcpDTO = getHcpDTO(spDTO.getStudyProtocolType().getValue(), 
                investigatorIi, poOrgId);      
        partSiteService.addStudySitePrimaryContact(ssIi, crsDTO, hcpDTO, null, list);  
    }
    
    private void addGenericContact(Ii ssIi, Ii orgContIi, DSet<Tel> list) throws PAException {
        
        OrganizationalContactDTO orgContDTO;
        try {
            orgContDTO = PoRegistry.getOrganizationalContactCorrelationService().getCorrelation(orgContIi);
        } catch (NullifiedRoleException e) {
            throw new PAException("Generic Contact is no longer available.", e);
        }
        partSiteService.addStudySiteGenericContact(ssIi, orgContDTO, true, list);  
    }

    private void enforceBusinessRulesForProprietary() {
        String err = "error.submit.invalidDate";      // validate date and its format
        enforcePartialRulesForProp1(err);
        String strDateOpenedForAccrual = "dateOpenedForAccrual";
        
        String strDateClosedForAccrual = "dateClosedForAccrual";
        enforcePartialRulesForProp2(err, strDateOpenedForAccrual, strDateClosedForAccrual);
        enforcePartialRulesForProp3(strDateOpenedForAccrual, strDateClosedForAccrual);
        enforcePartialRulesForProp4(strDateOpenedForAccrual, strDateClosedForAccrual);
        

    }
    private void enforcePartialRulesForProp2(String err, 
            String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        if (StringUtils.isNotEmpty(dateOpenedForAccrual)) {
            if (!PAUtil.isValidDate(dateOpenedForAccrual)) {
                addFieldError(strDateOpenedForAccrual, getText(err));
            } else if (PAUtil.isDateCurrentOrPast(dateOpenedForAccrual)) {
                addFieldError(strDateOpenedForAccrual, getText("error.submit.invalidStatusDate"));
            }
        }
        if (StringUtils.isNotEmpty(dateClosedForAccrual)) {
            if (!PAUtil.isValidDate(dateClosedForAccrual)) {
                addFieldError(strDateClosedForAccrual, getText(err));
            } else if (PAUtil.isDateCurrentOrPast(dateClosedForAccrual)) {
                addFieldError(strDateClosedForAccrual, getText("error.submit.invalidStatusDate"));
            }
        }
    }
    private void enforcePartialRulesForProp1(String err) {
    
        if (StringUtils.isEmpty(editOrg.getIdentifier())) {
            addFieldError("editOrg.name", getText("error.orgId.required"));
        }
        if (StringUtils.isEmpty(siteLocalTrialIdentifier)) {
            addFieldError("siteLocalTrialIdentifier", getText("error.siteLocalTrialIdentifier.required"));
        }
        if (personContactWebDTO.getSelectedPersId() == null) {
            addFieldError("personContactWebDTO.selectedPersId", getText("error.selectedPersId.required"));
        }
        if (StringUtils.isEmpty(recStatus)) {
            addFieldError("recStatus", getText("error.participatingOrganizations.recruitmentStatus"));
        }
        
        if (!PAUtil.isValidDate(recStatusDate)) {
            addFieldError(REC_STATUS_DATE, getText(err));
        } else if (PAUtil.isDateCurrentOrPast(recStatusDate)) {
                addFieldError(REC_STATUS_DATE, getText("error.submit.invalidStatusDate"));
        }
    
    
    }
    private void enforcePartialRulesForProp3(String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        
            if (StringUtils.isNotEmpty(dateClosedForAccrual)
                && StringUtils.isEmpty(dateOpenedForAccrual)) {
                    addFieldError(strDateOpenedForAccrual,
                            getText("error.proprietary.dateOpenReq"));
            }
            if (StringUtils.isNotEmpty(dateOpenedForAccrual)
                    && StringUtils.isNotEmpty(dateClosedForAccrual)) {
                Timestamp dateOpenedDateStamp = PAUtil.dateStringToTimestamp(dateOpenedForAccrual);
                Timestamp dateClosedDateStamp = PAUtil.dateStringToTimestamp(dateClosedForAccrual);
                if (dateClosedDateStamp.before(dateOpenedDateStamp)) {
                    addFieldError(strDateClosedForAccrual,
                            getText("error.proprietary.dateClosedAccrualBigger"));
                }
            }
            
    }
    
    private void enforcePartialRulesForProp4(String strDateOpenedForAccrual, String strDateClosedForAccrual) {
        
        
            if (StringUtils.isNotEmpty(recStatus)) {
                if (RecruitmentStatusCode.WITHDRAWN.getCode().equalsIgnoreCase(recStatus)
                        || RecruitmentStatusCode.NOT_YET_RECRUITING.getCode().equalsIgnoreCase(recStatus)) {
                    if (StringUtils.isNotEmpty(dateOpenedForAccrual)) {
                        addFieldError(strDateOpenedForAccrual, "Date Opened for Acrual must be null for " + recStatus);
                    }
                }  else if (StringUtils.isEmpty(dateOpenedForAccrual)) {
                    addFieldError(strDateOpenedForAccrual, "Date Opened for Acrual must be not null for " + recStatus);
                }
                if ((RecruitmentStatusCode.TERMINATED_RECRUITING.getCode().equalsIgnoreCase(recStatus)
                        || RecruitmentStatusCode.COMPLETED.getCode().equalsIgnoreCase(recStatus))
                        && StringUtils.isEmpty(dateClosedForAccrual)) {
                    addFieldError(strDateClosedForAccrual, "Date Closed for Acrual must not be null for " + recStatus);
                }
            }
    }
    /**
     * @return result
     */
    public String historyPopup()  {
        String studySiteId = ServletActionContext.getRequest().getParameter("studySiteId");
        if (StringUtils.isEmpty(studySiteId)) {
            return "historypopup";
        }
        overallStatusList = new ArrayList<StudyOverallStatusWebDTO>();
        List<StudySiteAccrualStatusDTO> isoList;
        try {
            isoList = ssasService.getStudySiteAccrualStatusByStudySite(
                    IiConverter.convertToStudySiteIi(Long.valueOf(studySiteId)));
            StudyOverallStatusWebDTO studySiteStatus = null;
            for (StudySiteAccrualStatusDTO iso : isoList) {
                studySiteStatus = new StudyOverallStatusWebDTO();
                studySiteStatus.setStatusCode(RecruitmentStatusCode.getByCode(iso
                    .getStatusCode().getCode()).getDisplayName());
                studySiteStatus.setStatusDate(PAUtil.normalizeDateString(
                TsConverter.convertToTimestamp(iso.getStatusDate()).toString()));
                overallStatusList.add(studySiteStatus);
            }
        } catch (PAException e) {
            addActionError(e.getMessage());
        }
        return "historypopup";
    }

    /**
     * @return the overallStatusList
     */
    public List<StudyOverallStatusWebDTO> getOverallStatusList() {
        return overallStatusList;
    }

    /**
     * @param overallStatusList the overallStatusList to set
     */
    public void setOverallStatusList(
            List<StudyOverallStatusWebDTO> overallStatusList) {
        this.overallStatusList = overallStatusList;
    }
    /**
      * @return the programCode
    */
    public String getProgramCode() {
      return programCode;
    }

    /**
     * @param programCode the programCode to set
     */
     public void setProgramCode(String programCode) {
       this.programCode = programCode;
     }

    /**
     * @param correlationUtils the correlationUtils to set
     */
    public void setCorrelationUtils(CorrelationUtilsRemote correlationUtils) {
        this.correlationUtils = correlationUtils;
    }

}
