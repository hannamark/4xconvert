package gov.nih.nci.pa.dao;

import gov.nih.nci.pa.dto.ProtocolDTO;
import gov.nih.nci.pa.domain.HealthcareSite;
import gov.nih.nci.pa.domain.Investigator;
import gov.nih.nci.pa.domain.Protocol;
import gov.nih.nci.pa.domain.ProtocolStatus;
import gov.nih.nci.pa.domain.StudyInvestigator;
import gov.nih.nci.pa.domain.StudySite;
import gov.nih.nci.pa.service.PAException;
import gov.nih.nci.pa.service.ProtocolSearchCriteria;
import gov.nih.nci.pa.util.HibernateUtil;
import gov.nih.nci.pa.util.PAUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;

/**
 * Protocol DAO for accessing DAO.
 *
 * @author Naveen Amiruddin
 * @since 05/22/2007 copyright NCI 2007. All rights reserved. This code may not
 *        be used without the express written permission of the copyright
 *        holder, NCI.
 */
@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.ExcessiveMethodLength", "PMD.UnusedLocalVariable" })
public class ProtocolDAO {

    private static final Logger LOG  = Logger.getLogger(ProtocolDAO.class);    
    private static final int THREE = 3;
    private static final int FOUR = 4;
    private static final int FIVE = 5;


    /**
     *
     * @param protocolSearchCriteria
     *            protocolSearchCriteria
     * @return List queryList
     * @throws PAException paException
     */
    public List<ProtocolDTO> queryProtocol(ProtocolSearchCriteria protocolSearchCriteria) throws PAException {
        LOG.debug("Entering queryProtocol ");
        List<Object> queryList = new ArrayList<Object>();
        Session session = null;
        try {
        session = HibernateUtil.getCurrentSession();
        Query query = null;
        // step 1: form the hql
        String hql = generateProtocolQuery(protocolSearchCriteria);
        LOG.info(" query protocol = " + hql);
        // step 2: construct query object
        query = session.createQuery(hql);
        // step 3: query the result
        queryList = query.list();
        } catch (HibernateException hbe) {
            LOG.error(" Hibernate exception in query protocol ", hbe);
            throw new PAException(" Hibernate exception in query protocol ", hbe);
        }
        LOG.debug("Leaving queryProtocol ");
        return convertToProtocolDTO(queryList);
    }

    /**
     *
     * @param protocolQueryResult
     * @return List ProtocolDTO
     * @throws PAException paException
     */

    private List<ProtocolDTO> convertToProtocolDTO(
            List protocolQueryResult) throws PAException {
        LOG.debug("Entering convertProtocolQueryResultsToProtocolDTO ");
        List<ProtocolDTO> protocolDtos = new ArrayList<ProtocolDTO>();
        ProtocolDTO protocolDto = null;
        Protocol protocol = null;
        ProtocolStatus protocolStatus = null;
        StudySite studySite = null;
        HealthcareSite healthcareSite = null;
        Investigator investigator = null;
        StudyInvestigator studyInvestigator = null;
        // array of objects for each row
        Object[] searchResult = null;
        try {
            for (int i = 0; i < protocolQueryResult.size(); i++) {
                searchResult = (Object[]) protocolQueryResult.get(i);
                if (searchResult == null) {
                       break; 
                }
                protocolDto = new ProtocolDTO();
                // get the protocol object first
                protocol = (Protocol) searchResult[0];
                // get the protocol status
                protocolStatus = (ProtocolStatus) searchResult[1];
                // get the StudySite
                studySite = (StudySite) searchResult[2];
                // get the HealthcareSite
                healthcareSite = (HealthcareSite) searchResult[THREE];
                // get the study investigator
                studyInvestigator = (StudyInvestigator) searchResult[FOUR];
                // get the Investigator
                investigator = (Investigator) searchResult[FIVE];

                // transfer protocol to protocolDto
                protocolDto.setProtocolId(protocol.getId());
                protocolDto.setLongTitleText(protocol.getLongTitleText());
                protocolDto.setStudyTypeCode(protocol.getStudyTypeCode());
                protocolDto.setSponsorMonitorCode(protocol.getSponsorMonitorCode());
                protocolDto.setStudyPhaseCode(protocol.getStudyPhaseCode());
                protocolDto.setNciIdentifier(protocol.getNciIdentifier());
                // transfer studySite to protocolDto
                if (studySite != null && studySite.getLeadOrganizationProtocolId() != null) {
                    protocolDto.setLeadOrganizationProtocolId(studySite.getLeadOrganizationProtocolId());
                }
                // transfer from investigator to protocolDto
                //protocolDto.setPrincipalInvestigatorFullName(investigator.getFullName());
                // transfer from healthcareSite to protocolDto
                if (healthcareSite != null) {
                    protocolDto.setLeadOrganizationName(healthcareSite.getName());
                    protocolDto.setLeadOrganizationId(healthcareSite.getId());
                }
                // add to the list
                protocolDtos.add(protocolDto);
                
                
                
            }
        } catch (Exception e) {
            LOG.error("General error in while converting to DTO", e);
            throw new PAException("General error in while converting to DTO2", e);
        } finally {
            LOG.debug("Leaving convertProtocolQueryResultsToProtocolDTO ");
        }
        return protocolDtos;
    }

