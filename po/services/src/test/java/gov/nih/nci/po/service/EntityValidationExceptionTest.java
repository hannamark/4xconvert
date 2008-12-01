package gov.nih.nci.po.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;


public class EntityValidationExceptionTest {

    @Test
    public void nullErrorMessages() {
        EntityValidationException e = new EntityValidationException(null);
        assertEquals("", e.getErrorMessages());
    }
    
    @Test
    public void getErrorMessages1() {
        HashMap<String, String[]> errors = new HashMap<String, String[]>();
        errors.put("aaa", null);
        EntityValidationException e = new EntityValidationException(errors);
        assertEquals("aaa=null", e.getErrorMessages());
    }
    
    @Test
    public void getErrorMessages2() {
        HashMap<String, String[]> errors = new HashMap<String, String[]>();
        errors.put("aaa", null);
        errors.put("bbb", new String[]{""});
        EntityValidationException e = new EntityValidationException(errors);
        assertEquals("aaa=null\nbbb=[]", e.getErrorMessages());
    }
    
    @Test
    public void getErrorMessages3() {
        HashMap<String, String[]> errors = new HashMap<String, String[]>();
        errors.put("aaa", null);
        EntityValidationException e = new EntityValidationException("customMessage", errors);
        assertEquals("aaa=null", e.getErrorMessages());
    }
}
