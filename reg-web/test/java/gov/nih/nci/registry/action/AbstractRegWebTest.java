/**
 * 
 */
package gov.nih.nci.registry.action;

import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.domain.HealthCareFacility;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.OrganizationalContact;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.domain.ResearchOrganization;
import gov.nih.nci.pa.enums.EntityStatusCode;
import gov.nih.nci.pa.enums.StructuralRoleStatusCode;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.registry.dto.ProprietaryTrialDTO;
import gov.nih.nci.registry.dto.StudyProtocolBatchDTO;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.test.util.MockPoServiceLocator;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationManager;
import com.opensymphony.xwork2.config.providers.XWorkConfigurationProvider;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;

/**
 * @author Vrushali
 *
 */
public abstract class AbstractRegWebTest {
    /**
     * Set up services.
     */
    @Before
    public void setUpServices() {
        RegistryServiceLocator.getInstance().setServiceLocator(new RegistrationMockServiceLocator());
        PoRegistry.getInstance().setPoServiceLocator(new MockPoServiceLocator());
    }
    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockrequest() {
    	ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.addContainerProvider(new XWorkConfigurationProvider());
        Configuration config = configurationManager.getConfiguration();
        Container container = config.getContainer();

        ValueStack stack = container.getInstance(ValueStackFactory.class).createValueStack();
        stack.getContext().put(ActionContext.CONTAINER, container);
        ActionContext.setContext(new ActionContext(stack.getContext()));

        assertNotNull(ActionContext.getContext());
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(new MockHttpSession());
        ServletActionContext.setRequest(request);
    }
    
