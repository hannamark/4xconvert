package gov.nih.nci.coppa.services.pa.documentservice.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.DocumentTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeDocumentEjb;
import gov.nih.nci.pa.iso.dto.DocumentDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Implementation of the DocumentService. Dispatches to the remote EJBs and the Transformers.
 *
 * @author Hugh Reinhart
 */
public class DocumentServiceImpl extends DocumentServiceImplBase {

  private static final Logger logger = LogManager.getLogger(DocumentServiceImpl.class);
  private final InvokeDocumentEjb documentService = new InvokeDocumentEjb();

  public DocumentServiceImpl() throws RemoteException {
    super();
  }

  public gov.nih.nci.coppa.services.pa.Document[] getDocumentsByStudyProtocol(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          List<DocumentDTO> results = documentService.getDocumentsByStudyProtocol(iiDto);
          if (results == null) {
              return null;
          }
          return DocumentTransformer.INSTANCE.convert(results);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.Document get(gov.nih.nci.coppa.services.pa.Id id) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          Ii iiDto = IITransformer.INSTANCE.toDto(id);
          DocumentDTO result = documentService.get(iiDto);
          if (result == null) {
              return null;
          }
          return DocumentTransformer.INSTANCE.toXml(result);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.Document create(gov.nih.nci.coppa.services.pa.Document document) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          DocumentDTO result = documentService.create(DocumentTransformer.INSTANCE.toDto(document));
          if (result == null) {
              return null;
          }
          return DocumentTransformer.INSTANCE.toXml(result);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.pa.Document update(gov.nih.nci.coppa.services.pa.Document document) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          DocumentDTO result = documentService.update(DocumentTransformer.INSTANCE.toDto(document));
          if (result == null) {
              return null;
          }
          return DocumentTransformer.INSTANCE.toXml(result);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public void delete(gov.nih.nci.coppa.services.pa.Document document) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault {
      try {
          documentService.delete(IITransformer.INSTANCE.toDto(document.getIdentifier()));
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }
}

