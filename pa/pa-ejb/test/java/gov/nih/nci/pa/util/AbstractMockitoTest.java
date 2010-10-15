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
package gov.nih.nci.pa.util;

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
import gov.nih.nci.iso21090.Int;
import gov.nih.nci.iso21090.Ivl;
import gov.nih.nci.iso21090.Pq;
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
import gov.nih.nci.pa.enums.AccrualReportingMethodCode;
import gov.nih.nci.pa.enums.ActiveInactiveCode;
import gov.nih.nci.pa.enums.ActivityCategoryCode;
import gov.nih.nci.pa.enums.ArmTypeCode;
import gov.nih.nci.pa.enums.BlindingRoleCode;
import gov.nih.nci.pa.enums.ReviewBoardApprovalStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.enums.StudyContactRoleCode;
import gov.nih.nci.pa.enums.StudyRecruitmentStatusCode;
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
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.dto.StudySiteContactDTO;
import gov.nih.nci.pa.iso.dto.StudySiteDTO;
import gov.nih.nci.pa.iso.util.AddressConverterUtil;
import gov.nih.nci.pa.iso.util.BlConverter;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.DSetConverter;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.ArmServiceLocal;
import gov.nih.nci.pa.service.DiseaseServiceLocal;
import gov.nih.nci.pa.service.DocumentServiceLocal;
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
import gov.nih.nci.pa.service.StudyResourcingServiceLocal;
import gov.nih.nci.pa.service.StudySiteAccrualStatusServiceLocal;
import gov.nih.nci.pa.service.StudySiteContactServiceLocal;
import gov.nih.nci.pa.service.StudySiteServiceLocal;
import gov.nih.nci.pa.service.correlation.CorrelationUtils;
import gov.nih.nci.pa.service.correlation.OrganizationCorrelationServiceRemote;
import gov.nih.nci.pa.service.util.CTGovXmlGeneratorServiceRemote;
import gov.nih.nci.pa.service.util.PAOrganizationServiceRemote;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import gov.nih.nci.pa.service.util.RegulatoryInformationServiceRemote;
import gov.nih.nci.services.correlation.NullifiedRoleException;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationEntityServiceRemote;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;

/**
 * @author ludetc
 *
 * Extend this class whenever you need mocikto-mocked services.
 * Set the instances in your test class.
 *
 */
public class AbstractMockitoTest {


    protected PoServiceLocator poSvcLoc;
    protected StudyProtocolServiceLocal spSvc;
    protected StudySiteServiceLocal studySiteSvc;
    protected RegistryUserServiceLocal regUserSvc;
    protected StudyIndldeServiceLocal studyIndIdeSvc;
    protected OrganizationCorrelationServiceRemote orgSvc;
    protected StudyContactServiceLocal studyContactSvc;
    protected CorrelationUtils corUtils;
    protected StudyRegulatoryAuthorityServiceLocal studyRegAuthSvc;
    protected RegulatoryInformationServiceRemote regulInfoSvc;
    protected StudyOverallStatusServiceLocal studyOverallStatusSvc;
    protected StudyRecruitmentStatusServiceLocal studyRecruitmentStatusSvc;
    protected StudyOutcomeMeasureServiceLocal studyOutcomeMeasureSvc;
    protected StudyDiseaseServiceLocal studyDiseaseSvc;
    protected ArmServiceLocal armSvc;
    protected PlannedActivityServiceLocal plannedActSvc;
    protected DocumentWorkflowStatusServiceLocal dwsSvc;
    protected DiseaseServiceLocal diseaseSvc;
    protected StudySiteAccrualStatusServiceLocal studySiteAccrualStatusSvc;
    protected StudySiteContactServiceLocal studySiteContactSvc;
    protected OrganizationEntityServiceRemote poOrgSvc;
    protected InterventionServiceLocal interventionSvc;
    protected StudyResourcingServiceLocal studyResourcingSvc;
    protected InterventionAlternateNameServiceRemote interventionAltNameSvc;
    protected DocumentServiceLocal documentSvc;