    /**
     * generate HQL query for search protocol.
     *
     * @param protocolSearchCriteria
     *            protocolSearchCriteria
     * @return hql
     * @throws PAException paException
     */
    private String generateProtocolQuery(
            ProtocolSearchCriteria protocolSearchCriteria) throws PAException {
        LOG.debug("Entering generateProtocolQuery ");
        StringBuffer hql = new StringBuffer();
        try {
            hql.append(" select pro , ps ,ss , hc , si , inv  from Protocol as pro  " 
                    + "left outer join pro.protocolStatuses as ps  " 
                    + "left join pro.studySites as ss  left join ss.healtcareSite as hc  " 
                    + "left join pro.studyInvestigators  as si left join si.investigator as inv  ")
            .append(generateWhereClause(protocolSearchCriteria));
        } catch (Exception e) {
            LOG.error("General error in while converting to DTO3", e);
            throw new PAException("General error in while converting to DTO4", e);
        } finally {
            LOG.debug("Leaving generateProtocolQuery ");
        }
        return hql.toString();

    }

    /**
     * generate a where clause for a given protocol search criteria.
     *
     * @param protocolSearchCriteria
     * @return String String
     * @throws PAException paException
     */
    @SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.InefficientStringBuffering" })
    private String generateWhereClause(
            ProtocolSearchCriteria protocolSearchCriteria) throws PAException {
        LOG.debug("Entering generateWhereClause ");
        StringBuffer where = new StringBuffer();
        try {
            where.append("where 1 = 1 ");
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria.getProtocolId())) {
                where.append(" and pro.id = ").append(protocolSearchCriteria.getProtocolId());
                      
            }
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria
                    .getNciIdentifier())) {
                where.append(" and pro.nciIdentifier = '").append(protocolSearchCriteria.getNciIdentifier() + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria
                    .getLongTitleText())) {
                where.append(" and upper(pro.longTitleText)  like '%"
                        + protocolSearchCriteria.getLongTitleText().toUpperCase()
                                .trim().replaceAll("'", "''") + "%'");
            }
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria
                    .getStudyTypeCode())) {
                where
                        .append(" and pro.intentCode  = '"
                                + protocolSearchCriteria.getStudyTypeCode()
                                        .getCode() + "'");
            }
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria
                    .getLeadOrganizationProtocolId())) {
                where.append(" and upper(ss.localProtocolIdentifier)  like '%"
                        + protocolSearchCriteria.getLeadOrganizationProtocolId()
                                .toUpperCase().trim().replaceAll("'", "''") + "%'");
            }
            if (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria
                    .getLeadOrganizationId())) {
                where.append(" and hc.id  = '"
                        + protocolSearchCriteria.getLeadOrganizationId() + "'");
            }
        } catch (Exception e) {
            LOG.error("General error in while create where cluase", e);
            throw new PAException("General error in while create where cluase", e);
        } finally {
            LOG.debug("Leaving generateWhereClause ");
        }
        /* todo : to add protocol status column in protocol table
         * if
         * (PAUtil.isNotNullOrNotEmpty(protocolSearchCriteria.getStudyStatusCode())) {
         * where.append(" and ps.statusCode = '" +
         * protocolSearchCriteria.getStudyStatusCode().getCode() + "'"); } else {
         * where.append(" and ps.id in ( select max(ps1.id) from ProtocolStatus
         * as ps1 " + " where pro.id = ps1.protocol.id ) "); }
         */
        return where.toString();
    }
}
