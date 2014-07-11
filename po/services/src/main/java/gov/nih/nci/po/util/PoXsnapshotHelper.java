package gov.nih.nci.po.util;

import com.fiveamsolutions.nci.commons.data.persistent.PersistentObject;
import gov.nih.nci.po.data.bo.AbstractEnhancedOrganizationRole;
import gov.nih.nci.po.data.bo.AbstractOrganization;
import gov.nih.nci.po.data.bo.AbstractPerson;
import gov.nih.nci.po.data.bo.AbstractPersonRole;
import gov.nih.nci.po.data.bo.AbstractRole;
import gov.nih.nci.services.PoDto;
import gov.nih.nci.services.correlation.AbstractEnhancedOrganizationRoleDTO;
import gov.nih.nci.services.correlation.AbstractPersonRoleDTO;
import gov.nih.nci.services.correlation.AbstractRoleDTO;
import gov.nih.nci.services.correlation.ExtendedEnhancedOrganizationRoleDTOHelper;
import gov.nih.nci.services.correlation.ExtendedPersonRoleDTOHelper;
import gov.nih.nci.services.correlation.ExtendedRoleDTOHelper;
import gov.nih.nci.services.organization.AbstractOrganizationDTO;
import gov.nih.nci.services.organization.ExtendedOrganizationDTOHelper;
import gov.nih.nci.services.person.AbstractPersonDTO;
import gov.nih.nci.services.person.ExtendedPersonDTOHelper;
import net.sf.xsnapshot.SnapshotHelper;
import net.sf.xsnapshot.TransformContext;
import net.sf.xsnapshot.XSnapshotRegistry;
import net.sf.xsnapshot.XSnapshotUtils;
import net.sf.xsnapshot.cfg.XSnapshotPropertiesConfigurator;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.net.URL;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author gax
 */
@SuppressWarnings("PMD.TooManyMethods")
public final class PoXsnapshotHelper extends XSnapshotRegistry {

    private static final String UNCHECKED = "unchecked";
    /** Default xsnapshot entity name. */
    public static final String DEFAULT_ISO_SNAPSHOT_NAME = "iso";

    /** configured XSnapshotUtil for all conversions. */
    private static final XSnapshotUtils PO_XSNAPSHOTUTILS;
    private static final PoXsnapshotHelper REGISTRY = new PoXsnapshotHelper();

    static {
        PO_XSNAPSHOTUTILS = new XSnapshotUtils(REGISTRY);
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

            Class<?> snapshotClass = AbstractOrganizationDTO.class;
            this.registerSnapshotClass(AbstractOrganization.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedOrganizationDTOHelper());

            snapshotClass = AbstractPersonDTO.class;
            this.registerSnapshotClass(AbstractPerson.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedPersonDTOHelper());

            snapshotClass = AbstractRoleDTO.class;
            this.registerSnapshotClass(AbstractRole.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedRoleDTOHelper());

            snapshotClass = AbstractPersonRoleDTO.class;
            this.registerSnapshotClass(AbstractPersonRole.class, DEFAULT_ISO_SNAPSHOT_NAME, snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedPersonRoleDTOHelper());

            snapshotClass = AbstractEnhancedOrganizationRoleDTO.class;
            this.registerSnapshotClass(AbstractEnhancedOrganizationRole.class, DEFAULT_ISO_SNAPSHOT_NAME,
                    snapshotClass);
            this.registerHelper(snapshotClass, new ExtendedEnhancedOrganizationRoleDTOHelper());

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
     * @param <DTO> DTO type
     * @return the collection containing the snapshot objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <BO extends PersistentObject, DTO extends PoDto> Collection<DTO> createSnapshotCollection(
            Collection<BO> modelCollection, Collection<DTO> destCollection) {
        return PO_XSNAPSHOTUTILS.createSnapshotCollection(modelCollection, DEFAULT_ISO_SNAPSHOT_NAME, destCollection);
    }

    /**
     * convert a collection of model objects into a collection of snapshot objects, putting them in a list.
     *
     * @param modelCollection the collection of model objects to convert
     * @return the list of snapshot objects
     */
    public static List createSnapshotList(Collection<?> modelCollection) {
        return PO_XSNAPSHOTUTILS.createSnapshotList(modelCollection, DEFAULT_ISO_SNAPSHOT_NAME);
    }

    /**
     * @param model the model to translate
     * @return a snapshot object
     */
    public static PoDto createSnapshot(PersistentObject model) {
        return (PoDto) PO_XSNAPSHOTUTILS.createSnapshot(model, DEFAULT_ISO_SNAPSHOT_NAME);
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
    public static <BO extends PersistentObject, DTO extends PoDto> Collection<DTO> createModelCollection(
            Collection<DTO> snapshotCollection, Collection<BO> destCollection) {
        return PO_XSNAPSHOTUTILS.createModelCollection(snapshotCollection, destCollection);
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
    public static <BO extends PersistentObject, DTO extends PoDto> List<BO> createModelList(
            Collection<DTO> snapshotCollection) {
        return PO_XSNAPSHOTUTILS.createModelList(snapshotCollection);
    }

    /**
     * Returns the snapshot helper for a model class and snapshot name.
     *
     * @param snapshot the snapshot to translate
     * @return a model object
     */
    public static PersistentObject createModel(PoDto snapshot) {
        return (PersistentObject) PO_XSNAPSHOTUTILS.createModel(snapshot);
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
    public SnapshotHelper getHelperForModelClass(Class modelClass, String snapshotName) {
        if (modelClass == null) {
            return null;
        }
        SnapshotHelper helper = super.getHelperForModelClass(modelClass, snapshotName);
        if (helper == null) {
            helper = getHelperForModelClass(modelClass.getSuperclass(), snapshotName);
        }
        return helper;
    }

    /**
     * Copy properties from snapshot into model.
     *
     * @param snapshot the snapshot to translate
     * @param model the target.
     * @param modelClassForHelper the model class to search for the right helper by.
     */
    public static void copyIntoAbstractModel(PoDto snapshot, PersistentObject model, Class<?> modelClassForHelper) {
        SnapshotHelper helper = REGISTRY.getHelperForModelClass(modelClassForHelper, DEFAULT_ISO_SNAPSHOT_NAME);
        helper.copyIntoModel(snapshot, model, new TransformContext(REGISTRY));
    }
}
