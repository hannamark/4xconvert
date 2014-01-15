package gov.nih.nci.security.cgmm.helper.impl;


import gov.nih.nci.cagrid.authentication.stubs.types.AuthenticationProviderFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InsufficientAttributeFault;
import gov.nih.nci.cagrid.authentication.stubs.types.InvalidCredentialFault;
import gov.nih.nci.cagrid.opensaml.SAMLAssertion;
import gov.nih.nci.security.cgmm.exceptions.CGMMAuthenticationURLException;
import gov.nih.nci.security.cgmm.exceptions.CGMMGridAuthenticationServiceException;
import gov.nih.nci.security.cgmm.exceptions.CGMMInputException;
import gov.nih.nci.security.cgmm.helper.AuthenticationServiceHelper;

import java.rmi.RemoteException;

import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;
import org.cagrid.gaards.authentication.BasicAuthentication;
import org.cagrid.gaards.authentication.client.AuthenticationClient;

public class AuthenticationServiceHelperImpl implements AuthenticationServiceHelper
{

    static final Logger LOG = Logger.getLogger(AuthenticationServiceHelperImpl.class.getName());
    
	public AuthenticationServiceHelperImpl()
	{
		super();
	}

	public SAMLAssertion authenticate(String authenticationServiceURL, String userName, String password) throws  CGMMInputException, CGMMGridAuthenticationServiceException, CGMMAuthenticationURLException
	{
		SAMLAssertion samlAssertion = null;
		 // Create credential
        BasicAuthentication auth = new BasicAuthentication();
        auth.setUserId(userName);
        auth.setPassword(password);
        

		AuthenticationClient authenticationClient;
		try
		{
		    authenticationClient = new AuthenticationClient(
		            authenticationServiceURL);
		} 
		catch (MalformedURIException e)
		{
		    LOG.error(e, e);
			throw new CGMMAuthenticationURLException(CGMMMessages.EXCEPTION_INVALID_AUTHENTICATION_URL+ e.getMessage());
		} 
		catch (RemoteException e)
		{
		    LOG.error(e, e);
			throw new CGMMGridAuthenticationServiceException(CGMMMessages.EXCEPTION_GRID_AUTH_SERVICE_UNAVAILABLE + e.getMessage());
		}
		try
		{
			samlAssertion = authenticationClient.authenticate(auth);
		} 
		catch (InvalidCredentialFault e)
		{
		    LOG.error(e.getMessage()+", username: "+userName);
			throw new CGMMGridAuthenticationServiceException(CGMMMessages.EXCEPTION_INVALID_CREDENTIALS+e.getFaultString());
		} 
		catch (InsufficientAttributeFault e)
		{
		    LOG.error(e, e);
			throw new CGMMGridAuthenticationServiceException(CGMMMessages.EXCEPTION_GRID_AUTH_SERVICE_UNAVAILABLE_INSUFFICIENT_ATTRIBUTES+e.getFaultDetails());
		} 
		catch (AuthenticationProviderFault e)
		{
		    LOG.error(e, e);
			throw new CGMMGridAuthenticationServiceException(CGMMMessages.EXCEPTION_GRID_AUTH_SERVICE_UNAVAILABLE+e.getFaultDetails());
		} 
		catch (RemoteException e)
		{
		    LOG.error(e, e);
			throw new CGMMGridAuthenticationServiceException(CGMMMessages.EXCEPTION_GRID_AUTH_SERVICE_UNAVAILABLE + e.getMessage());
		}
		return samlAssertion;
	}

}
