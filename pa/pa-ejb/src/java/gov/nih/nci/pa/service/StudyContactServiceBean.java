package gov.nih.nci.pa.service;

import gov.nih.nci.coppa.iso.Ii;
import gov.nih.nci.pa.domain.StudyContact;
import gov.nih.nci.pa.iso.convert.StudyContactConverter;
import gov.nih.nci.pa.iso.dto.StudyContactDTO;
import gov.nih.nci.pa.iso.util.IiConverter;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * @author Bala nair
 * @since 10/23/2008 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@Stateless
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.AvoidDuplicateLiterals" })
public class StudyContactServiceBean
        extends AbstractStudyPaService<StudyContactDTO>  
        implements StudyContactServiceRemote {

    private static final Logger LOG  = Logger.getLogger(StudyContactServiceBean.class);
    
    /**
     * @return log4j Logger
     */
    @Override
    protected Logger getLogger() { return LOG; }

    /**
     * @param dto StudyContactDTO
     * @return StudyContactDTO
     * @throws PAException PAException
     */
    public StudyContactDTO create(
            StudyContactDTO dto) throws PAException {
        if ((dto.getIdentifier() != null) && !PAUtil.isIiNull(dto.getIdentifier())) {
            serviceError(" Update method should be used to modify existing. ");
        }
        if (PAUtil.isIiNull(dto.getHealthCareProvider())) {
            serviceError("Healthcare Provider  must be set.  ");
        }

        StudyContactDTO resultDto = null;
        Session session = null;
        try {
            session = HibernateUtil.getCurrentSession();
            session.beginTransaction();
            StudyContact bo = StudyContactConverter.convertFromDtoToDomain(dto);
            session.saveOrUpdate(bo);
            session.flush();
            resultDto = StudyContactConverter.convertFromDomainToDTO(bo);
        } catch (HibernateException hbe) {
            serviceError(" Hibernate exception in createStudyContact ", hbe);
        }
        return resultDto;
    }
    
    /**
     * @param studyProtocolIi id of protocol
     * @return list StudyContactDTO   
     * @throws PAException on error 
     */
    public List<StudyContactDTO> getByStudyProtocol(
            Ii studyProtocolIi) throws PAException {
        if ((studyProtocolIi == null) || PAUtil.isIiNull(studyProtocolIi)) {
            serviceError(" Ii should not be null ");
        }
        LOG.info("Entering getByStudyProtocol");
        Session session = null;
        List<StudyContact> queryList = new ArrayList<StudyContact>();
        try {
            session = HibernateUtil.getCurrentSession();

            Query query = null;
            
            // step 1: form the hql
            String hql = "select scontact "
                       + "from StudyContact scontact "
                       + "join scontact.studyProtocol spro "
                       + "where spro.id = :studyProtocolId " 
                       + " order by scontact.id ";
            LOG.info(" query StudyContact = " + hql);
            
            // step 2: construct query object
            query = session.createQuery(hql);
            query.setParameter("studyProtocolId", IiConverter.convertToLong(studyProtocolIi));
            queryList = query.list();
            
        }  catch (HibernateException hbe) {
            serviceError(" Hibernate exception while retrieving "
                    + "StudyContact for pid = " + studyProtocolIi.getExtension() , hbe);
        }
        
        List<StudyContactDTO> resultList = new ArrayList<StudyContactDTO>();
        for (StudyContact sc : queryList) {
            resultList.add(StudyContactConverter.convertFromDomainToDTO(sc));
        }
        LOG.info("Leaving getByStudyProtocol");
        return resultList;
    }

   
}
