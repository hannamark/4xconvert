package gov.nih.nci.po.util;

import java.net.URL;
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

    /** Default xsnapshot name. */
    public static final String DEFAULT_NAME = "entity";
    /** configured XSnapshotUtil for all conversions. */
    public static final XSnapshotUtils PO_XSNASHOTUTILS;
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
        } catch (ConfigurationException ex) {
            throw new RuntimeException("failed to init xsnapshot", ex);
        }
    }

    /**
     * @param model the model object to translate
     * @return a snapshotobject
     */
    public static Object createSnapshot(Object model) {
        return PO_XSNASHOTUTILS.createSnapshot(model, DEFAULT_NAME);
    }
    
    /**
     * @param snapshot the snapshot to stranslate
     * @return a model object
     */
    public static Object createModel(Object snapshot) {
        return PO_XSNASHOTUTILS.createModel(snapshot);
    }

    /**
     * Returns the snapshot helper for a model class and snapshot name. 
     * @param modelClass the model class 
     * @param snapshotName the snapshot name
     * @return the helper object, or null if no helper is registered for that 
     * combination of model class and snapshot name
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
