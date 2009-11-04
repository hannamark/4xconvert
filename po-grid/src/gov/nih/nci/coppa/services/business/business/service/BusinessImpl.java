package gov.nih.nci.coppa.services.business.business.service;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.CorrelationNode;
import gov.nih.nci.coppa.po.grid.dto.transform.po.CorrelationNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.EntityNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.remote.InvokeBusinessEjb;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.BLTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.CDTransformer;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.IITransformer;
import gov.nih.nci.services.EntityNodeDto;
import gov.nih.nci.services.correlation.CorrelationNodeDTO;

import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.LogManager;

/** 
 * Impl of Business grid service.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class BusinessImpl extends BusinessImplBase {

    private static org.apache.log4j.Logger logger = LogManager.getLogger(BusinessImpl.class);
    private InvokeBusinessEjb businessService = new InvokeBusinessEjb();
	public BusinessImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.po.EntityNode getEntityByIdWithCorrelations(gov.nih.nci.coppa.po.Id id,gov.nih.nci.coppa.po.Cd[] players,gov.nih.nci.coppa.po.Cd[] scopers) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedEntityFault {
      try {
            Ii ii = IITransformer.INSTANCE.toDto(id);
            Cd[] playerArray = null;
            Cd[] scoperArray = null; 
            List<Cd> myPlayers = CDTransformer.INSTANCE.convert(players);
            if (myPlayers != null) {
                playerArray = myPlayers.toArray(new Cd[myPlayers.size()]);
            }
            List<Cd> myScopers = CDTransformer.INSTANCE.convert(scopers);
            if (myScopers != null) {
                scoperArray = myScopers.toArray(new Cd[myScopers.size()]);
            }
            EntityNodeDto entNodeDto = businessService.getEntityByIdWithCorrelations(ii, 
                    playerArray, scoperArray);
            return EntityNodeTransformer.INSTANCE.toXml(entNodeDto);
        } catch (Exception e) {
            logger.error("Error in getting entity node dto.", e);
            throw FaultUtil.reThrowRemote(e);
        }
  }

  public gov.nih.nci.coppa.po.CorrelationNode getCorrelationByIdWithEntities(gov.nih.nci.coppa.po.Id id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      try {
          Ii ii = IITransformer.INSTANCE.toDto(id);
          Bl myPlayer = BLTransformer.INSTANCE.toDto(player);
          Bl myScoper = BLTransformer.INSTANCE.toDto(scoper);
         
          CorrelationNodeDTO corrNodeDto = businessService
              .getCorrelationByIdWithEntities(ii, myPlayer.getValue(), myScoper.getValue());
          return CorrelationNodeTransformer.INSTANCE.toXml(corrNodeDto);
      } catch (Exception e) {
          logger.error("Error in getting correlation node dto.", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.po.CorrelationNode[] getCorrelationsByIdsWithEntities(gov.nih.nci.coppa.po.Id[] id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      try {       
          List<Ii> ids = IITransformer.INSTANCE.convert(id);
          Ii[] idsArray = null;
          if (ids != null) {
              idsArray = ids.toArray(new Ii[ids.size()]);
          }
          Bl myPlayer = BLTransformer.INSTANCE.toDto(player);
          Bl myScoper = BLTransformer.INSTANCE.toDto(scoper);
         
          CorrelationNodeDTO[] corrNodeDtos = businessService
              .getCorrelationsByIdsWithEntities(idsArray, myPlayer.getValue(), myScoper.getValue());
          
          CorrelationNode[] corrNodes = new CorrelationNode[corrNodeDtos.length];
          int j = 0;
          for (CorrelationNodeDTO corrNodeDto : corrNodeDtos) {
              corrNodes[j++] = CorrelationNodeTransformer.INSTANCE.toXml(corrNodeDto);
          }
          return corrNodes;
      
      } catch (Exception e) {
          logger.error("Error in getting correlation node dto.", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.po.CorrelationNode[] getCorrelationsByPlayerIdsWithEntities(gov.nih.nci.coppa.po.Cd cd,gov.nih.nci.coppa.po.Id[] id,gov.nih.nci.coppa.po.Bl player,gov.nih.nci.coppa.po.Bl scoper) throws RemoteException, gov.nih.nci.coppa.po.faults.NullifiedRoleFault {
      try {       
          List<Ii> ids = IITransformer.INSTANCE.convert(id);
          Ii[] idsArray = null;
          if (ids != null) {
              idsArray = ids.toArray(new Ii[ids.size()]);
          }
          Cd myType = CDTransformer.INSTANCE.toDto(cd);
          Bl myPlayer = BLTransformer.INSTANCE.toDto(player);
          Bl myScoper = BLTransformer.INSTANCE.toDto(scoper);
         
          CorrelationNodeDTO[] corrNodeDtos = businessService
              .getCorrelationsByPlayerIdsWithEntities(myType, idsArray, myPlayer.getValue(), myScoper.getValue());
          
          CorrelationNode[] corrNodes = new CorrelationNode[corrNodeDtos.length];
          int j = 0;
          for (CorrelationNodeDTO corrNodeDto : corrNodeDtos) {
              corrNodes[j++] = CorrelationNodeTransformer.INSTANCE.toXml(corrNodeDto);
          }
          return corrNodes;
      
      } catch (Exception e) {
          logger.error("Error in getting correlation node dto.", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

