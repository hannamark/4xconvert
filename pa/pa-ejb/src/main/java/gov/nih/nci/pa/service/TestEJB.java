package gov.nih.nci.pa.service;


import javax.naming.InitialContext;

/**
 * 
 * @author hjayanna
 *
 */
public class TestEJB {
    
    

    /**
     * 
     * @param args String
     */
    public static void main(String args
            []) {
       getEJB("pa/DiseaseCondServiceBean/remote");
    }
    
    /**
     * 
     * @param jndiName String
     * @return object
     */
    public static Object getEJB(String jndiName) {
        Object object = null;
        try {
            InitialContext ctx = new InitialContext();            
            object = ctx.lookup(jndiName);
        } catch (Exception e) {
            //e.printStackTrace();
            object = null;
        }
        return object;
    } 
}
