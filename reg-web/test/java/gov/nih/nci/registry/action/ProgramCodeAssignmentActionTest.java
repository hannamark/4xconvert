package gov.nih.nci.registry.action;

import gov.nih.nci.pa.domain.RegistryUser;
import gov.nih.nci.pa.dto.FamilyDTO;
import gov.nih.nci.pa.dto.ProgramCodeDTO;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.StudyStatusCode;
import gov.nih.nci.pa.service.util.FamilyProgramCodeService;
import gov.nih.nci.pa.service.util.ProtocolQueryServiceLocal;
import gov.nih.nci.pa.service.util.RegistryUserServiceLocal;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StreamResult;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_ALTERNATE_TITLES;
import static gov.nih.nci.pa.service.util.ProtocolQueryPerformanceHints.SKIP_LAST_UPDATER_INFO;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Will test ProgramCodeAssignmentAction
 */
public class ProgramCodeAssignmentActionTest  extends AbstractRegWebTest {
    private ProgramCodeAssignmentAction action;
    private FamilyProgramCodeService familyProgramCodeService;
    private ProtocolQueryServiceLocal protocolQueryService;
    private RegistryUserServiceLocal registryUserService;

    @Before
    public void init() throws Exception {
        action = new ProgramCodeAssignmentAction();

        familyProgramCodeService = mock(FamilyProgramCodeService.class);
        protocolQueryService = mock(ProtocolQueryServiceLocal.class);
        registryUserService = mock(RegistryUserServiceLocal.class);

        action.setRegistryUserService(registryUserService);
        action.setFamilyProgramCodeService(familyProgramCodeService);
        action.setProtocolQueryService(protocolQueryService);
        RegistryUser user = new RegistryUser();
        user.setAffiliatedOrganizationId(1L);
        when(registryUserService.getUser(anyString())).thenReturn(user);

        FamilyDTO f = new FamilyDTO(1L);
        f.setName("FamilyDTO-1");
        when(familyProgramCodeService.getFamilyDTOByPoId(1L)).thenReturn(f);


        when(protocolQueryService.getStudyProtocolByCriteria(any(StudyProtocolQueryCriteria.class),
            eq(SKIP_ALTERNATE_TITLES), eq(SKIP_LAST_UPDATER_INFO))).thenReturn(createTrials());
        ServletActionContext.getRequest().getSession().setAttribute("isSiteAdmin", true);
    }

    @Test
    public void testExecute() {
       assertEquals("success", action.execute());
    }
    @Test
    public void testFindTrials() {
      try {

          Field field = StreamResult.class.getDeclaredField("inputStream");
          ReflectionUtils.makeAccessible(field);

          //when I reset the family
          StreamResult sr = action.findTrials();

          InputStream is = (InputStream) field.get(sr);
          String json = IOUtils.toString(is);
          assertEquals("{\"data\":[]}", json);

          //When I reset to a valid family
          action.setFamilyPoId(1L);

          sr = action.findTrials();

          //then I must see no fields
          is = (InputStream) field.get(sr);
          json = IOUtils.toString(is);
          assertEquals("{\"data\":[{\"title\":\"title\",\"identifiers\":\"1, 2\"," +
                  "\"programCodes\":[null,null],\"trialStatus\":\"Active\"," +
                  "\"piFullName\":\"Joel Joseph\",\"nciIdentifier\":\"1\"}]}", json);


      } catch (Exception e) {
          e.printStackTrace();
          fail("should not throw exception");
      }
    }

    @Test
    public void testChangeFamily() {

         //When I send null familyPoId
         action.setFamilyPoId(null);
         //Then I should see success page
         assertEquals("success", action.changeFamily());


        //When I send 1 as familyPoId
        action.setFamilyPoId(1L);
        //Then I should see success page
        assertEquals("success", action.changeFamily());

        //when I do not have privilege and issue change family
        ServletActionContext.getRequest().getSession().removeAttribute("isSiteAdmin");
        //then I should be redirected to error page.
        assertEquals("error", action.changeFamily());

        //Also when I am not a siteAdmin
        ServletActionContext.getRequest().getSession().setAttribute("isSiteAdmin", false);
        //then I should be redirected to error page.
        assertEquals("error", action.changeFamily());
    }

    private List<StudyProtocolQueryDTO> createTrials() {
        List<StudyProtocolQueryDTO> trials = new ArrayList<StudyProtocolQueryDTO>();

        StudyProtocolQueryDTO trial = new StudyProtocolQueryDTO();
        trial.setNciIdentifier("1");
        trial.setNctIdentifier("2");
        trial.setOfficialTitle("title");
        trial.setPiFullName("Joel Joseph");
        ProgramCodeDTO pg1 = new ProgramCodeDTO();
        pg1.setProgramName("3");
        pg1.setId(3L);
        ProgramCodeDTO pg2 = new ProgramCodeDTO();
        pg2.setProgramName("4");
        pg2.setId(4L);
        trial.setProgramCodes(Arrays.asList(pg1, pg2));
        trial.setStudyStatusCode(StudyStatusCode.ACTIVE);
        trials.add(trial);

        return trials;
    }
}
