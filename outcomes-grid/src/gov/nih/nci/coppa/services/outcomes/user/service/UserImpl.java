package gov.nih.nci.coppa.services.outcomes.user.service;

import gov.nih.nci.accrual.dto.UserDto;
import gov.nih.nci.coppa.iso.St;
import gov.nih.nci.coppa.services.grid.dto.transform.iso.STTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.UserTransformer;
import gov.nih.nci.coppa.services.outcomes.grid.dto.transform.faults.FaultUtil;
import gov.nih.nci.coppa.services.outcomes.grid.remote.InvokeUserEjb;
import gov.nih.nci.coppa.services.outcomes.user.service.globus.UserAuthorization;

import java.rmi.RemoteException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/** 
 * User Impl for the User grid service. The get and update User methods are secure while the create method
 * is not, pulling the login name from the grid server to pass onto the ejb service.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class UserImpl extends UserImplBase {

    private static final Logger logger = LogManager.getLogger(UserImpl.class);
    private final InvokeUserEjb userService = new InvokeUserEjb();

	public UserImpl() throws RemoteException {
		super();
	}
	
  public gov.nih.nci.coppa.services.outcomes.User getUser(gov.nih.nci.coppa.services.outcomes.ST loginName) throws RemoteException {
      try {
          St stDto = STTransformer.INSTANCE.toDto(loginName);
          UserDto userDto = userService.getUser(stDto);
          return UserTransformer.INSTANCE.toXml(userDto);
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.User createUser(gov.nih.nci.coppa.services.outcomes.User user) throws RemoteException {
      try {
          UserDto userDto = UserTransformer.INSTANCE.toDto(user);
          St loginName = new St();
          loginName.setValue(UserAuthorization.getCallerIdentity());
          userDto.setLoginName(loginName);
          return UserTransformer.INSTANCE.toXml(userService.createUser(userDto));
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

  public gov.nih.nci.coppa.services.outcomes.User updateUser(gov.nih.nci.coppa.services.outcomes.User user) throws RemoteException {
      try {
          UserDto userDto = UserTransformer.INSTANCE.toDto(user);
          return UserTransformer.INSTANCE.toXml(userService.updateUser(userDto));
      } catch (Exception e) {
          logger.error(e.getMessage(), e);
          throw FaultUtil.reThrowRemote(e);
      }
  }

}

