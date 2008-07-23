package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.domain.Condition;
import gov.nih.nci.pa.domain.StudyCondition;
import gov.nih.nci.pa.dto.DiseaseConditionDTO;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * 
 * @author Harsha
 *
 */
public class DiseaseCondDAO {
    
    private static final Logger LOG  = Logger.getLogger(DiseaseCondDAO.class); 
    
    /**
     * 
     * @param protocolID Long
     * @return List of DiseaseConditionDTO
     * @throws PAException on error
     */
    @SuppressWarnings("unchecked")
    public List<DiseaseConditionDTO> getDiseaseCondition(Long protocolID) throws PAException {     
            LOG.debug("Entering queryDiseaseConditions ");
            List<DiseaseConditionDTO> queryList = new ArrayList<DiseaseConditionDTO>();
            Session session = null;
            try {
                session = HibernateUtil.getCurrentSession();
                Query query = null;               
                String hql = generateDiseaseCondQuery(protocolID);
                LOG.info(" query protocol = " + hql);
                query = session.createQuery(hql);
                queryList = query.list();
            } catch (HibernateException hbe) {
                LOG.error(" Hibernate exception in query protocol ", hbe);
                throw new PAException(" Hibernate exception in query protocol ", hbe);
            }
            LOG.debug("Leaving queryProtocol ");
            return convertToDiseaseConditionDTO(queryList);
    }
    
    /**
     * 
     * @param results
     * @return list of DiseaseConditionDTO
     */
    private List<DiseaseConditionDTO> convertToDiseaseConditionDTO(List results) {
        List<DiseaseConditionDTO> diseaseConditionDtos = new ArrayList<DiseaseConditionDTO>();
        DiseaseConditionDTO conditionDTO = null;
        Condition condition = null;
        StudyCondition studyCondition = null;
        Object[] searchResult = null;
        for (int i = 0; i < results.size(); i++) {
            searchResult = (Object[]) results.get(i);
            condition = (Condition) searchResult[0];
            studyCondition = (StudyCondition) searchResult[1];
            conditionDTO = new DiseaseConditionDTO();
            conditionDTO.setDiseaseCode(condition.getCode());
            conditionDTO.setDiseaseName(condition.getName());
            conditionDTO.setLeadIndicator(studyCondition.getLeadIndicator());
            conditionDTO.setParentCode(condition.getParentCode());
            diseaseConditionDtos.add(conditionDTO);
        }
        return diseaseConditionDtos;
    }
    
    /**
     * 
     * @param protocolID
     * @return query String
     * @throws PAException
     */
    @SuppressWarnings("PMD.ConsecutiveLiteralAppends")
    private String generateDiseaseCondQuery(Long protocolID) throws PAException {
        LOG.debug("Entering generateProtocolQuery ");
        StringBuffer hql = new StringBuffer();
        try {           
            hql.append("select co, sc from StudyCondition as sc" 
                    + " inner join sc.condition as co" 
                    + " where sc.studyProtocol =" + protocolID); 
        } catch (Exception e) {
            LOG.error("General error in while converting to DTO3", e);
            throw new PAException("General error in while converting to DTO4", e);
        } finally {
            LOG.debug("Leaving generateProtocolQuery ");
        }
        return hql.toString();
    }
}
