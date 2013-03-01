/*
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The po
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This po Software License (the License) is between NCI and You. You (or
 * Your) shall mean a person or an entity, and all other entities that control,
 * are controlled by, or are under common control with the entity. Control for
 * purposes of this definition means (i) the direct or indirect power to cause
 * the direction or management of such entity, whether by contract or otherwise,
 * or (ii) ownership of fifty percent (50%) or more of the outstanding shares,
 * or (iii) beneficial ownership of such entity.
 *
 * This License is granted provided that You agree to the conditions described
 * below. NCI grants You a non-exclusive, worldwide, perpetual, fully-paid-up,
 * no-charge, irrevocable, transferable and royalty-free right and license in
 * its rights in the po Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the po Software; (ii) distribute and
 * have distributed to and by third parties the po Software and any
 * modifications and derivative works thereof; and (iii) sublicense the
 * foregoing rights set out in (i) and (ii) to third parties, including the
 * right to license such rights to further third parties. For sake of clarity,
 * and not by way of limitation, NCI shall have no right of accounting or right
 * of payment from You or Your sub-licensees for the rights granted under this
 * License. This License is granted at no charge to You.
 *
 * Your redistributions of the source code for the Software must retain the
 * above copyright notice, this list of conditions and the disclaimer and
 * limitation of liability of Article 6, below. Your redistributions in object
 * code form must reproduce the above copyright notice, this list of conditions
 * and the disclaimer of Article 6 in the documentation and/or other materials
 * provided with the distribution, if any.
 *
 * Your end-user documentation included with the redistribution, if any, must
 * include the following acknowledgment: This product includes software
 * developed by 5AM and the National Cancer Institute. If You do not include
 * such end-user documentation, You shall include this acknowledgment in the
 * Software itself, wherever such third-party acknowledgments normally appear.
 *
 * You may not use the names "The National Cancer Institute", "NCI", or "5AM"
 * to endorse or promote products derived from this Software. This License does
 * not authorize You to use any trademarks, service marks, trade names, logos or
 * product names of either NCI or 5AM, except as required to comply with the
 * terms of this License.
 *
 * For sake of clarity, and not by way of limitation, You may incorporate this
 * Software into Your proprietary programs and into any third party proprietary
 * programs. However, if You incorporate the Software into third party
 * proprietary programs, You agree that You are solely responsible for obtaining
 * any permission from such third parties required to incorporate the Software
 * into such third party proprietary programs and for informing Your
 * sub-licensees, including without limitation Your end-users, of their
 * obligation to secure any required permissions from such third parties before
 * incorporating the Software into such third party proprietary software
 * programs. In the event that You fail to obtain such permissions, You agree
 * to indemnify NCI for any claims against NCI by such third parties, except to
 * the extent prohibited by law, resulting from Your failure to obtain such
 * permissions.
 *
 * For sake of clarity, and not by way of limitation, You may add Your own
 * copyright statement to Your modifications and to the derivative works, and
 * You may provide additional or different license terms and conditions in Your
 * sublicenses of modifications of the Software, or any derivative works of the
 * Software as a whole, provided Your use, reproduction, and distribution of the
 * Work otherwise complies with the conditions stated in this License.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS," AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 * (INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * NON-INFRINGEMENT AND FITNESS FOR A PARTICULAR PURPOSE) ARE DISCLAIMED. IN NO
 * EVENT SHALL THE NATIONAL CANCER INSTITUTE, 5AM SOLUTIONS, INC. OR THEIR
 * AFFILIATES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 * OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package gov.nih.nci.po.web.contactable;

import gov.nih.nci.po.data.bo.Contact;
import gov.nih.nci.po.data.bo.Contactable;
import gov.nih.nci.po.data.bo.Email;
import gov.nih.nci.po.data.bo.PhoneNumber;
import gov.nih.nci.po.data.bo.PlayedRole;
import gov.nih.nci.po.data.bo.URL;
import gov.nih.nci.po.web.util.POUtils;
import gov.nih.nci.po.web.util.PoHttpSessionUtil;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 *
 * @param <Entry> the content of the list
 *
 * @author gax
 */
@SuppressWarnings({ "PMD.TooManyMethods", "PMD.ExcessiveClassLength" })
public abstract class AbstractEditContactListAction<Entry extends Contact> extends ActionSupport implements Preparable {

    private static final long serialVersionUID = 1L;

    /**
     * Specifies the name of the hibernate validator.
     */
    public static final String VALIDATOR_HIB = "hibernate";

    private Contactable contactable;
    private Entry entry;
    private String rootKey;
    private boolean readonly;
    private boolean usePlayerContactsAsDefault;
    private boolean usOrCanadaFormatForValidationOnly;
    private boolean create;
    private boolean usOrCanadaFormat;

