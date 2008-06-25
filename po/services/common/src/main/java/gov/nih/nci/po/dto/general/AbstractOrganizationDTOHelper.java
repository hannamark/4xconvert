/**
 * Auto-generated file.  Do not edit.
 * 
 * This is the helper class for the gov.nih.nci.po.dto.general.AbstractOrganizationDTO snapshot class.
 */

package gov.nih.nci.po.dto.general;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.commons.collections.IteratorUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.beanutils.PropertyUtils;

import net.sf.xsnapshot.*;
import net.sf.xsnapshot.support.*;

public class AbstractOrganizationDTOHelper implements SnapshotHelper {
  /**
   *  Creates the snapshot object based on the model object given. This method should
   * not be called directly. Instead, call createSnapshot(Object).
   */
  public Object createSnapshot(Object model, TransformContext context) throws XSnapshotException {
    if (model == null) {
      return null;
    } else {
      Class myClass = gov.nih.nci.po.data.common.AbstractOrganization.class;
      if (myClass.isInstance (model)) {

        // cannot happen: there cannot be a concrete instance of an 
        // abstract class
        throw new IllegalStateException ("Concrete instance of an abstract class found, or helper called directly");

      } else {
         throw new IllegalArgumentException ("model object is of class " + model.getClass () + " which is not a subclass of gov.nih.nci.po.data.common.AbstractOrganization");
      }
    }
  }
  /**
   * Create the model for the given snapshot
   */
  public Object createModel(Object snapshot, TransformContext context) throws XSnapshotException {
    if (snapshot == null) {
      return null;
    } else {
      // check if this is actually of my type
      Class myClass = gov.nih.nci.po.dto.general.AbstractOrganizationDTO.class;
      if (myClass.isInstance (snapshot)) {

        // cannot happen: there cannot be a concrete instance of an 
        // abstract class
        throw new IllegalStateException ("Concrete instance of an abstract class found, or helper called directly");

      } else {
         throw new IllegalArgumentException ("snapshot object is of class " + snapshot.getClass () + " which is not a subclass of gov.nih.nci.po.dto.general.AbstractOrganizationDTO");
      }
    }     
  }

  /**
    * Copies a gov.nih.nci.po.data.common.AbstractOrganization model object into a gov.nih.nci.po.dto.general.AbstractOrganizationDTO snapshot object
    * @param model the model object
    * @param snapshot the snapshot object
    */
  public void copyIntoSnapshot (Object model, Object snapshot, TransformContext context) throws XSnapshotException {
    gov.nih.nci.po.dto.general.AbstractOrganizationDTO snapshotCasted = (gov.nih.nci.po.dto.general.AbstractOrganizationDTO) snapshot;
    gov.nih.nci.po.data.common.AbstractOrganization modelCasted = (gov.nih.nci.po.data.common.AbstractOrganization) model;

  }
  /**
    * Copies a XDtXSnapshot:snapshotClass/> snapshot object into a gov.nih.nci.po.data.common.AbstractOrganization  model object
    * Warning: not an exact inverse of copyIntoSnapshot
    * @param snapshot the snapshot object
    * @param model the model object
    */  
  public void copyIntoModel(Object snapshot, Object model, TransformContext context) throws XSnapshotException {

    gov.nih.nci.po.data.common.AbstractOrganization modelCasted = (gov.nih.nci.po.data.common.AbstractOrganization)model;
    gov.nih.nci.po.dto.general.AbstractOrganizationDTO snapshotCasted = (gov.nih.nci.po.dto.general.AbstractOrganizationDTO)snapshot;

  }

}