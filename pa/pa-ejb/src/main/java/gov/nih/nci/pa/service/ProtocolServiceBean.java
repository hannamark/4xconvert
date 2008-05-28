package gov.nih.nci.pa.service;

import gov.nih.nci.pa.dao.hibernate.ProtocolServiceImpl;

import javax.ejb.Stateless;

/**
 * @author Hugh Reinhart
 *
 */
@Stateless
public class ProtocolServiceBean extends ProtocolServiceImpl implements ProtocolServiceLocal, ProtocolServiceRemote {

}
