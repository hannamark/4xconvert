/**
 * The software subject to this notice and license includes both human readable
 * source code form and machine readable, binary, object code form. The pa
 * Software was developed in conjunction with the National Cancer Institute
 * (NCI) by NCI employees and 5AM Solutions, Inc. (5AM). To the extent
 * government employees are authors, any rights in such works shall be subject
 * to Title 17 of the United States Code, section 105.
 *
 * This pa Software License (the License) is between NCI and You. You (or
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
 * its rights in the pa Software to (i) use, install, access, operate,
 * execute, copy, modify, translate, market, publicly display, publicly perform,
 * and prepare derivative works of the pa Software; (ii) distribute and
 * have distributed to and by third parties the pa Software and any
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
package gov.nih.nci.pa.action;

import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.util.MailManagerServiceLocal;
import gov.nih.nci.pa.service.util.PDQTrialUploadService;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaRegistry;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

/**
 * @author ludetc
 *
 */
public class PDQTrialLoadAction extends ActionSupport {

    private static final long serialVersionUID = 1L;
    private File upload;
    private String email;
    private static final int BYTE_SIZE = 1024;

    private MailManagerServiceLocal mailSvc = null;
    private PDQTrialUploadService pdqTrialUploadService = null;   
    private String uploadFileName = null;

    private MessageFormat htmlBody = null;
    private MessageFormat item = null;
    private MessageFormat line = null;

    private static final Logger LOG  = Logger.getLogger(PDQTrialLoadAction.class);

    private void initServices() {
        mailSvc = PaRegistry.getMailManagerService();
        pdqTrialUploadService = PaRegistry.getPDQTrialUploadService();        
    }

    @Override
    public String execute() {
        if (mailSvc == null) {
            initServices();
        }

        if (htmlBody == null) {
            initMessages();
        }

        Map<File, List<String>> reportMap = new HashMap<File, List<String>>();
        String username = ServletActionContext.getRequest().getRemoteUser();

        if (upload == null || !StringUtils.endsWith(uploadFileName, ".zip")) {
            addActionError("Please select a valid ZIP file to upload");
            return SUCCESS;
        }

        process(reportMap, username);

        String emailSubject = "PDQ Load Report : "
            + new SimpleDateFormat("MM / dd / yy", Locale.US).format(new Date());

        try {
            File reportFile = File.createTempFile("report-", ".html");
            FileUtils.writeStringToFile(reportFile, generateEmailBody(reportMap));
            File[] attachments = new File[1];
            attachments[0] = reportFile;

            mailSvc.sendMailWithAttachment(email, emailSubject, "Your load report is attached", attachments);
        } catch (IOException e) {
            addActionError("Unable to write report file before sending to user.");
            LOG.error(e.getMessage(), e);
        }

        return SUCCESS;
    }

    private void initMessages() {
        try {
            htmlBody = new MessageFormat(getText("pdq.html.template"));
            item = new MessageFormat(getText("pdq.html.reportItem"));
            line = new MessageFormat(getText("pdq.html.line"));
        } catch (Exception e) {
            // Junit will always get there. Swallow.
            LOG.error(e);
        }
    }

    private void process(Map<File, List<String>> reportMap, String username) {
        File tempFile = null;
        List<File> xmlFiles = null;
        try {
            tempFile = File.createTempFile("pdq-orig-", ".zip");
            FileUtils.copyFile(upload, tempFile);

            xmlFiles = extractZip(tempFile);
            processFiles(username, xmlFiles, reportMap);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            addActionError("Unable to unarchive uploaded file. ");
        } finally {
            tempFile.delete();
            if (xmlFiles != null) {
                for (File xmlFile : xmlFiles) {
                    xmlFile.delete();
                }
            }
        }
    }

    private void processFiles(String username, List<File> xmlFiles, Map<File, List<String>> reportMap) {
        for (File xmlFile : xmlFiles) {
            List<String> report = new ArrayList<String>();       
            report = pdqTrialUploadService.uploadTrialFromPDQXml(xmlFile, username);            
            reportMap.put(xmlFile, report);
        }
    }

    private String generateEmailBody(Map<File, List<String>> report) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<File, List<String>> entry : report.entrySet()) {
            StringBuilder lineSb = new StringBuilder();
            for (String s : entry.getValue()) {
                lineSb.append(item.format(ArrayUtils.add(new String[0], s)));
            }
            String[] params = new String[2];
            params[0] = entry.getKey().getName();
            params[1] = lineSb.toString();
            sb.append(line.format(params));
        }
        return htmlBody.format(ArrayUtils.add(new String[0], sb.toString()));
    }

    private List<File> extractZip(File input) throws IOException, PAException {
        LOG.info("Extracting Zip ...");

        List<File> extractedFiles = new ArrayList<File>();

        byte[] buf = new byte[BYTE_SIZE];
        ZipInputStream zipinputstream = null;
        ZipEntry zipentry;
        zipinputstream = new ZipInputStream(new FileInputStream(input));

        zipentry = zipinputstream.getNextEntry();
        while (zipentry != null) {
            String entryName = zipentry.getName();
            int n;
            FileOutputStream fileoutputstream;
            String directory;
            directory = PaEarPropertyReader.getPDQUploadPath();

            String fullpath = directory + File.separator + entryName;
            File newFile = new File(fullpath);

            LOG.info("extracting: " + fullpath);

            fileoutputstream = new FileOutputStream(fullpath);

            while ((n = zipinputstream.read(buf, 0, BYTE_SIZE)) > -1) {
                fileoutputstream.write(buf, 0, n);
            }

            extractedFiles.add(newFile);
            fileoutputstream.close();
            zipinputstream.closeEntry();
            zipentry = zipinputstream.getNextEntry();
        }
        zipinputstream.close();
        return extractedFiles;
    }

    /**
     * The uploaded file name.
     * @param uploadFileName file name.
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * Struts setter for email destination.
     * @param dest the email.
     */
    public void setMailDestination(String dest) {
        this.email = dest;
    }

    /**
     * Struts setter for the file.
     * @param upload the uploaded file.
     */
    public void setUpload(File upload) {
        this.upload = upload;
    }

    /**
     * Setter for MailManagerService.
     * @param service the service
     */
    public void setMailManagerService(MailManagerServiceLocal service) {
        mailSvc = service;
    }   

    /**
     * @param pdqTrialUploadService the pdqTrialUploadService to set
     */
    void setPdqTrialUploadService(PDQTrialUploadService pdqTrialUploadService) {
        this.pdqTrialUploadService = pdqTrialUploadService;
    }

    
}

