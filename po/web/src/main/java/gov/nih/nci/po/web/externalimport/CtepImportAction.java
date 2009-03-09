/**
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
package gov.nih.nci.po.web.externalimport;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.po.service.external.CtepOrganizationImporter;
import gov.nih.nci.po.service.external.CtepPersonImporter;
import gov.nih.nci.po.util.PoHibernateUtil;
import gov.nih.nci.po.util.PoRegistry;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.validation.SkipValidation;

import com.fiveamsolutions.nci.commons.util.HibernateHelper;
import com.fiveamsolutions.nci.commons.web.struts2.action.ActionHelper;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;


/**
 * @author Scott Miller
 *
 */
@SuppressWarnings("PMD.TooManyMethods")
public class CtepImportAction extends ActionSupport {
    private static final Logger LOG = Logger.getLogger(CtepImportAction.class);
    private static final long serialVersionUID = 1L;
    private File file;

    /**
     * Action to go to the page allowing file upload.
     * @return SUCCESS
     */
    @SkipValidation
    public String start() {
        return INPUT;
    }

    /**
     * Method to handle upload of org ids.
     * @return SUCCESS
     * @throws IOException on error processing file.
     * @throws JMSException on error saving.
     */
    public String uploadOrganizations() throws IOException, JMSException {
        new ImportHelper(getFile()).process(new OrgImporter());
        return SUCCESS;
    }

    /**
     * Method to handle upload of person ids.
     * @return SUCCESS
     * @throws IOException on error processing file.
     * @throws JMSException on error saving.
     */
    public String uploadPeople() throws IOException, JMSException {
        new ImportHelper(getFile()).process(new PersonImporter());
        return SUCCESS;
    }

    /**
     *  Callback interface to generalize behavior.
     */
    interface Importer {
        /**
         * @param value CTEP identifier to lookup and import
         * @return COPPA-PO Object saved, otherwise null
         * @throws JMSException the JMS related exception encountered
         */
        Object invoke(Ii value) throws JMSException;

        /**
         * @return the friendly name
         */
        String getType();

        /**
         * generate an ii.
         * @param id the id to use
         * @return the ii
         */
        Ii generateIi(String id);
    }

    /**
     * Callback implementation to import Person.
     */
    static class PersonImporter implements Importer {
        /**
         * {@inheritDoc}
         */
        public Object invoke(Ii value) throws JMSException {
            return PoRegistry.getInstance().getServiceLocator().
            getCtepImportService().importCtepPerson(value);
        }

        /**
         * {@inheritDoc}
         */
        public String getType() {
            return Person.class.getSimpleName();
        }

        /**
         * {@inheritDoc}
         */
        public Ii generateIi(String id) {
            Ii ii = new Ii();
            ii.setExtension(id);
            ii.setRoot(CtepPersonImporter.CTEP_PERSON_DB_ROOT);
            return ii;
        }

    }

    /**
     * Callback implementation to import Organization.
     */
    static class OrgImporter implements Importer {
        /**
         * {@inheritDoc}
         */
        public Object invoke(Ii value) throws JMSException {
            return PoRegistry.getInstance().getServiceLocator().
            getCtepImportService().importCtepOrganization(value);
        }
        /**
         * {@inheritDoc}
         */
        public String getType() {
            return Organization.class.getSimpleName();
        }

        /**
         * {@inheritDoc}
         */
        public Ii generateIi(String id) {
            Ii ii = new Ii();
            ii.setExtension(id);
            ii.setRoot(CtepOrganizationImporter.CTEP_ORG_ROOT);
            return ii;
        }
    }

    /**
     * Generic implementation to import Organization or Person types.
     */
    class ImportHelper {
        private final File importFile;

        public ImportHelper(File file) {
            this.importFile = file;
        }
        void process(Importer callback) throws IOException {
            BufferedReader reader = getFileReader();
            String line = reader.readLine();
            List<String> passedRecords = new ArrayList<String>();
            List<String> skippedRecords = new ArrayList<String>();
            List<String> erroredRecords = new ArrayList<String>();
            try {
                while (line != null) {
                    line = line.trim();
                    if (StringUtils.isNotEmpty(line)) {
                        try {
                            Object imported = callback.invoke(callback.generateIi(line));
                            if (imported  != null) {
                                passedRecords.add(line);
                            } else {
                                skippedRecords.add(line);
                            }
                        } catch (Exception e) {
                            // Validation errors cause the transaction to be closed, so close and re-open the session
                            // to import the rest of the lines
                            HibernateHelper hh = PoHibernateUtil.getHibernateHelper();
                            hh.unbindAndCleanupSession();
                            hh.openAndBindSession();
                            LOG.error("Error importing " + callback.getType() + " with id:  " +  line, e);
                            erroredRecords.add(line);
                        }
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                LOG.error("An unexpected error occurred while reading the uploaded CTEP Import file, "
                        + "processing halted", e);
                ActionHelper.saveMessage("An unexpected error occurred while processing the uploaded "
                        + "CTEP Import file. Processing was halted prematurely on the line containing '" + line
                        + "'. Below are the results for processing up to this fatal error:");
            } finally  {
                addMessages(passedRecords, skippedRecords, erroredRecords);
            }
        }

        private BufferedReader getFileReader() throws FileNotFoundException {
            FileReader fileReader = new FileReader(this.importFile);
            return new BufferedReader(fileReader);
        }

        private void addMessages(List<String> passed, List<String> skipped, List<String> failed) {
            ActionHelper.saveMessage(passed.size() + " records successfully imported.");
            String separator = ", ";
            if (CollectionUtils.isNotEmpty(skipped)) {
                StringBuffer msg = new StringBuffer("The following line(s) did not correspond to a record in ctep, "
                        + "any record with one of these ctep id's was inactivated: ");
                msg.append(StringUtils.join(skipped, separator));
                ActionHelper.saveMessage(msg.toString());
            }
            if (CollectionUtils.isNotEmpty(failed)) {
                StringBuffer msg = new StringBuffer("An error occurred processing the following line(s): ");
                msg.append(StringUtils.join(failed, separator));
                ActionHelper.saveMessage(msg.toString());
            }
        }

    }

    /**
     * @return the file
     */
    @RequiredFieldValidator(key = "import.file.required", message = "Please select the file to import.")
    public File getFile() {
        return this.file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }
}
