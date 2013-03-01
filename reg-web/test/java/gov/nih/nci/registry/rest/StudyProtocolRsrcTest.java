package gov.nih.nci.registry.rest;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Test;

public class StudyProtocolRsrcTest {

    @Test
    public void getStudiesXMLNullTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML(null);
        assertEquals(400, resp.getStatus());
    }

    @Test
    public void getStudiesXMLInternalServerErroTest() {
        StudyProtocolsRsrc rsrc = new StudyProtocolsRsrc();
        Response resp = rsrc.getStudiesXML("xyzzy");
        assertEquals(500, resp.getStatus());
    }
}