    /**
     * @return session key of the Contactable to edit.
     */
    public String getRootKey() {
        return rootKey;
    }

    /**
     * @param rootKey session key of the Contactable to edit.
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
     * @param readOnly true for read-only display.
     */
    public void setReadonly(boolean readOnly) {
        this.readonly = readOnly;
    }
    /**
     * @return SUCCESS.
     */
    @SuppressWarnings("rawtypes")
    @SkipValidation
    public String edit() {
        if (usePlayerContactsAsDefault && contactable instanceof PlayedRole) {
            Contactable contactableToCopyFrom = (Contactable) ((PlayedRole) contactable)
                    .getPlayer();
            setDefaults(contactableToCopyFrom);
        }
        return SUCCESS;
    }

    /**
     * Copy defaults from the given {@link Contactable}.
     * @param contactableToCopyFrom Contactable
     */
    protected abstract void setDefaults(Contactable contactableToCopyFrom);

    /**
     * Prep.
     */
    @RequiredStringValidator(fieldName = "rootKey", message = "param rootKey must be set")
    public void prepare() {
        entry = newEntry();
        if (rootKey == null) {
            throw new IllegalArgumentException("cannot be called without setting param rootKey");
        }
        contactable = (Contactable) getSession().getAttribute(rootKey);
    }

    /**
     * @return contactInfo
     */
    public Contactable getContactable() {
        return contactable;
    }

    /**
     * @param contactInfo contactInfo
     */
    public void setContactable(Contactable contactInfo) {
        this.contactable = contactInfo;
    }

    /**
     * helper for ServletActionContext.getRequest().getSession().
     * @return the HttpSession for this invocation context.
     */
    protected static HttpSession getSession() {
        return ServletActionContext.getRequest().getSession();
    }

    /**
     * @param val check if there is an Entry in the list that has this value.
     * @return Entry if found, null otherwise
     */
    protected Entry find(String val) {
        for (Entry e : getList()) {
            if (e.getValue().equals(val)) {
                return e;
            }
        }
        return null;
    }

    /**
     * @return SUCCESS
     */
    public String remove() {
        boolean removed = false;
        for (Entry e : getList()) {
            if (e.getValue().trim().equals(entry.getValue())) {
                removed = getList().remove(e);
                entry = newEntry();
                break;
            }
        }
        if (!removed) {
            ActionHelper.saveMessage("Value " + entry.getValue() + " was not found in list.");
        }
        return SUCCESS;
    }

    /**
     * Add an Entry to the list using value.
     * @return SUCCESS
     */
    @CustomValidator(type = VALIDATOR_HIB, fieldName = "entry")
    public String add() {
        Entry e = find(entry.getValue());
        if (e != null) {
            ActionHelper.saveMessage("Already added");
        } else {
            getList().add(getEntry());
            entry = newEntry();
        }
        return SUCCESS;
    }

    /**
     * @return entry entry to be added or removed
     */
    public Entry getEntry() {
        return entry;
    }

    /**
     *@param e entry to set.
     **/
    public void setEntry(Entry e) {
        this.entry = e;
    }

    /**
     * @return the list of entries from contactInfo
     */
    protected abstract List<Entry> getList();

    /**
     * Helps avoid too many struts converters.
     * @return a new Entry instance .
     */
    protected abstract Entry newEntry();

    /**
     * @param create the create to set
     */
    public void setCreate(boolean create) {
        this.create = create;
    }

    /**
     * @return the create
     */
    public boolean isCreate() {
        return create;
    }

    /**
     * @param usFormat the usFormat to set
     */
    public void setUsOrCanadaFormat(boolean usFormat) {
        this.usOrCanadaFormat = usFormat;
    }

    /**
     * @return the usFormat
     */
    public boolean isUsOrCanadaFormat() {
        return usOrCanadaFormat;
    }

