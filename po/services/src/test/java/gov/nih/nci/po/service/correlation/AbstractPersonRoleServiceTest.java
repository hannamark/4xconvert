package gov.nih.nci.po.service.correlation;

import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.po.service.EntityValidationException;

import org.junit.Test;


public abstract class AbstractPersonRoleServiceTest<T extends PersonRole> extends AbstractStructrualRoleServiceTest<T>{

    @Test(expected = EntityValidationException.class)
    public void testUniqueConstraint() throws Exception {
        T obj = getSampleStructuralRole();
        T obj2 = getSampleStructuralRole();
        //ensure player is same
        obj.setPlayer(obj2.getPlayer());
        //ensure scoper is same
        obj2.setScoper(obj.getScoper());
        getService().create(obj);        
        getService().create(obj2);
    }
}
