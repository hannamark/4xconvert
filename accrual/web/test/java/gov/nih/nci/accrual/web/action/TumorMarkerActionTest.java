/**
 * 
 */
package gov.nih.nci.accrual.web.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import gov.nih.nci.accrual.web.dto.util.TumorMarkerWebDto;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.iso.Pq;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.StConverter;

import org.junit.Before;
import org.junit.Test;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Kalpana Guthikonda
 *
 */
public class TumorMarkerActionTest extends AbstractAccrualActionTest{
    TumorMarkerAction action;
    TumorMarkerWebDto  tumorMarker ;


        @Before
        public void initAction() throws Exception {
            action = new TumorMarkerAction();
            action.prepare();
            tumorMarker  = new TumorMarkerWebDto ();
        }
        
        @Test 
        public void testExecute(){
            assertEquals("success", action.execute());
        }
        
        @Test 
        public void testActionProperty(){
            assertNull(action.getTumorMarkerList());
            action.setTumorMarker(new TumorMarkerWebDto ());
            assertNotNull(action.getTumorMarker());
        }
        
        @Test
        public void addTestException() throws Exception {
            Pq tumor = new Pq();
            tumor.setUnit("Years");
            tumorMarker.setTumorMarker(CdConverter.convertStringToCd("Tumor Marker"));
            tumorMarker.setTmvUom(tumor);
            tumorMarker.setId(new Ii());
            action.setTumorMarker(tumorMarker);
            assertEquals(ActionSupport.INPUT, action.save());
        }
        
        @Test
        public void addTestException2() throws Exception {
            Pq tumor = new Pq();
            tumor.setUnit("Years");
            tumorMarker.setTumorMarker(CdConverter.convertStringToCd("Tumor Marker"));
            tumorMarker.setTmvUom(tumor);
            tumorMarker.setTumorMarkerValue(StConverter.convertToSt("ab"));
            tumorMarker.setId(new Ii());
            action.setTumorMarker(tumorMarker);
            assertEquals(ActionSupport.INPUT, action.save());
        }
        
        @Override
        @Test
        public void addTest() throws Exception {
            Pq tumor = new Pq();
            tumor.setUnit("Years");
            tumorMarker.setTumorMarker(CdConverter.convertStringToCd("Tumor Marker"));
            tumorMarker.setTmvUom(tumor);
            tumorMarker.setTumorMarkerValue(StConverter.convertToSt("2"));
            tumorMarker.setId(new Ii());
            action.setTumorMarker(tumorMarker);
            assertEquals("main", action.save());
        }
        @Test
        public void addTest2() throws Exception {
            tumorMarker.setTumorMarker(CdConverter.convertStringToCd("Tumor Marker"));
            tumorMarker.setTumorMarkerValue(StConverter.convertToSt("2"));
            tumorMarker.setId(new Ii());
            action.setTumorMarker(tumorMarker);
            assertEquals("main", action.save());
        }
}
