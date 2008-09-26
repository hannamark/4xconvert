package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.util.PoHibernateUtil;

import java.util.List;

/**
 * A static utility class to handle the processing of Change Requests.
 */
final class CRProcessor {
    
    private CRProcessor() {
        //noop
    }
    /**
     * A callback to update the target entity after processing.
     * 
     * @param <ENTITY>
     */
    interface EntityUpdateCallback<ENTITY> {
        void entityUpdate(ENTITY target);
    }

    /**
     * update the target ENTITY, and delete processed CRs (whether applied or rejected).
     * 
     * @param <CR> the PersistentObject type.
     * @param <ENTITY> the PersistentObject type.
     * @param crs the hibernate IDs of the CRs to delete. All crs must have the same target Org,
     */
    static <CR extends ChangeRequest<ENTITY>, ENTITY> void processCRs(List<CR> crs,
            EntityUpdateCallback<ENTITY> callback) {
        ENTITY target = null;
        for (CR ocr : crs) {
            ENTITY crTarget = ocr.getTarget();
            if (crTarget == null) {
                throw new IllegalArgumentException("target cannot be null");
            }
            if (target == null) {
                target = crTarget;
            } else if (!target.equals(crTarget)) {
                throw new IllegalArgumentException("all crs must have the same target");
            }
            // TODO delete or mark as processed
            // see https://jira.5amsolutions.com/browse/PO-492
            PoHibernateUtil.getCurrentSession().delete(ocr);
        }

        if (target != null) {
            callback.entityUpdate(target);
        }
    }
}
