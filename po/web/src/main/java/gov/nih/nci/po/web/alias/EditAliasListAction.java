package gov.nih.nci.po.web.alias;

import gov.nih.nci.po.data.bo.Alias;
import gov.nih.nci.po.data.bo.HealthCareFacility;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.ResearchOrganization;
import gov.nih.nci.po.util.AliasComparator;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * 
 * @author Rohit Gupta
 */
public class EditAliasListAction extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;

    /**
     * Specifies the name of the hibernate validator.
     */
    public static final String VALIDATOR_HIB = "hibernate";

    private List<Alias> aliases = new ArrayList<Alias>();
    private Alias alias;
    private String rootKey;
    private boolean readonly;
    private boolean usePlayerAliasesAsDefault;

    /**
     * Copy defaults from the given aliasList.
     * 
     * @param aliasList
     *            List<Alias>
     */
    @SkipValidation
    public void setDefaults(List<Alias> aliasList) {
        if (CollectionUtils.isNotEmpty(aliasList)) {
            for (Alias a : aliasList) {
                getAlias().setValue(a.getValue());
                add();
            }
        }
    }

    /**
     * Prep.
     */
    @RequiredStringValidator(fieldName = "rootKey", message = "param rootKey must be set")
    public void prepare() {
        alias = newAlias();
        if (rootKey == null) {
            throw new IllegalArgumentException(
                    "cannot be called without setting param rootKey");
        }

        Object obj = getSession().getAttribute(rootKey);
        if (obj instanceof gov.nih.nci.po.data.bo.HealthCareFacility) {
            HealthCareFacility hcf = (HealthCareFacility) obj;
            aliases = hcf.getAlias();
        } else if (obj instanceof gov.nih.nci.po.data.bo.ResearchOrganization) {
            ResearchOrganization ro = (ResearchOrganization) obj;
            aliases = ro.getAlias();
        } else if (obj instanceof gov.nih.nci.po.data.bo.Organization) {
            Organization organization = (Organization) getSession()
                    .getAttribute(rootKey);
            aliases = organization.getAlias();
        }
    }

    /**
     * @return SUCCESS.
     */
    @SkipValidation
    public String edit() {
        if (usePlayerAliasesAsDefault) {
            setDefaults(aliases);
        }
        return SUCCESS;
    }

    /**
     * @return SUCCESS
     */
    public String remove() {
        boolean removed = false;
        for (Alias a : getList()) {
            if (a.getValue().trim().equals(alias.getValue())) {
                removed = getList().remove(a);
                alias = newAlias();
                break;
            }
        }
        if (!removed) {
            ActionHelper.saveMessage("Value " + alias.getValue()
                    + " was not found in list.");
        }
        return SUCCESS;
    }

    /**
     * Add an Entry to the list using value.
     * 
     * @return SUCCESS
     */
    @CustomValidator(type = VALIDATOR_HIB, fieldName = "alias")
    public String add() {
        Alias a = find(alias.getValue());
        if (a != null) {
            ActionHelper.saveMessage("Already added");
        } else {
            getList().add(getAlias());
            alias = newAlias();
        }
        return SUCCESS;
    }

    /**
     * get the aliases.
     * 
     * @return aliases - List<Alias>.
     */
    public List<Alias> getAliases() {
        List<Alias> result = new ArrayList<Alias>();
        result.addAll(aliases);
        Collections.sort(result, new AliasComparator());
        return result;
    }

    /**
     * Set the aliases.
     * 
     * @param aliases
     *            List<Alias>.
     */
    public void setAliases(List<Alias> aliases) {
        this.aliases = aliases;
    }

    /**
     * @return alias alias to be added or removed
     */
    public Alias getAlias() {
        return alias;
    }

    /**
     * @param a
     *            alias to set.
     **/
    public void setAlias(Alias a) {
        this.alias = a;
    }

    /**
     * @return session key of the List<Alias> to edit.
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * @param rootKey
     *            session key of the List<Alias> to edit.
     */
    public void setRootKey(String rootKey) {
        PoHttpSessionUtil.validateSessionKey(rootKey);
        this.rootKey = rootKey;
    }

    /**
     * @return whether display should be read-only or not.
     */
    public boolean isReadonly() {
        return readonly;
    }

    /**
     * @param readOnly
     *            true for read-only display.
     */
    public void setReadonly(boolean readOnly) {
        this.readonly = readOnly;
    }

    /**
     * @return the usePlayerAliasesAsDefault
     */
    public boolean isUsePlayerAliasesAsDefault() {
        return usePlayerAliasesAsDefault;
    }

    /**
     * @param usePlayerAliasesAsDefault
     *            the usePlayerAliasesAsDefault to set
     */
    public void setUsePlayerAliasesAsDefault(boolean usePlayerAliasesAsDefault) {
        this.usePlayerAliasesAsDefault = usePlayerAliasesAsDefault;
    }

    /**
     * helper for ServletActionContext.getRequest().getSession().
     * 
     * @return the HttpSession for this invocation context.
     */
    private static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * @param val
     *            check if there is an Alias in the list that has this value.
     * @return Alias if found, null otherwise
     */
    public Alias find(String val) {
        for (Alias a : getList()) {
            if (a.getValue().equals(val)) {
                return a;
            }
        }
        return null;
    }

    /**
     * @return the list of aliases
     */
    private List<Alias> getList() {
        return aliases;
    }

    /**
     * Helps avoid too many struts converters.
     * 
     * @return a new Alias instance .
     */
    private Alias newAlias() {
        return new Alias();
    }

}