    protected Ii spId;
    protected StudyProtocolDTO spDto;
    protected StudySiteDTO studySiteDto;
    protected List<StudySiteDTO> studySiteDtoList;
    protected RegistryUser regUser;
    protected StudyIndldeDTO studySiteIndIdeDto;
    protected List<StudyIndldeDTO> studySiteIndIdeDtoList;
    protected Organization org;
    protected List<Organization> orgList;
    protected StudyContactDTO studyContactDto;
    protected List<StudyContactDTO> scDtoList;
    protected Person person;
    protected StudyRegulatoryAuthorityDTO studyRegulatoryAuthDto;
    protected RegulatoryAuthority ra;
    protected Country country;
    protected StudyOverallStatusDTO studyOverallStatusDto;
    protected StudyRecruitmentStatusDTO studyRecruitmentStatusDto;
    protected InterventionalStudyProtocolDTO interventionalSPDto;
    protected StudyOutcomeMeasureDTO studyOutcomeMeasureDto;
    protected List<StudyOutcomeMeasureDTO> studyOutcomeMeasureDtoList;
    protected StudyDiseaseDTO studyDiseaseDto;
    protected List<StudyDiseaseDTO> studyDiseaseDtoList;
    protected ArmDTO armDto;
    protected List<ArmDTO> armDtoList;
    protected PlannedActivityDTO plannedActDto;
    protected List<PlannedActivityDTO> plannedActDtoList;
    protected DocumentWorkflowStatusDTO dwsDto;
    protected List<DocumentWorkflowStatusDTO> dwsDtoList;
    protected DiseaseDTO diseaseDto;
    protected PAContactDTO paContactDto;
    protected OrganizationDTO orgDto;
    protected StudySiteAccrualStatusDTO studyStieAccrualStatusDto;
    protected StudySiteContactDTO studySiteContactDto;
    protected List<StudySiteContactDTO> studySiteContactDtoList;
    protected PlannedEligibilityCriterionDTO plannedECDto;
    protected List<PlannedEligibilityCriterionDTO> plannedEcDtoList;
    protected InterventionDTO interventionDto;
    protected InterventionAlternateNameDTO interventionAltNameDto;
    protected List<InterventionAlternateNameDTO> interventionAltNameDtoList;
    protected ObservationalStudyProtocolDTO observationalSPDto;
    protected List<StudyResourcingDTO> studyResourcingDtoList;

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

       studyOverallStatusDto = new StudyOverallStatusDTO();
       studyOverallStatusDto.setStatusCode(CdConverter.convertStringToCd(StudyStatusCode.WITHDRAWN.getCode()));

       studyRecruitmentStatusDto = new StudyRecruitmentStatusDTO();
       studyRecruitmentStatusDto.setStatusCode(CdConverter.convertToCd(StudyRecruitmentStatusCode.COMPLETED));

       setupIspDto();

       setupSomDto();

       setupSdDto();

       setupArmDto();

       setupPlActDto();

       setupDwsDto();

       diseaseDto = new DiseaseDTO();
       diseaseDto.setPreferredName(StConverter.convertToSt("some disease"));

       paContactDto = new PAContactDTO();
       paContactDto.setTitle("some title");

       setupOrgDto(telAd);

       studyStieAccrualStatusDto = new StudySiteAccrualStatusDTO();

       setupSsConDto(telAd);

       setupPlEcDto();

       setupInterDto();

       setupStudyResDtos();

       observationalSPDto = new ObservationalStudyProtocolDTO();

