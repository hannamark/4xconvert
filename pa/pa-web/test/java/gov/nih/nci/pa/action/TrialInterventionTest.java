package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.Constants;

import org.junit.Before;
import org.junit.Test;

public class TrialInterventionTest extends AbstractPaActionTest {

	TrialInterventionsAction  trialIntervention;
	@Before
    public void prepare() throws Exception {
		trialIntervention = new TrialInterventionsAction();
		getSession().setAttribute(Constants.STUDY_PROTOCOL_II, IiConverter.convertToIi(1L));
		trialIntervention.prepare();		      
     }
	@Test
	public void testAddWithErrors() throws PAException{
		assertEquals(trialIntervention.add(),"edit");
	}
	@Test
	public void testAddSubstance() throws PAException{
		trialIntervention.setInterventionType("drug");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setMinDoseValue("2");
		trialIntervention.setMaxDoseValue("4");
		trialIntervention.setMinDoseTotalValue("8");
		trialIntervention.setMaxDoseTotalValue("10");
		trialIntervention.setDoseUOM("ml");
		trialIntervention.setDoseTotalUOM("ml");		
		assertEquals(trialIntervention.add(),"list");
	}
	@Test
	public void testAddProcedure() throws PAException{
		trialIntervention.setInterventionType("Procedure/Surgery");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setInterventionDescription("test");
		trialIntervention.setTargetSite("chest");
		trialIntervention.setProcedureName("test");
		assertEquals(trialIntervention.add(),"list");
	}
	@Test
	public void testAdd() throws PAException{
		trialIntervention.setInterventionType("Device");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setInterventionDescription("test");
		assertEquals(trialIntervention.add(),"list");
	}
	@Test
	public void testUpdateWithErrors() throws PAException {
		assertEquals(trialIntervention.update(),"edit");
	}
	@Test
	public void testUpdateSubstance() throws PAException{
		trialIntervention.setSelectedRowIdentifier("1");
		trialIntervention.setInterventionType("Drug");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setMinDoseValue("2");
		trialIntervention.setMaxDoseValue("4");
		trialIntervention.setMinDoseTotalValue("8");
		trialIntervention.setMaxDoseTotalValue("10");
		trialIntervention.setDoseUOM("ml");
		trialIntervention.setDoseTotalUOM("ml");		
		assertEquals(trialIntervention.update(),"list");		
	}
	@Test
	public void testUpdateProcedure() throws PAException{
		trialIntervention.setSelectedRowIdentifier("1");
		trialIntervention.setInterventionType("Procedure/Surgery");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setInterventionDescription("test");
		trialIntervention.setTargetSite("chest");
		trialIntervention.setProcedureName("test");
		assertEquals(trialIntervention.add(),"list");
	}
	@Test
	public void testUpdate() throws PAException{
		trialIntervention.setSelectedRowIdentifier("1");
		trialIntervention.setInterventionType("Device");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setInterventionDescription("test");
		assertEquals(trialIntervention.add(),"list");
	}
	
	@Test
	public void testDelete() throws PAException{
		trialIntervention.setSelectedRowIdentifier("1");
		assertEquals(trialIntervention.delete(),"list");
	}
	@Test
	public void testDisplay() throws PAException{
		getRequest().setupAddParameter("interventionId", "1");
		trialIntervention.setInterventionIdentifier("1");
		assertEquals(trialIntervention.display(),"edit");
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeDoseForm() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "DoseForm");
		getRequest().setupAddParameter("divName", "DoseForm");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeDoseFrequency() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "DoseFrequency");
		getRequest().setupAddParameter("divName", "DoseFrequency");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeRouteOfAdministration() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "RouteOfAdministration");
		getRequest().setupAddParameter("divName", "RouteOfAdministration");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeMethodCode() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "MethodCode");
		getRequest().setupAddParameter("divName", "MethodCode");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeDoseUnitOfMeasurement() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "UnitOfMeasurement");
		getRequest().setupAddParameter("divName", "loadDoseUOM");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeDoseDurationUnitOfMeasurement() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "UnitOfMeasurement");
		getRequest().setupAddParameter("divName", "loadDoseDurationUOM");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeTotalDoseUnitOfMeasurement() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "UnitOfMeasurement");
		getRequest().setupAddParameter("divName", "loadTotalDoseUOM");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeTargetSite() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "TargetSite");
		getRequest().setupAddParameter("divName", "TargetSite");
		trialIntervention.displaySelectedType();
	}
	@Test(expected=Exception.class)
	public void testDisplaySelectedTypeApproachSite() throws PAException{
		getRequest().setupAddParameter("id", "1");
		getRequest().setupAddParameter("className", "TargetSite");
		getRequest().setupAddParameter("divName", "ApproachSite");
		trialIntervention.displaySelectedType();
	}
	@Test
	public void testDisplaySubPage() throws PAException{
		getRequest().setupAddParameter("interventionId", "1");
		getRequest().setupAddParameter("interventionName", "Aspirin");
		getRequest().setupAddParameter("interventionOtherNames", "baby aspirin");
		getRequest().setupAddParameter("interventionType", "Drug");
		assertEquals("edit",trialIntervention.displaySubPage());
	}
	@Test
	public void testGenerate() throws PAException{
		trialIntervention.setInterventionType("Drug");
		trialIntervention.setInterventionIdentifier("1");
		trialIntervention.setMinDoseValue("2");
		trialIntervention.setMaxDoseValue("4");
		trialIntervention.setMinDoseTotalValue("8");
		trialIntervention.setMaxDoseTotalValue("10");
		trialIntervention.setDoseUOM("ml");
		trialIntervention.setDoseTotalUOM("ml");		
		assertEquals(trialIntervention.generate(),"edit");
		assertNotNull(trialIntervention.getInterventionDescription());
	}
}
