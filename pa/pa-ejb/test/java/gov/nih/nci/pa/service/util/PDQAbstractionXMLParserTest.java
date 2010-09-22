/**
 *
 */
package gov.nih.nci.pa.service.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import gov.nih.nci.coppa.services.TooManyResultsException;
import gov.nih.nci.pa.domain.Country;
import gov.nih.nci.pa.enums.EligibleGenderCode;
import gov.nih.nci.pa.enums.RecruitmentStatusCode;
import gov.nih.nci.pa.iso.dto.ArmDTO;
import gov.nih.nci.pa.iso.dto.InterventionDTO;
import gov.nih.nci.pa.iso.dto.PlannedEligibilityCriterionDTO;
import gov.nih.nci.pa.iso.dto.StudyOutcomeMeasureDTO;
import gov.nih.nci.pa.iso.dto.StudySiteAccrualStatusDTO;
import gov.nih.nci.pa.iso.util.EnOnConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IntConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.pa.util.PoRegistry;
import gov.nih.nci.pa.util.PoServiceLocator;
import gov.nih.nci.pa.util.ServiceLocator;
import gov.nih.nci.services.correlation.IdentifiedOrganizationCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedOrganizationDTO;
import gov.nih.nci.services.correlation.IdentifiedPersonCorrelationServiceRemote;
import gov.nih.nci.services.correlation.IdentifiedPersonDTO;
import gov.nih.nci.services.entity.NullifiedEntityException;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * @author vrushali
 *
 */
public class PDQAbstractionXMLParserTest {
    private final URL testXMLUrl = this.getClass().getResource("/sample-with-location.xml");

    private PDQAbstractionXMLParser abstractionElememtParser;
    private PoServiceLocator poSvcLoc;
    private IdentifiedPersonCorrelationServiceRemote identifierPersonSvc;
    private IdentifiedOrganizationCorrelationServiceRemote identifierOrgSvc;
    private ServiceLocator paSvcLoc;
    private LookUpTableServiceRemote lookupSvc;
    @Before
    public void setup() throws PAException, NullifiedEntityException, TooManyResultsException {
        abstractionElememtParser = new PDQAbstractionXMLParser();
        setupPoSvc();
        paSvcLoc = mock (ServiceLocator.class);
        PaRegistry.getInstance().setServiceLocator(paSvcLoc);
        lookupSvc = mock (LookUpTableServiceRemote.class);
        when(paSvcLoc.getLookUpTableService()).thenReturn(lookupSvc);
        Country country = new Country();
        country.setAlpha3("USA");
        when(lookupSvc.getCountryByName(anyString())).thenReturn(country);
        PAServiceUtils paServiceUtil = mock (PAServiceUtils.class);
        abstractionElememtParser.setPaServiceUtils(paServiceUtil);
    }
    private void setupPoSvc() throws NullifiedEntityException, PAException, TooManyResultsException {
        poSvcLoc = mock(PoServiceLocator.class);
        PoRegistry.getInstance().setPoServiceLocator(poSvcLoc);
        identifierPersonSvc = mock(IdentifiedPersonCorrelationServiceRemote.class);
        identifierOrgSvc = mock(IdentifiedOrganizationCorrelationServiceRemote.class);
        when(poSvcLoc.getIdentifiedPersonEntityService()).thenReturn(identifierPersonSvc);
        when(poSvcLoc.getIdentifiedOrganizationEntityService()).thenReturn(identifierOrgSvc);
        IdentifiedPersonDTO idPersonDTO = new IdentifiedPersonDTO();
        idPersonDTO.setPlayerIdentifier(IiConverter.convertToPoPersonIi("1"));
        List<IdentifiedPersonDTO> idPerDtos = new ArrayList<IdentifiedPersonDTO>();
        idPerDtos.add(idPersonDTO);
        when(identifierPersonSvc.search(any(IdentifiedPersonDTO.class))).thenReturn(idPerDtos);
        List<IdentifiedOrganizationDTO> orgList = new ArrayList<IdentifiedOrganizationDTO>();
        IdentifiedOrganizationDTO idOrgDTO = new IdentifiedOrganizationDTO();
        idOrgDTO.setScoperIdentifier(IiConverter.convertToPoOrganizationIi("2"));
        when(identifierOrgSvc.search(any(IdentifiedOrganizationDTO.class))).thenReturn(orgList);
    }

    @Test(expected=IllegalStateException.class)
    public void testIForgotToCallSetURL() {
        abstractionElememtParser.parse();
        abstractionElememtParser.getIspDTO();
    }