    ///////////////////////////////////////////////////////////////////
    // Concrete implementations below
    /**
     * Edit <code>ContactInfo.email</code>.
     */
    public static class EmailAction extends AbstractEditContactListAction<Email> {

        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<Email> getList() {
            return getContactable().getEmail();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected Email newEntry() {
            return new Email();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @CustomValidator(type = VALIDATOR_HIB, fieldName = "emailEntry")
        public String add() {
            return super.add();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        @SkipValidation
        public void setDefaults(Contactable contactableToCopyFrom) {  
            if (contactableToCopyFrom != null) {
                for (Email e : contactableToCopyFrom.getEmail()) {
                    getEntry().setValue(e.getValue());
                    super.add();
                }
            }
        }

        /**
         * Get the Email.
         * @return email
         */
        public Email getEmailEntry() {
            return super.getEntry();
        }

        /**
         *@param e email to set.
         **/
        public void setEmailEntry(Email e) {
           setEntry(e);
        }
    }

    /**
     * Edit <code>ContactInfo.Url</code>.
     */
    public static class UrlAction extends AbstractEditContactListAction<URL> {

        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<URL> getList() {
            return getContactable().getUrl();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected URL newEntry() {
            return new URL();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @CustomValidator(type = VALIDATOR_HIB, fieldName = "urlEntry")
        public String add() {
            return super.add();
        }

        /**
         * Get the URL.
         * @return URL
         */
        public URL getUrlEntry() {
            return super.getEntry();
        }

        /**
         *@param url entry to set.
         **/
        public void setUrlEntry(URL url) {
           setEntry(url);
        }

        @Override
        protected void setDefaults(Contactable contactableToCopyFrom) { // NOPMD                       
        }
    }

    /**
     *
     * Edit <code>ContactInfo.phone</code>.
     */
    public static class PhoneAction extends AbstractEditContactListAction<PhoneNumber> {

        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<PhoneNumber> getList() {
            return getContactable().getPhone();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected PhoneNumber newEntry() {
            return new PhoneNumber();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @CustomValidator(type = VALIDATOR_HIB, fieldName = "phoneEntry")
        public String add() {
            super.getEntry().setValue(POUtils.adjustPhoneNumberFormat(super.getEntry().getValue()));
            return super.add();
        }
      
        /**
         * {@inheritDoc}
         */
        @Override
        @SkipValidation
        public void setDefaults(Contactable contactableToCopyFrom) {  
            if (contactableToCopyFrom != null) {
                for (PhoneNumber e : contactableToCopyFrom.getPhone()) {
                    getEntry().setValue(e.getValue());
                    super.add();
                }
            }
        }

        /**
         * Get the phone number.
         * @return phone number
         */
        public PhoneNumber getPhoneEntry() {
            return super.getEntry();
        }

        /**
         *@param phone phone to set.
         **/
        public void setPhoneEntry(PhoneNumber phone) {
            setEntry(phone);
        }
    }

    /**
     *
     * @author gax
     */
    public static class FaxAction extends PhoneAction {

        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<PhoneNumber> getList() {
            return getContactable().getFax();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @CustomValidator(type = VALIDATOR_HIB, fieldName = "faxEntry")
        public String add() {
            return super.add();
        }
        
        /**
         * {@inheritDoc}
         */
        @Override
        @SkipValidation
        public void setDefaults(Contactable contactableToCopyFrom) {  
            if (contactableToCopyFrom != null) {
                for (PhoneNumber e : contactableToCopyFrom.getFax()) {
                    getEntry().setValue(e.getValue());
                    super.add();
                }
            }
        }

        /**
         * Get the Fax Number.
         * @return fax number
         */
        public PhoneNumber getFaxEntry() {
            return super.getEntry();
        }

        /**
         * @param fax  fax to set.
         */
        public void setFaxEntry(PhoneNumber fax) {
            setEntry(fax);
        }
    }
    /**
     *
     * @author gax
     */
    public static class TtyAction extends PhoneAction {

        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<PhoneNumber> getList() {
            return getContactable().getTty();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @CustomValidator(type = VALIDATOR_HIB, fieldName = "ttyEntry")
        public String add() {
            return super.add();
        }

        /**
         * Get the TTY number.
         * @return TTY number
         */
        public PhoneNumber getTtyEntry() {
            return super.getEntry();
        }

        /**
         *@param tty fax to set.
         **/
        public void setTtyEntry(PhoneNumber tty) {
            setEntry(tty);
        }
    }
    /**
     * @return the usePlayerContactsAsDefault
     */
    public boolean isUsePlayerContactsAsDefault() {
        return usePlayerContactsAsDefault;
    }
   

    /**
     * @param usePlayerContactsAsDefault the usePlayerContactsAsDefault to set
     */
    public void setUsePlayerContactsAsDefault(boolean usePlayerContactsAsDefault) {
        this.usePlayerContactsAsDefault = usePlayerContactsAsDefault;
    }

    /**
     * @return the usOrCanadaFormatForValidationOnly
     */
    public boolean isUsOrCanadaFormatForValidationOnly() {
        return usOrCanadaFormatForValidationOnly;
    }

    /**
     * @param usOrCanadaFormatForValidationOnly the usOrCanadaFormatForValidationOnly to set
     */
    public void setUsOrCanadaFormatForValidationOnly(
            boolean usOrCanadaFormatForValidationOnly) {
        this.usOrCanadaFormatForValidationOnly = usOrCanadaFormatForValidationOnly;
    }

}
