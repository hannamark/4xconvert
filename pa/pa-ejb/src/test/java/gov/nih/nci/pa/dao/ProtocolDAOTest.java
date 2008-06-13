package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.service.ProtocolSearchCriteria;

import org.junit.Test;

public class ProtocolDAOTest {
    
    @Test
    public void queryProtocolTest() {
        ProtocolDAO dao = new ProtocolDAO();
        ProtocolSearchCriteria sc = new ProtocolSearchCriteria();
        sc.setLongTitleText("test");
        
//        try {
//            List<ProtocolDTO> result = dao.queryProtocol(sc);
//        } catch (PAException e) {
//            fail();
//        }
     }
}
 