/**
 * 
 */
package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.RegulatoryAuthority;
import gov.nih.nci.pa.iso.convert.RegulatoryAuthorityConverter;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;

import org.apache.log4j.Logger;

/**
 * @author asharma
 *
 */
public class RegulatoryAuthorityBeanLocal 
extends AbstractBaseIsoService<RegulatoryAuthorityDTO, RegulatoryAuthority, RegulatoryAuthorityConverter>
implements RegulatoryAuthorityServiceLocal {
  
  private static final Logger LOG  = Logger.getLogger(RegulatoryAuthorityBeanLocal.class);
  private static String errMsgMethodNotImplemented = "Method not yet implemented.";



 /**
  * @param dto RegulatoryAuthorityDTO
  * @return RegulatoryAuthorityDTO
  * @throws PAException PAException
  */
  @Override 
  public RegulatoryAuthorityDTO update(RegulatoryAuthorityDTO dto) throws PAException {
   LOG.error(errMsgMethodNotImplemented);
   throw new PAException(errMsgMethodNotImplemented);
  }


 /**
  * @param dto RegulatoryAuthorityDTO
  * @return RegulatoryAuthorityDTO
  * @throws PAException PAException
  */
  @Override 
  public RegulatoryAuthorityDTO create(RegulatoryAuthorityDTO dto) throws PAException {
   LOG.error(errMsgMethodNotImplemented);
   throw new PAException(errMsgMethodNotImplemented);
 }

 /**
  * @param ii Ii
  * @throws PAException PAException
  */
 @Override 
 public void delete(Ii ii) throws PAException {
  LOG.error(errMsgMethodNotImplemented);
  throw new PAException(errMsgMethodNotImplemented);
 }

}
