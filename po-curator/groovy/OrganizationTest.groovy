import org.apache.poi.hssf.usermodel.*
import org.apache.poi.hssf.util.*
import org.apache.poi.ss.usermodel.CreationHelper
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

/**
* @author Monish
*
*/
class OrganizationTest extends GroovyTestCase {
    
    void testEquals(){
        

        
        Organization o1 = new Organization(1,
                                        "name",
                                        "cityOrMunicipality",
                                        "deliveryAddressLine",
                                        "postalCode",
                                        "stateOrProvince",
                                        "streetAddressLine",
                                        "country",
                                        "email@email.com",
                                        1);
        Organization o2 = new Organization(1,
                                        "name",
                                        "cityOrMunicipality",
                                        "deliveryAddressLine",
                                        "postalCode",
                                        "stateOrProvince",
                                        "streetAddressLine",
                                        "country",
                                        "email@email.com",
                                        1);
                                    
        assertTrue(o1.equals(o2));
        
        o2.setEmail("m@mc.com")
        
        assertFalse(o1.equals(o2))
        
        o2.setEmail("ncictepcoppaservices@mail.nih.gov")
        
        assertFalse(o1.equals(o2))
        
        o2.setEmail("ctepcoppaservices@mail.nih.gov")
        
        assertFalse(o1.equals(o2))
        
        o1.setEmail("ncictepcoppaservices@mail.nih.gov")
        o2.setEmail("ctepcoppaservices@mail.nih.gov")
        assertTrue(o1.equals(o2));
        
        o1.setEmail("ctepcoppaservices@mail.nih.gov")
        o2.setEmail("ncictepcoppaservices@mail.nih.gov")
        assertTrue(o1.equals(o2));
        
        o1.setEmail("ncictepcoppaservices@mail.nih.gov")
        o2.setEmail("HamblinF@allkids.org")
        assertFalse(o1.equals(o2));
    }
}