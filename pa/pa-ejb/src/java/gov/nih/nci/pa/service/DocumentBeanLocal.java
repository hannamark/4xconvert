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
package gov.nih.nci.pa.service;

import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.convert.DocumentConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
import gov.nih.nci.pa.service.util.CSMUserService;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
import gov.nih.nci.pa.util.PaEarPropertyReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 */
@Stateless
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentBeanLocal extends AbstractStudyIsoService<DocumentDTO, Document, DocumentConverter> implements
        DocumentServiceLocal {

    private static final Logger LOG = Logger.getLogger(DocumentBeanLocal.class);
    private SessionContext ejbContext;
    /**
     * Set the invocation context.
     * @param ctx EJB context
     */
    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ejbContext = ctx;
    }

    /**
     * @param studyProtocolIi Ii
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi) throws PAException {
        if (PAUtil.isIiNull(studyProtocolIi)) {
            LOG.error(" studyProtocol Identifer should not be null ");
            throw new PAException(" studyProtocol Identifer should not be null ");
        }
        Session session = null;
        List<Document> queryList = new ArrayList<Document>();
        session = HibernateUtil.getCurrentSession();

        Query query = null;

        String hql = "select doc from Document doc join doc.studyProtocol sp"
                        + " where sp.id = :spId and doc.activeIndicator = true";
        LOG.debug("query getDocumentsByStudyProtocol = " + hql);

        query = session.createQuery(hql);
        query.setLong("spId", IiConverter.convertToLong(studyProtocolIi));
        queryList = query.list();

        ArrayList<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        for (Document bo : queryList) {
            resultList.add(new DocumentConverter().convertFromDomainToDto(bo));
        }
        return resultList;
    }

    /**
     * @param docDTO DocumentDTO
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @Override
    public DocumentDTO create(DocumentDTO docDTO) throws PAException {
        validate(docDTO);
        enforceDuplicateDocument(docDTO);
        Session session = null;
        Document doc = new DocumentConverter().convertFromDtoToDomain(docDTO);
        java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
        doc.setDateLastCreated(now);
        doc.setUserLastCreated(CSMUserService.getInstance().lookupUser(ejbContext));
        
        doc.setActiveIndicator(true);
        session = HibernateUtil.getCurrentSession();
        session.save(doc);
        session.flush();
        docDTO.setIdentifier(IiConverter.convertToDocumentIi(doc.getId()));
        saveFile(docDTO);
        return docDTO;
    }

    /**
     * @param id Ii
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @SuppressWarnings("unchecked")
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    @Override
    public DocumentDTO get(Ii id) throws PAException {
        Session session = null;
        DocumentDTO docDTO = null;
        Document doc = null;
        List<Document> queryList = new ArrayList<Document>();
        session = HibernateUtil.getCurrentSession();

        Query query = null;

        // step 1: form the hql
        String hql = " select doc " + " from Document doc " + " where doc.id = " + IiConverter.convertToLong(id);

        LOG.debug(" query getTrialDocumentById = " + hql);

        // step 2: construct query object
        query = session.createQuery(hql);
        queryList = query.list();

        if (!queryList.isEmpty()) {
            DocumentConverter dc = new DocumentConverter();
            doc = queryList.get(0);
            docDTO = dc.convertFromDomainToDto(doc);
            try {
                StringBuffer sb = new StringBuffer(PaEarPropertyReader.getDocUploadPath());
                StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
                StudyProtocolDTO spDTO = spBean.getStudyProtocol(docDTO.getStudyProtocolIdentifier());
                sb.append(File.separator).append(PAUtil.getAssignedIdentifierExtension(spDTO)).append(File.separator)
                  .append(docDTO.getIdentifier().getExtension()).append('-').append(doc.getFileName());
                File downloadFile = new File(sb.toString());
                docDTO.setText(EdConverter.convertToEd(PAUtil.readInputStream(new FileInputStream(downloadFile))));

            } catch (FileNotFoundException fe) {
                throw new PAException(" File Not found " + fe.getLocalizedMessage(), fe);
            } catch (IOException io) {
                throw new PAException(" IO Exception" + io.getLocalizedMessage(), io);
            }

        }
        return docDTO;
    }

    /**
     *
     * @param docDTO DocumentDTO
     * @return DocumentDTO
     * @throws PAException PAException
     */
    @Override
    public DocumentDTO update(DocumentDTO docDTO) throws PAException {

        validate(docDTO);
        DocumentDTO docRetDTO = null;
        docDTO.setInactiveCommentText(StConverter.convertToSt("A new record will be created"));
        docDTO.setInactiveCommentText(null);
        updateObjectToInActive(docDTO);
        docRetDTO = create(docDTO);
        return docRetDTO;
    }

    /**
     *
     * @param documentIi documentIi
     * @throws PAException PAException
     */
    @Override
    public void delete(Ii documentIi) throws PAException {
        if (PAUtil.isIiNull(documentIi)) {
            throw new PAException("docDTO should not be null");
        }
        DocumentDTO docDTO = get(documentIi);
        checkTypeCodesForDelete(docDTO);
        updateObjectToInActive(docDTO);
    }

    /**
     * creates a new record of studyprotocol by changing to new studyprotocol identifier.
     *
     * @param fromStudyProtocolIi from where the study protocol objects to be copied
     * @param toStudyProtocolIi to where the study protocol objects to be copied
     * @return map
     * @throws PAException on error
     */
    @Override
    public Map<Ii, Ii> copy(Ii fromStudyProtocolIi, Ii toStudyProtocolIi) throws PAException {
        Map<Ii, Ii> map = super.copy(fromStudyProtocolIi, toStudyProtocolIi);
        List<DocumentDTO> dtos = getByStudyProtocol(fromStudyProtocolIi);
        Session session = HibernateUtil.getCurrentSession();
        String fromName = null;
        String toName = null;
        StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
        String nciIdentifier = PAUtil.getAssignedIdentifierExtension(spBean.getStudyProtocol(fromStudyProtocolIi));

        for (DocumentDTO dto : dtos) {
            Document doc = (Document) session.load(Document.class, IiConverter.convertToLong(dto.getIdentifier()));
            Ii toIi = PAUtil.containsIi(map, dto.getIdentifier());
            if (toIi != null) {
                // rename the file
                fromName = PAUtil.getDocumentFilePath(doc.getId(), doc.getFileName(), nciIdentifier);
                toName =
                        PAUtil.getDocumentFilePath(Long.valueOf(toIi.getExtension()), doc.getFileName(), nciIdentifier);
                InputStream in;
                try {
                    in = new FileInputStream(fromName);
                    OutputStream out = new FileOutputStream(toName);
                    byte[] bytes = PAUtil.readInputStream(in);
                    out.write(bytes);
                } catch (IOException e) {
                    throw new PAException("Error while copy file from " + fromName + " to " + toName, e);
                }
            }
            session.delete(doc);
        }
        return map;
    }

    private void updateObjectToInActive(DocumentDTO docDTO) throws PAException {
        Session session = null;
        Document doc = null;
        session = HibernateUtil.getCurrentSession();

        String hql = " select d from Document d where d.id = :id";
        Query query = session.createQuery(hql);
        query.setLong("id", IiConverter.convertToLong(docDTO.getIdentifier()));
        doc = (Document) query.uniqueResult();
        // set the values from parameter
        doc.setActiveIndicator(false);
        doc.setInactiveCommentText(StConverter.convertToString(docDTO.getInactiveCommentText()));
        doc.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
        doc.setUserLastUpdated(CSMUserService.getInstance().lookupUser(ejbContext));
        
        session.update(doc);
        session.flush();
    }

    /**
     * @param docDTO DocumentDTO
     * @return Boolean
     * @throws PAException PAException rename to
     */
    private Boolean enforceDuplicateDocument(DocumentDTO docDTO) throws PAException {
        Boolean result = false;
        List<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
        resultList = getDocumentsByStudyProtocol(docDTO.getStudyProtocolIdentifier());
        if (!(resultList.isEmpty())) {
            for (DocumentDTO check : resultList) {
                if (!check.getTypeCode().getCode().equals(DocumentTypeCode.OTHER.getCode())) {
                    if (check.getTypeCode().getCode().equals(docDTO.getTypeCode().getCode())) {
                        result = false;
                        throw new PADuplicateException("Document with selected type already exists on the trial.");
                        // break;
                    }
                    result = true;
                }
            }
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Check type codes for delete.
     *
     * @param docDTO the doc dto
     * @return the boolean
     * @throws PAException the PA exception
     */
    private void checkTypeCodesForDelete(DocumentDTO docDTO) throws PAException {
        if (docDTO.getTypeCode().getCode().equals(DocumentTypeCode.PROTOCOL_DOCUMENT.getCode())
                || docDTO.getTypeCode().getCode().equals(DocumentTypeCode.IRB_APPROVAL_DOCUMENT.getCode())
                || docDTO.getTypeCode().getCode().equals(DocumentTypeCode.CHANGE_MEMO_DOCUMENT.getCode())) {
            throw new PAException("Document with selected type cannot be deleted. ");
        }
    }

    private void saveFile(DocumentDTO docDTO) throws PAException {
        if (docDTO.getText() == null || docDTO.getText().getData() == null) {
            throw new PAException("Document data cannot be null ");
        }
        String folderPath = PaEarPropertyReader.getDocUploadPath();
        StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
        StudyProtocolDTO sp = spBean.getStudyProtocol(docDTO.getStudyProtocolIdentifier());
        StringBuffer sb = new StringBuffer(folderPath);
        sb.append(File.separator).append(PAUtil.getAssignedIdentifier(sp).getExtension());
        File f = new File(sb.toString());
        if (!f.exists()) {
            // create a new directory
            createDirectory(f);
        }

        // create the file
        sb.append(File.separator).append(docDTO.getIdentifier().getExtension()).append('-').append(
                docDTO.getFileName().getValue());
        try {
            File outFile = new File(sb.toString());
            FileOutputStream fos = new FileOutputStream(outFile);
            fos.write(docDTO.getText().getData());
            fos.flush();
            fos.close();
        } catch (IOException e) {
            throw new PAException("Error while creating directory  " + sb.toString(), e);
        }
    }

    private void createDirectory(File f) throws PAException {
        try {
            boolean md = f.mkdir();
            if (!md) {
                throw new PAException("unable to create folder: " + f.getAbsolutePath());
            }
        } catch (SecurityException e) {
            throw new PAException("Security Exception while creating folder " + f.getAbsolutePath() + ": "
                    + e.getMessage(), e);
        }
    }

}
