package gov.nih.nci.po.web.selector;

import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.util.PoRegistry;
import gov.nih.nci.po.web.search.SearchPersonAction;

import com.opensymphony.xwork2.Preparable;

/**
 * Action to search for people.
 */
public class SelectPersonAction extends SearchPersonAction implements Preparable {

    /**
     * action result to view entity's detail.
     */
    static final String DETAIL_RESULT = "detail";

    private static final long serialVersionUID = 1L;
    private Person person = new Person();
    private Person source = new Person();

    /**
     * @return detail page
     */
    public String detail() {
        person = PoRegistry.getPersonService().getById(person.getId());
        return DETAIL_RESULT;
    }

    /**
     * @return person to view detail
     */
    public Person getPerson() {
        return person;
    }

    /**
     * @param person to view detail
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * @return source organization to search for.
     */
    public Person getSource() {
        return source;
    }

    /**
     * @param source organization to search for.
     */
    public void setSource(Person source) {
        this.source = source;
    }
}
