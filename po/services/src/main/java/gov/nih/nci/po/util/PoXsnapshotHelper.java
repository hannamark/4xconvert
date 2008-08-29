package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.PersonRole;
import gov.nih.nci.services.EntityDTO;
import gov.nih.nci.services.correlation.ExtendedPersonRoleDTOHelper;
import gov.nih.nci.services.correlation.PersonRoleDTO;
import gov.nih.nci.services.organization.AbstractOrganizationDTO;
import gov.nih.nci.services.organization.ExtendedOrganizationDTOHelper;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.person.AbstractPersonDTO;
import gov.nih.nci.services.person.ExtendedPersonDTOHelper;
import gov.nih.nci.services.person.PersonDTO;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import net.sf.xsnapshot.SnapshotHelper;
import net.sf.xsnapshot.TransformContext;
import net.sf.xsnapshot.XSnapshotRegistry;
import net.sf.xsnapshot.XSnapshotUtils;
import net.sf.xsnapshot.cfg.XSnapshotPropertiesConfigurator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;

/**
 *
 * @author gax
 */
public final class PoXsnapshotHelper extends XSnapshotRegistry {

    private static final String UNCHECKED = "unchecked";
    /** Default xsnapshot entity name. */
    public static final String DEFAULT_ISO_SNAPSHOT_NAME = "iso";

    /** configured XSnapshotUtil for all conversions. */
    private static final XSnapshotUtils PO_XSNASHOTUTILS;
    private static final PoXsnapshotHelper REGISTRY = new PoXsnapshotHelper();

    static {
        PO_XSNASHOTUTILS = new XSnapshotUtils(REGISTRY);
    }

    @SuppressWarnings("PMD.AvoidThrowingRawExceptionTypes")
    private PoXsnapshotHelper() {
        super();
        URL configResource = PoXsnapshotHelper.class.getClassLoader().getResource("xsnapshot.properties");
        if (configResource == null) {
            throw new RuntimeException("resource xsnapshot.properties not found");
        }
        try {
            PropertiesConfiguration config = new PropertiesConfiguration(configResource);
            XSnapshotPropertiesConfigurator.configure(this, config);

            Class snapshotClass = AbstractOrganizationDTO.class;
            this.registerSnapshotClass(AbstractOrganization.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedOrganizationDTOHelper());

            snapshotClass = AbstractPersonDTO.class;
            this.registerSnapshotClass(AbstractPerson.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedPersonDTOHelper());

            snapshotClass = PersonRoleDTO.class;
            this.registerSnapshotClass(PersonRole.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedPersonRoleDTOHelper());
        } catch (ConfigurationException ex) {
            throw new RuntimeException("failed to init xsnapshot", ex);
        }
    }

    /**
     * Convert a collection of model objects into the corresponding snapshot objects.
     *
     * @param modelCollection the collection of model objects to convert
     * @param destCollection a collection into which to put the snapshot objects
     * @param <BO> BO type
     * @param <DTO> DTO type        *
     * @return the collection containing the snapshot objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <BO extends PersistentObject, DTO extends EntityDTO<BO>> Collection<DTO> createSnapshotCollection(
            Collection<BO> modelCollection, Collection<DTO> destCollection) {
        return PO_XSNASHOTUTILS.createSnapshotCollection(modelCollection, DEFAULT_ISO_SNAPSHOT_NAME, destCollection);
    }

    /**
     * convert a collection of model objects into a collection of snapshot objects, putting them in a list.
     *
     * @param modelCollection the collection of model objects to convert
     * @return the list of snapshot objects
     */
    public static List createSnapshotList(Collection modelCollection) {
        return PO_XSNASHOTUTILS.createSnapshotList(modelCollection, DEFAULT_ISO_SNAPSHOT_NAME);
    }

    /**
     * @param model the model to translate
     * @param <BO> BO type
     * @param <DTO> DTO type
     * @return a snapshot object
     */
    @SuppressWarnings(UNCHECKED)
    public static <BO extends PersistentObject, DTO extends EntityDTO<BO>> DTO createSnapshot(BO model) {
        return (DTO) PO_XSNASHOTUTILS.createSnapshot(model, DEFAULT_ISO_SNAPSHOT_NAME);
    }

    /**
     * convert a collection of snapshot objects into a collection of model objects, putting them in a list.
     *
     * @param snapshotCollection the collection of snapshot objects to convert
     * @param destCollection the collection to populate
     * @param <BO> BO type
     * @param <DTO> DTO type     *
     * @return the list of model objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <BO extends PersistentObject, DTO extends EntityDTO<BO>> Collection<DTO> createModelCollection(
            Collection<DTO> snapshotCollection, Collection<BO> destCollection) {
        return PO_XSNASHOTUTILS.createModelCollection(snapshotCollection, destCollection);
    }

    /**
     * convert a collection of snapshot objects into a collection of model objects, putting them in a list.
     *
     * @param snapshotCollection the collection of snapshot objects to convert
     * @param <BO> BO type
     * @param <DTO> DTO type
     * @return the list of model objects
     */
    @SuppressWarnings(UNCHECKED)
    public static  <BO extends PersistentObject, DTO extends EntityDTO<BO>>
            List<BO> createModelList(Collection<DTO> snapshotCollection) {
        return PO_XSNASHOTUTILS.createModelList(snapshotCollection);
    }

    /**
     * Returns the snapshot helper for a model class and snapshot name.
     *
     * @param snapshot the snapshot to translate
     * @param <BO> BO type
     * @param <DTO> DTO type     *
     * @return a model object
     */
    @SuppressWarnings(UNCHECKED)
    public static <BO extends PersistentObject, DTO extends EntityDTO<BO>> BO createModel(DTO snapshot) {
        return (BO) PO_XSNASHOTUTILS.createModel(snapshot);
    }

    /**
     * Returns the snapshot helper for a model class and snapshot name.
     *
     * @param modelClass the model class
     * @param snapshotName the snapshot name
     * @return the helper object, or null if no helper is registered for that combination of model class and snapshot
     *         name
     */
    @Override
    public SnapshotHelper getHelperForModel(Class modelClass, String snapshotName) {
        if (modelClass == null) {
            return null;
        }
        SnapshotHelper helper = super.getHelperForModel(modelClass, snapshotName);
        if (helper == null) {
            helper = getHelperForModel(modelClass.getSuperclass(), snapshotName);
        }
        return helper;
    }

    /**
     * Copy properties from snapshot into model.
     *
     * @param snapshot the snapshot to translate
     * @param model the target.
     */
    public static void copyIntoAbstractModel(OrganizationDTO snapshot, AbstractOrganization model) {
        SnapshotHelper helper = REGISTRY.getHelperForModel(AbstractOrganization.class, DEFAULT_ISO_SNAPSHOT_NAME);
        helper.copyIntoModel(snapshot, model, new TransformContext(REGISTRY));
    }

     /**
     * Copy properties from snapshot into model.
     *
     * @param snapshot the snapshot to translate
     * @param model the target.
     */
    public static void copyIntoAbstractModel(PersonDTO snapshot, AbstractPerson model) {
        SnapshotHelper helper = REGISTRY.getHelperForModel(AbstractPerson.class, DEFAULT_ISO_SNAPSHOT_NAME);
        helper.copyIntoModel(snapshot, model, new TransformContext(REGISTRY));

    }

}
