package gov.nih.nci.po.service;

import gov.nih.nci.po.data.bo.ChangeRequest;
import gov.nih.nci.po.data.bo.Curatable;
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
     * @param <CR> the change request type.
     * @param <ENTITY> the PersistentObject type.
     * @param crs the hibernate IDs of the CRs to delete. All crs must have the same target Org,
     */
    @SuppressWarnings("unchecked")
    static <CR extends ChangeRequest<ENTITY>, ENTITY extends Curatable> void processCRs(List<CR> crs,
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

            ocr.setProcessed(true);
            PoHibernateUtil.getCurrentSession().update(ocr);
        }

        if (target != null) {
            target.getChangeRequests().removeAll(crs);
            callback.entityUpdate(target);
        }
    }
}
