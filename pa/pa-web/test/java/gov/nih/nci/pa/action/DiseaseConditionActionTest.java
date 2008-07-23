package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import gov.nih.nci.pa.BasePaTest;
import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaRegistry;
import gov.nih.nci.service.MockDiseaseConditionService;

public class DiseaseConditionActionTest extends BasePaTest {
    
    private DiseaseConditionAction action;
    
    @Before  
    public void setUp(){
        action = new DiseaseConditionAction();
    }
    
    @Test
    public void testGetRecords(){
        List<DiseaseConditionDTO> expected = new ArrayList<DiseaseConditionDTO>();
        assertEquals(expected.size(), action.getRecords().size());
    }    
    
    @Test
    public void test() throws PAException{
        MockDiseaseConditionService service = (MockDiseaseConditionService) PaRegistry.getInstance().getServiceLocator().getDiseaseConditionService();
        service.getDiseaseCondition(1L);
        String.valueOf(true);
    }

}
