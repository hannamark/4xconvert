/**
 *
 */
package gov.nih.nci.registry.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.StudyProtocol;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.TestSchema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Vrushali
 *
 */
public class MockProtocolQueryService implements ProtocolQueryServiceLocal {
    static List<StudyProtocolQueryDTO> list;
    static {
        list = new ArrayList<StudyProtocolQueryDTO>();
        StudyProtocolQueryDTO spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(1L);
        spQueryDTO.setNciIdentifier("NCI-2009-00001");
        spQueryDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setLeadOrganizationId(1L);
        spQueryDTO.setPiId(2L);
        spQueryDTO.setIsProprietaryTrial(false);
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        spQueryDTO.setUserLastCreated("TestUser@test.com");
        spQueryDTO.setDocumentWorkflowStatusCode(DocumentWorkflowStatusCode.ABSTRACTED);
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setStudyProtocolId(2L);
        spQueryDTO.setLeadOrganizationId(2L);
        spQueryDTO.setNciIdentifier("NCI-2009-00002");
        spQueryDTO.setLocalStudyProtocolIdentifier("DupTestinglocalStudyProtocolId");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        list.add(spQueryDTO);
        spQueryDTO = new StudyProtocolQueryDTO();
        spQueryDTO.setLeadOrganizationId(3L);
        spQueryDTO.setStudyProtocolId(3L);
        spQueryDTO.setNciIdentifier("NCI-2009-00003");
        spQueryDTO.setLocalStudyProtocolIdentifier("localStudyProtocolIdentifier3");
        spQueryDTO.setOfficialTitle("officialTitle");
        spQueryDTO.setStudyStatusCode(StudyStatusCode.ACTIVE);
        spQueryDTO.setStudyStatusDate(PAUtil.dateStringToTimestamp("4/15/2009"));
        spQueryDTO.setCtgovXmlRequiredIndicator(true);
        list.add(spQueryDTO);
    }
    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByCriteria(gov.nih.nci.pa.dto.StudyProtocolQueryCriteria)
     */
    public List<StudyProtocolQueryDTO> getStudyProtocolByCriteria(
            StudyProtocolQueryCriteria sc) throws PAException {
        if(sc.getOfficialTitle() != null && sc.getOfficialTitle().equalsIgnoreCase("ThrowException")) {
            throw new PAException("Test");
        }
        List<StudyProtocolQueryDTO> returnList = new ArrayList<StudyProtocolQueryDTO>();
        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getLocalStudyProtocolIdentifier().equals(sc.getLeadOrganizationTrialIdentifier())) {
                // TODO need to check with OrgId too
                returnList.add(sp);
            }
            if(sc.getNciIdentifier() != null && !sc.getNciIdentifier().equals("")
                    && sp.getNciIdentifier().equalsIgnoreCase(sc.getNciIdentifier())) {
                returnList.add(sp);
            }
            if (sc.isMyTrialsOnly()) {
                return list;
            }
        }
        return returnList;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getTrialSummaryByStudyProtocolId(java.lang.Long)
     */
    public StudyProtocolQueryDTO getTrialSummaryByStudyProtocolId(
            Long studyProtocolId) throws PAException {

        for (StudyProtocolQueryDTO sp: list) {
            if(sp.getStudyProtocolId().equals(studyProtocolId)) {
                return sp;
            }
        }
        return null;
    }

    /* (non-Javadoc)
     * @see gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal#getStudyProtocolByOrgIdentifier(java.lang.Long)
     */
    public List<StudyProtocol> getStudyProtocolByOrgIdentifier(Long orgIdentifier) throws PAException {
        List<StudyProtocol> returnList = new ArrayList<StudyProtocol>();
        for (StudyProtocolQueryDTO spDto : list) {
            if (spDto.getLeadOrganizationId().equals(orgIdentifier)) {
                StudyProtocol sp = new StudyProtocol();
                sp.setId(spDto.getStudyProtocolId());
                Set<Ii> others = new HashSet<Ii>();
                Ii nciid = IiConverter.convertToAssignedIdentifierIi(spDto.getNciIdentifier());
                others.add(nciid);
                sp.setOtherIdentifiers(others);
                sp.setOfficialTitle(spDto.getOfficialTitle());
                sp.setProprietaryTrialIndicator(Boolean.valueOf(spDto.getIsProprietaryTrial()));
                sp.setCtgovXmlRequiredIndicator(spDto.getCtgovXmlRequiredIndicator());
                sp.setUserLastCreated(TestSchema.createUser());
                returnList.add(sp);
            }
        }
        return returnList;
    }

}
