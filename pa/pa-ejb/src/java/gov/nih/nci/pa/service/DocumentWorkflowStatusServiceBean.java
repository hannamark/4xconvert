package gov.nih.nci.pa.service;

import gov.nih.nci.pa.domain.DocumentWorkflowStatus;
import gov.nih.nci.pa.enums.DocumentWorkflowStatusCode;
import gov.nih.nci.pa.iso.convert.DocumentWorkflowStatusConverter;
import gov.nih.nci.pa.iso.dto.DocumentWorkflowStatusDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.iso.util.TsConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Kalpana Guthikonda
 * @since 11/07/2008
 * copyright NCI 2008.  All rights reserved.
 * This code may not be used without the express written permission of the
 * copyright holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity" , "PMD.NPathComplexity" })
public class DocumentWorkflowStatusServiceBean extends 
    AbstractStudyIsoService<DocumentWorkflowStatusDTO, DocumentWorkflowStatus, DocumentWorkflowStatusConverter> 
    implements DocumentWorkflowStatusServiceRemote {

    /**
     * @param dto arm to create
     * @return the created planned activity
     * @throws PAException exception.
     */
    
    public DocumentWorkflowStatusDTO create(DocumentWorkflowStatusDTO dto) throws PAException {
        if (!PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError("Update method should be used to modify existing.  ");
        }
        Session session = null;
        List<DocumentWorkflowStatus> queryList = new ArrayList<DocumentWorkflowStatus>();
        try {
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
            dto.setStatusDateRange(TsConverter.convertToTs(new Timestamp((new Date()).getTime())));
            if (queryList == null || queryList.isEmpty()) {
                super.create(dto);
            } else if (queryList.size() == 1) {
                dto.setIdentifier(IiConverter.convertToIi(queryList.get(0).getId()));
                super.update(dto);
                    
            } else if (queryList.size() > 1) {
                serviceError("There cannot be more than 1 record for a give protocol and status " 
                        + " protocol id = " + dto.getStudyProtocolIdentifier().getExtension() + " status code " 
                        + dto.getStatusCode().getCode());
            }
            
        }  catch (HibernateException hbe) {
            session.flush();
            
            serviceError("Hibernate exception while retrieving document workflow status  for protocol id  = " 
                    + dto.getStudyProtocolIdentifier().getExtension() , hbe);
        }
        return null;
    }
    
}
