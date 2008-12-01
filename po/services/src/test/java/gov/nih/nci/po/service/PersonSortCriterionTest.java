
package gov.nih.nci.po.service;

import java.util.Arrays;
import org.junit.Test;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author gax
 */
public class PersonSortCriterionTest {


    /**
     * Test of getOrderByList method, of class PersonSortCriterion.
     */
    @Test
    public void testGetOrderByList() {
        assertSame(PersonSortCriterion.PERSON_ID, PersonSortCriterion.PERSON_ID.getOrderByList().get(0));
        assertNull(PersonSortCriterion.PERSON_FULLNAME.getOrderField());
        assertEquals(
                Arrays.asList(PersonSortCriterion.PERSON_LASTNAME, PersonSortCriterion.PERSON_FIRSTNAME, PersonSortCriterion.PERSON_MIDDLENAME),
                PersonSortCriterion.PERSON_FULLNAME.getOrderByList());
    }

}