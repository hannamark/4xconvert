/*
 * Auto-generated file.  Do not edit.
 */
package gov.nih.nci.po.dto.general;

import java.io.Serializable;
import java.util.*;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import net.sf.xsnapshot.XSnapshotRegistry;
import net.sf.xsnapshot.XSnapshotUtils;
import net.sf.xsnapshot.XSnapshotException;

/**
 * This is the "general" snapshot auto-generated from the 
 * AbstractOrganization class.
 * <p> 
 * Please see the documentation for the original AbstractOrganization
 * class for documentation of each individual attribute.
 * @see gov.nih.nci.po.data.common.AbstractOrganization
 */

public abstract class AbstractOrganizationDTO 
  extends java.lang.Object 
  implements Serializable {

  /**
   * Default constructor.
   */
  public AbstractOrganizationDTO () { }

// equals, hashcode, toString

  public String toString () {
    return ToStringBuilder.reflectionToString (this);
  }    

// additional code from merge file for this model class

// additional code from merge file for this snapshot 

// wrapper methods for operations on snapshots that cast arguments
// appropriately

  /**
   * create the given snapshot for the given model object.
   * @param model the model object
   * @return a snapshot of the given type on the model. 
   * @throws XSnapshotException if no snapshot with given name defined on the model 
   * or if an XSnapshot error occurs during conversion
   */
  public static AbstractOrganizationDTO createSnapshot(gov.nih.nci.po.data.common.AbstractOrganization model, XSnapshotRegistry registry) throws XSnapshotException {
    return (AbstractOrganizationDTO) XSnapshotUtils.createSnapshot(model, "general", registry);
  }
  /**
   * copy the given model object into the given snapshot object
   * @param model the model object
   * @param snapshot the snapshot object
   * @param registry the registry to use     
   * @throws XSnapshotException if no snapshot with given name defined on the model 
   * or if an XSnapshot error occurs during conversion
   */
  public static void copyIntoSnapshot(gov.nih.nci.po.data.common.AbstractOrganization model, AbstractOrganizationDTO snapshot, XSnapshotRegistry registry) throws XSnapshotException {
    XSnapshotUtils.copyIntoSnapshot(model, snapshot, registry);
  }  
  /**
   * Convert a collection of model objects into the corresponding snapshot objects.
   * @param modelCollection the collection of model objects to convert
   * @param snapshotCollection a collection into which to put the snapshot objects
   * @param registry the registry to use
   * @return the collection containing the snapshot objects
   */
  public static Collection createSnapshotCollection(Collection modelCollection, Collection snapshotCollection,
                                                    XSnapshotRegistry registry) throws XSnapshotException {
    return XSnapshotUtils.createSnapshotCollection(modelCollection, "general", snapshotCollection, registry);
  }
  /**
   * convert a colletion of model objects into a collection of snapshot objects,
   * putting them in a list
   * @param modelCollection the collection of model objects to convert
   * @param registry the registry to use
   * @return the list of snapshot objects
   */
  public static List createSnapshotList(Collection modelCollection, String snapshotName, XSnapshotRegistry registry)
      throws XSnapshotException {
    return XSnapshotUtils.createSnapshotList(modelCollection, "general", registry);  
  }
  /**
   * convert a collection of model objects into an of snapshot objects
   * @param modelCollection the collection of model objects to convert
   * @param registry the registry to use
   * @return the array of snapshot objects
   */
  public static AbstractOrganizationDTO[] createSnapshotArray(Collection modelCollection, XSnapshotRegistry registry) throws XSnapshotException {
    return (AbstractOrganizationDTO[]) XSnapshotUtils.createSnapshotArray (modelCollection, "general", new AbstractOrganizationDTO[modelCollection.size()], registry);
  }
  /**
   * create the model based on the given snapshot object
   * @param snapshot the snapshot object
   * @param registry the registry to use
   * @return the model object of which the given snapshot object is a snapshot 
   * @throws XSnapshotException if the given object is not a known snapshot 
   * or if an XSnapshot error occurs during conversion
   */
  public static gov.nih.nci.po.data.common.AbstractOrganization createModel(AbstractOrganizationDTO snapshot, XSnapshotRegistry registry) throws XSnapshotException {
    return (gov.nih.nci.po.data.common.AbstractOrganization) XSnapshotUtils.createModel(snapshot, registry);
  }
  /**
   * copy the given model object into the given snapshot object
   * @param model the model object
   * @param snapshot the snapshot object
   * @param registry the registry to use     
   * @throws XSnapshotException if no snapshot with given name defined on the model 
   * or if an XSnapshot error occurs during conversion
   */
  public static void copyIntoModel(AbstractOrganizationDTO snapshot, gov.nih.nci.po.data.common.AbstractOrganization model, XSnapshotRegistry registry) throws XSnapshotException {
    XSnapshotUtils.copyIntoModel(snapshot, model, registry);
  }
    /**
   * Convert a collection of snapshot objects into the corresponding model objects.
   * @param snapshotCollection the collection of snapshot objects to convert 
   * @param modelCollection a collection into which to put the model objects
   * @param registry the registry to use
   * @return the collection containing the model objects
   */
  public static Collection createModelCollection(Collection snapshotCollection, Collection modelCollection,
                                                 XSnapshotRegistry registry) throws XSnapshotException {
    return XSnapshotUtils.createModelCollection (snapshotCollection, modelCollection, registry);                                                 
  }  
    /**
   * convert a colletion of snapshot objects into a collection of model objects,
   * putting them in a list 
   * @param snapshotCollection the collection of snapshot objects to convert
   * @param registry the registry to use
   * @return the list of model objects
   */
  public static List createModelList(Collection snapshotCollection, XSnapshotRegistry registry) throws XSnapshotException {
    return XSnapshotUtils.createModelList(snapshotCollection, registry);
  }
    /**
   * convert a collection of snapshot objects into an array of model objects
   * @param snapshotCollection the collection of snapshot objects to convert
   * @param modelArray the array to put into into. if it's big enough to
   * hold the model objects, this array will be returned. otherwise, a new
   * array of the same type will be constructed with size big enough to hold
   * all model.
   * @param registry the registry to use
   * @return the list of model objects
   */
  public static gov.nih.nci.po.data.common.AbstractOrganization[] createModelArray(Collection snapshotCollection, XSnapshotRegistry registry) throws XSnapshotException {  
    return (gov.nih.nci.po.data.common.AbstractOrganization[]) XSnapshotUtils.createModelArray (snapshotCollection, new gov.nih.nci.po.data.common.AbstractOrganization[snapshotCollection.size()], registry);
  }

}