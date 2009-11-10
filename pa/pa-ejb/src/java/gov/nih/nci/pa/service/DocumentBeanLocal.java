/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.Document;
import gov.nih.nci.pa.enums.DocumentTypeCode;
import gov.nih.nci.pa.iso.convert.DocumentConverter;
import gov.nih.nci.pa.iso.dto.DocumentDTO;
import gov.nih.nci.pa.iso.dto.StudyProtocolDTO;
import gov.nih.nci.pa.iso.util.EdConverter;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.StConverter;
import gov.nih.nci.pa.service.exception.PADuplicateException;
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
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentBeanLocal extends 
 AbstractStudyIsoService<DocumentDTO, Document, DocumentConverter> implements DocumentServiceLocal {
  
  private static final Logger LOG  = Logger.getLogger(DocumentBeanLocal.class);
  private static final String MSG = "docDTO should not be null";

  private SessionContext ejbContext;

  @Resource
  void setSessionContext(SessionContext ctx) {
   this.ejbContext = ctx;
  }
 /**
  * @param studyProtocolIi Ii
  * @return DocumentDTO
  * @throws PAException PAException
  */
 @SuppressWarnings("unchecked")
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 public List<DocumentDTO> getDocumentsByStudyProtocol(Ii studyProtocolIi)
 throws PAException {
     if (PAUtil.isIiNull(studyProtocolIi)) {
         LOG.error(" studyProtocol Identifer should not be null ");
         throw new PAException(" studyProtocol Identifer should not be null ");
     }
     LOG.info("Entering getDocumentsByStudyProtocol");
     Session session = null;
     List<Document> queryList = new ArrayList<Document>();
     session = HibernateUtil.getCurrentSession();

     Query query = null;

     // step 1: form the hql
     String hql = " select doc "
         + " from Document doc "
         + " join doc.studyProtocol sp "
         + " where sp.id = " + IiConverter.convertToLong(studyProtocolIi)
         + " and doc.activeIndicator =  '" + Boolean.TRUE + "'";

     LOG.info(" query getDocumentsByStudyProtocol = " + hql);

     // step 2: construct query object
     query = session.createQuery(hql);
     queryList = query.list();

     ArrayList<DocumentDTO> resultList = new ArrayList<DocumentDTO>();
     for (Document bo : queryList) {
         resultList.add(new DocumentConverter().convertFromDomainToDto(bo));
     }
     LOG.info("Leaving getDocumentsByStudyProtocol");
     return resultList;
 }

 /**
  * @param docDTO DocumentDTO
  * @return DocumentDTO
  * @throws PAException PAException
  */
 @Override
 public DocumentDTO create(DocumentDTO docDTO)
 throws PAException {
     LOG.debug("Entering createTrialDocument ");
     validate(docDTO);
     enforceDuplicateDocument(docDTO);
     Session session = null;
     Document doc = new DocumentConverter().convertFromDtoToDomain(docDTO);
     java.sql.Timestamp now = new java.sql.Timestamp((new java.util.Date()).getTime());
     doc.setDateLastCreated(now);
     doc.setDateLastUpdated(now);
     if (ejbContext != null) {
         doc.setUserLastCreated(ejbContext.getCallerPrincipal().getName());
     }
     // create Protocol Obj
     /*StudyProtocol studyProtocol = new StudyProtocol();
     studyProtocol.setId(IiConverter.convertToLong(docDTO.getStudyProtocolIi()));

     doc.setStudyProtocol(studyProtocol);*/
     doc.setActiveIndicator(true);
     session = HibernateUtil.getCurrentSession();
     session.save(doc);
     session.flush();
     docDTO.setIdentifier(IiConverter.convertToDocumentIi(doc.getId()));
     saveFile(docDTO);
     LOG.debug("Leaving createStudyResourcing ");
     return docDTO;
 }

 /**
  * @param id Ii
  * @return DocumentDTO
  * @throws PAException PAException
  */
 @SuppressWarnings({"PMD" })
 @TransactionAttribute(TransactionAttributeType.SUPPORTS)
 @Override
 public DocumentDTO get(Ii id) throws PAException {

     LOG.info("Entering getTrialDocumentById");
     Session session = null;
     DocumentDTO docDTO = null;
     Document doc = null;
     List<Document> queryList = new ArrayList<Document>();
     session = HibernateUtil.getCurrentSession();

     Query query = null;

     // step 1: form the hql
     String hql = " select doc "
         + " from Document doc "
         + " where doc.id = " + IiConverter.convertToLong(id);

     LOG.info(" query getTrialDocumentById = " + hql);

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
             sb.append(File.separator).append(spDTO.getAssignedIdentifier().getExtension()).append(File.separator).
                 append(docDTO.getIdentifier().getExtension()).append('-').append(doc.getFileName());
             File downloadFile = new File(sb.toString());
             docDTO.setText(EdConverter.convertToEd(PAUtil.readInputStream(new FileInputStream(downloadFile))));

         } catch (FileNotFoundException fe) {
             throw new PAException(" File Not found " + fe.getLocalizedMessage(), fe);
         } catch (IOException io) {
             throw new PAException(" IO Exception" + io.getLocalizedMessage(), io);
         }

     }
     LOG.info("Leaving getTrialDocumentById");
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
     LOG.debug("Leaving updateTrialDocument ");
     return docRetDTO;
 }

 /**
  *
  * @param  documentIi documentIi
  * @throws PAException PAException
  */
 @SuppressWarnings("unchecked")
 @Override
 public void delete(Ii documentIi) throws PAException {

    if (PAUtil.isIiNull(documentIi)) {
         throw new PAException(MSG);
     }
     DocumentDTO docDTO = get(documentIi);
     checkTypeCodesForDelete(docDTO);
     LOG.debug("Entering deleteTrialDocumentByID ");
     updateObjectToInActive(docDTO);
     LOG.debug("Leaving deleteTrialDocumentByID ");
 }
 
 
 /**
  * creates a new record of studyprotocol by changing to new studyprotocol identifier.
  * @param fromStudyProtocolIi from where the study protocol objects to be copied  
  * @param toStudyProtocolIi to where the study protocol objects to be copied
  * @return map 
  * @throws PAException on error
  */
 @SuppressWarnings({"PMD" })
 @Override
 public Map<Ii , Ii> copy(Ii fromStudyProtocolIi , Ii toStudyProtocolIi) throws PAException {
     Map<Ii, Ii> map = super.copy(fromStudyProtocolIi, toStudyProtocolIi);
     List<DocumentDTO> dtos = getByStudyProtocol(fromStudyProtocolIi);
     Session session = HibernateUtil.getCurrentSession();
     String fromName = null;
     String toName = null;
     StudyProtocolBeanLocal spBean = new StudyProtocolBeanLocal();
     String nciIdentifier = spBean.getStudyProtocol(fromStudyProtocolIi).getAssignedIdentifier().getExtension();
     
     for (DocumentDTO dto : dtos) {
         Document doc = (Document) session.load(Document.class, IiConverter.convertToLong(dto.getIdentifier()));
         Ii toIi = PAUtil.containsIi(map, dto.getIdentifier());
         if (toIi != null) {
             // rename the file
             fromName = PAUtil.getDocumentFilePath(doc.getId(), doc.getFileName() , nciIdentifier);
             toName = PAUtil.getDocumentFilePath(Long.valueOf(toIi.getExtension()), doc.getFileName(),
                     nciIdentifier);
             InputStream in;
             try {
                 in = new FileInputStream(fromName);
                 OutputStream out = new FileOutputStream(toName);
                 byte[] bytes = PAUtil.readInputStream(in);
                 out.write(bytes);
             } catch (IOException e) {
                 throw new PAException("Error while copy file from " + fromName + " to " + toName , e);
             }
             
//             File fromFile = new File(fromName);
//             File toFile = new File(toName);
//             
//             if (!toFile.exists()) {
//              boolean success = fromFile.renameTo(toFile);
//              if (!success) {
//                 throw new PAException("Unable to rename the file from " + fromName + " to " + toName);
//              }
//             }
         }
         session.delete(doc);
     }
     return map;
 }    

 private void updateObjectToInActive(DocumentDTO docDTO) throws PAException {
     Session session = null;
     Document doc = null;
     session = HibernateUtil.getCurrentSession();

     // step 1: form the hql
     String hql = " select d from Document d"
         + " where d.id = " + IiConverter.convertToLong(docDTO.getIdentifier());
     // step 2: construct query object
     doc = (Document) session.createQuery(hql).list().get(0);
     // set the values from paramter
     doc.setActiveIndicator(false);
     doc.setInactiveCommentText(StConverter.convertToString(
             docDTO.getInactiveCommentText()));
     doc.setDateLastUpdated(new java.sql.Timestamp((new java.util.Date()).getTime()));
     if (ejbContext != null) {
         doc.setUserLastUpdated(ejbContext.getCallerPrincipal().getName());
     }
     session.update(doc);
     session.flush();
 }
 
 /**
  * @param docDTO DocumentDTO
  * @return Boolean
  * @throws PAException PAException
  * rename to 
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
                 throw new PADuplicateException("Document with selected type already exists on the trial. ");
                 //break;
             } else {
             result = true;
             }
         }
      }
     } else {
         result = true;
     }
     return result;
 }


 /**
  * Check type codes for delete.
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
     StringBuffer sb  = new StringBuffer(folderPath);
     sb.append(File.separator).append(sp.getAssignedIdentifier().getExtension());
     File f = new File(sb.toString());
     if (!f.exists()) {
         // create a new directory
         try {
             boolean md = f.mkdir();
             if (!md) {
                 throw new PAException("unable to create a folder  " + sb.toString());
             }
         } catch (SecurityException e) {
             throw new PAException("Security Exception while creating a folder "
                     + sb.toString() + e.getMessage(), e);
         }
     }

     // create the file
     sb.append(File.separator).append(docDTO.getIdentifier().getExtension()).
         append('-').append(docDTO.getFileName().getValue());
     try {
         File outFile = new File(sb.toString());
         FileOutputStream fos = new FileOutputStream(outFile);
         fos.write(docDTO.getText().getData());
         fos.flush();
         fos.close();
     } catch (IOException e) {
         throw new PAException("Error while creating directory  " + sb.toString() , e);
     }
 }
 

}
