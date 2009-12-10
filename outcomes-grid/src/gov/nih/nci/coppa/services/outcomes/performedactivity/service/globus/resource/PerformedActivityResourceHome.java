package gov.nih.nci.coppa.services.outcomes.performedactivity.service.globus.resource;

import gov.nih.nci.coppa.services.outcomes.performedactivity.common.PerformedActivityConstants;
import gov.nih.nci.coppa.services.outcomes.performedactivity.stubs.PerformedActivityResourceProperties;

import org.apache.axis.components.uuid.UUIDGen;
import org.apache.axis.components.uuid.UUIDGenFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.globus.wsrf.InvalidResourceKeyException;
import org.globus.wsrf.PersistenceCallback;
import org.globus.wsrf.Resource;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceKey;
import org.globus.wsrf.ResourceContext;
import gov.nih.nci.cagrid.introduce.servicetools.SingletonResourceHomeImpl;
import org.globus.wsrf.jndi.Initializable;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This class implements the resource home for the resource type represented
 * by this service.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class PerformedActivityResourceHome extends SingletonResourceHomeImpl implements Initializable {

	static final Log logger = LogFactory.getLog(PerformedActivityResourceHome.class);
    private static final UUIDGen UUIDGEN = UUIDGenFactory.getUUIDGen();

	public Resource createSingleton() {
		logger.info("Creating a single resource.");
		try {
		    PerformedActivityResourceProperties props = new PerformedActivityResourceProperties();
			PerformedActivityResource resource = new PerformedActivityResource();
			if (resource instanceof PersistenceCallback) {
			      //try to load the resource if it was persisted
                  try{
                    ((PersistenceCallback) resource).load(null);
			      } catch (InvalidResourceKeyException ex){
			      	  //persisted singleton resource was not found so we will just create a new one
			          resource.initialize(props, PerformedActivityConstants.RESOURCE_PROPERTY_SET, UUIDGEN.nextUUID());
			      }
            } else {
                    resource.initialize(props, PerformedActivityConstants.RESOURCE_PROPERTY_SET, UUIDGEN.nextUUID());
            }
			
			return resource;
		} catch (Exception e) {
			logger.error("Exception when creating the resource",e);
			return null;
		}
	}


	public Resource find(ResourceKey key) throws ResourceException {
		PerformedActivityResource resource = (PerformedActivityResource) super.find(key);
		return resource;
	}


	/**
	 * Initialze the singleton resource, when the home is initialized.
	 */
	public void initialize() throws Exception {
		logger.info("Attempting to initialize resource.");
		Resource resource = find(null);
		if (resource == null) {
			logger.error("Unable to initialize resource!");
		} else {
			logger.info("Successfully initialized resource.");
		}
	}
	
    /**
     * Get the resouce that is being addressed in this current context
     */
    public PerformedActivityResource getAddressedResource() throws Exception {
        PerformedActivityResource thisResource;
        thisResource = (PerformedActivityResource) ResourceContext.getResourceContext().getResource();
        return thisResource;
    }
}