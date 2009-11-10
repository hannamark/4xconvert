/**
 * 
 */
package gov.nih.nci.pa.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.DocumentWorkflowStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.IvlConverter;
import gov.nih.nci.pa.util.HibernateSessionInterceptor;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;

import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author asharma
 *
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.NPathComplexity" })
@Interceptors(HibernateSessionInterceptor.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DocumentWorkflowStatusBeanLocal extends
AbstractCurrentStudyIsoService<DocumentWorkflowStatusDTO, DocumentWorkflowStatus, DocumentWorkflowStatusConverter>
implements DocumentWorkflowStatusServiceLocal { 

    /**
     * @param dto arm to create
     * @return the created planned activity
     * @throws PAException exception.
     */

    @SuppressWarnings("unchecked")
    @Override
    public DocumentWorkflowStatusDTO create(DocumentWorkflowStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            throw new PAException("Update method should be used to modify existing.  ");
        }
        Session session = null;
        List<DocumentWorkflowStatus> queryList = new ArrayList<DocumentWorkflowStatus>();
        session = HibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = "select dwfs from DocumentWorkflowStatus dwfs "
            + " join dwfs.studyProtocol sp where sp.id = :spId "
            + " and dwfs.statusCode = :statusCode";
        query = session.createQuery(hql);
        query.setParameter("spId", IiConverter.convertToLong(dto.getStudyProtocolIdentifier()));
        query.setParameter("statusCode", DocumentWorkflowStatusCode.getByCode(dto.getStatusCode().getCode()));
        queryList = query.list();
        dto.setStatusDateRange(IvlConverter.convertTs().convertToIvl(new Timestamp((new Date()).getTime()), null));
        if (queryList == null || queryList.isEmpty()) {
            super.create(dto);
        } else if (queryList.size() == 1) {
            dto.setIdentifier(IiConverter.convertToIi(queryList.get(0).getId()));
            super.update(dto);
        } else if (queryList.size() > 1) {
            throw new PAException("There cannot be more than 1 record for a give protocol and status "
                    + " protocol id = " + dto.getStudyProtocolIdentifier().getExtension() + " status code "
                    + dto.getStatusCode().getCode());
        }
        return null;
    }

}
