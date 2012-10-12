package gov.nih.nci.coppa.services.pa.regulatoryauthorityservice.service;

import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
import gov.nih.nci.coppa.services.pa.RegulatoryAuthority;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.RegulatoryAuthorityTransformer;
import gov.nih.nci.coppa.services.pa.grid.dto.pa.faults.FaultUtil;
import gov.nih.nci.coppa.services.pa.grid.remote.InvokeRegulatoryAuthorityEjb;
import gov.nih.nci.pa.iso.dto.RegulatoryAuthorityDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * Impl for RegulatoryAuthority service.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class RegulatoryAuthorityServiceImpl extends RegulatoryAuthorityServiceImplBase {

    private static final Logger logger = LogManager.getLogger(RegulatoryAuthorityServiceImpl.class);
    private final InvokeRegulatoryAuthorityEjb regAuthService = new InvokeRegulatoryAuthorityEjb();

    static {
        gov.nih.nci.coppa.services.pa.grid.PAGridUtils.initIso21090Transformers();
    }


	public RegulatoryAuthorityServiceImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.services.pa.RegulatoryAuthority[] search(gov.nih.nci.coppa.services.pa.RegulatoryAuthority regulatoryAuthority,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.services.pa.faults.PAFault, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          RegulatoryAuthorityDTO org = RegulatoryAuthorityTransformer.INSTANCE.toDto(regulatoryAuthority);
          List<RegulatoryAuthorityDTO> results = regAuthService.search(org, limitOffsetDTO);
          if (results == null) {
              return null;
          }
         
          RegulatoryAuthority[] returnResults = RegulatoryAuthorityTransformer.INSTANCE.convert(results);
         
          return returnResults;
      } catch (Exception e) {
          logger.error("SEARCH:REGULATORYAUTHORITY", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

