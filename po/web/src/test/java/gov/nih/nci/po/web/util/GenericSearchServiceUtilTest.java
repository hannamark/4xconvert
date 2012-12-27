package gov.nih.nci.po.web.util;

import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.PersonServiceLocal;
import gov.nih.nci.po.service.PersonSortCriterion;
import gov.nih.nci.po.service.StrutsPersonSearchCriteria;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.AbstractPoTest;
import gov.nih.nci.po.web.GenericSearchServiceUtil;

import java.util.ArrayList;

import org.displaytag.properties.SortOrderEnum;
import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.displaytag.PaginatedList;


public class GenericSearchServiceUtilTest extends AbstractPoTest {

    @SuppressWarnings("static-access")
    @Test
    public void search() {
        new GenericSearchServiceUtil();
        PaginatedList<Person> persons = new PaginatedList<Person>(0, new ArrayList<Person>(),
                PoRegistry.DEFAULT_RECORDS_PER_PAGE, 1, null, PersonSortCriterion.PERSON_ID.name(),
                SortOrderEnum.ASCENDING);
        PersonServiceLocal personService = PoRegistry.getInstance().getPersonService();

        GenericSearchServiceUtil.search(personService, new StrutsPersonSearchCriteria(), persons, PersonSortCriterion.class);
    }
}