    @Test
    public void testReadStudyDesign() {
        setURLAndParse();
        assertEquals(3, abstractionElememtParser.getIspDTO().getNumberOfInterventionGroups().getValue().intValue());
        assertEquals("Randomized", abstractionElememtParser.getIspDTO().getAllocationCode().getCode());
        assertEquals("3", IntConverter.convertToString(
                abstractionElememtParser.getIspDTO().getNumberOfInterventionGroups()));
        assertTrue(abstractionElememtParser.getIspDTO().getPublicTitle().getValue().startsWith(
                "Bevacizumab With or Without Interferon "));
        assertTrue(abstractionElememtParser.getIspDTO().getPublicDescription().getValue().startsWith(
                "RATIONALE: Monoclonal antibodies,"));
        assertTrue(abstractionElememtParser.getIspDTO().getScientificDescription().getValue().startsWith("OBJECTIVES:"));

        assertTrue(abstractionElememtParser.getIspDTO().getKeywordText().getValue().startsWith("stage"));
        assertEquals("10/05/2007", TsConverter.convertToString(
                abstractionElememtParser.getIspDTO().getRecordVerificationDate()));
        assertEquals(65, abstractionElememtParser.getIspDTO().getTargetAccrualNumber().getLow().getValue().intValue());


    }
    @Test
    public void testReadOutcomes() {
        setURLAndParse();
        assertEquals(2, abstractionElememtParser.getOutcomeMeasureDTOs().size());
        List <StudyOutcomeMeasureDTO> outList = abstractionElememtParser.getOutcomeMeasureDTOs();
        assertTrue(outList.get(0).getPrimaryIndicator().getValue());
    }


    @Test
    public void testReadIntervention() {
        setURLAndParse();
        assertEquals(2, abstractionElememtParser.getListOfInterventionsDTOS().size());
        Map<InterventionDTO, List<ArmDTO>> map = abstractionElememtParser.getArmInterventionMap();
        for (Iterator<InterventionDTO> iter = map.keySet().iterator(); iter.hasNext();) {
            InterventionDTO interventionDTO = (InterventionDTO) iter.next();
            assertEquals("Biological/Vaccine", interventionDTO.getTypeCode().getCode());
            List<ArmDTO> armList = map.get(interventionDTO);
            for (ArmDTO armDTO: armList){
                assertEquals("Arm I",armDTO.getName().getValue());
                break;
            }

            break;
      }
    }
    @Test
    public void testReadCondition() {
        setURLAndParse();
        assertEquals(2,abstractionElememtParser.getListOfDiseaseDTOs().size());
        assertEquals(abstractionElememtParser.getListOfDiseaseDTOs().get(0).getDiseaseCode().getValue(),"CDR0000038837");
    }

    @Test
    public void testArmGroup() {
        setURLAndParse();
        assertEquals(3,abstractionElememtParser.getListOfArmDTOS().size());
        assertEquals(abstractionElememtParser.getListOfArmDTOS().get(0).getName().getValue(),"Arm I");
        assertEquals(abstractionElememtParser.getListOfArmDTOS().get(0).getTypeCode().getCode(),"Experimental");
    }
    @Test
    public void testReadLocations() {
        setURLAndParse();
        Map<OrganizationDTO, Map<PersonDTO, StudySiteAccrualStatusDTO>> location
            = abstractionElememtParser.getLocationsMap();
        for (OrganizationDTO locOrg : location.keySet()) {
            Map<PersonDTO, StudySiteAccrualStatusDTO> valueMap = location.get(locOrg);
            String orgName = EnOnConverter.convertEnOnToString(locOrg.getName());
            assertEquals("London Regional Cancer Program at London Health Sciences Centre", orgName);
            for (PersonDTO locContact : valueMap.keySet()) {
                StudySiteAccrualStatusDTO recrutingStatus = valueMap.get(locContact);
                assertEquals(RecruitmentStatusCode.RECRUITING.getCode(), recrutingStatus.getStatusCode().getCode());
                break;
            }
            break;
        }
    }
    @Test
    public void testReadIrbInfo() {
        setURLAndParse();
        assertNull(abstractionElememtParser.getIrbOrgDTO());
    }
    @Test
    public void testReadEligibility() {
        setURLAndParse();
        PlannedEligibilityCriterionDTO  eligibleCriterionDTO = abstractionElememtParser.getEligibilityList().get(0);
        assertNotNull(eligibleCriterionDTO);
        assertEquals(EligibleGenderCode.BOTH.getCode(), eligibleCriterionDTO.getEligibleGenderCode().getCode());
        eligibleCriterionDTO = abstractionElememtParser.getEligibilityList().get(1);
        assertEquals(18,IvlConverter.convertPq().convertLow(eligibleCriterionDTO.getValue()).getValue().intValue());
        assertEquals(120,IvlConverter.convertPq().convertHigh(eligibleCriterionDTO.getValue()).getValue().intValue());
        assertTrue(abstractionElememtParser.getOtherCriterionTextBlock().startsWith("DISEASE CHARACTERISTICS:"));
        assertEquals("no", abstractionElememtParser.getHealthyVolunteers());
    }
    @Test
    public void testReadCollaborators() {
        setURLAndParse();
        assertNotNull(abstractionElememtParser.getCollaboratorOrgDTOs());
        OrganizationDTO orgDTO = abstractionElememtParser.getCollaboratorOrgDTOs().get(0);
        assertEquals("Eastern Cooperative Oncology Group", EnOnConverter.convertEnOnToString(orgDTO.getName()));
    }
    /**
     *
     */
    private void setURLAndParse() {
        abstractionElememtParser.setUrl(testXMLUrl);
        abstractionElememtParser.parse();
    }

}
