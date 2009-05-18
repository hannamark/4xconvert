/**
 * 
 */
package gov.nih.nci.registry.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.registry.dto.TrialDTO;
import gov.nih.nci.registry.dto.TrialDocumentWebDTO;
import gov.nih.nci.registry.dto.TrialFundingWebDTO;
import gov.nih.nci.registry.dto.TrialIndIdeDTO;
import gov.nih.nci.registry.test.util.RegistrationMockServiceLocator;
import gov.nih.nci.registry.util.RegistryServiceLocator;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;

import com.mockrunner.mock.web.MockHttpServletRequest;
import com.mockrunner.mock.web.MockHttpSession;

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
    }
    /**
     * Initialize the mock request.
     */
    @Before
    public void initMockrequest() {
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

}
