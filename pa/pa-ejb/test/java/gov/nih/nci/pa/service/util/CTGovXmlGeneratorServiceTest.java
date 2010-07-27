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
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.iso21090.Ad;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.iso21090.DSet;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.iso21090.Tel;
import gov.nih.nci.iso21090.TelEmail;
import gov.nih.nci.iso21090.TelPhone;
import gov.nih.nci.iso21090.TelUrl;
import gov.nih.nci.iso21090.Ts;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.dto.PAContactDTO;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteContactRoleCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.DiseaseDTO;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.dto.InterventionAlternateNameDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.InterventionalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.ObservationalStudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.PlannedActivityDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.dto.StudyDiseaseDTO;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudyOverallStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyRecruitmentStatusDTO;
import gov.nih.nci.pa.iso.dto.StudyRegulatoryAuthorityDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.DocumentWorkflowStatusServiceLocal;
import gov.nih.nci.pa.service.InterventionAlternateNameServiceRemote;
import gov.nih.nci.pa.service.InterventionServiceLocal;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.PlannedActivityServiceLocal;
import gov.nih.nci.pa.service.StudyContactServiceLocal;
import gov.nih.nci.pa.service.StudyDiseaseServiceLocal;
import gov.nih.nci.pa.service.StudyIndldeServiceLocal;
import gov.nih.nci.pa.service.StudyOutcomeMeasureServiceLocal;
import gov.nih.nci.pa.service.StudyOverallStatusServiceLocal;
import gov.nih.nci.pa.service.StudyProtocolServiceLocal;
import gov.nih.nci.pa.service.StudyRecruitmentStatusServiceLocal;
import gov.nih.nci.pa.service.StudyRegulatoryAuthorityServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.util.PAConstants;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CTGovXmlGeneratorServiceTest {

    private final CTGovXmlGeneratorServiceBean bean = new CTGovXmlGeneratorServiceBean();

    private PoServiceLocator poSvcLoc;
    private StudyProtocolServiceLocal spSvc;
    private StudySiteServiceLocal ssSvc;
    private RegistryUserServiceRemote regSvc;
    private StudyIndldeServiceLocal sIndSvc;
    private OrganizationCorrelationServiceRemote orgSvc;
    private StudyContactServiceLocal scSvc;
    private CorrelationUtils corUtils;
    private StudyRegulatoryAuthorityServiceLocal sraSvc;
    private RegulatoryInformationServiceRemote riSvc;
    private StudyOverallStatusServiceLocal sosSvc;
    private StudyRecruitmentStatusServiceLocal srsSvc;
    private StudyOutcomeMeasureServiceLocal somSvc;
    private StudyDiseaseServiceLocal sdSvc;
    private ArmServiceLocal armSvc;
    private PlannedActivityServiceLocal plActSvc;
    private DocumentWorkflowStatusServiceLocal dwsSvc;
    private DiseaseServiceLocal disSvc;
    private StudySiteAccrualStatusServiceLocal ssasSvc;
    private StudySiteContactServiceLocal ssconSvc;
    private OrganizationEntityServiceRemote poOrgSvc;
    private InterventionServiceLocal interSvc;
    private InterventionAlternateNameServiceRemote interAnSvc;

    private Ii spId;
    private StudyProtocolDTO spDto;
    private StudySiteDTO ssDto;
    private List<StudySiteDTO> ssDtoList;
    private RegistryUser regUser;
    private StudyIndldeDTO sIndDto;
    private List<StudyIndldeDTO> sIndDtoList;
    private Organization org;
    private List<Organization> orgList;
    private StudyContactDTO scDto;
    private List<StudyContactDTO> scDtoList;
    private Person person;
    private StudyRegulatoryAuthorityDTO sraDto;
    private RegulatoryAuthority ra;
    private Country country;
    private StudyOverallStatusDTO sosDto;
    private StudyRecruitmentStatusDTO srsDto;
    private InterventionalStudyProtocolDTO ispDto;
    private StudyOutcomeMeasureDTO somDto;
    private List<StudyOutcomeMeasureDTO> somDtoList;
    private StudyDiseaseDTO sdDto;
    private List<StudyDiseaseDTO> sdDtoList;
    private ArmDTO armDto;
    private List<ArmDTO> armDtoList;
    private PlannedActivityDTO plActDto;
    private List<PlannedActivityDTO> plActDtoList;
    private DocumentWorkflowStatusDTO dwsDto;
    private List<DocumentWorkflowStatusDTO> dwsDtoList;
    private DiseaseDTO disDto;
    private PAContactDTO paConDto;
    private OrganizationDTO orgDto;
    private StudySiteAccrualStatusDTO ssasDto;
    private StudySiteContactDTO ssconDto;
    private List<StudySiteContactDTO> ssconDtoList;
    private PlannedEligibilityCriterionDTO plECDto;
    private List<PlannedEligibilityCriterionDTO> plEcDtoList;
    private InterventionDTO interDto;
    private InterventionAlternateNameDTO interAnDto;
    private List<InterventionAlternateNameDTO> interAnDtoList;
    private ObservationalStudyProtocolDTO ospDto;

    @Before
    public void setUp() throws Exception {
       setupSpDto();

       setupSsDto();

       setupRegUser();

       setupSIndDto();

       setupOrg();

       DSet<Tel> telAd = setupScDto();

       setupPerson();

       setupSraDto();

       ra = new RegulatoryAuthority();
       country = new Country();

       sosDto = new StudyOverallStatusDTO();
       sosDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.WITHDRAWN.getCode()));

       srsDto = new StudyRecruitmentStatusDTO();

       setupIspDto();

       setupSomDto();

       setupSdDto();

       setupArmDto();

       setupPlActDto();

       setupDwsDto();

       disDto = new DiseaseDTO();
       disDto.setPreferredName(StConverter.convertToSt("some disease"));

       paConDto = new PAContactDTO();
       paConDto.setTitle("some title");

       setupOrgDto(telAd);

       ssasDto = new StudySiteAccrualStatusDTO();

       setupSsConDto(telAd);

       setupPlEcDto();

       setupInterDto();

       ospDto = new ObservationalStudyProtocolDTO();

       setupMocks();
     }

    private void setupInterDto() {
        interDto = new InterventionDTO();

           interAnDtoList = new ArrayList<InterventionAlternateNameDTO>();
           interAnDto = new InterventionAlternateNameDTO();
           interAnDto.setNameTypeCode(StConverter.convertToSt(PAConstants.SYNONYM));
           interAnDto.setName(StConverter.convertToSt("name 1"));
           interAnDtoList.add(interAnDto);
           interAnDto = new InterventionAlternateNameDTO();
           interAnDto.setNameTypeCode(StConverter.convertToSt(PAConstants.ABBREVIATION));
           interAnDto.setName(StConverter.convertToSt("name 2"));
           interAnDtoList.add(interAnDto);
    }

    private void setupPlEcDto() {
        plEcDtoList = new ArrayList<PlannedEligibilityCriterionDTO>();
           plECDto = new PlannedEligibilityCriterionDTO();
           Ivl<Pq> ivlPq = new Ivl<Pq>();
           Pq pq = new Pq();
           pq.setValue(BigDecimal.valueOf(1L));
           ivlPq.setLow(pq);
           ivlPq.setHigh(pq);
           plECDto.setValue(ivlPq);
           plECDto.setDisplayOrder(IntConverter.convertToInt(1));
           plECDto.setCriterionName(StConverter.convertToSt("GENDER"));
           plECDto.setEligibleGenderCode(CdConverter.convertStringToCd("M"));
           plECDto.setTextDescription(StConverter.convertToSt("some description"));
           plECDto.setOperator(StConverter.convertToSt("+"));
           plEcDtoList.add(plECDto);

           plECDto = new PlannedEligibilityCriterionDTO();
           plECDto.setCriterionName(StConverter.convertToSt("AGE"));
           ivlPq = new Ivl<Pq>();
           pq = new Pq();
           pq.setValue(BigDecimal.valueOf(1L));
           pq.setUnit("some unit");
           ivlPq.setLow(pq);
           pq = new Pq();
           pq.setValue(BigDecimal.valueOf(999L));
           ivlPq.setHigh(pq);
           plECDto.setValue(ivlPq);
           plECDto.setDisplayOrder(IntConverter.convertToInt(2));
           plECDto.setTextDescription(StConverter.convertToSt("some description"));
           plECDto.setInclusionIndicator(BlConverter.convertToBl(true));
           plECDto.setOperator(StConverter.convertToSt("+"));
           plEcDtoList.add(plECDto);
    }

    private void setupSsConDto(DSet<Tel> telAd) {
        ssconDtoList = new ArrayList<StudySiteContactDTO>();
           ssconDto = new StudySiteContactDTO();
           ssconDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
           ssconDto.setTelecomAddresses(telAd);
           ssconDto.setClinicalResearchStaffIi(spId);
           ssconDto.setOrganizationalContactIi(spId);
           ssconDtoList.add(ssconDto);

           ssconDto = new StudySiteContactDTO();
           ssconDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.COORDINATING_INVESTIGATOR.getCode()));
           ssconDto.setTelecomAddresses(telAd);
           ssconDto.setOrganizationalContactIi(spId);
           ssconDtoList.add(ssconDto);
    }

    private void setupOrgDto(DSet<Tel> telAd) {
        orgDto = new OrganizationDTO();
           orgDto.setTelecomAddress(telAd);
           Ad adr = AddressConverterUtil.create("street", "deliv", "city", "MD", "20000", "USA");
           orgDto.setPostalAddress(adr);
           orgDto.setName(EnOnConverter.convertToEnOn("some org name"));
    }

    private void setupDwsDto() {
        dwsDtoList = new ArrayList<DocumentWorkflowStatusDTO>();
           dwsDto = new DocumentWorkflowStatusDTO();
           Ivl<Ts> ivlTs = new Ivl<Ts>();
           ivlTs.setLow(TsConverter.convertToTs(new Timestamp(0)));
           dwsDto.setStatusDateRange(ivlTs);
           dwsDtoList.add(dwsDto);
    }

    private void setupPlActDto() {
        plActDtoList = new ArrayList<PlannedActivityDTO>();
           plActDto = new PlannedActivityDTO();
           plActDto.setCategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
           plActDto.setSubcategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
           plActDtoList.add(plActDto);
    }

    private void setupArmDto() {
        armDto = new ArmDTO();
           armDto.setName(StConverter.convertToSt("some name"));
           armDto.setDescriptionText(StConverter.convertToSt("some description"));
           armDtoList = new ArrayList<ArmDTO>();
           armDtoList.add(armDto);
    }

    private void setupSdDto() {
        sdDtoList = new ArrayList<StudyDiseaseDTO>();
           sdDto = new StudyDiseaseDTO();
           sdDto.setLeadDiseaseIndicator(BlConverter.convertToBl(true));
           sdDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
           sdDto.setDiseaseIdentifier(spId);
           sdDtoList.add(sdDto);

           sdDto = new StudyDiseaseDTO();
           sdDto.setLeadDiseaseIndicator(BlConverter.convertToBl(true));
           sdDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
           sdDtoList.add(sdDto);
    }

    private void setupSomDto() {
        somDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
           somDto = new StudyOutcomeMeasureDTO();
           somDto.setName(StConverter.convertToSt("some name"));
           somDto.setSafetyIndicator(BlConverter.convertToBl(true));
           somDto.setPrimaryIndicator(BlConverter.convertToBl(true));
           somDto.setTimeFrame(StConverter.convertToSt("some time"));
           somDtoList.add(somDto);
           somDto = new StudyOutcomeMeasureDTO();
           somDto.setName(StConverter.convertToSt("some name"));
           somDto.setSafetyIndicator(BlConverter.convertToBl(true));
           somDto.setPrimaryIndicator(BlConverter.convertToBl(true));
           somDto.setTimeFrame(StConverter.convertToSt("some time"));
           somDto.setPrimaryIndicator(BlConverter.convertToBl(false));
           somDtoList.add(somDto);
    }

    private void setupIspDto() {
        ispDto = new InterventionalStudyProtocolDTO();
           List<Cd> blindingRoles = new ArrayList<Cd>();
           blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.CAREGIVER.getCode()));
           blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.INVESTIGATOR.getCode()));
           blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.OUTCOMES_ASSESSOR.getCode()));
           blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.SUBJECT.getCode()));
           blindingRoles.add(CdConverter.convertStringToCd("some unknown code"));
           ispDto.setBlindedRoleCode(DSetConverter.convertCdListToDSet(blindingRoles));
    }

    private void setupSraDto() {
        sraDto = new StudyRegulatoryAuthorityDTO();
           sraDto.setRegulatoryAuthorityIdentifier(spId);
    }

    private void setupPerson() {
        person = new Person();
           person.setFirstName("first name");
           person.setLastName("last Name");
    }

    private DSet<Tel> setupScDto() throws URISyntaxException {
        scDto = new StudyContactDTO();
           scDto.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
           scDto.setClinicalResearchStaffIi(spId);
           scDto.setOrganizationalContactIi(spId);
           scDto.setHealthCareProviderIi(spId);
           DSet<Tel> telAd = new DSet<Tel>();
           Set<Tel> telSet = new HashSet<Tel>();
           TelEmail email = new TelEmail();
           email.setValue(new URI("mailto:X"));
           telSet.add(email);
           TelPhone phone = new TelPhone();
           phone.setValue(new URI("tel:111-222-3333"));
           telSet.add(phone);
           TelUrl url = new TelUrl();
           url.setValue(new URI("http://ctrp.com"));
           telSet.add(url);
           telAd.setItem(telSet);
           scDto.setTelecomAddresses(telAd);
           scDtoList = new ArrayList<StudyContactDTO>();
           scDtoList.add(scDto);
        return telAd;
    }

    private void setupOrg() {
        orgList = new ArrayList<Organization>();
           org = new Organization();
           org.setName("some name");
           orgList.add(org);
    }

    private void setupSIndDto() {
        sIndDto = new StudyIndldeDTO();
           sIndDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
           sIndDtoList = new ArrayList<StudyIndldeDTO>();
           sIndDtoList.add(sIndDto);
    }

    private void setupRegUser() {
        regUser = new RegistryUser();
           regUser.setPrsOrgName("prs Org Name");
    }

    private void setupSsDto() {
        ssDto = new StudySiteDTO();
           ssDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode()));
           ssDtoList = new ArrayList<StudySiteDTO>();
           ssDtoList.add(ssDto);
    }

    private void setupSpDto() {
        spId = new Ii();
           spId.setExtension("1");

           spDto = new StudyProtocolDTO();
           spDto.setPublicTitle(StConverter.convertToSt("title"));
           spDto.setAcronym(StConverter.convertToSt("acronym"));
           spDto.setOfficialTitle(StConverter.convertToSt("off title"));
           spDto.setIdentifier(spId);
           spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(true));
           spDto.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
           spDto.setDataMonitoringCommitteeAppointedIndicator(BlConverter.convertToBl(true));
           spDto.setSection801Indicator(BlConverter.convertToBl(true));
           spDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
           spDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(true));
           spDto.setPrimaryCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
           spDto.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(0)));
    }

    private void setupMocks() throws PAException, NullifiedRoleException, NullifiedEntityException {
        setupSpSvcMock();

        setupSsSvcMock();

        setupRegSvcMock();

        setupSIndSvcMock();

        setupOrgSvcMock();

        setupScSvcMock();

        setupCorSvcMock();

        setupSraMock();

        setupRiMock();

        setupSosSvc();

        setupSrsSvc();

        setupSomSvc();

        setupSdSvc();

        setupArmSvc();

        setupPlActSvc();

        setupDwsSvc();

        setupDisSvc();

        setupSsasSvc();

        setupSsconSvc();

        setupInterSvc();

        setupPoSvc();

    }

    private void setupPoSvc() throws NullifiedEntityException, PAException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(orgDto);
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
    }

    private void setupInterSvc() throws PAException {
        interSvc = mock(InterventionServiceLocal.class);
        when(interSvc.get(any(Ii.class))).thenReturn(interDto);
        bean.setInterventionService(interSvc);

        interAnSvc = mock(InterventionAlternateNameServiceRemote.class);
        when(interAnSvc.getByIntervention(any(Ii.class))).thenReturn(interAnDtoList);
        bean.setInterventionAlternateNameService(interAnSvc);
    }

    private void setupSsconSvc() throws PAException {
        ssconSvc = mock(StudySiteContactServiceLocal.class);
        when(ssconSvc.getByStudySite(any(Ii.class))).thenReturn(ssconDtoList);
        when(ssconSvc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(ssconDtoList);
        bean.setStudySiteContactService(ssconSvc);
    }

    private void setupSsasSvc() throws PAException {
        ssasSvc = mock(StudySiteAccrualStatusServiceLocal.class);
        when(ssasSvc.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(ssasDto);
        bean.setStudySiteAccrualStatusService(ssasSvc);
    }

    private void setupDisSvc() throws PAException {
        disSvc = mock(DiseaseServiceLocal.class);
        when(disSvc.get(any(Ii.class))).thenReturn(disDto);
        bean.setDiseaseService(disSvc);
    }

    private void setupDwsSvc() throws PAException {
        dwsSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwsSvc.getByStudyProtocol(any(Ii.class))).thenReturn(dwsDtoList);
        when(dwsSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwsDto);
        bean.setDocumentWorkflowStatusService(dwsSvc);
    }

    private void setupPlActSvc() throws PAException {
        plActSvc = mock(PlannedActivityServiceLocal.class);
        when(plActSvc.getByStudyProtocol(any(Ii.class))).thenReturn(plActDtoList);
        when(plActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(plEcDtoList);
        bean.setPlannedActivityService(plActSvc);
    }

    private void setupArmSvc() throws PAException {
        armSvc = mock(ArmServiceLocal.class);
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(armDtoList);
        when(armSvc.getByPlannedActivity(any(Ii.class))).thenReturn(armDtoList);
        bean.setArmService(armSvc);
    }

    private void setupSdSvc() throws PAException {
        sdSvc = mock(StudyDiseaseServiceLocal.class);
        when(sdSvc.getByStudyProtocol(any(Ii.class))).thenReturn(sdDtoList);
        bean.setStudyDiseaseService(sdSvc);
    }

    private void setupSomSvc() throws PAException {
        somSvc = mock(StudyOutcomeMeasureServiceLocal.class);
        when(somSvc.getByStudyProtocol(any(Ii.class))).thenReturn(somDtoList);
        bean.setStudyOutcomeMeasureService(somSvc);
    }

    private void setupSrsSvc() throws PAException {
        srsSvc = mock(StudyRecruitmentStatusServiceLocal.class);
        when(srsSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(srsDto);
        bean.setStudyRecruitmentService(srsSvc);
    }

    private void setupSosSvc() throws PAException {
        sosSvc = mock(StudyOverallStatusServiceLocal.class);
        when(sosSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(sosDto);
        bean.setStudyOverallStatusService(sosSvc);
    }

    private void setupRiMock() throws PAException {
        riSvc = mock(RegulatoryInformationServiceRemote.class);
        when(riSvc.get(anyLong())).thenReturn(ra);
        when(riSvc.getRegulatoryAuthorityCountry(anyLong())).thenReturn(country);
        bean.setRegulatoryInformationService(riSvc);
    }

    private void setupSraMock() throws PAException {
        sraSvc = mock(StudyRegulatoryAuthorityServiceLocal.class);
        when(sraSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(sraDto);
        bean.setStudyRegulatoryAuthorityService(sraSvc);
    }

    private void setupCorSvcMock() throws PAException, NullifiedRoleException {
        corUtils = mock(CorrelationUtils.class);
        when(corUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(corUtils.getContactByPAOrganizationalContactId(anyLong())).thenReturn(paConDto);
        when(corUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
        bean.setCorrelationUtils(corUtils);
    }

    private void setupScSvcMock() throws PAException {
        scSvc = mock(StudyContactServiceLocal.class);
        when(scSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(scDtoList);
        bean.setStudyContactService(scSvc);
    }

    private void setupOrgSvcMock() throws PAException {
        orgSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgSvc.getOrganizationByFunctionRole(any(Ii.class), any(Cd.class))).thenReturn(org);
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class))).thenReturn(orgList);
        bean.setOrgCorrelationService(orgSvc);
    }

    private void setupSIndSvcMock() throws PAException {
        sIndSvc = mock(StudyIndldeServiceLocal.class);
        when(sIndSvc.getByStudyProtocol(any(Ii.class))).thenReturn(sIndDtoList);
        bean.setStudyIndldeService(sIndSvc);
    }

    private void setupRegSvcMock() throws PAException {
        regSvc = mock(RegistryUserServiceRemote.class);
        when(regSvc.getUser(anyString())).thenReturn(regUser);
        bean.setRegistryUserService(regSvc);
    }

    private void setupSsSvcMock() throws PAException {
        ssSvc = mock(StudySiteServiceLocal.class);
        when(ssSvc.getByStudyProtocol(any(Ii.class))).thenReturn(ssDtoList);
        when(ssSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(ssDtoList);
        when(ssSvc.getByStudyProtocol(any(Ii.class), any(List.class))).thenReturn(ssDtoList);
        bean.setStudySiteService(ssSvc);
    }

    private void setupSpSvcMock() throws PAException {
        spSvc = mock(StudyProtocolServiceLocal.class);
        when(spSvc.getStudyProtocol((Ii)anyObject())).thenReturn(spDto);
        when(spSvc.getInterventionalStudyProtocol((Ii)anyObject())).thenReturn(ispDto);
        when(spSvc.getObservationalStudyProtocol(any(Ii.class))).thenReturn(ospDto);
        bean.setStudyProtocolService(spSvc);
    }

    @Test(expected=PAException.class)
    public void nullParameter() throws Exception {
        bean.generateCTGovXml(null);
    }

    @Test
    public void testHappyPath() throws PAException {
       String st = bean.generateCTGovXml(spId);
       assertTrue(st.contains("<clinical_study>"));
       assertTrue(st.contains("<is_section_801>"));
    }

    @Test
    public void testCtGovFalse() throws PAException {
        spDto.setCtgovXmlRequiredIndicator(BlConverter.convertToBl(false));
        assertFalse(bean.generateCTGovXml(spId).contains("<is_section_801>"));
    }

    @Test
    public void test801False() throws PAException {
        spDto.setSection801Indicator(BlConverter.convertToBl(false));
        assertTrue(bean.generateCTGovXml(spId).contains("<is_section_801>No</is_section_801>"));
    }

    @Test
    public void testExpAccIndNull() throws PAException {
        spDto.setExpandedAccessIndicator(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<expanded_access_status>"));
    }

    @Test
    public void testExpAccIndFalse() throws PAException {
        spDto.setExpandedAccessIndicator(BlConverter.convertToBl(false));
        assertTrue(bean.generateCTGovXml(spId).contains("<expanded_access_status>No longer available</expanded_access_status>"));
    }

    @Test
    public void testSosByCurrentIsNull() throws PAException {
        when(sosSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<overall_status>"));
    }

    @Test
    public void testCtGovNull() throws PAException {
        sdDto.setCtGovXmlIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testLeadDisIndNull() throws PAException {
        sdDtoList.remove(0);
        sdDto.setLeadDiseaseIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSDCtGovNull() throws PAException {
        sdDtoList.remove(0);
        sdDto.setCtGovXmlIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testOrgContIiNull() throws PAException {
        scDto.setOrganizationalContactIi(null);
        assertFalse(bean.generateCTGovXml(spId).contains("some title"));
    }

    @Test
    public void testNoGoodTel() throws PAException, URISyntaxException {
        DSet<Tel> telAd = new DSet<Tel>();
        Set<Tel> telSet = new HashSet<Tel>();
        TelUrl url = new TelUrl();
        url.setValue(new URI("http://abc.com"));
        telSet.add(url);
        telAd.setItem(telSet);
        scDto.setTelecomAddresses(telAd);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testTelNull() throws PAException {
        scDto.setTelecomAddresses(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<email>X</email>\n</overall_contact>"));
    }

    @Test
    public void testCrsNull() throws PAException {
        scDto.setClinicalResearchStaffIi(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<last_name>some title"));
    }

    @Test
    public void testCurrentSraNull() throws PAException {
        when(sraSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRIgetCountryNull() throws PAException {
        when(riSvc.getRegulatoryAuthorityCountry(anyLong())).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRiGetNull() throws PAException {
        when(riSvc.get(anyLong())).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<regulatory_authority>"));
    }

    @Test
    public void testRbApNull() throws PAException {
        spDto.setReviewBoardApprovalRequiredIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<approval_status>Not required</"));
    }

    @Test
    public void testRbAppFalse() throws PAException {
        spDto.setReviewBoardApprovalRequiredIndicator(BlConverter.convertToBl(false));
        assertTrue(bean.generateCTGovXml(spId).contains("<approval_status>Not required</"));
    }

    @Test
    public void testGetOrgThrowNpe() throws PAException, NullifiedEntityException {
        when(poOrgSvc.getOrganization(any(Ii.class))).thenThrow(new NullifiedEntityException(spId));
        assertTrue(bean.generateCTGovXml(spId).contains("<error_description>"));
    }

    @Test
    public void testGetPlActECBySpReturnNull() throws PAException {
        when(plActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testSIndGetNull() throws PAException {
        when(sIndSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<is_ind_study>No</"));
    }

    @Test
    public void testCritNameNull() throws PAException {
        plECDto.setCriterionName(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testInclIndTrue() throws PAException {
        plECDto.setInclusionIndicator(BlConverter.convertToBl(true));
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritAndInclIndNull() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setInclusionIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullInclIndFalse() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setInclusionIndicator(BlConverter.convertToBl(false));
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNull() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setTextDescription(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullOpNull() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setTextDescription(null);
        plECDto.setOperator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullInclIndFalse() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setTextDescription(null);
        plECDto.setInclusionIndicator(BlConverter.convertToBl(false));
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testCritNullTextNullInclIndNull() throws PAException {
        plECDto.setCriterionName(null);
        plECDto.setTextDescription(null);
        plECDto.setInclusionIndicator(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testPlLowUnitNull() throws PAException {
        plECDto.getValue().getLow().setUnit(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<eligibility>"));
    }

    @Test
    public void testArmBySpNull() throws PAException {
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<arm_group>"));
    }

    @Test
    public void testPlSubCatNull() throws PAException {
        plActDto.setSubcategoryCode(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<intervention_type>"));
    }

    @Test
    public void testAPlCatNull() throws PAException {
        plActDto.getCategoryCode().setCode(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<intervention_type>"));
    }

    @Test
    public void testInterTypeNull() throws PAException {
        interAnDto.getNameTypeCode().setValue(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSpTypeObs() throws PAException {
        spDto.setStudyProtocolType(StConverter.convertToSt("ObservationalStudyProtocol"));
        assertTrue(bean.generateCTGovXml(spId).contains("<study_type>Observational</"));
    }

    @Test
    public void testSpTypeOther() throws PAException {
        spDto.setStudyProtocolType(StConverter.convertToSt("some other type"));
        assertFalse(bean.generateCTGovXml(spId).contains("<study_type>"));
    }

    @Test
    public void testSomNull() throws PAException {
       when(somSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
       assertFalse(bean.generateCTGovXml(spId).contains("<primary_outcome>"));
    }

    @Test
    public void testOrgEmpty() throws PAException {
        orgList = new ArrayList<Organization>();
        assertTrue(bean.generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testSsConRolePrim() throws PAException {
        ssconDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<contact>"));
    }

    @Test
    public void testScListEmpty() throws PAException {
        when(scSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(new ArrayList<StudyContactDTO>());
        assertFalse(bean.generateCTGovXml(spId).contains("<overall_contact>"));
    }

    @Test
    public void testOrgBySSNull() throws PAException {
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class))).thenReturn(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<collaborator>"));
    }

    @Test
    public void testSpNull() throws PAException {
        when(spSvc.getStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<study_identifier>Unknown"));
    }

    @Test
    public void testOrgAdNull() throws PAException {
        orgDto.setPostalAddress(null);
        assertFalse(bean.generateCTGovXml(spId).contains("</full_address>\n</irb_info>"));
    }

    @Test
    public void testRecVerNull() throws PAException {
        spDto.setRecordVerificationDate(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSrsNull() throws PAException {
        when(srsSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatApp() throws PAException {
        sosDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.APPROVED.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatTemp() throws PAException {
        sosDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSosStatAccInt() throws PAException {
        sosDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.TEMPORARILY_CLOSED_TO_ACCRUAL_AND_INTERVENTION.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSdNull() throws PAException {
       when(sdSvc.getByStudyProtocol(any(Ii.class))).thenReturn(null);
       assertFalse(bean.generateCTGovXml(spId).contains("<condition>some disease</condition>"));
    }

    @Test
    public void testScCrsNull() throws PAException {
        scDto.setClinicalResearchStaffIi(null);
        scDto.setOrganizationalContactIi(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<overall_contact>\n<first_name>"));
    }

    @Test
    public void testScAdNull() throws PAException {
        scDto.setClinicalResearchStaffIi(null);
        scDto.setOrganizationalContactIi(null);
        scDto.setTelecomAddresses(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<overall_contact>"));
    }

    @Test
    public void testScRolCent() throws PAException {
        scDto.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.CENTRAL_CONTACT.getCode()));
        assertFalse(bean.generateCTGovXml(spId).contains("<overall_official>"));
    }

    @Test
    public void testRBStatSub() throws PAException {
        ssDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMISSION_NOT_REQUIRED.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<irb_info/>"));
    }

    @Test
    public void testRegPrsEmpty() throws PAException {
        regUser.setPrsOrgName("");
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSIndExpTrue() throws PAException {
        sIndDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsListEmpty() throws PAException {
        when(ssSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(new ArrayList<StudySiteDTO>());
        assertFalse(bean.generateCTGovXml(spId).contains("<organization>\n</resp_party>"));
    }

    @Test
    public void testSsRbSubDen() throws PAException {
        ssDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_DENIED.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsRbSubEx() throws PAException {
        ssDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_EXEMPT.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testSsRbSubPen() throws PAException {
        ssDto.setReviewBoardApprovalStatusCode(CdConverter.convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_PENDING.getCode()));
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testOrgTelEmpty() throws PAException {
        orgDto.setTelecomAddress(new DSet<Tel>());
        assertTrue(bean.generateCTGovXml(spId).contains("<irb_info>\n"
                + "<approval_status>Approved</approval_status>\n<name>some name</name>\n"
                + "<full_address>street,city,MD,20000USA</full_address>\n</irb_info>"));
    }

    @Test
    public void testSindGrantCodeNull() throws PAException {
        sIndDto.setGrantorCode(null);
        sIndDto.setIndldeNumber(null);
        sIndDto.setExpandedAccessIndicator(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<has_expanded_access>"));
    }

    @Test
    public void testPlEcDispOrdNull() throws PAException {
        plECDto.setDisplayOrder(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testPlEcCritAge() throws PAException {
        plECDto.setCriterionName(StConverter.convertToSt("AGE"));
        plECDto.getValue().setLow(null);
        plECDto.getValue().setHigh(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testPlEcCritGender() throws PAException {
        plECDto.setCriterionName(StConverter.convertToSt("GENDER"));
        plECDto.setEligibleGenderCode(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<clinical_study>"));
    }

    @Test
    public void testArmDescNull() throws PAException {
        armDto.setDescriptionText(null);
        armDto.setName(null);
        armDto.setTypeCode(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<arm_group>"));
    }

    @Test
    public void testIspBlCodeNull() throws PAException {
        ispDto.setBlindedRoleCode(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<interventional_design/>"));
    }

    @Test
    public void testIspBlCodeItemNull() throws PAException {
        ispDto.getBlindedRoleCode().setItem(null);
        assertTrue(bean.generateCTGovXml(spId).contains("<interventional_design/>"));
    }

    @Test
    public void testSomPrimTrue() throws PAException {
        somDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        somDto = new StudyOutcomeMeasureDTO();
        somDto.setName(new St());
        somDto.setTimeFrame(new St());
        somDto.setPrimaryIndicator(BlConverter.convertToBl(true));
        somDtoList.add(somDto);
        when(somSvc.getByStudyProtocol(any(Ii.class))).thenReturn(somDtoList);
        assertFalse(bean.generateCTGovXml(spId).contains("<primary_outcome>"));
    }

    @Test
    public void testSomPrimFalse() throws PAException {
        somDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        somDto = new StudyOutcomeMeasureDTO();
        somDto.setName(new St());
        somDto.setTimeFrame(new St());
        somDto.setPrimaryIndicator(BlConverter.convertToBl(false));
        somDtoList.add(somDto);
        when(somSvc.getByStudyProtocol(any(Ii.class))).thenReturn(somDtoList);
        assertFalse(bean.generateCTGovXml(spId).contains("<primary_outcome>"));
    }

    @Test
    public void testOrgNameNull() throws PAException {
        org.setName(null);
        assertFalse(bean.generateCTGovXml(spId).contains("<lead_sponsor>"));
    }

    @Test
    public void testOrgBySsEmpty() throws PAException {
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class)))
           .thenReturn(new ArrayList<Organization>());
        assertFalse(bean.generateCTGovXml(spId).contains("<collaborator>"));
    }


}
