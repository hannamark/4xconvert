package gov.nih.nci.pa.service.util;

import gov.nih.nci.coppa.services.interceptor.RemoteAuthorizationInterceptor;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.dto.StudyProtocolQueryCriteria;
import gov.nih.nci.pa.dto.StudyProtocolQueryDTO;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.PaEarPropertyReader;
import gov.nih.nci.pa.util.PaHibernateSessionInterceptor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.SystemUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * @author Denis G. Krylov
 * 
 */
@Stateless
@SuppressWarnings("unchecked")
@Interceptors({ RemoteAuthorizationInterceptor.class,
        PaHibernateSessionInterceptor.class })
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CTGovUploadServiceBeanLocal implements CTGovUploadServiceLocal {

    private static final Logger LOG = Logger
            .getLogger(CTGovUploadServiceBeanLocal.class);

    private static final int TIMEOUT = 120 * 1000;
    static {
        LOG.setLevel(Level.INFO);
    }

    @EJB
    private CTGovXmlGeneratorServiceLocal generatorServiceLocal;

    @EJB
    private ProtocolQueryServiceLocal queryServiceLocal;

    @EJB
    private LookUpTableServiceRemote lookUpTableService;

    private PAServiceUtils paServiceUtils = new PAServiceUtils();

    private List<String> getAbstractedStatusCodes() {
        List<String> list = new ArrayList<String>();
        for (DocumentWorkflowStatusCode code : DocumentWorkflowStatusCode
                .values()) {
            if (code.isAbstractedOrAbove()) {
                list.add(code.getCode());
            }
        }
        return list;
    }

    private List<Ii> getTrialIdsForUpload() throws PAException {
        StudyProtocolQueryCriteria criteria = new StudyProtocolQueryCriteria();
        criteria.setNciSponsored(true);
        criteria.setDocumentWorkflowStatusCodes(getAbstractedStatusCodes());
        criteria.setTrialCategory("n");
        criteria.setCtroOverride(false);
        criteria.setStudyProtocolType("InterventionalStudyProtocol");

        List<Ii> ids = new ArrayList<Ii>();
        List<String> ccrLeadIDs = getCCRTrialLeadOrgIds();
        for (StudyProtocolQueryDTO dto : queryServiceLocal
                .getStudyProtocolByCriteria(criteria)) {
            if (!ccrLeadIDs.contains(dto.getLocalStudyProtocolIdentifier())) {
                ids.add(IiConverter.convertToStudyProtocolIi(dto
                        .getStudyProtocolId()));
            }
        }
        return ids;
    }

    private List<String> getCCRTrialLeadOrgIds() throws PAException {
        String ccrTrialList = lookUpTableService
                .getPropertyValue("ctep.ccr.trials");
        return Arrays.asList(ccrTrialList.replaceAll("\\s+", "").split(","));
    }

    private String hidePassword(URL url) {
        return url.toString().replaceAll(":[^\\:]+@", ":*****@");
    }

    /**
     * @param tempDir
     * @param id
     * @throws PAException
     * @throws IOException
     */
    private void saveXmlToFile(File tempDir, Ii id) throws IOException {
        try {
            String xml = generatorServiceLocal.generateCTGovXml(id,
                    CTGovXmlGeneratorOptions.USE_SUBMITTERS_PRS);
            String nciID = StringUtils
                    .defaultIfEmpty(paServiceUtils.getTrialNciId(IiConverter
                            .convertToLong(id)), UUID.randomUUID().toString());
            File xmlFile = new File(tempDir, nciID.concat(".xml"));
            FileUtils.writeStringToFile(xmlFile, xml, "UTF-8");
        } catch (PAException e) {
            LOG.error("Unable to produce XML for trial ID=" + id.getExtension()
                    + ". This trial is excluded from the FTP upload.");
            LOG.error(ExceptionUtils.getFullStackTrace(e));
        }
    }

    /**
     * @param generatorServiceLocal
     *            the generatorServiceLocal to set
     */
    public void setGeneratorServiceLocal(
            CTGovXmlGeneratorServiceLocal generatorServiceLocal) {
        this.generatorServiceLocal = generatorServiceLocal;
    }

    /**
     * @param lookUpTableService
     *            the lookUpTableService to set
     */
    public void setLookUpTableService(
            LookUpTableServiceRemote lookUpTableService) {
        this.lookUpTableService = lookUpTableService;
    }

    /**
     * @param queryServiceLocal
     *            ProtocolQueryServiceLocal
     */
    public void setQueryServiceLocal(ProtocolQueryServiceLocal queryServiceLocal) {
        this.queryServiceLocal = queryServiceLocal;
    }

    private void uploadFileToCTGov(File file, URL ftpURL) {
        OutputStream os = null;
        InputStream fileIs = null;
        try {
            URL completeUploadURL = new URL(ftpURL, file.getName());
            URLConnection connection = completeUploadURL.openConnection();
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setDoOutput(true);

            os = connection.getOutputStream();
            fileIs = FileUtils.openInputStream(file);
            IOUtils.copy(fileIs, os);
            os.flush();
            LOG.info(file.getName() + " uploaded to "
                    + hidePassword(completeUploadURL));

        } catch (Exception e) {
            LOG.error("Upload of " + file.getName() + " failed due to "
                    + e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileIs);
            IOUtils.closeQuietly(os);
        }

    }

    @Override
    public void uploadToCTGov() {
        LOG.info("Nightly CT.Gov FTP Upload kicked off.");
        try {
            List<Ii> trialIDs = getTrialIdsForUpload();
            LOG.info("Got " + trialIDs.size() + " trials to upload.");
            uploadToCTGov(trialIDs);
            LOG.info("Done.");
        } catch (Exception e) {
            LOG.error("CT.Gov FTP Upload has failed due to " + e);
            LOG.error(e, e);
        }

    }

    private void uploadToCTGov(File dir, URL ftpURL) throws PAException {
        for (File file : dir.listFiles()) {
            uploadFileToCTGov(file, ftpURL);
        }
    }

    private void uploadToCTGov(List<Ii> trialIDs) throws PAException,
            IOException {
        URL ftpURL = new URL(PaEarPropertyReader.getCTGovFtpURL());
        LOG.info("CT.Gov FTP is " + hidePassword(ftpURL));
        File tempDir = new File(SystemUtils.JAVA_IO_TMPDIR, UUID.randomUUID()
                .toString());
        try {
            if (tempDir.mkdirs()) {
                for (Ii id : trialIDs) {
                    saveXmlToFile(tempDir, id);
                }
                uploadToCTGov(tempDir, ftpURL);
            } else {
                throw new PAException(
                        "Could not create a temporary directory for unknown reason. Aborting upload.");
            }
        } finally {
            FileUtils.deleteQuietly(tempDir);
        }
    }

    /**
     * @param paServiceUtils
     *            the paServiceUtils to set
     */
    public void setPaServiceUtils(PAServiceUtils paServiceUtils) {
        this.paServiceUtils = paServiceUtils;
    }

}
