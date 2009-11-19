package gov.nih.nci.pa.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.coppa.iso.DSet;
import gov.nih.nci.coppa.iso.Tel;
import gov.nih.nci.pa.domain.Organization;
import gov.nih.nci.pa.domain.Person;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.dto.TrialDTO;
import gov.nih.nci.pa.dto.TrialDocumentDTO;
import gov.nih.nci.pa.dto.TrialFundingDTO;
import gov.nih.nci.pa.dto.TrialIndIdeDTO;
import gov.nih.nci.pa.enums.ActualAnticipatedTypeCode;
import gov.nih.nci.pa.enums.PhaseCode;
import gov.nih.nci.pa.enums.PrimaryPurposeCode;
import gov.nih.nci.pa.enums.StudySiteFunctionalCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.enums.SummaryFourFundingCategoryCode;
import gov.nih.nci.pa.iso.dto.StudyIndldeDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.dto.StudyResourcingDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.services.correlation.NullifiedRoleException;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class TrialUtilTest {

	TrialUtil trialUtil = new TrialUtil();
	StudyProtocolDTO spDTO = new StudyProtocolDTO();
	TrialDTO trialDTO = new TrialDTO();
	
	@Test
	public void testCopyStudyProtocolDTOTrialDTO() {
		spDTO.setOfficialTitle(StConverter.convertToSt("test"));
		spDTO.setAssignedIdentifier(IiConverter.convertToIi("1"));
		spDTO.setPhaseCode(CdConverter.convertStringToCd(PhaseCode.O.getCode()));
		spDTO.setPhaseOtherText(StConverter.convertToSt("test"));
		spDTO.setPrimaryPurposeCode(CdConverter.convertStringToCd(PrimaryPurposeCode.OBSERVATIONAL.getCode()));
		spDTO.setPrimaryPurposeOtherText(StConverter.convertToSt("other text"));
		spDTO.setStartDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ACTUAL.getCode()));
		spDTO.setPrimaryCompletionDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));
		spDTO.setPrimaryCompletionDateTypeCode(CdConverter.convertStringToCd(ActualAnticipatedTypeCode.ANTICIPATED.getCode()));
		spDTO.setStudyProtocolType(StConverter.convertToSt("InterventionalStudyProtocol"));
		spDTO.setIdentifier(IiConverter.convertToIi("1"));
        spDTO.setStartDate(TsConverter.convertToTs(new Timestamp(new Date().getTime())));  
		
		trialUtil.copy(spDTO, trialDTO);
		assertEquals(trialDTO.getOfficialTitle(), "test");
	}

	@Test
	public void testCopyStudyProtocolQueryDTOTrialDTO() {
		StudyProtocolQueryDTO spqDTO = new StudyProtocolQueryDTO();
		spqDTO.setLocalStudyProtocolIdentifier("1");
		spqDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
		spqDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
		trialUtil.copy(spqDTO, trialDTO);
		assertEquals(trialDTO.getLocalProtocolIdentifier(), "1");
	}

	@Test
	public void testCopyLO() {
		Organization org = new Organization();
		org.setIdentifier("1");
		org.setName("OrgName");
		trialUtil.copyLO(org, trialDTO);
		assertEquals(trialDTO.getLeadOrganizationName(), "OrgName");
	}

	@Test
	public void testCopyPI() {
		Person person = new Person();
		person.setIdentifier("1");
		person.setFirstName("Full");
		person.setLastName("Name");
		trialUtil.copyPI(person, trialDTO);
		assertEquals(trialDTO.getPiName(), "Name, Full");
	}

	@Test(expected=Exception.class)
	public void testCopyResponsibleParty() throws PAException, NullifiedRoleException {
		trialUtil.copyResponsibleParty(IiConverter.convertToIi("1"), trialDTO);
		assertNull(trialDTO.getResponsiblePersonName());
	}

	@Test
	public void testCopyDSetOfTelTrialDTO() {
		DSet<Tel> dset = null;
		trialUtil.copy(dset, trialDTO);
		assertNull(trialDTO.getContactEmail());
	}

	@Test(expected=Exception.class)
	public void testCopySponsor() throws PAException {
		trialUtil.copySponsor(IiConverter.convertToIi("1"), trialDTO);
		assertNull(trialDTO.getSponsorName());
	}

	@Test(expected=Exception.class)
	public void testCopyNctNummber() throws PAException {
		trialUtil.copyNctNummber(IiConverter.convertToIi("1"), trialDTO);
		assertNull(trialDTO.getNctIdentifier());
	}

	@Test(expected=Exception.class)
	public void testGetStudySite() throws PAException {
		assertNull(trialUtil.getStudySite(IiConverter.convertToIi("1"), StudySiteFunctionalCode.COLLABORATORS));
	}

	@Test
	public void testCopySummaryFour() throws PAException{
		StudyResourcingDTO srDTO = null;
		trialUtil.copySummaryFour(srDTO, trialDTO);		
	}
	@Test
	public void testCopyINDIDEList() throws PAException {
		List<StudyIndldeDTO> studyIndldeDTOList = null;
		trialUtil.copyINDIDEList(studyIndldeDTOList, trialDTO);
	}

	@Test
	public void testCopyGrantList() throws PAException {
		List<StudyResourcingDTO> isoGrantlist = null;
		trialUtil.copyGrantList(isoGrantlist,trialDTO);
}

	@Test(expected=Exception.class)
	public void testConvertToStudyProtocolDTO() throws PAException{
		assertNull(trialUtil.convertToStudyProtocolDTO(trialDTO));
	}

	@Test
	public void testConvertToStudyOverallStatusDTO() {
		trialDTO.setStatusCode(StudyStatusCode.ACTIVE.getCode());
		trialDTO.setReason("txt");
		trialDTO.setStatusDate(PAUtil.today());
		assertNotNull(trialUtil.convertToStudyOverallStatusDTO(trialDTO));
	}

	@Test
	public void testConvertToSummary4OrgDTO() {
		trialDTO.setSummaryFourOrgIdentifier("");
		assertNull(trialUtil.convertToSummary4OrgDTO(trialDTO));
	}

	@Test
	public void testConvertToLeadOrgDTO() {
		trialDTO.setLeadOrganizationIdentifier("1");
		assertNotNull(trialUtil.convertToLeadOrgDTO(trialDTO));
	}

	@Test
	public void testConvertToSponsorOrgDTO() {
		trialDTO.setSponsorIdentifier("1");
		assertNotNull(trialUtil.convertToSponsorOrgDTO(trialDTO));
	}

	@Test
	public void testConvertToLeadPI() {
		trialDTO.setPiIdentifier("1");
		assertNotNull(trialUtil.convertToLeadPI(trialDTO));
	}

	@Test
	public void testConvertToResponsiblePartyContactDTO() {
		trialDTO.setResponsiblePersonIdentifier("1");
		assertNotNull(trialUtil.convertToResponsiblePartyContactDTO(trialDTO));
	}

	@Test
	public void testConvertToSummary4StudyResourcingDTO() {
		trialDTO.setSummaryFourFundingCategoryCode(SummaryFourFundingCategoryCode.NATIONAL.getCode());
		assertNotNull(trialUtil.convertToSummary4StudyResourcingDTO(trialDTO));
	}

	@Test
	public void testConvertToStudySiteDTO() {
		trialDTO.setLocalProtocolIdentifier("1");
		assertNotNull(trialUtil.convertToStudySiteDTO(trialDTO));
	}

	@Test
	public void testConvertToNCTStudySiteDTO() {
		trialDTO.setNctIdentifier("1");
		assertNotNull(trialUtil.convertToNCTStudySiteDTO(trialDTO));
	}

	@Test
	public void testConvertToStudyContactDTO() {
		trialDTO.setContactEmail("a@a.org");
		assertNotNull(trialUtil.convertToStudyContactDTO(trialDTO));
	}

	@Test
	public void testConvertToStudySiteContactDTO() {
		trialDTO.setContactEmail("a@a.org");
		assertNotNull(trialUtil.convertToStudySiteContactDTO(trialDTO));
	}

	@Test
	public void testConvertToISODocumentList() {
		List<TrialDocumentDTO> docList = new ArrayList<TrialDocumentDTO>();
		TrialDocumentDTO dto = new TrialDocumentDTO();
		dto.setFileName("c:\test.doc");
		dto.setText(new byte[1]);
		dto.setTypeCode("IRB");
		docList.add(dto);
		assertNotNull(trialUtil.convertToISODocumentList(docList));
	}

	@Test(expected=IOException.class)
	public void testConvertToDocumentList() throws IOException{
		trialUtil.convertToDocumentList("IRB","c:\text.doc", new File("c:\text.doc"));
		
	}

	@Test
	public void testConvertISOINDIDEList() {
		List<TrialIndIdeDTO> indList = new ArrayList<TrialIndIdeDTO>();
		assertNull(trialUtil.convertISOINDIDEList(indList));
	}

	@Test
	public void testConvertISOGrantsList() {
		List<TrialFundingDTO> grantList = new ArrayList<TrialFundingDTO>();
		assertNull(trialUtil.convertISOGrantsList(grantList));
	}

}
