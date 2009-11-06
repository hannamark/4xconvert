package gov.nih.nci.coppa.services.business.business.service;

import gov.nih.nci.coppa.iso.Bl;
import gov.nih.nci.coppa.iso.Cd;
import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.coppa.po.grid.dto.transform.po.CorrelationNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.EntityNodeTransformer;
import gov.nih.nci.coppa.po.grid.dto.transform.po.faults.FaultUtil;
import gov.nih.nci.coppa.po.grid.remote.InvokeBusinessEjb;
import gov.nih.nci.coppa.services.LimitOffset;
import gov.nih.nci.coppa.services.grid.dto.transform.DtoTransformException;
import gov.nih.nci.coppa.services.grid.dto.transform.common.LimitOffsetTransformer;
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
            Cd[] playerArray = transformFromPoCd(players);
            Cd[] scoperArray = transformFromPoCd(scopers);
           
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
              .getCorrelationByIdWithEntities(ii, myPlayer, myScoper);
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
         
          List<CorrelationNodeDTO> corrNodeDtos = businessService
              .getCorrelationsByIdsWithEntities(idsArray, myPlayer, myScoper);
          
          return CorrelationNodeTransformer.INSTANCE.convert(corrNodeDtos);
      
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
         
          List<CorrelationNodeDTO> corrNodeDtos = businessService
              .getCorrelationsByPlayerIdsWithEntities(myType, idsArray, myPlayer, myScoper);
          
          return CorrelationNodeTransformer.INSTANCE.convert(corrNodeDtos);
      
      } catch (Exception e) {
          logger.error("Error in getting correlation node dto.", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.po.CorrelationNode[] searchCorrelationsWithEntities(gov.nih.nci.coppa.po.CorrelationNode correlationNode,gov.nih.nci.coppa.po.Bl players,gov.nih.nci.coppa.po.Bl scopers,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          Bl myPlayer = BLTransformer.INSTANCE.toDto(players);
          Bl myScoper = BLTransformer.INSTANCE.toDto(scopers);
          CorrelationNodeDTO corrNodeIso = CorrelationNodeTransformer.INSTANCE.toDto(correlationNode);
          List<CorrelationNodeDTO> results = businessService
              .searchCorrelationsWithEntities(corrNodeIso, myPlayer, myScoper, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          logger.debug("CorrelationNode(s) found from COPPA:" + results.size());
          return CorrelationNodeTransformer.INSTANCE.convert(results);
          
      } catch (Exception e) {
          logger.error("Error in searching CorrelationNode(s).", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }
  
  private Cd[] transformFromPoCd(gov.nih.nci.coppa.po.Cd[] items) throws DtoTransformException {
      Cd[] returnArray = null; 
      List<Cd> myItems = CDTransformer.INSTANCE.convert(items);
      if (myItems != null) {
          returnArray = myItems.toArray(new Cd[myItems.size()]);
      }
      
      return returnArray;
  }

  public gov.nih.nci.coppa.po.EntityNode[] searchEntitiesWithCorrelations(gov.nih.nci.coppa.po.EntityNode entityNode,gov.nih.nci.coppa.po.Cd[] players,gov.nih.nci.coppa.po.Cd[] scopers,gov.nih.nci.coppa.common.LimitOffset limitOffset) throws RemoteException, gov.nih.nci.coppa.common.faults.TooManyResultsFault {
      try {
          LimitOffset limitOffsetDTO = LimitOffsetTransformer.INSTANCE.toDto(limitOffset);
          Cd[] playerArray = transformFromPoCd(players);
          Cd[] scoperArray = transformFromPoCd(scopers);
          EntityNodeDto entityNodeIso = EntityNodeTransformer.INSTANCE.toDto(entityNode);
          List<EntityNodeDto> results = businessService
              .searchEntitiesWithCorrelations(entityNodeIso, playerArray, scoperArray, limitOffsetDTO);
          if (results == null) {
              return null;
          }
          logger.debug("EntityNode(s) found from COPPA:" + results.size());
          return EntityNodeTransformer.INSTANCE.convert(results);
      } catch (Exception e) {
          logger.error("Error in searching EntityNode(s).", e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

