package gov.nih.nci.po.web.alias;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.web.AbstractPoTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.Action;

/**
 * 
 * @author Rohit Gupta
 */
public class EditAliasListActionTest extends AbstractPoTest {

    @Test
    public void simple() {
        EditAliasListAction instance = new EditAliasListAction();
        assertEquals(Action.SUCCESS, instance.edit());
    }

    @Test
    public void prepare() {
        List<Alias> aliases = new ArrayList<Alias>();

        String key = "foo";
        getSession().setAttribute(key, aliases);

        EditAliasListAction instance = new EditAliasListAction();
        assertTrue(instance.getAliases().size() == 0);
        assertNull(instance.getRootKey());
        try {
            instance.prepare();
            fail("should no work w/o a ciKey");
        } catch (IllegalArgumentException e) {
            // ok
        }
        instance.setRootKey(key);
        instance.prepare();
        assertEquals(aliases, instance.getAliases());
    }

    @Test
    public void testOrganizationAlias() {
        Organization org = new Organization();
        EditAliasListAction instance = new EditAliasListAction();
        String key = "foo";
        getSession().setAttribute(key, org);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        Alias a = instance.getAlias();
        a.setValue(v);
        instance.add();
        a = org.getAlias().get(0);
        assertEquals(v, a.getValue());

        instance.setAlias(a);
        assertEquals(a, instance.getAlias());
    }

    @Test
    public void testHCFAlias() {
        HealthCareFacility hcf = new HealthCareFacility();
        EditAliasListAction instance = new EditAliasListAction();
        String key = "foo";
        getSession().setAttribute(key, hcf);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        Alias a = instance.getAlias();
        a.setValue(v);
        instance.add();

        a = hcf.getAlias().get(0);
        assertEquals(v, a.getValue());

        instance.setAlias(a);
        assertEquals(a, instance.getAlias());
    }

    @Test
    public void testROAlias() {
        ResearchOrganization ro = new ResearchOrganization();
        EditAliasListAction instance = new EditAliasListAction();
        String key = "foo";
        getSession().setAttribute(key, ro);
        instance.setRootKey(key);

        instance.prepare();

        String v = "foo";
        Alias a = instance.getAlias();
        a.setValue(v);
        instance.add();

        a = ro.getAlias().get(0);
        assertEquals(v, a.getValue());

        instance.setAlias(a);
        assertEquals(a, instance.getAlias());
    }

    @Test
    public void testReadonlyProperty() {
        EditAliasListAction action = new EditAliasListAction();
        assertFalse(action.isReadonly());
        action.setReadonly(true);
        assertTrue(action.isReadonly());
    }

    @Test
    public void addRemoveOrgAlias() {
        Organization org = new Organization();
        EditAliasListAction instance = new EditAliasListAction();
        String key = "foo alias";
        getSession().setAttribute(key, org);
        instance.setRootKey(key);
        instance.prepare();

        String v = "foo alias";
        assertNull(instance.find(v));
        Alias a = new Alias(v);
        org.getAlias().add(a);
        assertSame(a, instance.find(v));
        assertNull(ActionHelper.getMessages());
        instance.getAlias().setValue(v);

        instance.add(); // add existing alias
        assertTrue(ActionHelper.getMessages().get(0).contains("Already "));

        // now add a different alias to the org
        v = "bar alias";
        ActionHelper.getMessages().clear();
        instance.getAlias().setValue(v);
        instance.add();
        assertTrue(ActionHelper.getMessages().isEmpty());
        assertEquals(2, org.getAlias().size());
        assertEquals(v, org.getAlias().get(1).getValue());

        instance.getAlias().setValue(v);
        instance.remove();
        assertEquals(1, org.getAlias().size());
        assertFalse(v.equals(org.getAlias().get(0).getValue()));

        // make sure you can remove a value with leading or trailing spaces
        v = " bar alias ";
        ActionHelper.getMessages().clear();
        instance.getAlias().setValue(v);
        instance.add();
        assertTrue(ActionHelper.getMessages().isEmpty());
        assertEquals(2, org.getAlias().size());
        assertEquals(v, org.getAlias().get(1).getValue());

        String valueNoSpaces = "bar alias";
        instance.getAlias().setValue(valueNoSpaces);
        instance.remove();
        assertEquals(1, org.getAlias().size());
        assertFalse(v.equals(org.getAlias().get(0).getValue()));
        assertFalse(valueNoSpaces.equals(org.getAlias().get(0).getValue()));
        assertTrue(ActionHelper.getMessages().isEmpty());
    }

    @Test
    public void testRemoveOnEmptyList() {
        List<Alias> aliases = new ArrayList<Alias>();
        EditAliasListAction instance = new EditAliasListAction();
        instance.setAliases(aliases);
        instance.setAlias(new Alias());
        instance.remove();
        assertTrue(ActionHelper.getMessages().get(0).contains("was not found"));
    }

    @Test
    public void testAliasOrdering() {
        List<Alias> aliases = new ArrayList<Alias>();
        aliases.add(new Alias("Zulu"));
        aliases.add(new Alias("XRay"));
        aliases.add(new Alias("Whiskey"));

        EditAliasListAction action = new EditAliasListAction();
        action.setAliases(aliases);

        List<Alias> sortedAliases = action.getAliases();
        assertEquals("Whiskey", sortedAliases.get(0).getValue());
        assertEquals("XRay", sortedAliases.get(1).getValue());
        assertEquals("Zulu", sortedAliases.get(2).getValue());

        //make sure the original isn't changed
        assertEquals("Whiskey", aliases.get(2).getValue());
        assertEquals("XRay", aliases.get(1).getValue());
        assertEquals("Zulu", aliases.get(0).getValue());
    }

}