    protected TrialDTO getMockTrialDTO() {
        TrialDTO trialDTO = new TrialDTO();
        trialDTO.setAmendmentDate("01/20/2009");
        trialDTO.setLocalAmendmentNumber("localAmendmentNumber");
        trialDTO.setAssignedIdentifier("assignedIdentifier");
        trialDTO.setLocalProtocolIdentifier("localProtocolIdentifier");
        trialDTO.setOfficialTitle("officialTitle");
        trialDTO.setPhaseCode("II");
        trialDTO.setPrimaryPurposeCode("TREATMENT");
        trialDTO.setTrialType("Interventional");
        trialDTO.setLeadOrganizationIdentifier("1");
        trialDTO.setPiIdentifier("2");
        trialDTO.setSponsorIdentifier("3");
        trialDTO.setResponsiblePartyType("pi");
        trialDTO.setIdentifier("1");
        trialDTO.setContactEmail("contactEmail@mail.com");
        trialDTO.setContactPhone("contact Phone ");
        trialDTO.setStatusDate("01/20/2008");
        trialDTO.setStatusCode("Active");
        trialDTO.setCompletionDateType("Anticipated");
        trialDTO.setCompletionDate("01/20/2010");
        trialDTO.setStartDateType("Actual");
        trialDTO.setStartDate("01/20/2008");
        trialDTO.setReason("");
        trialDTO.setSummaryFourOrgIdentifier("");
        trialDTO.setNctIdentifier("nctIdentifier");
        trialDTO.setLeadOrganizationName("leadOrganizationName");
        trialDTO.setPiName("piName");
        trialDTO.setSponsorName("sponsorName");
        return trialDTO;
    }
    protected List<TrialFundingWebDTO> getfundingDtos(){
        TrialFundingWebDTO grantDto = new TrialFundingWebDTO();
        grantDto.setFundingMechanismCode("B09");
        grantDto.setNihInstitutionCode("AG");
        grantDto.setNciDivisionProgramCode("CCR");
        grantDto.setSerialNumber("123456");
        grantDto.setId("1");
        grantDto.setRowId("1");
        List<TrialFundingWebDTO> fundingDtos = new ArrayList<TrialFundingWebDTO>();
        fundingDtos.add(grantDto);
        return fundingDtos;
    }
    protected List<TrialDocumentWebDTO> getDocumentDtos(){
        TrialDocumentWebDTO dto = new TrialDocumentWebDTO();
        dto.setFileName("fileName");
        dto.setTypeCode("typeCode");
        byte[] content = new byte[10];;
        dto.setText(content);
        List<TrialDocumentWebDTO> returnList =  new ArrayList<TrialDocumentWebDTO>();
        returnList.add(dto);
        return returnList;
    }
    protected List<TrialIndIdeDTO> getIndDtos() {
        List<TrialIndIdeDTO> indDtos = new ArrayList<TrialIndIdeDTO>();
        TrialIndIdeDTO indDto = new TrialIndIdeDTO();
        indDto.setIndIde("IND");
        indDto.setNumber("Ind no");
        indDto.setGrantor("CDER");
        indDto.setHolderType("Investigator");
        indDto.setExpandedAccess("false");
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDto.setExpandedAccessType("expandedAccessType");
        indDto.setProgramCode("programCode");
        indDto.setRowId("1");
        indDto.setIndIdeId("1");
        indDtos.add(indDto);
        return indDtos;
    }
    protected void deleteCreatedFolder() throws PAException{
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StringBuffer sbFolderPath = new StringBuffer(folderPath);
        File uploadedFolder = new File(sbFolderPath.toString());
        if (!uploadedFolder.exists()) {
            return;
          }
          String[] childrenFolder = uploadedFolder.list();
          for (int i = 0; i < childrenFolder.length; i++) {
            File currentFolder = new File(sbFolderPath + File.separator + childrenFolder[i]);
            if (!currentFolder.isDirectory()) { // skip ., .., other directories, etc.
              continue;
            }
            if (currentFolder.getName().startsWith("orgName")) { // name match
                System.out.println("removing " + currentFolder.getPath());
                if (!deleteDir(currentFolder))
                    System.err.println("Couldn't remove " + currentFolder.getPath());
                }
            }
    }
    /** Deletes all files and sub directories under dir.
     Returns true if all deletions were successful.
     If a deletion fails, the method stops attempting to delete and returns false.
     **/
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    } 
    protected ProprietaryTrialDTO getMockProprietaryTrialDTO() {
        ProprietaryTrialDTO trialDTO = new ProprietaryTrialDTO();
        trialDTO.setAssignedIdentifier("assignedIdentifier");
        trialDTO.setOfficialTitle("officialTitle");
        trialDTO.setPhaseCode("II");
        trialDTO.setPrimaryPurposeCode("TREATMENT");
        trialDTO.setTrialType("Interventional");
        trialDTO.setLeadOrganizationIdentifier("1");
        trialDTO.setLeadOrganizationName("leadOrganizationName");
        trialDTO.setLeadOrgTrialIdentifier("leadOrgTrialIdentifier");
        trialDTO.setIdentifier("1");
        trialDTO.setSummaryFourOrgIdentifier("");
        trialDTO.setNctIdentifier("nctIdentifier");

        trialDTO.setSiteStatusCode("Not yet recruiting");
        trialDTO.setSiteStatusDate("12/09/2009");
        trialDTO.setSiteOrganizationIdentifier("siteOrganizationIdentifier");
        trialDTO.setSiteOrganizationName("siteOrganizationName");
        trialDTO.setSitePiIdentifier("sitePiIdentifier");
        trialDTO.setSitePiName("sitePiName");
        trialDTO.setSiteProgramCodeText("siteProgramCodeTxt");
        trialDTO.setLocalSiteIdentifier("localSiteIdentifier");
        return trialDTO;
    }
    /**
     * 
     * @return StudyProtocolBatchDTO
     */
    protected StudyProtocolBatchDTO getBatchDto() {
        StudyProtocolBatchDTO  dto = new StudyProtocolBatchDTO ();
        dto.setTrialType("Interventional");
        dto.setProtcolDocumentFileName("protcolDocumentFileName.doc");
        dto.setIrbApprovalDocumentFileName("irbApprovalDocumentFileName.doc");
        dto.setParticipatinSiteDocumentFileName("participatinSiteDocumentFileName.doc");
        dto.setInformedConsentDocumentFileName("informedConsentDocumentFileName.doc");
        dto.setOtherTrialRelDocumentFileName("otherTrialRelDocumentFileName.doc");
        dto.setProtocolHighlightDocFileName("protocolHighlightDocFileName.doc");
        dto.setChangeRequestDocFileName("changeRequestDocFileName.doc");
        dto.setSubmissionType("O");
        dto.setLeadOrgName("leadOrgName");
        dto.setLeadOrgStreetAddress("leadOrgStreetAddress");
        dto.setLeadOrgCity("leadOrgCity");
        dto.setLeadOrgState("MD");
        dto.setLeadOrgZip("leadOrgZip");
        dto.setLeadOrgCountry("USA");
        dto.setLeadOrgEmail("leadOrgE@mail.co");
        dto.setLeadOrgPhone("leadOrgPhone");
        dto.setSponsorOrgName("sponsorOrgName");
        dto.setSponsorStreetAddress("sponsorStreetAddress");
        dto.setSponsorCity("sponsorCity");
        dto.setSponsorCountry("JPN");
        dto.setSponsorZip("sponsorZip");
        dto.setSponsorEmail("sponsorE@mail.co");
        dto.setSponsorPhone("sponsorPhone");
        dto.setPiFirstName("piFirstName");
        dto.setPiLastName("piLastName");
        dto.setPiStreetAddress("piStreetAddress");
        dto.setPiCity("piCity");
        dto.setPiState("Sta");
        dto.setPiZip("piZip");
        dto.setPiCountry("AUS");
        dto.setPiEmail("piE@mail.co");
        dto.setPiPhone("piPhone");
        dto.setResponsibleParty("Sponsor");
        dto.setSponsorContactType("Personal");
        dto.setSponsorContactFName("sponsorContactFName");
        dto.setSponsorContactLName("sponsorContactLName");
        dto.setSponsorContactStreetAddress("sponsorContactStreetAddress");
        dto.setSponsorContactCity("sponsorContactCity");
        dto.setSponsorContactState("OC");
        dto.setSponsorContactCountry("CAN");
        dto.setSponsorContactZip("sponsorContactZip");
        dto.setSponsorContactEmail("sponsorContactE@mail.co");
        dto.setLocalProtocolIdentifier("localProtocolIdentifier");
        dto.setTitle("title");
        dto.setCurrentTrialStatus("Approved");
        dto.setCurrentTrialStatusDate("12/10/2009");
        dto.setStudyStartDate("12/12/2009");
        dto.setStudyStartDateType("Anticipated");
        dto.setPrimaryCompletionDate("12/12/2009");
        dto.setPrimaryCompletionDateType("Anticipated");
        dto.setPhase("II");
        dto.setPrimaryPurpose("Other");
        dto.setPrimaryPurposeOtherValueSp("primaryPurposeOtherValueSp");
        dto.setDataMonitoringCommitteeAppointedIndicator("No");
        dto.setFdaRegulatoryInformationIndicator("Yes");
        dto.setSection801Indicator("YES");
        dto.setDelayedPostingIndicator("NO");
        dto.setOversightAuthorityCountry("oversightAuthorityCountry");
        dto.setOversightOrgName("oversightOrgName");
        return dto;
    }
    /**
     * @param dto
     */
    protected void getBatchSumm4Info(StudyProtocolBatchDTO dto) {
        dto.setSumm4OrgName("summ4OrgName");
        dto.setSumm4OrgStreetAddress("summ4OrgStreetAddress");
        dto.setSumm4City("summ4City");
        dto.setSumm4State("MD");
        dto.setSumm4Country("USA");
        dto.setSumm4Email("summ4E@mail.co");
        dto.setSumm4Phone("summ4Phone");
        dto.setSumm4Zip("summ4Zip");
    }
    /**
     * @param dto
     */
    protected void getBatchIndIde(StudyProtocolBatchDTO dto) {
        dto.setIndExpandedAccessStatus("Available");
        dto.setIndGrantor("CDRH");
        dto.setIndHasExpandedAccess("YES");
        dto.setIndHolderType("Industry");
        dto.setIndNCIDivision("NCI");
        dto.setIndNIHInstitution("NIH");
        dto.setIndNumber("indNumber");
        dto.setIndType("IDE");
    }
    /**
     * @param dto
     */
    protected void getBatchMultipleIndIde(StudyProtocolBatchDTO dto) {
        dto.setIndExpandedAccessStatus("indExpandedAccessStatus1;indExpandedAccessStatus2");
        dto.setIndGrantor("indGrantor1;indGrantor2");
        dto.setIndHasExpandedAccess("indHasExpandedAccess1;indHasExpandedAccess2");
        dto.setIndHolderType("indHolderType1;indHolderType2");
        dto.setIndNCIDivision("indNCIDivision1;indNCIDivision2");
        dto.setIndNIHInstitution("indNIHInstitution1;indNIHInstitution2");
        dto.setIndNumber("indNumber1;indNumber2");
        dto.setIndType("indType1;indType2");
    }
    /**
     * @param dto
     */
    protected void getBatchGrants(StudyProtocolBatchDTO dto) {
        dto.setNihGrantFundingMechanism("nihGrantFundingMechanism");
        dto.setNihGrantInstituteCode("nihGrantInstituteCode");
        dto.setNihGrantNCIDivisionCode("nihGrantNCIDivisionCode");
        dto.setNihGrantSrNumber("12345");
    }
    /**
     * @param dto
     */
    protected void getBatchMultipleGrants(StudyProtocolBatchDTO dto) {
        dto.setNihGrantFundingMechanism("nihGrantFundingMechanism1;nihGrantFundingMechanism2");
        dto.setNihGrantInstituteCode("nihGrantInstituteCode1;nihGrantInstituteCode2");
        dto.setNihGrantNCIDivisionCode("nihGrantNCIDivisionCode1;nihGrantNCIDivisionCode2");
        dto.setNihGrantSrNumber("SrNum1;SrNum2");
    }
    /**
    *
    * @param <T> t
    * @param obj o
    */
    protected  <T> void addUpdObject(T obj) {
       Session session = HibernateUtil.getCurrentSession();
       Transaction transaction = session.beginTransaction();
       session.saveOrUpdate(obj);
       transaction.commit();
   }

   /**
    *
    * @param <T> t
    * @param oList o
    */
   protected  <T> void addUpdObjects(ArrayList<T> oList) {
       for (T obj : oList) {
           addUpdObject(obj);
       }
   }

   /**
    *
    */
   protected void primeData() {
       Organization org = new Organization();
       org.setName("Mayo University");
       org.setIdentifier("1");
       org.setUserLastUpdated("abstractor");
       java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
       org.setDateLastUpdated(now);
       org.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(org);
       HealthCareFacility hfc = new HealthCareFacility();
       hfc.setOrganization(org);
       hfc.setIdentifier("1");
       hfc.setStatusCode(StructuralRoleStatusCode.PENDING);
       addUpdObject(hfc);
       
       
       ResearchOrganization rOrg = new ResearchOrganization();
       rOrg.setOrganization(org);
       rOrg.setStatusCode(StructuralRoleStatusCode.ACTIVE);
       rOrg.setIdentifier("1");
       addUpdObject(rOrg);
       OrganizationalContact orgContact = new OrganizationalContact();
       orgContact.setIdentifier("1");
       orgContact.setOrganization(org);
       orgContact.setStatusCode(StructuralRoleStatusCode.ACTIVE);
       addUpdObject(orgContact);
       
       Person per = new Person();
       per.setFirstName("firstName");
       per.setLastName("lastName");
       per.setIdentifier("1");
       per.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(per);
       per = new Person();
       per.setFirstName("firstNameOne");
       per.setLastName("lastNameTwo");
       per.setIdentifier("2");
       per.setStatusCode(EntityStatusCode.PENDING);
       addUpdObject(per);

       Country country = new Country();
       country.setAlpha2("ZZ");
       country.setAlpha3("ZZZ");
       country.setName("Zanzibar");
       country.setNumeric("67");
       addUpdObject(country);
       HibernateUtil.getCurrentSession().clear();
   }
}