       setupMocks();
     }

    private void setupInterDto() {
        interventionDto = new InterventionDTO();

        interventionAltNameDtoList = new ArrayList<InterventionAlternateNameDTO>();
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.SYNONYM));
        interventionAltNameDto.setName(StConverter.convertToSt("name 1"));
        interventionAltNameDtoList.add(interventionAltNameDto);
        interventionAltNameDto = new InterventionAlternateNameDTO();
        interventionAltNameDto.setNameTypeCode(StConverter.convertToSt(PAConstants.ABBREVIATION));
        interventionAltNameDto.setName(StConverter.convertToSt("name 2"));
        interventionAltNameDtoList.add(interventionAltNameDto);

        interventionDto.setStatusCode(CdConverter.convertToCd(ActiveInactiveCode.ACTIVE));
    }

    private void setupPlEcDto() {
        plannedEcDtoList = new ArrayList<PlannedEligibilityCriterionDTO>();
        plannedECDto = new PlannedEligibilityCriterionDTO();
        Ivl<Pq> ivlPq = new Ivl<Pq>();
        Pq pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        ivlPq.setLow(pq);
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(1));
        plannedECDto.setCriterionName(StConverter.convertToSt("GENDER"));
        plannedECDto.setEligibleGenderCode(CdConverter.convertStringToCd("M"));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);

        plannedECDto = new PlannedEligibilityCriterionDTO();
        plannedECDto.setCriterionName(StConverter.convertToSt("AGE"));
        ivlPq = new Ivl<Pq>();
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(1L));
        pq.setUnit("some unit");
        ivlPq.setLow(pq);
        pq = new Pq();
        pq.setValue(BigDecimal.valueOf(999L));
        ivlPq.setHigh(pq);
        plannedECDto.setValue(ivlPq);
        plannedECDto.setDisplayOrder(IntConverter.convertToInt(2));
        plannedECDto.setTextDescription(StConverter.convertToSt("some description"));
        plannedECDto.setInclusionIndicator(BlConverter.convertToBl(true));
        plannedECDto.setOperator(StConverter.convertToSt("+"));
        plannedECDto.setCategoryCode(CdConverter.convertToCd(ActivityCategoryCode.COURSE));
        plannedEcDtoList.add(plannedECDto);
    }

    private void setupSsConDto(DSet<Tel> telAd) {
        studySiteContactDtoList = new ArrayList<StudySiteContactDTO>();
        studySiteContactDto = new StudySiteContactDTO();
        studySiteContactDto.setRoleCode(CdConverter.convertStringToCd(StudySiteContactRoleCode.PRIMARY_CONTACT.getCode()));
        studySiteContactDto.setTelecomAddresses(telAd);
        studySiteContactDto.setClinicalResearchStaffIi(spId);
        studySiteContactDto.setOrganizationalContactIi(spId);
        studySiteContactDtoList.add(studySiteContactDto);

        studySiteContactDto = new StudySiteContactDTO();
        studySiteContactDto.setRoleCode(CdConverter
                .convertStringToCd(StudySiteContactRoleCode.COORDINATING_INVESTIGATOR.getCode()));
        studySiteContactDto.setTelecomAddresses(telAd);
        studySiteContactDto.setOrganizationalContactIi(spId);
        studySiteContactDtoList.add(studySiteContactDto);
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
        plannedActDtoList = new ArrayList<PlannedActivityDTO>();
        plannedActDto = new PlannedActivityDTO();
        plannedActDto.setCategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
        plannedActDto.setSubcategoryCode(CdConverter.convertStringToCd(ActivityCategoryCode.INTERVENTION.getCode()));
        plannedActDtoList.add(plannedActDto);
    }

    private void setupArmDto() {
        armDto = new ArmDTO();
        armDto.setName(StConverter.convertToSt("some name"));
        armDto.setDescriptionText(StConverter.convertToSt("some description"));
        armDto.setTypeCode(CdConverter.convertToCd(ArmTypeCode.EXPERIMENTAL));
        armDtoList = new ArrayList<ArmDTO>();
        armDtoList.add(armDto);
    }

    private void setupSdDto() {
        studyDiseaseDtoList = new ArrayList<StudyDiseaseDTO>();
        studyDiseaseDto = new StudyDiseaseDTO();
        studyDiseaseDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        studyDiseaseDto.setDiseaseIdentifier(spId);
        studyDiseaseDtoList.add(studyDiseaseDto);

        studyDiseaseDto = new StudyDiseaseDTO();
        studyDiseaseDto.setCtGovXmlIndicator(BlConverter.convertToBl(true));
        studyDiseaseDtoList.add(studyDiseaseDto);
    }

    private void setupSomDto() {
        studyOutcomeMeasureDtoList = new ArrayList<StudyOutcomeMeasureDTO>();
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("some name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setPrimaryIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
        studyOutcomeMeasureDto = new StudyOutcomeMeasureDTO();
        studyOutcomeMeasureDto.setName(StConverter.convertToSt("some name"));
        studyOutcomeMeasureDto.setSafetyIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setPrimaryIndicator(BlConverter.convertToBl(true));
        studyOutcomeMeasureDto.setTimeFrame(StConverter.convertToSt("some time"));
        studyOutcomeMeasureDto.setPrimaryIndicator(BlConverter.convertToBl(false));
        studyOutcomeMeasureDtoList.add(studyOutcomeMeasureDto);
    }

    private void setupIspDto() {
        interventionalSPDto = new InterventionalStudyProtocolDTO();
        List<Cd> blindingRoles = new ArrayList<Cd>();
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.CAREGIVER.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.INVESTIGATOR.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.OUTCOMES_ASSESSOR.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd(BlindingRoleCode.SUBJECT.getCode()));
        blindingRoles.add(CdConverter.convertStringToCd("some unknown code"));
        interventionalSPDto.setBlindedRoleCode(DSetConverter.convertCdListToDSet(blindingRoles));
        interventionalSPDto.setPrimaryPurposeCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setPhaseCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setDesignConfigurationCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setNumberOfInterventionGroups(IntConverter.convertToInt(0));
        interventionalSPDto.setBlindingSchemaCode(CdConverter.convertStringToCd(""));
        interventionalSPDto.setAllocationCode(CdConverter.convertStringToCd(""));
        Ivl<Int> ivlint = new Ivl<Int>();
        ivlint.setLow(IntConverter.convertToInt(0));
        interventionalSPDto.setTargetAccrualNumber(ivlint);
    }

    private void setupSraDto() {
        studyRegulatoryAuthDto = new StudyRegulatoryAuthorityDTO();
        studyRegulatoryAuthDto.setRegulatoryAuthorityIdentifier(spId);
    }

    private void setupPerson() {
        person = new Person();
        person.setFirstName("first name");
        person.setLastName("last Name");
    }

    private DSet<Tel> setupScDto() throws URISyntaxException {
        studyContactDto = new StudyContactDTO();
        studyContactDto.setRoleCode(CdConverter.convertStringToCd(StudyContactRoleCode.STUDY_PRINCIPAL_INVESTIGATOR.getCode()));
        studyContactDto.setClinicalResearchStaffIi(spId);
        studyContactDto.setOrganizationalContactIi(spId);
        studyContactDto.setHealthCareProviderIi(spId);
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
        studyContactDto.setTelecomAddresses(telAd);
        scDtoList = new ArrayList<StudyContactDTO>();
        scDtoList.add(studyContactDto);
        return telAd;
    }

    private void setupStudyResDtos() {
        studyResourcingDtoList = new ArrayList<StudyResourcingDTO>();
        StudyResourcingDTO studyResDto = new StudyResourcingDTO();
        Cd cd = new Cd();
        cd.setCode("U10");
        studyResDto.setFundingMechanismCode(cd);
        cd = new Cd();
        cd.setCode("CA");
        studyResDto.setNihInstitutionCode(cd);
        studyResDto.setSerialNumber(StConverter.convertToSt("SR_SER"));
        studyResourcingDtoList.add(studyResDto);
    }

    private void setupOrg() {
        orgList = new ArrayList<Organization>();
        org = new Organization();
        org.setName("some name");
        orgList.add(org);
    }

    private void setupSIndDto() {
        studySiteIndIdeDto = new StudyIndldeDTO();
        studySiteIndIdeDto.setExpandedAccessIndicator(BlConverter.convertToBl(true));
        studySiteIndIdeDtoList = new ArrayList<StudyIndldeDTO>();
        studySiteIndIdeDtoList.add(studySiteIndIdeDto);
    }

    private void setupRegUser() {
        regUser = new RegistryUser();
        regUser.setPrsOrgName("prs Org Name");
    }

    private void setupSsDto() {
        studySiteDto = new StudySiteDTO();
        studySiteDto.setReviewBoardApprovalStatusCode(CdConverter
                .convertStringToCd(ReviewBoardApprovalStatusCode.SUBMITTED_APPROVED.getCode()));
        studySiteDtoList = new ArrayList<StudySiteDTO>();
        studySiteDto.setHealthcareFacilityIi(spId);
        studySiteDto.setResearchOrganizationIi(spId);
        studySiteDto.setStatusCode(CdConverter.convertToCd(StructuralRoleStatusCode.SUSPENDED));
        studySiteDto.setFunctionalCode(CdConverter.convertToCd(StudySiteFunctionalCode.SPONSOR));
        studySiteDtoList.add(studySiteDto);
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
        spDto.setRecordVerificationDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setAccrualReportingMethodCode(CdConverter.convertToCd(AccrualReportingMethodCode.ABBREVIATED));
        spDto.setStartDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setStartDateTypeCode(CdConverter.convertStringToCd(""));
        spDto.setPrimaryCompletionDate(TsConverter.convertToTs(new Timestamp(0)));
        spDto.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(""));
        spDto.setPublicDescription(StConverter.convertToSt("public description"));


        DSet<Ii> secondaryIdentifiers = new DSet<Ii>();
        Ii assignedId = new Ii();
        assignedId.setRoot(IiConverter.STUDY_PROTOCOL_ROOT);
        assignedId.setExtension("NCI_2010_0001");
        Set<Ii> iis = new HashSet<Ii>();
        iis.add(assignedId);
        secondaryIdentifiers.setItem(iis);
        spDto.setSecondaryIdentifiers(secondaryIdentifiers);
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
        setupStudyResSvc();
        setupDocSvc();

        setupPaRegistry();

    }

    private void setupPaRegistry() throws PAException {
        ServiceLocator paRegSvcLoc = mock(ServiceLocator.class);

        OrganizationCorrelationServiceRemote orgCorSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgCorSvc.getOrganizationByFunctionRole(any(Ii.class), any(Cd.class))).thenReturn(org);

        PAOrganizationServiceRemote paOrgSvc = mock(PAOrganizationServiceRemote.class);

        CTGovXmlGeneratorServiceRemote ctgovXmlSvc = mock(CTGovXmlGeneratorServiceRemote.class);
        when(ctgovXmlSvc.generateCTGovXml(any(Ii.class))).thenReturn("<pdq></pdq>");

        when(paRegSvcLoc.getOrganizationCorrelationService()).thenReturn(orgCorSvc);
        when(paRegSvcLoc.getPAOrganizationService()).thenReturn(paOrgSvc);
        when(paRegSvcLoc.getStudyProtocolService()).thenReturn(spSvc);
        when(paRegSvcLoc.getCTGovXmlGeneratorService()).thenReturn(ctgovXmlSvc);

        PaRegistry.getInstance().setServiceLocator(paRegSvcLoc);
    }

    private void setupDocSvc() throws PAException {
        documentSvc = mock(DocumentServiceLocal.class);
        when(documentSvc.getDocumentsByStudyProtocol(any(Ii.class))).thenReturn(null);
    }

    private void setupPoSvc() throws NullifiedEntityException, PAException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        poOrgSvc = mock(OrganizationEntityServiceRemote.class);
        when(poOrgSvc.getOrganization(any(Ii.class))).thenReturn(orgDto);
        when(poSvcLoc.getOrganizationEntityService()).thenReturn(poOrgSvc);
    }

    private void setupStudyResSvc() throws PAException {
        studyResourcingSvc = mock(StudyResourcingServiceLocal.class);
        when(studyResourcingSvc.getStudyResourcingByStudyProtocol(any(Ii.class))).thenReturn(studyResourcingDtoList);
    }

    private void setupInterSvc() throws PAException {
        interventionSvc = mock(InterventionServiceLocal.class);
        when(interventionSvc.get(any(Ii.class))).thenReturn(interventionDto);

        interventionAltNameSvc = mock(InterventionAlternateNameServiceRemote.class);
        when(interventionAltNameSvc.getByIntervention(any(Ii.class))).thenReturn(interventionAltNameDtoList);
    }

    private void setupSsconSvc() throws PAException {
        studySiteContactSvc = mock(StudySiteContactServiceLocal.class);
        when(studySiteContactSvc.getByStudySite(any(Ii.class))).thenReturn(studySiteContactDtoList);
        when(studySiteContactSvc.getByStudyProtocol(any(Ii.class), any(StudySiteContactDTO.class))).thenReturn(studySiteContactDtoList);
    }

    private void setupSsasSvc() throws PAException {
        studySiteAccrualStatusSvc = mock(StudySiteAccrualStatusServiceLocal.class);
        when(studySiteAccrualStatusSvc.getCurrentStudySiteAccrualStatusByStudySite(any(Ii.class))).thenReturn(studyStieAccrualStatusDto);
    }

    private void setupDisSvc() throws PAException {
        diseaseSvc = mock(DiseaseServiceLocal.class);
        when(diseaseSvc.get(any(Ii.class))).thenReturn(diseaseDto);
    }

    private void setupDwsSvc() throws PAException {
        dwsSvc = mock(DocumentWorkflowStatusServiceLocal.class);
        when(dwsSvc.getByStudyProtocol(any(Ii.class))).thenReturn(dwsDtoList);
        when(dwsSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(dwsDto);
    }

    private void setupPlActSvc() throws PAException {
        plannedActSvc = mock(PlannedActivityServiceLocal.class);
        when(plannedActSvc.getByStudyProtocol(any(Ii.class))).thenReturn(plannedActDtoList);
        when(plannedActSvc.getPlannedEligibilityCriterionByStudyProtocol(any(Ii.class))).thenReturn(plannedEcDtoList);
    }

    private void setupArmSvc() throws PAException {
        armSvc = mock(ArmServiceLocal.class);
        when(armSvc.getByStudyProtocol(any(Ii.class))).thenReturn(armDtoList);
        when(armSvc.getByPlannedActivity(any(Ii.class))).thenReturn(armDtoList);
    }

    private void setupSdSvc() throws PAException {
        studyDiseaseSvc = mock(StudyDiseaseServiceLocal.class);
        when(studyDiseaseSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyDiseaseDtoList);
    }

    private void setupSomSvc() throws PAException {
        studyOutcomeMeasureSvc = mock(StudyOutcomeMeasureServiceLocal.class);
        when(studyOutcomeMeasureSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studyOutcomeMeasureDtoList);
    }

    private void setupSrsSvc() throws PAException {
        studyRecruitmentStatusSvc = mock(StudyRecruitmentStatusServiceLocal.class);
        when(studyRecruitmentStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyRecruitmentStatusDto);
    }

    private void setupSosSvc() throws PAException {
        studyOverallStatusSvc = mock(StudyOverallStatusServiceLocal.class);
        when(studyOverallStatusSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyOverallStatusDto);
    }

    private void setupRiMock() throws PAException {
        regulInfoSvc = mock(RegulatoryInformationServiceRemote.class);
        when(regulInfoSvc.get(anyLong())).thenReturn(ra);
        when(regulInfoSvc.getRegulatoryAuthorityCountry(anyLong())).thenReturn(country);
    }

    private void setupSraMock() throws PAException {
        studyRegAuthSvc = mock(StudyRegulatoryAuthorityServiceLocal.class);
        when(studyRegAuthSvc.getCurrentByStudyProtocol(any(Ii.class))).thenReturn(studyRegulatoryAuthDto);
    }

    private void setupCorSvcMock() throws PAException, NullifiedRoleException {
        corUtils = mock(CorrelationUtils.class);
        when(corUtils.getPAPersonByIi(any(Ii.class))).thenReturn(person);
        when(corUtils.getContactByPAOrganizationalContactId(anyLong())).thenReturn(paContactDto);
        when(corUtils.getPAOrganizationByIi(any(Ii.class))).thenReturn(org);
    }

    private void setupScSvcMock() throws PAException {
        studyContactSvc = mock(StudyContactServiceLocal.class);
        when(studyContactSvc.getByStudyProtocol(any(Ii.class), any(StudyContactDTO.class))).thenReturn(scDtoList);
    }

    private void setupOrgSvcMock() throws PAException {
        orgSvc = mock(OrganizationCorrelationServiceRemote.class);
        when(orgSvc.getOrganizationByFunctionRole(any(Ii.class), any(Cd.class))).thenReturn(org);
        when(orgSvc.getOrganizationByStudySite(anyLong(), any(StudySiteFunctionalCode.class))).thenReturn(orgList);
    }

    private void setupSIndSvcMock() throws PAException {
        studyIndIdeSvc = mock(StudyIndldeServiceLocal.class);
        when(studyIndIdeSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studySiteIndIdeDtoList);
    }

    private void setupRegSvcMock() throws PAException {
        regUserSvc = mock(RegistryUserServiceLocal.class);
        when(regUserSvc.getUser(anyString())).thenReturn(regUser);
    }

    private void setupSsSvcMock() throws PAException {
        studySiteSvc = mock(StudySiteServiceLocal.class);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class))).thenReturn(studySiteDtoList);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(StudySiteDTO.class))).thenReturn(studySiteDtoList);
        when(studySiteSvc.getByStudyProtocol(any(Ii.class), any(List.class))).thenReturn(studySiteDtoList);
    }

    private void setupSpSvcMock() throws PAException {
        spSvc = mock(StudyProtocolServiceLocal.class);
        when(spSvc.getStudyProtocol((Ii)anyObject())).thenReturn(spDto);
        when(spSvc.getInterventionalStudyProtocol((Ii)anyObject())).thenReturn(interventionalSPDto);
        when(spSvc.getObservationalStudyProtocol(any(Ii.class))).thenReturn(observationalSPDto);
        when(spSvc.getCollaborativeTrials()).thenReturn(Arrays.asList(spDto));
    }

}
