package gov.nih.nci.pa.action;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.enums.IdentifierType;
import gov.nih.nci.pa.iso.dto.StudyProtocolAssociationDTO;
import gov.nih.nci.pa.iso.util.CdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.StudyProtocolService;
import gov.nih.nci.pa.service.util.MockPAServiceUtils;
import gov.nih.nci.pa.service.util.PAServiceUtils;
import gov.nih.nci.pa.util.Constants;

import org.apache.struts2.ServletActionContext;
import org.junit.Before;
import org.junit.Test;
/**
 * 
 * @author Reshma Koganti
 *
 */
public class ManageOtherIdentifiersActionTest  extends AbstractPaActionTest {
    ManageOtherIdentifiersAction action;
    MockPAServiceUtils util;
    
    @Before
    public void setUp() throws PAException {
        action =  new ManageOtherIdentifiersAction();
        List<Ii> list = new ArrayList<Ii>();
        Ii id = IiConverter.convertToIi(1L);
        Ii id1 = IiConverter.convertToIi(2L);
        list.add(id);
        list.add(id1);
        getRequest().setupAddParameter("otherIdentifier", "1L");
        getRequest().setupAddParameter("otherIdentifierType", "2");
        getSession().setAttribute(Constants.OTHER_IDENTIFIERS_LIST,list);
        getSession().setAttribute(Constants.STUDY_PROTOCOL_II, id);
    } 
    
    @Test
    public void addOtherIdentifierTest() {
        assertEquals("display_otherIdentifiers", action.addOtherIdentifier());
        List<Ii> secondaryIds =
                (List<Ii>) ServletActionContext.getRequest().getSession().
                getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
        assertTrue(secondaryIds.size() == 3 );
        assertEquals("Duplicate NCI study protocol identifier", secondaryIds.get(2).getIdentifierName());
    }
    
    @Test
    public void deleteOtherIdentifiertest() {
       getRequest().setupAddParameter("uuid", "2");
       assertEquals("display_otherIdentifiers", action.deleteOtherIdentifier());
       List<Ii> secondaryIds =
               (List<Ii>) ServletActionContext.getRequest().getSession().
               getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
       assertTrue(secondaryIds.size() == 1 );
       assertEquals("1", secondaryIds.get(0).getExtension());
       
    }
    
    @Test
    public void saveOtherIdentifierRowTest() {
       getRequest().setupAddParameter("uuid", "2");
       assertEquals("display_otherIdentifiers", action.saveOtherIdentifierRow());
       List<Ii> secondaryIds =
               (List<Ii>) ServletActionContext.getRequest().getSession().
               getAttribute(Constants.OTHER_IDENTIFIERS_LIST);
       assertTrue(secondaryIds.size() == 2 );
       assertEquals("1L", secondaryIds.get(1).getExtension());
       assertEquals("Duplicate NCI study protocol identifier", secondaryIds.get(1).getIdentifierName());
       
    }
    
    @Test
    public void showWaitDialogTest() {
       assertEquals("show_ok_create", action.showWaitDialog());
    }
}
