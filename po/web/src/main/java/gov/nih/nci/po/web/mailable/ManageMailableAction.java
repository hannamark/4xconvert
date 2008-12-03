package gov.nih.nci.po.web.mailable;

import gov.nih.nci.po.data.bo.Address;
import gov.nih.nci.po.data.bo.Country;
import gov.nih.nci.po.data.bo.Mailable;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.ValidationParameter;
import com.opensymphony.xwork2.validator.annotations.Validations;

/**
 * Action to manage Mailable instances.
 * @author smatyas
 */
public class ManageMailableAction extends ActionSupport implements Preparable {
    private static final String ADDRESS = "address";

    private static final long serialVersionUID = 1L;
    
    private Mailable mailable;
    private String rootKey;
    private Address address = new Address();
    private Integer index = -1;
    
    /**
     * {@inheritDoc}
     * @throws Exception 
     */
    @SuppressWarnings("PMD.SignatureDeclareThrowsException")
    public void prepare() throws Exception {
        if (rootKey == null) {
            throw new IllegalArgumentException("cannot be called without setting param rootKey");
        }
        mailable = (Mailable) getSession().getAttribute(getRootKey());
        if (isValidIndex()) {
            int tmpIdx = 0;
            for (Iterator<Address> itr = getMailable().getPostalAddresses().iterator(); itr.hasNext();) {
                Address tmp = itr.next();
                if (doesIndexMatch(tmpIdx)) {
                    setAddress(tmp);
                    break;
                }
                tmpIdx++;
            }
        }
        initializeAddressCountry();
    }
    
    private HttpSession getSession() {
        return PoHttpSessionUtil.getSession();
    }
    
    /**
     * @return addresses result
     */
    @Override
    public String input() {
        initializeAddressCountry();
        return INPUT;
    }

    private boolean isValidIndex() {
        return getIndex() != null && getIndex() > -1;
    }
    
    private void initializeAddressCountry() {
        if (getAddress().getCountry() == null) { //if address country is null, set to blank country
            getAddress().setCountry(new Country());
        }
    }
    
    /**
     * @return addresses result
     */
    @Validations(
            customValidators = { @CustomValidator(type = "hibernate", fieldName = ADDRESS ,
                    parameters = { @ValidationParameter(name = "resourceKeyBase", value = ADDRESS) })
            }
    )
    
    public String add() {
        getMailable().getPostalAddresses().add(getAddress());
        setAddress(new Address());
        initializeAddressCountry();
        ActionHelper.saveMessage(getAddAddressSuccessMessage());
        return INPUT;
    }
    
    private String getAddAddressSuccessMessage() {
        return "clinicalresearchstaff.postaladdress.create.success";
    }
    
    /**
     * @return addresses result
     */
    @Validations(
            customValidators = { @CustomValidator(type = "hibernate", fieldName = ADDRESS ,
                    parameters = { @ValidationParameter(name = "resourceKeyBase", value = ADDRESS) })
            }
    )
    public String edit() {
        ActionHelper.saveMessage(getEditAddressSuccessMessage());
        return INPUT;
    }
    
    private String getEditAddressSuccessMessage() {
        return "clinicalresearchstaff.postaladdress.update.success";
    }

    /**
     * @return addresses result
     */
    public String remove() {
        int tmpIdx = 0;
        for (Iterator<Address> itr = getMailable().getPostalAddresses().iterator(); itr.hasNext();) {
            itr.next();
            if (doesIndexMatch(tmpIdx)) {
                itr.remove();
                break;
            }
            tmpIdx++;
        }
        return SUCCESS;
    }

    private boolean doesIndexMatch(int tmpIdx) {
        return getIndex() != null && tmpIdx == getIndex();
    }
    
    /**
     * @return instance of Mailable 
     */
    public Mailable getMailable() {
        return mailable;
    }
    
    /**
     * 
     * @return the session key of the root object (instanceof Mailable)
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * 
     * @param rootKey the session key of the root object.
     */
    public void setRootKey(String rootKey) {
        this.rootKey = rootKey;
    }
    
    /**
     * @return addresses result
     */
    public String addresses() {
        return SUCCESS;
    }
    
    /**
     * @return address to add/edit
     */
    public Address getAddress() {
        return address;
    }

    /**
     * @param address to add/edit
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * @return index of address to remove
     */
    public Integer getIndex() {
        return index;
    }

    /**
     * @param addressIndex index of address to remove
     */
    public void setIndex(Integer addressIndex) {
        this.index = addressIndex;
    }    
}
