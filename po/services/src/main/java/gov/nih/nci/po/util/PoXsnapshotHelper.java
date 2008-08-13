package gov.nih.nci.po.util;

import gov.nih.nci.po.data.bo.Curatable;
import gov.nih.nci.po.data.bo.Organization;
import gov.nih.nci.po.data.bo.Person;
import gov.nih.nci.services.EntityDTO;
import gov.nih.nci.services.organization.OrganizationDTO;
import gov.nih.nci.services.organization.OrganizationDTOHelper;
import gov.nih.nci.services.person.PersonDTO;
import gov.nih.nci.services.person.PersonDTOHelper;

import java.net.URL;
import java.util.Collection;
import java.util.List;

import net.sf.xsnapshot.SnapshotHelper;
import net.sf.xsnapshot.XSnapshotRegistry;
import net.sf.xsnapshot.XSnapshotUtils;
import net.sf.xsnapshot.cfg.XSnapshotPropertiesConfigurator;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 *
 * @author gax
 */
public final class PoXsnapshotHelper extends XSnapshotRegistry {

    private static final String UNCHECKED = "unchecked";
    /** Default xsnapshot name. */
    public static final String DEFAULT_NAME = "entity";
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

            Class snapshotClass = OrganizationDTO.class;
            this.registerSnapshotClass(Organization.class, "entity", snapshotClass);
            this.registerHelper(snapshotClass, new OrganizationDTOHelper());

            snapshotClass = PersonDTO.class;
            this.registerSnapshotClass(Person.class, "entity", snapshotClass);
            this.registerHelper(snapshotClass, new PersonDTOHelper());
        } catch (ConfigurationException ex) {
            throw new RuntimeException("failed to init xsnapshot", ex);
        }
    }

    /**
     * Convert a collection of model objects into the corresponding snapshot objects.
     *
     * @param modelCollection the collection of model objects to convert
     * @param destCollection a collection into which to put the snapshot objects
     * @param <T> BO type
     * @param <U> DTO type        *
     * @return the collection containing the snapshot objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <T extends Curatable, U extends EntityDTO> Collection<U> createSnapshotCollection(
            Collection<T> modelCollection, Collection<U> destCollection) {
        return PO_XSNASHOTUTILS.createSnapshotCollection(modelCollection, DEFAULT_NAME, destCollection);
    }

    /**
     * convert a collection of model objects into a collection of snapshot objects, putting them in a list.
     *
     * @param modelCollection the collection of model objects to convert
     * @return the list of snapshot objects
     */
    public static List createSnapshotList(Collection modelCollection) {
        return PO_XSNASHOTUTILS.createSnapshotList(modelCollection, DEFAULT_NAME);
    }

    /**
     * @param model the model to translate
     * @param <T> BO type
     * @param <U> DTO type       *
     * @return a snapshot object
     */
    @SuppressWarnings(UNCHECKED)
    public static <T extends Curatable, U extends EntityDTO> U createSnapshot(T model) {
        return (U) PO_XSNASHOTUTILS.createSnapshot(model, DEFAULT_NAME);
    }

    /**
     * convert a collection of snapshot objects into a collection of model objects, putting them in a list.
     *
     * @param snapshotCollection the collection of snapshot objects to convert
     * @param destCollection the collection to populate
     * @param <T> BO type
     * @param <U> DTO type     *
     * @return the list of model objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <T extends Curatable, U extends EntityDTO> Collection<T> createModelCollection(
            Collection<U> snapshotCollection, Collection<T> destCollection) {
        return PO_XSNASHOTUTILS.createModelCollection(snapshotCollection, destCollection);
    }

    /**
     * convert a collection of snapshot objects into a collection of model objects, putting them in a list.
     *
     * @param snapshotCollection the collection of snapshot objects to convert
     * @param <T> BO type
     * @param <U> DTO type
     * @return the list of model objects
     */
    @SuppressWarnings(UNCHECKED)
    public static <T extends Curatable, U extends EntityDTO> List<T> createModelList(Collection<U> snapshotCollection) {
        return PO_XSNASHOTUTILS.createModelList(snapshotCollection);
    }

    /**
     * Returns the snapshot helper for a model class and snapshot name.
     *
     * @param snapshot the snapshot to translate
     * @param <T> BO type
     * @param <U> DTO type     *
     * @return a model object
     */
    @SuppressWarnings(UNCHECKED)
    public static <T extends Curatable, U extends EntityDTO> T createModel(U snapshot) {
        return (T) PO_XSNASHOTUTILS.createModel(snapshot);
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

}
